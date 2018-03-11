var _courseTable;
var searchCase;
var courseForm;
var teacherArray=new Array();
var _courseRecTable;
debugger;
$(function(){

	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	
	
	/** 直播课管理 begin */
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},
    {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
	{ "title": "直播名称", "class":"center","width":"8%","sortable":false,"data": 'courseName' },
	{ "title": "直播状态", "class":"center","width":"6%","sortable":false,"data": 'liveStatus' ,"mRender":function (data, display, row) {
		debugger
			if(data==1 ){  //直播状态1.直播中，2预告，3直播结束
				return "直播中";
			}else if(data== 2){
				return "预告";
			}else if(data== 3){
				return "结束";
			}
		} },
    { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'menuName' },
    { "title": "作者", "class":"center","width":"8%","sortable":false,"data": 'lecturerName' },
    { "title": "主播", "class":"center","width":"8%","sortable":false,"data": 'lecturer' },
    { "title": "课程时长", "class":"center","width":"7%", "sortable":false,"data": 'courseLength' },
    { "title": "开播时间", "class":"center","width":"10%", "sortable":false,"data": 'startTime' },
    { "title": "发布时间", "class":"center","width":"10%", "sortable":false,"data": 'releaseTime' },
    //	private  int liveSource;  //直播来源  1、后台新增  2、app申请
    { "title": "直播来源", "class":"center","width":"10%","data":"liveSource","sortable":false,"mRender":function(data,display,row){
    	if(data!=null && data== 2){
    		return "用户申请";
    	}else{
    		return "后台新增";
    	}
    }},

    { "title": "价格", "class":"center","width":"5%","sortable":false,"mRender":function(data,display,row){
    	data = row.currentPrice;
    	return "<span name='coursePrice'>"+data+"</span>"
    }},
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="<span name='zt'>已启用</span>";
    	}else{
    		return data="<span name='zt'>已禁用</span>";
    	}
    } },
    { "title": "推荐值", "class":"center","width":"6%", "sortable":false,"data": 'recommendSort' },

    { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status=="1"){
	    		var str = '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                // '<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
				if(row.directId == null || row.directId == ""){
					str+='<a class="blue" href="javascript:void(-1);" title="生成直播间" onclick="createWebinar(this);"><i class="ace-icon fa fa-retweet bigger-130"></i></a> '
				}else{
					str+='<a class="blue" href="javascript:void(-1);" title="主播地址" onclick="getWebinarUrl(this);"><i class="ace-icon glyphicon glyphicon-camera bigger-130"></i></a> '
				}
				return str;
	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                    // '<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'+
                    // '<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '
	    	}
	    }
	},
	{ "title": "课程展示图", "class":"center","width":"13%","sortable":false,"data": 'smallimgPath',"visible":false},
	{ "title": "直播方式", "class":"center","width":"13%","sortable":false,"data": 'directSeeding',"visible":false},
	{ "title": "直播间ID", "class":"center","width":"13%","sortable":false,"data": 'directId',"visible":false},
	{ "title": "外部链接", "class":"center","width":"13%","sortable":false,"data": 'externalLinks',"visible":false}];

	//TODO
	_courseTable = initTables("courseTable",basePath+"/publiccloudclass/course/list",objData,true,true,2,null,searchCase,function(data){
		var texts = $("[name='courseNameList']");
	    for (var i = 0; i < texts.length; i++) {
	            texts.eq(i).parent().attr("title",texts.eq(i).text());
	    }
	    var texts1 = $("[name='lecturerNameList']");
	    for (var i = 0; i < texts.length; i++) {
	    	texts1.eq(i).parent().attr("title",texts1.eq(i).text());
	    }
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

			courseForm = $("#addCourse-form").validate({
		        messages: {
		        	smallingPath:{
						required:"课程展示图不能为空！",

		            },
					courseName: {
						required:"请输入课程名称！",
						minlength:"课程名称过短，应大于2个字符！",
						maxlength:"课程名称过长，应小于20个字符！"
		            },
					classTemplate: {
						required:"请输入班级名称模板！"
					},
					menuId: {
						required:"请选择所属学科！"
					},
					userLecturerId:{
						required:"选择作者！"
					},
					courseLength: {
						required:"请输入课程时长！",
						digits: "课程时长必须为整数！"
					},
					coursePwd: {
						digits: "课程密码必须为整数！"
					},
					startTime: {
						required:"开播时间不能为空！",
					},
					endTime: {
						required:"结束时间不能为空！",
					},
					directId: {
						required:"直播间ID不能为空！",
					}
		        }
		    });
		updateCourseForm = $("#updateCourse-form").validate({
			messages: {
				smallingPath:{
					required:"课程展示图不能为空！",

	            },
				courseName: {
					required:"请输入课程名称！",
					minlength:"课程名称过短，应大于2个字符！",
					maxlength:"课程名称过长，应小于20个字符！"
	            },
				classTemplate: {
					required:"请输入班级名称模板！"
				},
				menuId: {
					required:"请选择所属学科！"
				},
				lecturerId:{
					required:"选择教师！"
				},
				courseLength: {
					required:"请输入课程时长！",
					digits: "课程时长必须为整数！"
				},
				coursePwd: {
					digits: "课程密码必须为整数！"
				},
				startTime: {
					required:"开播时间不能为空！",
				},
				endTime: {
					required:"结束时间不能为空！",
				},
				directId: {
					required:"直播间ID不能为空！",
				},
				externalLinks: {
					required:"外部链接不能为空！"
				},
				smallingPath:{
					required:"课程展示图不能为空！",

	            },
				courseName: {
					required:"请输入课程名称！",
					minlength:"课程名称过短，应大于2个字符！",
					maxlength:"课程名称过长，应小于20个字符！"
	            },
				classTemplate: {
					required:"请输入班级名称模板！"
				},
				menuId: {
					required:"请选择所属学科！"
				},
				lecturerId:{
					required:"选择教师！"
				},
				userLecturerId:{
					required:"选择教师！"
				},
				courseLength: {
					required:"请输入课程时长！",
					digits: "课程时长必须为整数！"
				},
				startTime: {
					required:"开播时间不能为空！",
				},
				endTime: {
					required:"结束时间不能为空！",
				},
				directId: {
					required:"直播间ID不能为空！",
				},
				externalLinks: {
					required:"外部链接不能为空！"
				},
				teacherImgPath: {
					required:"讲师头像不能为空！"
				}
			}
		});
	});
	/** 直播课管理 end */

	
	/** 直播课统计 begin */
	/*var objRecData = [
	{title: '序号', "class": "center","data-width":"13px" ,"data": 'id',datafield: 'xuhao', "sortable": false},
	{ "title": "公开课名称", "width":"16%","class":"center","sortable":false,"data": 'courseName' ,"mRender":function (data, display, row) {
    		return "<span name='courseNameList'>"+data+"</span>";
    } },
	{ "title": "学科", "class":"center","width":"12%","sortable":false,"data": 'menuName' },
	{ "title": "授课老师", "class":"center","width":"12%","sortable":false,"data": 'lecturerName' ,"mRender":function (data, display, row) {
    		return "<span name='lecturerNameList'>"+data+"</span>";
    } },
//	{ "title": "鲜花数", "class":"center","width":"8%","sortable":false,"data": 'flowersNumber' },
	{ "title": "访问量", "class":"center","width":"12%","sortable":false,"data": 'pv' },
//	{ "title": "最高在线人数", "class":"center","width":"8%","sortable":false,"data": 'highestNumberLine' },
//	{ "title": "直播结束时<br/>在线人数", "class":"center","width":"8%","sortable":false,"data": 'endLineNumber' },
	{ "title": "课程时长", "class":"center","width":"12%", "sortable":false,"data": 'courseLength'},
	{ "title": "开始时间", "class":"center","width":"12%", "sortable":false,"data": 'startTime'},
	{ "title": "结束时间", "class":"center","width":"12%", "sortable":false,"data": 'endTime' },
	{ "title": "课程展示图", "class":"center","width":"13%","sortable":false,"data": 'smallimgPath',"visible":false},
	{ "title": "直播方式", "class":"center","width":"13%","sortable":false,"data": 'directSeeding',"visible":false},
	{ "title": "直播间ID", "class":"center","width":"13%","sortable":false,"data": 'directId'},
	{ "title": "外部链接", "class":"center","width":"13%","sortable":false,"data": 'externalLinks',"visible":false}];

	_courseRecTable = initTables("courseRecTable",basePath+"/publiccloudclass/course/coursesReclist",objRecData,true,true,1,null,searchCase,function(data){
		var texts = $("[name='courseNameList']");
	    for (var i = 0; i < texts.length; i++) {
	            texts.eq(i).parent().attr("title",texts.eq(i).text());
	    }
	    var texts1 = $("[name='lecturerNameList']");
	    for (var i = 0; i < texts.length; i++) {
	    	texts1.eq(i).parent().attr("title",texts1.eq(i).text());
	    }
	});*/

	/** 直播课统计 end */
	
	
	/** 直播课 begin */
	//TODO
    var objZbRecData = [
    { "title": "序号", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "课程名称", "class":"center","width":"20%","sortable":false,"data": 'courseName' },
	{ "title": "业务类型", "class":"center","width":"13%","sortable":false,"data": 'serviceType' ,"mRender":function (data, display, row) {
		if(data==0){
			return "职业课";
		}else{
			return "微课";
		}
	}},
    { "title": "课程展示图", "class":"center","width":"13%","sortable":false,"data": 'recImgPath' ,"mRender":function (data, display, row) {
    	if(data != "" && data != null){
    		return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;' onclick='showImg(\""+row.id+"\",\""+row.courseName+"\",\""+row.recImgPath+"\")'/>";
    	}else{
    		return "暂无图片";    	
    	}
	}},
    { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'xMenuName' },
    { "title": "讲师", "class":"center","width":"10%","sortable":false,"data": 'lecturerName' },
    
    { "title": "现价格", "class":"center","width":"9%","sortable":false,"mRender":function(data,display,row){
    	return data = row.currentPrice;
    }},
    { "title": "开播时间", "class":"center","width":"10%", "sortable":false,"data": 'startTime' },
	{ "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
			if(data==1){
				return data="<span name='zt'>已启用</span>";
			}else{
				return data="<span name='zt'>已禁用</span>";
			}
		} },
    { "title": "推荐值", "class":"center","width":"10%","sortable":false,"data": 'recommendSort' },
    { "sortable": false,"class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
    		return '<div class="hidden-sm hidden-xs action-buttons">'+
			'<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this);">设置推荐值</a> ';
  		}
	}];

    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":8,"propertyName":"search_type","propertyValue1":"1","tempType":Integer}');
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_liveStatus","propertyValue1":"1","tempType":"Integer"}');
    
	zb_courseRecTable = initTables("courseZbRecTable",basePath+"/cloudclass/course/recList",objZbRecData,true,true,0,null,searchCase_P,function(data){
		$("[name='liveUpa']").each(function(index){
			if(index == 0){
				$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
		}); 
		$("[name='liveDowna']").each(function(index){
			if(index == $("[name='liveDowna']").size()-1){
				$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
		});
		$("#courseRecTable_info").hide();
	});
	/** 课程推荐列表end */
	
	
	createImageUpload($('.uploadImg'));//生成图片编辑器

	createDatePicker($("#search_startTime"));
	createDatePicker($("#search_endTime"));
});


