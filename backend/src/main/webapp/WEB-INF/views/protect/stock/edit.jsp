<%@page import="org.support.project.web.config.CommonWebParameter"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>
<c:param name="PARAM_SCRIPTS">
<script>
function confirmDelete() {
	bootbox.confirm("<%= jspUtil.label("message.confirm.delete") %>", function(result) {
		if (result) {
			$('#editForm').attr('action', '<%= request.getContextPath()%>/protect.stock/delete');
			$('#editForm').submit();
		}
	}); 
};
</script>
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.stock.edit") %></h4>

<form action="<%= request.getContextPath()%>/protect.stock/update" method="post" role="form" id="editForm">
	
	<input type="hidden" name="stockId" value="<%= jspUtil.out("stockId") %>" />
	
	<div class="form-group">
		<label for="stockName"><%= jspUtil.label("knowledge.stock.label.stockName") %></label>
		<input type="text" class="form-control" name="stockName" id="stockName" 
			placeholder="Stock Name" value="<%= jspUtil.out("stockName") %>">
	</div>
	<div class="form-group">
		<label for="description"><%= jspUtil.label("knowledge.stock.label.description") %></label>
		<input type="text" class="form-control" name="description" id="description" 
		placeholder="Description" value="<%= jspUtil.out("description") %>">
	</div>

	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
	
	<button type="button" class="btn btn-danger" onclick="confirmDelete();">
		<i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
	</button>
	
	<a href="<%= request.getContextPath() %>/protect.stock/mylist/<%= jspUtil.out("offset") %>"
	class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>

</form>



</c:param>

</c:import>

