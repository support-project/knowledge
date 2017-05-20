<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:if test="${!empty stocks}">
    <i class="fa fa-star-o"></i>
    <c:forEach var="stock" items="${stocks}" varStatus="status">
        <a href="<%=request.getContextPath()%>/open.knowledge/stocks?stockid=<%=jspUtil.out("stock.stockId")%>"> <span
            class="tag label label-primary"> <i class="fa fa-star"></i><%=jspUtil.out("stock.stockName")%></span>
        </a>&nbsp;
    </c:forEach>
</c:if>


