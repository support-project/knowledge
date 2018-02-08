package org.support.project.knowledge.config;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class LocaleTextReader {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static LocaleTextReader get() {
        return Container.getComp(LocaleTextReader.class);
    }
    
    private Map<String, String> map = new HashMap<>();
    
    public String read(String path, Locale locale) {
        String key = path + "-" + locale.toString();
        if (map.containsKey(key)) {
            return map.get(key);
        }
        String text = loadResource(path, locale);
        map.put(key, text);
        return map.get(key);
    }

    private String loadResource(String path, Locale locale) {
        try {
            if (locale == null) {
                LOG.trace(path);
                return FileUtil.read(getClass().getResourceAsStream(path));
            }
            StringBuilder builder = new StringBuilder();
            builder.append(path.substring(0, path.lastIndexOf(".")));
            builder.append("_").append(locale.getLanguage());
            if (StringUtils.isNotEmpty(locale.getCountry())) {
                builder.append("_").append(locale.getCountry());
            }
            if (StringUtils.isNotEmpty(locale.getVariant())) {
                builder.append("_").append(locale.getVariant());
            }
            builder.append(path.substring(path.lastIndexOf(".")));
            LOG.trace(builder.toString());
            return FileUtil.read(getClass().getResourceAsStream(builder.toString()));
        } catch (IOException | NullPointerException e) {
            if (locale == null) {
                return "";
            } else {
                if (StringUtils.isNotEmpty(locale.getVariant())) {
                    Locale locale2 = new Locale(locale.getLanguage(), locale.getCountry());
                    return loadResource(path, locale2);
                } else if (StringUtils.isNotEmpty(locale.getCountry())) {
                    Locale locale2 = new Locale(locale.getLanguage());
                    return loadResource(path, locale2);
                } else {
                    return loadResource(path, null);
                }
            }
        }
    }
}
