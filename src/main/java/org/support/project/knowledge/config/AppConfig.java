package org.support.project.knowledge.config;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.vo.LabelValue;


public class AppConfig extends org.support.project.web.config.AppConfig {
	
	public static AppConfig get() {
		if (appConfig == null) {
			appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		}
		return appConfig;
	}
	private static AppConfig appConfig = null;
	
	public static final String SYSTEM_NAME = "knowledge";
	
	private String indexPath;
	
	private boolean convIndexPath = false;
	
	private List<LabelValue> languages = new ArrayList<>();
	
	
	/**
	 * @return the indexPath
	 */
	public String getIndexPath() {
		if (StringUtils.isEmpty(indexPath)) {
			return "";
		}

		if (!convIndexPath) {
			String path = indexPath;
			this.indexPath = convPath(path);
			convIndexPath = true;
		}
		return indexPath;
	}

	/**
	 * @param indexPath the indexPath to set
	 */
	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	/**
	 * @return the languages
	 */
	public List<LabelValue> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List<LabelValue> languages) {
		this.languages = languages;
	}
	
}
