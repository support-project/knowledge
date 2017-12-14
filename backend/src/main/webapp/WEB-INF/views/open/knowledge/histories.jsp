<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
    </c:param>

    <c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-knowledge-histories.js -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<!-- endbuild -->
    <script>
    echo.init();
    </script>
    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%= jspUtil.label("knowledge.histories.title") %>
        page[<%= jspUtil.getValue("page", Integer.class) + 1 %>]
        </h4>
        
        <% 
            String connect = "&";
            if (StringUtils.isEmpty(jspUtil.out("params"))) {
                connect = "?";
            }
        %>
        
        <nav>
            <ul class="pager">
                <li class="previous">
                    <a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("previous") %>">
                        <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                    </a>
                </li>
                <li class="next">
                    <a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("next") %>">
                        <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                    </a>
                </li>
            </ul>
        </nav>
        
        
<div class="list-group">
<c:if test="${empty histories}">
<%= jspUtil.label("knowledge.histories.list.label.empty") %>
</c:if>

<c:forEach var="history" items="${histories}" varStatus="status">
    <a class="list-group-item" href="<%= request.getContextPath() %>/open.knowledge/history/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("page") %>&history_no=<%= jspUtil.out("history.historyNo") %>" >

        <img src="<%= request.getContextPath()%>/images/loader.gif" 
            data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("history.updateUser") %>" 
            alt="icon" width="36" height="36" style="float:left" />

        <h4 class="list-group-item-heading">
        <%= jspUtil.out("history.historyNo") %>
        <%= jspUtil.out("history.title") %>
        </h4>
        
        <p class="list-group-item-text">
        
        <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("history.updateDatetime")%>
        <i class="fa fa-user"></i>&nbsp;
        <%= jspUtil.out("history.userName") %>
        </p>
        
    </a>
</c:forEach>
</div>
        
        
        <nav>
            <ul class="pager">
                <li class="previous">
                    <a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("previous") %>">
                        <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                    </a>
                </li>
                <li class="next">
                    <a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("next") %>">
                        <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                    </a>
                </li>
            </ul>
        </nav>
        
    <a href="<%= request.getContextPath() %>/open.knowledge/view/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>"
        class="btn btn-warning" role="button"><i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
    </a>
    
    <a href="<%= request.getContextPath() %>/open.knowledge/list/<%= jspUtil.out("offset") %><%= jspUtil.out("params") %>"
    class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.view.back.list") %></a>
        
        
    </c:param>

</c:import>

