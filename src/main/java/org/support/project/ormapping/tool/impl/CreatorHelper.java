package org.support.project.ormapping.tool.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.tool.DaoGenConfig;

public class CreatorHelper {

    private NameConvertor nameConvertor = new NameConvertor();

    public PrintWriter getPrintWriter(File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            return new DebugAblePrintWriter(bufferedWriter);
        } catch (FileNotFoundException e) {
            throw new ORMappingException(e);
        }
    }

    public String getColmnTypeImport(List<ColumnDefinition> columnDefinitions) {
        StringBuilder builder = new StringBuilder();
        List<Short> types = new ArrayList<>();
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            short type = columnDefinition.getData_type();
            if (!types.contains(type)) {
                if (Types.BLOB == type) {
                    // builder.append("import ").append(Blob.class.getName()).append(";\n");
                    builder.append("import ").append(InputStream.class.getName()).append(";\n");
                } else if (Types.VARBINARY == type) {
                    builder.append("import ").append(InputStream.class.getName()).append(";\n");
                } else if (Types.LONGVARBINARY == type) {
                    builder.append("import ").append(InputStream.class.getName()).append(";\n");
                } else if (Types.BOOLEAN == type) {
                    // builder.append("import ").append(Boolean.class.getName()).append(";\n");
                } else if (Types.CHAR == type) {
                    // builder.append("import ").append(Character.class.getName()).append(";\n");
                } else if (Types.CLOB == type) {
                    // builder.append("import ").append(Clob.class.getName()).append(";\n");
                } else if (Types.DATE == type) {
                    builder.append("import ").append(Date.class.getName()).append(";\n");
                } else if (Types.DECIMAL == type) {
                    builder.append("import ").append(BigDecimal.class.getName()).append(";\n");
                } else if (Types.DOUBLE == type) {
                    // builder.append("import ").append(Double.class.getName()).append(";\n");
                } else if (Types.FLOAT == type) {
                    // builder.append("import ").append(Float.class.getName()).append(";\n");
                } else if (Types.INTEGER == type) {
                    // builder.append("import ").append(Integer.class.getName()).append(";\n");
                } else if (Types.TIME == type) {
                    builder.append("import ").append(Time.class.getName()).append(";\n");
                } else if (Types.TIMESTAMP == type) {
                    builder.append("import ").append(Timestamp.class.getName()).append(";\n");
                } else if (Types.VARCHAR == type) {
                    // builder.append("import ").append(String.class.getName()).append(";\n");
                }
                types.add(type);
            }
        }
        return builder.toString();
    }

    public String getColumnClass(ColumnDefinition columnDefinition) {
        short type = columnDefinition.getData_type();
        // if (Types.BIGINT == type) {
        // return BigDecimal.class.getSimpleName();
        // } else if (Types.BIT == type) {
        // return Boolean.class.getSimpleName();
        // } else if (Types.BINARY == type) {
        // return Blob.class.getSimpleName();
        // } else
        if (Types.BOOLEAN == type) {
            return Boolean.class.getSimpleName();
        } else if (Types.CHAR == type) {
            return Character.class.getSimpleName();
            // } else if (Types.CLOB == type) {
            // return Clob.class.getSimpleName();
        } else if (Types.DATE == type) {
            return Date.class.getSimpleName();
        } else if (Types.DECIMAL == type) {
            return BigDecimal.class.getSimpleName();
        } else if (Types.DOUBLE == type) {
            return Double.class.getSimpleName();
        } else if (Types.FLOAT == type) {
            return Float.class.getSimpleName();
        } else if (Types.INTEGER == type) {
            return Integer.class.getSimpleName();
            // } else if (Types.LONGNVARCHAR == type) {
            // return Clob.class.getSimpleName();
            // } else if (Types.LONGVARBINARY == type) {
            // return Blob.class.getSimpleName();
            // } else if (Types.SMALLINT == type) {
            // return Integer.class.getSimpleName();
        } else if (Types.TIME == type) {
            return Time.class.getSimpleName();
        } else if (Types.TIMESTAMP == type) {
            return Timestamp.class.getSimpleName();
        } else if (Types.VARCHAR == type) {
            return String.class.getSimpleName();

        } else if (Types.BIGINT == type) {
            return Long.class.getSimpleName();
        } else if (Types.CLOB == type) {
            return String.class.getSimpleName();
        } else if (Types.BLOB == type) {
            return InputStream.class.getSimpleName();
        } else if (Types.VARBINARY == type) {
            return InputStream.class.getSimpleName();
        } else if (Types.LONGVARBINARY == type) {
            return InputStream.class.getSimpleName();
        }

        throw new ORMappingException("この型は未対応 : " + type);
    }

    public String colmnNameToGetterMethod(ColumnDefinition columnDefinition) {
        String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
        StringBuilder builder = new StringBuilder();
        // コメント
        builder.append("    /**").append("\n");
        builder.append("     * Get " + columnDefinition.getRemarks() + ".").append("\n");
        builder.append("     * @return " + columnDefinition.getRemarks()).append("\n");
        builder.append("     */\n");
        // 1行目
        builder.append("    public ");
        builder.append(getColumnClass(columnDefinition)).append(" ");

        builder.append(feildNameToGetter(feildName));
        builder.append("()");
        builder.append(" {\n");
        // 2行目
        builder.append("        return this.").append(feildName).append(";").append("\n");
        // 3行目
        builder.append("    }");

        return builder.toString();
    }

    public String feildNameToGetter(String feildName) {
        StringBuilder builder = new StringBuilder();
        builder.append("get");
        for (int i = 0; i < feildName.length(); i++) {
            char c = feildName.charAt(i);
            if (i == 0) {
                builder.append(Character.toUpperCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    protected String colmnNameToSetterMethod(String genEntityClassName, ColumnDefinition columnDefinition) {
        String feildName = nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name());
        StringBuilder builder = new StringBuilder();
        // コメント
        builder.append("    /**\n");
        builder.append("     * Set " + columnDefinition.getRemarks() + ".\n");
        builder.append("     * @param " + feildName + " " + columnDefinition.getRemarks()).append("\n");
        builder.append("     * @return this object");
        builder.append("     */\n");
        // 1行目
        builder.append("    public ").append(genEntityClassName).append(" ");
        builder.append(feildNameToSetter(feildName));

        builder.append("(");
        builder.append(getColumnClass(columnDefinition)).append(" ");
        builder.append(feildName);
        builder.append(") {\n");
        // 2行目
        builder.append("        this.").append(feildName).append(" = ").append(feildName).append(";").append("\n");
        // 3行目
        builder.append("        return this;\n");
        // 4行目
        builder.append("    }");

        return builder.toString();
    }

    public String feildNameToSetter(String feildName) {
        StringBuilder builder = new StringBuilder();
        builder.append("set");
        for (int i = 0; i < feildName.length(); i++) {
            char c = feildName.charAt(i);
            if (i == 0) {
                builder.append(Character.toUpperCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public String makeInstanceMethod(String className) {
        StringBuilder builder = new StringBuilder();
        builder.append("    /**\n");
        builder.append("     * Get instance from DI container.\n");
        builder.append("     * @return instance\n");
        builder.append("     */\n");
        builder.append("    public static ").append(className).append(" get() {\n");
        builder.append("        return Container.getComp(").append(className).append(".class);\n");
        builder.append("    }\n");
        return builder.toString();
    }

    public String makeConstractor(String className) {
        StringBuilder builder = new StringBuilder();
        builder.append("    /**\n");
        builder.append("     * Constructor.\n");
        builder.append("     */\n");

        builder.append("    public " + className + "() {\n");
        builder.append("        super();\n");
        builder.append("    }\n");

        return builder.toString();
    }

    public boolean is(String able) {
        if (able != null) {
            return able.toString().toLowerCase().equals("yes");
        }
        return false;
    }

    public boolean isnot(String able) {
        if (able != null) {
            return able.toString().toLowerCase().equals("no");
        }
        return false;
    }

    
    
    /**
     * キー項目のJavadocを出力
     * @param pw PrintWriter
     * @param config DaoGenConfig
     */
    public void writeKeyParamOnJavadoc(PrintWriter pw, DaoGenConfig config) {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        for (ColumnDefinition columnDefinition : primaryKeys) {
            pw.print("     * @param ");
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            pw.println("");
        }
    }
    /**
     * キーをメソッドに渡す部分を出力
     * @param pw PrintWriter
     * @param config DaoGenConfig
     */
    public void writeKeyParam(PrintWriter pw, DaoGenConfig config) {
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        int count = 0;
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(this.getColumnClass(columnDefinition));
            pw.print(" " + nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
    }
    /**
     * 実行部分でキーを実行するメソッドに設定する部分を出力
     * @param pw PrintWriter
     * @param config DaoGenConfig
     */
    public void writeKeyParamOnExecute(PrintWriter pw, DaoGenConfig config) {
        int count = 0;
        List<ColumnDefinition> columnDefinitions = config.getTableDefinition().getColumns();
        Collection<ColumnDefinition> primaryKeys = config.getPrimaryKeys(columnDefinitions);
        for (ColumnDefinition columnDefinition : primaryKeys) {
            if (count > 0) {
                pw.print(", ");
            }
            pw.print(nameConvertor.colmnNameToFeildName(columnDefinition.getColumn_name()));
            count++;
        }
    }

}
