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
	{"title": "封面图", "class": "center", "width": "12%", "sortable": false, "data": 'smallimgPath',"mRender":function(data){
		return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;'/>";
	}},
	{ "title": "直播名称", "class":"center","width":"8%","sortable":false,"data": 'courseName' },
	{ "title": "直播状态", "class":"center","width":"6%","sortable":false,"data": 'liveStatus' ,"mRender":function (data, display, row) {
		debugger;
			if(data==1 ){  //直播状态1.直播中，2预告，3直播结束
				return "直播中";
			}else if(data== 2){
				return "预告";
			}else if(data== 3){
				return "结束";
			}else if(data== 4){
				return "即将直播";
			}else if(data== 5){
				return "准备直播 ";
			}else if(data== 6){
				return "异常直播";
			}
		} },
    { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'menuName' },
	{ "title": "主讲人", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
	{ "title": "主播", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
    { "title": "开播时间", "class":"center","width":"10%", "sortable":false,"data": 'startTime' },
    { "title": "结束时间", "class":"center","width":"10%", "sortable":false,"data": 'endTime' },
    { "title": "发布时间", "class":"center","width":"10%", "sortable":false,"data": 'releaseTime' },
    //	private  int liveSource;  //直播来源  1、后台新增  2、app申请
    { "title": "直播来源", "class":"center","width":"6%","data":"liveSource","sortable":false,"mRender":function(data,display,row){
    	if(data!=null && data== 2){
    		return "用户申请";
    	}else{
    		return "后台新增";
    	}
    }},

    { "title": "价格", "class":"center","width":"5%","sortable":false,"mRender":function(data,display,row){
    	data = parseInt(row.currentPrice);
    	return "<span name='coursePrice'>"+data+"</span>"
    }},
    { "title": "状态", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="<span name='zt'>已启用</span>";
    	}else{
    		return data="<span name='zt'>已禁用</span>";
    	}
    } },
    { "title": "推荐时效", "class":"center","width":"10%","sortable":false,"data": 'sortUpdateTime'},
    { "title": "推荐值", "class":"center","width":"5%", "sortable":false,"data": 'recommendSort' },
    { "sortable": false,"class": "center","width":"9%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status=="1"){
	    		var str = '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" href="javascript:void(-1);" title="查看" onclick="showCourseInfoDetail(this,1)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
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
					'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '
	    	}
	    }
	}];

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

	});
	/** 直播课管理 end */

	createImageUpload($('.uploadImg'));//生成图片编辑器
	createDatePicker($("#search_startTime"));
	createDatePicker($("#search_endTime"));
    createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
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
