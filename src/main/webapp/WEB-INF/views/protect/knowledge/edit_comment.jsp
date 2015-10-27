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
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload-ui.css" />

<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-edit.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
</c:param>
<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>

	<script>
	var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
	var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
	var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
	var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
	var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
	var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
	var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
	var _SET_IMAGE_LABEL= '<%= jspUtil.label("knowledge.edit.set.image.path") %>';
	</script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>
	
	<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-common.js") %>"></script>
	<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/comment.js") %>"></script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.comment.edit") %></h4>

<form action="<%= request.getContextPath()%>/protect.knowledge/update_comment" method="post" role="form" id="commentForm" enctype="multipart/form-data">

<table width="100%" cellspacing="0" cellpadding="0">
<tr>
<td width="50%" valign="top">
	<div class="form-group">
		<label for="comment">Comment
		<span class="helpMarkdownLabel">
		<a data-toggle="modal" data-target="#helpMarkdownModal">Markdown supported</a>
		</span>
		</label>
		<input type="hidden" name="commentNo" value="<%= jspUtil.out("commentNo") %>" id="commentNo"/>
		<textarea onKeyup="previewInput();" class="form-control" name="comment" rows="16"
		placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="comment"><%= jspUtil.out("comment") %></textarea>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a>
	</div>
</td>
<td width="50%" valign="top">
		<div style="height: 360px;overflow-y: scroll;overflow-x: auto;">
			<p class="preview markdown" id="preview" style="margin-top: 0px;"></p>
		</div>
</td>
</tr>
</table>


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

	<button type="submit" class="btn btn-primary">
		<i class="fa fa-save"></i>&nbsp;
		<%= jspUtil.label("label.update") %>
	</button>
<!--
	<button type="button" class="btn btn-info" onclick="preview();"><i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
-->
	<button type="button" class="btn btn-danger" onclick="deleteComment();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>

	<a href="<%= request.getContextPath()%>/protect.knowledge/view/<%= jspUtil.out("knowledgeId") %>" class="btn btn-warning">
		<i class="fa fa-undo"></i>&nbsp;
		<%= jspUtil.label("label.cancel") %>
	</a>

</form>

<br/>
<span style="display: none;" id="comment_text">
</span>


<jsp:include page="markdown.jsp"></jsp:include>
<jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>

<script>
<!--
//初期表示
window.onload = function() {
  preview();
};
-->
</script>

</c:param>

</c:import>


