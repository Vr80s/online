var P_courseTable;//线下课列表
var M_courseTable;//微课列表
var PX_courseTable;//课程排序列表
var courseForm;//添加课程表单
var updateCourseForm;//修改课程表单
var studyDayForm;//设置学习计划模板表单

var _courseRecTable;//课程推荐列表
var _cityTable;

$(function(){
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	//debugger;
	/** 线下课列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    searchCase_P.push('{"tempMatchType":undefined,"propertyName":"type","propertyValue1":"'+$("#type").val()+'","tempType":undefined}');
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "课程ID", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "课程名称", "class":"center","width":"9%","sortable":false,"data": 'courseName' },
    { "title": "作者", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
    { "title": "讲师", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
    { "title": "所在城市", "class":"center","width":"6%", "sortable":false,"data": 'realCitys'},
    { "title": "实际学习人数", "class":"center","width":"6%", "sortable":false,"data": 'actCount',"visible":true},
    { "title": "课程时长", "class":"center","width":"8%", "sortable":false,"data": 'courseLength',"visible":false,"mRender":function (data, display, row) {
        return data+"'";
    }},
    // { "title": "咨询QQ", "class":"center","sortable":false,"data": 'qqno',"visible":false},
    // { "title": "现价格", "class":"center","sortable":false,"data": 'originalCost',"visible":false},
    { "title": "价格", "class":"center","width":"8%","sortable":false,"mRender":function(data,display,row){
    	data = row.currentPrice;
    	return "<span name='coursePrice'>"+data+"</span>"
    }},
    // { "title": "现价格", "class":"center","sortable":false,"data": 'currentPrice',"visible":false},
    // { "title": "班级数", "class":"center","sortable":false,"data": 'countGradeNum',"visible":false},
    // { "title": "默认报名人数", "class":"center","sortable":false,"data": 'learndCount',"visible":false},
    // { "title": "实际报名人数", "class":"center","sortable":false,"data": 'actCount',"visible":false},
    { "title": "是否推荐", "class":"center","width":"8%","sortable":false,"data": 'isRecommend',"mRender":function (data, display, row) {
		if(data==1){
			return "<span name='sftj'>已推荐</span>";
		}else{
			return "<span name='sftj'>未推荐</span>";
		}
	} },
	
	
	
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="<span name='zt'>已启用</span>";
    	}else{
    		return data="<span name='zt'>已禁用</span>";
    	}
    } },
    {"sortable": false,"class": "center","width":"5%","title":"排序","mRender":function (data, display, row) {
    		return '<div class="hidden-sm hidden-xs action-buttons">'+
    		'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)" name="up_PX"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
    		'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)" name="down_PX"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
	}},
    { "sortable": false,"class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status=="1"){
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                    '<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'+
	    		/*'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
	        	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a>'+*/
			    // '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '
				// '<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'
//				'<a class="blue" href="javascript:void(-1);" title="分配老师" onclick="gradeTeacherDialog(this,1)"><i class="glyphicon glyphicon-user bigger-130"></i></a>'+
//				'<a class="blue" href="javascript:void(-1);" title="资源管理" onclick="showVideoDialog(this,1);"><i class="ace-icon fa fa-cog bigger-130"></i></a>'
//				'<a class="blue" href="javascript:void(-1);" title="设置学习计划模板" onclick="setPlanTemplate(this);"><i class="ace-icon fa fa-sitemap bigger-130"></i></a></div>';
	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
	    		/*'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
	        	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a>'+*/
                    '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
                    '<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'+
                    // '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this,1)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this,1);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '
				// '<a class="blue" href="javascript:void(-1);" title="编辑详情" onclick="showDetailDialog(this,1);"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>'
//				'<a class="blue" href="javascript:void(-1);" title="分配老师" onclick="gradeTeacherDialog(this,1)"><i class="glyphicon glyphicon-user bigger-130"></i></a>'+
//				'<a class="blue" href="javascript:void(-1);" title="资源管理" onclick="showVideoDialog(this,1);"><i class="ace-icon fa fa-cog bigger-130"></i></a>'
//				'<a class="blue" href="javascript:void(-1);" title="设置学习计划模板" onclick="setPlanTemplate(this);"><i class="ace-icon fa fa-sitemap bigger-130"></i></a></div>';
	    	}
	    } 
	}];
