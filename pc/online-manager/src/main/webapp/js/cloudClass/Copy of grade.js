var gradeTable;
var realGradeTable;
var gradeMicroTable;
var gradeForm;
var gradeteacherform;
var searchJson = new Array();
var courseArray=new Array();
var tempStartTime;
var tempHasPlan;
$(function() {
    loadGradeList();
    //下线时间 时间控件
    createDatePicker($("#sstart_time"));
    createDatePicker($("#sstop_time"));
    
   /* createDatetimePicker2($("#curriculum_time"),"yy-mm-dd","HH:mm:ss");
    createDatetimePicker2($("#stop_time"),"yy-mm-dd","HH:mm:ss");*/
    
    $('#curriculum_time').datetimepicker({
    	
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#curriculum_time" );
    		var $endDate = $('#stop_time');
    		var endDate = $endDate.datepicker( 'getDate' );
    		if(endDate < startDate){
    			$endDate.datetimepicker('setDate', startDate - 3600*1*24*60*60*60);
    		}
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });
    $('#stop_time').datetimepicker({
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
    	onSelect: function( endDate ) {
    		var $startDate = $( "#curriculum_time" );
    		var $endDate = $('#stop_time');
    		var startDate = $startDate.datepicker( "getDate" );
    		if(endDate < startDate){
    			$startDate.datetimepicker('setDate', startDate + 3600*1*24*60*60*60);
    		}
    		$startDate.datetimepicker( "option", "maxDate", endDate );
    	}
    });
    
   /* createDatetimePicker2($("#curriculum_time_update"),"yy-mm-dd","HH:mm:ss");
    createDatetimePicker2($("#stop_time_update"),"yy-mm-dd","HH:mm:ss");*/
    
 $('#curriculum_time_update').datetimepicker({
    	
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#curriculum_time_update" );
    		var $endDate = $('#stop_time_update');
    		var endDate = $endDate.datepicker( 'getDate' );
    		if(endDate < startDate){
    			$endDate.datetimepicker('setDate', startDate - 3600*1*24*60*60*60);
    		}
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });
    $('#stop_time_update').datetimepicker({
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm:ss',
    	onSelect: function( endDate ) {
    		var $startDate = $( "#curriculum_time_update" );
    		var $endDate = $('#stop_time_update');
    		var startDate = $startDate.datepicker( "getDate" );
    		if(endDate < startDate){
    			$startDate.datetimepicker('setDate', startDate + 3600*1*24*60*60*60);
    		}
    		$startDate.datetimepicker( "option", "maxDate", endDate );
    	}
    });
    
    
    document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
    
});

//班级列表展示
function loadGradeList(){
    var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
        { "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
            return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
        }},
        {title: 'ID', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '课程名称', "class": "center","data": 'courseName', "sortable": false},
        {title: '课程编号', "class": "center","data": 'courseId', "sortable": false, "visible":false},
        {title: '班级名称', "class": "center","data": 'name', "sortable": false},
        {title: '实际已报名<br/>人数', "class": "center", "width": "6%","data": 'studentCount', "sortable": false},
        {title: '班级额定<br/>人数', "class": "center", "width": "8%","data": 'studentAmount', "sortable": false},
//        {title: '讲师', "class": "center", "width": "7%","data": 'role_type1', "sortable": false},
//        {title: '班主任', "class": "center", "width": "7%","data": 'role_type2', "sortable": false},
//        {title: '助教', "class": "center", "width": "7%","data": 'role_type3', "sortable": false},
        {title: '开课时间', "class": "center", "width": "8%","data": 'curriculumTime', "sortable": false},
        {title: '结课日期', "class": "center", "width": "8%","data": 'stopTime', "sortable": false},
        {title: '状态', "class": "center", "width": "6%", "data": 'otcStatus', "sortable": false,"mRender":function(data,display,row){
            return data==1 ? '已开班':'未开班'
        }},
