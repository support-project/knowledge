var shown = 0;
var notices = [];

var showNotice = function(idx) {
    var notice = notices[idx];
    shown = idx;
    parseMarkdown(notice.title, notice.message, '#notice_content_area', '#notice_title_area').then(function() {
        return processMathJax('#notice_content_area');
    });
    if (notice.showNextTime) {
        $('#showagain').prop('checked', true);
    } else {
        $('#showagain').prop('checked', false);
    }
    readMark(notice.no, notice.showNextTime);
    $('#noticeModal').modal('show');
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

var displayDate = function(str, invalidValue) {
    var format = 'YYYY-MM-DD';
    var m = moment(str);
    if (m.isValid()) {
        return m.format(format);
    } else {
        return invalidValue;
    }
};

$(document).ready(function() {
    var limit = 10;
    var offset = 0;
    var total = 0;
    
    var loadList = function () {
        $('#notices').html('<i class="fa fa-refresh fa-spin fa-3x fa-fw margin-bottom"></i>');
        $.ajax({
            type : 'get',
            url : _CONTEXT + '/open.api/mynotices?all=true'
        }).then(function(items) {
            $('#notices').html('');
            notices = items;
            if (!items || items.length == 0) {
                $('#notices').append('empty.');
            }
            
            items.forEach(function(item, index) {
                var start = displayDate(item.startDatetime, '-');
                var end = displayDate(item.endDatetime, '-');
                var list = '<a class="list-group-item" onclick="showNotice(' + index + ');">';
                list += '<h4 class="list-group-item-heading">' + item.title + '</h4>';
                list += '<p class="list-group-item-text">';
                list += '<i class="fa fa-calendar"></i>&nbsp;';
                list += start;
                list += '&nbsp;&nbsp;&nbsp;';
                list += '<i class="fa fa-calendar"></i>&nbsp;';
                list += end;
                list += '</p>';
                list += '</a>';
                $('#notices').append(list);
            });
        }, function(err) {
            $('#notices').html('');
            console.log(err);
        });
    };
    
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
    
    loadList(); // 起動時に読み込む
});
