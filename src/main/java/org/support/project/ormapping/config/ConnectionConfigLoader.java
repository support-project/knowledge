package org.support.project.ormapping.config;

import java.io.InputStream;

import org.support.project.di.DI;
import org.support.project.ormapping.config.impl.ConnectionConfigPropertiesLoader;
import org.support.project.ormapping.config.impl.ConnectionConfigXmlLoader;
import org.support.project.ormapping.exception.ORMappingException;

/**
 * ConnectionConfigLoader
 * 
 * @author Koda
 *
 */
@DI(keys = { "XML", "Properties" }, impls = { ConnectionConfigXmlLoader.class, ConnectionConfigPropertiesLoader.class })
public interface ConnectionConfigLoader {
    /**
     * 設定ファイルからコネクションの設定を読み出す
     * 
     * @param path
     *            path
     * @return config
     * @throws ORMappingException
     *             ORMappingException
     */
    ConnectionConfig load(String path) throws ORMappingException;

    /**
     * 設定ファイルからコネクションの設定を読み出す
     * 
     * @param in
     *            input stream
     * @return config
     * @throws ORMappingException
     *             ORMappingException
     */
    ConnectionConfig load(InputStream in) throws ORMappingException;

}
