var groups = [];
var selectedGroups = [];
var editors = [];
var selectedEditors = [];

$(document).ready(function() {
	marked.setOptions({
		langPrefix : '',
		highlight : function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
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
	
	var elt= $('#input_tags');
	elt.tagsinput({
		typeahead: {
			source: _TAGS,
			displayText: function(item) {
				if (item) {
					return item.name || item;
				}
				return '';
			}
		},
		freeInput: true
	});
	elt.on('typeahead:selected', function(event, datum) {
		console.log(datum);
	});
	/*
	$('#input_tags').on('itemAdded', function(event) {
		console.log(event);
		$('#input_tags').tagsinput('refresh');
	});
	*/
	
	dispChangeGroupArea($('input[name="publicFlag"]:checked').val());
	$('input[name="publicFlag"]:radio').change( function() {
		dispChangeGroupArea($( this ).val());
	});
	
	// targets
	var keyword = $('#groupKeyword').val();
	var offset = 0;
	$('#groupSelectModal').on('show.bs.modal', function (event) {
		getGroups(keyword, offset);
	});
	$('#groupSearchButton').click(function() {
		keyword = $('#groupKeyword').val();
		offset = 0;
		getGroups(keyword, offset);
	});
	$('#groupSearchPrevious').click(function() {
		keyword = $('#groupKeyword').val();
		offset--;
		if (offset < 0){
			offset = 0;
		}
		getGroups(keyword, offset);
	});
	$('#groupSearchNext').click(function() {
		keyword = $('#groupKeyword').val();
		offset++;
		getGroups(keyword, offset);
	});
	$('#groupDecision').click(function() {
		$('#groupSelectModal').modal('hide')
	});
	$('#clearSelectedGroup').click(function() {
		selectedGroups = [];
		viewGroup();
	});
	viewGroup();
	
	// editor
	var editorKeyword = $('#editorKeyword').val();
	var offset = 0;
	$('#editorSelectModal').on('show.bs.modal', function (event) {
		getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
	});
	$('#editorSearchButton').click(function() {
		keyword = $('#editorKeyword').val();
		offset = 0;
		getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
	});
	$('#editorSearchPrevious').click(function() {
		keyword = $('#editorKeyword').val();
		offset--;
		if (offset < 0){
			offset = 0;
		}
		getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
	});
	$('#editorSearchNext').click(function() {
		keyword = $('#editorKeyword').val();
		offset++;
		getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
	});
	$('#clearSelectedEditor').click(function() {
		selectedEditors = [];
		viewEditor();
	});
	viewEditor();
	
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
	$('#helpMarkdownModal').on('shown.bs.modal', function (event) {
		$.post(_CONTEXT + '/open.knowledge/marked', {
			title : 'Markdown Sample',
			content : $('#sampleMarkdownText').val()
		}, function(data) {
			var html = '<div style="word-break:break-all" id="content">';
			var content = data.content;
			html += content;
			html += '</div>';
			
			var jqObj = $('#markdownSamplePreview');
			jqObj.html(html);
			jqObj.find('code').addClass('hljs');
			codeHighlight(jqObj)
			.then(function() {
				var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
				jqObj.html(content);
			}).then(function () {
				jqObj.find('a.oembed').oembed();
			});
		});
	});
	
	setUpTagSelect();
	
	$('input[name="typeId"]:radio').change(function() {
		changeTemplate();
	});
	changeTemplate();
	
	
	// 保存処理
	// フォームのサブミットは禁止
	$('#knowledgeForm').submit(function(event) {
		console.log('submit');
		// 操作対象のフォーム要素を取得
		var $form = $(this);
		// ページ遷移を禁止して、Ajaxで保存
		event.preventDefault();
		
		// 送信ボタンを取得
		// （後で使う: 二重送信を防止する。）
		var $button = $form.find('button');
		
		// 送信
		$.ajax({
			url: $form.attr('action'),
			type: $form.attr('method'),
			data: $form.serialize(),
			timeout: 10000,  // 単位はミリ秒

			// 送信前
			beforeSend: function(xhr, settings) {
				// ボタンを無効化し、二重送信を防止
				$button.attr('disabled', true);
			},
			// 応答後
			complete: function(xhr, textStatus) {
				// ボタンを有効化し、再送信を許可
				$button.attr('disabled', false);
			},
			
			// 通信成功時の処理
			success: function(result, textStatus, xhr) {
				// 入力値を初期化
				console.log(result);
				
				$form.attr('action', _CONTEXT + '/protect.knowledge/update');
				
				var knowledgeId = result.result;
				$('#knowledgeId').val(knowledgeId);
				$('#knowledgeIdForDelete').val(knowledgeId);
				$('#savebutton').html('<i class="fa fa-save"></i>&nbsp;' + _LABEL_UPDATE);
				$('#title_msg').text(_UPDATE_TITLE);
				$('#deleteButton').removeClass('hide');
				$('#cancelButton').removeClass('hide');
				$('#cancelButton').attr('href', _CONTEXT + '/open.knowledge/view/' + knowledgeId);
				
				$.notify(result.message, 'info');
			},
			// 通信失敗時の処理
			error: function(xhr, textStatus, error) {
				// 入力値を初期化
				console.log(xhr.responseJSON);
				var msg = xhr.responseJSON;
				if (msg.children) {
					for (var i = 0; i < msg.children.length; i++) {
						var child = msg.children[i];
						console.log(child);
						$.notify(child.message, 'warn');
					}
				}
			}
		});
		return false;
	});
	
});

var getGroups = function(keyword, offset, listId, pageId, selectFunc) {
	if (!listId) {
		listId = '#groupList';
		pageId = '#groupPage';
		selectFunc = 'selectGroup';
	}
	$(listId).html('Now loading...');
	var url = _CONTEXT + '/protect.target/typeahead'
	var params = {
			keyword : keyword,
			offset: offset
	};
	
	$.get(url, params, function(result){
		groups = result;
		editors = result;
		var html = '';
		if (result.length == 0) {
			html += 'empty';
		} else {
			html+= '<div class="list-group">';
			for (var int = 0; int < result.length; int++) {
				html += '<a href="javascript:void(0);" class="list-group-item" onclick="' + selectFunc + '(' + int + ')">';
				html += result[int].label;
				html += '</a>';
			}
			html += '</div>';
		}
		$(listId).html(html);
		$(pageId).text('- page:' + (offset + 1) + ' -');
	});
};


var selectGroup = function(idx) {
	var exist = false;
	for (var i = 0; i < selectedGroups.length; i++) {
		var item = selectedGroups[i];
		if (item.value == groups[idx].value) {
			exist = true;
			break;
		}
	}
	if (!exist) {
		selectedGroups.push(groups[idx]);
	}
	viewGroup();
};

var viewGroup = function() {
	var values = [];
	var labels = [];
	for (var i = 0; i < selectedGroups.length; i++) {
		var item = selectedGroups[i];
		values.push(item.value);
		labels.push(item.label);
	}
	if (selectedGroups.length == 0) {
		$('#clearSelectedGroup').hide();
	} else {
		$('#clearSelectedGroup').show();
	}
	
	$('#selectedList').text(labels.join(','));
	$('#groupsLabel').text(labels.join(','));
	$('#groups').val(values.join(','));
};

var selectEditor = function(idx) {
	var exist = false;
	for (var i = 0; i < selectedEditors.length; i++) {
		var item = selectedEditors[i];
		if (item.value == editors[idx].value) {
			exist = true;
			break;
		}
	}
	if (!exist) {
		selectedEditors.push(editors[idx]);
	}
	viewEditor();
};

var viewEditor = function() {
	var values = [];
	var labels = [];
	for (var i = 0; i < selectedEditors.length; i++) {
		var item = selectedEditors[i];
		values.push(item.value);
		labels.push(item.label);
	}
	if (selectedEditors.length == 0) {
		$('#clearSelectedEditor').hide();
	} else {
		$('#clearSelectedEditor').show();
	}
	
	$('#selectedEditorList').text(labels.join(','));
	$('#editorsLabel').text(labels.join(','));
	$('#editors').val(values.join(','));
};



var dispChangeGroupArea = function(val) {
	var grops_area = $('#grops_area');
	if (val == '2') {
		grops_area.show('normal');
	} else {
		grops_area.hide('normal');
	}
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
	var textarea = $('#content');
	textarea.val(textarea.val() + text);
}



var emoji = window.emojiParser;
var preview = function() {
	$.post(_CONTEXT + '/open.knowledge/marked', {
		title : $('#input_title').val(),
		content : $('#content').val()
	}, function(data) {
		var html = '<div style="word-break:break-all" id="content">';
		var content = data.content;
		html += content;
		html += '</div>';
		
		var jqObj = $('#preview');
		jqObj.html(html);
		jqObj.find('code').addClass('hljs');
		codeHighlight(jqObj)
		.then(function() {
			var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
			jqObj.html(content);
		}).then(function () {
			jqObj.find('a.oembed').oembed();
		});
	});
};

var emojiSelect = function(id) {
	$(id).find('.name').each(function(i, block) {
		$(this).click(function(event) {
			var val = ' :' + $(this).text() + ': ';
			var textarea = $('#content');
			textarea.val(textarea.val() + val);
			$(id).modal('hide');
		});
	});
};

function deleteKnowledge() {
	bootbox.confirm(_CONFIRM, function(result) {
		if (result) {
			$('#knowledgeDeleteForm').attr('action', _CONTEXT + '/protect.knowledge/delete');
			$('#knowledgeDeleteForm').submit();
		}
	}); 
};

var changeTemplate = function() {
	$('#template_info').removeClass('show');
	$('#template_info').addClass('hide');
	var typeId = $('input[name="typeId"]:checked').val();
	console.log(typeId);
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
	$('#template_msg').text(template.description);
	$('#template_info').removeClass('hide');
	$('#template_info').addClass('show');
	
	$('#template_items').html('');
	if (template.items && template.items.length > 0) {
		for (var i = 0; i < template.items.length; i++) {
			var item = template.items[i];
			console.log(item);
			var tag = '<div class="form-group"><label for="item_' + item.itemNo + '">' + item.itemName + '</label>';
			
			// テンプレートの項目の種類毎に生成する入力項目を変化
			if (item.itemType === 1) {
				// textarea
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
						tag += '/> &nbsp;' + choice.choiceLabel + '</label><br/>';
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
						tag += '/> &nbsp;' + choice.choiceLabel + '</label><br/>';
					}
				}
			} else {
				// text
				tag += '<input type="text" class="form-control" name="item_' + item.itemNo;
				var val = '';
				if (item.itemValue) {
					val = item.itemValue;
				}
				tag += '" value="' + val + '" />';
			}
			tag += '</div>';
			$('#template_items').append(tag);
		}
	}
};


