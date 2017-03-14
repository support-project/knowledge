$(document).ready(function() {
    var item = 0;
    var choiceCount = new Array();
    document._TEMPLATE = {
        editable: false
    };

    //item数初期化
    item = $('.add_item').length;
    console.log($('.add_item').length);
    
    /** 入力項目のIDを生成 **/
    var createItemId = function() {
        item++;
        return "item" + item;
    };
    /** 
     * 入力項目を削除
     * @param itemid 入力項目のID
     **/
    var deleteItem = function(itemId) {
        var s = '#' + itemId;
        $(s).remove();
    };
    /**
     * 入力項目に対し、選択肢を追加（ラジオやチェックボックスなどの選択項目）
     * @param itemid 入力項目のID
     */
    var addChoice = function(itemId, label, value) {
        if (!label) {
            label = '';
        }
        if (!value) {
            value = '';
        }
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
            + '" id="label_' + itemId + '_' + c + '" value="' + label + '" />';
        addItem += '</div>';
        
        addItem += '<div class="form-group choice_item_bottom">';
        addItem += '<label for="">';
        addItem += LABEL_CHOICE_VALUE;
        addItem += '</label>';
        addItem += '<input type="text" class="form-control" name="value_' + itemId // + '_' + c 
            + '" id="value_' + itemId + '_' + c + '" value="' + value + '" />';
        addItem += '</div>';
        
        $(tb).append(addItem);
    };
    /**
     * 入力項目の選択肢を削除
     * 一番うしろのものから削除し、途中の項目削除には対応していない
     * @param itemid 入力項目のID
     */
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
    
    /**
     * 入力項目を追加
     */
    var addItem = function(itemId, kind, label, value) {
        if (!label) {
            label = '';
        }
        if (!value) {
            value = '';
        }
        var prefix = 'text';
        if (kind === LABEL_RADIO_ITEM) {
            prefix = 'radio';
        }
        if(kind === LABEL_CHECKBOX_ITEM) {
            prefix = 'checkbox';
        }
        if(kind === LABEL_INTEGER_ITEM) {
            prefix = 'integer';
        }
        if(kind === LABEL_DATE_ITEM) {
            prefix = 'date';
        }
        if(kind === LABEL_TIME_ITEM) {
            prefix = 'time';
        }
        if(kind === LABEL_TIMEZONE_ITEM) {
            prefix = 'timezone';
        }
        var addItem = '';
        addItem += '<div id="' + itemId + '" class="add_item">';
            addItem += '<h5 class="item_title">' + kind + '<input type="hidden" name="itemType" value="' + prefix + '_' + itemId + '"/>';
            if (document._TEMPLATE.editable) {
                addItem += '<button type="button" class="btn btn-warning" id="deleteItem_' + itemId + '" >';
                addItem += '<i class="fa fa-minus-square"></i>&nbsp;' + LABEL_DELETE;
                addItem += '</button>&nbsp;';
                if (kind === LABEL_RADIO_ITEM || kind === LABEL_CHECKBOX_ITEM) {
                    addItem += '<button type="button" class="btn btn-success" id="addChoice_' + itemId + '" >';
                    addItem += '<i class="fa fa-plus-circle"></i>&nbsp;' + LABEL_ADD_CHOICE;
                    addItem += '</button>&nbsp;';
                    addItem += '<button type="button" class="btn btn-success" id="deleteChoice_' + itemId + '" >';
                    addItem += '<i class="fa fa-minus-circle"></i>&nbsp;' + LABEL_DELETE_CHOICE;
                    addItem += '</button>';
                }
            }
            addItem += '</h5>';
            addItem += '<div class="form-group">';
            addItem += '<label for="">';
            addItem += LABEL_ITEM_TITLE + '</label>';
            addItem += '<input type="text" class="form-control" name="title_' + itemId + '" id="title_' + itemId + '" value="' + label + '" />';
            addItem += '</div>';
            addItem += '<div class="form-group">';
            addItem += '<label for="">' + LABEL_ITEM_DESCRIPTION + '</label>';
            addItem += '<input type="text" class="form-control" name="description_' + itemId + '" id="description_' + itemId + '" value="' + value + '" />';
            addItem += '</div>';
            
            addItem += '<div id="ch_' + itemId + '" class="choice_item_list">';
            addItem += '</div>';
        addItem += '</div>';
        
        $("#items").append(addItem);
        
        $('#deleteItem_' + itemId).click(function() {
            deleteItem(itemId);
        });
        $('#addChoice_' + itemId).click(function() {
            addChoice(itemId);
        });
        $('#deleteChoice_' + itemId).click(function() {
            deleteChoice(itemId);
        });
    };
    
    var handleErrorResponse = function(xhr, textStatus, error) {
        // 入力値を初期化
        console.log(xhr.responseJSON);
        var msg = xhr.responseJSON;
        if (msg.children) {
            for (var i = 0; i < msg.children.length; i++) {
                var child = msg.children[i];
                console.log(child);
                $.notify(child.message, 'warn');
            }
        } else {
            $.notify('data load error. please try again.', 'warn');
        }
    };
    
    //テキストのアイテムを追加
    $("#addText").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_TEXT_ITEM);
    });
    //ラジオボタンの選択肢追加
    $("#addRadio").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_RADIO_ITEM);
        addChoice(itemId);
    });
    //チェックボックスの選択肢追加
    $("#addCheckbox").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_CHECKBOX_ITEM);
        addChoice(itemId);
    });
    //整数のアイテムを追加
    $("#addInteger").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_INTEGER_ITEM);
    });
    //日付のアイテムを追加
    $("#addDate").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_DATE_ITEM);
    });
    //時間のアイテムを追加
    $("#addTime").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_TIME_ITEM);
    });
    //タイムゾーンのアイテムを追加
    $("#addTimezone").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_TIMEZONE_ITEM);
    });
    
    $('#deletebutton').click(function(){
        deleteTemplate();
    });
    
    
    var loaddata = function(url) {
        return new Promise(function(resolve, reject) {
            $.ajax({
                url: url,
                type: 'GET',
                timeout: 10000
            }).done(function(result, textStatus, xhr) {
                logging(result);
                $('#typeName').val(result.typeName);
                $('#typeIcon').val(result.typeIcon);
                $('#description').val(result.description);
                document._TEMPLATE.editable = result.editable;
                if (!result.editable) {
                    $('#editableMsg').removeClass('hide');
                } else {
                    $('.editbtn').removeClass('hide');
                }
                item = result.items.length;
                result.items.forEach(function(item) {
                    logging(item);
                    var itemId = 'item' + item.itemNo;
                    if (item.itemType === 0) {
                        addItem(itemId, LABEL_TEXT_ITEM, item.itemName, item.description);
                    } else if (item.itemType === 2) {
                        addItem(itemId, LABEL_INTEGER_ITEM, item.itemName, item.description);
                    } else if (item.itemType === 10) {
                        addItem(itemId, LABEL_RADIO_ITEM, item.itemName, item.description);
                        item.choices.forEach(function(choice) {
                            addChoice(itemId, choice.choiceLabel, choice.choiceValue);
                        });
                    } else if (item.itemType === 11) {
                        addItem(itemId, LABEL_CHECKBOX_ITEM, item.itemName, item.description);
                        item.choices.forEach(function(choice) {
                            addChoice(itemId, choice.choiceLabel, choice.choiceValue);
                        });
                    } else if (item.itemType === 20) {
                        addItem(itemId, LABEL_DATE_ITEM, item.itemName, item.description);
                    } else if (item.itemType === 21) {
                        addItem(itemId, LABEL_TIME_ITEM, item.itemName, item.description);
                    } else if (item.itemType === 22) {
                        addItem(itemId, LABEL_TIMEZONE_ITEM, item.itemName, item.description);
                    }
                });
            }).fail(function(xhr, textStatus, error) {
                handleErrorResponse(xhr, textStatus, error);
            });
        });
    };
    
    this.loaddata = loaddata;
});
