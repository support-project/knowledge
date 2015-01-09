<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/js/account.js"></script>
</c:param>


<c:param name="PARAM_CONTENT">
<h4>ユーザ新規登録</h4>

<div class="row">
	<div class="col-sm-12 col-md-12">
		<form action="<%= request.getContextPath()%>/open.signup/save" method="post" role="form">
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
			
			<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;登録</button>
			
		</form>
	</div>
</div>


</c:param>

</c:import>

