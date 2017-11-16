<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-system-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->

<style>
.radio_block {
    margin-bottom: 10px;
}
</style>
</c:param>

<c:param name="PARAM_SCRIPTS">
<% if (jspUtil.is(Boolean.TRUE, "start_reindexing")) { %>
<script>
var webSocket;
window.onload = function() {
    var forRtoA = document.createElement('a');
    forRtoA.href = '<%= request.getContextPath() %>/reindexing';
    console.log(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
    webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
    webSocket.onopen = function() {
    }
    webSocket.onclose = function() {
        console.log('onclose');
        //$("#indexing_msg").alert('close');
        $('#msg').text('<%= jspUtil.label("knowledge.reindexing.msg.end") %>');
        var psconsole = $('#log');
        psconsole.scrollTop(
            psconsole[0].scrollHeight - psconsole.height()
        );
    }
    webSocket.onmessage = function(message) {
        //console.log('[RECEIVE] ');
        var result = JSON.parse(message.data);
        console.log(result);
        $('#log').val($('#log').val() + result.message + "\n");
        var psconsole = $('#log');
        psconsole.scrollTop(
            psconsole[0].scrollHeight - psconsole.height()
        );
    }
    webSocket.onerror = function(message) {
    }
}
</script>
<% } %>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.reindexing.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<% if(jspUtil.is(Boolean.TRUE, "start_reindexing")) { %>
    <div class="alert alert-warning alert-dismissible" role="alert" id="indexing_msg">
        <strong>Information</strong>
        <p id="msg"><%= jspUtil.label("knowledge.reindexing.msg.start") %></p>
    </div>
<% } %>

<div class="alert alert-info alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Information</strong><br/>
    - <%= jspUtil.label("knowledge.reindexing.msg1") %><br/>
    - <%= jspUtil.label("knowledge.reindexing.msg2") %><br/>
    - <%= jspUtil.label("knowledge.reindexing.msg3") %><br/>
    - <%= jspUtil.label("knowledge.reindexing.msg4") %>
</div>

<form action="<%= request.getContextPath()%>/admin.database/start_reindexing" method="post" role="form">
    <div class="form-group">
        <label for="start"><%= jspUtil.label("knowledge.reindexing.label.start") %></label>
        <input type="number" class="form-control" name="start" id="start" placeholder="start" value="<%= jspUtil.out("start") %>" required="required"/>
    </div>
    <div class="form-group">
        <label for="end"><%= jspUtil.label("knowledge.reindexing.label.end") %></label>
        <input type="number" class="form-control" name="end" id="end" placeholder="end" value="<%= jspUtil.out("end") %>" required="required"/>
    </div>
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-refresh"></i>&nbsp;<%= jspUtil.label("label.start") %></button>
</form>



<% if(jspUtil.is(Boolean.TRUE, "start_reindexing")) { %>
    <div class="form-group">
        <label for="log">LOG</label>
        <textarea class="form-control" id="log" readonly="readonly" rows="5"></textarea>
    </div>
<% } %>


</c:param>

</c:import>

