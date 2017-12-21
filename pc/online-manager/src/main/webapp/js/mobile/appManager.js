var mobileBannerTable;
var mobileBannerForm;
var mobileBannerFormEdit;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
	nowTime=show();
    loadMobileBannerList();
});

//列表展示
function loadMobileBannerList(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
		{ "title": checkbox,"class":"center","width":"5%","height":"68px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
		    return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
		}},
        {title: '序号', "class": "center", "width": "5%","height":"68px","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: 'app名称', "class": "center","width": "10%","data": 'filename', "sortable": false},
        {title: '版本', "class": "center","width": "10%","data": 'version', "sortable": false},
        {title: '下载地址', "class": "center","height":"20%","data": 'downUrl', "sortable": false,"mRender":function(data,display,row){
        	return "<div style='white-space:normal;'><a href='"+data+"' target='blank'>"+data+"</a></div>";
        }},
        {title: '强制更新', "class": "center", "width": "6%","height":"68px", "data": 'mustUpdate', "sortable": false,"mRender":function(data,display,row){
        	debugger;
        	if(data == true){
        		return "是";
        	}else{
        		return "否";
        	}
        }},
        {title: '创建人', "class": "center", "width": "10%","height":"68px","data": 'createPerson', "sortable": false},
        {title: '状态', "class": "center", "width": "6%","height":"68px", "data": 'status', "sortable": false,"mRender":function(data,display,row){
        	var status ;
        	if(data == 1){
        		status = "已启用";
            }else if(data == 0){
            	status = "已禁用";
            }
        	return status;
        }},
        {title: '排序', "class": "center", "width": "8%","height":"34px","data": 'sort', "sortable": false,"mRender":function(data, display, row){
        	var str;
        	if(row.status ==1){//如果是禁用
        		str='<a class="blue" name="upa" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
            	'<a class="blue" href="javascript:void(-1);" name="downa" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
        	}else{//如果是不禁用
        		str='<a class="gray" href="javascript:void(-1);" title="上移"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
            	'<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
        	}
        	return '<div class="hidden-sm hidden-xs action-buttons">'+str;
        }},
        {title:"操作","class": "center","width":"8%","height":"34px","data":"id","sortable": false,"mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="修改" onclick="updateMobileBanner(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
	   			if(row.status==1) {
					buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this,0);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
				}else{
					buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
				}
				buttons += "</div>";
                return buttons;
            }
        }
    ];

    mobileBannerTable = initTables("mobileBannerTable", basePath + "/operate/appManager/findAppManagerList", dataFields, true, true, 2,null,searchJson,function(data){
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

    mobileBannerForm = $("#addMobileBanner-form").validate({
        messages: {
        	version: {
				required:"请输入版本号！",
            },
            downUrl: {
				required:"请上传安装包！"
			},
			describe: {
				required:"请输入更新介绍！",
			}
        }
    });

    mobileBannerFormEdit = $("#updateMobileBanner-form").validate({
        messages: {
        	messages: {
            	version: {
    				required:"请输入版本号！",
                },
    			describe: {
    				required:"请输入更新介绍！",
    			}
            }		
        }
    });

    createImageUpload($('.uploadImg'));//生成图片编辑器
}

 //条件搜索
 function search(){
     searchButton(mobileBannerTable,searchJson);
}

//新增框
 $(".add_bx").click(function(){
 	mobileBannerForm.resetForm();
 	$(".clearfixAdd").remove();

 	var dialog = openDialog("addMobileBannerDialog","dialogAddMobileBannerDiv","新增",580,500,true,"确定",function(){
 		if($("#addMobileBanner-form").valid()){
 			 mask();
 			 $("#addMobileBanner-form").attr("action", basePath+"/operate/appManager/addAppManager");
 	            $("#addMobileBanner-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#addMobileBannerDialog").dialog("close");
 	                    layer.msg(data.resultObject);
 	                    freshTable(mobileBannerTable);
 	                }else{
 	                	alertInfo(data.errorMessage);
 	               }
 	         });
 		}
 	});
});

