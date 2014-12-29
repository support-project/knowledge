<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">
	<c:param name="PARAM_CONTENT">
		<div class="container">
		<h3>Error</h3>
		<p>
			<fmt:bundle basename="appresource">
			<fmt:message key="message.httpstatus.401" />
			</fmt:bundle>
		</p>
		</div>
		
	</c:param>

</c:import>



