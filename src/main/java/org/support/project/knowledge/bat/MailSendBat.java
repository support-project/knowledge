package org.support.project.knowledge.bat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.web.dao.MailsDao;

/**
 * メールの送信処理は、時間がかかるため、バッチ処理の中で処理する
 * 
 * @author Koda
 *
 */
public class MailSendBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailSendBat.class);

    public static void main(String[] args) throws Exception {
        try {
            initLogName("MailSendBat.log");
            configInit(ClassUtils.getShortClassName(MailSendBat.class));
            
            MailSendBat bat = new MailSendBat();
            bat.dbInit();
            bat.start();
            
            finishInfo();
        } catch (Exception e) {
            LOG.error("any error", e);
            throw e;
        }
    }

    /**
     * メール送信処理の実行
     * 
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public void start() throws UnsupportedEncodingException, MessagingException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        MailLogic.get().startSendMails();
        int deletecount = MailsDao.get().physicalDeleteOnOldData();
        LOG.info("delete old mails. [count]" + deletecount);
    }

}
