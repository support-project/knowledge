<%@page import="redcomet.knowledge.entity.KnowledgesEntity"%>
<%@page import="redcomet.knowledge.entity.CommentsEntity"%>
<%@page import="redcomet.knowledge.logic.KnowledgeLogic"%>
<%@page import="redcomet.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-edit.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-view.css" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/knowledge-view.js"></script>
</c:param>



<c:param name="PARAM_CONTENT">

	<div class="row">
		<div class="col-sm-12">
			<div class="thumbnail">
				<div class="caption">
					<h3>[${knowledgeId}] ${title}</h3>
					
					<c:if test="${!empty tags}">
					<p class="tags">
					<input type="text" name="tags" id="input_tags" placeholder="" data-role="tagsinput" value="${tags}" disabled="disabled"/>
					</p>
					</c:if>
					
					<p>
						<button class="btn btn-link" onclick="addlike(${knowledgeId});">
							<i class="fa fa-thumbs-o-up"></i>&nbsp;参考になった！ × <span id="like_count">${like_count}</span>
						</button>
					</p>
					
					<p class="insert_info">
						<i class="fa fa-user"></i>&nbsp;${insertUserName}
						&nbsp;&nbsp;&nbsp;
						<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
						&nbsp;&nbsp;&nbsp;
						<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag", 
								"<i class=\"fa fa-globe\"></i>&nbsp;[公開]") %>
						<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag", 
								"<i class=\"fa fa-lock\"></i>&nbsp;[非公開]") %>
					</p>
					
					<c:forEach var="file" items="${files}" >
						<p>
							<img src="${ file.thumbnailUrl }" />
							<a href="${ file.url }">
							<c:out value="${file.name}"></c:out>
							</a>
						</p>
					</c:forEach>
					
					<p style="word-break:break-all" id="content">
					</p>
					
				</div>
			</div>
		</div>
	</div>
	
	<% if (request.getRemoteUser() != null) { 
		if (request.isUserInRole("admin") 
			|| jspUtil.out("insertUser").equals(request.getRemoteUser())) { %>
		<a href="<%= request.getContextPath() %>/protect.knowledge/view_edit/${knowledgeId}?offset=${offset}&keyword=${keyword}&tag=${tag}"
		class="btn btn-primary" role="button"><i class="fa fa-edit"></i>&nbsp;
		編集
		</a>
	<%	} %>
	<% } else { %>
		<a href="<%= request.getContextPath() %>/protect.knowledge/view_edit/${knowledgeId}?offset=${offset}&keyword=${keyword}&tag=${tag}"
		class="btn btn-primary" role="button"><i class="fa fa-edit"></i>&nbsp;
		編集(サインイン)
		</a>
	<% } %>

	<a href="<%= request.getContextPath() %>/open.knowledge/list/${offset}?keyword=${keyword}&tag=${tag}"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;一覧へ戻る</a>
	
	<hr/>
	<h5><i class="fa fa-comments-o"></i>&nbsp;Comment</h5>
	<c:forEach var="comment" items="${comments}" varStatus="status">
	<%
		CommentsEntity comment = jspUtil.getValue("comment", CommentsEntity.class);
		Integer knowledge = jspUtil.getValue("insertUser", Integer.class);
		if (!comment.getInsertUser().equals(knowledge)) {
	%>
	<div class="row">
		<div class="col-sm-12">
		<%= jspUtil.date("comment.updateDatetime")%> [${comment.updateUserName}]
		</div>
	</div>
	<div class="question_Box">
	<div class="question_image"><img src="<%= request.getContextPath() %>/images/icon/110197.png" alt="107039.png" width="90" height="90"/></div>
	<div class="arrow_question">
	${comment.comment}
	</div><!-- /.arrow_question -->
	</div><!-- /.question_Box -->
	<% } else { %>
	<div class="row">
		<div class="col-sm-12" style="text-align: right;">
		<%= jspUtil.date("comment.updateDatetime")%> [${comment.updateUserName}]
		</div>
	</div>
	<div class="question_Box">
	<div class="answer_image"><img src="<%= request.getContextPath() %>/images/icon/107039.png" alt="110197.png" width="90" height="90"/></div>
	<div class="arrow_answer">
	${comment.comment}
	</div><!-- /.arrow_answer -->
	</div><!-- /.question_Box -->
	<% } %>
	</c:forEach>
	
	<% if (request.getRemoteUser() != null) { %>
		<form action="<%= request.getContextPath()%>/protect.knowledge/comment/${knowledgeId}" method="post" role="form">
		<textarea class="form-control" name="comment" rows="1" placeholder="Comment" id="comment"></textarea>
		<button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i>&nbsp;コメントを追加</button>
		</form>
	<% } else { %>
		<form action="<%= request.getContextPath()%>/protect.knowledge/view/${knowledgeId}" method="get" role="form">
		<button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i>&nbsp;コメントを追加(サインイン)</button>
		</form>
	<% } %>
	
<span style="display: none;" id="content_text">
<c:out value="${content}"></c:out>
</span>


</c:param>

</c:import>

