package org.support.project.common.log.impl.def;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.log.LogInitializer;

public class JavaDefaultLogInitializerImpl implements LogInitializer {
	private static boolean init = false;

	@Override
	public Log createLog(Class<?> type) {
		if (!init) {
			try {
				// 標準のログの設定ファイルを読み込む
				InputStream inputStream = LogFactory.class.getResourceAsStream("/logging.properties");
				if (inputStream != null) {
					LogManager.getLogManager().readConfiguration();
				}
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			init = true;
		}
		Logger logger = Logger.getLogger(type.getCanonicalName());
		Log log = new JavaDefaultLogImpl(logger);
		return log;
	}

}
