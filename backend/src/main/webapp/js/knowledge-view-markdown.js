$(document).ready(function() {
    processDecoration('#content').then(function() {
        if ($('#typeId').val() === '-102') {
            // プレゼンテーションのタイプであった場合に、プレゼンテーションを生成する
            createPresentation($('#content'));
        }
    });
    processDecoration('.arrow_answer');
    processDecoration('.arrow_question');
});

