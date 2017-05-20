<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<% if (jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag")) { %>
<%=jspUtil.label("label.public.view")%>
<% } %>

<% if (jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag")) { %>
<%=jspUtil.label("label.private.view")%>
<% } %>

<% if (jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "publicFlag")) { %>
<%=jspUtil.label("label.protect.view")%>
    <c:if test="${targets.containsKey(knowledgeId)}">
        <c:forEach var="target" items="${targets.get(knowledgeId)}">
            <c:choose>
                <c:when test="${targetLogic.isGroupLabel(target.value)}">
                    <c:set var="groupId" value="${targetLogic.getGroupId(target.value)}" />
                    <a href="<%=request.getContextPath()%>/open.knowledge/list?group=<%=jspUtil.out("groupId")%>"> <span
                        class="tag label label-success"><i class="fa fa-users"></i><%=jspUtil.out("target.label")%></span>
                    </a>
                </c:when>
                <c:when test="${targetLogic.isUserLabel(target.value)}">
                    <c:set var="userId" value="${targetLogic.getUserId(target.value)}" />
                    <a href="<%=request.getContextPath()%>/open.knowledge/list?user=<%=jspUtil.out("userId")%>"> <span
                        class="tag label label-success"><i class="fa fa-user"></i><%=jspUtil.out("target.label")%></span>
                    </a>
                </c:when>
                <c:otherwise>
                    <a><span class="tag label label-success"><%=jspUtil.out("target.label")%></span>
                    </a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        &nbsp;
    </c:if>
<% } %>
