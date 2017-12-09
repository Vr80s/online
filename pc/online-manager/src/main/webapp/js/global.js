$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
	_title: function(title) {
		var $title = this.options.title || '&nbsp;';
		if( ("title_html" in this.options) && this.options.title_html == true )
			title.html($title);
		else title.text($title);
	}
}));
$(function() {
	
	document.onkeydown=function(event){
		 var e = event || window.event || arguments.callee.caller.arguments[0];
		 if(e && e.keyCode==13){// enter 键
			 var display = $(".ui-dialog").css("display");
			 if(display != "block"){
				 var button = $("#searchDiv").find("button");
				 if(button.length>0){
					 button.focus();
					 button.click();
				 }
			 }
		 }else if(e && e.keyCode==32){//空格事件
			 e.preventDefault();//阻止它默认行为的发生而发生其他的事件
		 }
	 };
	$.extend($.fn, {
//		mask : function() {
//			$(this).append('<div class="message-loading-overlay" style="z-index:10000"><i class="fa-spin ace-icon fa fa-spinner orange2 bigger-160"></i><span>ddd</span></div>');
//		},
//		unmask : function() {
//			$(this).find('.message-loading-overlay').remove();
//		}
	});
	
	jQuery.validator.addMethod("phone", function (value, element) {
		return this.optional(element) || (/^1[3|5|7|8][0-9]\d{4,8}$/.test(value) || /^1[4][2|7]\d{4,8}$/.test(value)) && value.length==11;
	}, "手机格式不正确");
	
	jQuery.validator.addMethod("qq", function (value, element) {
		return this.optional(element) || (/^[1-9]\d{4,19}$/.test(value));
	}, "QQ号码格式不正确");

	jQuery.validator.addMethod("cardNo", function (value, element) {
		return this.optional(element) || isCardID(value);
	}, "身份证格式不正确");
	jQuery.validator.addMethod("email", function (value, element) {
		return this.optional(element) || (/^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(value));
	}, "邮箱格式不正确");
	jQuery.validator.addMethod("CNEN", function (value, element) {
		return this.optional(element) || (/^[\u4e00-\u9fa5a-zA-Z]{0,}$/.test(value));
	}, "只能输入中英文");
	jQuery.validator.addMethod("CNENNO", function (value, element) {
		return this.optional(element) || (/^[\u4e00-\u9fa5a-zA-Z0-9]{0,}$/.test(value));
	}, "只能输入中英文与数字");
	jQuery.validator.addMethod("CNENNO2", function (value, element) {
		return this.optional(element) || (/^[\u4e00-\u9fa5a-zA-Z0-9_]{0,}$/.test(value));
	}, "只能输入中英文与数字和_");
	jQuery.validator.addMethod("ENNUMUNDERLINE", function (value, element) {
		return this.optional(element) || (/^[_0-9a-zA-Z]{6,16}$/.test(value));
	}, "请输入6-16位以字母、数字或下划线组成的密码");
	jQuery.validator.addMethod("ENNUM", function (value, element) {
		return this.optional(element) || (/^[0-9a-zA-Z]{0,}$/.test(value));
	}, "只能输入数字和字母");
	
	Date.prototype.format = function(format){
		 var o = {
			 "M+" : this.getMonth()+1, //month
			 "d+" : this.getDate(),    //day
			 "h+" : this.getHours(),   //hour
			 "m+" : this.getMinutes(), //minute
			 "s+" : this.getSeconds(), //second
			 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
			 "S" : this.getMilliseconds() //millisecond
		 };
		 if(/(y+)/.test(format)) 
			 format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
		 for(var k in o)
			 if(new RegExp("("+ k +")").test(format))
				 format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
		 return format;
	}
	
	Function.prototype.method = function(name, func) {
		this.prototype[name] = func; 
		return this;
	};
	if (!String.prototype.trim) { //判断下浏览器是否自带有trim()方法  
		String.method('trim', function() {
			return this.replace(/^\s+|\s+$/g, '');
		});
		String.method('ltrim', function() {
			return this.replace(/^\s+/g, '');
		});
		String.method('rtrim', function() {
			return this.replace(/\s+$/g, '');
		});
	}

});
function errorPositon(){
	$(".ui-dialog label").each(function(){
		if($(this).hasClass("error")){
			var heights = $(this).parent().height();
			$(this).css({"position":"absolute","left":"15px","top":heights});
		}
	})
};
setInterval("errorPositon()","1");
/**
 * 判断td宽度是否超过 超过添加title
 */
$("#pageContentArea").on("mouseover"," .table td",function(){
	if($(this).get(0).scrollWidth>$(this).get(0).clientWidth){
		var text = $(this).text();
		$(this).attr("title",text);
	}
});

/**
 * 异步加载
 * @param url
 * @param data
 * @param callback
 */
function ajaxRequest(url,data,callback){
	$.ajax({
		url:url,
		data:data,
		type:'POST',
		success:function(datas){
			if(sessionValidate(datas)){
				callback(datas);
			}
		},
		error:function(response){
			unmask();
			try{
				var res = eval("(" + response.responseText + ")");
				console.info(res.errorMessage);
			}catch(e){
				console.info(response.responseText);
			}
		}
	});
}

/**
 * 同步加载
 * @param url
 * @param data
 * @param callback
 */
function syncRequest(url,data,callback){
	$.ajax({
		url:url,
		data:data,
		type:'POST',
		async:false,
		success:function(datas){
			if(sessionValidate(datas)){
				callback(datas);
			}
		}
	});
}

/**
 * js异步操作时的session失效判断
 * @param datas
 */
function sessionValidate(datas){
	if(Object.prototype.toString.call(datas) === "[object String]"){
		if(datas.indexOf("<!DOCTYPE>")>-1 || datas.indexOf("<!doctype html>")>-1){//session失效自动刷新页面
			window.location.reload();
			return false;
		}
	}
	
	return true;
}

/**
 * 
 * @param select
 * @param url
 * @param data
 * @param isShowHead  是否显示"请选择"
 * @param value  是否选中
 * @param callback
 */
