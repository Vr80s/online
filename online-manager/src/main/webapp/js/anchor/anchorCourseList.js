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
    searchCase_P.push('{"tempMatchType":"9","propertyName":"userId","propertyValue1":"'+userId+'","tempType":"String"}');
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var objData = [
        { "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
            return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
        }},
        { "title": "序号", "class":"center","width":"9%","sortable":false,"data": 'courseId' },
        { "title": "课程名称", "class":"center","width":"9%","sortable":false,"data": 'title' },
        {"title": "封面图", "class": "center", "width": "12%", "sortable": false, "data": 'imgPath',"mRender":function(data){
                return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;'/>";
            }},
        { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'menuName' },
        { "title": "主讲人", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
        { "title": "价格", "class":"center","width":"8%","sortable":false,"data": 'price'},
        { "title": "原价", "class":"center","width":"8%","sortable":false,"data": 'originalCost',"mRender":function(data,display,row){
                if(data!=null && data != 0){
                    return data;
                }
                return "";
            }},
        { "title": "默认学习人数", "class":"center","width":"10%","sortable":false,"data": 'defaultStudentCount'},
        { "title": "直播时间", "class": "center", "width": "10%","sortable":false,"data": 'startTime',"mRender":function(data,display,row){
            if(data!=null&&data!=""){
                return getLocalTime(data);
            }
                return data;
            }},
        { "title": "资源类型", "class":"center","width":"6%","sortable":false,"data": 'multimediaType' ,"mRender":function (data, display, row) {
            if(data == 1){
                return "视频";
            }else if(data == 2){
                return "音频";
            }

        }},
        { "title": "是否为专辑", "class":"center","width":"6%","sortable":false,"data": 'collection' ,"mRender":function (data, display, row) {
            if(data){
                return "是";
            }
            return "否";
        }},
        { "title": "是否上架", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
                if(data==1){
                    return "<span name='zt'>已上架</span>";
                }else{
                    return "<span name='zt'>未上架</span>";
                }
            } },
        { "title": "课程类型", "class":"center","width":"6%","sortable":false,"data": 'courseForm' ,"mRender":function (data, display, row) {
                if(data==1 ){  //课程分类 1:公开直播课（1.直播2.点播3.线下课）
                    return "直播课";
                }else if(data== 2){
                    return "点播";
                }else if(data== 3){
                    return "线下课";
                }
            } },
        { "title": "审核状态", "class":"center","width":"6%","sortable":false,"data": 'applyStatus' ,"mRender":function (data, display, row) {
                if(data==0 ){
                    return "未通过";
                }else if(data== 1){
                    return "通过";
                }else if(data== 2){
                    return "未审核";
                }
            } },
        { "title": "发布时间", "class":"center","width":"8%","sortable":false,"data": 'releaseTime',"mRender":function(data,display,row){
                if(data!=null&&data!=""){
                    return getLocalTime(data);
                }
                return data;
            }},
        { "sortable": false,"class": "center","width":"9%","title":"操作","mRender":function (data, display, row) {
                if(row.status=="1"&&row.applyStatus=="1"){
                    var str = '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="设置默认学习人数" onclick="updateDefaultStudentCount(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
                        '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a></div> ';

                    return str;
                }else if(row.status=="0"&&row.applyStatus=="1"){
                    return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="设置默认学习人数" onclick="updateDefaultStudentCount(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
                        '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a></div> '
                }
            }}
    ];

    P_courseTable = initTables("courseTable", basePath + "/anchor/courseAnchor/courseList", objData, true, true, 0, null, searchCase_P, function (data) {
        ;
        var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize +1;//页码
        var countPage;
        if(countNum%pageSize == 0){
            countPage = parseInt(countNum/pageSize);
        }else{
            countPage = parseInt(countNum/pageSize) + 1;
        }
    });
    /**主播列表结束**/

});

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}

//返回
function return_P() {
    window.location.href = basePath + '/home#anchor/courseAnchor/index';
}
//条件搜索
function search_P(){
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"userId","propertyValue1":"'+userId+'","tempType":"String"}');
    searchButton(P_courseTable,searchCase_P);
};

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){

    var oo = $(obj).parent().parent().parent();
    var row = P_courseTable.fnGetData(oo); // get datarow
    ajaxRequest(basePath+"/cloudclass/course/updateStatus",{"id":row.courseId},function(data){
        console.log(data);
        if(data.success==false){
            layer.msg(data.errorMessage);
        }
        freshTable(P_courseTable);
    });
};


/**
 * 修改默认学习人数。啊啊啊啊啊
 * @param obj
 */
function updateStatus(obj){

    var oo = $(obj).parent().parent().parent();
    var row = P_courseTable.fnGetData(oo); // get datarow
    ajaxRequest(basePath+"/cloudclass/course/updateStatus",{"id":row.courseId},function(data){
        console.log(data);
        if(data.success==false){
            layer.msg(data.errorMessage);
        }
        freshTable(P_courseTable);
    });
};

/**
 * Description：修改默认值
 * @Date: 2018/3/9 14:11
 **/
function updateDefaultStudentCount(obj){
    var oo = $(obj).parent().parent().parent();
    var row = P_courseTable.fnGetData(oo);
    
    $("#UpdateRecommendSort_id").val(row.courseId);
    var dialog = openDialog("UpdateRecommendSortDialog","dialogUpdateRecommendSortDiv","设置默认学习人数",350,300,true,"确定",function(){
        if($("#UpdateRecommendSortFrom").valid()){
            mask();
            $("#UpdateRecommendSortFrom").attr("action", basePath+"/cloudclass/course/updatedefaultStudent");
            $("#UpdateRecommendSortFrom").ajaxSubmit(function(data){
                data = getJsonData(data);
                unmask();
                if(data.success){
                    $("#recommendSort").val("");
                    $("#recommendTime").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                    freshTable(P_courseTable);
                }else{
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};




