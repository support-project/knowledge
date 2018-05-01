package org.support.project.ormapping.config;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;

@Serialize(value = SerializerValue.Jaxb)
@XmlRootElement
public class ConnectionManagerConfig implements Serializable {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;
    /** コネクション設定の名前 */
    private String name;
    /** コネクション設定ファイルのタイプ */
    private String configType;
    /** コネクション設定ファイル名(基本は設定の名前と同じ) */
    private String configFileName;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            セットする name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return configType
     */
    public String getConfigType() {
        return configType;
    }

    /**
     * @param configType
     *            セットする configType
     */
    public void setConfigType(String configType) {
        this.configType = configType;
    }

    /**
     * @return configFileName
     */
    public String getConfigFileName() {
        return configFileName;
    }

    /**
     * @param configFileName
     *            セットする configFileName
     */
    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

}
