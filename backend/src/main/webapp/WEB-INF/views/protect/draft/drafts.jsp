<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.common.util.StringUtils"%>
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
        <h4 class="title"><%= jspUtil.label("knowledge.draft.list.title") %> 
            <span style="font-size: 14px">page[<%= jspUtil.getValue("offset", Integer.class) + 1 %>]</span>
        </h4>

        <nav>
            <ul class="pager">
                <li class="previous">
                    <a href="<%= request.getContextPath() %>/protect.draft/list/<%= jspUtil.out("previous") %>">
                        <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                    </a>
                </li>
                <li class="next">
                    <a href="<%= request.getContextPath() %>/protect.draft/list/<%= jspUtil.out("next") %>">
                        <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                    </a>
                </li>
            </ul>
        </nav>
        
        
        
<div class="list-group">
<c:if test="${empty drafts}">
<%= jspUtil.label("knowledge.draft.list.empty") %>
</c:if>

<c:forEach var="draft" items="${drafts}" varStatus="status">
    <a href="<%= request.getContextPath() %>/protect.draft/view/<%= jspUtil.out("draft.draftId")%>" class="list-group-item">
        <h4 class="list-group-item-heading">
            <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("draft.updateDatetime")%>
            <% if (StringUtils.isEmpty(jspUtil.getValue("draft.title", String.class))) { %>
                <%= jspUtil.label("knowledge.draft.list.empty.title") %>
            <% } else { %>
                <%= jspUtil.out("draft.title", jspUtil.ESCAPE_HTML, 40) %>
            <% } %>
        </h4>
        <p class="list-group-item-text">
            <%= jspUtil.out("draft.content", jspUtil.ESCAPE_HTML, 40) %>
        </p>
    </a>
</c:forEach>
</div>
        
        
        
        
        
        
        
        
        <nav>
            <ul class="pager">
                <li class="previous">
                    <a href="<%= request.getContextPath() %>/protect.draft/list/<%= jspUtil.out("previous") %>">
                        <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                    </a>
                </li>
                <li class="next">
                    <a href="<%= request.getContextPath() %>/protect.draft/list/<%= jspUtil.out("next") %>">
                        <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                    </a>
                </li>
            </ul>
        </nav>


    </c:param>

</c:import>





