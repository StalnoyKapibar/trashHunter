$(document).ready(function () {
    getTable();
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
                        offerRow += ' тип мусора: ' + value;
                    }
                    if (key == "offerStatus") {
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
                                    '<a href="/chat/?partnerId=' + taker.id + '&offerId=' + offerId +'" class="btn btn-info btn-icon " target="_blank">' +
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
                                '<span class="icon"><i class="fas fa-trash-restore"></i></span>востановить</button>' +
                                '<button class="btn btn-warning btn-icon" id="rateBtn" onclick="rateOffer(' + offerId + ')">' +
                                '<span class="icon"><i class="fas fa-trash-restore"></i></span>оценить сделку' +
                                '</button></div>' +'</div>'+
                                 '</div>';
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
                                    '   <a href="/chat/?partnerId=' + taker.id + '&offerId=' + offerId +'" class="btn btn-info btn-icon " target="_blank" >' +
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
                offerRow += '<div class="card-footer" style="background-color: #4d90fe">'+
                    '<div class="row">' +
                    '<div class="col-sm-10"></div>'+
                    '<a href="/sender/edit_offer/' + offerId + '" class="btn btn-light btn-circle"' +
                    'data-toggle="tooltip" data-placement="bottom" title="Радактировать предложение"' +
                    'style="margin-right: 1% " >' +
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
                    '</button>' +
                    '</div>'+
                    '</div>';
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
             $('#offer'+offerId).hide();
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

function rateOffer(offer){
    $("#takerName").text(offer.)
    $("#rateOffer").modal("show");
    console.log(123);
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
        "                    <h4 class=\"modal-title\">Оценка юзера <span id='takerName'></span></h4>\n" +
        "                    <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
        "                </div>\n" +
        "                <div class=\"modal-body\">\n" +
        "                    <p>Оцени этого пидора</p>\n" +
        "                </div>\n" +
        "                <div class=\"modal-footer\">\n" +
        "                    <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>";
}