$('#startTime,#courseLength').change(function(){

     var startTime = $('#startTime').val();
     var hours= $('#courseLength').val()*60*60*1000;
     if(startTime!=""&&!isNaN(hours)&&hours!=""){
    	 startTime = startTime.replace(new RegExp("-","gm"),"/");
         startTime = (new Date(startTime)).getTime(); //得到毫秒数
         $('#endTime').val(new Date(startTime+hours).format("yyyy-MM-dd hh:mm:ss"));
     }
	});

Date.prototype.Format = function(fmt) { //author: meizz
  var o = {
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日
    "h+" : this.getHours(),                   //小时
    "m+" : this.getMinutes(),                 //分
    "s+" : this.getSeconds(),                 //秒
    "q+" : Math.floor((this.getMonth()+3)/3), //季度
    "S"  : this.getMilliseconds()             //毫秒
  };
  if(/(y+)/.test(fmt))
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)
    if(new RegExp("("+ k +")").test(fmt))
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
  return fmt;
}
//新增框
$(".add_bx").click(function(){
	courseForm.resetForm();
	debugger;
	$("#lecturer").html("");
	$('#lecturer').append("<option value=''>请选择</option>");

	$("#add-directIdDiv").hide();
	$("#add-externalLinksDiv").hide();
	$('#startTime').datepicker( "option" , {
		 minDate: null,
		 maxDate: null} );
//	$('#endTime').datepicker( "option" , {
//		 minDate: null,
//		 maxDate: null} );
	 $('#startTime').datetimepicker({
	    	showSecond: true,
			changeMonth: true,
			changeYear: true,
			dateFormat:'yy-mm-dd',
			monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
			timeFormat: 'HH:mm:ss',
	    	onSelect: function( startDate ) {
	    		var $startDate = $( "#startTime" );
	    		var $endDate = $('#endTime');
	    		var endDate = $endDate.datepicker( 'getDate' );
	    		if(endDate < startDate){
	    			$endDate.datetimepicker('setDate', startDate - 3600*1*24*60*60*60);
	    		}
	    		$endDate.datetimepicker( "option", "minDate", startDate );
	    	}
	    });
//	    $('#endTime').datetimepicker({
//	    	showSecond: true,
//			changeMonth: true,
//			changeYear: true,
//			dateFormat:'yy-mm-dd',
//			monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
//			timeFormat: 'HH:mm:ss',
//	    	onSelect: function( endDate ) {
//	    		var $startDate = $( "#startTime" );
//	    		var $endDate = $('#endTime');
//	    		var startDate = $startDate.datepicker( "getDate" );
//	    		if(endDate < startDate){
//	    			$startDate.datetimepicker('setDate', startDate + 3600*1*24*60*60*60);
//	    		}
//	    		$startDate.datetimepicker( "option", "maxDate", endDate );
//	    	}
//	    });
	imgSenBut();
	createImageUpload($('#addCourse-form .uploadImg'));//生成图片编辑器

	var dialog = openDialog("addCourseDialog","dialogAddCourseDiv","新增公开课",580,600,true,"确定",function(){

		if($("#addCourse-form").valid()){
			mask();
			 $("#addCourse-form").attr("action", basePath+"/publiccloudclass/course/addCourse");
	            $("#addCourse-form").ajaxSubmit(function(data){
	            	try{
                		data = jQuery.parseJSON(jQuery(data).text());
                	}catch(e) {
                		data = data;
                	  }
                	unmask();
	                if(data.success){
	                    $("#addCourseDialog").dialog("close");
	                    layer.msg(data.errorMessage);
	                    freshTable(_courseTable);
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
		}
	});
});

//新增头像
function imgSenBut(){
	$("#imgAdd").html('<input type="file" name="smallImgPath_file" id="smallImgPath_file" class="uploadImg"/>');
	$("#teacherImgAdd").html('<input type="file" name="teacherImgPath_file" id="teacherImgPath_file" class="uploadImg"/>');
};
//图片上传统一上传到附件中心----新增用
$("#addCourse-form").on("change","#smallImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");

			$("#smallImgPath").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
$("#addCourse-form").on("change","#teacherImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 89px; height: 89px;");
			
			$("#teacherImgPath").val(data.url);
			document.getElementById("teacherImgPath_file").focus();
			document.getElementById("teacherImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
$('#startTime_edit,#courseLength_edit').change(function(){
	
    var startTime = $('#startTime_edit').val();
    var hours= $('#courseLength_edit').val()*60*60*1000;
    if(startTime!=""&&!isNaN(hours)&&hours!=""){
   	 startTime = startTime.replace(new RegExp("-","gm"),"/");
        startTime = (new Date(startTime)).getTime(); //得到毫秒数
        $('#endTime_edit').val(new Date(startTime+hours).format("yyyy-MM-dd hh:mm:ss"));
    }
	});

Date.prototype.Format = function(fmt) { //author: meizz   
 var o = {   
   "M+" : this.getMonth()+1,                 //月份   
   "d+" : this.getDate(),                    //日   
   "h+" : this.getHours(),                   //小时   
   "m+" : this.getMinutes(),                 //分   
   "s+" : this.getSeconds(),                 //秒   
   "q+" : Math.floor((this.getMonth()+3)/3), //季度   
   "S"  : this.getMilliseconds()             //毫秒   
 };   
 if(/(y+)/.test(fmt))   
   fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
 for(var k in o)   
   if(new RegExp("("+ k +")").test(fmt))   
 fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
 return fmt;   
} 

//修改
function toEdit(obj){
	
	
	updateCourseForm.resetForm();
	$('#startTime_edit').datepicker( "option" , {
		 minDate: null,
		 maxDate: null} );
//	$('#endTime_edit').datepicker( "option" , {
//		 minDate: null,
//		 maxDate: null} );
	$('#startTime_edit').datetimepicker({
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#startTime_edit" );
    		var $endDate = $('#endTime_edit');
    		var endDate = $endDate.datepicker( 'getDate' );
    		if(endDate < startDate){
    			$endDate.datetimepicker('setDate', startDate - 3600*1*24*60*60*60);
    		}
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });
//    $('#endTime_edit').datetimepicker({
//    	showSecond: true,
//		changeMonth: true,
//		changeYear: true,
//		dateFormat:'yy-mm-dd',
//		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
//		timeFormat: 'HH:mm:ss',
//    	onSelect: function( endDate ) {
//    		var $startDate = $( "#startTime_edit" );
//    		var $endDate = $('#endTime_edit');
//    		var startDate = $startDate.datepicker( "getDate" );
//    		if(endDate < startDate){
//    			$startDate.datetimepicker('setDate', startDate + 3600*1*24*60*60*60);
//    		}
//    		$startDate.datetimepicker( "option", "maxDate", endDate );
//    	}
//    });
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	reviewImageRecImg("smallImgPath_file_edit",row.smallimgPath);//预览
	reviewImageRecImg2("teacherImgPath_file_edit",row.teacherImgPath);//预览

	$("#updateCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	
	$("#editCourse_id").val(row.id); //ID
	//所属学科
	for(i=0;i<$("#menuId_edit option").length;i++){
		if($("#menuId_edit option").eq(i).text()==row.menuName){
			$("#menuId_edit option").eq(i).attr("select","selected"); 
			$("#menuId_edit").val($("#menuId_edit option").eq(i).val());
		}
	}
	changeMenu_edit();
	//授课老师
	/*for(i=0;i<$("#lecturer_edit option").length;i++){
		if($("#lecturer_edit option").eq(i).text()==row.lecturerName){
			$("#lecturer_edit option").eq(i).attr("select","selected"); 
			$("#lecturer_edit").val($("#lecturer_edit option").eq(i).val());
		}
	}*/
	
	$("#edit-defaultStudentCount").val(row.defaultStudentCount); //课程名称
	$("#courseName_edit").val(row.courseName); //课程名称
    $("#edit_userLecturerId").val(row.lecturerName); //主播
	$("#courseLength_edit").val(row.courseLength); //课程时常
	$("#subtitle_edit").val(row.subtitle);
	$("#lecturer_edit").val(row.lecturer);
	$("#courseLength_edit").val(row.courseLength);
	$("#startTime_edit").val(row.startTime); //开播时间
	$("#endTime_edit").val(row.endTime); //结束时间
	//直播方式
	if(row.directSeeding == 1){ //本站
		$("#this_web_edit1").attr('checked',true);
		$("#this_web_edit2").removeAttr('checked');
		$("#this_web_edit3").removeAttr('checked');
		
	}else if(row.directSeeding == 2){//外站
		$("#this_web_edit2").attr('checked',true);
		$("#this_web_edit1").removeAttr('checked');
		$("#this_web_edit3").removeAttr('checked');
	}else{
		$("#this_web_edit3").attr('checked',true);
		$("#this_web_edit1").removeAttr('checked');
		$("#this_web_edit2").removeAttr('checked');
	}
	$("#directId_edit").val(row.directId);//直播间ID
//	$("#externalLinks_edit").val(row.externalLinks);//外部链接
	$("#smallImgPath_edit").val(row.smallimgPath); //图片字段赋值
	$("#teacherImgPath_edit").val(row.teacherImgPath); //图片字段赋值
	
	$("#coursePwd_edid").val(row.coursePwd); //密码
	
	$("#edid_originalCost").val(row.originalCost); //原价
	$("#edid_currentPrice").val(row.currentPrice); //现价
	$("#edid_courseDescribe").val(row.description); //简介
	
	var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv","修改课程",580,650,true,"确定",function(){
		
		if($("#updateCourse-form").valid()){
			mask();
            $("#updateCourse-form").attr("action", basePath+"/publiccloudclass/course/updateCourseById");
            $("#updateCourse-form").ajaxSubmit(function(data){
            	try{
            		data = jQuery.parseJSON(jQuery(data).text());
            	}catch(e) {
            		data = data;
            	  }
            
                unmask();
                if(data.success){
                	
                    $("#EditCourseDialog").dialog("close");
                    layer.msg(data.errorMessage);
                     freshTable(_courseTable);
                }else{
                	 layer.msg(data.errorMessage);
                }
            });
		}
	});
	
	//杨宣修改--增加模糊查询讲师功能
	for(i=0;i<$("#combobox1 option").length;i++){
	    console.log($("#combobox1 option").eq(i).val()+"==="+row.userLecturerId+"==="+$("#combobox1 option").eq(i).text());
		if($("#combobox1 option").eq(i).val()==row.userLecturerId){
			$("#combobox1 option").eq(i).attr("select","selected"); 
			$("#combobox1").val($("#combobox1 option").eq(i).val());
			$("#updateCourse-form #nihao").val($("#combobox1 option").eq(i).text());
		}
    }
	
	
}

//图片上传统一上传到附件中心--------修改用
$("#updateCourse-form").on("change","#smallImgPath_file_edit",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			
			$("#smallImgPath_edit").val(data.url);
			
			document.getElementById("smallImgPath_file_edit").focus();
			document.getElementById("smallImgPath_file_edit").blur();
		}else {
			alert(data.message);
		}
		$(".remove").hide();
	})
});

