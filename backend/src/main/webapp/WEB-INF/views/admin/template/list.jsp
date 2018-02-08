<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-template-list.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.navbar.config.admin.template") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<nav>
    <ul class="pager">
        <li >
            <a href="<%= request.getContextPath() %>/admin.template/edit">
                <i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("label.add") %>
            </a>
        </li>
    </ul>
</nav>


<div class="list-group">
<c:if test="${empty templates}">
<%= jspUtil.label("knowledge.template.list.label.empty") %>
</c:if>

<c:forEach var="template" items="${templates}" varStatus="status">
    <a href="<%= request.getContextPath() %>/admin.template/edit/<%= jspUtil.out("template.typeId") %>" class="list-group-item">
        <h4 class="list-group-item-heading">
            <i class="fa <%= jspUtil.out("template.typeIcon") %>"></i>&nbsp;<%= jspUtil.out("template.typeName") %>
        </h4>
        <p class="list-group-item-text">
        <%= jspUtil.out("template.description") %>
        </p>
        
    </a>
</c:forEach>
</div>




</c:param>

</c:import>

