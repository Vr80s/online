var cloudClassMenuTable;
var roleForm;
var searchCase;
var seed=1;
var searchCase = new Array();
var scoreTypeTable = $("#childMenus");
var rowId ;
$(function(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"2%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
	               { "title": "序号", "class": "center","width":"4%","sortable": false,"data":"id" },
	               { "title": "学科名称", "class":"center","width":"8%","sortable":false,"data": 'name' },
					{ "title": "包含的标签数量", "class":"center","width":"5%","sortable":false,"data": 'tagCount' },
	               { "title": "创建人", "class":"center","width":"5%","sortable":false,"data": 'createPerson' },
					{ "title": "创建日期", "class":"center","width":"5%","sortable":false,"data": 'createTime' },
					{ "title": "权限状态", "class":"center","width":"5%","sortable":false,"data": 'askLimit',"mRender":function (data, display, row) {
						return row.askLimit=="1"?"全部公开":"只对付费课程公开";
						}
					},
					{ "title": "状态", "class":"center","width":"5%","sortable":false,"data": 'boStatus',"mRender":function (data, display, row) {
						return row.boStatus=="1"?"已启用":"已禁用";
						}
					},
					{ "sortable": false,"data":"id","class": "center","width":"5%","title":"排序","mRender":function (data, display, row) {
						if(row.boStatus ==1){//如果是禁用
				    		return '<div class="hidden-sm hidden-xs action-buttons">'+
				    		'<a class="blue" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				        	'<a class="blue" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
				    	}else{
				    		return '<div class="hidden-sm hidden-xs action-buttons">'+
				    		'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				        	'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
				    	}
//				   		var buttons= '<div class="hidden-sm hidden-xs action-buttons">';
//							buttons+='<a class="blue" id="moveUp" href="javascript:void(-1);" title="上移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
//							'<a class="blue" id="moveDown" href="javascript:void(-1);" title="下移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a>';
//							return buttons;
			   			}
					},
	               { "sortable": false,"data":"id","class": "center","width":"5%","title":"操作","mRender":function (data, display, row) {
	            	   		if(row.courseCount ==0){
	            	   			var buttons= '<div class="hidden-sm hidden-xs action-buttons">'+
								/*'<a class="blue" href="javascript:void(-1);" title="查看" onclick="openShow(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>'+
								'<a class="blue" href="javascript:void(-1);" title="修改" onclick="editDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+*/
	            	   			'<a class="blue" href="javascript:void(-1);" title="标签管理" onclick="openMenuManage(this)"><i class="glyphicon  glyphicon-tag"></i></a>'+
							   '<a class="blue" href="javascript:void(-1);" title="设置权限" onclick="openMenuLimit(this)"><i class="glyphicon glyphicon-wrench"></i></a>';
					   			if(row.boStatus==1) {
									buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
								}else{
									buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
								};
								return buttons;
	            	   		}else {
		      				
			      				var buttons= '<div class="hidden-sm hidden-xs action-buttons">'+
							/*	'<a class="blue" href="javascript:void(-1);" title="查看" onclick="openShow(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>'+
								'<a class="gray" href="javascript:void(-1);" title="修改" onclick=""><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+*/
			      				'<a class="blue" href="javascript:void(-1);" title="标签管理" onclick="openMenuManage(this)"><i class="glyphicon  glyphicon-tag"></i></a>'+
			      				'<a class="blue" href="javascript:void(-1);" title="设置权限" onclick="openMenuLimit(this)"><i class="glyphicon glyphicon-wrench"></i></a>';
					   			if(row.boStatus==1) {
									buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
								}else{
									buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
								};
								return buttons;
	            	   		}
					   		
				   		}
		      		},
		      		{ "title": "排序", "class": "center","width":"80px","sortable": false,"data":"sort","visible":false },
		      		{ "title": "包含的课程", "class": "center","width":"80px","sortable": false,"data":"courseName","visible":false },
		      		{ "title": "权限", "class": "center","width":"80px","sortable": false,"data":"askLimit","visible":false }
		      		];
	cloudClassMenuTable = initTables("cloudClassTable",basePath+"/ask/menu/list",objData,true,true,2,null,searchCase,function(data){
			var iDisplayStart = data._iDisplayStart;
			var countNum = data._iRecordsTotal;//总条数
			pageSize = data._iDisplayLength;//每页显示条数
			currentPage = iDisplayStart / pageSize +1;//页码
			if(currentPage == 1){//第一页的第一行隐藏向上箭头
//				$("#cloudClassTable tbody tr:first td:last a:eq(4)").hide();
				$("#cloudClassTable tbody tr:first td").eq(8).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
			if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
//				$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
				$("#cloudClassTable tbody tr:last td").eq(8).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
			var countPage;
			if(countNum%pageSize == 0){
				countPage = parseInt(countNum/pageSize);
			}else{
				countPage = parseInt(countNum/pageSize) + 1;
			}
//			if(countPage == currentPage){//隐藏最后一条数据下移
////				$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
//				$("#cloudClassTable tbody tr:last td").eq(8).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
//			}
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
	roleForm = $("#role-form").validate({
		messages:{
			menuName:{
				required:"请输入学科名称！",
				minlength:"学科名称过短，应大于4个字符！",
				maxlength:"学科名称过长，应小于14个字符！"

			}
		}
	});

	updateForm = $("#update-form").validate({
		messages:{
			menuName:{
				required:"请输入学科名称！",
				minlength:"学科名称过短，应大于4个字符！",
				maxlength:"学科名称过长，应小于14个字符！"
			}
		}
	});

	//下线时间 时间控件
	createDatetimePicker3($(".datetime-picker"),"yy-mm-dd");
});

/**
 * 检查学科名称的长度
 */
function checkNameLength(){
	var name = $("#aname").val();
	if(name.length <4 ){
		alertInfo("学科名称过短，应大于4个字符");
		return ;
	}

	if(name.length > 14 ){
		alertInfo("学科名称过短，应大于4个字符");
		return ;
	}
}


$(function(){

	
	 /* 添加 */
      $(".add_bx").click(function(){
		  ajaxRequest(basePath+"/ask/menu/maxSort",null,function(result){
			  $("input[name='code']").removeAttr("disabled");//增加角色把代码框放开
			  roleForm.resetForm();
			 // $("#remark").val(result.resultObject+1); 去掉之前sort的默认值
			  openDialog("roleDialog","dialogRoleDiv","新增学科",500,330,true,"提交",function(){
				  if($("#role-form").valid()){
					  mask();
					  $("#role-form").attr("action", "cloudClass/menu/add");
					  $("#role-form").ajaxSubmit(function(data){
						  unmask();
						  if(data.success){
							  $("#roleDialog").dialog("close");
							  layer.msg(data.errorMessage);
							  freshTable(cloudClassMenuTable);
						  }else{
							  layer.msg(data.errorMessage);
						  }
					  });
				  }
			  });
				
		  });
      });


      /*  删除*/
      $(".dele_bx").click(function(){
      	  deleteAll('user/role/deletes',cloudClassMenuTable);
      });

      /* 修改 */
      $(".rolexg").click(function(){

      });
});


/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = cloudClassMenuTable.fnGetData(oo);
	ajaxRequest(basePath+"/ask/menu/updateStatus",{"id":aData.id,"status":aData.status},function(data){
		if(data.success){
			  layer.msg(data.errorMessage);
			  freshTable(cloudClassMenuTable);
		  }else{
			  layer.msg(data.errorMessage);
		  }
	});
}

/**
 * 当复选框没有选中时，执行插入操作。
 * @param obj
 */
function childrenMenusUpdateStatus(event,id,menuId,check){
	if($(event.target).is(':checked') == false){
			ajaxRequest(basePath+"/ask/menu/removeMenuCourseType",{"id":id,"menuId":menuId},function(){
				//freshTable(cloudClassMenuTable);
		});
	}else if($(event.target).is(':checked') == true){
			ajaxRequest(basePath+"/ask/menu/addMenuCourseType",{"id":id,"menuId":menuId},function(){
				//freshTable(cloudClassMenuTable);
		});
	}
	
}

/**
 * 当复选框为选中状态时，点击时将
 * @param id
 * @param menuId
 */
//function childrenMenusUpdateStatus(id,menuId,check){
//		
//}

/**
 * 上移
 * @param obj
 */
function childrenMenusUpMove(id,courseCount){
	var parentId =$("#parentId").val();
	ajaxRequest(basePath+"/ask/menu/scoreTypeUp",{"id":id},function(res){
		ajaxRequest(basePath+"/ask/menu/childMenu",{'id':parentId},function(data) {
			drawMenusPage(data,parentId,courseCount);
			if(courseCount==0){
				$("input:checkbox").removeAttr("disabled");
			}
			var dialog = openDialogNoBtnName("childMenuDialog", "childMenuDialogDiv", "关联课程类别",580,450, true, "确定", null);
			$("#childMenuDialogDiv .ui-button-text-only" ).click(function(){
				freshTable(cloudClassMenuTable);
			});
				
			
		});
	});
}

/**
 * 下移
 * @param obj
 */
function childrenMenusDownMove(id,courseCount){
	var parentId =$("#parentId").val();
	ajaxRequest(basePath+"/ask/menu/scoreTypeDown",{"id":id},function(res){
		ajaxRequest(basePath+"/ask/menu/childMenu",{'id':parentId},function(data) {
			drawMenusPage(data,parentId,courseCount);
			if(courseCount==0){
				$("input:checkbox").removeAttr("disabled");
			}
			var dialog = openDialogNoBtnName("childMenuDialog", "childMenuDialogDiv", "关联课程类别",580,450, true, "确定", null);
			$("#childMenuDialogDiv .ui-button-text-only" ).click(function(){
				freshTable(cloudClassMenuTable);
			});
				
			
		});
	});
}


/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = cloudClassMenuTable.fnGetData(oo);
	ajaxRequest(basePath+"/ask/menu/up",{"id":aData.id},function(res){
		if(res.success){
			freshTable(cloudClassMenuTable);
		}
	});
}

