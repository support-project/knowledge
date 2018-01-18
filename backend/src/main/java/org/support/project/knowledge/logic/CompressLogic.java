package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class CompressLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static CompressLogic get() {
        return Container.getComp(CompressLogic.class);
    }

    /**
     * ダウンロードするファイルを追加していく
     * 
     * @param os
     * @param file
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void addZip(ZipArchiveOutputStream os, File file, String basePath) throws IOException, FileNotFoundException {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotEmpty(basePath)) {
            builder.append(basePath);
        }
        builder.append(file.getName());
        if (file.isDirectory()) {
            builder.append("/");
            ZipArchiveEntry zae = new ZipArchiveEntry(builder.toString());
            os.putArchiveEntry(zae);
            LOG.debug("add zip: " + builder.toString());

            os.closeArchiveEntry();
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    addZip(os, f, builder.toString());
                }
            }
        } else if (file.isFile()) {
            ZipArchiveEntry zae = new ZipArchiveEntry(builder.toString());
            os.putArchiveEntry(zae);

            LOG.debug("add zip: " + builder.toString());
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            while (true) {
                int len = is.read(buffer);
                if (len < 0) {
                    break;
                }
                os.write(buffer, 0, len);
            }
            is.close();
            os.closeArchiveEntry();
        }
    }

    /**
     * Zipを解凍
     * 
     * @param fileItem
     * @param tmp
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void unZip(FileItem fileItem, File tmp) throws IOException, FileNotFoundException {
        ZipArchiveInputStream archive = null;
        try {
            archive = new ZipArchiveInputStream(fileItem.getInputStream(), "UTF-8", true);
            ZipArchiveEntry entry;
            while ((entry = archive.getNextZipEntry()) != null) {
                File file = new File(tmp, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        IOUtils.copy(archive, out);
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                }
            }
        } finally {
            if (archive != null) {
                archive.close();
            }
            fileItem.delete();
        }
    }
}
