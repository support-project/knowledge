package org.support.project.knowledge.bat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.CompressLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.ExportUser;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;

public class CreateExportDataBat extends AbstractBat {
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static final String DATA_DIR = "DataExport";

    public static void main(String[] args) throws Exception {
        try {
            initLogName("CreateExportDataBat.log");
            configInit(ClassUtils.getShortClassName(CreateExportDataBat.class));
    
            CreateExportDataBat bat = new CreateExportDataBat();
            bat.dbInit();
            bat.start();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    private void start() throws Exception {
        SystemConfigsEntity entity = SystemConfigsDao.get().selectOnKey(SystemConfig.DATA_EXPORT, AppConfig.get().getSystemName());
        if (entity == null) {
            send("[Fail] create fail. please try again.");
            return;
        }
        // エクスポートデータを格納するディレクトリ
        AppConfig config = AppConfig.get();
        File base = new File(config.getTmpPath());
        File dir = new File(base, DATA_DIR);
        if (dir.exists()) {
            FileUtil.delete(dir);
        }
        dir.mkdirs();
        File attach = new File(dir, "attach");
        attach.mkdirs();
        File userdir = new File(dir, "user");
        userdir.mkdirs();

        // ナレッジデータを取得
        AccessUser loginedUser = new AccessUser() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isAdmin() {
                return true;
            }
        };
        KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
        CommentsDao commentsDao = CommentsDao.get();
        KnowledgeFilesDao knowledgeFilesDao = KnowledgeFilesDao.get();

        List<KnowledgesEntity> knowledges = knowledgeLogic.searchKnowledge("", loginedUser, 0, 100).getItems();
        for (KnowledgesEntity knowledgesEntity : knowledges) {
            File f = new File(dir, "knowledge-" + StringUtils.zeroPadding(knowledgesEntity.getKnowledgeId(), 6) + ".xml");
            String xml = SerializeUtils.objectToXml(knowledgesEntity);
            FileUtil.write(f, xml);

            // ナレッジのコメントを取得
            List<CommentsEntity> comments = commentsDao.selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
            for (CommentsEntity commentsEntity : comments) {
                File c = new File(dir, "comment-" + StringUtils.zeroPadding(knowledgesEntity.getKnowledgeId(), 6) + "-"
                        + StringUtils.zeroPadding(commentsEntity.getKnowledgeId(), 6) + ".xml");
                xml = SerializeUtils.objectToXml(commentsEntity);
                FileUtil.write(c, xml);
            }
            List<KnowledgeFilesEntity> files = knowledgeFilesDao.selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
            for (KnowledgeFilesEntity knowledgeFilesEntity : files) {
                KnowledgeFilesEntity file = knowledgeFilesDao.selectOnKey(knowledgeFilesEntity.getFileNo());
                File a = new File(attach, "attach-" + StringUtils.zeroPadding(knowledgesEntity.getKnowledgeId(), 6) + "-"
                        + StringUtils.zeroPadding(knowledgeFilesEntity.getFileNo(), 6) + knowledgeFilesEntity.getFileName());
                OutputStream outputStream = new FileOutputStream(a);
                InputStream inputStream = file.getFileBinary();
                try {
                    FileUtil.copy(inputStream, outputStream);
                } finally {
                    inputStream.close();
                    outputStream.close();
                }
            }
            send("[Export] knowledge-" + knowledgesEntity.getKnowledgeId());
        }

        // ユーザ情報を取得
        UsersDao usersDao = UsersDao.get();
        List<UsersEntity> users = usersDao.selectAll();
        for (UsersEntity usersEntity : users) {
            ExportUser user = new ExportUser();
            PropertyUtil.copyPropertyValue(usersEntity, user);
            File f = new File(userdir, "user-" + StringUtils.zeroPadding(user.getUserId(), 6) + ".xml");
            String xml = SerializeUtils.objectToXml(user);
            FileUtil.write(f, xml);
            send("[Export] user-" + user.getUserId());
        }

        // zip圧縮
        send("[Export] during the compression");
        String name = DATA_DIR + ".zip";
        File comp = new File(base, name);

        BufferedOutputStream output = null;
        ZipArchiveOutputStream os = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(comp));
            os = new ZipArchiveOutputStream(output);
            os.setEncoding("UTF-8");
            CompressLogic.get().addZip(os, dir, null);
        } finally {
            if (os != null) {
                os.close();
            }
            if (output != null) {
                output.close();
            }
        }
        send("[Export] complete!");
        // 圧縮の予約を削除
        SystemConfigsDao.get().physicalDelete(entity);
    }

}
