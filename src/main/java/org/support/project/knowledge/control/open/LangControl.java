package org.support.project.knowledge.control.open;

import java.util.Locale;

import org.support.project.knowledge.control.Control;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpUtil;

public class LangControl extends Control {
	
	public Boundary en() {
		Locale locale = Locale.ENGLISH;
		HttpUtil.setLocale(super.getRequest(), locale);
		return redirect(getRequest().getContextPath() + "/index");
	}
	
	
	public Boundary ja() {
		Locale locale = Locale.JAPANESE;
		HttpUtil.setLocale(super.getRequest(), locale);
		return redirect(getRequest().getContextPath() + "/index");
	}
	
}
