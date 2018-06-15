$(function(){
	
});
function uploadFile(callback) {
	var uploadify = $("#uploadify").val();
	if (isnull(uploadify)) {
		alertInfo("请选择要上传的文件");
		return;
	}
	if (!checkuploadfile(uploadify)) {
		alertInfo("只能上传后缀名为:xls或xlsx的文件");
		return;
	}
	mask("文件正在上传,请等待");
	

	$.ajaxFileUpload({
        url:basePath+"/excel/upload", // 需要链接到服务器地址
        secureuri: false,
        fileElementId:"uploadify", // 文件选择框的id属性
        dataType: 'text', // 服务器返回的格式
        success: function(data,status){
        	unmask();
        	if(!isnull(data)){
        		if(data.indexOf("failure")>-1){
    				alertInfo(data);
    				return null;
    			}
        		
        		$("#xlsupload").dialog("close");
    			$("#importResult").html("");
    			
    			//progressbar
    			progressbar(0);
    			openLoading();//弹出进度框
    			
        		if(!isnull(callback)){
        			callback(data);//执行自己自定义的回调函数进行业务处理
        		}
        	}
        },
        error: function(data, status, e){
            alertInfo('上传失败');
        }
    });

}
function openExcel(callback,beforeback,height){
	$("#form-attachments").html("");
	$("#form-attachments").html('<input type="file" name="uploadify" id="uploadify" />');
	$('#uploadForm input[type=file]').ace_file_input({
		no_file : '没有文件 ...',
		btn_choose : '选择文件',
		btn_change : '改变文件',
		droppable : false,
		onchange : null,
		thumbnail : false
	});
	$('#uploadForm input[type=file]').ace_file_input('reset_input');
	if(isnull(height)){
		height = 240;
	}
	openDialog("xlsupload","excelShowDiv","Excel导入",600,height,true,"提交",function(){
		if(!isnull(beforeback)){
			var result = beforeback();
			if(!result){
				return null;
			}else{
				/**2016.02.20 新增导入实操题目 */
				var uploadify = $("#uploadify").val();
				if (isnull(uploadify)) {
					alertInfo("请选择要上传的文件");
					return null;
				}
				if (!checkuploadfile(uploadify)) {
					alertInfo("只能上传后缀名为:xls或xlsx的文件");
					return null;
				}
				mask("文件正在上传,请等待");
				//以上判断Excel上传文件格式
				$.ajaxFileUpload({
			        url:basePath+"/excel/uploadzip", 
			        secureuri: false,
			        fileElementId:"zipraratt", 
			        dataType: 'text', // 服务器返回的格式
			        success: function(data,status){
			        	unmask();
		        		if(data.indexOf("failure")>-1){
		    				alertInfo(data);
		    				return null;
		    			}else{
		    				uploadFile(callback);
		    			}
			        },
			        error: function(data, status, e){
			            alertInfo('上传附件文件失败');
			            return null;
			        }
			    });
				
			}
		}else{
			//没有附件直接上传Excel
			uploadFile(callback);
		}
	});
}
function openLoading(){
	openDialog("xlsuploading","excelShowDiv","文件导入中",600,180,false);
	$(".ui-dialog-titlebar button.ui-dialog-titlebar-close").hide();
}
function openError(data){
	openDialog("errorDiv","excelShowDiv","错误信息",600,340,false);
	$("#errorHtml").html(data);
	$(".ui-dialog-titlebar button.ui-dialog-titlebar-close").show();
}
function checkuploadfile(filepath) {
	if (isnull(filepath)) {
		return;
	}
	filepath = filepath.substring(filepath.lastIndexOf(".") + 1,filepath.length);
	filepath = filepath.toLowerCase();
	if (filepath == "xls" || filepath == "xlsx") {
		return true;
	} else {
		return false;
	}
}
function setNumForImport(isTotalNumber, rownum) {
	if (isTotalNumber) {
		$("#importResult").html("共: " + rownum + "行,开始处理第: 1 行...");
		$("#totalCount").val(rownum);
	} else {
		var num = parseInt(parseFloat(rownum / parseInt($("#totalCount").val())) * 100);
		progressbar(num);
		$("#importResult").html(
				"<span>正在检查第" + rownum + "条,总计" + $("#totalCount").val()+ "条记录</span>");
	}
}
function beachNumber(rownum) {
	$("#importResult").html("<span>正在执行批量保存！</span>");
	if (rownum == 0) {
		progressbar(0);
		return;
	}
	var num = parseInt(parseFloat(rownum / parseInt($("#totalCount").val())) * 100);
	progressbar(num);
}

function progressbar(num){
	$( "#progressbar" ).progressbar({
		value: num,
		create: function( event, ui ) {
			$(this).addClass('progress progress-striped active')
				   .children(0).addClass('progress-bar progress-bar-success');
		}
	});
}


