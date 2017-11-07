var resourceTable;
var resourceForm;

function initLoadTreeGrid(){
	var url = "user/resource/list";
	reLoadTreeGrid(url);
}

function reLoadTreeGrid(url){
	var dataFields = [ { name : 'id' }, 
	                   { name : 'name', type : 'string'}, 
	                   { name : 'parentId', type : 'string'}, 
	                   { name : 'displayOrder', type : 'int' },
	                   { name : 'isDelete', type : 'boolean' },
	                   { name : 'permission', type : 'string'}, 
	                   { name : 'url', type : 'string'},
	                   { name : 'description', type : 'string'},
	                   { name : 'typeDesc', type : 'string'},
	                   { name : 'type', type : 'string'}]
	var columns = [ { text: '全选', align : 'center',columntype: 'checkbox', width : '6%',datafield: 'Discontinued' },
	                { text : '名称', dataField : 'name', width : '10%', align : 'center',cellsalign : 'center' }, 
	                { text : '状态', dataField : 'delete', width : '10%', align : 'center', cellsalign : 'center',	cellsRenderer: function (id, column, value) {
//	                		alertInfo("id:" + id + " columnName:" + column + " value:" + value);
//	                	debugger
	                		return value?"禁用":"启用"
	                	}}, 
	                { text : '权限代码', dataField : 'permission', width : '20%', align : 'center', cellsalign : 'center' }, 
	                { text : '资源类型', dataField : 'typeDesc', align : 'center', cellsalign : 'center'},
	                { text : '操作',  align : 'center', cellsalign : 'center',
	                	cellsRenderer: function (id, column, value) {
	                		return '<div class="hidden-sm hidden-xs action-buttons">'+
							'<a class="blue" href="javascript:void(-1);" title="添加子资源" onclick="addSubResourceDialog(\''+id+'\')"><i class="ace-icon fa fa-plus-square bigger-130"></i></a>' + 
							'<a class="blue" href="javascript:void(-1);" title="修改" onclick="editResourceDialog(\''+id+'\')"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
							'<a class="red" href="javascript:void(-1);" title="删除" onclick="delResourceDialog(\''+id+'\')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>';
		      			  }
	                } ];
	/**
	 * @param url  请求后台的url
	 * @param dataFields 映射对象
	 * @param columns  列对象
	 * @param isExpand  是否展开树
	 * @param isPage  是否显示分页 
	 */
	loadTreeGrid(url,dataFields,columns,true,false);
}

$(function(){
	initLoadTreeGrid();
	resourceTable = $('#treeGrid');
	resourceForm = $("#resource-form").validate({
		messages:{
            name:{
                required:"名称不能为空"
            },
            permission:{
                required:"权限代码不能为空"
            }
		}
	});
	$('#treeGrid').on('rowCheck', function(event) {
		var args = event.args;
		var row = args.row;
		var key = args.key;
		var children = row.children;
		//debugger;
		var children = row.children;
		if(!isnull(children)){
			for(var i=0;i<children.length;i++){
		    		$("#treeGrid").jqxTreeGrid('checkRow', children[i].id);
		    }
		}
	});
	
	$('#treeGrid').on('rowUncheck', function(event) {
		var args = event.args;
		var row = args.row;
		var key = args.key;
		var children = row.children;
		//debugger;
		if(!isnull(children)){
			for(var i=0;i<children.length;i++){
		    		$("#treeGrid").jqxTreeGrid('uncheckRow', children[i].id);
		    }
		}
		//$("#treeGrid").jqxTreeGrid('uncheckRow', row.parentId);
	});
	/**
	 * 判断td宽度是否超过 超过添加title
	 */
	$("#pageContentArea").on("mouseover"," #tabletreeGrid td, #tabletreeGrid th",function(){
		if($(this).get(0).scrollWidth>$(this).get(0).clientWidth){
			var text = $(this).text();
			$(this).attr("title",text.trim());
		}
	});
});

$(function(){
	/* 添加 */
    $(".add_bx").click(function(){
      	addResourceDialog();
    });
      
  /*  删除*/
  $(".dele_bx").click(function(){
  	  var deleteResIds = new Array();
  	  var checkedRows = $("#treeGrid").jqxTreeGrid('getCheckedRows');
      for (var i = 0; i < checkedRows.length; i++) {
          var rowData = checkedRows[i];
          deleteResIds.push(rowData.id);
      }
  	  if(deleteResIds.length>0){
		showDelDialog(function(){
			var url='user/resource/deletes';
			ajaxRequest(url,{'ids':deleteResIds.join(",")},function(data){
				if(!data.success){
					alertInfo(data.errorMessage);
				}else{
					initLoadTreeGrid();
				}
			});
		});
	}else{
		alertInfo("请选择删除对象！");
	}
  });
});

