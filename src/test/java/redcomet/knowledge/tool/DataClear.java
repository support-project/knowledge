package redcomet.knowledge.tool;

import java.io.File;

import redcomet.common.config.ConfigLoader;
import redcomet.common.util.FileUtil;
import redcomet.knowledge.config.AppConfig;
import redcomet.knowledge.deploy.InitDB;
import redcomet.ormapping.tool.dao.InitializeDao;

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
