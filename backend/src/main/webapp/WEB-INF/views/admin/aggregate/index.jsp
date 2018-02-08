<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-system-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">

<!-- build:js(src/main/webapp) js/page-aggregate.js -->
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
forRtoA.href = '<%= request.getContextPath() %>/aggregate';
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
    webSocket.send('START_PROCESS');
});

</script>
</c:param>


<c:param name="PARAM_CONTENT">
<h4 class="title">
<%= jspUtil.label("knowledge.aggregate.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<%= jspUtil.label("knowledge.aggregate.msg") %>
<br/>
<button class="btn btn-primary" id="startMigrate">Execute</button><br/>
<br/>

-- Execute Log-- <br/>
<textarea class="form-control" id="log" readonly="readonly" rows="5"></textarea>

<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.admin.backtomenu") %></a>

</c:param>

</c:import>

