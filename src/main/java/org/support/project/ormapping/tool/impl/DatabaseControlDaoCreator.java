package org.support.project.ormapping.tool.impl;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.entity.TableDefinition;
import org.support.project.ormapping.tool.DaoGenConfig;

public class DatabaseControlDaoCreator {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(DefaultTableDaoClassCreator.class);
    private CreatorHelper helper = new CreatorHelper();

    public void create(Collection<TableDefinition> tableDefinitions, DaoGenConfig config) {
        File controlDaoFile = new File(config.getGenDir(), "DatabaseControlDao.java");
        LOG.info(controlDaoFile.getAbsolutePath() + "を作成します");

        PrintWriter pw = null;
        try {
            pw = helper.getPrintWriter(controlDaoFile);

            // クラス定義部分の出力
            pw.println("package " + config.getGenPackage() + ";");
            pw.println();
            pw.println("import org.support.project.ormapping.dao.AbstractDao;");
            pw.println("import org.support.project.common.log.Log;");
            pw.println("import org.support.project.common.log.LogFactory;");
            pw.println("import org.support.project.di.DI;");
            pw.println("import org.support.project.di.Instance;");
            pw.println();
            
            pw.println("/**");
            pw.println(" * " + config.getTableDefinition().getRemarks());
            pw.println(" * this class is auto generate and not edit.");
            pw.println(" */");
            pw.println("@DI(instance = Instance.Singleton)");
            pw.println("public class DatabaseControlDao extends AbstractDao {");
            pw.println();
            
            pw.println("    /** SerialVersion */");
            pw.println("    private static final long serialVersionUID = 1L;");
            
            pw.println("    /** LOG */");
            pw.println("    private static final Log LOG = LogFactory.getLog(DatabaseControlDao.class);");
            
            pw.println();

            createDropAllTable(pw, tableDefinitions, config);
            createDropAllData(pw, tableDefinitions, config);
            
            pw.println();
            pw.println("}");

            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

        
    }

    private void createDropAllData(PrintWriter pw, Collection<TableDefinition> tableDefinitions, DaoGenConfig config) {
        pw.println("    /** Delete all table data */");
        pw.println("    public void dropAllData() {");
        pw.println("        String[] sqls = new String[" + tableDefinitions.size() + "];");
        int count = 0;
        for (TableDefinition tableDefinition : tableDefinitions) {
            String sql = "TRUNCATE TABLE " + tableDefinition.getTable_name() + ";";
            pw.println("        sqls[" + count + "] = \"" + sql + "\";");
            count++;
        }
        pw.println("        for (String sql : sqls) {");
        pw.println("            LOG.debug(sql);");
        pw.println("            executeUpdate(sql);");
        pw.println("        }");
        pw.println("    }");
        
    }

    private void createDropAllTable(PrintWriter pw, Collection<TableDefinition> tableDefinitions, DaoGenConfig config) {
        pw.println("    /** Drop all tables */");
        pw.println("    public void dropAllTable() {");
        pw.println("        String[] sqls = new String[" + tableDefinitions.size() + "];");
        int count = 0;
        for (TableDefinition tableDefinition : tableDefinitions) {
            String sql = "DROP TABLE IF EXISTS " + tableDefinition.getTable_name() + " CASCADE;";
            pw.println("        sqls[" + count + "] = \"" + sql + "\";");
            count++;
        }
        pw.println("        for (String sql : sqls) {");
        pw.println("            LOG.debug(sql);");
        pw.println("            executeUpdate(sql);");
        pw.println("        }");
        pw.println("    }");
    }

}
