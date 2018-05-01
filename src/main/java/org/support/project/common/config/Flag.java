package org.support.project.common.config;

public class Flag {
    
    public static boolean is(Integer flag) {
        if (flag == null) {
            return false;
        }
        if (flag.intValue() == INT_FLAG.ON.getValue()) {
            return true;
        }
        return false;
    }
    
}
