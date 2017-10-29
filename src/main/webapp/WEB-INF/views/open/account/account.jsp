<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="java.util.List"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-open-account.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-list.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-open-account.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/moment/min/moment.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/moment/locale/ja.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/chart.js/dist/Chart.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/vue/dist/vue.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/knowledge-list.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/account-page.js"></script>
<!-- endbuild -->
</c:param>


<c:param name="PARAM_CONTENT">
<input type="hidden" id="userId" value="<%= jspUtil.out("userId") %>" />
<input type="hidden" id="point" value="<%= jspUtil.out("point") %>" />
<div class="row">
    <div class="col-sm-6 col-md-6">
        <h4 class="title">
        <img id="icon" src="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("userId") %>"
        width="64" height="64" />&nbsp;
        <%= jspUtil.out("userName") %>
        </h4>
        
        <div class="row">
            <div class="col-xs-6">
            <i class="fa fa-heart-o" ></i>&nbsp;<%= jspUtil.label("knowledge.account.label.cp") %>
            </div>
            <div class="col-xs-6">
            <i class="fa fa-times"></i>&nbsp;<%= jspUtil.out("point") %>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-6">
            <i class="fa fa-book"></i>&nbsp;<%= jspUtil.label("knowledge.account.label.knowledge.count") %>
            </div>
            <div class="col-xs-6">
            <i class="fa fa-times"></i>&nbsp;<%= jspUtil.out("knowledgeCount") %>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-6">
            <i class="fa fa-thumbs-o-up"></i>&nbsp;<%= jspUtil.label("knowledge.account.label.like.count") %>
            </div>
            <div class="col-xs-6">
            <i class="fa fa-times"></i>&nbsp;<%= jspUtil.out("likeCount") %>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-6">
            <i class="fa fa-star-o"></i>&nbsp;<%= jspUtil.label("knowledge.account.label.stock.count") %>
            </div>
            <div class="col-xs-6">
            <i class="fa fa-times"></i>&nbsp;<%= jspUtil.out("stockCount") %>
            </div>
        </div>
        
    </div>
    <div class="col-sm-6 col-md-6">
        <canvas id="cpChart"></canvas>
    </div>
</div>

<div class="row" id="tabArea">
    <ul class="nav nav-tabs">
        <li role="presentation" class="active" id="tabKnowledge"><a v-on:click="showKnowledge">
            <%= jspUtil.label("knowledge.account.label.knowledges") %>
        </a></li>
        <%--
        <li role="presentation" id="tabLike"><a v-on:click="showLike">
            <%=jspUtil.label("knowledge.account.label.like")%>
        </a></li>
        --%>
        <li role="presentation" id="tabActivity"><a v-on:click="showActivity">
            <%=jspUtil.label("knowledge.account.label.activity")%>
        </a></li>
    </ul>
</div>


<div id="knowledgesArea">
    <div class="sub_title">
    </div>
    <!-- リスト -->
    <div class="row" id="knowledgeList">
        <% request.setAttribute("list_data", jspUtil.getValue("knowledges", List.class)); %>
        <c:import url="/WEB-INF/views/open/knowledge/partials/common_list.jsp" />
    </div>
    <!-- Pager -->
    <nav>
        <ul class="pager">
            <li class="previous">
                <a href="<%= request.getContextPath() %>/open.account/info/<%= jspUtil.out("userId") %>?offset=<%= jspUtil.out("previous") %>">
                    <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                </a>
            </li>
            <li class="next">
                <a href="<%= request.getContextPath() %>/open.account/info/<%= jspUtil.out("userId") %>?offset=<%= jspUtil.out("next") %>">
                    <%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>


<div id="activityArea" style="display:none">
    <br/><br/>
    <div class="list-group" id="activityList">
        <a class="list-group-item" v-for="activity in activities">
            <h4 class="list-group-item-heading" v-html="activity.msg"></h4>
            <p class="list-group-item-text">{{ activity.dispDate }}</p>
        </a>
    </div>
</div>

</c:param>

</c:import>

