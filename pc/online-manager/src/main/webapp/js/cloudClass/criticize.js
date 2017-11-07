var criticizeTable;
var criticizeForm;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
    loadCriticizeList();
});

//列表展示
function loadCriticizeList(){
    var dataFields = [
	      { "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
	      { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'xMenuName' },
	      { "title": "课程类别", "class":"center","width":"9%","sortable":false,"data": 'scoreTypeName' },
	      { "title": "授课方式", "class":"center","width":"8%","sortable":false,"data": 'teachMethodName',"mRender":function (data, display, row) {
	      		return "<span name='skfs'>"+data+"</span>";
	      } },
	      { "title": "班级数", "class":"center","width":"8%","sortable":false,"data": 'countGradeNum'},
	      { "title": "好评率", "class":"center","width":"8%","sortable":false,"data": 'goodCriticizeNum',"mRender":function (data, display, row) {
		    	var hpl = 0;
		    	 if(row.criticizeNum==0)
	    		{
		    		 hpl = 0;
	    		}else{
	    			hpl = Math.round((row.goodCriticizeNum / row.criticizeNum) * 10000)/100;
	    		}
		      	return hpl+"%";
	      } },
	      { "title": "评价条数", "class":"center","width":"8%","sortable":false,"data": 'criticizeNum'},
	      {title:"操作","class": "center","width":"10%","data":"id","sortable": false,"mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="查看评价"  onclick="showCriticizeDetail(\''+row.id+'\',\''+row.courseName+'\')">查看评价</a>';
				buttons += "</div>";
                return buttons;
          }}
    ];

    criticizeTable = initTables("criticizeTable", basePath + "/cloudClass/criticize/findCourseList", dataFields, true, true, 0,null,searchJson,function(data){
    });
    
  //新增根据一级菜单获取相应的二级菜单 暂时用不上，现在没有级联菜单
	$('#menuName').change(function(){


		var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
		 $.ajax({  
			          type:'post',      
			          url:basePath+'/cloudclass/course/getSecoundMenu',  
			          data:{firstMenuNumber:firstMenuNumber},  
			          dataType:'json',  
			          success:function(data){  
		                     
			        	  		var optionstring = ""; 
		                        for (var j = 0; j < data.length; j++) {  
		                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";  
		                        }  
			        	      
		                        $("#menuNameSecond").html("<option value=''>请选择</option> "+optionstring);  
			          }  
			      }) ; 
		
	
		}) ;
	
	//编辑根据一级菜单获取相应的二级菜单
	$('#edid_menuName').change(function(){
		
		var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
		 $.ajax({  
			          type:'post',      
			          url:basePath+'/cloudclass/course/getSecoundMenu',  
			          data:{firstMenuNumber:firstMenuNumber},  
			          dataType:'json',  
			          success:function(data){  
		                     
		                        var optionstring = "";  
		                        for (var j = 0; j < data.length; j++) {  
		                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";  
		                        }  
		                        $("#edid_menuNameSecond").html("<option value=''>请选择</option> "+optionstring); 
			          }  
			      }) ; 
		 
		}) ;
	
	$('#search_menu').change(function(){
	
	var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
	 $.ajax({
		          type:'post',      
		          url:basePath+'/cloudclass/course/getSecoundMenu',  
		          data:{firstMenuNumber:firstMenuNumber},  
		          dataType:'json',  
		          success:function(data){  
	                     
	                        var optionstring = "";  
	                        for (var j = 0; j < data.length; j++) {  
	                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";  
	                        }  
	                        $("#search_scoreType").html("<option value=''>请选择</option> "+optionstring); 
		          }  
		      }) ; 
	 
	}) ;
}

 //条件搜索
 function search(sortType){
	 if(sortType == 1){
         searchJson.push('{"tempMatchType":"9","propertyName":"sortType","propertyValue1":"' + 1 + '","tempType":"String"}');
     }
	 if(sortType == 2){
         searchJson.push('{"tempMatchType":"9","propertyName":"sortType","propertyValue1":"' + 2 + '","tempType":"String"}');
     }
     searchButton(criticizeTable,searchJson);
     
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
}

//查看详情
function showCriticizeDetail(courseId,courseName){
 	 window.location.href=basePath+"/home#/cloudClass/criticize/criticizeDetail?courseId="+courseId+"&courseName="+courseName;
}