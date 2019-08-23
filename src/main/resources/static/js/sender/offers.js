$(document).ready(function () {
    // 'use strict';
    // feather.replace();

    getTable();
    // $('#sortBy option').each(function () {
    //     var param = $(this);
    //     if (location.href.indexOf(param.val()) !== -1) {
    //         param.prop('selected', true);
    //     }
    // });

});

function getTable() {
    let offerId;
    $.ajax({
        url: "/api/sender/my_offers",
        type: "GET",
        success: function (result) {
            let res = result;
            $('#employerTable tbody').empty();
            $.each(result, function (offer, takers) {

                var offerRow =
                    '<div class="container-fluid">' +
                    '<div class="row">' +
                    '<div class="col-sm-2"></div>'+
                    '<div class="col-sm-10">' +
                    '<div class="container-fluid cards">' +
                    '<div class="card" >' +
                    '<div class="card-header" style="background-color: #7295b1">';
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

                    }
                    if (key == "offerStatus") {
                        if (value == "ACTIVE") {
                            offerRow += '</div>' +
                                '<div class="card-body" style="background-color: aliceblue">';
                            $.each(takers, function (i, taker) {
                                offerRow += '<div class="row" style="margin-bottom: 1%">' +
                                    '<div class="col-sm-1"></div>' +
                                    '<div class="col-sm-7">' + taker.name + ' ' +
                                    taker.email +
                                    '</div>' +
                                    '<button class="btn btn-primary col-sm-1"' +
                                    ' style="margin-right: 1%"' +
                                    'onclick="confirmOffer(' + taker.id +',' + offerId + ')">' +
                                    + 'Подвтведрдить' +
                                    '</button>' +
                                    '<a href="/chat" class="btn btn-secondary col-sm-1" >' +
                                    +'Чат' +
                                    '</a>' +
                                    '<div class="col-sm-1"></div>' +
                                    '</div>';
                                ;
                            });
                            offerRow += '</div>';
                        } else {
                            offerRow += '</div>' +
                                +'<div class="card-body" style="background-color: aliceblue">' +
                                'На предложение пока никто не откликнулся :('
                                + '</div>';
                        }
                    }

                });
                offerRow += '<div class="card-footer" style="background-color: #7295b1">'+
                    '<div class="row">' +
                    '<div class="col-sm-9"></div>'+
                    '<button class="btn btn-info col-sm-1" style="margin-right: 1% " onclick="(' + offerId + '" >' +
                    +'<span>change</span>' +
                    '</button>' +
                    '<button class="btn btn-info col-sm-1" onclick="deleteOffer(' + offerId+ ')" >' +
                    +'delete' +
                    '</button>' +
                    '</div>'+
                    '<div class="col-sm-1"></div>' +'</div>';
                offerRow +=
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>'
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

// function sort() {
//     if (document.getElementById('sort').checked) {
//         $('#sort').addClass("is-valid");
//         $('#employerTable tbody').empty();
//         getTable('active');
//
//     } else {
//         $('#sort').removeClass("is-valid");
//         $('#employerTable tbody').empty();
//         getTable('all');
//     }
// }
function offerDelete(offerId) {
    $.ajax({
        url: '/api/s/confirmOffer/' + id,
        type: 'GET',
        success: function () {
            $('#employerTable tbody').empty();
            getTablle();
        }
    });
}

function confirmOffer(id) {
    $.ajax({
        url: '/api/sender/confirmOffer/' + id,
        type: 'GET',
        success: function () {
            $('#employerTable tbody').empty();
            getTablle();
        }
    });
}