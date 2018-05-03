package org.support.project.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Utility for number.
 * 
 * @author Koda
 *
 */
public class NumberUtils {
    /** Format */
    public static final NumberFormat NUMBER_FORMAT = new DecimalFormat("##.##");

    /** Format */
    public static final NumberFormat COST_FORMAT = new DecimalFormat("###,###,###,###,###");

    /**
     * 数値オブジェクトの比較（Nullセーフ）
     * 
     * @param num1
     *            num1
     * @param num2
     *            num2
     * @return result
     */
    public static final boolean is(Integer num1, Integer num2) {
        if (num1 == null) {
            if (num2 == null) {
                return true;
            }
        }
        if (num2 == null) {
            return false;
        }
        if (num1.intValue() == num2.intValue()) {
            return true;
        }
        return false;
    }

    /**
     * 数値オブジェクトの比較（Nullセーフ）
     * 
     * @param num1
     *            num1
     * @param num2
     *            num2
     * @return result
     */
    public static final boolean is(Number num1, Number num2) {
        if (num1 == null) {
            if (num2 == null) {
                return true;
            }
        }
        if (num2 == null) {
            return false;
        }
        if (num1.doubleValue() == num2.doubleValue()) {
            return true;
        }
        return false;
    }

}
