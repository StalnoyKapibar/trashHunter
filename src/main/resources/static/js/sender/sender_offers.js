var ratingValue;
var offerToRateId;
var takerToRateId;

$(document).ready(function () {
    getTable();

    $("body").on('mouseover', '#stars li', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently mouse on

        // Now highlight all the stars that's not after the current hovered star
        $(this).parent().children('li.star').each(function(e){
            if (e < onStar) {
                $(this).addClass('hover');
            }
            else {
                $(this).removeClass('hover');
            }
        });

    }).on('mouseout', '#stars li', function(){
        $(this).parent().children('li.star').each(function(e){
            $(this).removeClass('hover');
        });
    });


    /* 2. Action to perform on click */
    $("body").on('click', '#stars li', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently selected
        var stars = $(this).parent().children('li.star');

        for (i = 0; i < stars.length; i++) {
            $(stars[i]).removeClass('selected');
        }

        for (i = 0; i < onStar; i++) {
            $(stars[i]).addClass('selected');
        }

        // JUST RESPONSE (Not needed)
        ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
        //responseMessage(msg);
    });

    $("body").on('click', '#sendRatedUser', function() {
        let buttonId = "#rateBtn" + offerToRateId;
        $.ajax({
            url: '/api/offer/rate_offer/' + takerToRateId + '/' + offerToRateId + '/' + ratingValue,
            type: 'GET',
            success: function () {
                $("#sendRatedUser").hide();
            }
        });
        $(buttonId).hide();
        $("#successBlock").html("<div style='width: 300px' class=\"alert alert-success\" role=\"alert\">\n" +
            "  Спасибо, ваша оценка учтена\n" +
            "</div>")
    });

});

