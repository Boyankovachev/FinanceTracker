$(function (){

    $("#header").load("/header");

    $(".open-asset").on("click", function(){
        var data = {
            asset_type: $(this).attr('asset-type'),
            asset_name: $(this).attr("asset-name")
        };
            //$.ajax({
            //    type: 'GET',
            //    url: '/asset',
            //    data: data,
            //    success:function(response){
            //        alert(response);
            //        window.location.href = '/asset';
            //    }
        $.post("/asset", data);
    });

    $(".delete-asset").on("click", function(){
        var data = {
            assetType: $(this).attr('asset-type'),
            assetName: $(this).attr("asset-name")
        };

        var $error_message = $('#error-message');
        var id_of_table = $(this).attr("asset-name");
        var $table_row = $("#" + id_of_table);

        $.ajax({
            type: 'DELETE',
            url: '/remove-asset',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success:function(response){
                if(response.response.localeCompare('success') == 0){
                    $table_row.remove();
                }
                else{
                    $error_message.empty();
                    $error_message.append('<p>' + response.response+ '</p>');
                }
            }

        });
    });

});
