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
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-edit.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
</c:param>
<c:param name="PARAM_SCRIPTS">
	<script>
	var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
	</script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>
	<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/comment.js") %>"></script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.comment.edit") %></h4>

<form action="<%= request.getContextPath()%>/protect.knowledge/update_comment" method="post" role="form" id="commentForm">

	<div class="form-group">
		<label for="comment">Comment
		<span class="helpMarkdownLabel">
		<a data-toggle="modal" data-target="#helpMarkdownModal">Markdown supported</a>
		</span>
		</label>
		<input type="hidden" name="commentNo" value="<%= jspUtil.out("commentNo") %>" id="commentNo"/>
		<textarea class="form-control" name="comment" rows="8" 
		placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="comment"><%= jspUtil.out("comment") %></textarea>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a>
	</div>
	
	<button type="submit" class="btn btn-primary">
		<i class="fa fa-save"></i>&nbsp;
		<%= jspUtil.label("label.update") %>
	</button>
	
	<button type="button" class="btn btn-info" onclick="preview();"><i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
	
	<button type="button" class="btn btn-danger" onclick="deleteComment();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
	
	<a href="<%= request.getContextPath()%>/protect.knowledge/view/<%= jspUtil.out("knowledgeId") %>" class="btn btn-warning">
		<i class="fa fa-undo"></i>&nbsp;
		<%= jspUtil.label("label.cancel") %>
	</a>
	
</form>

<br/>
<p class="preview markdown" id="preview"></p>
<span style="display: none;" id="comment_text">
</span>


<jsp:include page="markdown.jsp"></jsp:include>
<jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>

</c:param>

</c:import>


