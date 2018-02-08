<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
    errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- Modal -->
<div class="modal fade" id="tagSelectModal" tabindex="-1"
    role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
                <h4 class="modal-title"><%= jspUtil.label("label.search.tags") %> [page-<span id='tagselectPageNo'></span>]</h4>
            </div>
            
            <div class="modal-body">
                
                <div role="form" class="form-inline">
                    <input type="text" class="form-control" 
                        value="" placeholder="Keyword" id="tagselectKeyword">
                    <button type="button" class="btn btn-success tagselectSearchButton">
                        <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.filter") %>
                    </button>
                    <button type="button" class="btn btn-default tagselectPagerPrev" >
                        <i class="fa fa-arrow-circle-left"></i>&nbsp;<%= jspUtil.label("label.previous") %>
                    </button>
                    <button type="button" class="btn btn-default tagselectPagerNext">
                        <%= jspUtil.label("label.next") %>&nbsp;<i class="fa fa-arrow-circle-right "></i>
                    </button>
                    <button type="button" class="btn btn-info tagselectAddButton">
                        <i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("label.add") %>
                    </button>
                </div>
                <br/>
            
                <div class="te">
                    <ul class="list-group" id="tagDatas">
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
