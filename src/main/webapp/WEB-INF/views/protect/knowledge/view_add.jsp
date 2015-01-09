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
</c:param>



<c:param name="PARAM_CONTENT">
<form action="<%= request.getContextPath()%>/protect.knowledge/add" method="post" role="form" enctype="multipart/form-data">

	<div class="form-group">
		<label for="input_title">Title</label>
		<input type="text" class="form-control" name="title" id="input_title" placeholder="Title" value="${title}">
	</div>
	<div class="form-group">
		<label for="input_content">Content</label>
		<textarea class="form-control" name="content" rows="5" placeholder="Content" id="content">${content}</textarea>
	</div>
	
	<div class="form-group">
		<label for="input_fileupload">添付</label><br/>
		<div id="fileupload">
			<span class="btn btn-info fileinput-button">
				<i class="fa fa-cloud-upload"></i>&nbsp;<span>Add files...</span>
				<input type="file" name="files[]" multiple>
			</span>
		</div>
	</div>
	<div class="form-group" id="drop_target">
		添付したいファイルをここにドロップしても添付できます
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
				<i class="fa fa-remove"></i>&nbsp;削除
			</button>
		</div>
	</c:forEach>
	</div>
	

	<div class="form-group">
		<label for="input_content">公開範囲</label><br/>
		<label class="radio-inline">
			<input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PUBLIC %>" name="publicFlag" 
				id="publicFlag_piblic" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag", true) %>/>
			<i class="fa fa-globe"></i>&nbsp;公開
		</label>
		<label class="radio-inline">
			<input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PRIVATE %>" name="publicFlag" 
				id="publicFlag_private" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag") %>/>
			<i class="fa fa-lock"></i>&nbsp;非公開（自分のみ）
		</label>
		<%--
		<label class="radio-inline">
			<input type="radio" value="2" name="publicFlag" id="publicFlag_private" />
			Select Users
		</label>
		--%>
	</div>
	<div class="form-group">
		<label for="input_tag">Tags</label>
		<p class="tags">
		<input type="text" class="form-control" name="tags" id="input_tags" placeholder="Tags" data-role="tagsinput" value="${tags}" />
		</p>
	</div>
	
	<input type="hidden" name="offset" value="${offset}" />
	<input type="hidden" name="keyword" value="${keyword}" />
	<input type="hidden" name="tag" value="${tag}" />

	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;登録</button>
	<button type="button" class="btn btn-info" onclick="preview();"><i class="fa fa-play-circle"></i>&nbsp;プレビュー</button>
	<a href="<%= request.getContextPath() %>/open.knowledge/list/${offset}?keyword=${keyword}&tag=${tag}"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;一覧へ戻る</a>

</form>





<p class="preview" id="preview"></p>

</c:param>

</c:import>

