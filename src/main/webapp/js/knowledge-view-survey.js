$(document).ready(function() {
    var knowledgeId = $('#knowledgeId').val();
    $.ajax({
        type : 'GET',
        url : _CONTEXT + '/protect.survey/load/' + knowledgeId
    }).done(function(data) {
        console.log(data);
        $('#btnAnswerSurvey').removeClass('hide');
        $('#modalAnswerSurveyLabel').text(data.title);
        $('#surveyDescription').text(data.description);
        document.__add_Template_Edit_Item(data);
    }).fail(function(err) {
        console.log(err);
    });
});
