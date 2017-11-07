var criticizeTable;
var criticizeForm;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
	//初始化Ztree
	initZtree();

	loadCriticizeList();

    //时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));
	
	document.onkeydown=function(event){}
});

//列表展示
function loadCriticizeList(){
    var dataFields = [
	      { "title": "评价内容", "class":"center","sortable":false,"data": 'content' ,"mRender":function (data, display, row) {
	    	  var content = "<a href='javascrip:void(0);return false;' class='blue' onclick='showCriticizeDetail(this);return false;'>"+data.replace(/<.*?>/ig,"").replace(/[\r\n]/g,"")+"</a>";
              return content;
	      }},
	      { "title": "评价星级", "class":"center","width":"10%","sortable":false,"data": 'starLevel' ,"mRender":function (data, display, row) {
              return data+"星";
	      }},
	      { "title": "点赞数", "class":"center","width":"8%","sortable":false,"data": 'praiseSum' },
	      { "title": "作者/用户昵称", "class":"center","width":"12%","sortable":false,"data": 'createPersonName'},
	      { "title": "所属班级", "class":"center","width":"15%","sortable":false,"data": 'gradeName'},
	      { "title": "回复状态", "class":"center","width":"10%","sortable":false,"data": 'response',"mRender":function (data, display, row) {
	    	  if(data && data.length > 0){
	    		  return '已回复';
	    	  }
	    	  return '未回复';
	      }},
	      { "title": "创建日期", "class":"center","width":"12%","sortable":false,"data": 'createTime'},
	      {title:"操作","class": "center","width":"8%","data":"id","sortable": false,"mRender":function (data, display, row) {
                var buttons= 
                '<div class="hidden-sm hidden-xs action-buttons">'+
                	'<a class="blue" href="javascript:void(-1);" title="回复" onclick="response(this)"><i class="ace-icon glyphicon glyphicon-envelope bigger-130"></i></a>'+
                	'<a class="blue" href="javascript:void(-1);" title="删除" onclick="deletesCriticize(\''+data.trim()+'\')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>'+
				'</div>';
                return buttons;
          }}
    ];
    searchJson.push('{"tempMatchType":"9","propertyName":"search_courseId","propertyValue1":"' + $("#search_courseId").val() + '","tempType":"String"}');
    criticizeTable = initTables("criticizeTable", basePath + "/cloudClass/criticize/findCriticizeList", dataFields, true, true, 0,null,searchJson,function(data){
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
function showCriticizeDetail(obj){
	var oo = $(obj).parent().parent();
	var aData = criticizeTable.fnGetData(oo);
	
	$("#show_id").val(aData.id);
	$("#showDiv").html(aData.content);
	
	var dialog = openDialog("showCriticizeDialog","dialogShowCriticizeDiv","评价内容",480,300,true,"删除此评论",function(){
		ajaxRequest(basePath+"/cloudClass/criticize/deletes",{'ids':$("#show_id").val()},function(data){
			if(!data.success){
				//alertInfo(data.errorMessage);
				layer.msg(data.errorMessage);
			}else{
				unmask();
				if(!isnull(criticizeTable)){
					dialog.dialog( "close" ); 
                	$("html").css({'overflow-x':'hidden','overflow-y':'auto'});
					freshDelTable(criticizeTable);
				}
				if(typeof(data.errorMessage) != "undefined"){
					//alertInfo(data.errorMessage);
					layer.msg(data.errorMessage);
				}
			}
		});
		
 	});
}

/**
 * 批量逻辑删除
 * 
 */
function deletesCriticize(id){
	showDelDialog(function(){
		ajaxRequest(basePath+"/cloudClass/criticize/deletes",{'ids':id},function(data){
			if(!data.success){
				//alertInfo(data.errorMessage);
				layer.msg(data.errorMessage);
			}else{
				if(!isnull(criticizeTable)){
					freshDelTable(criticizeTable);
				}
				if(typeof(data.errorMessage) != "undefined"){
					//alertInfo(data.errorMessage);
					layer.msg(data.errorMessage);
				}
			}
		});
	},"","","");
}



function initZtree(){
	var position = $(".contrightbox").offset();
	layer.load(1, {
		  shade: [0.1,'#fff'] ,//0.1透明度的白色背景
		  offset: [(position.top + 158) + 'px', (position.left + 128) + 'px']
		});
	//------------------------Ztree初始化开始-----------------------------------------------
	/**
	 * 
	 * @param objId  对象的ID
	 * @param url    请求的后台地址
	 * @param callback  成功后的回调方法
	 * @param addTree   右键添加方法
	 * @param updateTree  右键修改方法
	 * @param deleteTree  右键删除方法
	 * @returns
	 */
	treeObj = loadTreeGenerally("ztree",basePath+"/cloudclass/videores/showTreeCriticize?courseId="+$("#courseId").val()+"&courseName="+$("#courseName").val(),function(){
		$("#"+localStorage.spnode).trigger("click");
		$("#"+localStorage.snode).trigger("click");
	},function(node){
		var ids=[];
		ids = getChildren(ids,node);
		$("#search_chapterId").val(ids.join(","));
		$("#search_chapterLevel").val(node.level+1);
		
		search();
	});

//------------------------Ztree初始化结束-----------------------------------------------
	function getChildren(ids,treeNode){

		ids.push(treeNode.id);

		if (treeNode.isParent){

				for(var obj in treeNode.children){

					getChildren(ids,treeNode.children[obj]);

				}

		   }

		return ids;

	}

	$('#returnbutton').on('click',function(){
		window.location.href=basePath+'/home#cloudClass/criticize/index';
	});
	
}

//回复
function response(obj){
	var oo = $(obj).parent().parent().parent();
	var rowData = criticizeTable.fnGetData(oo); // get datarow
	$('#criticizeId').val(rowData.id);
	$('#criticizeContent').text(rowData.content);
	$('#responseContent').val(rowData.response);
	openDialog("responseDialog","dialogResponseDiv","评价回复",450,350,true,"提交",function(){
		mask();
		$("#responseForm").attr("action", basePath+'/cloudClass/criticize/addResponse');
		$("#responseForm").ajaxSubmit(function(data){
			unmask();
			if(data.success){
				$("#responseDialog").dialog("close");
			}else{
				alertInfo(data.errorMessage);
			}
			freshTable(criticizeTable);
		});
	});
}

