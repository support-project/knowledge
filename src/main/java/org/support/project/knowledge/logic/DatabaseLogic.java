package org.support.project.knowledge.logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.common.wrapper.FileInputStreamWithDeleteWrapper;
import org.support.project.di.Container;
import org.support.project.knowledge.config.AppConfig;

public class DatabaseLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(DatabaseLogic.class);

	public static DatabaseLogic get() {
		return Container.getComp(DatabaseLogic.class);
	}
	
	/**
	 * バックアップデータを取得
	 * （H2DatabaseのデータとLuceneのインデックス）
	 * @return
	 * @throws IOException
	 */
	public FileInputStreamWithDeleteWrapper getData() throws IOException {
		AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File tmpDir = new File(config.getTmpPath());
		String name = "knowledge-" + DateUtils.formatTransferDateTime(new Date()) + ".zip";
		File tmp = new File(tmpDir, name);
		
		BufferedOutputStream output = null;
		ZipArchiveOutputStream os = null;
		try {
			output = new BufferedOutputStream(new FileOutputStream(tmp));
			os = new ZipArchiveOutputStream(output);
			os.setEncoding("UTF-8");
			File dbDir = new File(config.getDatabasePath());
			File indexDir = new File(config.getIndexPath());
			addZip(os, dbDir, null);
			addZip(os, indexDir, null);
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
	 * ダウンロードするファイルを追加していく
	 * @param os
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void addZip(ZipArchiveOutputStream os, File file, String basePath) throws IOException, FileNotFoundException {
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
			for (File f : files) {
				addZip(os, f, builder.toString());
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
	 * DBの復元
	 * @param fileItem
	 * @return
	 * @throws IOException 
	 */
	public List<ValidateError> restore(FileItem fileItem) throws IOException {
		List<ValidateError> errors = new ArrayList<ValidateError>();
		AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File tmpDir = new File(config.getTmpPath());
		String name = "knowledge-restore-" + DateUtils.formatTransferDateTime(new Date()) + "";
		File tmp = new File(tmpDir, name);
		if (tmp.exists() && !tmp.isDirectory()) {
			errors.add(new ValidateError("knowledge.data.label.msg.fail"));
			return errors;
		}
		tmp.mkdirs();
		
		//zipファイルを解凍
		unZip(fileItem, tmp);
		
		// 解凍した中身の検証
		File[] children = tmp.listFiles();
		if (children.length != 2) {
			errors.add(new ValidateError("knowledge.data.label.msg.invalid.file"));
			return errors;
		}
		boolean dbexists = false;
		boolean indexexists = false;
		File dbDir = new File(config.getDatabasePath());
		File indexDir = new File(config.getIndexPath());
		
		for (File file : children) {
			if (file.getName().equals(dbDir.getName())) {
				dbexists = true;
			} else if (file.getName().equals(indexDir.getName())) {
				indexexists = true;
			}
		}
		if (!dbexists || !indexexists) {
			errors.add(new ValidateError("knowledge.data.label.msg.invalid.file"));
			return errors;
		}
		
		// 復元
//		H2DBServerLogic h2dbServerLogic = H2DBServerLogic.get();
		try {
//			h2dbServerLogic.stop(); // 復元中は停止（他のユーザの操作はエラーになる）
			
			Path dbfrom = Paths.get(tmp.getAbsolutePath(), dbDir.getName());
			Path idxfrom = Paths.get(tmp.getAbsolutePath(), indexDir.getName());
			
			Path dbtarget = dbDir.toPath();
			Path idxtarget = indexDir.toPath();
			
			//Files.delete(dbtarget);
			FileUtil.delete(dbDir);
			LOG.info("remove: " + dbDir.getAbsolutePath());
			//Files.delete(idxtarget);
			FileUtil.delete(indexDir);
			LOG.info("remove: " + dbDir.getAbsolutePath());
			
			//Files.copy(dbfrom, dbtarget, StandardCopyOption.REPLACE_EXISTING);
			Files.move(dbfrom, dbtarget, StandardCopyOption.REPLACE_EXISTING);
			LOG.info("restore: " + dbDir.getAbsolutePath());
			Files.move(idxfrom, idxtarget, StandardCopyOption.REPLACE_EXISTING);
			LOG.info("restore: " + indexDir.getAbsolutePath());
			tmp.delete();
		} finally {
//			h2dbServerLogic.start();
		}
		return errors;
	}
	
	/**
	 * Zipを解凍
	 * @param fileItem
	 * @param tmp
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void unZip(FileItem fileItem, File tmp) throws IOException, FileNotFoundException {
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
