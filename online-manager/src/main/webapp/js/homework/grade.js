var gradeTable;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
    loadGradeList();
    $("#courseName").keyup(function(e){
        if(e.keyCode==13)
            search();
    });
    $("#courseId").change(function(){
        search();
    });
});

//班级列表展示
function loadGradeList(){
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
        {title: '序号', "class": "center", "width": "4%","data": 'id',"sortable": false},
        {title: '班级名称', "class": "center","data": 'name', "sortable": false},
        {title: '课程名称', "class": "center","data": 'courseName', "sortable": false},
        {title: '班级人数', "class": "center", "width": "6%","data": 'studentCount', "sortable": false},
        {title: '班主任', "class": "center", "width": "7%","data": 'role_type2', "sortable": false},
        {title: '助教', "class": "center", "width": "7%","data": 'role_type3', "sortable": false},
        {title: '试卷数量', "class": "center", "width": "8%","data": 'paperCount', "sortable": false},
        {
            "sortable": false,"data":"id","class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" onclick="showClassPaper(this)">查看试卷</a></div>';
                return buttons;
            }
        }
    ];

    gradeTable = initTables("cloudClassTable", basePath + "/homework/findGradeList", dataFields, true, true, 1,null,searchJson,function(data){
        
    });
}

function showClassPaper(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = gradeTable.fnGetData(oo);
    window.location.href=basePath+'/home#homework/classPapers?courseId='+aData.courseId+'&classId='+aData.id+'&className='+aData.name;
}

 //条件搜索
 function search(){
     var courseId=$("#courseId").val();
     if(courseId!=null&&courseId!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"courseId","propertyValue1":"' + courseId + '","tempType":"String"}');
     }
     searchButton(gradeTable,searchJson);
     searchJson.pop();
     searchJson.pop();
 }
