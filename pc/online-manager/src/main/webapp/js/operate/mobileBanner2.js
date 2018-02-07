var mobileBannerTable;//职业课列表

var mobileBannerForm;
var mobileBannerFormEdit;


var _courseRecTable;//课程推荐列表

$(function(){

    $("#bannerType").val(1);
    $("#upload_bannerType").val(1);
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	//debugger;
	/** 职业课列表begin */
    var searchCase_P = new Array();
    
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [
	       		{ "title": checkbox,"class":"center","width":"5%","height":"68px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
	    		    return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
	    		}},
	            {title: '序号', "class": "center", "width": "5%","height":"68px","data": 'id',datafield: 'xuhao', "sortable": false},
	            {title: 'banner名称', "class": "center","height":"68px","data": 'name', "sortable": false},
	          
	            {title: '连接类型', "class": "center","height":"68px","data": 'linkType', "sortable": false,"mRender":function(data,display,row){
	            	var linkType ;
	            	/* 连接类型：1：活动页、2：专题页、3：课程:4：主播:5：课程列表（带筛选条件） -->*/
	            	//alert(data);
	            	if(data == 1){
	            		linkType = "活动页";
	                }else if(data == 2){
	                	linkType = "专题页";
	                }else if(data == 3){
	                	linkType = "课程";
	                }else if(data == 4){
	                	linkType = "主播";
	                }else if(data == 5){
	                	linkType = "课程列表";
	                }
	            	return linkType;
	            }},
	            
	            {title: '缩略图', "class": "center", "width": "144px","height":"38px","data": 'imgPath', "sortable": false,"mRender":function(data,display,row){
	            	return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;' onclick='showImg(\""+row.id+"\",\""+row.description+"\",\""+row.imgPath+"\")'/>";
	            }},
	            {title: '链接地址', "class": "center","height":"68px","data": 'url', "sortable": false,"mRender":function(data,display,row){
	            	return "<div style='white-space:normal;'><a href='"+data+"' target='blank'>"+data+"</a></div>";
	            }},
	            {title: '点击量', "class": "center", "width": "6%","height":"68px","data": 'clickSum', "sortable": false},
	            {title: '创建人', "class": "center", "width": "6%","height":"68px","data": 'createPersonName', "sortable": false},
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
		
    searchCase_P.push('{"tempMatchType":"1","propertyName":"banner_type","propertyValue1":"1","tempType":Integer}');	
		
	mobileBannerTable = initTables("courseTable",basePath+"/operate/mobileBanner/findMobileBannerList",objData,true,true,0,null,searchCase_P,function(data){
	
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
	/** 职业课列表end */
	/**
	 * 展示职业课管理
	 * 
	 */
	$(".zykgl_bx").click(function(){
		$("#courseDiv").show();
		$("#courseDiv_M").hide();
		$("#courseDiv_PX").hide();
		$("#courseRecDiv").hide();
		
		var banneType =  $(this).attr("title");
		
		debugger;
	
		$("#bannerType").val(banneType);
		
	    var json = new Array();
	    json.push('{"tempMatchType":"9","propertyName":"banner_type","propertyValue1":"'+banneType+'","tempType":"Integer"}');
		searchButton(mobileBannerTable,json);
	});
	
	
	/*$("#add-originalCost").hide();
	$("#add-currentPrice").hide();*/
	//createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");

	
	createImageUpload($('.uploadImg'));//生成图片编辑器


	  mobileBannerForm = $("#addMobileBanner-form").validate({
	        messages: {
	        	name: {
					required:"请输入banner名称！",
	            },
	            imgPath: {
					required:"请选择图片！"
				},
	            url: {
					required:"请输入图片链接！",
					maxlength:"链接地址最长为120个字符！"
				},
				linkType: {
					required:"请选择连接类型！"
				}
	        }
	    });

	    mobileBannerFormEdit = $("#updateMobileBanner-form").validate({
	        messages: {
	        	name: {
					required:"请输入banner名称！",
	            },
	            imgPath: {
					required:"请选择图片！"
				},
				url: {
					required:"请输入图片链接！",
					maxlength:"链接地址最长为120个字符！"
				},
				linkType: {
					required:"请选择连接类型！"
				}			
	        }
	    });
	
});



/**
 * 点击上传弹出框
 */
$(".upload_bx").click(function(){
 	var dialog = openDialog("addwordDialog","dialogAddWordDiv","新增",580,500,true,"确定",function(){
 		 $("#addwordDialog").dialog("close");
 	});
});

/**
 * 上传word
 */
$("#addword-form").on("change","#imgPath_file",function(){
 	var id = $(this).attr("id");
	var v = this.value.split(".")[1].toUpperCase();
	if(v!='DOCX'){
		layer.alert("文件格式错误,请重新选择.docx 结尾的文档");
		this.value="";
		return;
	}
 	debugger
    mask();
 	ajaxFileUpload(this.id,basePath+"/link/word/upload", function(data){
 		unmask();
 		//alert(data.success);
 		if (data.error == 0) {
 			debugger
 			alert("上传成功");
 		}else {
 			alert(data.message);
 		}
 	})
 });

/**
 * 点击上传弹出框
 */
$(".upload_excel").click(function(){
	$("#excel_file").val("");
 	var dialog = openDialog("addExcelDialog","dialogAddExcelDiv","新增",580,500,true,"确定",function(){
 		 $("#addExcelDialog").dialog("close");
 	});
});

/**
 * 上传excel
 */
$("#addExcel-form").on("change","#excel_file",function(){
 	var id = $(this).attr("id");
	var v = this.value.split(".")[1].toUpperCase();
//	if(v!=''){
//		layer.alert("文件格式错误,请重新选择.docx 结尾的文档");
//		this.value="";
//		return;
//	}
 	debugger
    mask();
 	ajaxFileUpload(this.id,basePath+"/link/word/importExcel", function(data){
 		unmask();
 		//alert(data.success);
 		if (data.error == 0) {
 			debugger
 			layer.alert(data.excel_error);
 		}else {
 			alert(data.message);
 		}
 	})
 });



//条件搜索
function search(){
    searchButton(mobileBannerTable,searchJson);
}

//新增框
$(".add_bx").click(function(){
	mobileBannerForm.resetForm();
	//$(".remove").trigger("click");
	$(".clearfixAdd").remove();
	$("#addDiv").prepend("<div class=\"clearfixAdd\">\n" +
						  "	<input type=\"file\" name=\"imgPath_file\" id=\"imgPath_file\" class=\"uploadImg\"/>\n" +
						  "</div>");
	createImageUpload($('#imgPath_file'));//生成图片编辑器

	var dialog = openDialog("addMobileBannerDialog","dialogAddMobileBannerDiv","新增",580,500,true,"确定",function(){
		if($("#addMobileBanner-form").valid()){
			 mask();
			 $("#addMobileBanner-form").attr("action", basePath+"/operate/mobileBanner/addMobileBanner");
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
	$("#update_name").val(row.name);
	$("#update_url").val(row.url);
	$("#update_imgPath").val(row.imgPath);
	$("#update_id").val(row.id);

	var linkType = row.linkType;
	/**
	 *  跳转
	 */
	for(i=0;i<$("#update_linkType option").length;i++){
		if($("#update_linkType option").eq(i).val()==linkType){
			//$("#show_userLecturerId").text($("#view_mapList option").eq(i).text());
		
			$("#update_linkType option").eq(i).attr("select","selected"); 
			$("#update_linkType").val(linkType);
		}
    }
	reviewImage("update_imgPath_file",row.imgPath);
	
	var dialog = openDialog("updateMobileBannerDialog","dialogUpdateMobileBannerDiv","修改",580,500,true,"确定",function(){
		if($("#updateMobileBanner-form").valid()){
			 mask();
			 $("#updateMobileBanner-form").attr("action", basePath+"/operate/mobileBanner/updateMobileBannerById");
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
	ajaxRequest(basePath+"/operate/mobileBanner/updateStatus",{"id":row.id,"status":status},function(data){
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
	deleteAll(basePath+"/operate/mobileBanner/deletes",mobileBannerTable);
});

/**
* 上移
* @param obj
*/
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = mobileBannerTable.fnGetData(oo);
	ajaxRequest(basePath+'/operate/mobileBanner/upMove',{"id":aData.id},function(res){
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
	ajaxRequest(basePath+'/operate/mobileBanner/downMove',{"id":aData.id},function(res){
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