debugger;
	P_courseTable = initTables("courseTable",basePath+"/realClass/course/list",objData,true,true,0,null,searchCase_P,function(data){
		debugger;
		var iDisplayStart = data._iDisplayStart;
		var countNum = data._iRecordsTotal;//总条数
		pageSize = data._iDisplayLength;//每页显示条数
		currentPage = iDisplayStart / pageSize +1;//页码
		/*if(currentPage == 1){//第一页的第一行隐藏向上箭头
			$("#courseTable tbody tr:first td").eq(7).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
			$("#courseTable tbody tr:last td").eq(7).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}*/
		var countPage;
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
		/*if(countPage == currentPage){//隐藏最后一条数据下移
			$("#courseTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}*/

        $("[name='up_PX']").each(function(index){
            if(index == 0){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
        $("[name='down_PX']").each(function(index){
            if(index == $("[name='down_PX']").size()-1){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
	});
	/** 线下课列表end */
	
	//TODO
	
	/** 课程推荐列表begin */
	
    var objRecData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "课程ID", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "课程名称", "class":"center","width":"9%","sortable":false,"data": 'courseName' },
    { "title": "作者", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
    { "title": "讲师", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
    { "title": "所在城市", "class":"center","width":"6%", "sortable":false,"data": 'realCitys'},
    { "title": "现价格", "class":"center","sortable":false,"data": 'currentPrice',"width":"8%"},
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="<span name='zt'>已启用</span>";
    	}else{
    		return data="<span name='zt'>已禁用</span>";
    	}
    } },
    {title: '排序', "class": "center", "width": "8%","height":"34px","data": 'sort', "sortable": false,"mRender":function(data, display, row){
    	var str;
    	if(row.status ==1){//如果是禁用
    		str='<a class="blue" name="upa_a" href="javascript:void(-1);" title="上移" onclick="upMoveRec(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="blue" href="javascript:void(-1);" name="downa_a" title="下移" onclick="downMoveRec(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}else{//如果是不禁用
    		str='<a class="gray" href="javascript:void(-1);" title="上移"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}
    	return '<div class="hidden-sm hidden-xs action-buttons">'+str;
    }},
    { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
		return '<div class="hidden-sm hidden-xs action-buttons">'+
		'<a class="blue" href="javascript:void(-1);" title="取消推荐" onclick="updateRec(this);">取消推荐</a> ';
		}
    }];
     debugger;
     
     var searchCase_P1 = new Array();
     searchCase_P1.push('{"tempMatchType":9,"propertyName":"search_onlineCourse","propertyValue1":"1","tempType":Integer}');
	
     _courseRecTable = initTables("courseRecTable",basePath+"/cloudclass/course/recList",objRecData,true,true,0,null,searchCase_P1,function(data){
	
		debugger;
		var iDisplayStart = data._iDisplayStart;
		var countNum = data._iRecordsTotal;//总条数
		pageSize = data._iDisplayLength;//每页显示条数
		currentPage = iDisplayStart / pageSize +1;//页码
		/*if(currentPage == 1){//第一页的第一行隐藏向上箭头
			$("#courseRecTable tbody tr:first td").eq(7).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
			$("#courseRecTable tbody tr:last td").eq(7).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}*/
		var countPage;
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
         $("[name='upa_a']").each(function(index){
             if(index == 0){
                 $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
             }
         });
         $("[name='downa_a']").each(function(index){
             if(index == $("[name='downa_a']").size()-1){
                 $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
             }
         });
		/*if(countPage == currentPage){//隐藏最后一条数据下移
			$("#courseRecTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}*/
		
		
	});
	/** 课程推荐列表end */
	
	
	/** 城市管理开始 */
	//TODO
	var objCityData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "城市管理ID", "class": "center","width":"5%","sortable": false,"data":"id" },
    { "title": "城市名称", "class":"center","width":"9%","sortable":false,"data": 'cityName' },
    { "title": "城市展示图", "class":"center","width":"13%","sortable":false,"data": 'icon' ,"mRender":function (data, display, row) {
    	if(data != "" && data != null){
    		return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;'/>";
    	}else{
    		return "暂无图片";    	
    	}
	}},
	//TODO
    {title: '排序', "class": "center", "width": "8%","height":"34px","data": 'sort', "sortable": false,"mRender":function(data, display, row){
    	var str;
    	if(row.status ==1 || row.isRecommend == 0){//如果是禁用
    		str='<a class="blue" name="cityUpa" href="javascript:void(-1);" title="上移" onclick="cityUpMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="blue" href="javascript:void(-1);" name="cityDowna" title="下移" onclick="cityDownMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}else{//如果是不禁用
    		str='<a class="gray" href="javascript:void(-1);" title="上移"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}
    	return '<div class="hidden-sm hidden-xs action-buttons">'+str;
    }},
	{ "title": "是否推荐", "class":"center","width":"8%","sortable":false,"data": 'isRecommend',"mRender":function (data, display, row) {
			if(data==1){
				return "<span name='sftj'>已推荐</span>";
			}else{
				return "<span name='sftj'>未推荐</span>";
			}
		} },
	{ "sortable": false,"class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
		return '<div class="hidden-sm hidden-xs action-buttons">'+
		'<a class="blue" href="javascript:void(-1);" title="设置图片" onclick="updateRecImg(this);">设置图片</a> </div>';
		}
	}];
     debugger;
    // P_courseTable = initTables("courseTable",basePath+"/realClass/course/list",objData,true,true,0,null,searchCase_P,function(data){
	_cityTable = initTables("courseCityTable",basePath+"/realClass/course/courseCityList",objCityData,true,true,0,null,searchCase_P,function(data){
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
        $("[name='cityUpa']").each(function(index){
            if(index == 0){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
        $("[name='cityDowna']").each(function(index){
            if(index == $("[name='cityDowna']").size()-1){
                $(this).css("pointer-events","none").removeClass("blue").addClass("gray");
            }
        });
		
	});
	/** 城市管理结束 */
	
	/** 表单验证START */
	studyDayForm = $("#studyDay-form").validate({
		messages: {
			totalDay : {
				required : "请填写学习计划模板天数"
			}
		}
	});
	courseForm = $("#addCourse-form").validate({
		messages: {
			courseName: {
				required:"请输入课程名称！",
				minlength:"课程名称过短，应大于2个字符！",
				maxlength:"课程名称过长，应小于20个字符！"
			},
			classTemplate: {
				required:"请输入班级名称模板！"
			},
			classRatedNum: {
				required:"请输入班级额定人数！"
			},
			menuId: {
				required:"请选择所属学科！"
			},
			courseTypeId: {
				required:"请选择课程类别！"
			},
			courseType: {
				required:"请选择授课方式！"
			},
			courseLength: {
				required:"请输入课程时长！",
				digits: "课程时长必须为整数！"
			},
			userLecturerId:{
				required:"选择教师！"
			},
			coursePwd: {
				digits: "课程密码必须为整数！"
			},
			currentPrice: {
				required:"请输入现价格！",
				number:"请输入合法的数字！",
				range:"价格范围在0.01到99999.99！"
			},
			learndCount: {
				required:"请填写默认报名人数！",
				digits: "请输入整数"
			}
		}
	});
	updateCourseForm = $("#updateCourse-form").validate({
		messages: {
			courseName: {
				required:"请输入课程名称！",
				minlength:"课程名称过短，应大于2个字符！",
				maxlength:"课程名称过长，应小于20个字符！"
			},
			classTemplate: {
				required:"请输入班级名称模板！"
			},
			classRatedNum: {
				required:"请输入班级额定人数！"
			},
			gradeQQ: {
				required:"请输入班级QQ群！"
			},
			defaultStudentCount: {
				required:"请输入班级默认报名人数！"
			},
			menuId: {
				required:"请选择所属学科！"
			},
			courseTypeId: {
				required:"请选择课程类别！"
			},
			courseType: {
				required:"请选择授课方式！"
			},
			courseLength: {
				required:"请输入课程时长！",
				digits: "课程时长必须为整数"
			},
			userLecturerId:{
				required:"选择教师！"
			},
			coursePwd: {
				digits: "课程密码必须为整数！"
			},
			originalCost: {
				required:"请输入原价格！",
				number:"请输入合法的数字！",
				range:"价格范围在0.01到99999.99！"
			},
			currentPrice: {
				required:"请输入现价格！",
				number:"请输入合法的数字！",
				range:"价格范围在0.01到99999.99！"
			},
			learndCount: {
				required:"请填写默认报名人数！",
				digits: "请输入整数",
			},
			qqno: {
				required:"请输入咨询QQ！",
				digits: "请输入合法的QQ号",
				minlength:"QQ号过短，应大于等于5个字符！"
			},
			description : {
				required : "请输入课程简介!"
			},
			descriptionHid : {
				required : "请输入课程简介!"
			},
			cloudClassroom: {
				required:"课程链接不能为空！"
			}
		}
	});
	/** 表单验证END */

	
	//新增根据一级菜单获取相应的二级菜单 暂时用不上，现在没有级联菜单
	$('#menuName').change(function(){
		var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
		$.ajax({
			type:'post',
			url:basePath+'/cloudclass/course/getSecoundMenu',
			data:{firstMenuNumber:firstMenuNumber},
			dataType:'json',
			success:function(data){
				var optionstring = "";
				for (var j = 0; j < data.length; j++) {
					optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
				}
				$("#menuNameSecond").html("<option value=''>请选择</option> "+optionstring);
			}
		}) ;
	}) ;

	//编辑根据一级菜单获取相应的二级菜单
	$('#edid_menuName').change(function () {
		var firstMenuNumber = $(this).children('option:selected').val();//这就是selected的值
		$.ajax({
			type: 'post',
			url: basePath + '/cloudclass/course/getSecoundMenu',
			data: {firstMenuNumber: firstMenuNumber},
			dataType: 'json',
			success: function (data) {
				var optionstring = "";
				for (var j = 0; j < data.length; j++) {
					optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
				}
				$("#edid_menuNameSecond").html("<option value=''>请选择</option> " + optionstring);
			}
		});
	});

	$('#search_menu').change(function () {
		var firstMenuNumber = $(this).children('option:selected').val();//这就是selected的值
		$.ajax({
			type: 'post',
			url: basePath + '/cloudclass/course/getSecoundMenu',
			data: {firstMenuNumber: firstMenuNumber},
			dataType: 'json',
			success: function (data) {
				var optionstring = "";
				for (var j = 0; j < data.length; j++) {
					optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
				}
				$("#search_scoreType").html("<option value=''>请选择</option> " + optionstring);
			}
		});
	});

	$("#updateRecImg-form").validate({
		messages: {
			icon: {
				required: "请选择图片！"
			}
		}
	});
	
	/*$("#add-originalCost").hide();
	$("#add-currentPrice").hide();*/
	createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
});



/**
 * 课程排序列表上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = P_courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/realClass/course/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(P_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 课程推荐列表上移
 * @param obj
 */
function upMoveRec(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseRecTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudclass/course/upMoveRec',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseRecTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};
/**
 * 城市列表上移
 * @param obj
 */
function cityUpMove(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = _cityTable.fnGetData(oo);
    ajaxRequest(basePath+'/cloudclass/course/cityUpMove',{"id":aData.id},function(res){
        if(res.success){
            freshTable(_cityTable);
        }else{
            layer.msg(res.errorMessage);
        }
    });
};

function showDetailDialog(obj,status){
	var oo = $(obj).parent().parent().parent();
	var aData,page;
		aData = P_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(P_courseTable);
	window.location.href=basePath+'/home#cloudclass/course/courseDetail?courseId='+aData.id;
}

function showVideoDialog(obj,status){
	debugger;
	var oo = $(obj).parent().parent().parent();
    var aData,page;
    if(status==1) {
        aData = P_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(P_courseTable);
    }else{
        aData = M_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(M_courseTable);
    }
//	window.location.href=basePath+'/home#cloudclass/course/videoRes?page='+
//	 	page+'&courseId='+aData.id+'&courseName='+encodeURIComponent(aData.courseName);
    window.location.href=basePath+'/home#cloudclass/course/videoRes?page='+
 	page+'&courseId='+aData.id+'&courseName='+encodeURIComponent(aData.courseName);
}


/**
 * 课程排序列表下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = P_courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/realClass/course/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(P_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 课程推荐列表下移
 * @param obj
 */
function downMoveRec(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = _courseRecTable.fnGetData(oo);
    ajaxRequest(basePath+'/cloudclass/course/downMoveRec',{"id":aData.id},function(res){
        if(res.success){
            freshTable(_courseRecTable);
        }else{
            layer.msg(res.errorMessage);
        }
    });
};

/**
 * 城市列表下移
 * @param obj
 */
function cityDownMove(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = _cityTable.fnGetData(oo);
    ajaxRequest(basePath+'/cloudclass/course/cityDownMove',{"id":aData.id},function(res){
        if(res.success){
            freshTable(_cityTable);
        }else{
            layer.msg(res.errorMessage);
        }
    });
};

/**
 * 添加线下课
 */
$(".add_P").click(function(){

    createImageUpload($('.uploadImg_add'));//新增弹出框的生成图片编辑器
	$("input[name='isFree']").eq(1).attr("checked","checked");

	courseForm.resetForm();
    $("#classRatedNum").attr("disabled",true);
    $("#gradeStudentSum").hide();
	$("#classQQ").hide();
	$("#gradeQQ").attr("disabled",true);
	$("#defaultStudent").hide();
	$("#defaultStudentCount").attr("disabled",true);


	$("#addCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	$("#add_serviceType").val(0);//线下课类型
	var dialog = openDialog("addCourseDialog","dialogAddCourseDiv","新增线下课",580,600,true,"确定",function(){
		$("#descriptionHid").val($("#courseDescribe").val());
        if($("#addCourse-form").valid()){
			mask();
			 $("#addCourse-form").attr("action", basePath+"/realClass/course/addCourse");
	            $("#addCourse-form").ajaxSubmit(function(data){
	            	try{
                		data = jQuery.parseJSON(jQuery(data).text());
                	}catch(e) {
                		data = data;
                	  }
                	unmask();
	                if(data.success){
                        $(".ace-file-container").remove();
	                    $("#addCourseDialog").dialog("close");
	                    layer.msg(data.errorMessage);
	                    freshTable(P_courseTable);
	                    $("html").css("overflow","auto");
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
        }
	});
});

//新增 -- 当选择付费时，显示原价格和现价格
$("#is_free").click(function(){
	$("#add-originalCost").show();
	$("#add-currentPrice").show();
	
	$('#originalCost').attr("disabled",false);
	$("#currentPrice").attr("disabled",false);
	$("#originalCost").val("");
	$("#currentPrice").val("");
	
//	$("#originalCost").addClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
//	$("#currentPrice").addClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
});
//新增 -- 当选择免费时，隐藏原价格和现价格
$("#no_free").click(function(){
	$("#add-originalCost").hide();
	$("#add-currentPrice").hide();
	
	$("#originalCost").val(1);
	$("#currentPrice").val(1);
	$('#originalCost').attr("disabled",true);
	$("#currentPrice").attr("disabled",true);
//	$("#originalCost").removeClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
//	$("#currentPrice").removeClass("col-xs-10 col-sm-12 {required:true,number:true,range:[0,999999.99]}");
//	$("#originalCost").addClass("col-xs-10 col-sm-12");
//	$("#currentPrice").addClass("col-xs-10 col-sm-12");
});

function imgSenBut(){
	$("#imgAdd").html('<input type="file" name="img" id="smallingPathFile" class="uploadImg"/>');
};
function imgSenBut1(){
	$("#imgAdd1").html('<input type="file" name="img" id="detailImgPathFile" class="uploadImg"/>');
};
function imgSenBut2(){
	$("#imgAdd2").html('<input type="file" name="img" id="bigImgPathFile" class="uploadImg"/>');
};

function edit_imgSenBut(){
	$("#edit_imgAdd").html('<input type="file" name="img" id="edit_smallingPathFile" class="uploadImg"/>');
};
function edit_imgSenBut1(){
	$("#edit_imgAdd1").html('<input type="file" name="img" id="edit_detailImgPathFile" class="uploadImg"/>');
};
function edit_imgSenBut2(){
	$("#edit_imgAdd2").html('<input type="file" name="img" id="edit_bigImgPathFile" class="uploadImg"/>');
};


/**
 * 图片上传
 */
var _this;
//图片上传统一上传到附件中心
$("#addCourse-form").on("change","#smallingPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#smallingPath").val(data.url);
		  	document.getElementById("imgAdd").focus();
		  	document.getElementById("imgAdd").blur();
		  	$(".remove").hide();
		} else {
			layer.msg(data.message);
		}
	})
});
//图片上传统一上传到附件中心
$("#addCourse-form").on("change","#detailImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			
			$("#detailImgPath").val(data.url);
			document.getElementById("imgAdd1").focus();
			document.getElementById("imgAdd1").blur();
			$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});
//图片上传统一上传到附件中心
$("#addCourse-form").on("change","#bigImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#bigImgPath").val(data.url);
		  	document.getElementById("imgAdd2").focus();
		  	document.getElementById("imgAdd2").blur();
		  	$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});


