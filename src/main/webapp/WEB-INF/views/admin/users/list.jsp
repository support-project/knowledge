<%@page import="redcomet.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>



<c:param name="PARAM_CONTENT">

<nav>
	<ul class="pager">
		<li class="previous">
			<a href="<%= request.getContextPath() %>/admin.users/list/${previous}"><span aria-hidden="true">&larr;</span>Previous</a>
		</li>
		<li >
		<a href="<%= request.getContextPath() %>/admin.users/view_add?offset=${offset}"><i class="fa fa-plus-circle"></i>&nbsp;Add</a>
		</li>
		<li class="next">
			<a href="<%= request.getContextPath() %>/admin.users/list/${next}">Next <span aria-hidden="true">&rarr;</span></a>
		</li>
	</ul>
</nav>


<div class="list-group">
<c:if test="${empty users}">
empty
</c:if>

<c:forEach var="user" items="${users}" varStatus="status">
	<a href="<%= request.getContextPath() %>/admin.users/view_edit/${user.userId}?offset=${offset}" class="list-group-item">
		<h4 class="list-group-item-heading">${user.userName} (${user.userKey})</h4>
		<p class="list-group-item-text">
		登録日時 / 更新日時 
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("user.insertDatetime")%> / 
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("user.updateDatetime")%>
		</p>
		
	</a>
</c:forEach>
</div>


<nav>
	<ul class="pager">
		<li class="previous">
			<a href="<%= request.getContextPath() %>/admin.users/list/${previous}"><span aria-hidden="true">&larr;</span>Previous</a>
		</li>
		<li >
		<a href="<%= request.getContextPath() %>/admin.users/view_add?offset=${offset}"><i class="fa fa-plus-circle"></i>&nbsp;Add</a>
		</li>
		<li class="next">
			<a href="<%= request.getContextPath() %>/admin.users/list/${next}">Next <span aria-hidden="true">&rarr;</span></a>
		</li>
	</ul>
</nav>


</c:param>

</c:import>

