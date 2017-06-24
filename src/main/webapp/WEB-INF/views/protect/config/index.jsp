<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
    </c:param>

    <c:param name="PARAM_SCRIPTS">
    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%=jspUtil.label("knowledge.navbar.config")%></h4>

        <h4 class="sub_title"><%=jspUtil.label("knowledge.config.config")%></h4>

        <ul class="menu_list" role="menu">
            <li><a href="<%=request.getContextPath()%>/protect.account" style="cursor: pointer;">
                <i class="fa fa-smile-o"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.account.myaccount")%>
            </a></li>
            
            <% if (jspUtil.is(Boolean.TRUE, "ldapExists")) { %>
            <li><a href="<%=request.getContextPath()%>/protect.connect" style="cursor: pointer;">
                <i class="fa fa-link"></i>&nbsp;<%=jspUtil.label("knowledge.connect.title")%>
            </a></li>
            <% } %>
            
            <li><a href="<%=request.getContextPath()%>/protect.notify" style="cursor: pointer;">
                <i class="fa fa-bell-o"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.account.notify")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/protect.account/targets" style="cursor: pointer;">
                <i class="fa fa-paper-plane-o"></i>&nbsp;<%=jspUtil.label("knowledge.account.targets")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/open.thema/list" style="cursor: pointer;">
                <i class="fa fa-television"></i>&nbsp;<%=jspUtil.label("knowledge.config.thema")%>
            </a></li>
            <li><a class="" href="<%=request.getContextPath()%>/open.language" style="cursor: pointer;">
                <i class="fa fa-language"></i>&nbsp;<%=jspUtil.label("knowledge.language.title")%>
            </a></li>
            <li><a class="" href="<%=request.getContextPath()%>/protect.token" style="cursor: pointer;">
                <i class="fa fa-id-card-o"></i>&nbsp;<%=jspUtil.label("knowledge.token.title")%>
            </a></li>
        </ul>

        <h4 class="sub_title"><%=jspUtil.label("knowledge.config.list")%></h4>

        <ul class="menu_list" role="menu">
            <li><a href="<%=request.getContextPath()%>/open.knowledge/list?user=<%=jspUtil.id()%>">
                <i class="fa fa-male"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.account.myknowledge")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/protect.draft/list">
                <i class="fa fa-database"></i>&nbsp;<%=jspUtil.label("knowledge.draft.list.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/protect.stock/mylist">
                <i class="fa fa-star-o"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.account.mystock")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/protect.group/list">
                <i class="fa fa-group"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.group.list")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/open.tag/list">
                <i class="fa fa-tags"></i>&nbsp;<%=jspUtil.label("knowledge.list.tags.list")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/open.notice/list">
                <i class="fa fa-rocket"></i>&nbsp;<%=jspUtil.label("knowledge.notice.title")%>
            </a></li>
        </ul>


    </c:param>

</c:import>

