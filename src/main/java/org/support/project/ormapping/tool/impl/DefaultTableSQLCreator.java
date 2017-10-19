package org.support.project.ormapping.tool.impl;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DefaultTableSQLCreator {
    /** ログ */
    private static Log log = LogFactory.getLog(DefaultTableSQLCreator.class);

    private CreatorHelper helper = new CreatorHelper();
    // private NameConvertor nameConvertor = new NameConvertor();

    private DaoGenConfig config;

    public DefaultTableSQLCreator(DaoGenConfig config) {
        super();
        this.config = config;
    }

    public void createDefaultSql() {
        createPhysicalSelectAllSqlFile();
        createPhysicalSelectOnKeySqlFile();
        createSelectAllSqlFile();
        createSelectOnKeySqlFile();
        createInsertSqlFile();
        createUpdateSqlFile();
        createDeleteSqlFile();

        createSelectOn();
        createPhysicalSelectOn();
    }

    // logical → 論理削除を考慮
    // physical → 論理削除は考慮しない(物理削除のみ)

    public String getDeleteSqlFileName() {
        return config.getDaoClassName() + "_delete.sql";
    }

    public String getUpdateSqlFileName() {
        return config.getDaoClassName() + "_update.sql";
    }

    public String getInsertSqlFileName() {
        return config.getDaoClassName() + "_insert.sql";
    }

    public String getRawInsertSqlFileName() {
        return config.getDaoClassName() + "_raw_insert.sql";
    }

    public String getSelectOnKeySqlFileName() {
        return config.getDaoClassName() + "_select_on_key.sql";
    }

    public String getSelectAllSqlFileName() {
        return config.getDaoClassName() + "_select_all.sql";
    }

    public String getSelectAllWithPagerSqlFileName() {
        return config.getDaoClassName() + "_select_all_with_pager.sql";
    }
    
    public String getSelectCountAllSqlFileName() {
        return config.getDaoClassName() + "_select_count_all.sql";
    }

    public String getPhysicalSelectOnKeySqlFileName() {
        return config.getDaoClassName() + "_physical_select_on_key.sql";
    }

    public String getPhysicalSelectAllSqlFileName() {
        return config.getDaoClassName() + "_physical_select_all.sql";
    }

    public String getPhysicalSelectAllWithPagerSqlFileName() {
        return config.getDaoClassName() + "_physical_select_all_with_pager.sql";
    }

    public List<String> getSelectOnSqlFileNames() {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        List<String> list = new ArrayList<>();
        if (primaryKeys.size() <= 1) {
            return list;
        }
        for (ColumnDefinition primary : primaryKeys) {
            String col = primary.getColumn_name().toLowerCase();
            if (col.equals("key")) {
                col = "col_key";
            }
            list.add(config.getDaoClassName() + "_select_on_" + col + ".sql");
        }
        return list;
    }

    public List<String> getPhysicalSelectOnSqlFileNames() {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        List<String> list = new ArrayList<>();
        if (primaryKeys.size() <= 1) {
            return list;
        }
        for (ColumnDefinition primary : primaryKeys) {
            String col = primary.getColumn_name().toLowerCase();
            if (col.equals("key")) {
                col = "col_key";
            }
            list.add(config.getDaoClassName() + "_physical_select_on_" + col + ".sql");
        }
        return list;
    }

    private void createDeleteSqlFile() {
        String sqlFileName = getDeleteSqlFileName();
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
            Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);

            pw = helper.getPrintWriter(sqlFile);
            pw.println("DELETE FROM " + config.getTableDefinition().getTable_name().toUpperCase());
            pw.println("WHERE ");
            int count = 0;
            for (ColumnDefinition column : primaryKeys) {
                if (count > 0) {
                    pw.print(" AND ");
                }
                pw.print(column.getColumn_name());
                pw.println(" = ?");
                count++;
            }
            pw.println(";");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void createUpdateSqlFile() {
        String sqlFileName = getUpdateSqlFileName();
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
            Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);

            List<String> primaryKeyName = new ArrayList<>();
            for (ColumnDefinition column : primaryKeys) {
                primaryKeyName.add(column.getColumn_name());
            }

            pw = helper.getPrintWriter(sqlFile);
            pw.println("UPDATE " + config.getTableDefinition().getTable_name().toUpperCase());

            pw.println("SET ");
            int count = 0;
            for (ColumnDefinition column : columnDefinitions) {
                if (!primaryKeyName.contains(column.getColumn_name())) {
                    if (count > 0) {
                        pw.print(" , ");
                    } else {
                        pw.print("   ");
                    }
                    pw.print(column.getColumn_name());
                    pw.println(" = ?");
                    count++;
                }
            }
            pw.println("WHERE ");

            count = 0;
            for (ColumnDefinition column : primaryKeys) {
                if (count > 0) {
                    pw.print(" AND ");
                }
                pw.print(column.getColumn_name());
                pw.println(" = ?");
                count++;
            }
            pw.println(";");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void createInsertSqlFile() {
        String sqlFileName = getInsertSqlFileName();
        String sqlRawFileName = getRawInsertSqlFileName();

        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        File sqlRawFile = new File(config.getSqlDir(), sqlRawFileName);

        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        PrintWriter pw2 = null;
        try {
            List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
            pw = helper.getPrintWriter(sqlFile);
            pw2 = helper.getPrintWriter(sqlRawFile);

            pw.println("INSERT INTO " + config.getTableDefinition().getTable_name().toUpperCase());
            pw2.println("INSERT INTO " + config.getTableDefinition().getTable_name().toUpperCase());

            Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
            boolean keygen = false;
            ColumnDefinition keycol = null;
            if (primaryKeys.size() == 1) {
                keycol = new ArrayList<>(primaryKeys).get(0);
                // 主キーは１つ、かつオートインクリメント
                String auto = keycol.getIs_autoincrement();
                if (auto != null) {
                    auto = auto.toLowerCase();
                }
                if ("yes".equals(auto)) {
                    keygen = true;
                }
            }
            pw.println("( ");
            pw2.println("( ");
            int count = 0;

            List<String> primaryKeyName = new ArrayList<>();
            for (ColumnDefinition column : primaryKeys) {
                if (count > 0) {
                    pw.print(" , ");
                    pw2.print(" , ");
                }
                pw.println(column.getColumn_name());
                pw2.println(column.getColumn_name());
                count++;
                primaryKeyName.add(column.getColumn_name());
            }
            for (ColumnDefinition column : columnDefinitions) {
                if (!primaryKeyName.contains(column.getColumn_name())) {
                    if (count > 0) {
                        pw.print(" , ");
                        pw2.print(" , ");
                    }
                    pw.println(column.getColumn_name());
                    pw2.println(column.getColumn_name());
                    count++;
                }
            }
            pw.println(") VALUES (");
            pw2.println(") VALUES (");
            count = 0;
            for (@SuppressWarnings("unused") ColumnDefinition column : primaryKeys) {
                if (count > 0) {
                    pw.print(" , ");
                    pw2.print(" , ");
                }
                if (keygen) {
                    pw.println("DEFAULT");
                } else {
                    pw.println("?");
                }
                pw2.println("?");
                count++;
            }
            for (ColumnDefinition column : columnDefinitions) {
                if (!primaryKeyName.contains(column.getColumn_name())) {
                    if (count > 0) {
                        pw.print(" , ");
                        pw2.print(" , ");
                    }
                    pw.println("?");
                    pw2.println("?");
                    count++;
                }
            }
            pw.println(");");
            pw2.println(");");
            pw.flush();
            pw2.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (pw2 != null) {
                pw2.close();
            }
        }
    }

    /**
     * キーが複数ある場合に、１つの一覧を取得する
     */
    private void createSelectOn() {
        List<String> sqls = getSelectOnSqlFileNames();
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);

        if (primaryKeys.size() <= 1) {
            return;
        }

        int idx = 0;
        for (ColumnDefinition primary : primaryKeys) {
            if (!primary.getColumn_name().toLowerCase().equals("key")) {
                String fileName = sqls.get(idx++);
                createSelectOn(primary, fileName);
            }
        }
    }

    private void createSelectOn(ColumnDefinition primary, String fileName) {
        String sqlFileName = fileName;
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            pw = helper.getPrintWriter(sqlFile);
            pw.println("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase());
            pw.println(" WHERE ");

            pw.print(primary.getColumn_name());
            pw.println(" = ?");

            if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
                pw.print(" AND ");
                pw.print(config.getCommonDeleteFlag());
                pw.print(" = ");
                pw.print(INT_FLAG.OFF.getValue());
            }
            pw.println(";");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void createSelectOnKeySqlFile() {
        String sqlFileName = getSelectOnKeySqlFileName();
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
            pw = helper.getPrintWriter(sqlFile);
            pw.println("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase());
            pw.println(" WHERE ");

            Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
            int count = 0;
            for (ColumnDefinition column : primaryKeys) {
                if (count > 0) {
                    pw.print(" AND ");
                }
                pw.print(column.getColumn_name());
                pw.println(" = ?");
                count++;
            }
            if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
                pw.print(" AND ");
                pw.print(config.getCommonDeleteFlag());
                pw.print(" = ");
                pw.print(INT_FLAG.OFF.getValue());
            }
            pw.println(";");
            pw.flush();
            log.debug(pw.toString());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * キーが複数ある場合に、１つの一覧を取得する
     */
    private void createPhysicalSelectOn() {
        List<String> sqls = getPhysicalSelectOnSqlFileNames();
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);

        if (primaryKeys.size() <= 1) {
            return;
        }

        int idx = 0;
        for (ColumnDefinition primary : primaryKeys) {
            String fileName = sqls.get(idx++);
            createPhysicalSelectOn(primary, fileName);
        }
    }

    private void createPhysicalSelectOn(ColumnDefinition primary, String fileName) {
        String sqlFileName = fileName;
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            pw = helper.getPrintWriter(sqlFile);
            pw.println("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase());
            pw.println(" WHERE ");

            pw.print(primary.getColumn_name());
            pw.println(" = ?");
            pw.println(";");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void createPhysicalSelectOnKeySqlFile() {
        String sqlFileName = getPhysicalSelectOnKeySqlFileName();
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
            pw = helper.getPrintWriter(sqlFile);
            pw.println("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase());
            pw.println(" WHERE ");

            Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
            int count = 0;
            for (ColumnDefinition column : primaryKeys) {
                if (count > 0) {
                    pw.print(" AND ");
                }
                pw.print(column.getColumn_name());
                pw.println(" = ?");
                count++;
            }
            pw.println(";");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * SelectAllのSQLを生成
     */
    private void createSelectAllSqlFile() {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        ColumnDefinition datetimeColumn = null;
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonInsertDateTime().toLowerCase())) {
                datetimeColumn = columnDefinition;
                break;
            }
        }
        
        String sqlFileName = getSelectAllSqlFileName();
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            pw = helper.getPrintWriter(sqlFile);
            pw.print("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase() + "");
            if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
                pw.print("\n");
                pw.print("WHERE ");
                pw.print(config.getCommonDeleteFlag());
                pw.print(" = ");
                pw.print(INT_FLAG.OFF.getValue());
            }
            if (datetimeColumn != null) {
                pw.print("\n");
                pw.print("ORDER BY " + datetimeColumn.getColumn_name() + " %s");
            }
            pw.println(";");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

        sqlFileName = getSelectAllWithPagerSqlFileName();
        sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        try {
            pw = helper.getPrintWriter(sqlFile);
            pw.print("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase() + "");
            if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
                pw.print("\n");
                pw.print("WHERE ");
                pw.print(config.getCommonDeleteFlag());
                pw.print(" = ");
                pw.print(INT_FLAG.OFF.getValue());
            }
            if (datetimeColumn != null) {
                pw.print("\n");
                pw.print("ORDER BY " + datetimeColumn.getColumn_name() + " %s");
            }
            pw.print("\n");
            pw.println("LIMIT ? OFFSET ?;");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        
        sqlFileName = getSelectCountAllSqlFileName();
        sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        try {
            pw = helper.getPrintWriter(sqlFile);
            pw.println("SELECT COUNT(*) FROM " + config.getTableDefinition().getTable_name().toUpperCase() + "");
            if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
                pw.print("WHERE ");
                pw.print(config.getCommonDeleteFlag());
                pw.print(" = ");
                pw.print(INT_FLAG.OFF.getValue());
                pw.print(";\n");
            }
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        
        
        
    }

    /**
     * SelectAllのSQLを生成
     */
    private void createPhysicalSelectAllSqlFile() {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        ColumnDefinition datetimeColumn = null;
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonInsertDateTime().toLowerCase())) {
                datetimeColumn = columnDefinition;
                break;
            }
        }
        
        
        String sqlFileName = getPhysicalSelectAllSqlFileName();
        File sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        PrintWriter pw = null;
        try {
            pw = helper.getPrintWriter(sqlFile);
            pw.print("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase() + "");
            if (datetimeColumn != null) {
                pw.print("\n");
                pw.print("ORDER BY " + datetimeColumn.getColumn_name() + " %s");
            }
            pw.println(";");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

        sqlFileName = getPhysicalSelectAllWithPagerSqlFileName();
        sqlFile = new File(config.getSqlDir(), sqlFileName);
        log.info(sqlFile.getAbsolutePath() + "を作成します");
        try {
            pw = helper.getPrintWriter(sqlFile);
            pw.print("SELECT * FROM " + config.getTableDefinition().getTable_name().toUpperCase());
            if (datetimeColumn != null) {
                pw.print("\n");
                pw.print("ORDER BY " + datetimeColumn.getColumn_name() + " %s");
            }
            pw.print("\n");
            pw.println("LIMIT ? OFFSET ?;");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

    }

}