//        {title: '学习<br/>计划', "class": "center", "width": "6%", "data": 'hasPlan', "sortable": false,"mRender":function(data,display,row){
//        	var cnt = row.defaultStudentCount - row.hasPlan;
//        	return cnt == 0?'已生成':'--';
//        }},
        {
            "sortable": false,"data":"id","class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showGradeDialog(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>' +
                             '<a class="blue" href="javascript:void(-1);" title="修改" onclick="editGradeDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
		                     /*'<a class="blue" href="javascript:void(-1);" title="分配老师" onclick="gradeTeacherDialog(\''+data+'\',\''+row.courseId+'\')"><i class="glyphicon glyphicon-user bigger-130"></i></a>'+*/
		                     '<a class="blue" href="javascript:void(-1);" title="管理学员" onclick="student_manager(this);"><i class="fa fa-graduation-cap bigger-130"></i></a>';
//                			 if((row.defaultStudentCount - row.hasPlan) > 0){
//                				 buttons += '<a class="blue" href="javascript:void(-1);" title="生成学习计划" onclick="bulidStudyPlan(this)"><i class="fa fa-building bigger-130" aria-hidden="true"></i>';
//                			 }else{
//                				 buttons += '<a class="blue" href="javascript:void(-1);" title="查看学习计划" onclick="bulidStudyPlan(this)"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130" aria-hidden="true"></i>';
//                			 }
		                     
		            buttons += '</a></div>';
                return buttons;

            }
        }
    ];

    gradeTable = initTables("cloudClassTable", basePath + "/cloudClass/grade/findGradeList", dataFields, true, true, 0,null,searchJson,function(data){
        
    });

    var dataFields = [
					  
					  {title: 'ID', "class": "center", "width": "3%","data": 'id',datafield: 'xuhao', "sortable": false},
					  {title: '微课课程名称', "class": "center","width": "15%","data": 'courseName', "sortable": false},
					  {title: '课程类别', "class": "center", "width": "15%","data": 'scoreTypeName', "sortable": false},
					  {title: '班级名称', "class": "center","width": "15%","data": 'name', "sortable": false},
					  {title: '已报名人数', "class": "center","width": "15%", "data": 'studentCount', "sortable": false},
					  {title: '班级额定人数', "class": "center","width": "15%", "data": 'studentAmount', "sortable": false},
//					  {title: '讲师', "class": "center", "width": "7%","data": 'role_type1', "sortable": false},
//				      {title: '班主任', "class": "center", "width": "7%","data": 'role_type2', "sortable": false},
//				      {title: '助教', "class": "center", "width": "7%","data": 'role_type3', "sortable": false},
					  {"sortable": false,"data":"id","class": "center","width":"15%","title":"操作","mRender":function (data, display, row) {
							  var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
							  '<a class="blue" href="javascript:void(-1);" title="查看" onclick="student_managerMicro(this)">查看学员</a></div>';
							  return buttons;
						  }
					  }];
    
    gradeMicroTable = initTables("gradeMicroTable", basePath + "/cloudClass/grade/findMicroGradeList", dataFields, true, true, 0,null,searchJson,function(data){
    	
    });
    

      var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
      var dataFields = [
          { "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
              return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
          }},
          {title: 'ID', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
          {title: '课程名称', "class": "center","data": 'courseName', "sortable": false},
          {title: '课程编号', "class": "center","data": 'courseId', "sortable": false, "visible":false},
          {title: '班级名称', "class": "center","data": 'name', "sortable": false},
          {title: '实际已报名<br/>人数', "class": "center", "width": "6%","data": 'studentCount', "sortable": false},
          {title: '班级额定<br/>人数', "class": "center", "width": "8%","data": 'studentAmount', "sortable": false},
          {title: '开课时间', "class": "center", "width": "8%","data": 'curriculumTime', "sortable": false},
          {title: '结课日期', "class": "center", "width": "8%","data": 'stopTime', "sortable": false},
          {title: '状态', "class": "center", "width": "6%", "data": 'otcStatus', "sortable": false,"mRender":function(data,display,row){
              return data==1 ? '已开班':'未开班'
          }},
          {
              "sortable": false,"data":"id","class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {

                  var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                               '<a class="blue" href="javascript:void(-1);" title="查看" onclick="showGradeDialog(this)"><i class="ace-icon fa fa-search  bigger-130"></i></a>' +
                               '<a class="blue" href="javascript:void(-1);" title="修改" onclick="editGradeDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>' +
  		                     '<a class="blue" href="javascript:void(-1);" title="管理学员" onclick="student_manager(this);"><i class="fa fa-graduation-cap bigger-130"></i></a>';
  		                     
  		            buttons += '</a></div>';
                  return buttons;

              }
          }
      ];

      realGradeTable = initTables("realClasss", basePath + "/cloudClass/grade/findGradeList", dataFields, true, true, 0,null,searchJson,function(data){
          
      });
    
    jQuery.validator.addMethod("disableEdit", function (value, element) {
    	if(value != tempStartTime && tempHasPlan > 0){//如果时间与临时保存的开课时间不一致 就去后台验证 是否已经生成学习计划
    		return false;
    	}else{
    		return true;
    	}
	}, "学习计划已经生成开课时间不能修改！");
    
    jQuery.validator.addMethod("qq", function(value, element) { 
    	var tel = /^[1-9]\d{4,9}$/; 
    	return this.optional(element) || (tel.test(value)); 
    	}, "qq号码格式错误");

    jQuery.validator.addMethod("syncRequiredDay", function(value, element) {
    	var restDaySum = $(element).parent().parent().find("[name='restDaySum']").eq(0).val();
    	var workDaySum = $(element).parent().parent().find("[name='workDaySum']").eq(0).val();
    	if(value == "" || value == null){//为空的进来验证  如果另外一个不为空 那么这个也不能为空
    		if($(element).attr("name")=="workDaySum"){
    			if(restDaySum != "" && restDaySum != null){
        			return false;
        		}
    		}else{
    			if(workDaySum != "" && workDaySum != null){
        			return false;
        		}
    		}
    	}
    	return true;
    	
    }, "天数不能为空");
    
    jQuery.validator.addMethod("lessAmount", function(value, element) {
    	if(Number(value)>Number($(element).parent().parent().parent().find("[name='studentAmount']").eq(0).val())){
    		return false;
    	}
    	return true;
    	
    }, "默认人数不能大于额定人数");
    
    jQuery.validator.addMethod("lessThenTeachingDays", function(value, element) {
    	if($(element).parent().parent().parent().find("[name='teachingDays']").eq(0).val() != null && 
    		 $(element).parent().parent().parent().find("[name='teachingDays']").eq(0).val() != "" && 
    			$(element).parent().parent().parent().find("[name='teachingDays']").eq(0).val() != 0){
    		if(Number(value)>=Number($(element).parent().parent().parent().find("[name='teachingDays']").eq(0).val())){
        		return false;
        	}
    	}
    	return true;
    	
    }, "必须小于授课天数");
    
    gradeForm = $("#grade-form").validate({
        messages: {
        	menuId : {
        		required : "请选择学科"
        	},
        	scoreTypeId : {
        		required : "请选择课程类别"
        	},
        	courseId : {
        		required : "请选择课程"
        	},
        	nameNumber : {
        		required : "请输入班级期数！",
        		digits: "班级期数必须为整数"
        	},
            name: {
                required: "班级名称不能为空"
            },
            seat: {
                required: "剩余席数不能为空"
            },
            curriculumTime: {
                required: "开课时间不能为空",
                date:"请输入正确的时间格式"
            },
            stopTime: {
                required: "结课时间不能为空",
                date:"请输入正确的时间格式"
            },
            studentAmount: {
            	required: "额定人数不能为空",
            	digits: "额定人数必须为整数",
            	range:"额定人数范围为1~99999"
            },
            workDaySum: {
            	required: "天数不能为空",
            	digits: "天数必须为整数",
            	range:"天数范围为1~99"
            },
            restDaySum: {
            	required: "天数不能为空",
            	digits: "天数必须为整数",
            	range:"天数范围为1~99"
            },
            qqno: {
            	required: "班级Q群不能为空",
            	qq: "Q群格式错误"
            }

        }
    });
    
    updateForm = $("#update-form").validate({
        messages: {
        	menuId : {
        		required : "请选择学科"
        	},
        	scoreTypeId : {
        		required : "请选择课程类别"
        	},
        	courseId : {
        		required : "请选择课程"
        	},
        	nameNumber : {
        		required : "请输入班级期数！",
        		digits: "班级期数必须为整数"
        	},
            name: {
                required: "班级名称不能为空"
            },
            seat: {
                required: "剩余席数不能为空"
            },
            curriculumTime: {
                required: "开课时间不能为空"
            },
            stopTime: {
                required: "结课时间不能为空"
            },
            studentAmount: {
            	required: "额定人数不能为空",
            	digits: "额定人数必须为整数",
            	range:"额定人数范围为1~99999"
            },
            workDaySum: {
            	required: "天数不能为空",
            	digits: "天数必须为整数",
            	range:"天数范围为1~99"
            },
            restDaySum: {
            	required: "天数不能为空",
            	digits: "天数必须为整数",
            	range:"天数范围为1~99"
            },
            qqno: {
            	required: "班级Q群不能为空",
            	qq: "Q群格式错误"
            }
        }
    });
    
    //修改界面验证
    $("#grade-form").validate({
        messages: {
            name: {
                required: "班级名称不能为空"
            },
            seat: {
                required: "剩余席数不能为空"
            },
            curriculumTime: {
                required: "开课时间不能为空"
            },
            stopTime: {
                required: "结课时间不能为空"
            }

        }
    });
    
}

