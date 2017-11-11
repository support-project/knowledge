package org.support.project.knowledge.bat;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.logic.WebhookLogic;

/**
 * Webhookの送信処理は、時間がかかるため、バッチ処理の中で処理する
 *
 * @author nagodon
 */
public class WebhookBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(WebhookBat.class);


    public static void main(String[] args) throws Exception {
        try {
            initLogName("WebhookBat.log");
            configInit(ClassUtils.getShortClassName(WebhookBat.class));
    
            WebhookBat bat = new WebhookBat();
            bat.dbInit();
            bat.start();
    
            finishInfo();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }
    private void start() {
        WebhookLogic.get().startNotifyWebhook();
    }


}