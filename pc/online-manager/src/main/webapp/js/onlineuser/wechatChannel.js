var _courseTable;
var searchCase;
var courseForm;
var teacherArray=new Array();
var _courseRecTable;


$.validator.addMethod("mobile", function(value, element) {
    debugger
    return this.optional(element) || (/^1(3|4|5|6|7|8)\d{9}$/.test(value))  || (/^0\d{2,3}-?\d{7,8}$/.test(value) );
}, "请输入正确的手机号");

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

    { "title": "创建人", "class":"center","width":"8%","sortable":false,"data": 'createPerson',"mRender":function (data, display, row) {
    		return "<span name='lecturerNameList'>"+data+"</span>";
    } },
    
    { "title": "创建时间", "class":"center","width":"8%","sortable":false,"data": 'createTime',"mRender":function (data, display, row) {
		return getDateTimeFormat(data);
     } },
    
    { "title": "渠道名称", "class":"center","width":"8%", "sortable":false,"data": 'name' },
    
    { "title": "联系人", "class":"center","width":"8%", "sortable":false,"data": 'contact' },
    
    { "title": "联系电话", "class":"center","width":"8%", "sortable":false,"data": 'mobile' },
    
    { "title": "省/市", "class":"center","width":"8%", "sortable":false,"data": 'xmbPrice', "mRender":function (data, display, row) {
    		return "<span name='lecturerNameList'>"+row.province+"/"+ row.city +"</span>";
    } },
    { "title": "二维码", "class":"center","width":"8%", "sortable":false,"data": 'qrCodeImg' ,"mRender":function(data){
		return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;'/>";
	}},
	
	{ "title": "自定义二维码", "class":"center","width":"8%", "sortable":false,"data": 'customQrCodeUrl' ,"mRender":function(data){
		return "<span name='lecturerNameList'>"+data+"</span>";
	}},

    { "sortable": false,"class": "center","width":"10%","title":"操作","mRender":function (data, display, row) {
	    	if(row.status=="1"){
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
	    		'<a class="blue" href="'+row.qrCodeImg+'" title="下载二维码" download="qrcode.jpg"><i class="ace-icon fa fa-paperclip bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> '
	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
	    		'<a class="blue" href="'+row.qrCodeImg+'" title="下载二维码" target="_blank"  ><i class="ace-icon fa fa-paperclip bigger-130"></i></a>'+
				 '<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
				'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> '
	    	}
	    } 
	}];
		
	_courseTable = initTables("courseTable",basePath+"/wechatChannel/list",objData,true,true,2,null,searchCase,function(data){
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
		        	name: {
						required:"渠道名字不可空！",
					},
					contact: {
						required:"联系人不可空！",
					},
					mobile: {
						required:"手机号不可空！",
						isphoneNum:"请填写正确的手机号码",
					}
		        }
		    });
		    updateCourseForm = $("#updateCourse-form").validate({
		    	messages: {
		        	name: {
						required:"渠道名字不可空！",
					},
					contact: {
						required:"联系人不可空！",
					},
					mobile: {
						required:"手机号不可空！",
					}
		        }
		   });
	});
});

//新增框
$(".add_bx").click(function(){
	courseForm.resetForm();
	
	
	$("#add-directIdDiv").hide();
	$("#add-externalLinksDiv").hide();

	var dialog = openDialog("addCourseDialog","dialogAddCourseDiv","新增渠道",400,400,true,"确定",function(){
		
		if($("#addCourse-form").valid()){
			mask();
			 $("#addCourse-form").attr("action", basePath+"/wechatChannel/addWechatChannel");
	            $("#addCourse-form").ajaxSubmit(function(data){
	            	
	            	debugger;
	            	
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

function getDateTimeFormat(data){
	//微信性别  --》 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	if(data && data != ''){
		var d = new Date(data);
		return d.format('yyyy-M-d hh:mm:ss');
	}
}


//修改
function toEdit(obj){
	debugger;
	updateCourseForm.resetForm();
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow

	$("#updateCourse-form :input").not(":button, :submit, :radio").val("").removeAttr("checked").remove("selected");//核心
	
	//<input type="hidden" id="editChannel_id"  name="id" class="col-xs-10 col-sm-8 {required:true}">
	$("#editChannel_id").val(row.id); //充值id
	
//	debugger;
	$("#editName_id").val(row.name); //充值id
	$("#editContact_id").val(row.contact); //充值价格
	$("#editMobile_id").val(row.mobile); //充值价格

	
	$("#qrCodeImg").val(row.qrCodeImg); //
	$("#customQrCodeUrl").text(row.customQrCodeUrl); //

	
	
	//省市区
//	var address = result[0].address;
//	var p_c_a = address.split("-");
//	if(p_c_a.length==3){
		//省
		for(i=0;i<$("#edit_province option").length;i++){
    		if($("#edit_province option").eq(i).val()==row.realProvince){
    			$("#edit_province option").eq(i).attr("selected",true);
    			break;
    		}
    	}
		
		$('#edit_province').val(row.realProvince);
		
		
		$("#edit_citys").empty();
		$("#edit_county").empty();
		
		
		$('#edit_province').trigger("change");
		$("#edit_citys").val(row.realCitys);
		
		$("#edit_citys").trigger("change");
		$("#edit_county").val(row.realCounty);
		
//		var city = "<option id='edit_chooseCity' value='-1'>请选择您所在城市</option><option id='"+row.realCitys+"' selected='selected'>"+row.city+"</option>";
//		$("#edit_citys").append(city);
//		$("#edit_citys option").eq(1).attr("selected",true);
//		//$("#edit_citys").val(row.realCitys);
		
//		var county = "<option id='edit_chooseCounty' value='-1'>请选择您所在县区</option><option id='"+row.realCounty+"' selected='selected'>"+row.area+"</option>";
//		$("#edit_county").append(county);
//		$("#edit_county option").eq(1).attr("selected",true);
		
		//$("#edit_county").val(row.realCounty);
		
		$("#edit_realProvince").val(row.province);
		$("#edit_realCitys").val(row.city); 
		$("#edit_realCounty").val(row.area);
//	}
	
	
	
	
	var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv","修改渠道",700,550,true,"确定",function(){
		
		if($("#updateCourse-form").valid()){
			mask();
            $("#updateCourse-form").attr("action", basePath+"/wechatChannel/updateWechatChannelById");
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
	
	
}



/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var row = _courseTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/wechatChannel/updateStatus",{"id":row.id},function(data){
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
	deleteAll(basePath+"/wechatChannel/deletes",_courseTable);
});

$(".fencheng_bx").click(function(){
	setBrokerage(basePath+"/wechatChannel/deletes",_courseTable);
});

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
	ajaxRequest(basePath+'/wechatChannel/upMove',{"id":aData.id},function(res){
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
	ajaxRequest(basePath+'/wechatChannel/downMove',{"id":aData.id},function(res){
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
