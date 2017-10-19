package org.support.project.common.config;

/**
 * Boolean型がINTになるDBで、INT値をBooleanにする場合の選択値
 * 
 * @author Koda
 */
public enum INT_FLAG {
    /** OFF */
    OFF,
    /** ON */
    ON;
    
    /**
     * 選択値をint値で取得
     * @return int vaue
     */
    public int getValue() {
        return ordinal();
    }
    /**
     * int値から、選択値を取得
     * @param type int
     * @return emu
     */
    public static INT_FLAG getType(int type) {
        INT_FLAG[] values = values();
        return values[type];
    }

    /**
     * Integer型のフラグをチェック
     * 
     * @param check check
     * @return check result
     */
    public static boolean flagCheck(Integer check) {
        if (check == null) {
            return false;
        }
        if (check.intValue() == INT_FLAG.ON.getValue()) {
            return true;
        }
        return false;
    }

}
