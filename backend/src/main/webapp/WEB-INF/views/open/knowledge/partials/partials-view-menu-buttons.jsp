<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.logic.TemplateLogic"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<div class="article_buttons">
    <div>
        <%
            if (request.getRemoteUser() != null) {
                if ((Boolean) request.getAttribute("edit")) {
        %>
        <a href="<%=request.getContextPath()%>/protect.knowledge/view_edit/<%=jspUtil.out("knowledgeId")%>"
            class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp; <%=jspUtil.label("knowledge.view.edit")%>
        </a>
        <%
            } else {
        %>
        <button class="btn btn-primary btn_edit disabled" disabled="disabled">
            <i class="fa fa-info-circle"></i>&nbsp;<%=jspUtil.label("knowledge.view.edit.disable")%>
        </button>
        <%
            }
        %>
        <%
            } else {
        %>
        <a href="<%=request.getContextPath()%>/protect.knowledge/view_edit/<%=jspUtil.out("knowledgeId")%>"
            class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp; <%=jspUtil.label("knowledge.view.edit.with.login")%>
        </a>
        <%
            }
        %>
    </div>

    <div>
        <%
            if (request.getRemoteUser() != null) {
        %>
        <button type="button" class="btn btn-info btn_stock" data-toggle="modal" data-target="#stockModal">
            <i class="fa fa-star-o"></i>&nbsp;
            <%=jspUtil.label("knowledge.view.fav")%>
        </button>
        <%
            } else {
        %>
        <a href="<%=request.getContextPath()%>/protect.knowledge/view/<%=jspUtil.out("knowledgeId")%>"
            class="btn btn-info btn_stock" role="button"> <i class="fa fa-star-o"></i>&nbsp; <%=jspUtil.label("knowledge.view.fav")%><%--(<%= jspUtil.label("knowledge.navbar.signin") %>) --%>
        </a>
        <%
            }
        %>
        <button class="btn btn-info btn_like" onclick="addlike(<%=jspUtil.out("knowledgeId")%>);">
            <i class="fa fa-thumbs-o-up"></i>&nbsp;
            <%=jspUtil.label("knowledge.view.like")%>
        </button>
    </div>
    
    <div>
        <button class="btn btn-info btn_agenda" onclick="showAgenda();" id="buttonToc">
            <i class="fa fa-list"></i>&nbsp;
            <%=jspUtil.label("knowledge.view.label.show.toc")%>
        </button>
        <button class="btn btn-info btn_copy_url" data-clipboard-text="<%= jspUtil.out("url") %>" id="urlBtn">
            <i class="fa fa-copy"></i>&nbsp;
            <%=jspUtil.label("knowledge.view.label.copy.url")%>
        </button>
    </div>
    
    <div>
    <% if (jspUtil.is(TemplateLogic.TYPE_ID_EVENT, "typeId")) { %>
        <% if (jspUtil.logined()) { %>
            <button class="btn btn-info btn_col2 hide" id="btnParticipation">
                <i class="fa fa-user-plus"></i>&nbsp;
                <%=jspUtil.label("knowledge.view.label.participation")%>
            </button>
            <button class="btn btn-info btn_col2 text-warning hide" id="btnNonparticipation">
                <i class="fa fa-user-times"></i>&nbsp;
                <%=jspUtil.label("knowledge.view.label.nonparticipation")%>
            </button>
        <% } else { %>
            <a href="<%= request.getContextPath() %>/protect.knowledge/view/<%=jspUtil.out("knowledgeId")%>" class="btn btn-info btn_col2">
                <i class="fa fa-user-plus"></i>&nbsp;
                <%=jspUtil.label("knowledge.view.label.participation")%>
            </a>
        <% } %>
    <% } %>
        <a id="btnAnswerSurvey" class="btn btn-info btn_col2 hide" data-toggle="modal" href="#modalAnswerSurvey">
            <i class="fa fa-check-square-o"></i>&nbsp;
            <%=jspUtil.label("knowledge.view.label.answer")%>
        </a>
    </div>

    <%
        if (request.getRemoteUser() != null && (Boolean) request.getAttribute("edit")) {
    %>
    <!-- Survey -->
    <div >
        <a id="editSurveyBtn" class="btn btn-info btn_col2" href="<%= request.getContextPath()%>/protect.survey/edit/<%=jspUtil.out("knowledgeId")%>">
            <i class="fa fa-th-list"></i>&nbsp;
            <%=jspUtil.label("knowledge.survey.label.edit")%>
        </a>
        
        <a id="answersSurveyBtn" class="btn btn-info btn_col2" href="<%= request.getContextPath()%>/protect.survey/answers/<%=jspUtil.out("knowledgeId")%>">
            <i class="fa fa-bar-chart"></i>&nbsp;
            <%=jspUtil.label("knowledge.survey.label.report")%>
        </a>
    </div>
    <% } %>



</div>

