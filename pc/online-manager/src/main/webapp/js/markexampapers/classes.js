var gradeTable;
var searchJson = new Array();
$(function() {
    loadGradeList();
});

//班级列表展示
function loadGradeList(){
    var dataFields = [
        {title: '序号', "class": "center", "width": "5%", "sortable": false},
        {title: '课程名称', "class": "center","data": 'course_name', "sortable": false},
        {title: '班级名称', "class": "center","data": 'grade_name', "sortable": false},
        {title: '总关数', "class": "center", "width": "8%","data": 'barrier_count', "sortable": false},
        {title: '平均通关率', "class": "center", "width": "8%","data": 'pass_rate', "sortable": false,"mRender":function (data, display, row) {
            return data+"%";
         }
        },
        {title: '班级人数', "class": "center", "width": "8%","data": 'student_count', "sortable": false},
        {
            "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" title="查看关卡列表" onclick="showBarriers(this);">查看关卡列表</a><div>';
                    return buttons;

            }
        }
    ];

    gradeTable = initTables("cloudClassTable", basePath + "/markexampapers/findGradeList", dataFields, true, true, 1,null,searchJson,function(data){

    });
}

function showBarriers(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = gradeTable.fnGetData(oo);
    window.location.href=basePath+'/home#markexampapers/barrier/index?grade_id='+aData.id+'&grade_name='+aData.grade_name;
}
function search(){
    searchButton(gradeTable,searchJson);
}
