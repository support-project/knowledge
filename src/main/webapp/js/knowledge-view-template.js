$(document).ready(function() {
    changeTemplate();
});








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
