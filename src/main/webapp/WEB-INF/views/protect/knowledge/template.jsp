<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
	errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- ユーザー名 -->
<input type="hidden" id="userName" value="<%= jspUtil.name() %>">
<!-- 手順書 -->
<input type="text" id="Title1" value="手順書/title" style="display:none;">
<textarea id="Template1" rows="8" class="form-control" style="display:none;">
# 前提

# 手順

# 注意点
</textarea>
<!-- 日報 -->
<input type="text" id="Title2" value="日報/%{Year}/%{month}/%{day}/%{user}/title" style="display:none;">
<textarea id="Template2" rows="8" class="form-control" style="display:none;">
# 本日の作業内容

- [ ] Task
- [ ] Task
- [x] Task

# 発生した問題

# 明日の作業予定

# 所感
</textarea>
<!-- 議事録 -->
<input type="text" id="Title3" value="議事録/%{Year}/%{month}/%{day}/会議名" style="display:none;">
<textarea id="Template3" rows="8" class="form-control" style="display:none;">
- 日時: 
- 場所: 
- 参加者: 

# アジェンダ

# 決定事項

# 共有事項
</textarea>

	<div class="form-group">
		<label for="select_template">テンプレート</label>
		<select id="select_template" class="selectpicker" title='Select Template'>
			<option value="1">手順書</option>
			<option value="2">日報</option>
			<option value="3">議事録</option>
		</select>
	</div>
