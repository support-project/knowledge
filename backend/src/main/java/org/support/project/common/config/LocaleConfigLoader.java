package org.support.project.common.config;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;

/**
 * Load config file at Locale
 * 
 * @author Koda
 */
public class LocaleConfigLoader {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /** 設定ファイル＋ロケールで、どの設定ファイルを読み込むかを保持するマップ */
    public static Map<String, String> configPathMap = null;

    /**
     * LocaleConfigLoader
     * @param configDir configDir
     * @param configName configName
     * @param locale locale
     * @param type type
     * @param <T> type
     * @return config value object 
     */
    public static <T> T load(final String configDir, final String configName, final Locale locale, final Class<? extends T> type) {
        if (configPathMap == null) {
            configPathMap = new HashMap<String, String>();
        }
        StringBuffer path = new StringBuffer();
        path.append(configDir);
        path.append(configName);
        if (locale != null) {
            path.append(locale.toString());
        }
        if (configPathMap.containsKey(path.toString())) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("load config: " + configPathMap.get(path.toString()) + ".");
            }
            Object load = ConfigLoader.read(configPathMap.get(path.toString()), type);
            return (T) ConfigLoader.clone(load);
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(configDir).append(configName);
            if (locale != null) {
                if (!StringUtils.isEmpty(locale.getVariant())) {
                    buffer.append("_").append(locale.getLanguage());
                    buffer.append("_").append(locale.getCountry());
                    buffer.append("_").append(locale.getVariant());
                    buffer.append(".xml");
                    InputStream in = ConfigLoader.class.getResourceAsStream(buffer.toString());
                    if (in != null) {
                        configPathMap.put(path.toString(), buffer.toString());
                        Object load = ConfigLoader.read(configPathMap.get(path.toString()), type);
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("config: " + configPathMap.get(path.toString()) + " is finded.");
                        }
                        return (T) ConfigLoader.clone(load);
                    } else {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("config:" + buffer.toString() + " is not exist.");
                        }
                        buffer = new StringBuffer();
                        buffer.append(configDir).append(configName);
                    }
                }
                if (!StringUtils.isEmpty(locale.getCountry())) {
                    buffer.append("_").append(locale.getLanguage());
                    buffer.append("_").append(locale.getCountry());
                    buffer.append(".xml");
                    InputStream in = ConfigLoader.class.getResourceAsStream(buffer.toString());
                    if (in != null) {
                        configPathMap.put(path.toString(), buffer.toString());
                        Object load = ConfigLoader.read(configPathMap.get(path.toString()), type);
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("config: " + configPathMap.get(path.toString()) + " is finded.");
                        }
                        return (T) ConfigLoader.clone(load);
                    } else {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("config:" + buffer.toString() + " is not exist.");
                        }
                        buffer = new StringBuffer();
                        buffer.append(configDir).append(configName);
                    }
                }
                buffer.append("_").append(locale.getLanguage());
                buffer.append(".xml");
                InputStream in = ConfigLoader.class.getResourceAsStream(buffer.toString());
                if (in != null) {
                    configPathMap.put(path.toString(), buffer.toString());
                    Object load = ConfigLoader.read(configPathMap.get(path.toString()), type);
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("config: " + configPathMap.get(path.toString()) + " is finded.");
                    }
                    return (T) ConfigLoader.clone(load);
                } else {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("config:" + buffer.toString() + " is not exist.");
                    }
                    buffer = new StringBuffer();
                    buffer.append(configDir).append(configName);
                }
            }
            buffer.append(".xml");
            InputStream in = ConfigLoader.class.getResourceAsStream(buffer.toString());
            if (in != null) {
                configPathMap.put(path.toString(), buffer.toString());
                Object load = ConfigLoader.read(configPathMap.get(path.toString()), type);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("config: " + configPathMap.get(path.toString()) + " is finded.");
                }
                return (T) ConfigLoader.clone(load);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("config:" + buffer.toString() + " is not exist.");
            }
        }
        throw new SystemException("errors.common.config.not.exist", configDir, configName, locale.toString());
    }

}
