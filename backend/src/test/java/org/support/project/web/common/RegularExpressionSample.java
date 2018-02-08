package org.support.project.web.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionSample {

    private static String ignoreRegularExpression = "^/open|css$|js$|jpg$";

    /**
     * @param args
     */
    public static void main(String[] args) {
        Pattern p1 = Pattern.compile(ignoreRegularExpression);

        String test = "aaaa.jpg";
        check(p1, test);

        test = "/open/1234";
        check(p1, test);

        test = "/protect/1123";
        check(p1, test);

        test = "/protect/123.js";
        check(p1, test);

    }

    private static void check(Pattern p, String target) {
        Matcher m = p.matcher(target);

        if (m.find()) {
            System.out.println("○ " + target);
        } else {
            System.out.println("× " + target);
        }
    }

}