function createSelect(select, url, data, isShowHead, value, callback) {
	ajaxRequest(url,data,function(result){
		select.empty();
		if (isShowHead) {
			$("<option value=''>请选择</option>").appendTo(select);
		}
		if (result.length > 0) {
			for (var i = 0; i < result.length; i++) {
				$("<option value='" + result[i].id + "'>"
						+ result[i].name + "</option>").appendTo(select);
			}
			if(!isnull(value)){
				select.attr("value", value);// 赋值让其呈选中状态
			}
		}
		if (callback != null && callback != undefined) {
			callback();
		}
	});
}
function createSelectName(select, url, data, isShowHead, value, callback) {
	ajaxRequest(url,data,function(result){
		select.empty();
		if (isShowHead) {
			$("<option value=''>请选择</option>").appendTo(select);
		}
		if (result.length > 0) {
			for (var i = 0; i < result.length; i++) {
				$("<option value='" + result[i].name + "'>"
						+ result[i].name + "</option>").appendTo(select);
			}
			if(!isnull(value)){
				select.attr("value", value);// 赋值让其呈选中状态
			}
		}
		if (callback != null && callback != undefined) {
			callback();
		}
	});
}

function isnull(data) {
	if (data == null || data == undefined || data == "") {
		return true;
	} else {
		if(typeof data == "string" && data.trim()==""){
			return true;
		}
		return false;
	}
}

function replaceBlank(content) {
	var content2 = content.replace(/<br>+/g,"")
						  .replace(/<div>+|<\/div>+/g,"")
						  .replace(/<p>+|<P>+/g,"")
						  .replace(/<\/p>+|<\/P>+/g,"")
						  .replace(/&nbsp;+/g,"");
	if(isnull(content2)){
		content = "";
	}
	if(content==""){
		return "";
	} else {
		return content2;
	}
}

/**
 * 
 * @param divId
 * @param appendDiv  弹框显示的div位置
 * @param title      弹框标题
 * @param width      弹框宽度
 * @param height     弹框高度
 * @param isShowBtn  是否显示弹框上的按钮
 * @param btnName    自定义按钮名字
 * @param method     自定义按钮事件
 * @returns
 */
function openDialog(divId,appendDiv,title,width,height,isShowBtn,btnName,method){
	var dialog;
	if(isShowBtn){//显示按钮
    	$("html").css({'overflow-x':'hidden','overflow-y':'hidden'});
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			closeText:"关闭",
			modal: true,
			width: width,
			height: height,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true,
			buttons: [
				{
					text: btnName,
					"class" : "btn btn-primary btn-xs",
					click: function() {
						if(!isnull(method)){
							method();
						}
						$("html").css({'overflow-x':'hidden','overflow-y':'auto'});
					} 
				},
				{
					text: "取消",
					"class" : "btn btn-xs",
					click: function() {
						$( this ).dialog( "close" ); 
	                	$("html").css({'overflow-x':'hidden','overflow-y':'auto'});
					} 
				}
			]
		});
	}else{
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			closeText:"关闭",
			modal: true,
			width: width,
			height: height,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true
		});
	}
	//divId,appendDiv  addCourseDialog","dialogAddCourseDiv  EditCourseDialog","dialogEditCourseDiv
//	<div id="dialogAddCourseDiv"></div>
//	<div id="addCourseDialog" class="hide">
	if(divId == "addCourseDialog" && appendDiv == "dialogAddCourseDiv"){
		$( "#combobox" ).combobox();
		/*$( "#toggle" ).click(function() {
		   $( "#combobox" ).toggle();
		});*/
	}
	
	if(divId == "EditCourseDialog" && appendDiv == "dialogEditCourseDiv"){
		$( "#combobox1" ).combobox();
		//杨宣修改--增加模糊查询讲师功能
//		for(i=0;i<$("#combobox1 option").length;i++){
//		    console.log($("#combobox1 option").eq(i).val()+"==="+row.userLecturerId+"==="+$("#combobox1 option").eq(i).text());
//			if($("#combobox1 option").eq(i).val()==row.userLecturerId){
//				$("#combobox1 option").eq(i).attr("select","selected"); 
//				$("#combobox1").val($("#combobox1 option").eq(i).text());
//				$("#nihao").val($("#combobox1 option").eq(i).text());
//			}
//	    }
		//$("#combobox1").val($("#combobox1 option").eq(i).val());
		//$("#nihao").val($("#combobox1 option").eq(i).text());
	/*	$( "#toggle" ).click(function() {
		   $( "#combobox1" ).toggle();
		});*/
	}
	moveDiv();
	return dialog;
}

/**
 * 
 * @param divId
 * @param appendDiv  弹框显示的div位置
 * @param title      弹框标题
 * @param width      弹框宽度
 * @param height     弹框高度
 * @param isShowBtn  是否显示弹框上的按钮
 * @param btnName    自定义按钮名字
 * @returns
 */
function openDialogNoBtnName(divId,appendDiv,title,width,height,isShowBtn,btnName){
	var dialog;
	if(isShowBtn){//显示按钮
    	$("html").css({'overflow-x':'hidden','overflow-y':'hidden'});
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			closeText:"关闭",
			modal: true,
			width: width,
			height: height,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true,
			buttons: [
				{
					text: btnName,
					"class" : "btn btn-primary btn-xs",
					click: function() {
						$( this ).dialog( "close" );
	                	$("html").css({'overflow-x':'hidden','overflow-y':'auto'});
					} 
				}
			]
		});
	}else{
    	$("html").css({'overflow-x':'hidden','overflow-y':'hidden'});
		dialog = $("#"+divId).removeClass('hide').dialog({
			appendTo: "#"+appendDiv,
			closeOnEscape:false,
			closeText:"关闭",
			modal: true,
			width: width,
			height: height,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
			title_html: true
		});
	}
	$(".ui-dialog").unbind();
	moveDiv();
	return dialog;
}

/**
 * 删除之前的确认框
 * @param callback
 * @param title  标题名字  有默认值
 * @param content  内容  有默认值
 * @param btnName  按钮名称  有默认值
 */
