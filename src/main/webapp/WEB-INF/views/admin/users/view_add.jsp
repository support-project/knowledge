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

</c:param>



<c:param name="PARAM_CONTENT">

<form action="<%= request.getContextPath()%>/admin.users/create" method="post" role="form">

	<div class="form-group">
		<label for="userKey">Mail Address</label>
		<input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="${userKey}" />
	</div>
	<div class="form-group">
		<label for="userName">User Name</label>
		<input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" value="${userName}" />
	</div>
	
	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" class="form-control" name="password" id="password" placeholder="Password" value="${password}" />
	</div>
	<div class="form-group">
		<label for="confirm_password">Confirm Password</label>
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
	
	<input type="hidden" name="offset" value="${offset}" />
	
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;登録</button>
	<a href="<%= request.getContextPath() %>/admin.users/list/${offset}"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;一覧へ戻る</a>
	
</form>


</c:param>

</c:import>

