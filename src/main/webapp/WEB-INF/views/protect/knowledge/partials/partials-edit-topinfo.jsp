<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<% String hide = "hide"; %>

<% if (!StringUtils.isEmpty(jspUtil.getValue("knowledgeId", Long.class))) { %>
    <% hide = ""; %>
    <div class="form-group title" id="title_msg"><%= jspUtil.label("knowledge.edit.title") %></div>
    <!-- info -->
    <label for="input_no"><%= jspUtil.label("knowledge.edit.label.key") %></label>
    <i class="fa fa-key"></i>&nbsp;<%= jspUtil.out("knowledgeId") %>
     / 
    <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
<% } else { %>
    <div class="form-group title" id="title_msg"><%= jspUtil.label("knowledge.add.title") %></div>
<% } %>

