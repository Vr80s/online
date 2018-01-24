var P_courseTable;//职业课列表
var courseForm;//添加课程表单

$(function() {
    /** 职业课列表begin */
    var searchCase_P = new Array();
    var objData = [
        {"title": "名称", "class": "center", "width": "6%", "sortable": false, "data": 'title'},
        {"title": "上传人账号", "class": "center", "width": "8%", "sortable": false, "data": 'loginName'},
        {"title": "上传人昵称", "class": "center", "width": "8%", "sortable": false, "data": 'userName'},
        {"title": "时长", "class": "center", "width": "8%", "sortable": false, "data": 'length'},
        {"title": "媒体类型", "class": "center", "width": "8%", "sortable": false, "data": 'multimediaType',
            "mRender": function (data) {
                if (data == 1) {
                    return "视频";
                return "音频";
            }}
        },
        {
            "title": "状态",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'deleted',
            "mRender": function (data) {
                if (data) {
                    return "有效";
                }
                return "已删除";
            }
        },
        {
            "title": "上传时间",
            "class": "center",
            "width": "8%",
            "sortable": false,
            "data": 'createTime',
            "mRender": function (data) {
                return getLocalTime(data);
            }
        },
        {
            "sortable": false,
            "class": "center",
            "width": "12%",
            "title": "操作",
            "mRender": function (data, display, row) {
                var str =  '<div class="hidden-sm hidden-xs action-buttons">';
                str += '<a class="blue" href="javascript:void(-1);" title="观看视频" onclick="look(this);"><i class="ace-icon glyphicon glyphicon-facetime-video bigger-130"></i></a>';
                if (row.deleted) {
                    str += '<a class="blue" href="javascript:void(-1);" title="恢复" onclick="deleteOrRecoveryCourseApplyResource(this,false);"><i class="ace-icon glyphicon glyphicon-share-alt bigger-130"></i></a>';
                } else {
                    str += '<a class="blue" href="javascript:void(-1);" title="删除" onclick="deleteOrRecoveryCourseApplyResource(this,true);"><i class="ace-icon glyphicon glyphicon-trash bigger-130"></i></a>';
                }
                str +='</div>';
                return str;
            }
        }];

    P_courseTable = initTables("courseTable", basePath + "/cloudclass/courseResource/list", objData, true, true, 0, null, searchCase_P, function (data) {
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

    function deleteOrRecoveryCourseApplyResource(obj,deleted) {
        var oo = $(obj).parent().parent().parent();
        var aData = P_courseTable.fnGetData(oo); // get datarow
        var url =basePath+'/cloudclass/courseResource/';
        if(deleted){
            url += 'delete?courseApplyResourceId='+aData.id;
        }else{
            url += 'recovery?courseApplyResourceId='+aData.id;
        }
        $.ajax({
            type:'post',
            url:url,
            contentType:'application/json',
            dataType:'json',
            success:function(data){
                alertInfo(data.errorMessage,function(){
                    if(data.success){
                        window.location.reload();
                    }
                });
            }
        }) ;
    }


function look(obj) {
    debugger
    var oo = $(obj).parent().parent().parent();
    var aData = P_courseTable.fnGetData(oo); // get datarow
    if(aData.resource==null ||aData.resource==''){
        alertInfo('资源不存在');
        return;
    }
    $("#videoDialog").html(aData.playCode);
    openDialog("videoDialog", "videoDiv", "资源", 650, 610, true, "确定", function () {
        // notPass();
    });
}

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}