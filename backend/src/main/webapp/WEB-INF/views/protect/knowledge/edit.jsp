<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="java.util.List"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.common.util.HtmlUtils"%>
<%@page import="org.support.project.web.logic.SanitizingLogic"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.bean.LabelValue"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>

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
<%
List<LabelValue> viewers = (List<LabelValue>) request.getAttribute("groups");
if (viewers != null) {
    for (LabelValue lv : viewers) {
        String groupName = HtmlUtils.escapeHTML(lv.getLabel());
        String groupId = HtmlUtils.escapeHTML(lv.getValue());
%>
selectedGroups.push({label: '<%= groupName %>', value: '<%= groupId %>'});
<%
    }
}
%>
<c:forEach var="editor" items="${editors}" varStatus="status">
selectedEditors.push({label: '<%= jspUtil.out("editor.label") %>', value: '<%= jspUtil.out("editor.value") %>'});
</c:forEach>
</script>
</c:param>

<c:param name="PARAM_PAGE_TITLE">
<% if (!StringUtils.isEmpty(jspUtil.getValue("title", String.class))) { %>
<%= jspUtil.label("label.update") %> - <%= jspUtil.out("title", JspUtil.ESCAPE_CLEAR) %> - Knowledge
<% } else { %>
<%= jspUtil.label("label.add") %> - Knowledge
<% } %>
</c:param>

<c:param name="PARAM_CONTENT">
<form action="<%= request.getContextPath()%>/protect.knowledge/save" method="post" role="form" id="knowledgeForm" enctype="multipart/form-data">
    <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
        value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
    <input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeId" />
    <input type="hidden" name="draftId" value="<%= jspUtil.out("draftId") %>" id="draftId" />
    <input type="hidden" name="updateContent" value="" id="updateContent" />
    <%-- ヘッダ部（ボタンなど） --%>
    <div class="row" id="content_head">
        <div class="col-sm-8">
            <%-- ページ上部のインフォメーション表示 --%>
            <jsp:include page="partials/partials-edit-topinfo.jsp"></jsp:include>
        </div>
        <%-- 右上のボタン部分 --%>
        <div class="col-sm-4">
            <%-- 操作ボタン --%>
            <jsp:include page="partials/partials-edit-buttons.jsp"></jsp:include>
        </div>
    </div>
    
    <%-- ナレッジ編集（メインのコンテンツ部分） --%>
    <div class="row" id="content_main">
        <div class="col-sm-8">
            <%-- 編集 --%>
            <jsp:include page="partials/partials-edit-editormain.jsp"></jsp:include>
        </div>
        <div class="col-sm-4">
            <%-- 属性情報 --%>
            <jsp:include page="partials/partials-edit-meta.jsp"></jsp:include>
        </div>
    </div>
    
</form>

<%-- Editors select modal --%>
<jsp:include page="partials/partials-edit-editor-modal.jsp"></jsp:include>
<%-- Viewer select modal --%>
<jsp:include page="partials/partials-edit-viewer-modal.jsp"></jsp:include>

<jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>
<jsp:include page="markdown.jsp"></jsp:include>
<jsp:include page="../../open/tag/dialog.jsp"></jsp:include>

<form action="<%= request.getContextPath()%>/protect.knowledge/delete" method="post" role="form" id="knowledgeDeleteForm">
    <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
        value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
    <input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeIdForDelete" />
</form>

</c:param>

</c:import>

