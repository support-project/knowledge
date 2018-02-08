package org.support.project.common.bean;

import java.util.Locale;

import org.support.project.common.config.CommonBaseParameter;
import org.support.project.common.config.Resources;
import org.support.project.common.log.LogLevel;

public class ValidateError {
	
	public static final int WARN = LogLevel.WARN.getValue();
	public static final int ERROR = LogLevel.ERROR.getValue();
	
	
	private Integer level;
	
	//private String msg;
	
	private String key;
	private String[] params;
	
	public ValidateError(Integer level, String key, String... params) {
		super();
		this.level = level;
		this.key = key;
		this.params = params;
		//Resources resources = Resources.getInstance(CommonBaseParameter.APP_RESOURCE);
		//msg = resources.getResource(key, params);
	}
	
	
	public ValidateError(String key, String... params) {
		this(WARN, key, params);
	}
	
	public String getMsg(Locale locale) {
		Resources resources = Resources.getInstance(CommonBaseParameter.APP_RESOURCE, locale);
		String msg = resources.getResource(key, params);
		return msg;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	
}
