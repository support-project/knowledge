<%@page import="org.support.project.knowledge.control.Control"%>
<%@page import="org.support.project.knowledge.entity.ServiceConfigsEntity"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="java.util.List"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.logic.SanitizingLogic"%>
<%@page import="org.support.project.web.bean.LabelValue"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.knowledge.entity.ServiceLocaleConfigsEntity"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% String top = "/open.knowledge/list"; %>
<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <% 
            String icon = "fa-book";
            ServiceConfigsEntity serviceConfig = SystemConfig.getServiceConfigsEntity();
            if (serviceConfig != null && StringUtils.isNotEmpty(serviceConfig.getServiceIcon())) {
                icon = serviceConfig.getServiceIcon();
            }
            %>
            <a class="navbar-brand"
                href="<%= request.getContextPath() %><%= top %>"
                style="cursor: pointer;"> <i class="fa <%= icon %>"></i>&nbsp;
                <% 
                if (serviceConfig != null && StringUtils.isNotEmpty(serviceConfig.getServiceLabel())) { %>
                    <%= SanitizingLogic.get().sanitize(serviceConfig.getServiceLabel()) %>
                <% } else { %>
                    <%=jspUtil.label("knowledge.navbar.title") %>
                <% } %>
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <form class="nav navbar-nav navbar-form " role="search"
                action="<%= request.getContextPath() %><%= top %>">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="<%= jspUtil.label("knowledge.navbar.search.placeholder") %>"
                        name="keyword" id="navSearch" value="<%= jspUtil.out("searchKeyword") %>" />
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>


            <ul class="nav navbar-nav navbar-right">
                <li class="navButton navAddButton">
                    <div class="btn-group">
                        <button type="button" onclick="location.href='<%= request.getContextPath() %>/protect.knowledge/view_add<%= jspUtil.out("params") %>'" class="btn btn-info" id="navAddButtonLink" tabindex="-1">
                            <i class="fa fa-plus-circle"></i>&nbsp;
                            <span class="navListButtonText"><%= jspUtil.label("knowledge.navbar.add.knowledge") %></span>
                        </button>
                        <a href="#" class="btn btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a class="dropdown-item" href="<%= request.getContextPath() %>/protect.knowledge/view_add<%= jspUtil.out("params") %>">
                                    <i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.add.knowledge") %>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="<%=request.getContextPath()%>/protect.draft/list">
                                    <i class="fa fa-database"></i>&nbsp;<%=jspUtil.label("knowledge.draft.list.title")%>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="navButton navListButton">
                    <div class="btn-group">
                        <a href="<%=request.getContextPath()%>/protect.stock/mylist" class="btn btn-warning" id="navListButtonLink">
                            <i class="fa fa-star-o"></i>&nbsp;
                            <span class="navListButtonText"><%=jspUtil.label("knowledge.navbar.account.mystock")%></span>
                        </a>
                    </div>
                </li>
                
                <% if (!jspUtil.logined()) { %>
                <li class="navButton navMenuButton">
                    <div class="btn-group">
                        <a href="#" class="btn btn-default dropdown-toggle" id="navMenuButtonLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.id() %>" alt="icon" width="15" height="15"/>
                            <span class="caret"></span>
                        </a>
                        <!-- 
                        <a href="#" class="btn btn-default dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                        </a>
                        -->
                        <ul class="dropdown-menu" role="menu">
                            <li >
                                <a href="<%= request.getContextPath() %>/open.knowledge/list" >
                                    <i class="fa fa-list"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.list.knowledge") %>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li >
                                <a href="<%= request.getContextPath() %>/open.knowledge/search" >
                                    <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.search") %>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a id="menuSignin" href="<%= request.getContextPath() %>/signin?page=<%= top %>" style="cursor: pointer;">
                                    <i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signin") %>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <% } else { %>
                <li class="navButton navLoginedMenuButton">
                    <div class="btn-group">
                        <a href="#" class="btn btn-success dropdown-toggle" id="navMenuButtonLink" data-toggle="dropdown">
                            <img src="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.id() %>" alt="icon" width="15" height="15"/>
                            <small>
                            <span class="badge badge-pill"><%= jspUtil.out(Control.NOTIFY_UNREAD_COUNT) %></span>
                            </small>
                            <span class="caret"></span>
                        </a>
                        <!--
                        <a href="#" class="btn btn-success dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                        </a>
                        -->
                        <ul class="dropdown-menu" role="menu">
                            <li >
                                <a href="<%= request.getContextPath() %>/open.knowledge/list" >
                                    <i class="fa fa-list"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.list.knowledge") %>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li >
                                <a href="<%= request.getContextPath() %>/protect.notification/list" >
                                    <i class="fa fa-bullhorn"></i>&nbsp;<%= jspUtil.label("knowledge.notification.title") %>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li >
                                <a href="<%= request.getContextPath() %>/open.knowledge/search" >
                                    <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.search") %>
                                </a>
                            </li>
                            <% if (request.isUserInRole("admin")) { %>
                            <li class="divider"></li>
                            <li >
                                <a href="<%= request.getContextPath() %>/admin.systemconfig/index" >
                                    <i class="fa fa-cogs" ></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.system") %>
                                </a>
                            </li>
                            <% } %>
                            <% if (jspUtil.logined()) { %>
                            <li class="divider"></li>
                            <li >
                                <a href="<%= request.getContextPath() %>/protect.config/index" >
                                    <i class="fa fa-cog" ></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config") %>
                                </a>
                            </li>
                            <% } %>
                            <li class="divider"></li>
                            <li >
                                <a id="menuSignout" href="<%= request.getContextPath() %>/signout" style="cursor: pointer;">
                                    <i class="fa fa-sign-out"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signout") %>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <% } %>
            </ul>
            
            
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

