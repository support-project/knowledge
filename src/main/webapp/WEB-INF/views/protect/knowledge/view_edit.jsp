<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<jsp:include page="partials/partials-edit-styles.jsp"></jsp:include>
</c:param>

<c:param name="PARAM_SCRIPTS">
<jsp:include page="partials/partials-edit-scripts.jsp"></jsp:include>
<script>
<c:forEach var="group" items="${groups}" varStatus="status">
selectedGroups.push({label: '<%= jspUtil.out("group.label") %>', value: '<%= jspUtil.out("group.value") %>'});
</c:forEach>
<c:forEach var="editor" items="${editors}" varStatus="status">
selectedEditors.push({label: '<%= jspUtil.out("editor.label") %>', value: '<%= jspUtil.out("editor.value") %>'});
</c:forEach>
</script>
</c:param>

<c:param name="PARAM_PAGE_TITLE">
<%= jspUtil.label("label.update") %> - <%= jspUtil.out("title", JspUtil.ESCAPE_CLEAR) %> - Knowledge
</c:param>

<c:param name="PARAM_CONTENT">
<form action="<%= request.getContextPath()%>/protect.knowledge/update" method="post" role="form" id="knowledgeForm" enctype="multipart/form-data">
    <div class="form-inline">
        <div class="form-group title" id="title_msg"><%= jspUtil.label("knowledge.edit.title") %></div>

        <!-- info -->
        <div class="form-group pull-right">
            <label for="input_no"><%= jspUtil.label("knowledge.edit.label.key") %></label>
            <p class="form-control-static"><i class="fa fa-key"></i>&nbsp;<%= jspUtil.out("knowledgeId") %> / <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%></p>
        </div>
    </div>
    <input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeId" />

    <!-- Editor Main -->
    <jsp:include page="partials/partials-edit-editormain.jsp"></jsp:include>
    
    
    <!-- upload files -->
    <jsp:include page="partials/partials-edit-attach.jsp"></jsp:include>


    <!-- editors -->
    <div class="form-group" id="editor_area">
        <label for="input_groups"><%= jspUtil.label("knowledge.add.label.editors") %></label>
        <a id="groupselect" class="btn btn-primary btn-xs" data-toggle="modal" href="#editorSelectModal">
            <i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.editors.select") %>
        </a>
        <p>
            <input type="hidden" name="editors" id="editors" value="">
            <span id="editorsLabel"></span>
        </p>
    </div>
    
    <!-- buttons -->
    <hr/>
    <button type="submit" class="btn btn-primary" id="savebutton"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
    <button type="button" class="btn btn-danger" onclick="deleteKnowledge();" id="deleteButton">
        <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
    </button>
    <a href="<%= request.getContextPath() %>/open.knowledge/view/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %>"
        class="btn btn-warning" role="button" id="cancelButton">
        <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.cancel") %>
    </a>
    
    <a href="<%= request.getContextPath() %>/open.knowledge/list/<%= jspUtil.out("offset") %><%= jspUtil.out("params") %>"
        class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %>
    </a>
</form>

<%-- Editors --%>
<jsp:include page="partials/partials-edit-editor-modal.jsp"></jsp:include>

<%-- Viewre --%>
<jsp:include page="partials/partials-edit-viewer-modal.jsp"></jsp:include>


<jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>
<jsp:include page="markdown.jsp"></jsp:include>
<jsp:include page="../../open/tag/dialog.jsp"></jsp:include>


<form action="<%= request.getContextPath()%>/protect.knowledge/delete" method="post" role="form" id="knowledgeDeleteForm">
    <input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeIdForDelete" />
</form>

</c:param>

</c:import>

