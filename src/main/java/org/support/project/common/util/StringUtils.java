package org.support.project.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 文字列操作のユーティリティクラス
 * 
 * @author Koda
 *
 */
public abstract class StringUtils extends org.apache.commons.lang.StringUtils {
    /** 全角英数字 */
    private static String sZen = "０１２３４５６７８９" + "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ" + "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";
    /** 半角英数字 */
    private static String sHan = "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /** FOLDER_SEPARATOR */
    public static final String FOLDER_SEPARATOR = "/";
    /** WINDOWS_FOLDER_SEPARATOR */
    public static final String WINDOWS_FOLDER_SEPARATOR = "\\";
    /** TOP_PATH */
    public static final String TOP_PATH = "..";
    /** CURRENT_PATH */
    public static final String CURRENT_PATH = ".";
    /** EXTENSION_SEPARATOR */
    public static final char EXTENSION_SEPARATOR = '.';
    /** MAIL_FORMAT */
    public static final String MAIL_FORMAT = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*"
            + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)*$";

    /**
     * 文字列がNULLか空文字列かどうかのチェック
     * 
     * @param str
     *            文字列
     * @return 空かどうかのチェック結果
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 空かチェック
     * 
     * @param str
     *            オブジェクト
     * @return チェック結果
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 文字列がNULLか空文字列かどうかのチェック
     * 
     * @param str
     *            文字列
     * @return 空かどうかのチェック結果
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 文字列がIntegerになるかチェックする
     * 
     * @param str
     *            文字列
     * @return チェック結果
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 文字列がLongになるかチェックする
     * 
     * @param str
     *            文字列
     * @return チェック結果
     */
    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 文字列が数値であるかチェックする
     * 
     * @param str
     *            文字列
     * @return チェック結果
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // /**
    // * Unicode表記(&#12486;&#12473;など)から通常のテキスト表記に変換
    // *
    // * @param text
    // * @return
    // * @throws IOException
    // * @throws IOException
    // */
    // public static String covertFromUnicode(String text) throws IOException {
    // StringBuilder builder = new StringBuilder();
    // StringBuilder cha = new StringBuilder();
    // int flag = 0;
    // int count = 0;
    // for (int i = 0; i < text.length(); i++) {
    // char c = text.charAt(i);
    // if (c == '&') {
    // flag = 1;
    // } else if (c == '#' && flag == 1) {
    // flag = 2;
    // } else if (c >= '0' && c <= '9' && flag == 2) {
    // cha.append(c);
    // count++;
    // } else if (c == ';' && flag == 2 && count == 5) {
    // // UNICODE数値から文字を戻す
    // char num = (char) Integer.parseInt(cha.toString());
    // builder.append(num);
    //
    // cha.setLength(0);
    // flag = 0;
    // count = 0;
    // } else {
    // if (cha.length() > 0) {
    // builder.append(cha.toString());
    // cha.setLength(0);
    // }
    // builder.append(c);
    // flag = 0;
    // count = 0;
    // }
    // }
    //
    // return builder.toString();
    // }
    //
    // /**
    // * 文字列をUNICODE表記に変換する
    // *
    // * @param text
    // * @return
    // * @throws IOException
    // */
    // public static String convertToUnicode(String text) throws IOException {
    // StringBuilder builder = new StringBuilder();
    // for (int i = 0; i < text.length(); i++) {
    // char c = text.charAt(i);
    // if ((c < 0x0020) || (c > 0x007e)) {
    // builder.append("&#");
    // builder.append(Integer.toString(c).toUpperCase());
    // builder.append(";");
    // } else {
    // builder.append(c);
    // }
    // }
    // return builder.toString();
    // }

    /**
     * ゼロパディング
     * 
     * @param num
     *            num
     * @param digit
     *            digit
     * @return String
     */
    public static String zeroPadding(int num, int digit) {
        return zeroPadding(new Long(num), digit);
    }

    /**
     * ゼロパディング
     * 
     * @param num
     *            num
     * @param digit
     *            digit
     * @return String
     */
    public static String zeroPadding(long num, int digit) {
        StringBuffer sb = new StringBuffer();
        String numStr = String.valueOf(num);
        int numLength = numStr.length();
        if (numLength < digit) {
            for (int i = 0; i < digit - numLength; i++) {
                sb.append("0");
            }
        }
        sb.append(num);
        return sb.toString();
    }

    /**
     * ファイル名から拡張子を取得する (.からスタート)
     * 
     * @param filename
     *            拡張子付き文字列
     * @return 拡張子
     */
    public static String getExtension(String filename) {
        if (filename.lastIndexOf(".") != -1) {
            String extension = filename.substring(filename.lastIndexOf("."));
            return extension.toLowerCase();
        }
        return "";
    }

    /**
     * ファイル名称の取得(パスの最後のファイル名)
     * 
     * @param fileName
     *            ファイル名称(パス含む)
     * @return ファイル名称
     */
    public static String getFileName(String fileName) {
        if (fileName.lastIndexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        }
        if (fileName.lastIndexOf("/") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        return fileName;
    }

    /**
     * テキスト値＋１の値を返す
     * 
     * @param maxLength
     *            maxLength
     * @param maxNo
     *            maxNo
     * @return テキスト
     */
    public static String nextMaxNo(int maxLength, String maxNo) {
        if (maxNo == null) {
            return addLength(1, maxLength);
        } else {
            long maxInt = Long.parseLong(maxNo);
            maxInt++;
            return addLength(maxInt, maxLength);
        }
    }

    /**
     * テキストと数値の桁あわせ
     * 
     * @param intNum
     *            num
     * @param maxLength
     *            maxLength
     * @return テキスト
     */
    public static String addLength(long intNum, int maxLength) {
        String num = String.valueOf(intNum);
        return addLength(num, maxLength);
    }

    /**
     * 桁あわせ
     * 
     * @param num
     *            num
     * @param maxLength
     *            maxLength
     * @return テキスト
     */
    private static String addLength(String num, int maxLength) {
        StringBuffer buff = new StringBuffer();
        for (int i = num.length(); i < maxLength; i++) {
            buff.append("0");
        }
        buff.append(num);
        return buff.toString();
    }

    /**
     * テキスト値＋１の値を返す(Hex)
     * 
     * @param maxLength
     *            maxLength
     * @param maxNo
     *            maxNo
     * @return テキスト
     */
    public static String nextMaxNoHex(int maxLength, String maxNo) {
        if (maxNo == null) {
            return addLengthHex(1, maxLength);
        } else {
            long value = 0;
            value = Long.parseLong(maxNo, 16);
            value++;
            return addLengthHex(value, maxLength);
        }
    }

    /**
     * テキストと数値の桁あわせ(Hex)
     * 
     * @param num
     *            num
     * @param maxLength
     *            maxLength
     * @return テキスト
     */
    public static String addLengthHex(long num, int maxLength) {
        String hexStr = Long.toHexString(num).toUpperCase();
        return addLength(hexStr, maxLength);
    }

    /**
     * 文字列の前後からスペース(全角半角、改行、タブなど)を削除した文字列を取得
     * 
     * @param string
     *            文字列
     * @return 前後のスペースを削除した文字列
     */
    public static String dellControlString(String string) {
        string = string.replaceAll(" ", "");
        string = string.replaceAll("\n", " ");
        string = string.replaceAll("\r", " ");
        string = string.replaceAll("\t", " ");
        string = string.replaceAll(" ", " ");
        return string;
    }

    /**
     * 全角空白を含めたtrim
     * 
     * @param s
     *            文字列
     * @return trimした文字
     */
    public static String trimUni(String s) {
        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();
        while (st < len && (val[st] <= ' ' || val[st] == '　')) {
            st++;
        }
        while (st < len && (val[len - 1] <= ' ' || val[len - 1] == '　')) {
            len--;
        }
        if (st > 0 || len < s.length()) {
            return s.substring(st, len);
        }
        return s;
    }

    /**
     * 複数行に渡るテキストを行単位にTrimをかける
     * 
     * @param text
     *            テキスト
     * @return trim結果
     * @throws IOException
     *             IOException
     */
    public static String lineTrim(String text) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(text));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line.trim());
        }
        reader.close();
        return builder.toString();
    }

    // /**
    // * 改行コードがあれば、<br/>
    // * を挿入する (改行コードは消さない)
    // *
    // * @param text
    // * @return
    // * @throws IOException
    // */
    // public static String lineTrimAppendBr(String text) throws IOException {
    // BufferedReader reader = new BufferedReader(new StringReader(text));
    // StringBuilder builder = new StringBuilder();
    //
    // String line;
    // boolean body = false;
    // while ((line = reader.readLine()) != null) {
    // if ((line.toLowerCase().indexOf("</body>") != -1)
    // || (line.toLowerCase().indexOf("<p ") != -1)) {
    // body = false;
    // }
    // builder.append(line.trim());
    // if (body) {
    // builder.append("<br/>");
    // }
    // if ((line.toLowerCase().indexOf("<body>") != -1)
    // || (line.toLowerCase().indexOf("</p>") != -1)) {
    // body = true;
    // }
    // }
    // reader.close();
    // return builder.toString();
    // }

    /**
     * バイト桁数を取得
     * 
     * @param value
     *            String
     * @return length of bytes
     */
    public static int getByteLength(String value) {
        int length = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c >= 0x20 && c <= 0x7E) {
                // JISローマ字(ASCII)
                length++;
            } else if (c >= 0xFF61 && c <= 0xFF9F) {
                // JISカナ(半角カナ)
                length++;
            } else {
                // その他(全角)
                length += 2;
            }
        }
        return length;
    }

    /**
     * 数値を見やすく3桁区切りに区切る
     * 
     * @param number
     *            数値文字列
     * @return フォーマットされた文字列
     */
    public static String formatNumber(Long number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        return decimalFormat.format(number);
    }

    /** 文章を分ける文字の初期値 */
    // private static final String[] DEFAULT_SPLIT_STRING = {"。", "\\.", "!",
    // "\\?", "？", ":", "：", "！", "\\|", "｜", "\r", "\n", "\r\n", "\t"};
    private static final String[] DEFAULT_SPLIT_STRING = { "。", "!", "\\?", "？", "！", "\r", "\n", "\r\n", "\t" };

    /**
     * 文字を分割する
     * 
     * @param str
     *            String
     * @return list of string
     */
    public static List<String> splitString(String str) {
        List<String> list = new ArrayList<String>();
        list.add(str);
        for (String string : DEFAULT_SPLIT_STRING) {
            list = splitString(list, string);
        }
        return list;
    }

    /**
     * 文字を分割する
     * 
     * @param list
     *            list
     * @param splitString
     *            separator string
     * @return list of string
     */
    public static List<String> splitString(List<String> list, String splitString) {
        List<String> stringlist = new ArrayList<String>();
        for (String string : list) {
            String[] sp = string.split(splitString);
            int count = 1;
            for (String string2 : sp) {
                if (count < sp.length) {
                    string2 = string2.concat(splitString);
                }
                if (sp.length == 1 && !sp[0].equals(string)) {
                    string2 = string;
                }
                stringlist.add(string2);
                count++;
            }
        }
        return stringlist;
    }

    // /**
    // * 文字を指定のオブジェクトに変換する
    // *
    // * @param value
    // * @param type
    // * @return
    // */
    // public static <T> T convObject(String value, Class<? extends T> type) {
    // if (value == null) {
    // return null;
    // }
    // if (type.isAssignableFrom(Integer.class)) {
    // return (T) Integer.valueOf(value);
    // } else if (type.isAssignableFrom(String.class)) {
    // return (T) value;
    // }
    // throw new NotImplementedException("This class is not covered. ["
    // + type.getClass().getName() + "]");
    // }

    /**
     * ランダムな文字列を取得
     * 
     * @param len length
     * @return randam string
     */
    public static String randamGen(int len) {
        return RandomUtil.randamGen(len);
    }

    /**
     * 半角英数字 → 全角英数字に置換する。
     * 原理は、置換対象文字列 sSrc から1文字づつ取り出して sHan に該当する文字があったら、
     * その文字が何文字目にあるか取得して、sZen から同じ文字目から文字を 取ると、全角にすり返るという方法。 
     * 例) sSrc = "ab1";
     * 1番目の文字 "a" は、sHanの36文字目なので、sZenの36文字目は、"ａ"で、置換 
     * 2番目の文字 "b" は、sHanの37文字目なので、sZenの37文字目は、"ｂ"で、置換 
     * 3番目の文字 "1" は、sHanの1文字目なので、sZenの1文字目は、"１"で、置換
     * 
     * @param str
     *            文字列
     * @return covert string
     */
    public static String convHankaku(String str) {
        String sSrc = str;
        // 対象文字数分ループ
        for (int iLoop = 0; iLoop < sSrc.length(); iLoop++) {
            // １．対象文字列よりiLoop番目の文字取得
            // ２．文字型を文字型に変換
            // ３．sHan(半角文字)の何番目に該当するか取得->iPosに格納
            int iPos = sHan.indexOf(String.valueOf(sSrc.charAt(iLoop)));
            // sHan(半角文字)に該当する文字があった場合
            if (iPos > -1) {
                // sSrc = sSrcの0～iLoop番目の文字列を取得
                // + sZen(全角文字)からiPos番目の文字を１つ取得
                // + sSrcのiLoop番目+1より後ろの文字列を取得
                sSrc = sSrc.substring(0, iLoop) + sZen.charAt(iPos) + sSrc.substring(iLoop + 1);
            }
        }
        return sSrc;
    }

    /**
     * パターンのカウントを取得
     * 
     * @param source
     *            文字列
     * @param pattern
     *            パターン
     * @return カウント
     */
    public static int countOccurrencesOf(String source, String pattern) {
        int count = 0;
        if (source != null) {
            final int len = pattern.length();
            int found = -1;
            int start = 0;
            while ((found = source.indexOf(pattern, start)) != -1) {
                start = found + len;
                count++;
            }
            return count;
        } else {
            return 0;
        }
    }

    // public static String join(List<String> list, String separator) {
    // if (list == null || list.isEmpty()) {
    // return "";
    // }
    // StringBuffer buffer = new StringBuffer();
    // int count = 0;
    // for (String string : list) {
    // if (count > 0) {
    // buffer.append(",");
    // }
    // buffer.append(string);
    // count++;
    // }
    // return null;
    // }

    /**
     * メールアドレス形式かどうか判定
     * 
     * @param str
     *            string
     * @return is email address
     */
    public static boolean isEmailAddress(String str) {
        if (str == null) {
            return false;
        }
        return str.matches(MAIL_FORMAT);
    }
    
    /**
     * 配列の中に、指定の文字が含まれているかをチェック
     * @param str
     * @param strings
     * @return
     */
    public static boolean contains(String str, String ... strings) {
        if (strings == null) {
            return false;
        }
        for (String string : strings) {
            if (string.equals(str)) {
                return true;
            }
        }
        return false;
    }

}
