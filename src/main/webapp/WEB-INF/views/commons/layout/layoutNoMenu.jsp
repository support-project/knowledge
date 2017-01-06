<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.config.AnalyticsConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
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

<div class="container" id="content_top">
${param.PARAM_CONTENT}
</div>

<c:import url="/WEB-INF/views/commons/layout/commonFooter.jsp" />

<c:import url="/WEB-INF/views/commons/layout/commonScripts.jsp" />

<c:if test="${param.PARAM_SCRIPTS != null}">
${param.PARAM_SCRIPTS}
</c:if>

<%= AnalyticsConfig.get().getAnalyticsScript() %>

</body>
</html>

