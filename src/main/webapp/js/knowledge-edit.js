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
	
	$('#sampleMarkdownCheck').click(function() {
		var text = $('#sampleMarkdownText').val();
		var textarea = $('#content');
		textarea.val(text);
		preview();
		$('#helpMarkdownModal').modal('hide');
		var p = $("#preview").offset().top - 60;
		$('html,body').animate({ scrollTop: p }, 'fast');
	});
	
	$('#select_template').change(function() {
		var sel = $('#select_template').val();
		var title = $('#Title' + sel).val();
		var text = $('#Template' + sel).val();
		var textarea = $('#content');
		var inputtitle = $('#input_title');
		//入力済みチェック
		if (!(inputtitle.val() == '' && textarea.val() == '')) {
			if(!confirm("編集内容が失われますがよろしいですか？")) {
				return;
			}
		}
		//変数置換
		var date = new Date();
		title=title.replace("%{Year}", date.getFullYear());
		title=title.replace("%{month}", date.getMonth()+1);
		title=title.replace("%{day}", date.getDate());
		title=title.replace("%{user}", $("#userName").val());
		text=text.replace("%{Year}", date.getFullYear());
		text=text.replace("%{month}", date.getMonth()+1);
		text=text.replace("%{day}", date.getDate());
		text=text.replace("%{user}", $("#userName").val());
		//セット
		inputtitle.val(title);
		textarea.val(text);
		preview();
	});
	
	setUpTagSelect();
	
	if ($('.selectpicker').length) {
		$('.selectpicker').selectpicker();
		
		$('input[name="typeId"]:radio').change(function() {
			changeTemplate();
		});
		changeTemplate();
	}
	
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
				html += '<a href="#" class="list-group-item" onclick="' + selectFunc + '(' + int + ')">';
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

var previewTimer;

var previewInput = function() {
	clearTimeout(previewTimer);
	previewTimer = setTimeout('preview();',400);
}

var preview = function() {
	$.post(_CONTEXT + '/open.knowledge/marked', {
		title : $('#input_title').val(),
		content : $('#content').val()
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
		
		var jqObj = $('#preview');
		jqObj.html(html);
		codeHighlight(jqObj)
		.then(function() {
			var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
			jqObj.html(content);
			
			//var speed = 500;
			//var target = $('#preview');
			//var position = target.offset().top;
			//$("html, body").animate({scrollTop:position}, speed, "swing");
			
			return;
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
			$('#knowledgeForm').attr('action', _CONTEXT + '/protect.knowledge/delete');
			$('#knowledgeForm').submit();
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
	$('#template_name').text(template.typeName);
	$('#template_msg').text(template.description);
	$('#template_info').removeClass('hide');
	$('#template_info').addClass('show');
	

	$('#template_items').html('');
	if (template.items && template.items.length > 0) {
		for (var i = 0; i < template.items.length; i++) {
			var item = template.items[i];
			console.log(item);
			var tag = '<label for="item_' + item.itemNo + '">' + item.itemName + '</label>';
			tag += '<input type="text" class="form-control" name="item_' + item.itemNo + '" for="item_' + item.itemNo
			var val = '';
			if (item.itemValue) {
				val = item.itemValue;
			}
			tag += '" value="' + val + '" />';
			$('#template_items').append(tag);
		}
	}
};


