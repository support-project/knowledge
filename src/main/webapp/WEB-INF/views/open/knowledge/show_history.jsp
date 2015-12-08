<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-list.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-list.js") %>"></script>
</c:param>

<c:param name="PARAM_CONTENT">
	
	<!-- Title -->
	<div class="row">
		<ul class="nav nav-tabs">
			<li role="presentation"><a href="<%= request.getContextPath() %>/open.knowledge/list"><%= jspUtil.label("knowledge.list.kind.list") %></a></li>
<%--		<li role="presentation"><a href="#"><%= jspUtil.label("knowledge.list.kind.popular") %></a></li> --%>
<%--			<li role="presentation"><a href="#"><%= jspUtil.label("knowledge.list.kind.stock") %></a></li> --%>
			<li role="presentation" class="active"><a href="<%= request.getContextPath() %>/open.knowledge/show_history"><%= jspUtil.label("knowledge.list.kind.history") %></a></li>
		</ul>
	</div>
	
	
	
	
	<div class="row" id="knowledgeList">
		<div class="col-sm-12 col-md-8">
		
		<c:if test="${empty histories}">
		<%= jspUtil.label("knowledge.list.empty") %>
		</c:if>
		
		<c:forEach var="knowledge" items="${histories}" varStatus="status">
			<div class="thumbnail" 
				onclick="showKnowledge('<%= jspUtil.out("knowledge.knowledgeId") %>',
					'<%= jspUtil.out("offset") %>', '<%= jspUtil.out("keyword") %>',
					'<%= jspUtil.out("tag") %>', '<%= jspUtil.out("user") %>');">
				<div class="discription" id="discription_<%= jspUtil.out("knowledge.knowledgeId") %>">
					<i class="fa fa-check-square-o"></i>&nbsp;show!
				</div>
				<div class="caption">
					<h4>
						<%= jspUtil.out("knowledge.title", JspUtil.ESCAPE_CLEAR) %>
					</h4>
					<div class="insert_info">
					<div style="float:left">
					<img src="<%= request.getContextPath()%>/images/loader.gif" 
						data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("knowledge.insertUser") %>" 
						alt="icon" width="36" height="36" style="float:left"/>
					</div>
					<div>
					<%= jspUtil.label("knowledge.list.info.insert", jspUtil.out("knowledge.insertUserName"), jspUtil.date("knowledge.updateDatetime")) %>
					<br/>
					<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "knowledge.publicFlag", 
							jspUtil.label("label.public.view")) %>
					<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "knowledge.publicFlag", 
							jspUtil.label("label.private.view")) %>
					<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "knowledge.publicFlag", 
							jspUtil.label("label.protect.view")) %>
					<c:if test="${targets.containsKey(knowledge.knowledgeId)}">
						<c:forEach var="target" items="${ targets.get(knowledge.knowledgeId) }">
							<span class="tag label label-info"><%= jspUtil.out("target.label") %></span>
						</c:forEach>
					</c:if>
					&nbsp;&nbsp;&nbsp;
					<c:if test="${!empty knowledge.tagNames}">
						<i class="fa fa-tags"></i>
						<c:forEach var="tagName" items="${knowledge.tagNames.split(',')}">
							<span class="tag label label-info"><%= jspUtil.out("tagName") %></span>
						</c:forEach>
					</c:if>
					</div>
					</div>
					
					<div class="item-info">
						<i class="fa fa-thumbs-o-up" style="margin-left: 5px;"></i>&nbsp;× <span id="like_count"><%= jspUtil.out("knowledge.likeCount") %></span>
						&nbsp;&nbsp;&nbsp;
						<i class="fa fa-comments-o"></i>&nbsp;× <%= jspUtil.out("knowledge.commentCount") %>
						&nbsp;&nbsp;&nbsp;
					</div>
					<c:if test="${!empty keyword}">
					<p style="word-break:break-all" class="content">
					<%= jspUtil.out("knowledge.content", JspUtil.ESCAPE_CLEAR, 300) %>
					</p>
					</c:if>
				</div>
			</div>
		</c:forEach>
		</div>
	
	
	
	
		<!-- Sub List -->
		<div class="col-sm-12 col-md-4">
		
		<h5>- <i class="fa fa-group"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.group") %> - </h5>
		<c:choose>
		<c:when test="${groups != null}">
		<div class="list-group">
			<c:forEach var="group_item" items="${groups}">
			<a class="list-group-item"
				href="<%= request.getContextPath() %>/open.knowledge/list?group=<%= jspUtil.out("group_item.groupId") %>" >
				<span class="badge"><%= jspUtil.out("group_item.groupKnowledgeCount") %></span>
				<i class="fa fa-group"></i>&nbsp;<%= jspUtil.out("group_item.groupName") %>
			</a>
			</c:forEach>
		</div>
		<div style="width: 100%;text-align: right;">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups">
				<i class="fa fa-group"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.group.list") %>
			</a>&nbsp;&nbsp;&nbsp;
		</div>
		</c:when>
		<c:otherwise>
		<%= jspUtil.label("knowledge.list.info.group") %>
		<div style="width: 100%;text-align: right;">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups">
				<i class="fa fa-group"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.group.list") %>
			</a>&nbsp;&nbsp;&nbsp;
		</div>
		</c:otherwise>
		</c:choose>
		<br/>
		
		<h5>- <i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("knowledge.list.popular.tags") %> - </h5>
		<div class="list-group">
		<c:forEach var="tag_item" items="${tags}">
			<a class="list-group-item"
				href="<%= request.getContextPath() %>/open.knowledge/list?tag=<%= jspUtil.out("tag_item.tagId") %>" >
				<span class="badge"><%= jspUtil.out("tag_item.knowledgeCount") %></span>
				<i class="fa fa-tag"></i>&nbsp;<%= jspUtil.out("tag_item.tagName") %>
			</a>
		</c:forEach>
		</div>
		<div style="width: 100%;text-align: right;">
			<a href="<%= request.getContextPath() %>/open.tag/list">
				<i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("knowledge.list.tags.list") %>
			</a>&nbsp;&nbsp;&nbsp;
		</div>
		</div>
	
	</div>
	
	
</c:param>

</c:import>

	
	