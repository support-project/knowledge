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
		<a href="<%= request.getContextPath()%>/open.knowledge/view/<%= jspUtil.out("knowledge.knowledgeId") %><%= jspUtil.out("params") %>">
			<div class="knowledge_item">
			
				<h4><%= jspUtil.out("knowledge.title", JspUtil.ESCAPE_CLEAR) %></h4>
				
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
				
				<div class="ite_caption">
					<c:if test="${!empty keyword}">
					<p style="word-break:break-all" class="content">
					<%= jspUtil.out("knowledge.content", JspUtil.ESCAPE_CLEAR, 300) %>
					</p>
					</c:if>
				</div>
			</div>
			
		</a>
	</c:forEach>
	
</div>


	