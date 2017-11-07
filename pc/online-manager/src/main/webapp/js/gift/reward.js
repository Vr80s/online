var _courseTable;
var searchCase;
var courseForm;
var teacherArray=new Array();
var _courseRecTable;

$(function(){
	
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},
    {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
   
    { "title": "金额", "class":"center","width":"10%", "sortable":false,"data": 'price',"mRender":function (data, display, row) {
    	if(row.isFreeDom){
    		return data="自由输入";
    	}else{
    		return data;
    	}
    } },
    { "title": "最后修改时间", "class":"center","width":"10%", "sortable":false,"data": 'createTime' },
    { "title": "平台分成", "class":"center","width":"10%", "sortable":false,"data": 'brokerage',"mRender":function (data, display, row) {
    		return data+"%";
    } },
    { "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="<span name='zt'>已启用</span>";
    	}else{
    		return data="<span name='zt'>已禁用</span>";
    	}
    } },
    {"sortable": false,"class": "center","width":"8%","title":"排序","mRender":function (data, display, row) {
//    	debugger;
    	if(row.status ==1){//如果是禁用
    		return '<div class="hidden-sm hidden-xs action-buttons">'+
    		'<a class="blue" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="blue" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}else{
    		return '<div class="hidden-sm hidden-xs action-buttons">'+
    		'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
        	'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
    	}
    }},
    { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status=="1"){
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '
	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '
	    	}
	    } 
	},
	{ "title": "礼物展示图", "class":"center","width":"13%","sortable":false,"data": 'smallimgPath',"visible":false}];
	
	_courseTable = initTables("courseTable",basePath+"/reward/list",objData,true,true,2,null,searchCase,function(data){
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
//				debugger;
				if(index == 0){
					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
				}
			});
			$("[name='downa']").each(function(index){
//				debugger;
				if(index == $("[name='downa']").size()-1){
					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
				}
			});
			
			courseForm = $("#addCourse-form").validate({
		        messages: {
		        	smallingPath:{
						required:"礼物图标不能为空！",
						
		            },
					giftName: {
						required:"请输入礼物名称！",
						minlength:"礼物名称过短，应大于2个字符！",
						maxlength:"礼物名称过长，应小于20个字符！"
		            },
					price: {
						required:"价格不可空！",
					}
		        }
		    });
		updateCourseForm = $("#updateCourse-form").validate({
			messages: {
	        	smallingPath:{
					required:"礼物图标不能为空！",
					
	            },
				giftName: {
					required:"请输入礼物名称！",
					minlength:"礼物名称过短，应大于2个字符！",
					maxlength:"礼物名称过长，应小于20个字符！"
	            },
				price: {
					required:"价格不可空！",
				}
	        }
		});
	});

	
	createImageUpload($('.uploadImg'));//生成图片编辑器
	    
});

//新增框
$(".add_bx").click(function(){
	courseForm.resetForm();
	
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
	imgSenBut();
	createImageUpload($('#addCourse-form .uploadImg'));//生成图片编辑器
	
	var dialog = openDialog("addCourseDialog","dialogAddCourseDiv","新增打赏",580,600,true,"确定",function(){
		
		if($("#addCourse-form").valid()){
			mask();
			 $("#addCourse-form").attr("action", basePath+"/gift/addGift");
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

//修改
function toEdit(obj){
	updateCourseForm.resetForm();
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	reviewImageRecImg2("teacherImgPath_file_edit",row.smallimgPath);//预览

	$("#updateCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	
//	debugger;
	$("#editCourse_id").val(row.id); //礼物名称
	$("#price_edit").val(row.price); //礼物时常
	$("#brokerage_edit").val(row.brokerage); //图片字段赋值
	
	var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv","修改打赏",350,350,true,"确定",function(){
		
		if($("#updateCourse-form").valid()){
			mask();
            $("#updateCourse-form").attr("action", basePath+"/reward/updateRewardById");
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
			$("#nihao").val($("#combobox1 option").eq(i).text());
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

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/reward/updateStatus",{"id":row.id},function(data){
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
	deleteAll(basePath+"/reward/deletes",_courseTable);
});

$(".fencheng_bx").click(function(){
	setBrokerage(basePath+"/reward/deletes",_courseTable);
});
//设置平台分成
function setBrokerage(url,dataTable,title,content,btnName){
	var ids = new Array();
	$(".dataTable tbody input[type='checkbox']:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length>0){
//		var idArr = ids.join(",");
		$("#ids").val(ids);
		var dialog = openDialog("FcDialog","dialogEditFcDiv","修改礼物",400,300,true,"确定",function(){
			if($("#updateBrokerage-form").valid()){
	            $("#updateBrokerage-form").attr("action", basePath+"/reward/updateBrokerage");
	            $("#updateBrokerage-form").ajaxSubmit(function(data){
	            	try{
	            		data = jQuery.parseJSON(jQuery(data).text());
	            	}catch(e) {
	            		data = data;
	            	  }
	                unmask();
	                if(data.success){
	                	debugger
	                    $("#FcDialog").dialog("close");
	                    layer.msg(data.resultObject);
	                     freshTable(_courseTable);
	                }else{
	                	 layer.msg(data.errorMessage);
	                }
	            });
			}
		});
	}else{
		showDelDialog("",title,"请选择操作对象！",btnName);
	}
}
function search(){
	searchButton(_courseTable);
};
/**
 * 公开课管理显示
 * 
 */
$(".kcgl_bx").click(function(){
	$("#courseDiv").show();
	$("#courseRecDiv").hide();
	freshTable(_courseTable);
});

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseTable.fnGetData(oo);
	ajaxRequest(basePath+'/reward/upMove',{"id":aData.id},function(res){
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
	ajaxRequest(basePath+'/reward/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};



$("#this_web").click(function(){
	$("#directId").val("");
	$("#externalLinks").val("");
	
	$("#add-directIdDiv").show();
	$("#add-externalLinksDiv").hide();
	
	$('#externalLinks').attr("disabled",true);
	$("#directId").attr("disabled",false);

});

$("#other_web").click(function(){
	$("#directId").val("");
	$("#externalLinks").val("");
	
	$("#add-externalLinksDiv").show();
	$("#add-directIdDiv").hide();
	
	$('#externalLinks').attr("disabled",false);
	$("#directId").attr("disabled",true);
	
});

$("#this_web_edit").click(function(){
/*	$("#directId_edit").val("");
	$("#externalLinks_edit").val("");*/
	
	$("#directIdDiv_edit").show();
	$("#externalLinksDiv_edit").hide();
	
	$('#externalLinks_edit').attr("disabled",true);
	$("#directId_edit").attr("disabled",false);

});

$("#other_web_edit").click(function(){
/*	$("#directId_edit").val("");
	$("#externalLinks_edit").val("");*/
	
	$("#externalLinksDiv_edit").show();
	$("#directIdDiv_edit").hide();
	
	$('#externalLinks_edit').attr("disabled",false);
	$("#directId_edit").attr("disabled",true);
	
});
//-------------------------------------------------------------------公开课统计-----------------------------------------------------------------------
/**
 * 公开课统计显示
 * 
 */
$(".kctj_bx").click(function(){
	$("#courseDiv").hide();
	$("#courseRecDiv").show();
	freshTable(_courseRecTable);
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

$(function() {
});
