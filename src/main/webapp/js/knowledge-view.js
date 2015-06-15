$(document).ready(function(){
	hljs.initHighlightingOnLoad();
	marked.setOptions({
		langPrefix: '',
		highlight: function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	var emoji = window.emojiParser;
	$('#content').html(marked($('#content_text').text()));
	$('#content pre code').each(function(i, block) {
		hljs.highlightBlock(block);
	});
	var content = emoji($('#content').html(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
	$('#content').html(content);
	
	echo.init();
	
	$('#commentsLink').click(function(){
		var speed = 500;
		var href= $(this).attr("href");
		//var target = $(href == "#" || href == "" ? 'html' : href);
		var target = $(href);
		var position = target.offset().top;
		$("html, body").animate({scrollTop:position}, speed, "swing");
		return false;
	});
	
	$('#input_tags').on('beforeItemRemove', function(event) {
		event.cancel = true;
	});
	
	$('.arrow_question').each(function(i, block) {
		var content = $(this).text().trim();
		content = marked(content);
		$(this).html(content);
		$(this).find('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
		var content = emoji($(this).html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
		console.log(content);
		$(this).html(content);
	});
	$('.arrow_answer').each(function(i, block) {
		var content = $(this).text().trim();
		content = marked(content);
		$(this).html(content);
		$(this).find('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
		var content = emoji($(this).html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
		console.log(content);
		$(this).html(content);
	});
	
});




var addlike = function(knowledgeId) {
	var url = _CONTEXT + '/open.knowledge/like/' + knowledgeId;
	$.ajax({
		type : 'GET',
		url : url,
		success : function(data, dataType) {
			console.log(data);
			$('#like_count').text(data.count);
			$.notify(LABEL_LIKE, 'info');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.log(textStatus);
		}
	});
};



var viewProtect = function(knowledgeId) {
	var url = _CONTEXT + '/protect.knowledge/view_targets/' + knowledgeId;
	$.ajax({
		type : 'GET',
		url : url,
		success : function(datas, dataType) {
			var targets = '';
			for ( var i = 0; i < datas.length; i++) {
				var data = datas[i];
//				console.log(data);
				targets += data.label;
			}
			bootbox.alert(targets);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.log(textStatus);
		}
	});
};


var preview = function() {
	$.post(_CONTEXT + '/open.knowledge/escape', {
		title : '',
		content : $('#comment').val()
	}, function(data) {
		$('#comment_text').html(data.content);
		var html = '<div class="question_Box">';
		html += '<div class="question_image">';
		html += '<img src="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
//		html += '<img src="' + _CONTEXT + '/images/loader.gif" ';
//		html += 'data-echo="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
		html += 'alt="icon" width="64" height="64"/>';
		html += '</div>';
		html += '<div class="arrow_question">';
		
		html += '<p style="word-break:break-all" id="content">';
		var content = marked($('#comment_text').html());
		html += content;
		
		html += '</div><!-- /.arrow_question -->';
		html += '</div><!-- /.question_Box -->';
		
		$('#preview').html(html);
		$('#preview').find('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
		var emoji = window.emojiParser;
		var content = emoji($('#preview').html(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
		$('#preview').html(content);
	});
};


var previewans = function() {
	$.post(_CONTEXT + '/open.knowledge/escape', {
		title : '',
		content : $('#comment').val()
	}, function(data) {
		$('#comment_text').html(data.content);
		var html = '<div class="question_Box">';
		html += '<div class="answer_image">';
		html += '<img src="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
		html += 'alt="icon" width="64" height="64"/>';
		html += '</div>';
		html += '<div class="arrow_answer">';
		
		html += '<p style="word-break:break-all" id="content">';
		var content = marked($('#comment_text').html());
		html += content;
		
		html += '</div>';
		html += '</div>';
		
		$('#preview').html(html);
		$('#preview').find('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
		var emoji = window.emojiParser;
		var content = emoji($('#preview').html(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
		$('#preview').html(content);
	});
};


