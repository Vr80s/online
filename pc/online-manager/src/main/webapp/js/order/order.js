var orderTable;
var orderForm;
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
	
    loadOrderList();
    //下线时间 时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));
});

//列表展示
function loadOrderList(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
		/*{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
		    return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
		}},*/
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '订单内容', "class": "center","data": 'courseName', "sortable": false,"mRender":function(data,display,row){
        	return "<a href='jvascript:void(0);' style='color: blue;' onclick='previewDialog(this);return false;'>"+data+"</a>";
        }},
        {title: '订单号', "class": "center", "width": "12%","data": 'orderNo', "sortable": false},
        {title: '订单类型', "class": "center", "width": "8%","data": 'orderFrom', "sortable": false,"mRender":function(data,display,row){
        	var orderForm = "";
        	if(data == 0){
        		orderForm = "官网订单";
        	}else if(data == 1){
        		orderForm = "分销订单";
        	}else if(data == 2){
                orderForm = "线下录入";
            }else if(data == 3){
                orderForm = "微信";
            }else if(data == 4){
                orderForm = "h5";
            }else if(data == 5){
                orderForm = "app";
            }
        	return orderForm;
        }},
        {title: '创建时间', "class": "center", "width": "10%","data": 'createTime', "sortable": false},
        {title: '优惠方式', "class": "center", "width": "8%","data": 'preferentyWay',"visible":false},
        {title: '实际支付(元)', "class": "center", "width": "97px","data": 'actualPay', "sortable": false,"mRender":function(data,display,row){
        	return "<span style='color:red'>"+data.formatMoney()+"</span>";
        }},
        {title: '购买者(昵称)', "class": "center", "width": "8%","data": 'createPersonName', "sortable": false},
		{title: '购买者(用户名)', "class": "center", "width": "8%","data": 'createPerson', "sortable": false},
        {title: '支付方式', "class": "center", "width": "10%","data": 'payType', "sortable": false,"mRender":function(data,display,row){
        	var payType ;
        	if(data == 0){
        		payType = "支付宝\n"+row.payTime;
            }else if(data == 1){
            	payType = "微信支付\n"+row.payTime;
            }else if(data == 2){
            	payType = "网银支付\n"+row.payTime;
            }else{
            	payType= "-- --"
            }
        	return payType;
        }},
        {title: '佣金总计(元)', "class": "center", "width": "10%","data": 'subsidies', "sortable": false,"mRender":function(data,display,row){
        	return data.formatMoney();
        }},
        {title: '订单状态', "class": "center", "width": "8%", "data": 'orderStatus', "sortable": false,"mRender":function(data,display,row){
        	var status ;
        	if(data == 0){
        		status = "<span style='color:red'>未付款</span>";
            }else if(data == 1){
            	status = "<span style='color:green'>已付款</span>";
            }else if(data == 2){
            	status = "已关闭";
            }
        	return status;
        }}];
    orderTable = initTables("orderTable", basePath + "/order/order/findOrderList", dataFields, true, true, 1,null,searchJson,function(data){
    	return false;
    });
}

 //条件搜索
 function search(){
     searchButton(orderTable);
}

 /**
  * 批量逻辑删除
  * 
  */
$(".dele_bx").click(function(){
 	deleteAll(basePath+"/order/order/deletes",orderTable);
});

