function confirmOffer(id) {
    $.ajax({
        url: '/sender/confirmOffer/' + id,
        type: 'GET',
        success: function () {
            location.href = '/sender_my_offers'
        }
    });
}