/* 添加 */
$(".add_bx").click(function () {
    addGradeDialog();
});

/*  删除*/
$(".dele_bx").click(function(){
    deleteAll(basePath+"/cloudClass/grade/deleteGrades",gradeTable);
});


function teachMethodInfo()
{
    $("#classTemplateId").val("");
    $("#classTemplateShowId").html("");
    $("#studentAmount").val("");
	$("#studentAmount").removeAttr("readOnly");

    for(var i=0;i<courseArray.length;i++)
    {
        if($("#courseIdId").val()==courseArray[i].id)
        {
            if(courseArray[i].classTemplate==null)
            {
                courseArray[i].classTemplate="";
            }
            $("#classTemplateId").val(courseArray[i].classTemplate);
            $("#classTemplateShowId").html(courseArray[i].classTemplate);
            //添加班级额定人数  学习期数
//            $("#studentAmount").val(courseArray[i].gradeStudentSum);
            $("#courseType").val(courseArray[i].courseType);
            $("#teachingDays").val(courseArray[i].teachingDays);

            if($("#courseType").val()==0){//职业课
            	$("#studentAmount").val("");
            	$("#studentAmount").removeAttr("readOnly");
            }else if($("#courseType").val()==1){//微课
            	$("#studentAmount").val(courseArray[i].gradeStudentSum);
            	$("#studentAmount").attr("readOnly","readOnly");
            }
        }
    }
    $("#teachMethodId").html("");
    ajaxRequest(basePath+'/cloudClass/grade/teachMethod',{"courseId":$("#courseIdId").val()},function(res) {
        if(res.resultObject.name!=null) {
            $("#teachMethodId").html(res.resultObject.name);
        }else{
            $("#teachMethodId").html("");
        }
    });
}


