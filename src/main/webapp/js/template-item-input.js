/**
 * テンプレートのマスターデータを元に、入力項目（フォーム）を作るさいの共通処理
 */
$(document).ready(function() {
    var timezones = [];
    var createTextItem = function(item) {
        // text
        var tag = '<input type="text" class="form-control" name="item_' + item.itemNo;
        var val = '';
        if (item.itemValue) {
            val = item.itemValue;
        }
        tag += '" value="' + val + '" />';
        return tag;
    };

    var createTextAreaItem = function(item) {
        // text area
        var tag = '<textarea class="form-control" name="item_' + item.itemNo+ '" >';
        var val = '';
        if (item.itemValue) {
            val = item.itemValue;
        }
        tag += val;
        tag += '</textarea>';
        return tag;
    };

    var createIntegerItem = function(item) {
        // Integer
        var tag = '<input type="number" class="form-control" name="item_' + item.itemNo;
        var val = '';
        if (item.itemValue) {
            val = item.itemValue;
        }
        tag += '" value="' + val + '" min="0" step="1"/>';
        return tag;
    };


    var createRadioItem = function(item) {
        var tag = '';
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
        return tag;
    };
    var createCheckboxItem = function(item) {
        var tag = '';
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
        return tag;
    };
    var createDateItem = function(item) {
        var tag = '<div class="input-group">';
        tag += '<input type="text" class="form-control datepicker" name="item_' + item.itemNo;
        var val = '';
        if (item.itemValue) {
            val = item.itemValue;
        }
        tag += '" value="' + val + '" />';
        tag += '<span class="input-group-addon">';
        tag += '<span class="fa fa-calendar"></span>';
        tag += '</span>';
        tag += '</div>';
        return tag;
    };
    var createTimeItem = function(item) {
        var tag = '<div class="input-group clockpicker" data-placement="bottom" data-align="top" data-autoclose="true">';
        tag += '<input type="text" class="form-control" name="item_' + item.itemNo;
        var val = '';
        if (item.itemValue) {
            val = item.itemValue;
        }
        tag += '" value="' + val + '" />';
        tag += '<span class="input-group-addon">';
        tag += '<span class="fa fa-clock-o"></span>';
        tag += '</span>';
        tag += '</div>';
        return tag;
    };
    var createTimeZoneItem = function(item) {
        var tag = '<div class="input-group" data-placement="bottom" data-align="top" data-autoclose="true">';
        tag += '<input type="text" class="form-control timezone" name="item_' + item.itemNo;
        var val = '';
        if (item.itemValue) {
            val = item.itemValue;
        } else {
            var tz = jstz.determine();
            val = tz.name();
            logging(val);
        }
        tag += '" value="' + val + '" />';
        tag += '<span class="input-group-addon">';
        tag += '<span class="fa fa-globe"></span>';
        tag += '</span>';
        tag += '</div>';
        return tag;
    };
    
    var initialValue = '';
    var addTemplateItem = function(template) {
        $('#template_msg').text(template.description);
        $('#template_info').removeClass('hide');
        $('#template_info').addClass('show');
        
        var contentStr = $('#content').val();
        if (!contentStr) {
            $('#content').val(template.initialValue);
            initialValue = template.initialValue;
        } else {
            if (initialValue.replace(/\r?\n/g,"") == contentStr.replace(/\r?\n/g,"")) {
                // 初期値を指定してから変更していない
                $('#content').val(template.initialValue ? template.initialValue : '');
                initialValue = template.initialValue;
            }
        }
        
        $('#template_items').html('');
        var exists_date = false;
        var exists_time = false;
        var exists_timezone = false;
        if (template.items && template.items.length > 0) {
            for (var i = 0; i < template.items.length; i++) {
                var item = template.items[i];
//                console.log(item);
                var tag = '<div class="form-group"><label for="item_' + item.itemNo + '">' + item.itemName + '</label>';
                
                // テンプレートの項目の種類毎に生成する入力項目を変化
                if (item.itemType === 1) {
                    // textarea
                    tag += createTextAreaItem(item);
                } else if (item.itemType === 2) {
                    // Integer
                    tag += createIntegerItem(item);
                } else if (item.itemType === 10) {
                    // Radio
                    tag += createRadioItem(item);
                } else if (item.itemType === 11) {
                    // Checkbox
                    tag += createCheckboxItem(item);
                } else if (item.itemType === 20) {
                    // Date
                    tag += createDateItem(item);
                    exists_date = true;
                } else if (item.itemType === 21) {
                    // Time
                    tag += createTimeItem(item);
                    exists_time = true;
                } else if (item.itemType === 22) {
                    // TimeZone
                    tag += createTimeZoneItem(item);
                    exists_timezone = true;
                } else {
                    tag += createTextItem(item);
                }
                tag += '</div>';
                $('#template_items').append(tag);
            }
        }
        if (exists_date) {
            var lang = 'en';
            if (_LANG) {
                lang = _LANG;
            }
            $('.datepicker').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayHighlight: true,
                language: lang,
            });
        }
        if (exists_time) {
            $('.clockpicker').clockpicker()
            .find('input').change(function(){
                logging(this.value);
            });
        }
        if (exists_timezone) {
//            console.log('timezone');
            Promise.try(function() {
                if (timezones.length > 0) {
                    return Promise.resolve();
                }
                return new Promise(function(resolve, reject) {
                    $.ajax({
                        url: _CONTEXT + '/bower/moment-timezone/data/meta/latest.json',
                        type: 'GET',
                        timeout: 10000
                    }).done(function(result, textStatus, xhr) {
                        for (var zone in result.zones) {
                            timezones.push(zone);
                        }
                        return resolve();
                    }).fail(function(xhr, textStatus, error) {
                        return reject(error);
                    });
                });
            }).then(function() {
                $('.timezone').typeahead({ source:timezones });
            });
        }
    };
    
    document.__add_Template_Edit_Item = addTemplateItem;
    
    
});

