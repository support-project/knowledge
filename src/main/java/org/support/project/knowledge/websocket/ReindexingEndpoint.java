package org.support.project.knowledge.websocket;

import java.io.File;
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.support.project.common.bat.AsyncJavaJob;
import org.support.project.common.bat.BatListener;
import org.support.project.common.bat.ConsoleListener;
import org.support.project.common.bat.JobResult;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.bat.ReIndexingBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;

@ServerEndpoint(value = "/reindexing", configurator=NotifyEndpointConfigurator.class)
public class ReindexingEndpoint {
	/** ログ */
	private static Log LOG = LogFactory.getLog(DataTransferEndpoint.class);
	
	private Thread thread;
	
	@OnOpen
	public void onOpen(Session session) throws IOException {
		 if (session.getUserProperties().containsKey(NotifyEndpointConfigurator.LOCALE_KEY)) {
			LoginedUser loginuser = (LoginedUser) session.getUserProperties().get(NotifyEndpointConfigurator.LOGIN_USER_KEY);
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
						if (message.startsWith("[SEND]")) {
							MessageResult result = new MessageResult();
							result.setMessage(message.substring("[SEND]".length()));
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
						session.close();
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
