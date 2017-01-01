<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
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
<h4 class="title"><%= jspUtil.label("knowledge.tags.title") %></h4>
    
    <nav>
        <ul class="pager">
            <li class="previous">
                <a href="<%= request.getContextPath() %>/open.tag/list/<%= jspUtil.out("previous") %>">
                    <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                </a>
            </li>
            <li class="next">
                <a href="<%= request.getContextPath() %>/open.tag/list/<%= jspUtil.out("next") %>">
                    <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                </a>
            </li>
        </ul>
    </nav>

    <c:if test="${empty tags}">
        <div class="col-sm-12">
        Empty
        </div>
    </c:if>
    
    <div class="list-group">
        <c:forEach var="tag" items="${tags}">
            <a class="list-group-item " 
            href="<%= request.getContextPath() %>/open.knowledge/list?tag=<%= jspUtil.out("tag.tagId") %>" >
                <span class="badge"><%= jspUtil.out("tag.knowledgeCount") %></span>
                <i class="fa fa-tag"></i>&nbsp;<%= jspUtil.out("tag.tagName") %>
            </a>
        </c:forEach>
    </div>

    <nav>
        <ul class="pager">
            <li class="previous">
                <a href="<%= request.getContextPath() %>/open.tag/list/<%= jspUtil.out("previous") %>">
                    <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                </a>
            </li>
            <li class="next">
                <a href="<%= request.getContextPath() %>/open.tag/list/<%= jspUtil.out("next") %>">
                    <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                </a>
            </li>
        </ul>
    </nav>

</c:param>

</c:import>

