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
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload-ui.css" />

<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-edit.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-view.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>

<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-common.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view.js") %>"></script>


<script>
var LABEL_LIKE = '<%= jspUtil.label("knowledge.view.like") %>';

var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
var _SET_IMAGE_LABEL= '<%= jspUtil.label("knowledge.edit.set.image.path") %>';

</script>

</c:param>

<c:param name="PARAM_PAGE_TITLE">
Knowledge - [<%= jspUtil.out("knowledgeId") %>] <%= jspUtil.out("title", JspUtil.ESCAPE_CLEAR) %>
</c:param>


<c:param name="PARAM_CONTENT">
	<div class="row">
		<div class="col-sm-9">
			<%-- <h4 class="title"><%= jspUtil.label("knowledge.view.title") %></h4> --%>
			
			<span class="insert_info_text">
			<%-- 
			<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag",
					jspUtil.label("label.public.view")) %>
			 --%>
			<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag",
					jspUtil.label("label.private.view")) %>
			<%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "publicFlag",
					jspUtil.label("label.protect.view")) %>
			</span>
			<h4 class="title"><%= jspUtil.out("title", JspUtil.ESCAPE_CLEAR) %></h4>
			<%-- タグ --%>
			<div class="tag_list">
				<c:if test="${!empty tagNames}">
					<i class="fa fa-tags insert_info_text"></i>&nbsp;
					<c:forEach var="tagName" items="${tagNames.split(',')}">
					<span class="tag label label-info"><%= jspUtil.out("tagName") %></span>
					</c:forEach>
				</c:if>
			</div>
		<%-- 登録者情報 --%>
		<%--概要：「登録者情報」のコメントアウト
			意図：記事において、記事上部の情報は記事全体よりも先に読者の目に入るため、いかに記事の要約をわかりやすく伝えるかが重要になります。
				”誰が”、”いつ”、書いたかはそれら重要な情報の一つですが、”誰がいつ登録したか”という情報と”誰が最後に更新したか”という情報は、「時間」という観点からみた場合重複した情報になるので、”誰が最後に更新したか”一つに絞ります
			補遺：
		 --%>
			<div class="insert_info">
		<%-- 					
				<div class="saveType">
				[<%= jspUtil.label("label.registration") %>]
				</div>
		 --%>						
				<img src="<%= request.getContextPath()%>/images/loader.gif" 
					data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("insertUser") %>" 
					alt="icon" width="24" height="24" style="float:left" />
				<a href="<%= request.getContextPath() %>/open.knowledge/list/0?user=<%= jspUtil.out("insertUser") %>">
				<%= jspUtil.out("insertUserName", JspUtil.ESCAPE_CLEAR) %>
				</a>が
				<a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %>">
				<%= jspUtil.date("insertDatetime")%>
				</a>に[<%= jspUtil.label("label.registration") %>]
			</div>
		
		<%--
			<div class="insert_info">
				<div class="saveType">
				[<%= jspUtil.label("label.update") %>]
				</div>
				<img src="<%= request.getContextPath()%>/images/loader.gif" 
					data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("updateUser") %>" 
					alt="icon" width="36" height="36" style="float:left" />
				<a href="<%= request.getContextPath() %>/open.knowledge/list/0?user=<%= jspUtil.out("updateUser") %>">
				<%= jspUtil.out("updateUserName", JspUtil.ESCAPE_CLEAR) %>
				</a>
				<br/>
				<a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %>">
				<%= jspUtil.date("updateDatetime")%>
				</a>
			</div>
		 --%>					
		<%-- 公開区分やイイネ件数など --%>
		<%-- 操作ボタン --%>
		<%--概要：「いいね」ボタンのコメントアウト
			意図：「ストック」機能がある場合、「いいね」ボタンは好意を伝えるボタン以外の機能を果たさないため、ストックよりも機能性が弱いと判断してコメントアウト
			補遺：	
			<button class="btn btn-warning" onclick="addlike(<%= jspUtil.out("knowledgeId") %>);">
				<i class="fa fa-thumbs-o-up"></i>&nbsp;
				<%= jspUtil.label("knowledge.view.like") %>
			</button>
		 --%>	
			
		<%-- 
			<a href="<%= request.getContextPath() %>/open.knowledge/list/<%= jspUtil.out("offset") %><%= jspUtil.out("params") %>"
			class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.view.back.list") %></a>
		 --%>

		</div>
		<div class="col-sm-3">
			<div class="article_buttons">
				<%--概要：「投稿を」という目的格を添えた
					意図：「何を」という意味が伝わるので、わかりやすくなります
					補遺： --%>
				<% if (request.getRemoteUser() != null) { 
					if ((boolean) request.getAttribute("edit")) { %>
					<a href="<%= request.getContextPath() %>/protect.knowledge/view_edit/<%= jspUtil.out("knowledgeId") %>"
					class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp;
					投稿を<%= jspUtil.label("label.edit") %>
					</a>
				<% } %>
				<% } else { %>
					<a href="<%= request.getContextPath() %>/protect.knowledge/view_edit/<%= jspUtil.out("knowledgeId") %>"
					class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp;
					<%= jspUtil.label("knowledge.view.edit.with.login") %>
					</a>
				<% } %>
				<% if (request.getRemoteUser() != null) { %>
					<button type="button" class="btn btn-info btn_stock" data-toggle="modal" data-target="#stockModal">
					<i class="fa fa-star-o"></i>&nbsp;
					<%= jspUtil.label("knowledge.view.fav") %>
					</button>
				<% } else { %>
					<a href="<%= request.getContextPath() %>/protect.knowledge/view/<%= jspUtil.out("knowledgeId") %>"
						class="btn btn-info" role="button">
						<i class="fa fa-star-o"></i>&nbsp;
						<%= jspUtil.label("knowledge.view.fav") %>(<%= jspUtil.label("knowledge.navbar.signin") %>)
					</a>
				<% } %>
			</div>
				
			<div class="article_info">
				<%--概要： 「いいね」「コメント」という文言をアイコンの隣に加える
					意図：  情報を伝える際に、アイコンを使用するのはとても有効ですが、”その図が何を表すか”ということを読み取るのは誰にでも容易な作業ではありません。
						可能な限り、アイコンは文字とセットにして使うのが効果的です。--%>
				<p>
					<a class="btn btn-link" href="<%= request.getContextPath() %>/open.knowledge/likes/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>" >
						<i class="fa fa-thumbs-o-up"></i>&nbsp;いいね
						× <span id="like_count"><%= jspUtil.out("like_count") %></span>
					</a>
					<a class="btn btn-link" href="#comments" id="commentsLink">
						<i class="fa fa-comments-o"></i>&nbsp;コメント
						× <%= jspUtil.out("comments.size()") %>
					</a>
					<c:if test="${targets.containsKey(knowledgeId)}">
						<c:forEach var="target" items="${targets.get(knowledgeId)}">
							<span class="tag label label-info"><%= jspUtil.out("target.label") %></span>
						</c:forEach>
						&nbsp;
					</c:if>
				</p>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-12">
			<div class="thumbnail">
				<div class="caption">
					
