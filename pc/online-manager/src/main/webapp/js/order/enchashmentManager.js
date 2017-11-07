var orderTable;
var searchJson = new Array();

$(function() {
	
	Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
        places = !isNaN(places = Math.abs(places)) ? places : 2;
        symbol = symbol !== undefined ? symbol : "";
        thousand = thousand || ",";
        decimal = decimal || ".";
        var number = this,
            negative = number < 0 ? "-" : "",
            i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
            j = (j = i.length) > 3 ? j % 3 : 0;
        return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
    };
	
    //下线时间 时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));
});

$(function(){
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
        /*{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
            return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
        }},*/
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '订单号', "class": "center", "width": "8%","data": 'id', "sortable": false},
        {title: '订单状态', "class": "center", "width": "6%","data": 'enchashmentStatus', "sortable": false,"mRender":function(data,display,row){
            if(data==0){
                return "未处理";
            }else if(data == 1){
                return "已打款";
            }else if(data == 2){
                return "已驳回";
            }
        }},
        {title: '提现详情', "class": "center", "width": "5%","data": 'courseName', "sortable": false,"mRender":function(data,display,row){
            return "<a href='jvascript:void(0);' style='color: blue;cursor: pointer' onclick='previewDialog(this);return false;'>查看</a>";
        }},
        
        {title: '申请时间', "class": "center", "width": "10%","data": 'time', "sortable": false,"mRender":function(data,display,row){
            return getLocalTime(data);
        }},
        {title: '提现金额(元)', "class": "center", "width": "97px","data": 'enchashmentSum', "sortable": false,"mRender":function(data,display,row){
            return "<span style='color:red'>"+data+"</span>";
        }},
        {title: '可提现余额(元)', "class": "center", "width": "97px","data": 'enableEnchashmentBalance', "sortable": false,"mRender":function(data,display,row){
            return "<span style='color:red'>"+data+"</span>";
        }},
        {title: '提现来源', "class": "center", "width": "6%","data": 'clientType', "sortable": false,"mRender":function(data,display,row){
            if(data == 1){
                return "PC";
            }else if(data == 2){
                return "H5";
            }else if(data == 3){
                return "APP";
            }
        }},
        {title: '提现人（账号）', "class": "center", "width": "8%","data": 'loginName', "sortable": false},
//        {title: '提现方式', "class": "center", "width": "10%","data": 'enchashmentAccountType', "sortable": false,"mRender":function(data,display,row){
//            var payType ;
//            if(data == 0){
//                payType = "支付宝";
//            }else if(data == 1){
//                payType = "微信支付";
//            }else if(data == 2){
//                payType = "网银支付";
//            }else{
//                payType= "-- --"
//            }
//            return payType;
//        }},
        { "sortable": false,"class": "center","width":"7%","title":"操作","mRender":function (data, display, row) {
                if(row.enchashmentStatus==0){
                    return "<a  style='color: blue;cursor: pointer' onclick='dakuan("+row.id+")'>打款</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  style='color: blue;cursor: pointer' onclick='bohui("+row.id+")'>驳回</a>";
                }else if(row.enchashmentStatus==1){
                    return '<span>已打款</span>';
                }else{
                    return '<span>已驳回</span>';
                }
            } 
        },
        {title: '备注', "class": "center", "width": "10%", "sortable": false,"mRender":function(data,display,row){
        	 var payType ;
             if(row.enchashmentAccountType == 0){
                 payType = "支付宝";
             }else if(row.enchashmentAccountType == 1){
                 payType = "微信支付";
             }else if(row.enchashmentAccountType == 2){
                 payType = "网银支付";
             }
        	
            var remark ;
            if(row.enchashmentStatus == 0){
                remark = "";
            }else if(row.enchashmentStatus == 1){
                remark = "<span style='color:green'>"+payType+"付款("+"<a style='color: blue;cursor: pointer' onclick='yfk(this)'>查看</a>)\n"+getLocalTime(row.ticklingTime)+"</span>";
            }else if(row.enchashmentStatus == 2){
                remark = "<span style='color:green'>已驳回("+"<a style='color: blue;cursor: pointer' onclick='ybh(this)'>查看</a>)\n"+getLocalTime(row.ticklingTime)+"</span>";
            }
            return remark;
        }}];
    orderTable = initTables("orderTable", basePath + "/order/enchashmentManager/findEnchashmentList", dataFields, true, true, 1,null,searchJson,function(data){
        return false;
    });
});

