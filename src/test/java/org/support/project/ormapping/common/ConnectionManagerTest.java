package org.support.project.ormapping.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.ormapping.config.Connection.ConfigType;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.ormapping.entity.TableDefinition;

public class ConnectionManagerTest {
    /** ログ */
    private static Log logger = LogFactory.getLog(ConnectionManagerTest.class);

    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!H2DBServerLogic.get().isActive()) {
            H2DBServerLogic.get().start();
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testGetConnection() throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ConnectionManager.getInstance().addConnectionConfig(ORMappingParameter.DEFAULT_CONNECTION_NAME, ConfigType.XML,
                    ORMappingParameter.CONNECTION_SETTING);

            con = ConnectionManager.getInstance().getConnection();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            rs = databaseMetaData.getTables(null, ConnectionManager.getInstance().getSchema().toUpperCase(), "%", null);
            // rs = databaseMetaData.getTables(null, "%", "%", null);

            String driverClass = ConnectionManager.getInstance().getDriverClass();

            while (rs.next()) {
                TableDefinition definition = new TableDefinition();
                ResultSetLoader.load(rs, definition, driverClass);
                logger.info(definition.toString());
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        ConnectionManager.getInstance().release();
    }

}
