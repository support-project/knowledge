package org.support.project.ormapping.tool.dao;

import java.lang.invoke.MethodHandles;
import java.util.Collection;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.dao.DatabaseMetaDataDao;
import org.support.project.ormapping.entity.TableDefinition;

/**
 * 初期化のDao
 * 
 * @author koda
 *
 */
@DI(instance = Instance.Singleton)
public class InitializeDao extends DatabaseMetaDataDao {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static InitializeDao get() {
        return Container.getComp(InitializeDao.class);
    }

    /**
     * DBの初期化
     * @param sqlPaths paths of initial sql files.
     */
    public void initializeDatabase(String... sqlPaths) {
        log.info("Database initialize start.");
        for (String sqlPath : sqlPaths) {
            String[] sqls = SQLManager.getInstance().getSqls(sqlPath);
            for (String sql : sqls) {
                executeUpdate(sql);
            }
            log.info(sqlPath + " is loaded.");
        }
        log.info("Database initialize finish.");
    }

    /**
     * 全てのデータをクリア
     */
    @Deprecated
    public void deleteAllData() {
        // DBの情報を解析
        super.dbAnalysis();

        Collection<TableDefinition> tableDefinitions = super.getTableInfos();
        String[] sqls = new String[tableDefinitions.size()];
        int count = 0;
        for (TableDefinition tableDefinition : tableDefinitions) {
            String sql = "DELETE FROM " + tableDefinition.getTable_name();
            sqls[count] = sql;
            count++;
        }
        for (String sql : sqls) {
            log.info(sql);
            executeUpdate(sql);
        }
    }

    /**
     * 全てのテーブルを削除
     */
    @Deprecated
    public void dropAllTable() {
        // DBの情報を解析
        super.dbAnalysis();

        Collection<TableDefinition> tableDefinitions = super.getTableInfos();
        String[] sqls = new String[tableDefinitions.size()];
        int count = 0;
        for (TableDefinition tableDefinition : tableDefinitions) {
            String sql = "DROP TABLE if exists " + tableDefinition.getTable_name() + " cascade";
            sqls[count] = sql;
            count++;
        }
        for (String sql : sqls) {
            log.info(sql);
            executeUpdate(sql);
        }
    }

}
