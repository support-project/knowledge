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
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.navbar.config") %></h4>


	<ul class="menu_list" role="menu">
		<li>
			<a href="<%= request.getContextPath() %>/protect.account" style="cursor: pointer;">
				<i class="fa fa-smile-o"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.account.myaccount") %>
			</a>
		</li>
		
		<%--
		<li>
			<a href="<%= request.getContextPath() %>/open.knowledge/list?user=<%= jspUtil.id() %>" >
				<i class="fa fa-male"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.account.myknowledge") %>
			</a>
		</li>
		--%>
		<li>
			<a href="<%= request.getContextPath() %>/protect.stock/mylist" >
				<i class="fa fa-star-o"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.account.mystock") %>
			</a>
		</li>
		
		<li id="tabLogout">
			<a href="<%= request.getContextPath() %>/protect.notify" style="cursor: pointer;">
				<i class="fa fa-bell-o"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.account.notify") %>
			</a>
		</li>
		
	</ul>



</c:param>

</c:import>

