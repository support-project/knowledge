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
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.indexer.IndexingValue;
import org.support.project.knowledge.logic.CrawlerLogic;
import org.support.project.knowledge.logic.IndexLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.SlideLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.parser.Parser;
import org.support.project.knowledge.parser.ParserFactory;
import org.support.project.knowledge.vo.ParseResult;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.dao.ProxyConfigsDao;
import org.support.project.web.entity.ProxyConfigsEntity;

public class FileParseBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(FileParseBat.class);

    public static final int PARSE_STATUS_WAIT = 0;
    public static final int PARSE_STATUS_SLIDE_PARSED = 1;
    /** スライドからテキスト抽出が終わった場合のステータス(これ以下のステータスは、パース対象） */
    public static final int PARSE_STATUS_PARSING = 50;
    public static final int PARSE_STATUS_PARSED = 100;
    public static final int PARSE_STATUS_ERROR_FINISHED = 1000;
    public static final int PARSE_STATUS_NO_TARGET = 1001;

    public static final Integer UPDATE_USER_ID = -100; // システムユーザ（パースバッチ）

    public static final int TYPE_FILE = IndexType.KnowledgeFile.getValue();
    public static final String ID_PREFIX = "FILE-";
    public static final String WEB_ID_PREFIX = "WEB-";

    public static void main(String[] args) throws Exception {
        try {
            initLogName("FileParseBat.log");
            configInit(ClassUtils.getShortClassName(FileParseBat.class));
    
            FileParseBat bat = new FileParseBat();
            bat.dbInit();
            bat.start();
    
            finishInfo();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    private void start() throws Exception {
        fileParse();
        crawl();
    }

    
    /**
     * 情報の種別がBookmarkの場合に、URLに書かれているページのテキストを取得し検索可能にする
     * @throws Exception
     */
    private void crawl() throws Exception {
        KnowledgeItemValuesDao itemValuesDao = KnowledgeItemValuesDao.get();
        List<KnowledgeItemValuesEntity> itemValues = itemValuesDao.selectOnTypeIdAndItemNoAndStatus(TemplateLogic.TYPE_ID_BOOKMARK,
                TemplateItemsDao.ITEM_ID_BOOKMARK_URL, KnowledgeItemValuesEntity.STATUS_SAVED);
        if (itemValues != null && !itemValues.isEmpty()) {
            ProxyConfigsEntity proxyConfigs = ProxyConfigsDao.get().selectOnKey(AppConfig.get().getSystemName());
            if (proxyConfigs == null) {
                proxyConfigs = new ProxyConfigsEntity();
            }
            LOG.info("web target count: " + itemValues.size());
            CrawlerLogic logic = CrawlerLogic.get();
            for (KnowledgeItemValuesEntity itemValue : itemValues) {
                KnowledgesEntity knowledgesEntity = KnowledgesDao.get().selectOnKey(itemValue.getKnowledgeId());
                if (knowledgesEntity == null) {
                    continue;
                }
                // パースステータスを処理中に変更
                itemValue.setItemStatus(KnowledgeItemValuesEntity.STATUS_DO_PROCESS);
                itemValuesDao.update(itemValue);
                
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
                    value.setTemplate(knowledgesEntity.getTypeId());
                    value.setTitle(itemValue.getItemValue());
                    value.setContents(content);
                    value.addUser(knowledgesEntity.getInsertUser());
                    if (knowledgesEntity.getPublicFlag() == null || KnowledgeLogic.PUBLIC_FLAG_PUBLIC == knowledgesEntity.getPublicFlag()) {
                        value.addUser(KnowledgeLogic.ALL_USER);
                    } else if (knowledgesEntity.getPublicFlag() != null 
                            && knowledgesEntity.getPublicFlag().intValue() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
                        List<LabelValue> targets = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgesEntity.getKnowledgeId());
                        if (targets != null) {
                            for (LabelValue target : targets) {
                                Integer id = TargetLogic.get().getGroupId(target.getValue());
                                if (id != Integer.MIN_VALUE) {
                                    value.addGroup(id);
                                }
                                id = TargetLogic.get().getUserId(target.getValue());
                                if (id != Integer.MIN_VALUE) {
                                    value.addUser(id);
                                }
                            }
                        }
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

    /**
     * 添付したファイルの中身を抽出する
     * @throws FileNotFoundException
     * @throws IOException
     * @throws MimeTypeException
     * @throws Exception
     */
    private void fileParse() throws FileNotFoundException, IOException, MimeTypeException, Exception {
        KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
        IndexLogic indexLogic = IndexLogic.get();
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        TagsDao tagsDao = TagsDao.get();

        // パースステータスがパース中より前のものを取得
        List<KnowledgeFilesEntity> filesEntities = filesDao.selectWaitStateFiles();
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File tmpDir = new File(appConfig.getTmpPath());
        LOG.info("file target count: " + filesEntities.size());

        for (KnowledgeFilesEntity knowledgeFilesEntity : filesEntities) {
            // DBから取得したバイナリをいったんファイルに格納
            KnowledgeFilesEntity entity = filesDao.selectOnKey(knowledgeFilesEntity.getFileNo());
            if (entity.getFileBinary() == null) {
                LOG.error("binary input stream is null. file no = " + entity.getFileNo() + ". skip this file.");
                filesDao.changeStatus(entity.getFileNo(), PARSE_STATUS_ERROR_FINISHED, UPDATE_USER_ID);
                continue;
            }
            
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
                try {
                    outputStream.close();
                    entity.getFileBinary().close();
                } catch (Exception e) {
                    LOG.error("Any exception was thrown.", e);
                }
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
            try {
                try {
                    // スライドの抽出は、パース待ちの場合は、どのようなステータスでも実施
                    if (entity.getParseStatus().intValue() == PARSE_STATUS_WAIT) {
                        boolean slideParse = SlideLogic.get().parseSlide(tmp, knowledgeFilesEntity);
                        if (slideParse) {
                            // スライドのパース済のステータスへ
                            filesDao.changeStatus(entity.getFileNo(), PARSE_STATUS_SLIDE_PARSED, UPDATE_USER_ID);
                        }
                    }
                } catch (Exception e) {
                    // スライド画像抽出に失敗しても、ログだけだして次の処理へ
                    LOG.error("Slide parse error. file_no = " + entity.getFileNo(), e);
                }
                
                // ナレッジを取得
                KnowledgesEntity knowledgesEntity = knowledgesDao.selectOnKey(knowledgeFilesEntity.getKnowledgeId());
                if (knowledgesEntity == null) {
                    // 紐づくナレッジが存在していないのであれば解析はしない（例えば、一度添付ファイル付きのナレッジを登録後、ナレッジを削除した場合）
                    // 理由：ナレッジに紐付いていないため、アクセス権が不定
                    // ナレッジに紐づいていないファイルで、かつ更新日が24時間前のものはステータスを更新する
                    if (knowledgeFilesEntity.getDraftId() == null) {
                        filesDao.changeStatus(knowledgeFilesEntity.getFileNo(), PARSE_STATUS_NO_TARGET, UPDATE_USER_ID);
                    } else {
                        DraftKnowledgesEntity draft = DraftKnowledgesDao.get().selectOnKey(knowledgeFilesEntity.getDraftId());
                        if (draft == null) {
                            // 下書きが存在する場合は、削除対象にしないが、下書きも消えている場合はステータスを更新する
                            filesDao.changeStatus(knowledgeFilesEntity.getFileNo(), PARSE_STATUS_NO_TARGET, UPDATE_USER_ID);
                        }
                    }
                    continue;
                }
                // タグを取得
                List<TagsEntity> tagsEntities = tagsDao.selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
                
                // パースステータスをパース中（1）に変更(もしパースでエラーが発生しても、次回から対象外になる）
                filesDao.changeStatus(entity.getFileNo(), PARSE_STATUS_PARSING, UPDATE_USER_ID);

                // パースを実行
                Parser parser = ParserFactory.getParser(tmp.getAbsolutePath());
                ParseResult result = parser.parse(tmp);
                LOG.info("content text(length): " + result.getText().length());
                LOG.info("content text        : " + StringUtils.abbreviate(result.getText(), 300));

                // 全文検索エンジンへ登録
                IndexingValue value = new IndexingValue();
                value.setType(TYPE_FILE);
                value.setId(ID_PREFIX + entity.getFileNo());
                value.setTemplate(knowledgesEntity.getTypeId());
                value.setTitle(entity.getFileName());
                value.setContents(result.getText());
                value.addUser(entity.getInsertUser());
                if (knowledgesEntity.getPublicFlag() == null || KnowledgeLogic.PUBLIC_FLAG_PUBLIC == knowledgesEntity.getPublicFlag()) {
                    value.addUser(KnowledgeLogic.ALL_USER);
                } else if (knowledgesEntity.getPublicFlag() != null 
                        && knowledgesEntity.getPublicFlag().intValue() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
                    List<LabelValue> targets = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgesEntity.getKnowledgeId());
                    if (targets != null) {
                        for (LabelValue target : targets) {
                            Integer id = TargetLogic.get().getGroupId(target.getValue());
                            if (id != Integer.MIN_VALUE) {
                                value.addGroup(id);
                            }
                            id = TargetLogic.get().getUserId(target.getValue());
                            if (id != Integer.MIN_VALUE) {
                                value.addUser(id);
                            }
                        }
                    }
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
            } finally {
                // 終了すれば、テンポラリを削除
                tmp.delete();
                LOG.info("deleteed: " + tmp.getAbsolutePath());
            }
        }
    }

}
