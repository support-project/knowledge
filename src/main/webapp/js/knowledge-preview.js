$(document).ready(function() {
});


var preview = function() {
    parseMarkdown(
        $('#input_title').val(),
        $('#content').val(),
        '#preview'
    ).then(function() {
        return processMathJax('#preview');
    });
};


