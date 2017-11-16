<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-user-list.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.accept.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>


<div class="list-group">
<c:if test="${empty entries}">
<%= jspUtil.label("knowledge.accept.label.list.empty") %>
</c:if>

<c:forEach var="entry" items="${entries}" varStatus="status">
    <div class="list-group-item">
        <div class="list-group-item-heading">
            <%= jspUtil.out("entry.userName") %> (<%= jspUtil.out("entry.userKey") %>)
            <a href="<%= request.getContextPath() %>/admin.users/accept_delete/<%= jspUtil.out("entry.id") %>" class="pull-right btn btn-xs btn-danger">
                <i class="fa fa-delete"></i>&nbsp;<%= jspUtil.label("knowledge.accept.label.delete") %>
            </a>
            <span class="pull-right">&nbsp;</span>
            <a href="<%= request.getContextPath() %>/admin.users/accept/<%= jspUtil.out("entry.id") %>" class="pull-right btn btn-xs btn-primary">
                <i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.accept.label.accept") %>
            </a>
        </div>
        <p class="list-group-item-text">
        <%= jspUtil.label("label.regist.datetime") %>
            <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("user.insertDatetime")%> / 
        </p>
    </div>
        
</c:forEach>
</div>




</c:param>

</c:import>

