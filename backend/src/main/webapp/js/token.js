$(document).ready(function(){
    var clipboard = new Clipboard('#copyaddon');
    clipboard.on('success', function(e) {
        $.notify(_MSG_COPIED, 'success');
    });
    $('.datepicker').datepicker({
        format: 'yyyy-mm-dd',
        startDate: '-1d'
    });
});