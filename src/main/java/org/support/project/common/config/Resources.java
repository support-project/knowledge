package org.support.project.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

/**
 * プロパティリソースを読み込む
 * 
 * これを使うと、今まで読み込んだリソースファイルを全て保持していく
 * (APPResouceとDIResourceを読み込むと、両方のリソースが取得できるようになる)
 * 
 */
public class Resources {
	/** ログ */
	private static Log logger = LogFactory.getLog(Resources.class);

	
	/** リソースを保持するマップ(ロケール毎に保持) */
	private static Map<Locale, Resources> mapOnLocale;
	
	/**
	 * すでに読み込み済みのリソースファイル名
	 */
	private List<String> readed = new ArrayList<String>();
	
	/**
	 * Ordered list of the inserted resource bundles.
	 */
	private LinkedList<ResourceBundle> bundles = new LinkedList<>();

	/**
	 * インスタンスを取得
	 * 
	 * @param key key
	 * @param locale locale
	 * @return インスタンス
	 */
	public static Resources getInstance(String key, Locale locale) {
		if (mapOnLocale == null) {
			mapOnLocale = new HashMap<>();
		}
		
		if (!mapOnLocale.containsKey(locale)) {
			Resources resources = new Resources();
			mapOnLocale.put(locale, resources);
		}
		Resources resources = mapOnLocale.get(locale);
		try {
			//CommonBaseParameter.APP_RESOURCE に指定のあるResourcesは常に取得
			resources.add(CommonBaseParameter.APP_RESOURCE, locale);
			// 指定したファイルを読み込み
			if (!CommonBaseParameter.APP_RESOURCE.equals(key)) {
				resources.add(key, locale);
			}
		} catch (IOException e) {
			throw new SystemException(e);
		}
		return resources;
	}
		
	/**
	 * インスタンスを取得
	 * @param key key
	 * @return インスタンス
	 */
	public static Resources getInstance(String key) {
		Locale locale = Locale.getDefault();
		Resources resources = getInstance(key, locale);
		return resources;
	}
	
	/**
	 * インスタンスを取得
	 * @param locale locale
	 * @return インスタンス
	 */
	public static Resources getInstance(Locale locale) {
		String key = CommonBaseParameter.APP_RESOURCE;
		Resources resources = getInstance(key, locale);
		return resources;
	}
	
	/**
	 * インスタンスを取得
	 * @return インスタンス
	 */
	public static Resources getInstance() {
		// CommonBaseParameter.APP_RESOURCE に指定のあるResourcesを取得
		String key = CommonBaseParameter.APP_RESOURCE;
		Locale locale = Locale.getDefault();
		Resources resources = getInstance(key, locale);
		return resources;
	}
	

	
	
	
	
	/**
	 * コンストラクタ
	 */
	private Resources() {
		super();
	}
	
//	/**
//	 * ディフォルトのリソースから設定値を取得
//	 * @param key
//	 * @param params
//	 * @return
//	 */
//	public static String get(String key, String... params) {
//		Resources resources = getInstance(CommonBaseParameter.APP_RESOURCE);
//		return resources.getResource(key, params);
//	}
//	/**
//	 * ディフォルトのリソースから設定値を取得
//	 * @param key
//	 * @return
//	 */
//	public static String get(String key) {
//		Resources resources = getInstance(CommonBaseParameter.APP_RESOURCE);
//		return resources.getResource(key);
//	}
	
	/**
	 * Adds a resource bundle. This may throw a MissingResourceException that should be handled in the calling code.
	 * 
	 * @param basename
	 *            The basename of the resource bundle to add.
	 * @param locale 
	 * @throws IOException 
	 */
	private void add(String basename, Locale locale) throws IOException {
		if (!readed.contains(basename)) {
			//ResourceBundle bundle = PropertyResourceBundle.getBundle(basename, locale);
			InputStream inputStream = getStream(basename, locale);
			if (inputStream == null) {
				logger.error("Resources: " + basename + " is not find.");
				return;
			}
			InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
			ResourceBundle bundle = new PropertyResourceBundle(reader);
			bundles.addFirst(bundle);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Resources: " + basename + "    Locale: " + locale.toString() + " is added.");
			}
			readed.add(basename);
		}
	}
	
	/**
	 * ロケールに従い、読み込むファイルを取得する
	 * @param basename basename
	 * @param locale locale
	 * @return 読み込むファイル
	 */
	private InputStream getStream(String basename, Locale locale) {
		InputStream inputStream = null;
		StringBuilder builder = new StringBuilder();
		builder.append(basename).append("_").append(locale.toString()).append(".properties");
		logger.trace(builder.toString());
		inputStream = getClass().getResourceAsStream(builder.toString());
		if (inputStream != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Resources: " + builder.toString() + " is finded.");
			}
			return inputStream;
		}
		
		builder = new StringBuilder();
		builder.append(basename).append("_").append(locale.getLanguage()).append(".properties");
		logger.trace(builder.toString());
		inputStream = getClass().getResourceAsStream(builder.toString());
		if (inputStream != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Resources: " + builder.toString() + " is finded.");
			}
			return inputStream;
		}
		
		builder = new StringBuilder();
		builder.append(basename).append(".properties");
		logger.trace(builder.toString());
		inputStream = getClass().getResourceAsStream(builder.toString());
		if (inputStream != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Resources: " + builder.toString() + " is finded.");
			}
			return inputStream;
		}
		
		return inputStream;
	}

	/**
	 * 指定のキーのりーソースの文字列を取得
	 * @param key key
	 * @param params params
	 * @return 文字列
	 */
	public String getResource(String key, String... params) {
		String value = getString(key);

		// Applies default value if required
		if (value == null) {
			value = key;
			return value;
		}

		// Replaces the placeholders with the values in the array
		if (value != null && params != null) {
			StringBuffer result = new StringBuffer();
			String index = null;

			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);

				if (c == '{') {
					index = "";
				} else if (index != null && c == '}') {
					int tmp = Integer.parseInt(index) - 1;

					if (tmp >= 0 && tmp < params.length) {
						result.append(params[tmp]);
					}

					index = null;
				} else if (index != null) {
					index += c;
				} else {
					result.append(c);
				}
			}

			value = result.toString();
		}

		return value;
	}

	/**
	 * 指定のキーのりーソースの文字列を取得
	 * @param key key
	 * @return 文字列
	 */
	public String getResource(String key) {
		return getResource(key, new String[0]);
	}

	/**
	 * 指定のキーのりーソースの文字列を取得
	 * @param key key
	 * @return 文字列
	 */
	private String getString(String key) {
		Iterator<ResourceBundle> it = bundles.iterator();
		while (it.hasNext()) {
			try {
				ResourceBundle bundle = it.next();
				return bundle.getString(key);
			} catch (MissingResourceException mrex) {
				// continue
			}
		}
		return null;
	}

}
