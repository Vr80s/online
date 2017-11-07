var roleTable;
var roleForm;
$(function(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"60px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
	               { "title": "序号", "class": "center","width":"80px","sortable": false,"data":"id" },
	               { "title": "角色代码", "class":"center","sortable":false,"data": 'code' },
	               { "title": "角色名称", "class":"center","sortable":false,"data": 'name' },
	               //{ "title": "状态", "class":"center","sortable":false,"data": 'isDelete',"mRender":function (data, display, row) {return data=='true'?"禁用":"启用"} },
	               { "title": "创建时间", "class":"center","sortable":false,"data": 'createTime' },
	               { "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
		      				return '<div class="hidden-sm hidden-xs action-buttons">'+
							'<a class="blue" href="javascript:void(-1);" title="分配权限" onclick="configResource(this)"><i class="ace-icon fa fa-cog bigger-130"></i></a>'+
							'<a class="blue" href="javascript:void(-1);" title="修改" onclick="editDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
							'<a class="red" href="javascript:void(-1);" title="删除" onclick="delDialog(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
		      			  }
		      		}];
	roleTable = initTables("roleTable",basePath+"/user/role/list",objData,true,true,2);
	roleForm = $("#role-form").validate({
		messages:{
			name:{
				required:"角色名称不能为空"
			},
			code:{
                required:"角色代码不能为空"
            },
            description:{
                required:"角色名称不能为空"
            }
		}
	});
});

$(function(){
	 /* 添加 */
      $(".add_bx").click(function(){
      $("input[name='code']").removeAttr("disabled");//增加角色把代码框放开
      roleForm.resetForm();
    	  openDialog("roleDialog","dialogRoleDiv","新增角色",550,330,true,"提交",function(){
    			if($("#role-form").valid()){
    				mask();
    				$("#role-form").attr("action", "user/role/add");
    				$("#role-form").ajaxSubmit(function(data){
					unmask();
					if(data.success){
						$("#roleDialog").dialog("close");
						alertInfo(data.errorMessage);
						freshTable(roleTable);
					}else{
						alertInfo(data.errorMessage);
					}
				});
    			}
    		});
      });
      
      
      /*  删除*/
      $(".dele_bx").click(function(){
      	  deleteAll('user/role/deletes',roleTable);
      });
      
      /* 修改 */
      $(".rolexg").click(function(){
    	  
      });
});

function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = roleTable.fnGetData(oo);
	showDelDialog(function(){
		mask();
		var url = "user/role/delete";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			if(!data.success){
				alertInfo(data.errorMessage);
			}else{
				alertInfo(data.errorMessage);
				freshTable(roleTable);
			}
		});
	});
}

function editDialog(obj){
	$("input[name='code']").attr("disabled","disabled");//角色代码不允许修改
	roleForm.resetForm();
	var oo = $(obj).parent().parent().parent();
	var aData = roleTable.fnGetData(oo); // get datarow
	$("#id").val(aData.id);
	$("#code").val(aData.code);
	$("#name").val(aData.name);
	//console.log("aData.delete:" + aData.delete);
	if(aData.isDelete=='true'){
		$("#delete_true").prop("checked", "checked");
	}else{
		$("#delete_false").prop("checked", "checked");
	}
	openDialog("roleDialog","dialogRoleDiv","修改角色",550,330,true,"提交",function(){
		if($("#role-form").valid()){
			mask();
			$("#role-form").attr("action", "user/role/update");
			$("#role-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#roleDialog").dialog("close");
					alertInfo(data.errorMessage);
					freshTable(roleTable);
				}else{
					alertInfo(data.errorMessage);
				}
			});
		}
	});
}

function search(){
	searchButton(roleTable);
}

//配置角色权限
function configResource(obj){ 
	var oo = $(obj).parent().parent().parent();
	var aData = roleTable.fnGetData(oo); // get datarow
	//alertInfo("配置" + aData.name + "权限");
	//debugger;
	var roleId = aData.id;
	console.log("roleId:" + roleId);
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
					alertInfo(result.errorMessage);
			}else{
				alertInfo(result.errorMessage);
			}
		});
	});
}