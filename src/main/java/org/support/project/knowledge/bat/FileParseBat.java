package org.support.project.knowledge.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.IndexType;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.indexer.IndexingValue;
import org.support.project.knowledge.logic.CrawlerLogic;
import org.support.project.knowledge.logic.IndexLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.parser.Parser;
import org.support.project.knowledge.parser.ParserFactory;
import org.support.project.knowledge.vo.ParseResult;
import org.support.project.web.dao.ProxyConfigsDao;
import org.support.project.web.entity.ProxyConfigsEntity;

public class FileParseBat extends AbstractBat {
	/** ログ */
	private static Log LOG = LogFactory.getLog(FileParseBat.class);
	
	public static final int PARSE_STATUS_WAIT = 0;
	public static final int PARSE_STATUS_PARSING = 1;
	public static final int PARSE_STATUS_ERROR_FINISHED = -100;
	public static final int PARSE_STATUS_PARSED = 100;
	
	public static final int PARSE_STATUS_NO_TARGET = -1;
	
	public static final Integer UPDATE_USER_ID = -100; // システムユーザ（パースバッチ）
	
	public static final int TYPE_FILE = IndexType.KnowledgeFile.getValue();
	public static final String ID_PREFIX = "FILE-";
	public static final String WEB_ID_PREFIX = "WEB-";
	
	public static void main(String[] args) throws Exception {
		initLogName("FileParseBat.log");
		configInit(ClassUtils.getShortClassName(FileParseBat.class));
		
		FileParseBat bat = new FileParseBat();
		bat.dbInit();
		bat.start();
		
		finishInfo();
	}

	private void start() throws Exception {
		fileParse();
		crawl();
	}

	private void crawl() throws Exception {
		KnowledgeItemValuesDao itemValuesDao = KnowledgeItemValuesDao.get();
		List<KnowledgeItemValuesEntity> itemValues = itemValuesDao.selectOnTypeIdAndItemNoAndStatus(
				TemplateMastersDao.TYPE_ID_BOOKMARK, TemplateItemsDao.ITEM_ID_BOOKMARK_URL, KnowledgeItemValuesEntity.STATUS_SAVED);
		if (itemValues != null && !itemValues.isEmpty()) {
			ProxyConfigsEntity proxyConfigs = ProxyConfigsDao.get().selectOnKey(AppConfig.get().getSystemName());
			if (proxyConfigs == null) {
				proxyConfigs = new  ProxyConfigsEntity();
			}
			LOG.info("web target count: " + itemValues.size());
			CrawlerLogic logic = CrawlerLogic.get();
			for (KnowledgeItemValuesEntity itemValue : itemValues) {
				KnowledgesEntity knowledgesEntity = KnowledgesDao.get().selectOnKey(itemValue.getKnowledgeId());
				if (knowledgesEntity == null) {
					continue;
				}
				// タグを取得
				List<TagsEntity> tagsEntities = TagsDao.get().selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
				// Webアクセス
				String content = null;
				try {
					content = logic.crawle(proxyConfigs, itemValue.getItemValue());
				} catch (Exception e) {
					LOG.error("crawl error:" + itemValue.getItemValue(), e);
					// ステータス更新(エラー)
					itemValue.setItemStatus(KnowledgeItemValuesEntity.STATUS_WEBACCESS_ERROR);
					itemValuesDao.update(itemValue);
					continue;
				}
				if (StringUtils.isNotEmpty(content)) {
					LOG.info("[SUCCESS] " + itemValue.getItemValue());
					// 全文検索エンジンにのみ、検索できるように情報登録
					IndexingValue value = new IndexingValue();
					value.setType(IndexType.bookmarkContent.getValue());
					value.setId(WEB_ID_PREFIX + itemValue.getKnowledgeId());
					value.setTitle(itemValue.getItemValue());
					value.setContents(content);

					value.addUser(knowledgesEntity.getInsertUser());
					if (knowledgesEntity.getPublicFlag() == null
							|| KnowledgeLogic.PUBLIC_FLAG_PUBLIC == knowledgesEntity.getPublicFlag()) {
						value.addUser(KnowledgeLogic.ALL_USER);
					}
					for (TagsEntity tagsEntity : tagsEntities) {
						value.addTag(tagsEntity.getTagId());
					}
					value.setCreator(knowledgesEntity.getInsertUser());
					value.setTime(knowledgesEntity.getUpdateDatetime().getTime()); // 更新日時をセットするので、更新日時でソート
					IndexLogic.get().save(value);
					
					// ステータス更新
					itemValue.setItemStatus(KnowledgeItemValuesEntity.STATUS_WEBACCESSED);
					itemValuesDao.update(itemValue);
				}
			}
		}
	}

	private void fileParse() throws FileNotFoundException, IOException, MimeTypeException, Exception {
		KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
		IndexLogic indexLogic = IndexLogic.get();
		KnowledgesDao knowledgesDao = KnowledgesDao.get();
		TagsDao tagsDao = TagsDao.get();
		
		// パースステータスがパース待ち（0）かつナレッジに紐づいているものを取得
		List<KnowledgeFilesEntity> filesEntities = filesDao.selectWaitStateFiles();
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File tmpDir = new File(appConfig.getTmpPath());
		LOG.info("file target count: " + filesEntities.size());
		
		for (KnowledgeFilesEntity knowledgeFilesEntity : filesEntities) {
			// ナレッジを取得
			KnowledgesEntity knowledgesEntity = knowledgesDao.selectOnKey(knowledgeFilesEntity.getKnowledgeId());
			if (knowledgesEntity == null) {
				// 紐づくナレッジが存在していないのであれば解析はしない（例えば、一度添付ファイル付きのナレッジを登録後、ナレッジを削除した場合）
				// ナレッジに紐づいていないファイルで、かつ更新日が24時間前のものは削除される
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
			
			try {
				// パースを実行
				Parser parser = ParserFactory.getParser(tmp.getAbsolutePath());
				ParseResult result = parser.parse(tmp);
				LOG.info("content text(length): " + result.getText().length());
				LOG.info("content text        : " + StringUtils.abbreviate(result.getText(), 300));
				
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
				
			} catch (Exception e) {
				// パースの解析でなんらかのエラー
				filesDao.changeStatus(entity.getFileNo(), PARSE_STATUS_ERROR_FINISHED, UPDATE_USER_ID);
				LOG.error("File parse error.", e);
				throw e;
			}
			// 終了すれば、テンポラリを削除
			tmp.delete();
			LOG.info("deleteed: " + tmp.getAbsolutePath());
		}
	}

}
