package org.support.project.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility for list.
 * 
 * @author Koda
 */
public class ListUtils {

    /**
     * リストの型をコンバートする
     * 
     * @param <T> convert type
     * @param list
     *            List
     * @param type
     *            type of conv class
     * @return convert list
     * @throws IllegalAccessException
     *             IllegalAccessException
     * @throws InstantiationException
     *             InstantiationException
     */
    public static <T> List<T> convertList(final Collection<?> list, final Class<? extends T> type)
            throws InstantiationException, IllegalAccessException {
        List<T> models = new ArrayList<T>();
        if (list != null) {
            for (Object src : list) {
                if (src != null) {
                    T dest = type.newInstance();
                    PropertyUtil.copyPropertyValue(src, dest);
                    models.add(dest);
                }
            }
        }
        return models;
    }

    /**
     * Array to String
     * 
     * @param params
     *            array
     * @return string
     */
    public static String toString(Object[] params) {
        if (params == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        int idx = 0;
        for (Object object : params) {
            if (idx > 0) {
                builder.append("\n");
            }
            builder.append("[").append(idx++).append("]").append(PropertyUtil.reflectionToString(object));
        }
        return builder.toString();
    }

    /**
     * List to string
     * 
     * @param params
     *            List
     * @return string
     */
    public static String toString(List<?> params) {
        if (params == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        int idx = 0;
        for (Object object : params) {
            if (idx > 0) {
                builder.append("\n");
            }
            builder.append("[").append(idx++).append("]").append(PropertyUtil.reflectionToString(object));
        }
        return builder.toString();
    }

}
