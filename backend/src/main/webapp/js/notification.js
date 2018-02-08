var notifyDesktop = function(msg, link) {
    $.notify(msg, 'info');
    
    var doNotification = function() {
        var options = {
            body: 'Notification from knowledge.',
            icon: _CONTEXT + '/favicon.ico'
        };
        var n = new Notification(msg, options);
        n.onclick = function() {
            window.location.href = link;
        };
    };
    checkPermission().then(function(result) {
        if (result) {
            doNotification();
        }
    });
};

var checkPermission = function() {
    return Promise.try(function() {
        if (window.Notification) {
            if (Notification.permission === 'granted') {
                console.log('Notification.permission is granted');
                return Promise.resolve(true);
            } else if (Notification.permission === 'denied') {
                console.log('Notification.permission is denied');
                return Promise.resolve(false);
            } else if (Notification.permission === 'default') {
                console.log('Notification.permission is default');
                Notification.requestPermission(function(result) {
                    if (result === 'denied') {
                        console.log('requestPermission is denied');
                        return Promise.resolve(false);
                    } else if (result === 'default') {
                        console.log('requestPermission is default');
                        return Promise.resolve(false);
                    } else if (result === 'granted') {
                        console.log('requestPermission is granted');
                        return Promise.resolve(true);
                    }
                });
            }
        } else {
            console.log('Notification is not available');
            return Promise.resolve(false);
        }
    });
};

var webSocket;
window.onload = function() {
    checkPermission();
    
    var forRtoA = document.createElement('a');
    forRtoA.href = _CONTEXT + '/notify';
    console.log(forRtoA.href.replace("http://", "ws://").replace("https://",
            "wss://"));
    webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace(
            "https://", "wss://"));
    webSocket.onopen = function() {
    }
    webSocket.onclose = function() {
    }
    webSocket.onmessage = function(message) {
        console.log('[RECEIVE] ');
        var result = JSON.parse(message.data);
        console.log(result);
        notifyDesktop(result.message, result.result);
    }
    webSocket.onerror = function(message) {
    }
};
