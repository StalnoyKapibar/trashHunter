var senderToRateId;
var offerToRateId;
var ratingValue = 0;

$(document).ready(function () {
   getTable(doFilter('TakerActive'));

    $("body").on('mouseover', '#stars li', function(){
        var onStar = parseInt($(this).data('value'), 10);
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

    $("body").on('click', '#stars li', function(){
        var onStar = parseInt($(this).data('value'), 10);
        var stars = $(this).parent().children('li.star');
        for (i = 0; i < stars.length; i++) {
            $(stars[i]).removeClass('selected');
        }
        for (i = 0; i < onStar; i++) {
            $(stars[i]).addClass('selected');
        }
        ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
    });

    $("body").on('click', '#sendRatedUser', function() {
        $.ajax({
            url: '/api/offer/rate_offer/' + senderToRateId + '/' + offerToRateId + '/' + ratingValue,
            type: 'GET',
            success: function () {
                $("#sendRatedUser").hide();
                $("#completeOffer").hide();
            }
        });
        $("#successBlock").html("<div style='width: 300px' class=\"alert alert-success\" role=\"alert\">\n" +
            "  Спасибо, ваша оценка учтена\n" +
            "</div>")
    });

    $("body").on('click', '#completeOffer', function() {
        $("#sendRatedUser").hide();
        $("#completeOffer").hide();
    });

    $("body").on('hidden.bs.modal', "#rateOffer",  function () {
        if ( $("#sendRatedUser").css('display') == 'none' || $("#sendRatedUser").css("visibility") == "hidden") {
            makeCompleteOffer(offerToRateId);
        }
    })
});

function getTable(data) {
    console.log(data);
    $('#takerOffersTable tbody').empty();
    $.each(data, function (i, offer) {
        let offerRow = '';
            offerRow +=
                '<div class="container-fluid" id="offer' + offer.id + '">' +
                '<div class="row">' +
                '<div class="col-sm-10">' +
                '<div class="container-fluid cards">' +
                '<div class="card" id="' + offer.id + '" style="margin-bottom: 1%" >' +
                '<div class="card-header" style="color: white; background-color: #4d90fe">' +
                '<div class="row">'+
                '<div class="col-sm-10">'+
                ' Заказ№ ' + offer.id + ' ' +
                ' вес: ' + offer.weight + 'кг ' +
                ' объем: ' + offer.volume + 'м³ ' +
                ' цена: ' + offer.price + 'руб ';
                offerRow += ' тип мусора: ';
                if (offer.trashType == 'METAL') { offerRow+='Метал';}
                if (offer.trashType == 'FOOD') { offerRow+='Оходы';}
                if (offer.trashType == 'WOOD') { offerRow+='Дерево';}
                if (offer.trashType == 'GLASS') { offerRow+='Стекло';}
                if (offer.trashType == 'PAPER') { offerRow+='Бумага';}
                if (offer.trashType == 'PLASTIC') { offerRow+='Пластик';}
                offerRow += '</div>'+
                '<a href="/taker/offer_page/' + offer.id + '" class="btn btn-light btn-circle"' +
                'data-toggle="tooltip" data-placement="bottom" title="просмотреть предложение"' +
                'style="margin-right: 1% " >' +
                '<i class="fas fa-bars"></i>' +
                '</a>' +
                '<button class="btn btn-light btn-circle "' +
                'data-toggle="tooltip" data-placement="bottom" title="отказаться"' +
                'onclick="cancelOffer(' + offer.id + ')">' +
                '<i class="fas fa-window-close"></i>' +
                '</button>' +
                '</div>' +
                '</div>' +
                '<div class="card-body" style="background-color: #ffffff">' +
                '<div class="row" style="margin-bottom: 1%">' +
                '<div class="col-sm-1"></div>' +
                '<div class="input-group col-sm-4">' +
                '<div class="input-group-prepend">' +
                '<a href="/profile/' + offer.sender.id + '" class="btn-image" ' +
                'data-toggle="tooltip" data-placement="bottom" title="просмотреть профиль">' +
                '<span style="padding: 0" class="input-group-text">' +
                '<img width="38" height="36" src="/image/avatar/' + offer.sender.id + '"></span>' + '</a>' + '</div>' +
                '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="' +
                offer.sender.name + '" disabled>' + '</div>' +

                '<div class="input-group col-sm-3">' +
                '<div class="input-group-prepend">' +
                getModalWindow() +
                '<span class="input-group-text"><i class="fas fa-envelope"></i></span>' +
                '</div>' +
                '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="' +
                offer.sender.email + '" disabled>' +
                '</div>';
            if (offer.offerStatus == 'TAKEN') {
                offerRow += '<button class="btn btn-primary btn-icon "' +
                    'onclick="rateSender(' + offer.id + ', ' + offer.sender.id + ', \'' + offer.sender.name +'\')">' +
                    '<span class="icon"><i class="fas fa-truck-loading"></i></span>мусор вывезен' +
                    '</button>';
            }
            if (offer.offerStatus == 'ACTIVE') {
                offerRow += '<button class="btn btn-warning btn-icon "disabled>' +
                    '<span class="icon"><i class="fas fa-spinner"></i></span>ожидание ответа' +
                    '</button>';
            }
            offerRow += '<a href="/chat/' + offer.sender.id + '?offerId=' + offer.id + '" class="btn btn-info btn-icon" >' +
                '<span class="icon"><i class="fas fa-comments"></i></span>чат' +
                '</a>' +
                '<div class="col-sm-1"></div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
            $('#takerOffersTable tbody').append(offerRow);
        });

}

function doFilterInit() {
    getTable(doFilter('TakerActive'));
}

function rateSender(offerId, senderId, senderName) {
    $("#sendRatedUser").show();
    $("#completeOffer").show();
    $("#successBlock").empty();
    offerToRateId = offerId;
    senderToRateId = senderId;
    $("#takerName").text(senderName);
    $("#rateOffer").modal("show");
}

function makeCompleteOffer(offerId) {
    $.ajax({
        url: '/api/offer/makeCompleteOfferByTaker/' + offerId,
        type: 'GET',
        success: function () {
            $('#takerOffersTable tbody').empty();
            getTable(doFilter('TakerActive'));
        }
    });
}

function cancelOffer(offerId) {
    $.ajax({
        url: '/api/offer/cancelOffer/' + offerId,
        type: 'GET',
        success: function () {
            $( '#offer'+offerId).hide();
        }
    });
}

function getModalWindow() {
    return "<div class=\"modal fade\" id=\"rateOffer\" role=\"dialog\">\n" +
        "        <div class=\"modal-dialog\">\n" +
        "            <div class=\"modal-content\">\n" +
        "                <div class=\"modal-header\">\n" +
        "                    <h4 class=\"modal-title\">Оценка сдатчика <span id='takerName'></span></h4>\n" +
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
        "                    <button id='completeOffer' type=\"button\" class=\"btn btn-white\" data-dismiss=\"modal\">Нет, спасибо</button>\n" +
        "                    <button id='sendRatedUser' type=\"button\" class=\"btn btn-success\">Оценить</button>\n" +
        "                    <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Закрыть</button>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>";
}
