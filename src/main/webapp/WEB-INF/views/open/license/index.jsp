<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
    errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-license.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/markdown.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-license.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/marked/lib/marked.js"></script>
<!-- endbuild -->

<script>
$(document).ready(function(){
    hljs.initHighlightingOnLoad();
    marked.setOptions({
        langPrefix: '',
        highlight: function(code, lang) {
            console.log('[highlight]' + lang);
            return code;
        }
    });
    $.ajax({
        url: _CONTEXT + '/assets/Third_party_license.md',
        type: 'GET',
        timeout: 10000,
    }).done(function(result, textStatus, xhr) {
        $('#content').html(marked(result));
    }).fail(function(xhr, textStatus, error) {
        handleErrorResponse(xhr, textStatus, error);
    });
});
</script>
</c:param>

<c:param name="PARAM_CONTENT">

<h2>Knowledge - <%= jspUtil.label("label.version") %></h2>
<h2>This Project License</h2>
<ul>
    <li>This project is provided under Apache License, Version 2.0</li>
    <li>http://www.apache.org/licenses/LICENSE-2.0</li>
</ul>

<div id="content"></div>

</c:param>

</c:import>

