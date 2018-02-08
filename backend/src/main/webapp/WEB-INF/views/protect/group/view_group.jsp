<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.support.project.web.config.CommonWebParameter"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-protect-group.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/EasyWizard/easyWizard.css" />
<!-- endbuild -->
</c:param>
<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-protect-group.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/EasyWizard/easyWizard.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/group.js"></script>
<!-- endbuild -->
<script>
var _CONFIRM_DELETE = '<%= jspUtil.label("knowledge.group.view.label.confirm.delete") %>';
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.group.view.title") %></h4>

<form action="<%= request.getContextPath()%>/protect.group/view" method="post" role="form" id ="groupForm">

    <div class="form-group">
        <label for="input_groupName"><%= jspUtil.label("knowledge.group.view.label.groupname") %></label>
        <br/>
        <%= jspUtil.out("groupName") %>
    </div>
    <div class="form-group">
        <label for="input_description"><%= jspUtil.label("knowledge.group.view.label.description") %></label>
        <br/>
        <%= jspUtil.out("description") %>
    </div>

    <div class="form-group">
        <label for="input_content"><%= jspUtil.label("knowledge.group.view.label.public.class") %></label>
        <br/>
        <%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PUBLIC, "groupClass", jspUtil.label("knowledge.group.view.label.public")) %>
        <%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PROTECT, "groupClass", jspUtil.label("knowledge.group.view.label.protect")) %>
        <%= jspUtil.is(CommonWebParameter.GROUP_CLASS_PRIVATE, "groupClass", jspUtil.label("knowledge.group.view.label.private")) %>
    </div>

    <c:if test="${ editAble }">
    <div class="form-group">
        <a href="<%= request.getContextPath() %>/protect.group/view_edit/<%= jspUtil.out("groupId") %>" 
            class="btn btn-primary" role="button"><i class="fa fa-edit"></i>&nbsp;<%= jspUtil.label("label.edit") %>
        </a>
        <button type="button" class="btn btn-danger" onclick="deleteGroup();">
            <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
        </button>
    </div>
    </c:if>
    <input type="hidden" name="groupId" value="<%= jspUtil.out("groupId") %>" id="groupId">
    <input type="hidden" name="groupKey" value="<%= jspUtil.out("groupKey") %>">
</form>

<h4 class="title">
    <%= jspUtil.label("knowledge.group.view.label.member") %>
    <span style="font-size: -1">page[<%= jspUtil.getValue("offset", Integer.class) + 1 %>]</span>
</h4>

<p>
<% if(jspUtil.is(CommonWebParameter.GROUP_CLASS_PROTECT, "groupClass")) { %>
    <%-- 非公開の場合(このページにアクセスしているので、少なくともアクセス権は持っている) --%>
<% } %>

<c:if test="${ belong == false }">
<%-- このグループに所属していない --%>
<% if(jspUtil.is(CommonWebParameter.GROUP_CLASS_PUBLIC, "groupClass")) { %>
    <%-- 公開の場合(自分でユーザを登録できる) --%>
    <a href="<%= request.getContextPath() %>/protect.group/subscribe/<%= jspUtil.out("groupId") %>" 
        class="btn btn-info" role="button">
        <i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.subscribe.public") %>
    </a>
<% } %>

<% if(jspUtil.is(CommonWebParameter.GROUP_CLASS_PROTECT, "groupClass")) { %>
    <%-- 保護の場合(追加のリクエストは登録できる/管理者はそれを承認できる) --%>
    <a href="<%= request.getContextPath() %>/protect.group/request/<%= jspUtil.out("groupId") %>" 
        class="btn btn-info" role="button">
        <i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.subscribe.protect") %>
    </a>
<% } %>
</c:if>

<c:if test="${ belong }">
<% if(!jspUtil.is(CommonWebParameter.GROUP_CLASS_PRIVATE, "groupClass")) { %>
    <a href="<%= request.getContextPath() %>/protect.group/unsubscribe/<%= jspUtil.out("groupId") %>" 
        class="btn btn-danger" role="button">
        <i class="fa fa-sign-out"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.unsubscribe") %>
    </a>
<% } %>
</c:if>

