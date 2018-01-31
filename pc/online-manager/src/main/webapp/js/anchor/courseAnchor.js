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
    var objData = [
        {"title": "主播", "class": "center", "width": "10%", "sortable": false, "data": 'name'},
        {"title": "类型", "class": "center", "width": "6%", "sortable": false, "data": 'type',"mRender": function (data, display, row) {
            if(row.type==1){
                return "医师";
            }else if(row.type==2){
                return "医馆";
            }
        }},
        {"title": "点播分成(%)", "class": "center", "width": "8%", "sortable": false, "data": 'vodDivide'},
        {"title": "直播分成(%)", "class": "center", "width": "8%", "sortable": false, "data": 'liveDivide'},
        {"title": "线下课分成(%)", "class": "center", "width": "8%", "sortable": false, "data": 'offlineDivide'},
        {"title": "礼物分成(%)", "class": "center", "width": "8%", "sortable": false, "data": 'giftDivide'},
        {"title": "主播权限", "class": "center", "width": "6%", "sortable": false, "data": 'type',"mRender": function (data, display, row) {
            if(row.status){
                return "已开启";
            }
            return "已关闭";
        }},
        {
            "sortable": false,
            "class": "center",
            "width": "12%",
            "title": "操作",
            "mRender": function (data, display, row) {
                var str = '<div class="hidden-sm hidden-xs action-buttons">' ;
                if (row.status) {
                       str += '<a class="blue" href="javascript:void(-1);" title="关闭主播权限" onclick="editPermissions(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a>';
                       } else {
                       str += '<a class="blue" href="javascript:void(-1);" title="打开主播权限" onclick="editPermissions(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a>';
                }
                str += '<a class="blue" href="javascript:void(-1);" title="设置分成比例" onclick="toEdit(this,1);"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
                return str;
            }
        }];

    P_courseTable = initTables("courseTable", basePath + "/anchor/courseAnchor/list", objData, true, true, 0, null, searchCase_P, function (data) {
    });
});

function toEdit(obj,status){
    var oo = $(obj).parent().parent().parent();
    var row = P_courseTable.fnGetData(oo); // get datarow
    //根据当前id查找对应的课程信息
    $("#id").val(row.id);
    $.get(basePath+"/anchor/courseAnchor/findCourseAnchorById",{id:row.id}, function(result){
        debugger
        $("#name").val(result.name);
        $("#vod").val(result.vodDivide);
        $("#live").val(result.liveDivide);
        $("#offline").val(result.offlineDivide);
        $("#gift").val(result.giftDivide);

        var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv","设置分成比例",500,500,true,"确定",function(){
            debugger
            if($("#updateCourse-form").valid()){
                mask();
                $("#updateCourse-form").attr("action", basePath+"/anchor/courseAnchor/updateCourseById");
                $("#updateCourse-form").ajaxSubmit(function(data){
                    try{
                        data = jQuery.parseJSON(jQuery(data).text());
                    }catch(e) {
                        data = data;
                    }
                    unmask();
                    if(data.success){
                        $("#EditCourseDialog").dialog("close");
                        freshTable(P_courseTable)
                    }else{
                        layer.msg(data.errorMessage);
                    }
                });
            }
        });
    });

}


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

function editPermissions(obj){
    var oo = $(obj).parent().parent().parent();
    var row = P_courseTable.fnGetData(oo);
    ajaxRequest(basePath+"/anchor/courseAnchor/editPermissions",{"id":row.id},function(){
            freshTable(P_courseTable);
    });
}

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}