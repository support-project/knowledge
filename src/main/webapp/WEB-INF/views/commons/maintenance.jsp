<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="imagetoolbar" content="no" />

<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">

<meta content="<%= jspUtil.label("knowledge.header.meta.title") %>" name="title">
<meta content="<%= jspUtil.label("knowledge.header.meta.description") %>" name="description">
<meta content='ナレッジマネジメント,ナレッジベース,情報共有,オープンソース,KnowledgeBase,KnowledgeManagement,OSS,Markdown' name='keywords'>
<meta property="og:type" content="article"/>
<meta property="og:title" content="<%= jspUtil.label("knowledge.header.meta.title") %>"/>
<meta property="og:description" content="<%= jspUtil.label("knowledge.header.meta.description") %>" />
<%-- <meta property="og:image" content="" /> --%>
<meta property="og:url" content="https://support-project.org/knowledge/index" />
<meta property="og:site_name" content="Knowledge"/>

<link rel="icon" href="<%= request.getContextPath() %>/favicon.ico" type="image/vnd.microsoft.icon" /> 

<% if (StringUtils.isNotEmpty(jspUtil.out("thema"))) { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch/<%= jspUtil.out("thema") %>/bootstrap.min.css" />
<% } else { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch/<%= jspUtil.cookie(SystemConfig.COOKIE_KEY_THEMA, "flatly") %>/bootstrap.min.css" />
<% } %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/flag-icon-css/css/flag-icon.min.css" />

<!-- Knowledge - <%= jspUtil.label("label.version") %> -->

</head>

<body class="container">
<br/>

<div class="jumbotron">
  <h1>
  <i class="fa fa-spin fa-1x fa-cog" aria-hidden="true"></i>
  </h1>
  <p>
  Site is down for maintenance
  </p>
</div>

<hr/>

<% if(jspUtil.logined()) { %>
    <% if (jspUtil.isAdmin()) { %>
        <a href="<%= request.getContextPath() %>/protect.migrate">
            <i class="fa fa-cog" aria-hidden="true"></i><%= jspUtil.label("knowledge.maintenance.do.migrate") %>
        </a><br/>
    <% } else { %>
    <% } %>
    <a href="<%= request.getContextPath() %>/signout">
        <i class="fa fa-sign-out"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signout") %>
    </a>
<% } else { %>
    <a href="<%= request.getContextPath() %>/protect.migrate">
        <i class="fa fa-cog" aria-hidden="true"></i><%= jspUtil.label("knowledge.maintenance.do.migrate.no.login") %>
    </a>
<% } %>


<!-- build:js(src/main/webapp) js/page-maintenance.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bluebird/js/browser/bluebird.min.js"></script>
<!-- endbuild -->

</body>

</html>