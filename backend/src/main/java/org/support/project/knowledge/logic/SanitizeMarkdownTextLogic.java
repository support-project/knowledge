package org.support.project.knowledge.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.HtmlUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.logic.SanitizingLogic;

@DI(instance = Instance.Singleton)
public class SanitizeMarkdownTextLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(SanitizeMarkdownTextLogic.class);

    public static SanitizeMarkdownTextLogic get() {
        return Container.getComp(SanitizeMarkdownTextLogic.class);
    }
    
    public String sanitize(String str) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        // ``` で囲まれたブロックを抽出
        StringBuilder builder = new StringBuilder();
        StringBuilder codeblock = new StringBuilder();
        boolean blockin = false;
        String[] strs = str.split("\n");
        int cnt = 0;
        for (String line : strs) {
            if (cnt > 0) {
                if (blockin) {
                    codeblock.append("\n");
                } else {
                    builder.append("\n");
                }
            }
            if (line.startsWith("```")) {
                if (blockin) {
                    // コードブロック終わり
                    // 今までのコードブロックの内容を、append
                    builder.append(codeblock.toString());
                    builder.append(line);
                    codeblock.setLength(0); // 入力クリア
                } else {
                    // コードブロック始まり
                    codeblock.append(HtmlUtils.escapeHTML(line)); // コードブロック内のタグは全てエスケープ
                }
                blockin = !blockin;
            } else {
                if (blockin) {
                    codeblock.append(HtmlUtils.escapeHTML(line)); // コードブロック内のタグは全てエスケープ
                } else {
                    // 正規表現のパターンを作成
                    Pattern p = Pattern.compile("`.+`");
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace(line);
                            LOG.trace(m.group());
                        }
                        line = line.replace(m.group(), HtmlUtils.escapeHTML(m.group()));
                    }
                    builder.append(line);
                }
            }
            cnt++;
        }
        if (blockin) {
            // コードブロックが閉じないで終了している場合は、それまでのものをappend
            builder.append(codeblock.toString());
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace(builder.toString());
        }
        return SanitizingLogic.get().sanitize(builder.toString());
    }
    

}
