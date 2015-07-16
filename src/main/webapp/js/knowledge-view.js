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
	$('#content').find('pre code').each(function(i, block) {
		hljs.highlightBlock(block);
	});
	
	console.log($('#content').html());
	var html = emoji($('#content').html(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
	$('#content').html(html);
	console.log($('#content').html());
	
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
		var content = $(this).html().trim();
		//content = marked(content);
		$(this).html(content);
		$(this).find('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
		var content = emoji($(this).html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
		console.log(content);
		$(this).html(content);
	});
	$('.arrow_answer').each(function(i, block) {
		var content = $(this).html().trim();
		//content = marked(content);
		$(this).html(content);
		$(this).find('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
		var content = emoji($(this).html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
		console.log(content);
		$(this).html(content);
	});
	
	$('#emojiPeopleModal').on('loaded.bs.modal', function (event) {
		emojiSelect('#emojiPeopleModal');
	});
	$('#emojiNatureModal').on('loaded.bs.modal', function (event) {
		emojiSelect('#emojiNatureModal');
	});
	$('#emojiObjectsModal').on('loaded.bs.modal', function (event) {
		emojiSelect('#emojiObjectsModal');
	});
	$('#emojiPlacesModal').on('loaded.bs.modal', function (event) {
		emojiSelect('#emojiPlacesModal');
	});
	$('#emojiSymbolsModal').on('loaded.bs.modal', function (event) {
		emojiSelect('#emojiSymbolsModal');
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
	$.post(_CONTEXT + '/open.knowledge/marked', {
		title : '',
		content : $('#comment').val()
	}, function(data) {
		var html = '<div class="question_Box">';
		html += '<div class="question_image">';
		html += '<img src="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
//		html += '<img src="' + _CONTEXT + '/images/loader.gif" ';
//		html += 'data-echo="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
		html += 'alt="icon" width="64" height="64"/>';
		html += '</div>';
		html += '<div class="arrow_question">';
		
		html += '<p style="word-break:break-all" id="content">';
		var content = data.content;
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
	$.post(_CONTEXT + '/open.knowledge/marked', {
		title : '',
		content : $('#comment').val()
	}, function(data) {
		var html = '<div class="question_Box">';
		html += '<div class="answer_image">';
		html += '<img src="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
		html += 'alt="icon" width="64" height="64"/>';
		html += '</div>';
		html += '<div class="arrow_answer">';
		
		html += '<p style="word-break:break-all" id="content">';
		var content = data.content;
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

var emojiSelect = function(id) {
	$(id).find('.name').each(function(i, block) {
		$(this).click(function(event) {
			var val = ' :' + $(this).text() + ': ';
			var textarea = $('#comment');
			textarea.val(textarea.val() + val);
			$(id).modal('hide');
		});
	});
};

