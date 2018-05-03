package org.support.project.ormapping.tool.config;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;

/**
 * OR Mapping ToolのDao自動生成の設定
 * 
 * @author Koda
 */
@Serialize(value = SerializerValue.Jaxb)
@XmlRootElement
public class ORmappingDaoGenConfig implements Serializable {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;

    /** 出力するディレクトリ */
    private String daoOutDir;
    /** パッケージ名 */
    private String daoPackage;
    /** サフィックス */
    private String daoSuffix;

    /** 登録ユーザIDを登録するカラム名 */
    private String insertUserColumn;
    /** 登録日時を登録するカラム名 */
    private String insertDatetimeColumn;
    /** 更新ユーザIDを登録するカラム名 */
    private String updateUserColumn;
    /** 更新日時を登録するカラム名 */
    private String updateDatetimeColumn;
    /** 削除フラグを登録するカラム名(論理削除がある場合) */
    private String deleteFlagColumn;
    /** 行ID */
    private String rowIdColumn;

    private String deleteFlagColumnType;

    /** ユーザIDの型(String,Intなど) */
    private String userColumnType;

    /** Daoを生成しないテーブルの名称のリスト */
    private List<String> ignoreTables;

    /**
     * @return daoOutDir
     */
    public String getDaoOutDir() {
        return daoOutDir;
    }

    /**
     * @param daoOutDir セットする daoOutDir
     */
    public void setDaoOutDir(String daoOutDir) {
        this.daoOutDir = daoOutDir;
    }

    /**
     * @return daoPackage
     */
    public String getDaoPackage() {
        return daoPackage;
    }

    /**
     * @param daoPackage セットする daoPackage
     */
    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    /**
     * @return daoSuffix
     */
    public String getDaoSuffix() {
        return daoSuffix;
    }

    /**
     * @param daoSuffix セットする daoSuffix
     */
    public void setDaoSuffix(String daoSuffix) {
        this.daoSuffix = daoSuffix;
    }

    /**
     * @return insertUserColumn
     */
    public String getInsertUserColumn() {
        return insertUserColumn;
    }

    /**
     * @param insertUserColumn セットする insertUserColumn
     */
    public void setInsertUserColumn(String insertUserColumn) {
        this.insertUserColumn = insertUserColumn;
    }

    /**
     * @return insertDatetimeColumn
     */
    public String getInsertDatetimeColumn() {
        return insertDatetimeColumn;
    }

    /**
     * @param insertDatetimeColumn セットする insertDatetimeColumn
     */
    public void setInsertDatetimeColumn(String insertDatetimeColumn) {
        this.insertDatetimeColumn = insertDatetimeColumn;
    }

    /**
     * @return updateUserColumn
     */
    public String getUpdateUserColumn() {
        return updateUserColumn;
    }

    /**
     * @param updateUserColumn セットする updateUserColumn
     */
    public void setUpdateUserColumn(String updateUserColumn) {
        this.updateUserColumn = updateUserColumn;
    }

    /**
     * @return updateDatetimeColumn
     */
    public String getUpdateDatetimeColumn() {
        return updateDatetimeColumn;
    }

    /**
     * @param updateDatetimeColumn セットする updateDatetimeColumn
     */
    public void setUpdateDatetimeColumn(String updateDatetimeColumn) {
        this.updateDatetimeColumn = updateDatetimeColumn;
    }

    /**
     * @return deleteFlagColumn
     */
    public String getDeleteFlagColumn() {
        return deleteFlagColumn;
    }

    /**
     * @param deleteFlagColumn セットする deleteFlagColumn
     */
    public void setDeleteFlagColumn(String deleteFlagColumn) {
        this.deleteFlagColumn = deleteFlagColumn;
    }

    /**
     * @return userColumnType
     */
    public String getUserColumnType() {
        return userColumnType;
    }

    /**
     * @param userColumnType セットする userColumnType
     */
    public void setUserColumnType(String userColumnType) {
        this.userColumnType = userColumnType;
    }

    /**
     * @return ignoreTables
     */
    public List<String> getIgnoreTables() {
        return ignoreTables;
    }

    /**
     * @param ignoreTables セットする ignoreTables
     */
    public void setIgnoreTables(List<String> ignoreTables) {
        this.ignoreTables = ignoreTables;
    }

    /**
     * @return rowIdColumn
     */
    public String getRowIdColumn() {
        return rowIdColumn;
    }

    /**
     * @param rowIdColumn セットする rowIdColumn
     */
    public void setRowIdColumn(String rowIdColumn) {
        this.rowIdColumn = rowIdColumn;
    }

    /**
     * Get deleteFlagColumnType
     * @return the deleteFlagColumnType
     */
    public String getDeleteFlagColumnType() {
        return deleteFlagColumnType;
    }

    /**
     * Set deleteFlagColumnType
     * @param deleteFlagColumnType the deleteFlagColumnType to set
     */
    public void setDeleteFlagColumnType(String deleteFlagColumnType) {
        this.deleteFlagColumnType = deleteFlagColumnType;
    }

}
