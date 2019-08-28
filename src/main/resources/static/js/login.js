$('.dropdown-toggle').on('click', function (e) {
    $(this).next().toggle();
});
$('.dropdown-menu.dropdown-menu-right').on('click', function (e) {
    e.stopPropagation();
});

function login() {
    let email = $('#username').serialize();
    let password = $('#password').serialize();

    console.info("Attempting to authenticate");

    $.ajax({
        type: 'POST',
        url: '/login',
        data: email + '&' + password,
        cache: false,
        dataType: "json",
        crossDomain: false,
        success: function (data) {
            if (data.login == true) {
                console.info("Authentication Success!");
                $('#ups_message').hide();
                $('#reset_message').hide();
                window.location.href = "/";
            } else {
                $('#ups_message').slideDown({opacity: "show"}, "slow");
                console.error("Unable to login");
                if (data.reset_msg == false) {
                    $('#reset_message').hide();
                    console.log('ok');
                } else {
                    $('#reset_message').slideDown({opacity: "show"}, "slow");
                    $('#link_reset_message').attr("href", "/reset/send_message/?"+email);
                }
            }
        },
        error: function (data) {
            console.error("Login failure");
        }
    });
}