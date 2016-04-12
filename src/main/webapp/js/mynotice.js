$(document).ready(function() {
    var shown = 0;
    var notices = [];
    
    var showNotice = function(notice) {
        parseMarkdown(notice.title, notice.message, '#notice_content_area');
    };
    
    var loadList = function () {
        $.ajax({
            type : 'get',
            url : _CONTEXT + '/open.api/mynotices'
        }).then(function(items) {
            notices = items;
            console.log(notices);
            if (notices.length > 0) {
                showNotice(notices[shown]);
                $('#noticeModal').modal('show');
                if (notices.length > 1) {
                    $('#notice_next_button').prop('disabled', false);
                }
            }
        }, function(err) {
            $('#notices').html('');
            console.log(err);
        });
    };
    
    $('#notice_next_button').click(function() {
        shown++;
        var notice = notices[shown];
        if (notice) {
            showNotice(notice);
            $('#notice_prev_button').prop('disabled', false);
            if (notices.length <= shown + 1) {
                $('#notice_next_button').prop('disabled', true);
            }
        }
    });
    
    $('#notice_prev_button').click(function() {
        shown--;
        var notice = notices[shown];
        if (notice) {
            showNotice(notice);
            $('#notice_next_button').prop('disabled', false);
            if (shown === 0) {
                $('#notice_prev_button').prop('disabled', true);
            }
        }
    });
    
    loadList();
});