function teachMethodInfo_update()
{
    $("#classTemplateId_update").val("");
    $("#classTemplateShowId_update").text("");
    for(var i=0;i<courseArray.length;i++)
    {
        if($("#courseIdId_update").val()==courseArray[i].id)
        {
            if(courseArray[i].classTemplate==null)
            {
                courseArray[i].classTemplate="";
            }
            $("#classTemplateId_update").val(courseArray[i].classTemplate);
            $("#classTemplateShowId_update").text(courseArray[i].classTemplate);
            
          //添加班级额定人数  学习期数
//            $("#studentAmount_update").val(courseArray[i].gradeStudentSum);
            $("#courseType_update").val(courseArray[i].courseType);
            $("#teachingDays_update").val(courseArray[i].teachingDays);
        	$("#studentAmount").removeAttr("readOnly");
        }
    }
    $("#teachMethodId_update").html("");
    ajaxRequest(basePath+'/cloudClass/grade/teachMethod',{"courseId":$("#courseIdId_update").val()},function(res) {
        if(res.resultObject.name!=null) {
            $("#teachMethodId_update").html(res.resultObject.name);
        }else{
            $("#teachMethodId_update").html("");
        }
    });
}

function courseList()
{
    courseArray=[];
    $("#teachMethodId").html("");
    $("#classTemplateShowId").val("");
    $("#classTemplateId").val("");
    $("#studentAmount").val("");
    $("#studentAmount").removeAttr("readonly");
    $("#teachingDays").val("");
   /* $("#firstNameNumber").text("");*/
   // $("#grade-form :select").remove("selected");
    ajaxRequest(basePath+'/cloudClass/grade/courseList',{"menuId":$("#menuIdId").val(),"courseTypeId":$("#scoreTypeIdId").val()},function(res) {
        $('#courseIdId').empty();
        if(res.resultObject.length>0)
        {
            for(var i=0;i<res.resultObject.length;i++)
            {
                courseArray.push(res.resultObject[i]);
                $('#courseIdId').append("<option value='"+res.resultObject[i].id+"'>"+res.resultObject[i].courseName+"</option>");
                if(i == 0){
                	$("#classTemplateShowId").text(res.resultObject[i].classTemplate);
                    $("#classTemplateId").val(res.resultObject[i].classTemplate);
                }
            }
            ajaxRequest(basePath+'/cloudClass/grade/teachMethod',{"courseId":res.resultObject[0].id},function(res) {
                $("#teachMethodId").html(res.resultObject.name);
            });
            
            if($("#courseIdId").val() != "" && $("#courseIdId").val() !=null){
            	teachMethodInfo();
            }
        }
    });
}



