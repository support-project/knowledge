package org.support.project.knowledge.tool;

import org.h2.tools.Server;
import org.support.project.common.config.ConfigLoader;
import org.support.project.web.config.AppConfig;

public class DBStart {

    public static void main(String[] args) throws Exception {
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        String[] parms = { "-tcp", "-baseDir", appConfig.getDatabasePath() };

        Server server = Server.createTcpServer(parms);
        server.start();
        System.out.println("Database start...");

        int count = 0;
        while (true) {
            Thread.sleep(1000);
            count++;
            if (count > 300) {
                break;
            }
        }
    }

}
