package org.support.project.knowledge.logic.notification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractNotification implements Notification {
    
    /** date format */
    private static DateFormat getDayFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }
    /**
     * メール送信のIDを生成
     * @param string
     * @return
     */
    protected String idGen(String label) {
        StringBuilder builder = new StringBuilder();
        builder.append(label);
        builder.append("-");
        builder.append(getDayFormat().format(new Date()));
        builder.append("-");
        builder.append(UUID.randomUUID().toString());
        return builder.toString();
    }
    


}
