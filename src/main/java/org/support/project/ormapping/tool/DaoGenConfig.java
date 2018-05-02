package org.support.project.ormapping.tool;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.common.NameConvertor;
import org.support.project.ormapping.entity.ColumnDefinition;
import org.support.project.ormapping.entity.TableDefinition;
import org.support.project.ormapping.tool.config.ORmappingDaoGenConfig;
import org.support.project.ormapping.tool.config.ORmappingEntityGenConfig;
import org.support.project.ormapping.tool.config.ORmappingToolConfig;
import org.support.project.ormapping.tool.impl.CreatorHelper;

public class DaoGenConfig {

    private CreatorHelper helper = new CreatorHelper();
    private NameConvertor nameConvertor = new NameConvertor();

    private String daoPackageName;
    private String daoSuffix;

    private String entityDir;
    private String entityPackageName;
    private String entitySuffix;

    private String commonInsertUserName;
    private String commonInsertDateTime;
    private String commonUpdateUserName;
    private String commonUpdateDateTime;
    private String[] commonIgnoreTables;
    private String commonUseridType;
    private String commonDeleteFlag;
    private String rowIdColumn;
    private String deleteFlagColumnType;

    private File daoDir;
    private File genDir;
    private File sqlsDir;

    private String daoClassName;
    private String genDaoClassName;

    private TableDefinition tableDefinition;

    private String entityClassName;
    private File daoFile;
    private File genDaoFile;
    private String genPackage;
    private File sqlDir;
    private String sqlPackagePath;

    private File sqlBaseDir;

    public void init(ORmappingToolConfig config) {
        ORmappingDaoGenConfig daoGenConfig = config.getDaoGenConfig();
        ORmappingEntityGenConfig entityGenConfig = config.getEntityGenConfig();
        daoPackageName = daoGenConfig.getDaoPackage();

        String dirName = daoGenConfig.getDaoOutDir();

        if (!dirName.endsWith("/")) {
            dirName = dirName + "/";
        }
        String daoDirName = dirName + "java/" + daoPackageName.replaceAll("\\.", "/");
        String sqlDirPath = dirName + "resources/" + daoPackageName.replaceAll("\\.", "/");

        // ディレクトリが存在しなければ作成
        daoDir = new File(daoDirName);
        if (!daoDir.exists()) {
            daoDir.mkdirs();
        }

        sqlBaseDir = new File(sqlDirPath);
        if (!sqlBaseDir.exists()) {
            sqlBaseDir.mkdirs();
        }

        genDir = new File(daoDir, "gen");
        if (!genDir.exists()) {
            genDir.mkdirs();
        }
        sqlsDir = new File(sqlBaseDir, "sql");
        if (!sqlsDir.exists()) {
            sqlsDir.mkdirs();
        }

        daoSuffix = daoGenConfig.getDaoSuffix();

        entityDir = entityGenConfig.getEntityOutDir();
        entityPackageName = entityGenConfig.getEntityPackage();
        entitySuffix = entityGenConfig.getEntitySuffix();

        commonInsertUserName = daoGenConfig.getInsertUserColumn();
        commonInsertDateTime = daoGenConfig.getInsertDatetimeColumn();
        commonUpdateUserName = daoGenConfig.getUpdateUserColumn();
        commonUpdateDateTime = daoGenConfig.getUpdateDatetimeColumn();
        rowIdColumn = daoGenConfig.getRowIdColumn();

        commonUseridType = daoGenConfig.getUserColumnType();
        if (StringUtils.isEmpty(commonUseridType) || commonUseridType.equals("dao.common.userid.type")) {
            commonUseridType = "String";
        }

        commonDeleteFlag = daoGenConfig.getDeleteFlagColumn();
        if (StringUtils.isEmpty(commonDeleteFlag) || commonDeleteFlag.equals("dao.common.delete.flag.column")) {
            commonDeleteFlag = null;
        }

        List<String> ignoreList = daoGenConfig.getIgnoreTables();
        String[] ignores = new String[0];
        if (ignoreList != null && !ignoreList.isEmpty()) {
            ignores = ignoreList.toArray(new String[0]);
        }
        this.commonIgnoreTables = ignores;

        this.deleteFlagColumnType = daoGenConfig.getDeleteFlagColumnType();
    }

