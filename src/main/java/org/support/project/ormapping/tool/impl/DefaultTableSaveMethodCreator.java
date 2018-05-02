package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DefaultTableSaveMethodCreator {
    private CreatorHelper helper = new CreatorHelper();
    private NameConvertor nameConvertor = new NameConvertor();

    private DaoGenConfig config;

    public void writeSaveMethod(DaoGenConfig config, PrintWriter pw) {
        this.config = config;

        writeSaveOnUser(pw);
        writeSave(pw);
    }

    private void writeSave(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Save. ");
        pw.println("     * if same key data is exists, the data is update. otherwise the data is insert.");
        pw.println("     * @param entity entity");
        pw.println("     * @return saved entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" save(");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        // DBの存在チェック
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        pw.print("        ");
        pw.print(config.getEntityClassName());
        pw.print(" db = selectOnKey(");
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print("entity.");
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.print(helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
        }
        pw.println(");");

        pw.println("        if (db == null) {");
        pw.println("            return insert(entity);");
        pw.println("        } else {");
        pw.println("            return update(entity);");
        pw.println("        }");

        pw.println("    }");
    }

    private void writeSaveOnUser(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Save. ");
        pw.println("     * if same key data is exists, the data is update. otherwise the data is insert.");
        pw.println("     * set saved user id.");
        pw.println("     * @param user saved userid");
        pw.println("     * @param entity entity");
        pw.println("     * @return saved entity");
        pw.println("     */");

        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" save(");
        pw.print(config.getCommonUseridType());
        pw.print(" user, ");
        pw.print(config.getEntityClassName());
        pw.println(" entity) {");

        // DBの存在チェック
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        pw.print("        ");
        pw.print(config.getEntityClassName());
        pw.print(" db = selectOnKey(");
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print("entity.");
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.print(helper.feildNameToGetter(feildName));
            pw.print("()");
            count++;
        }
        pw.println(");");

        pw.println("        if (db == null) {");
        pw.println("            return insert(user, entity);");
        pw.println("        } else {");
        pw.println("            return update(user, entity);");
        pw.println("        }");

        pw.println("    }");

    }

}
