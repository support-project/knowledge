package org.support.project.common.util;

/**
 * 比較処理のユーティリティ
 * @author Koda
 *
 */
public class Compare {
    
    /**
     * Integer型の比較
     * Nullセーフ
     * @param integer1
     * @param integer2
     * @return
     */
    public static boolean equal(Integer integer1, Integer integer2) {
        if (integer1 == null && integer2 == null) {
            return true;
        }
        if (integer1 == null && integer2 != null) {
            return false;
        }
        if (integer1 != null && integer2 == null) {
            return false;
        }
        return integer1.intValue() == integer2.intValue();
    }
    
}
