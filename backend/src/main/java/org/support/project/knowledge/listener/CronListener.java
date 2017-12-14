package org.support.project.knowledge.listener;

import java.io.File;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.support.project.common.bat.JavaJob;
import org.support.project.common.bat.JobResult;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.bat.FileParseBat;
import org.support.project.knowledge.bat.KnowledgeFileClearBat;
import org.support.project.knowledge.bat.MailReadBat;
import org.support.project.knowledge.bat.MailSendBat;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.bat.WebhookBat;
import org.support.project.knowledge.config.AppConfig;

public class CronListener implements ServletContextListener {

    private static final Log LOG = LogFactory.getLog(CronListener.class);

    private ScheduledThreadPoolExecutor service;
    private ScheduledFuture<?> fileClearfuture;
    private ScheduledFuture<?> parsefuture;
    private ScheduledFuture<?> mailfuture;
    private ScheduledFuture<?> webhookfuture;
    private ScheduledFuture<?> notifyfuture;
    private ScheduledFuture<?> mailHookfuture;

    
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        String logsPath = AppConfig.get().getLogsPath();
        File logDir = new File(logsPath);
        AppConfig.get();
        String envValue = System.getenv(AppConfig.getEnvKey());

        service = new ScheduledThreadPoolExecutor(1);
        fileClearfuture = service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (AppConfig.get().isMaintenanceMode()) {
                    LOG.debug("This service is maintenance mode now.");
                    return;
                }
                LOG.trace("called. [fileClearfuture]");
                // Java を別のVMで実行（添付ファイルの定期的なクリア）
                JavaJob job = new JavaJob();
                job.setCurrentDirectory(logDir);
                job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
                job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
                job.setMainClass(KnowledgeFileClearBat.class.getName());
                job.setXmx(256);
                if (StringUtils.isNotEmpty(envValue)) {
                    job.addEnvironment(AppConfig.getEnvKey(), envValue);
                }
                try {
                    JobResult result = job.execute();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("finish KnowledgeFileClearBat [result]" + result.getResultCode());
                    }
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(result.getStdout());
                    }
                } catch (Exception e) {
                    LOG.error("Faild clear files.", e);
                }
            }
        }, 1, 60, TimeUnit.MINUTES); // 1分後に実行、60分毎に実行

        parsefuture = service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (AppConfig.get().isMaintenanceMode()) {
                    LOG.debug("This service is maintenance mode now.");
                    return;
                }
                LOG.trace("called. [parsefuture]");
                // 添付ファイルの中身を抽出し検索可能にする
                JavaJob job = new JavaJob();
                job.setCurrentDirectory(logDir);
                job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
                job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
                job.setMainClass(FileParseBat.class.getName());
                job.setXmx(1024);
                if (StringUtils.isNotEmpty(envValue)) {
                    job.addEnvironment(AppConfig.getEnvKey(), envValue);
                }
                try {
                    JobResult result = job.execute();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("finish FileParseBat [result]" + result.getResultCode());
                    }
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(result.getStdout());
                    }
                } catch (Exception e) {
                    LOG.error("Faild parse.", e);
                }
            }
        }, 5, 5, TimeUnit.MINUTES); // 5分毎に実行

        mailfuture = service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (AppConfig.get().isMaintenanceMode()) {
                    LOG.debug("This service is maintenance mode now.");
                    return;
                }
                LOG.trace("called. [mailfuture]");
                // メール送信
                JavaJob job = new JavaJob();
                job.setCurrentDirectory(logDir);
                job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
                job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
                job.setMainClass(MailSendBat.class.getName());
                job.setXmx(256);
                if (StringUtils.isNotEmpty(envValue)) {
                    job.addEnvironment(AppConfig.getEnvKey(), envValue);
                }
                try {
                    JobResult result = job.execute();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("finish MailSendBat [result]" + result.getResultCode());
                    }
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(result.getStdout());
                    }
                } catch (Exception e) {
                    LOG.error("Failed send mail.", e);
                }
            }
        }, 150, 180, TimeUnit.SECONDS); // 180秒毎に実行

        webhookfuture = service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (AppConfig.get().isMaintenanceMode()) {
                    LOG.debug("This service is maintenance mode now.");
                    return;
                }
                LOG.trace("called. [webhookfuture]");
                // Webhook
                JavaJob job = new JavaJob();
                job.setCurrentDirectory(logDir);
                job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
                job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
                job.setMainClass(WebhookBat.class.getName());
                job.setXmx(256);
                if (StringUtils.isNotEmpty(envValue)) {
                    job.addEnvironment(AppConfig.getEnvKey(), envValue);
                }
                try {
                    JobResult result = job.execute();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("finish WebhookBat [result]" + result.getResultCode());
                    }
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(result.getStdout());
                    }
                } catch (Exception e) {
                    LOG.error("Failed send webhooks.", e);
                }
            }
        }, 180, 180, TimeUnit.SECONDS); // 180秒毎に実行

        mailHookfuture = service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (AppConfig.get().isMaintenanceMode()) {
                    LOG.debug("This service is maintenance mode now.");
                    return;
                }
                LOG.trace("called. [mailHookfuture]");
                // メールからの投稿用のメールの読み込み
                JavaJob job = new JavaJob();
                job.setCurrentDirectory(logDir);
                job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
                job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
                job.setMainClass(MailReadBat.class.getName());
                job.setXmx(256);
                if (StringUtils.isNotEmpty(envValue)) {
                    job.addEnvironment(AppConfig.getEnvKey(), envValue);
                }
                try {
                    JobResult result = job.execute();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("finish MailReadBat [result]" + result.getResultCode());
                    }
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(result.getStdout());
                    }
                } catch (Exception e) {
                    LOG.error("Failed to MailHook", e);
                }
            }
        }, 110, 180, TimeUnit.SECONDS); // 180秒毎に実行

        notifyfuture = service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (AppConfig.get().isMaintenanceMode()) {
                    LOG.debug("This service is maintenance mode now.");
                    return;
                }
                LOG.trace("called. [notifyfuture]");
                // Java を別のVMで実行（添付ファイルの中身を抽出し検索可能にする）
                JavaJob job = new JavaJob();
                job.setCurrentDirectory(logDir);
                job.addjarDir(new File(sce.getServletContext().getRealPath("/WEB-INF/lib")));
                job.addClassPathDir(new File(sce.getServletContext().getRealPath("/WEB-INF/classes")));
                job.setMainClass(NotifyMailBat.class.getName());
                if (StringUtils.isNotEmpty(envValue)) {
                    job.addEnvironment(AppConfig.getEnvKey(), envValue);
                }
                try {
                    JobResult result = job.execute();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("finish NotifyMailBat [result]" + result.getResultCode());
                    }
                    if (LOG.isTraceEnabled()) {
                        LOG.trace(result.getStdout());
                    }
                } catch (Exception e) {
                    LOG.error("Failed to Notify", e);
                }
            }
        }, 90, 180, TimeUnit.SECONDS); // 180秒毎に実行
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        LOG.info("finish batch processes.");
        try {
            fileClearfuture.cancel(true);
            fileClearfuture.get();
        } catch (Exception e) {
            LOG.debug("An error has occurred in the end processing of the batch", e); // 基本は無視でOK
        }
        try {
            parsefuture.cancel(true);
            parsefuture.get();
        } catch (Exception e) {
            LOG.debug("An error has occurred in the end processing of the batch", e); // 基本は無視でOK
        }
        try {
            mailfuture.cancel(true);
            mailfuture.get();
        } catch (Exception e) {
            LOG.debug("An error has occurred in the end processing of the batch", e); // 基本は無視でOK
        }
        try {
            webhookfuture.cancel(true);
            webhookfuture.get();
        } catch (Exception e) {
            LOG.debug("An error has occurred in the end processing of the batch", e); // 基本は無視でOK
        }
        try {
            mailHookfuture.cancel(true);
            mailHookfuture.get();
        } catch (Exception e) {
            LOG.debug("An error has occurred in the end processing of the batch", e); // 基本は無視でOK
        }
        try {
            notifyfuture.cancel(true);
            service.shutdown();
            notifyfuture.get();
        } catch (Exception e) {
            LOG.debug("An error has occurred in the end processing of the batch", e); // 基本は無視でOK
        }
    }
}
