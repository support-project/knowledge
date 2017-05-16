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
            
            var columns = [];
            var data = [];
            
            columns.push({
                field: 'answer_datetime',
                title: _ANSWER_DATETIME
            });
            columns.push({
                field: 'answer_user',
                title: _ANSWER_USER
            });
            result.survey.items.forEach(function(item) {
                columns.push({
                    field: item.itemNo,
                    title: item.itemName
                });
            });

            
            result.answers.forEach(function(answer) {
                var row = {};
                row.answer_datetime = answer.localDatetime;
//                row.answer_datetime = moment(answer.insertDatetime, 'YYYY-MM-DD HH:mm:ss.SSS').format('YYYY-MM-DD HH:mm');
                row.answer_user = answer.userName;
                
                answer.items.forEach(function(answerItem) {
                    row[answerItem.itemNo] = answerItem.itemValue;
                });
                data.push(row);
            });
            
            $('#table').bootstrapTable({
                columns: columns,
                data: data,
                showExport: true,
                exportTypes: ['json', 'xml', 'csv']
            });
            
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        });
    }
});