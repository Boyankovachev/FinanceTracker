$(function (){

    var $name = $('#name');

    $("#form").on("submit", function(e){
        alert("dd");
        e.preventDefault();
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

