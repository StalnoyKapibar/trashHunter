function validatePass() {
    $('#not_validate').attr('class','invisible').css("color", "red");
    $('#not_matches').attr('class','invisible').css("color", "red");
    $('#success_change').attr('class','invisible').css("color", "green");


    let newPass = document.getElementById("newPassword").value;
    let repeatPass = document.getElementById("repeatPassword").value;
    let token = document.getElementById("token").value;
    let email = document.getElementById("email").value;
    if(newPass !== repeatPass) {
        $('#not_matches').attr('class','visible alert').css("color", "red");
    } else {
        let passChange = {
            'new_pass': newPass,
            'token': token,
            'email' : email
        };

        $.ajax({
            url: "/reset/change_password",
            type: 'POST',
            data: passChange,
            beforeSend: function (request) {
                request.setRequestHeader(header, token);
            },

            success: function (e) {
                $('#success_change').attr('class','visible alert').css("color", "green");
                $('#not_validate').remove();
                $('#div1').attr('class', 'btn btn-primary').html('Домой').click(function(){
                    $('#not_validate').attr('class','invisible');
                    window.location.href="/";
                });

            },
            error: function (e) {
                $('#not_validate').attr('class','visible alert').css("color", "red");
            }
        });

    }
};