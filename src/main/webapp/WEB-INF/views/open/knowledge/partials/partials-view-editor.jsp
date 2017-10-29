<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<div class="insert_info">
    <img src="<%=request.getContextPath()%>/images/loader.gif"
        data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("insertUser")%>" alt="icon" width="24"
        height="24" />
    <%
        String insertLink = "<a href=\"" + request.getContextPath() + "/open.account/info/" + jspUtil.out("insertUser") + "\" class=\"text-primary btn-link\" >"
                        + jspUtil.out("insertUserName", JspUtil.ESCAPE_CLEAR) + "</a>";
    %>
    <%=jspUtil.label("knowledge.view.info.insert", insertLink, jspUtil.date("insertDatetime"))%>

<% if (!jspUtil.date("insertDatetime").equals(jspUtil.date("updateDatetime"))) { %>
(
    <img src="<%=request.getContextPath()%>/images/loader.gif"
        data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("updateUser")%>" alt="icon" width="24"
        height="24" />
    <%
        String updateLink = "<a href=\"" + request.getContextPath() + "/open.account/info/" + jspUtil.out("updateUser") + "\" class=\"text-primary btn-link\">"
                        + jspUtil.out("updateUserName", JspUtil.ESCAPE_CLEAR) + "</a>";
    %>
    <%=jspUtil.label("knowledge.view.info.update", updateLink, jspUtil.date("updateDatetime"))%>
    <%
        String historyLink = "<a href=\"" + request.getContextPath() + "/open.knowledge/histories/" + jspUtil.out("knowledgeId") + "\" class=\"text-primary btn-link\">&lt;"
                        + jspUtil.label("knowledge.view.info.history") + "&gt;</a>";
    %>
    <%= historyLink %>
)
<% } %>
</div>

