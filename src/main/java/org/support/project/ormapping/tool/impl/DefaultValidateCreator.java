package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.sql.Types;
import java.util.List;

import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;

/**
 * 入力チェックメソッドの作成
 * 
 * @author Koda
 *
 */
public class DefaultValidateCreator {
    /** CreatorHelper */
    private CreatorHelper helper = new CreatorHelper();
    /** NameConvertor */
    private NameConvertor nameConvertor = new NameConvertor();

    /**
     * Constructor
     * 
     * @param helper
     *            CreatorHelper
     * @param nameConvertor
     *            NameConvertor
     */
    public DefaultValidateCreator(CreatorHelper helper, NameConvertor nameConvertor) {
        super();
        this.helper = helper;
        this.nameConvertor = nameConvertor;
    }

    /**
     * DBの列定義を用いて、入力チェックメソッドを生成
     * 
     * @param columnDefinitions columnDefinitions
     * @param pw PrintWriter
     */
    public void writeValidate(List<ColumnDefinition> columnDefinitions, PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * validate ");
        pw.println("     * @return validate error list ");
        pw.println("     */");

        pw.println("    public List<ValidateError> validate() {");

        pw.println("        List<ValidateError> errors = new ArrayList<>();");
        pw.println("        Validator validator;");
        pw.println("        ValidateError error;");

        for (ColumnDefinition columnDefinition : columnDefinitions) {
            if (helper.isnot(columnDefinition.getIs_autoincrement()) && helper.isnot(columnDefinition.getIs_nullable())) {
                // NULLは許さない
                pw.println("        validator = ValidatorFactory.getInstance(Validator.REQUIRED);");
                pw.print("        error = validator.validate(");
                pw.print("this.");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.print(", convLabelName(\"");
                pw.print(nameConvertor.colmnNameToLabelName(columnDefinition.getColumn_name()));
                pw.println("\"));");
                pw.println("        if (error != null) {");
                pw.println("            errors.add(error);");
                pw.println("        }");
            }
            short type = columnDefinition.getData_type();
            if (type == Types.INTEGER) {
                // Intの型チェック
                pw.println("        validator = ValidatorFactory.getInstance(Validator.INTEGER);");
                pw.print("        error = validator.validate(");
                pw.print("this.");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.print(", convLabelName(\"");
                pw.print(nameConvertor.colmnNameToLabelName(columnDefinition.getColumn_name()));
                pw.println("\"));");
                pw.println("        if (error != null) {");
                pw.println("            errors.add(error);");
                pw.println("        }");
            } else if (type == Types.VARCHAR) {
                // VARCHAR なので文字数チェック
                pw.println("        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);");
                pw.print("        error = validator.validate(");
                pw.print("this.");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.print(", convLabelName(\"");
                pw.print(nameConvertor.colmnNameToLabelName(columnDefinition.getColumn_name()));
                pw.print("\"), ");
                pw.print(columnDefinition.getCharacter_maximum_length());
                pw.println(");");

                pw.println("        if (error != null) {");
                pw.println("            errors.add(error);");
                pw.println("        }");
            }
        }

        pw.println("        return errors;");

        pw.println("    }");
    }

    /**
     * DBの列定義を用いて、入力チェックメソッドを生成 クラスに紐づく、staticメソッドを作成
     * 
     * @param columnDefinitions columnDefinitions
     * @param pw PrintWriter
     */
    public void writeClassValidate(List<ColumnDefinition> columnDefinitions, PrintWriter pw) {
        pw.println("    /**");
        pw.println("     * validate ");
        pw.println("     * @param values value map ");
        pw.println("     * @return validate error list ");
        pw.println("     */");

        pw.println("    public List<ValidateError> validate(Map<String, String> values) {");

        pw.println("        List<ValidateError> errors = new ArrayList<>();");
        pw.println("        Validator validator;");
        pw.println("        ValidateError error;");

        for (ColumnDefinition columnDefinition : columnDefinitions) {
            if (helper.isnot(columnDefinition.getIs_autoincrement()) && helper.isnot(columnDefinition.getIs_nullable())) {
                // NULLは許さない
                pw.println("        validator = ValidatorFactory.getInstance(Validator.REQUIRED);");
                pw.print("        error = validator.validate(");
                pw.print("values.get(\"");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.print("\"), convLabelName(\"");
                pw.print(nameConvertor.colmnNameToLabelName(columnDefinition.getColumn_name()));
                pw.println("\"));");
                pw.println("        if (error != null) {");
                pw.println("            errors.add(error);");
                pw.println("        }");
            }
            short type = columnDefinition.getData_type();
            if (type == Types.INTEGER) {
                // Intの型チェック
                pw.println("        validator = ValidatorFactory.getInstance(Validator.INTEGER);");
                pw.print("        error = validator.validate(");
                pw.print("values.get(\"");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.print("\"), convLabelName(\"");
                pw.print(nameConvertor.colmnNameToLabelName(columnDefinition.getColumn_name()));
                pw.println("\"));");
                pw.println("        if (error != null) {");
                pw.println("            errors.add(error);");
                pw.println("        }");
            } else if (type == Types.VARCHAR) {
                // VARCHAR なので文字数チェック
                pw.println("        validator = ValidatorFactory.getInstance(Validator.MAX_LENGTH);");
                pw.print("        error = validator.validate(");
                pw.print("values.get(\"");
                pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
                pw.print("\"), convLabelName(\"");
                pw.print(nameConvertor.colmnNameToLabelName(columnDefinition.getColumn_name()));
                pw.print("\"), ");
                pw.print(columnDefinition.getCharacter_maximum_length());
                pw.println(");");

                pw.println("        if (error != null) {");
                pw.println("            errors.add(error);");
                pw.println("        }");
            }
        }

        pw.println("        return errors;");

        pw.println("    }");
    }

}
