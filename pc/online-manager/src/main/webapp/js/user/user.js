var teacherTable;
var teacherForm;
$(function(){
	var objData = [{ "title": createAllCheckBox(),"class":"center","width":"60px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
                        return createCheckBox(data);
                    }},
	               { "title": "序号", "class": "center","width":"80px","sortable": false,"data":"id" },
	               { "title": "姓名", "class":"center","sortable":false,"data": 'name' },
	               { "title": "登录名", "class":"center","sortable":false,"data": 'loginName' },
	               { "title": "状态", "class":"center","sortable":false,"data": 'isDelete',"mRender":function (data, display, row) {
					   if(row.delete==true){
						   return "禁用";
					   }else{
						   return "启用";
					   }
				   } },
	               { "title": "所属角色", "class":"center","sortable":false,"data": 'roleNames' },
	               { "title": "创建时间", "class":"center","sortable":false,"data": 'createTimeStr' },
	               { "sortable": false,"data":"id","class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
		      				return '<div class="hidden-sm hidden-xs action-buttons">'+
		      				'<a class="blue" href="javascript:void(-1);" title="查看" onclick="viewUserDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
							'<a class="blue" href="javascript:void(-1);" title="重置密码" onclick="resetPassword(this)"><i class="ace-icon fa fa-key bigger-130"></i></a>'+
							'<a class="blue" href="javascript:void(-1);" title="修改" onclick="editDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
							'<a class="red" href="javascript:void(-1);" title="删除" onclick="delDialog(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
		      			  }
		      		}];
	teacherTable = initTables("teacherTable",basePath+"/user/list",objData,true,true,2);
	teacherForm = $("#teacher-form").validate({
		messages:{
            name:{
                required:"姓名不能为空"
            },
            mobile:{
                required:"手机不能为空"
            },
            loginName:{
            		required:"登录名不能为空"
            }
		}
	});
});

$(function(){
	 /* 添加 */
      $(".add_bx").click(function(){
      teacherForm.resetForm();
      $("#loginName").removeAttr("readonly");
      $("#password").prop("placeholder", "默认为123456");
      $("#description").text("");
    	  openDialog("teacherDialog","dialogTeacherDiv","新增用户",720,510,true,"提交",function(){
    			if($("#teacher-form").valid()){
    				mask();
    				$("#teacher-form").attr("action", "user/add");
    				$("#teacher-form").ajaxSubmit(function(data){
					unmask();
					if(data.success){
						$("#teacherDialog").dialog("close");
						alertInfo(data.errorMessage);
						freshTable(teacherTable);
					}else{
						alertInfo(data.errorMessage);
					}
				});
    			}
    		});
      });
    
      /*  赋角色*/ 
      $(".user").click(function(){
    	  
    	    var uids = getCheckedIds(teacherTable);
	  		if(uids.length < 1){
	  			alertInfo("请选择用户！");
	  			return;
	  		}
	  		//姜海成 - 清空checkbox选择 - 160127
	  		$(":checkbox[name='roleIds']").prop("checked",false);
	  		if(uids.length == 1){
	  			//获取当前用户角色ID
	  			$.get(basePath+"/user/get/userRoleIds",{"userId":uids[0]},function(data){
	  					if(data.resultObject != null && data.resultObject != ""){
		  					
	  						var arr = data.resultObject.split(",");
		  					
		  					$(":checkbox[name='roleIds']").each(function(){
		  						for(var i = 0;i<arr.length;i++){
		  							if($(this).attr("value")==arr[i]){
		  								$(this).prop("checked",true);
		  							}
		  						}
		  					});

	  					}else{

	  					}
	  				}
	  			)
	  		}

        	  openDialog("roleDialog","dialogRoleDiv","赋予角色",750,330,true,"提交",function(){
        	  		
        	  		$("#userIds").val(uids.join(","));
        			if($("#role-form").valid()){
        				mask();
        				$("#role-form").attr("action", "user/edit/role");
        				$("#role-form").ajaxSubmit(function(data){
	    					unmask();
	    					if(data.success){
	    						$("#roleDialog").dialog("close");
	    					}else{
	    						alertInfo(data.errorMessage);
	    					}
	    					freshTable(teacherTable);
    					});
        			}
        		});
        	  	
          });
      /*  删除*/
      $(".dele_bx").click(function(){
      	  deleteAll('user/deletes',teacherTable);
      });
      
      /* 修改 */
      $(".rolexg").click(function(){
    	  
      });
});

