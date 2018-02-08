package org.support.project.knowledge.control.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.LogManageLogic;
import org.support.project.knowledge.vo.LogFile;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class LoggingControl extends Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary index() {
        LOG.trace("index");
        LogManageLogic logManageLogic = LogManageLogic.get();
        List<LogFile> list = logManageLogic.getLogFiles();
        setAttribute("logs", list);

        SystemConfigsEntity entity = SystemConfigsDao.get().selectOnKey(SystemConfig.LOG_DELETE_TERM, AppConfig.get().getSystemName());
        if (entity != null) {
            setAttribute("status", getResource("label.enable"));
            setAttribute("days", entity.getConfigValue());
        } else {
            setAttribute("status", getResource("label.disable"));
        }
        return forward("index.jsp");
    }

    @Post(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary delete_config() {
        LOG.trace("delete_config");
        String control = getAttributeByString("control");
        String days = getAttributeByString("days");

        if ("enable".equals(control)) {
            if (StringUtils.isEmpty(days)) {
                addMsgWarn("errors.required", getResource("knowledge.admin.logging.days"));
                return index();
            }
            if (!StringUtils.isInteger(days)) {
                addMsgWarn("errors.integer", getResource("knowledge.admin.logging.days"));
                return index();
            }

            SystemConfigsEntity entity = new SystemConfigsEntity(SystemConfig.LOG_DELETE_TERM, AppConfig.get().getSystemName());
            entity.setConfigValue(days);
            SystemConfigsDao.get().save(entity);
        } else {
            SystemConfigsEntity entity = new SystemConfigsEntity(SystemConfig.LOG_DELETE_TERM, AppConfig.get().getSystemName());
            SystemConfigsDao.get().physicalDelete(entity);
        }

        addMsgSuccess("message.success.save");

        return index();
    }

    @Get(subscribeToken = "admin")
    @Auth(roles = "admin")
    public Boundary download() throws FileNotFoundException, InvalidParamException {
        LOG.trace("download");
        String fileName = getPathString();
        String logsPath = AppConfig.get().getLogsPath();
        File logDir = new File(logsPath);
        File logFile = new File(logDir, fileName);
        if (!logDir.exists()) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "");
        }
        return download(fileName, new FileInputStream(logFile), logFile.length());
    }

}
