$(document).ready(function() {
    processDecoration('#content');
    $('.arrow_question').each(function(i, block) {
        var content = $(this).html().trim();
        var jqObj = $(this);
        jqObj.html(content);
        processDecoration(jqObj);
    });
    $('.arrow_answer').each(function(i, block) {
        var content = $(this).html().trim();
        var jqObj = $(this);
        jqObj.html(content);
        processDecoration(jqObj);
    });
});
