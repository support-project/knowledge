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

    private LinkedHashMap<String, String> tokens = new LinkedHashMap<>();

    /**
     * 指定のキーに対するTokenを発行する
     * 
     * @param key key
     * @return token
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public String addToken(String key) throws NoSuchAlgorithmException {
        if (tokens.containsKey(key)) {
            return tokens.get(key);
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
        String tokenString = token.getToken();
        tokens.put(key, tokenString);
        return tokenString;
    }

    /**
     * トークンが正しい値かチェックする
     * 
     * @param key key
     * @param reqTokens CSRFTokens
     * @return チェック結果
     */
    public boolean checkToken(String key, LinkedHashMap<String, String> reqTokens) {
        Iterator<String> iterator = tokens.keySet().iterator();
        while (iterator.hasNext()) {
            String k = iterator.next();
            String token = tokens.get(k);
            if (k.equals(key)) {
                Iterator<String> iterator2 = reqTokens.keySet().iterator();
                while (iterator2.hasNext()) {
                    String k2 = iterator2.next();
                    String reqToken = reqTokens.get(k);
                    if (k2.equals(key) && token.equals(reqToken)) {
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
        Iterator<String> iterator = tokens.values().iterator();
        while (iterator.hasNext()) {
            String csrfToken = (String) iterator.next();
            if (csrfToken.equals(key)) {
                // 保持されているTokenのリストの中に存在すればOK
                return true;
            }
        }
        return false;
    }

    public LinkedHashMap<String, String> getTokens() {
        return tokens;
    }

}
