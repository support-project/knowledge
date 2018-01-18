package org.support.project.knowledge.websocket;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.support.project.common.bat.AsyncJavaJob;
import org.support.project.common.bat.BatListener;
import org.support.project.common.bat.ConsoleListener;
import org.support.project.common.bat.JobResult;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.bat.ReIndexingBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.websocket.EndpointConfigurator;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

@ServerEndpoint(value = "/reindexing", configurator = EndpointConfigurator.class)
public class ReindexingEndpoint {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private Thread thread;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        if (session.getUserProperties().containsKey(EndpointConfigurator.LOCALE_KEY)) {
            AccessUser loginuser = (AccessUser) session.getUserProperties().get(EndpointConfigurator.LOGIN_USER_KEY);
            if (!loginuser.isAdmin()) {
                // 管理者以外はアクセス出来ない
                session.close();
                return;
            }
            // インデックス再作成
            LOG.info("websocket open: " + session.getId() + " : " + loginuser.getUserId());

            // バッチプログラム実行
            AppConfig appConfig = AppConfig.get();

            LOG.info(appConfig.getWebRealPath());

            AsyncJavaJob job = new AsyncJavaJob();
            job.addjarDir(new File(appConfig.getWebRealPath().concat("/WEB-INF/lib/")));
            job.addClassPathDir(new File(appConfig.getWebRealPath().concat("/WEB-INF/classes/")));
            job.setMainClass(ReIndexingBat.class.getName());
            job.setConsoleListener(new ConsoleListener() {
                @Override
                public void write(String message) {
                    LOG.info(message);
                    try {
                        if (message.indexOf("[SEND]") != -1) {
                            MessageResult result = new MessageResult();
                            result.setMessage(message.substring(message.indexOf("[SEND]") + "[SEND]".length()));
                            session.getBasicRemote().sendText(JSON.encode(result));
                        }
                    } catch (JSONException | IOException e) {
                        LOG.warn("websocket message send error", e);
                    }
                }
            });

            job.addListener(new BatListener() {
                @Override
                public void finish(JobResult result) {
                    MessageResult message = new MessageResult();
                    message.setMessage("Reindexing is ended. [status]" + result.getResultCode());
                    try {
                        session.getBasicRemote().sendText(JSON.encode(message));
                        //session.close();
                    } catch (JSONException | IOException e) {
                        LOG.warn("websocket message send error", e);
                    }
                }
            });

            thread = new Thread(job);
            thread.start();
        }
    }

    @OnClose
    public void onClose(Session session) {
    }

    @OnMessage
    public void onMessage(String text) throws JSONException, IOException {
    }

    @OnError
    public void onError(Throwable t) {
        LOG.warn("websocket on error." + t.getClass().getName() + " : " + t.getMessage());
        if (LOG.isDebugEnabled()) {
            LOG.warn("websocket error -> ", t);
        }
    }
}
