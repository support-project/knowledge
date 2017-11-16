<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-notice-list.css -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/css/bootstrap-datepicker3.min.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/markdown.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
    </c:param>

    <c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-notice-list.js -->
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.en-GB.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.ja.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/moment/min/moment.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/knowledge-common.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/notice.js"></script>
<!-- endbuild -->
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>
    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%=jspUtil.label("knowledge.admin.notice.title")%>
        <span class="backlink">
        <a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
        </span>
        </h4>

        <div class="alert alert-info alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Information</strong><br /> -
            <%=jspUtil.label("knowledge.admin.notice.msg")%>
        </div>

        <nav>
            <ul class="pager">
                <li class="previous"><a class="previousButton"><span aria-hidden="true">&larr;</span> <%=jspUtil.label("label.previous")%></a></li>
                <li><a class="addButton" data-toggle="modal" data-target="#editModal"><i class="fa fa-plus-circle"></i>&nbsp; <%=jspUtil.label("label.add")%></a></li>
                <li class="next"><a class="nextButton"><%=jspUtil.label("label.next")%> <span aria-hidden="true">&rarr;</span></a></li>
            </ul>
        </nav>

        <div id="notices" class="list-group"></div>

        <nav>
            <ul class="pager">
                <li class="previous"><a class="previousButton"><span aria-hidden="true">&larr;</span> <%=jspUtil.label("label.previous")%></a></li>
                <li><a class="addButton" data-toggle="modal" data-target="#editModal"><i class="fa fa-plus-circle"></i>&nbsp; <%=jspUtil.label("label.add")%></a></li>
                <li class="next"><a class="nextButton"><%=jspUtil.label("label.next")%> <span aria-hidden="true">&rarr;</span></a></li>
            </ul>
        </nav>

        <form id="editForm">
            <!-- Modal -->
            <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="myModalLabel"><%=jspUtil.label("knowledge.admin.notice.dialog.title")%></h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-info hide" role="alert" id="validate_msg">
                            </div>
                            
                            <div class="form-group">
                                <input type="text" class="form-control"placeholder="Title" id="input_title">
                            </div>
                            
                            <div class="row">
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                        <input type="text" class="form-control datepicker" id="input_startdate" placeholder="Start Date(UTC)">
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                        <input type="text" class="form-control datepicker" id="input_enddate" placeholder="End Date(UTC)">
                                    </div>
                                </div>
                            </div>
                            
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#writeable" data-toggle="tab">
                                    <%= jspUtil.label("knowledge.add.label.content") %></a>
                                </li>
                                <li><a href="#preview" data-toggle="tab" id="tabPreview">
                                    <%= jspUtil.label("label.preview") %></a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="writeable">
                                    <div>
                                        <div class="form-group">
                                            <textarea class="form-control" rows="7" placeholder="Message" id="input_content"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane fade preview markdown" id="preview">
                                    <span style="display: none;" id="content_text">
                                    </span>
                                </div>
                            </div>
                            
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="saveButton">Save</button>
                            <button type="button" class="btn btn-danger" id="deleteButton">Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

    </c:param>

</c:import>

