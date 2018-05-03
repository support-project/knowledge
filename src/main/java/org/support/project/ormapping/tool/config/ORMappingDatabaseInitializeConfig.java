package org.support.project.ormapping.tool.config;

import java.io.Serializable;
import java.util.List;

/**
 * OR Mapping ToolのDatabae初期化の設定
 * @author Koda
 */
public class ORMappingDatabaseInitializeConfig implements Serializable {
	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;
	
	/** 初期化で実施するSQLのパス */
	private List<String> sqlPaths;

	/**
	 * @return the sqlPaths
	 */
	public List<String> getSqlPaths() {
		return sqlPaths;
	}

	/**
	 * @param sqlPaths the sqlPaths to set
	 */
	public void setSqlPaths(List<String> sqlPaths) {
		this.sqlPaths = sqlPaths;
	}
	
	
	
}
