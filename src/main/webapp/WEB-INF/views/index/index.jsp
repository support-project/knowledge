<%@page import="org.support.project.knowledge.vo.LabelValue"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
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

<meta content="<%=jspUtil.label("knowledge.header.meta.title")%>" name="title">
<meta content="<%=jspUtil.label("knowledge.header.meta.description")%>" name="description">

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">

<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/vnd.microsoft.icon" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/bower/highlightjs/styles/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/bower/bootstrap/dist/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/bower/bootstrap/dist/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/bower/font-awesome/css/font-awesome.min.css" />

<title><%=jspUtil.label("knowledge.title")%></title>

<link rel="stylesheet" href="<%=jspUtil.mustReloadFile("/css/common.css")%>" />

<style>
#container { /*親要素*/
    position: relative;
    width: 100%;
    min-height: 100%;
    height: auto !important;
    height: 100%;
    padding: 0px;
    margin: 0px;
}

#content{
	margin-top:50px;
	padding-bottom:140px; /*フッターの高さと同じ*/
	min-height: 200px;
	text-align: left;
	padding-left: 20px;
}
#footer {
	height:140px;
	width:100%;
	position:absolute;
	bottom:0;
}

#headerwrap {
	background: url('<%=request.getContextPath()%>/images/startup-594090_1280.jpg') no-repeat center top;
	margin-top: -10px;
	padding-top:20px;
	text-align:center;
	background-attachment: relative;
	background-position: center center;
	min-height: 700px;
	width: 100%;
	
    -webkit-background-size: 100%;
    -moz-background-size: 100%;
    -o-background-size: 100%;
    background-size: 100%;

    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
}
#headerwrap h1 {
	margin-top: 130px;
	font-size: 120px;
	font-weight: 700;
	letter-spacing: 3px;
	color: rgba(50,50,50,0.9);
	text-shadow:
	0 0 25px #edf8ff,
	0 0 20px #edf8ff,
	0 0 0.40px #edf8ff;
}

#headerwrap h2 {
	font-size: 50px;
	font-weight: 300;
	letter-spacing: 2px;
	color: rgba(50,50,50,0.9);
	text-shadow:
	0 0 25px #edf8ff,
	0 0 20px #edf8ff,
	0 0 0.40px #edf8ff;
}

@media screen and (max-width: 340px) {
	#headerwrap h1 {
		font-size: 40px;
	}
	#headerwrap h2 {
		font-size: 20px;
	}
}
@media screen and (min-width: 341px) and (max-width: 580px) {
	#headerwrap h1 {
		font-size: 40px;
	}
	#headerwrap h2 {
		font-size: 20px;
	}
}
@media screen and (min-width: 581px) and (max-width: 800px) {
	#headerwrap h1 {
		font-size: 70px;
	}
}
@media screen and (min-width: 801px) and (max-width: 1000px) {
	#headerwrap h1 {
		font-size: 90px;
	}
}

#msg {
	text-align:left;
	color: white;
}

</style>

</head>

<body id="headerwrap">

	<c:import url="/WEB-INF/views/commons/layout/commonNavbar.jsp" />

	<div class="container" id="container">
	
	<div >
		<h1><i class="fa fa-book"></i>&nbsp;Knowledge</h1>
		<h2>Free Knowledge Base System</h2>
		<br/>
		<a id="showlist" class="btn btn-info btn-lg" role="button"
			href="<%=request.getContextPath()%>/open.knowledge/list">
			<i class="fa fa-diamond"></i>&nbsp;<%=jspUtil.label("knowledge.top.use.button")%>
		</a>
		<br/><br/>
		<br/><br/>
		
		<%
			AppConfig appConfig = AppConfig.get();
			List<LabelValue> languages = appConfig.getLanguages();
			for (LabelValue language : languages) {
		%>
			<a href="<%= request.getContextPath() %>/open.lang/select/<%= language.getValue() %>" style="cursor: pointer;color: red;font-size: 18px">
				<%= language.getLabel() %>
			</a>
			&nbsp;&nbsp;
		<%
			}
		%>
	</div>
	
	<%--
	<div id="msg">
	<p><%=jspUtil.label("knowledge.top.description")%></p>
	</div>
	--%>
	
	<br/>
	<br/>

<div id="content" class="jumbotron">
<%=jspUtil.label("knowledge.top.features.title")%>
<%=jspUtil.label("knowledge.top.features.1")%>
<%=jspUtil.label("knowledge.top.features.2")%>
<%=jspUtil.label("knowledge.top.features.3")%>
<%=jspUtil.label("knowledge.top.features.4")%>
<%=jspUtil.label("knowledge.top.features.5")%>
<%=jspUtil.label("knowledge.top.features.6")%>

[-> More Information](https://support-project.org/knowledge_info/index "-> More Information")
</div>

	<div id="footer">
		<ul class="footer-menu list-inline">
			<li class="first"><a class=""
				href="<%=request.getContextPath()%>/index"
				style="cursor: pointer;"> About</a></li>
			<li><a class=""
				href="<%=request.getContextPath()%>/open.knowledge/list"
				style="cursor: pointer;"> Show Knowledges</a></li>
			<li><a class=""
				href="<%=request.getContextPath()%>/open.license"
				style="cursor: pointer;"> License</a></li>
		</ul>
		<!-- /nav -->
		<div class="clearfix"></div>
		<div class="copy">
			<span>Copyright &#169; 2015 <a href="https://support-project.org/knowledge_info/index">support-project.org [Knowledge project]</a></span>
		</div>
		<!-- /copy -->
	</div>
	<!-- /footer -->

	</div>

	<c:import url="/WEB-INF/views/commons/layout/commonScripts.jsp" />

	<script>
		$(document).ready(function() {
			hljs.initHighlightingOnLoad();
			marked.setOptions({
				langPrefix : '',
				highlight : function(code, lang) {
					console.log('[highlight]' + lang);
					return code;
				}
			});
			$('#content').html(marked($('#content').text()));
		});
	</script>


</body>
</html>


