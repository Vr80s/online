var P_courseTable;//医师列表
var PX_courseTable;//课程排序列表
var courseForm;//添加课程表单
var updateCourseForm;//修改课程表单

var _courseRecTable;//课程推荐列表

$(function(){
	/** 医师列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
    searchCase_P.push('{"tempMatchType":undefined,"propertyName":"type","propertyValue1":"'+$("#type").val()+'","tempType":undefined}');
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
	{ "title": "用户名", "class":"center","width":"8%","sortable":false,"data": 'loginName',"mRender":function (data) {
		return data == null ? "未绑定":data;
	}},
    { "title": "用户昵称", "class":"center","width":"8%","sortable":false,"data": 'name'},
    { "title": "性别", "class":"center","width":"8%","sortable":false,"data": 'sex',"mRender":function (data) {
        if(data==1)return "男";
        return "女";
    }},
    { "title": "生日", "class":"center","width":"6%", "sortable":false,"data": 'birthday',"visible":true},
    { "title": "测评结果", "class":"center","width":"6%", "sortable":false,"data": 'result',"visible":true,'mRender':function(data){
        if(data==null)return "无症状";
    	return data;
    	}
    },
	{ "title": "测评日期", "class":"center","width":"8%","sortable":false,"data": 'createTime','mRender':function(data){
                return getLocalTime(data);}
	},
	{ "sortable": false,"class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
				return '<div class="hidden-sm hidden-xs action-buttons">'+
					'<a class="blue" href="javascript:void(-1);" title="查看答题" onclick="toDetails(this)">查看答题</a>'+
					'</div> ';
	}
		}
	];
	
	P_courseTable = initTables("courseTable",basePath+"/medical/healthy/list",objData,true,true,0,null,searchCase_P,function(data){
		// ;
		// var iDisplayStart = data._iDisplayStart;
		// var countNum = data._iRecordsTotal;//总条数
		// pageSize = data._iDisplayLength;//每页显示条数
		// currentPage = iDisplayStart / pageSize +1;//页码
		// if(currentPage == 1){//第一页的第一行隐藏向上箭头
		// 	$("#courseTable tbody tr:first td").eq(7).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		// }
		// if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
		// 	$("#courseTable tbody tr:last td").eq(7).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		// }
		// var countPage;
		// if(countNum%pageSize == 0){
		// 	countPage = parseInt(countNum/pageSize);
		// }else{
		// 	countPage = parseInt(countNum/pageSize) + 1;
		// }
		// if(countPage == currentPage){//隐藏最后一条数据下移
		// 	$("#courseTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		// }
	});
	/** 医师列表end */

    $(".kctj_bx").click(function(){

        freshTable(PX_courseTable1());
    });

    /** 医馆排序列表end */

	//TODO
	
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
			recImgPath: {
				required: "请选择图片！"
			}
		}
	});
	
	createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
});



/**
 * 医师列表搜索
 */
function search_P(){
    var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
	searchButton(P_courseTable,json);
};
/**
 * 课程排序列表搜索
 */
function search_PX(){
	var json = new Array();
	$("#searchDiv_PX .searchTr").each(function() {
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
	var str = "[" + json.join(",") + "]";
	PX_courseTable.fnFilter(str);
};


/**
 * 查看课程信息
 * @param obj
 * @param status（1，医师，2：微课）
 */
function previewDialog(obj,status){
	// var oo = $(obj).parent().parent().parent();
	// var row;
	// if(status==1) {
	// 	row = P_courseTable.fnGetData(oo); // get datarow
	// }else{
	// 	row = M_courseTable.fnGetData(oo); // get datarow
	// }
	//
	//
	// //根据当前id查找对应的课程信息
    // $.get(basePath+"/medical/doctor/findMedicalDoctorById",{id:row.id}, function(result){
    //
    	// $("#show_name").text(result.name);
    	// $("#show_title").text(result.title);
    	// $("#show_tel").text(result.tel);
    	// $("#show_type").text(doctorType(result.type));
    	// $("#show_province").text(result.province);
    	// $("#show_city").text(result.city);
    	// // $("#show_detailedAddress").text(result.detailedAddress);
    	// $("#show_description").html(result.description); //课程简介
    	// $("#show_workTime").text(result.workTime);
    //
     //    $("#show_description").removeClass("form-control");
    //
    // });
	// var prev_title="查看医师";
	// var dialog = openDialogNoBtnName("showCourseDialog","showCourseDiv",prev_title,530,600,false,"确定",null);

    var oo = $(obj).parent().parent().parent();
    var aData,page;
    if (status == 1) {
        aData = P_courseTable.fnGetData(oo); // get datarow
        page = getCurrentPageNo(P_courseTable);
    }
    var str ="";
    if(aData.authenticationInformationId!=null && aData.authenticationInformationId!="" && aData.authenticationInformationId!=undefined){
        str += "&mdaiId="+aData.authenticationInformationId;
    }else{
        str += "&mdaiId=";
    }
    window.location.href = basePath + '/home#medical/doctor/info/' + aData.id+"?page="+page+"&doctorId="+aData.id+str;

};

function getLocalTime(nS) {
	if(nS == null || nS == ""){
		return "";
	}
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}

function toDetails(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = P_courseTable.fnGetData(oo); // get datarow
    window.location.href = basePath + '/home#medical/healthy/info/' + aData.id+"/"+aData.sex;
}