$(document).ready(function(){
	marked.setOptions({
		langPrefix: '',
		highlight: function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	
	codeHighlight($('#content'))
	.then(function() {console.log('finish codeHighlight.'); return;});
	
	//console.log($('#content').html());
	var html = emoji($('#content').html(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
	$('#content').html(html);
	//console.log($('#content').html());
	
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
		var jqObj = $(this);
		jqObj.html(content);
		codeHighlight(jqObj)
		.then(function() {
			var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
			jqObj.html(content);
			return;
		});
	});
	$('.arrow_answer').each(function(i, block) {
		var content = $(this).html().trim();
		var jqObj = $(this);
		jqObj.html(content);
		codeHighlight(jqObj)
		.then(function() {
			var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
			jqObj.html(content);
			return;
		});
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
	
	var url = _CONTEXT + '/protect.file/upload';
	$('#fileupload').fileupload({
		url : url,
		dataType : 'json',
		autoUpload: true,
		maxFileSize: 5000000, // 5 MB
	}).on('fileuploaddone', function (e, data) {
		//$('#files').show();
		$.each(data.result.files, function(index, file) {
			console.log(file);
			var filediv = '<div class="filediv" id="file-' + file.fileNo + '">';
			filediv += '<div class="file-image">';
			filediv += '<img src="' + file.thumbnailUrl + '" />';
			filediv += '</div>';
			filediv += '<div class="file-label">';
			filediv += '<a href="' + file.url + '">';
			filediv += file.name;
			filediv += '</a>';
			filediv += '</div>';
			filediv += '<br class="fileLabelBr"/>';
			
			filediv += '<input type="hidden" name="files" value="' + file.fileNo + '" />';
			filediv += '&nbsp;&nbsp;&nbsp;';
			filediv += '<button type="button" class="btn btn-success" onclick="setImagePath(\'' + file.url + '\', \'' + file.name + '\')">';
			filediv += '<i class="fa fa-file-image-o"></i>&nbsp;' + _SET_IMAGE_LABEL;
			filediv += '</button>';
			filediv += '<button type="button" class="btn btn-danger" onclick="removeAddedFile(' + file.fileNo + ')">';
			filediv += '<i class="fa fa-remove"></i>';
			filediv += '&nbsp;' + _DELETE_LABEL + '</button>';
			filediv += '</div>';
			$('#files').append(filediv);
		});
		$.notify(_UPLOADED, 'success');
		setTimeout(function() {
			$('#progress').hide();
		}, 5000);
		
	 }).on('fileuploadprogressall', function (e, data) {
			$('#progress').show();
			var progress = parseInt(data.loaded / data.total * 100, 10);
			console.log('' + progress + '%');
			$('.progress .progress-bar').css('width', progress + '%');
			$('.progress .progress-bar').text(progress + '%');
			
	 }).on('fileuploadfail', function (e, data) {
		if (data && data.jqXHR && data.jqXHR.responseJSON && data.jqXHR.responseJSON.msg) {
			var msg = data.jqXHR.responseJSON.msg;
			$.each(data.files, function (index) {
				var filediv = '<div class="alert alert-warning alert-dismissible" role="alert">';
				filediv += '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
				filediv += '<strong>';
				filediv += '<i class="fa fa-remove"></i>&nbsp;';
				filediv += data.files[index].name;
				filediv += '</strong>&nbsp;&nbsp;';
				filediv += msg;
				filediv += '</div>';
				$('#files').append(filediv);
				$.notify(_FAIL_UPLOAD, 'warn');
			});
		} else {
			console.log(e);
			console.log(data);
			$.notify('アップロードに失敗しました', 'warn');
		}
	}).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');	
	
	changeTemplate();
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
		
		var jqObj = $('#preview');
		jqObj.html(html);
		codeHighlight(jqObj)
		.then(function() {
			var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
			jqObj.html(content);
			return;
		});
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
		
		var jqObj = $('#preview');
		jqObj.html(html);
		codeHighlight(jqObj)
		.then(function() {
			var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
			jqObj.html(content);
			return;
		});
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


var removeAddedFile = function(fileNo) {
	var url = _CONTEXT + '/protect.file/delete';
	$.ajax({
		type : 'GET',
		url : url,
		data : 'fileNo=' + fileNo,
		success : function(data, dataType) {
			$.notify(_REMOVE_FILE, 'info');
			$('#file-' + fileNo).remove();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			//alert("error: " + textStatus);
			$.notify(_FAIL_REMOVE_FILE, 'warn');
		}
	});
};

var setImagePath = function(url, name) {
	var text = '\n![' + name + '](' + url + ')\n';
	var textarea = $('#comment');
	textarea.val(textarea.val() + text);
}




var changeTemplate = function() {
	var typeId = $('#typeId').val();
	var url = _CONTEXT + '/open.knowledge/template';
	var knowledgeId = null;
	if ($('#knowledgeId')) {
		knowledgeId = $('#knowledgeId').val();
	}
	$.ajax({
		type : 'GET',
		url : url,
		data : 'type_id=' + typeId + '&knowledge_id=' + knowledgeId,
		success : function(data, dataType) {
			console.log(data);
			addTemplateItem(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$.notify('[fail] get template info', 'warn');
		}
	});
};

var addTemplateItem = function(template) {
	var templateTag = '<h5><i class="fa ' + template.typeIcon + '"></i>&nbsp;' + template.typeName + '</h5>';
	$('#template').html(templateTag);
	
	if (template.items && template.items.length > 0) {
		for (var i = 0; i < template.items.length; i++) {
			var item = template.items[i];
			console.log(item);
			var tag = '';
			if (i > 0) {
				tag += '<br/>';
			}
			tag += item.itemName + ': ';
			
			// Bookmrkの場合は、項目はURLのみ
			if (template.typeId == -99) {
				var url = '';
				if (item.itemValue) {
					url = item.itemValue;
				}
				tag += '<a href="' + url + '" target="_blank" >' + url + '</a>';
			} else {
				if (item.itemType === 1) {
					// textarea
					tag += item.itemValue;
				} else if (item.itemType === 10) {
					// Radio
					if (item.choices) {
						tag += '<br/>';
						for (var j = 0; j < item.choices.length; j++) {
							var choice = item.choices[j];
							tag += '<label class="radio-inline"><input type="radio" class="" name="item_' + item.itemNo;
							tag += '" value="' + choice.choiceValue + '" ';
							if (choice.choiceValue == item.itemValue) {
								tag += 'checked="checked" ';
							}
							tag += ' disable="disable" /> &nbsp;' + choice.choiceLabel + '</label><br/>';
						}
					}
				} else if (item.itemType === 11) {
					// Checkbox
					if (item.choices) {
						tag += '<br/>';
						for (var j = 0; j < item.choices.length; j++) {
							var choice = item.choices[j];
							tag += '<label class="checkbox-inline"><input type="checkbox" class="" name="item_' + item.itemNo;
							tag += '" value="' + choice.choiceValue + '" ';
							if (item.itemValue) {
								var vals = item.itemValue.split(',');
								for (var k = 0; k < vals.length; k++) {
									if (choice.choiceValue == vals[k].trim()) {
										tag += 'checked="checked" ';
										break;
									}
								}
							}
							tag += ' disable="disable" /> &nbsp;' + choice.choiceLabel + '</label><br/>';
						}
					}
				} else {
					// text
					tag += '<br/>';
					tag += item.itemValue;
				}
			}
			
			$('#template_items').append(tag);
			$('#template_items_area').show();
		}
	}
};


