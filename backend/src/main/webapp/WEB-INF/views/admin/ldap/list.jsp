<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="java.util.List"%>
<%@page import="org.support.project.common.util.HtmlUtils"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.entity.LdapConfigsEntity"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-system-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.ldap.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<a href="<%= request.getContextPath() %>/admin.ldap/config" class="btn btn-info"><i class="fa fa-plus-circle"></i>&nbsp;
    <%= jspUtil.label("label.add") %>
</a>

<div class="list-group">
<% List<LdapConfigsEntity> configs = (List<LdapConfigsEntity>) request.getAttribute("configs"); %>
<% for (LdapConfigsEntity ldapConfig : configs) { %>
    <a href="<%= request.getContextPath() %>/admin.ldap/config?key=<%= ldapConfig.getSystemName() %>" class="list-group-item">
        <%= ldapConfig.getDescription() %>
    </a>
<% } %>
</div>

</c:param>
</c:import>

