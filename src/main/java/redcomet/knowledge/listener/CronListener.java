package redcomet.knowledge.listener;

import java.io.File;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import redcomet.common.bat.JavaJob;
import redcomet.common.bat.JobResult;
import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.knowledge.bat.FileParseBat;
import redcomet.knowledge.bat.KnowledgeFileClearBat;

public class CronListener implements ServletContextListener {
	
	private static Log LOG = LogFactory.getLog(CronListener.class);
	
	private ScheduledThreadPoolExecutor service;
	private ScheduledFuture<?> fileClearfuture;
	private ScheduledFuture<?> parsefuture;
	
	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		service = new ScheduledThreadPoolExecutor(1);
		fileClearfuture = service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				LOG.trace("called.");
				
				// Java を別のVMで実行（添付ファイルの定期的なクリア）
				JavaJob job = new JavaJob();
				job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
				job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
				job.setMainClass(KnowledgeFileClearBat.class.getName());
				try {
					JobResult result = job.execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("finish KnowledgeFileClearBat [result]" + result.getResultCode());
					}
					if (LOG.isTraceEnabled()) {
						LOG.trace(result.getStdout());
					}
				} catch (Exception e) {
					LOG.error("添付ファイルの定期的なクリアに失敗しました", e);
				}
			}
		}, 10, 60, TimeUnit.MINUTES); // 10分後に実行、60分毎に実行
		
		parsefuture = service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				LOG.trace("called.");
				// Java を別のVMで実行（添付ファイルの中身を抽出し検索可能にする）
				JavaJob job = new JavaJob();
				job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
				job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
				job.setMainClass(FileParseBat.class.getName());
				try {
					JobResult result = job.execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("finish FileParseBat [result]" + result.getResultCode());
					}
					if (LOG.isTraceEnabled()) {
						LOG.trace(result.getStdout());
					}
				} catch (Exception e) {
					LOG.error("添付ファイルのパースに失敗しました", e);
				}
			}
		}, 60, 30, TimeUnit.SECONDS); // 10分後に実行、3分毎に実行
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		fileClearfuture.cancel(true);
		parsefuture.cancel(true);
		service.shutdown();
	}

}
