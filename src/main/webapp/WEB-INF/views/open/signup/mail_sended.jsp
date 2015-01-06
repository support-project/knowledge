<%@page import="redcomet.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>


<c:param name="PARAM_CONTENT">
サービスへの招待メールを送信しました。<br/>
受信したメールから、ユーザ登録を完了するとログイン出来るようになります。<br/>

<hr/>
※注意：メーラーによっては、招待のメールが迷惑メールに入ってしまうことがあります。<br/>
迷惑メールとして届いていないか確認してください。

</c:param>

</c:import>

