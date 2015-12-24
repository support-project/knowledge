<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<!-- List -->
<div class="col-sm-12 col-md-8 knowledge_list">

	<c:if test="${empty list_data}">
	<%= jspUtil.label("knowledge.list.empty") %>
	</c:if>
	
	<c:forEach var="knowledge" items="${list_data}" varStatus="status">
		<div class="knowledge_item">
			<h4>
				<a href="<%= request.getContextPath()%>/open.knowledge/view/<%= jspUtil.out("knowledge.knowledgeId") %><%= jspUtil.out("params") %>">
					<%= jspUtil.out("knowledge.title", JspUtil.ESCAPE_CLEAR) %>
				</a>
			</h4>
			
			<div class="insert_info">
				<div style="float:left">
				<img src="<%= request.getContextPath()%>/images/loader.gif"
					data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("knowledge.insertUser") %>"
					alt="icon" width="36" height="36" style="float:left"/>
				</div>
				<div>
				<%= jspUtil.label("knowledge.list.info.insert", jspUtil.out("knowledge.updateUserName"), jspUtil.date("knowledge.updateDatetime")) %>
				<br/>
				<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "knowledge.publicFlag",
						jspUtil.label("label.public.view")) %>
				<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "knowledge.publicFlag",
						jspUtil.label("label.private.view")) %>
				<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "knowledge.publicFlag",
						jspUtil.label("label.protect.view")) %>
				<c:if test="${targets.containsKey(knowledge.knowledgeId)}">
					<c:forEach var="target" items="${ targets.get(knowledge.knowledgeId) }">
						<c:choose>
							<c:when test="${targetLogic.isGroupLabel(target.value)}">
								<c:set var="groupId" value="${targetLogic.getGroupId(target.value)}"/>
								<a class="target" href="<%= request.getContextPath()%>/open.knowledge/list?group=<%= jspUtil.out("groupId") %>">
									<span class="tag label label-info"><%= jspUtil.out("target.label") %></span>
								</a>
							</c:when>
							<c:when test="${targetLogic.isUserLabel(target.value)}">
								<c:set var="userId" value="${targetLogic.getUserId(target.value)}"/>
								<a class="target" href="<%= request.getContextPath()%>/open.knowledge/list?user=<%= jspUtil.out("userId") %>">
									<span class="tag label label-info"><%= jspUtil.out("target.label") %></span>
								</a>
							</c:when>
							<c:otherwise>
								<a class="target" href="#">
									<span class="tag label label-info"><%= jspUtil.out("target.label") %></span>
								</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
				&nbsp;&nbsp;&nbsp;
				<c:if test="${!empty knowledge.tagNames}">
					<i class="fa fa-tags"></i>
					<c:forEach var="tagName" items="${knowledge.tagNames.split(',')}">
						<a class="target" href="<%= request.getContextPath()%>/open.knowledge/list?tagNames=<%= jspUtil.out("tagName") %>">
							<span class="tag label label-info"><%= jspUtil.out("tagName") %></span>
						</a>
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

			<div class="ite_caption">
				<c:if test="${!empty keyword}">
				<p style="word-break:break-all" class="content">
				<%= jspUtil.out("knowledge.content", JspUtil.ESCAPE_CLEAR, 300) %>
				</p>
				</c:if>
			</div>
		</div>
	</c:forEach>
	
</div>


	