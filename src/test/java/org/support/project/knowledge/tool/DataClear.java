package org.support.project.knowledge.tool;

import java.io.File;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.util.FileUtil;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.gen.DatabaseControlDao;
import org.support.project.knowledge.deploy.InitDB;

public class DataClear {

    public static void main(String[] args) throws Exception {
        // DBを完全初期化
        DatabaseControlDao dao1 = new DatabaseControlDao();
        dao1.dropAllData();
        dao1.dropAllTable();

        org.support.project.web.dao.gen.DatabaseControlDao dao2 = new org.support.project.web.dao.gen.DatabaseControlDao();
        dao2.dropAllData();
        dao2.dropAllTable();

        InitDB.main(new String[0]);
        // 全文検索エンジンのインデックスの消去
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        File indexDir = new File(appConfig.getIndexPath());
        FileUtil.delete(indexDir);
    }

}
