package org.support.project.common.util;

/**
 * Utility for random value.
 * 
 * @author Koda
 */
public class RandomUtil {

    /** 半角英数字 */
    private static String sHan = "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * ランダムな半角英数の文字列を取得
     * 
     * @param len
     *            length
     * @return random value
     */
    public static String randamGen(int len) {
        char[] list = new char[sHan.length()];
        for (int i = 0; i < sHan.length(); i++) {
            char c = sHan.charAt(i);
            list[i] = c;
        }
        StringBuilder builder = new StringBuilder();
        // java.util.Random rand = new java.util.Random();
        java.security.SecureRandom rand = new java.security.SecureRandom();
        while (true) {
            builder.append(list[rand.nextInt(list.length)]);
            if (builder.length() == len) {
                break;
            }
        }
        return builder.toString();
    }

    /**
     * ランダムな数値を取得
     * 
     * @param min
     *            min value
     * @param max
     *            max value
     * @return random value
     */
    public static int randamNum(int min, int max) {
        java.security.SecureRandom r = new java.security.SecureRandom();
        // int n = r.nextInt(max + 1) + min;
        int n = r.nextInt(max);
        while (n < min) {
            n = r.nextInt(max);
        }
        return n;
    }

}
