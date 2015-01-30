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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-edit.css" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/knowledge-edit.js"></script>

<script>
var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';

<c:forEach var="group" items="${groups}" varStatus="status">
selectedGroups.push({label: '${group.groupName}', value: '${group.groupId}'});
</c:forEach>

</script>

</c:param>




<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.edit.title") %></h4>
<form action="<%= request.getContextPath()%>/protect.knowledge/update" method="post" role="form" id="knowledgeForm" enctype="multipart/form-data">

	<div class="form-group">
		<label for="input_no"><%= jspUtil.label("knowledge.edit.label.key") %></label>
		<p class="form-control-static"><i class="fa fa-key"></i>&nbsp;${knowledgeId} / <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%></p> 
	</div>
	<div class="form-group">
		<label for="input_title"><%= jspUtil.label("knowledge.add.label.title") %></label>
		<input type="text" class="form-control" name="title" id="input_title" placeholder="<%= jspUtil.label("knowledge.add.label.title") %>" value="${title}" />
	</div>
	<div class="form-group">
		<label for="input_content"><%= jspUtil.label("knowledge.add.label.content") %></label>
		<textarea class="form-control" name="content" rows="5" placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="content">${content}</textarea>
	</div>
	
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
	<c:forEach var="file" items="${files}" >
		<div class="filediv" id="file-${ file.fileNo }">
			<div class="file-label">
				<img src="${ file.thumbnailUrl }" />
				<a href="${ file.url }">
				<c:out value="${file.name}"></c:out>
				</a>
			</div>
			<input type="hidden" name="files" value="${ file.fileNo }" />
			&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger" onclick="removeAddedFile(${ file.fileNo })">
				<i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
			</button>
		</div>
	</c:forEach>
	</div>
		
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
		<label for="input_groups"><%= jspUtil.label("knowledge.add.label.groups") %></label>
		<p>
			<input type="hidden" name="groups" id="groups" value="">
			<span id="groupsLabel"></span>
		</p>
		<a id="groupselect" class="btn btn-info" data-toggle="modal" href="#groupSelectModal">
			<i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.groups.select") %>
		</a>
	</div>
	
	<div class="form-group">
		<label for="input_tag"><%= jspUtil.label("knowledge.add.label.tags") %></label>
		<p class="tags">
		<input type="text" class="form-control" name="tags" id="input_tags" 
			placeholder="<%= jspUtil.label("knowledge.add.label.tags") %>" value="${tags}" />
		</p>
	</div>
	
	
	<input type="hidden" name="knowledgeId" value="${knowledgeId}" />
	<input type="hidden" name="offset" value="${offset}" />
	<input type="hidden" name="keyword" value="${keyword}" />
	<input type="hidden" name="tag" value="${tag}" />
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
	<button type="button" class="btn btn-info" onclick="preview();"><i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
	
	<button type="button" class="btn btn-danger" onclick="deleteKnowledge();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
	
	<a href="<%= request.getContextPath() %>/open.knowledge/view/${knowledgeId}?offset=${offset}&keyword=${keyword}&tag=${tag}&user=${user}"
	class="btn btn-warning" role="button"><i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.cancel") %></a>
	<a href="<%= request.getContextPath() %>/open.knowledge/list/${offset}?keyword=${keyword}&tag=${tag}&user=${user}"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
	
</form>

<p class="preview" id="preview"></p>



<div class="modal fade" id="groupSelectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
				<span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
				<h4 class="modal-title" id="myModalLabel">
					<%= jspUtil.label("knowledge.add.label.groups.select") %>
					<span style="font-size: 14px;" id="groupPage"></span>
				</h4>
			</div>
			<div class="modal-body">
				<div role="form" class="form-inline">
					<input type="text" name="keyword" class="form-control" value="${ keyword }" placeholder="Keyword" id="groupKeyword">
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
				<%-- 決定はしないで、選択したら動的に呼び出し元のフォームに反映する
				<button type="button" class="btn btn-primary" id="groupDecision">
					<i class="fa fa-legal"></i>&nbsp;<%= jspUtil.label("label.decision") %>
				</button>
				--%>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


</c:param>

</c:import>

