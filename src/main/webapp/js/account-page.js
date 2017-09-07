$(document).ready(function() {
    var userId = $('#userId').val();
    
    // 軸を作成
    moment.locale(_LANG);
    var m = moment();
    var labels = [];
    var ymds = [];
    for (var i = 0; i < 28; i++) {
        labels.push(m.format('L'));
        ymds.push(m.format('YYYYMMDD'));
        m.subtract(1, 'days');
    }
    labels.reverse();
    ymds.reverse();
    $.ajax({
        url: _CONTEXT + '/open.account/cp/' + userId,
        type: 'GET',
        timeout: 10000,  // 単位はミリ秒
    }).done(function(result, textStatus, xhr) {
        //console.log(result);
        var before = $('#point').val();
        var valmap = {};
        if (result.length > 0) {
            var hit = false;
            for (var i = 0; i < result.length; i++) {
                var item = result[i];
                if (!hit) {
                    before = item.before;
                }
                valmap[item.ymd] = item.total;
                if (ymds.includes(item.ymd)) {
                    hit = true;
                }
            }
        }
        //console.log(ymds);
        //console.log(valmap);
        var data = [];
        ymds.forEach(function(ymd) {
            if (valmap[ymd]) {
                data.push(valmap[ymd]);
                before = valmap[ymd];
            } else {
                data.push(before);
            }
        });
        
        var ctx = document.getElementById('cpChart').getContext('2d');
        var myChart = new Chart(ctx, {
          type: 'line',
          data: {
            labels: labels,
            datasets: [{
              label: 'Contribution Point',
              data: data,
              backgroundColor: "rgba(0,0,255,0.5)",
              steppedLine: 'before'
            }]
          }
        });
        
    }).fail(function(xhr, textStatus, error) {
        handleErrorResponse(xhr, textStatus, error);
    });
});
