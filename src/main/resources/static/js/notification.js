$(function (){

    $("#header").load("/header");

    $("#add").on("click", function(){
        var $select = $('#select-notification');
        var $priceTarget = $('#price');
        var notificationType = $('option:selected', $select).attr("notification-type");
        var $notificationName = $('#name');

        var input = {
            notificationAsset: $select.val(),
            priceTarget: $priceTarget.val(),
            name: $notificationName.val(),
            notificationType: notificationType
        };

        var $errorMessage = $('#error-message');
        $errorMessage.empty();
        var $notificationTable = $('#notification-table');

        $.ajax({
            type: 'POST',
            url: '/add-notification',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(input),
            success: function(response){
                if(response.success.localeCompare('success') == 0){
                    $notificationTable.append("<tr><td>" + response.name + "</td>" +
                    "<td>" + response.price + "</td></tr>");
                }
                else{
                    $errorMessage.append("<p>" + response.success + "</p>");
                }
            }
        });
    });

});
