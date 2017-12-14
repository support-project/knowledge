/**
 * Created by tetsukazu on 2017/02/05.
 */
$(document).ready(function () {
    var createFileName = function() {
        var m = moment();
        return 'clip-' + m.format('YYYYMMDDHHmmss') + '.png';
    };
    var handlePasteImage = function (element) {
        return function (event) {
            var imageBlob,formData,url;
            if (!event.clipboardData
                || !event.clipboardData.types
                || (event.clipboardData.types.length != 1)
                || (event.clipboardData.types[0] != "Files")) {
                return true;
            }
            imageBlob = event.clipboardData.items[0].getAsFile();
            formData = new FormData();
            formData.append('files[]', imageBlob, createFileName());
            url = _CONTEXT + '/protect.file/upload';
            jQuery.ajax({
                type: 'POST',
                url: url,
                contentType: false,
                processData: false,
                data: formData,
                success: function (msg) {
                    uploadedFiles(msg.files);
                    var target = $(element);
                    writeImageLink(msg, target);
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


var writeImageLink = function(msg, obj) {
    var file,str,obj,s,p,np;
    if (0 < msg.files.length) {
        file = msg.files[0];
        str = '![' + file.name + '](' + file.url + ')';
        obj.focus();
        s = obj.val();
        p = obj.get(0).selectionStart;
        np = p + str.length;
        obj.val(s.substr(0, p) + str + s.substr(p));
        obj.get(0).setSelectionRange(np, np);
    }
};


