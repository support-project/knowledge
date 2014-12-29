<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">
	<c:param name="PARAM_CONTENT">
		<div class="container">

<h3>Server Error</h3>

<p>
	<fmt:bundle basename="appresource">
	<fmt:message key="message.httpstatus.500" />
	</fmt:bundle>
</p>


<%
if (request.getRemoteAddr().startsWith("192.168.") || request.getRemoteAddr().equals("127.0.0.1")) {
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

	</div>
	</c:param>

</c:import>


