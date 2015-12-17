package org.support.project.knowledge.config;

public class SystemConfig {
	public static final String KNOWLEDGE_ENV_KEY = "KNOWLEDGE_HOME";
	
	/** ユーザ登録のやりかたを判定するシステム設定のラベル */
	public static final String USER_ADD_TYPE = "USER_ADD_TYPE";
	/** ユーザ登録のやりかた: 管理者が登録（デフォルト） */
	public static final String USER_ADD_TYPE_VALUE_ADMIN = "ADMIN";
	/** ユーザ登録のやりかた: ユーザ自身で登録し、管理者が承認(メール通知あり) */
	public static final String USER_ADD_TYPE_VALUE_APPROVE = "APPROVE";
	/** ユーザ登録のやりかた: ユーザ自身で登録（メールアドレスのチェック無し） */
	public static final String USER_ADD_TYPE_VALUE_USER = "USER";
	/** ユーザ登録のやりかた: ユーザ自身で登録（メールアドレスのチェックあり） */
	public static final String USER_ADD_TYPE_VALUE_MAIL = "MAIL";
	
	/** システムへアクセスするためのURLのシステム設定のラベル */
	public static final String SYSTEM_URL = "SYSTEM_URL";
	
	/** ユーザ登録を実施した後、通知するか(ON/OFF) */
	public static final String USER_ADD_NOTIFY = "USER_ADD_NOTIFY";
	/** ユーザ登録を実施した後、通知する(ON) */
	public static final String USER_ADD_NOTIFY_ON = "ON";
	/** ユーザ登録を実施した後、通知しない(OFF) */
	public static final String USER_ADD_NOTIFY_OFF = "OFF";
	
	/** インデックス再作成のための設定値 */
	public static final String RE_INDEXING = "RE_INDEXING";
	/** エクスポートのための設定値 */
	public static final String DATA_EXPORT = "DATA_EXPORT";
	
	/** 「公開」の情報であれば、ログインしなくても参照出来る */
	public static final String SYSTEM_EXPOSE_TYPE = "SYSTEM_EXPOSE_TYPE";
	/** 「公開」の情報であれば、ログインしなくても参照出来る */
	public static final String SYSTEM_EXPOSE_TYPE_OPEN = "OPEN";
	/** 全ての機能は、ログインしないとアクセス出来ない */
	public static final String SYSTEM_EXPOSE_TYPE_CLOSE = "CLOSE";
	
	/** UIのテーマの設定のキー */
	public static final String CONFIG_KEY_THEMA = "THEMA";
}