//图片上传统一上传到附件中心---- 修改
$("#updateCourse-form").on("change","#edit_smallingPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#edit_smallingPath").val(data.url);
		  	document.getElementById("edit_imgAdd").focus();
		  	document.getElementById("edit_imgAdd").blur();
		  	$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改
$("#updateCourse-form").on("change","#edit_detailImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.msg("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#edit_detailImgPath").val(data.url);
		  	document.getElementById("edit_imgAdd1").focus();
		  	document.getElementById("edit_imgAdd1").blur();
		  	$(".remove").hide();
		}else {
			layer.msg(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改
$("#updateCourse-form").on("change","#edit_bigImgPathFile",function(){
	_this = this;
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
	
		if (data.error == 0) {
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 85px; height: 85px;");
		  	$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
		  	
		  	$("#edit_bigImgPath").val(data.url);
		  	document.getElementById("edit_imgAdd2").focus();
		  	document.getElementById("edit_imgAdd2").blur();
		  	$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
// zhuwenbao-2018-0109 图片上传统一上传到附件中心-新增（线下课）
$("#addCourse-form").on("change","#smallImgPath_file",function(){
    _this = this;

    // 添加唯一class用来区分用户点击的是确定按钮还是取消按钮
    $('.ui-dialog-buttonset .ui-button-text').eq(1).addClass('add_P_cancel');

    var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
    if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
        layer.msg("图片格式错误,请重新选择.");
        this.value="";
        return;
    }
    var id = $(this).attr("id");
    ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
        if (data.error == 0) {
        	console.log('上传成功');
            $("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
            $("#"+id).parent().find(".ace-file-name img").attr("src",data.url);

            $("#smallImgPath").val(data.url);
            document.getElementById("imgAdd").focus();
            document.getElementById("imgAdd").blur();
            $(".remove").hide();
        } else {
            layer.msg(data.message);
        }
    })
});
// zhuwenbao-2018-0109 图片上传统一上传到附件中心-修改（线下课）
$("#updateCourse-form").on("change","#smallImgPathFileEdit",function(){
    _this = this;

    // 添加唯一class用来区分用户点击的是确定按钮还是取消按钮
    $('.ui-dialog-buttonset .ui-button-text').eq(1).addClass('edit_P_cancel');

    var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
    if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
        layer.msg("图片格式错误,请重新选择.");
        this.value="";
        return;
    }
    var id = $(this).attr("id");
    ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
        if (data.error == 0) {
            console.log('上传成功');
            $("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
            $("#"+id).parent().find(".ace-file-name img").attr("src",data.url);

            $("#edid_smallImgPath").val(data.url);

            document.getElementById("imgAdd").focus();
            document.getElementById("imgAdd").blur();
            $(".remove").hide();
        } else {
            layer.msg(data.message);
        }
    })
});

// 防止 新增／修改职业课 上传好图片后 直接点取消或者右上角的x后 下次再新增职业课时出现上次上传好的图
$('#dialogAddCourseDiv').on("click",".ui-dialog-titlebar-close", function () {
    $(".ace-file-container").remove();
});

$('#dialogAddCourseDiv').on("click",".add_P_cancel", function () {
    $(".ace-file-container").remove();
});

$('#dialogEditCourseDiv').on("click",".ui-dialog-titlebar-close", function () {
    $(".ace-file-container").remove();
});

$('#dialogEditCourseDiv').on("click",".edit_P_cancel", function () {
    $(".ace-file-container").remove();
});

/**
 * 线下课列表搜索
 */
function search_P(){
    var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    
    
	searchButton(P_courseTable,json);
};
//TODO
/**
 * 线下课推荐
 */
function search_rec(){
	
	var searchCase_P = new Array();
	searchCase_P.push('{"tempMatchType":9,"propertyName":"search_onlineCourse","propertyValue1":"1","tempType":Integer}');
	searchCase_P.push('{"tempMatchType":"9","propertyName":"search_city","propertyValue1":"'+$("#search_rec").val()+'","tempType":"String"}');
	searchButton(_courseRecTable,searchCase_P);
};
/**
 * 城市推荐管理
 */
function search_City(){
	var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_city","propertyValue1":"'+$("#search_city").val()+'","tempType":"String"}');
	searchButton(_cityTable,json);
};


/**
 * 查看课程信息
 * @param obj
 * @param status（1，线下课，2：微课）
 */
function previewDialog(obj,status){
	var oo = $(obj).parent().parent().parent();
	var row;
	if(status==1) {
		row = P_courseTable.fnGetData(oo); // get datarow
	}else{
		row = M_courseTable.fnGetData(oo); // get datarow
	}
	
	debugger
	//根据当前id查找对应的课程信息
    $.get(basePath+"/cloudclass/course/findCourseById",{id:row.id}, function(result){
        $("#show_gradeStudentSum").text(result[0].classRatedNum); //班级额定人数
        if(status==1) {
            $("#show_classRatedNum").hide();
			$("#classGradeQQ").hide();
        }else{
            $("#show_classRatedNum").show();
        }
    	$("#show_courseName").text(result[0].courseName); //课程名称
    	$("#show_menuName").text(result[0].xMenuName); //所属学科
    	$("#show_menuNameSecond").text(result[0].scoreTypeName); //课程类别
    	$("#show_courseType").text(result[0].teachMethodName); //授课方式
    	$("#show_courseLength").text(result[0].courseLength+"分钟"); //课程时长
    	$("#show_coursePwd").text(result[0].coursePwd); //课程时长
		$("#show_gradeQQ").text(result[0].gradeQQ); //班级QQ群
    	$("#show_qqno").text(result[0].qqno); //咨询QQ
    	$("#show_learndCount").text(result[0].defaultStudentCount); //默认报名人数
    	$("#show_actCount").text(row.actCount==null?0:row.actCount);//实际报名人数
    	$("#show_sum").text(result[0].defaultStudentCount*1+row.actCount*1);//累计报名人数
    	
    	$("#role_type1_show").html(row.lecturerName);//讲师
//    	$("#role_type1_show").html(row.role_type1);//讲师
//    	$("#role_type2_show").html(row.role_type2);//班主任
//    	$("#role_type3_show").html(row.role_type3);//助教
//    	
//    	$("#show_description").text(result[0].description); //包含班级
    	$("#show_createTime").text(result[0].createTime); //创建时间
        $("#show_startTime").text(result[0].startTime); //创建时间
        $("#show_endTime").text(result[0].endTime); //创建时间
    	if(result[0].status == 1){
    		$("#show_status").text("启用"); //状态
    	}else{
    		$("#show_status").text("禁用"); //状态
    	}
    	if(result[0].isFree == true){ //免费 true
    		$("#show_no_free").attr('checked',true);
    		$("#show-originalCost").hide();
    		$("#show-currentPrice").hide();
    	}else if(result[0].isFree == false){
    		$("#show_is_free").attr('checked',true);
    		$("#show-originalCost").show();
    		$("#show-currentPrice").show();
    	}
    	
    	$("#show_originalCost").text(result[0].originalCost); //原价格
    	$("#show_currentPrice").text(result[0].currentPrice); //现价格
    	
    	$("#show_address").text(result[0].address); //现价格
    	$("#show_courseDescribe").text(result[0].description); //课程简介
    	$("#show_cloudClassroom").text(result[0].cloudClassroom); //云课堂连接
    	
    	for(i=0;i<$("#view_mapList option").length;i++){
    		if($("#view_mapList option").eq(i).val()==result[0].userLecturerId){
    			
    			$("#show_userLecturerId").text($("#view_mapList option").eq(i).text());
    		}
         }
    });
	var prev_title="查看课程";
	if(status ==1){
		prev_title="查看线下课";
	}else{
		prev_title="查看微课程";
	}
	var dialog = openDialogNoBtnName("showCourseDialog","showCourseDiv",prev_title,530,600,false,"确定",null);
};


/**
 * 修改表单
 * @param obj
 * @param status（1：线下课，2：微课）
 */
function toEdit(obj,status){
    debugger

    createImageUpload($('.uploadImg_edit'));//'修改'弹出框的生成图片编辑器

	updateCourseForm.resetForm();

    // 防止第二次点击编辑的时候 省份没有显示出来
    $('#edit_province').find("option:selected").attr("selected",false);

	var oo = $(obj).parent().parent().parent();
	var row;
	if(status==1) {
		row = P_courseTable.fnGetData(oo); // get datarow
        $("#edid_gradeStudentSum").attr("disabled",false);
        $("#edid_classRatedNum").hide();
		$("#edid_gradeQQ").attr("disabled",true);
		$("#edid_classQQ").hide();
		$("#edid_defaultStudentCount").attr("disabled",false);
		$("#edid_classDefaultStudent").hide();
	}else{
		row = M_courseTable.fnGetData(oo); // get datarow
        $("#edid_gradeStudentSum").attr("disabled",false);
        $("#edid_classRatedNum").show();
		$("#edid_gradeQQ").attr("disabled",false);
		$("#edid_classQQ").show();
		$("#edid_defaultStudentCount").attr("disabled",false);
		$("#edid_classDefaultStudent").show();
	}
	$("#updateCourse-form").resetForm();
	$("#updateCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	//根据当前id查找对应的课程信息
    $.get(basePath+"/realClass/course/findCourseById",{id:row.id}, function(result){
    	$("#editCourse_id").val(row.id);
    	//选中一级菜单select
    	debugger;
    	//所属学科
    	for(i=0;i<$("#edid_menuName option").length;i++){
    		if($("#edid_menuName option").eq(i).text()==result[0].xMenuName){
    			$("#edid_menuName option").eq(i).attr("select","selected"); 
    			$("#edid_menuName").val($("#edid_menuName option").eq(i).val());
    		}
    	}
    	 $.ajax({  
			          type:'post',      
			          url:basePath+'/realClass/course/getSecoundMenu',  
			          data:{firstMenuNumber:row.menuId},  
			          dataType:'json',  
			          success:function(data){  
		                     
		                        var optionstring = "";  
		                        for (var j = 0; j < data.length; j++) {  
		                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";  
		                        }  
		                        $("#edid_menuNameSecond").html("<option value=''>请选择</option> "+optionstring); 
		                        
		                    	//课程类别
		                    	for(i=0;i<$("#edid_menuNameSecond option").length;i++){
		                    		
		                    		if($("#edid_menuNameSecond option").eq(i).text()==result[0].scoreTypeName){
		                    			$("#edid_menuNameSecond option").eq(i).attr("select","selected"); 
		                    			$("#edid_menuNameSecond").val($("#edid_menuNameSecond option").eq(i).val());
		                    		}
		                    	}
			          }  
			      }) ; 
    	
    
//    	 debugger;
    	//授课方式
    	for(i=0;i<$("#edid_addCourseType option").length;i++){
    		
    		if($("#edid_addCourseType option").eq(i).text()==result[0].teachMethodName){
    			$("#edid_addCourseType option").eq(i).attr("select","selected"); 
    			$("#edid_addCourseType").val($("#edid_addCourseType option").eq(i).val());
    		}
    	}
//    	debugger;
    	for(i=0;i<$("#edid_multimediaType option").length;i++){
    		
    		if($("#edid_multimediaType option").eq(i).val()==result[0].multimediaType){
    			$("#edid_multimediaType option").eq(i).attr("select","selected"); 
    			$("#edid_multimediaType").val($("#edid_multimediaType option").eq(i).val());
    		}
    	}
    	
/*    	<select id="edit_province" name="edit_province" onchange="doProvAndCityRelation();" 
            class="clearfix col-xs-10 col-sm-12 {required:true}" >
			<option id="edit_choosePro"value="-1">请选择您所在省份</option>
   </select>
    <select id="edit_citys" name="edit_city" class="clearfix col-xs-10 col-sm-12 {required:true}">
			<option id='edit_chooseCity' value='-1'>请选择您所在城市</option>
	　　</select>*/
    	
    	
    	//省市区
    	var address = result[0].address;
    	var p_c_a = address.split("-");
    	if(p_c_a.length==3){
    		//省
    		for(i=0;i<$("#edit_province option").length;i++){
        		if($("#edit_province option").eq(i).text()==p_c_a[0]){
        			$("#edit_province option").eq(i).attr("selected",true);
        			break;
        		}
        	}
    		$("#edit_citys").empty();
    		$("#edit_county").empty();
    		
    		var city = "<option id='10086'>"+p_c_a[1]+"</option>";
    		$("#edit_citys").append(city);
    		
    		var countysDetails = p_c_a[2].split(" ");
    		
    		var county = "<option id='10089'>"+countysDetails[0]+"</option>";
    		$("#edit_county").append(county);
    		
    		
    		$("#edit_realProvince").val(p_c_a[0]);
    		$("#edit_realCitys").val(p_c_a[1]);
    		$("#edit_realCounty").val(p_c_a[2]);
    		
    		//授课地点
    		$("#edit_address").val(countysDetails[1]);
    	}

		// zhuwenbao-2018-0109
        reviewImage("edid_smallImgPath", result[0].smallimgPath);// 照片回显
        $('#edid_smallImgPath').val(result[0].smallimgPath);

    	$("#edid_courseName").val(result[0].courseName); //课程名称
    	$("#edid_classTemplate").val(result[0].classTemplate); //班级名称模板
    	$("#edid_courseLength").val(result[0].courseLength); //课程时长
    	$("#edit_userLecturerId").val(result[0].userLecturerId); //作者
    	$("#edid_coursePwd").val(result[0].coursePwd); //课程时长
    	$("#edid_qqno").val(result[0].qqno); //咨询QQ
		$("#edid_gradeQQ").val(result[0].gradeQQ); //班级QQ群
		$("#edid_defaultStudentCount").val(result[0].defaultStudentCount); //默认报名人数

		debugger
	    //回显课程讲师
		for(i=0;i<$("#combobox1 option").length;i++){
			console.log($("#combobox1 option").eq(i).val()+"==="+result[0].userLecturerId+"==="+$("#combobox1 option").eq(i).text());
			if($("#combobox1 option").eq(i).val()==result[0].userLecturerId){
				$("#combobox1 option").eq(i).attr("select","selected"); 
				$("#combobox1").val($("#combobox1 option").eq(i).val());
				$("#updateCourse-form #nihao").val($("#combobox1 option").eq(i).text());//yuruixin -20170820
			}
		}
    	$("#edid_originalCost").val(result[0].originalCost); //原价格
    	$("#edid_currentPrice").val(result[0].currentPrice); //现价格
		debugger
    	$("#subtitle_edit").val(result[0].subtitle); //副标题
    	$("#courseLength_edit").val(result[0].courseLength); //时长
    	$("#lecturer_edit").val(result[0].lecturer); //主播
    	$("#edid_courseDescribe").val(result[0].description); //课程简介
    	$("#edid_cloudClassroom").val(result[0].cloudClassroom); //课程简介
    	$("#edit_learndCount").val(result[0].learndCount); //课程简介
        $("#edid_gradeStudentSum").val(result[0].classRatedNum);//班级额定人数
//        debugger;
        $("#edit_startTime").val(result[0].startTime);//开始时间
        $("#edit_endTime").val(result[0].endTime);//结束时间
      
    	
    	var edit_title="修改课程";
    	if(status ==1){
			edit_title="修改线下课";
		}else{
			edit_title="修改微课程";
		}
    	var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv",edit_title,580,650,true,"确定",function(){
    		debugger
    		$("#edid_descriptionHid").val($("#edid_courseDescribe").val());
    		if($("#updateCourse-form").valid()){
                mask();
                $("#updateCourse-form").attr("action", basePath+"/realClass/course/updateCourseById");
                $("#updateCourse-form").ajaxSubmit(function(data){
                	try{
                		data = jQuery.parseJSON(jQuery(data).text());
                	}catch(e) {
                		data = data;
                	  }
                
                    unmask();
                    if(data.success){
                    	
                        $("#EditCourseDialog").dialog("close");

                        // 删除这个元素 是因为课程展示图回显后 然后更新课程 没有将回显的div删除 造成弹出框呈现多张课程展示图
                        $(".ace-file-container").remove();
                        // 防止第二次点击编辑的时候 省份没有显示出来
                        $('#edit_province').find("option:selected").attr("selected",false);

                        layer.msg(data.errorMessage);
                        if(edit_title=='修改线下课'){
                        	freshTable(P_courseTable);	
                        }else{
                        	freshTable(M_courseTable);
                        }
                         
                    }else{
                    	 layer.msg(data.errorMessage);
                    }
                });
            }
	    });
    });
    
};

