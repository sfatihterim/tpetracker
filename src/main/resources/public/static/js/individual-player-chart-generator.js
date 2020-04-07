var ctx = document.getElementById('myChart');
new Chart(ctx, {
    type: 'line',
    data: {
        labels: labelSet,
        datasets: [
            {label: 'TPE', lineTension: 0, data: dataSet}
        ]
    },
    options: {
        responsive: !0,
        title: {
            display: !0,
            text:
                'TPE History'
        },
        legend: {
            display: !1
        },
        tooltips: {
            mode: 'index',
            intersect: !1,
            callbacks: {
                label: function (e) {
                    return e.yLabel
                }
            }
        },
        hover: {
            mode: 'nearest',
            intersect: !0
        },
        scales: {
            yAxes: [{ticks: {min: 0}}]
        }
    }
});
