<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload-ui.css" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-edit.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/slide.css") %>" />

