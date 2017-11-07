var shareOrderDetailTable;
var shareOrderForm;
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
    
    loadShareOrderDetailList();
    //下线时间 时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));
});

//列表展示
function loadShareOrderDetailList(){
    var dataFields = [
        {title: '分销订单号', "class": "center","width":"8%","data": 'shareOrderNo', "sortable": false},
        {title: '课程名称', "class": "center", "width": "6%","data": 'courseName', "sortable": false},
        {title: '购买者（用户名）', "class": "center", "width": "8%","data": 'createPersonName', "sortable": false},
        {title: '实际支付（元）', "class": "center", "width": "10%" ,"data": 'actualPay', "sortable": false,"mRender":function(data,display,row){
        	return "<span style='color:blue;'>"+data.formatMoney()+"</span>";
        }},
        {title: '支付时间', "class": "center", "width": "10%" ,"data": 'payTime', "sortable": false},
//        {title: '佣金级别', "class": "center", "width": "6%","data": 'level', "sortable": false,"mRender":function(data,display,row){
//        	var sex;
//        	if(data==0){
//        		sex = "一级";
//        	}else if(data==1){
//        		sex = "二级";
//        	}else if(data==2){
//        		sex = "三级";
//        	}
//        	return sex;
//        }},
        {title: '佣金数额', "class": "center", "width": "6%" , "data": 'subsidies', "sortable": false,"mRender":function(data,display,row){
        	return "<span style='color:red;'>"+data.formatMoney()+"</span>";
        }}
    ];
    
    searchJson = getSearchGroup();
	str = "[" + searchJson.join(",") + "]";
	
    shareOrderDetailTable = initTables("shareOrderDetailTable", basePath + "/order/shareOrder/findShareOrderListDeatil", dataFields, true, true, null,null,searchJson,function(data){
    });
}

 //条件搜索
 function search(){
     searchButton(shareOrderDetailTable);
}

$("#selType").on("change",function(){
	$("#search_shareOrderNo").val("");
	$("#search_courseName").val("");
	$("#search_createPersonName").val("");

	if(this.value==0){
		$("#search_shareOrderNo").parent().parent().show();
		$("#search_courseName").parent().parent().hide();
		$("#search_createPersonName").parent().parent().hide();
	}else if(this.value==1){
		$("#search_shareOrderNo").parent().parent().hide();
		$("#search_courseName").parent().parent().show();
		$("#search_createPersonName").parent().parent().hide();
	}else if(this.value==2){
		$("#search_shareOrderNo").parent().parent().hide();
		$("#search_courseName").parent().parent().hide();
		$("#search_createPersonName").parent().parent().show();
	}
});