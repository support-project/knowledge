package org.support.project.knowledge.bat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;

/**
 * メールの送信処理は、時間がかかるため、バッチ処理の中で処理する
 * 
 * @author Koda
 *
 */
public class MailSendBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailSendBat.class);

    /** メールの状態：未送信（送信待ち） */
    public static final int MAIL_STATUS_UNSENT = 0;
    /** メールの状態：送信済 */
    public static final int MAIL_STATUS_SENDED = 10;
    /** メールの状態：なんらかのエラーが発生した */
    public static final int MAIL_STATUS_ERROR = -1;
    /** メールの状態：アドレスのフォーマットエラー */
    public static final int MAIL_STATUS_FORMAT_ERROR = -2;

    public static final String MAIL_FORMAT = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*"
            + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)*$";

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
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            return;
        }

        MailsDao dao = MailsDao.get();
        List<MailsEntity> entities = dao.selectOnStatus(MAIL_STATUS_UNSENT);
        int count = 0;
        for (MailsEntity mailsEntity : entities) {
            if (mailsEntity.getToAddress().matches(MAIL_FORMAT)) {
                try {
                    MailLogic.get().mailSend(mailConfigsEntity, mailsEntity);
                    // ステータス更新
                    // mailsEntity.setStatus(MAIL_STATUS_SENDED);
                    // MailsDao.get().save(mailsEntity);
                    // 送信処理が終われば、物理削除
                    dao.physicalDelete(mailsEntity);
                } catch (Exception e) {
                    LOG.error("mail send error", e);
                    //TODO メール送信失敗、二度と送らないようにする（リトライする？）
                    // 未送信にしておけば、再送できるが永遠に再送してしまう
                    // カウント制御するべきか？
                    mailsEntity.setStatus(MAIL_STATUS_ERROR);
                    MailsDao.get().save(mailsEntity);
                }
            } else {
                mailsEntity.setStatus(MAIL_STATUS_FORMAT_ERROR);
                dao.save(mailsEntity);
            }
            count++;
        }
        LOG.info("MAIL sended. count: " + count);
    }

}
