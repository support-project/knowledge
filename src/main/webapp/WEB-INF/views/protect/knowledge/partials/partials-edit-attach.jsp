<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

    <div class="form-group">
        <div id="fileupload">
        <label for="input_fileupload"><%= jspUtil.label("knowledge.add.label.files") %></label>
            <span class="btn btn-primary fileinput-button btn-xs">
                <i class="fa fa-cloud-upload"></i>&nbsp;<span><%= jspUtil.label("knowledge.add.label.select.file") %></span>
                <input type="file" name="files[]" multiple>
            </span>
        </div>
    </div>
    <div class="form-group" id="drop_target">
        <%= jspUtil.label("knowledge.add.label.area.upload") %>
    </div>
    <div class="form-group" style="display: none;" id="progress">
        <div class="progress">
            <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
            0%
            </div>
        </div>
    </div>
    <div class="form-group" id="files">
    <c:forEach var="file" items="${files}" >
        <div class="filediv" id="file-<%= jspUtil.out("file.fileNo") %>">
            <div class="file-image"><img src="<%= jspUtil.out("file.thumbnailUrl") %>" /></div>
            <div class="file-label"><a href="<%= jspUtil.out("file.url") %>"><%= jspUtil.out("file.name") %></a></div>
            <br class="fileLabelBr"/>
            <input type="hidden" name="files" value="<%= jspUtil.out("file.fileNo") %>" />
            &nbsp;&nbsp;&nbsp;
            <% if (jspUtil.is("image", "file.type")) { %>
            <button type="button" class="btn btn-success" onclick="setImagePath('<%= jspUtil.out("file.url") %>', '<%= jspUtil.out("file.name") %>')">
                <i class="fa fa-file-image-o"></i>&nbsp;<%= jspUtil.label("knowledge.edit.set.image.path") %>
            </button>
            <% } else if (jspUtil.is("slide", "file.type")) { %>
            <button type="button" class="btn btn-success" onclick="setSlidePath('<%= jspUtil.out("file.fileNo") %>', '<%= jspUtil.out("file.name") %>')">
                <i class="fa fa-television"></i>&nbsp;<%= jspUtil.label("knowledge.edit.set.slide.path") %>
            </button>
            <% } %>
            <button type="button" class="btn btn-danger" onclick="removeAddedFile(<%= jspUtil.out("file.fileNo") %>)">
                <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
            </button>
        </div>
    </c:forEach>
    </div>



