package org.support.project.ormapping.tool.impl;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.entity.TableDefinition;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.tool.EntityClassCreator;

public class DefaultEntityCteatorImpl implements EntityClassCreator {
    /** ログ */
    private static Log log = LogFactory.getLog(DefaultEntityCteatorImpl.class);

    private CreatorHelper helper = new CreatorHelper();
    private NameConvertor nameConvertor = new NameConvertor();

    /**
     * エンティティクラスを生成する エンティティは、後で手で修正したい事もあるので、以下の構成にする
     * 
     * xxx.entity.gen GenXxxxEntity テーブル定義から自動生成したカラム設定が書かれる(このクラスは手で修正はしない) アブストラクトにする
     * 
     * xxx.entityパッケージ XxxxEntity 上記GenEntityを継承したクラス。いったん箱だけ用意する
     * 
     */
    @Override
    public void create(Collection<TableDefinition> tableDefinitions, String dir, String packageName, String suffix) throws ORMappingException {
        // ディレクトリが存在しなければ作成
        File entityDir = new File(dir);
        if (!entityDir.exists()) {
            entityDir.mkdirs();
        }
        File genDir = new File(dir, "gen");
        if (!genDir.exists()) {
            genDir.mkdirs();
        }

        for (TableDefinition tableDefinition : tableDefinitions) {
            log.info("テーブル [" + tableDefinition.getTable_name() + "] の処理を開始します。");
            String entityClassName = nameConvertor.tableNameToClassName(tableDefinition.getTable_name()).concat(suffix);
            String genEntityClassName = "Gen" + nameConvertor.tableNameToClassName(tableDefinition.getTable_name()).concat(suffix);

            File entityFile = new File(entityDir, entityClassName.concat(".java"));
            File genEntityFile = new File(genDir, genEntityClassName.concat(".java"));

            String entityPackage = packageName;
            String genPackage = packageName.concat(".gen");

            createGenEntity(tableDefinition, genEntityFile, genEntityClassName, genPackage);
            log.info(genEntityFile.getAbsolutePath() + " を処理しました");

            createEntity(tableDefinition, entityFile, entityClassName, entityPackage, genEntityClassName, genPackage);
            log.info(entityFile.getAbsolutePath() + " を処理しました");

        }
    }

