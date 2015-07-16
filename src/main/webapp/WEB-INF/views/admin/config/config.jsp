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
<h4 class="title"><%= jspUtil.label("knowledge.config.title") %></h4>

<% if(jspUtil.is(0, "authType")) { %>
<div class="alert alert-info alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<strong>Information</strong><br/>
	- DBに登録されているユーザでサインインします。下記のユーザの追加方法の設定は有効です。
</div>
<% } %>
<% if(jspUtil.is(1, "authType")) { %>
<div class="alert alert-warning alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<strong>Warning</strong><br/>
	- Ldap認証のみでサインインします。下記のユーザの追加方法の設定は無効です。（ユーザの追加はLdapで行ってください）
</div>
<% } %>
<% if(jspUtil.is(2, "authType")) { %>
<div class="alert alert-success alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<strong>Information</strong><br/>
	- Ldap認証、もしくはDBに登録されているユーザでサインインします。Ldapに登録されていれば、Ldapの情報でサインインし、なければDBに登録されているかチェックします。<br/>
	- 下記のユーザの追加方法の設定は有効です。追加したユーザはDBに登録します。（Ldapの情報には登録しません）
</div>
<% } %>


<form action="<%= request.getContextPath()%>/admin.config/save" method="post" role="form">

	<div class="form-group">
		<label for="authType_lock"><%= jspUtil.label("knowledge.config.label.registration.method") %></label><br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_ADMIN %>" name="userAddType" 
				id="userAddType_admin" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN, "userAddType", true) %>/>
			<i class="fa fa-lock fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.label.registration.method.admin") %>
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_APPROVE %>" name="userAddType" 
				id="userAddType_approve" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_APPROVE, "userAddType", false) %>/>
			<i class="fa fa-gavel fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.label.registration.method.accept") %>
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_MAIL %>" name="userAddType" 
				id="userAddType_mail" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_MAIL, "userAddType", false) %>/>
			<i class="fa fa-envelope-square fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.label.registration.method.mail") %>
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_TYPE_VALUE_USER %>" name="userAddType" 
				id="userAddType_mail" <%= jspUtil.checked(SystemConfig.USER_ADD_TYPE_VALUE_USER, "userAddType", false) %>/>
			<i class="fa fa-unlock fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.label.registration.method.free") %>
		</label>
	</div>
	
	<div class="form-group">
		<label for="userAddNotify_off"><%= jspUtil.label("knowledge.config.label.registration.notify") %></label><br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_NOTIFY_OFF %>" name="userAddNotify" 
				id="userAddNotify_off" <%= jspUtil.checked(SystemConfig.USER_ADD_NOTIFY_OFF, "userAddNotify", true) %>/>
			<i class="fa fa-bell-slash-o fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.label.registration.notify.no") %>
		</label>
		<br/>
		<label class="radio-inline radio_block">
			<input type="radio" value="<%= SystemConfig.USER_ADD_NOTIFY_ON %>" name="userAddNotify" 
				id="userAddNotify_on" <%= jspUtil.checked(SystemConfig.USER_ADD_NOTIFY_ON, "userAddNotify", false) %>/>
			<i class="fa fa-bell-o fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.label.registration.notify.yes") %>
		</label>
	</div>
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
</form>


</c:param>

</c:import>

