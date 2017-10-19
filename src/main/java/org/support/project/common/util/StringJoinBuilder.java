package org.support.project.common.util;

import java.util.ArrayList;
import java.util.List;

public class StringJoinBuilder<T extends Object> {

    private List<T> params = new ArrayList<>();

    public StringJoinBuilder() {
        super();
    }

    public StringJoinBuilder(List<T> params) {
        super();
        this.params = params;
    }

    public StringJoinBuilder(T[] array) {
        super();
        for (T o : array) {
            params.add(o);
        }
    }

    public StringJoinBuilder append(T obj) {
        params.add(obj);
        return this;
    }

    public String join(String delimiter) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (T object : params) {
            if (count > 0) {
                builder.append(delimiter);
            }
            builder.append(object.toString().trim());
            count++;
        }
        return builder.toString();
    }

    public int length() {
        return params.size();
    }

}
