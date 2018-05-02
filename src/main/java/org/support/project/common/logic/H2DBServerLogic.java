package org.support.project.common.logic;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.support.project.common.config.AppConfig;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.connection.ConnectionManager;

@DI(instance = Instance.Singleton)
public class H2DBServerLogic {
    private static Log LOG = LogFactory.getLog(H2DBServerLogic.class);

    private Server server = null;
    private boolean active;

    public static H2DBServerLogic get() {
        return Container.getComp(H2DBServerLogic.class);
    }

    public static void main(String[] args) throws Exception {
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        String[] parms = { "-tcp", "-baseDir", appConfig.getDatabasePath() };

        Server server = Server.createTcpServer(parms);
        server.start();

        int count = 0;
        while (true) {
            Thread.sleep(1000);
            count++;
            if (count > 300) {
                break;
            }
        }
    }

    public void start() {
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        String[] parms = { "-tcp", "-baseDir", appConfig.getDatabasePath() };

        try {
            server = Server.createTcpServer(parms);
            server.start();
            LOG.info("H2 Database server started.");
            active = true;
        } catch (SQLException e) {
            throw new SystemException(e);
        }
    }

    public void stop() {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.release();
        if (server != null) {
            server.stop();
            LOG.info("H2 Database server stoped.");
            server = null;
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

}
