<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>



<c:param name="PARAM_CONTENT">


<div class="list-group">
<c:if test="${empty entries}">
承認待ちのユーザはいません
</c:if>

<c:forEach var="entry" items="${entries}" varStatus="status">
	<div class="list-group-item">
		<a href="<%= request.getContextPath() %>/admin.users/accept/${entry.id}" class="btn btn-primary">
			<i class="fa fa-gavel"></i>&nbsp;承認
		</a>
		<h4 class="list-group-item-heading">${entry.userName} (${entry.userKey})</h4>
		<p class="list-group-item-text">
		登録日時
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("user.insertDatetime")%> / 
		</p>
	</div>
		
</c:forEach>
</div>




</c:param>

</c:import>

