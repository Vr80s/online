var objData; 
var linkTable
var linkForm;
var searchCase = new Array();
$(function(){
	objData = [
	            { "title": createAllCheckBox(),"class":"center","data":"id","sortable":false,"width":"5%","mRender":function(data,display,row){
                    return createCheckBox(data);
                }},
                { "title": "序号", "class":"center","sortable":false,"data": 'id',"width":"7%"  },
                { "title": "链接名称", "class":"center","sortable":false,"data": 'orgname' },
		        { "title": "链接", "class":"center","sortable":false,"data": 'url' },
		        { "title": "创建时间", "class":"center","sortable":false,"data": 'createTime',"width":"15%","mRender":function (data, display, row){
		        	var d = new Date(data);
		        	var createDate = FormatDate(d);
		        	
		        	return createDate != "" ? createDate: "";
		        } },
		        { "title": "创建人", "class":"center","sortable":false,"data": 'createPerson',"width":"8%" },
		        { "title": "状态", "class":"center","sortable":false,"data": 'status',"width":"8%","mRender":function (data, display, row){
		        																								if(data == "1"){
		        																									return "已启用"
		        																								}else{
		        																									return "已禁用";
		        																								}
		        																							}
		        },
		        { "sortable": false,"class": "center","width":"13%","title":"操作","mRender":function (data, display, row) {
//		        	if(row.status ==1){//如果是禁用
//			    		return '<div class="hidden-sm hidden-xs action-buttons">'+
//			    		'<a class="blue" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
//			        	'<a class="blue" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>'+
//			        	'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
//			        	'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '+
//			        	'<a class="blue" href="javascript:void(-1);" title="删除" onclick="delDialog(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
//			    	}else{
//			    		return '<div class="hidden-sm hidden-xs action-buttons">'+
//			    		'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
//			        	'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>'+
//			        	'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
//			        	'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '+
//			        	'<a class="blue" href="javascript:void(-1);" title="删除" onclick="delDialog(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
//			    	}
		        	if(row.status == "1"){
		        		return '<div class="hidden-sm hidden-xs action-buttons">'+
		        		'<a class="blue" href="javascript:void(-1);" name="upa" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
			        	'<a class="blue" href="javascript:void(-1);" name="downa" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a>'+
						'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
						'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '+
						'<a class="blue" href="javascript:void(-1);" title="删除" onclick="delDialog(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
		        	}else{
		        		return '<div class="hidden-sm hidden-xs action-buttons">'+
		        		'<a class="gray" href="javascript:void(-1);"  title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
			        	'<a class="gray" href="javascript:void(-1);" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a>'+
						'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
						'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '+
						'<a class="blue" href="javascript:void(-1);" title="删除" onclick="delDialog(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a></div>';
		        	}
		        	
		        	
				}}
		    ];
	linkTable = initTables("linkTable","link/initLink",objData,true,true,2,null,null,function(data){
		var iDisplayStart = data._iDisplayStart;
		var countNum = data._iRecordsTotal;//总条数
		pageSize = data._iDisplayLength;//每页显示条数
		currentPage = iDisplayStart / pageSize +1;//页码
		if(currentPage == 1){//第一页的第一行隐藏向上箭头
			$("#linkTable tbody tr:eq(0) td:last a:first").css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
			$("#linkTable tbody tr:last td:last a:eq(1)").css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		var countPage;
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
//		if(countPage == currentPage){//隐藏最后一条数据下移
//			$("#linkTable tbody tr:last td:last a:eq(1)").css("pointer-events","none").removeClass("blue").addClass("gray");
//		}
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
	});//第2列作为序号列
	createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
	//图片上传
	$("#logo_img").on("change",function(){
		ajaxFileUpload("logo_img","image/attachment/upload?type=question",function(data){
			var json = eval('('+data+')');
			$("#logoImg").attr("src","image/attachment/previewImg?id="+json.id+"");
			$("#link_logo").val(json.id);
		});
	});
	
	createSelect($("#link_sort"),basePath+"/link/sortlist",null,true);
	/**
	 * 新增
	 */
	$(".add_bx").click(function(){
		$("#logoImg").attr("src","");
		$("#link_logo").val("");
		$("#add_orgname").val("");
		$("#add_url").val("");
		$("#link_id").val("");
		$("#link_sort").val("");
		$("#logo_img").val("");
		var dialog = openDialog("addLinkDialog","dialogLinkDiv","新增",400,240,true,"确定",function(){
			if($("#addlink-form").valid()){
				mask();
				var param = $("#addlink-form").serialize();
				ajaxRequest(basePath+"/link/verifySort",{"sort":$("#link_sort").val(),"id":""},function(res){
					if(res == true){
						ajaxRequest(basePath+"/link/addLink",param,function(res){
							unmask();
							if(res.success){
								dialog.dialog("close");
							}
							ajaxCallback(res);
						});
					}else{
						layer.alert("此位置信息已存在");
						unmask();
					}
				})
			}
		});
		$(".error").html("");
	});
	
});

//单条删除
function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = linkTable.fnGetData(oo);
	showDelDialog(function(){
		ajaxRequest("link/delete",{"id":aData.id},function(res){
			layer.alert(res.errorMessage);
			freshTable(linkTable);
		});
	});
}

