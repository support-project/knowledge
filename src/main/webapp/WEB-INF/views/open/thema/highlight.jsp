<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/highlightjs/styles/<%= jspUtil.out("highlight") %>.css" />
<style>
body {
    padding: 5px;
    overflow: hidden;
}
</style>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/highlightjs/highlight.pack.js"></script>

<script type="text/javascript">
hljs.initHighlightingOnLoad();
</script>

</head>

<body>

<pre>
<code class="javascript">
/** Style Name: <%= jspUtil.out("title") %> */

/** Do not right click */
function no_rclick() {
    var msg = 'Do not right click'; // Change
    if (event.button == 2) {
        alert(msg);
    }
}
document.onmousedown=no_rclick;
</code>
</pre>

</body>
</html>

