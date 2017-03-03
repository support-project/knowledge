<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.common.util.DateUtils"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.control.NoticesControl"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.config.AppConfig"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
        <jsp:include page="partials/partials-list-styles.jsp"></jsp:include>
    </c:param>

    <c:param name="PARAM_SCRIPTS">
        <jsp:include page="partials/partials-list-scripts.jsp"></jsp:include>
        <script>
        _SELECTED_DATE = '<%= jspUtil.out("date") %>';
        </script>
    </c:param>

    <c:param name="PARAM_CONTENT">

        <!-- Title -->
        <div class="row">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/open.knowledge/list"><%=jspUtil.label("knowledge.list.kind.list")%></a></li>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_popularity"><%=jspUtil.label("knowledge.list.kind.popular")%></a></li>
                <% if (jspUtil.logined()) { %>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/stocks"><%= jspUtil.label("knowledge.list.kind.stock") %></a></li>
                <% } %>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_history"><%=jspUtil.label("knowledge.list.kind.history")%></a></li>
            </ul>
        </div>

        <!-- Filter -->
        <div class="row">
        &nbsp;
        </div>
        <div class="row">
            <div class="col-sm-12">
            <i class="fa fa-calendar"></i>&nbsp;
            <strong>
            <%= jspUtil.label("knowledge.list.event.start", DateUtils.gateDayFormat().format(jspUtil.getValue("start", Date.class))) %>
            </strong>
            </div>
        </div>
        
        <!-- リスト -->
        <div class="row" id="knowledgeList">
            <%
                request.setAttribute("list_data", jspUtil.getValue("knowledges", List.class));
            %>
            <c:import url="/WEB-INF/views/open/knowledge/partials/common_list.jsp" />
            <c:import url="/WEB-INF/views/open/knowledge/partials/common_sub_list.jsp" />
        </div>
    </c:param>

</c:import>

