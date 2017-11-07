var barrierTable;
var searchJson = new Array();
$(function() {
    loadBarrierList();
});

//班级列表展示
function loadBarrierList(){
    var grade_id = $("#grade_id").val();
    searchJson.push('{"tempMatchType":"9","propertyName":"grade_id","propertyValue1":"' + grade_id + '","tempType":"String"}');
    var dataFields = [
        {title: '序号', "class": "center", "width": "5%", "sortable": false},
        {title: '关卡名称', "class": "center","data": 'barrier_name', "sortable": false},
        {title: '平均通关率', "class": "center","width": "10%","data": 'pass_rate', "sortable": false,"mRender":function (data, display, row) {
        return data+"%";
    }},
        {title: '已通关人数', "class": "center", "width": "10%","data": 'pass_num', "sortable": false},
        {title: '参加人数', "class": "center", "width": "10%","data": 'join_num', "sortable": false},
        {title: '班级人数', "class": "center", "width": "10%","data": 'student_num', "sortable": false},
        {title: '一次通关人数', "class": "center", "width": "10%","data": 'once_num', "sortable": false},
        {title: '两次通关人数', "class": "center", "width": "10%","data": 'twice_num', "sortable": false},
        {
            "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" title="查看学员闯关记录" onclick="showRecords(this);">查看学员闯关记录</a><div>';
                    return buttons;

            }
        }
    ];

    barrierTable = initTables("barrierTable", basePath + "/markexampapers/findbarriers", dataFields, true, true, 1,null,searchJson,function(data){

    });
}

function showRecords(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = barrierTable.fnGetData(oo);
    var grade_id = $("#grade_id").val();
    var grade_name = $("#grade_name").val();
    window.location.href=basePath+'/home#markexampapers/record/index?grade_id='+ grade_id +'&barrier_id='+aData.id+'&grade_name='+grade_name+'&barrier_name='+aData.barrier_name;
}
function goBack(){
    window.location.href=basePath+'/home#markexampapers/index';
}

