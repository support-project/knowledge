var items;
var notice_no = -1;

var editNotice = function(idx) {
    var item = items[idx];
    console.log(item);
    notice_no = item.no;
    $('#input_title').val(item.title);
    $('#input_content').val(item.message);
    $('#input_startdate').val(displayDate(item.startDatetime, ''));
    $('#input_enddate').val(displayDate(item.endDatetime, ''));
    $('#deleteButton').removeClass('hide');
    $('#validate_msg').addClass('hide');
    $('#editModal').modal('show');
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
            url : _CONTEXT + '/admin.api/notices?limit=' + limit + '&offset=' + offset
        }).then(function(notices) {
            $('#notices').html('');
            console.log(notices);
            total = notices.total;
            items = notices.items;
            if (!items || items.length == 0) {
                $('#notices').append('empty.');
            }
            
            items.forEach(function(item, index) {
                var start = displayDate(item.startDatetime, '-');
                var end = displayDate(item.endDatetime, '-');
                var list = '<a class="list-group-item" onclick="editNotice(' + index + ');">';
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
    $('.datepicker').datepicker({
        format: 'yyyy-mm-dd',
        startDate: '-1d'
    });

    $('#tabPreview').click(function() {
        doPreview('#input_title','#input_content','#preview');
    });
    
    $('.previousButton').click(function() {
        if (offset === 0) {
            return;
        }
        offset = offset - limit;
        if (offset < 0) {
            offset = 0;
        }
        loadList();
    });
    
    $('.nextButton').click(function() {
        offset = offset + limit;
        if (total < offset) {
            offset = offset - limit;
            return;
        }
        loadList();
    });
    
    $('.addButton').click(function() {
        notice_no = -1;
        $('#validate_msg').addClass('hide');
        $('#deleteButton').addClass('hide');
    });
    
    $('#saveButton').click(function(){
        $('#validate_msg').addClass('hide');
        var data = {
            title: $('#input_title').val(),
            message: $('#input_content').val(),
            startDatetime: $('#input_startdate').val(),
            endDatetime: $('#input_enddate').val()
        };
        if (!data.title) {
            $('#validate_msg').html('title is required.');
            $('#validate_msg').removeClass('hide');
            return;
        }
        console.log(data);
        var type = 'post';
        var url = _CONTEXT + '/admin.api/notices';
        if (notice_no > 0) {
            type = 'put';
            url = url + '/' + notice_no;
        }
        $.ajax({
            type : type,
            url : url,
            data: JSON.stringify(data),
            contentType: 'application/json', 
            dataType: "json"
        }).then(function(success) {
            console.log(success);
            offset = 0;
            $.notify(success.message, 'success');
            loadList();
            $('#editModal').modal('hide');
        }, function(err) {
            console.log(err);
        });
    });
    
    $('#deleteButton').click(function() {
        $.ajax({
            type : 'delete',
            url : _CONTEXT + '/admin.api/notices' + '/' + notice_no,
            dataType: "json"
        }).then(function(success) {
            console.log(success);
            offset = 0;
            $.notify(success.message, 'success');
            loadList();
            $('#editModal').modal('hide');
        }, function(err) {
            console.log(err);
        });
    });
    
    loadList(); // 起動時に読み込む
});
