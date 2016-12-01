<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<% String hide = "hide";
   if (!StringUtils.isEmpty(jspUtil.getValue("knowledgeId", Long.class))) {
       hide = "";
   }
%>
<div class="article_buttons">
    <div>
        <button type="submit" class="btn btn-primary btn_2" id="draftbutton">
            <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.draft") %>
        </button>
        <button type="button" class="btn btn-info btn_2" id="releasebutton">
            <i class="fa fa-rocket"></i>&nbsp;<%= jspUtil.label("label.release") %>
        </button>
    </div>
    <div>
        <button type="button" class="btn btn-danger btn_2 <%= hide %>" onclick="deleteKnowledge();" id="deleteButton">
            <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
        </button>
        <a href="<%= request.getContextPath() %>/open.knowledge/view/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>"
            class="btn btn-warning btn_2 <%= hide %>" role="button" id="cancelButton">
            <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.cancel") %>
        </a>
    </div>
    <div>
        <a href="<%= request.getContextPath() %>/open.knowledge/list/<%= jspUtil.out("offset") %><%= jspUtil.out("params") %>"
            class="btn btn-success btn_2" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %>
        </a>
    </div>
</div>

