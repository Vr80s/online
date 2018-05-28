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
        {title: '订单号', "class": "center", "width": "12%","data": 'id', "sortable": false},
        {title: '单价', "class": "center", "width": "97px","data": 'price', "sortable": false},
        {title: '赠送人', "class": "center", "width": "8%","data": 'giver', "sortable": false},
        {title: '接收人', "class": "center", "width": "8%","data": 'receiver', "sortable": false},
        {title: '支付方式', "class": "center", "width": "10%","data": 'payType', "sortable": false,"mRender":function(data,display,row){
        	var payType ;
        	if(data == 0){
        		payType = "支付宝\n"+row.createTime;
            }else if(data == 1){
            	payType = "微信支付\n"+row.createTime;
            }else if(data == 2){
            	payType = "网银\n"+row.createTime;
            }else{
            	payType= "-- --"
            }
        	return payType;
        }},
        {title: '客户端', "class": "center", "width": "10%","data": 'clientType', "sortable": false,"mRender":function(data,display,row){
        	var payType ;
        	if(data == 1){
        		payType = "pc端";
        	}else if(data == 2){
        		payType = "app端";
        	}else if(data == 3){
        		payType = "h5端";
        	}else {
        		payType = "-- --";
        	}
        	return payType;
        }}];
    orderTable = initTables("orderTable", basePath + "/rewardStatement/list", dataFields, true, true, 1,null,searchJson,function(data){
    	return false;
    });
}

 //条件搜索
 function search(){
     searchButton(orderTable);
}


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
