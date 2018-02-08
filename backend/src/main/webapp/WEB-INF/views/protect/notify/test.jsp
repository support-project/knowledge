<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">

<script type="text/javascript">
    var webSocket;
    window.onload = function() {
        var forRtoA = document.createElement('a');
        forRtoA.href = '<%= request.getContextPath() %>/notify';
        console.log(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
        webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
        
        var appendMessage = function(value, color) {
            console.log(value + ':' + color);
            $('#messageArea').append('<div style="color:' + color + '">' + value + '</div>');
        }
        webSocket.onopen = function() {
            appendMessage("Opened", "blue");
        }
        webSocket.onclose = function() {
            appendMessage("Closed", "red");
        }
        webSocket.onmessage = function(message) {
            console.log('[RECEIVE] ');
            var result = JSON.parse(message.data);
            console.log(result);
            appendMessage(result.message, "black");
        }
        webSocket.onerror = function(message) {
            appendMessage(message, "red");
        }
        var messageInput = document.getElementById("messageInput");
        messageInput.onkeypress = function(e) {
            if (13 == e.keyCode) {
                var message = messageInput.value;
                if (webSocket && "" != message) {
                    console.log('[SEND] ' + message);
                    webSocket.send(message);
                    messageInput.value = "";
                }
            }
        }
    }
</script>


</c:param>


<c:param name="PARAM_CONTENT">


    <div>
        <input type="text" width="90%" id="messageInput" />
    </div>
    
    <div id="messageArea">
    
    </div>
    <div></div>


</c:param>

</c:import>

