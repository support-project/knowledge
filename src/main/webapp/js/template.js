var item = 0;
var choiceCount = new Array();

var createItemId = function() {
	item++;
	return "item" + item;
};

var deleteItem = function(itemId) {
	var s = '#' + itemId;
	$(s).remove();
};


var addChoice = function(itemId) {
	var c = 1;
	if (itemId in choiceCount) {
		c = choiceCount[itemId];
	}
	c++;
	choiceCount[itemId] = c;
	
	var tb = "#ch_" + itemId;
	
	var addItem = '';
	addItem += '<div class="form-group choice_item_top">';
	addItem += '<label for="">';
	addItem += LABEL_CHOICE_LABEL;
	addItem += '</label>';
	addItem += '<input type="text" class="form-control" name="label_' + itemId // + '_' + c 
		+ '" id="label_' + itemId + '_' + c + '" /> ';
	addItem += '</div>';
	
	addItem += '<div class="form-group choice_item_bottom">';
	addItem += '<label for="">';
	addItem += LABEL_CHOICE_VALUE;
	addItem += '</label>';
	addItem += '<input type="text" class="form-control" name="value_' + itemId // + '_' + c 
		+ '" id="value_' + itemId + '_' + c + '" /> ';
	addItem += '</div>';
	
	$(tb).append(addItem);
};

var deleteChoice = function(itemId) {
	var c = 1;
	if (itemId in choiceCount) {
		c = choiceCount[itemId];
	}
	c--;
	choiceCount[itemId] = c;
	
	var tb = "#ch_" + itemId + " div:last";
	$(tb).remove(); // value
	$(tb).remove(); // label
};

