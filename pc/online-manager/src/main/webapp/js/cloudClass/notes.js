var notesTable;
var notesForm;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
    loadNotesList();
});

//列表展示
function loadNotesList(){
    var dataFields = [
		  { "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
	      { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'xMenuName' },
	      { "title": "课程类别", "class":"center","width":"9%","sortable":false,"data": 'scoreTypeName' },
	      // { "title": "授课方式", "class":"center","width":"8%","sortable":false,"data": 'teachMethodName',"mRender":function (data, display, row) {
	      // 		return "<span name='skfs'>"+data+"</span>";
	      // } },
	      { "title": "班级数", "class":"center","width":"8%","sortable":false,"data": 'countGradeNum'},
	      { "title": "笔记条数", "class":"center","width":"8%","sortable":false,"data": 'notesNum'},
	      {title:"操作","class": "center","width":"10%","data":"id","sortable": false,"mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="查看笔记"  onclick="showNotesDetail(\''+row.id+'\',\''+row.courseName+'\')">查看笔记</a>';
				buttons += "</div>";
                return buttons;
          }}
    ];

    notesTable = initTables("notesTable", basePath + "/cloudClass/notes/findCourseList", dataFields, true, true, 0,null,searchJson,function(data){
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
}

 //条件搜索
 function search(sortType){
	 if(sortType == 1){
         searchJson.push('{"tempMatchType":"9","propertyName":"sortType","propertyValue1":"' + 1 + '","tempType":"String"}');
     }
     searchButton(notesTable,searchJson);
     
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
}

//查看详情
 function showNotesDetail(courseId,courseName){
  	 window.location.href=basePath+"/home#/cloudClass/notes/notesDetail?courseId="+courseId+"&courseName="+courseName;
 }
 
/**
  * 批量逻辑删除
  * 
  */
$(".dele_bx").click(function(){
 	deleteAll(basePath+"/cloudClass/Notes/deletes",NotesTable);
});