<% if(jspUtil.is(CommonWebParameter.GROUP_CLASS_PRIVATE, "groupClass") && jspUtil.is(Boolean.TRUE, "editAble")) { %>
<%-- 非公開グループのメンバー選択 --%>
    <a id="groupselect" class="btn btn-info" data-toggle="modal" href="#groupSelectModal">
        <i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.group.label.add.user") %>
    </a>
    <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#wizardModal">
        <i class="fa fa-list-alt" aria-hidden="true"></i> <%= jspUtil.label("knowledge.group.add.members") %>
    </button>
<% } %>
<p>


<div class="list-group">
<nav>
    <ul class="pager">
        <li class="previous">
            <a href="<%= request.getContextPath() %>/protect.group/view/<%= jspUtil.out("groupId") %>?offset=<%= jspUtil.out("previous") %>">
                <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
            </a>
        </li>
        <li class="next">
            <a href="<%= request.getContextPath() %>/protect.group/view/<%= jspUtil.out("groupId") %>?offset=<%= jspUtil.out("next") %>">
                <%= jspUtil.label("label.next") %><span aria-hidden="true">&rarr;</span>
            </a>
        </li>
    </ul>
</nav>

<c:if test="${empty users}">
<%= jspUtil.label("knowledge.group.list.empty") %>
</c:if>

<c:forEach var="user" items="${users}" varStatus="status">
    <div class="list-group-item">
        <h4 class="list-group-item-heading">
        <%= jspUtil.out("user.userName") %>
        <span style="font-size: small;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <%= jspUtil.label("label.status") %>: 
        <%= jspUtil.is(CommonWebParameter.GROUP_ROLE_WAIT, "user.groupRole", jspUtil.label("knowledge.group.view.label.role.wait")) %>
        <%= jspUtil.is(CommonWebParameter.GROUP_ROLE_ADMIN, "user.groupRole", jspUtil.label("knowledge.group.view.label.role.admin")) %>
        <%= jspUtil.is(CommonWebParameter.GROUP_ROLE_MEMBER, "user.groupRole", jspUtil.label("knowledge.group.view.label.role.member")) %>
        </span>
        
        </h4>
        <p class="list-group-item-text">
        
        <% if (jspUtil.is(Boolean.TRUE, "editAble")) { %>
            <% if(jspUtil.is(CommonWebParameter.GROUP_ROLE_WAIT, "user.groupRole")) { %>
            <a href="<%= request.getContextPath() %>/protect.group/accept/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>" class="btn btn-primary">
                <i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.accept.label.accept") %>
            </a>
            <% } else { %>
            <a href="<%= request.getContextPath() %>/protect.group/change/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>&status=<%= CommonWebParameter.GROUP_ROLE_ADMIN %>" 
                class="btn btn-default btn-sm">
                <i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.change.admin") %>
            </a>
            <a href="<%= request.getContextPath() %>/protect.group/change/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>&status=<%= CommonWebParameter.GROUP_ROLE_MEMBER %>" 
                class="btn btn-default btn-sm">
                <i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.change.member") %>
            </a>
            <a href="<%= request.getContextPath() %>/protect.group/change/<%= jspUtil.out("groupId") %>?userId=<%= jspUtil.out("user.userId") %>&status=0" 
                class="btn btn-default btn-sm">
                <i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.change.none") %>
            </a>
            <% } %>
        <% } %>
        </p>
    </div>
</c:forEach>

<nav>
    <ul class="pager">
        <li class="previous">
            <a href="<%= request.getContextPath() %>/protect.group/view/<%= jspUtil.out("groupId") %>?offset=<%= jspUtil.out("previous") %>">
                <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
            </a>
        </li>
        <li class="next">
            <a href="<%= request.getContextPath() %>/protect.group/view/<%= jspUtil.out("groupId") %>?offset=<%= jspUtil.out("next") %>">
                <%= jspUtil.label("label.next") %><span aria-hidden="true">&rarr;</span>
            </a>
        </li>
    </ul>
