$(document).ready(function() {
    $('input[name="typeId"]:radio').change(function() {
        changeTemplate();
    });
    changeTemplate();
});




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
    var draftId = $('#draftId').val();
    $.ajax({
        type : 'GET',
        url : url,
        data : 'type_id=' + typeId + '&knowledge_id=' + knowledgeId + '&draft_id=' + draftId,
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

