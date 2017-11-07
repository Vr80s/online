var gradeTable;
var gradeForm;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
    loadGradeList();
    //下线时间 时间控件
    createDatePicker($(".datetime-picker"));
});

//班级列表展示
function loadGradeList(){
    var dataFields = [
        {title: '序号', "class": "center", "width": "5%", "sortable": false},
        {title: '编号', "class": "center", "width": "10%","data": 'id', "sortable": false, "visible":false},
        {title: '课程名称', "class": "center","data": 'courseName', "sortable": false},
        {title: '课程编号', "class": "center","data": 'courseId', "sortable": false, "visible":false},
        {title: '班级名称', "class": "center","data": 'name', "sortable": false},
        {title: '班级人数', "class": "center", "width": "7%","data": 'studentCount', "sortable": false},
        {title: '讲师', "class": "center", "width": "6%","data": 'role_type1', "sortable": false},
        {title: '班主任', "class": "center", "width": "7%","data": 'role_type2', "sortable": false},
        {title: '助教', "class": "center", "width": "6%","data": 'role_type3', "sortable": false},
        {title: '开课时间', "class": "center", "width": "8%","data": 'curriculumTime', "sortable": false},
        {title: '结课日期', "class": "center", "width": "8%","data": 'stopTime', "sortable": false},
        {title: '状态', "class": "center", "width": "6%", "data": 'otcStatus', "sortable": false,"mRender":function(data,display,row){
            return data==1 ? '已开班':'未开班'
        }},
        {
            "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {


                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" title="管理学员" onclick="student_manager(this);">管理学员</a>';
                    return buttons;

            }
        }
    ];

    gradeTable = initTables("cloudClassTable", basePath + "/cloudClass/studentGrade/findGradeList", dataFields, true, true, 1,null,searchJson,function(data){
        var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize +1;//页码
        if(currentPage == 1){//第一页的第一行隐藏向上箭头
            $("#cloudClassTable tbody tr:first td:last a:eq(3)").css("pointer-events","none").removeClass("blue").addClass("gray");
        }
        if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
            $("#cloudClassTable tbody tr:last td:last a:eq(4)").css("pointer-events","none").removeClass("blue").addClass("gray");
        }
        var countPage;
        if(countNum%pageSize == 0){
            countPage = parseInt(countNum/pageSize);
        }else{
            countPage = parseInt(countNum/pageSize) + 1;
        }
        if(countPage == currentPage){//隐藏最后一条数据下移
            $("#cloudClassTable tbody tr:last td:last a:eq(4)").css("pointer-events","none").removeClass("blue").addClass("gray");
        }
    });
}

function student_manager(obj)
{
    var oo = $(obj).parent().parent().parent();
    var aData = gradeTable.fnGetData(oo);
    window.location.href=basePath+'/home#cloudClass/studentGrade/student?page='+getCurrentPageNo(gradeTable)+'&gradeId='+aData.id;
}

$(function() {
    /* 添加 */
    $(".add_bx").click(function () {
        addGradeDialog();
    });

    /*  删除*/
    $(".del_bx").click(function(){
        deleteAll(basePath+"/cloudClass/grade/deleteGrades",gradeTable);
    });
});



 //条件搜索
 function search(){
     var startTime=$("#sstart_time").val(); //开始时间
     var endTime=$("#sstop_time").val(); //结束时间
     var menuId=$("#menuId").val();
     var teachMethodId=$("#teachMethodId").val();
     var scoreTypeId=$("#scoreTypeId").val();
     var courseId=$("#courseId").val();
     var gradeStatus=$("#gradeStatus").val();

     if(startTime != "" || endTime != "") {
         if (endTime != "" && startTime == "") {
             alertInfo("开课时间不能为空");
             return;
         }
         if (startTime != "" && endTime == "") {
             alertInfo("结课时间不能为空");
             return;
         }
         if (startTime > endTime) {
             alertInfo("开课时间不能大于结课时间");
             return;
         }
         searchJson.push('{"tempMatchType":"7","propertyName":"curriculumTime","propertyValue1":"' + startTime + '","tempType":"String"}');
         searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + endTime + '","tempType":"String"}');
     }
     if(menuId!=null&&menuId!="-1")
     {
         searchJson.push('{"tempMatchType":"8","propertyName":"menuId","propertyValue1":"' + menuId + '","tempType":"String"}');
     }
     if(scoreTypeId!=null&&scoreTypeId!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"scoreTypeId","propertyValue1":"' + scoreTypeId + '","tempType":"String"}');
     }
     if(courseId!=null&&courseId!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"courseId","propertyValue1":"' + courseId + '","tempType":"String"}');
     }
     if(gradeStatus!=null&&gradeStatus!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"gradeStatus","propertyValue1":"' + gradeStatus + '","tempType":"String"}');
     }
     if(teachMethodId!=null&&teachMethodId!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"teachMethodId","propertyValue1":"' + teachMethodId + '","tempType":"String"}');
     }
     searchButton(gradeTable,searchJson);
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
 }