//图片上传统一上传到附件中心--------修改用
$("#updateCourse-form").on("change","#teacherImgPath_file_edit",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 89px; height: 89px;");
			
			
			$("#teacherImgPath_edit").val(data.url);
			
			document.getElementById("teacherImgPath_file_edit").focus();
			document.getElementById("teacherImgPath_file_edit").blur();
		}else {
			alert(data.message);
		}
		$(".remove").hide();
	})
});

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImageRecImg(inputId,imgSrc){
	if(imgSrc == null){
		return ;
	}
	$(".remove").hide();
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			 +('<img class="middle" style="width: 252px; height: 97px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
					 +'</span>');
}
/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImageRecImg2(inputId,imgSrc){
	if(imgSrc == null){
		$("#teacherImgEdit").html("<input type=\"file\" name=\"teacherImgPath_file\" id=\"teacherImgPath_file_edit\" class=\"uploadImg\"/>");
		$("#teacherImgPath_edit").val("");
		createImageUpload($("#teacherImgPath_file_edit"));
		
		//图片上传统一上传到附件中心--------修改用
		$("#updateCourse-form").on("change","#teacherImgPath_file_edit",function(){
			var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
			if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
				layer.alert("图片格式错误,请重新选择.");
				this.value="";
				return;
			}
			var id = $(this).attr("id");
			ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
				if (data.error == 0) {
					$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
					$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
					$("#"+id).parent().find(".ace-file-name img").attr("style","width: 89px; height: 89px;");
					
					
					$("#teacherImgPath_edit").val(data.url);
					
					document.getElementById("teacherImgPath_file_edit").focus();
					document.getElementById("teacherImgPath_file_edit").blur();
				}else {
					alert(data.message);
				}
				$(".remove").hide();
			})
		});		
		return ;
	}
	$(".remove").hide();
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			+('<img class="middle" style="width: 89px; height: 89px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
			+'</span>');
}

