<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% exception.printStackTrace(); %>

<h3>Error</h3>

<p>
    <fmt:bundle basename="appresource">
    <fmt:message key="message.httpstatus.500" />
    </fmt:bundle>
</p>

<%
if (request.getRemoteAddr().startsWith("192.168.") || request.getRemoteAddr().equals("127.0.0.1")) {
    exception.printStackTrace(new java.io.PrintWriter(out)); 
}
%>


