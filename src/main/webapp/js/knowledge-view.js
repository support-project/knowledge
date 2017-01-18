$(document).ready(function(){
    // Lazy load
    echo.init();
    /*
    $('#input_tags').on('beforeItemRemove', function(event) {
        event.cancel = true;
    });
    */
    // clip the url
    var clipboard = new Clipboard('#urlBtn');
    clipboard.on('success', function(e) {
        $.notify(_MSG_COPIED, 'success');
    });
    
    // Auto complete
    setAutoComplete($("#comment"));
});

//イイネを押下
var addlike = function(knowledgeId) {
    var url = _CONTEXT + '/open.knowledge/like/' + knowledgeId;
    $.ajax({
        type : 'POST',
        url : url,
        success : function(data, dataType) {
            console.log(data);
            $('#like_count').text(data.count);
            $.notify(LABEL_LIKE, 'info');
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            console.log(textStatus);
        }
    });
};

/*
var viewProtect = function(knowledgeId) {
    var url = _CONTEXT + '/protect.knowledge/view_targets/' + knowledgeId;
    $.ajax({
        type : 'GET',
        url : url,
        success : function(datas, dataType) {
            var targets = '';
            for ( var i = 0; i < datas.length; i++) {
                var data = datas[i];
                targets += data.label;
            }
            bootbox.alert(targets);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            console.log(textStatus);
        }
    });
};
*/