function updateMobileBanner(obj){
	var oo = $(obj).parent().parent().parent();
	var row = mobileBannerTable.fnGetData(oo); // get datarow
	mobileBannerFormEdit.resetForm();
	$("#edit_name").val(row.version);
//	$("#edit_path_file").val(row.downUrl);
	$("#edit_path").val(row.downUrl);
	$("#update_id").val(row.id);
	$("#edit_describe").val(row.describe);
	if(row.mustUpdate){
		$("#r1").prop("checked","checked");
	}else{
		$("#r2").prop("checked","checked");
	}

//	reviewImage("update_imgPath_file",row.imgPath);
	
 	var dialog = openDialog("updateMobileBannerDialog","dialogUpdateMobileBannerDiv","修改",580,500,true,"确定",function(){
 		if($("#updateMobileBanner-form").valid()){
 			 mask();
 			 $("#updateMobileBanner-form").attr("action", basePath+"/operate/appManager/updateAppVersionInfoById");
 	            $("#updateMobileBanner-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#updateMobileBannerDialog").dialog("close");
 	                    layer.msg(data.resultObject);
 	                    freshTable(mobileBannerTable);
 	                }else{
 	                	alertInfo(data.errorMessage);
 	               }
 	         });
 		}
 	});
}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj,status){
	var oo = $(obj).parent().parent().parent();
	var row = mobileBannerTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/operate/appManager/updateStatus",{"id":row.id,"status":status},function(data){
		if(data.success){
			layer.msg(data.resultObject);
			freshTable(mobileBannerTable);
		}else{
			alertInfo(data.errorMessage);
		}
	});
};

//图片上传统一上传到附件中心---- 修改  列表页
$("#addMobileBanner-form").on("change","#imgPath_file",function(){
 	var v = this.value.split(".")[1].toUpperCase();
// 	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
// 		layer.alert("图片格式错误,请重新选择.");
// 		this.value="";
// 		return;
// 	}
 	var id = $(this).attr("id");
 	 mask();
 	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
 		unmask();
 		if (data.error == 0) {
 			debugger
 			$("#add_imgPath").val(data.url);
 			document.getElementById("imgPath_file").focus();
 			document.getElementById("imgPath_file").blur();
 			$(".remove").hide();
 		}else {
 			alert(data.message);
 		}
 	})
 });

//图片上传统一上传到附件中心---- 修改  列表页
$("#updateMobileBanner-form").on("change","#update_imgPath_file",function(){
	debugger
	alert(1);
 	var v = this.value.split(".")[1].toUpperCase();
 	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
 		layer.alert("图片格式错误,请重新选择.");
 		this.value="";
 		return;
 	}
 	var id = $(this).attr("id");
 	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
 		if (data.error == 0) {
 			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
 			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
 			
 			$("#update_imgPath").val(data.url);
 			document.getElementById("update_imgPath_file").focus();
 			document.getElementById("update_imgPath_file").blur();
 			$(".remove").hide();
 		}else {
 			alert(data.message);
 		}
 	})
 });

 //展示图片大图
 function showImg(rowId,rowDescription,rowImgPath){
	 var a=new Image()
	 a.src = rowImgPath;
	 
	 layer.open({
		  title:false,
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框 
		  area: [(a.widht+20)+"px",(a.height+12)+"px"], //宽高 926 386
		  content: '<img src="'+rowImgPath+'" onclick="layer.closeAll()"/>',
		  shadeClose:true
		});
}

 /**
  * 批量逻辑删除
  * 
  */
$(".dele_bx").click(function(){
 	deleteAll(basePath+"/operate/appManager/deletes",mobileBannerTable);
});

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = mobileBannerTable.fnGetData(oo);
	ajaxRequest(basePath+'/operate/appManager/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(mobileBannerTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = mobileBannerTable.fnGetData(oo);
	ajaxRequest(basePath+'/operate/appManager/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(mobileBannerTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};


//获取当前时间
function show(){
	   var mydate = new Date();
	   var str = "" + mydate.getFullYear() + "-";
	   str += (mydate.getMonth()+1) + "-";
	   str += mydate.getDate() ;
	   return str;
}

//字符串转成Time(dateDiff)所需方法
function stringToTime(string) {
   var f = string.split(' ', 2);
   var d = (f[0] ? f[0] : '').split('-', 3);
   var t = (f[1] ? f[1] : '').split(':', 3);
   return (new Date(
  parseInt(d[0], 10) || null,
  (parseInt(d[1], 10) || 1) - 1,
parseInt(d[2], 10) || null,
parseInt(t[0], 10) || null,
parseInt(t[1], 10) || null,
parseInt(t[2], 10) || null
)).getTime();
}

function dateDiff(date1, date2) {
    var type1 = typeof date1, type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();

    return (date2 - date1) / (1000 * 60 * 60 * 24); //结果是秒
}