$(document).ready(function() {
	// フォームのサブミットは禁止
	$('#templateForm').submit(function(event) {
		console.log('submit');
		// 操作対象のフォーム要素を取得
		var $form = $(this);
		if ($form.attr('action') == (_CONTEXT + '/admin.template/delete')) {
			return true;
		}
		
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
				$.notify(result.message, 'info');
				
				$form.attr('action', _CONTEXT + '/admin.template/update');
				var typeid = result.result;
				$('#typeId').val(typeid);
				$('#savebutton').html('<i class="fa fa-save"></i>&nbsp;' + LABEL_UPDATE);
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
	
	
	//テキストのアイテムを追加
	$("#addText").click(function(){
		var itemId = createItemId();
		var addItem = '';
		addItem += '<div id="' + itemId + '" class="add_item">';
		
			addItem += '<h5 class="item_title">' + LABEL_TEXT_ITEM + '<input type="hidden" name="itemType" value="text_' + itemId + '"/>';
			addItem += '<button type="button" class="btn btn-warning" onclick="deleteItem(\'' + itemId + '\');" >';
			addItem += '<i class="fa fa-minus-square"></i>&nbsp;' + LABEL_DELETE;
			addItem += '</button></h5>';
			
			addItem += '<div class="form-group">';
			addItem += '<label for="">';
			addItem += LABEL_ITEM_TITLE + '</label>';
			addItem += '<input type="text" class="form-control" name="title_' + itemId + '" id="title_' + itemId + '" />';
			addItem += '</div>';
			addItem += '<div class="form-group">';
			addItem += '<label for="">' + LABEL_ITEM_DESCRIPTION + '</label>';
			addItem += '<input type="text" class="form-control" name="description_' + itemId + '" id="description_' + itemId + '" />';
			addItem += '</div>';
			
		addItem += '</div>';
		
		$("#items").append(addItem);
	});
	//ラジオボタンの選択肢追加
	$("#addRadio").click(function(){
		var itemId = createItemId();
		var addItem = '';
		addItem += '<div id="' + itemId + '" class="add_item">';
		
			addItem += '<h5 class="item_title">' + LABEL_RADIO_ITEM + '<input type="hidden" name="itemType" value="radio_' + itemId + '"/>';
			addItem += '<button type="button" class="btn btn-warning" onclick="deleteItem(\'' + itemId + '\');" >';
			addItem += '<i class="fa fa-minus-square"></i>&nbsp;' + LABEL_DELETE;
			addItem += '</button>&nbsp;';
			addItem += '<button type="button" class="btn btn-success" onclick="addChoice(\'' + itemId + '\');" >';
			addItem += '<i class="fa fa-plus-circle"></i>&nbsp;' + LABEL_ADD_CHOICE;
			addItem += '</button>&nbsp;';
			addItem += '<button type="button" class="btn btn-success" onclick="deleteChoice(\'' + itemId + '\');" >';
			addItem += '<i class="fa fa-minus-circle"></i>&nbsp;' + LABEL_DELETE_CHOICE;
			addItem += '</button></h5>';
			
			addItem += '<div class="form-group">';
			addItem += '<label for="">';
			addItem += LABEL_ITEM_TITLE + '</label>';
			addItem += '<input type="text" class="form-control" name="title_' + itemId + '" id="title_' + itemId + '" />';
			addItem += '</div>';
			addItem += '<div class="form-group">';
			addItem += '<label for="">' + LABEL_ITEM_DESCRIPTION + '</label>';
			addItem += '<input type="text" class="form-control" name="description_' + itemId + '" id="description_' + itemId + '" />';
			addItem += '</div>';
			
			addItem += '<div id="ch_' + itemId + '" class="choice_item_list">';
				addItem += '<div class="form-group choice_item choice_item_top">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_LABEL;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="label_' + itemId + '" id="label_' + itemId + '_1" /> ';
				addItem += '</div>';
				
				addItem += '<div class="form-group choice_item_bottom">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_VALUE;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="value_' + itemId + '" id="value_' + itemId + '_1" /> ';
				addItem += '</div>';
			addItem += '</div>';
		addItem += '</div>';
		
		$("#items").append(addItem);
	});
	
	//チェックボックスの選択肢追加
	$("#addCheckbox").click(function(){
		var itemId = createItemId();
		
		var addItem = '';
		addItem += '<div id="' + itemId + '" class="add_item">';
		
			addItem += '<h5 class="item_title">' + LABEL_CHECKBOX_ITEM + '<input type="hidden" name="itemType" value="checkbox_' + itemId + '"/>';
			addItem += '<button type="button" class="btn btn-warning" onclick="deleteItem(\'' + itemId + '\');" >';
			addItem += '<i class="fa fa-minus-square"></i>&nbsp;' + LABEL_DELETE;
			addItem += '</button>&nbsp;';
			addItem += '<button type="button" class="btn btn-success" onclick="addChoice(\'' + itemId + '\');" >';
			addItem += '<i class="fa fa-plus-circle"></i>&nbsp;' + LABEL_ADD_CHOICE;
			addItem += '</button>&nbsp;';
			addItem += '<button type="button" class="btn btn-success" onclick="deleteChoice(\'' + itemId + '\');" >';
			addItem += '<i class="fa fa-minus-circle"></i>&nbsp;' + LABEL_DELETE_CHOICE;
			addItem += '</button></h5>';
		
			addItem += '<div class="form-group">';
			addItem += '<label for="">';
			addItem += LABEL_ITEM_TITLE + '</label>';
			addItem += '<input type="text" class="form-control" name="title_' + itemId + '" id="title_' + itemId + '" />';
			addItem += '</div>';
			addItem += '<div class="form-group">';
			addItem += '<label for="">' + LABEL_ITEM_DESCRIPTION + '</label>';
			addItem += '<input type="text" class="form-control" name="description_' + itemId + '" id="description_' + itemId + '" />';
			addItem += '</div>';
			
			addItem += '<div id="ch_' + itemId + '" class="choice_item_list">';
				addItem += '<div class="form-group choice_item choice_item_top">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_LABEL;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="label_' + itemId + '" id="label_' + itemId + '_1" /> ';
				addItem += '</div>';
				
				addItem += '<div class="form-group choice_item_bottom">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_VALUE;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="value_' + itemId + '" id="value_' + itemId + '_1" /> ';
				addItem += '</div>';
			addItem += '</div>';
		addItem += '</div>';
		
		$("#items").append(addItem);
	});
	
});
