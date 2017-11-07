var examTable;
var examForm;
var searchJson = new Array();
$(function() {
    loadExamList();
});

//列表展示
function loadExamList(){
    var dataFields = [
          { "title": '序号', "class": "center", "width":"5%","data": 'id',datafield: 'xuhao', "sortable": false},
          { "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
	      { "title": "所属学科", "class":"center","sortable":false,"data": 'xMenuName' },
	      { "title": "试卷数量", "class":"center","sortable":false,"data": 'paperNum'},
	      { "title": "课程状态", "class":"center","sortable":false,"data": 'status',"mRender":function (data, display, row) {
	      	if(data==1){
	      		return data="<span name='zt'>启用</span>";
	      	}else{
	      		return data="<span name='zt'>禁用</span>";
	      	}
	      }},
	      {title:"操作","class": "center","width":"11%","data":"id","sortable": false,"mRender":function (data, display, row) {
              var buttons= 
            	'<div class="hidden-sm hidden-xs action-buttons">'+
            	'<a class="blue" href="javascript:void(-1);" title="查看试卷" onclick="showExamPaper(\''+row.id+'\',\''+row.courseName+'\')">查看试卷</a>'+
				'</div>';
              return buttons;
         }}
    ];

    examTable = initTables("examTable", basePath + "/exam/examPaper/findCourseList", dataFields, true, true, 1,null,searchJson,function(data){
    });

}

 //条件搜索
 function search(){
     searchButton(examTable,searchJson);
}

//查看试卷
function showExamPaper(courseId,courseName){
  	 window.location.href=basePath+"/home#/exam/examPaper/examPaper?courseId="+courseId+"&courseName="+courseName;
}

function useExam(courseId){
	 confirmDialog(function(){
		 $.ajax({
			 url:basePath + '/exam/exam/useExam',
			 data:{courseId:courseId},
			 type:'post',
			 dataType:'json',
			 success:function(data){
				 if(data.success){
					 layer.msg('操作成功！');
					 freshTable(examTable);
				 } else {
					 alertInfo(data.errorMessage);
				 }
			 }
		 });
	 },'提示','启用关卡后将不能：禁用、新增、删除、修改上级，确定要启用吗？','确定启用');
 }