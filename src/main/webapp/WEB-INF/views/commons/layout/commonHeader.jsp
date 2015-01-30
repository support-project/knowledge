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

<meta content="<%= jspUtil.label("knowledge.header.meta.title") %>" name="title">
<meta content="<%= jspUtil.label("knowledge.header.meta.description") %>" name="description">

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">

<link rel="icon" href="<%= request.getContextPath() %>/favicon.ico" type="image/vnd.microsoft.icon" /> 

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/highlightjs/styles/default.css" />

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap/dist/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap/dist/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/font-awesome/css/font-awesome.min.css" />


