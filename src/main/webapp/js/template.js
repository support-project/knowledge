var LABEL_DELETE = '項目削除';
var LABEL_TEXT_ITEM = '<i class="fa fa-pencil"></i>&nbsp;テキスト入力項目';
var LABEL_RADIO_ITEM = '<i class="fa fa-dot-circle-o"></i>&nbsp;ラジオボタン入力項目';
var LABEL_CHECKBOX_ITEM = '<i class="fa fa-check-square-o"></i>&nbsp;チェックボックスの選択項目';
var LABEL_ITEM_TITLE = '項目名';
var LABEL_ITEM_DESCRIPTION = '説明文';
var LABEL_ADD_CHOICE = '選択肢追加';
var LABEL_DELETE_CHOICE = '選択肢削除';
var LABEL_CHOICE_LABEL = '表示する値';
var LABEL_CHOICE_VALUE = 'データの値';

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
	addItem += '<input type="text" class="form-control" name="label_' + itemId + '_1" id="label_' + itemId + '_1" /> ';
	addItem += '</div>';
	
	addItem += '<div class="form-group choice_item_bottom">';
	addItem += '<label for="">';
	addItem += LABEL_CHOICE_VALUE;
	addItem += '</label>';
	addItem += '<input type="text" class="form-control" name="value_' + itemId + '_1" id="value_' + itemId + '_1" /> ';
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
			addItem += '<input type="text" class="form-control" name="explanation_' + itemId + '" id="explanation_' + itemId + '" />';
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
			addItem += '<input type="text" class="form-control" name="explanation_' + itemId + '" id="explanation_' + itemId + '" />';
			addItem += '</div>';
			
			addItem += '<div id="ch_' + itemId + '" class="choice_item_list">';
				addItem += '<div class="form-group choice_item choice_item_top">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_LABEL;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="label_' + itemId + '_1" id="label_' + itemId + '_1" /> ';
				addItem += '</div>';
				
				addItem += '<div class="form-group choice_item_bottom">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_VALUE;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="value_' + itemId + '_1" id="value_' + itemId + '_1" /> ';
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
			addItem += '<input type="text" class="form-control" name="explanation_' + itemId + '" id="explanation_' + itemId + '" />';
			addItem += '</div>';
			
			addItem += '<div id="ch_' + itemId + '" class="choice_item_list">';
				addItem += '<div class="form-group choice_item choice_item_top">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_LABEL;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="label_' + itemId + '_1" id="label_' + itemId + '_1" /> ';
				addItem += '</div>';
				
				addItem += '<div class="form-group choice_item_bottom">';
				addItem += '<label for="">';
				addItem += LABEL_CHOICE_VALUE;
				addItem += '</label>';
				addItem += '<input type="text" class="form-control" name="value_' + itemId + '_1" id="value_' + itemId + '_1" /> ';
				addItem += '</div>';
			addItem += '</div>';
		addItem += '</div>';
		
		$("#items").append(addItem);
	});
	
});