//修改 -- 当选择付费时，显示原价格和现价格
$("#edit_is_free").click(function(){ 
	$("#edit-originalCost").show();
	$("#edit-currentPrice").show();
	$("#edid_originalCost").val("");
	$("#edid_currentPrice").val("");
//	$("#edid_originalCost").addClass("col-xs-10 col-sm-12 {required:true,rangelength:[2,20]}");
//	$("#edid_currentPrice").addClass("col-xs-10 col-sm-12 {required:true,rangelength:[2,20]}");
});
//修改 -- 当选择免费时，隐藏原价格和现价格
$("#edit_no_free").click(function(){
	$("#edit-originalCost").hide();
	$("#edit-currentPrice").hide();
	$("#edid_originalCost").val(0);
	$("#edid_currentPrice").val(0);
//	$("#edid_originalCost").removeClass("col-xs-10 col-sm-12 {required:true,rangelength:[2,20]}");
//	$("#edid_currentPrice").removeClass("col-xs-10 col-sm-12 {required:true,rangelength:[2,20]}");
//	$("#edid_originalCost").addClass("col-xs-10 col-sm-12");
//	$("#edid_currentPrice").addClass("col-xs-10 col-sm-12");
});

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
		row = M_courseTable.fnGetData(oo); // get datarow
	}
	ajaxRequest(basePath+"/realClass/course/updateStatus",{"id":row.id},function(){
		if(status==1) {
			freshTable(P_courseTable);
		}else{
			freshTable(M_courseTable);
		}
	});
};

