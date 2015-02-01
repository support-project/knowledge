<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
	errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String top = "/open.knowledge/list";
%>
<%
	JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

	<c:param name="PARAM_HEAD">
	</c:param>

	<c:param name="PARAM_SCRIPTS">
	</c:param>

	<c:param name="PARAM_CONTENT">
		<h4 class="title"><%= jspUtil.label("knowledge.view.title") %></h4>

		<div class="row">
			<br/>
			<div class="col-sm-12 col-md-8">
			<form role="search" action="<%=request.getContextPath()%><%=top%>">
				<div class="input-group">
					<div class="input-group-addon"><i class="fa fa-pencil-square-o"></i></div>
					<input type="text" class="form-control" placeholder="<%= jspUtil.label("knowledge.search.placeholder") %>"
						name="keyword" id="keyword" value="<%=jspUtil.out("keyword")%>" />
					<div class="input-group-btn">
					</div>
				</div>
			<br/>
				<button class="btn btn-primary" type="submit">
					<i class="glyphicon glyphicon-search"></i>&nbsp;<%= jspUtil.label("label.search") %>
				</button>
				<a href="<%= request.getContextPath() %>/open.knowledge/list/<%=jspUtil.out("offset")%>?keyword=<%=jspUtil.out("keyword")%>&tag=<%=jspUtil.out("tag")%>&user=<%=jspUtil.out("user")%>"
				class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
			</form>
			</div>
		</div>

	</c:param>

</c:import>

