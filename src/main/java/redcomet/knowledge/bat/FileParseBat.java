package redcomet.knowledge.bat;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;

import redcomet.common.config.ConfigLoader;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.common.util.FileUtil;
import redcomet.common.util.StringUtils;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.config.IndexType;
import redcomet.knowledge.dao.KnowledgeFilesDao;
import redcomet.knowledge.dao.KnowledgesDao;
import redcomet.knowledge.dao.TagsDao;
import redcomet.knowledge.entity.KnowledgeFilesEntity;
import redcomet.knowledge.entity.KnowledgesEntity;
import redcomet.knowledge.entity.TagsEntity;
import redcomet.knowledge.indexer.IndexingValue;
import redcomet.knowledge.logic.IndexLogic;
import redcomet.knowledge.logic.KnowledgeLogic;
import redcomet.knowledge.parser.Parser;
import redcomet.knowledge.parser.ParserFactory;
import redcomet.knowledge.vo.ParseResult;

public class FileParseBat {
	/** ログ */
	private static Log LOG = LogFactory.getLog(FileParseBat.class);
	
	public static final int PARSE_STATUS_WAIT = 0;
	public static final int PARSE_STATUS_PARSING = 1;
	public static final int PARSE_STATUS_PARSED = 100;
	
	public static final int PARSE_STATUS_NO_TARGET = -1;
	
	public static final Integer UPDATE_USER_ID = -100; // システムユーザ（パースバッチ）
	
	public static final int TYPE_FILE = IndexType.KnowledgeFile.getValue();
	public static final String ID_PREFIX = "FILE-";
	
	public static void main(String[] args) throws Exception {
		FileParseBat bat = new FileParseBat();
		bat.start();
	}

	private void start() throws Exception {
		LOG.info("start");
		KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
		IndexLogic indexLogic = IndexLogic.get();
		KnowledgesDao knowledgesDao = KnowledgesDao.get();
		TagsDao tagsDao = TagsDao.get();
		
		// パースステータスがパース待ち（0）かつナレッジに紐づいているものを取得
		List<KnowledgeFilesEntity> filesEntities = filesDao.selectWaitStateFiles();
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File tmpDir = new File(appConfig.getTmpPath());
		
		for (KnowledgeFilesEntity knowledgeFilesEntity : filesEntities) {
			// ナレッジを取得
			KnowledgesEntity knowledgesEntity = knowledgesDao.selectOnKey(knowledgeFilesEntity.getKnowledgeId());
			if (knowledgesEntity == null) {
				// 紐づくナレッジが存在していないのであれば解析はしない
				filesDao.changeStatus(knowledgeFilesEntity.getFileNo(), PARSE_STATUS_NO_TARGET, UPDATE_USER_ID);
				continue;
			}
			// タグを取得
			List<TagsEntity> tagsEntities = tagsDao.selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
			
			// DBから取得したバイナリをいったんファイルに格納
			KnowledgeFilesEntity entity = filesDao.selectOnKey(knowledgeFilesEntity.getFileNo());
			StringBuilder name = new StringBuilder();
			name.append(entity.getFileNo());
			String extension = StringUtils.getExtension(entity.getFileName());
			if (StringUtils.isNotEmpty(extension)) {
				name.append(extension);
			}
			
			File tmp = new File(tmpDir, name.toString());
			FileOutputStream outputStream = new FileOutputStream(tmp);
			try {
				FileUtil.copy(entity.getFileBinary(), outputStream);
				LOG.info("file: " + entity.getFileNo() + " to " + tmp.getAbsolutePath());
			} finally {
				outputStream.close();
				entity.getFileBinary().close();
			}
			if (StringUtils.isEmpty(extension)) {
				String mime = Files.probeContentType(tmp.toPath());
				MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
				MimeType mimeType = allTypes.forName(mime);
				String mimeExtention = mimeType.getExtension(); // Mimeから拡張子判定
				if (StringUtils.isNotEmpty(mimeExtention)) {
					name.append(".").append(mimeExtention);
					Files.move(tmp.toPath(), Paths.get(name.toString()), StandardCopyOption.REPLACE_EXISTING);
					tmp = new File(name.toString());
				}
			}
			
			// パースステータスをパース中（1）に変更(もしパースでエラーが発生しても、次回から対象外になる）
			filesDao.changeStatus(entity.getFileNo(), PARSE_STATUS_PARSING, UPDATE_USER_ID);
			
			// パースを実行
			Parser parser = ParserFactory.getParser(tmp.getAbsolutePath());
			ParseResult result = parser.parse(tmp);
			LOG.info("content text(length): " + result.getText().length());
			
			// 全文検索エンジンへ登録
			IndexingValue value = new IndexingValue();
			value.setType(TYPE_FILE);
			value.setId(ID_PREFIX + entity.getFileNo());
			value.setTitle(entity.getFileName());
			value.setContents(result.getText());
			value.addUser(entity.getInsertUser());
			if (knowledgesEntity.getPublicFlag() == null
					|| KnowledgeLogic.PUBLIC_FLAG_PUBLIC == knowledgesEntity.getPublicFlag()) {
				value.addUser(KnowledgeLogic.ALL_USER);
			}
			for (TagsEntity tagsEntity : tagsEntities) {
				value.addTag(tagsEntity.getTagId());
			}
			value.setCreator(entity.getInsertUser());
			value.setTime(entity.getUpdateDatetime().getTime()); // 更新日時をセットするので、更新日時でソート
			indexLogic.save(value);
			
			// パースステータスをパース完了に変更(もしパースでエラーが発生しても、次回から対象外になる）
			filesDao.changeStatus(entity.getFileNo(), PARSE_STATUS_PARSED, UPDATE_USER_ID);
			
			// 正常に終了すれば、テンポラリを削除
			tmp.delete();
			LOG.info("deleteed: " + tmp.getAbsolutePath());
		}
	}

}
