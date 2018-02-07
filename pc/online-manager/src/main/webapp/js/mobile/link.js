var mobileBannerTable;//职业课列表

var mobileBannerForm;
var mobileBannerFormEdit;
var _courseRecTable;//课程推荐列表


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