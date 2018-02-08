package org.support.project.web.deploy;

public interface Migrate {
	
	/**
	 * マイグレーション実行
	 * @return result
	 * @throws Exception Exception
	 */
	boolean doMigrate() throws Exception;
	
	/**
	 * バージョンを取得
	 * @return version
	 */
	Double getVersion();
}
