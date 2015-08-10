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
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function deleteConfig() {
	bootbox.confirm('<%= jspUtil.label("knowledge.ldap.confirm.delete") %>', function(result) {
		if (result) {
			$('#ldapForm').attr('action', '<%= request.getContextPath()%>/admin.ldap/delete');
			$('#ldapForm').submit();
		}
	}); 
};
</script>

</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.ldap.title") %></h4>

<div class="alert alert-info alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<strong>Information</strong><br/>
	- <%= jspUtil.label("knowledge.ldap.msg.ldap") %><br/>
	- <%= jspUtil.label("knowledge.ldap.msg.adminid1") %><br/>
	- <%= jspUtil.label("knowledge.ldap.msg.adminid2") %><br/>
	- <%= jspUtil.label("knowledge.ldap.msg.adminid3") %><br/>
	- <%= jspUtil.label("knowledge.ldap.msg.adminid4") %><br/>
</div>

<form action="<%= request.getContextPath()%>/admin.ldap/check" method="post" role="form" id="ldapForm">
	
	<div class="form-group">
		<label for="plain"><%= jspUtil.label("knowledge.ldap.label.authtype") %></label><br/>
		<label class="radio-inline">
			<input type="radio" value="0" name="authType" 
				id="authtype_db" <%= jspUtil.checked("0", "authType", true) %>/>
			<i class="fa fa-database"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.authtype.db") %>
		</label><br/>
		<label class="radio-inline">
			<input type="radio" value="1" name="authType" 
				id="authtype_ldap" <%= jspUtil.checked("1", "authType", false) %>/>
			<i class="fa fa-user-plus"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.authtype.ldap") %>
		</label><br/>
		<label class="radio-inline">
			<input type="radio" value="2" name="authType" 
				id="authtype_both" <%= jspUtil.checked("2", "authType", false) %>/>
			<i class="fa fa-users"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.authtype.both") %>
		</label>
	</div>
	
	<div class="form-group">
		<label for="host"><%= jspUtil.label("knowledge.ldap.label.host") %><%= jspUtil.label("label.required") %></label>
		<input type="text" class="form-control" name="host" id="host" placeholder="HOST" value="<%= jspUtil.out("host") %>" />
	</div>
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.port") %><%= jspUtil.label("label.required") %></label>
		<input type="text" class="form-control" name="port" id="port" placeholder="PORT" value="<%= jspUtil.out("port") %>" />
	</div>
	
	<div class="form-group">
		<label for="plain"><%= jspUtil.label("knowledge.ldap.label.security") %></label><br/>
		<label class="radio-inline">
			<input type="radio" value="plain" name="security" 
				id="plain" <%= jspUtil.checked("plain", "security", true) %>/>
			<i class="fa fa-unlock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.plain") %>
		</label>
		<label class="radio-inline">
			<input type="radio" value="usessl" name="security" 
				id="usessl" <%= jspUtil.checked("usessl", "security", false) %>/>
			<i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.usessl") %>
		</label>
		<label class="radio-inline">
			<input type="radio" value="usetls" name="security" 
				id="usetls" <%= jspUtil.checked("usetls", "security", false) %>/>
			<i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.usetls") %>
		</label>
	</div>
	
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.basedn") %><%= jspUtil.label("label.required") %></label>
		<input type="text" class="form-control" name="baseDn" id="baseDn" placeholder="Base DN" value="<%= jspUtil.out("baseDn") %>" />
	</div>
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.idattr") %><%= jspUtil.label("label.required") %></label>
		<input type="text" class="form-control" name="idAttr" id="idAttr" placeholder="Id Attribute" value="<%= jspUtil.out("idAttr") %>" />
	</div>
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.nameattr") %></label>
		<input type="text" class="form-control" name="nameAttr" id="nameAttr" placeholder="Name Attribute" value="<%= jspUtil.out("nameAttr") %>" />
	</div>
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.mailattr") %></label>
		<input type="text" class="form-control" name="mailAttr" id="mailAttr" placeholder="Mail Attribute" value="<%= jspUtil.out("mailAttr") %>" />
	</div>
	
	
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.testid") %><%= jspUtil.label("label.required") %></label>
		<input type="text" class="form-control" name="bindDn" id="bindDn" placeholder="ID" value="<%= jspUtil.out("bindDn") %>" />
	</div>
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.testpass") %><%= jspUtil.label("label.required") %></label>
		<input type="password" class="form-control" name="bindPassword" id="bindPassword" placeholder="Password" value="<%= jspUtil.out("bindPassword") %>" />
	</div>
	<div class="form-group">
		<label for="port"><%= jspUtil.label("knowledge.ldap.label.adminid") %><%= jspUtil.label("label.required") %></label>
		<input type="text" class="form-control" name="adminCheckFilter" id="adminCheckFilter" 
			placeholder="<%= jspUtil.label("knowledge.ldap.label.adminid") %>" value="<%= jspUtil.out("adminCheckFilter") %>" />
	</div>
	
	<input type="hidden" name="systemName" value="<%= jspUtil.out("systemName") %>" />
	
	<button type="submit" class="btn btn-success" formaction="<%= request.getContextPath()%>/admin.ldap/check">
		<i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.dotest") %>
	</button>
	<button type="submit" class="btn btn-primary" formaction="<%= request.getContextPath()%>/admin.ldap/save">
		<i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %>
	</button>
	<button type="button" class="btn btn-danger" onclick="deleteConfig();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
</form>


</c:param>

</c:import>

