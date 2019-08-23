$(document).ready(function () {
    getTable();
});

function getTable() {
    let offerId;
    $.ajax({
        url: "/api/sender/my_offers",
        type: "GET",
        success: function (result) {
            $('#employerTable tbody').empty();
            $.each(result, function (offer, takers) {

                var offerRow =
                    '<div class="container-fluid">' +
                    '<div class="row">' +
                    '<div class="col-sm-1"></div>'+
                    '<div class="col-sm-10">' +
                    '<div class="container-fluid cards">' +
                    '<div class="card" style="margin-bottom: 1%" >' +
                    '<div class="card-header" style="color: white; background-color: #7295b1">';
                JSON.parse(offer, function (key, value) {
                    if (key == "id") {
                        offerId = value;
                        offerRow += ' Заказ№ ' + value + ' ';
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
                        offerRow += ' тип мусора ' + value;
                    // <div class="input-group mb-2">
                    //         <div class="input-group-prepend">
                    //         <div class="input-group-text">@</div>
                    //         </div>
                    //         <input type="text" class="form-control" id="inlineFormInputGroup" placeholder="Username">
                    //         </div>
                    }
                    if (key == "offerStatus") {
                        if (value == "ACTIVE") {
                            offerRow += '</div>' +
                                '<div class="card-body" style="background-color: aliceblue">';
                            $.each(takers, function (i, taker) {
                                offerRow += '<div class="row" style="margin-bottom: 1%">' +
                                    '<div class="col-sm-1"></div>' +
                                    '<div class="input-group col-sm-3">' +
                                    '<div class="input-group-prepend">'+
                                    '<span class="input-group-text"><i class="fas fa-user"></i></span>'+ '</div>'+
                                    '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="'+
                                    taker.name +'" disabled>' + '</div>' +
                                    '<div class="input-group col-sm-4">' +
                                    '<div class="input-group-prepend">'+
                                    '<span class="input-group-text"><i class="fas fa-envelope"></i></span>'+ '</div>'+
                                    '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="'
                                    + taker.email+'" disabled>' +
                                    '</div>' +
                                    '<button class="btn btn-primary btn-icon "' +
                                    'onclick="confirmOffer(' + taker.id +',' + offerId + ')">' +
                                     '<span class="icon"><i class="fas fa-check"></i></span>подтвердить' +
                                    '</button>' +
                                    '<a href="/chat" class="btn btn-info btn-icon " >' +
                                    '<span class="icon"><i class="fas fa-download"></i></span>чат' +
                                    '</a>' +
                                    '<div class="col-sm-1"></div>' +
                                    '</div>';
                                ;
                            });
                            offerRow += '</div>';
                        } else {
                            offerRow += '</div>' +
                                '<div class="card-body" style="background-color: aliceblue">' +
                                'На предложение пока никто не откликнулся :('
                                + '</div>';
                        }
                    }

                });
                offerRow += '<div class="card-footer" style="background-color: #7295b1">'+
                    '<div class="row">' +
                    '<div class="col-sm-10"></div>'+
                    '<button class="btn btn-light btn-circle"' +
                    'data-toggle="tooltip" data-placement="bottom" title="Радактировать предложение"' +
                    'style="margin-right: 1% " onclick=edit"(' + offerId + ')">' +
                    '<i class="fas fa-edit"></i>' +
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
                '</div>';

                offerRow += '<br>';


                $('#employerTable tbody').append(offerRow);

            });

        },
        error: function (message) {
            console.log(message);
        }
    });
}

function deleteOffer(offerId) {
    $.ajax({
        url: '/api/sender/deleteOffer/' + offerId,
        type: 'GET',
        success: function () {
            $('#employerTable tbody').empty();
            getTable();
        }
    });
}

function confirmOffer(takerId, offerId) {
    $.ajax({
        url: '/api/sender/confirmOffer/' + takerId + '/' + offerId,
        type: 'GET',
        success: function () {
            $('#employerTable tbody').empty();
            getTable();
        }
    });
}