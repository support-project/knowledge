package org.support.project.ormapping.config;

import java.io.InputStream;

import org.junit.Test;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.di.Container;
import org.support.project.ormapping.config.Connection;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.ormapping.config.ConnectionConfigLoader;
import org.support.project.ormapping.config.ORMappingParameter;

public class ConnectionConfigLoaderTest {

    @Test
    public void test() throws Exception {
        ConnectionConfigLoader loader = Container.getComp(Connection.ConfigType.XML.name(), ConnectionConfigLoader.class);

        ConnectionConfig config = loader.load(ORMappingParameter.CONNECTION_SETTING);

        InputStream inputStream = null;
        try {
            inputStream = getClass().getResourceAsStream("/connection.xml");
            ConnectionConfig config2 = SerializeUtils.bytesToObject(inputStream, ConnectionConfig.class);

            // assertTrue(PropertyUtil.equalsProperty(config, config2));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
