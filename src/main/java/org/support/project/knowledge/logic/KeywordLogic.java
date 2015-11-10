package org.support.project.knowledge.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance=Instance.Singleton)
public class KeywordLogic {
	public static final Map<String, String> regexList = new HashMap<String, String>();

	static {
		regexList.put("groups", "(?:groups:(?<groups>[^ ]+))");
		regexList.put("tags", "(?:tags:(?<tags>[^ ]+))");
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
		Pattern pattern = Pattern.compile(regexList.get(key));
		Matcher matcher = pattern.matcher(text);

		if (!matcher.find()) {
			return null;
		}
		return  matcher.group(key);
	}

	/**
	 * 文字列をパースしてキーワードを返す
	 * 
	 * @param text
	 * @return String
	 */
	public String parseKeyword(String text) {
		text = text.replaceAll(KeywordLogic.regexList.get("groups"), "");
		text = text.replaceAll(KeywordLogic.regexList.get("tags"), "");
		text = text.trim();
		return text;
	}
}