<%@page import="java.util.List"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.entity.KnowledgesEntity"%>
<%@page import="org.support.project.knowledge.entity.CommentsEntity"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload-ui.css" />

<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-edit.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-view.css") %>" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>

<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-common.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view.js") %>"></script>


<script>
var LABEL_LIKE = '<%= jspUtil.label("knowledge.view.like") %>';

var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
var _SET_IMAGE_LABEL= '<%= jspUtil.label("knowledge.edit.set.image.path") %>';

</script>

</c:param>

<c:param name="PARAM_PAGE_TITLE">
<%= jspUtil.out("title", JspUtil.ESCAPE_CLEAR) %> - Knowledge
</c:param>


<c:param name="PARAM_CONTENT">
    <div class="row" id="content_head">
        <div class="col-sm-8">
            <h4 class="title"><%=jspUtil.out("title", JspUtil.ESCAPE_CLEAR)%></h4>
            <div style="margin-top: 10px;">
                <span id="template"></span>
            </div>
            <%-- 更新者情報 --%>
            <div class="insert_info">
                <img src="<%=request.getContextPath()%>/images/loader.gif"
                    data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("updateUser")%>" alt="icon" width="24"
                    height="24" style="float: left" />
                <%
                    String userLink = "<a href=\"" + request.getContextPath() + "/open.account/info/" + jspUtil.out("updateUser") + "\">"
                                    + jspUtil.out("updateUserName", JspUtil.ESCAPE_CLEAR) + "</a>";
                %>
                <%
                    String historyLink = "<a href=\"" + request.getContextPath() + "/open.knowledge/histories/" + jspUtil.out("knowledgeId") + "\">"
                                    + jspUtil.date("updateDatetime") + "</a>";
                %>
                <%=jspUtil.label("knowledge.view.info.update", userLink, historyLink)%>
            </div>

            <%-- タグ --%>
            <c:if test="${!empty tagNames}">
                <div class="tag_list">
                    <i class="fa fa-tags"></i>&nbsp;
                    <c:forEach var="tagName" items="${tagNames.split(',')}">
                        <a href="<%=request.getContextPath()%>/open.knowledge/list?tagNames=<%=jspUtil.out("tagName")%>"> <span
                            class="tag label label-info"><i class="fa fa-tag"></i><%=jspUtil.out("tagName")%></span>
                        </a>
                    </c:forEach>
                </div>
            </c:if>

            <%-- ストックに入れているか --%>
            <c:if test="${!empty stocks}">
                <div class="tag_list">
                    <i class="fa fa-star-o"></i>
                    <c:forEach var="stock" items="${stocks}" varStatus="status">
                        <a href="<%=request.getContextPath()%>/open.knowledge/stocks?stockid=<%=jspUtil.out("stock.stockId")%>"> <span
                            class="tag label label-primary"> <i class="fa fa-star"></i><%=jspUtil.out("stock.stockName")%></span>
                        </a>&nbsp;
                </c:forEach>
                </div>
            </c:if>


            <%--概要；記事のステータスが公開時に「公開」とは表示しないようにする
            <%= jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag",
                jspUtil.label("label.public.view")) %>
            --%>
            <%
                if (jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag")) {
            %>
            <div class="tag_list"><%=jspUtil.label("label.private.view")%></div>
            <%
                }
            %>
            <%
                if (jspUtil.is(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "publicFlag")) {
            %>
            <div class="tag_list"><%=jspUtil.label("label.protect.view")%>
                <c:if test="${targets.containsKey(knowledgeId)}">
                    <c:forEach var="target" items="${targets.get(knowledgeId)}">
                        <c:choose>
                            <c:when test="${targetLogic.isGroupLabel(target.value)}">
                                <c:set var="groupId" value="${targetLogic.getGroupId(target.value)}" />
                                <a href="<%=request.getContextPath()%>/open.knowledge/list?group=<%=jspUtil.out("groupId")%>"> <span
                                    class="tag label label-success"><i class="fa fa-users"></i><%=jspUtil.out("target.label")%></span>
                                </a>
                            </c:when>
                            <c:when test="${targetLogic.isUserLabel(target.value)}">
                                <c:set var="userId" value="${targetLogic.getUserId(target.value)}" />
                                <a href="<%=request.getContextPath()%>/open.knowledge/list?user=<%=jspUtil.out("userId")%>"> <span
                                    class="tag label label-success"><i class="fa fa-user"></i><%=jspUtil.out("target.label")%></span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="#"> <span class="tag label label-success"><%=jspUtil.out("target.label")%></span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    &nbsp;
                </c:if>
            </div>
            <%
                }
            %>


            <%-- 添付ファイル --%>
            <c:forEach var="file" items="${files}">
                <c:if test="${file.commentNo == 0}">
                    <div class="downloadfile">
                        <img src="<%=jspUtil.out("file.thumbnailUrl")%>" /> <a href="<%=jspUtil.out("file.url")%>"> <%=jspUtil.out("file.name")%>
                        </a>
                    </div>
                </c:if>
            </c:forEach>

        </div>


        <%-- 公開区分やイイネ件数など --%>
        <div class="col-sm-4">
            <div class="article_buttons">
                <div>
                    <%
                        if (request.getRemoteUser() != null) {
                                    if ((Boolean) request.getAttribute("edit")) {
                    %>
                    <a href="<%=request.getContextPath()%>/protect.knowledge/view_edit/<%=jspUtil.out("knowledgeId")%>"
                        class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp; <%=jspUtil.label("knowledge.view.edit")%>
                    </a>
                    <%
                        } else {
                    %>
                    <button class="btn btn-primary btn_edit disabled" disabled="disabled">
                        <i class="fa fa-info-circle"></i>&nbsp;<%=jspUtil.label("knowledge.view.edit.disable")%>
                    </button>
                    <%
                        }
                    %>
                    <%
                        } else {
                    %>
                    <a href="<%=request.getContextPath()%>/protect.knowledge/view_edit/<%=jspUtil.out("knowledgeId")%>"
                        class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp; <%=jspUtil.label("knowledge.view.edit.with.login")%>
                    </a>
                    <%
                        }
                    %>
                </div>

                <div>
                    <%
                        if (request.getRemoteUser() != null) {
                    %>
                    <button type="button" class="btn btn-info btn_stock" data-toggle="modal" data-target="#stockModal">
                        <i class="fa fa-star-o"></i>&nbsp;
                        <%=jspUtil.label("knowledge.view.fav")%>
                    </button>
                    <%
                        } else {
                    %>
                    <a href="<%=request.getContextPath()%>/protect.knowledge/view/<%=jspUtil.out("knowledgeId")%>"
                        class="btn btn-info btn_stock" role="button"> <i class="fa fa-star-o"></i>&nbsp; <%=jspUtil.label("knowledge.view.fav")%><%--(<%= jspUtil.label("knowledge.navbar.signin") %>) --%>
                    </a>
                    <%
                        }
                    %>
                    <button class="btn btn-warning btn_like" onclick="addlike(<%=jspUtil.out("knowledgeId")%>);">
                        <i class="fa fa-thumbs-o-up"></i>&nbsp;
                        <%=jspUtil.label("knowledge.view.like")%>
                    </button>
                </div>
            </div>

            <div class="article_info">
                <a href="<%=request.getContextPath()%>/open.knowledge/likes/<%=jspUtil.out("knowledgeId")%><%=jspUtil.out("params")%>">
                    <i class="fa fa-thumbs-o-up"></i>&nbsp;<%=jspUtil.label("knowledge.view.like")%> × <span id="like_count"><%=jspUtil.out("like_count")%></span>
                </a> <a href="#comments" id="commentsLink"> <i class="fa fa-comments-o"></i>&nbsp;<%=jspUtil.label("knowledge.view.comment.label")%>
                    × <%=jspUtil.out("comments.size()")%>
                </a>
            </div>
        </div>
    </div>

    <%-- ナレッジ表示 --%>
    <div class="row" id="content_main">
        <div class="col-sm-12">
            <input type="hidden" id="knowledgeId" value="<%=jspUtil.out("knowledgeId")%>" /> <input type="hidden" id="typeId"
                value="<%=jspUtil.out("typeId")%>" />
            <%-- テンプレートの項目 --%>
            <div style="word-break: break-all; display: none;" id="template_items_area" class="markdown">
                <span id="template_items"></span>
            </div>
            <%-- ナレッジコンテンツ --%>
            <div style="word-break: break-all;" id="content" class="markdown viewarea">
                <%=jspUtil.out("content", JspUtil.ESCAPE_NONE)%>
            </div>

        </div>
    </div>



    <%-- コメント表示 --%>
    <%
        List commentList = jspUtil.getValue("comments", List.class);
                if (commentList != null && !commentList.isEmpty()) {
    %>
    <hr />
    <h5 id="comments">
        <i class="fa fa-comments-o"></i>&nbsp;<%=jspUtil.label("knowledge.view.comment.label")%></h5>
    <c:forEach var="comment" items="${comments}" varStatus="status">
        <%
            CommentsEntity comment = jspUtil.getValue("comment", CommentsEntity.class);
                            Integer knowledge = jspUtil.getValue("insertUser", Integer.class);
                            if (!comment.getInsertUser().equals(knowledge)) {
        %>
        <div class="row">
            <div class="col-sm-12">
                [<%=jspUtil.label("label.registration")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.insertUserName")%>]
                <%
                if (comment.isUpdate()) {
            %>
                <br />[<%=jspUtil.label("label.update")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.updateUserName")%>]
                <%
                    }
                %>

                <%
                    if (jspUtil.isAdmin() || jspUtil.is(jspUtil.id(), "comment.insertUser") || (Boolean) request.getAttribute("edit")) {
                %>
                &nbsp; <a class="btn btn-primary btn-xs"
                    href="<%=request.getContextPath()%>/protect.knowledge/edit_comment/<%=comment.getCommentNo()%>"> <i class="fa fa-edit"></i>
                    <%=jspUtil.label("label.edit")%>
                </a> &nbsp;
                <button class="btn btn-info btn-xs <%if (comment.getCommentStatus() == 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 1);" id="collapse_on_<%=comment.getCommentNo()%>">
                    <i class="fa fa-minus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.collapse")%>
                </button>
                &nbsp;
                <button class="btn btn-warning btn-xs <%if (comment.getCommentStatus() != 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 0);" id="collapse_off_<%=comment.getCommentNo()%>">
                    <i class="fa fa-plus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.open")%>
                </button>
                <%
                    }
                %>

            </div>
        </div>
        <div class="question_Box <%if (comment.getCommentStatus() == 1) {%>hide<%}%>" id="comment_<%=comment.getCommentNo()%>">
            <div class="question_image">
                <img src="<%=request.getContextPath()%>/images/loader.gif"
                    data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("comment.insertUser")%>" alt="icon" width="64"
                    height="64" />
            </div>
            <div class="arrow_question markdown">
                <%=jspUtil.out("comment.comment", JspUtil.ESCAPE_NONE)%>
                <!-- コメントに付けた添付ファイルの表示 -->
                <c:forEach var="file" items="${files}">
                    <c:if test="${file.commentNo == comment.commentNo}">
                        <div class="downloadfile">
                            <img src="<%=jspUtil.out("file.thumbnailUrl")%>" /> <a href="<%=jspUtil.out("file.url")%>"> <%=jspUtil.out("file.name")%>
                            </a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <!-- /.arrow_question -->
        </div>
        <!-- /.question_Box -->

        <div class="<%if (comment.getCommentStatus() != 1) {%>hide<%}%> text-left collapse_comment"
            id="comment_collapse_<%=comment.getCommentNo()%>">
            <%=jspUtil.label("knowledge.view.comment.collapsed")%>
            <br />
        </div>

        <%
            } else {
        %>
        <div class="row">
            <div class="col-sm-12" style="text-align: right;">

                <%
                    if (jspUtil.isAdmin() || jspUtil.is(jspUtil.id(), "comment.insertUser") || (Boolean) request.getAttribute("edit")) {
                %>
                &nbsp; <a class="btn btn-primary btn-xs"
                    href="<%=request.getContextPath()%>/protect.knowledge/edit_comment/<%=comment.getCommentNo()%>"> <i class="fa fa-edit"></i>
                    <%=jspUtil.label("label.edit")%>
                </a> &nbsp;
                <button class="btn btn-info btn-xs <%if (comment.getCommentStatus() == 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 1);" id="collapse_on_<%=comment.getCommentNo()%>">
                    <i class="fa fa-minus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.collapse")%>
                </button>
                &nbsp;
                <button class="btn btn-warning btn-xs <%if (comment.getCommentStatus() != 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 0);" id="collapse_off_<%=comment.getCommentNo()%>">
                    <i class="fa fa-plus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.open")%>
                </button>
                <%
                    }
                %>

                [<%=jspUtil.label("label.registration")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.insertUserName")%>]
                <%
                    if (comment.isUpdate()) {
                %>
                <br />[<%=jspUtil.label("label.update")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.updateUserName")%>]
                <%
                    }
                %>

            </div>
        </div>
        <div class="question_Box <%if (comment.getCommentStatus() == 1) {%>hide<%}%>" id="comment_<%=comment.getCommentNo()%>">
            <div class="answer_image">
                <img src="<%=request.getContextPath()%>/images/loader.gif"
                    data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("comment.insertUser")%>" alt="icon" width="64"
                    height="64" />
            </div>
            <div class="arrow_answer markdown">
                <%=jspUtil.out("comment.comment", JspUtil.ESCAPE_NONE)%>
                <!-- コメントに付けた添付ファイルの表示 -->
                <c:forEach var="file" items="${files}">
                    <c:if test="${file.commentNo == comment.commentNo}">
                        <div class="downloadfile">
                            <img src="<%=jspUtil.out("file.thumbnailUrl")%>" /> <a href="<%=jspUtil.out("file.url")%>"> <%=jspUtil.out("file.name")%>
                            </a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <!-- /.arrow_answer -->
        </div>
        <!-- /.question_Box -->

        <div class="<%if (comment.getCommentStatus() != 1) {%>hide<%}%> text-right collapse_comment"
            id="comment_collapse_<%=comment.getCommentNo()%>">
            <%=jspUtil.label("knowledge.view.comment.collapsed")%>
            <br />
        </div>
        <%
            }
        %>
    </c:forEach>
    <br />
    <br />
    <%
        }
    %>


    <%-- コメント登録 --%>
    <hr />
    <h4 class="sub_title">
        <i class="fa fa-comment-o"></i>&nbsp;<%=jspUtil.label("knowledge.comment.add")%></h4>
    <%
        if (request.getRemoteUser() != null) {
    %>
    <form action="<%=request.getContextPath()%>/protect.knowledge/comment/<%=jspUtil.out("knowledgeId")%><%=jspUtil.out("params")%>"
        method="post" role="form" enctype="multipart/form-data">
        <textarea class="form-control" name="addcomment" rows="4" placeholder="Comment" id="comment"><%=jspUtil.out("addcomment")%></textarea>
        <a data-toggle="modal" href="<%=request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a> <a
            data-toggle="modal" href="<%=request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a> <a
            data-toggle="modal" href="<%=request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a> <a
            data-toggle="modal" href="<%=request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a> <a
            data-toggle="modal" href="<%=request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a> <br />


        <div class="form-group">
            <label for="input_fileupload"><%=jspUtil.label("knowledge.add.label.files")%></label><br />
            <div id="fileupload">
                <span class="btn btn-info fileinput-button"> <i class="fa fa-cloud-upload"></i>&nbsp;<span><%=jspUtil.label("knowledge.add.label.select.file")%></span>
                    <input type="file" name="files[]" multiple>
                </span>
            </div>
        </div>
        <div class="form-group" id="drop_target">
            <%=jspUtil.label("knowledge.add.label.area.upload")%>
        </div>
        <div class="form-group" style="display: none;" id="progress">
            <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">0%</div>
            </div>
        </div>
        <div class="form-group" id="files">
            <c:forEach var="file" items="${comment_files}">
                <div class="filediv" id="file-<%= jspUtil.out("file.fileNo") %>">
                    <div class="file-image">
                        <img src="<%= jspUtil.out("file.thumbnailUrl") %>" />
                    </div>
                    <div class="file-label">
                        <a href="<%= jspUtil.out("file.url") %>"><%= jspUtil.out("file.name") %></a>
                    </div>
                    <br class="fileLabelBr" /> <input type="hidden" name="files" value="<%= jspUtil.out("file.fileNo") %>" /> &nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-success"
                        onclick="setImagePath('<%= jspUtil.out("file.url") %>', '<%= jspUtil.out("file.name") %>')">
                        <i class="fa fa-file-image-o"></i>&nbsp;<%= jspUtil.label("knowledge.edit.set.image.path") %>
                    </button>
                    <button type="button" class="btn btn-danger" onclick="removeAddedFile(<%= jspUtil.out("file.fileNo") %>)">
                        <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
                    </button>
                </div>
            </c:forEach>
        </div>

        <% if (jspUtil.out("insertUser").equals(request.getRemoteUser())) { %>
        <button type="button" class="btn btn-info" onclick="previewans();">
            <i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
        <%	} else { %>
        <button type="button" class="btn btn-info" onclick="preview();">
            <i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
        <%	} %>

        <button type="submit" class="btn btn-primary">
            <i class="fa fa-comment-o"></i>&nbsp;<%= jspUtil.label("knowledge.view.comment") %></button>

        <input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" /> <input type="hidden" name="keyword"
            value="<%= jspUtil.out("keyword") %>" /> <input type="hidden" name="tag" value="<%= jspUtil.out("tag") %>" /> <input type="hidden"
            name="user" value="<%= jspUtil.out("user") %>" /> <input type="hidden" name="loginuser" value="<%= request.getRemoteUser() %>"
            id="loginuser" />

    </form>
    <% } else { %>
    <form action="<%= request.getContextPath()%>/protect.knowledge/view/<%= jspUtil.out("knowledgeId") %>" method="get" role="form">
        <button type="submit" class="btn btn-primary">
            <i class="fa fa-comment-o"></i>&nbsp;<%= jspUtil.label("knowledge.view.comment.with.login") %></button>
    </form>
    <% } %>


    <p class="preview markdown" id="preview"></p>
    <span style="display: none;" id="comment_text"> </span>






    <!-- Stock Modal -->
    <div class="modal fade" id="stockModal" tabindex="-1" role="dialog" aria-labelledby="stockModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="stockModalLabel"><%= jspUtil.label("knowledge.view.stock.modal.title") %></h4>
                </div>
                <div class="modal-body">
                    <%-- コンテンツ --%>
                    <nav>
                        <ul class="pager">
                            <li class="previous"><a href="#" onclick="getStockInfoPrevious();"> <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                            </a></li>
                            <li class="next"><a href="#" onclick="getStockInfoNext();"> <%= jspUtil.label("label.next") %> <span
                                    aria-hidden="true">&rarr;</span>
                            </a></li>
                        </ul>
                    </nav>
                    Page: <span id="stockPage"></span>

                    <div id="stockSelect"></div>
                    <span id="stockLink" style="display: none;"> <%= jspUtil.label("knowledge.stock.empty") %><br /> <a
                        href="<%= request.getContextPath() %>/protect.stock/mylist"><%= jspUtil.label("knowledge.stock.label.link") %></a>
                    </span>
                </div>
                <div class="modal-footer">
                    <div class="form-group">
                        <input type="text" class="form-control" name="stockComment" id="stockComment"
                            placeholder="<%= jspUtil.label("label.comment") %>" value="">
                    </div>

                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %></button>
                    <button type="button" class="btn btn-primary" id="saveStockButton" onclick="saveStocks(<%= jspUtil.out("knowledgeId") %>);">
                        <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
                </div>
            </div>
        </div>
    </div>


    <jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>


</c:param>

</c:import>

