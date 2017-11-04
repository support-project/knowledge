<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>
<% 
    String connect = "&";
    if (StringUtils.isEmpty(jspUtil.out("params"))) {
        connect = "?";
    }
%>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
    </c:param>

    <c:param name="PARAM_SCRIPTS">
    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%= jspUtil.label("knowledge.likes.title") %>
        page[<%= jspUtil.getValue("page", Integer.class) + 1 %>]
        </h4>
        
        <nav>
            <ul class="pager">
                <li class="previous">
                    <a href="<%= request.getContextPath() %>/open.knowledge/likes/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("previous") %>">
                        <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                    </a>
                </li>
                <li class="next">
                    <a href="<%= request.getContextPath() %>/open.knowledge/likes/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("next") %>">
                        <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                    </a>
                </li>
            </ul>
        </nav>
        
        
<ul class="list-group">
<c:if test="${empty likes}">
<%= jspUtil.label("knowledge.likes.list.label.empty") %>
</c:if>

<c:forEach var="like" items="${likes}" varStatus="status">
    <li class="list-group-item">
        <h4 class="list-group-item-heading">
        <i class="fa fa-user"></i>&nbsp;
        <c:if test="${empty like.userName}">
        <%= jspUtil.label("knowledge.likes.anonymous") %>
        </c:if>
        <%= jspUtil.out("like.userName") %>
        </h4>
        
        <p class="list-group-item-text">
        <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("like.insertDatetime")%>
        </p>
        
    </li>
</c:forEach>
</ul>
        
        
        <nav>
            <ul class="pager">
                <li class="previous">
                    <a href="<%= request.getContextPath() %>/open.knowledge/likes/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("previous") %>">
                        <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                    </a>
                </li>
                <li class="next">
                    <a href="<%= request.getContextPath() %>/open.knowledge/likes/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("next") %>">
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

