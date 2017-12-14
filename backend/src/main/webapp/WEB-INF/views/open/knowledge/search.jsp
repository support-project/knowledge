<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
    errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    String top = "/open.knowledge/list";
%>
<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-knowledge-search.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-edit.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-knowledge-search.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap3-typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/tagselect.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/groupselect.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/search.js"></script>
<!-- endbuild -->

<script>
var _TAGS = [];
var tagname;
<c:forEach var="tagitem" items="${tagitems}" varStatus="status">
tagname = unescapeHTML('<%= jspUtil.out("tagitem.tagName") %>');
_TAGS.push(tagname);
</c:forEach>

var _GROUPS = [];
var groupname;
<c:forEach var="groupitem" items="${groupitems}" varStatus="status">
groupname = unescapeHTML('<%= jspUtil.out("groupitem.groupName") %>');
_GROUPS.push(groupname);
</c:forEach>
</script>

</c:param>

<c:param name="PARAM_CONTENT">

    <h4 class="title"><%= jspUtil.label("knowledge.navbar.search") %></h4>
    
    <form role="form" action="<%=request.getContextPath()%><%=top%>">
    
        <div class="form-group">
            <label for="input_tag">
            <%= jspUtil.label("knowledge.search.keyword") %>
            </label>
            <input type="text" class="form-control" placeholder="<%= jspUtil.label("knowledge.search.placeholder") %>"
                name="keyword" id="searchkeyword" value="<%=jspUtil.out("searchKeyword")%>" />
        </div>
        <div class="form-group">
            <label for="input_tag">
            <%= jspUtil.label("knowledge.add.label.type") %>
            </label><br/>
            <!-- <input type="checkbox" name="" value="all" checked="checked">ALL &nbsp; -->
            <c:forEach var="template" items="${templates}" varStatus="status">
                <label><input type="checkbox" name="template" value="<%= jspUtil.out("template.typeId") %>" checked>
                <i class="fa <%= jspUtil.out("template.typeIcon") %>"></i>
                <%= jspUtil.out("template.typeName") %>&nbsp;
                </label>
            </c:forEach>
        </div>
        <div class="form-group">
            <label for="input_tag">
            <%= jspUtil.label("knowledge.search.tags") %>
            <span class="helpMarkdownLabel">
            <a data-toggle="modal" data-target="#tagSelectModal"><%= jspUtil.label("label.search.tags") %></a>
            </span>
            </label>
            <p class="tags">
            <input type="text" class="form-control" name="tagNames" id="input_tags" data-role="tags input"
                placeholder="<%= jspUtil.label("knowledge.add.label.tags") %>" value="<%= jspUtil.out("tagNames") %>" />
            </p>
        </div>
        
        <div class="form-group">
            <label for="input_tag">
            <%= jspUtil.label("knowledge.search.creator") %>
            <span class="helpMarkdownLabel">
            <a data-toggle="modal" data-target="#searchUserModal"><%= jspUtil.label("knowledge.search.creator") %></a>
            </span>
            </label>
            <p class="creators">
            <input type="text" class="form-control" name="creators" id="creators" data-role="tags input"
                placeholder="<%= jspUtil.label("knowledge.search.creator") %>" value="<%= jspUtil.out("creators") %>" />
            </p>
        </div>
        
        <% if (jspUtil.logined()) { %>
        <div class="form-group">
            <label for="input_group">
            <%= jspUtil.label("knowledge.search.groups") %>
            <span class="helpMarkdownLabel">
            <a data-toggle="modal" data-target="#groupSelectModal"><%= jspUtil.label("label.search.groups") %></a>
            </span>
            </label>
            <p class="groups">
            <input type="text" class="form-control" name="groupNames" id="input_groups" data-role="groups input"
                placeholder="<%= jspUtil.label("knowledge.add.label.groups") %>" value="<%= jspUtil.out("groupNames") %>" />
            </p>
        </div>
        <% } %>
        <button class="btn btn-primary" type="submit">
            <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.search") %>
        </button>
        <button class="btn btn-warning" type="button" id="searchParamClear">
            <i class="fa fa-times-circle"></i>&nbsp;<%= jspUtil.label("label.clear") %>
        </button>
        <a href="<%= request.getContextPath() %>/open.knowledge/list/<%=jspUtil.out("offset")%><%= jspUtil.out("params") %>"
        class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
    </form>


    <jsp:include page="../tag/dialog.jsp"></jsp:include>
    <jsp:include page="../../protect/group/dialog.jsp"></jsp:include>
    <jsp:include page="partials/selectUserDialog.jsp"></jsp:include>

</c:param>

</c:import>

