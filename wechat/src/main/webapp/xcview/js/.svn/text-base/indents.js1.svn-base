   



function dateStr(bbb) {  
  	  var date = new Date(bbb);
      var y = date.getFullYear();  
      var m = date.getMonth() + 1;  
      m = m < 10 ? ('0' + m) : m;  
      var d = date.getDate();  
      d = d < 10 ? ('0' + d) : d;  
      var h = date.getHours();  
      var minute = date.getMinutes();  
      minute = minute < 10 ? ('0' + minute) : minute;  
      return y + '-' + m + '-' + d+' '+h+':'+minute;  
}; 

/**
 *  订单列表：
 */
var pageNumber = 1;
var pageSize = 10;

function initOrderList(status,downOrOn){
	
	if(downOrOn){
		pageNumber = 1;
	}else{
		pageNumber = pageNumber+1;
	}
	var urlParam ={
		pageSize:pageSize,
		status:status,
		userId:localStorage.getItem("userId")	
	}
	urlParam.pageNumber = pageNumber
	
    //未支付
    requestService("/bxg/order/list", urlParam, function(data) {
        if(data.success){
        	var content = document.getElementById("indent_main");
			if(downOrOn){
				content.innerHTML = "";
			}
			var result = data.resultObject;
        	if(result.length>0){
                for(var i=0;i<result.length;i++) {
                	var odiv = document.createElement("div");
					odiv.className = "mui-table-view-cell";
					odiv.id = 'div' + i;
				    var html="";
				    var order = result[i]; 
                	//顶部
                	html+="<div class='indent_main'>"+
        			"<div class='indent_main_top'>"+
        				"<div class='indent_main_top_left'>订单编号：<span>" + order.orderNo + "</span></div>"+
        				"<div class='learning_right'><!-- 下单时间： --><span>" + order.createTime.substring(0,16) + "</span></div>"+
        			"</div>";
                	//课程部分
                	 //循环订单中的所有课程啦
                	for(var j=0;j<order.allCourse.length;j++){
                		var course =order.allCourse[j];
                		var type  =  course.type; // type :1 直播  2视频 3 音频
                		var liveStatus = course.liveStatus;
                		var liveTypeOrState ="直播";  //直播状态 1.直播中，2预告，3直播结束
                		//判断课程的状态啦  
                		if(type == 1){
                			if(liveStatus==1){
                				liveTypeOrState ="直播中";
                			}else if(liveStatus==2){
                				liveTypeOrState ="预告";
                			}else if(liveStatus==3){
                				liveTypeOrState ="回放";
                			}
                		}else if(type == 2){
                			liveTypeOrState ="视频";
                		}else if(type == 3){
                			liveTypeOrState ="音频";
                		}
                		//立即观看、去预约呢，还是直播呢，还是点播呢
                		html+="<div class='indent_main_cen' title="+type+" courseId = "+course.id+" " +
                				"onlineCourse="+course.onlineCourse+" alt="+liveStatus+">"+
            				"<div class='indent_main_cen_left'>"+
            					"<img  src='"+course.smallImgPath+"' alt='' />"+
            					"<p class='indent_main_left_p1'>" + course.gradeName + "</p>"+
            					"<div class='indent_main_one'><p class='indent_main_left_p2'>老师：<span>" + course.teacherName + "</span></p><p class='indent_main_left_p3'>价格:<span>"+course.actualPay+"</span></p><div class='both'></div></div>"+
            					"<div class='indent_main_two'><p class='indent_main_left_p02'>"+liveTypeOrState+"</p><p class='indent_main_left_p03'>课程有效期：不限</p></div>"+
            				"</div>"+
            			"</div>";
                	 }
                	var orderStatus=order.orderStatus;
                	html+="<div class='both'></div>"+
        			"<div class='indent_main_bot' title="+order.id+" id="+order.orderNo+">"+
        				"<div class='indent_main_bot_left'>订单价格：<span>￥<span>"+result[i].actualPay+"</span></span></div>";
        				if(orderStatus == 0){
        					html+="<a href='javascript: ;'   class='indent_main_bot_a common_click gotoPay' >立即购买</a>" +
        							"<a class='order_falg common_click cancelOrder'>取消</a>";
        				}else if(orderStatus == 1){
        					/*html+="<a href='javascript: ;' class='indent_main_bot_a common_click watch'>立即观看</a>";*/
        				}else if(orderStatus == 2){
        					html+="<a href='javascript: ;' class='indent_main_bot_a common_click deleteOrder'>删除订单</a>" +
        							"<a href='javascript: ;' class='indent_main_bot_a common_click toBuy'>重新购买</a>";
        				}
        			"</div>";
        			//已失效标识
        			if(orderStatus == 2){
        				html+="<img src='../images/lose.png' alt='' " +
        						"style='position: absolute;right: 0;top: 50%;width: 2.875rem;height: 2.875rem;margin-top: -1.4675rem;margin-right: 0.4rem;' />";
        			}
        	        "</div>";
        	       odiv.innerHTML = html;
				   content.appendChild(odiv);
                }	
            	mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
				mui('#refreshContainer').pullRefresh().refresh(true);
            }else{
            	var cells = document.body.querySelectorAll('.mui-table-view-cell');
				var num = cells.length;
				console.log(num);
				if (num == "0") {
					document.getElementById("indent_main").innerHTML = "";
					var odiv = document.createElement("div");
					odiv.id = 'content';
					var img_str = "null01.png";
					odiv.innerHTML = '<img style="width:4.675rem;margin-top:4.75rem;" src="/xcviews/images/'+img_str+'" alt="" class="kongbai" />';
					document.getElementById("indent_main").appendChild(odiv);
				} else {
					mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
				}
            }
        }else{
        	alert("网络异常");
        }
    });
}