function courseList_update()
{
    courseArray=[];
    $("#teachMethodId_update").html("");
    $("#classTemplateShowId_update").text("");
    $("#classTemplateId_update").val();
    /*$("#firstNameNumber_update").text("");*/
    // $("#grade-form :select").remove("selected");
    ajaxRequest(basePath+'/cloudClass/grade/courseList',{"menuId":$("#menuIdId_update").val(),"courseTypeId":$("#scoreTypeIdId_update").val()},function(res) {
        $('#courseIdId_update').empty();
        if(res.resultObject.length>0)
        {
            for(var i=0;i<res.resultObject.length;i++)
            {
                courseArray.push(res.resultObject[i]);
                $('#courseIdId_update').append("<option value='"+res.resultObject[i].id+"'>"+res.resultObject[i].courseName+"</option>");
            }
            ajaxRequest(basePath+'/cloudClass/grade/teachMethod',{"courseId":res.resultObject[0].id},function(res) {
                $("#teachMethodId_update").html(res.resultObject.name);
            });
        }
    });
}

//添加班级信息
function addGradeDialog(){
    gradeForm.resetForm();
    $("#courseIdId").empty();
    $("#courseIdId").append("<option value='-1'>请选择</option>");
    $("#teachMethodId").html("");
    $("#classTemplateShowId").html("");
    $("#classTemplateId").val("");
   /* $("#firstNameNumber").text("");*/
    openDialog("gradeDialog","dialogGradeDiv","新增班级 ",720,500,true,"提交",function(){
        if($("#grade-form").valid()){
            mask();
            $("#grade-form").attr("action", basePath+"/cloudClass/grade/saveGrade");
            $("#grade-form").ajaxSubmit(function(data){
                unmask();
                if(data.success){
                    $("#gradeDialog").dialog("close");
                    freshTable(gradeTable);
                }else{
               	 layer.msg(data.errorMessage);
                }
            });
        }
    });
}


