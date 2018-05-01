package org.support.project.ormapping.tool;

import java.io.IOException;
import java.util.List;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.tool.config.ORMappingDatabaseInitializeConfig;
import org.support.project.ormapping.tool.config.ORmappingToolConfig;
import org.support.project.ormapping.tool.dao.InitializeDao;

public class DatabaseInitializer {

    public static void main(String[] args) throws Exception, IOException {
        String configFileName = ORMappingParameter.OR_MAPPING_TOOL_CONFIG;
        if (args != null && args.length == 1) {
            configFileName = args[0];
        }
        
        DatabaseInitializer initializer = new DatabaseInitializer(configFileName);
        initializer.dropAllTable();
        initializer.initDatabase();
    }
    /**　設定ファイル名 */
    private String configFileName;
    /** 設定ファイル情報 */
    private ORmappingToolConfig config;

    public DatabaseInitializer(String configFileName) throws SerializeException, IOException {
        super();
        this.configFileName = configFileName;
        config = SerializeUtils.bytesToObject(
                getClass().getResourceAsStream(configFileName), 
                ORmappingToolConfig.class);
        config.getConnectionConfig().convURL();
        ConnectionManager.getInstance().addConnectionConfig(config.getConnectionConfig());
        
        InitializeDao initializeDao = InitializeDao.get();
        initializeDao.initializeDatabase();
//        initializeDao.setConnectionConfig(config.getConnectionConfig());
    }

    /**
     * 初期SQLの実行
     */
    public void initDatabase() {
        InitializeDao initializeDao = InitializeDao.get();
        initializeDao.initializeDatabase();
        ORMappingDatabaseInitializeConfig initializeConfig = config.getInitializeConfig();
        if (initializeConfig != null) {
            List<String> sqlpaths = config.getInitializeConfig().getSqlPaths();
            if (sqlpaths != null && !sqlpaths.isEmpty()) {
                String[] sqls = sqlpaths.toArray(new String[0]);
                initializeDao.initializeDatabase(sqls);
            }
        }
    }
    
    /**
     * DBのテーブルのデータを全て削除
     */
    @Deprecated
    public void deleteAllTable() {
        InitializeDao initializeDao = InitializeDao.get();
        initializeDao.deleteAllData();
    }

    /**
     * DBのテーブルを全て削除
     */
    @Deprecated
    public void dropAllTable() {
        InitializeDao initializeDao = InitializeDao.get();
        initializeDao.dropAllTable();
    }


}
