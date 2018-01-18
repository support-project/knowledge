package org.support.project.knowledge.deploy.v1_5_0;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.Base64Utils;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.deploy.Migrate;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.ormapping.tool.dao.InitializeDao;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.ProxyConfigsDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.ProxyConfigsEntity;

public class Migrate_1_5_0 implements Migrate {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private static final String CIPHER_ALGORITHM = "AES";

    public static Migrate_1_5_0 get() {
        return org.support.project.di.Container.getComp(Migrate_1_5_0.class);
    }

    private String decrypt(String string, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(key.getBytes());
        Key secretKey = new SecretKeySpec(hash, CIPHER_ALGORITHM);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] bytes = Base64Utils.fromBase64(string);
        byte[] dec = cipher.doFinal(bytes);
        return new String(dec);
    }
    
    
    @Override
    public boolean doMigrate() throws Exception {
        InitializeDao initializeDao = InitializeDao.get();
        String[] sqlpaths = {
            "/org/support/project/knowledge/deploy/v1_5_0/migrate.sql",
        };
        initializeDao.initializeDatabase(sqlpaths);
        
        // 暗号化の仕組みを変更したため、旧暗号化の仕組みを使っていた場合、それを復元して、再度暗号化し直す
        File keyTxt = new File(AppConfig.get().getBasePath(), "key.txt");
        if (!keyTxt.exists()) {
            LOG.info("start data convert.");
            AppConfig.get().getKey();
            List<LdapConfigsEntity> ldaps = LdapConfigsDao.get().selectAll();
            for (LdapConfigsEntity entity : ldaps) {
                if (StringUtils.isNotEmpty(entity.getBindPassword())) {
                    String pass = decrypt(entity.getBindPassword(), entity.getSalt());
                    String salt = PasswordUtil.getSalt();
                    entity.setBindPassword(PasswordUtil.encrypt(pass, salt));
                    entity.setSalt(salt);
                    LdapConfigsDao.get().save(entity);
                }
            }
            
            List<MailConfigsEntity> mails = MailConfigsDao.get().selectAll();
            for (MailConfigsEntity entity : mails) {
                if (StringUtils.isNotEmpty(entity.getSmtpPassword())) {
                    String pass = decrypt(entity.getSmtpPassword(), entity.getSalt());
                    String salt = PasswordUtil.getSalt();
                    entity.setSmtpPassword(PasswordUtil.encrypt(pass, salt));
                    entity.setSalt(salt);
                    MailConfigsDao.get().save(entity);
                }
            }
            
            List<ProxyConfigsEntity> proxies = ProxyConfigsDao.get().selectAll();
            for (ProxyConfigsEntity entity : proxies) {
                if (StringUtils.isNotEmpty(entity.getProxyAuthPassword())) {
                    String pass = decrypt(entity.getProxyAuthPassword(), entity.getProxyAuthSalt());
                    String salt = PasswordUtil.getSalt();
                    entity.setProxyAuthPassword(PasswordUtil.encrypt(pass, salt));
                    entity.setProxyAuthSalt(salt);
                    ProxyConfigsDao.get().save(entity);
                }
            }
        }
        return true;
    }
}