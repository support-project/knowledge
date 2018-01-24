package org.support.project.web.bean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;

@Serialize(value = SerializerValue.Serializable)
public class CSRFTokens implements Serializable {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;

    private LinkedHashMap<String, CSRFToken> tokens = new LinkedHashMap<>();

    /**
     * 指定のキーに対するTokenを発行する
     * 
     * @param key key
     * @return token
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public String addToken(String key) throws NoSuchAlgorithmException {
        if (tokens.containsKey(key)) {
            CSRFToken token = tokens.get(key);
            return token.getToken();
        }
        if (tokens.size() > 20) {
            Iterator<String> iterator = tokens.keySet().iterator();
            while (iterator.hasNext()) {
                String string = (String) iterator.next();
                tokens.remove(string); // 初めの1件を削除（古いもの）
                break;
            }
        }
        CSRFToken token = CSRFToken.create(key);
        tokens.put(key, token);
        return token.getToken();
    }

    /**
     * トークンが正しい値かチェックする
     * 
     * @param key key
     * @param reqTokens CSRFTokens
     * @return チェック結果
     */
    public boolean checkToken(String key, CSRFTokens reqTokens) {
        Iterator<CSRFToken> iterator = tokens.values().iterator();
        while (iterator.hasNext()) {
            CSRFToken csrfToken = (CSRFToken) iterator.next();
            if (csrfToken.getKey().equals(key)) {
                Iterator<CSRFToken> iterator2 = reqTokens.tokens.values().iterator();
                while (iterator2.hasNext()) {
                    CSRFToken reqToken = (CSRFToken) iterator2.next();
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
     * 
     * @param key key
     * @return チェック結果
     */
    public boolean checkToken(String key) {
        Iterator<CSRFToken> iterator = tokens.values().iterator();
        while (iterator.hasNext()) {
            CSRFToken csrfToken = (CSRFToken) iterator.next();
            if (csrfToken.getToken().equals(key)) {
                // 保持されているTokenのリストの中に存在すればOK
                return true;
            }
        }
        return false;
    }

}
