$(document).ready(function() {
    $('#notice_prev_button').prop('disabled', true);
    $('#notice_next_button').prop('disabled', true);
    $('#showagain').prop('checked', false);
    var shown = 0;
    var notices = [];
    
    var showNotice = function(notice) {
        parseMarkdown(notice.title, notice.message, '#notice_content_area', '#notice_title_area').then(function() {
            return processMathJax('#notice_content_area');
        });
        if (notice.showNextTime) {
            $('#showagain').prop('checked', true);
        } else {
            $('#showagain').prop('checked', false);
        }
        
        readMark(notice.no, notice.showNextTime);
    };
    
    var readMark = function(no, showNextTime) {
        if (_LOGIN_USER_ID) {
            if (!showNextTime) {
                showNextTime = 0;
            } else {
                showNextTime = 1;
            }
            var url = _CONTEXT + '/open.api/readmark/' + no + '?showNextTime=' + showNextTime;
            $.ajax({
                type : 'put',
                url : url
            }).then(function(success) {
                console.log(success);
            }, function(err) {
                console.log(err);
            });
        }
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
    
    $('#showagain').on('change', function () {
        var notice = notices[shown];
        if (!notice) {
            return;
        }
        if ($(this).prop('checked')) {
            readMark(notice.no, 1);
        } else {
            readMark(notice.no, 0);
        }
    });
    
    loadList();
});