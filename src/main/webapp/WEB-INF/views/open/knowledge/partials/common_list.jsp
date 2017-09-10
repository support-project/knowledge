<%@page import="org.support.project.knowledge.logic.TemplateLogic"%>
<%@page import="org.support.project.knowledge.logic.EventsLogic"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.common.util.DateUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.entity.KnowledgesEntity"%>
<%@page import="org.support.project.knowledge.entity.TemplateMastersEntity"%>
<%@page import="org.support.project.knowledge.vo.StockKnowledge"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
    Map<Integer, TemplateMastersEntity> templates = jspUtil.getValue("templates", Map.class);
    String timezone = jspUtil.out("timezone");
%>

<!-- List -->
<div class="col-sm-12 col-md-8 knowledge_list">

    <c:if test="${empty list_data}">
        <%=jspUtil.label("knowledge.list.empty")%>
    </c:if>

    <c:forEach var="knowledge" items="${list_data}" varStatus="status">
        <% KnowledgesEntity knowledge = (KnowledgesEntity) pageContext.getAttribute("knowledge");
        StockKnowledge stock = null;
        String unread = "";
        String unreadLabel = "";
        if (knowledge instanceof StockKnowledge) { 
            stock = (StockKnowledge) knowledge;
            if (!stock.isViewed()) {
                unread = "unread";
                unreadLabel = "[" + jspUtil.label("label.unread") + "]";
            }
        }
        %>
        <div class="knowledge_item <%= unread %>">
            <div class="insert_info">
                <a href="<%=request.getContextPath()%>/open.knowledge/view/<%=jspUtil.out("knowledge.knowledgeId")%><%=jspUtil.out("params")%>"
                    class="text-primary btn-link">
                    <div class="list-title">
                    <span class="dispKnowledgeId">
                        #<%= jspUtil.out("knowledge.knowledgeId") %>
                    </span>
                    <%=jspUtil.out("knowledge.title", JspUtil.ESCAPE_CLEAR)%>
                    </div>
                </a>
                <div>
                    <%= unreadLabel %>
                    <img src="<%=request.getContextPath()%>/images/loader.gif"
                        data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("knowledge.insertUser")%>" alt="icon"
                        width="20" height="20" />
                    <%
                        String insertLink = "<a href=\"" + request.getContextPath() + "/open.account/info/" + jspUtil.out("knowledge.insertUser") + "\" class=\"text-primary btn-link\">"
                                        + jspUtil.out("knowledge.insertUserName", JspUtil.ESCAPE_CLEAR) + "</a>";
                    %>
                    <%=jspUtil.label("knowledge.view.info.insert", insertLink, jspUtil.date("knowledge.insertDatetime"))%>
                    <% if (!jspUtil.date("knowledge.insertDatetime").equals(jspUtil.date("knowledge.updateDatetime"))) { %>
                        (
                        <img src="<%=request.getContextPath()%>/images/loader.gif"
                            data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("knowledge.updateUser")%>" alt="icon"
                            width="20" height="20" />
                        <%
                            String updateLink = "<a href=\"" + request.getContextPath() + "/open.account/info/" + jspUtil.out("knowledge.updateUser") + "\" class=\"text-primary btn-link\">"
                                            + jspUtil.out("knowledge.updateUserName", JspUtil.ESCAPE_CLEAR) + "</a>";
                        %>
                        <%=jspUtil.label("knowledge.view.info.update", updateLink, jspUtil.date("knowledge.updateDatetime"))%>
                        )
                    <% } %>
                </div>
                 <% if (knowledge.getStartDateTime() != null) { %>
                 <div>
                     <i class="fa fa-calendar"></i>&nbsp;
                     <%= jspUtil.label("knowledge.list.event.datetime") %>: <%= knowledge.getLocalStartDateTime(jspUtil.locale(), timezone) %>
                     <%
                         if (stock != null && stock.getParticipations() != null) {
                     %>
                     <i class="fa fa-users"></i>&nbsp;<%= stock.getParticipations().getCount() %> /  <%= stock.getParticipations().getLimit() %>
                     <% if (stock.getParticipations().getStatus() != null) { %>
                     <span class="badge">
                         <% if (stock.getParticipations().getStatus() == EventsLogic.STATUS_PARTICIPATION) { %>
                            <%= jspUtil.label("knowledge.view.label.status.participation") %>
                         <% } else { %>
                            <%= jspUtil.label("knowledge.view.label.status.wait.cansel") %>
                         <% } %>
                     </span>
                     <% } %>
                     <% } %>
                 </div>
                 <% } %>
            </div>
        
            <div class="item-info">
                <i class="fa fa-heart-o" style="margin-left: 5px;"></i>&nbsp;× <%=jspUtil.out("knowledge.point")%> &nbsp;
                <% if (knowledge.getPointOnTerm() != null && knowledge.getPointOnTerm().intValue() > 0) {  %>
                (<i class="fa fa-line-chart" aria-hidden="true"></i>&nbsp;× <%=jspUtil.out("knowledge.pointOnTerm")%>) &nbsp;
                <% } %>
                <a class="text-primary btn-link"
                    href="<%=request.getContextPath()%>/open.knowledge/likes/<%=jspUtil.out("knowledge.knowledgeId")%><%=jspUtil.out("params")%>">
                    <i class="fa fa-thumbs-o-up"></i>&nbsp;× <span id="like_count"><%=jspUtil.out("knowledge.likeCount")%></span>
                </a> &nbsp;
                <a class="text-primary btn-link"
                    href="<%=request.getContextPath()%>/open.knowledge/view/<%=jspUtil.out("knowledge.knowledgeId")%><%=jspUtil.out("params")%>#comments">
                    <i class="fa fa-comments-o"></i>&nbsp;× <%=jspUtil.out("knowledge.commentCount")%>
                </a> &nbsp;
                <% 
                TemplateMastersEntity template = templates.get(knowledge.getTypeId());
                if (template == null) {
                    template = templates.get(TemplateLogic.TYPE_ID_KNOWLEDGE);
                }
                %>
                <i class="fa <%= template.getTypeIcon() %>"></i>
                <%= template.getTypeName() %>
                &nbsp;
                <%=jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "knowledge.publicFlag", jspUtil.label("label.public.view"))%>
                <%=jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "knowledge.publicFlag", jspUtil.label("label.private.view"))%>
                <%=jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "knowledge.publicFlag", jspUtil.label("label.protect.view"))%>
                <c:if test="${targets.containsKey(knowledge.knowledgeId)}">
                    <c:forEach var="target" items="${ targets.get(knowledge.knowledgeId) }">
                        <c:choose>
                            <c:when test="${targetLogic.isGroupLabel(target.value)}">
                                <c:set var="groupId" value="${targetLogic.getGroupId(target.value)}" />
                                <a href="<%=request.getContextPath()%>/open.knowledge/list?group=<%=jspUtil.out("groupId")%>">
                                    <span class="tag label label-success">
                                    <i class="fa fa-users"></i><%=jspUtil.out("target.label")%>
                                    </span>
                                </a>&nbsp;
                            </c:when>
                            <c:when test="${targetLogic.isUserLabel(target.value)}">
                                <c:set var="userId" value="${targetLogic.getUserId(target.value)}" />
                                <a href="<%=request.getContextPath()%>/open.knowledge/list?user=<%=jspUtil.out("userId")%>">
                                    <span class="tag label label-success">
                                    <i class="fa fa-user"></i><%=jspUtil.out("target.label")%>
                                    </span>
                                </a>&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a> <span class="tag label label-success"><%=jspUtil.out("target.label")%></span>
                                </a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
                &nbsp;&nbsp;&nbsp;
                <c:if test="${!empty knowledge.tagNames}">
                    <c:set var="tagIds" value="${knowledge.tagIds.split(',')}" />
                    <i class="fa fa-tags"></i>
                    <c:forEach var="tagName" items="${knowledge.tagNames.split(',')}" varStatus="status">
                        <a href="<%= request.getContextPath()%>/open.knowledge/list?tagNames=<%=jspUtil.out("tagName", JspUtil.ESCAPE_URL)%>">
                            <span class="tag label label-info"><i class="fa fa-tag"></i><%=jspUtil.out("tagName")%></span>
                        </a>&nbsp;
                    </c:forEach>
                </c:if>
                &nbsp;&nbsp;&nbsp;
                <c:if test="${!empty knowledge.stocks}">
                    <i class="fa fa-star-o"></i>
                    <c:forEach var="stock" items="${knowledge.stocks}" varStatus="status">
                        <a href="<%=request.getContextPath()%>/open.knowledge/stocks?stockid=<%=jspUtil.out("stock.stockId")%>">
                        <span class="tag label label-primary">
                            <i class="fa fa-star"></i><%=jspUtil.out("stock.stockName")%></span>
                        </a>&nbsp;
                    </c:forEach>
                </c:if>

            </div>

            <c:if test="${!empty keyword}">
                <div class="item_caption">
                    <p style="word-break: normal" class="content">
                        <%=jspUtil.out("knowledge.content", JspUtil.ESCAPE_CLEAR, 300)%>
                    </p>
                </div>
            </c:if>
        </div>
    </c:forEach>

</div>


