package org.support.project.knowledge.bat;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.logic.MailhookLogic;

public class MailReadBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    public static void main(String[] args) {
        try {
            initLogName("MailReadBat.log");
            configInit(ClassUtils.getShortClassName(MailReadBat.class));
            
            MailReadBat bat = new MailReadBat();
            bat.dbInit();
            bat.start();
            
            finishInfo();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    private void start() {
        MailhookLogic.get().postFromMail();
    }

}
