var fcodeDetailTable;
var searchJson = new Array();
var validateForm;
$(function() {
	loadFcodeDetailList();
})
//列表展示
function loadFcodeDetailList(){
	
	searchJson.push('{"tempMatchType":"9","propertyName":"lotNo","propertyValue1":"'+$("#lotNo").val()+'","tempType":"String"}');
	var dataFields = [{title: '兑换码', "class": "center", "width": "5%","height":"68px","data": 'fcode', "sortable": false,"mRender":function (data, display, row) {
					      if(row.status==3){
					    		return data;
					    	}else {
					    		return data.substring(0,4)+"*************";
					    	}
					   }},
	                  {title: '生成时间', "class": "center", "width": "5%","height":"68px","data": 'createTime', "sortable": false},
	                  {title: '兑换时间', "class": "center", "width": "5%","height":"68px","data": 'lockTime', "sortable": false},
	                  {title: '使用时间', "class": "center", "width": "5%","height":"68px","data": 'useTime', "sortable": false},
	                  {title: '使用状态', "class": "center", "width": "5%","height":"68px","data": 'status', "sortable": false,"mRender":function (data, display, row) {
	                  	if(data==0){
	                		return "未发放";
	                	}else if(data==1){
	                		return "已发放";
	                	}else if(data==2){
	                		return "已领取";
	                	}else if(data==3){
	                		return "已使用";
	                	}else if(data==4){
	                		return "已作废";
	                	}
	                  } },
	                  {title: '兑换课程', "class": "center", "width": "5%","height":"68px","data": 'courseName', "sortable": false},
	                  {title: '兑换课程ID', "class": "center", "width": "5%","height":"68px","data": 'usedCourseId', "visible": false},
	                  {title: '使用人(用户名)', "class": "center", "width": "5%","height":"68px","data": 'name', "sortable": false},
	                  {title: '使用人(账号)', "class": "center", "width": "5%","height":"68px","data": 'loginName', "sortable": false},
	                  ];
	fcodeDetailTable = initTables("fcodeTable", basePath + "/factivity/fcode/findFcodeDetailList", dataFields, true, true, 0,null,searchJson,function(data){
    });                  
	
}

/**
 * 搜索
 */
function search(){
	searchJson.push('{"tempMatchType":"9","propertyName":"lotNo","propertyValue1":"'+$("#lotNo").val()+'","tempType":"String"}');
	searchButton(fcodeDetailTable,searchJson);
	searchJson.pop();
};