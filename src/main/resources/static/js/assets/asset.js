$(function (){

    $("#header").load("/header");

    $("#confirm").on("click", function(){
        //on click to add purchase to any asset-type (except passive resource)
        var $quantity = $('#quantity');
        var $price = $('#price');
        var $date = $('#date');
        var $asset_type = $(this).attr("asset-type")
        var $asset_name = $("#asset-name");
        var $asset_symbol = $("#asset-symbol");

        var input = {
            quantity: $quantity.val(),
            price: $price.val(),
            date: $date.val(),
            assetType: $asset_type,
            assetName: unEntity($asset_name.html()),
            assetSymbol: unEntity($asset_symbol.html())
        };

        var $purchase_table = $('#purchase-table');
        var $error_message = $('#error-message');

        $.ajax({
            type: 'POST',
            url: '/add-purchase',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(input),
            success:function(response){
                if(response.success.localeCompare('success') == 0){
                    $purchase_table.append('<tr><td>' + response.price + '</td>' +
                        '<td>' + response.quantity + '</td>' +
                        '<td>' + response.date + '</td></tr>');
                }
                else{
                    $error_message.append('<p>' + response.success+ '</p>');
                }
            }
        });
    });

    $("#change-current-price").on("click", function(){
        //on click to change current price of passive asset
        var $price = $('#price');
        var $name = $('#name');
        var $priceText = $('#price-text');

        //alert("new price: " + $price.val() + "\nname: " + $name.html() + "\nold price: " + $priceText.html());

        var price = $price.val();
        var input = {
            price: price,
            name: $name.html()
        };

        $.ajax({
            type: 'PUT',
            url: '/edit-passive-resource',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(input),
            success:function(response){
                if(response.success.localeCompare('success') == 0){
                    $priceText.html(price);
                }
                else{
                    $error_message.empty();
                    $error_message.append('<p>' + response.success+ '</p>');
                }
            }
        });
    });

});

function unEntity(str){
    // & turns to &amp;/g
    //fix the issue
    if(typeof str !== 'undefined'){
        return str.replace(/&amp;/g, "&");
    }
}
