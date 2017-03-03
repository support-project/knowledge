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

var createTextItem = function(item) {
    var tag = '';
    tag += item.itemValue;
    return tag;
};
var createTextArea = function(item) {
    var tag = '<pre>';
    tag += item.itemValue;
    tag += '</pre>';
    return tag;
};
var createURLItem = function(item) {
    var tag = '';
    var url = '';
    if (item.itemValue) {
        url = item.itemValue;
    }
    tag += '<h4>' + item.itemName + ': ';
    tag += '<a href="' + url + '" target="_blank" >' + url + '</a></h4>';
    return tag;
};
var createRadioItem = function(item) {
    var tag = '';
    if (item.choices) {
        for (var j = 0; j < item.choices.length; j++) {
            var choice = item.choices[j];
            if (choice.choiceValue == item.itemValue) {
                tag += '<i class="fa fa-dot-circle-o" aria-hidden="true"></i>';
            } else {
                tag += '<i class="fa fa-circle-o" aria-hidden="true"></i>';
            }
            tag += '&nbsp;' + choice.choiceLabel + '&nbsp;&nbsp;&nbsp;';
        }
    }
    return tag;
};
var createCheckboxItem = function(item) {
    var tag = '';
    if (item.choices) {
        for (var j = 0; j < item.choices.length; j++) {
            var choice = item.choices[j];
            if (item.itemValue) {
                var vals = item.itemValue.split(',');
                var exist = false;
                for (var k = 0; k < vals.length; k++) {
                    if (choice.choiceValue == vals[k].trim()) {
                        exist = true;
                        break;
                    }
                }
            }
            if (exist) {
                tag += '<i class="fa fa-check-square-o" aria-hidden="true"></i>';
            } else {
                tag += '<i class="fa fa-square-o" aria-hidden="true"></i>';
            }
            
            tag += '&nbsp;' + choice.choiceLabel + '&nbsp;&nbsp;&nbsp;';
        }
    }
    return tag;
};


var addTemplateItem = function(template) {
    var templateTag = '<i class="fa ' + template.typeIcon + '"></i>&nbsp;' + template.typeName + '';
    $('#template').html(templateTag);
    
    if (template.items && template.items.length > 0) {
        for (var i = 0; i < template.items.length; i++) {
            var item = template.items[i];
            console.log(item);
            var tag = '';
            if (i > 0) {
                tag += '<br/>';
            }
            // Bookmrkの場合は、項目はURLのみ
            if (template.typeId == -99) {
                tag += createURLItem(item);
            } else {
                tag += item.itemName + ': ';
                if (item.itemType === 1) {
                    // textarea
                    tag += createTextArea(item);
                } else if (item.itemType === 10) {
                    // Radio
                    tag += createRadioItem(item);
                } else if (item.itemType === 11) {
                    // Checkbox
                    tag += createCheckboxItem(item);
                } else {
                    tag += createTextItem(item);
                }
            }
            
            $('#template_items').append(tag);
            $('#template_items_area').show();
        }
    }
};