/**
 * 点击购买的进行观看啦
 */
mui("#refreshContainer").on('tap', '.indent_main_cen', function (event) {
	var div = $(this);
	var liveStatus = div.attr("alt");     // liveStatus
	var type = div.attr("title"); //type
	var courseId = div.attr("courseId"); //type
	var onlineCourse = div.attr("onlineCourse"); //type
	
	if(onlineCourse == 1){
		location.href='/xcviews/html/training.html?id='+courseId;
		return;
	}else{
		if(type == 1){  
			if(liveStatus!=2){ //直播详情
				location.href = "/bxg/xcpage/courseDetails?courseId="+courseId;
				return;
			}else{     //预告
				location.href = "/xcviews/html/foreshow.html?course_id=" + courseId;
				return;
			}
		}else{ //音频或者视频
			location.href = "/xcviews/html/particulars.html?courseId=" + courseId;
			return;
		}
	}
	
	
});

/**
 * 删除订单、取消订单、去购买
 */
mui("#refreshContainer").on('tap', '.common_click', function (event) {
	
	var ev = $(this);
	var className = ev.attr("class");

	var div = $(this).parent();
	var orderNo = div.attr("id");
	var orderId = div.attr("title");
	
	if(className.indexOf("gotoPay")!=-1){ //立即购买
		location.href ="/xcviews/html/pay.html?orderId="+orderId;
	}else if(className.indexOf("cancelOrder")!=-1){//取消订单
		
		$(".history_bg_cen_size").text("确定要取消此订单吗？");
		$(".history_bg_bto1").attr("title","cancelOrder");
		$(".history_bg_bto1").attr("id",orderNo);
		
		$(".history_bg").show();
	}else if(className.indexOf("deleteOrder")!=-1){//删除订单
		$(".history_bg_cen_size").text("确定要删除此订单吗？");
		$(".history_bg_bto1").attr("title","deleteOrder");
		$(".history_bg_bto1").attr("id",orderNo);
		
		$(".history_bg").show();
	}else if(className.indexOf("toBuy")!=-1){ //重新购买  --》订单从已失效变为未支付，可以重新购买。
		/*
		 * 点击重新支付的时候，需要重新生成一个未支付的订单。并且还有一个
		 */
		
	    var btype=   localStorage.getItem("access")
	    var orderFrom;
	    if(btype=='wx'){
	        orderFrom=3;
	    }else if(btype=='brower'){
	        orderFrom=4;
	    }
	    requestService("/bxg/order/saveRegenerate", {orderId : orderId,orderFrom:orderFrom}, function(data) {
	        if (data.success) {
	            var result = data.resultObject;
	            location.href = "/xcviews/html/pay.html?orderNo="+result.orderNo+"&orderId="+result.orderId+"&page=1";
	        }else{
	            alert("提交订单错误！请稍后再试！");
	        }
	    });
	}
});

function deleteOrcancel(){
	
	var title = $(".history_bg_bto1").attr("title");
	var orderNo = $(".history_bg_bto1").attr("id");
	
	var type =0;
	var strSuccess = "取消成功";
	var strError = "取消成功";
	if(title == "cancelOrder"){
		type =1;
		strSuccess="取消成功";
		strError ="取消失败";
	}else if(title == "deleteOrder"){
		type =0;
		strSuccess="删除成功";
		strError ="删除失败";
	}
	requestService("/bxg/order/update", {
		orderNo : orderNo,
		type:type
	},function(data) {
		if (data.success) { //去详情页面
			$(".frequency_div li p").each(function(index, element) {
				var className = element.className;
				if (className.indexOf("indent_p2") != -1) {
					var name = $(this).attr("name");
					initOrderList(name,true);
					return;
				}
			});
		}else{
			alert("网络异常");
		}
	})
}



/**
 * ************************************ 页面刷新下刷新事件
 * **************************************************
 */
mui.init();
mui.init({
	pullRefresh: {
		container: '#refreshContainer',
		down: {
			callback: pulldownRefresh
		},
		up: {
			contentrefresh: '正在加载...',
			callback: pullupRefresh
		}
	}
});
/**
 * 下拉刷新
 */
function pulldownRefresh() {
	setTimeout(function() {
		
		$(".frequency_div li p").each(function(index, element) {
			var className = element.className;
			if (className.indexOf("indent_p2") != -1) {
				var name = $(this).attr("name");
				initOrderList(name,true);
				return;
			}
		});
		mui('#refreshContainer').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 500);
};
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	setTimeout(function() {
		$(".frequency_div li p").each(function(index, element) {
			var className = element.className;
			if (className.indexOf("indent_p2") != -1) {
				var name = $(this).attr("name");
				initOrderList(name,false);
				return;
			}
		});
	}, 500);
}
