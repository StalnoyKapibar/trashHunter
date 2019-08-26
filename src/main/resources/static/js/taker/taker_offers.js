$(document).ready(function () {
   getTable(doFilter());
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
                                '<div class="card-header" style="color: white; background-color: #7295b1">' +
                                    ' Заказ№ ' + offer.id + ' '+
                                    ' вес: ' + offer.weight + 'кг '+
                                    ' объем: ' + offer.volume + 'м³ '+
                                    ' цена: ' + offer.price + 'руб '+
                                    ' тип мусора: ' + offer.trashType +
                                '</div>' +
                                '<div class="card-body" style="background-color: moccasin">' +
                                    '<div class="row" style="margin-bottom: 1%">' +
                                    '<div class="col-sm-1"></div>'+
                                        '<div class="input-group col-sm-3">' +
                                            '<div class="input-group-prepend">'+
                                            '<span class="input-group-text"><i class="fas fa-user"></i></span>'+
                                        '</div>'+
                                        '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="'+
                                            offer.sender.name +'" disabled>' +
                                        '</div>' +
                                        '<div class="input-group col-sm-4">' +
                                            '<div class="input-group-prepend">'+
                                                '<span class="input-group-text"><i class="fas fa-envelope"></i></span>'+
                                            '</div>'+
                                            '<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="' +
                                                offer.sender.email+'" disabled>' +
                                        '</div>' +
                                    '<button class="btn btn-primary btn-icon "' +
                                        'onclick="makeCompleteOffer(' + offer.id +')">' +
                                        '<span class="icon"><i class="fas fa-check"></i></span>закончить' +
                                    '</button>' +
                                    '<a href="/chat/?partnerId=' + offer.sender.id + '&offerId=' + offer.id + '" class="btn btn-info btn-icon " >' +
                                        '<span class="icon"><i class="fas fa-comments"></i></span>чат' +
                                    '</a>' +
                                    '<div class="col-sm-1"></div>' +
                            '</div>'+
                        '</div>'+
                        '<div class="card-footer" style="background-color: #7295b1">'+
                            '<div class="row">' +
                                '<div class="col-sm-10"></div>'+
                                    '<button class="btn btn-light btn-circle "'+
                                        'data-toggle="tooltip" data-placement="bottom" title="отказаться"'+
                                        'onclick="cancelOffer(' + offer.id + ')">'+
                                        '<i class="fas fa-window-close"></i>' +
                                    '</button>' +
                                '</div>'+
                            '</div>'+
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '</div>';

            $('#takerOffersTable tbody').append(offerRow);
        });

}

function doFilterInit() {
    getTable(doFilter());
}

function makeCompleteOffer(offerId) {
    $.ajax({
        url: '/api/offer/makeCompleteOffer/' + offerId,
        type: 'GET',
        success: function () {
            $('#takerOffersTable tbody').empty();
            doFilter();
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

// function doFilter(){
//     let trashType = [];
//     $('input[type=checkbox]').each(function () {
//         if (this.checked) {
//             trashType.push($(this).attr("id"))
//         }
//     });
//     let weightFrom = $("#weightFrom").val();
//     let weightTo = $("#weightTo").val();
//     let weight = weightFrom + "-" + weightTo;
//
//     let volumeFrom = $("#volumeFrom").val();
//     let volumeTo = $("#volumeTo").val();
//     let volume = volumeFrom + "-" + volumeTo;
//
//     let isSorted = $("#isSorted option:selected").attr("value");
//     let isFree = $("#isFree option:selected").attr("value");
//
//     let filter = {};
//     if (trashType.length!==0) {
//         filter["trashType"] = trashType;
//     }
//     if (weight.length>=2 && (weightFrom !== "" || weightFrom !== "")) {
//         filter["weight"] = weight;
//     }
//     if (volume.length>=2 && (volumeFrom !== "" || volumeFrom !== "")) {
//         filter["volume"] = volume;
//     }
//     if (isSorted!=="") {
//         filter["isSorted"] = isSorted;
//     }
//     if (isFree!=="") {
//         filter["isFree"] = isFree;
//     }
//
//     console.log(filter);
//
//     $.ajax({
//         url: "/api/taker/my_offers" ,
//         type: "post",
//         contentType: "application/json; charset=utf-8",
//         async: false,
//         dataType: "json",
//         data: JSON.stringify(filter),
//         success: function (data) {
//             $('#takerOffersTable tbody').empty();
//             getTable(data);
//         }
//     });
// }
//
// function openFilter() {
//     document.getElementById("filter").style.height = "400px";
//     document.getElementById("filter-open-btn").style.height = "0";
// }
//
// function closeFilter() {
//     document.getElementById("filter").style.height = "0";
//     document.getElementById("filter-open-btn").style.height = "50px";
// }
//
