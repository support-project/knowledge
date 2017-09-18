package org.support.project.knowledge.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LoginedUser;

/**
 * Logic for attached file.
 * @author Koda
 */
@DI(instance = Instance.Singleton)
public class UploadedFileLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(UploadedFileLogic.class);
    /** KnowledgeFilesDao */
    private KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
    /** Get instance */
    public static UploadedFileLogic get() {
        return Container.getComp(UploadedFileLogic.class);
    }
    
    /** アイコン画像の種類 */
    public static final String[] ICONS = { "_blank", "_page", "aac", "ai", "aiff", "avi", "bmp", "c", "cpp",
            "css", "dat", "dmg", "doc", "dotx", "dwg", "dxf", "eps", "exe", "flv", "gif", "h",
            "hpp", "html", "ics", "iso", "java", "jpg", "js", "key", "less", "mid", "mp3", "mp4",
            "mpg", "odf", "ods", "odt", "otp", "ots", "ott", "pdf", "php", "png", "ppt", "psd",
            "py", "qt", "rar", "rb", "rtf", "sass", "scss", "sql", "tga", "tgz", "tiff", "txt",
            "wav", "xls", "xlsx", "xml", "yml", "zip" };
    
    
    
    /**
     * ファイルを保存する
     * 
     * @param fileItem
     * @param loginedUser
     * @param context
     * @return
     * @throws IOException
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UploadFile saveFile(FileItem fileItem, LoginedUser loginedUser, String context) throws IOException {
        LOG.trace("saveFile()");
        KnowledgeFilesEntity entity = new KnowledgeFilesEntity();
        entity.setFileName(fileItem.getName());
        entity.setFileSize(new Double(fileItem.getSize()));
        entity.setFileBinary(fileItem.getInputStream());
        entity.setParseStatus(0);
        entity = filesDao.insert(entity);
        UploadFile file = convUploadFile(context, entity);
        // 処理が完了したら、テンポラリのファイルを削除
        fileItem.delete();
        return file;
    }

    /**
     * ファイルを保存する
     * 
     * @param img
     * @param loginedUser
     * @param context
     * @return
     * @throws IOException
     */
    public UploadFile saveFile(byte[] img, LoginedUser loginedUser, String context) {
        LOG.trace("saveFile()");
        KnowledgeFilesEntity entity = new KnowledgeFilesEntity();
        entity.setFileName("image-" + DateUtils.formatTransferDateTime(DateUtils.now()) + ".png");
        entity.setFileSize(new Double(img.length));
        entity.setFileBinary(new ByteArrayInputStream(img));
        entity.setParseStatus(0);
        entity = filesDao.insert(entity);
        UploadFile file = convUploadFile(context, entity);
        return file;
    }
    
    
    
    /**
     * KnowledgeFilesEntity の情報から、画面に戻す UploadFile の情報を生成する
     * 
     * @param context
     * @param entity
     * @return
     */
    public UploadFile convUploadFile(String context, KnowledgeFilesEntity entity) {
        UploadFile file = new UploadFile();
        file.setFileNo(entity.getFileNo());
        file.setUrl(context + "/open.file/download?fileNo=" + entity.getFileNo());
        file.setName(entity.getFileName());
        
        String thumbnai = context + "/bower/teambox.free-file-icons/16px/_blank.png";
        String extention = StringUtils.getExtension(entity.getFileName());
        if (StringUtils.isNotEmpty(extention)) {
            extention = extention.substring(1); // 先頭の「.」を削除
            if (StringUtils.contains(extention, "png", "jpg", "jpeg", "gif", "bmp")) {
                file.setType("image");
            } else if (StringUtils.contains(extention, "pdf", "pptx")) {
                file.setType("slide");
            } else {
                file.setType("file");
            }
            if (StringUtils.contains(extention, ICONS)) {
                thumbnai = context + "/bower/teambox.free-file-icons/16px/" + extention + ".png";
            }
        }
        file.setThumbnailUrl(thumbnai);
        
        file.setSize(entity.getFileSize());
        file.setDeleteUrl(context + "/protect.file/delete?fileNo=" + entity.getFileNo());
        file.setDeleteType("DELETE");
        if (entity.getKnowledgeId() != null && 0 != entity.getKnowledgeId().longValue()) {
            file.setKnowlegeId(entity.getKnowledgeId());
        }
        file.setCommentNo(entity.getCommentNo());
        return file;
    }

    /**
     * ナレッジに紐付く添付ファイルの情報を更新
     * 
     * 1. 現在のナレッジに紐づくファイルを一覧取得する 2. 紐づけるファイルの番号のファイルが存在しない場合、ファイル番号でファイル情報を取得 2-1. 取得したファイル情報に、ナレッジ番号をセットし更新 3. 1で取得したファイル一覧から、処理済（紐付け済）ファイル番号を削除する 4.
     * 1で取得したファイルの一覧で、残っているものがあれば、そのファイルは削除（紐付けがきれている）
     * 
     * @param entity
     * @param fileNos
     * @param loginedUser
     * @param commentNo
     * @throws Exception
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void setKnowledgeFiles(Long knowledgeId, List<Long> fileNos, LoginedUser loginedUser) throws Exception {
        // 現在、すでに紐づいている添付ファイルを取得
        List<KnowledgeFilesEntity> filesEntities = filesDao.selectOnKnowledgeId(knowledgeId);
        Map<Long, KnowledgeFilesEntity> filemap = new HashMap<>();
        for (KnowledgeFilesEntity entity : filesEntities) {
            if (entity.getCommentNo() == null) {
                filemap.put(entity.getFileNo(), entity);
            }
        }

        // 画面で設定されている添付ファイルの番号で紐付けを作成
        if (fileNos != null) {
            for (Long fileNo : fileNos) {
                KnowledgeFilesEntity entity = filesDao.selectOnKeyWithoutBinary(fileNo);
                if (entity != null) {
                    if (entity.getKnowledgeId() == null || 0 == entity.getKnowledgeId().longValue()) {
                        filesDao.connectKnowledge(fileNo, knowledgeId, null, loginedUser);
                    }
                }
                filemap.remove(fileNo);
            }
        }

        // 始めに取得した一覧で、紐付けが作成されなかった（＝紐付けが切れた）ファイルを削除
        Iterator<Long> iterator = filemap.keySet().iterator();
        while (iterator.hasNext()) {
            Long fileNo = (Long) iterator.next();
            // filesDao.delete(fileNo);
            filesDao.physicalDelete(fileNo); // バイナリは大きいので、物理削除する
            // 全文検索エンジンから情報を削除
            IndexLogic indexLogic = IndexLogic.get();
            indexLogic.delete("FILE-" + fileNo);
        }
    }

    /**
     * コメントでナレッジに紐付く添付ファイルを更新
     * 
     * 1. 現在のナレッジに紐づくファイルを一覧取得する 2. 紐づけるファイルの番号のファイルが存在しない場合、ファイル番号でファイル情報を取得 2-1. 取得したファイル情報に、ナレッジ番号をセットし更新 3. 1で取得したファイル一覧から、処理済（紐付け済）ファイル番号を削除する 4.
     * 1で取得したファイルの一覧で、残っているものがあれば、そのファイルは削除（紐付けがきれている）
     * 
     * @param entity
     * @param fileNos
     * @param loginedUser
     * @param commentNo
     * @throws Exception
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void setKnowledgeFiles(Long knowledgeId, List<Long> fileNos, LoginedUser loginedUser, Long commentNo) throws Exception {
        // 現在、すでに紐づいている添付ファイルを取得
        List<KnowledgeFilesEntity> filesEntities = filesDao.selectOnKnowledgeId(knowledgeId);
        Map<Long, KnowledgeFilesEntity> filemap = new HashMap<>();
        for (KnowledgeFilesEntity entity : filesEntities) {
            if (entity.getCommentNo() != null && entity.getCommentNo().equals(commentNo)) {
                filemap.put(entity.getFileNo(), entity);
            }
        }
        // 画面で設定されている添付ファイルの番号で紐付けを作成
        for (Long fileNo : fileNos) {
            KnowledgeFilesEntity entity = filesDao.selectOnKeyWithoutBinary(fileNo);
            if (entity != null) {
                if (entity.getKnowledgeId() == null || 0 == entity.getKnowledgeId().longValue()) {
                    filesDao.connectKnowledge(fileNo, knowledgeId, commentNo, loginedUser);
                }
            }
            filemap.remove(fileNo);
        }

        // 始めに取得した一覧で、紐付けが作成されなかった（＝紐付けが切れた）ファイルを削除
        Iterator<Long> iterator = filemap.keySet().iterator();
        while (iterator.hasNext()) {
            Long fileNo = (Long) iterator.next();
            // filesDao.delete(fileNo);
            filesDao.physicalDelete(fileNo); // バイナリは大きいので、物理削除する

            // 全文検索エンジンから情報を削除
            IndexLogic indexLogic = IndexLogic.get();
            indexLogic.delete("FILE-" + fileNo);
        }
    }

    /**
     * ファイルを削除する
     * 
     * @param fileNo
     * @throws Exception
     */
    public void removeFile(Long fileNo) throws Exception {
        // DBのデータを削除
        filesDao.physicalDelete(fileNo); // バイナリは大きいので、物理削除する

        // 全文検索エンジンから情報を削除
        IndexLogic indexLogic = IndexLogic.get();
        indexLogic.delete("FILE-" + fileNo);
    }

    /**
     * 指定のナレッジに紐づく添付ファイルの情報を取得
     * 
     * @param knowledgeId
     * @param context
     * @return
     */
    public List<UploadFile> selectOnKnowledgeId(Long knowledgeId, String context) {
        List<UploadFile> files = new ArrayList<UploadFile>();
        List<KnowledgeFilesEntity> filesEntities = filesDao.selectOnKnowledgeId(knowledgeId);
        for (KnowledgeFilesEntity entity : filesEntities) {
            files.add(convUploadFile(context, entity));
        }
        return files;
    }

    /**
     * 指定のナレッジに紐づく添付ファイルの情報を取得
     * 
     * @param knowledgeId
     * @param context
     * @return
     */
    public List<UploadFile> selectOnKnowledgeIdWithoutCommentFiles(Long knowledgeId, String context) {
        List<UploadFile> files = new ArrayList<UploadFile>();
        List<KnowledgeFilesEntity> filesEntities = filesDao.selectOnKnowledgeId(knowledgeId);
        for (KnowledgeFilesEntity entity : filesEntities) {
            if (entity.getCommentNo() == null || entity.getCommentNo() == 0) {
                files.add(convUploadFile(context, entity));
            }
        }
        return files;
    }

    /**
     * 指定の添付ファイル番号の情報を取得
     * 
     * @param fileNos
     * @param context
     * @return
     */
    public List<UploadFile> selectOnFileNos(List<Long> fileNos, String context) {
        List<UploadFile> files = new ArrayList<UploadFile>();
        for (Long fileNo : fileNos) {
            KnowledgeFilesEntity entity = filesDao.selectOnKeyWithoutBinary(fileNo);
            files.add(convUploadFile(context, entity));
        }
        return files;
    }

    /**
     * ナレッジを削除する際に、添付ファイルを削除
     * 
     * @param knowledgeId
     * @throws Exception
     */
    public void deleteOnKnowledgeId(Long knowledgeId) throws Exception {
        List<KnowledgeFilesEntity> filesEntities = filesDao.selectOnKnowledgeId(knowledgeId);
        for (KnowledgeFilesEntity entity : filesEntities) {
            filesDao.physicalDelete(entity.getFileNo());
            // 全文検索エンジンから情報を削除
            IndexLogic indexLogic = IndexLogic.get();
            indexLogic.delete("FILE-" + entity.getFileNo());
        }
    }

    /**
     * ダウンロードする添付ファイルを取得 （取得して良い権限が無い場合はNULLを返す)
     * 
     * @param fileNo
     * @param loginedUser
     * @return
     */
    public KnowledgeFilesEntity getFile(Long fileNo, LoginedUser loginedUser) {
        KnowledgeFilesEntity entity = filesDao.selectOnKeyWithoutBinary(fileNo);
        if (entity == null) {
            return null;
        }
        if (entity.getKnowledgeId() != null && 0 != entity.getKnowledgeId().longValue()) {
            // ナレッジに紐づいている場合、そのナレッジにアクセスできれば添付ファイルにアクセス可能
            KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
            if (knowledgeLogic.select(entity.getKnowledgeId(), loginedUser) == null) {
                return null;
            }
        } else {
            // ナレッジに紐づいていない場合、登録者のみがアクセス可能
            if (loginedUser == null) {
                return null;
            }
            if (entity.getInsertUser().intValue() != loginedUser.getUserId().intValue()) {
                return null;
            }
        }
        return filesDao.selectOnKey(fileNo);
    }


}
