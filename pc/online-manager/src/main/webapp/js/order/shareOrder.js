var shareOrderTable;
var shareOrderForm;
var searchJson = new Array();
$(function() {
    loadShareOrderList();
    //下线时间 时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));
});

//列表展示
function loadShareOrderList(){
    var dataFields = [
        {title: '用户名', "class": "center","width":"8%","data": 'createPersonName', "sortable": false},
        {title: '性别', "class": "center", "width": "6%","data": 'sex', "sortable": false,"mRender":function(data,display,row){
        	var sex;
        	if(data==1){
        		sex = "男";
        	}else if(data==2){
        		sex = "女";
        	}
        	return sex;
        }},
        {title: '手机号', "class": "center", "width": "8%","data": 'mobile', "sortable": false},
        {title: '邮箱', "class": "center", "width": "10%" ,"data": 'email', "sortable": false,"mRender":function(data,display,row){
        	if(data==null)return "";
        	return "<span name='emailSpan'>"+data+"</span>";
        }},
        {title: '注册时间', "class": "center", "width": "10%" ,"data": 'createTime', "sortable": false},
        {title: '推荐人(用户名)', "class": "center", "width": "10%" ,"data": 'shareUserName', "sortable": false},
        {title: '总计发展人数', "class": "center", "width": "10%" , "data": 'shareCount', "sortable": false},
        {title: '累计佣金', "class": "center", "width": "6%" ,"data": 'totalShareMoney', "sortable": false},
        {title: '实际提现', "class": "center", "width": "6%" ,"data": 'getShareMoney', "sortable": false},
        {title:"操作","class": "center","width":"8%","data":"id","sortable": false,"mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="佣金记录" onclick="shareOrderDetail(this)">佣金记录</a>';
				buttons += "</div>";
                return buttons;
            }
        }
    ];

    shareOrderTable = initTables("shareOrderTable", basePath + "/order/shareOrder/findShareOrderList", dataFields, true, true, null,null,searchJson,function(data){
    	$("span[name='emailSpan']").each(function(){
    		$(this).parent().attr("title",$(this).text());
    	});
    	
    });

}

 //条件搜索
 function search(){
     searchButton(shareOrderTable);
}

$("#selType").on("change",function(){
	$("#search_mobile").val("");
	$("#search_email").val("");
	$("#search_createPersonName").val("");
	$("#search_sharePersonName").val("");

	if(this.value==0){
		$("#search_mobile").parent().parent().show();
		$("#search_email").parent().parent().hide();
		$("#search_createPersonName").parent().parent().hide();
		$("#search_sharePersonName").parent().parent().hide();
	}else if(this.value==1){
		$("#search_mobile").parent().parent().hide();
		$("#search_email").parent().parent().show();
		$("#search_createPersonName").parent().parent().hide();
		$("#search_sharePersonName").parent().parent().hide();
	}else if(this.value==2){
		$("#search_mobile").parent().parent().hide();
		$("#search_email").parent().parent().hide();
		$("#search_createPersonName").parent().parent().show();
		$("#search_sharePersonName").parent().parent().hide();
	}else if(this.value==3){
		$("#search_mobile").parent().parent().hide();
		$("#search_email").parent().parent().hide();
		$("#search_createPersonName").parent().parent().hide();
		$("#search_sharePersonName").parent().parent().show();
	}
});

function shareOrderDetail(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = shareOrderTable.fnGetData(oo);
	var cpn = aData.createPersonName==null?"":aData.createPersonName;
	window.location.href=basePath+'/home#/order/shareOrder/shareOrderDetail?createPerson='+aData.createPerson+"&createPersonName="+cpn+"&page="+getCurrentPageNo(shareOrderTable);
}
