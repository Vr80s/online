var recordTable;
var searchJson = new Array();
$(function() {
    loadRecordList();
});

//班级列表展示
function loadRecordList(){
    var grade_id = $("#grade_id").val();
    var barrier_id = $("#barrier_id").val();
    var barrier_status = $("#barrier_status").val();
    var user_name = $("#user_name").val();
    searchJson.push('{"tempMatchType":"9","propertyName":"grade_id","propertyValue1":"' + grade_id + '","tempType":"String"}');
    searchJson.push('{"tempMatchType":"9","propertyName":"barrier_id","propertyValue1":"' + barrier_id + '","tempType":"String"}');
    searchJson.push('{"tempMatchType":"9","propertyName":"barrier_status","propertyValue1":"' + barrier_status + '","tempType":"String"}');
    searchJson.push('{"tempMatchType":"9","propertyName":"user_name","propertyValue1":"' + user_name + '","tempType":"String"}');
    var dataFields = [
        {title: '学号', "class": "center","width": "5%","data": 'student_number', "sortable": false},
        {title: '姓名', "class": "center","data": 'user_name', "sortable": false},
        {title: '最近闯关时间', "class": "center", "width": "12%","data": 'create_time', "sortable": false},
        {title: '耗时', "class": "center", "width": "8%","data": 'expend_time', "sortable": false,"mRender":function (data, display, row) {
            return data + "（分钟）";
            }
        },
        {title: '闯关次数', "class": "center", "width": "8%","data": 'recore_num', "sortable": false},
        {title: '总分', "class": "center", "width": "8%","data": 'total_score', "sortable": false},
        {title: '通关分数', "class": "center", "width": "8%","data": 'pass_score_percent', "sortable": false},
        {title: '闯关分数记录', "class": "center", "width": "15%","data": 'score_record', "sortable": false},
        {title: '当前状态', "class": "center", "width": "8%","data": 'barrier_status', "sortable": false,"mRender":function (data, display, row) {
            if (data == 0) {
                return '<span style="color:red;">未通过</span>';
            } else if (data == 1) {
                return '<span style="color:green;">已通过</span>';
            }
        }},
        {
            "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" title="查看闯关试卷" onclick="showExamPaper(this);">查看闯关试卷</a><div>';
                    return buttons;

            }
        }
    ];

    recordTable = initTables("recordTable", basePath + "/markexampapers/barrier/record", dataFields, true, true, 0,null,searchJson,function(data){

    });
}

function showExamPaper(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = recordTable.fnGetData(oo);
    if(aData.recore_num==0){
        alertMsg("该学员暂无闯关记录！");
        return;
    }
    var grade_id = $("#grade_id").val();
    var grade_name = $("#grade_name").val();
    var barrier_id = $("#barrier_id").val();
    var barrier_name = $("#barrier_name").val();
    window.location.href=basePath+'/home#markexampapers/examPaper/index?grade_id='+ grade_id +
        '&user_id='+aData.id+'&grade_name='+grade_name+'&user_name='+aData.user_name +
        '&barrier_id='+barrier_id+'&barrier_name='+barrier_name;
}
function goBack(grade_id,grade_name) {
    window.location.href = basePath + '/home#markexampapers/barrier/index?grade_id='+ grade_id +'&grade_name='+ grade_name;
}
function search(){
    searchButton(recordTable,searchJson);
}

