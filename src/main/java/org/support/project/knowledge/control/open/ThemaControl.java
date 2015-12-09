package org.support.project.knowledge.control.open;

import javax.servlet.http.Cookie;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance=Instance.Prototype)
public class ThemaControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(ThemaControl.class);
	
	@Get
	public Boundary list() {
		return forward("list.jsp");
	}

	@Get
	public Boundary show() {
		String thema = getPathInfo();
		if (StringUtils.isEmpty(thema)) {
			thema = "flatly";
		}
		if (thema.startsWith("/")) {
			thema = thema.substring(1);
		}
		setAttribute("thema", thema);
		
		String title = thema.substring(0, 1).toUpperCase() + thema.substring(1);
		setAttribute("title", title);
		return forward("thema.jsp");
	}

	@Get
	public Boundary enable() {
		String thema = getPathInfo();
		if (StringUtils.isEmpty(thema)) {
			thema = "flatly";
		}
		if (thema.startsWith("/")) {
			thema = thema.substring(1);
		}
		setAttribute("thema", thema);
		
		Cookie cookie = new  Cookie("KNOWLEDGE_THEMA", thema);
		cookie.setPath(getRequest().getContextPath());
		cookie.setMaxAge(KnowledgeControl.COOKIE_AGE);
		getResponse().addCookie(cookie);
		
		return forward("list.jsp");
	}
	
	
	
	
}
