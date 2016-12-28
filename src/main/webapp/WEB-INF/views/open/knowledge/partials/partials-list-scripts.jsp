<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>
<%-- 
<script type="text/x-mathjax-config">
MathJax.Hub.Config({
    tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]},
    skipStartupTypeset: true
});
</script>
<script type="text/javascript" src="bower/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML,Safe"></script>

<script type="text/javascript" src="bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="bower/emoji-parser/main.min.js"></script>
<script type="text/javascript" src="bower/moment/min/moment.min.js"></script>

<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/slide.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-common.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-list.js")%>"></script>
--%>

<!-- build:js(src/main/webapp) js/vendor-knowledge-list.js -->
<script type="text/javascript" src="bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="bower/echojs/dist/echo.min.js"></script>
<!-- endbuild -->



<script type="text/javascript" src="<%=jspUtil.mustReloadFile("/js/knowledge-list.js")%>"></script>

