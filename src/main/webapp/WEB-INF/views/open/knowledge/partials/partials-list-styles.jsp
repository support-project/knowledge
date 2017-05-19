<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- build:css(src/main/webapp) css/page-knowledge-list.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/css/bootstrap-datepicker3.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-list.css" />
<!-- endbuild -->

<style>
.events {
    width: 100%;
    text-align: center;
    border: 1px solid #ecf0f1;
    border-radius: 4px;
}

.datepicker,
.table-condensed {
    width: 100%;
    min-width: 280px;
}

.datepicker table tr td.eventday {
    color: #000;
    background-color: #D0F5A9;
    border-color: #D0F5A9;
}

</style>
