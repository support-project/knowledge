package org.support.project.knowledge.logic.notification;

import java.util.Locale;

import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.MessageResult;

public interface DesktopNotification {
    /**
     * デスクトップ通知するメッセージを取得
     * 
     * @param loginuser
     * @param locale
     * @return
     */
    MessageResult getMessage(AccessUser loginuser, Locale locale);
}