function showDelDialog(callback,title,content,btnName){
	
	if(isnull(title)){
		title = "信息";
	}
	if(!isnull(content)){
		$("#confirm_content").html(content);
	}else{
		$("#confirm_content").html("确定要删除该条数据吗?");
	}
	if(isnull(btnName)){
		btnName = "确认";
	}
	$( "#dialog-confirm" ).removeClass('hide').dialog({
		resizable: false,
		modal: true,
		title: "<div class='widget-header'><h4 class='smaller'> "+title+"</h4></div>",
		title_html: true,
		buttons: [
			/*{
				html: "取消",
				"class" : "btn btn-xs no_line",
				click: function() {
					$( this ).dialog( "close" );
				}
			},   */
			{
				html: btnName,
				"class" : "btn btn-info btn-xs ui-state-focus out-btn",
				click: function() {
					$( this ).dialog( "close" );
					if(!isnull(callback)){
						callback();
					}
				}
			}
			
		]
	});
	$("div").focus();
	
	/*if(isnull(title)){
		title = "删除数据?";
	}
	if(!isnull(content)){
		$("#confirm_content").html(content);
	}else{
		$("#confirm_content").html("确定要删除该条数据吗?");
	}
	if(isnull(btnName)){
		btnName = "删除";
	}
	$( "#dialog-confirm" ).removeClass('hide').dialog({
		resizable: false,
		modal: true,
		title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> "+title+"</h4></div>",
		title_html: true,
		buttons: [
			{
				html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; "+btnName,
				"class" : "btn btn-danger btn-xs",
				click: function() {
					$( this ).dialog( "close" );
					if(!isnull(callback)){
						callback();
					}
				}
			},
			{
				html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});*/
}

/**
 * 异步文件上传
 * @param fileId  input的ID
 * @param url     请求的后台地址
 * @param callback  回调方法
 */
function ajaxFileUpload(fileId,url,callback){
	$.ajaxFileUpload({
        url:url, // 需要链接到服务器地址
        secureuri: false,
        fileElementId:fileId, // 文件选择框的id属性
        dataType: 'text', // 服务器返回的格式
        success: function(data,status){
        	data=strToJson(data);
        	if(data.error==0){
        		if(!isnull(callback)){
        			callback(data);
        		}
        	}else{
        		unmask();
        		alertInfo(data.message);
        	}
//        	if(data.indexOf("failure")>-1){
//				alertInfo(data);
//			}else if(!isnull(callback)){
//        		callback(data);
//			}
        }
	});
}

/**
 * 将字符串去掉<pre>标签并转为对象
 * @param data
 * @returns
 */
function strToJson(data){
	var str =data.replace(/<\/?pre[^>]*>/gi,""); 
	var o=eval('(' + str + ')');
	return o;
}

/**
 * 查询table
 * @param dataTable
 * @param otherCase  其他的附加参数
 */
function searchButton(dataTable,otherCase,searchDiv) {
	var searchs = getSearchGroup(searchDiv);
	
//	searchs = encodeURI(searchs);
	
	var str = "";
	if(!isnull(otherCase)){
		for(var i=0;i<otherCase.length;i++){
			searchs.push(otherCase[i]);
		}
	}
	str = "[" + searchs.join(",") + "]";
	
	dataTable.fnFilter(str);
}
//得到查询框的值Div
function getSearchGroup(searchDiv) {
	var json = new Array();
	$((searchDiv || '#searchDiv') + " .searchTr").each(function () {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				json.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});
	
	return json;
}

/**
 * 
 * @param tabId  dataTable的ID
 * @param url    请求的url路径
 * @param objData  数据对象
 * @param bFilter  是否支持搜索方法
 * @param bPaginate  是否要分页显示
 * @param showNum  显示序号  默认为null  1：表示第1列作为序号列 2：表示第2列作为序号列
 * @param callback  回调方法
 * @param searchCase  额外传递的参数  数组  格式：[{"tempMatchType":"5","propertyName":loginName,"propertyValue1":"jth","tempType":"String"}]
 * @param iDisplayLength 每页显示数量
 *  
 * @returns  返回dataTable对象
 */
function initTables(tabId,url,objData,bFilter,bPaginate,showNum,callback,searchCase,iDisplayLength,iPageNumber){
	var searchs = "";
	if(!isnull(searchCase) && searchCase.length>0){
		searchs = "[" + searchCase.join(",") + "]";
	}
	if(isnull(showNum)){
		showNum = -1;
	}else{
		showNum = showNum - 1;
	}
	iDisplayLength = 10;
	var aa = getCookie("dataTablePageSize");
	if(!isnull(aa)){
		iDisplayLength = aa;
	}
	var currentPage=0,pageSize=0;
	var tables = $('#'+tabId).dataTable( {
		"columns": objData,
		"columnDefs": [{
			"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
				if(showNum != -1){
					$(nTd).text((iRow + 1)+(currentPage-1)*pageSize);
				}
			},
			"targets": showNum
		}],
		"bAutoWidth": true,
		"bDeferRender":true,
		"bLengthChange": true, //开关，是否显示每页大小的下拉框
		"bPaginate": false, //开关，是否显示分页器
		"bPaginate": bPaginate,  //是否分页
		"iDisplayLength":iDisplayLength, //每页显示10条记录  默认
		"bInfo": true, //开关，是否显示表格的一些信息
		"bFilter": bFilter, //开关，是否启用客户端过滤器
		"bJQueryUI": true, //开关，是否启用JQueryUI风格
		"bProcessing": true, //当datatable获取数据时候是否显示正在处理提示信息
		"aLengthMenu": [[10, 15, 20, 30], [10, 15, 20, 30]],
		"bServerSide": true,
		"sPaginationType": 'full_numbers',//分页样式 
		"sAjaxSource": url,
		"sAjaxDataProp":"aaData",
		"sServerMethod":"POST",
		"oSearch": { "sSearch": searchs},
		"oLanguage": {
			"sProcessing": "正在加载中......",
            "sSearch": "搜索:",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "没有记录",
            "sInfo": "共 _TOTAL_条记录",
            "sInfoEmpty": "共0条记录",
            "oPaginate": {
                "sPrevious": " 上一页 ",
                "sNext":     " 下一页 ",
                "sFirst": "首页",
                "sLast": "尾页"
            }
		},
		"sDom":'<"row"<"col-xs-6"r><"col-xs-6"f>>t<"row"<"col-xs-3"l><"col-xs-2"i><"col-xs-4"p>>',
		"preDrawCallback": function( settings ) {
			
			pageSize = settings._iDisplayLength;
			
			var iDisplayStart = settings._iDisplayStart;
			
			currentPage = iDisplayStart / pageSize +1;
		},
		"drawCallback": function( settings ) {
			var displayLength = settings._iDisplayLength;
			if(displayLength!=iDisplayLength){
				setCookie("dataTablePageSize", displayLength, 1);
			}
			$("#"+settings.sInstance+" input[type='checkbox']").prop("checked", false);
			$("#"+settings.sInstance+" tbody input[type='checkbox']").change(function(){
				if(!$(this).prop("checked")){
					$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", false);
				}else{
					var len = $("#"+settings.sInstance+" tbody input[type='checkbox']").not("input:checked").length;
					if(len==0){
						$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", true);
					}
				}
			});
			setTimeout(function(){
				$('#'+tabId).removeAttr("style");
			},100);
		},
		"initComplete": function(settings, json) {
			if(!isnull(callback)){
				callback();
			}
		}
    });
	return tables;
}




