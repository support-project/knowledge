/**
 * テンプレートの項目を管理するページで使う共通関数
 */
$(document).ready(function() {
    var item = 0;
    var choiceCount = new Array();
    document._TEMPLATE = {
        editable: false
    };

    //item数初期化
    item = $('.add_item').length;
//    console.log($('.add_item').length);
    
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
    
    var moveupItem = function(itemId) {
        var s = '#' + itemId;
        var e = $(s);
        e.prev().insertAfter(e);
        
    }
    var movedownItem = function(itemId) {
        var s = '#' + itemId;
        var e = $(s);
        e.next().insertBefore(e);
        
    }
    
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
        addItem += '<div class="form-group form-inline">';
        addItem += '<label for="" class="col-sm-3 control-label">' + LABEL_CHOICE_VALUE + ' / ' + LABEL_ITEM_TITLE + '</label>';
        addItem += '<div class="col-sm-3 input-group">';
        addItem += '<div class="input-group-addon"><i class="fa fa-database" aria-hidden="true"></i></div>';
        addItem += '<input type="text" class="form-control" name="value_' + itemId // + '_' + c 
        + '" id="value_' + itemId + '_' + c + '" value="' + value + '" />';
        addItem += '</div>';
        addItem += '<div class="col-sm-6 input-group">';
        addItem += '<div class="input-group-addon"><i class="fa fa-play-circle" aria-hidden="true"></i></div>';
        addItem += '<input type="text" class="form-control" name="label_' + itemId // + '_' + c 
        + '" id="label_' + itemId + '_' + c + '" value="' + label + '" />';
        addItem += '</div>';
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
        
        var tb = "#ch_" + itemId + "";
        $(tb).children().last().remove();
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
        if (kind === LABEL_TEXTAREA_ITEM) {
            prefix = 'textarea';
        } else if (kind === LABEL_RADIO_ITEM) {
            prefix = 'radio';
        } else if(kind === LABEL_CHECKBOX_ITEM) {
            prefix = 'checkbox';
        } else if(kind === LABEL_INTEGER_ITEM) {
            prefix = 'integer';
        } else if(kind === LABEL_DATE_ITEM) {
            prefix = 'date';
        } else if(kind === LABEL_TIME_ITEM) {
            prefix = 'time';
        } else if(kind === LABEL_TIMEZONE_ITEM) {
            prefix = 'timezone';
        }
        var addItem = '';
        addItem += '<div id="' + itemId + '" class="add_item">';
            addItem += '<h5 class="item_title">' + kind + '<input type="hidden" name="itemType" value="' + prefix + '_' + itemId + '"/>';
            if (document._TEMPLATE.editable) {
                if (kind === LABEL_RADIO_ITEM || kind === LABEL_CHECKBOX_ITEM) {
                    addItem += '<button type="button" class="btn btn-success" id="addChoice_' + itemId + '" >';
                    addItem += '<i class="fa fa-plus-circle"></i>&nbsp;' + LABEL_ADD_CHOICE;
                    addItem += '</button>&nbsp;';
                    addItem += '<button type="button" class="btn btn-success" id="deleteChoice_' + itemId + '" >';
                    addItem += '<i class="fa fa-minus-circle"></i>&nbsp;' + LABEL_DELETE_CHOICE;
                    addItem += '</button>&nbsp;';
                }
                addItem += '<button type="button" class="btn btn-info" id="moveupItem_' + itemId + '" >';
                addItem += '<i class="fa fa-arrow-up"></i>&nbsp;' + LABEL_MOVE_UP;
                addItem += '</button>&nbsp;';
                addItem += '<button type="button" class="btn btn-info" id="movedownItem_' + itemId + '" >';
                addItem += '<i class="fa fa-arrow-down"></i>&nbsp;' + LABEL_MOVE_DOWN;
                addItem += '</button>&nbsp;';
                addItem += '<button type="button" class="btn btn-warning deleteItemButton" id="deleteItem_' + itemId + '" >';
                addItem += '<i class="fa fa-minus-square"></i>&nbsp;' + LABEL_DELETE;
                addItem += '</button>&nbsp;';
            }
            addItem += '</h5>';
            addItem += '<div class="form-group">';
            addItem += '<label for="" class="col-sm-2 control-label">' + LABEL_ITEM_TITLE + '</label>';
            addItem += '<div class="col-sm-10 input-group"><div class="input-group-addon"><i class="fa fa-tag" aria-hidden="true"></i></div>';
            addItem += '<input type="text" class="form-control" name="title_' + itemId + '" id="title_' + itemId + '" value="' + label + '" />';
            addItem += '</div>';
            addItem += '</div>';
            addItem += '<div class="form-group">';
            addItem += '<label for="" class="col-sm-2 control-label">' + LABEL_ITEM_DESCRIPTION + '</label>';
            addItem += '<div class="col-sm-10 input-group"><div class="input-group-addon"><i class="fa fa-commenting-o" aria-hidden="true"></i></div>';
            addItem += '<input type="text" class="form-control" name="description_' + itemId + '" id="description_' + itemId + '" value="' + value + '" />';
            addItem += '</div>';
            addItem += '</div>';
            
            addItem += '<div id="ch_' + itemId + '" class="choice_item_list">';
            addItem += '</div>';
        addItem += '</div>';
        
        $("#items").append(addItem);
        
        $('#deleteItem_' + itemId).click(function() {
            deleteItem(itemId);
        });
        $('#moveupItem_' + itemId).click(function() {
            moveupItem(itemId);
        });
        $('#movedownItem_' + itemId).click(function() {
            movedownItem(itemId);
        });
        $('#addChoice_' + itemId).click(function() {
            addChoice(itemId);
        });
        $('#deleteChoice_' + itemId).click(function() {
            deleteChoice(itemId);
        });
    };
    
    //テキストのアイテムを追加
    $("#addText").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_TEXT_ITEM);
    });
    //テキストのアイテムを追加
    $("#addTextArea").click(function(){
        var itemId = createItemId();
        addItem(itemId, LABEL_TEXTAREA_ITEM);
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
    
    var loaddata = function(url) {
        return new Promise(function(resolve, reject) {
            $.ajax({
                url: url,
                type: 'GET',
                timeout: 10000
            }).done(function(result, textStatus, xhr) {
                logging(result);
                $('#typeName').val(result.typeName ? result.typeName : result.title);
                $('#typeIcon').val(result.typeIcon);
                $('#description').val(result.description);
                $('#initialValue').val(result.initialValue);
                $('#deletebutton').removeClass('hide');
                document._TEMPLATE.editable = result.editable;
                if (!result.editable) {
                    $('#editableMsg').removeClass('hide');
                    $('#deletebutton').addClass('hide');
                } else {
                    $('.editbtn').removeClass('hide');
                    $('#deletebutton').removeClass('hide');
                }
                item = result.items.length;
                result.items.forEach(function(item) {
                    logging(item);
                    var itemId = 'item' + item.itemNo;
                    if (item.itemType === 0) {
                        addItem(itemId, LABEL_TEXT_ITEM, item.itemName, item.description);
                    } else if (item.itemType === 1) {
                        addItem(itemId, LABEL_TEXTAREA_ITEM, item.itemName, item.description);
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
                return resolve();
            }).fail(function(xhr, textStatus, error) {
                if (xhr.status === 404) {
                    console.log('data is not exist.');
                } else {
                    handleErrorResponse(xhr, textStatus, error);
                    return reject('error');
                }
            });
        });
    };
    
    this.__load_survey = loaddata;
});
