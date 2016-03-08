<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- Modal -->
<div class="modal fade" id="helpMarkdownModal" tabindex="-1" role="dialog" aria-labelledby="helpMarkdownModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <!-- 
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Markdown</h4>
            </div>
            -->

            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Markdown sample</h3>
                        <textarea id="sampleMarkdownText" rows="8" class="form-control">
### Markdown sample 
<%=jspUtil.label("knowledge.sample.markdown.1")%>
<%=jspUtil.label("knowledge.sample.markdown.2")%>

<%=jspUtil.label("knowledge.sample.markdown.11")%>
<%=jspUtil.label("knowledge.sample.markdown.12")%>
<%=jspUtil.label("knowledge.sample.markdown.13")%>
<%=jspUtil.label("knowledge.sample.markdown.14")%>

<%=jspUtil.label("knowledge.sample.markdown.21")%>
<%=jspUtil.label("knowledge.sample.markdown.22")%>
<%=jspUtil.label("knowledge.sample.markdown.23")%>
<%=jspUtil.label("knowledge.sample.markdown.24")%>
<%=jspUtil.label("knowledge.sample.markdown.25")%>

<%=jspUtil.label("knowledge.sample.markdown.31")%>
<%=jspUtil.label("knowledge.sample.markdown.32")%>
<%=jspUtil.label("knowledge.sample.markdown.33")%>
<%=jspUtil.label("knowledge.sample.markdown.34")%>

<%=jspUtil.label("knowledge.sample.markdown.41")%>
<%=jspUtil.label("knowledge.sample.markdown.42")%>
<%=jspUtil.label("knowledge.sample.markdown.43")%>

<%=jspUtil.label("knowledge.sample.markdown.51")%>
<%=jspUtil.label("knowledge.sample.markdown.52")%>
<%=jspUtil.label("knowledge.sample.markdown.53")%>
<%=jspUtil.label("knowledge.sample.markdown.54")%>

<%=jspUtil.label("knowledge.sample.markdown.61")%>
<%=jspUtil.label("knowledge.sample.markdown.62")%>
<%=jspUtil.label("knowledge.sample.markdown.63")%>

<%=jspUtil.label("knowledge.sample.markdown.71")%>
<%=jspUtil.label("knowledge.sample.markdown.72")%>

<%=jspUtil.label("knowledge.sample.markdown.73")%>
<%=jspUtil.label("knowledge.sample.markdown.74")%>
<%=jspUtil.label("knowledge.sample.markdown.75")%>
<%=jspUtil.label("knowledge.sample.markdown.76")%>
<%=jspUtil.label("knowledge.sample.markdown.77")%>

<%=jspUtil.label("knowledge.sample.markdown.78")%>
<%=jspUtil.label("knowledge.sample.markdown.79")%>
<%=jspUtil.label("knowledge.sample.markdown.80")%>
<%=jspUtil.label("knowledge.sample.markdown.81")%>
<%=jspUtil.label("knowledge.sample.markdown.82")%>
<%=jspUtil.label("knowledge.sample.markdown.83")%>

<%=jspUtil.label("knowledge.sample.markdown.501")%>
<%=jspUtil.label("knowledge.sample.markdown.502")%>

</textarea>
                    </div>
                </div>
                <div class="row ">
                    <div class="col-sm-12">
                        <h3>Display sample</h3>
                        <div class="markdown viewarea markdown_sample" id="markdownSamplePreview" style="word-break: break-all;">
                        <li class="fa fa-refresh fa-spin fa-5x"></li>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

