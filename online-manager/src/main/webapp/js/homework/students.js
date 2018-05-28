var studentTable;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
    loadGradeList();
    $("#studentName").keyup(function(e){
        if(e.keyCode==13)
            search();
    });
    $("#status").change(function(){
        search();
    });
    $('#returnbutton').on('click',function(){
        var courseId = $("#courseId").val();
        var classId = $("#classId").val();
        var className = $("#className").val();
        window.location.href=basePath+'/home#homework/classPapers?courseId='+courseId+'&classId='+classId+'&className='+className;
    });
});

//学员列表展示
function loadGradeList(){
    var checkbox = '<label class="position-relative"><input type="checkbox" class="ace" onclick="chooseAll(this)" /><span class="lbl"></span></label>';
    var dataFields = [
        { "title": checkbox,"class":"center","width":"4%","sortable":false,"visible":true,"data": 'id' ,"mRender":function(data,display,row){
            var enable = row.status == '2';
            return createStudentPaperListCheckBox(enable,data);//处于发布状态的才可以被勾选
        }},
        {title: '学号', "class": "center", "width": "10%","data": 'student_number', "sortable": false},
        {title: '姓名', "class": "center","data": 'studentName', "sortable": false},
        {title: '提交时间', "class": "center", "width": "14%","data": 'submit_time', "sortable": false},
        {title: '耗时', "class": "center", "width": "14%","data": 'expendTime', "sortable": false},
        {title: '得分', "class": "center showSortIcon", "width": "12%","data": 'score', "sortable": true},
        {title: '状态', "class": "center", "width": "12%","sortable": false,"mRender":function (data, display, row) {
            if(row.status==0){
                return "未交卷";
            }else if(row.status==1){
                return "未批阅";
            }else if(row.status==2){
                return "已批阅";
            }else if(row.status==3){
                return "已发布";
            }
        }},
        {
            "sortable": false,"data":"id","class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons">';
                if(row.status==0) {
                    buttons +='-';
                }else if(row.status==1){
                    buttons +='<a class="blue" href="javascript:void(-1);" onclick="readOverPaper(this)">批阅</a>';
                }else if(row.status==2){
                    buttons +='<a class="blue" href="javascript:void(-1);" onclick="readOverPaper(this)">修改</a>' +
                        '<a class="blue" href="javascript:void(-1);" onclick="publish(this)">发布</a>';
                }else if(row.status==3){
                    buttons +='<a class="blue" href="javascript:void(-1);" onclick="previewPaper(this)">查看</a>';
                }
                buttons += '</div>';
                return buttons;
            }
        }
    ];
    var classPaperId=$("#classPaperId").val();
    searchJson.push('{"tempMatchType":"9","propertyName":"classPaperId","propertyValue1":"' + classPaperId + '","tempType":"String"}');
    var classId=$("#classId").val();
    searchJson.push('{"tempMatchType":"9","propertyName":"classId","propertyValue1":"' + classId + '","tempType":"String"}');
    studentTable = initTables2("studentTable", basePath + "/homework/queryStudentList", dataFields, true, true, null,null,searchJson,function(data){

    });
}

/**
 * 创建复选框
 * @param enable 是否置灰
 * @param data 复选框value
 * @returns {String}
 */
function createStudentPaperListCheckBox(enable,data){
    var checkBoxhtml =  '<label class="position-relative">';
    if(enable)
        checkBoxhtml += '<input type="checkbox" value='+data+' class="ace" />';
    else
        checkBoxhtml += '<input type="checkbox" class="ace" disabled="disabled" />';
    return checkBoxhtml + '<span class="lbl"></span></label>';
}

/**
 * 批阅试卷
 * @param obj
 */
function readOverPaper(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = studentTable.fnGetData(oo);
	window.location.href=basePath+'/home#homework/readOverPaper?classPaperId='+$("#classPaperId").val()+'&className='+$("#className").val()+'&classId='+$("#classId").val()
    +'&paperName='+$("#paperName").val()+'&courseId='+$("#courseId").val()+'&userId='+aData.user_id+'&studentName='+aData.studentName+'&userPaperId='+aData.id;
}
/**
 * 查看试卷
 * @param obj
 */
function previewPaper(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = studentTable.fnGetData(oo);
    window.location.href=basePath+'/home#homework/readOverPaper?classPaperId='+$("#classPaperId").val()+'&className='+$("#className").val()+'&classId='+$("#classId").val()
        +'&paperName='+$("#paperName").val()+'&courseId='+$("#courseId").val()+'&userId='+aData.user_id+'&studentName='+aData.studentName+'&userPaperId='+aData.id+'&type=pre';
}

 //条件搜索
 function search(){
     var classPaperId=$("#classPaperId").val();
     searchJson.push('{"tempMatchType":"9","propertyName":"classPaperId","propertyValue1":"' + classPaperId + '","tempType":"String"}');
     var classId=$("#classId").val();
     searchJson.push('{"tempMatchType":"9","propertyName":"classId","propertyValue1":"' + classId + '","tempType":"String"}');
     searchButton(studentTable,searchJson);
     searchJson.pop();
 }

/**
 * 发布成绩
 * @param obj
 */
function publish(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = studentTable.fnGetData(oo);
    var classPaperId=$("#classPaperId").val();
    ajaxRequest(basePath+"/homework/saveUserResult",{'userPaperIds':aData.id,'classPaperId':classPaperId},function(data){
        if(data.success){
            layer.msg("发布成功！");
            search();
        }else{
            layer.msg(data.errorMessage);
        }
    });
}
/**
 * 批量发布成绩
 */
 function batchPublish(){
    var classPaperId=$("#classPaperId").val();
     var ids = new Array();
     var trs = $(".dataTable tbody input[type='checkbox']:checked");
     for(var i = 0;i<trs.size();i++){
         ids.push($(trs[i]).val());
     }
     if(ids.length>0){
         confirmInfo('是否发布共计'+ids.length+'名学员成绩？', function() {//点击确定按钮
             ajaxRequest(basePath + "/homework/saveUserResult", {
                 'userPaperIds': ids.join(","),
                 'classPaperId': classPaperId
             }, function (data) {
                 if (data.success) {
                     layer.msg("批量发布成功！");
                     search();
                 } else {
                     layer.msg(data.errorMessage);
                 }
             });
         });
     }else{
         alertInfo("请选择学员！");
     }
 }
/**
 * 下载附件
 */
function downLoad(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/homework/downLoad",{'ids':ids.join(",")},function(data){
            if(!data.success){//如果失败
                layer.msg(data.errorMessage);
            }else{
                layer.msg("附件下载成功！");
                search();
            }
        });
    }else{
        alertInfo("请选择学员！");
    }
}
