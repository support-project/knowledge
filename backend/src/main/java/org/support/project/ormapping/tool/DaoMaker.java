package org.support.project.ormapping.tool;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.di.Container;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.dao.DatabaseMetaDataDao;
import org.support.project.ormapping.tool.config.ORmappingToolConfig;

/**
 * Create java code file of Dao class.
 * 
 * @author Koda
 */
public class DaoMaker {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    /**
     * main process.
     * 
     * @param args
     *            args
     * @throws Exception
     *             Exception
     */
    public static void main(String[] args) throws Exception {
        String configFileName = ORMappingParameter.OR_MAPPING_TOOL_CONFIG;
        if (args.length == 1) {
            configFileName = args[0];
        }
        DaoMaker daoMaker = new DaoMaker(configFileName);
        daoMaker.make();
    }

    /** 設定ファイル情報 */
    private ORmappingToolConfig config;

    /**
     * Constructor
     * 
     * @param configFileName
     *            configFileName
     * @throws SerializeException
     *             SerializeException
     * @throws IOException
     *             IOException
     */
    public DaoMaker(String configFileName) throws SerializeException, IOException {
        config = SerializeUtils.bytesToObject(getClass().getResourceAsStream(configFileName), ORmappingToolConfig.class);
        config.getConnectionConfig().convURL();
        ConnectionManager.getInstance().addConnectionConfig(config.getConnectionConfig());
    }

    /**
     * Daoクラスを生成する。 Daoクラス生成前に、Entityクラスは生成済みを前提としている。
     * 
     */
    private void make() {
        log.info("Daoを作成します。");
        DaoGenConfig config = new DaoGenConfig();
        // config.init();
        config.init(this.config);

        // データベースのテーブル定義情報を解析
        DatabaseMetaDataDao metaDataDao = Container.getComp(DatabaseMetaDataDao.class);
        metaDataDao.dbAnalysis();

        // データベース毎のDao生成クラスに処理を委譲
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DaoClassCreator creator = ORMappingToolFactory.getDaoClassCreator(connectionManager.getDriverClass());
        creator.create(metaDataDao.getTableInfos(), config);

        log.info("Daoの作成が完了しました。");
    }

}
