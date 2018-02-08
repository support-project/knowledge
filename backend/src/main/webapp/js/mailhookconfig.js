$(document).ready(function() {
    $('#addProperty').click(function() {
        addProperty('','');
    })
    $('#removeProperty').click(function() {
        $('#properties').find('.form-group:last-child').remove();
        
    });
});


var addProperty = function(key, value) {
    var html = '';
    html += '<div class="form-group form-inline">';
        html += '<label class="col-sm-2 control-label">Property</label>';
        html += '<div class="col-sm-5 input-group">';
            html += '<div class="input-group-addon"><i class="fa fa-database" aria-hidden="true"></i></div>';
            html += '<input type="text" class="form-control" name="propertyKey" value="' + key + '" />';
        html += '</div>';
        html += '<div class="col-sm-5 input-group">';
            html += '<div class="input-group-addon"><i class="fa fa-play-circle" aria-hidden="true"></i></div>';
            html += '<input type="text" class="form-control" name="propertyValue" value="' + value + '" />';
        html += '</div>';
    html += '</div>';
    $('#properties').append(html);
};