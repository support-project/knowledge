<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/template.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/template.js") %>"></script>
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
	
	<h5><b>項目</b></h5>
	<div class="form-group">
		<a href="#" class="btn btn-info" id="addText"><i class="fa fa-pencil"></i>&nbsp;テキスト項目追加</a>
		<a href="#" class="btn btn-info" id="addRadio"><i class="fa fa-dot-circle-o"></i>&nbsp;ラジオボタン項目追加</a>
		<a href="#" class="btn btn-info" id="addCheckbox"><i class="fa fa-check-square-o"></i>&nbsp;チェックボックス項目追加</a>
	</div>
	<div id="items"></div>
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.registration") %></button>
	<a href="<%= request.getContextPath() %>/admin.template/list/<%= jspUtil.out("offset") %>"
		class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
	
</form>

</c:param>

</c:import>

