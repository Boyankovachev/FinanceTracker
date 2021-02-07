$(function (){

    var $name = $('#name');
    var $password = $('#password');

    $("#login").on("click", function(){
        var input = {
            username: $name.val(),
            password: $password.val()
        };
        $.ajax({
            type: 'POST',
            url: '/login',
            data: input
        });
    });
});
