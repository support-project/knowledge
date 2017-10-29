<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<% 
    String deleteHide = "hide";
    String draftDeleteHide = "hide";
    String cancelUrl = request.getContextPath() + "/open.knowledge/list" + jspUtil.out("params");
    if (jspUtil.getValue("knowledgeId", Long.class) != null) {
        deleteHide = "";
        cancelUrl = request.getContextPath() + "/open.knowledge/view/" + jspUtil.out("knowledgeId") + jspUtil.out("params");
    }
    if (jspUtil.getValue("draftId", Long.class) != null) {
        draftDeleteHide = "";
    }
%>
<div class="article_buttons">
    <div class="checkbox">
        <label>
            <input type="checkbox" name="notUpdateTimeline" value="true">
            <%= jspUtil.label("knowledge.edit.do.not.update.timeline") %>
        </label>
    </div>
    <div>
        <button type="button" class="btn btn-primary btn_1" id="releasebutton">
            <i class="fa fa-rocket"></i>&nbsp;<%= jspUtil.label("label.release") %>
        </button>
    </div>
    <div>
        <button type="submit" class="btn btn-info btn_2" id="draftbutton">
            <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save.draft") %>
        </button>
        <button type="button" class="btn btn-info btn_2 text-warning <%= draftDeleteHide %>" onclick="" id="draftDeleteButton">
            <i class="fa fa-eraser"></i>&nbsp; <%= jspUtil.label("label.delete.draft") %>
        </button>
    </div>
    <div>
        <a href="<%= cancelUrl %>"
            class="btn btn-info btn_2" role="button" id="cancelButton">
            <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.cancel") %>
        </a>
        <button type="button" class="btn btn-info btn_2 text-warning <%= deleteHide %>" onclick="deleteKnowledge();" id="deleteButton">
            <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
        </button>
    </div>
    
</div>

