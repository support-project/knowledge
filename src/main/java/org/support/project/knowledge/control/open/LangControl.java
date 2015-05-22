package org.support.project.knowledge.control.open;

import java.util.Locale;

import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.control.Control;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

public class LangControl extends Control {
	
	@Get
	public Boundary en() {
		Locale locale = Locale.ENGLISH;
		HttpUtil.setLocale(super.getRequest(), locale);
		this.setLocale(getLoginedUser(), HttpUtil.getLocale(getRequest()));
		return redirect(getRequest().getContextPath() + "/index");
	}
	
	
	@Get
	public Boundary ja() {
		Locale locale = Locale.JAPANESE;
		HttpUtil.setLocale(super.getRequest(), locale);
		this.setLocale(getLoginedUser(), HttpUtil.getLocale(getRequest()));
		return redirect(getRequest().getContextPath() + "/index");
	}
	
	private void setLocale(LoginedUser loginedUser, Locale locale) {
		if (loginedUser == null) {
			return;
		}
		loginedUser.setLocale(locale);
	}


	@Get
	public Boundary select() throws InvalidParamException {
		String LocaleID = getPathString();
		if (StringUtils.isEmpty(LocaleID)) {
			return en();
		} else if (LocaleID.equals("en")) {
			return en();
		} else if (LocaleID.equals("ja")) {
			return ja();
		}
		Locale locale = Locale.ENGLISH;
		if (LocaleID.indexOf("_") == -1) {
			locale = new Locale(LocaleID);
		} else {
			String[] sp = LocaleID.split("_");
			if (sp.length == 2) {
				locale = new Locale(sp[0], sp[1]);
			} else if (sp.length >= 3) {
				locale = new Locale(sp[0], sp[1], sp[2]);
			}
		}
		HttpUtil.setLocale(super.getRequest(), locale);
		this.setLocale(getLoginedUser(), HttpUtil.getLocale(getRequest()));
		return redirect(getRequest().getContextPath() + "/index");
	}
	
	
}
