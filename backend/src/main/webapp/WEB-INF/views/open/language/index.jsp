<%@page import="java.util.Locale"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.bean.LabelValue"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>

<c:param name="PARAM_CONTENT">
    <h4 class="title"><%= jspUtil.label("knowledge.language.title") %></h4>

    <ul>
    <%
        AppConfig appConfig = AppConfig.get();
        List<LabelValue> languages = appConfig.getLanguages();
        for (LabelValue language : languages) {
    %>
    <li >
        <a href="<%= request.getContextPath() %>/lang/select/<%= language.getValue() %>" style="cursor: pointer;">
            <%-- <i class="flag-icon flag-icon-<%= language.getLabel() %>"></i>&nbsp; --%>
            <%= jspUtil.locale(language.getValue()).getDisplayName(jspUtil.locale(language.getValue())) %>
        </a>
    </li>
    <%
        }
    %>
    </ul>

    
<a href="<%=request.getContextPath()%>/protect.config/index/" class="btn btn-info">
    <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
</a>
    
</c:param>

</c:import>

