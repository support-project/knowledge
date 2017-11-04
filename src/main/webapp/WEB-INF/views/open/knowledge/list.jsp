<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.common.util.HtmlUtils"%>
<%@page import="org.support.project.web.config.AppConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.control.NoticesControl"%>
<%@page import="org.support.project.web.logic.SanitizingLogic"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.knowledge.entity.TemplateMastersEntity"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
        <jsp:include page="partials/partials-list-styles.jsp"></jsp:include>
    </c:param>

    <c:param name="PARAM_SCRIPTS">
        <jsp:include page="partials/partials-list-scripts.jsp"></jsp:include>
        
        <%
            if (jspUtil.logined()) {
        %>
        <%-- ログインしているユーザは、常に通知を取得する（既読はユーザ毎に管理） --%>
        <script type="text/javascript" src="<%=jspUtil.mustReloadFile("/js/mynotice.js")%>"></script>
        <%
            } else {
                HttpSession session = request.getSession();
                Boolean read = (Boolean) session.getAttribute(NoticesControl.READ_NOTICES);
                if (read == null || !read) {
        %>
        <%-- ログインしていないユーザは全て表示されてしまうので、セッション単位に1回表示する --%>
        <script type="text/javascript" src="<%=jspUtil.mustReloadFile("/js/mynotice.js")%>"></script>
        <%      }
            }
        %>
    </c:param>

    <c:param name="PARAM_CONTENT">

        <!-- Title -->
        <div class="row">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/open.knowledge/list"><%=jspUtil.label("knowledge.list.kind.list")%></a></li>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_popularity"><%=jspUtil.label("knowledge.list.kind.popular")%></a></li>
                <% if (jspUtil.logined()) { %>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/stocks"><%= jspUtil.label("knowledge.list.kind.stock") %></a></li>
                <% } %>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_history"><%=jspUtil.label("knowledge.list.kind.history")%></a></li>
            </ul>
        </div>

        <!-- Filter -->
        <c:if
            test="${!empty selectedTag || !empty selectedGroup || !empty selectedUser || !empty selectedTags || !empty selectedGroups || !empty keyword || !empty types || !empty creators }">
            <div class="row">
                <div class="col-sm-12 selected_tag">

                    <c:if test="${!empty types}">
                        <c:forEach var="type" items="${types}" varStatus="status">
                        <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list?template=<%=jspUtil.out("type.typeId")%>">
                            <i class="fa <%= jspUtil.out("type.typeIcon") %>"></i>&nbsp;
                            <%=jspUtil.out("type.typeName")%>
                        </a>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${!empty selectedTag}">
                        <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list?tag=<%=jspUtil.out("selectedTag.tagId")%>">
                            <i class="fa fa-tag"></i>&nbsp;<%=jspUtil.out("selectedTag.tagName")%>
                        </a>
                    </c:if>

                    <c:if test="${!empty selectedGroup}">
                        <a class="text-link"
                            href="<%=request.getContextPath()%>/open.knowledge/list?group=<%=jspUtil.out("selectedGroup.groupId")%>"> <i
                            class="fa fa-group"></i>&nbsp;<%=jspUtil.out("selectedGroup.groupName")%>
                        </a>
                    </c:if>

                    <c:if test="${!empty selectedUser}">
                        <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list?user=<%=jspUtil.out("selectedUser.userId")%>">
                            <i class="fa fa-user"></i>&nbsp;<%=jspUtil.out("selectedUser.userName")%>
                        </a>
                    </c:if>

                    <c:if test="${!empty selectedTags}">
                        <c:forEach var="tag" items="${selectedTags}" varStatus="status">
                            <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list?tag=<%=jspUtil.out("tag.tagId")%>"> <i
                                class="fa fa-tag"></i>&nbsp;<%=jspUtil.out("tag.tagName")%>
                            </a>
                        </c:forEach>
                    </c:if>
                    
                    <c:if test="${!empty creators}">
                        <c:forEach var="creator" items="${creators}" varStatus="status">
                            <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list?creators=<%=jspUtil.out("creator.userName")%>">
                                <i class="fa fa-pencil"></i>&nbsp;<%=jspUtil.out("creator.userName")%>
                            </a>
                        </c:forEach>
                    </c:if>
                    

                    <c:if test="${!empty selectedGroups}">
                        <c:forEach var="group" items="${selectedGroups}" varStatus="status">
                            <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list?group=<%=jspUtil.out("group.groupId")%>">
                                <i class="fa fa-group"></i>&nbsp;<%=jspUtil.out("group.groupName")%>
                            </a>
                        </c:forEach>
                    </c:if>

                    <c:if test="${!empty keyword}">
                        <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list?keyword=<%=jspUtil.out("keyword")%>"> <i
                            class="fa fa-search"></i>&nbsp;<%=jspUtil.out("keyword")%>
                        </a>
                    </c:if>

                    <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/list"> <i class="fa fa-times-circle"></i>&nbsp;
                    </a>
                    <c:if test="${!empty keyword}">
                        <div class="btn-group pull-right">
                            <button type="button" class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <%=jspUtil.label("knowledge.list.sort") %>:
                                <span id="current-keyword-sort" data-key="<%=AppConfig.get().getSystemName() %>_<%= SystemConfig.COOKIE_KEY_KEYWORD_SORT_TYPE%>">
                                    <%
                                    if (String.valueOf(KnowledgeLogic.KEYWORD_SORT_TYPE_SCORE).equals(jspUtil.out("keywordSortType"))) {
                                    %>
                                    <%=jspUtil.label("knowledge.list.sort.score") %>
                                    <%
                                    } else if (String.valueOf(KnowledgeLogic.KEYWORD_SORT_TYPE_TIME).equals(jspUtil.out("keywordSortType"))) {
                                    %>
                                    <%=jspUtil.label("knowledge.list.sort.time") %>
                                    <%
                                    }
                                    %>
                                </span>
                                &nbsp;
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li>
                                    <a class="dropdown-keyword-sort" href="javascript:void(0);" data-value="<%=KnowledgeLogic.KEYWORD_SORT_TYPE_SCORE%>"><%=jspUtil.label("knowledge.list.sort.score") %></a>
                                </li>
                                <li>
                                    <a class="dropdown-keyword-sort" href="javascript:void(0);" data-value="<%=KnowledgeLogic.KEYWORD_SORT_TYPE_TIME%>"><%=jspUtil.label("knowledge.list.sort.time") %></a>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-12 text-right">
                    <small class="text-info"><%= jspUtil.label("knowledge.list.msg.advanced.serch") %></small>
                    <a href="<%= request.getContextPath() %>/open.knowledge/search" >
                        &gt;&gt;
                        <i class="fa fa-search-plus"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.search") %>
                    </a>
                </div>
            </div>
        </c:if>
        
        <c:if test="${empty types}">
        <div class="row">
            <div class="col-sm-12">
                <form role="form" action="<%=request.getContextPath()%>/open.knowledge/list">
                <input type="hidden" name="from" value="quickFilter" />
                <a href="#quickFilter" data-toggle="collapse">
                <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                <i class="fa fa-filter" aria-hidden="true"></i>Filter</a>
                <div id="quickFilter" class="collapse">
                <%
                    List<TemplateMastersEntity> selectedFilter = (List) request.getAttribute("selectedTemplates");
                    List<Integer> selectedKeys = new ArrayList<>();
                    if (selectedFilter != null) {
                        for (TemplateMastersEntity item : selectedFilter) {
                            selectedKeys.add(item.getTypeId());
                        }
                    }
                    Map<Integer, TemplateMastersEntity> templates = (Map) request.getAttribute("templates");
                    Iterator<Integer> iterator = templates.keySet().iterator();
                    while (iterator.hasNext()) {
                        Integer key = iterator.next();
                        TemplateMastersEntity template = templates.get(key);
                        String checked = "";
                        if (selectedKeys.contains(template.getTypeId())) checked = "checked";
                %>
                    <label><input type="checkbox" name="template" value="<%= template.getTypeId() %>" <%= checked %> />
                    <i class="fa <%= HtmlUtils.escapeHTML(template.getTypeIcon()) %>"></i>
                    <%= HtmlUtils.escapeHTML(template.getTypeName()) %>&nbsp;
                    </label>
                <%
                    }
                %>
                <button class="btn btn-primary btn-xs" type="submit">
                    <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.apply") %>
                </button>
                </div>
                </form>
            </div>
        </div>
        </c:if>

        <!-- リスト -->
        <div class="row" id="knowledgeList">
            <%
                request.setAttribute("list_data", jspUtil.getValue("knowledges", List.class));
            %>
            <c:import url="/WEB-INF/views/open/knowledge/partials/common_list.jsp" />
            <c:import url="/WEB-INF/views/open/knowledge/partials/common_sub_list.jsp" />
        </div>

        <!-- Pager -->
        <nav>
            <ul class="pager">
                <li class="previous"><a
                    href="<%=request.getContextPath()%>/open.knowledge/list/<%=jspUtil.out("previous")%><%=jspUtil.out("params")%>"> <span
                        aria-hidden="true">&larr;</span><%=jspUtil.label("label.previous")%>
                </a></li>
                <li class="next"><a
                    href="<%=request.getContextPath()%>/open.knowledge/list/<%=jspUtil.out("next")%><%=jspUtil.out("params")%>"> <%=jspUtil.label("label.next")%>
                        <span aria-hidden="true">&rarr;</span>
                </a></li>
            </ul>
        </nav>

        <c:import url="/WEB-INF/views/commons/notice/notice.jsp" />

    </c:param>

</c:import>

