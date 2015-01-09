package org.support.project.knowledge.tool;

import java.io.File;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.util.FileUtil;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.deploy.InitDB;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class DataClear {

	public static void main(String[] args) throws Exception {
		//DBを完全初期化
		InitializeDao initializeDao = InitializeDao.get();
		initializeDao.dropAllTable();
		InitDB.main(null);
		// 全文検索エンジンのインデックスの消去
		AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		File indexDir = new File(appConfig.getIndexPath());
		FileUtil.delete(indexDir);
	}

}