/**
 * 状态修改
 * @param obj
 */
function updateRec(obj){
	var oo = $(obj).parent().parent().parent();
	var row = _courseRecTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/cloudclass/course/updateRec",{"ids":row.id,"isRec":0},function(data){
		if(data.success){
			layer.msg("取消成功！");
			freshTable(_courseRecTable);
		}else{
			layer.msg("取消失败！");
		}
	});
};

/**
 * 设置图片
 * @param obj
 */
function updateRecImg(obj){
	debugger; //TODO
	var oo = $(obj).parent().parent().parent();
	var row = _cityTable.fnGetData(oo); // get datarow
	
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
 			 $("#updateRecImg-form").attr("action", basePath+"/realClass/course/courseCityUpdate");
 	            $("#updateRecImg-form").ajaxSubmit(function(data){
 	            	try{
                 		data = jQuery.parseJSON(jQuery(data).text());
                 	}catch(e) {
                 		data = data;
                 	}
 	                unmask();
 	                debugger;
 	                if(data.success){
 	                    $("#updateRecImgDialog").dialog("close");
 	                    layer.msg("修改成功");
 	                    freshTable(_cityTable);
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

//图片上传统一上传到附件中心---- 修改  列表页
$("#updateRecImg-form").on("change","#update_recImgPath_file",function(){
 	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
 	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
 		layer.alert("图片格式错误,请重新选择.");
 		this.value="";
 		return;
 	}
 	var id = $(this).attr("id");
 	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
 		if (data.error == 0) {
 			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
 			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 252px; height: 97px;");
 			
 			$("#update_recImgPath").val(data.url);
 			document.getElementById("update_recImgPath_file").focus();
 			document.getElementById("update_recImgPath_file").blur();
 			$(".remove").hide();
 		}else {
 			alert(data.message);
 		}
 	})
 });

/**
 * 逻辑删除
 * @param obj
 */
function delDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var row = P_courseTable.fnGetData(oo); // get datarow
	showDelDialog(function(){
		mask();
		ajaxRequest(basePath+"/cloudclass/course/deleteCourseById",{"id":row.id},function(data){
			if(!data.success){
				//alertInfo(data.errorMessage);
				layer.msg(data.errorMessage);
			}else{
					layer.msg(data.resultObject);
					freshDelTable(P_courseTable);
			}
			unmask();
			
		});
	});
	
}
/**
 * 线下课批量逻辑删除
 * 
 */