function getTable() {
    var offerId;
    $.ajax({
        url: "/api/sender/my_offers",
        type: "GET",
        success: function (result) {
            $('#senderOffersTable tbody').empty();
            $.each(result, function (offer, takers) {
                let offerRow = '';
                let counter = 0;
                JSON.parse(offer, function (key, value) {
                    if (key == "id") {
                        offerId = value;
                        offerRow +=
                            '<div class="container-fluid" id="offer' + offerId +'">' +
                            '<div class="row">' +
                            '<div class="col-sm-1"></div>'+
                            '<div class="col-sm-10">' +
                            '<div class="container-fluid cards">' +
                            '<div class="card" id="'+ offerId +'" style="margin-bottom: 1%" >' +
                            '<div class="card-header" style="color: white; background-color: #4d90fe">'+
                            '<div class="row">'+'<div class="col-sm-10">' +
                            ' Заказ№ ' + value + ' ';
                    }
                    if (key == "weight") {
                        offerRow += ' вес: ' + value + 'кг ';
                    }
                    if (key == "volume") {
                        offerRow += ' объем: ' + value + 'м³ ';
                    }
                    if (key == "price") {
                        offerRow += ' цена: ' + value + 'руб ';
                    }
                    if (key == "trashType") {
                        offerRow += ' тип мусора: ';
                            if (value == 'METAL') { offerRow+='Метал';}
                            if (value == 'FOOD') { offerRow+='Отходы';}
                            if (value == 'WOOD') { offerRow+='Дерево';}
                            if (value == 'GLASS') { offerRow+='Стекло';}
                            if (value == 'PAPER') { offerRow+='Бумага';}
                            if (value == 'PLASTIC') { offerRow+='Пластик';}
                    }
                    if (key == "offerStatus") {
                        offerRow+= '</div>' +
                            '<a href="/sender/edit_offer/' + offerId + '" class="btn btn-light btn-circle"' +
                            'data-toggle="tooltip" data-placement="bottom" title="Радактировать предложение"' +
                            'style="margin-right: 2% " >' +
                            '<i class="fas fa-edit"></i>' +
                            '</a>' +
                            '<button class="btn btn-light btn-circle "'+
                            'data-toggle="tooltip" data-placement="bottom" title="сделать шаблоном"'+
                            'onclick="makeCompleteOffer(' + offerId + ')">'+
                            '<i class="fas fa-download"></i>' +
                            '</button>' +
                            '<button class="btn btn-light btn-circle "'+
                            'data-toggle="tooltip" data-placement="bottom" title="удалить предложение"'+
                            'onclick="deleteOffer(' + offerId + ')">'+
                            '<i class="fas fa-trash"></i>' +
                            '</button>'+'</div>';
                        if (value == "ACTIVE") {
                            offerRow += '</div>' +
                                '<div class="card-body" style="background-color: aliceblue">';
                            $.each(takers, function (i, taker) {
                                offerRow += '<div class="row" style="margin-bottom: 1%">' +
                                    '<div class="col-sm-1"></div>' +
                                    '<div class="input-group col-sm-4">' +
                                    '<div class="input-group-prepend">'+
                                    '<a href="/profile/' + taker.id + '" class="btn-image" ' +
                                    'data-toggle="tooltip" data-placement="bottom" title="просмотреть профиль">' +
                                    '<span style="padding: 0" class="input-group-text">' +
                                    '<img width="38" height="36" src="/image/avatar/'+ taker.id +'"></span>'+ '</a>' +'</div>'+
                                    '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="'+
                                    taker.name +'" disabled>' + '</div>' +
                                    '<div class="input-group col-sm-3">' +
                                    '<div class="input-group-prepend">'+
                                    '<span class="input-group-text"><i class="fas fa-envelope"></i></span>'+ '</div>'+
                                    '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="'
                                    + taker.email+'" disabled>' +
                                    '</div>' +
                                    '<button class="btn btn-primary btn-icon "' +
                                    'onclick="confirmOffer(' + taker.id +',' + offerId + ')">' +
                                     '<span class="icon"><i class="fas fa-check"></i></span>подтвердить' +
                                    '</button>' +
                                    '<a href="/chat/' + taker.id + '?offerId=' + offerId +'" class="btn btn-info btn-icon">' +
                                    '<span class="icon"><i class="fas fa-comments"></i></span>чат' +
                                    '</a>' +
                                    '<div class="col-sm-1"></div>' +
                                    '</div>';
                            });
                            offerRow += '</div>';
                        } else if (value == 'COMPLETE'){
                            offerRow += '</div>' +
                                '<div class="card-body" style="background-color: #ffa9af">' +
                                '<div class="row"> <div class="col-sm-1"></div>' +
                                '<div class="col-sm-6"><h5 >Востановить Предложение</h5></div>' +
                                '<div class="col-sm-5">' +
                                '<button class="btn btn-primary btn-icon " onclick="restoreOffer(' + offerId + ')">' +
                                '<span class="icon"><i class="fas fa-trash-restore"></i></span>востановить</button>';
                            if(takers.length !== 0){
                                offerRow += '<button class="btn btn-warning btn-icon" id="rateBtn' + offerId + '"' +
                                    ' onclick="rateOffer(' + offerId + ', ' + takers[0].id + ', \'' + takers[0].name +'\')">' +
                                    '<span class="icon"><i class="fas fa-award"></i></span>оценить</button>';
                            }
                            offerRow += '</div></div></div>';
                        } else if (value == 'TAKEN'){
                            offerRow += '</div>' +
                                '<div class="card-body" style="background-color: aliceblue">';
                            $.each(takers, function (i, taker) {
                                offerRow += '<div class="row" style="margin-bottom: 1%">' +
                                    '<div class="col-sm-1"></div>' +
                                    '<div class="input-group col-sm-4">' +
                                    '<div class="input-group-prepend">'+
                                    '<a href="/profile/' + taker.id + '" class="btn-image" ' +
                                    'data-toggle="tooltip" data-placement="bottom" title="просмотреть профиль">' +
                                    '<span style="padding: 0" class="input-group-text">' +
                                    '<img width="38" height="36" src="/image/avatar/'+ taker.id +'"></span>'+ '</a>' +'</div>'+
                                    '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="'+
                                    taker.name +'" disabled>' + '</div>'+
                                    '<div class="input-group col-sm-3">' +
                                    '<div class="input-group-prepend">'+
                                    '<span class="input-group-text"><i class="fas fa-envelope"></i></span>'+ '</div>'+
                                    '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="'
                                    + taker.email+'" disabled>' +
                                    '</div>' +
                                    '<button class="btn btn-primary btn-icon "' +
                                    'onclick="cancelOffer(' + offerId + ')">' +
                                    '<span class="icon"><i class="fas fa-window-close"></i></span>отказать' +
                                    '</button>' +
                                    '   <a href="/chat/' + taker.id + '?offerId=' + offerId +'" class="btn btn-info btn-icon ">' +
                                        '<span class="icon"><i class="fas fa-comments"></i></span>чат' +
                                        '</a>' +
                                        '<div class="col-sm-1"></div>' +
                                    '</div>';
                            });
                            offerRow += '</div>';
                        } else {
                            offerRow += '</div>' +
                                '<div class="card-body" style="background-color: #b3ffe3">' +
                                '<h4>На предложение пока никто не откликнулся :( </h4>'
                                + '</div>';
                        }
                    }
                });
                offerRow +=
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>'+
                '</div>' + getModalWindow();

                $('#senderOffersTable tbody').append(offerRow);

            });
        },
        error: function (message) {
            console.log(message);
        }
    });
}

