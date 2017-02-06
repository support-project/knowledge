<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>


<!-- List -->
<div class="col-sm-12 col-md-8 knowledge_list">

    <c:if test="${empty list_data}">
        <%=jspUtil.label("knowledge.list.empty")%>
    </c:if>

    <c:forEach var="knowledge" items="${list_data}" varStatus="status">
        <c:if test="${status.index == 1}">
            <div class="ad_item">

<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 320*100 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:320px;height:100px"
     data-ad-client="ca-pub-6818655992018426"
     data-ad-slot="6826060792"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>

            </div>
        </c:if>
        <c:if test="${status.index == 21}">
            <div class="ad_item">
            
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 320*100 -2 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:320px;height:100px"
     data-ad-client="ca-pub-6818655992018426"
     data-ad-slot="9081523196"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
            
            </div>
        </c:if>
        
    
        <div class="knowledge_item">
            <a href="<%=request.getContextPath()%>/open.knowledge/view/<%=jspUtil.out("knowledge.knowledgeId")%><%=jspUtil.out("params")%>">
                <div class="insert_info">
                    <h4>
                        <span class="dispKnowledgeId">
                            #<%= jspUtil.out("knowledge.knowledgeId") %>
                        </span>
                        <%=jspUtil.out("knowledge.title", JspUtil.ESCAPE_CLEAR)%>
                    </h4>
                    <div>
                        <img src="<%=request.getContextPath()%>/images/loader.gif"
                            data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("knowledge.insertUser")%>" alt="icon" width="36"
                            height="36" />
                        <%=jspUtil.label("knowledge.view.info.insert", jspUtil.out("knowledge.insertUserName"),
                        jspUtil.date("knowledge.insertDatetime"))%>
                        <% if (!jspUtil.date("knowledge.insertDatetime").equals(jspUtil.date("knowledge.updateDatetime"))) { %>
                        (
                        <img src="<%=request.getContextPath()%>/images/loader.gif"
                            data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("knowledge.updateUser")%>" alt="icon" width="36"
                            height="36" />
                        <%=jspUtil.label("knowledge.view.info.update", jspUtil.out("knowledge.updateUserName"),
                        jspUtil.date("knowledge.updateDatetime"))%>
                        )
                        <% } %>
                    </div>
                </div>
            </a>
            <div class="item-info">
                <a class="btn-link"
                    href="<%=request.getContextPath()%>/open.knowledge/likes/<%=jspUtil.out("knowledge.knowledgeId")%><%=jspUtil.out("params")%>">
                    <i class="fa fa-thumbs-o-up" style="margin-left: 5px;"></i>&nbsp;× <span id="like_count"><%=jspUtil.out("knowledge.likeCount")%></span>
                </a> &nbsp;&nbsp;&nbsp; <a class="btn-link"
                    href="<%=request.getContextPath()%>/open.knowledge/view/<%=jspUtil.out("knowledge.knowledgeId")%><%=jspUtil.out("params")%>#comments">
                    <i class="fa fa-comments-o"></i>&nbsp;× <%=jspUtil.out("knowledge.commentCount")%>
                </a> &nbsp;&nbsp;&nbsp;
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
                    <c:forEach var="tagName" items="${knowledge.tagNames.split(',')}" varStatus="status2">
                        <a href="<%= request.getContextPath()%>/open.knowledge/list?tag=<c:out value="${tagIds[status.index]}"/>">
                            <span class="tag label label-info"><i class="fa fa-tag"></i><%=jspUtil.out("tagName")%></span>
                        </a>&nbsp;
                    </c:forEach>
                </c:if>
                &nbsp;&nbsp;&nbsp;
                <c:if test="${!empty knowledge.stocks}">
                    <i class="fa fa-star-o"></i>
                    <c:forEach var="stock" items="${knowledge.stocks}" varStatus="status3">
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


