var paperTable;
var paperListTable;
var makeHomeworkForm;
var searchJson = new Array();
$(function() {
    makeHomeworkForm = $("#makeHomeworkForm");
    loadGradeList();
    $('#returnbutton').on('click',function(){
        window.location.href=basePath+'/home#homework/index';
    });

    createScopeDatePicker();//创建日期选择器
    var currDate  = new Date();
    currDate.setDate(currDate.getDate()-1);
    //开始日期默认最小为当天
    $("#startTime").datetimepicker({
        showSecond: true,
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        timeFormat: 'HH:mm:ss',
        minDate:currDate,
        onSelect:function(data){
            var d = $("#duration").val(); //试卷时长
            var s = data.replace(/-/g,"/");
            var start = new Date(s);
            var end = new Date();
            end.setTime(start.getTime()+(d*60*1000));
            $("#endTime").val(end.Format("yyyy-MM-dd hh:mm:ss"));
        }
    });
    $(".clearStartTime").click(function(){
        $("#startTime").val("");
        $("#endTime").val("");
    });
});

//作业列表展示
function loadGradeList(){
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
        {title: '序号', "class": "center", "width": "4%","data": 'id',"sortable": false},
        {title: '试卷名称', "class": "center","data": 'paperName', "sortable": false},
        {title: '开始时间', "class": "center", "width": "11%","data": 'start_time', "sortable": false},
        {title: '截止时间', "class": "center", "width": "11%","data": 'end_time', "sortable": false},
        {title: '已交/总数', "class": "center", "width": "7%","sortable": false,"mRender":function (data, display, row) {
            return row.sendCount+"/"+row.totalCount;
        }},
        {title: '难度', "class": "center", "width": "7%", "sortable": false,"mRender":function (data, display, row) {
            if(row.difficulty=='A'){
                return "简单";
            }else if(row.difficulty=='B'){
                return "一般";
            }else if(row.difficulty=='C'){
                return "困难";
            }else if(row.difficulty=='D'){
                return "非常困难";
            }
        }},
        {title: '平均分数', "class": "center", "width": "8%","data": 'averageScore', "sortable": false,"mRender":function (data, display, row) {
            if(row.averageScore==null){
                return "-";
            }else{
                return row.averageScore;
            }
        }},
        {title: '状态', "class": "center", "width": "8%", "sortable": false,"mRender":function (data, display, row) {
            if(row.status==0){
                return "未发布";
            }else if(row.status==1){
                return "未批阅（"+row.unReadOver+"人）";
            }else if(row.status==2){
                return "待发成绩";
            }else if(row.status==3){
                return "已完成";
            }
        }},
        {
            "sortable": false,"data":"id","class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons">';
                if(row.status==0){
                    buttons += '<a class="blue" href="javascript:void(-1);" title="修改" onclick="updateDialog(this)">修改</a>' +
                    '<a class="blue" href="javascript:void(-1);" title="发布" onclick="publish(this)">发布</i></a>' +
                    '<a class="blue" href="javascript:void(-1);" title="预览" onclick="preview(this);">预览</a>' +
                    '<a class="blue" href="javascript:void(-1);" title="删除" onclick="deleteHomework(this);">删除</a>';
                }else if(row.status==1){
                    buttons += '<a class="blue" href="javascript:void(-1);" title="批阅" onclick="studentHomework(this)">批阅</a>' +
                        '<a class="blue" href="javascript:void(-1);" title="预览" onclick="preview(this);">预览</a>';
                }else if(row.status==2){
                    buttons += '<a class="blue" href="javascript:void(-1);" title="发布成绩" onclick="studentHomework(this)">发布成绩</a>' +
                        '<a class="blue" href="javascript:void(-1);" title="预览" onclick="preview(this);">预览</a>';
                }else if(row.status==3){
                    buttons += '<a class="blue" href="javascript:void(-1);" title="查看" onclick="studentHomework(this)">查看</a>' +
                        '<a class="blue" href="javascript:void(-1);" title="预览" onclick="preview(this);">预览</a>';
                }
                buttons += '</div>';
                return buttons;
            }
        }
    ];
    var classId=$("#classId").val();
    searchJson.push('{"tempMatchType":"9","propertyName":"classId","propertyValue1":"' + classId + '","tempType":"String"}');
    paperTable = initTables("paperTable", basePath + "/homework/findPaperList", dataFields, true, true, 1,null,searchJson,function(data){
        
    });
}

/**
 * 班级试卷预览
 * @param obj
 */
function preview(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = paperTable.fnGetData(oo);
    window.open(basePath+'/homework/reviewPaper?paperName='+aData.paperName+'&paperId='+aData.paper_id);
}

/**
 * 课程试卷预览
 * @param obj
 */
function preview_kc(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = paperListTable.fnGetData(oo);
    window.open(basePath+'/homework/reviewPaper?paperName='+aData.paperName+'&paperId='+aData.id);
}

/**
 * 学员列表
 * @param obj
 */
