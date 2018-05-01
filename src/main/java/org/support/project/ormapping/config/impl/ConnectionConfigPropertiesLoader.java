package org.support.project.ormapping.config.impl;

import java.io.InputStream;

import org.apache.commons.lang.NotImplementedException;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.config.ConnectionConfigLoader;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.exception.ORMappingException;

@Deprecated
public class ConnectionConfigPropertiesLoader implements ConnectionConfigLoader {
	/** ログ */
	private static Log logger = LogFactory.getLog(ConnectionConfigPropertiesLoader.class);
	
	public ConnectionConfig load(String path) {
//		Resources conectionSetting = Resources.getInstance(ORMappingParameter.CONNECTION_SETTING);
		Resources conectionSetting = Resources.getInstance(path);
		String name = conectionSetting.getResource("name");
		if (StringUtils.isEmpty(name)) {
			name = ORMappingParameter.DEFAULT_CONNECTION_NAME;
		}
		
		String driverClass = conectionSetting.getResource("driverClassName");
		String URL = conectionSetting.getResource("url");
		if (URL.indexOf("{user.home}") != -1) {
			String userHome = System.getProperty("user.home");
			URL = URL.replace("{user.home}", userHome);
		}
		if (URL.indexOf("\\") != -1) {
			URL = URL.replaceAll("\\\\", "/");
		}
		logger.info("Database connection url : " + URL);
		
		String user = conectionSetting.getResource("username");
		String password = conectionSetting.getResource("password");
		String schema = conectionSetting.getResource("schema");
		String max = conectionSetting.getResource("maxConn");
		int maxConn = 5;
		if (StringUtils.isNotEmpty(max)) {
			try {
				maxConn = Integer.parseInt(max);
			} catch (NumberFormatException e) {
			}
		}
		String auto = conectionSetting.getResource("autocommit");
		boolean autocommit = false;
		if (StringUtils.isNotEmpty(auto)) {
			if ("true".equals(auto.toLowerCase())) {
				autocommit = true;
			}
		}

		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new ORMappingException("errors.or.instantiation", e);
		}
		
		ConnectionConfig config = new ConnectionConfig();
		config.setName(name);
		config.setDriverClass(driverClass);
		config.setURL(URL);
		config.setUser(user);
		config.setPassword(password);
		config.setSchema(schema);
		config.setMaxConn(maxConn);
		config.setAutocommit(autocommit);
		return config;
	}

	@Override
	public ConnectionConfig load(InputStream in) throws ORMappingException {
		throw new NotImplementedException();
	}
	
	
}
