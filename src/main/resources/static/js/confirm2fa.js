$(function (){
    var $code = $('#code');

    $("#confirm").on("click", function(){
        var input = {
            code: $code.val()
        };
        $.ajax({
            type: 'POST',
            url: '/confirm-login',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(input),
            success: function(response){
                 if(response.response.localeCompare('success') == 0){
                    //check notes - 3.2.2021
                    $.get("/redirect-to-user");
                    window.location.replace("/user");
                 }
                 else{
                    $('#error-div').append('<p>' + response.response + '</p>');
                    setTimeout(() => {
                      window.location.replace("/login");
                    }, 1000);
                 }
            }

        });
    });
});

