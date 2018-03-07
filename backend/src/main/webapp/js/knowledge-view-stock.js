$(document).ready(function(){
});


/**
 * ストックするダイアログを表示
 */
var stockPage = 0;
$('#stockModal').on('show.bs.modal', function () {
    return getStockInfo();
});
var getStockInfoPrevious = function() {
    stockPage--;
    if (stockPage < 0) {
        stockPage = 0;
    }
    return getStockInfo();
};
var getStockInfoNext = function() {
    stockPage++;
    return getStockInfo();
};
/**
 * 自分が登録しているストックを表示
 */
var stocksInfo;
var getStockInfo = function() {
    var url = _CONTEXT + '/protect.stock/chooselist/' + stockPage;
    var knowledgeId = null;
    if ($('#knowledgeId')) {
        knowledgeId = $('#knowledgeId').val();
    }
    $('#stockSelect').html('');
    $('#stockPage').text(stockPage + 1);
    
    $.ajax({
        type : 'GET',
        url : url,
        data : 'knowledge_id=' + knowledgeId,
        success : function(datas, dataType) {
            console.log(datas);
            stocksInfo = datas;
            var selectStocks = '';
            if (!datas || datas.length == 0) {
                $('#stockLink').show();
                $('#saveStockButton').prop("disabled", true);
                return;
            }
            
            $('#stockLink').hide();
            $('#saveStockButton').prop("disabled", false);
            for (var i = 0; i < datas.length; i++) {
                selectStocks += '<div class="checkbox">';
                selectStocks += '<label><input type="checkbox" value="' + datas[i].stockId + '" ';
                if (datas[i].stocked) {
                    selectStocks += 'checked="checked" ';
                }
                selectStocks += ' />';
                selectStocks += datas[i].stockName + '</label>';
                selectStocks += '</div>';
            }
            $('#stockSelect').html(selectStocks);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            $.notify('[fail] get stock info', 'warn');
        }
    });
};
var saveStocks = function(knowledgeId) {
    var comment = $('#stockComment').val();
    $('#stockSelect').find('input').each(function(){
        for (var i = 0; i < stocksInfo.length; i++) {
            if (stocksInfo[i].stockId == this.value) {
                stocksInfo[i].stocked = this.checked;
                stocksInfo[i].description = comment;
                stocksInfo[i].comment = comment;
                break;
            }
        }
    });
    var url = _CONTEXT + '/protect.knowledge/stock/' + knowledgeId;
    console.log(stocksInfo);
    $.ajax({
        type : 'POST',
        url : url,
        dataType: 'json',
        data : JSON.stringify(stocksInfo),
        contentType: 'application/JSON',
        dataType : 'JSON',
        scriptCharset: 'utf-8',
        success : function(datas, dataType) {
            console.log(datas);
            $.notify(datas.message, 'success');
            $('#stockModal').modal('hide');
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            $.notify('[fail] save stock info', 'warn');
            $('#stockModal').modal('hide');
        }
    });
};

