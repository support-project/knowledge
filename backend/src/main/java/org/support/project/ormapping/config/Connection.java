package org.support.project.ormapping.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Connection
 * 
 * @author Koda
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Connection {
    /**
     * Config type
     * @author Koda
     */
    enum ConfigType {
        /** type: XML */
        XML,
        /** type: Properties */
        Properties
    };

    /** 
     * コネクション設定の名前
     * @return name
     */
    String name() default ORMappingParameter.DEFAULT_CONNECTION_NAME;

    /** 
     * コネクション設定ファイルのタイプ
     * @return config type
     */
    ConfigType configType() default ConfigType.XML;

    /**
     * コネクション設定ファイル名(基本は設定の名前と同じ)
     * @return config file name
     */
    String configFileName() default ORMappingParameter.CONNECTION_SETTING;

}
