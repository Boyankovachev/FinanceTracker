$(function (){
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

});