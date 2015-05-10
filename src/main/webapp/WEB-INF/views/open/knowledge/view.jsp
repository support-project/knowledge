<%@page import="org.support.project.knowledge.entity.KnowledgesEntity"%>
<%@page import="org.support.project.knowledge.entity.CommentsEntity"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-edit.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-view.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view.js") %>"></script>

<script>
var LABEL_LIKE = '<%= jspUtil.label("knowledge.view.like") %>';
</script>

</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.view.title") %></h4>

	<div class="row">
		<div class="col-sm-12">
			<div class="thumbnail">
				<div class="caption">
					<h3>
					[<%= jspUtil.out("knowledgeId") %>] <%= jspUtil.out("title") %>
					</h3>
					
					<c:if test="${!empty tagNames}">
					<p class="tags">
					<input type="text" name="tags" id="input_tags" placeholder="" data-role="tagsinput"
						value="<%= jspUtil.out("tagNames") %>" disabled="disabled"/>
					</p>
					</c:if>
					
					<p>
						<button class="btn btn-warning" onclick="addlike(<%= jspUtil.out("knowledgeId") %>);">
							<i class="fa fa-thumbs-o-up"></i>&nbsp;
							<%= jspUtil.label("knowledge.view.like") %>
						</button>
						
						<a class="btn btn-link" href="<%= request.getContextPath() %>/open.knowledge/likes/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>" >
							<i class="fa fa-thumbs-o-up"></i>&nbsp;
							× <span id="like_count"><%= jspUtil.out("like_count") %></span>
						</a>
						
						<a class="btn btn-link" href="#comments" id="commentsLink">
							<i class="fa fa-comments-o"></i>&nbsp;
							× <%= jspUtil.out("comments.size()") %>
						</a>
					</p>
					
					<p class="insert_info">
						<img src="<%= request.getContextPath()%>/images/loader.gif" 
							data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("insertUser") %>" 
							alt="icon" width="36" height="36" style="float:left" />
						
						<a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %>">
						<i class="fa fa-calendar" style="margin-left: 5px;"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
						</a>
						&nbsp;&nbsp;&nbsp;
						
						<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag", 
								jspUtil.label("label.public.view")) %>
						<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag", 
								jspUtil.label("label.private.view")) %>
								
						<% if(jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "publicFlag")) { %>
							<button class="btn btn-link" onclick="viewProtect(<%= jspUtil.out("knowledgeId") %>);">
								<%= jspUtil.label("label.protect.view") %>
							</button>
							
						<% } %>
						<br/>
						
						<a href="<%= request.getContextPath() %>/open.knowledge/list/0?user=<%= jspUtil.out("insertUser") %>">
						<i class="fa fa-user" style="margin-left: 5px;"></i>&nbsp;<%= jspUtil.out("insertUserName") %>
						</a>
					</p>
					
					<c:forEach var="file" items="${files}" >
						<p>
							<img src="<%= jspUtil.out("file.thumbnailUrl") %>" />
							<a href="<%= jspUtil.out("file.url") %>">
							<%= jspUtil.out("file.name") %>
							</a>
						</p>
					</c:forEach>
					
					<p style="word-break:break-all" id="content" class="markdown">
					</p>
					
				</div>
			</div>
		</div>
	</div>
	
	<% if (request.getRemoteUser() != null) { 
		if (request.isUserInRole("admin") 
			|| jspUtil.out("insertUser").equals(request.getRemoteUser())) { %>
		<a href="<%= request.getContextPath() %>/protect.knowledge/view_edit/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>"
		class="btn btn-primary" role="button"><i class="fa fa-edit"></i>&nbsp;
		<%= jspUtil.label("label.edit") %>
		</a>
	<%	} %>
	<% } else { %>
		<a href="<%= request.getContextPath() %>/protect.knowledge/view_edit/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>"
		class="btn btn-primary" role="button"><i class="fa fa-edit"></i>&nbsp;
		<%= jspUtil.label("knowledge.view.edit.with.login") %>
		</a>
	<% } %>

	<a href="<%= request.getContextPath() %>/open.knowledge/list/<%= jspUtil.out("offset") %><%= jspUtil.out("params") %>"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.view.back.list") %></a>
	
	<hr/>
	<h5 id="comments"><i class="fa fa-comments-o"></i>&nbsp;Comment</h5>
	<c:forEach var="comment" items="${comments}" varStatus="status">
	<%
		CommentsEntity comment = jspUtil.getValue("comment", CommentsEntity.class);
		Integer knowledge = jspUtil.getValue("insertUser", Integer.class);
		if (!comment.getInsertUser().equals(knowledge)) {
	%>
	<div class="row">
		<div class="col-sm-12">
		<%= jspUtil.date("comment.updateDatetime")%> [<%= jspUtil.out("comment.updateUserName") %>]
		</div>
	</div>
	<div class="question_Box">
	<div class="question_image">
		<img src="<%= request.getContextPath()%>/images/loader.gif" 
			data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("comment.updateUser") %>" 
			alt="icon" width="64" height="64"/>
	</div>
	<div class="arrow_question">
	<%= jspUtil.out("comment.comment") %>
	</div><!-- /.arrow_question -->
	</div><!-- /.question_Box -->
	<% } else { %>
	<div class="row">
		<div class="col-sm-12" style="text-align: right;">
		<%= jspUtil.date("comment.updateDatetime")%> [<%= jspUtil.out("comment.updateUserName") %>]
		</div>
	</div>
	<div class="question_Box">
	<div class="answer_image">
		<img src="<%= request.getContextPath()%>/images/loader.gif" 
			data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("comment.updateUser") %>" 
			alt="icon" width="64" height="64"/>
	</div>
	<div class="arrow_answer">
	<%= jspUtil.out("comment.comment") %>
	</div><!-- /.arrow_answer -->
	</div><!-- /.question_Box -->
	<% } %>
	</c:forEach>
	
	<% if (request.getRemoteUser() != null) { %>
		<form action="<%= request.getContextPath()%>/protect.knowledge/comment/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>" method="post" role="form">
		<textarea class="form-control" name="comment" rows="1" placeholder="Comment" id="comment"></textarea>
		<button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i>&nbsp;<%= jspUtil.label("knowledge.view.comment") %></button>
		
		<input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" />
		<input type="hidden" name="keyword" value="<%= jspUtil.out("keyword") %>" />
		<input type="hidden" name="tag" value="<%= jspUtil.out("tag") %>" />
		<input type="hidden" name="user" value="<%= jspUtil.out("user") %>" />
		
		</form>
	<% } else { %>
		<form action="<%= request.getContextPath()%>/protect.knowledge/view/<%= jspUtil.out("knowledgeId") %>" method="get" role="form">
		<button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i>&nbsp;<%= jspUtil.label("knowledge.view.comment.with.login") %></button>
		</form>
	<% } %>
	
<span style="display: none;" id="content_text">
<%= jspUtil.out("content") %>
</span>


</c:param>

</c:import>

