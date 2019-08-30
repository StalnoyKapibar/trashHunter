function resend() {
    let reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    let address = document.getElementById("email").value;
    $('#not_matches').attr('class','invisible').css("color", "red");

    if(reg.test(address) == false) {
        $('#not_matches').attr('class','visible alert').css("color", "red");
        return false;
    }
    let addressForResend = {'address': address};
    $.ajax({
        url: "/api/user/resend_email_for_token_recovery",
        type: 'POST',
        data: addressForResend,
              success: function (e) {
            $('#not_matches').attr('class','visible success').html('Письмо отправлено на почтовый ящик').css("color", "green");
       },
        error: function (e) {
            $('#not_matches').attr('class','visible alert').html('Ой:( Ваша учетная запись уже активна');
        }
    });
};

function showButton() {
    $("#div1").hide();
    $(".dropdown-toggle").next().toggle();
}