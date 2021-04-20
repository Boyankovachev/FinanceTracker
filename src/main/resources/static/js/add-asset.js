$(function (){

    //to add stock template as default
    var $addDiv = $('#add');
    var template = $('#stock').html();
    $addDiv.append(template);
    var $purchaseDiv = $('#purchase');
    var templatePurchase = $('#purchase-info').html();
    $purchaseDiv.append(templatePurchase);


    //".add-button" is dynamically loaded
    //so i attach it to the div with #add id,
    //which is always present at the DOM
    $("#add").on("click", ".add-button", function(){ //on click add button function
        var $addButton = $(".add-button");
        var assetType = $addButton.attr('asset-type');

        if(assetType.localeCompare('passive-resource') != 0){
            //alert("TVA E STOCK INDEX ILI CRYPTO");
            var $quantity = $('#quantity');
            var $price = $('#price');
            var $date = $('#date');
            var $symbol = $('.value');
            var input = {
                assetType: assetType,
                symbol: $symbol.val(),
                quantity: $quantity.val(),
                price: $price.val(),
                date: $date.val(),
            };

            $.ajax({
                type: 'POST',
                url: '/add-active-asset',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(input),
                success:function(response){
                    $('#response').empty();
                    $('#response').append('<p class="alert alert-success">' + response.response + '</p>');
                },
                error: function(response) {
                    alert("something went wrong");
                }
            });
        }
        else if(assetType.localeCompare('passive-resource') == 0){
            //alert("TVA E PASSIVE RESOURCE");
            var $name = $('.value');
            var $price = $('#passive-price');
            var $date = $('#passive-date');
            var $description = $('#description');
            var $currency = $('#currency');
            var $currencySymbol = $('#currency-symbol');

            var input = {
                name: $name.val(),
                price: $price.val(),
                date: $date.val(),
                description: $description.val(),
                currency: $currency.val(),
                currencySymbol: $currencySymbol.val()
            };

            $.ajax({
                type: 'POST',
                url: '/add-passive-asset',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(input),
                success: function(response){
                    $('#response').empty();
                    $('#response').append('<p class="alert alert-success">' + response.response + '</p>');
                },
                error: function(response) {
                    alert("something went wrong");
                }
            });
        }
    });
});


function onChange(assetType){
    var $addDiv = $('#add');
    var template = $('#' + assetType).html();
    $addDiv.empty();
    $addDiv.append(template);

    var $purchaseDiv = $('#purchase');
    var templatePurchase = $('#purchase-info').html();
    $purchaseDiv.empty();
    if(assetType.localeCompare('passive-resource') != 0){
        $purchaseDiv.append(templatePurchase);
    }
};