$("#selType").on("change",function(){
	$("#search_courseName").val("");
	$("#search_orderNo").val("");
	$("#search_createPersonName").val("");

	if(this.value==0){
		$("#search_courseName").parent().parent().show();
		$("#search_orderNo").parent().parent().hide();
		$("#search_createPersonName").parent().parent().hide();
	}else if(this.value==1){
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
	var oo = $(obj).parent().parent();
	var row = orderTable.fnGetData(oo); // get datarow
    
	$("#show_orderNo").text(row.orderNo);
	var ordreStatus ;
	if(row.orderStatus == 0){
		ordreStatus = "未支付";
    }else if(row.orderStatus == 1){
    	ordreStatus = "已支付";
    }else{
    	ordreStatus = "已关闭";
    }

	$("#show_orderStatus").text(ordreStatus);
	$("#show_courseName").text(row.courseName);
	$("#show_createPersonName").html("<span style='color:blue;font-size: medium'>"+row.createPersonName+"</span>");
	var preferentyMoney = 0;
	var actualPay = 0;
	if(row.preferentyMoney > 0){
		preferentyMoney = row.preferentyMoney;
	}
	
	if(row.actualPay > 0){
		actualPay = row.actualPay;
	}

	$("#show_totalPrice").html("<span style='color:red;font-size: medium'>"+(preferentyMoney+actualPay).formatMoney()+"&nbsp;元</span>");
	
	$(".autoTr").remove();
	$.ajax({
		  url:"/order/order/getOrderPreferenty",
		  type:"get",
		  data:{orderNo:row.orderNo},
		  success:function(result){
			  if(result.success){
				  var str="<tr class='autoTr'><th>课程名称</th><th>课程价格（元）</th><th>优惠方式</th><th>应付金额（元）</th></tr>";
				  var resuts = result.resultObject;
				  var sum = 0;
				 for(var i=0;i<resuts.length;i++){
					 sum=sum +resuts[i].actualPay;
					var pref = (resuts[i].preferenty==null?"":resuts[i].preferenty);
					str=str+"<tr class='autoTr'><td >"+resuts[i].courseName+"</td><td>"+resuts[i].price+"</td><td>"+ pref +"</td><td>"+resuts[i].actualPay+"</td></tr>"
					
				  }
				 var foot="<tr class='autoTr'><td colspan='3'>合计</td><td>"+resuts[0].totalPay+"</td></tr>";
				 $("#showCoursePreferenty").append(str);
				 $("#showCoursePreferenty").append(foot);
			
				 $("#showCoursePreferenty").rowspan(2);//传入的参数是对应的列数从0开始，哪一列有相同的内容就输入对应的列数值  
				 
			  }
		  }
	  })
	
	  
	  
	//$("#show_preferentyWay").text(row.preferentyWay);

	//$("#preferenty_money").html("<span style='color:red;font-size: medium'>"+preferentyMoney.formatMoney()+"&nbsp;元</span>");
	$("#show_actualPay").html("<span style='color:red;font-size: medium'>"+actualPay.formatMoney())+"&nbsp;元</span>";
	
	var payType ;
	if(row.payType == 0){
		payType = "微信支付";
    }else if(row.payType == 1){
    	payType = "支付宝";
    }else if(row.payType == 2){
    	payType = "网银支付";
    }

	$("#show_payType").text(payType);
	$("#show_payAccount").text(row.payAccount);
	$("#show_createTime").text(row.createTime);
	$("#show_payTime").text(row.payTime);
	
	
	 $("#show_firstMoney").html("<span style='color:red;font-size: medium'>0</span>&nbsp;元");
	 $("#show_firstUser").html("<span style='color:blue;font-size: medium'>无</span>");
	
	 $("#show_secMoney").html("<span style='color:red;font-size: medium'>0</span>&nbsp;元");
	 $("#show_secUser").html("<span style='color:blue;font-size: medium'>无</span>");
	  
	 $("#show_thirdMoney").html("<span style='color:red;font-size: medium'>0</span>&nbsp;元");
	 $("#show_thirdUser").html("<span style='color:blue;font-size: medium'>无</span>");
	 
	 $("#show_totalMoney").html("<span style='color:red;font-size: medium'>0</span>&nbsp;元");

	$.ajax({
		  url:"/order/shareOrder/getShareOrderDetail",
		  type:"get",
		  data:{orderNo:row.orderNo},
		  success:function(result){
			  if(result.success){
				  var  total = 0;
				  var resuts = result.resultObject;
				  for(var i=0;i<resuts.length;i++){
					  if(resuts[i].level == 0){
						  total += resuts[i].subsidies;
						  $("#show_firstMoney").html("<span style='color:red;font-size: medium'>"+resuts[i].subsidies.formatMoney()+"</span>&nbsp;元");
						  $("#show_firstUser").html("<span style='color:blue;font-size: medium'>"+resuts[i].name+"("+resuts[i].loginName+")"+"</span>");
					  }else if(resuts[i].level == 1){
						  total += resuts[i].subsidies;
						  $("#show_secMoney").html("<span style='color:red;font-size: medium'>"+resuts[i].subsidies.formatMoney()+"</span>&nbsp;元");
						  $("#show_secUser").html("<span style='color:blue;font-size: medium'>"+resuts[i].name+"("+resuts[i].loginName+")"+"</span>");
					  }else if(resuts[i].level == 2){
						  total += resuts[i].subsidies;
						  $("#show_thirdMoney").html("<span style='color:red;font-size: medium'>"+resuts[i].subsidies.formatMoney()+"</span>&nbsp;元");
						  $("#show_thirdUser").html("<span style='color:blue;font-size: medium'>"+resuts[i].name+"("+resuts[i].loginName+")"+"</span>");
					  }
				  }
				  $("#show_totalMoney").html("<span style='color:red;font-size: medium'>"+total.formatMoney()+"</span>&nbsp;元");
			  }
		  }
	  })


	var dialog = openDialogNoBtnName("showOrderDialog","showOrderDiv","订单详情",750,630,true,"关闭",null);
};


jQuery.fn.rowspan = function(colIdx) {//封装jQuery小插件用于合并相同内容单元格(列)  
    return this.each(function(){   
        var that;  
        var thatNext;//相邻单元格
        var sum=0;
        $('tr', this).each(function(row) {   
            $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {   
                if (that!=null && $(this).html() == $(that).html()) {   
                    rowspan = $(that).attr("rowSpan");
                    rowspanNext = $(thatNext).attr("rowSpan");
                    if (rowspan == undefined) {   
                        $(that).attr("rowSpan",1);   
                        rowspan = $(that).attr("rowSpan"); 
                        
                        $(thatNext).attr("rowSpan",1);   
                        rowspanNext = $(that).attr("rowSpan"); 
                    }   
                    console.log($(this).html().split("减")[1].split("元")[0])
                    rowspan = Number(rowspan)+1;   
                    $(that).attr("rowSpan",rowspan);   
                    $(this).hide(); 
                    
                    rowspanNext = Number(rowspanNext) + 1;
                    sum+=parseFloat($(this).next().html())
					$(thatNext).attr("rowSpan", rowspanNext);
                   
					$(thatNext).html((sum-$(this).html().split("减")[1].split("元")[0]).toFixed(2));
					$(this).next().hide();

                } else {   
                    that = this; 
                    thatNext = $(this).next();
                    sum=parseInt($(thatNext).html())
                }   
            });   
        });   
    });   
}   
