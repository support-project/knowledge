package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.vo.LogFile;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;

public class LogManageLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(LogManageLogic.class);

    public static LogManageLogic get() {
        return Container.getComp(LogManageLogic.class);
    }

    private File[] logFiles() {
        String logsPath = AppConfig.get().getLogsPath();
        File logDir = new File(logsPath);
        File[] logs = logDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().indexOf(".log") != -1;
            }
        });
        return logs;
    }

    public List<LogFile> getLogFiles() {
        File[] logs = logFiles();
        List<LogFile> list = new ArrayList<>();
        if (logs != null) {
            for (File file : logs) {
                LOG.trace(file);
                LogFile logFile = new LogFile();
                logFile.setFilename(FilenameUtils.getName(file.getName()));
                logFile.setSize(file.length() + " [byte]");
                logFile.setLastModified(new Date(file.lastModified()));

                list.add(logFile);
            }
        }
        return list;
    }

    public void clearLogFiles() {
        SystemConfigsEntity entity = SystemConfigsDao.get().selectOnKey(SystemConfig.LOG_DELETE_TERM, AppConfig.get().getSystemName());
        if (entity != null && StringUtils.isLong(entity.getConfigValue())) {
            long days = Long.parseLong(entity.getConfigValue());
            long term = 1000 * 60 * 60 * 24 * days;
            File[] logs = logFiles();

            Date now = DateUtils.now();
            if (logs != null) {
                for (File file : logs) {
                    if (now.getTime() - file.lastModified() > term) {
                        if (file.delete()) {
                            LOG.info("[File delete] " + FilenameUtils.getName(file.getName()));
                        } else {
                            LOG.warn("[File delete] Failed." + FilenameUtils.getName(file.getName()));
                        }
                    }
                }
            }
        }
    }

}
