$(document).ready(function() {
    var tz = jstz.determine();
    var now = moment();
    
    var loadEvents = function(m) {
        var data = {
            timezone: tz.name(),
            date: m.format('YYYYMM')
        };
        $.ajax({
            url: _CONTEXT + '/open.event/list',
            type: 'GET',
            data: data,
            timeout: 10000,
        }).done(function(result, textStatus, xhr) {
            logging(result);
            
            var lang = 'en';
            if (_LANG) {
                lang = _LANG;
            }
            $('#datepicker').datepicker({
                todayHighlight: false,
                language: lang,
                beforeShowDay : function(date) {
                    var d = moment(date);
                    logging(d.format());
                    for (var idx = 0; idx < result.length; idx++) {
                        var event = result[idx];
                        var ed = moment.utc(event.startDateTime).tz(data.timezone);
                        logging(ed.format());
                        if (ed.year() === d.year() && ed.month() === d.month() && ed.date() === d.date()) {
                            return {
                                tooltip: 'event',
                                classes: 'active'
                            }
                        }
                    }
                }
            });
            $('#datepicker').on("changeDate", function() {
                logging('changeDate');
                logging($('#datepicker').datepicker('getFormattedDate'));
            });
            $('#datepicker').on("changeMonth", function() {
                logging('changeMonth');
                logging($('#datepicker').datepicker('getFormattedDate'));
            });
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        }).always(function( jqXHR, textStatus ) {
        });
    };
    
    loadEvents(now);
});