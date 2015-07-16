<%@page import="org.support.project.web.config.CommonWebParameter"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
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
<h4 class="title"><%= jspUtil.label("knowledge.group.add.title") %></h4>

<form action="<%= request.getContextPath()%>/protect.group/update" method="post" role="form">

	<div class="form-group">
		<label for="input_groupName"><%= jspUtil.label("knowledge.group.view.label.groupname") %></label>
		<input type="text" class="form-control" name="groupName" id="input_groupName" placeholder="Group Name" value="<%= jspUtil.out("groupName") %>">
	</div>
	<div class="form-group">
		<label for="input_description"><%= jspUtil.label("knowledge.group.view.label.description") %></label>
		<input type="text" class="form-control" name="description" id="input_description" placeholder="Description" value="<%= jspUtil.out("description") %>">
	</div>

	<div class="form-group">
		<label for="input_content"><%= jspUtil.label("knowledge.group.view.label.public.class") %></label><br/>
		<label class="radio-inline">
			<input type="radio" value="<%= CommonWebParameter.GROUP_CLASS_PUBLIC %>" name="groupClass" 
				id="groupClass_piblic" <%= jspUtil.checked(CommonWebParameter.GROUP_CLASS_PUBLIC, "groupClass", true) %>/>
			<i class="fa fa-globe"></i>&nbsp;<%= jspUtil.label("knowledge.group.edit.label.public") %>
		</label>
		<br/>
		<label class="radio-inline">
			<input type="radio" value="<%= CommonWebParameter.GROUP_CLASS_PROTECT %>" name="groupClass" 
				id="groupClass_protect" <%= jspUtil.checked(CommonWebParameter.GROUP_CLASS_PROTECT, "groupClass") %>/>
			<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.group.edit.label.protect") %>
		</label>
		<br/>
		<label class="radio-inline">
			<input type="radio" value="<%= CommonWebParameter.GROUP_CLASS_PRIVATE %>" name="groupClass" 
				id="groupClass_private" <%= jspUtil.checked(CommonWebParameter.GROUP_CLASS_PRIVATE, "groupClass") %>/>
			<i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.group.edit.label.private") %>
		</label>
	</div>

	<input type="hidden" name="groupId" value="<%= jspUtil.out("groupId") %>">
	<input type="hidden" name="groupKey" value="<%= jspUtil.out("groupKey") %>">

	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
	
	<a href="<%= request.getContextPath() %>/protect.group/view/<%= jspUtil.out("groupId") %>"
	class="btn btn-warning" role="button"><i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.cancel") %></a>

	<a href="<%= request.getContextPath() %>/protect.group/mygroups"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.mygroup") %></a>

	<a href="<%= request.getContextPath() %>/protect.group/list"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.list") %></a>


</form>



</c:param>

</c:import>

