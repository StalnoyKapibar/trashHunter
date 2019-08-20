function validatePass() {
    var oldPass = document.getElementById("oldPassword").value;
    var newPass = document.getElementById("newPassword").value;
    var repeatPass = document.getElementById("repeatPassword").value;
    if(newPass !== repeatPass) {
        $('#newPassword').attr('class',' alert alert-danger');
        $('#repeatPassword').attr('class',' alert alert-danger');
        $('#pass-alert').attr('class','visible alert alert-danger');

    } else {
        var passChange = {old_pass: oldPass,
                          new_pass: newPass
        };
        var json = JSON.stringify(passChange);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/succChange",
            dataType: "json",
            data: json,

        });

    }
};