function changeMenu(){
	teacherArray=[];
	$("#lecturer").html("");
	ajaxRequest(basePath+'/publiccloudclass/course/getTeacher',{"menuId":$("#menuId").val()},function(res) {
		$('#lecturer').empty();
		 
	       if(res.length>0)
	        {
	            for(var i=0;i<res.length;i++)
	            {
	            	teacherArray.push(res[i]);
	                $('#lecturer').append("<option value='"+res[i].id+"'>"+res[i].name+"</option>");
	               
	            }
	           
	        }else{
	        	$('#lecturer').append("<option value=''>无</option>");
	        }
	       
		
	});
}

function changeMenu_edit(){
	teacherArray=[];
	$("#lecturer_edit").html("");
	ajaxRequest(basePath+'/publiccloudclass/course/getTeacher',{"menuId":$("#menuId_edit").val()},function(res) {
		$('#lecturer_edit').empty();
		 
	       if(res.length>0)
	        {
	            for(var i=0;i<res.length;i++)
	            {
	            	teacherArray.push(res[i]);
	                $('#lecturer_edit').append("<option value='"+res[i].id+"'>"+res[i].name+"</option>");
	               
	            }
	           
	        }else{
	        	$('#lecturer_edit').append("<option value=''>无</option>");
	        }
	       
		
	});
}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/cloudclass/course/updateStatus",{"id":row.id},function(data){
		console.log(data);
		if(data.success==false){
			layer.msg(data.errorMessage);
		}
		freshTable(_courseTable);
	});
};