/**
 *
 * @param tabId  dataTable的ID
 * @param url    请求的url路径
 * @param objData  数据对象
 * @param bFilter  是否支持搜索方法
 * @param bPaginate  是否要分页显示
 * @param showNum  显示序号  默认为null  1：表示第1列作为序号列 2：表示第2列作为序号列
 * @param callback  回调方法
 * @param searchCase  额外传递的参数  数组  格式：[{"tempMatchType":"5","propertyName":loginName,"propertyValue1":"jth","tempType":"String"}]
 * @returns  返回dataTable对象
 */
function initTables(tabId,url,objData,bFilter,bPaginate,showNum,initComplete,searchCase,callback,iDisplayLength){
	var searchs = "";
	if(!isnull(searchCase) && searchCase.length>0){
		searchs = "[" + searchCase.join(",") + "]";
	}
	if(isnull(showNum)){
		showNum = -1;
	}else{
		showNum = showNum - 1;
	}
	if(isnull(iDisplayLength)){
		iDisplayLength = 10
	}
	var currentPage=0,pageSize=0;
	var tables = $('#'+tabId).dataTable( {
		"columns": objData,
		"columnDefs": [{
			"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
				if(showNum != -1){
					$(nTd).text((iRow + 1)+(currentPage-1)*pageSize);
				}
			},
			"targets": showNum
		}],
		"bAutoWidth": true,
		"bDeferRender":true,
		"bLengthChange": true, //开关，是否显示每页大小的下拉框
		"bPaginate": false, //开关，是否显示分页器
		"bPaginate": bPaginate,  //是否分页
		"iDisplayLength":iDisplayLength, //每页显示10条记录  默认
		"bInfo": true, //开关，是否显示表格的一些信息
		"bFilter": bFilter, //开关，是否启用客户端过滤器
		"bJQueryUI": true, //开关，是否启用JQueryUI风格
		"bProcessing": true, //当datatable获取数据时候是否显示正在处理提示信息
		"aLengthMenu": [[10, 15, 30], [10, 15, 30]],
		"bServerSide": true,
		"stateSave": false,
		"sPaginationType": 'full_numbers',//分页样式
		"sAjaxSource": url,
		"sAjaxDataProp":"aaData",
		"sServerMethod":"POST",
		"oSearch": { "sSearch": searchs},
		"oLanguage": {
			"sProcessing": "正在加载中......",
			"sSearch": "搜索:",
			"sLengthMenu": "每页显示 _MENU_ 条记录",
			"sZeroRecords": "没有记录",
			"sInfo": "共 _TOTAL_条记录",
			"sInfoEmpty": "共0条记录",
			"oPaginate": {
				"sPrevious": " 上一页 ",
				"sNext":     " 下一页 ",
				"sFirst": "首页",
				"sLast": "尾页"
			}
		},
		"sDom":'<"row"<"col-xs-6"r><"col-xs-6"f>>t<"row"<"col-xs-3"l><"col-xs-2"i><"col-xs-4"p>>',
		"preDrawCallback": function( settings ) {
			var iDisplayStart = settings._iDisplayStart;
			pageSize = settings._iDisplayLength;
			currentPage = iDisplayStart / pageSize +1;
		},
		"drawCallback": function( settings ) {
			if(!isnull(callback)){
				callback(settings);
			}
			$("#"+settings.sInstance+" input[type='checkbox']").prop("checked", false);
			$("#"+settings.sInstance+" tbody input[type='checkbox']").change(function(){
				if(!$(this).prop("checked")){
					$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", false);
				}else{
					var len = $("#"+settings.sInstance+" tbody input[type='checkbox']").not("input:checked").length;
					if(len==0){
						$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", true);
					}
				}
			});
			
			
			$("#"+tabId + " th").eq("0").css("cursor","default");
			
			if($("#"+tabId + " tbody tr").eq("0").find("[type='checkbox']").length>0){
				$("#"+tabId + " tbody tr").css("cursor","pointer");
//				$("#"+tabId + " tr").eq("0").css("cursor","default");
				
				$("#"+tabId + " thead tr").on('click',function(e){
					
					return true;//chan pin shuo bu yaole  qu diao
					if($(e.target).get(0).tagName=='I'){//如果点击的是I标签就退回
						return false;
					}
					if($(e.target).get(0).tagName=='INPUT'){//如果点击的是INPUT标签就退回
						return true;
					}
					
					//点击行 选中复选框
					if($(this).find(".ace").prop("checked")){
						$(this).find(".ace").prop("checked",false);
						chooseAll($(this).find(".ace")[0])
					}else{
						$(this).find(".ace").prop("checked",true);
						chooseAll($(this).find(".ace")[0])
					}
				});
				
				$("#"+tabId + " tbody tr").on('click',function(e){
//					alert($(e.target).get(0).tagName);
					if($(e.target).get(0).tagName=='I'||$(e.target).get(0).tagName=='SPAN'){//如果点击的是I标签就退回
						return true;
					}
					if($(e.target).get(0).tagName=='INPUT'){//如果点击的是INPUT标签就退回
						return true;
					}
					
					//点击行 选中复选框
					if($(this).find(".ace").prop("checked")){
						$(this).find(".ace").prop("checked",false);
						$("#"+tabId+" .ace").eq(0).prop("checked",false);
					}else{
						$(this).find(".ace").prop("checked",true);
						if($("#"+tabId+" .ace").not("input:checked").length==1){
							$("#"+tabId+" .ace").eq(0).prop("checked",true);
						}
					}
				});
			}
			
			setTimeout(function(){
				if(tabId != 'commentTable'){
					$('#'+tabId).removeAttr("style");
				}
			},100);

		},
		"initComplete": function(settings, json) {
			if(!isnull(initComplete)){
				initComplete();
			}
		}
	});
	return tables;
}
/**
 * 去除行点击选中事件
 * @param tabId
 * @param url
 * @param objData
 * @param bFilter
 * @param bPaginate
 * @param showNum
 * @param initComplete
 * @param searchCase
 * @param callback
 * @param iDisplayLength
 * @returns {*|jQuery}
 */
