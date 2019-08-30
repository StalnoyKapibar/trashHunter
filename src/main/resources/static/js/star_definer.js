$(document).ready(function () {
    voteCurrentUser();
    countVoteCurrentUser();
    isStar($("#a_rating").text());
});

function isStar(rating) {
    if (rating <= 1) {
        $("#stars").append("<span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else if (rating > 1 && rating <= 1.5) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>")
    } else if (rating > 1.5  && rating <= 2.5) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else if (rating > 2.5 && rating <= 3.5) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else if (rating > 3.5 && rating <= 4.5)  {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else if (rating > 4.5 && rating <= 5) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>");
    } else {
        $("#stars").append("<span class=\"fa fa-star\"></span>\n" +
        "                                <span class=\"fa fa-star\"></span>\n" +
        "                                <span class=\"fa fa-star\"></span>\n" +
        "                                <span class=\"fa fa-star\"></span>\n" +
        "                                <span class=\"fa fa-star\"></span>");

    }
}

function putLike(userId, vote) {
    $.ajax({
        url: '/api/vote/user/' + userId + '/vote/' + vote,
        type: 'GET',
        success: function (data) {
            if (data != -1) {
                if (vote === true) {
                    $('.like').css({"color": "#007cff"});
                    $('.dislike').css({"color": "black"});
                } else {
                    $('.like').css({"color": "black"});
                    $('.dislike').css({"color": "red"});
                }
                countVoteCurrentUser();
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
                    $('.like').css({"color": "#007cff"});
                    $('.dislike').css({"color": "black"});
                } else {
                    $('.like').css({"color": "black"});
                    $('.dislike').css({"color": "red"});
                }
            }
        }
    });
}

function countVoteCurrentUser() {
    let currentProfileId = $("#currentProfileId").text();
    $.ajax({
        url: '/api/vote/count/user/' + currentProfileId,
        type: 'GET',
        success: function (vote) {
            $("#countLike").text(vote[0]);
            $("#countDislike").text(vote[1]);
        }
    });
}