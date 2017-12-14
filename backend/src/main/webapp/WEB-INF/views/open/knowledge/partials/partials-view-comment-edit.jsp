<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

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
                        <a href="<%= jspUtil.out("file.url") %>&amp;attachment=true"><%= jspUtil.out("file.name") %></a>
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
        <%  } else { %>
        <button type="button" class="btn btn-info" onclick="preview();">
            <i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
        <%  } %>

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