function initTables2(tabId,url,objData,bFilter,bPaginate,showNum,initComplete,searchCase,callback,iDisplayLength){
	var searchs = "";
	if(!isnull(searchCase) && searchCase.length>0){
		searchs = "[" + searchCase.join(",") + "]";
	}
	if(isnull(showNum)){
		showNum = -1;
	}else{
		showNum = showNum - 1;
	}
	if(isnull(iDisplayLength)){
		iDisplayLength = 10
	}
	var currentPage=0,pageSize=0;
	var tables = $('#'+tabId).dataTable( {
		"columns": objData,
		"columnDefs": [{
			"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
				if(showNum != -1){
					$(nTd).text((iRow + 1)+(currentPage-1)*pageSize);
				}
			},
			"targets": showNum
		}],
		"bAutoWidth": true,
		"bDeferRender":true,
		"bLengthChange": true, //开关，是否显示每页大小的下拉框
		"bPaginate": false, //开关，是否显示分页器
		"bPaginate": bPaginate,  //是否分页
		"iDisplayLength":iDisplayLength, //每页显示10条记录  默认
		"bInfo": true, //开关，是否显示表格的一些信息
		"bFilter": bFilter, //开关，是否启用客户端过滤器
		"bJQueryUI": true, //开关，是否启用JQueryUI风格
		"bProcessing": true, //当datatable获取数据时候是否显示正在处理提示信息
		"aLengthMenu": [[10, 15, 30], [10, 15, 30]],
		"bServerSide": true,
		"stateSave": false,
		"sPaginationType": 'full_numbers',//分页样式
		"sAjaxSource": url,
		"sAjaxDataProp":"aaData",
		"sServerMethod":"POST",
		"oSearch": { "sSearch": searchs},
		"oLanguage": {
			"sProcessing": "正在加载中......",
			"sSearch": "搜索:",
			"sLengthMenu": "每页显示 _MENU_ 条记录",
			"sZeroRecords": "没有记录",
			"sInfo": "共 _TOTAL_条记录",
			"sInfoEmpty": "共0条记录",
			"oPaginate": {
				"sPrevious": " 上一页 ",
				"sNext":     " 下一页 ",
				"sFirst": "首页",
				"sLast": "尾页"
			}
		},
		"sDom":'<"row"<"col-xs-6"r><"col-xs-6"f>>t<"row"<"col-xs-3"l><"col-xs-2"i><"col-xs-4"p>>',
		"preDrawCallback": function( settings ) {
			var iDisplayStart = settings._iDisplayStart;
			pageSize = settings._iDisplayLength;
			currentPage = iDisplayStart / pageSize +1;
		},
		"drawCallback": function( settings ) {
			if(!isnull(callback)){
				callback(settings);
			}
			$("#"+settings.sInstance+" input[type='checkbox']").prop("checked", false);
			$("#"+settings.sInstance+" tbody input[type='checkbox']").change(function(){
				if(!$(this).prop("checked")){
					$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", false);
				}else{
					var len = $("#"+settings.sInstance+" tbody input[type='checkbox']").not("input:checked").length;
					if(len==0){
						$("#"+settings.sInstance+" thead input[type='checkbox']").prop("checked", true);
					}
				}
			});


			$("#"+tabId + " th").eq("0").css("cursor","default");


			setTimeout(function(){
				if(tabId != 'commentTable'){
					$('#'+tabId).removeAttr("style");
				}
			},100);

		},
		"initComplete": function(settings, json) {
			if(!isnull(initComplete)){
				initComplete();
			}
		}
	});
	return tables;
}

function setCookie(c_name, value, expiredays){
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie=c_name+ "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}
function getCookie(c_name){
	if (document.cookie.length>0){
		var c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1){
			c_start = c_start + c_name.length+1;
			c_end=document.cookie.indexOf(";",c_start);		
			if (c_end==-1)
				c_end=document.cookie.length;
		
			return unescape(document.cookie.substring(c_start,c_end));	
		}
	}
	
	return "";
}

/**
 * 刷新table
 * @param dataTable
 * @param pageNo 页码
 */
function freshTable(dataTable,pageNo){
	var oSettings = dataTable.fnSettings();
	var iDisplayLength = oSettings._iDisplayLength;
	if(!isnull(pageNo)){
		oSettings._iDisplayStart = iDisplayLength*(pageNo-1);
	}
	dataTable.fnDraw(oSettings);
	$(".dataTable input[type='checkbox']").prop("checked", false);
}



/**
 * 删除后刷新table
 * @param dataTable
 */
function freshDelTable(dataTable){
	var oSettings = dataTable.fnSettings();
	var iDisplayLength = oSettings._iDisplayLength;
	var iRecordsTotal = oSettings._iRecordsTotal;
	if(iRecordsTotal>iDisplayLength && iRecordsTotal%iDisplayLength==1){
		oSettings._iDisplayStart = iRecordsTotal-1-iDisplayLength;
	}
	dataTable.fnDraw(oSettings);
	$(".dataTable input[type='checkbox']").prop("checked", false);
}


/**
 * 查看table行的某一列的值
 * @param row  当前行对象
 * @param colNum  要查看的列数 1：表示查看第1列
 * @returns
 */
function getColData(row,colNum){
	var num = colNum-1;
	if(!isnull(row) && num>=0){
		var datas = $(row).find("td");
		return datas[num].innerHTML;
	}else{
		return "";
	}
}

/**
 * 加载tabs并附上分页控件 
 * @param divId  显示位置的DIV
 * @param tabsObj  [{"name":"xxx","url":"xxx","objData":objData},{"name":"xxx","url":"xxx","objData":objData}]
 */
