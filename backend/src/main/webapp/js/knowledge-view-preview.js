$(document).ready(function() {
});


// 登録者で無い
var preview = function() {
    $.post(_CONTEXT + '/open.knowledge/marked', {
        title : '',
        content : $('#comment').val()
    }, function(data) {
        var html = '<div class="question_Box">';
        html += '<div class="question_image">';
        html += '<img src="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
//      html += '<img src="' + _CONTEXT + '/images/loader.gif" ';
//      html += 'data-echo="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
        html += 'alt="icon" width="64" height="64"/>';
        html += '</div>';
        html += '<div class="arrow_question">';
        
        html += '<div style="word-break:normal" id="content">';
        var content = data.content;
        html += content;
        html += '</div>';
        
        html += '</div><!-- /.arrow_question -->';
        html += '</div><!-- /.question_Box -->';
        
        var target = '#preview';
        var jqObj = $(target);
        jqObj.html(html);
        processDecoration(target).then(function() {
            return processMathJax(target);
        });
    });
};


//登録者の場合
var previewans = function() {
    $.post(_CONTEXT + '/open.knowledge/marked', {
        title : '',
        content : $('#comment').val()
    }, function(data) {
        var html = '<div class="question_Box">';
        html += '<div class="answer_image">';
        html += '<img src="' + _CONTEXT + '/open.account/icon/' + $('#loginuser').val() + '" ';
        html += 'alt="icon" width="64" height="64"/>';
        html += '</div>';
        html += '<div class="arrow_answer">';
        html += '<div style="word-break:normal" id="content">';
        var content = data.content;
        html += content;
        html += '</div>';
        html += '</div>';
        html += '</div>';
        
        var target = '#preview';
        var jqObj = $(target);
        jqObj.html(html);
        processDecoration(target).then(function() {
            return processMathJax(target);
        });
    });
};


