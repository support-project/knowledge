package org.support.project.ormapping.config.impl;

import java.io.InputStream;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.config.ConnectionConfigLoader;
import org.support.project.ormapping.exception.ORMappingException;

public class ConnectionConfigXmlLoader implements ConnectionConfigLoader {
	/** ログ */
	private static Log logger = LogFactory.getLog(ConnectionConfigXmlLoader.class);

	@Override
	public ConnectionConfig load(String path) throws ORMappingException {
		try {
			InputStream inputStream = null;
			try {
				String classResourcePath = path;
				if (!classResourcePath.startsWith("/")) {
					classResourcePath = "/" + classResourcePath;
				}
				if (!classResourcePath.toLowerCase().endsWith(".xml")) {
					classResourcePath = classResourcePath + ".xml";
				}
				inputStream = getClass().getResourceAsStream(classResourcePath);
				return load(inputStream);
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (Exception e) {
			throw new ORMappingException("errors.or.instantiation", e);
		}
	}

	@Override
	public ConnectionConfig load(InputStream in) throws ORMappingException {
		try {
			ConnectionConfig config = SerializeUtils.bytesToObject(in, ConnectionConfig.class);
			String URL = config.getURL();
			if (URL.indexOf("{user.home}") != -1) {
				String userHome = System.getProperty("user.home");
				URL = URL.replace("{user.home}", userHome);
			}
			if (URL.indexOf("\\") != -1) {
				URL = URL.replaceAll("\\\\", "/");
			}
			config.setURL(URL);

			if (logger.isTraceEnabled()) {
				logger.trace("[Database Config]\n" + config.toString());
			}
			return config;
		} catch (Exception e) {
			throw new ORMappingException("errors.or.instantiation", e);
		}

	}

}