function studentHomework(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = paperTable.fnGetData(oo);
    var className = $("#className").val();
    var courseId = $("#courseId").val();
	window.location.href=basePath+'/home#homework/studentHomework?classId='+aData.class_id+'&classPaperId='+aData.id+'&className='+className+
        '&paperName='+aData.paperName+'&courseId='+courseId;
}

 //条件搜索
 function search(){
     searchButton(paperTable,searchJson);
 }

/**
 *创建日期picker
 */
function createScopeDatePicker(){
    var currDate  = new Date();
    currDate.setDate(currDate.getDate()-1);
    //开始日期默认最大为当天
    $("#start_time").datepicker({
        autoclose: true,
        changeMonth: true,
        changeYear: true,
        dateFormat: 'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        yearRange: '-100:+10',
        todayHighlight: true,
        onSelect:function(data){
            $("#end_time").datepicker("option","minDate",data);
        }
    });

    //结束日期 默认最大为当天
    $("#end_time").datepicker({
        autoclose: true,
        changeMonth: true,
        changeYear: true,
        dateFormat: 'yy-mm-dd',
        monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
        yearRange: '-100:+10',
        todayHighlight: true,
        minDate:currDate,
        onSelect:function(data){
        }
    });
}

/**
 * 点击安排作业按钮，初始化安排作业页面
 */
function initElementBeforeAdd(){
    resetForm();
    $("#choosePaperButton").attr("disabled",false);
    $("#choosePaperButton").removeClass("hide");
    $("#paperNameShowSpan").addClass("hide");
    $("#paperChange").addClass("hide");
    $("#makeHomeworkForm #paperId").val("");
}

function resetForm(){
    makeHomeworkForm.each(function(){
        this.reset();//有效，其他无效
    });
    makeHomeworkForm.resetForm();
}
/**
 * 保存成功后清空数据
 */
function clearData(){
    $('#startTime').val("");
    $('#endTime').val("");
    $("#paperNameShowSpan").html("");
    $('#homeworkId').val("");
    $('#paperId').val("");
}
/**
 * 点击作业列表的修改按钮，初始化安排作业页面
 */
function initElementBeforeUpdate(){
    resetForm();
    $("#chooseClassButton").addClass("hide");
    $("#classNameShowSpan").removeClass("hide");
    $("#classChange").removeClass("hide");
    $("#choosePaperButton").addClass("hide");
    $("#paperNameShowSpan").removeClass("hide");
    $("#paperChange").removeClass("hide");
}
/**
 * 布置试卷对话框
 */
function addDialog(){
    initElementBeforeAdd();
    var dialog = openDialog("addOrUpdateHomeworkDialog","addOrUpdateHomeworkDialogDiv","选择试卷",520,288,true,"提交",function(){
        if(makeHomeworkForm.valid()){
            if($('#paperId').val() == null || $('#paperId').val() == ""){
                alertInfo("请选择试卷");
                return;
            }
            mask();
            var param = makeHomeworkForm.serialize();
            ajaxRequest(basePath+"/homework/makeHomework",param,function(res){
                if(res.success){
                    dialog.dialog("close");
                    search();
                    clearData();
                }else alertInfo(res.errorMessage);
                unmask();
            });
        }
    });
}
/**
 * 修改试卷对话框
 */
function updateDialog(obj){
    initElementBeforeUpdate();
    var oo = $(obj).parent().parent().parent();
    var aData = paperTable.fnGetData(oo);
    var id = aData.id;
    var paperId = aData.paper_id;
    $("#makeHomeworkForm #homeworkId").val(id);
    $("#makeHomeworkForm #paperId").val(paperId);

    if(aData.paperName.length>20){
        $("#paperNameShowSpan").html(aData.paperName.substr(0,20)+'...');
        $("#paperNameShowSpan").attr("title",aData.paperName);
    }else $("#paperNameShowSpan").html(aData.paperName);

    $('#startTime').val(aData.start_time);
    $('#endTime').val(aData.end_time);
    var dialog = openDialog("addOrUpdateHomeworkDialog","addOrUpdateHomeworkDialogDiv","修改试卷",520,288,true,"提交",function(){
        if(makeHomeworkForm.valid()){

            if($('#paperId').val() == null || $('#paperId').val() == ""){
                alertInfo("请选择试卷");
                return;
            }
            if($("#paperId").val()==paperId//如果数据没有被改动则不提交
                && ($('#startTime').val()==aData.start_time || isnull(aData.start_time)&&$('#startTime').val()=='')
                && ($('#endTime').val()==aData.end_time || isnull(aData.end_time)&&$('#endTime').val()=='')){
                dialog.dialog("close");
                clearData();
                return;
            }

            mask();
            var param = makeHomeworkForm.serialize();
            ajaxRequest(basePath+"/homework/makeHomework",param,function(res){
                if(res.success){
                    dialog.dialog("close");
                    search();
                    clearData();
                }else alertInfo(res.errorMessage);
                unmask();
            });

        }
    });
}

/**
 * 打开作业卷选择对话框
 */