$(".dele_P").click(function(){
	deleteAll(basePath+"/cloudclass/course/deletes",P_courseTable,null,"删除操作不可逆，对于已报名用户不会产生影响，是否确认删除该课程？");
});
/**
 * 微课批量逻辑删除
 *
 */

$(".dele_M").click(function(){
    deleteAll(basePath+"/cloudclass/course/deletes",M_courseTable);
});

/**
 * 线下课批量推荐
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
	if(ids.length > 4)
	{
		showDelDialog("","","最多只能推荐4个点播课程！","");
		return false;
	}

	if(ids.length>0){ 
			ajaxRequest(basePath+"/cloudclass/course/updateRec",{'ids':ids.join(","),"isRec":1},function(data){
				if(!data.success){//如果失败
					//alertInfo(data.errorMessage);
					layer.msg(data.errorMessage);
				}else{
					if(!isnull(P_courseTable)){
                        layer.msg("推荐成功！");
                        //freshDelTable(P_courseTable);
						search_P();
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
			showDelDialog("","","无法推荐已推荐精品课程！","");
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
 * 城市批量推荐
 *
 */
$(".city_rec").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){

        if($(trs[i]).parent().parent().find("[name='sftj']").eq("0").text() == "已推荐")
        {
            showDelDialog("","","无法推荐已推荐城市！","");
            return false;
        }
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/cloudclass/course/updateCityRec",{'ids':ids.join(","),"isRec":1},function(data){
            if(!data.success){//如果失败
                //alertInfo(data.errorMessage);
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(_cityTable)){
                    layer.msg("推荐成功！");
                    search_City();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择推荐城市！","");
    }
});
/**
 * 城市取消推荐
 *
 */
