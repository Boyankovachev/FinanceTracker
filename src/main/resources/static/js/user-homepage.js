$(function (){

    $(".delete-asset").on("click", function(){
        var data = {
            assetType: $(this).attr('asset-type'),
            assetName: $(this).attr("asset-name")
        };

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
                    alert("An error occurred.Could not remove asset!");
                }
            }

        });
    });

});
