<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.control.NoticesControl"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
        <link rel="stylesheet" href="<%=jspUtil.mustReloadFile("/css/knowledge-list.css")%>" />
    </c:param>

    <c:param name="PARAM_SCRIPTS">
        <script type="text/x-mathjax-config">
        MathJax.Hub.Config({
            tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]},
            skipStartupTypeset: true
        });
        </script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML,Safe"></script>
        
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/moment/min/moment.min.js"></script>
        
        <script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/slide.js") %>"></script>
        <script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-common.js") %>"></script>
        <script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-list.js")%>"></script>
        
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
            test="${!empty selectedTag || !empty selectedGroup || !empty selectedUser || !empty selectedTags || !empty selectedGroups || !empty keyword}">
            <div class="row">
                <div class="col-sm-12 selected_tag">

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

