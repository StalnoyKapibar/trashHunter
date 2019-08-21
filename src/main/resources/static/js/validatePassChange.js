function validatePass(id) {
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
            //contentType: "json",
            url: "/succChange/succChange",
            dataType: "json",
            data: json,
            success: function (e) {
                alert(e);
            },
            error: function (e) {
                alert('error');
            }
        });

    }
};