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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-list.css" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/knowledge-list.js"></script>
</c:param>

<c:param name="PARAM_CONTENT">

	<c:if test="${!empty selectedTag}">
		<div class="row">
			<div class="col-sm-12 selected_tag">
			<a class="text-primary" 
				href="<%= request.getContextPath() %>/open.knowledge/list?tag=${selectedTag.tagId}" >
					<i class="fa fa-tag"></i>&nbsp;${selectedTag.tagName}
			</a>
			<a class="text-primary" 
				href="<%= request.getContextPath() %>/open.knowledge/list" >
					<i class="fa fa-times-circle"></i>&nbsp;
			</a>
			</div>
		</div>
	</c:if>

		<nav>
			<ul class="pager">
				<li class="previous">
					<a href="<%= request.getContextPath() %>/open.knowledge/list/${previous}?keyword=<%= jspUtil.out("keyword") %>&tag=${tag}">
						<span aria-hidden="true">&larr;</span>Previous
					</a>
				</li>
				<li>
					<a href="<%= request.getContextPath() %>/protect.knowledge/view_add?offset=${offset}&keyword=<%= jspUtil.out("keyword") %>&tag=${tag}" style="cursor: pointer;">
						<i class="fa fa-plus-circle"></i>&nbsp;New Knowledge
					</a>
				</li>
				<li class="next">
					<a href="<%= request.getContextPath() %>/open.knowledge/list/${next}?keyword=<%= jspUtil.out("keyword") %>&tag=${tag}">
						Next <span aria-hidden="true">&rarr;</span>
					</a>
				</li>
			</ul>
		</nav>

		<div class="row">
			<div class="col-sm-12 col-md-8">
			<c:if test="${empty knowledges}">
			条件に該当する情報は存在しませんでした。条件（検索キーワード、タグ、ページきりかえ）の変更をお試しください。
			</c:if>
			
			<c:forEach var="knowledge" items="${knowledges}" varStatus="status">
				<div class="thumbnail" 
					onclick="showKnowledge('<%= request.getContextPath() %>/open.knowledge/view/${knowledge.knowledgeId}', '${offset}', '<%= jspUtil.out("keyword") %>', '${tag}');">
					<div class="discription"><i class="fa fa-check-square-o"></i>&nbsp;show!</div>
					<div class="caption">
						<h4>[${knowledge.knowledgeId}]&nbsp;${knowledge.title}</h4>
						
						<c:if test="${!empty knowledge.tags}">
						<p class="tags">
						<input type="text" name="tags" id="input_tags" placeholder="" data-role="tagsinput" value="${knowledge.tags}" disabled="disabled"/>
						</p>
						</c:if>
						
						<p class="insert_info">
						<img src="<%= request.getContextPath()%>/images/loader.gif" 
							data-echo="<%= request.getContextPath()%>/open.account/icon/${knowledge.insertUser}" 
							alt="icon" width="36" height="36" style="float:left"/>
						<i class="fa fa-user"></i>&nbsp;${knowledge.insertUserName}
						&nbsp;&nbsp;&nbsp;
						<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "knowledge.publicFlag", 
								"<i class=\"fa fa-globe\"></i>&nbsp;[公開]") %>
						<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "knowledge.publicFlag", 
								"<i class=\"fa fa-lock\"></i>&nbsp;[非公開]") %>
						<br/>
						<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("knowledge.updateDatetime")%>
						&nbsp;&nbsp;&nbsp;
						<i class="fa fa-thumbs-o-up"></i>&nbsp;× <span id="like_count">${knowledge.likeCount}</span>
						</p>
						<p style="clear:left;">
						
						<p style="word-break:break-all" class="content">
						<%-- <c:out value="${knowledge.content}"></c:out>--%>
						${knowledge.content}
						</p>
					</div>
				</div>
			</c:forEach>
			</div>
			
			<div class="col-sm-12 col-md-4">
			<h5>- Popular Tags - </h5>
			
			<div class="list-group">
			<c:forEach var="tag" items="${tags}">
				<a class="list-group-item " 
				href="<%= request.getContextPath() %>/open.knowledge/list?tag=${tag.tagId}" >
					<span class="badge">${tag.knowledgeCount}</span>
					<i class="fa fa-tag"></i>&nbsp;${tag.tagName}
				</a>
			</c:forEach>
			</div>
			<div style="width: 100%;text-align: right;">
				<a href="<%= request.getContextPath() %>/open.tag/list">
					<i class="fa fa-tags"></i>&nbsp;タグ一覧
				</a>&nbsp;&nbsp;&nbsp;
			</div>
			
			<h5>- History - </h5>
			<div class="list-group">
			<c:forEach var="history" items="${histories}">
				<a href="<%= request.getContextPath() %>/open.knowledge/view/${history.knowledgeId}?offset=${offset}&keyword=<%= jspUtil.out("keyword") %>" 
				class="list-group-item">
					<h5 class="list-group-item-heading"><i class="fa fa-history"></i>&nbsp;[${history.knowledgeId}]&nbsp;${history.title}</h5>
					<p class="list-group-item-text">${history.content}</p>
				</a>
			</c:forEach>
			</div>
			
			
			<%--
			<h5>- Popular Knowledge - </h5>
			表示回数のをとっておき、回数が多いものを表示
			（履歴データを期間で範囲指定してカウントする）
			 --%>
			
			</div>
		
		</div>
		
		<nav>
			<ul class="pager">
				<li class="previous">
					<a href="<%= request.getContextPath() %>/open.knowledge/list/${previous}?keyword=<%= jspUtil.out("keyword") %>&tag=${tag}">
						<span aria-hidden="true">&larr;</span>Previous
					</a>
				</li>
				<li>
					<a href="<%= request.getContextPath() %>/protect.knowledge/view_add?offset=${offset}&keyword=<%= jspUtil.out("keyword") %>&tag=${tag}" style="cursor: pointer;">
						<i class="fa fa-plus-circle"></i>&nbsp;New Knowledge
					</a>
				</li>
				<li class="next">
					<a href="<%= request.getContextPath() %>/open.knowledge/list/${next}?keyword=<%= jspUtil.out("keyword") %>&tag=${tag}">
						Next <span aria-hidden="true">&rarr;</span>
					</a>
				</li>
			</ul>
		</nav>

</c:param>

</c:import>