</nav>

</div>

<a href="<%= request.getContextPath() %>/protect.group/mygroups"
class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.backto.mygroup") %></a>
<%--
<a href="<%= request.getContextPath() %>/protect.group/list"
class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.list") %></a>
--%>
<% if (jspUtil.is(CommonWebParameter.GROUP_ROLE_MEMBER, "status")
    || jspUtil.is(CommonWebParameter.GROUP_ROLE_ADMIN, "status")
    || jspUtil.user().isAdmin()) { %>
<a href="<%= request.getContextPath() %>/open.knowledge/list?group=<%= jspUtil.out("groupId") %>"
class="btn btn-info" role="button"><i class="fa fa-book"></i>&nbsp;<%= jspUtil.label("knowledge.group.view.label.group.knowledges") %></a>
<% } %>


<%-- Targets --%>
<div class="modal fade" id="groupSelectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                <span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <%= jspUtil.label("knowledge.group.label.add.user") %>
                    <span style="font-size: 14px;" id="groupPage"></span>
                </h4>
            </div>
            <div class="modal-body">
                <div role="form" class="form-inline">
                    <input type="text" name="keyword" class="form-control" value="<%= jspUtil.out("keyword") %>" placeholder="Keyword" id="groupKeyword">
                    <button type="button" class="btn btn-success" id="groupSearchButton">
                        <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.filter") %>
                    </button>
                    <button type="button" class="btn btn-default" id="groupSearchPrevious">
                        <i class="fa fa-arrow-circle-left"></i>&nbsp;<%= jspUtil.label("label.previous") %>
                    </button>
                    <button type="button" class="btn btn-default" id="groupSearchNext">
                        <%= jspUtil.label("label.next") %>&nbsp;<i class="fa fa-arrow-circle-right "></i>
                    </button>
                </div>
                <hr/>
                <p>
                    <%-- 選択済みの一覧 --%>
                    <span id="selectedList"></span>
                    <button type="button" class="btn btn-default" id="clearSelectedGroup">
                        <i class="fa fa-eraser"></i>&nbsp;<%= jspUtil.label("label.clear") %>&nbsp;
                    </button>
                </p>
                <hr/>
                <p id="groupList">
                    <%-- 選択するための一覧 --%>
                </p>
            </div>
            <div class="modal-footer">
                <span id="loading"></span>
                <button type="button" class="btn btn-info" id="groupAdd">
                    <i class="fa fa-users"></i>&nbsp;<%= jspUtil.label("label.add") %>
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %>
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->




    <div class="modal fade" id="wizardModal" tabindex="-1" role="dialog" aria-labelledby="wizardModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="wizardModalLabel"><%= jspUtil.label("knowledge.group.add.members") %></h4>
                </div>
                <div class="modal-body wizard-content">
                    <div class="wizard-step">
                        {Step 1} <br>
                        <%= jspUtil.label("knowledge.group.add.members.msg.list.1") %>
                        <br/>
                        <textarea class="form-control" rows="2" id="inputEmail"
                            placeholder="<%= jspUtil.label("knowledge.group.add.members.label.list") %>"></textarea>
                    </div>
                    <div class="wizard-step">
                        {Step 2} <br>
                        <%= jspUtil.label("knowledge.group.add.members.msg.list.2") %>
                        <br/>
                        Count: <span id="mailCount"></span><br/>
                        <textarea class="form-control" rows="2" id="emails"
                            placeholder="Emails"></textarea>
                    </div>
                    <div class="wizard-step">
                        {Step 3} <br>
                        <%= jspUtil.label("knowledge.group.add.members.msg.list.3") %>
                        <br/>
                        <textarea class="form-control" rows="2" id="msgResult"
                            placeholder="Result" readonly="readonly"></textarea>
                    </div>
                </div>
                <div class="modal-footer wizard-buttons">
                    <!-- The wizard button will be inserted here. -->
                </div>
            </div>
        </div>
    </div>



</c:param>

</c:import>

