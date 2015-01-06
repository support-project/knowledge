<%@page import="redcomet.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload-ui.css" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/account.js"></script>
</c:param>


<c:param name="PARAM_CONTENT">
<div class="row">
	<div class="col-sm-3 col-md-2">
		<div id="icondiv">
			<img id="icon" src="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.id() %>"
			width="64" height="64" />
		</div>
		<form action="<%= request.getContextPath()%>/protect.account/iconupload" method="post" role="form" enctype="multipart/form-data">
		<div class="form-group" style="display: none;" id="progress">
			<div class="progress">
				<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
				0%
				</div>
			</div>
		</div>
		<div class="form-group" id="drop_target">
		アイコン画像を、ここへドロッップ<br/>
		（png/jpg/jpeg/gif）
		</div>
		<div id="fileupload">
			<span class="btn btn-info fileinput-button">
				<i class="fa fa-cloud-upload"></i>&nbsp;<span>Icon change...</span>
				<input type="file" name="files[]" multiple>
			</span>
		</div>
		</form>
	</div>
	
	<div class="col-sm-9 col-md-10">
		<form action="<%= request.getContextPath()%>/protect.account/update" method="post" role="form">
			<div class="form-group">
				<label for="userKey">Mail Address(ログイン時のIDになります/公開されることはありません)</label>
				<input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="${userKey}" />
			</div>
			<div class="form-group">
				<label for="userName">User Name(このサービスの中で表示される名称です)</label>
				<input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" value="${userName}" />
			</div>
			
			<div class="form-group">
				<label for="password">Password (input to change password)</label>
				<input type="password" class="form-control" name="password" id="password" placeholder="Password" value="${password}" />
			</div>
			<div class="form-group">
				<label for="confirm_password">Confirm Password (input to change password)</label>
				<input type="password" class="form-control" name="confirm_password" id="confirm_password" placeholder="Confirm Password" value="${confirm_password}" />
			</div>
			
			<div class="form-group">
				<label for="input_no">登録日時 / 更新日時</label>
				<p class="form-control-static">
					<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("insertDatetime")%> / 
					<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
				</p>
			</div>
			
			
			<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;更新</button>
			<a href="<%= request.getContextPath()%>/protect.account/withdrawal" class="btn btn-default"><i class="fa fa-remove"></i>&nbsp;退会</a>
			
		</form>
	</div>
</div>


</c:param>

</c:import>

