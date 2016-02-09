<%@page import="org.support.project.common.util.StringUtils"%>
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
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap3-typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/tagselect.js") %>"></script>

<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-common.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-edit.js") %>"></script>

<script>
var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
var _SET_IMAGE_LABEL= '<%= jspUtil.label("knowledge.edit.set.image.path") %>';
var _LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
var _UPDATE_TITLE = '<%= jspUtil.label("knowledge.edit.title") %>';

<c:forEach var="group" items="${groups}" varStatus="status">
selectedGroups.push({label: '<%= jspUtil.out("group.label") %>', value: '<%= jspUtil.out("group.value") %>'});
</c:forEach>
<c:forEach var="editor" items="${editors}" varStatus="status">
selectedEditors.push({label: '<%= jspUtil.out("editor.label") %>', value: '<%= jspUtil.out("editor.value") %>'});
</c:forEach>

var _TAGS = [];
<c:forEach var="tagitem" items="${tagitems}" varStatus="status">
_TAGS.push('<%= jspUtil.out("tagitem.tagName") %>');
</c:forEach>

</script>

</c:param>

<c:param name="PARAM_PAGE_TITLE">
<%= jspUtil.label("label.update") %> - <%= jspUtil.out("title", JspUtil.ESCAPE_CLEAR) %> - Knowledge
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title" id="title_msg"><%= jspUtil.label("knowledge.edit.title") %></h4>
<form action="<%= request.getContextPath()%>/protect.knowledge/update" method="post" role="form" id="knowledgeForm" enctype="multipart/form-data">
	<!-- info -->
	<div class="form-group">
		<label for="input_no"><%= jspUtil.label("knowledge.edit.label.key") %></label>
		<p class="form-control-static"><i class="fa fa-key"></i>&nbsp;<%= jspUtil.out("knowledgeId") %> / <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%></p> 
	</div>
	
	<!-- template -->
	<div class="form-group">
		<label for="input_title"><%= jspUtil.label("knowledge.add.label.type") %></label><br/>
		<c:forEach var="template" items="${templates}" >
			<label class="radio-inline">
				<input type="radio" value="<%= jspUtil.out("template.typeId") %>" name="typeId" 
					id="typeId_<%= jspUtil.out("template.typeId") %>" <%= jspUtil.checked(jspUtil.out("template.typeId"), "typeId", false) %>/>
				<% if (!StringUtils.isEmpty(jspUtil.out("template.typeIcon"))) { %>
					<i class="fa <%= jspUtil.out("template.typeIcon") %>"></i>&nbsp;
				<% } else { %>
					<i class="fa fa-edit"></i>&nbsp;
				<% } %>
				<%= jspUtil.out("template.typeName") %>
			</label>
		</c:forEach>
	</div>
	
	<div class="alert alert-info hide" role="alert" id="template_info">
		<strong id="template_name"></strong><br/>
		<span id="template_msg"></span>
	</div>
	
	<!-- title -->
	<div class="form-group">
		<label for="input_title"><%= jspUtil.label("knowledge.add.label.title") %></label>
		<input type="text" class="form-control" name="title" id="input_title" placeholder="<%= jspUtil.label("knowledge.add.label.title") %>" value="<%= jspUtil.out("title") %>" />
	</div>
	
	<!-- items -->
	<div class="form-group" id="template_items">
	</div>
	
	<!-- contents -->
	<div class="form-group">
		<label for="input_content"><%= jspUtil.label("knowledge.add.label.content") %>
		<span class="helpMarkdownLabel">
		<a data-toggle="modal" data-target="#helpMarkdownModal">Markdown supported</a>
		</span>
		</label>
		<textarea class="form-control" name="content" rows="8" placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="content"><%= jspUtil.out("content") %></textarea>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a>
		<a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a>
	</div>
	
	<!-- upload files -->
	<div class="form-group">
		<div id="fileupload">
		<label for="input_fileupload"><%= jspUtil.label("knowledge.add.label.files") %></label>
			<span class="btn btn-primary fileinput-button btn-xs">
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
	<c:forEach var="file" items="${files}" >
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
	
	

	<!-- view targets -->
	<div class="form-group">
		<label for="input_content"><%= jspUtil.label("knowledge.add.label.public.class") %></label><br/>
		<label class="radio-inline">
			<input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PUBLIC %>" name="publicFlag" 
				id="publicFlag_piblic" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag", true) %>/>
			<i class="fa fa-globe"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.public") %>
		</label>
		<label class="radio-inline">
			<input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PRIVATE %>" name="publicFlag" 
				id="publicFlag_private" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag") %>/>
			<i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.private") %>
		</label>
		<label class="radio-inline">
			<input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PROTECT %>" name="publicFlag" 
				id="publicFlag_private" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "publicFlag") %>/>
			<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.protect") %>
		</label>
	</div>
	
	<div class="form-group" id="grops_area" <%= jspUtil.isnot(KnowledgeLogic.PUBLIC_FLAG_PROTECT, "publicFlag", "style=\"display: none;\"") %>>
		<label for="input_groups"><%= jspUtil.label("knowledge.add.label.destination") %></label>
		<a id="groupselect" class="btn btn-primary btn-xs" data-toggle="modal" href="#groupSelectModal">
			<i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.destination.select") %>
		</a>
		<p>
			<input type="hidden" name="groups" id="groups" value="">
			<span id="groupsLabel"></span>
		</p>
	</div>
	
	
	
	<!-- tags -->
	<div class="form-group">
		<label for="input_tag">
		<%= jspUtil.label("knowledge.add.label.tags") %>
		<span class="helpMarkdownLabel">
		<a data-toggle="modal" data-target="#tagSelectModal"><%= jspUtil.label("label.search.tags") %></a>
		</span>
		</label>
		<p class="tags">
		<input type="text" name="tagNames" id="input_tags" data-role="tags input"
			placeholder="<%= jspUtil.label("knowledge.add.label.tags") %>" value="<%= jspUtil.out("tagNames") %>" />
		</p>
	</div>


	<!-- editors -->
	<div class="form-group" id="editor_area">
		<label for="input_groups"><%= jspUtil.label("knowledge.add.label.editors") %></label>
		<a id="groupselect" class="btn btn-primary btn-xs" data-toggle="modal" href="#editorSelectModal">
			<i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.editors.select") %>
		</a>
		<p>
			<input type="hidden" name="editors" id="editors" value="">
			<span id="editorsLabel"></span>
		</p>
	</div>
	
	<input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeId" />

	<input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" />
	<input type="hidden" name="keyword" value="<%= jspUtil.out("keyword") %>" />
	<input type="hidden" name="tag" value="<%= jspUtil.out("tag") %>" />
	<input type="hidden" name="user" value="<%= jspUtil.out("user") %>" />

	<!-- buttons -->
	<hr/>
	
	<button type="submit" class="btn btn-primary" id="savebutton"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
	<button type="button" class="btn btn-info" onclick="preview();"><i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
	
	<button type="button" class="btn btn-danger" onclick="deleteKnowledge();" id="deleteButton">
		<i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
	</button>
	<a href="<%= request.getContextPath() %>/open.knowledge/view/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>"
		class="btn btn-warning" role="button" id="cancelButton">
		<i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.cancel") %>
	</a>
	
	<a href="<%= request.getContextPath() %>/open.knowledge/list/<%= jspUtil.out("offset") %><%= jspUtil.out("params") %>"
		class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %>
	</a>
	
