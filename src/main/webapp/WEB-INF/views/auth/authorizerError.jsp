<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
isErrorPage="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:import url="/WEB-INF/views/commons/layout/layoutNoMenu.jsp">
	
	<c:param name="PARAM_CONTENT">
		アクセスする権限がありません。
	</c:param>

</c:import>

