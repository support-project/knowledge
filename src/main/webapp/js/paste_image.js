/**
 * Created by tetsukazu on 2017/02/05.
 */
$(document).ready(function () {
    var formatDate = function (date, format) {
        if (!format) format = 'YYYY-MM-DD hh:mm:ss.SSS';
        format = format.replace(/YYYY/g, date.getFullYear());
        format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
        format = format.replace(/DD/g, ('0' + date.getDate()).slice(-2));
        format = format.replace(/hh/g, ('0' + date.getHours()).slice(-2));
        format = format.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
        format = format.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
        if (format.match(/S/g)) {
            var milliSeconds = ('00' + date.getMilliseconds()).slice(-3);
            var length = format.match(/S/g).length;
            for (var i = 0; i < length; i++) format = format.replace(/S/, milliSeconds.substring(i, i + 1));
        }
        return format;
    };

    var handlePasteImage = function (element) {
        return function (event) {
            if (!event.clipboardData
                || !event.clipboardData.types
                || (event.clipboardData.types.length != 1)
                || (event.clipboardData.types[0] != "Files")) {
                return true;
            }
            var imageFile = event.clipboardData.items[0].getAsFile();
            var formData = new FormData();
            formData.append('files[]', new File([imageFile], formatDate(new Date(), "YYYYMMDDhhmmssSSS") + "_paste_image.png"));
            var url = _CONTEXT + 'protect.file/upload';
            jQuery.ajax({
                type: 'POST',
                url: url,
                contentType: false,
                processData: false,
                data: formData,
                success: function (msg) {
                    if (0 < msg.files.length) {
                        var file = msg.files[0];
                        var str = '![' + file.name + '](' + file.url + ')';
                        var obj = $(element);
                        obj.focus();
                        if (navigator.userAgent.match(/MSIE/)) {
                            var r = document.selection.createRange();
                            r.text = str;
                            r.select();
                        } else {
                            var s = obj.val();
                            var p = obj.get(0).selectionStart;
                            var np = p + str.length;
                            obj.val(s.substr(0, p) + str + s.substr(p));
                            obj.get(0).setSelectionRange(np, np);
                        }
                    }
                }
            });
        }
    };

    var content = document.querySelector("#content");
    if(content) {
        content.addEventListener("paste", handlePasteImage(content));
    }

    var comment = document.querySelector("#comment");
    if(comment) {
        comment.addEventListener("paste", handlePasteImage(comment));
    }

});