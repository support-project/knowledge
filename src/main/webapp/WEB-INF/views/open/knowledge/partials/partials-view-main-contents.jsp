<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<input type="hidden" id="knowledgeId" value="<%=jspUtil.out("knowledgeId")%>" />
<input type="hidden" id="typeId" value="<%=jspUtil.out("typeId")%>" />
<%-- テンプレートの項目 --%>
<div style="word-break: normal; display: none;" id="template_items_area" class="markdown">
    <span id="template_view_items"></span>
</div>
<%-- プレゼンテーション --%>
<div id="presentationArea" class="slideshow"></div>
<%-- ナレッジコンテンツ --%>
<div style="word-break: normal;" id="content" class="markdown viewarea">
    <%=jspUtil.out("content", JspUtil.ESCAPE_NONE)%>
</div>
