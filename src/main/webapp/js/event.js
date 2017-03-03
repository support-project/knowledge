$(document).ready(function() {
    var tz = jstz.determine();
    var now = moment();
    if (_SELECTED_DATE) {
        now = moment(_SELECTED_DATE, 'YYYYMMDD');
    }
    var result = [];
    
    var loadEvents = function(m) {
        logging(m.format('YYYY-MM-DD'));
        $('#loading').removeClass('hide');
        $("#datepicker").datepicker('remove');
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
            $('#datepicker').datepicker({
                todayHighlight: false,
                language: lang,
                defaultViewDate: {
                    year: m.year(),
                    month: m.month(),
                    day: 1
                },
                beforeShowDay : function(date) {
                    var d = moment(date);
                    //logging(d.format());
                    for (var idx = 0; idx < result.length; idx++) {
                        var event = result[idx];
                        var ed = moment.utc(event.startDateTime).tz(data.timezone);
                        //logging(ed.format());
                        if (ed.year() === d.year() && ed.month() === d.month() && ed.date() === d.date()) {
                            return {
                                tooltip: 'event',
                                classes: 'today'
                            }
                        }
                    }
                }
            });
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        }).always(function( jqXHR, textStatus ) {
            $('#loading').addClass('hide');
        });
    };
    
    var lang = 'en';
    if (_LANG) {
        lang = _LANG;
    }
    $('#datepicker').datepicker({
        todayHighlight: false,
        language: lang
    });
    $('#datepicker').on("changeDate", function(e) {
        logging('changeDate');
        var current = moment(e.date);
        location.href = _CONTEXT + '/open.knowledge/events?date=' + current.format('YYYYMMDD') + '&timezone=' + encodeURIComponent(tz.name());
    });
    $('#datepicker').on("changeMonth", function(e) {
        var current = moment(e.date);
        logging('changeMonth:' + current.format());
        loadEvents(current);
    });
   
    loadEvents(now);
});