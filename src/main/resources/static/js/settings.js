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

});
