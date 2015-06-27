<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
	errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- Modal -->
<div class="modal fade" id="helpMarkdownModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">Markdown</h4>
			</div>
			
			<div class="modal-body">
				<div class="te">
<textarea id="sampleMarkdownText" rows="20" class="form-control">
### Markdown sample 
<%= jspUtil.label("knowledge.sample.markdown.1") %>
<%= jspUtil.label("knowledge.sample.markdown.2") %>

<%= jspUtil.label("knowledge.sample.markdown.11") %>
<%= jspUtil.label("knowledge.sample.markdown.12") %>
<%= jspUtil.label("knowledge.sample.markdown.13") %>
<%= jspUtil.label("knowledge.sample.markdown.14") %>

<%= jspUtil.label("knowledge.sample.markdown.21") %>
<%= jspUtil.label("knowledge.sample.markdown.22") %>
<%= jspUtil.label("knowledge.sample.markdown.23") %>
<%= jspUtil.label("knowledge.sample.markdown.24") %>
<%= jspUtil.label("knowledge.sample.markdown.25") %>

<%= jspUtil.label("knowledge.sample.markdown.31") %>
<%= jspUtil.label("knowledge.sample.markdown.32") %>
<%= jspUtil.label("knowledge.sample.markdown.33") %>
<%= jspUtil.label("knowledge.sample.markdown.34") %>

<%= jspUtil.label("knowledge.sample.markdown.41") %>
<%= jspUtil.label("knowledge.sample.markdown.42") %>
<%= jspUtil.label("knowledge.sample.markdown.43") %>

<%= jspUtil.label("knowledge.sample.markdown.51") %>
<%= jspUtil.label("knowledge.sample.markdown.52") %>
<%= jspUtil.label("knowledge.sample.markdown.53") %>
<%= jspUtil.label("knowledge.sample.markdown.54") %>

<%= jspUtil.label("knowledge.sample.markdown.61") %>
<%= jspUtil.label("knowledge.sample.markdown.62") %>
<%= jspUtil.label("knowledge.sample.markdown.63") %>

<%= jspUtil.label("knowledge.sample.markdown.71") %>
<%= jspUtil.label("knowledge.sample.markdown.72") %>

<%= jspUtil.label("knowledge.sample.markdown.73") %>
<%= jspUtil.label("knowledge.sample.markdown.74") %>
<%= jspUtil.label("knowledge.sample.markdown.75") %>
<%= jspUtil.label("knowledge.sample.markdown.76") %>
<%= jspUtil.label("knowledge.sample.markdown.77") %>

<%= jspUtil.label("knowledge.sample.markdown.501") %>
<%= jspUtil.label("knowledge.sample.markdown.502") %>

</textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="sampleMarkdownCheck"><%= jspUtil.label("knowledge.sample.markdown.preview") %></button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

