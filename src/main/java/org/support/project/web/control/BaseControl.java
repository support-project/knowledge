package org.support.project.web.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.Resources;
import org.support.project.common.log.LogLevel;
import org.support.project.common.util.HtmlUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.common.HttpUtil;

/**
 * Controlにメッセージ処理などの基本処理を追加したクラス
 * @author Koda
 */
@DI(instance = Instance.Prototype)
public abstract class BaseControl extends Control {
    /** INFO レベルのメッセージをリクエストスコープにセットする際のキー */
    public static final String MSG_INFO = "NOTIFY_MSG_INFO";
    /** SUCCESS レベルのメッセージをリクエストスコープにセットする際のキー */
    public static final String MSG_SUCCESS = "NOTIFY_MSG_SUCCESS";
    /** WARN レベルのメッセージをリクエストスコープにセットする際のキー */
    public static final String MSG_WARN = "NOTIFY_MSG_WARN";
    /** ERROR レベルのメッセージをリクエストスコープにセットする際のキー */
    public static final String MSG_ERROR = "NOTIFY_MSG_ERROR";

    /** INFO レベルのメッセージを格納するリスト */
    private List<String> infos = null;
    /** SUCCESS レベルのメッセージを格納するリスト */
    private List<String> successes = null;
    /** WARN レベルのメッセージを格納するリスト */
    private List<String> warns = null;
    /** ERROR レベルのメッセージを格納するリスト */
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
    }
    
    /**
     * INFO レベルのメッセージを追加
     * @param key key
     * @param params params
     */
    protected void addMsgInfo(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        infos.add(HtmlUtils.escapeHTML(msg));
    }

    /**
     * SUCCESS レベルのメッセージを追加
     * @param key key
     * @param params params
     */
    protected void addMsgSuccess(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        successes.add(HtmlUtils.escapeHTML(msg));
    }

    /**
     * WARN レベルのメッセージを追加
     * @param key key
     * @param params params
     */
    protected void addMsgWarn(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        warns.add(HtmlUtils.escapeHTML(msg));
    }

    /**
     * ERROR レベルのメッセージを追加
     * @param key key
     * @param params params
     */
    protected void addMsgError(String key, String... params) {
        Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
        String msg = resources.getResource(key, params);
        errors.add(HtmlUtils.escapeHTML(msg));
    }

    /**
     * ValidateError をメッセージにセット
     * @param errors errors
     */
    protected void setErrors(List<ValidateError> errors) {
        if (errors != null) {
            for (ValidateError validateError : errors) {
                if (validateError.getLevel().intValue() == LogLevel.ERROR.getValue()) {
                    addMsgError(validateError.getMsg(HttpUtil.getLocale(getRequest())));
                } else {
                    addMsgWarn(validateError.getMsg(HttpUtil.getLocale(getRequest())));
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.support.project.web.control.Control#copy(org.support.project.web.control.Control)
     */
    @Override
    protected void copy(org.support.project.web.control.Control control) {
        super.copy(control);
        if (control instanceof BaseControl) {
            BaseControl c = (BaseControl) control;
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
    
}
