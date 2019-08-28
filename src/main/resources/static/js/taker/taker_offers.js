$(document).ready(function () {
   getTable(doFilter('TakerActive'));
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
                                    ' Заказ№ ' + offer.id + ' '+
                                    ' вес: ' + offer.weight + 'кг '+
                                    ' объем: ' + offer.volume + 'м³ '+
                                    ' цена: ' + offer.price + 'руб '+
                                    ' тип мусора: ' + offer.trashType +
                                '</div>' +
                                '<div class="card-body" style="background-color: #ffffff">' +
                                    '<div class="row" style="margin-bottom: 1%">' +
                                    '<div class="col-sm-1"></div>'+
                                        '<div  class="input-group col-sm-3">' +
                                            '<div class="input-group-prepend">'+
                                            '<span style="padding: 0" class="input-group-text">' +
                                        '<img width="38" height="36" src="/image/avatar/'+ offer.sender.id +'"></span>'+
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
                        '<div class="card-footer" style="background-color: #4d90fe">'+
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
    getTable(doFilter('TakerActive'));
}

function makeCompleteOffer(offerId) {
    $.ajax({
        url: '/api/offer/makeCompleteOffer/' + offerId,
        type: 'GET',
        success: function () {
            $('#takerOffersTable tbody').empty();
            getTable(doFilter());
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
