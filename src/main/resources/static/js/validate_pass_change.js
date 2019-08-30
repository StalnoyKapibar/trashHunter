function validatePass() {
    $('#not_validate').attr('class','invisible').css("color", "red");
    $('#not_matches').attr('class','invisible').css("color", "red");
    $('#success_change').attr('class','invisible').css("color", "green");


    let oldPass = document.getElementById("oldPassword").value;
    let newPass = document.getElementById("newPassword").value;
    let repeatPass = document.getElementById("repeatPassword").value;
    if(newPass !== repeatPass) {
        $('#not_matches').attr('class','visible alert').css("color", "red");


    } else {
        let passChange = {'old_pass': oldPass,
                          'new_pass': newPass
        };

        $.ajax({
            url: "/api/user/change_password",
            type: 'POST',
            // dataType: "application/json; charset=utf-8",
            data: passChange,
            // contentType: "application/json",
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