//修改班级信息
function editGradeDialog(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = gradeTable.fnGetData(oo);
    updateForm.resetForm();
    $("#menuIdId_update").val(aData.menuId);
    $("#scoreTypeIdId_update").val(aData.scoreTypeId);
    $("#gradeId_update").val(aData.id);
    tempStartTime = aData.curriculumTime;//用于验证是否可以修改开课时间
    tempHasPlan = aData.hasPlan;

    ajaxRequest(basePath+'/cloudClass/grade/courseList',{"menuId":$("#menuIdId_update").val(),"courseTypeId":$("#scoreTypeIdId_update").val()},function(res) {
        $('#courseIdId_update').empty();
        $("#courseIdId_update").append("<option value='-1'>请选择</option>");
        if(res.resultObject.length>0)
        {
            for(var i=0;i<res.resultObject.length;i++)
            {
                courseArray.push(res.resultObject[i]);
                $('#courseIdId_update').append("<option value='"+res.resultObject[i].id+"'>"+res.resultObject[i].courseName+"</option>");
            }
        }

        $("#courseIdId_update").val(aData.courseId);
        $("#teachMethodId_update").val(aData.teachMethodName);
        $("#classTemplateShowId_update").text(aData.classTemplate);
        $("#classTemplateId_update").val(aData.classTemplate);
        $("#nameNumber_update").val(aData.nameNumber);
        $("#curriculum_time_update").val(aData.curriculumTime);
        $("#stop_time_update").val(aData.stopTime);
        $("#studentAmount_update").val(aData.studentAmount);
        $("#workDaySum_update").val(aData.workDaySum);
        $("#restDaySum_update").val(aData.restDaySum);
        $("#qqno_update").val(aData.qqno);
        $("#defaultStudentCount_update").val(aData.defaultStudentCount);
        teachMethodInfo_update();
        openDialog("updateDialog","updateGradeDiv","修改班级",720,500,true,"提交",function(){
            if($("#update-form").valid()){
                mask();
                $("#update-form").attr("action", basePath+"/cloudClass/grade/updateGrade");
                $("#update-form").ajaxSubmit(function(data){
                    unmask();
                    if(data.success){
                        $("#updateDialog").dialog("close");
                        layer.msg(data.errorMessage);
                        freshTable(gradeTable);
                    }else{
                    	 layer.msg(data.errorMessage);
                    }
                });
            }
        });
    });


}




function gradeTeacherDialog(id,courseId1){
    gradeForm.resetForm();
    $(".teacher .allTeacher").empty();
    $(".banzhuren .allTeacher").empty();
    $(".zhujiao .allTeacher").empty();
    $("#jiangshiLi").empty();
    $("#banzhurenLi").empty();
    $("#zhujiaoLi").empty();
    //根据当前id查找对应的班级信息
    $.get(basePath+"/cloudClass/grade/teachers",{gradeId:id,courseId:courseId1}, function(result){
        $("#teachers_grade_id").val(id);
        $("#teachers_course_id").val(courseId1);
        $("#teachers_courseName").html(result.resultObject.teachers_courseName);
        
        
        if(result.resultObject.roleType1!=null&&result.resultObject.roleType1.length>0){
          /*  var roleType1Checkboxs="";
            var roleType2Checkboxs="";
            var roleType3Checkboxs="";*/
            for(var i=0;i<result.resultObject.roleType1.length;i++)
            {
                if(result.resultObject.roleType1[i]!=null&&result.resultObject.roleType1[i].status==1)
                {
                	$(".teacher .allTeacher").append("<div><span data-jiangshiid='"+result.resultObject.roleType1[i].id+"' class='tName'>"+result.resultObject.roleType1[i].name+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
                	deleteTeacher();
                }
            	
            	$("#jiangshiLi").append("<li data-jiangshiid="+result.resultObject.roleType1[i].id+">"+result.resultObject.roleType1[i].name+"</li>");
            }
            //$("#teachers_roleType1").html(roleType1Checkboxs);
            for(var i=0;i<result.resultObject.roleType2.length;i++)
            {
                if(result.resultObject.roleType2[i]!=null&&result.resultObject.roleType2[i].status==1)
                {
                    $(".banzhuren .allTeacher").append("<div><span data-banzhurenid='"+result.resultObject.roleType2[i].id+"' class='tName'>"+result.resultObject.roleType2[i].name+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
                    deleteTeacher();
                }
            	
            	$("#banzhurenLi").append("<li data-banzhurenid="+result.resultObject.roleType2[i].id+">"+result.resultObject.roleType2[i].name+"</li>");
            }
            //$("#teachers_roleType2").html(roleType2Checkboxs);
            for(var i=0;i<result.resultObject.roleType3.length;i++)
            {
                if(result.resultObject.roleType3[i]!=null&&result.resultObject.roleType3[i].status==1)
                {
                     $(".zhujiao .allTeacher").append("<div><span data-zhujiaoid='"+result.resultObject.roleType3[i].id+"' class='tName'>"+result.resultObject.roleType3[i].name+"</span><img class='allTeacherClose' src='"+basePath+"/images/close01.png'/></div>");
                     deleteTeacher();
                }
            	
            	$("#zhujiaoLi").append("<li data-zhujiaoid="+result.resultObject.roleType3[i].id+">"+result.resultObject.roleType3[i].name+"</li>");
            }
            //$("#teachers_roleType3").html(roleType3Checkboxs);
        }
        //$("#uname").val(result[0].name);

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
        	  
        	/*   gradeteacherform = $("#grade-teacher-form").validate({
     	      	  messages: {
     	      		  roleType1: {
     	                    required: "请选择讲师"
     	                },
     	                roleType2: {
     	                    required: "请选择班主任"
     	                },
     	                roleType3: {
     	                    required: "请选择助教"
     	                }
     	            }
     	      });*/
            $("#grade-teacher-form").attr("action",basePath+"/cloudClass/grade/teachers/save");
            if($("#grade-teacher-form").valid()){
            	 $("#grade-teacher-form").ajaxSubmit(function(data){
                     if(data.success){
                    	 layer.msg("老师分配成功！");
                         $("#gradeTeacherDialog").dialog("close");
                         freshTable(gradeTable);
                     }else{
                    	 alertInfo("老师分配失败！");
                         //layer.msg(data.errorMessage);
                     }
                 });
            }
           
        });
    });
}

