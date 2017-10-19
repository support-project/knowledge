package org.support.project.web.bean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;

@Serialize(value = SerializerValue.Serializable)
public class CSRFTokens implements Serializable {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;
    
    private List<CSRFToken> tokens = new ArrayList<>();
    
    /**
     * 指定のキーに対するTokenを発行する
     * @param key key 
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public String addToken(String key) throws NoSuchAlgorithmException {
        if (tokens.size() > 20) {
            tokens.remove(0);
        }
        CSRFToken token = CSRFToken.create(key);
        tokens.add(token);
        return token.getToken();
    }
    
    /**
     * トークンが正しい値かチェックする
     * @param key key
     * @param reqTokens CSRFTokens
     * @return チェック結果
     */
    public boolean checkToken(String key, CSRFTokens reqTokens) {
        for (CSRFToken csrfToken : tokens) {
            if (csrfToken.getKey().equals(key)) {
                for (CSRFToken reqToken : reqTokens.tokens) {
                    if (reqToken.getKey().equals(key) && csrfToken.getToken().equals(reqToken.getToken())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * リクエストのHiddenトークンが正しい値かチェックする
     * @param key key
     * @return チェック結果
     */
    public boolean checkToken(String key) {
        for (CSRFToken csrfToken : tokens) {
            if (csrfToken.getToken().equals(key)) {
                // 保持されているTokenのリストの中に存在すればOK
                return true;
            }
        }
        return false;
    }
    
}
