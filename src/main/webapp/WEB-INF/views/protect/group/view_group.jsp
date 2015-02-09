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
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/group.js") %>"></script>
<script>
var _CONFIRM_DELETE = '<%= jspUtil.label("knowledge.group.view.label.confirm.delete") %>';
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.group.view.title") %></h4>

<form action="<%= request.getContextPath()%>/protect.group/view" method="post" role="form" id ="groupForm">

	<div class="form-group">
		<label for="input_groupName"><%= jspUtil.label("knowledge.group.view.label.groupname") %></label>
		<br/>
		<%= jspUtil.out("groupName") %>
	</div>
	<div class="form-group">
		<label for="input_description"><%= jspUtil.label("knowledge.group.view.label.description") %></label>
		<br/>
		<%= jspUtil.out("description") %>
	</div>

	<div class="form-group">
		<label for="input_content"><%= jspUtil.label("knowledge.group.view.label.public.class") %></label>
		<br/>
		<%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PUBLIC, "groupClass", jspUtil.label("knowledge.group.view.label.public")) %>
		<%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PROTECT, "groupClass", jspUtil.label("knowledge.group.view.label.protect")) %>
		<%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PRIVATE, "groupClass", jspUtil.label("knowledge.group.view.label.private")) %>
	</div>

	<c:if test="${ editAble }">
	<div class="form-group">
		<a href="<%= request.getContextPath() %>/protect.group/view_edit/<%= jspUtil.out("groupId") %>" 
			class="btn btn-primary" role="button"><i class="fa fa-edit"></i>&nbsp;<%= jspUtil.label("label.edit") %>
		</a>
		<button type="button" class="btn btn-danger" onclick="deleteGroup();">
			<i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
		</button>
	</div>
	</c:if>
	<input type="hidden" name="groupId" value="<%= jspUtil.out("groupId") %>">
	<input type="hidden" name="groupKey" value="<%= jspUtil.out("groupKey") %>">
</form>

<h4 class="title"><%= jspUtil.label("knowledge.group.view.label.member") %></h4>

<p>
<% if(jspUtil.is(CommonWebParameter.GROUP_CLASS_PROTECT, "groupClass")) { %>
	<%-- 非公開の場合(このページにアクセスしているので、少なくともアクセス権は持っている) --%>
<% } %>

<c:if test="${ belong == false }">
<%-- このグループに所属していない --%>
<% if(jspUtil.is(CommonWebParameter.GROUP_CLASS_PUBLIC, "groupClass")) { %>
	<%-- 公開の場合(自分でユーザを登録できる) --%>
	<a href="<%= request.getContextPath() %>/protect.group/subscribe/<%= jspUtil.out("groupId") %>" 
			class="btn btn-info" role="button"><i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.subscribe.public") %>
	</a>
<% } %>

<% if(jspUtil.is(CommonWebParameter.GROUP_CLASS_PROTECT, "groupClass")) { %>
	<%-- 保護の場合(追加のリクエストは登録できる/管理者はそれを承認できる) --%>
	<a href="<%= request.getContextPath() %>/protect.group/request/<%= jspUtil.out("groupId") %>" 
			class="btn btn-info" role="button"><i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.subscribe.protect") %>
	</a>
<% } %>
</c:if>

<c:if test="${ belong }">
	<a href="<%= request.getContextPath() %>/protect.group/unsubscribe/<%= jspUtil.out("groupId") %>" 
			class="btn btn-danger" role="button"><i class="fa fa-sign-out"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.unsubscribe") %>
	</a>
</c:if>
<p>

<div class="list-group">
<c:if test="${empty users}">
<%= jspUtil.label("knowledge.accept.label.list.empty") %>
</c:if>

<c:forEach var="user" items="${users}" varStatus="status">
	<div class="list-group-item">
		<h4 class="list-group-item-heading"><%= jspUtil.out("user.userName") %></h4>
		<p class="list-group-item-text">
			<%= jspUtil.label("label.status") %>: 
			<%= jspUtil.is(CommonWebParameter.GROUP_ROLE_WAIT, "user.groupRole", jspUtil.label("knowledge.group.view.label.role.wait")) %>
			<%= jspUtil.is(CommonWebParameter.GROUP_ROLE_ADMIN, "user.groupRole", jspUtil.label("knowledge.group.view.label.role.admin")) %>
			<%= jspUtil.is(CommonWebParameter.GROUP_ROLE_MEMBER, "user.groupRole", jspUtil.label("knowledge.group.view.label.role.member")) %>
			
			&nbsp;&nbsp;&nbsp;&nbsp;
		<% if (jspUtil.is(Boolean.TRUE, "editAble")) { %>
			<% if(jspUtil.is(CommonWebParameter.GROUP_ROLE_WAIT, "user.groupRole")) { %>
			<a href="<%= request.getContextPath() %>/protect.group/accept/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>" class="btn btn-primary">
				<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.accept.label.accept") %>
			</a>
			<% } else { %>
			<a href="<%= request.getContextPath() %>/protect.group/change/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>&status=<%= CommonWebParameter.GROUP_ROLE_ADMIN %>" 
				class="btn btn-default btn-sm">
				<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.change.admin") %>
			</a>
			<a href="<%= request.getContextPath() %>/protect.group/change/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>&status=<%= CommonWebParameter.GROUP_ROLE_MEMBER %>" 
				class="btn btn-default btn-sm">
				<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.change.member") %>
			</a>
			<a href="<%= request.getContextPath() %>/protect.group/change/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>&status=0" 
				class="btn btn-default btn-sm">
				<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.change.none") %>
			</a>
			<% } %>
		<% } %>
		</p>
	</div>
		
</c:forEach>
</div>


	<a href="<%= request.getContextPath() %>/protect.group/mygroups"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.mygroup") %></a>

	<a href="<%= request.getContextPath() %>/protect.group/list"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.list") %></a>
	
</c:param>

</c:import>

