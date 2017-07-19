<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
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
<h4 class="title"><%= jspUtil.label("knowledge.notification.title") %></h4>

<nav>
    <ul class="pager">
        <li class="previous">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("previous") %>">
                <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
            </a>
        </li>
        <li class="next">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("next") %>">
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
        <a class="list-group-item " 
            href="<%= request.getContextPath() %>/protect.notification/view/<%= jspUtil.out("notification.no") %>" >
            <%= jspUtil.out("notification.title") %>
        </a>
        
    </c:forEach>
</div>





<nav>
    <ul class="pager">
        <li class="previous">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("previous") %>">
                <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
            </a>
        </li>
        <li class="next">
            <a href="<%= request.getContextPath() %>/protect.notification/list/<%= jspUtil.out("next") %>">
                <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
            </a>
        </li>
    </ul>
</nav>

</c:param>

</c:import>

