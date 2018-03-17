var P_courseTable;//主播列表
var anchorRecTable;//推荐主播列表
var courseForm;//添加课程表单

$(function() {
    createDatePicker($(".datetime-picker"),"yy-mm-dd");
    document.onkeydown = function (event) {
        if (event.keyCode == 13) {
            return false;
        }
    }
    /** 主播列表开始 */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [
        { "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
            return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
        }},
        {"title": "主播", "class": "center", "width": "10%", "sortable": false, "data": 'name'},
        {"title": "帐号", "class": "center", "width": "10%", "sortable": false, "data": 'loginName'},
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
        }},{ "title": "是否推荐", "class":"center","width":"6%","sortable":false,"data": 'isRecommend',"mRender":function (data, display, row) {
            if(data==1){
                return "<span name='sftj'>已推荐</span>";
            }else{
                return "<span name='sftj'>未推荐</span>";
            }
        } },
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
                str += '<a class="blue" href="javascript:void(-1);" title="课程列表" onclick="showCourseListDialog(this);"><i class="ace-icon fa fa-ellipsis-h bigger-130"></i></a>';
                return str;
            }
        }];

    P_courseTable = initTables("courseTable", basePath + "/anchor/courseAnchor/list", objData, true, true, 0, null, searchCase_P, function (data) {
    });
    /**主播列表结束**/
    /** 主播推荐列表开始 */
    var searchCase_Rec = new Array();
    var checkboxRec = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    // searchCase_Rec.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    var recData = [
        { "title": checkboxRec,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
            return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
        }},{"title": "主播", "class": "center", "width": "10%", "sortable": false, "data": 'name'},
        {"title": "帐号", "class": "center", "width": "10%", "sortable": false, "data": 'loginName'},
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
        {"sortable": false,"class": "center","width":"10%","title":"排序","data": 'sort',"mRender":function (data, display, row) {
            return '<div class="hidden-sm hidden-xs action-buttons">'+
               /* '<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMoveRec(this)" name="upa"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
                '<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMoveRec(this)" name="downa"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';*/
                '<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this);">设置推荐值</a> </div>';
        }}];

    anchorRecTable = initTables("recAnchorTable", basePath + "/anchor/courseAnchor/recList", recData, true, true, 0, null, searchCase_Rec, function (data) {
        $("[name='upa']").each(function(index){
            if(index == 0){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
        $("[name='downa']").each(function(index){
            if(index == $("[name='downa']").size()-1){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
    });
    /**主播推荐列表结束**/
    $(".recAnchor").click(function () {
        debugger;
        recAnchorTable();
    });
    $(".rec_P").click(function () {
        var ids = new Array();
        var trs = $(".dataTable tbody input[type='checkbox']:checked");

        for(var i = 0;i<trs.size();i++){
            ids.push($(trs[i]).val());
        }
        if(ids.length>0){
            ajaxRequest(basePath+"/anchor/courseAnchor/updateRec",{'ids':ids.join(","),"isRec":1},function(data){
                if(!data.success){//如果失败
                    layer.msg(data.errorMessage);
                }else{
                    layer.msg(data.errorMessage);
                    freshTable(P_courseTable);
                    freshTable(anchorRecTable);
                }
            });
        }else{
            showDelDialog("","","请选择推荐主播！","");
        }
    });
    $(".rec_P_cancel").click(function () {
        var ids = new Array();
        var trs = $(".dataTable tbody input[type='checkbox']:checked");

        for(var i = 0;i<trs.size();i++){
            ids.push($(trs[i]).val());
        }
        if(ids.length>0){
            ajaxRequest(basePath+"/anchor/courseAnchor/updateRec",{'ids':ids.join(","),"isRec":0},function(data){
                if(!data.success){//如果失败
                    layer.msg(data.errorMessage);
                }else{
                    layer.msg(data.errorMessage);
                    freshTable(P_courseTable);
                    freshTable(anchorRecTable);
                }
            });
        }else{
            showDelDialog("","","请选择主播！","");
        }
    });
});

/**
 * 课程排序搜索
 */
function recAnchorTable(){
    var json = new Array();
    searchButton(anchorRecTable,json);
};

// /**
//  * 状态修改
//  * @param obj
//  */
// function updateRec(obj){
//     var oo = $(obj).parent().parent().parent();
//     var row = P_courseTable.fnGetData(oo); // get datarow
//     ajaxRequest(basePath+"/anchor/courseAnchor/updateRec",{"ids":row.id,"isRec":0},function(data){
//         if(data.success){
//             layer.msg("取消成功！");
//             freshTable(P_courseTable);
//             freshTable(anchorRecTable);
//         }else{
//             layer.msg("取消失败！");
//         }
//     });
// };

/**
 * 课程排序列表上移
 * @param obj
 */
function upMoveRec(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = anchorRecTable.fnGetData(oo);
    ajaxRequest(basePath+'/anchor/courseAnchor/upMoveRec',{"id":aData.id},function(res){
        if(res.success){
            freshTable(anchorRecTable);
        }else{
            layer.msg(res.errorMessage);
        }
    });
};

/**
 * 课程排序列表下移
 * @param obj
 */
function downMoveRec(obj){
    debugger;
    var oo = $(obj).parent().parent().parent();
    var aData = anchorRecTable.fnGetData(oo);
    ajaxRequest(basePath+'/anchor/courseAnchor/downMoveRec',{"id":aData.id},function(res){
        if(res.success){
            freshTable(anchorRecTable);
        }else{
            layer.msg(res.errorMessage);
        }
    });
};

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

function setUserLecturer(obj,op){

    var oo = $(obj).parent().parent().parent();
    var aData = onlineuserTable.fnGetData(oo); // get datarow

    var loginName  = aData.loginName;//用户id
    ajaxRequest('onlineuser/updateUserLecturer', {
        'loginName' : loginName,
        'lecturerStatus' : op
    }, function(data) {
        console.log(data);
        console.log("====="+data.resultObject.resultObject);
        if (data.success) {
            freshTable(onlineuserTable);
            if(op==1){
                var str ="设置该用户的讲师职位成功";
                /*	$("#room_number").html("房间号:"+data.resultObject.resultObject);
                    var dialog = openDialogNoBtnName("userRoomNumberDialog","viewRoomNumber",str,300,200,false,"确定",null);*/
                alertInfo("设置该用户的讲师职位成功<br>房间号："+data.resultObject.resultObject);
            }else{
                alertInfo("取消该用户的讲师职位成功");
            }
        } else {
            alertInfo(data.errorMessage);
        }
    });
}
/**
 * Description：点击主播，跳转到主播的课程列表
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/3/15 21:16
 **/
function showCourseListDialog(obj) {
    debugger;
    var oo = $(obj).parent().parent().parent();
    var aData = P_courseTable.fnGetData(oo); // get datarow
    window.location.href = basePath + '/home#anchor/courseAnchor/anchorCourse?userId=' + aData.userId;
}

/**
 * Description：设置推荐值
 * @Date: 2018/3/9 14:11
 **/
function updateRecommendSort(obj){
    var row="";
    var oo = $(obj).parent().parent().parent();
    row = anchorRecTable.fnGetData(oo);
    $("#UpdateRecommendSort_id").val(row.id);
    var dialog = openDialog("UpdateRecommendSortDialog","dialogUpdateRecommendSortDiv","修改推荐值",350,200,true,"确定",function(){
        if($("#UpdateRecommendSortFrom").valid()){
            mask();
            $("#UpdateRecommendSortFrom").attr("action", basePath+"/anchor/courseAnchor/updateRecommendSort");
            $("#UpdateRecommendSortFrom").ajaxSubmit(function(data){
                try{
                    data = jQuery.parseJSON(jQuery(data).text());
                }catch(e) {
                    data = data;
                }
                unmask();
                if(data.success){
                    $("#recommendSort").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                    freshTable(anchorRecTable);
                }else{
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};