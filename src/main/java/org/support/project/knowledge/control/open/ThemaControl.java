package org.support.project.knowledge.control.open;

import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class ThemaControl extends Control {

    @Get
    public Boundary list() {
        getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");
        return forward("list.jsp");
    }

    @Get
    public Boundary show() {
        getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");
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
        getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");
        String thema = getPathInfo();
        if (StringUtils.isEmpty(thema)) {
            thema = "flatly";
        }
        if (thema.startsWith("/")) {
            thema = thema.substring(1);
        }
        setAttribute("thema", thema);
        setCookie(SystemConfig.COOKIE_KEY_THEMA, thema);
        return forward("list.jsp");
    }

    @Get
    public Boundary highlight() {
        getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");
        String thema = getPathInfo();
        if (StringUtils.isEmpty(thema)) {
            thema = "dark";
        }
        if (thema.startsWith("/")) {
            thema = thema.substring(1);
        }
        setAttribute("highlight", thema);

        String title = thema.substring(0, 1).toUpperCase() + thema.substring(1);
        setAttribute("title", title);
        return forward("highlight.jsp");
    }

    @Get
    public Boundary style() {
        getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");
        String thema = getPathInfo();
        if (StringUtils.isEmpty(thema)) {
            thema = "dark";
        }
        if (thema.startsWith("/")) {
            thema = thema.substring(1);
        }
        setAttribute("highlight", thema);
        setCookie(SystemConfig.COOKIE_KEY_HIGHLIGHT, thema);
        return forward("list.jsp");
    }

}
