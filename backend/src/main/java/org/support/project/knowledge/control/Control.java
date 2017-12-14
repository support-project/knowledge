package org.support.project.knowledge.control;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.config.Resources;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.log.LogLevel;
import org.support.project.common.util.HtmlUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.UserConfig;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.vo.UserConfigs;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.logic.DateConvertLogic;
import org.support.project.web.logic.NotificationLogic;
import org.support.project.web.logic.SanitizingLogic;
import org.support.project.web.util.JspUtil;

@DI(instance = Instance.Prototype)
public abstract class Control extends org.support.project.web.control.Control {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(Control.class);

    public static final String MSG_INFO = "NOTIFY_MSG_INFO";
    public static final String MSG_SUCCESS = "NOTIFY_MSG_SUCCESS";
    public static final String MSG_WARN = "NOTIFY_MSG_WARN";
    public static final String MSG_ERROR = "NOTIFY_MSG_ERROR";
    public static final String NOTIFY_UNREAD_COUNT = "NOTIFY_UNREAD_COUNT";

    private List<String> infos = null;
    private List<String> successes = null;
    private List<String> warns = null;
    private List<String> errors = null;

    @Override
    public void setRequest(HttpServletRequest request) {
        super.setRequest(request);
        infos = new ArrayList<String>();
        successes = new ArrayList<String>();
        warns = new ArrayList<String>();
        errors = new ArrayList<String>();

        request.setAttribute(MSG_INFO, infos);
        request.setAttribute(MSG_SUCCESS, successes);
        request.setAttribute(MSG_WARN, warns);
        request.setAttribute(MSG_ERROR, errors);
        
        // 通知の件数を取得
        if (getLoginedUser() != null) {
            try {
                Integer count = NotificationLogic.get().countUnRead(getLoginUserId());
                if (count == null) {
                    count = 0;
                }
                request.setAttribute(NOTIFY_UNREAD_COUNT, count);
            } catch (Exception e) {
                LOG.warn("Error on get user notification count. " + e.getClass().getSimpleName());
            }
        }
    }

    protected String getResource(String key) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        return resources.getResource(key);
    }

    protected String getResource(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        return resources.getResource(key, params);
    }

    protected void addMsgInfo(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        infos.add(HtmlUtils.escapeHTML(msg));
    }

    protected void addMsgSuccess(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        successes.add(HtmlUtils.escapeHTML(msg));
    }

    protected void addMsgWarn(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        warns.add(HtmlUtils.escapeHTML(msg));
    }

    protected void addMsgError(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        errors.add(HtmlUtils.escapeHTML(msg));
    }

    protected void setResult(String successMsg, List<ValidateError> errors, String... params) {
        if (errors == null || errors.isEmpty()) {
            addMsgSuccess(successMsg, params);
        } else {
            for (ValidateError validateError : errors) {
                if (validateError.getLevel().intValue() == LogLevel.ERROR.getValue()) {
                    addMsgError(validateError.getMsg(HttpUtil.getLocale(getRequest())));
                } else {
                    addMsgWarn(validateError.getMsg(HttpUtil.getLocale(getRequest())));
                }
            }
        }
    }

    public static String sanitize(String str) throws ParseException {
        return SanitizingLogic.get().sanitize(str);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.control.Control#copy(org.support.project.web.control.Control)
     */
    @Override
    protected void copy(org.support.project.web.control.Control control) {
        super.copy(control);
        if (control instanceof Control) {
            Control c = (Control) control;
            for (String string : infos) {
                c.addMsgInfo(string);
            }
            for (String string : successes) {
                c.addMsgSuccess(string);
            }
            for (String string : warns) {
                c.addMsgWarn(string);
            }
            for (String string : errors) {
                c.addMsgError(string);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.support.project.web.control.Control#forward(java.lang.String)
     */
    @Override
    protected ForwardBoundary forward(String path) {
        // 画面表示を行う前に、全ての画面の共通処理を追記

        // デスクトップ通知するかどうか(ログインしているユーザのみ）
        if (getLoginUserId() > 0) {
            NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
            NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(getLoginUserId());
            if (notifyConfigsEntity != null) {
                if (flagCheck(notifyConfigsEntity.getNotifyDesktop())) {
                    // デスクトップ通知がON

                    if (flagCheck(notifyConfigsEntity.getMyItemComment()) || flagCheck(notifyConfigsEntity.getMyItemLike())
                            || flagCheck(notifyConfigsEntity.getMyItemStock()) || flagCheck(notifyConfigsEntity.getStockItemSave())
                            || flagCheck(notifyConfigsEntity.getStokeItemComment()) || flagCheck(notifyConfigsEntity.getToItemComment())
                            || flagCheck(notifyConfigsEntity.getToItemSave())) {
                        if (LOG.isTraceEnabled()) {
                            LOG.info("Notify On to [" + getLoginUserId() + "]");
                        }
                        setAttribute("desktopNotify", true);
                    }
                }
            }
        }
        return super.forward(path);
    }

    private boolean flagCheck(Integer check) {
        if (check == null) {
            return false;
        }
        if (check.intValue() == INT_FLAG.ON.getValue()) {
            return true;
        }
        return false;
    }
    
    protected UserConfigs getUserConfigs() {
        UserConfigs userConfigs =  (UserConfigs) getRequest().getAttribute(UserConfig.REQUEST_USER_CONFIG_KEY);
        if (userConfigs == null) {
            userConfigs = new UserConfigs();
        }
        LoginedUser login = getLoginedUser();
        if (login != null) {
            userConfigs.setLocale(login.getLocale());
        } else {
            userConfigs.setLocale(getLocale());
        }
        
        if (userConfigs.getTimezone().equals("UTC")) {
            String offset = HttpUtil.getCookie(getRequest(), JspUtil.TIME_ZONE_OFFSET);
            if (StringUtils.isInteger(offset)) {
                int off = Integer.parseInt(offset);
                userConfigs.setTimezoneOffset(off);
                TimeZone zone = DateConvertLogic.get().getTimezone(getLocale(), off);
                userConfigs.setTimezone(zone.getDisplayName());
            }
        }
        return userConfigs;
    }

}
