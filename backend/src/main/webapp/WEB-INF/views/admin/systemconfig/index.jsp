<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
    </c:param>

    <c:param name="PARAM_SCRIPTS">
    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%=jspUtil.label("knowledge.navbar.config.system")%></h4>

        <%
            if (!jspUtil.is(Boolean.TRUE, "postgres")) {
        %>
        <div class="alert alert-info alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Information</strong>
            <%=jspUtil.label("knowledge.connection.msg.recommend.postgres")%>
            <br/>
            <%=jspUtil.label("knowledge.sysconfig.msg.recommend.postgres")%>
        </div>
        <% } %>



        <h4 class="sub_title"><%=jspUtil.label("knowledge.navbar.config.admin")%></h4>
        <ul role="menu" class="menu_list">
            <li><a href="<%=request.getContextPath()%>/admin.notice/list" style="cursor: pointer;">
                <i class="fa fa-rocket"></i>&nbsp;<%=jspUtil.label("knowledge.admin.notice.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.users/list" style="cursor: pointer;">
                <i class="fa fa-users"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.admin.users")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.users/accept_list" style="cursor: pointer;">
                <i class="fa fa-gavel"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.admin.acccept")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.template/list" style="cursor: pointer;">
                <i class="fa fa-sticky-note"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.admin.template")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.logging/" style="cursor: pointer;">
                <i class="fa fa-file-archive-o"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.admin.logging")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.mailhook/" style="cursor: pointer;">
                <i class="fa fa-inbox"></i>&nbsp;<%=jspUtil.label("knowledge.admin.post.from.mail")%>
            </a></li>
        </ul>

        <h4 class="sub_title"><%=jspUtil.label("knowledge.navbar.config.system")%></h4>
        <ul role="menu" class="menu_list">
            <li><a href="<%=request.getContextPath()%>/admin.config/system" style="cursor: pointer;">
                <i class="fa fa-cog"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.system.params")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.config/config" style="cursor: pointer;">
                <i class="fa fa-cogs"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.system.general")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.mail/config" style="cursor: pointer;">
                <i class="fa fa-envelope-o"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.config.system.mail")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.ldap/list" style="cursor: pointer;">
                <i class="fa fa-user-plus"></i>&nbsp;<%=jspUtil.label("knowledge.ldap.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.proxy/config" style="cursor: pointer;">
                <i class="fa fa-globe"></i>&nbsp;<%=jspUtil.label("knowledge.proxy.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.webhook/config" style="cursor: pointer;">
                <i class="fa fa-link"></i>&nbsp;<%=jspUtil.label("knowledge.webhook.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.config/analytics" style="cursor: pointer;">
                <i class="fa fa-area-chart"></i>&nbsp;<%=jspUtil.label("knowledge.analytics.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.customservice/config" style="cursor: pointer;">
                <i class="fa fa-magic"></i>&nbsp;<%=jspUtil.label("knowledge.custom.service.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.mailtemplate/index" style="cursor: pointer;">
                <i class="fa fa-envelope-square"></i>&nbsp;<%=jspUtil.label("knowledge.mailtemplate.title")%>
            </a></li>

        </ul>

        <h4 class="sub_title"><%=jspUtil.label("knowledge.navbar.data")%></h4>
        <ul role="menu" class="menu_list">
            <li><a href="<%=request.getContextPath()%>/admin.database/index" style="cursor: pointer;">
                <i class="fa fa-recycle"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.data.backup")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.database/connect" style="cursor: pointer;">
                <i class="fa fa-database"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.data.connect")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.database/reindexing" style="cursor: pointer;">
                <i class="fa fa-refresh"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.data.reindexing")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.aggregate/index" style="cursor: pointer;">
                <i class="fa fa-calculator"></i>&nbsp;<%=jspUtil.label("knowledge.aggregate.title")%>
            </a></li>
            <li><a href="<%=request.getContextPath()%>/admin.database/export" style="cursor: pointer;">
                <i class="fa fa-external-link"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.data.export")%>
            </a></li>
        </ul>

    </c:param>

</c:import>

