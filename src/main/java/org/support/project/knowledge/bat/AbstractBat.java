package org.support.project.knowledge.bat;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.support.project.web.logic.DBConnenctionLogic;

public abstract class AbstractBat {
	public static void initLogName(String logname) {
		Logger log = Logger.getRootLogger();
		FileAppender appendar= (FileAppender) log.getAppender("APP_FILEOUT");
		appendar.setFile(logname);
		appendar.activateOptions();//変更の反映
	}
	
	/**
	 * コネクションの接続先がカスタマイズされていたら、バッチでもカスタマイズ先を参照する
	 */
	public void dbInit() {
		DBConnenctionLogic.get().connectCustomConnection();
	}
	
	protected void send(String msg) {
		System.out.println("[SEND]" + msg);
	}

}
