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
 * Create java code file of Entity class.
 * 
 * @author Koda
 */
public class EntityMaker {

    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * 設定に従い、 Entityクラスを作成する
     * 
     * @param args
     *            パラメータ(いったん今は何も使わない。後で、設定ファイル名を渡すとか考える)
     * @throws Exception Exception
     * 
     */
    public static void main(String[] args) throws Exception {
        String configFileName = ORMappingParameter.OR_MAPPING_TOOL_CONFIG;
        if (args.length == 1) {
            configFileName = args[0];
        }

        EntityMaker entityMaker = new EntityMaker(configFileName);
        entityMaker.make();
    }

    /** 設定ファイル情報 */
    private ORmappingToolConfig config;
    
    /**
     * Constructor
     * @param configFileName configFileName
     * @throws SerializeException SerializeException
     * @throws IOException IOException
     */
    public EntityMaker(String configFileName) throws SerializeException, IOException {
        config = SerializeUtils.bytesToObject(getClass().getResourceAsStream(configFileName), ORmappingToolConfig.class);
        config.getConnectionConfig().convURL();
        ConnectionManager.getInstance().addConnectionConfig(config.getConnectionConfig());
    }

    /**
     * エンティティクラスを生成する エンティティは、後で手で修正したい事もあるので、以下の構成にする
     * 
     * xxx.entity.gen GenXxxxEntity テーブル定義から自動生成したカラム設定が書かれる(このクラスは手で修正はしない) アブストラクトにする
     * 
     * xxx.entityパッケージ XxxxEntity 上記GenEntityを継承したクラス。いったん箱だけ用意する
     * 
     */
    private void make() {
        LOG.info("Entityを作成します。");
        // Resources resources = Resources.getInstance(ORMappingParameter.OR_MAPPING_AUTO_GEN_RESOURCE);
        // String dir = resources.getResource("entity.out.dir");
        // String packageName = resources.getResource("entity.package.name");
        // String suffix = resources.getResource("entity.suffix.name");

        String dir = config.getEntityGenConfig().getEntityOutDir();
        String packageName = config.getEntityGenConfig().getEntityPackage();
        String suffix = config.getEntityGenConfig().getEntitySuffix();

        // データベースのテーブル定義情報を解析
        DatabaseMetaDataDao metaDataDao = Container.getComp(DatabaseMetaDataDao.class);
        metaDataDao.dbAnalysis();

        if (!dir.endsWith("/")) {
            dir = dir + "/";
        }
        dir = dir + "java/" + packageName.replaceAll("\\.", "/");
        LOG.info(dir);

        // データベース毎のEntity生成クラスに処理を委譲
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        EntityClassCreator creator = ORMappingToolFactory.getEntityClassCreator(connectionManager.getDriverClass());
        creator.create(metaDataDao.getTableInfos(), dir, packageName, suffix);

        LOG.info("Entityの作成が完了しました。");
    }

}
