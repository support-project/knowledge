package org.support.project.ormapping.config;

public class ORMappingParameter {
	/** アプリケーションリソース設定(多言語対応) */
	public static final String OR_MAPPING_RESOURCE = "/org/support/project/ormapping/config/or_mapping_resource";

	/** EntityやDaoの自動生成用の設定ファイル */
	public static final String OR_MAPPING_AUTO_GEN_RESOURCE = "auto_gen_for_or_mapping";
	
	/** デフォルトのコネクション設定ファイルの名称 */
	public static final String CONNECTION_SETTING = "connection";
	
	/** デフォルトのコネクション設定名 */
	public static final String DEFAULT_CONNECTION_NAME = "connection";
	
	/** ORマッピングのツール(初期化／DaoやEntityの生成など)の設定ファイル名 */
	public static final String OR_MAPPING_TOOL_CONFIG = "/ormappingtool.xml";
	
	public static final String DRIVER_NAME_H2 = "org.h2.Driver";
	public static final String DRIVER_NAME_POSTGRESQL = "org.postgresql.Driver";
	
}
