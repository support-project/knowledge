$(document).ready(function() {
});


var preview = function() {
    parseMarkdown(
        $('#input_title').val(),
        $('#content').val(),
        '#preview'
    ).then(function() {
        return processMathJax('#preview');
    }).then(function() {
        if ($('input[name=typeId]:checked').val() === '-102') {
            // プレゼンテーションのタイプであった場合に、プレゼンテーションを生成する
            return createPresentation($($('#preview').children()[0]));
        }
    });
};


