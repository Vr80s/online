var gradeTable;
var gradeForm;
var gradeteacherform;
var searchJson = new Array();
var courseArray=new Array();
var titleArray= new Array();
$(function() {
    loadQuerstionList();
    //下线时间 时间控件
    createDatePicker($("#startTime"));
    createDatePicker($("#stopTime"));
});

//投诉列表展示
function loadQuerstionList(){
    var dataFields = [
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '投诉内容', "class": "center","width": "45%","data": 'content', "sortable": false,"mRender":function(data,display,row){
        	titleArray[row.id]=data.replace(/[\r\n]/g,"");
        	return "<span class='titleSpan' id='"+row.id+"'></span>";//data.replace(/[\r\n]/g,"");
        }},
        {title: '投诉类型', "class": "center", "width": "8%","data": 'accuseType', "sortable": false,"classes":"td-type","mRender":function(data,display,row){
        	var content ;
        	if(data == 0){
        		content = "广告营销";
            }else if(data == 1){
            	content = "抄袭内容";
            }else if(data == 2){
            	content = "人身攻击";
            }else if(data == 3){
            	content = "色情反动";
            }else if(data == 4){
            	content = "<div title='"+row.accuseTypeContent+"' class='divelse'>其他</div>";
            }

        	return content;
        }},
        {title: '投诉用户', "class": "center", "width": "9%","data": 'createPersonName', "sortable": false},
        {title: '被投诉用户', "class": "center", "width": "9%","data": 'accusePersonName', "sortable": false},
        {title: '投诉日期', "class": "center", "width": "10%","data": 'createTime', "sortable": false},
        {title: '投诉状态', "class": "center", "width": "8%", "data": 'status', "sortable": false,"mRender":function(data,display,row){
        	var status ;
        	if(data == 0){
        		status = "未处理";
            }else if(data == 1){
            	status = "已删除";
            }else if(data == 2){
            	status = "无效投诉";
            }

        	return status;
        }},
        {
            "sortable": false,"data":"id","class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {


                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" title="查看详情" onclick="showAccusePage(\''+row.questionId+'\',\''+row.id+'\')"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
                return buttons;
            }
        }
    ];

    accuseTable = initTables("accuseTable", basePath + "/ask/accuse/findAccuseList", dataFields, true, true, 1,null,searchJson,function(data){
    	$(".titleSpan").each(function(index){
    		$(this).text(titleArray[$(this).attr("id")]);
    	});
    	//赋值完成后清空
    	titleArray.length = 0;
    	
    	tableFont40();
    	tableTitle();
    });
}

 //条件搜索
 function search(orders){
	 
     var startTime = $("#startTime").val(); //开始时间
     var stopTime = $("#stopTime").val(); //结束时间
     var accuseType = $("#accuseType").val();
     var status = $("#status").val();
     var content = $("#content").val();

     
     if(startTime != "" || stopTime != "") {
         
    	 if (startTime != "" && stopTime != "" && startTime > stopTime) {
             alertInfo("开始日期不能大于结束日期");
             return;
         }
         searchJson.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
         searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
     }
     
     if(accuseType != ""){
    	 searchJson.push('{"tempMatchType":"8","propertyName":"accuseType","propertyValue1":"' + accuseType + '","tempType":"String"}');
     }
     
     if(status!=null&&status!=""){
         searchJson.push('{"tempMatchType":"9","propertyName":"status","propertyValue1":"' + status + '","tempType":"String"}');
     }

     if(content!=null&&content!=""){
         searchJson.push('{"tempMatchType":"10","propertyName":"content","propertyValue1":"' + content + '","tempType":"String"}');
     }
     searchButton(accuseTable,searchJson);
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();

}

 //删除页面
 function showAccusePage(questionId,id){
	 $.ajax({  
	        type:'get',      
	        url:basePath+'/ask/accuse/checkAccuseStatus',  
	        data:{"id":id},
	        dataType:'json',
	        success:function(data){
	        	if(data.success == false){//未解决
	        		var newTab=window.open('about:blank');
	        		debugger;
		       		 newTab.location.href = weburl+"/web/jumpMethod/"+questionId+"/2/"+mname;
	        	}else{//已解决
	        		alertInfo(data.errorMessage);
	        	}
	        }  
	   });
}
 
//修改文本显示带...
 function tableFont40(){
 	var MaxLength = 40;//设置一行显示的长度
     var txt = new Array();
     var texts = $("table .center");
     for (var i = 0; i < texts.length; i++) {
         if (texts.eq(i).text().length > MaxLength) {
             texts.eq(i).parent().index(i);
             txt[i] = texts.eq(i).text();
             var st = texts.eq(i).text().substr(0, MaxLength) + '...';
             texts.eq(i).attr("title",txt[i]);
             texts.eq(i).text(st);
         }
     }
 }

//修改文本显示带...
 function tableTitle(){
     var texts = $("table .divelse");
     for (var i = 0; i < texts.length; i++) {
         texts.eq(i).parent().attr("title",texts.eq(i).attr("title"));
     }
 }