function viewUserDialog(obj){
	
	$("#uiName,#uiLoginName,#uiMobile,#uiIdentity,#uiEmail,#uiqq,#uiDelete,#uiEducation,#uiDescription").text('');
	
	var oo = $(obj).parent().parent().parent();
	var aData = teacherTable.fnGetData(oo); // get datarow
	$("#uiName").text(aData.name);
	$("#uiLoginName").text(aData.loginName);
	$("#uiMobile").text(aData.mobile);
	$("#uiIdentity").text(aData.identity);
	$("#uiEmail").text(aData.email);
	$("#uiqq").text(aData.qq);
	$("#uiDelete").text(aData.isDelete ? "禁用" : "启用");
	$("#uiEducation").text(aData.education);
	$("#uiDescription").text(aData.description);
	/*
	openDialog("userInfoDialog","dialogUserInfoDiv","详细信息",620,400,true,"确定", function(){
		$("#userInfoDialog").dialog("close");
	});
	*/
	var dialog = openDialogNoBtnName("userInfoDialog","dialogUserInfoDiv","查看用户",543,400,false,"确定",null);
	/*var dialog=openDialog("userInfoDialog","dialogUserInfoDiv","查看用户",543,430,false);
	dialog.find("#close").click(function(){
		dialog.dialog("close");
	});*/
}

function editDialog(obj){
	teacherForm.resetForm();
	var oo = $(obj).parent().parent().parent();
	var aData = teacherTable.fnGetData(oo); // get datarow
	$("#id").val(aData.id);
	$("#name").val(aData.name);
	$("#loginName").val(aData.loginName);
	$("#loginName").prop("readonly","readonly")
	$("#mobile").val(aData.mobile);
	$("#identity").val(aData.identity);
	$("#email").val(aData.email);
	$("#qq").val(aData.qq);
	$("#password").prop("placeholder", "");
	if(aData.delete){
		$("#delete_true").prop("checked", "checked");
	}else{
		$("#delete_false").prop("checked", "checked");
	}
	$("#education").val(aData.education);
	$("#description").text(aData.description);
	//console.log("aData.delete:" + aData.delete);
	
	openDialog("teacherDialog","dialogTeacherDiv","修改用户",720,500,true,"提交",function(){
		if($("#teacher-form").valid()){
			mask();
			$("#teacher-form").attr("action", "user/update");
			$("#teacher-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#teacherDialog").dialog("close");
					alertInfo(data.errorMessage);
					freshTable(teacherTable);
				}else{
					alertInfo(data.errorMessage);
				}
			});
		}
	});
}

function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = teacherTable.fnGetData(oo);
	showDelDialog(function(){
		mask();
		var url = "user/delete";
		ajaxRequest(url,{'id':aData.id},function(data){
			unmask();
			if(data.success){
				alertInfo(data.errorMessage);
				freshTable(teacherTable);
			}else{
				alertInfo(data.errorMessage);
			}
		});
	});
}

function search(){
	searchButton(teacherTable);
}

function getCheckedIds(dataTable){
	var ids = new Array();
	$(".dataTable tbody input[type='checkbox']:checked").each(function(){
		ids.push($(this).val());
	});
	return ids;
}

function resetPassword(obj){
	openDialog("resetPasswordDialog","dialogResetPasswordDiv","重置密码",450,260,true,"提交",function(){
			mask();
			var oo = $(obj).parent().parent().parent();
			var aData = teacherTable.fnGetData(oo); // get datarow
			var url = "user/reset/password";
			ajaxRequest(url,{'loginName':aData.loginName},function(data){
				unmask();
				if(!data.success){
					alertInfo(data.errorMessage);
				}else{
					$("#resetPasswordDialog").dialog("close");
					alertInfo(data.errorMessage);
					//freshTable(teacherTable);
				}
			});
	});
}