$(document).ready(function() {
    $('#btnMarkAllAsRead').click(function() {
        console.log(UNREAD_LIST);
        if (UNREAD_LIST.length > 0) {
            var data = {
                    no: UNREAD_LIST.join(',')
            };
            $.ajax({
                url: _CONTEXT + '/protect.notification/markread',
                type: 'POST',
                data: data,
                timeout: 10000,
            }).done(function(result, textStatus, xhr) {
                console.log(result);
                location.reload();
            }).fail(function(xhr, textStatus, error) {
                console.log(error);
            });
        }
    });
});