</form>

<p class="preview markdown" id="preview"></p>
<span style="display: none;" id="content_text">
</span>

<%-- Editors --%>
<div class="modal fade" id="editorSelectModal" tabindex="-1" role="dialog" aria-labelledby="editorModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
				<span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
				<h4 class="modal-title" id="editorModalLabel">
					<%= jspUtil.label("knowledge.add.label.editors.select") %>
					<span style="font-size: 14px;" id="editorPage"></span>
				</h4>
			</div>
			<div class="modal-body">
				<div role="form" class="form-inline">
					<input type="text" name="keyword" class="form-control" value="<%= jspUtil.out("keyword") %>" placeholder="Keyword" id="editorKeyword">
					<button type="button" class="btn btn-success" id="editorSearchButton">
						<i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.filter") %>
					</button>
					<button type="button" class="btn btn-default" id="editorSearchPrevious">
						<i class="fa fa-arrow-circle-left"></i>&nbsp;<%= jspUtil.label("label.previous") %>
					</button>
					<button type="button" class="btn btn-default" id="editorSearchNext">
						<%= jspUtil.label("label.next") %>&nbsp;<i class="fa fa-arrow-circle-right "></i>
					</button>
				</div>
				<hr/>
				<p>
					<%-- 選択済みの一覧 --%>
					<span id="selectedEditorList"></span>
					<button type="button" class="btn btn-default" id="clearSelectedEditor">
						<i class="fa fa-eraser"></i>&nbsp;<%= jspUtil.label("label.clear") %>&nbsp;
					</button>
				</p>
				<hr/>
				<p id="editorList">
					<%-- 選択するための一覧 --%>
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %>
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->




<%-- Targets --%>
<div class="modal fade" id="groupSelectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
				<span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
				<h4 class="modal-title" id="myModalLabel">
					<%= jspUtil.label("knowledge.add.label.destination.select") %>
					<span style="font-size: 14px;" id="groupPage"></span>
				</h4>
			</div>
			<div class="modal-body">
				<div role="form" class="form-inline">
					<input type="text" name="keyword" class="form-control" value="<%= jspUtil.out("keyword") %>" placeholder="Keyword" id="groupKeyword">
					<button type="button" class="btn btn-success" id="groupSearchButton">
						<i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.filter") %>
					</button>
					<button type="button" class="btn btn-default" id="groupSearchPrevious">
						<i class="fa fa-arrow-circle-left"></i>&nbsp;<%= jspUtil.label("label.previous") %>
					</button>
					<button type="button" class="btn btn-default" id="groupSearchNext">
						<%= jspUtil.label("label.next") %>&nbsp;<i class="fa fa-arrow-circle-right "></i>
					</button>
				</div>
				<hr/>
				<p>
					<%-- 選択済みの一覧 --%>
					<span id="selectedList"></span>
					<button type="button" class="btn btn-default" id="clearSelectedGroup">
						<i class="fa fa-eraser"></i>&nbsp;<%= jspUtil.label("label.clear") %>&nbsp;
					</button>
				</p>
				<hr/>
				<p id="groupList">
					<%-- 選択するための一覧 --%>
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %>
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>
<jsp:include page="markdown.jsp"></jsp:include>
<jsp:include page="../../open/tag/dialog.jsp"></jsp:include>

<form action="<%= request.getContextPath()%>/protect.knowledge/delete" method="post" role="form" id="knowledgeDeleteForm">
	<input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeIdForDelete" />
</form>

</c:param>

</c:import>

