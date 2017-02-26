<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- build:js(src/main/webapp) js/page-knowledge-list.js -->
<script type="text/javascript" src="bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="bower/jstzdetect/jstz.min.js"></script>
<script type="text/javascript" src="bower/moment/moment.js"></script>
<script type="text/javascript" src="bower/moment-timezone/builds/moment-timezone-with-data.min.js"></script>
<script type="text/javascript" src="bower/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.en-GB.min.js"></script>
<script type="text/javascript" src="bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.ja.min.js"></script>

<script type="text/javascript" src="js/knowledge-list.js"></script>
<script type="text/javascript" src="js/knowledge-common.js"></script>
<script type="text/javascript" src="js/knowledge-view-markdown.js"></script>
<script type="text/javascript" src="js/knowledge-view-emoji-select.js"></script>
<script type="text/javascript" src="js/event.js"></script>
<!-- endbuild -->

<script>
var _LANG = '<%= jspUtil.locale().getLanguage()%>';
</script>