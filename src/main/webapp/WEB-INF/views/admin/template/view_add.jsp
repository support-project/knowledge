<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">

</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.template.add.title") %></h4>

<form action="<%= request.getContextPath()%>/admin.template/create" method="post" role="form">
	<div class="form-group">
		<label for="typeName"><%= jspUtil.label("knowledge.template.label.name") %></label>
		<input type="text" class="form-control" name="typeName" id="typeName" placeholder="Name" value="<%= jspUtil.out("typeName") %>" />
	</div>
	<div class="form-group">
		<label for="typeIcon"><%= jspUtil.label("knowledge.template.label.icon") %></label>
		<input type="text" class="form-control" name="typeIcon" id="typeIcon" placeholder="Icon" value="<%= jspUtil.out("typeIcon") %>" />
	</div>
	<div class="form-group">
		<label for="description"><%= jspUtil.label("knowledge.template.label.description") %></label>
		<textarea class="form-control" name="description" id="description" placeholder="Description" ><%= jspUtil.out("description") %></textarea>
	</div>
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.registration") %></button>
	<a href="<%= request.getContextPath() %>/admin.template/list/<%= jspUtil.out("offset") %>"
		class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
	
</form>

</c:param>

</c:import>

