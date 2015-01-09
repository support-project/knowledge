<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<style>
.radio_block {
	margin-bottom: 10px;
}
</style>

</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>



<c:param name="PARAM_CONTENT">

<form action="<%= request.getContextPath()%>/admin.config/save" method="post" role="form">

	<div class="form-group">
		<label for="authType_lock">ユーザ登録のやり方</label><br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_ADMIN %>" name="userAddType" 
				id="userAddType_admin" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN, "userAddType", true) %>/>
			<i class="fa fa-lock fa-lg"></i>&nbsp;管理者が登録する
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_APPROVE %>" name="userAddType" 
				id="userAddType_approve" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_APPROVE, "userAddType", false) %>/>
			<i class="fa fa-gavel fa-lg"></i>&nbsp;ユーザ自身でメールアドレスで登録し、管理者が承認する
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_MAIL %>" name="userAddType" 
				id="userAddType_mail" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_MAIL, "userAddType", false) %>/>
			<i class="fa fa-envelope-square fa-lg"></i>&nbsp;ユーザ自身でメールアドレスを登録し、システムから招待のメールを出す（メール送信設定が必要です）
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_USER %>" name="userAddType" 
				id="userAddType_mail" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_USER, "userAddType", false) %>/>
			<i class="fa fa-unlock fa-lg"></i>&nbsp;ユーザ自身で自由に登録（重複チェックのみ行います）
		</label>
	</div>
	
	<div class="form-group">
		<label for="userAddNotify_off">ユーザ登録の管理者への通知</label><br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_NOTIFY_OFF %>" name="userAddNotify" 
				id="userAddNotify_off" <%= jspUtil.checked(SystemConfig.USER_ADD_NOTIFY_OFF, "userAddNotify", true) %>/>
			<i class="fa fa-bell-slash-o fa-lg"></i>&nbsp;通知しない
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_NOTIFY_ON %>" name="userAddNotify" 
				id="userAddNotify_on" <%= jspUtil.checked(SystemConfig.USER_ADD_NOTIFY_ON, "userAddNotify", false) %>/>
			<i class="fa fa-bell-o fa-lg"></i>&nbsp;通知する（承認が必要な場合、仮登録の時点で管理者にメールが届きます。その他の場合、ユーザが登録された場合にメールが届きます）
		</label>
	</div>
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;登録</button>
</form>


</c:param>

</c:import>