<%-- 添付ファイル --%>
					<c:forEach var="file" items="${files}" >
						<c:if test="${file.commentNo == 0}">
						<div class="downloadfile">
							<img src="<%= jspUtil.out("file.thumbnailUrl") %>" />
							<a href="<%= jspUtil.out("file.url") %>">
							<%= jspUtil.out("file.name") %>
							</a>
						</div>
						</c:if>
					</c:forEach>
					
<%-- ナレッジ表示 --%>
					<div style="margin-top: 10px;">
					<input type="hidden" id="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" />
					<input type="hidden" id="typeId" value="<%= jspUtil.out("typeId") %>" />
					<span id="template"></span>
					</div>
					<div style="word-break:break-all;display: none;" id="template_items_area" class="markdown viewarea">
					<span id="template_items"></span>
					</div>
					
<%--概要：「content」という文言のコメントアウト
	意図：記事本体に対する見出しは必要ありません。記事を閲覧したときに、本体がそれとわかるように記事の外観をデザインすることが理想です。
	補遺：
 					<h5>content</h5>
 --%>					<div style="word-break:break-all;" id="content" class="markdown viewarea">
					<%= jspUtil.out("content", JspUtil.ESCAPE_NONE) %>
					</div>
				</div>
			</div>
		</div>
	</div>
	

	
