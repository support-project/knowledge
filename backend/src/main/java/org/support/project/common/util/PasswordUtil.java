package org.support.project.common.util;

import java.lang.invoke.MethodHandles;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.support.project.common.config.AppConfig;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

/**
 * パスワードのエンコード／デコードに利用するユーティリティ
 * @author koda
 */
public class PasswordUtil {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /** ALGORITHM */
    private static final String CIPHER_ALGORITHM = "AES";
    // private static final String CIPHER_TRANSFORMATION = CIPHER_ALGORITHM + "/CBC/PKCS5Padding";
    
    /**
     * generat secret bytes key.
     * @param key key
     * @return secretKey
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static byte[] generatSecretKey(String key) throws NoSuchAlgorithmException {
        synchronized (CIPHER_ALGORITHM) {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            StringBuilder builder = new StringBuilder();
            // LOG.trace(AppConfig.get().getKey());
            builder.append(key).append(AppConfig.get().getKey());
            int hashIterations = 100;
            byte[] hash = digest.digest(builder.toString().getBytes());
            for (int i = 0; i < hashIterations; i++) {
                hash = digest.digest(hash);
            }
            // LOG.trace(hash);
            return hash;
        }
    }
    /**
     * hash on sha 256.
     * @param key key
     * @return hash
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static byte[] sha256(String key) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(key.getBytes());
        return hash;
    }
    /**
     * generat secret key.
     * @param secretKey secretKey
     * @return Key
     */
    private static Key makeKey(byte[] secretKey) {
        return new SecretKeySpec(secretKey, CIPHER_ALGORITHM);
    }

    /**
     * encrypt
     * 
     * @param string secretKey
     * @param key key
     * @return encrypted string
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws NoSuchPaddingException NoSuchPaddingException
     * @throws InvalidKeyException InvalidKeyException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws BadPaddingException BadPaddingException
     */
    public static final String encrypt(String string, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (string == null) {
            return null;
        }
        Key secretKey = makeKey(generatSecretKey(key));

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(string.getBytes());
        
        return Base64Utils.toBase64(bytes);
    }

    /**
     * decrypt
     * 
     * @param string string
     * @param key key
     * @return decrypt string
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws NoSuchPaddingException NoSuchPaddingException
     * @throws InvalidKeyException InvalidKeyException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws BadPaddingException BadPaddingException
     */
    public static final String decrypt(String string, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (string == null) {
            return null;
        }
        try {
            Key secretKey = makeKey(generatSecretKey(key));
    
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
    
            byte[] bytes = Base64Utils.fromBase64(string);
            byte[] dec = cipher.doFinal(bytes);
            return new String(dec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            LOG.warn("decrypt error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 文字列をハッシュ文字列にする
     * 
     * @param string
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hash(String string) throws NoSuchAlgorithmException {
        byte[] bytes = sha256(string);
        return Base64Utils.toBase64(bytes);
    }
    
    /**
     * generate salt
     * @return salt
     */
    public static String getSalt() {
        String randam = RandomUtil.randamGen(254);
        return randam;
    }

    /**
     * パスワード用のハッシュを生成
     * 
     * @param password password
     * @param salt salt
     * @param hashIterations hashIterations
     * @return hash
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static String getStretchedPassword(String password, String salt, int hashIterations) throws NoSuchAlgorithmException {
        String hash = "";
        for (int i = 0; i < hashIterations; i++) {
            hash = hash(hash + salt + password);
        }
        return hash;
    }
}
