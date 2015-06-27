<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/commons/layout/commonHeader.jsp" />

<%-- 後からヘッダー部に何か追加する場合のパラメータ --%>
<c:if test="${param.PARAM_HEAD != null}">
	${param.PARAM_HEAD}
</c:if>

<title>
<c:if test="${param.PARAM_PAGE_TITLE != null}">
	${param.PARAM_PAGE_TITLE}
</c:if>
<c:if test="${param.PARAM_PAGE_TITLE == null}">
	<%= jspUtil.label("knowledge.title") %>
</c:if>
</title>

<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/common.css") %>" />

</head>

<body>

	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="<%=request.getContextPath()%>/">
				<i class="fa fa-book"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.title") %>
				<span style="font-size: 8pt;"><%= jspUtil.label("label.version") %></span>
				</a>
			</div>
			<div class="navbar-collapse collapse">
			</div>
		</div>
	</div>

<div class="container">
${param.PARAM_CONTENT}
</div>



	<div id="footer">
		<ul class="footer-menu list-inline">
			<li class="first">
				<a class="" href="<%= request.getContextPath() %>/index" style="cursor: pointer;"> About</a>
			</li>
			<li>
				<a class="" href="<%= request.getContextPath() %>/open.knowledge/list" style="cursor: pointer;"> Show Knowledges</a>
			</li>
			<li>
				<a class="" href="<%= request.getContextPath() %>/open.license" style="cursor: pointer;"> License</a>
			</li>
		</ul>
		<!-- /nav -->
		<div class="clearfix"></div>
		<div class="copy">
			<span>Copyright &#169; 2015 <a href="https://support-project.org/knowledge_info/index">support-project.org [Knowledge project]</a></span>
		</div>
		<!-- /copy -->
	</div>
	<!-- /footer -->


<c:import url="/WEB-INF/views/commons/layout/commonScripts.jsp" />

<c:if test="${param.PARAM_SCRIPTS != null}">
${param.PARAM_SCRIPTS}
</c:if>

</body>
</html>