function student_manager(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = gradeTable.fnGetData(oo);
	window.location.href=basePath+'/home#cloudClass/studentGrade/student?page='+getCurrentPageNo(gradeTable)+'&gradeId='+aData.id;
}

function student_managerMicro(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = gradeMicroTable.fnGetData(oo);
	window.location.href=basePath+'/home#cloudClass/studentGrade/student?page='+getCurrentPageNo(gradeTable)+'&gradeId='+aData.id;
}

//查看班级详细信息
function showGradeDialog(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = gradeTable.fnGetData(oo);
    //根据当前id查找对应的班级信息
    $("#menuName_show").html(aData.menuName);
    $("#scoreTypeName_show").html(aData.scoreTypeName);
    $("#courseName_show").html(aData.courseNameTmp);
    $("#name_show").html(aData.name);
    $("#role_type1_show").html(aData.role_type1);
    $("#role_type2_show").html(aData.role_type2);
    $("#role_type3_show").html(aData.role_type3);
    $("#studentCount_show").html(aData.studentCount);
    $("#curriculumTime_show").html(aData.curriculumTime);
    $("#stopTime_show").html(aData.stopTime);
    $("#studentAmount_show").html(aData.studentAmount);
    if(aData.workDaySum != null){
    	$("#plan_show").html("上"+aData.workDaySum+"天 休息"+aData.restDaySum+"天");//学习计划
    }else{
    	$("#plan_show").html();//学习计划
    }
    $("#qqno_show").html(aData.qqno);//QQ群号
    $("#defaultStudentCount_show").html(aData.defaultStudentCount);//默认报名人数
    $("#totalStudentCount_show").html(Number(aData.defaultStudentCount) + Number(aData.studentCount));//默认报名人数


    openDialogNoBtnName("showDialog","showGradeDiv","查看班级信息",680,540,false,"确定",null);
}




