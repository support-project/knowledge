package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

/**
 * Daoの中のInsertMethodを生成する
 * 
 * @author Koda
 */
public class DefaultTableInsertMethodCreator {
    private CreatorHelper helper = new CreatorHelper();
    private NameConvertor nameConvertor = new NameConvertor();

    private DaoGenConfig config;
    private DefaultTableSQLCreator sqlCreator;

    public void writeInsertMethod(DaoGenConfig config, PrintWriter pw) {
        this.config = config;
        this.sqlCreator = new DefaultTableSQLCreator(config);

        writeCreateRowId(pw);
        writeRawPhysicalInsert(pw);
        writePhysicalInsert(pw);
        writeInsertOnUser(pw);
        writeInsert(pw);
    }

    private void writeInsert(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Insert.");
        pw.println("     * saved user id is auto set.");
        pw.println("     * @param entity entity");
        pw.println("     * @return saved entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" insert(");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");
        if (StringUtils.isEmpty(config.getCommonInsertUserName())) {
            pw.println("        return physicalInsert(entity);");
        } else {
            pw.println("        DBUserPool pool = Container.getComp(DBUserPool.class);");
            pw.print("        ");
            pw.print(config.getCommonUseridType());
            pw.print(" userId = (");
            pw.print(config.getCommonUseridType());
            pw.println(") pool.getUser();");
            pw.println("        return insert(userId, entity);");
        }
        pw.println("    }");
    }

    private void writeInsertOnUser(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Insert.");
        pw.println("     * set saved user id.");
        pw.println("     * @param user saved userid");
        pw.println("     * @param entity entity");
        pw.println("     * @return saved entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" insert(");
        pw.print(config.getCommonUseridType());
        pw.print(" user, ");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        if (StringUtils.isNotEmpty(config.getCommonInsertUserName())) {
            // ユーザ名の列定義
            List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
            ColumnDefinition userColumn = null;
            ColumnDefinition datetimeColumn = null;

            ColumnDefinition updateUserColumn = null;
            ColumnDefinition updateDatetimeColumn = null;

            for (ColumnDefinition columnDefinition : columnDefinitions) {
                if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonInsertUserName().toLowerCase())) {
                    userColumn = columnDefinition;
                } else if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonInsertDateTime().toLowerCase())) {
                    datetimeColumn = columnDefinition;
                }

                if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonUpdateUserName().toLowerCase())) {
                    updateUserColumn = columnDefinition;
                } else if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonUpdateDateTime().toLowerCase())) {
                    updateDatetimeColumn = columnDefinition;
                }

            }
            if (userColumn != null) {
                // 登録ユーザをセット
                String feildName = nameConvertor.colmnNameToFeildName(userColumn.getColumn_name());
                pw.print("        entity.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(user);");
            }
            if (datetimeColumn != null) {
                // 登録ユーザをセット
                String feildName = nameConvertor.colmnNameToFeildName(datetimeColumn.getColumn_name());
                pw.print("        entity.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(new Timestamp(DateUtils.now().getTime()));");
            }

            if (updateUserColumn != null) {
                // 登録ユーザをセット
                String feildName = nameConvertor.colmnNameToFeildName(updateUserColumn.getColumn_name());
                pw.print("        entity.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(user);");
            }
            if (updateDatetimeColumn != null) {
                // 登録ユーザをセット
                String feildName = nameConvertor.colmnNameToFeildName(updateDatetimeColumn.getColumn_name());
                pw.print("        entity.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(new Timestamp(DateUtils.now().getTime()));");
            }

        }

        if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
            // 削除フラグをセット
            String feildName = nameConvertor.colmnNameToFeildName(config.getCommonDeleteFlag());
            pw.print("        entity.");
            pw.print(helper.feildNameToSetter(feildName));
            pw.print("(");
            if (StringUtils.isNotEmpty(config.getDeleteFlagColumnType()) && "boolean".equals(config.getDeleteFlagColumnType().toLowerCase())) {
                pw.print("Boolean.FALSE");
            } else {
                pw.print(INT_FLAG.OFF.getValue());
            }
            pw.println(");");
        }

        if (StringUtils.isNotEmpty(config.getRowIdColumn())) {
            pw.print("        entity.");
            String f = nameConvertor.colmnNameToFeildName(config.getRowIdColumn());
            pw.print(helper.feildNameToSetter(f));
            pw.println("(createRowId());");
        }

        pw.println("        return physicalInsert(entity);");
        pw.println("    }");
    }

    private void writePhysicalInsert(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Physical Insert.");
        pw.println("     * if key column have sequence, key value create by database.");
        pw.println("     * @param entity entity");
        pw.println("     * @return saved entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" physicalInsert(");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getInsertSqlFileName());
        pw.println("\");");

        pw.print("        ");

        // プライマリキーが自動生成であるかチェック
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
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

        if (keygen) {
            pw.println(
                    "Class<?> type = PropertyUtil.getPropertyType(entity, \"" + nameConvertor.colmnNameToFeildName(keycol.getColumn_name()) + "\");");
            pw.println("        Object key = executeInsert(sql, type, ");
            int count = 0;

            List<String> primaryKeyName = new ArrayList<>();
            for (ColumnDefinition column : primaryKeys) {
                primaryKeyName.add(column.getColumn_name());
            }

            for (ColumnDefinition column : columnDefinitions) {
                if (!primaryKeyName.contains(column.getColumn_name())) {
                    if (count > 0) {
                        pw.println(", ");
                    }
                    pw.print("            ");
                    pw.print("entity.");
                    String feildName = nameConvertor.colmnNameToFeildName(column.getColumn_name());
                    pw.print(helper.feildNameToGetter(feildName));
                    pw.print("()");
                    count++;
                }
            }
            pw.println(");");
            // 採番したキーをセット
            pw.println("        PropertyUtil.setPropertyValue(entity, \""
                    + nameConvertor.colmnNameToFeildName(keycol.getColumn_name()) + "\", key);");
        } else {
            pw.println("executeUpdate(sql, ");
            int count = 0;

            List<String> primaryKeyName = new ArrayList<>();
            for (ColumnDefinition column : primaryKeys) {
                if (count > 0) {
                    pw.println(", ");
                }
                pw.print("            ");
                pw.print("entity.");
                String feildName = nameConvertor.colmnNameToFeildName(column.getColumn_name());
                pw.print(helper.feildNameToGetter(feildName));
                pw.print("()");
                count++;

                primaryKeyName.add(column.getColumn_name());
            }

            for (ColumnDefinition column : columnDefinitions) {
                if (!primaryKeyName.contains(column.getColumn_name())) {
                    if (count > 0) {
                        pw.println(", ");
                    }
                    pw.print("            ");
                    pw.print("entity.");
                    String feildName = nameConvertor.colmnNameToFeildName(column.getColumn_name());
                    pw.print(helper.feildNameToGetter(feildName));
                    pw.print("()");
                    count++;
                }
            }
            pw.println(");");
        }
        pw.print("        ");
        pw.println("return entity;");

        pw.println("    }");
    }
    
    
    private void writeRawPhysicalInsert(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Physical Insert.");
        pw.println("     * it is not create key on database sequence.");
        pw.println("     * @param entity entity");
        pw.println("     * @return saved entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" rawPhysicalInsert(");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getRawInsertSqlFileName());
        pw.println("\");");

        pw.print("        ");

        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);

        pw.println("executeUpdate(sql, ");
        int count = 0;

        List<String> primaryKeyName = new ArrayList<>();
        for (ColumnDefinition column : primaryKeys) {
            if (count > 0) {
                pw.println(", ");
            }
            pw.print("            ");
            pw.print("entity.");
            String feildName = nameConvertor.colmnNameToFeildName(column.getColumn_name());
            pw.print(helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
            primaryKeyName.add(column.getColumn_name());
        }

        for (ColumnDefinition column : columnDefinitions) {
            if (!primaryKeyName.contains(column.getColumn_name())) {
                if (count > 0) {
                    pw.println(", ");
                }
                pw.print("            ");
                pw.print("entity.");
                String feildName = nameConvertor.colmnNameToFeildName(column.getColumn_name());
                pw.print(helper.feildNameToGetter(feildName));
                pw.print("()");
                count++;
            }
        }
        pw.println(");");

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

        if (keygen) {
            // Postgresの場合、シーケンスを再設定する必要あり
            String tableName = config.getTableDefinition().getTable_name();
            String primary = keycol.getColumn_name();
            String seq = tableName + "_" + primary + "_" + "seq";
            // まず、現在の最大値を取得
            String max = "\"SELECT MAX(" + primary + ") from " + tableName + ";\"";
            String sql = "\"SELECT SETVAL('" + seq + "', ?);\"";
            pw.println("        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());");
            pw.println("        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {");
            pw.println("            String maxSql = " + max + ";");
            pw.println("            long max = executeQuerySingle(maxSql, Long.class);");
            pw.println("            if (max < 1) {");
            pw.println("                max = 1;");
            pw.println("            }");
            pw.println("            String setValSql = " + sql + ";");
            pw.println("            executeQuerySingle(setValSql, Long.class, max);");
            pw.println("        }");
        }

        pw.print("        ");
        pw.println("return entity;");

        pw.println("    }");

    }

    private void writeCreateRowId(PrintWriter pw) {
        if (StringUtils.isEmpty(config.getRowIdColumn())) {
            return;
        }

        pw.println("    /**");
        pw.println("     * Create row id.");
        pw.println("     * @return row id");
        pw.println("     */");

        // メソッド定義
        pw.print("    protected String");
        pw.print(" createRowId(");
        pw.println(") {");
        pw.print("        return IDGen.get().gen(\"");
        pw.print(config.getTableDefinition().getTable_name());
        pw.println("\");");
        pw.println("    }");
    }

}
