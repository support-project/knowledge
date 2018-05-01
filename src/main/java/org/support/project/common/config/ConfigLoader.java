package org.support.project.common.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;

/**
 * ConfigLoader
 * 
 * @author Koda
 *
 */
public class ConfigLoader {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ConfigLoader.class);
    /** config map */
    public static Map<String, Object> configMap = null;

    /**
     * クラスパス上の設定ファイルを、指定の形式のファイル(XML)で読み込み オブジェクトにマッピングして返す
     * 
     * @param configPath
     *            configPath
     * @param type
     *            type
     * @param <T> type
     * @return config value
     */
    static <T> T read(String configPath, final Class<? extends T> type) {
        if (configMap == null) {
            configMap = new HashMap<String, Object>();
        }
        StringBuilder builder = new StringBuilder();
        builder.append(configPath).append("::").append(type.getName());

        if (!configMap.containsKey(builder.toString())) {
            try {
                Serializer serializer = new Persister(); // XMLを読み込める
                T config = serializer.read(type, ConfigLoader.class.getResourceAsStream(configPath), false);
                configMap.put(builder.toString(), config);
            } catch (Exception e) {
                throw new SystemException(e);
            }
        }
        return (T) configMap.get(builder.toString());
    }

    /**
     * 指定のオブジェクトをクローンして返す
     * 
     * @param obj object
     * @return clone object
     */
    static Object clone(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Cloneable) {
            Cloneable cloneable = (Cloneable) obj;
            Method method;
            try {
                method = obj.getClass().getMethod("clone");
                if (method.isAccessible()) {
                    Object clone;
                    try {
                        clone = method.invoke(obj);
                        return clone;
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        LOG.error("load fail.", e);
                        throw new SystemException("errors.common.call", e);
                    }
                }
            } catch (NoSuchMethodException | SecurityException e) {
                LOG.error("load fail.", e);
                throw new SystemException("errors.common.call", e);
            }
        }
        Object target;
        try {
            target = obj.getClass().newInstance();
            PropertyUtil.copyPropertyValue(obj, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("load fail.", e);
            throw new SystemException("errors.common.call", e);
        }
    }

    /**
     * クラスパス上の設定ファイルを、指定の形式のファイル(XML)で読み込み オブジェクトにマッピングして返す
     * 
     * readは一度実行すると結果を保持し続ける。 このため、どこかで設定を変えると、システム全体として設定が置き換わる。 そこでloadでは、毎回オブジェクトをクローンして渡すようにする。 （読み込む設定はCloneableを実装すること）
     * 
     * Cloneableを実装していない場合、PropertyUtil.copyPropertyValueで値をコピーして渡す
     * 
     * @param configPath configPath
     * @param type type
     * @param <T> type
     * @return config value
     */
    public static <T> T load(String configPath, final Class<? extends T> type) {
        Object obj = read(configPath, type);
        return (T) clone(obj);
    }

}
