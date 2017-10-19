package org.support.project.web.websocket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.support.project.common.bat.AsyncJavaJob;
import org.support.project.common.bat.BatListener;
import org.support.project.common.bat.ConsoleListener;
import org.support.project.common.bat.JobResult;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.config.AppConfig;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

public class JobOnWebsocket {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(CallBatchEndpoint.class);
    private List<Session> sessions = new ArrayList<>();
    private String sendPlefix = "[SEND]";
    private Thread thread;
    private Listener listener = null;
    public interface Listener {
        void finishJob(JobResult result, Class<?> batch, List<Session> sessions);
    }
    public void addSession(Session session) {
        this.sessions.add(session);
    }
    public void removeSession(Session session) {
        this.sessions.remove(session);
    }
    
    private void sendText(String message) {
        MessageResult result = new MessageResult();
        if (StringUtils.isNotEmpty(sendPlefix)) {
            if (message.startsWith(sendPlefix)) {
                result.setMessage(message.substring(sendPlefix.length()));
            } else {
            }
        } else {
            result.setMessage(message);
        }
        if (StringUtils.isEmpty(result.getMessage())) {
            return;
        }
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(JSON.encode(result));
            } catch (IllegalStateException e) {
                LOG.warn("websocket message send error. " + e.getClass().getSimpleName() + ". but this batch is continue...");
            } catch (JSONException | IOException e) {
                LOG.warn("websocket message send error", e);
            }
        }
    }

    protected void execute(Class<?> batch) {
        LOG.info("start batch. " + batch.getSimpleName());
        // バッチプログラム実行
        AppConfig appConfig = AppConfig.get();
        String envValue = System.getenv(AppConfig.getEnvKey());
        AsyncJavaJob job = new AsyncJavaJob();
        job.addjarDir(new File(appConfig.getWebRealPath().concat("/WEB-INF/lib/")));
        job.addClassPathDir(new File(appConfig.getWebRealPath().concat("/WEB-INF/classes/")));
        job.setMainClass(batch.getName());
        if (StringUtils.isNotEmpty(envValue)) {
            job.addEnvironment(AppConfig.getEnvKey(), envValue);
        }
        job.setXmx(256);
        job.setConsoleListener(new ConsoleListener() {
            @Override
            public void write(String message) {
                LOG.info(message);
                sendText(message);
            }
        });
        
        job.addListener(new BatListener() {
            @Override
            public void finish(JobResult result) {
                LOG.info("finish batch. " + batch.getSimpleName());
                sendText("Processing has been completed. [status]" + result.getResultCode());
                if (listener != null) {
                    listener.finishJob(result, batch, sessions);
                }
            }
        });
        
        thread = new Thread(job);
        thread.start();
    }
    /**
     * @return the sendPlefix
     */
    public String getSendPlefix() {
        return sendPlefix;
    }
    /**
     * @param sendPlefix the sendPlefix to set
     */
    public void setSendPlefix(String sendPlefix) {
        this.sendPlefix = sendPlefix;
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