    public void setTableDefinition(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;

        daoClassName = nameConvertor.tableNameToClassName(tableDefinition.getTable_name()).concat(daoSuffix);
        genDaoClassName = "Gen" + nameConvertor.tableNameToClassName(tableDefinition.getTable_name()).concat(daoSuffix);

        entityClassName = nameConvertor.tableNameToClassName(tableDefinition.getTable_name()).concat(entitySuffix);

        daoFile = new File(daoDir, daoClassName.concat(".java"));
        genDaoFile = new File(genDir, genDaoClassName.concat(".java"));

        // String daoPackage = daoPackageName;
        genPackage = daoPackageName.concat(".gen");

        sqlDir = new File(sqlsDir, daoClassName);
        if (!sqlDir.exists()) {
            sqlDir.mkdirs();
        }
        sqlPackagePath = "/" + daoPackageName.replaceAll("\\.", "/") + "/sql/" + daoClassName;
    }

    public Collection<ColumnDefinition> getColumns() {
        return tableDefinition.getColumns();
    }

    public Collection<ColumnDefinition> getPrimaryKeys() {
        return getPrimaryKeys(tableDefinition.getColumns());
    }

    public Collection<ColumnDefinition> getPrimaryKeys(List<ColumnDefinition> columnDefinitions) {
        TreeMap<Integer, ColumnDefinition> map = new TreeMap<>();
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            if (columnDefinition.isPrimary()) {
                map.put(columnDefinition.getPrimary_no(), columnDefinition);
            }
        }
        return map.values();
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public String getDaoSuffix() {
        return daoSuffix;
    }

    public String getEntityDir() {
        return entityDir;
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public String getEntitySuffix() {
        return entitySuffix;
    }

    public String getCommonInsertUserName() {
        return commonInsertUserName;
    }

    public String getCommonInsertDateTime() {
        return commonInsertDateTime;
    }

    public String getCommonUpdateUserName() {
        return commonUpdateUserName;
    }

    public String getCommonUpdateDateTime() {
        return commonUpdateDateTime;
    }

    public String[] getCommonIgnoreTables() {
        return commonIgnoreTables;
    }

    public String getCommonUseridType() {
        return commonUseridType;
    }

    public File getDaoDir() {
        return daoDir;
    }

    public File getGenDir() {
        return genDir;
    }

    public File getSqlsDir() {
        return sqlsDir;
    }

    public String getCommonDeleteFlag() {
        return commonDeleteFlag;
    }

    public CreatorHelper getHelper() {
        return helper;
    }

    public NameConvertor getNameConvertor() {
        return nameConvertor;
    }

    public String getDaoClassName() {
        return daoClassName;
    }

    public String getGenDaoClassName() {
        return genDaoClassName;
    }

    public TableDefinition getTableDefinition() {
        return tableDefinition;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public File getDaoFile() {
        return daoFile;
    }

    public File getGenDaoFile() {
        return genDaoFile;
    }

    public String getGenPackage() {
        return genPackage;
    }

    public File getSqlDir() {
        return sqlDir;
    }

    public String getSqlPackagePath() {
        return sqlPackagePath;
    }

    /**
     * @return rowIdColumn
     */
    public String getRowIdColumn() {
        return rowIdColumn;
    }

    /**
     * Get deleteFlagColumnType
     * 
     * @return the deleteFlagColumnType
     */
    public String getDeleteFlagColumnType() {
        return deleteFlagColumnType;
    }

    /**
     * Set deleteFlagColumnType
     * 
     * @param deleteFlagColumnType the deleteFlagColumnType to set
     */
    public void setDeleteFlagColumnType(String deleteFlagColumnType) {
        this.deleteFlagColumnType = deleteFlagColumnType;
    }

}
