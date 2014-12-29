<%@page import="redcomet.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>



<c:param name="PARAM_CONTENT">
<form action="<%= request.getContextPath()%>/protect.account/update" method="post" role="form">

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
		<label for="input_no">登録日時 / 更新日時</label>
		<p class="form-control-static">
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("insertDatetime")%> / 
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
		</p>
	</div>
	
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;更新</button>
	<a href="<%= request.getContextPath()%>/protect.account/withdrawal" class="btn btn-default"><i class="fa fa-remove"></i>&nbsp;退会</a>
	
</form>


</c:param>

</c:import>

