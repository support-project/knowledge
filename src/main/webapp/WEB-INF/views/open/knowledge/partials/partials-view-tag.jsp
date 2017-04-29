<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:if test="${!empty tagNames}">
    <i class="fa fa-tags"></i>&nbsp;
    <c:forEach var="tagName" items="${tagNames.split(',')}">
        <a href="<%=request.getContextPath()%>/open.knowledge/list?tagNames=<%=jspUtil.out("tagName", JspUtil.ESCAPE_URL)%>"> <span
            class="tag label label-info"><i class="fa fa-tag"></i><%=jspUtil.out("tagName")%></span>
        </a>
    </c:forEach>
</c:if>



