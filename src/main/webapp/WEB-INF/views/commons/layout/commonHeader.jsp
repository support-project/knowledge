<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

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
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/highlightjs/styles/default.css" />
<%--
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap/dist/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap/dist/css/bootstrap-theme.min.css" />
--%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch-dist/css/bootstrap.min.css" />

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-oembed-all/jquery.oembed.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/flag-icon-css/css/flag-icon.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/notifyjs/dist/styles/metro/notify-metro.css" />

<!--[if lt IE 9]>
    <script src="<%= request.getContextPath() %>/bower/html5shiv/dist/html5shiv.min.js"></script>
    <script src="<%= request.getContextPath() %>/bower/respond/dest/respond.min.js"></script>
<![endif]-->

<!-- Knowledge - <%= jspUtil.label("label.version") %> -->
