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
<h4 class="title"><%= jspUtil.label("knowledge.accept.title") %></h4>


<div class="list-group">
<c:if test="${empty entries}">
<%= jspUtil.label("knowledge.accept.label.list.empty") %>
</c:if>

<c:forEach var="entry" items="${entries}" varStatus="status">
	<div class="list-group-item">
		<a href="<%= request.getContextPath() %>/admin.users/accept/${entry.id}" class="btn btn-primary">
			<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.accept.label.accept") %>
		</a>
		<h4 class="list-group-item-heading">${entry.userName} (${entry.userKey})</h4>
		<p class="list-group-item-text">
		<%= jspUtil.label("label.regist.datetime") %>
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("user.insertDatetime")%> / 
		</p>
	</div>
		
</c:forEach>
</div>




</c:param>

</c:import>

