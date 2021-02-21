$(function (){

    var $name = $('#name');

    $("#submit").on("click", function(){
        var input = {
            username: $name.val()
        };
        /*
        $.ajax({
            type: 'post',
            url: '/test',
            data: input
        });
        */
        $.post("/test", input);
    });
});