function openChoosePaperDialog(){
    $("input[id=paperName]").val("");
    $("select[id=difficult]").val("");
    initPaperListTable();
    var dialog = openDialog("choosePaperDialog","choosePaperDialogDiv",
        "选择试卷",1120,600,true,"确定",function(){
            var checkElement = $("#paperListTable tbody input[type='radio']:checked");
            if(checkElement==null || checkElement.length==0){
                alertInfo("请选择一张试卷");
                return;
            }
            var oo = checkElement.parent().parent().parent();
            var aData = paperListTable.fnGetData(oo);
            $("#makeHomeworkForm #paperId").val(aData.id);
            $("#makeHomeworkForm #duration").val(aData.duration);
            $('#startTime').val("");
            $('#endTime').val("");

            if(aData.paperName.length>20){
                $("#paperNameShowSpan").html(aData.paperName.substr(0,20)+'...');
                $("#paperNameShowSpan").attr("title",aData.paperName);
            }else $("#paperNameShowSpan").html(aData.paperName);

            $("#choosePaperButton").addClass("hide");
            $("#paperNameShowSpan").removeClass("hide");
            $("#paperChange").removeClass("hide");
            dialog.dialog("close");
        });
}
/**
 * 初始化作业卷列表
 */
function initPaperListTable(){
    var hasChild = $("#paperListTable").has("tr").length>0;
    if(isnull(paperListTable) || !hasChild){
        var objData = [
            { "title": "选择", "class": "center","sortable": false,"data":"id", "width":"5%","mRender":function (data, display, row) {
                return '<label class="position-relative"><input name="paper_radio" type="radio" value='+data+' class="ace" '+
                    ($("#paperId").val()==data?'checked':'')+'/><span class="lbl"></span></label>';
            }},
            { "title": "试卷名称", "class":"center","sortable":false,"data": 'paperName',"width":"12%" },
            { "title": "难易度", "class":"center","sortable":false,"data": 'difficult' ,"width":"6%","mRender":function (data, display, row) {
                if(row.difficult=='A'){
                    return "简单";
                }else if(row.difficult=='B'){
                    return "一般";
                }else if(row.difficult=='C'){
                    return "困难";
                }else if(row.difficult=='D'){
                    return "非常困难";
                }
            }},
            { "title": "时长（min）", "class":"center","sortable":false,"data": 'duration' ,"width":"5%"},
            { "title": "使用次数", "class":"center","sortable":false,"data": 'useSum' ,"width":"5%"},
            { "title":"操作","sortable": false,"data":"id","class": "center","width":"6%","mRender":function (data, display, row) {
                var str = '<div class="hidden-sm hidden-xs action-buttons">'
                    + '<a class="blue" href="javascript:void(-1);" title="查看" onclick="preview_kc(this)">查看</a></div>';
                return str;
            }
            }];
        var searchPaperCase = new Array();
        searchPaperCase.push('{"tempMatchType":"4","propertyName":"courseId","propertyValue1":"'+$("#courseId").val()+'","tempType":"String"}');
        paperListTable = initTables("paperListTable",basePath+"/homework/queryHomeworkList",objData,true,true,null,null,searchPaperCase,function(data){

        });
    }
    else searchPaperList();
}

/**
 * 搜索作业卷列表
 */
function searchPaperList(){
    var searchPaperCase = new Array();
    searchPaperCase.push('{"tempMatchType":"4","propertyName":"courseId","propertyValue1":"'+$("#courseId").val()+'","tempType":"String"}');
    searchButton(paperListTable,searchPaperCase,"#searchPaperListDiv");
}

/**
 * 预览作业卷
 * @param obj
 */
function privew(listTable,obj){
    var oo = $(obj).parent().parent().parent();
    var aData = listTable.fnGetData(oo);
    var id = aData.id;
    var paperId = aData.paper_id;
    var paperName = aData.paperName;
    var url = basePath+"/homework/preview?";
    if(id != null)url +="id="+id;
    else if(paperId != null)url +="paperId="+paperId+"&paperName="+paperName;
    window.open(url);
}

/**
 * 发布作业
 *
 * @param obj
 */
function publish(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = paperTable.fnGetData(oo);
    var id = aData.id;
    var hintMessage = '';
    if(!isnull(aData.end_time)){
        var endTime = new Date(Date.parse(aData.end_time));
        var nowDate = new Date();
        if((endTime.getTime()-nowDate.getTime()) < 0){
            alertInfo("截止时间已过，请修改!");
            return;
        }
        // if((endTime.getTime()-nowDate.getTime())<600000){
        //     hintMessage='您发布的作业距截止时间已不足10分钟，';
        // }
    }
    showDelDialog(function(){
        mask();
        ajaxRequest(basePath+"/homework/publishHomework",{"id":id},function(ret){
            if(ret.success){
                layer.msg(ret.resultObject);
                search();
            }else layer.msg(ret.errorMessage);
            unmask();
        });
    },"发布作业",hintMessage+"是否确定发布？","确定");
}

/**
 * 删除作业卷
 * @param obj
 */
function deleteHomework(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = paperTable.fnGetData(oo);
    showDelDialog(function(){
        mask();
        ajaxRequest(basePath+"/homework/deleteHomework",{"id":aData.id},function(data){
            if(!data.success){
                layer.msg(data.errorMessage);
            }else{
                layer.msg(data.resultObject);
                search();
            }
            unmask();
        });
    });
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

