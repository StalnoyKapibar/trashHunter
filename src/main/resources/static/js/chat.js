var nameInput = $('#name');
var roomInput = $('#room-id');
var offerId = $('#offerId');
var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var roomIdDisplay = document.querySelector('#room-id-display');
var dataFromOffer;
var takerEmail;
var senderEmail;
var lastOffer;

var stompClient = null;
var currentSubscription;
var username = null;
var roomId = null;
var topic = null;

let colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0',
    '#e41032', '#f2d4e8'
];

$(document).ready(function () {
    usernamePage.classList.remove('hidden');
    usernameForm.addEventListener('submit', connect, true);
    messageForm.addEventListener('submit', sendMessage, true);
    connect();

    fillInSidebar();
});

function connect(event) {
    username = nameInput.val().trim();
    Cookies.set('name', username);
    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function showMessage(message) {
    let messageElement = document.createElement('li');
    messageElement.classList.add('chat-message');

    let avatarElement = document.createElement('i');
    let avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message);

    if ((message.type === 'LASTOFFER') || (message.type === 'OFFER')) {
        avatarElement = document.createElement("img");
        avatarElement.setAttribute("src", getImageForOffer(message.trashType));
    }

    messageElement.appendChild(avatarElement);
    let usernameElement = document.createElement('span');
    let usernameText = document.createTextNode(message.sender + "(" + message.created + ")");
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
    let textElement = document.createElement('p');
    if (message.type === 'LASTOFFER') {
        textElement.style['background-color'] = colors[9];
    }
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function showPreviousMessages(takerId, senderId) {
    $.ajax({
        url: `/api/chat/taker/${takerId}/sender/${senderId}`,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (messages) {
            $.each(messages, function (key, message) {
                if (message.type === 'OFFER') {
                    lastOffer = message.content;
                }
                showMessage(message);
            });
        }
    });
}

function enterRoom(newRoomId) {
    roomId = newRoomId;
    Cookies.set('roomId', roomId);

    topic = `/app/chat/${newRoomId}`;

    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }
    currentSubscription = stompClient.subscribe(`/channel/${roomId}`, onMessageReceived);

    stompClient.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );

    setChatHeader(newRoomId.split('_')[0], newRoomId.split('_')[1]);
    showPreviousMessages(newRoomId.split('_')[0], newRoomId.split('_')[1]);
    showOffer();
}

function showOffer() {
    getSelectedOffer();
    let chatMessage = {
        sender: "Offer",
        content: "Вид отходов:" + dataFromOffer.trashType + " " +
            "Вес:" + dataFromOffer.weight + " " +
            "Объем:" + dataFromOffer.volume + " " +
            "Описание:" + dataFromOffer.description,
        type: "OFFER",
        trashType: dataFromOffer.trashType
    };
    if (chatMessage.content === lastOffer) {
        chatMessage.type = 'LASTOFFER';
    }
    stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));
}

function getSelectedOffer() {
    if (offerId.val()) {
        $.ajax({
            url: '/api/offer/' + offerId.val(),
            dataType: "json",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (data) {
                dataFromOffer = data;
            }
        });
    }
}

function onConnected() {
    enterRoom(roomInput.val());
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    let messageContent = messageInput.value.trim();
    if (messageContent.startsWith('/join ')) {
        let newRoomId = messageContent.substring('/join '.length);
        enterRoom(newRoomId);
        while (messageArea.firstChild) {
            messageArea.removeChild(messageArea.firstChild);
        }
    } else if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));
    }
    messageInput.value = '';
    event.preventDefault();
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');
        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);
        if ((message.type === 'LASTOFFER') || (message.type === 'OFFER')) {
            avatarElement = document.createElement("img");
            avatarElement.setAttribute("src", getImageForOffer(message.trashType));
        }
        messageElement.appendChild(avatarElement);
        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender + "(" + message.created + ")");
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    let textElement = document.createElement('p');
    if ((message.type === 'LASTOFFER') || (message.type === 'OFFER')) {
        textElement.style['background-color'] = colors[9];
    }
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(message) {
    let senderName = message.sender || message;
    if (senderName.indexOf("sender") !== -1) {
        return colors[0];
    } else if (senderName.indexOf("Offer") !== -1) {
        return colors[8];
    } else {
        return colors[1];
    }
}

function setChatHeader(takerId, senderId) {
    getTakerEmailById(takerId);
    getSenderEmailById(senderId);
    roomIdDisplay.textContent = takerEmail + "-" + senderEmail;
}

/*
function getEmailById(id) {
    $.ajax({
        url: '/api/user/' + id,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (user) {

            takerEmail = user.email;
        }
    });
}
*/

function getTakerEmailById(id) {
    $.ajax({
        url: '/api/user/' + id,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (user) {
            takerEmail = user.email;
        }
    });
}

function getSenderEmailById(id) {
    $.ajax({
        url: '/api/user/' + id,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (user) {
            senderEmail = user.email;
        }
    });
}

function getImageForOffer(trashType) {
    if (trashType === 'METAL') {
        return "../img/metall.png";
    } else if (trashType === 'WOOD') {
        return "../img/wood.png";
    } else if (trashType === 'PAPER') {
        return "../img/paper.png";
    } else if (trashType === 'PLASTIC') {
        return "../img/plastic.png";
    } else if (trashType === 'GLASS') {
        return "../img/glass.png";
    } else if (trashType === 'FOOD') {
        return "../img/food.png";
    } else {
        return "../img/blank.png";
    }
}

var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
    dropdown[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === "block") {
            dropdownContent.style.display = "none";
        } else {
            dropdownContent.style.display = "block";
        }
    });
}