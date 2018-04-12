

/**
 * 截取url传递的参数
 * @param name 传递 key  得到value 
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/**
 *  1、主讲人介绍
 *  2、主播介绍
 */
var type = getQueryString('type');
var typeId = getQueryString('typeId');

if(type == 4){
	$("#real_jieshao").show();
}else{
	$("#real_jieshao").hide();
}



//ajax统一请求
function requestService(url, param, callback, ac) {
	if (ac == null)
		ac = true;// 默认异步
//    if(document.location.host.indexOf('dev.ixincheng.com')!=-1){
//        url = "/apis"+url;
//    }
	mui.ajax({
		url : url,
		type : "post",
		data : param,
		async : ac,
		success : function(msg) {
			if (callback) {
				callback(msg);
			}
		},
		error : function(msg) {
			// alert(msg);
		}
	});
}

requestService("/xczh/common/richTextDetails",{
	type:type,
	typeId:typeId
},function(data) {
	
	if(type ==1 ){ // 展示页面  课程详情
		if(data.resultObject==''||data.resultObject==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject);
		}
	}else if(type == 2){ //主讲人  --》 直播间主讲人
		if(data.resultObject==''||data.resultObject==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject);
		}
	}else if(type == 3){  //展示页面  主讲人
		if(data.resultObject==''||data.resultObject==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject);
		}
	}else if(type == 4){  //主播详情页面 --》 介绍 
		if(data.resultObject==''||data.resultObject==null){
			$("#jieshao").hide()			
		}else{
			$(".user_mywrite").html(data.resultObject);
		}
	}
	
	var aa= $(".user_mywrite").css("height");
	var scrollHeight=document.getElementById("aaaaa").scrollHeight;
	
	var testEle = document.getElementById("aaaaa") 
	testEle.setAttribute("test",scrollHeight); // 设置 
	var aaa = testEle.getAttribute("test"); //获取
	
	//alert(aa+"========"+scrollHeight+"======="+aaa);
})