//禁用班级
function forbiddenGrade(param){
     var pars=param.split(",");
    //根据当前id查找对应的班级信息
    $.post(basePath+"/cloudClass/grade/updateGradeStatus",{gradeId:pars[0],isGradeStatus:pars[1]==1?0:1}, function(result){
        freshTable(gradeTable);
    });
}


 //条件搜索
 function search(){
     var startTime=$("#sstart_time").val(); //开始时间
     var endTime=$("#sstop_time").val(); //结束时间
     var menuId=$("#menuId").val();
     var teachMethodId=$("#teachMethodIdSearch").val();
     var scoreTypeId=$("#scoreTypeId").val();
     var courseId=$("#courseId").val();
     var gradeStatus=$("#gradeStatus").val();


     if(startTime != "" || endTime != "") {
         if (endTime != "" && startTime == "") {
             alertInfo("开课时间不能为空");
             return;
         }
         if (startTime != "" && endTime == "") {
             alertInfo("结课时间不能为空");
             return;
         }
         if (startTime > endTime) {
             alertInfo("开课时间不能大于结课时间");
             return;
         }
         searchJson.push('{"tempMatchType":"7","propertyName":"curriculumTime","propertyValue1":"' + startTime + '","tempType":"String"}');
         searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + endTime + '","tempType":"String"}');
     }
     if(menuId!=null&&menuId!="-1")
     {
         searchJson.push('{"tempMatchType":"8","propertyName":"menuId","propertyValue1":"' + menuId + '","tempType":"String"}');
     }
     if(scoreTypeId!=null&&scoreTypeId!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"scoreTypeId","propertyValue1":"' + scoreTypeId + '","tempType":"String"}');
     }
     if(courseId!=null&&courseId!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"courseId","propertyValue1":"' + courseId + '","tempType":"String"}');
     }
     if(gradeStatus!=null&&gradeStatus!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"gradeStatus","propertyValue1":"' + gradeStatus + '","tempType":"String"}');
     }
     if(teachMethodId!=null&&teachMethodId!="-1")
     {
         searchJson.push('{"tempMatchType":"9","propertyName":"teachMethodId","propertyValue1":"' + teachMethodId + '","tempType":"String"}');
     }
     searchButton(gradeTable,searchJson);
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();


 }
 
 //条件搜索
 function searchMicro(){
	 var searchs  = new Array();
		$("#searchMicroDiv .searchTr").each(function() {
			if (!isnull($(this).find('.propertyValue1').val())) {
				var propertyValue2 = $(this).find('.propertyValue2').val();
				if(!isnull(propertyValue2)){
					searchs.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
							+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
							+',"propertyValue2":"'+propertyValue2+'"}');
				}else{
					searchs.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
							+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
				}
			}
		});
	 
		var str = "[" + searchs.join(",") + "]";
		
		gradeMicroTable.fnFilter(str);
	 
}


 /**
  * 职业班级管理
  * 
  */
$(".vocationalCourseGrade_bx").click(function(){
 	$("#courseDiv").show();
 	$("#courseRecDiv").hide();
 	freshTable(gradeTable);
 });

 /**
  * 微课班级管理
  * 
  */
$(".microCourseGrade_bx").click(function(){
 	$("#courseDiv").hide();
 	$("#courseRecDiv").show();
 	freshTable(gradeMicroTable);
 });

 
/**
 * 班级上移1
 * @param obj
 */
function  moveUp(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = gradeTable.fnGetData(oo);
    ajaxRequest(basePath+"/cloudClass/grade/moveUp",{"id":aData.id},function(res){
        if(res.success){
            freshTable(gradeTable);
        }
    });
}

/**
 * 班级下移
 * @param obj
 */
function  moveDown(obj){
    var oo = $(obj).parent().parent().parent();
    var aData = gradeTable.fnGetData(oo);
    ajaxRequest(basePath+"/cloudClass/grade/moveDown",{"id":aData.id},function(res){
        if(res.success){
            freshTable(gradeTable);
        }
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
//    console.log(target);
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
//新建学习计划
function bulidStudyPlan(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = gradeTable.fnGetData(oo);

	//验证该班级是否可以生成计划模板
	ajaxRequest(basePath+'/cloudClass/grade/checkBuildPlan',{"courseId":aData.courseId},function(res) {
        if(!res.success) {
        	//false说明不可以新建
        	alert("当前班级所属课程没有设置学习计划模板，不可生成！");
        }else{
        	//true说明可以新建
        	window.location.href=basePath+'/home#cloudClass/plan/toAdd?courseId='+aData.courseId+'&gradeId='+aData.id+'&gradeName='+aData.name;
        }
    });
}

$(".fenpeiTeacherBtn .cancle").click(function(){
    $("#fenpeiTeacher").css("display","none");
    $(".zhezhao").css("display","none");
})

function addTitle(obj){
	$(obj).attr("title",$(obj).find("option:checked").text());
}