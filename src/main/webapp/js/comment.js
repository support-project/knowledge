$(document).ready(function() {
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
	
	$('#sampleMarkdownCheck').click(function() {
		var text = $('#sampleMarkdownText').val();
		var textarea = $('#comment');
		textarea.val(text);
		preview();
		$('#helpMarkdownModal').modal('hide');
		var p = $("#preview").offset().top - 60;
		$('html,body').animate({ scrollTop: p }, 'fast');
	});
	
});

var emoji = window.emojiParser;
var preview = function() {
	$.post(_CONTEXT + '/open.knowledge/marked', {
		title : $('#input_title').val(),
		content : $('#comment').val()
	}, function(data) {
		var html = '<div class="row">';
		html += '<div class="col-sm-12">';
		html += '<div class="thumbnail">';
		html += '<div class="caption">';
		html += '[preview]';
		html += '<h3>';
		html += data.title;
		html += '</h3><hr/>';
		html += '<p style="word-break:break-all" id="content">';
		var content = data.content;
		html += content;
		html += '</p>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		$('#preview').html(html);
		$('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
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

function deleteComment() {
	bootbox.confirm(_CONFIRM, function(result) {
		if (result) {
			$('#commentForm').attr('action', _CONTEXT + '/protect.knowledge/delete_comment/' + $('#commentNo').val());
			$('#commentForm').attr('method', 'GET');
			$('#commentForm').submit();
		}
	}); 
};



