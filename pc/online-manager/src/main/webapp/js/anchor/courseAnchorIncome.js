var P_courseTable;//职业课列表
var courseForm;//添加课程表单

$(function() {
    createDatePicker($(".datetime-picker"),"yy-mm-dd");
    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            return false;
        }
    }
    /** 职业课列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_type","propertyValue1":"DESC","tempType":"String"}');
    var objData = [
        {"title": "主播账号", "class": "center", "width": "10%", "sortable": false, "data": 'loginName'},
        {"title": "主播昵称", "class": "center", "width": "6%", "sortable": false, "data": 'name'},
        {"title": "总收入", "class": "center", "width": "8%", "sortable": false, "data": 'total'},
        {"title": "点播课收入", "class": "center", "width": "8%", "sortable": false, "data": 'vod'},
        {"title": "直播课收入", "class": "center", "width": "8%", "sortable": false, "data": 'live'},
        {"title": "线下课收入", "class": "center", "width": "8%", "sortable": false, "data": 'offline'},
        {"title": "礼物收入", "class": "center", "width": "8%", "sortable": false, "data": 'gift'},
        {"title": "人民币余额", "class": "center", "width": "8%", "sortable": false, "data": 'rmb'},
        {"title": "熊猫币余额", "class": "center", "width": "8%", "sortable": false, "data": 'coin'},
        {"title": "提现金额", "class": "center", "width": "8%", "sortable": false, "data": 'enchashmentTotal'},
        {"title": "提现次数", "class": "center", "width": "8%", "sortable": false, "data": 'enchashmentCount'}
        ];
    P_courseTable = initTables("courseTable", basePath + "/anchor/courseAnchorIncome/list", objData, true, true, 0, null, searchCase_P, function (data) {
    });
});

/**
 * 职业课列表搜索
 */
function search_P() {
    var json = new Array();
    var startTime = $("#startTime").val(); //开始时间
    var stopTime = $("#stopTime").val(); //结束时间
    if(startTime != "" || stopTime != "") {

        if (startTime != "" && stopTime != "" && startTime > stopTime) {
            alertInfo("开始日期不能大于结束日期");
            return;
        }
        json.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
        json.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
    }
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    searchButton(P_courseTable, json);
};

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}
