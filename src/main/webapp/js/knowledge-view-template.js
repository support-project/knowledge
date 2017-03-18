$(document).ready(function() {
    
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
                // console.log(data);
                var template = data;
                var templateTag = '<i class="fa ' + template.typeIcon + '"></i>&nbsp;' + template.typeName + '';
                $('#template').html(templateTag);
                
                document.__add_Template_Item(data);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                $.notify('[fail] get template info', 'warn');
            }
        });
    };
    
    changeTemplate();
});