<%-- コメント表示 --%>
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
		[<%= jspUtil.label("label.registration") %>]
		<%= jspUtil.date("comment.updateDatetime")%> [<%= jspUtil.out("comment.insertUserName") %>]
		<% if (comment.isUpdate()) { %>
			<br/>[<%= jspUtil.label("label.update") %>]
			<%= jspUtil.date("comment.updateDatetime")%> [<%= jspUtil.out("comment.updateUserName") %>]
		<% } %>
		
		<% if (jspUtil.isAdmin() || jspUtil.is(jspUtil.id(), "comment.insertUser") || (boolean) request.getAttribute("edit")) { %>
			&nbsp;
			<a class="btn btn-primary btn-xs" href="<%= request.getContextPath() %>/protect.knowledge/edit_comment/<%= comment.getCommentNo() %>">
				<i class="fa fa-edit"></i> Edit
			</a>
		<% } %>
		
		</div>
	</div>
	<div class="question_Box">
	<div class="question_image">
		<img src="<%= request.getContextPath()%>/images/loader.gif" 
			data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("comment.insertUser") %>" 
			alt="icon" width="64" height="64"/>
	</div>
	<div class="arrow_question">
	<%= jspUtil.out("comment.comment", JspUtil.ESCAPE_NONE) %>
	
		<hr/>
		<c:forEach var="file" items="${files}" >
			<c:if test="${file.commentNo == comment.commentNo}">
			<div class="downloadfile">
				<img src="<%= jspUtil.out("file.thumbnailUrl") %>" />
				<a href="<%= jspUtil.out("file.url") %>">
				<%= jspUtil.out("file.name") %>
				</a>
			</div>
			</c:if>
		</c:forEach>
	
	</div><!-- /.arrow_question -->
	</div><!-- /.question_Box -->
	<% } else { %>
	<div class="row">
		<div class="col-sm-12" style="text-align: right;">
		
		<% if (jspUtil.isAdmin() || jspUtil.is(jspUtil.id(), "comment.insertUser") || (boolean) request.getAttribute("edit")) { %>
			&nbsp;
			<a class="btn btn-primary btn-xs" href="<%= request.getContextPath() %>/protect.knowledge/edit_comment/<%= comment.getCommentNo() %>">
				<i class="fa fa-edit"></i> Edit
			</a>
		<% } %>
		
		[<%= jspUtil.label("label.registration") %>]
		<%= jspUtil.date("comment.updateDatetime")%> [<%= jspUtil.out("comment.insertUserName") %>]
		<% if (comment.isUpdate()) { %>
			<br/>[<%= jspUtil.label("label.update") %>]
			<%= jspUtil.date("comment.updateDatetime")%> [<%= jspUtil.out("comment.updateUserName") %>]
		<% } %>
		
		</div>
	</div>
	<div class="question_Box">
	<div class="answer_image">
		<img src="<%= request.getContextPath()%>/images/loader.gif" 
			data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("comment.insertUser") %>" 
			alt="icon" width="64" height="64"/>
	</div>
	<div class="arrow_answer">
	<%= jspUtil.out("comment.comment", JspUtil.ESCAPE_NONE) %>
	
		<c:forEach var="file" items="${files}" >
			<c:if test="${file.commentNo == comment.commentNo}">
			<div class="downloadfile">
				<img src="<%= jspUtil.out("file.thumbnailUrl") %>" />
				<a href="<%= jspUtil.out("file.url") %>">
				<%= jspUtil.out("file.name") %>
				</a>
			</div>
			</c:if>
		</c:forEach>
	
	</div><!-- /.arrow_answer -->
	</div><!-- /.question_Box -->
	<% } %>
	
	</c:forEach>
	
	<br/>
	<br/>
	
