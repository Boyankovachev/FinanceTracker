$(function (){

    var $period = $('#period');
    setupChart($('option:selected', $period).val());

});

function setupChart(period){
    var $symbol = $('#asset-symbol');
    var $type = $('#head');

    var input = {
        symbol: $symbol.html(),
        type: $type.html(),
        period: period
    };

    $.ajax({
        type: 'POST',
        url: '/get-chart-data',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(input),
        error: function(e){
            console.log(e);
        },
        success: function(response){
            var dataPoints = [];
            for(const point of response){
                temp = {
                    x: new Date(point.year, point.month, point.day),
                    y: point.price
                };
                dataPoints.push(temp);
            }
            plotChart(dataPoints);
        }
    });
}

function plotChart(data) {
    var options = {
        animationEnabled: true,
        title:{
            text: "time"
        },
        axisX: {
            valueFormatString: "MMM YYY"
        },
        axisY: {
            title: "price",
        },
        data: [{
            yValueFormatString: "#0.0"%"",
            xValueFormatString: "MMM YYY",
            type: "spline",
            dataPoints: data //add the data points
        }]
    };
    $("#chartContainer").empty();
    $("#chartContainer").CanvasJSChart(options);
}
