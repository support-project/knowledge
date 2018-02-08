package org.support.project.ormapping.common;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.common.util.PropertyUtil;
import org.support.project.ormapping.config.ConnectionConfig;

public class ConnectionConfigTest {

    @Test
    public void test() throws Exception {
        try {
            ConnectionConfig config = new ConnectionConfig();
            config.setDriverClass("org.h2.Driver");
            config.setURL("jdbc:h2:tcp://localhost/./data/testDB");
            config.setUser("sa");
            config.setPassword("");
            config.setSchema("public");
            config.setMaxConn(5);
            config.setAutocommit(false);

            File file = new File("target/connection_test.xml");
            OutputStream outputStream = null;
            InputStream inputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                SerializeUtils.writeObject(config, outputStream);

                inputStream = new FileInputStream(file);
                ConnectionConfig config2 = SerializeUtils.bytesToObject(inputStream, ConnectionConfig.class);

                assertTrue(PropertyUtil.equalsProperty(config, config2));

                file.deleteOnExit();
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
