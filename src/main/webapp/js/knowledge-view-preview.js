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
        
        html += '<div style="word-break:break-all" id="content">';
        var content = data.content;
        html += content;
        html += '</div>';
        
        html += '</div><!-- /.arrow_question -->';
        html += '</div><!-- /.question_Box -->';
        
        var jqObj = $('#preview');
        jqObj.html(html);
        jqObj.find('code').addClass('hljs');
        codeHighlight(jqObj)
        .then(function() {
            var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
            jqObj.html(content);
            return;
        }).then(function () {
            jqObj.find('a.oembed').oembed();
            // call slide.js
            showSlide('#preview');
            // call MathJax
            MathJax.Hub.Queue(function() {
                $('#preview').find('.lang-math').each(function(i, block) {
                    var jqobj = $(this);
                    jqobj.addClass('hljs');
                    MathJax.Hub.Typeset(jqobj[0]);
                });
            });
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
        html += '<div style="word-break:break-all" id="content">';
        var content = data.content;
        html += content;
        html += '</div>';
        html += '</div>';
        html += '</div>';
        
        var jqObj = $('#preview');
        jqObj.html(html);
        jqObj.find('code').addClass('hljs');
        codeHighlight(jqObj)
        .then(function() {
            var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
            jqObj.html(content);
        }).then(function () {
            jqObj.find('a.oembed').oembed();
            // call slide.js
            showSlide('#preview');
            // call MathJax
            MathJax.Hub.Queue(function() {
                $('#preview').find('.lang-math').each(function(i, block) {
                    var jqobj = $(this);
                    jqobj.addClass('hljs');
                    MathJax.Hub.Typeset(jqobj[0]);
                });
            });
        });
    });
};


