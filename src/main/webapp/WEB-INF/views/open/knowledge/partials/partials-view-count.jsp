<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.logic.TemplateLogic"%>
<%@page import="org.support.project.knowledge.vo.UploadFile"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<div class="row">
    <div class="col-xs-6 col-sm-3">
        <%-- 
        <a href="<%=request.getContextPath()%>/open.knowledge/activities/<%=jspUtil.out("knowledgeId")%><%=jspUtil.out("params")%>" class="text-primary btn-link">
        <i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;CP × <%= jspUtil.out("point") %>
        </a>
        --%>
        <i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;CP × <%= jspUtil.out("point") %>
    </div>
    <div class="col-xs-6 col-sm-3">
        <a href="<%=request.getContextPath()%>/open.knowledge/likes/<%=jspUtil.out("knowledgeId")%><%=jspUtil.out("params")%>" class="text-primary btn-link">
            <i class="fa fa-thumbs-o-up"></i>&nbsp;<%=jspUtil.label("knowledge.view.like")%> × <span id="like_count"><%=jspUtil.out("like_count")%></span>
        </a>
    </div>
    <div class="col-xs-6 col-sm-3">
        <a href="#comments" id="commentsLink" class="text-primary btn-link inner-page-link">
            <i class="fa fa-comments-o"></i>&nbsp;<%=jspUtil.label("knowledge.view.comment.label")%>
            × <%=jspUtil.out("comments.size()")%>
        </a>
    </div>
    <div class="col-xs-6 col-sm-3">
        <%
            int num = 0;
            List<UploadFile> attachs = jspUtil.getValue("files", List.class);
            for (int i = 0; i < attachs.size(); i++) {
                UploadFile attach = attachs.get(i);
                if (attach.getCommentNo() == null || attach.getCommentNo() == 0) {
                    num++;
                }
            }
        %>
        <a href="#attachFilesPanel" id="attachFilesLink" class="text-primary btn-link inner-page-link">
            <i class="fa fa-download"></i>&nbsp;<%=jspUtil.label("knowledge.view.label.attach")%>
            × <%= num %>
        </a>
    </div>
    <div class="col-xs-4">
        <% if (jspUtil.is(TemplateLogic.TYPE_ID_EVENT, "typeId")) { %>
        <a id="eventInfoLink" class="text-primary btn-link">
            <i class="fa fa-users"></i>&nbsp;<%= jspUtil.label("knowledge.view.label.participants") %>
            <span id="eventStatus"></span>
        </a>
        <% } %>
    </div>
</div>

