<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<% String hide = "hide"; %>
<span class="dispKnowledgeInfo">
<% if (jspUtil.getValue("knowledgeId", Long.class) != null) { %>
    <% hide = ""; %>
    <%-- <div class="form-group title" id="title_msg"><%= jspUtil.label("knowledge.edit.title") %></div> --%>
    <!-- info -->
    #<%= jspUtil.out("knowledgeId") %>
     / 
    <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
<% } else { %>
    <%-- <div class="form-group title" id="title_msg"><%= jspUtil.label("knowledge.add.title") %></div> --%>
    [<%= jspUtil.label("label.new") %>]
<% } %>
</span>

<!-- title -->
<div class="form-group">
    <label for="input_title"><%= jspUtil.label("knowledge.add.label.title") %></label>
    <input type="text" class="form-control" name="title" id="input_title" placeholder="<%= jspUtil.label("knowledge.add.label.title") %>" value="<%= jspUtil.out("title") %>" />
</div>

<%
    String draft = "";
    if (jspUtil.getValue("draftId", Long.class) == null) {
        draft = "hide";
    }

%>

<div class="form-group <%= draft %>" id="draft_flag">
    <%= jspUtil.label("knowledge.add.draft.flag") %>
    <span class="tips_info"><%= jspUtil.label("knowledge.add.draft.info") %></span>
</div>

