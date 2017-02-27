<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.web.config.AppConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.control.Control"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- build:js(src/main/webapp) js/page-common.js -->
<script type="text/javascript" src="bower/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="bower/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="bower/bluebird/js/browser/bluebird.min.js"></script>

<script type="text/javascript" src="bower/notifyjs/dist/notify.min.js"></script>
<script type="text/javascript" src="bower/notifyjs/dist/notify-combined.min.js"></script>
<script type="text/javascript" src="bower/notifyjs/dist/styles/bootstrap/notify-bootstrap.js"></script>

<script type="text/javascript" src="bower/marked/lib/marked.js"></script>
<script type="text/javascript" src="bower/highlightjs/highlight.pack.js"></script>

<script type="text/javascript" src="bower/bootbox/bootbox.js"></script>

<script type="text/javascript" src="bower/notify.js/notify.js"></script>
<script type="text/javascript" src="bower/jquery-oembed-all/jquery.oembed.js"></script>

<script type="text/javascript" src="bower/jstzdetect/jstz.min.js"></script>

<script type="text/javascript" src="js/common.js"></script>
<!-- endbuild -->


<script type="text/javascript">
var _LOGGING_NOTIFY_DESKTOP = false;
var _CONTEXT = '<%= request.getContextPath() %>';
<% if (jspUtil.logined()) { %>
var _LOGIN_USER_ID = <%= jspUtil.id() %>;
<% } else { %>
var _LOGIN_USER_ID = null;
<% } %>

var getCookies = function() {
    var result = new Array();
    var allcookies = document.cookie;
    if( allcookies != '' ) {
        var cookies = allcookies.split( '; ' );
        for( var i = 0; i < cookies.length; i++ ) {
            var cookie = cookies[ i ].split( '=' );
            result[ cookie[ 0 ] ] = decodeURIComponent( cookie[ 1 ] );
        }
    }
    return result;
}

var setCookie = function(c_name, value, expiredays, path) {
    var extime = new Date().getTime();
    var cltime = new Date(extime + (60 * 60 * 24 * 1000 * expiredays));
    var exdate = cltime.toUTCString();
    var s = '';
    s += c_name + '=' + escape(value) + ';';
    if (expiredays) {
        s += ' expires=' + exdate + ';';
    }
    if (path) {
        s += ' path=' + path + ';';
    } else {
        s += ' path=' + _CONTEXT + '/;';
//        s += ' path=/;';
    }
    
    document.cookie = s;
}
setCookie('<%= AppConfig.get().getSystemName() %>_<%= JspUtil.TIME_ZONE_OFFSET %>', (new Date()).getTimezoneOffset(), 60);
setCookie('<%= AppConfig.get().getSystemName() %>_TIMEZONE', jstz.determine().name(), 60);

<% 

java.util.List<String> successes = (java.util.List<String>) request.getAttribute(Control.MSG_SUCCESS);
if (successes != null) {
    for (String msg : successes) {
%>
$.notify('<%= msg %>', 'success');
<%
    }
}

java.util.List<String> infos = (java.util.List<String>) request.getAttribute(Control.MSG_INFO);
if (infos != null) {
    for (String msg : infos) {
%>
$.notify('<%= msg %>', 'info');
<%
    }
}

java.util.List<String> warns = (java.util.List<String>) request.getAttribute(Control.MSG_WARN);
if (warns != null) {
    for (String msg : warns) {
%>
$.notify('<%= msg %>', 'warn');
<%
    }
}

java.util.List<String> errors = (java.util.List<String>) request.getAttribute(Control.MSG_ERROR);
if (errors != null) {
    for (String msg : errors) {
%>
$.notify('<%= msg %>', 'error');
<%
    }
}

%>

<% if (jspUtil.is(Boolean.TRUE, "desktopNotify")) { %>
function onNotifyShow() {
    console.log('notification was shown!');
}

function notifyDesktop(msg, link) {
    $.notify(msg, 'info');
    if (Notify.isSupported) {
        Notify.requestPermission(
            function() {
                var myNotification = new Notify('Notify from Knowledge', {
                    body: msg,
                    notifyShow: onNotifyShow,
                    timeout: 3,
                    notifyClick: function() {
                        if (link) {
                            window.location.href=link;
                        }
                    }
                });
                myNotification.show();
            },
            function() {
                console.log('notification off. msg show to console.');
                console.log(msg);
            }
        );
    }
}

if (Notify.isSupported) {
    Notify.requestPermission(function() {
        console.log('notification on.');
    }, function() {
        console.log('notification off.');
    });
}

var webSocket;
window.onload = function() {
    var forRtoA = document.createElement('a');
    forRtoA.href = '<%= request.getContextPath() %>/notify';
    console.log(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
    webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
    webSocket.onopen = function() {
    }
    webSocket.onclose = function() {
    }
    webSocket.onmessage = function(message) {
        console.log('[RECEIVE] ');
        var result = JSON.parse(message.data);
        console.log(result);
        notifyDesktop(result.message, result.result);
    }
    webSocket.onerror = function(message) {
    }
    <%-- //webSocket.send(message); --%>
}
<% } %>

</script>

<!-- /scripts -->
