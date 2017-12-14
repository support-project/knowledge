package org.support.project.knowledge.sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractEmail {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("引数にメールアドレスを含む一覧を入れてください");
            System.exit(-1);
        }
        String str = args[0];
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(str);
        while (m.find()) {
            System.out.println(m.group());
        }
    }

}
