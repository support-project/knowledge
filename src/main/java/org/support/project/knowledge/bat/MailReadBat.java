package org.support.project.knowledge.bat;

import org.apache.commons.lang.ClassUtils;
import org.support.project.knowledge.logic.MailhookLogic;

public class MailReadBat extends AbstractBat {
    public static void main(String[] args) {
        initLogName("MailReadBat.log");
        configInit(ClassUtils.getShortClassName(MailReadBat.class));
        
        MailReadBat bat = new MailReadBat();
        bat.dbInit();
        bat.start();
        
        finishInfo();
    }

    private void start() {
        MailhookLogic.get().postFromMail();
    }

}
