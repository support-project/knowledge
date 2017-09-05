$(document).ready(function() {
    var ctx = document.getElementById('cpChart').getContext('2d');
    
    moment.locale(_LANG);
    var m = moment();
    var labels = [];
    var data = [];
    for (var i = 0; i < 30; i++) {
        labels.push(m.format('L'));
        m.subtract(1, 'days');
        data.push(i);
    }
    labels.reverse();
    
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
    
    
});
