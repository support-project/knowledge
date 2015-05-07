package org.support.project.knowledge.bat;

import org.support.project.web.logic.DBConnenctionLogic;

public abstract class AbstractBat {
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
