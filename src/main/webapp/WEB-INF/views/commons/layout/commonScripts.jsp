<%@page import="redcomet.web.util.JspUtil"%>
<%@page import="redcomet.knowledge.control.Control"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- scripts -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery/dist/jquery.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap/dist/js/bootstrap.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/notifyjs/dist/notify.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/notifyjs/dist/notify-combined.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/notifyjs/dist/styles/bootstrap/notify-bootstrap.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/marked/lib/marked.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/highlightjs/highlight.pack.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootbox/bootbox.js"></script>


<script type="text/javascript">
var _CONTEXT = '<%= request.getContextPath() %>';

function setCookie(c_name, value, expiredays) {
//	var path = location.pathname;
//	var paths = new Array();
//	paths = path.split("/");
//	if (paths[paths.length - 1] != "") {
//		paths[paths.length - 1] = "";
//		path = paths.join("/");
//	}
	
	var extime = new Date().getTime();
	var cltime = new Date(extime + (60 * 60 * 24 * 1000 * expiredays));
	var exdate = cltime.toUTCString();
	var s = "";
	s += c_name + "=" + escape(value);
	s += "; path=" + _CONTEXT;
	if (expiredays) {
		s += "; expires=" + exdate + "; ";
	} else {
		s += "; ";
	}
	document.cookie = s;
}
setCookie('<%= JspUtil.TIME_ZONE_OFFSET %>', (new Date()).getTimezoneOffset(), 60);

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


</script>

<!-- /scripts -->
