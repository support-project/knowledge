package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DefaultTableSelectMethodCreator {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DefaultTableSelectMethodCreator.class);

    private CreatorHelper helper = new CreatorHelper();
    private NameConvertor nameConvertor = new NameConvertor();

    private DaoGenConfig config;
    private DefaultTableSQLCreator sqlCreator;

    public void writeSelectMethod(DaoGenConfig config, PrintWriter pw) {
        LOG.debug("write metheds of select");
        this.config = config;
        this.sqlCreator = new DefaultTableSQLCreator(config);

        writePhysicalSelectAll(pw);
        writePhysicalSelectOnKey(pw);

        writeSelectAll(pw);
        writeSelectOnKey(pw);

        writeSelectOn(pw);
        writePhysicalSelectOn(pw);
        
        writeSelectCount(pw);
        
        
    }

   
    private void writeSelectCount(PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Count all data");
        pw.println("     * @return count");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        pw.println("    public int physicalCountAll() {");
        pw.println("        String sql = \"SELECT COUNT(*) FROM " + config.getTableDefinition().getTable_name().toUpperCase() + "\";");
        pw.println("        return executeQuerySingle(sql, Integer.class);");
        pw.println("    }");
        
//        pw.println("    /** CountAll(ignore logical delete) */");
//        pw.println("    public int countAll() {");
//        pw.println("        String sql = \"SELECT COUNT(*) FROM " + config.getTableDefinition().getTable_name().toUpperCase() + "\";");
//        if (StringUtils.isNotEmpty(config.getCommonDeleteFlag())) {
//            pw.println("        sql += \"WHERE " + config.getCommonDeleteFlag() + " = " + INT_FLAG.OFF.getValue() + "\";");
//        }
//        pw.println("        return executeQuerySingle(sql, Integer.class);");
//        pw.println("    }");
    }


    /**
     * 複合キーの場合、１つのキーでリストを取得する
     * 
     * @param pw
     */
    private void writePhysicalSelectOn(PrintWriter pw) {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);

        if (primaryKeys.size() <= 1) {
            // キーが一つ以下であれば、一つのキーで取得するメソッドは必要無し
            return;
        }
        List<String> fileNames = sqlCreator.getPhysicalSelectOnSqlFileNames();
        int idx = 0;
        for (ColumnDefinition primary : primaryKeys) {
            String fileName = fileNames.get(idx++);
            writePhysicalSelectOn(pw, primary, fileName);
        }

    }

    private void writePhysicalSelectOn(PrintWriter pw, ColumnDefinition primary, String fileName) {
        pw.println("    /**");
        pw.print("     * Select data on ");
        pw.print(primary.getColumn_name());
        pw.println(" column.");
        pw.print("     * @param");
        pw.print(" " + nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.print(" " + nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.println("");
        pw.println("     * @return list");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> physicalSelectOn");
        pw.print(nameConvertor.colmnNameToFeildName(primary.getColumn_name(), true));
        pw.print("(");
        pw.print(helper.getColumnClass(primary));
        pw.print(" " + nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.println(") {");

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(fileName);
        pw.println("\");");

        // SQLの実行
        pw.print("        return executeQueryList(sql, ");
        pw.print(config.getEntityClassName() + ".class, ");
        pw.print(nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.println(");");

        pw.println("    }");
    }

    /**
     * 複合キーの場合、１つのキーでリストを取得する
     * 
     * @param pw
     */
    private void writeSelectOn(PrintWriter pw) {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);

        if (primaryKeys.size() <= 1) {
            // キーが一つ以下であれば、一つのキーで取得するメソッドは必要無し
            return;
        }
        List<String> fileNames = sqlCreator.getSelectOnSqlFileNames();
        int idx = 0;
        for (ColumnDefinition primary : primaryKeys) {
            String fileName = fileNames.get(idx++);
            writeSelectOn(pw, primary, fileName);
        }
    }

    /**
     * １つのキーでリストを取得する
     * 
     * @param pw
     * @param fileName
     */
    private void writeSelectOn(PrintWriter pw, ColumnDefinition primary, String fileName) {
        pw.println("    /**");
        pw.print("     * Select data that not deleted on ");
        pw.print(primary.getColumn_name());
        pw.println(" column.");
        pw.print("     * @param");
        pw.print(" " + nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.print(" " + nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.println("");
        pw.println("     * @return list");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> selectOn");
        pw.print(nameConvertor.colmnNameToFeildName(primary.getColumn_name(), true));
        pw.print("(");
        pw.print(helper.getColumnClass(primary));
        pw.print(" " + nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.println(") {");

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(fileName);
        pw.println("\");");

        // SQLの実行
        pw.print("        return executeQueryList(sql, ");
        pw.print(config.getEntityClassName() + ".class, ");
        pw.print(nameConvertor.colmnNameToFeildName(primary.getColumn_name()));
        pw.println(");");

        pw.println("    }");

    }

    private void writeSelectOnKey(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Select data that not deleted on key.");
        helper.writeKeyParamOnJavadoc(pw, config); // キーのJavadocを出力
        pw.println("     * @return data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" selectOnKey(");
        helper.writeKeyParam(pw, config); // キーをメソッドに渡す部分を出力
        pw.println(") {");

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getSelectOnKeySqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.print("        return executeQuerySingle(sql, ");
        pw.print(config.getEntityClassName() + ".class, ");
        helper.writeKeyParamOnExecute(pw, config); // 実行部分でキーを実行するメソッドに設定する部分を出力
        pw.println(");");

        pw.println("    }");
    }

    private void writePhysicalSelectOnKey(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Select data on key.");
        helper.writeKeyParamOnJavadoc(pw, config); // キーのJavadocを出力
        pw.println("     * @return data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public ");
        pw.print(config.getEntityClassName());
        pw.print(" physicalSelectOnKey(");
        helper.writeKeyParam(pw, config); // キーをメソッドに渡す部分を出力
        pw.println(") {");

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getPhysicalSelectOnKeySqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.print("        return executeQuerySingle(sql, ");
        pw.print(config.getEntityClassName() + ".class, ");
        helper.writeKeyParamOnExecute(pw, config); // 実行部分でキーを実行するメソッドに設定する部分を出力
        pw.println(");");

        pw.println("    }");
    }

    private void writeSelectAll(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data that not deleted.");
        pw.println("     * @return all data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> selectAll() { ");
        pw.println();
        pw.println("        return selectAll(Order.DESC);");
        pw.println("    }");
        
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data that not deleted.");
        pw.println("     * @param order order");
        pw.println("     * @return all data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> selectAll(Order order) { ");
        pw.println();

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getSelectAllSqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.println("        sql = String.format(sql, order.toString());");
        pw.print("        return executeQueryList(sql, ");
        pw.println(config.getEntityClassName() + ".class);");
        pw.println("    }");
        
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data that not deleted with pager.");
        pw.println("     * @param limit limit");
        pw.println("     * @param offset offset");
        pw.println("     * @return all data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> selectAllWidthPager(int limit, int offset) { ");
        pw.println();
        pw.println("        return selectAllWidthPager(limit, offset, Order.DESC);");
        pw.println("    }");
        
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data that not deleted with pager.");
        pw.println("     * @param limit limit");
        pw.println("     * @param offset offset");
        pw.println("     * @param order order");
        pw.println("     * @return all data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> selectAllWidthPager(int limit, int offset, Order order) { ");
        pw.println();

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getSelectAllWithPagerSqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.println("        sql = String.format(sql, order.toString());");
        pw.print("        return executeQueryList(sql, ");
        pw.println(config.getEntityClassName() + ".class, limit, offset);");

        pw.println("    }");
        
        
        // コメント
        pw.println("    /**");
        pw.println("     * Select count that not deleted.");
        pw.println("     * @return count");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public Integer");
        pw.print(" selectCountAll() { ");
        pw.println();

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getSelectCountAllSqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.println("        return executeQuerySingle(sql, Integer.class);");

        pw.println("    }");
        
    }

    private void writePhysicalSelectAll(PrintWriter pw) {
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data.");
        pw.println("     * @return all data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> physicalSelectAll() { ");
        pw.println();
        pw.println("        return physicalSelectAll(Order.DESC);");
        pw.println("    }");
        
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data.");
        pw.println("     * @param order order");
        pw.println("     * @return all data");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> physicalSelectAll(Order order) { ");
        pw.println();

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getPhysicalSelectAllSqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.println("        sql = String.format(sql, order.toString());");
        pw.print("        return executeQueryList(sql, ");
        pw.println(config.getEntityClassName() + ".class);");

        pw.println("    }");
        
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data with pager.");
        pw.println("     * @param limit limit");
        pw.println("     * @param offset offset");
        pw.println("     * @return all data on limit and offset");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");
        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> physicalSelectAllWithPager(int limit, int offset) { ");
        pw.println();
        pw.println("        return physicalSelectAllWithPager(limit, offset, Order.DESC);");
        pw.println("    }");
        
        // コメント
        pw.println("    /**");
        pw.println("     * Select all data with pager.");
        pw.println("     * @param limit limit");
        pw.println("     * @param offset offset");
        pw.println("     * @param order order");
        pw.println("     * @return all data on limit and offset");
        pw.println("     */");
        pw.println("    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)");

        // メソッド定義
        pw.print("    public List<");
        pw.print(config.getEntityClassName());
        pw.print("> physicalSelectAllWithPager(int limit, int offset, Order order) { ");
        pw.println();

        // SQLの取得
        pw.print("        String sql = SQLManager.getInstance().getSql(\"");
        pw.print(config.getSqlPackagePath());
        pw.print("/");
        pw.print(sqlCreator.getPhysicalSelectAllWithPagerSqlFileName());
        pw.println("\");");

        // SQLの実行
        pw.println("        sql = String.format(sql, order.toString());");
        pw.print("        return executeQueryList(sql, ");
        pw.println(config.getEntityClassName() + ".class, limit, offset);");

        pw.println("    }");
    }

}