function showTabsAndLoadData(divId,tabsObj){
	$("#"+divId).html("");
	var htmls = '<div id="tabs"><ul>';
	var tabDiv = '';
	if(tabsObj.length>0){
		for(var i=0;i<tabsObj.length;i++){
			htmls += '<li><a href="#tabs-'+i+'">'+tabsObj[i].name+'</a></li>';
			tabDiv += '<div id="tabs-'+i+'"><table id="tabs-'+i+'_dataTable" class="table table-striped table-bordered table-hover"></table></div>';
		}
		htmls += '</ul>'+tabDiv+'</div>';
		$("#showTabs").html(htmls);
		
		$('#tabs').on('click', 'li a', function() {
			var href = $(this).attr("href");
			var n = href.substring(href.length-1);
			href = href.substring(1);
			var tableId = href+"_dataTable";
			if (!$.fn.dataTable.isDataTable('#'+tableId)) {
				initTables(tableId,tabsObj[n].url,tabsObj[n].objData,true,true,tabsObj[n].num);
			}
		});
		setTimeout(function(){
			$("#tabs").tabs();
			$('#tabs li a[href="#tabs-0"]').click();
		},100);
	}
}

/**
 * 加载tabs页 
 * @param divId  显示位置的DIV
 * @param tabsObj  [{"name":"xxx","html":"text","id":"tabs_id"}]
 */
function showTabs(divId,tabsObj,callback){
	$("#"+divId).html("");
	var htmls = '<div id="'+divId+'_tabs"><ul>';
	var tabDiv = '';
	if(tabsObj.length>0){
		for(var i=0;i<tabsObj.length;i++){
			var id = tabsObj[i].id;
			if(!isnull(id)){
				htmls += '<li><a href="#'+id+'">'+tabsObj[i].name+'</a></li>';
				tabDiv += '<div id="'+id+'">'+tabsObj[i].html+'</div>';
			}else{
				htmls += '<li><a href="#'+divId+'_tabs-'+i+'">'+tabsObj[i].name+'</a></li>';
				tabDiv += '<div id="'+divId+'_tabs-'+i+'">'+tabsObj[i].html+'</div>';
			}
		}
		htmls += '</ul>'+tabDiv+'</div>';
		$("#"+divId).html(htmls);
		
		setTimeout(function(){
			$('#'+divId+'_tabs').tabs();			
			if(!isnull(callback)){
				callback();
			}
		},100);
	}
}

/**
 * 获取当前选中的tab页的对象
 * @param divId
 * @returns {Object}
 */
function getCurrentTabs(divId){
	var object=null;
	$("#"+divId+"_tabs").find("div").each(function(){
		if($(this).css("display")=="block"){
			object = $(this);
			return false;
		}
	});
	return object;
}

/**
 * 
 * @param obj
 * @param selectEvent  选中日期的事件
 */
function createDatePicker(obj,selectEvent){
	obj.datepicker({
		autoclose: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		yearRange: '-100:+10',
		todayHighlight: true,
		onSelect:function(data){
			if(!isnull(selectEvent)){
				selectEvent(data);
			}
		}
	});
	$(".hasDatepicker").attr({ readonly: 'true' }); 
}

/**
 * 
 * @param obj
 * @param dateFormat  yy-mm-dd
 */
function createDatePicker2(obj,dateFormat){
	obj.datepicker({
		autoclose: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: dateFormat,
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		yearRange: '-100:+10',
		todayHighlight: true
	});
}

/**
 * 
 * @param obj
 * @param selectEvent  选中日期的事件
 */
function createDatetimePicker(obj,selectEvent){
	obj.datetimepicker({
		showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
		onSelect:function(data){
			if(!isnull(selectEvent)){
				selectEvent(data);
			}
		}
	});
}

/**
 * 
 * @param obj
 * @param dateFormat  yy-mm-dd
 * @param timeFormat  HH:mm:ss
 */
function createDatetimePicker2(obj,dateFormat,timeFormat){
	obj.datetimepicker({
		showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: dateFormat,
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: timeFormat
	});
	$(".datetime-picker").attr({ readonly: 'true' }); 
}

/**
 *
 * @param obj
 * @param dateFormat  yy-mm-dd
 * @param timeFormat  HH:mm:ss
 */
function createDatetimePicker3(obj,dateFormat){
	obj.datepicker({
		lang:"ch",
		showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat: dateFormat,
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ]
	});
}

function createKindEdit(obj,afterBlur){
	var kindEditor = KindEditor.create(obj, {
		allowFileManager : true,
		items : ['undo', 'redo', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'image', 'link','unlink'],
		afterBlur:function(){
			if(!isnull(afterBlur)){
				afterBlur();
			}
		}
	});
	return kindEditor;
}

//创建编辑器
function createEdit(obj,hasImg){
	var options = [
	   			'font',
				null,
				'fontSize',
				null,
				{name:'bold', className:'btn-info'},
				{name:'italic', className:'btn-info'},
				{name:'strikethrough', className:'btn-info'},
				{name:'underline', className:'btn-info'},
				null,
				{name:'insertunorderedlist', className:'btn-success'},
				{name:'insertorderedlist', className:'btn-success'},
				{name:'outdent', className:'btn-purple'},
				{name:'indent', className:'btn-purple'},
				null,
				{name:'justifyleft', className:'btn-primary'},
				{name:'justifycenter', className:'btn-primary'},
				{name:'justifyright', className:'btn-primary'},
				{name:'justifyfull', className:'btn-inverse'},
				null,
				{name:'createLink', className:'btn-pink'},
				{name:'unlink', className:'btn-pink'},
				null,
//				{name:'insertImage', className:'btn-success'},
				null,
				'foreColor',
				null,
				//{name:'undo', className:'btn-grey'},
				//{name:'redo', className:'btn-grey'}
			];
	if(typeof(hasImg)=="undefined"){
		options.push({name:'insertImage', className:'btn-success'});
		options.push({name:'undo', className:'btn-grey'});
		options.push({name:'redo', className:'btn-grey'});
	}else{
		options.push({name:'undo', className:'btn-grey'});
		options.push({name:'redo', className:'btn-grey'});
	}
	obj.ace_wysiwyg({
		toolbar:options
		,
		'wysiwyg': {
			fileUploadError: showErrorAlert
		}
	}).prev().addClass('wysiwyg-style2');
	
	if ( typeof jQuery.ui !== 'undefined' && ace.vars['webkit'] ) {
		var lastResizableImg = null;
		function destroyResizable() {
			if(lastResizableImg == null) return;
			lastResizableImg.resizable( "destroy" );
			lastResizableImg.removeData('resizable');
			lastResizableImg = null;
		}

		var enableImageResize = function() {
			$('.wysiwyg-editor').on('mousedown', function(e) {
				var target = $(e.target);
				if( e.target instanceof HTMLImageElement ) {
					if( !target.data('resizable') ) {
						target.resizable({
							aspectRatio: e.target.width / e.target.height,
						});
						target.data('resizable', true);
						
						if( lastResizableImg != null ) {
							//disable previous resizable image
							lastResizableImg.resizable( "destroy" );
							lastResizableImg.removeData('resizable');
						}
						lastResizableImg = target;
					}
				}
			}).on('click', function(e) {
				if( lastResizableImg != null && !(e.target instanceof HTMLImageElement) ) {
					destroyResizable();
				}
			}).on('keydown', function() {
				destroyResizable();
			});
	    }

		enableImageResize();
	}
}

