$(function (){

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
                    $notificationTable.append("<tr id=" + response.name +"><td>" + response.name + "</td>" +
                    "<td>" + response.price + "</td>" +
                    "<td><button class='remove-notification btn btn-danger'  notification-name=" + response.name + "> X </button></td></tr>");
                }
                else{
                    $errorMessage.append("<p class='alert alert-danger'>" + response.success + "</p>");
                }
            }
        });
    });

    $("#notifications-container").on("click", ".remove-notification", function(){
        var notificationName = $(this).attr('notification-name');

        var input = {
            notificationName: notificationName
        };

        var $errorMessage = $('#error-message');
        $errorMessage.empty();
        var $notificationRow = $('#' + notificationName);


        $.ajax({
            type: 'DELETE',
            url: '/remove-notification',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(input),
            success: function(response){
                if(response.response.localeCompare('success') == 0){
                    $notificationRow.remove();
                }
                else{
                    $errorMessage.append("<p class='alert alert-danger'>" + response.response + "</p>");
                }
            }
        });

    });

});
