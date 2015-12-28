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
<h4 class="title"><%= jspUtil.label("knowledge.group.mylist.title") %> <span style="font-size: 14px">page[<%= jspUtil.getValue("offset", Integer.class) + 1 %>]</span></h4>

<a class="btn btn-info" href="<%= request.getContextPath() %>/protect.group/view_add" >
<i class="fa fa-plus"></i>&nbsp;<%= jspUtil.label("knowledge.group.mylist.label.add") %>
</a>
<a class="btn btn-success" href="<%= request.getContextPath() %>/protect.group/list" >
<i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.group.mylist.label.list") %>
</a>

<nav>
	<ul class="pager">
		<li class="previous">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups/<%= jspUtil.out("previous") %>"><span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %></a>
		</li>
		<li class="next">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups/<%= jspUtil.out("next") %>"><%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span></a>
		</li>
	</ul>
</nav>


<div class="list-group">
<c:if test="${empty groups}">
<%= jspUtil.label("knowledge.group.mylist.label.empty") %>
</c:if>

<c:forEach var="group" items="${groups}" varStatus="status">
	<a href="<%= request.getContextPath() %>/protect.group/view/<%= jspUtil.out("group.groupId") %>?listoffset=<%= jspUtil.out("offset") %>" class="list-group-item">
		<h4 class="list-group-item-heading">
		<%= jspUtil.out("group.groupName") %>
		<c:if test="${ group.editAble }">
		<span style="font-size: 12px"><%= jspUtil.label("knowledge.group.mylist.label.admin") %></span>
		</c:if>
		</h4>
		<p class="list-group-item-text">
		<%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PUBLIC, "group.groupClass", jspUtil.label("label.public.view")) %>
		<%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PROTECT, "group.groupClass", jspUtil.label("label.protect.view")) %>
		<%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PRIVATE, "group.groupClass", jspUtil.label("label.private.view")) %>
		
		<%= jspUtil.is(CommonWebParameter.GROUP_ROLE_ADMIN, "group.status", jspUtil.label("knowledge.group.mylist.label.admin")) %>
		<%= jspUtil.is(CommonWebParameter.GROUP_ROLE_MEMBER, "group.status", jspUtil.label("knowledge.group.mylist.label.member")) %>
		<%= jspUtil.is(CommonWebParameter.GROUP_ROLE_WAIT, "group.status", jspUtil.label("knowledge.group.mylist.label.wait")) %>
		<span class="badge pull-right"><%= jspUtil.out("group.groupKnowledgeCount") %></span>
		</p>
		<p>
		<c:choose>
		<c:when test="${ '' != group.description }">
		<%= jspUtil.out("group.description") %>
		</c:when>
		<c:otherwise>
		&nbsp;
		</c:otherwise>
		</c:choose>
		</p>
	</a>
</c:forEach>
</div>


<nav>
	<ul class="pager">
		<li class="previous">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups/<%= jspUtil.out("previous") %>"><span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %></a>
		</li>
		<li class="next">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups/<%= jspUtil.out("next") %>"><%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span></a>
		</li>
	</ul>
</nav>


</c:param>

</c:import>

