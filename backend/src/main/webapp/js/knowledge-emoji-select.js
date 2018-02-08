$(document).ready(function() {
    $('#emojiPeopleModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiPeopleModal');
    });
    $('#emojiNatureModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiNatureModal');
    });
    $('#emojiObjectsModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiObjectsModal');
    });
    $('#emojiPlacesModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiPlacesModal');
    });
    $('#emojiSymbolsModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiSymbolsModal');
    });
    $('#helpMarkdownModal').on('shown.bs.modal', function (event) {
        parseMarkdown(
            'Markdown Sample',
            $('#sampleMarkdownText').val(),
            '#markdownSamplePreview'
        ).then(function() {
            return processMathJax('#sampleMarkdownText');
        });
    });
});

var emojiSelect = function(id) {
    $(id).find('.name').each(function(i, block) {
        $(this).click(function(event) {
            var val = ' :' + $(this).text() + ': ';
            insertAtCaret('#content', val);
            $(id).modal('hide');
        });
    });
};