function yfk(obj){
	debugger;
	var oo = $(obj).parent().parent().parent();
	var row = orderTable.fnGetData(oo); // get datarow
	if(row.enchashmentAccountType==0){
		payType="支付宝";
	}else if(row.enchashmentAccountType==1){
		payType="微信";
	}else if(row.enchashmentAccountType==2){
		payType="网银";
	}
	
	console.info(row.realName);
	$("#s_payType").html(payType);
	$("#s_payAccount").html(row.enchashmentAccount);
	$("#s_payPrice").html(row.enchashmentSum);
	$("#s_payTime").html(getLocalTime(row.ticklingTime));
	var dialog = openDialogNoBtnName("showyfkDialog","showyfkDiv","已打款",400,350,true,"关闭",null);
}

function ybh(obj){
	debugger;
	var oo = $(obj).parent().parent().parent();
	var row = orderTable.fnGetData(oo); // get datarow
	var ct=null;
	var c=row.causeType;
	if(c==0){
		ct="未能与提现人取得联系";
	}else if(c==1){
		ct="支付宝账号有误";
	}else if(c==2){
		ct="微信账号有误";
	}else if(c==3){
		ct="手机号有误";
	}else if(c==4){
		ct="姓名有误";
	}else if(c==5){
		ct="其他";
	}
	console.info(row.realName);
	$("#s_bhcause").html(ct);
	$("#s_operateRemark").html(row.operateRemark);
	var dialog = openDialogNoBtnName("showybhDialog","showybhDiv","已驳回",400,300,true,"关闭",null);
}

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

//修改
function dakuan(id){
	debugger
	$('#startTime_edit').datepicker( "option" , {
		 minDate: null,
		 maxDate: null} );
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
	$("#dakuan_id").val(id);
	var dialog = openDialog("EditCourseDialog","dialogEditCourseDiv","打款",380,350,true,"确定",function(){
		if($("#dakuan-form").valid()){
			mask();
            $("#dakuan-form").attr("action", basePath+"/order/enchashmentManager/handleEnchashment");
            $("#dakuan-form").ajaxSubmit(function(data){
            	try{
            		data = jQuery.parseJSON(jQuery(data).text());
            	}catch(e) {
            		data = data;
            	  }
            
                unmask();
                if(data.success){
                    $("#EditCourseDialog").dialog("close");
                    layer.msg(data.errorMessage);
                     freshTable(orderTable);
                }else{
                	 layer.msg(data.errorMessage);
                }
            });
		}
	});
}

//驳回
function bohui(id){
	$("#bohui_id").val(id);
	var dialog = openDialog("EditbohuiDialog","dialogbohuiDiv","驳回",380,350,true,"确定",function(){
		if($("#bohui-form").valid()){
			mask();
			$("#bohui-form").attr("action", basePath+"/order/enchashmentManager/handleEnchashment");
			$("#bohui-form").ajaxSubmit(function(data){
				try{
					data = jQuery.parseJSON(jQuery(data).text());
				}catch(e) {
					data = data;
				}
				
				unmask();
				if(data.success){
					$("#EditbohuiDialog").dialog("close");
					layer.msg(data.errorMessage);
					freshTable(orderTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
	
}


function search(){
	searchButton(orderTable);
};

updateCourseForm = $("#updateCourse-form").validate({
	messages: {
		operateRemark: {
			digits: "备注不能为空！"
		},
		time: {
			required:"时间不能为空！",
		},
	}
});
$("#selType").on("change",function(){
	$("#search_orderNo").val("");
	$("#search_createPersonName").val("");

	if(this.value==1){
		$("#search_courseName").parent().parent().hide();
		$("#search_orderNo").parent().parent().show();
		$("#search_createPersonName").parent().parent().hide();
	}else if(this.value==2){
		$("#search_courseName").parent().parent().hide();
		$("#search_orderNo").parent().parent().hide();
		$("#search_createPersonName").parent().parent().show();
	}
});

//查看
function previewDialog(obj){
	debugger;
	var oo = $(obj).parent().parent();
	var row = orderTable.fnGetData(oo); // get datarow
	$("#s_name").html(row.realName);
	$("#s_phone").html(row.phone);
	$("#s_zfb").html(row.enchashmentAccount);
	$("#s_price").html(row.enchashmentSum);

	var dialog = openDialogNoBtnName("showOrderDialog","showOrderDiv","订单详情",400,350,true,"关闭",null);
};

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');    
}