function deleteOffer(offerId) {
    $.ajax({
        url: '/api/offer/deleteOffer/' + offerId,
        type: 'GET',
        success: function () {
             $('#offer' + offerId).hide();
        }
    });
}

function restoreOffer(offerId) {
    $.ajax({
        url: '/api/offer/restoreOffer/' + offerId,
        type: 'GET',
        success: function () {
            $('#senderOffersTable tbody').empty();
            getTable();
        }
    });
}

function rateOffer(offerId, takerId, takerName){
    $("#sendRatedUser").show();
    $("#successBlock").empty();
    offerToRateId = offerId;
    takerToRateId = takerId;
    $("#takerName").text(takerName);
    $("#rateOffer").modal("show");
}

function makeCompleteOffer(offerId) {
    $.ajax({
        url: '/api/offer/makeCompleteOffer/' + offerId,
        type: 'GET',
        success: function () {
            $('#senderOffersTable tbody').empty();
            getTable();
        }
    });
}

function cancelOffer(offerId) {
    $.ajax({
        url: '/api/offer/cancelOffer/' + offerId,
        type: 'GET',
        success: function () {
            $('#senderOffersTable tbody').empty();
            getTable();
        }
    });
}

function confirmOffer(takerId, offerId) {
    $.ajax({
        url: '/api/offer/confirmOffer/' + takerId + '/' + offerId,
        type: 'GET',
        success: function () {
            $('#senderOffersTable tbody').empty();
            getTable();
        }
    });
}

function getModalWindow() {
    return "<div class=\"modal fade\" id=\"rateOffer\" role=\"dialog\">\n" +
        "        <div class=\"modal-dialog\">\n" +
        "            <!-- Modal content-->\n" +
        "            <div class=\"modal-content\">\n" +
        "                <div class=\"modal-header\">\n" +
        "                    <h4 class=\"modal-title\">Оценка приемщика <span id='takerName'></span></h4>\n" +
        "                    <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
        "                </div>\n" +
        "                <div class=\"modal-body\">\n" +
        "                    <p>Оцени этого уважаемого пидора</p>\n" +
        "                    <div class='rating-stars text-center'>\n" +
        "                       <ul id='stars'>\n" +
        "                         <li class='star' title='Poor' data-value='1'>\n" +
        "                           <i class='fa fa-star fa-fw'></i>\n" +
        "                         </li>\n" +
        "                         <li class='star' title='Fair' data-value='2'>\n" +
        "                           <i class='fa fa-star fa-fw'></i>\n" +
        "                         </li>\n" +
        "                         <li class='star' title='Good' data-value='3'>\n" +
        "                           <i class='fa fa-star fa-fw'></i>\n" +
        "                         </li>\n" +
        "                         <li class='star' title='Excellent' data-value='4'>\n" +
        "                           <i class='fa fa-star fa-fw'></i>\n" +
        "                         </li>\n" +
        "                         <li class='star' title='WOW!!!' data-value='5'>\n" +
        "                           <i class='fa fa-star fa-fw'></i>\n" +
        "                         </li>\n" +
        "                       </ul>\n" +
        "                   </div>" +
        "                </div>\n" +
        "                <div id='successBlock'></div>" +
        "                <div class=\"modal-footer\">\n" +
        "                    <button id='sendRatedUser' type=\"button\" class=\"btn btn-success\">Оценить</button>\n" +
        "                    <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Закрыть</button>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>";
}

