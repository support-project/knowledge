package org.support.project.ormapping.config;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;

/**
 * コネクションの設定
 * 
 * @author koda
 */
@Serialize(value = SerializerValue.Jaxb)
@XmlRootElement
public class ConnectionConfig implements Serializable {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;

    /** コネクションの名前 */
    private String name;

    /** ドライバークラス名 */
    private String driverClass;
    /** JDBC接続URL */
    private String URL;
    /** ユーザID */
    private String user;
    /** パスワード */
    private String password;
    /** スキーマn */
    private String schema;
    /** コネクション最大数 */
    private int maxConn;
    /** オートコミット */
    private boolean autocommit;

    /**
     * URLの中の予約文字を変更する
     */
    public void convURL() {
        if (URL.indexOf("{user.home}") != -1) {
            String userHome = System.getProperty("user.home");
            URL = URL.replace("{user.home}", userHome);
        }
        if (URL.indexOf("\\") != -1) {
            URL = URL.replaceAll("\\\\", "/");
        }
        this.setURL(URL);
    }

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
     * @return driverClass
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @param driverClass
     *            セットする driverClass
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * @return uRL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @param uRL
     *            セットする uRL
     */
    public void setURL(String uRL) {
        URL = uRL;
    }

    /**
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     *            セットする user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            セットする password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema
     *            セットする schema
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @return maxConn
     */
    public int getMaxConn() {
        return maxConn;
    }

    /**
     * @param maxConn
     *            セットする maxConn
     */
    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    /**
     * @return autocommit
     */
    public boolean isAutocommit() {
        return autocommit;
    }

    /**
     * @param autocommit
     *            セットする autocommit
     */
    public void setAutocommit(boolean autocommit) {
        this.autocommit = autocommit;
    }

    /**
     * @return serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConnectionConfig [name=").append(name).append(", driverClass=").append(driverClass).append(", URL=").append(URL)
                .append(", user=").append(user).append(", password=").append(password).append(", schema=").append(schema).append(", maxConn=")
                .append(maxConn).append(", autocommit=").append(autocommit).append("]");
        return builder.toString();
    }

}