$(".city_qx_rec").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){

        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/cloudclass/course/updateCityRec",{'ids':ids.join(","),"isRec":0},function(data){
            if(!data.success){//如果失败
                //alertInfo(data.errorMessage);
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(_cityTable)){
                    layer.msg("取消成功！");
                    search_City();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择未推荐城市！","");
    }
});
/**
 * 微课批量推荐
 *
 */
$(".rec_M").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");

    for(var i = 0;i<trs.size();i++){
//        if($(trs[i]).parent().parent().find("[name='skfs']").eq("0").text() != "点播")
//        {
//            showDelDialog("","","目前只能推荐点播课程！","");
//            return false;
//        }

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
    if(ids.length > 4)
    {
        showDelDialog("","","最多只能推荐4个点播课程！","");
        return false;
    }

    if(ids.length>0){
        ajaxRequest(basePath+"/cloudclass/course/updateRec",{'ids':ids.join(","),"isRec":1},function(data){
            if(!data.success){//如果失败
                //alertInfo(data.errorMessage);
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(M_courseTable)){
                    layer.msg("推荐成功！");
                    //freshDelTable(M_courseTable);
                    search_M();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择推荐课程！","");
    }
});

/**
 * 展示线下课管理
 * 
 */
$(".xxpxb_bx").click(function(){
	$("#courseDiv").show();
	$("#courseRecDiv").hide();
	$("#courseCityDiv").hide();
	freshTable(P_courseTable);
});
/**
 *>线下课推荐管理
 *
 */
//TODO
$(".xxtj_bx").click(function(){
	$("#courseDiv").hide();
	$("#courseRecDiv").show();
	$("#courseCityDiv").hide();
	freshTable(_courseRecTable);
});
/**
 * 城市推荐管理
 */
$(".city_bx").click(function(){
	$("#courseDiv").hide();
	$("#courseRecDiv").hide();
	$("#courseCityDiv").show();
	
	/*
	 * 点击这个地方时，就要把对应的城市刷新下
	 */
	
	
	freshTable(_cityTable);
});


function test(){
	$(".libox").css("display","block");
}

function gradeTeacherDialog(obj,flag){
	var oo = $(obj).parent().parent().parent();
	if(flag==1) {
		row = P_courseTable.fnGetData(oo); // get datarow
	}else{
		row = M_courseTable.fnGetData(oo); // get datarow
	}
	
    $(".teacher .allTeacher").empty();
    $(".banzhuren .allTeacher").empty();
    $(".zhujiao .allTeacher").empty();
    $("#jiangshiLi").empty();
    $("#banzhurenLi").empty();
    $("#zhujiaoLi").empty();
    //根据当前id查找对应的班级信息
    $.get(basePath+"/cloudclass/course/teachers",{courseId:row.id}, function(result){
        $("#teachers_course_id").val(row.id);
        $("#teachers_courseName").html(row.xMenuName);
        
        
        if(result.resultObject.roleType1!=null&&result.resultObject.roleType1.length>0){
            for(var i=0;i<result.resultObject.roleType1.length;i++)
            {
                if(result.resultObject.roleType1[i]!=null&&result.resultObject.roleType1[i].status==1)
                {
                	$(".teacher .allTeacher").append("<div><span data-jiangshiid='"+result.resultObject.roleType1[i].id+"' class='tName'>"+result.resultObject.roleType1[i].name+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
                	deleteTeacher();
                }
            	
            	$("#jiangshiLi").append("<li data-jiangshiid="+result.resultObject.roleType1[i].id+">"+result.resultObject.roleType1[i].name+"</li>");
            }
            for(var i=0;i<result.resultObject.roleType2.length;i++)
            {
                if(result.resultObject.roleType2[i]!=null&&result.resultObject.roleType2[i].status==1)
                {
                    $(".banzhuren .allTeacher").append("<div><span data-banzhurenid='"+result.resultObject.roleType2[i].id+"' class='tName'>"+result.resultObject.roleType2[i].name+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
                    deleteTeacher();
                }
            	
            	$("#banzhurenLi").append("<li data-banzhurenid="+result.resultObject.roleType2[i].id+">"+result.resultObject.roleType2[i].name+"</li>");
            }
            for(var i=0;i<result.resultObject.roleType3.length;i++)
            {
                if(result.resultObject.roleType3[i]!=null&&result.resultObject.roleType3[i].status==1)
                {
                     $(".zhujiao .allTeacher").append("<div><span data-zhujiaoid='"+result.resultObject.roleType3[i].id+"' class='tName'>"+result.resultObject.roleType3[i].name+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
                     deleteTeacher();
                }
            	
            	$("#zhujiaoLi").append("<li data-zhujiaoid="+result.resultObject.roleType3[i].id+">"+result.resultObject.roleType3[i].name+"</li>");
            }
        }

        openDialog("gradeTeacherDialog","dialogTeacherGradeDiv","分配老师",550,600,true,"提交",function(){
        	    var teachersId=[];
        	    var zhujiaoId=[];
        	    var banzhurenId=[];
        	  
        	    $(".teacher").find("span").each(function(){
        	    	teachersId.push($(this).attr("data-jiangshiid"));
        	    })
        	    $(".zhujiao").find("span").each(function(){
        	    	 zhujiaoId.push($(this).attr("data-zhujiaoid"));
        	    })
        	    $(".banzhuren").find("span").each(function(){
        	    	 banzhurenId.push($(this).attr("data-banzhurenid"));
        	    })
        		$("#jiangshiInput").val(teachersId);
        	    $("#banzhurenInput").val(banzhurenId);
        	    $("#zhujiaoInput").val(zhujiaoId);
        	  
            $("#grade-teacher-form").attr("action",basePath+"/cloudclass/course/teachers/save");
            if($("#grade-teacher-form").valid()){
            	 $("#grade-teacher-form").ajaxSubmit(function(data){
                     if(data.success){
                    	 layer.msg("老师分配成功！");
                         $("#gradeTeacherDialog").dialog("close");
                         if(flag==1) {
                        	 freshTable(P_courseTable);
                     	}else{
                     		 freshTable(M_courseTable);
                     	}
                        
                     }else{
                    	 alertInfo("老师分配失败！");
                         //layer.msg(data.errorMessage);
                     }
                 });
            }
           
        });
    });
}

//分配老师部分
//三角切换
function sanjiaoChangeStatus(target2){
  $(target2).find("span").toggleClass("caret rotateCaret");
}
//搜索
$(".fenpeiTeacherImg").on("click",function(){
  var searchValue=$(".fenpeiTeacherSearch").val();
  $(".list-items2 li").each(function(){
      var currentValue=$(this).text();
      if(currentValue===searchValue){
          $(this).parentsUntil(".xuekeleibei").css("display","block");
          $(this).addClass("select").siblings().removeClass("select");
          $(this).parent().prev(".tagname").find("span").removeClass("rotateCaret").addClass("caret");
          $(this).parent().parent().parent().prev(".tagname").find("span").removeClass("rotateCaret").addClass("caret");
      }
  })
})
$(".fenpeiTeacherSearch").on("keyup",function(event){
  if(event.which==13){
      $(".fenpeiTeacherImg").trigger("click");
  }
})

$(".fenpeiTearcherClose").on("click",function(){
  $("#fenpeiTeacher").css("display","none");
  $(".zhezhao").css("display","none");
})
$(".xuekeleibei").on("click",function(event){
  event.stopPropagation();
  var target=event.target;
//  console.log(target);
  if($(target).is("span")){
      $(target).parent().parent().find(".list-items1").toggle();
      $(target).prev().toggleClass("caret rotateCaret");
  }
})
$("#xuekeleibeiId").unbind().click(function(){
  	$(this).toggleClass("caret rotateCaret");
  	$(this).parent().next().toggle();
  })

$("#jiaoshiSanjiao").unbind().click(function(){
	$(this).toggleClass("caret rotateCaret");
	$(this).parent().next().toggle();
})

$("#zhujiaoSanjiao").unbind().click(function(){
	$(this).toggleClass("caret rotateCaret");
	$(this).parent().next().toggle();
})

$("#banzhurenSanjiao").unbind().click(function(){
	$(this).toggleClass("caret rotateCaret");
	$(this).parent().next().toggle();
})
$(".list-items1").on("click",function(event){
  event.stopPropagation();
  var target2=event.target;
  if($(target2).is("span")){
      $(target2).parent().find(".list-items2").toggle();
      sanjiaoChangeStatus(target2);
  }
})
$(".list-items2").on("click",function(event){
  event.stopPropagation();
  var target3=event.target;
  var parentValue;
  parentValue=$(target3).parent().prev().text();
  if($(target3).is("li")){
      var teacherName=$(target3).text();
      var jiangshiId=$(target3).attr("data-jiangshiid");
      var zhujiaoId=$(target3).attr("data-zhujiaoid");
      var banzhurenId=$(target3).attr("data-banzhurenid");
      $(target3).addClass("select").siblings().removeClass("select");
      if(parentValue==="讲师"){
          $(".row-first").attr({"data-teacherName":teacherName,"data-jiangshiid":jiangshiId});
      }
      if(parentValue==="助教"){
          $(".row-second").attr({"data-teacherName":teacherName,"data-zhujiaoid":zhujiaoId});
      }
      if(parentValue==="班主任"){
          $(".row-third").attr({"data-teacherName":teacherName,"data-banzhurenid":banzhurenId});
      }
  }
})
$(".fenpeirow").on("click",function(event){
  event.stopPropagation();
  var target4=event.target;
  var rowValue=$(target4).attr("data-teacherName");
  var jiangshiId=$(target4).attr("data-jiangshiid");
  var zhujiaoId=$(target4).attr("data-zhujiaoid");
  var banzhurenId=$(target4).attr("data-banzhurenid");
  var fenpeiTeacherFlag=false;
  if($(target4).hasClass("row-first")){
      if($(".teacher .allTeacher .tName").length==0){
          fenpeiTeacherFlag=true;
      }
      $(".teacher .allTeacher .tName").each(function(){
          if($(this).text()!=rowValue){
              fenpeiTeacherFlag=true;
          }else{
              fenpeiTeacherFlag=false;
              return false;
          }
      })
      if(fenpeiTeacherFlag){
          if($(target4).has("data-teacherName") && rowValue!=undefined){
              $(".teacher .allTeacher").append("<div><span data-jiangshiid='"+jiangshiId+"' class='tName'>"+rowValue+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
              $(target4).attr("data-teacherName","");
              deleteTeacher()
          }else{
              return false;
          }
      }

  }else  if($(target4).hasClass("row-second")){
      $(".zhujiao .allTeacher .tName").each(function(){
          if($(this).text()!=rowValue){
              fenpeiTeacherFlag=true;
          }else{
              fenpeiTeacherFlag=false;
              return false;
          }
      })
      if($(".zhujiao .allTeacher .tName").length==0){
          fenpeiTeacherFlag=true;
      }
      if(fenpeiTeacherFlag){
          if($(target4).has("data-teacherName") && rowValue!=undefined){
              $(".zhujiao .allTeacher").append("<div><span data-zhujiaoid='"+zhujiaoId+"' class='tName'>"+rowValue+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
              $(target4).attr("data-teacherName","");
              deleteTeacher();
          }else{
              return false;
          }
      }

  }else if($(target4).hasClass("row-third")){
      $(".banzhuren .allTeacher .tName").each(function(){
          if($(this).text()!=rowValue){
              fenpeiTeacherFlag=true;
          }else{
              fenpeiTeacherFlag=false;
              return false;
          }
      })
      if($(".banzhuren .allTeacher .tName").length==0){
          fenpeiTeacherFlag=true;
      }
      if(fenpeiTeacherFlag){
          if($(target4).has("data-teacherName") && rowValue!=undefined){
              $(".banzhuren .allTeacher").append("<div><span data-banzhurenid='"+banzhurenId+"' class='tName'>"+rowValue+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
              $(target4).attr("data-teacherName","");
              deleteTeacher();
          }else{
              return false;
          }
      }

  }
})
//清空
$(".clear").unbind().on("click",function(){
  $(this).parent().next(".allTeacher").html("");
})
function deleteTeacher(){
  $(".allTeacherClose").on("click",function(){
      $(this).parent().remove();
  })
}


(function() {
	initTeacher();
})();
function initTeacher(){
	$=jQuery;
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

}


function showCourseInfoDetail(obj, status) {
    debugger
    var oo = $(obj).parent().parent().parent();
    var aData;
    if (status == 1) {
        aData = P_courseTable.fnGetData(oo); // get datarow
    }
    window.location.href = basePath + '/home#cloudclass/course/courseInfoDetail?id=' + aData.id;
}



