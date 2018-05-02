package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DefaultTableDeleteMethodCreator {
    private CreatorHelper helper = new CreatorHelper();
    private NameConvertor nameConvertor = new NameConvertor();

    private DaoGenConfig config;
    private DefaultTableSQLCreator sqlCreator;

    public void writedeleteMethod(DaoGenConfig config, PrintWriter pw) {
        this.config = config;
        this.sqlCreator = new DefaultTableSQLCreator(config);

        writePhysicalDelete(pw);
        writePhysicalDeleteEntity(pw);

        writeDeleteOnUser(pw);
        writeDelete(pw);

        writeDeleteEntityOnUser(pw);
        writeDeleteEntity(pw);

        // 論理削除の解除メソッド(復帰)
        writeaAtivationOnUser(pw);
        writeAtivation(pw);

        writeAtivationEntityOnUser(pw);
        writeAtivationEntity(pw);
    }

    private void writeAtivationEntity(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Ativation.");
        pw.println("     * if delete flag is exists and delete flag is true, delete flug is false to activate.");
        pw.println("     * @param entity entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print("void");
        pw.print(" activation(");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        pw.print("        activation(");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.print("entity." + helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
        }
        pw.println(");");
        pw.println();

        pw.println("    }");
    }

    private void writeAtivationEntityOnUser(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Ativation.");
        pw.println("     * if delete flag is exists and delete flag is true, delete flug is false to activate.");
        pw.println("     * set saved user id.");
        pw.println("     * @param user saved userid");
        pw.println("     * @param entity entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print("void");
        pw.print(" activation(");
        pw.print(config.getCommonUseridType());
        pw.print(" user, ");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        pw.print("        activation(user, ");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.print("entity." + helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
        }
        pw.println(");");
        pw.println();

        pw.println("    }");
    }

    private void writeAtivation(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Ativation.");
        pw.println("     * if delete flag is exists and delete flag is true, delete flug is false to activate.");
        helper.writeKeyParamOnJavadoc(pw, config); // キーのJavadocを出力
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public void");
        pw.print(" activation(");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(helper.getColumnClass(columnDefinition));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(") {");

        pw.println("        DBUserPool pool = Container.getComp(DBUserPool.class);");
        pw.print("        ");
        pw.print(config.getCommonUseridType());
        pw.print(" user = (");
        pw.print(config.getCommonUseridType());
        pw.println(") pool.getUser();");

        pw.print("        activation(user, ");
        count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(");");

        pw.println("    }");
    }

    private void writeaAtivationOnUser(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Ativation.");
        pw.println("     * if delete flag is exists and delete flag is true, delete flug is false to activate.");
        pw.println("     * set saved user id.");
        pw.println("     * @param user saved userid");
        helper.writeKeyParamOnJavadoc(pw, config); // キーのJavadocを出力
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public void");
        pw.print(" activation(");
        pw.print(config.getCommonUseridType());
        pw.print(" user, ");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(helper.getColumnClass(columnDefinition));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(") {");

        if (StringUtils.isEmpty(config.getCommonDeleteFlag())) {
            // 削除フラグが存在しないので、実行できない
            pw.println("        throw new ORMappingException(\"delete flag is not exists.\");");
        } else {
            pw.print("        ");
            pw.print(config.getEntityClassName());
            pw.print(" db = physicalSelectOnKey(");
            count = 0;
            for (ColumnDefinition columnDefinition : primaryKeys) {
                if (count > 0) {
                    pw.print(", ");
                }
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                count++;
            }
            pw.println(");");

            String feildName = nameConvertor.colmnNameToFeildName(config.getCommonDeleteFlag());
            pw.print("        db.");
            pw.print(helper.feildNameToSetter(feildName));
            pw.print("(");
            if (StringUtils.isNotEmpty(config.getDeleteFlagColumnType()) && "boolean".equals(config.getDeleteFlagColumnType().toLowerCase())) {
                pw.print("Boolean.FALSE");
            } else {
                pw.print(INT_FLAG.OFF.getValue());
            }
            pw.println(");");

            // pw.println("        update(user, db);");

            ColumnDefinition userColumn = null;
            ColumnDefinition datetimeColumn = null;

            for (ColumnDefinition columnDefinition : columnDefinitions) {
                if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonUpdateUserName().toLowerCase())) {
                    userColumn = columnDefinition;
                } else if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonUpdateDateTime().toLowerCase())) {
                    datetimeColumn = columnDefinition;
                }
            }

            if (userColumn != null) {
                // 更新ユーザをセット
                feildName = nameConvertor.colmnNameToFeildName(userColumn.getColumn_name());
                pw.print("        db.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(user);");
            }
            if (datetimeColumn != null) {
                // 更新ユーザをセット
                feildName = nameConvertor.colmnNameToFeildName(datetimeColumn.getColumn_name());
                pw.print("        db.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(new Timestamp(DateUtils.now().getTime()));");
            }
            pw.println("        physicalUpdate(db);");
        }

        pw.println("    }");
    }

    private void writeDeleteEntity(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Delete.");
        pw.println("     * if delete flag is exists, the data is logical delete.");
        pw.println("     * set saved user id.");
        pw.println("     * @param entity entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print("void");
        pw.print(" delete(");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        pw.print("        delete(");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.print("entity." + helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
        }
        pw.println(");");
        pw.println();

        pw.println("    }");
    }

    private void writeDeleteEntityOnUser(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Delete.");
        pw.println("     * if delete flag is exists, the data is logical delete.");
        pw.println("     * set saved user id.");
        pw.println("     * @param user saved userid");
        pw.println("     * @param entity entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print("void");
        pw.print(" delete(");
        pw.print(config.getCommonUseridType());
        pw.print(" user, ");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        pw.print("        delete(user, ");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.print("entity." + helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
        }
        pw.println(");");
        pw.println();

        pw.println("    }");
    }

    private void writeDelete(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Delete.");
        pw.println("     * if delete flag is exists, the data is logical delete.");
        helper.writeKeyParamOnJavadoc(pw, config); // キーのJavadocを出力
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public void");
        pw.print(" delete(");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(helper.getColumnClass(columnDefinition));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(") {");

        pw.println("        DBUserPool pool = Container.getComp(DBUserPool.class);");
        pw.print("        ");
        pw.print(config.getCommonUseridType());
        pw.print(" user = (");
        pw.print(config.getCommonUseridType());
        pw.println(") pool.getUser();");

        pw.print("        delete(user, ");
        count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(");");

        pw.println("    }");
    }

    private void writeDeleteOnUser(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Delete.");
        pw.println("     * if delete flag is exists, the data is logical delete.");
        pw.println("     * set saved user id.");
        pw.println("     * @param user saved userid");
        helper.writeKeyParamOnJavadoc(pw, config); // キーのJavadocを出力
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public void");
        pw.print(" delete(");
        pw.print(config.getCommonUseridType());
        pw.print(" user, ");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(helper.getColumnClass(columnDefinition));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(") {");

        if (StringUtils.isEmpty(config.getCommonDeleteFlag())) {
            // 削除フラグが存在しないので、物理削除
            pw.print("        physicalDelete(");
            count = 0;
            for (ColumnDefinition columnDefinition : primaryKeys) {
                if (count > 0) {
                    pw.print(", ");
                }
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                count++;
            }
            pw.println(");");

        } else {
            pw.print("        ");
            pw.print(config.getEntityClassName());
            pw.print(" db = selectOnKey(");
            count = 0;
            for (ColumnDefinition columnDefinition : primaryKeys) {
                if (count > 0) {
                    pw.print(", ");
                }
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                count++;
            }
            pw.println(");");

            String feildName = nameConvertor.colmnNameToFeildName(config.getCommonDeleteFlag());
            pw.print("        db.");
            pw.print(helper.feildNameToSetter(feildName));
            pw.print("(");
            if (StringUtils.isNotEmpty(config.getDeleteFlagColumnType()) && "boolean".equals(config.getDeleteFlagColumnType().toLowerCase())) {
                pw.print("Boolean.TRUE");
            } else {
                pw.print(INT_FLAG.ON.getValue());
            }
            pw.println(");");

            // pw.println("        update(user, db);");

            ColumnDefinition userColumn = null;
            ColumnDefinition datetimeColumn = null;

            for (ColumnDefinition columnDefinition : columnDefinitions) {
                if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonUpdateUserName().toLowerCase())) {
                    userColumn = columnDefinition;
                } else if (columnDefinition.getColumn_name().toLowerCase().equals(config.getCommonUpdateDateTime().toLowerCase())) {
                    datetimeColumn = columnDefinition;
                }
            }

            if (userColumn != null) {
                // 更新ユーザをセット
                feildName = nameConvertor.colmnNameToFeildName(userColumn.getColumn_name());
                pw.print("        db.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(user);");
            }
            if (datetimeColumn != null) {
                // 更新ユーザをセット
                feildName = nameConvertor.colmnNameToFeildName(datetimeColumn.getColumn_name());
                pw.print("        db.");
                pw.print(helper.feildNameToSetter(feildName));
                pw.println("(new Timestamp(DateUtils.now().getTime()));");
            }
            pw.println("        physicalUpdate(db);");
        }

        pw.println("    }");
    }

    private void writePhysicalDeleteEntity(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Physical Delete.");
        pw.println("     * @param entity entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print("void");
        pw.print(" physicalDelete(");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        pw.print("        physicalDelete(");
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.print("entity." + helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
        }
        pw.println(");");
        pw.println();

        pw.println("    }");
    }

    private void writePhysicalDelete(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Physical Delete.");
        helper.writeKeyParamOnJavadoc(pw, config); // キーのJavadocを出力
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print("void");
        pw.print(" physicalDelete(");
        helper.writeKeyParam(pw, config); // キーをメソッドに渡す部分を出力
        pw.println(") {");

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getDeleteSqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.print("        executeUpdate(sql, ");
        helper.writeKeyParamOnExecute(pw, config); // 実行部分でキーを実行するメソッドに設定する部分を出力
        pw.println(");");

        pw.println("    }");
    }

}