/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = cloudClassMenuTable.fnGetData(oo);
	ajaxRequest(basePath+"/ask/menu/down",{"id":aData.id},function(res){
		if(res.success){
			freshTable(cloudClassMenuTable);
		}
	});
}


function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = cloudClassMenuTable.fnGetData(oo);
	showDelDialog(function(){
		mask();
		var url = "user/role/delete";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			if(!data.success){
				alertInfo(data.errorMessage);
			}else{
				freshTable(cloudClassMenuTable);
			}
		});
	});
}

function editDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = cloudClassMenuTable.fnGetData(oo); // get datarow
	$("#update_id").val(aData.id);
	$("#update_menuName").val(aData.name);
	$("#update_remark").val(aData.remark);
	openDialog("updateDialog","updateDialogDiv","修改学科",500,330,true,"提交",function(){
		if($("#update-form").valid()){
			mask();
			$("#update-form").attr("action", "cloudClass/menu/update");
			$("#update-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#updateDialog").dialog("close");

					layer.msg(data.errorMessage);
					freshTable(cloudClassMenuTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
}

function openShow(obj){
	var oo = $(obj).parent().parent().parent();
	var row = cloudClassMenuTable.fnGetData(oo); // get datarow
	$("#show_menuName").text(row.name);
	$("#show_status").text(row.status=="1"?"已启用":"已禁用");
	$("#show_sort").text(row.sort);
	$("#show_createPerson").text(row.createPerson);
	$("#show_createTime").text(row.createTime);
	$("#show_childMenuNames").text(row.childMenuNames);
	$("#show_courseName").text(row.courseName);
	$("#show_remark").text(row.remark);
	var dialog = openDialogNoBtnName("previewCloudClasMenuDialog","previewCloudClasMenuDialogDiv","查看学科",680,500,false,"确定",null);
}

//开启权限管理窗口
function openMenuLimit(obj){
	
	$("#limit-form").resetForm();
	
	var oo = $(obj).parent().parent().parent();
	var row = cloudClassMenuTable.fnGetData(oo); // get datarow 

	if(row.askLimit==1){
		$("#allLimit").attr("checked",true);
		$("#partLimit").attr("checked",false);
	}else {
		$("#partLimit").attr("checked",true);
		$("#allLimit").attr("checked",false);
	}
	
	$("#LimitMenuid").val(row.id);
	$("#limitMenu").text(row.name);
	$("#limit-form").attr("action", basePath+"/ask/menu/updateAskMenuLimit");
	openDialog("LimitDialog","dialogLimitDiv","权限管理",580,350,true,"提交",function(){
	
			$("#limit-form").ajaxSubmit(function(data){
				unmask();
				try{
            		data = jQuery.parseJSON(jQuery(data).text());
            	}catch(e) {
            		data = data;
            	  }
				if(data.success){
					$("#LimitDialog").dialog("close");
					layer.msg(data.errorMessage);
					freshTable(cloudClassMenuTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});
	
	});
}

function openMenuManage(obj){
	var oo = $(obj).parent().parent().parent();
	var aData =cloudClassMenuTable.fnGetData(oo);
	window.location.href=basePath+'/home#ask/tag/tagIndex?page='+getCurrentPageNo(cloudClassMenuTable)+'&menuName='+aData.name+'&menuId='+aData.id;
}



function cancelRow(trId){
	$(trId).hide();
}
//function saveMenusRow(menus_name_id,pid){
//	var menuName=$("#"+menus_name_id).val();
//	if(menuName!=null&&menuName.length>0){
//		ajaxRequest(basePath+"/ask/menu/addChildren",{'menuName':menuName,'parentId':pid,'rowId':rowId},function(data) {
//			if(data.success){
//				ajaxRequest(basePath+"/ask/menu/childMenu",{'pid':pid},function(data) {
//					drawMenusPage(data,pid);
//				})
//			}
//		});
//	}
//}
function appendRow(id){
	$("#childMenus").append("<tr id='tr_"+seed+"'><td><input type='text' id='menus_name_"+seed+"' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' value='保存' onclick='saveMenusRow(\"menus_name_"+seed+"\",\""+id+"\")'/>&nbsp;&nbsp;&nbsp;<input type='button' value='取消' onclick='cancelRow(tr_"+seed+")'/></td></tr>");
	seed++;
}

//function deleteRow(rowId,pid){
//	ajaxRequest(basePath+"/ask/menu/deleteChildren",{'id':rowId},function(data) {
//		if(data.success){
//			ajaxRequest(basePath+"/ask/menu/childMenu",{'pid':pid},function(data) {
//				drawMenusPage(data,pid);
//			})
//		}
//	});
//}

function drawMenusPage(data,id,courseCount){
	//console.log(data);
	$("#childMenus").html("");
	if(data.success){
		for(var i=0;i<data.resultObject.length;i++){
			var rowData="<tr id='childMenus_tr_"+data.resultObject[i].id+"'><td> ";
			if(data.resultObject[i].checked=='true'){
				rowData+="<input style='margin-top:-1px' type='checkbox' name='childMenuNames'  checked='checked' disabled='disabled' value='"+data.resultObject[i].id+"' /></td><td>"+data.resultObject[i].name+"</td>";
			}else{
				rowData+="<input style='margin-top:-1px' type='checkbox' name='childMenuNames'  value='"+data.resultObject[i].id+"' /></td><td>"+data.resultObject[i].name+"</td>";
			}
			rowData+="</td>";
			rowData+="<td>";
			rowData+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			if(i!=0){
				rowData+="<a class='blue' href='javascript:void(-1);' title='上移' onclick='childrenMenusUpMove(\""+data.resultObject[i].id+"\",\""+courseCount+"\")'><i class='glyphicon glyphicon-arrow-up bigger-130'></i></a>";
			}else{
				//rowData+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				rowData+="<a style='pointer-events:none' class='gray' href='javascript:void(-1);' title='上移' onclick='childrenMenusDownMove(\""+data.resultObject[i].id+"\",\""+courseCount+"\")'><i class='glyphicon glyphicon-arrow-up bigger-130'></i></a>";
			}
			if(i+1!=data.resultObject.length){
				rowData+="<a class='blue' href='javascript:void(-1);' title='下移' onclick='childrenMenusDownMove(\""+data.resultObject[i].id+"\",\""+courseCount+"\")'><i class='glyphicon glyphicon-arrow-down bigger-130'></i></a>";
			}else{
				//rowData+="&nbsp;&nbsp;&nbsp;&nbsp;";
				rowData+="<a style='pointer-events:none' class='gray' href='javascript:void(-1);' title='下移' onclick='childrenMenusUpMove(\""+data.resultObject[i].id+"\",\""+courseCount+"\")'><i class='glyphicon glyphicon-arrow-down bigger-130'></i></a>";
			}
			//rowData+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='blue' href='javascript:void(-1);' title='增加' onclick='appendRow("+pid+")'><i class='glyphicon glyphicon-plus bigger-130'></i></A>&nbsp;&nbsp;<a class='blue' href='javascript:void(-1);' title='删除' onclick='deleteRow("+data.resultObject[i].id+","+pid+")'><i class='glyphicon glyphicon-minus bigger-130'></i></A>"
			rowData+="</td>";
			rowData+="</tr>";
			$("#childMenus").append(rowData);

		}
	}
}

function search(){
	var timeStart = $("#time_start").val();
	var timeEnd = $("#time_end").val();

	if(timeStart != "" || timeEnd != ""){
		if(timeEnd != "" && timeStart == ""){
			alertInfo("开始时间不能为空");
			return;
		}
		if(timeStart != "" && timeEnd == ""){
			alertInfo("结束时间不能为空");
			return;
		}
		if(timeStart > timeEnd){
			alertInfo("开始时间不能大于结束时间");
			return;
		}
		searchCase.push('{"tempMatchType":"7","propertyName":"time_start","propertyValue1":"'+timeStart+'","tempType":"String"}');
		searchCase.push('{"tempMatchType":"6","propertyName":"time_end","propertyValue1":"'+timeEnd+'","tempType":"String"}');
	}
	searchButton(cloudClassMenuTable,searchCase);
	searchCase.pop();
	searchCase.pop();
}

function deleteBatch(){
	deleteAll(basePath+"/ask/menu/deletes",cloudClassMenuTable);
}


//配置角色权限
function configResource(obj){ 
	var oo = $(obj).parent().parent().parent();
	var aData = cloudClassMenuTable.fnGetData(oo); // get datarow
	//alertInfo("配置" + aData.name + "权限");
	//debugger;
	var roleId = aData.id;
	initCommonZtrees("resource","resource2","user/resource/role/tree",{"roleId":roleId},"right",function(){//左右两边都加载
			initCommonZtrees("resource","resource2","user/resource/tree",{},"left");
	});
	openDialog("configDialog","dialogConfigDiv","分配权限",560,480,true,"提交",function(){
		var resourceIds = new Array();
		if(!isnull(Tree2)){
			var nodes = getAllNodes(Tree2);
			if(nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					var id = nodes[i].id;
					var name = nodes[i].name;
					resourceIds.push(id);
				}
			}
		}
		var url = "user/role/update/resources";
		var data = {"roleId":roleId,"resourceIds":resourceIds.join(",")};
		ajaxRequest(url,data,function(result){
			if(result.success){
					$("#configDialog").dialog("close");
			}else{
				alertInfo(result.errorMessage);
			}
		});
	});
}