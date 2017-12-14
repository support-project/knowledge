$(document).ready(function() {
    
    var changeTemplate = function() {
        $('#template_info').removeClass('show');
        $('#template_info').addClass('hide');
        var typeId = $('input[name="typeId"]:checked').val();
//        console.log(typeId);
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
//                console.log(data);
                document.__add_Template_Edit_Item(data);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                $.notify('[fail] get template info', 'warn');
            }
        });
    };
    
    $('input[name="typeId"]:radio').change(function() {
        changeTemplate();
    });
    
    changeTemplate();
});







