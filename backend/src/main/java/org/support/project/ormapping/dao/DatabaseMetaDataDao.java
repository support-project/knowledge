package org.support.project.ormapping.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.entity.TableDefinition;
import org.support.project.ormapping.exception.ORMappingException;

/**
 * Data accsess class for database meta data.
 * 
 * @author Koda
 */
public class DatabaseMetaDataDao extends AbstractDao {
    /** ログ. */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /** シリアルバージョン. */
    private static final long serialVersionUID = 1L;
    /** テーブル情報を格納するマップ(キーはテーブル名). */
    private Map<String, TableDefinition> tableMap;

    /**
     * テーブル情報を取得.
     * 
     * @return テーブル情報
     */
    public Collection<TableDefinition> getTableInfos() {
        return tableMap.values();
    }
    /**
     * Get instance.
     * @return instance
     */
    public static DatabaseMetaDataDao get() {
        return Container.getComp(DatabaseMetaDataDao.class);
    }

    /**
     * コンストラクタ.
     */
    public DatabaseMetaDataDao() {
        tableMap = new HashMap<String, TableDefinition>();
    }

    /**
     * 初期化処理 DBの定義情報の読み込み.
     */
    public void dbAnalysis() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = super.getConnection();
            // テーブル情報の取得
            List<TableDefinition> tables = this.readTableDefinition(con);
            for (TableDefinition tableDefinition : tables) {
                // 列情報を取得
                List<ColumnDefinition> columns = this.readColumnDefinition(con, tableDefinition);
                tableDefinition.setColumns(columns);
                tableMap.put(tableDefinition.getTable_name(), tableDefinition);
            }
        } catch (NoSuchMethodException | SQLException e) {
            throw new ORMappingException(e);
        } finally {
            close(stmt, rs, con);
        }
    }

    /**
     * テーブル情報の取得.
     * @param con Connection
     * @throws SQLException SQLException
     * @throws SystemException SystemException
     * @throws NoSuchMethodException NoSuchMethodException
     *
     */
    private List<TableDefinition> readTableDefinition(Connection con) throws SQLException, SystemException, NoSuchMethodException {
        List<TableDefinition> definitions = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            DatabaseMetaData databaseMetaData = con.getMetaData();

            String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
            if (ORMappingParameter.DRIVER_NAME_H2.equals(driverClass)) {
                // H2SQL
                rs = databaseMetaData.getTables(null, ConnectionManager.getInstance().getSchema().toUpperCase(), "%", null);
            } else {
                // Postgresql
                rs = databaseMetaData.getTables(null, ConnectionManager.getInstance().getSchema(), "%", null);
            }

            while (rs.next()) {
                TableDefinition definition = load(rs, TableDefinition.class);
                if (!"INDEX".equals(definition.getTable_type()) && !"SEQUENCE".equals(definition.getTable_type())) {
                    LOG.info("Read Table info: " + definition.getTable_name());
                    definitions.add(definition);
                }
            }
        } finally {
            close(stmt, rs);
        }
        return definitions;
    }

    /**
     * 列情報の取得
     * 
     * @param con
     * @param tableDefinition
     *
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws SystemException
     */
    private List<ColumnDefinition> readColumnDefinition(Connection con, TableDefinition tableDefinition)
            throws SQLException, SystemException, NoSuchMethodException {
        LOG.debug("Read Column info on Table: " + tableDefinition.getTable_name());

        List<ColumnDefinition> columns = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            DatabaseMetaData databaseMetaData = con.getMetaData();
            // TABLE_CAT String 指定したテーブルが存在するデータベースの名前です。
            // TABLE_SCHEM String テーブルのスキーマです。
            // TABLE_NAME String テーブルの名前です。
            // COLUMN_NAME String 列の名前です。
            // KEY_SEQ short 複数列の主キーにおける列のシーケンス番号です。
            // PK_NAME String 主キーの名前です。
            List<String> keys = new ArrayList<>();

            String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
            if (ORMappingParameter.DRIVER_NAME_H2.equals(driverClass)) {
                // H2SQL
                rs = databaseMetaData.getPrimaryKeys(null, ConnectionManager.getInstance().getSchema().toUpperCase(),
                        tableDefinition.getTable_name());
            } else {
                // Postgresql
                rs = databaseMetaData.getPrimaryKeys(null, ConnectionManager.getInstance().getSchema(), tableDefinition.getTable_name());
            }
            while (rs.next()) {
                String pkColumn = rs.getString("COLUMN_NAME");
                keys.add(pkColumn);
            }
            rs.close();

            if (ORMappingParameter.DRIVER_NAME_H2.equals(driverClass)) {
                // H2SQL
                rs = databaseMetaData.getColumns(null, ConnectionManager.getInstance().getSchema().toUpperCase(), tableDefinition.getTable_name(),
                        "%");
            } else {
                // Postgresql
                rs = databaseMetaData.getColumns("", ConnectionManager.getInstance().getSchema(), tableDefinition.getTable_name(), "%");
            }

            while (rs.next()) {
                ColumnDefinition definition = load(rs, ColumnDefinition.class);
                columns.add(definition);

                LOG.debug("Read Column info : " + tableDefinition.getTable_name() + "#" + definition.getColumn_name());

                boolean key = false;
                int idx = -1;
                for (int i = 0; i < keys.size(); i++) {
                    String keyName = keys.get(i);
                    if (keyName.equals(definition.getColumn_name())) {
                        key = true;
                        idx = i;
                        break;
                    }
                }
                definition.setPrimary(key);
                definition.setPrimary_no(idx);
            }
        } finally {
            close(stmt, rs);
        }
        return columns;
    }

}
