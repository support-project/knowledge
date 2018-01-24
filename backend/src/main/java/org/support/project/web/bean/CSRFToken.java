package org.support.project.web.bean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.RandomUtil;

@Serialize(value = SerializerValue.Serializable)
public class CSRFToken implements Serializable {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;
    
    public static CSRFToken create(String key) throws NoSuchAlgorithmException {
        CSRFToken token = new CSRFToken();
        token.key = key;
        token.date = DateUtils.now();
        token.token = createToken(key);
        return token;
    }
    
    private static String createToken(String k) throws NoSuchAlgorithmException {
        String s = RandomUtil.randamGen(16).concat(k).concat(DateUtils.now().toString());
        return PasswordUtil.hash(s);
    }

    /**
     * コンストラクタ
     */
    private CSRFToken() { }
    
    
    
    private String key;
    private String token;
    private Date date;
    
    /**
     * Get key
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * Get token
     * @return the token
     */
    public String getToken() {
        return token;
    }
    /**
     * Get date
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    
    
    
}
