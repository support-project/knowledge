<%@page import="org.support.project.knowledge.deploy.InitDB"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!DOCTYPE html>
<html>
<head>

<c:import url="/WEB-INF/views/commons/layout/commonHeader.jsp" />

</head>

<body class="container">

<h2>
<%= jspUtil.label("knowledge.maintenance.migrate.title") %>
</h2>
<p>
<%= jspUtil.label("knowledge.maintenance.migrate.message") %>
</p>
service database version: <%= jspUtil.out("db_version") %><br/>
latest database version: <%= InitDB.CURRENT %><br/>

<button class="btn btn-primary" id="startMigrate">Execute</button><br/>
<br/>

-- Execute Log-- <br/>
<textarea class="form-control" id="log" readonly="readonly" rows="5"></textarea>


<a href="<%= request.getContextPath() %>/"><i class="fa fa-home" aria-hidden="true"></i>Back to knowledge top</a>

<!-- build:js(src/main/webapp) js/page-migrate.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bluebird/js/browser/bluebird.min.js"></script>
<!-- endbuild -->

<script>
$('#log').val('');
var msgs = [];
var appendMsg = function(msg) {
    msgs.push(msg);
    if (msgs.length > 100) {
        msgs.shift();
    }
    $('#log').val(msgs.join("\n"));
    var psconsole = $('#log');
    psconsole.scrollTop(
        psconsole[0].scrollHeight - psconsole.height()
    );
};

var forRtoA = document.createElement('a');
forRtoA.href = '<%= request.getContextPath() %>/migrate';
console.log(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
webSocket.onopen = function() {
    console.log('onopen');
    appendMsg('start websocket.');
}
webSocket.onclose = function() {
    console.log('onclose');
}
webSocket.onmessage = function(message) {
    console.log(message);
    var obj = JSON.parse(message.data);
    console.log(obj.message);
    appendMsg(obj.message);
}
webSocket.onerror = function(message) {
    console.log(message);
}
$('#startMigrate').click(function() {
    webSocket.send('START_MIGRATE');
});

</script>
</body>

</html>