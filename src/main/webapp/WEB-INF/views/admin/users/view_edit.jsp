<%@page import="redcomet.knowledge.vo.Roles"%>
<%@page import="redcomet.web.util.JspUtil"%>

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
function deleteUser() {
	bootbox.confirm("本当に削除しますか?", function(result) {
		if (result) {
			$('#userForm').attr('action', '<%= request.getContextPath()%>/admin.users/delete');
			$('#userForm').submit();
		}
	}); 
};
</script>
</c:param>



<c:param name="PARAM_CONTENT">

<form action="<%= request.getContextPath()%>/admin.users/save" method="post" role="form" id="userForm">

	<div class="form-group">
		<label for="userKey">Mail Address</label>
		<input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="${userKey}" />
	</div>
	<div class="form-group">
		<label for="userName">User Name</label>
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
		<label for="role_${role.roleId}">権限</label><br/>
		<c:forEach var="role" items="${systemRoles}" varStatus="status">
		<label class="radio-inline">
			<input type="checkbox" value="${role.roleKey}" name="roles" 
				id="role_${role.roleId}" <% 
					if (jspUtil.getValue("role", Roles.class).isChecked()) {
						out.write("checked=\"checked\"");
					} 
				%>/>
			${role.roleName}
		</label>
		</c:forEach>
	</div>
	
	<div class="form-group">
		<label for="input_no">登録日時 / 更新日時</label>
		<p class="form-control-static">
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("insertDatetime")%> / 
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
		</p>
	</div>
	
	<input type="hidden" name="offset" value="${offset}" />
	<input type="hidden" name="userId" value="${userId}" />
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;更新</button>
	<button type="button" class="btn btn-danger" onclick="deleteUser();"><i class="fa fa-remove"></i>&nbsp;削除</button>
	<a href="<%= request.getContextPath() %>/admin.users/list/${offset}"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;一覧へ戻る</a>
	
</form>


</c:param>

</c:import>