    private void createEntity(TableDefinition tableDefinition, File entityFile, String entityClassName, String entityPackage,
            String genEntityClassName, String genPackage) {
        if (entityFile.exists()) {
            log.info(entityFile.getAbsolutePath() + "は既に存在するため、作成処理をスキップします");
            return;
        }
        log.debug(entityFile.getAbsolutePath() + "を作成します");

        PrintWriter pw = null;
        try {
            pw = helper.getPrintWriter(entityFile);

            pw.println("package " + entityPackage + ";");
            pw.println();
            pw.println("import " + genPackage + "." + genEntityClassName + ";");
            pw.println();

            pw.println("import java.util.List;");
            pw.println("import java.util.Map;");
            pw.println();

            pw.println("import org.support.project.common.bean.ValidateError;");
            pw.println("import org.support.project.di.Container;");
            pw.println("import org.support.project.di.DI;");
            pw.println("import org.support.project.di.Instance;");
            pw.println();

            List<ColumnDefinition> columnDefinitions = tableDefinition.getColumns();
            pw.println(helper.getColmnTypeImport(columnDefinitions));
            pw.println();

            pw.println("/**");
            pw.println(" * " + tableDefinition.getRemarks());
            pw.println(" */");

            pw.println("@DI(instance = Instance.Prototype)");
            pw.println("public class " + entityClassName + " extends " + genEntityClassName + " {");
            pw.println();
            pw.println("    /** SerialVersion */");
            pw.println("    private static final long serialVersionUID = 1L;");
            pw.println();

            // インスタンス取得
            pw.println(helper.makeInstanceMethod(entityClassName));

            // コンストラクタ
            pw.println(helper.makeConstractor(entityClassName));

            // キーのコンストラクタ
            pw.println(makeKeyConstractorComment(columnDefinitions));
            Collection<ColumnDefinition> primaryKeys = getPrimaryKeys(columnDefinitions);
            int count = 0;
            pw.print("    public " + entityClassName + "(");
            count = 0;
            for (ColumnDefinition columnDefinition : primaryKeys) {
                if (count > 0) {
                    pw.print(", ");
                }
                pw.print(helper.getColumnClass(columnDefinition));
                pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                count++;
            }
            pw.println(") {");
            pw.print("        super(");
            count = 0;
            for (ColumnDefinition columnDefinition : primaryKeys) {
                if (count > 0) {
                    pw.print(", ");
                }
                pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                count++;
            }
            pw.println(");");
            pw.println("    }");

            // staticメソッドでの提供を辞める
            // pw.println("    /**");
            // pw.println("     * validate ");
            // pw.println("     */");
            // pw.println("    public static List<ValidateError> validate(Map<String, String> values) {");
            // pw.print("        return ");
            // pw.print(genEntityClassName);
            // pw.println(".validate(values);");
            // pw.println("    }");

            pw.println();
            pw.println("}");

            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void createGenEntity(TableDefinition tableDefinition, File genEntityFile, String genEntityClassName, String genPackage) {
        log.debug(genEntityFile.getAbsolutePath() + "を作成します");

        PrintWriter pw = null;
        try {
            List<ColumnDefinition> columnDefinitions = tableDefinition.getColumns();

            pw = helper.getPrintWriter(genEntityFile);

            pw.println("package " + genPackage + ";");
            pw.println();
            pw.println("import java.io.Serializable;");
            pw.println("import java.util.List;");
            pw.println("import java.util.ArrayList;");
            pw.println("import java.util.Map;");

            pw.println();
            pw.println(helper.getColmnTypeImport(columnDefinitions));
            pw.println();
            // pw.println("import org.support.project.common.util.PropertyUtil;");
            pw.println();

            pw.println("import org.support.project.common.bean.ValidateError;");
            pw.println("import org.support.project.common.validate.Validator;");
            pw.println("import org.support.project.common.validate.ValidatorFactory;");

            pw.println("import org.support.project.di.Container;");
            pw.println("import org.support.project.di.DI;");
            pw.println("import org.support.project.di.Instance;");
            pw.println();

            pw.println("/**");
            pw.println(" * " + tableDefinition.getRemarks());
            pw.println(" */");

            pw.println("@DI(instance = Instance.Prototype)");
            pw.println("public class " + genEntityClassName + " implements Serializable {");
            pw.println();
            pw.println("    /** SerialVersion */");
            pw.println("    private static final long serialVersionUID = 1L;");
            pw.println();

            // インスタンス取得
            pw.println(helper.makeInstanceMethod(genEntityClassName));

            // コンストラクタ
            pw.println(helper.makeConstractor(genEntityClassName));

            // キーのコンストラクタ
            pw.println(makeKeyConstractorComment(columnDefinitions));
            writeKeyConstracter(columnDefinitions, pw, genEntityClassName);

            // 列定義出力(Getter/Setter)
            writeColumns(genEntityClassName, columnDefinitions, pw);

            // プライマリキーの取得
            writeKeyAccess(columnDefinitions, pw);

            // キーで比較
            writeEqualsOnKey(genEntityClassName, columnDefinitions, pw);

            // ToString生成
            writeToString(columnDefinitions, pw);

            // 列名から画面に表示するラベル名を取得するメソッド生成（後で変更できるように）
            writeToLabelName(columnDefinitions, pw);

            // validateの生成
            DefaultValidateCreator validateCreator = new DefaultValidateCreator(helper, nameConvertor);
            validateCreator.writeValidate(columnDefinitions, pw);
            validateCreator.writeClassValidate(columnDefinitions, pw);

            pw.println();
            pw.println("}");

            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 列名から表示用の名称を取得するメソッドを生成する
     * 
     * @param columnDefinitions
     * @param pw
     */
    private void writeToLabelName(List<ColumnDefinition> columnDefinitions, PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * Convert label to display ");
        pw.println("     * @param label label");
        pw.println("     * @return convert label ");
        pw.println("     */");

        pw.println("    protected String convLabelName(String label) {");
        pw.println("        return label;");
        pw.println("    }");
    }

    /**
     * 
     * @param columnDefinitions
     * @return
     */
    private String makeKeyConstractorComment(List<ColumnDefinition> columnDefinitions) {
        StringBuilder builder = new StringBuilder();
        builder.append("    /**\n");
        builder.append("     * Constructor\n");

        Collection<ColumnDefinition> primaryKeys = getPrimaryKeys(columnDefinitions);
        for (ColumnDefinition columnDefinition : primaryKeys) {
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            builder.append("     * @param " + feildName + " " + columnDefinition.getRemarks());
            // builder.append(" ");
            // builder.append(columnDefinition.getType_name());
            // builder.append("[");
            // builder.append(columnDefinition.getColumn_size());
            // builder.append("]");
            builder.append("\n");
        }

        builder.append("     */\n");
        return builder.toString();
    }

    private void writeToString(List<ColumnDefinition> columnDefinitions, PrintWriter pw) {
        Collection<ColumnDefinition> primaryKeys = getPrimaryKeys(columnDefinitions);

        pw.println("    /**");
        pw.println("     * ToString ");
        pw.println("     * @return string ");
        pw.println("     */");

        pw.println("    public String toString() {");

        pw.println("        StringBuilder builder = new StringBuilder();");
        for (ColumnDefinition columnDefinition : primaryKeys) {
            pw.print("        builder.append(\"");
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.print(" = \").append(");
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.println(").append(\"\\n\");");
        }

        for (ColumnDefinition columnDefinition : columnDefinitions) {
            if (!primaryKeys.contains(columnDefinition)) {
                pw.print("        builder.append(\"");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.print(" = \").append(");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.println(").append(\"\\n\");");
            }
        }

        pw.println("        return builder.toString();");

        pw.println("    }");
    }

    private void writeKeyConstracter(List<ColumnDefinition> columnDefinitions, PrintWriter pw, String genEntityClassName) {
        Collection<ColumnDefinition> primaryKeys = getPrimaryKeys(columnDefinitions);

        // setter
        int count = 0;
        pw.print("    public " + genEntityClassName + "(");
        count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(helper.getColumnClass(columnDefinition));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(") {");
        pw.println("        super();");
        for (ColumnDefinition columnDefinition : primaryKeys) {
            pw.print("        this.");
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.print(" = ");
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.println(";");
        }
        pw.println("    }");

    }

    private void writeColumns(String genEntityClassName, List<ColumnDefinition> columnDefinitions, PrintWriter pw) {
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            pw.println("    " + makeColumnComment(columnDefinition));
            pw.println("    private " + helper.getColumnClass(columnDefinition) + " "
                    + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()) + ";");
        }
        pw.println();

        for (ColumnDefinition columnDefinition : columnDefinitions) {
            // getter
            pw.println(helper.colmnNameToGetterMethod(columnDefinition));
            // setter
            pw.println(helper.colmnNameToSetterMethod(genEntityClassName, columnDefinition));

            pw.println();
        }

    }

    private String makeColumnComment(ColumnDefinition columnDefinition) {
        StringBuilder builder = new StringBuilder();
        builder.append("/** ");
        builder.append(columnDefinition.getRemarks());
        // builder.append(" ");
        // builder.append(columnDefinition.getType_name());
        // builder.append("[");
        // builder.append(columnDefinition.getColumn_size());
        // builder.append("]");
        builder.append(" */");
        return builder.toString();
    }

    private void writeKeyAccess(List<ColumnDefinition> columnDefinitions, PrintWriter pw) {
        Collection<ColumnDefinition> primaryKeys = getPrimaryKeys(columnDefinitions);

        // getter
        pw.println("    /**");
        pw.println("     * Get key values ");
        pw.println("     * @return values ");
        pw.println("     */");

        pw.println("    public Object[] getKeyValues() {");

        pw.print("        Object[] keyValues = new Object[");
        pw.print(primaryKeys.size());
        pw.println("];");

        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            pw.print("        keyValues[");
            pw.print(count);
            pw.print("] = this.");
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.println(";");
            count++;
        }
        pw.println("        return keyValues;");

        pw.println("    }");

        // setter
        pw.println("    /**");
        pw.println("     * Set key values ");
        for (ColumnDefinition columnDefinition : primaryKeys) {
            String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
            pw.println("     * @param " + feildName + " " + columnDefinition.getRemarks());
        }
        pw.println("     */");
        pw.print("    public void setKeyValues(");
        count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(helper.getColumnClass(columnDefinition));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
        pw.println(") {");
        for (ColumnDefinition columnDefinition : primaryKeys) {
            pw.print("        this.");
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.print(" = ");
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.println(";");
        }
        pw.println("    }");
    }

    private void writeEqualsOnKey(String genEntityClassName, List<ColumnDefinition> columnDefinitions, PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * compare on key ");
        pw.println("     * @param entity entity ");
        pw.println("     * @return result ");
        pw.println("     */");

        pw.print("    public boolean equalsOnKey(");
        pw.print(genEntityClassName);
        pw.println(" entity) {");

        pw.println("        Object[] keyValues1 = getKeyValues();");
        pw.println("        Object[] keyValues2 = entity.getKeyValues();");

        pw.println("        for (int i = 0; i < keyValues1.length; i++) {");
        pw.println("            Object val1 = keyValues1[i];");
        pw.println("            Object val2 = keyValues2[i];");
        pw.println("            if (val1 == null && val2 != null) {");
        pw.println("                return false;");
        pw.println("            }");
        pw.println("            if (val1 != null && val2 == null) {");
        pw.println("                return false;");
        pw.println("            }");
        pw.println("            if (val1 != null && val2 != null) {");
        pw.println("                if (!val1.equals(val2)) {");
        pw.println("                    return false;");
        pw.println("                }");
        pw.println("            }");

        pw.println("            ");

        pw.println("        }");

        pw.println("        return true;");
        pw.println("    }");
    }

    private Collection<ColumnDefinition> getPrimaryKeys(List<ColumnDefinition> columnDefinitions) {
        TreeMap<Integer, ColumnDefinition> map = new TreeMap<>();
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            if (columnDefinition.isPrimary()) {
                map.put(columnDefinition.getPrimary_no(), columnDefinition);
            }
        }
        return map.values();
    }

}
