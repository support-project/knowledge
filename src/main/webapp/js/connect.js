$(document).ready(function() {
    $('#disconnect').click(function() {
        $('#form').attr('action', _CONTEXT + '/protect.connect/disconnect');
        $('#form').attr('method', 'POST');
        $('#form').submit();
    });
    
    $('#update').click(function() {
        $('#form').attr('action', _CONTEXT + '/protect.connect/update');
        $('#form').attr('method', 'POST');
        $('#form').submit();
    });
});
