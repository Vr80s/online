var examPaperTable;
var examPaperForm;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
    loadExamPaperList();
});

//列表展示
function loadExamPaperList(){
    var dataFields = [
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '试卷名称', "class": "center","width": "15%","data": 'paperName', "sortable": false},
        {title: '难易度', "class": "center", "width": "8%","data": 'difficulty', "sortable": false,"mRender":function (data, display, row) {
        	var difficulty;
        	switch (data)
        	{
	        	case "A":
	        		difficulty = "简单";
	        	  break;
	        	case "B":
	        		difficulty = "一般";
	        	  break;
	        	case "C":
	        		difficulty = "难";
	        	  break;
	        	case "D":
	        		difficulty = "困难";
	        	  break;
        	}
            return difficulty;
        }},
        {title: '时长(min)', "class": "center", "width": "8%","data": 'duration', "sortable": false},
        {title: '使用次数', "class": "center", "width": "8%","data": 'useSum', "sortable": false},
        {title: '组卷人', "class": "center", "width": "8%","data": 'createPersonName', "sortable": false},
        {title: '组卷时间', "class": "center", "width": "12%","data": 'createTime', "sortable": false},
        {title:	"操作","class": "center","width":"12%","data":"id","sortable": false,"mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                			 '<a class="blue" href="javascript:void(-1);" title="查看" onclick="viewExam(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>' +
                			 '<a class="blue" href="javascript:void(-1);" title="修改" onclick="updateExamPaper(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
                			 '<a class="blue" href="javascript:void(-1);" title="删除" onclick="deletes(this);"><i class="ace-icon fa fa-trash-o bigger-130"></i></a> ';
				buttons += "</div>";
                return buttons;
        }}
    ];

    searchJson.push('{"tempMatchType":"9","propertyName":"search_courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');

    examPaperTable = initTables("examPaperTable", basePath + "/exam/examPaper/findExamPaperList", dataFields, true, true, 1,null,searchJson,function(data){
    });
}

$('#returnButton').on('click',function(){
	window.location.href=basePath+"/home#/exam/examPaper/index";
})

//条件搜索
function search(){
	searchJson.splice(0,searchJson.length);
	searchJson.push('{"tempMatchType":"9","propertyName":"search_courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
    searchButton(examPaperTable,searchJson);
    searchJson.pop();
}

$(".add_bx").click(function(kpointId,kpointName){
	window.location.href=basePath+"/home#/exam/examPaper/examPaperAdd?courseId="+$("#courseId").val()+"&courseName="+$("#courseName").val();	
})
/**
 * 修改
 * @param obj
 */
function updateExamPaper(obj){
	var oo = $(obj).parent().parent().parent();
	var row = examPaperTable.fnGetData(oo); // get datarow
	window.location.href=basePath+"/home#/exam/examPaper/examPaperUpdate?courseId="+$("#courseId").val()+
	"&courseName="+$("#courseName").val()+"&id="+row.id+"&paperName="+row.paperName+"&difficulty="+row.difficulty;
}

function deletes(obj){
	var oo = $(obj).parent().parent().parent();
	var row = examPaperTable.fnGetData(oo); // get datarow
	showDelDialog(function(){
		ajaxRequest(basePath+"/exam/examPaper/deletes",{'ids':row.id},function(data){
			if(!data.success){
				alertInfo(data.errorMessage);
			}else{
				if(!isnull(examPaperTable)){
					freshDelTable(examPaperTable);
				}
				layer.msg(data.resultObject);
			}
		});
	},"删除","确认删除该试卷吗？","确认");
}

function viewExam(obj){
	var oo = $(obj).parent().parent().parent();
	var row = examPaperTable.fnGetData(oo); // get datarow
	window.location.href=basePath+"/home#/exam/examPaper/examPaperShow?courseId="+$("#courseId").val()+
	"&courseName="+$("#courseName").val()+"&id="+row.id+"&paperName="+row.paperName+"&difficulty="+row.difficulty;
}