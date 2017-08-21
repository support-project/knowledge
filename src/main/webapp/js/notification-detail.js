$(document).ready(function() {
    $("#content").each(function(){
        $(this).html( $(this).html().replace(/((http|https):\/\/[\w?=&.\/-;#~%-]+(?![\w\s?&.\/;#~%"=-]*>))/g, '<a href="$1">$1</a> ') );
    });
});