function addResourceDialog(){
  resourceForm.resetForm();
  $("#parentId").val("0");
  openDialog("resourceDialog","dialogResourceDiv","新增资源 ",720,600,true,"提交",function(){
		if($("#resource-form").valid()){
			mask();
			$("#resource-form").attr("action", "user/resource/add");
			$("#resource-form").ajaxSubmit(function(data){
			unmask();
			if(data.success){
				$("#resourceDialog").dialog("close");
				initLoadTreeGrid();
			}else{
				alertInfo(data.errorMessage);
			}
		});
		}
	});
}

function addSubResourceDialog(id){
	resourceForm.resetForm();
	var aData =  resourceTable.jqxTreeGrid('getRow', id);
	$("#parentId").val(id);
	$("#type").val(aData.type);
	$("#permission").val(aData.permission);
	openDialog("resourceDialog","dialogResourceDiv","新增'"+aData.name+"'子资源 ",720,500,true,"提交",function(){
		if($("#resource-form").valid()){
			mask();
			$("#resource-form").attr("action", "user/resource/add");
			$("#resource-form").ajaxSubmit(function(data){
			unmask();
			if(data.success){
				$("#resourceDialog").dialog("close");
				initLoadTreeGrid();
			}else{
				alertInfo(data.errorMessage);
			}
		});
		}
	});
}

function editResourceDialog(id){
	resourceForm.resetForm();
	var aData =  resourceTable.jqxTreeGrid('getRow', id);
	$("#id").val(aData.id);
	$("#parentId").val(aData.parentId);
	$("#name").val(aData.name);
	$("#type").val(aData.type);
	$("#permission").val(aData.permission);
	$("#url").val(aData.url);
	$("#icon").val(aData.icon);
	$("#displayOrder").val(aData.displayOrder);
	$("#description").val(aData.description);
//	debugger;
	if(aData.delete){
		$("#delete_true").prop("checked", "checked");
		$("#delete_false").prop("checked", "");
	}else{
		$("#delete_false").prop("checked", "checked");
		$("#delete_true").prop("checked", "");
	}
	//console.log("aData.delete:" + aData.delete);
	
	openDialog("resourceDialog","dialogResourceDiv","修改资源",720,600,true,"提交",function(){
		if($("#resource-form").valid()){
			mask();
			$("#resource-form").attr("action", "user/resource/update");
			$("#resource-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#resourceDialog").dialog("close");
					initLoadTreeGrid();
				}else{
					alertInfo(data.errorMessage);
				}
			});
		}
	});
}

function delResourceDialog(id){
	var row = $("#treeGrid").jqxTreeGrid('getRow', id);
	var title = "删除数据?";
	var content = "确定删除该条数据及其关联数据吗？";
	if(row.leaf){//叶子节点不包含子资源
		var url = "user/resource/using";
		syncRequest(url,{'resourceId':id},function(data){
			if(data == "true"){//资源在使用
				//initLoadTreeGrid();
			}else if(data == "false"){//资源未使用
				content = "确定要删除该条数据吗?";
			}else{
				alertInfo(data);
			}
		});
	}
	doDelResourceDialog(id, title, content);
}

function doDelResourceDialog(id, title, content){
	var fun = function(){
		mask();
		var url = "user/resource/delete";
		ajaxRequest(url,{'id':id},function(data){
			unmask();
			if(data.success){
				initLoadTreeGrid();
			}else{
				alertInfo(data.errorMessage);
			}
		});
	}
	showDelDialog(fun, title, content);
}

function search(){
	var valid = $("#seachValidId").val();
	var type = $("#seachResourceTypeId").val();
	var url = "user/resource/list?type=" + type + "&valid=" + valid;
	reLoadTreeGrid(url);
}

$(function(){
	$("#displayOrder").keyup(function(){
		var tmptxt=$(this).val();
		$(this).val(tmptxt.replace(/\D|^0/g,''));
		if(!$(this).val()){
			$(this).val(0);
		}
	}).bind("paste",function(){
		var tmptxt=$(this).val();
		$(this).val(tmptxt.replace(/\D|^0/g,''));
		if(!$(this).val()){
			$(this).val(0);
		}
	}).css("ime-mode", "disabled");
});