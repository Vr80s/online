var notesTable;
var commentTable;
var notesForm;
var nowTime;
var searchJson = new Array();
var searchJson2 = new Array();
$(function() {
	//初始化Ztree
	initZtree();
	
    loadNotesList();
    
    //时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));
});

//列表展示
function loadNotesList(){
    var dataFields = [
		  { "title": "笔记内容", "class":"center","sortable":false,"data": 'content' ,"mRender":function (data, display, row) {
	    	  var content = "<a href='javascrip:void(0);' onclick='showNotesDetail(this);return false;'>"+data.replace(/<.*?>/ig,"").replace(/[\r\n]/g,"")+"</a>";
              return content;
	      }},
	      { "title": "点赞数", "class":"center","width":"9%","sortable":false,"data": 'praiseSum' },
	      { "title": "作者/用户昵称", "class":"center","width":"12%","sortable":false,"data": 'createPersonName'},
	      { "title": "所属班级", "class":"center","width":"15%","sortable":false,"data": 'gradeName'},
	      { "title": "创建日期", "class":"center","width":"12%","sortable":false,"data": 'createTime'},
	      {title:"操作","class": "center","width":"8%","data":"id","sortable": false,"mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="删除" onclick="deletesNotes(\''+data.trim()+'\')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>';
				buttons += "</div>";
                return buttons;
          }}
    ];
    searchJson.push('{"tempMatchType":"9","propertyName":"search_courseId","propertyValue1":"' + $("#search_courseId").val() + '","tempType":"String"}');
    notesTable = initTables("notesTable", basePath + "/cloudClass/notes/findNotesList", dataFields, true, true, 0,null,searchJson,function(data){
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
     searchButton(notesTable,searchJson);
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
}

 /**
  * 批量逻辑删除
  * 
  */
 function deletesNotes(id){
 	showDelDialog(function(){
 		ajaxRequest(basePath+"/cloudClass/notes/deletes",{'ids':id},function(data){
 			if(!data.success){
 				//alertInfo(data.errorMessage);
 				layer.msg(data.errorMessage);
 			}else{
 				if(!isnull(notesTable)){
 					freshDelTable(notesTable);
 				}
 				if(typeof(data.errorMessage) != "undefined"){
 					//alertInfo(data.errorMessage);
 					layer.msg(data.errorMessage);
 				}
 			}
 		});
 	},"","","");
}

 /**
  * 批量逻辑删除
  * 
  */
 function deletesNotesComment(id){
	 showDelDialog(function(){
		 ajaxRequest(basePath+"/cloudClass/notesComment/deletes",{'ids':id},function(data){
			 if(!data.success){
				 //alertInfo(data.errorMessage);
				 layer.msg(data.errorMessage);
			 }else{
				 if(!isnull(commentTable)){
					 freshDelTable(commentTable);
				 }
				 if(typeof(data.errorMessage) != "undefined"){
					 //alertInfo(data.errorMessage);
					 layer.msg(data.errorMessage);
				 }
			 }
		 });
	 },"","","");
 }


//查看详情
function showNotesDetail(obj){
	var oo = $(obj).parent().parent();
	var aData = notesTable.fnGetData(oo);
	$("#show_id").val(aData.id);
	$("#showDiv").html(aData.content);
	
	//初始化评论表格
	var dataFields = [
	      { "title": "评价内容", "class":"left","width":"90%","sortable":false,"data": 'id' ,"mRender":function (data, display, row) {
	    	  var content = "<div style='display:block;float:left;width:15%;margin-right: 10px;font-size:15'>"+row.createPerson+":</div><div style='display:block;float:left;width:80%;nowrap:false;font-size:15'>"+row.content +"</div>";
              return content;
	      }},
	      {title:"操作","class": "center","width":"10%","data":"id","sortable": false,"mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons delBtn" style="font-size:15"><a class="blue" href="javascript:void(-1);" title="删除" onclick="deletesNotesComment(\''+data.trim()+'\')">删除</a>';
				buttons += "</div>";
                return buttons;
          }}
    ];

    searchJson2.push('{"tempMatchType":"9","propertyName":"notesId","propertyValue1":"' + $("#show_id").val() + '","tempType":"String"}');

	commentTable = initTables("commentTable", basePath + "/cloudClass/notesComment/findNotesCommentList", dataFields, true, true, 0,null,searchJson2,function(data){
    	$("#commentTable thead").hide();
    	$("#commentTable_length").hide();
    	$("#commentTable_length").parent().remove();
    	$("#commentTable_info").hide();
    	$("#commentTable_info").parent().remove();
    	$("#commentTable_paginate").parent().attr("class","col-xs-12");
    	$("#commentTable_paginate").css("text-align","center");
    	
    	$(".delBtn").parent().css("width","10%");
    	$(".delBtn").parent().prev().css("text-align","left");
    	$(".delBtn").parent().prev().css("padding","0");
    	$(".delBtn").parent().prev().css("white-space","pre-wrap");
    	
		$(".delBtn").parent().parent().parent().parent().attr("border","0");
    	$(".delBtn").parent().parent().parent().parent().css("border","none");
    	
    	if($("#commentTable .dataTables_empty").is(":visible")){//如果没有记录可见
    		$("#commentTable_paginate").remove();
    	}
    	
    },5);
	
	var dialog = openDialog("showNotesDialog","dialogShowNotesDiv","笔记详情",1000,570,false,null,null); 
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
	treeObj = loadTreeGenerally("ztree",basePath+"/cloudclass/videores/showTreeNotes?courseId="+$("#courseId").val()+"&courseName="+$("#courseName").val(),function(){
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
		window.location.href=basePath+'/home#cloudClass/notes/index';
	});
	
}