function showErrorAlert (reason, detail) {
	var msg='';
	if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
	else {
		//console.log("error uploading file", reason, detail);
	}
	$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
	 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
}

//全选
function chooseAll(obj){
	var table = $(obj).parent().parent().parent().parent().parent();
	var tableId = table.attr("id");
	var isChecked = $(obj).is(":checked");
	$("#" + tableId + " input[type='checkbox']:enabled").prop("checked", isChecked);
}

//全选,置灰的不选中
function chooseAll2(obj){
	
	var table = $(obj).parent().parent().parent().parent().parent();
	var tableId = table.attr("id");
	var isChecked = $(obj).is(":checked");
	$("#"+tableId+" input[type='checkbox'][disabled!='disabled']").prop("checked", isChecked);
}

//生成全选checkkbox
function createAllCheckBox(){
	return '<label class="position-relative"><input type="checkbox" class="ace" onclick="chooseAll(this)" /><span class="lbl"></span></label>';
}
function createAllCheckBox2(){
	return '<label class="position-relative"><input type="checkbox" class="ace" onclick="chooseAll2(this)" /><span class="lbl"></span></label>';
}
//生成普通checkbox
function createCheckBox(data){
	return '<label class="position-relative"><input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span></label>';
}

/**
 * 
 * @param url
 * @param dataTable
 * @param title    删除时弹框的标题名字  有默认值
 * @param content  删除时弹框的内容  有默认值
 * @param btnName  删除时弹框的按钮名称  有默认值
 */
function deleteAll(url,dataTable,title,content,btnName){
	var ids = new Array();
	$(".dataTable tbody input[type='checkbox']:checked").each(function(){
		ids.push($(this).val());
		/*var oo = $(this).parent().parent();
		var aData = dataTable.fnGetData(oo); // get datarow
		alertInfo(aData.name)*/
	});
	if(ids.length>0){
		showDelDialog(function(){
			ajaxRequest(url,{'ids':ids.join(",")},function(data){
				if(!data.success){
					//alertInfo(data.errorMessage);
					layer.msg(data.errorMessage);
				}else{
					if(!isnull(dataTable)){
						freshDelTable(dataTable);
					}
					if(typeof(data.errorMessage) != "undefined"){
						//alertInfo(data.errorMessage);
						layer.msg(data.errorMessage);
					}
				}
			});
		},title,content,btnName);
	}else{
		showDelDialog("",title,"请选择操作对象！",btnName);
	}
}

function createImageUpload(obj){
	obj.ace_file_input({
		style:'well',
		btn_choose:'点击选择图片',
		btn_change:null,
		no_icon:'ace-icon fa fa-cloud-upload',
		droppable:true,
		thumbnail:'small',//large | fit	
		preview_error : function(filename, error_code) {
		}
	}).on('change', function(){
	});
	obj.ace_file_input('update_settings',
	{
		'allowExt': ["jpeg", "jpg", "png", "gif" , "bmp"],
		'allowMime': ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"]
	});
}

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImage(inputId,imgSrc){
    if(imgSrc==null || imgSrc == "")return;
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			 +('<img class="middle" style="width: 250px; height: 140px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
					 +'</span>');
}

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImage2(inputId,imgSrc){
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			 +('<img class="middle" style="width: 50px; height: 50px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
					 +'</span>');
}

/**
 * 生成普通文件上传
 * @param obj
 */
function createFileUpload(obj){
	obj.ace_file_input({
		no_file:'暂无文件 ...',
		btn_choose:'选择',
		btn_change:'选择',
		droppable:false,
		onchange:null,
		thumbnail:false //| true | large
		//whitelist:'gif|png|jpg|jpeg'
		//blacklist:'exe|php'
		//onchange:''
		//
	});
}


function mask(text){
	if(!isnull(text)){
		$('#rightDivContent').mask(text);
	}else{
		$('#rightDivContent').mask("loading...");
	}
}

function unmask(){
	$('#rightDivContent').unmask();
}

/**
 * 自定义弹框
 * @param msg
 */
function alertInfo(msg,callback){
	     var option = {
			resizable: false,
			modal: true,
				title: "消息提示",
//				icon: "0 0",//蓝色i
				onOk: function(){
					if(!isnull(callback)){
						callback();
					}
				}
			}
	window.wxc.xcConfirm(msg,option);
}


/**
 * 获取url中的参数
 * @param paramName参数名称
 * @return value
 */
function getQueryString(paramName)
{
    return getParam(paramName);
}

/**
 * textarea字数统计
 * @param node
 */
function textarealength(node){
	var maxCount =node.attr("maxCount")||200;
	 var len = node.get(0).value.trim().length;
	node.next(".count").html(len+"/"+maxCount);
	if(node.get(0).value.length>=maxCount){
		 node.get(0).value = node.get(0).value.trim().substr(0,maxCount);
	}
}

function rightPageLocationHref(){
	var location = window.location.href;
	var url = location;
	var tmp = location.split("#")[0]+"#welcome";
	
	$("body").append("<a data-url=\"\" href=\""+ tmp +"\"><span  id='hidePageGotoAtag' ></span></a>");		
	var a = $("body").find("#hidePageGotoAtag");
	$(a).attr("href",tmp);
	$(a).trigger("click");		
	$(a).remove();

	$("body").append("<a data-url=\"\" href=\""+ url +"\"><span  id='hidePageGotoAtag' ></span></a>");		
	a = $("body").find("#hidePageGotoAtag");
	$(a).attr("href",tmp);
	$(a).trigger("click");
	$(a).remove();
}

