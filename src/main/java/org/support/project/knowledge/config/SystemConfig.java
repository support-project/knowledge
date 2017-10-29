package org.support.project.knowledge.config;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.entity.ServiceConfigsEntity;
import org.support.project.knowledge.entity.ServiceLocaleConfigsEntity;
import org.support.project.web.config.WebConfig;

public class SystemConfig {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(SystemConfig.class);
    
    //public static final String KNOWLEDGE_ENV_KEY = "KNOWLEDGE_HOME";
    
    public static final int SYSTEM_USER_ID = -1;
    
    /** システム設定情報 */
    private static ServiceConfigsEntity serviceConfigsEntity = null;
    /** 言語毎のシステム設定情報 */
    private static Map<String, ServiceLocaleConfigsEntity> serviceLocaleConfigsEntities = new ConcurrentHashMap<>();
    /** デフォルトの言語 */
    public static final String DEFAULT_LANGUAGE = "en";
    
    /** ユーザ登録のやりかたを判定するシステム設定のラベル */
    public static final String USER_ADD_TYPE = "USER_ADD_TYPE";
    /** ユーザ登録のやりかた: 管理者が登録（デフォルト） */
    public static final String USER_ADD_TYPE_VALUE_ADMIN = "ADMIN";
    /** ユーザ登録のやりかた: ユーザ自身で登録し、管理者が承認(メール通知あり) */
    public static final String USER_ADD_TYPE_VALUE_APPROVE = "APPROVE";
    /** ユーザ登録のやりかた: ユーザ自身で登録（メールアドレスのチェック無し） */
    public static final String USER_ADD_TYPE_VALUE_USER = "USER";
    /** ユーザ登録のやりかた: ユーザ自身で登録（メールアドレスのチェックあり） */
    public static final String USER_ADD_TYPE_VALUE_MAIL = "MAIL";

    /** システムへアクセスするためのURLのシステム設定のラベル */
    public static final String SYSTEM_URL = WebConfig.KEY_SYSTEM_URL;
    /** アナリティクス設定を保持するキー */
    public static final String ANALYTICS = "ANALYTICS";

    /** ユーザ登録を実施した後、通知するか(ON/OFF) */
    public static final String USER_ADD_NOTIFY = "USER_ADD_NOTIFY";
    /** ユーザ登録を実施した後、通知する(ON) */
    public static final String USER_ADD_NOTIFY_ON = "ON";
    /** ユーザ登録を実施した後、通知しない(OFF) */
    public static final String USER_ADD_NOTIFY_OFF = "OFF";

    /** インデックス再作成のための設定値 */
    public static final String RE_INDEXING = "RE_INDEXING";
    /** エクスポートのための設定値 */
    public static final String DATA_EXPORT = "DATA_EXPORT";

    /** 「公開」の情報であれば、ログインしなくても参照出来る */
    public static final String SYSTEM_EXPOSE_TYPE = "SYSTEM_EXPOSE_TYPE";
    /** 「公開」の情報であれば、ログインしなくても参照出来る */
    public static final String SYSTEM_EXPOSE_TYPE_OPEN = "OPEN";
    /** 全ての機能は、ログインしないとアクセス出来ない */
    public static final String SYSTEM_EXPOSE_TYPE_CLOSE = "CLOSE";

    /** いいね！の登録制限の設定キー */
    public static final String LIKE_CONFIG = "LIKE_CONFIG";
    /** いいね！の登録制限の値：複数回押せる */
    public static final String LIKE_CONFIG_MANY = "MANY";
    /** いいね！の登録制限の値：１回のみ押せる（ログイン必須） */
    public static final String LIKE_CONFIG_ONLY_ONE = "ONLY_ONE";
    
    /** UIのテーマの設定のキー */
    public static final String CONFIG_KEY_THEMA = "THEMA";
    /** Cookieにセットする際のキー：参照履歴 */
    public static final String COOKIE_KEY_HISTORY = "HISTORY";
    /** Cookieにセットする際のキー：表示テーマ */
    public static final String COOKIE_KEY_THEMA = "THEMA";
    /** Cookieにセットする際のキー：コードハイライトのスタイル */
    public static final String COOKIE_KEY_HIGHLIGHT = "HIGHLIGHT";
    /** Cookieにセットする際のキー：キーワード検索時のソートタイプ */
    public static final String COOKIE_KEY_KEYWORD_SORT_TYPE = "KEYWORD_SORT_TYPE";
    /** ログ・ファイルを定期的に削除する場合の、日数 */
    public static final String LOG_DELETE_TERM = "LOG_DELETE_TERM";

    /** 添付ファイルの最大サイズ */
    public static final String UPLOAD_MAX_MB_SIZE = "UPLOAD_MAX_MB_SIZE";

    /**
     * @return the serviceConfigsEntity
     */
    public static ServiceConfigsEntity getServiceConfigsEntity() {
        return serviceConfigsEntity;
    }
    /**
     * @param serviceConfigsEntity the serviceConfigsEntity to set
     */
    public static void setServiceConfigsEntity(ServiceConfigsEntity serviceConfigsEntity) {
        SystemConfig.serviceConfigsEntity = serviceConfigsEntity;
    }
    /**
     * @return the serviceLocaleConfigsEntities
     */
    public static ServiceLocaleConfigsEntity getServiceLocaleConfigsEntity(Locale locale) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(locale.getLanguage());
        buffer.append("_").append(locale.getCountry());
        buffer.append("_").append(locale.getVariant());
        if (serviceLocaleConfigsEntities.containsKey(buffer.toString())) {
            return serviceLocaleConfigsEntities.get(buffer.toString());
        }
        buffer = new StringBuffer();
        buffer.append(locale.getLanguage());
        buffer.append("_").append(locale.getCountry());
        if (serviceLocaleConfigsEntities.containsKey(buffer.toString())) {
            return serviceLocaleConfigsEntities.get(buffer.toString());
        }
        buffer = new StringBuffer();
        buffer.append(locale.getLanguage());
        if (serviceLocaleConfigsEntities.containsKey(buffer.toString())) {
            return serviceLocaleConfigsEntities.get(buffer.toString());
        }
        buffer = new StringBuffer();
        buffer.append(DEFAULT_LANGUAGE);
        if (serviceLocaleConfigsEntities.containsKey(buffer.toString())) {
            return serviceLocaleConfigsEntities.get(buffer.toString());
        }
        return null;
    }
    /**
     * @param serviceLocaleConfigsEntities the serviceLocaleConfigsEntities to set
     */
    public static void setServiceLocaleConfigsEntities(List<ServiceLocaleConfigsEntity> localeConfigsEntities) {
        if (localeConfigsEntities != null) {
            for (ServiceLocaleConfigsEntity serviceLocaleConfigsEntity : localeConfigsEntities) {
                LOG.info("Load custom top page infomation. language: " + serviceLocaleConfigsEntity.getLocaleKey());
                if (serviceLocaleConfigsEntities.containsKey(serviceLocaleConfigsEntity.getLocaleKey())) {
                    ServiceLocaleConfigsEntity entity = serviceLocaleConfigsEntities.get(serviceLocaleConfigsEntity.getLocaleKey());
                    if (entity != null) {
                        entity.setPageHtml(serviceLocaleConfigsEntity.getPageHtml());
                    } else {
                        serviceLocaleConfigsEntities.put(serviceLocaleConfigsEntity.getLocaleKey(), serviceLocaleConfigsEntity);
                    }
                } else {
                    serviceLocaleConfigsEntities.put(serviceLocaleConfigsEntity.getLocaleKey(), serviceLocaleConfigsEntity);
                }
            }
        }
    }
    
    
}
