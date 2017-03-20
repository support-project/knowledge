<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.entity.StocksEntity"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
        <jsp:include page="partials/partials-list-styles.jsp"></jsp:include>
    </c:param>

    <c:param name="PARAM_SCRIPTS">
        <jsp:include page="partials/partials-list-scripts.jsp"></jsp:include>
    </c:param>

    <c:param name="PARAM_CONTENT">

        <!-- Title -->
        <div class="row">
            <ul class="nav nav-tabs">
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/list"><%=jspUtil.label("knowledge.list.kind.list")%></a></li>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_popularity"><%=jspUtil.label("knowledge.list.kind.popular")%></a></li>
                <% if (jspUtil.logined()) { %>
                <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/open.knowledge/stocks"><%= jspUtil.label("knowledge.list.kind.stock") %></a></li>
                <% } %>
                <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_history"><%=jspUtil.label("knowledge.list.kind.history")%></a></li>
            </ul>
        </div>

        <div class="row">
            <div class="col-sm-12 selected_tag">
                <c:if test="${!empty stock}">
                    <a class="text-link" href="<%=request.getContextPath()%>/open.knowledge/stocks?stockid=<%=jspUtil.out("stock.stockId")%>">
                        <i class="fa fa-star-o"></i>&nbsp;<%=jspUtil.out("stock.stockName")%>
                    </a>
                </c:if>
            </div>
       </div>
            
        <div class="row">
            <div class="col-sm-12 text-right">
                <a href="<%=request.getContextPath()%>/protect.stock/mylist">
                    &gt;&gt;
                    <%=jspUtil.label("knowledge.list.link.stock")%>
                </a>
            </div>
        </div>

        <!-- リスト -->
        <div class="row" id="knowledgeList">
            <% request.setAttribute("list_data", jspUtil.getValue("popularities", List.class)); %>
            <c:import url="/WEB-INF/views/open/knowledge/partials/common_list.jsp" />
            <c:import url="/WEB-INF/views/open/knowledge/partials/common_sub_list.jsp" />
        </div>

        <!-- Pager -->
        <nav>
            <% 
                String selectStock = "";
                StocksEntity stock = jspUtil.getValue("stock", StocksEntity.class);
                if (stock != null) {
                    selectStock = "?stockid=" + stock.getStockId();
                }
            %>
            <ul class="pager">
                <li class="previous"><a
                    href="<%=request.getContextPath()%>/open.knowledge/stocks/<%=jspUtil.out("previous")%><%= selectStock %>">
                        <span aria-hidden="true">&larr;</span><%=jspUtil.label("label.previous")%>
                </a></li>
                <li class="next"><a
                    href="<%=request.getContextPath()%>/open.knowledge/stocks/<%=jspUtil.out("next")%><%= selectStock %>">
                        <%=jspUtil.label("label.next")%> <span aria-hidden="true">&rarr;</span>
                </a></li>
            </ul>
        </nav>

    </c:param>

</c:import>



