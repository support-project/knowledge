<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
	errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
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
	$('#content').html(marked($('#content').text()));
});
</script>
</c:param>

	<c:param name="PARAM_CONTENT">

		<div class="jumbotron">
			<h2><i class="fa fa-book"></i>&nbsp;Knowledge</h2>
			<p></p>
			<p><%= jspUtil.label("knowledge.top.description") %></p>
			<p>
				<a class="btn btn-info btn-lg" role="button" href="<%= request.getContextPath() %>/open.knowledge/list" >
				<i class="fa fa-eye"></i>&nbsp;<%= jspUtil.label("knowledge.top.use.button") %>
				</a>
			</p>
		</div>
		
<div id="content">
<%= jspUtil.label("knowledge.top.features.title") %>
<%= jspUtil.label("knowledge.top.features.1") %>
<%= jspUtil.label("knowledge.top.features.2") %>
<%= jspUtil.label("knowledge.top.features.3") %>
<%= jspUtil.label("knowledge.top.features.4") %>
<%= jspUtil.label("knowledge.top.features.5") %>
<%= jspUtil.label("knowledge.top.features.6") %>

</div>

	</c:param>

</c:import>

