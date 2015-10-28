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
<script>
var LABEL_DELETE = '<%= jspUtil.label("knowledge.template.label.item.delete") %>';
var LABEL_TEXT_ITEM = '<i class="fa fa-pencil"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.text") %>';
var LABEL_RADIO_ITEM = '<i class="fa fa-dot-circle-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.radio") %>';
var LABEL_CHECKBOX_ITEM = '<i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.checkbox") %>';
var LABEL_ITEM_TITLE = '<%= jspUtil.label("knowledge.template.label.item.title") %>';
var LABEL_ITEM_DESCRIPTION = '<%= jspUtil.label("knowledge.template.label.item.description") %>';
var LABEL_ADD_CHOICE = '<%= jspUtil.label("knowledge.template.label.choice.add") %>';
var LABEL_DELETE_CHOICE = '<%= jspUtil.label("knowledge.template.label.choice.remove") %>';
var LABEL_CHOICE_LABEL = '<%= jspUtil.label("knowledge.template.label.choice.label") %>';
var LABEL_CHOICE_VALUE = '<%= jspUtil.label("knowledge.template.label.choice.value") %>';
var LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
</script>
<script>
function deleteUser() {
	bootbox.confirm("本当に削除しますか?", function(result) {
		if (result) {
			$('#templateForm').attr('action', '<%= request.getContextPath()%>/admin.template/delete');
			$('#templateForm').submit();
		}
	}); 
};
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.template.edit.title") %></h4>

<form action="<%= request.getContextPath()%>/admin.template/update" method="post" role="form" id="templateForm">
	<c:if test="${!editable}">
	<div class="alert alert-info alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<strong>Information</strong><br/>
		- <%= jspUtil.label("knowledge.template.label.not.editable") %>
	</div>
	
	</c:if>

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
	
	<h5><b><%= jspUtil.label("knowledge.template.label.item") %></b></h5>
	<div class="form-group">
		<a href="#" class="btn btn-info" id="addText"><i class="fa fa-pencil"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.text.add") %></a>
		<a href="#" class="btn btn-info" id="addRadio"><i class="fa fa-dot-circle-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.radio.add") %></a>
		<a href="#" class="btn btn-info" id="addCheckbox"><i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.checkbox.add") %></a>
	</div>
	<div id="items"></div>
	
	<input type="hidden" name="typeId" value="<%= jspUtil.out("typeId") %>" id="typeId"/>
	
	<button type="submit" class="btn btn-primary" id="savebutton"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %></button>
	<c:if test="${editable}">
	<button type="button" class="btn btn-danger" onclick="deleteUser();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
	</c:if>
	<a href="<%= request.getContextPath() %>/admin.template/list/<%= jspUtil.out("offset") %>"
		class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
	
</form>

</c:param>

</c:import>

