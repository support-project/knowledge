<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/bower/diff2html/dist/diff2html.css">
    </c:param>

    <c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-knowledge-history.js -->
    <script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/bower/jsdiff/diff.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/bower/diff2html/dist/diff2html.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/bower/diff2html/dist/diff2html-ui.min.js"></script>
<!-- endbuild -->
    <script>
    echo.init();
    $(document).ready(function() {
        var historyContent = document.querySelector("#history-content").value;
        var nowContent = document.querySelector("#now-content").value;
        var unifiedDiff = JsDiff.createTwoFilesPatch('History → Now', 'History → Now', historyContent, nowContent);
        var diff2htmlUi = new Diff2HtmlUI({diff: unifiedDiff});
        diff2htmlUi.draw('#content-diff', {inputFormat: 'diff', matching: 'lines'});
        diff2htmlUi.highlightCode('#content-diff');
    });
    </script>
    </c:param>

    <c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.histories.title") %></h4>

<p>
<img src="<%= request.getContextPath()%>/images/loader.gif" 
    data-echo="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("history.updateUser") %>" 
    alt="icon" width="36" height="36" style="float:left" />
<br/>
<i class="fa fa-user"></i>&nbsp;<%= jspUtil.out("history.userName") %>&nbsp;
<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("history.updateDatetime")%>
</p>

<h5 class="sub_title"><%= jspUtil.label("knowledge.histories.label.diff") %></h5>
<div id="content-diff"></div>

<br/>
<h5 class="sub_title"><%= jspUtil.label("knowledge.histories.label.history") %></h5>
<div class="form-group">
    <label for="input_content"><%= jspUtil.label("knowledge.add.label.content") %></label>
    <textarea id="history-content" class="form-control" name="content" rows="5" placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="content" readonly="readonly"><%= jspUtil.out("history.content") %></textarea>
</div>

<br/>
<h5 class="sub_title"><%= jspUtil.label("knowledge.histories.label.now") %></h5>
<div class="form-group">
    <label for="input_content"><%= jspUtil.label("knowledge.add.label.content") %></label>
    <textarea id="now-content" class="form-control" name="content" rows="5" placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="content" readonly="readonly"><%= jspUtil.out("now.content") %></textarea>
</div>

<% 
    String connect = "&";
    if (StringUtils.isEmpty(jspUtil.out("params"))) {
        connect = "?";
    }
%>

<a href="<%= request.getContextPath() %>/open.knowledge/histories/<%= jspUtil.out("knowledgeId") %><%= jspUtil.out("params") %><%= connect %>page=<%= jspUtil.out("page") %>"
    class="btn btn-warning" role="button"><i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
</a>

    </c:param>

</c:import>

