<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- Modal -->
<div class="modal fade" id="helpMarkdownModal" tabindex="-1" role="dialog" aria-labelledby="helpMarkdownModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Markdown sample</h3>
                        <textarea id="sampleMarkdownText" rows="8" class="form-control"><%= jspUtil.out("markdown", JspUtil.ESCAPE_NONE) %></textarea>
                    </div>
                </div>
                <div class="row ">
                    <div class="col-sm-12">
                        <h3>Display sample</h3>
                        <div class="markdown viewarea markdown_sample" id="markdownSamplePreview" style="word-break: normal;">
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