//批量删除
function deleteBatch(){
	deleteAll("link/deleteBatch",linkTable);
}

/**
 * 查看
 */
function previewDialog(obj){
	$("#link_name").val("");
	$("#link_url").val("");
	$("#link_img").val("");
	var oo = $(obj).parent().parent().parent();
	var row = linkTable.fnGetData(oo);
	var path;
	if(row.logo==null || row.logo==""){
		path = "";
	}else if(row.logo.indexOf(".")==-1){
		path = basePath+"/image/attachment/previewImg?id="+row.logo;
	}else{
		path = row.logo;
	}
	$("#link_img").attr("src",path);
	$("#link_name").val(row.orgname);
	$("#link_url").val(row.url);
	$("#previewlinkDialog").modal('show');
}

/**
 * 修改
 * @param obj
 */
function toEdit(obj){
	$("#add_orgname").val("");
	$("#add_url").val("");
	$("#link_id").val("");
	$("#link_sort").val("");
	var oo = $(obj).parent().parent().parent();
	var row = linkTable.fnGetData(oo);
	//console.log(row);
	$("#add_orgname").val(row.orgname);
	$("#add_url").val(row.url);
	$("#link_id").val(row.id);
	$("#link_sort").val(row.sort);
	var dialog = openDialog("addLinkDialog","dialogLinkDiv","修改",400,240,true,"确定",function(){
		if($("#addlink-form").valid()){
			mask();
			var param = $("#addlink-form").serialize();
			ajaxRequest(basePath+"/link/verifySort",{"sort":$("#link_sort").val(),"id":row.id},function(res){
				if(res == true){
					ajaxRequest(basePath+"/link/update",param,function(res){
						unmask();
						if(res.success){
							dialog.dialog("close");
						}
						ajaxCallback(res);
					});
				}else{
					layer.alert("此位置信息已存在");
					unmask();
				}
			});
		}
	});
	$(".error").html("");
}

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = linkTable.fnGetData(oo);
	ajaxRequest("link/upMove",{"id":aData.id},function(res){
		freshTable(linkTable);
	});
}

/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = linkTable.fnGetData(oo);
	ajaxRequest("link/downMove",{"id":aData.id},function(res){
		freshTable(linkTable);
	});
}

//条件查询
function search(){
	var timeStart = $("#timeStart").val();
	var timeEnd = $("#timeEnd").val();
	//将之前一次存在的时间条件属性删除
	var destStr = '"propertyName":"a.create_time"';
	var length = searchCase.length;
	for(var i=0;i<length;i++){
		if(searchCase[i].indexOf(destStr)){
			searchCase.splice(i+1,2);
			break;
		}
	}
	if(timeStart != "" || timeEnd != ""){
		if(timeEnd != "" && timeStart == ""){
			layer.alert("开始时间不能为空");
			return;
		}
		if(timeStart != "" && timeEnd == ""){
			layer.alert("结束时间不能为空");
			return;
		}
		if(timeStart > timeEnd){
			layer.alert("开始时间不能大于结束时间");
			return;
		}
		
	}
	searchButton(linkTable);
}

//回车查询事件
document.onkeydown=keyListener;
function keyListener(e){
	e = e ? e : event;
	if(e.keyCode == 13){
		document.getElementById("searchBtn").click();
	}
}

/**
 * 刷新
 * @param res
 */
function ajaxCallback(res){
	if(res.success){
		freshTable(linkTable);
		layer.alert(res.errorMessage);
	}else{
		layer.alert(res.errorMessage);
	}
}

//修改状态
function updateStatus(obj){
	
	var oo = $(obj).parent().parent().parent();
	var aData = linkTable.fnGetData(oo);
	mask();
	ajaxRequest(basePath+"/link/updateStatus",{"id":aData.id},function(res){
		freshTable(linkTable);
		unmask();
	});
}


/**
 * 去掉字符串前后空格
 * @param str
 * @returns
 */
function Trim(str)
{ 
    return str.replace(/(^\s*)|(\s*$)/g, ""); 
}

/**
* 日期格式化yyyy-MM-dd HH:mm:ss
*/
function FormatDate (strTime) {
	var year = strTime.getFullYear();
	var month = getFormat(strTime.getMonth()+1);
	var day = getFormat(strTime.getDate());
	var hours = getFormatHMS(strTime.getHours());
	var minutes = getFormatHMS(strTime.getMinutes());
	var seconds = getFormatHMS(strTime.getSeconds());
    return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
}
function getFormat(time){
	if(time >= 1 && time < 9){
		time = "0"+time;
	}
	return time;
}
function getFormatHMS(time){
	if(time >= 0 && time < 9){
		time = "0"+time;
	}
	return time;
}
