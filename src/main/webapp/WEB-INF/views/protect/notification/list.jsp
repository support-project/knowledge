<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/notification-list.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-list.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
var UNREAD_LIST = [
<c:forEach var="notification" items="${notifications}"><c:if test="${notification.status == 0}"><%= jspUtil.out("notification.no") %>,</c:if></c:forEach>
];
</script>
<!-- build:js(src/main/webapp) js/page-notification-list.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/notification-list.js"></script>
<!-- endbuild -->
</c:param>


<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.notification.title") %></h4>

<div class="col-sm-6">
<c:if test="${empty all}">
<i class="fa fa-dot-circle-o" aria-hidden="true"></i>
<%= jspUtil.label("knowledge.notification.list.only.unread") %>
<a href="<%= request.getContextPath() %>/protect.notification/list?all=true">
<i class="fa fa-circle-o" aria-hidden="true"></i>
<%= jspUtil.label("knowledge.notification.list.all") %>
</a>
</c:if>
<c:if test="${!empty all}">
<a href="<%= request.getContextPath() %>/protect.notification/list">
<i class="fa fa-circle-o" aria-hidden="true"></i>
<%= jspUtil.label("knowledge.notification.list.only.unread") %>
</a>
<i class="fa fa-dot-circle-o" aria-hidden="true"></i>
<%= jspUtil.label("knowledge.notification.list.all") %>
</c:if>
</div>
<div class="col-sm-6 text-right">
<button class="btn btn-link" id="btnMarkAllAsRead">
<i class="fa fa-check" aria-hidden="true"></i>
<%= jspUtil.label("label.mark.all.as.read") %>
</button>
</div>

<nav>
    <ul class="pager">
        <li class="previous">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("previous") %><c:if test="${!empty all}">?all=true</c:if>">
                <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
            </a>
        </li>
        <li class="next">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("next") %><c:if test="${!empty all}">?all=true</c:if>">
                <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
            </a>
        </li>
    </ul>
</nav>


<c:if test="${empty notifications}">
    <div class="col-sm-12">
    empty
    </div>
</c:if>

<div class="list-group">
    <c:forEach var="notification" items="${notifications}">
        <c:if test="${notification.status == 0}">
            <a class="list-group-item unread" 
                href="<%= request.getContextPath() %>/protect.notification/view/<%= jspUtil.out("notification.no") %><c:if test="${!empty all}">?all=true</c:if>" >
                <h4 class="list-group-item-heading">
                    <span class="dispKnowledgeId">@<%= jspUtil.out("notification.no") %></span>
                    [<%= jspUtil.label("label.unread") %>]
                    <%= jspUtil.out("notification.title") %>
                </h4>
                <p class="list-group-item-text"><%= jspUtil.date("notification.insertDatetime") %></p>
            </a>
        </c:if>
        <c:if test="${notification.status != 0}">
            <a class="list-group-item " 
                href="<%= request.getContextPath() %>/protect.notification/view/<%= jspUtil.out("notification.no") %><c:if test="${!empty all}">?all=true</c:if>" >
                <h4 class="list-group-item-heading">
                    <span class="dispKnowledgeId">@<%= jspUtil.out("notification.no") %></span>
                    <%= jspUtil.out("notification.title") %>
                </h4>
                <p class="list-group-item-text"><%= jspUtil.date("notification.insertDatetime") %></p>
            </a>
        </c:if>
    </c:forEach>
</div>





<nav>
    <ul class="pager">
        <li class="previous">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("previous") %><c:if test="${!empty all}">?all=true</c:if>">
                <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
            </a>
        </li>
        <li class="next">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("next") %><c:if test="${!empty all}">?all=true</c:if>">
                <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
            </a>
        </li>
    </ul>
</nav>

</c:param>

</c:import>

