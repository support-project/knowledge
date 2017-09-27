package org.support.project.knowledge.logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.fileupload.FileItem;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.FileUtil;
import org.support.project.common.wrapper.FileInputStreamWithDeleteWrapper;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;

@DI(instance = Instance.Singleton)
public class DatabaseLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DatabaseLogic.class);

    public static DatabaseLogic get() {
        return Container.getComp(DatabaseLogic.class);
    }

    /**
     * バックアップデータを取得 （H2DatabaseのデータとLuceneのインデックス）
     * 
     * @return
     * @throws IOException
     */
    public FileInputStreamWithDeleteWrapper getData() throws IOException {
        AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File tmpDir = new File(config.getTmpPath());
        String name = "knowledge-" + DateUtils.formatTransferDateTime(DateUtils.now()) + ".zip";
        File tmp = new File(tmpDir, name);

        BufferedOutputStream output = null;
        ZipArchiveOutputStream os = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(tmp));
            os = new ZipArchiveOutputStream(output);
            os.setEncoding("UTF-8");
            File dbDir = new File(config.getDatabasePath());
            File indexDir = new File(config.getIndexPath());
            CompressLogic.get().addZip(os, dbDir, null);
            CompressLogic.get().addZip(os, indexDir, null);
        } finally {
            if (os != null) {
                os.close();
            }
            if (output != null) {
                output.close();
            }
        }

        FileInputStreamWithDeleteWrapper inputStream = new FileInputStreamWithDeleteWrapper(tmp);
        return inputStream;
    }

    /**
     * DBの復元
     * 
     * @param fileItem
     * @return
     * @throws IOException
     */
    public List<ValidateError> restore(FileItem fileItem) throws IOException {
        List<ValidateError> errors = new ArrayList<ValidateError>();
        AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File tmpDir = new File(config.getTmpPath());
        String name = "knowledge-restore-" + DateUtils.formatTransferDateTime(DateUtils.now()) + "";
        File tmp = new File(tmpDir, name);
        if (tmp.exists() && !tmp.isDirectory()) {
            errors.add(new ValidateError("knowledge.data.label.msg.fail"));
            return errors;
        }
        tmp.mkdirs();

        // zipファイルを解凍
        CompressLogic.get().unZip(fileItem, tmp);

        // 解凍した中身の検証
        File[] children = tmp.listFiles();
        if (children != null && children.length != 2) {
            errors.add(new ValidateError("knowledge.data.label.msg.invalid.file"));
            return errors;
        }
        boolean dbexists = false;
        boolean indexexists = false;
        File dbDir = new File(config.getDatabasePath());
        File indexDir = new File(config.getIndexPath());

        if (children != null) {
            for (File file : children) {
                if (file.getName().equals(dbDir.getName())) {
                    dbexists = true;
                } else if (file.getName().equals(indexDir.getName())) {
                    indexexists = true;
                }
            }
        }
        if (!dbexists || !indexexists) {
            errors.add(new ValidateError("knowledge.data.label.msg.invalid.file"));
            return errors;
        }

        // 復元
        // H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
        try {
            // h2dbServerLogic.stop(); // 復元中は停止（他のユーザの操作はエラーになる）

            Path dbfrom = Paths.get(tmp.getAbsolutePath(), dbDir.getName());
            Path idxfrom = Paths.get(tmp.getAbsolutePath(), indexDir.getName());

            Path dbtarget = dbDir.toPath();
            Path idxtarget = indexDir.toPath();

            // Files.delete(dbtarget);
            FileUtil.delete(dbDir);
            LOG.info("remove: " + dbDir.getAbsolutePath());
            // Files.delete(idxtarget);
            FileUtil.delete(indexDir);
            LOG.info("remove: " + dbDir.getAbsolutePath());

            // Files.copy(dbfrom, dbtarget, StandardCopyOption.REPLACE_EXISTING);
            Files.move(dbfrom, dbtarget, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("restore: " + dbDir.getAbsolutePath());
            Files.move(idxfrom, idxtarget, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("restore: " + indexDir.getAbsolutePath());
            tmp.delete();
        } finally {
            LOG.trace("restore process end.");
            // h2dbServerLogic.start();
        }
        return errors;
    }

}
