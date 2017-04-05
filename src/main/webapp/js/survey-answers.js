$(document).ready(function() {
    var knowledgeId = $('#knowledgeId').val();
    if (knowledgeId) {
        $.ajax({
            url: _CONTEXT + '/protect.survey/report/' + knowledgeId,
            type: 'GET',
            timeout: 10000  // 単位はミリ秒
        }).done(function(result, textStatus, xhr) {
            if (!result.survey) {
                // 回答無し
                $('#dataSurveyAnswerTitle').text('no exist');
                return;
            }
            $('#dataSurveyAnswerTitle').text(result.survey.title);
            $('#dataSurveyAnswerCount').text(result.answers.length);
            
            var report = '';
            report += '<table class="table table-bordered table-striped table-sm"><thead><tr class="bg-primary">';
            report += '<td>' + _ANSWER_DATETIME + '</td>';
            report += '<td>' + _ANSWER_USER + '</td>';
            result.survey.items.forEach(function(item) {
                report += '<td>';
                report += item.itemName;
                report += '</td>';
            });
            report += '</tr></thead><tbody>';
            result.answers.forEach(function(answer) {
                report += '<tr>';
                report += '<td>';
                var m = moment(answer.insertDatetime, 'YYYY-MM-DD HH:mm:ss.SSS');
                console.log(m.format('YYYY-MM-DD HH:mm'));
                report += m.format('YYYY-MM-DD HH:mm');
                report += '</td>';
                report += '<td>';
                report += answer.userName;
                report += '</td>';
                
                answer.items.forEach(function(answerItem) {
                    report += '<td>';
                    report += answerItem.itemValue;
                    report += '</td>';
                })
                report += '</tr>';
            });
            report += '</tbody></table>';
            $('#dataSurveyAnswerValues').html(report);
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        });
    }
});