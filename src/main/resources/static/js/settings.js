$(function (){

    $("#change2FA").on("click", function(){
        var value = $('input[name="2fa"]:checked').val();
        $.ajax({
            type: 'POST',
            url: '/change-2fa',
            data: value,
            success: function(response){
                if(response.success.localeCompare('success') == 0){
                    var $info = $('#2fa');
                    $info.html(response.response);
                }
            }
        });
    });

    $("#change-username-button").on("click", function(){
        var value = $('#change-username').val();
        $('#error-message').empty();
        $.ajax({
            type: 'POST',
            url: '/change-username',
            data: value,
            success: function(response){
                if(response.success.localeCompare('success') == 0){
                    var $info = $('#username');
                    $info.html(response.response);
                }
                else{
                    $('#error-message').html('<p>' + response.success + '</p>');
                }
            }
        });
    });

    $("#change-email-button").on("click", function(){
        var value = $('#change-email').val();
        $('#error-message').empty();
        $.ajax({
            type: 'POST',
            url: '/change-email',
            data: value,
            success: function(response){
                if(response.success.localeCompare('success') == 0){
                    var $info = $('#email');
                    $info.html(response.response);
                }
                else{
                    $('#error-message').html('<p>' + response.success + '</p>');
                }
            }
        });
    });

    $("#change-password").on("click", function(){
        var currentPassword = $('#current-password').val();
        var newPassword = $('#new-password').val();
        var newPasswordRepeat = $('#repeat-new-password').val();
        var $error_message = $('#error-message');
        $error_message.empty();

        var input = {
            currentPassword: currentPassword,
            newPassword: newPassword,
            newPasswordRepeat: newPasswordRepeat
        };

        $.ajax({
            type: 'POST',
            url: '/change-password',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(input),
            success:function(response){
                if(response.response.localeCompare('success') == 0){
                    alert("Password changed successfully!");
                }
                else{
                    $error_message.append('<p>' + response.response+ '</p>');
                }
            }
        });
    })

});
