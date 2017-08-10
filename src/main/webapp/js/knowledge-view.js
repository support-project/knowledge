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
    
    $('.inner-page-link').click(function(){
        var speed = 500;
        var href= $(this).attr("href");
        //var target = $(href == "#" || href == "" ? 'html' : href);
        var target = $(href);
        if (target && target.offset && target.offset()) {
            var position = target.offset().top;
            position = position - 100;
            $("html, body").animate({scrollTop:position}, speed, "swing");
        }
        return false;
    });
    
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
            if (XMLHttpRequest.status === 403 && XMLHttpRequest.responseJSON) {
                $.notify(XMLHttpRequest.responseJSON.message, 'warn');
            }
        }
    });
};

var addlikeComment = function(commentNo) {
    console.log(commentNo);
    var url = _CONTEXT + '/open.knowledge/likecomment/' + commentNo;
    $.ajax({
        type : 'POST',
        url : url,
        success : function(data, dataType) {
            console.log(data);
            $('#like_comment_count_' + commentNo).text(data.count);
            $.notify(LABEL_LIKE, 'info');
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            if (XMLHttpRequest.status === 403 && XMLHttpRequest.responseJSON) {
                $.notify(XMLHttpRequest.responseJSON.message, 'warn');
            }
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







