package org.support.project.knowledge.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class KeywordLogic {
    public static final Map<String, String> REGEX_LIST = new HashMap<String, String>();

    static {
        REGEX_LIST.put("groups", "(?:groups: *\\((?<groups>[^)]+)\\))");
        REGEX_LIST.put("tags", "(?:tags: *\\((?<tags>[^)]+)\\))");
    }

    public static KeywordLogic get() {
        return Container.getComp(KeywordLogic.class);
    }

    /**
     * 文字列を指定の検索クエリでパースして検索クエリの内容を返す
     * 
     * @param key
     * @param text
     * @return String
     */
    public String parseQuery(String key, String text) {
        Pattern pattern = Pattern.compile(REGEX_LIST.get(key));
        Matcher matcher = pattern.matcher(text);

        if (!matcher.find()) {
            return null;
        }
        return matcher.group(key).replaceAll(", *", ",");
    }

    /**
     * 文字列をパースしてキーワードを返す
     * 
     * @param text
     * @return String
     */
    public String parseKeyword(String text) {
        String parsedText = text.replaceAll(KeywordLogic.REGEX_LIST.get("groups"), "");
        parsedText = parsedText.replaceAll(KeywordLogic.REGEX_LIST.get("tags"), "");
        return parsedText.trim();
    }

    /**
     * グループのクエリにして返す
     *
     * @param groupName
     * @return
     */
    public String toGroupsQuery(String groupName) {
        return String.format("groups: (%s) ", groupName);
    }

    /**
     * タグのクエリにして返す
     *
     * @param tagName
     * @return
     */
    public String toTagsQuery(String tagName) {
        return String.format("tags: (%s) ", tagName);
    }
}