/**
 * 批量逻辑删除
 * 
 */

$(".dele_bx").click(function(){
	deleteAll(basePath+"/cloudclass/course/deletes",_courseTable,null,"删除操作不可逆，对于已报名用户不会产生影响，是否确认删除该课程？");
});

function search(){
	searchButton(_courseTable);
};

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/publiccloudclass/course/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseTable);
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
	var aData = _courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/publiccloudclass/course/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};





/**
 * 状态修改
 * @param obj
 */
function updateRec(obj){
    var oo = $(obj).parent().parent().parent();
    var row = zb_courseRecTable.fnGetData(oo); // get datarow
    ajaxRequest(basePath+"/cloudclass/course/updateRec",{"ids":row.id,"isRec":0},function(data){
        if(data.success){
            layer.msg("取消成功！");
            freshTable(zb_courseRecTable);
        }else{
            layer.msg("取消失败！");
        }
    });
};

/**
 * Description：设置推荐值
 * @Date: 2018/3/9 14:11
 **/
function updateRecommendSort(obj,key){
	var row ="";
    var oo = $(obj).parent().parent().parent();
    if(key==1){
        row = _courseTable.fnGetData(oo);
    }else{
        row = zb_courseRecTable.fnGetData(oo); // get datarow
    }
    $("#UpdateRecommendSort_id").val(row.id);
    var dialog = openDialog("UpdateRecommendSortDialog","dialogUpdateRecommendSortDiv","修改推荐值",350,200,true,"确定",function(){
        if($("#UpdateRecommendSortFrom").valid()){
            mask();
            $("#UpdateRecommendSortFrom").attr("action", basePath+"/cloudclass/course/updateRecommendSort");
            $("#UpdateRecommendSortFrom").ajaxSubmit(function(data){
                try{
                    data = jQuery.parseJSON(jQuery(data).text());
                }catch(e) {
                    data = data;
                }
                unmask();
                if(data.success){
                    $("#recommendSort").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                    if(key==1){
                        freshTable(_courseTable);
                    }else{
                        freshTable(zb_courseRecTable);
                    }
                }else{
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};

/**
 * 职业课批量推荐
 * 
 */
$(".rec_P").click(function(){
	var ids = new Array();
	var trs = $(".dataTable tbody input[type='checkbox']:checked");
	
	for(var i = 0;i<trs.size();i++){
//		if($(trs[i]).parent().parent().find("[name='skfs']").eq("0").text() != "点播")
//		{
//			showDelDialog("","","目前只能推荐点播课程！","");
//			return false;
//		}
		
		if($(trs[i]).parent().parent().find("[name='zt']").eq("0").text() == "已禁用")
		{
			showDelDialog("","","无法推荐禁用课程！","");
			return false;
		}
		
		if($(trs[i]).parent().parent().find("[name='sftj']").eq("0").text() == "已推荐")
		{
			showDelDialog("","","无法推荐已推荐课程！","");
			return false;
		}
		ids.push($(trs[i]).val());
	}
//	if(ids.length > 4)
//	{
//		showDelDialog("","","最多只能推荐4个点播课程！","");
//		return false;
//	}
	if(ids.length>0){ 
			ajaxRequest(basePath+"/cloudclass/course/updateRec",{'ids':ids.join(","),"isRec":1},function(data){
				if(!data.success){//如果失败
					layer.msg(data.errorMessage);
				}else{
					if(!isnull(_courseTable)){
                        layer.msg("推荐成功！");
                        freshDelTable(_courseTable);
					}
					layer.msg(data.errorMessage);
				}
			});
	}else{
		showDelDialog("","","请选择推荐课程！","");
	}
});

/**
 * 设置为精品推荐
 */
$(".rec_jp").click(function(){
	var ids = new Array();
	var trs = $(".dataTable tbody input[type='checkbox']:checked");
	
	for(var i = 0;i<trs.size();i++){
		
		if($(trs[i]).parent().parent().find("[name='zt']").eq("0").text() == "已禁用")
		{
			showDelDialog("","","无法推荐禁用课程！","");
			return false;
		}
		
		if($(trs[i]).parent().parent().find("[name='jptj']").eq("0").text() == "已推荐")
		{
			showDelDialog("","","无法推荐已推荐课程！","");
			return false;
		}
		ids.push($(trs[i]).val());
	}
	
	if(ids.length>0){ 
		ajaxRequest(basePath+"/essencerecommend/course/updateEssenceRec",{'ids':ids.join(","),"isRec":1},function(data){
			if(!data.success){//如果失败
				layer.msg(data.errorMessage);
			}else{
				if(!isnull(P_courseTable)){
                    layer.msg("精品推荐成功,请到精品课程推荐管理中查看排序！");
                    //freshDelTable(P_courseTable);
                    search_menu();
				}
				layer.msg(data.errorMessage);
			}
		});
	}else{
		showDelDialog("","","请选择要推荐精品课程！","");
	}
})	



/**
 * 公开课管理显示
 * 
 */
$(".kcgl_bx").click(function(){
	$("#courseDiv").show();
	$("#courseRecDiv").hide();
	$("#courseZbRecDiv").hide();
	freshTable(_courseTable);
});

/**
 * 公开课统计显示
 * 
 */
$(".kctj_bx").click(function(){
	$("#courseDiv").hide();
	$("#courseRecDiv").show();
	$("#courseZbRecDiv").hide();
	freshTable(_courseRecTable);
});

/**
 * 直播中推荐 /  直播预告推荐 /  直播结束推荐
 */
$(".zbtj_bx").click(function(){
	$("#courseZbRecDiv").show();
	$("#courseDiv").hide();
	$("#courseRecDiv").hide();
	var liveStatus = $(this).attr("title");
	var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":8,"propertyName":"search_type","propertyValue1":"1","tempType":Integer}');
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_liveStatus","propertyValue1":"'+liveStatus+'","tempType":"Integer"}');
    searchButton(zb_courseRecTable,searchCase_P);
	//freshTable(_courseZbRecTable);
});


function searchCourseRecTable(){
	searchButton(_courseRecTable);
}

(function( $ ) {
  $.widget( "custom.combobox", {
    _create: function() {
      this.wrapper = $( "<span>" )
        .addClass( "custom-combobox" )
        .insertAfter( this.element );

      this.element.hide();
      this._createAutocomplete();
      this._createShowAllButton();
    },

    _createAutocomplete: function() {
      var selected = this.element.children( ":selected" ),
        value = selected.val() ? selected.text() : "";

      this.input = $( "<input>" )
        .appendTo( this.wrapper )
        .val( value )
        .attr( "title", "" )
        .attr( "id", "nihao" )
        .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
        .autocomplete({
          delay: 0,
          minLength: 0,
          source: $.proxy( this, "_source" )
        })
        .tooltip({
          tooltipClass: "ui-state-highlight"
        });

      this._on( this.input, {
        autocompleteselect: function( event, ui ) {
          ui.item.option.selected = true;
          this._trigger( "select", event, {
            item: ui.item.option
          });
        },

        autocompletechange: "_removeIfInvalid"
      });
    },

    _createShowAllButton: function() {
      var input = this.input,
        wasOpen = false;

      $( "<a>" )
        .attr( "tabIndex", -1 )
        .attr( "title", "Show All Items" )
        .tooltip()
        .appendTo( this.wrapper )
        .button({
          icons: {
            primary: "ui-icon-triangle-1-s"
          },
          text: false
        })
        .removeClass( "ui-corner-all" )
        .addClass( "custom-combobox-toggle ui-corner-right" )
        .mousedown(function() {
          wasOpen = input.autocomplete( "widget" ).is( ":visible" );
        })
        .click(function() {
          input.focus();

          // 如果已经可见则关闭
          if ( wasOpen ) {
            return;
          }

          // 传递空字符串作为搜索的值，显示所有的结果
          input.autocomplete( "search", "" );
        });
    },

    _source: function( request, response ) {
      var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
      response( this.element.children( "option" ).map(function() {
        var text = $( this ).text();
        if ( this.value && ( !request.term || matcher.test(text) ) )
          return {
            label: text,
            value: text,
            option: this
          };
      }) );
    },

    _removeIfInvalid: function( event, ui ) {

      // 选择一项，不执行其他动作
      if ( ui.item ) {
        return;
      }

      // 搜索一个匹配（不区分大小写）
      var value = this.input.val(),
        valueLowerCase = value.toLowerCase(),
        valid = false;
      this.element.children( "option" ).each(function() {
        if ( $( this ).text().toLowerCase() === valueLowerCase ) {
          this.selected = valid = true;
          return false;
        }
      });

      // 找到一个匹配，不执行其他动作
      if ( valid ) {
        return;
      }

      // 移除无效的值
      this.input
        .val( "" )
        .attr( "title", value + " didn't match any item" )
        .tooltip( "open" );
      this.element.val( "" );
      this._delay(function() {
        this.input.tooltip( "close" ).attr( "title", "" );
      }, 2500 );
      this.input.data( "ui-autocomplete" ).term = "";
    },

    _destroy: function() {
      this.wrapper.remove();
      this.element.show();
    }
  });
})( jQuery );


/**
 * 获取微吼主播url
 * @param obj
 */
function getWebinarUrl(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	

	ajaxRequest(basePath+"/publiccloudclass/course/getWebinarUrl",{"webinarId":row.directId},function(data){
		console.log(data);
		$("#zburl").html(data.resultObject);
		var dialog = openDialog("ShowUrlDialog","dialogShowUrlDiv","直播地址",580,350,false,"确定",function(){
			
		});
	});
};

/**
 * 创建直播间（适用于平台直播间已创建，微吼直播间id创建失败或未插入活动id）
 * @param obj
 */
function createWebinar(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	
	ajaxRequest(basePath+"/publiccloudclass/course/reCreateWebinar",{"courseId":row.id},function(data){
		if(data.success){
			freshTable(_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

function showCourseInfoDetail(obj, status) {
    debugger
    var oo = $(obj).parent().parent().parent();
    var aData;
    if (status == 1) {
        aData = _courseTable.fnGetData(oo); // get datarow
    }
    window.location.href = basePath + '/home#cloudclass/course/courseInfoDetail?id=' + aData.id;
}


function showDetailDialog(obj,status){
    var oo = $(obj).parent().parent().parent();
    var aData;
    aData = _courseTable.fnGetData(oo); // get datarow
    window.location.href=basePath+'/home#cloudclass/course/courseDetail?courseId='+aData.id;
}