<%-- コメント登録 --%>
<hr/>
<h4 class="title"><%= jspUtil.label("knowledge.comment.add") %></h4>
	<% if (request.getRemoteUser() != null) { %>
		<form action="<%= request.getContextPath()%>/protect.knowledge/comment/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>" 
		method="post" role="form" enctype="multipart/form-data">
		<textarea class="form-control" name="addcomment" rows="4" placeholder="Comment" id="comment"><%= jspUtil.out("addcomment") %></textarea>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a>
		<br/>
		
		
		<div class="form-group">
			<label for="input_fileupload"><%= jspUtil.label("knowledge.add.label.files") %></label><br/>
			<div id="fileupload">
				<span class="btn btn-info fileinput-button">
					<i class="fa fa-cloud-upload"></i>&nbsp;<span><%= jspUtil.label("knowledge.add.label.select.file") %></span>
					<input type="file" name="files[]" multiple>
				</span>
			</div>
		</div>
		<div class="form-group" id="drop_target">
			<%= jspUtil.label("knowledge.add.label.area.upload") %>
		</div>
		<div class="form-group" style="display: none;" id="progress">
			<div class="progress">
				<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
				0%
				</div>
			</div>
		</div>
		<div class="form-group" id="files">
		<c:forEach var="file" items="${comment_files}" >
			<div class="filediv" id="file-<%= jspUtil.out("file.fileNo") %>">
				<div class="file-image"><img src="<%= jspUtil.out("file.thumbnailUrl") %>" /></div>
				<div class="file-label"><a href="<%= jspUtil.out("file.url") %>"><%= jspUtil.out("file.name") %></a></div>
				<br class="fileLabelBr"/>
				<input type="hidden" name="files" value="<%= jspUtil.out("file.fileNo") %>" />
				&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-success" onclick="setImagePath('<%= jspUtil.out("file.url") %>', '<%= jspUtil.out("file.name") %>')">
					<i class="fa fa-file-image-o"></i>&nbsp;<%= jspUtil.label("knowledge.edit.set.image.path") %>
				</button>
				<button type="button" class="btn btn-danger" onclick="removeAddedFile(<%= jspUtil.out("file.fileNo") %>)">
					<i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
				</button>
			</div>
		</c:forEach>
		</div>
		
		
		
		<% if (jspUtil.out("insertUser").equals(request.getRemoteUser())) { %>
			<button type="button" class="btn btn-info" onclick="previewans();"><i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
		<%	} else { %>
			<button type="button" class="btn btn-info" onclick="preview();"><i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
		<%	} %>
		
		<button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i>&nbsp;<%= jspUtil.label("knowledge.view.comment") %></button>
		
		<input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" />
		<input type="hidden" name="keyword" value="<%= jspUtil.out("keyword") %>" />
		<input type="hidden" name="tag" value="<%= jspUtil.out("tag") %>" />
		<input type="hidden" name="user" value="<%= jspUtil.out("user") %>" />
		<input type="hidden" name="loginuser" value="<%= request.getRemoteUser() %>" id="loginuser" />
		
		</form>
	<% } else { %>
		<form action="<%= request.getContextPath()%>/protect.knowledge/view/<%= jspUtil.out("knowledgeId") %>" method="get" role="form">
		<button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i>&nbsp;<%= jspUtil.label("knowledge.view.comment.with.login") %></button>
		</form>
	<% } %>


<p class="preview markdown" id="preview"></p>
<span style="display: none;" id="comment_text">
</span>






<!-- Stock Modal -->
<div class="modal fade" id="stockModal" tabindex="-1" role="dialog" aria-labelledby="stockModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="stockModalLabel"><%= jspUtil.label("knowledge.view.stock.modal.title") %></h4>
			</div>
			<div class="modal-body">
				<%-- コンテンツ --%>
				<nav>
					<ul class="pager">
						<li class="previous">
							<a href="#" onclick="getStockInfoPrevious();">
								<span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
							</a>
						</li>
						<li class="next">
							<a href="#" onclick="getStockInfoNext();">
								<%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
							</a>
						</li>
					</ul>
				</nav>
				Page: <span id="stockPage"></span>
				
				<div id="stockSelect">
				</div>
				<span id="stockLink" style="display: none;">
					<%= jspUtil.label("knowledge.stock.empty") %><br/>
					<a href="<%= request.getContextPath() %>/protect.stock/mylist" ><%= jspUtil.label("knowledge.stock.label.link") %></a>
				</span>
			</div>
			<div class="modal-footer">
				<div class="form-group">
					<input type="text" class="form-control" name="stockComment" id="stockComment" 
						placeholder="<%= jspUtil.label("label.comment") %>" value="">
				</div>
				
				<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %></button>
				<button type="button" class="btn btn-primary" id="saveStockButton" onclick="saveStocks(<%= jspUtil.out("knowledgeId") %>);"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
			</div>
		</div>
	</div>
</div>


<jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>


</c:param>

</c:import>

