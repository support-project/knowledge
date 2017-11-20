<%@page import="org.support.project.web.config.CommonWebParameter"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_CONTENT">
<div class="container">

<h3>Server Error</h3>

<p>
    <fmt:bundle basename="appresource">
    <fmt:message key="message.httpstatus.500" />
    </fmt:bundle>
</p>

<%= jspUtil.out(CommonWebParameter.ERROR_ATTRIBUTE) %>
<br/>

<%
if (
    request.getRemoteAddr().equals("127.0.0.1") 
        || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
    if (exception == null && request.getAttribute("SERVER_EXCEPTION") != null) {
        exception = (Throwable) request.getAttribute("SERVER_EXCEPTION");
    }
    if (exception != null) {
        java.io.PrintWriter pw = new java.io.PrintWriter(out);
        pw.println("<pre>");
        exception.printStackTrace(pw);
        pw.println("</pre>");
    }
}
%>

<p>
<a href="<%= request.getContextPath() %>/index" class="btn btn-info">Back to Top</a>
</p>


</div>
</c:param>

</c:import>


