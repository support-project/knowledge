package org.support.project.ormapping.tool.config;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;
import org.support.project.ormapping.config.ConnectionConfig;

/**
 * ORMapping のツール設定クラス
 * 
 * @author Koda
 */
@Serialize(value = SerializerValue.Jaxb)
@XmlRootElement
public class ORmappingToolConfig implements Serializable {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = -8544696214523538804L;

    /** コネクションの設定 */
    private ConnectionConfig connectionConfig;
    /** エンティティ生成の設定情報 */
    private ORmappingEntityGenConfig entityGenConfig;
    /** Dao生成の設定情報 */
    private ORmappingDaoGenConfig daoGenConfig;
    /** DBの初期化設定 */
    private ORMappingDatabaseInitializeConfig initializeConfig;

    /**
     * @return initializeConfig
     */
    public ORMappingDatabaseInitializeConfig getInitializeConfig() {
        return initializeConfig;
    }

    /**
     * @param initializeConfig セットする initializeConfig
     */
    public void setInitializeConfig(ORMappingDatabaseInitializeConfig initializeConfig) {
        this.initializeConfig = initializeConfig;
    }

    /**
     * @return daoGenConfig
     */
    public ORmappingDaoGenConfig getDaoGenConfig() {
        return daoGenConfig;
    }

    /**
     * @param daoGenConfig セットする daoGenConfig
     */
    public void setDaoGenConfig(ORmappingDaoGenConfig daoGenConfig) {
        this.daoGenConfig = daoGenConfig;
    }

    /**
     * @return connectionConfig
     */
    public ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }

    /**
     * @param connectionConfig セットする connectionConfig
     */
    public void setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    /**
     * @return entityGenConfig
     */
    public ORmappingEntityGenConfig getEntityGenConfig() {
        return entityGenConfig;
    }

    /**
     * @param entityGenConfig セットする entityGenConfig
     */
    public void setEntityGenConfig(ORmappingEntityGenConfig entityGenConfig) {
        this.entityGenConfig = entityGenConfig;
    }

}
