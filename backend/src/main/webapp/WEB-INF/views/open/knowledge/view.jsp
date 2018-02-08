<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<jsp:include page="partials/partials-view-styles.jsp"></jsp:include>
</c:param>
<c:param name="PARAM_SCRIPTS">
<jsp:include page="partials/partials-view-scripts.jsp"></jsp:include>
</c:param>

<c:param name="PARAM_PAGE_TITLE">
<%= jspUtil.out("title", JspUtil.ESCAPE_CLEAR) %> - Knowledge
</c:param>


<c:param name="PARAM_CONTENT">
    <div class="row" id="content_head">
        <%-- 左上のヘッダー部分 --%>
        <div class="col-sm-8">
            <h4 class="title">
            <span class="dispKnowledgeId">
                #<%= jspUtil.out("knowledgeId") %>
            </span>
            <%=jspUtil.out("title", JspUtil.ESCAPE_CLEAR)%>
            </h4>

            <div class="meta-info">
                <%-- いいね、コメント、参加者 --%>
                <jsp:include page="partials/partials-view-count.jsp"></jsp:include>
            </div>
            <div class="meta-info">
                <%-- テンプレートの種類表示 --%>
                <jsp:include page="partials/partials-view-template.jsp"></jsp:include>
                <%-- タグ --%>
                <jsp:include page="partials/partials-view-tag.jsp"></jsp:include>
                <%-- ストックに入れているか --%>
                <jsp:include page="partials/partials-view-stock.jsp"></jsp:include>
                <%-- 公開区分 --%>
                <jsp:include page="partials/partials-view-public-flag.jsp"></jsp:include>
            </div>
            <div class="meta-info">
                <%-- 更新者情報 --%>
                <jsp:include page="partials/partials-view-editor.jsp"></jsp:include>
            </div>
        </div>

        <%-- 右上のボタン部分 --%>
        <div class="col-sm-4">
            <%-- ボタン --%>
            <jsp:include page="partials/partials-view-menu-buttons.jsp"></jsp:include>
            <%-- 目次 --%>
            <jsp:include page="partials/partials-view-toc.jsp"></jsp:include>
        </div>
    </div>

    <%-- ナレッジ表示（メインのコンテンツ部分） --%>
    <div class="row" id="content_main">
        <div class="col-sm-12" >
            <%-- メインのコンテンツ --%>
            <jsp:include page="partials/partials-view-main-contents.jsp"></jsp:include>
        </div>
    </div>
     
    <%-- 添付ファイル --%>
    <div class="row">
        <div class="col-sm-12" >
            <%-- 添付ファイル --%>
            <jsp:include page="partials/partials-view-attach-files.jsp"></jsp:include>
        </div>
    </div>
     
     
    <%-- コメント表示 --%>
    <jsp:include page="partials/partials-view-comment-list.jsp"></jsp:include>

    <%-- コメント登録 --%>
    <hr />
    <jsp:include page="partials/partials-view-comment-edit.jsp"></jsp:include>

    <%-- Stock Modal --%>
    <jsp:include page="partials/partials-view-modal-stock.jsp"></jsp:include>
    
    <%-- Survey --%>
    <jsp:include page="partials/partials-view-modal-answer-survey.jsp"></jsp:include>

    <%-- Emoji --%>
    <jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>


</c:param>

</c:import>