var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
function isCardID(sId){   
    var iSum=0 ;  
    var info="" ;  
    if(!/^\d{17}(\d|x)$/i.test(sId)) 
    	//return "你输入的身份证长度或格式错误";   
    	return false;
    sId=sId.replace(/x$/i,"a");   
    if(aCity[parseInt(sId.substr(0,2))]==null) 
    	//return "你的身份证地区非法";  
    	return false;
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));   
    var d=new Date(sBirthday.replace(/-/g,"/")) ;  
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))
    	//return "身份证上的出生日期非法";   
    	return false;
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;  
    if(iSum%11!=1) 
    	//return "你输入的身份证号非法";   
    	return false;
    return true; 
}






/**
 * 获取url中的参数
 * @param name
 * @returns
 */
function getParam(name) {
	var reg = new RegExp("(\\?|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.href.match(reg);
	if (r != null) return unescape(r[2]); return null;
} 


/**
 * 获取url中的参数
 * @param name
 * @returns
 */
function getParam2(name) {
	var reg = new RegExp("(\\?|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.href.match(reg);
	if (r != null) return r[2]; return null;
}

/**
 *判断上传的类型，只支持图片类型
 * @param o file对象 value值
 * @returns {Boolean}
 */
function validateImg(o){
    var pos=o.lastIndexOf("\\");
    var filepath=o.substring(pos+1);
    filepath = filepath.substring(filepath.lastIndexOf(".") + 1,filepath.length);
	filepath = filepath.toLowerCase();
	if (filepath == "jpg"||filepath == "jpeg" || filepath == "png"||filepath == "bmp"||filepath == "gif"||filepath=="svg") {
		return true;
	} else {
		return false;
	}
}

/**
 * 确认框
 * @param callback
 * @param title  标题名字  有默认值
 * @param content  内容  有默认值
 * @param btnName  按钮名称  有默认值
 */
function confirmDialog(callback,title,content,btnName){
	if(isnull(title)){
		title = "操作提示";
	}
	if(!isnull(content)){
		$("#confirm_content").html(content);
	}else{
		$("#confirm_content").html("确定要执行此操作吗?");
	}
	if(isnull(btnName)){
		btnName = "确定";
	}
	$( "#dialog-confirm" ).removeClass('hide').dialog({
		resizable: false,
		modal: true,
		closeText:"关闭",
		title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> "+title+"</h4></div>",
		title_html: true,
		buttons: [
			{
				html: btnName,
				"class" : "btn btn-success btn-xs",
				click: function() {
					$( this ).dialog( "close" );
					if(!isnull(callback)){
						callback();
					}
				}
			},
			{
				html: "取消",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
}

function moveDiv(){
$(".widget-header").mousedown(function(e)//e鼠标事件
{
$(this).css("cursor","move");//改变鼠标指针的形状

var offset = $(this).offset();//DIV在页面的位置
var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离
var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离
$(document).bind("mousemove",function(ev)//绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件
{
$(".ui-dialog").stop();//加上这个之后

var _x = ev.pageX - x;//获得X轴方向移动的值
var _y = ev.pageY - y;//获得Y轴方向移动的值

$(".ui-dialog").animate({left:_x+"px",top:_y+"px"},10);
});

});

$(document).mouseup(function()
{
$(".widget-header").css("cursor","default");
$(this).unbind("mousemove");
})
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
    return year+"-"+month+"-"+day ; 
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

/**
 * 获取当前页码
 * @param dataTable
 * @returns {Number}
 */
function getCurrentPageNo(dataTable){
	var oSettings = dataTable.fnSettings();
	var index = oSettings._iDisplayStart;
	var pageSize = oSettings._iDisplayLength;
	var currentPage = index / pageSize + 1;
	return currentPage;
}
/**
 * 生成随机字符串
 * @param len 长度，默认32
 */
function getRandomString(len) {
    len = len || 32;  
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1  
    var maxPos = $chars.length;  
    var pwd = '';  
    for (i = 0; i < len; i++) {  
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));  
    }  
    return pwd;  
}
function getRandomInt(n){
	return Math.floor(Math.random()*n+1)
}

/**
 * 数组去重
 * 
 */
Array.prototype.unique2 = function(){
	if(this[0] == undefined )
		return [];
	this.sort();
	var re=[this[0]];
	for(var i = 1; i < this.length; i++){
		if( this[i] !== re[re.length-1]){
			re.push(this[i]);
		}
	}
	return re;
}


/**
 * 弱提示，1.5s后消失
 * @param msg
 */
function alertMsg(msg){
	layer.open({
		title:["消息提示",'font-size:18px;font-weight:bold'],
		content:msg,
		offset: '335px',
		closeBtn: 0,
		icon: 6,
		btn:0,
		shade: 0,
		area: ['260px', '149px'],
		time: 1500
	});
}

/**
 * 自定义confirm弹框
 * @param txt
 * @param callback
 */
function confirmInfo(txt, callback, cancelCallback) {
	var option = {
		title: "提示",
		btn: parseInt("0011", 2),
		onOk: function () {
			if (!isnull(callback)) {
				callback();
			}
		},
		onCancel: function () {
			if (!isnull(cancelCallback)) {
				cancelCallback();
			}
		},
		onClose: function (event) {
			if (event === window.wxc.xcConfirm.eventEnum.close && !isnull(cancelCallback)) {
				cancelCallback();
			}
		}
	}
	window.wxc.xcConfirm(txt, "custom", option);
}
/*说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。*/
function parseDivisionError(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
/*说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。*/
function parseMultiplError(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
/*说明：javascript的加法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。*/
function parseAddError(arg1, arg2) {
	var a1, a2, ae;
	try {
		a1 = arg1.toString().split(".")[1].length
	} catch (e) {
		a1 = 0
	}
	try {
		a2 = arg2.toString().split(".")[1].length
	} catch (e) {
		a2 = 0
	}
	ae = Math.pow(10, Math.max(a1, a2))
	return (arg1 * ae + arg2 * ae) / ae
}
/*说明：javascript的减法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。*/
function parseSubtraError(arg1, arg2) {
	var sba1, sba2, sba;
	try {
		sba1 = arg1.toString().split(".")[1].length
	} catch (e) {
		sba1 = 0
	}
	try {
		sba2 = arg2.toString().split(".")[1].length
	} catch (e) {
		sba2 = 0
	}
	sba = Math.pow(10, Math.max(sba1, sba2))
	return (arg1 * sba - arg2 * sba) / sba
}

