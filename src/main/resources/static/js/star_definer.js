$(document).ready(function () {
    voteCurrentUser();
    isStar($("#a_rating").text());
});

function isStar(rating) {
    if (rating >= 1 && rating <= 2) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>")
    } else if (rating >= 2 && rating <= 3) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else if (rating >= 3 && rating <= 4) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>");
    }
}

function putLike(userId, vote) {
    $.ajax({
        url: '/api/vote/user/' + userId + '/vote/' + vote,
        type: 'GET',
        success: function (data) {
            if (vote === true) {
                $('.like').css({"color": "red"});
                $('.dislike').css({"color": "black"});
            } else {
                $('.like').css({"color": "black"});
                $('.dislike').css({"color": "red"});
            }
        }
    });
}

function voteCurrentUser() {
    let currentProfileId = $("#currentProfileId").text();
    $.ajax({
        url: '/api/vote/user/' + currentProfileId,
        type: 'GET',
        success: function (vote) {
            if (vote) {
                if (vote.vote === true) {
                    $('.like').css({"color": "red"});
                    $('.dislike').css({"color": "black"});
                } else {
                    $('.like').css({"color": "black"});
                    $('.dislike').css({"color": "red"});
                }
            }
        }
    });
}