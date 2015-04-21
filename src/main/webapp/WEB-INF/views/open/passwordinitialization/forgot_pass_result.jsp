<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.logic.SystemConfigLogic"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.auth.title.forgot.password") %></h4>
	
	<%= jspUtil.label("knowledge.auth.msg.accept.request") %>
	
</c:param>

</c:import>

