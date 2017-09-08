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

<meta charset="UTF-8">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="imagetoolbar" content="no" />

<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">

<meta content="<%= jspUtil.label("knowledge.header.meta.title") %>" name="title">
<meta content="<%= jspUtil.label("knowledge.header.meta.description") %>" name="description">
<meta content='ナレッジマネジメント,ナレッジベース,情報共有,オープンソース,KnowledgeBase,KnowledgeManagement,OSS,Markdown' name='keywords'>
<meta property="og:type" content="article"/>
<meta property="og:title" content="<%= jspUtil.label("knowledge.header.meta.title") %>"/>
<meta property="og:description" content="<%= jspUtil.label("knowledge.header.meta.description") %>" />
<%-- <meta property="og:image" content="" /> --%>
<meta property="og:url" content="https://support-project.org/knowledge/index" />
<meta property="og:site_name" content="Knowledge"/>

<link rel="icon" href="<%= request.getContextPath() %>/favicon.ico" type="image/vnd.microsoft.icon" /> 

<% if (StringUtils.isNotEmpty(jspUtil.out("thema"))) { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch/<%= jspUtil.out("thema") %>/bootstrap.min.css" />
<% } else { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch/<%= jspUtil.cookie(SystemConfig.COOKIE_KEY_THEMA, "flatly") %>/bootstrap.min.css" />
<% } %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/flag-icon-css/css/flag-icon.min.css" />

<!-- Knowledge - <%= jspUtil.label("label.version") %> -->

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
$('#startMigrate').click(function() {
    $('#log').val('');
    var forRtoA = document.createElement('a');
    forRtoA.href = '<%= request.getContextPath() %>/migrate';
    console.log(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
    webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
    webSocket.onopen = function() {
        console.log('onopen');
    }
    webSocket.onclose = function() {
        console.log('onclose');
    }
    webSocket.onmessage = function(message) {
        var result = JSON.parse(message.data);
        console.log(result);
        $('#log').val($('#log').val() + result.message + "\n");
        var psconsole = $('#log');
        psconsole.scrollTop(
            psconsole[0].scrollHeight - psconsole.height()
        );
    }
    webSocket.onerror = function(message) {
        console.log(message);
    }
});

</script>
</body>

</html>