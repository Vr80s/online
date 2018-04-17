var P_courseTable;//职业课列表
var PX_courseTable;//课程排序列表
var courseForm;//添加课程表单
var updateCourseForm;//修改课程表单
var studyDayForm;//设置学习计划模板表单

var _courseRecTable;//课程推荐列表

$(function(){
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	/** 点播管理列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "课程ID", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "课程名称", "class":"center","width":"9%","sortable":false,"data": 'courseName' },
    { "title": "所属学科", "class":"center","width":"6%","sortable":false,"data": 'xMenuName' },
    { "title": "资源类型", "class":"center","width":"6%","sortable":false,"data": 'multimediaType' ,"mRender":function (data, display, row) {
    	if(data == 1){
    		return "视频";
    	}
        return "音频";
    }},
	{ "title": "是否为专辑", "class":"center","width":"6%","sortable":false,"data": 'collection' ,"mRender":function (data, display, row) {
			if(data){
				return "是";
			}
			return "否";
		}},
    { "title": "主讲人", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
    { "title": "主播", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
    { "title": "实际学习人数", "class":"center","width":"6%", "sortable":false,"data": 'actCount',"visible":true},
    { "title": "课程时长", "class":"center","width":"8%", "sortable":false,"data": 'courseLength',"visible":true,"mRender":function (data, display, row) {
        return data;
    }},
    { "title": "价格", "class":"center","width":"8%","sortable":false,"mRender":function(data,display,row){
    	data = parseInt(row.currentPrice);
    	return "<span name='coursePrice'>"+data+"</span>"
    }},
    { "title": "发布时间", "class":"center","width":"10%","sortable":false,"data": 'releaseTime'},
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return "<span name='zt'>已上架</span>";
    	}else{
    		return "<span name='zt'>未上架</span>";
    	}
    } },
    { "title": "推荐时效", "class":"center","width":"10%","sortable":false,"data": 'sortUpdateTime'},
    { "title": "推荐值", "class":"center","width":"8%","sortable":false,"data": 'recommendSort'},
    { "sortable": false,"class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status=="1"){
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
			    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="下架" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
			    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="上架" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
	    	}
	    } 
	}];
	P_courseTable = initTables("courseTable",basePath+"/cloudclass/course/list",objData,true,true,0,null,searchCase_P,function(data){
	});
	/** 点播管理列表end */


	createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
});


/**
 * 点播课课列表搜索
 */
function search_P(){
    var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
	searchButton(P_courseTable,json);
};


/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj,status){
	var oo = $(obj).parent().parent().parent();
	var row;
	if(status==1) {
		row = P_courseTable.fnGetData(oo); // get datarow
	}else{
		// row = M_courseTable.fnGetData(oo); // get datarow
	}
	ajaxRequest(basePath+"/cloudclass/course/updateStatus",{"id":row.id},function(){
		if(status==1) {
			freshTable(P_courseTable);
		}else{
			// freshTable(M_courseTable);
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
        row = P_courseTable.fnGetData(oo);
    }else{
        row = _courseRecTable.fnGetData(oo);// get datarow
    }
    $("#UpdateRecommendSort_id").val(row.id);
    var dialog = openDialog("UpdateRecommendSortDialog","dialogUpdateRecommendSortDiv","修改推荐值",350,300,true,"确定",function(){
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
                    $("#recommendTime").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                    if(key==1){
                        freshTable(P_courseTable);
                    }else{
                        freshTable(_courseRecTable);
                    }
                }else{
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};

/**
 * 设置图片
 * @param obj
 */
function updateRecImg(obj){
	var oo = $(obj).parent().parent().parent();
	var row = _courseRecTable.fnGetData(oo); // get datarow
	
	$(".clearfixUpdate").remove();
 	$("#updateRecImg").prepend("<div class=\"clearfixUpdate\">\n" +
						  "	<input type=\"file\" name=\"update_recImgPath_file\" id=\"update_recImgPath_file\" class=\"uploadImg\"/>\n" +
						  "</div>");
 	
 	createImageUpload($('.uploadImg'));//生成图片编辑器

	$("#update_id").val(row.id);
	$("#update_recImgPath").val(row.recImgPath);
	
	if(row.recImgPath != null && row.recImgPath != ""){
		reviewImageRecImg("update_recImgPath_file",row.recImgPath);
		
		$(".remove").hide();
	}

	var dialog = openDialog("updateRecImgDialog","dialogUpdateRecImgDiv","设置课程展示图",580,330,true,"确定",function(){
 		if($("#updateRecImg-form").valid()){
 			 mask();
 			 $("#updateRecImg-form").attr("action", basePath+"/cloudclass/course/updateRecImgPath");
 	            $("#updateRecImg-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                if(data.success){
 	                    $("#updateRecImgDialog").dialog("close");
 	                    layer.msg(data.errorMessage);
 	                    freshTable(_courseRecTable);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	         });
 		}
 	});
};

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImageRecImg(inputId,imgSrc){
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



function showCourseInfoDetail(obj, status) {
    debugger
    var oo = $(obj).parent().parent().parent();
    var aData, page;
    if (status == 1) {
        aData = P_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(P_courseTable);
    }
    window.location.href = basePath + '/home#cloudclass/course/courseInfoDetail?id=' + aData.id;
}