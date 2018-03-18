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
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '订单号', "class": "center", "width": "12%","data": 'orderNoRecharge', "sortable": false},
        {title: '创建时间', "class": "center", "width": "10%","data": 'createTime',"mRender":function(data,display,row){
        	return getLocalTime(data);
        }},
        {title: '充值数量', "class": "center", "width": "8%","data": 'value',"sortable":false},
        {title: '订单来源', "class": "center", "width": "8%","data": 'orderFrom', "sortable": false,"mRender":function(data,display,row){
        	var orderForm = "";//1.pc 2.h5 3.android 4.ios
        	debugger;
        	if(data == 1){
        		orderForm = "pc";
        	}else if(data == 2){
        		orderForm = "h5";
        	}else if(data == 3){
                orderForm = "android";
            }else if(data == 4){
                orderForm = "ios";
            }
        	return orderForm;
        }},
        {title: '充值者(用户名)', "class": "center", "width": "8%","data": 'userId', "sortable": false},
        {title: '支付方式', "class": "center", "width": "10%","data": 'payType', "sortable": false,"mRender":function(data,display,row){
        	var payType ;// 0.支付宝1.微信2.苹果
        	if(data == 0){
        		payType = "支付宝";
            }else if(data == 1){
            	payType = "微信支付";
            }else if(data == 2){
            	payType= "苹果内购"
            }else{
            	payType= "-- --"
            }
        	return payType;
        }}];
    orderTable = initTables("orderTable", basePath + "/recharge/findRechargeList", dataFields, true, true, 1,null,searchJson,function(data){
    	return false;
    });
}

 //条件搜索
 function search(){
     searchButton(orderTable);
}

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
function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');    
}