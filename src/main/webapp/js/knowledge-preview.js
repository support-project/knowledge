$(document).ready(function() {
});


//var emoji = window.emojiParser;
var preview = function() {
    parseMarkdown(
        $('#input_title').val(),
        $('#content').val(),
        '#preview'
    );
};


