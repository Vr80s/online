(function($) {
	mui(".navtab").on('tap', 'a', function() {
		document.location.href = this.href;
	})
})(mui);

function show(str){
/*	var str ="";
	document.getElementById("nihao").innerHTML=str;
	setTimeout(function(){
		
		str +="<div id='popup'>"+
			"<div class='popup_bg'></div>"+
			"<div class='popup_div' id='nihao'></div>"+
			"</div>";
		document.getElementById("popup").style.display="none";
	},3000);	*/
}


/**
 * 得到  online-web 的测试或者生成域名 
 * @returns
 */
function getServerHost(){
	var server_domain = sessionStorage.server_domain;
	if(!stringnull(server_domain)){
		requestService("/bxg/common/getDomain", null, function(data) {
			if (data.success) {
				sessionStorage.setItem("server_domain",data.resultObject);
				server_domain =data.resultObject;
			}
		},false)
	}else{
		return server_domain;
	}
	if(!stringnull(server_domain)){
		//alert("1234");
		return "http://www.ixincheng.com"; 
	}
	return server_domain; 
}


/*
 * 判断是否来自分享啦网站链接用微信打开的啦
 */
var accessCommon = localStorage.access;
// 如果来自微信端的话
if (accessCommon == "wx") {
	var openid = localStorage.openid;
	if (openid == undefined || openid == "" || openid == null) {
		// 请求下后台，获取url。进行转发啦！
		requestService("/bxg/wxjs/h5BsGetCodeUrl ", null, function(data) {
			if (data.success) {
				var getCodeUrl = data.resultObject;
				window.location.href = getCodeUrl;
			}
		})
	}
}
/**
 * courseId 课程id
 * falg： false 官网   ture课程页面
 *  
 */ 
function h5PcConversions(falg,courserId){
	var browser={
			versions:function(){   
				var u = navigator.userAgent, app = navigator.appVersion;   
				return {//移动终端浏览器版本信息   
					trident: u.indexOf('Trident') > -1, //IE内核  
					presto: u.indexOf('Presto') > -1, //opera内核  
					webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核  
					gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
					mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端  
					ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
					android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器  
					iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器  
					iPad: u.indexOf('iPad') > -1, //是否iPad    
					webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部  
					weixin: u.indexOf('MicroMessenger') > -1, //是否微信   
					qq: u.match(/\sQQ/i) == " qq" //是否QQ  
				};
			}(),  
			language:(navigator.browserLanguage || navigator.language).toLowerCase()  
	}
	if(!(browser.versions.mobile || browser.versions.ios || browser.versions.android ||   
			browser.versions.iPhone || browser.versions.iPad)){    
		
		 var nihao   = getServerHost();
		if(falg ){//ture
			window.location = nihao+"/course/courses?courseId="+courserId
		}else{
			window.location = nihao;
		}
		return false;
	}else{
		return true;
	}
}

var current = location.href;
var domain = window.location.host;
if(current.indexOf("/xcviews/html/share.html")==-1
		&& current.indexOf("/xcviews/html/foreshow.html")==-1
		&& current.indexOf("/bxg/xcpage/courseDetails")==-1
		&& current.indexOf("/xcviews/html/particulars.html")==-1){
	h5PcConversions(false);
}

/**
 * 控制cookie
 */
var cookie = {
	    set:function(key,val,time){//设置cookie方法
	        var date=new Date(); //获取当前时间
	        var expiresDays=time;  //将date设置为n天以后的时间
	        date.setTime(date.getTime()+expiresDays*24*3600*1000); //格式化为cookie识别的时间
	        document.cookie=key + "=" + val +";expires="+date.toGMTString();  //设置cookie
	    },
	    get:function(key){//获取cookie方法
	        /*获取cookie参数*/
	        var getCookie = document.cookie.replace(/[ ]/g,"");  //获取cookie，并且将获得的cookie格式化，去掉空格字符
	        var arrCookie = getCookie.split(";")  //将获得的cookie以"分号"为标识 将cookie保存到arrCookie的数组中
	        var tips;  //声明变量tips
	        for(var i=0;i<arrCookie.length;i++){   //使用for循环查找cookie中的tips变量
	            var arr=arrCookie[i].split("=");   //将单条cookie用"等号"为标识，将单条cookie保存为arr数组
	            if(key==arr[0]){  //匹配变量名称，其中arr[0]是指的cookie名称，如果该条变量为tips则执行判断语句中的赋值操作
	                tips=arr[1];   //将cookie的值赋给变量tips
	                return tips;
	                break;   //终止for循环遍历
	            }
	        }
	    },    
	    delete1:function(key){ //删除cookie方法
	         var date = new Date(); //获取当前时间
	         date.setTime(date.getTime()-10000); //将date设置为过去的时间
	         document.cookie = key + "=v; expires =" +date.toGMTString();//设置cookie
	         return tips;
	    }
}
/**
 * 得到这个cookie,如果没有过期，那么就
 */
//登录页面、预约页面、详情页面、分享页面都不需要  /xcviews/html/share.html,/xcviews/html/foreshow.html,/xcviews/html/my.html
//http://localhost:58080/bxg/xcpage/courseDetails?courseId=410


if(current.indexOf("https")!=-1){
	domain = "https://"+domain+"/";
}else{
	domain = "http://"+domain+"/";
}
/*alert(current+"==============="+domain);*/
if(current.indexOf("/bxg/page/login/")==-1 
		&& current.indexOf("/bxg/page/index/")==-1 
		&& current.indexOf("/xcviews/html/share.html")==-1
		&& current.indexOf("/xcviews/html/foreshow.html")==-1
		&& current.indexOf("/xcviews/html/my.html")==-1
		&& current.indexOf("/bxg/xcpage/courseDetails")==-1
		&& current.indexOf("/xcviews/html/particulars.html")==-1
		&& current.indexOf("/bxg/page/reg")==-1  
		&& current.indexOf("/xcviews/html/find.html")==-1
		&& current.indexOf("/bxg/page/forgotPassword")==-1 
		&& current.indexOf("/xcviews/html/personalfor.html")==-1
		&& current.indexOf("/xcviews/html/complaint.html")==-1
		&& current.indexOf("/xcviews/html/complaint_details.html")==-1
		&& current.indexOf("/xcviews/html/index.html")==-1
		&& current != domain){
	
	
	var user_cookie = cookie.get("_uc_t_");
	if(user_cookie == null){  //去登录页面
		location.href = "/bxg/page/login/1";
	}
}


//ajax统一请求
function requestService(url, param, callback, ac) {
	if (ac == null)
		ac = true;// 默认异步
	
	mui.ajax({
		url : url,
		type : "post",
		data : param,
		async : ac,
		success : function(msg) {
			if(msg.code == 1002){  //过期
				location.href = "/bxg/page/login/1";
			}else if(msg.code == 1003){ //被同一用户顶掉了
				location.href = "/xcviews/html/common.html";
			}else{
				if (callback) {
					callback(msg);
				}
			}
		},
		error : function(msg) {
			// alert(msg);
		}
	});
}

/**
 * 现在有的页面是不拦截的
 */



/**
 * 判断是否是
 */

function isWeiXin() {
	var ua = window.navigator.userAgent.toLowerCase();
	console.log(ua);//mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko)version/9.0 mobile/13b143 safari/601.1
	if (ua.match(/MicroMessenger/i) == 'micromessenger') {
		return true;
	} else {
		return false;
	}
}



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
 * 现在的入口有两个呢，一个是
 */
function isLoginJump(){
  var userId  = localStorage.userId;
  
  if(stringnull(userId)){
	  /*
	   * 判断这上个地址是否来自我们的浏览器啦。如果是的就返回上一页，如果不是的话，那么就返回首页吧。
	   */
	  var before_address = document.referrer;
	  if(before_address.indexOf("page/index")!=-1 || 
			  before_address.indexOf("frequency.html")!=-1 || 
			  	before_address.indexOf("personage.html")!=-1 || 
			  		before_address.indexOf("index.html")!=-1 || 
			  		before_address.indexOf("queryResult")!=-1){
		  
		  
		  history.back(-1);
	  }else{
		  /**
		   */
		  location.href = "/bxg/page/index/"+localStorage.openid+"/null";
	  }
  }else{
	  //登录页面
	  location.href = "/bxg/page/login/1";
  }
}

/**
 * 取消关注 /bxg/focus/add
 * 讲师id
 */
$(".guanzhu1").click(function(){
	console.log($("#teacherId").val());
	var teacherId = $("#teacherId").val();
	if(teacherId == ""){
		alert("获取讲师信息有误");
		return;
	}
	requestService("/bxg/focus/add", {
		lecturerId : teacherId
	}, function(data) {
		if(data.success){
			//alert("关注成功");
			$(".guanzhu1").hide();
			$(".guanzhu2").show();
		};
	})
});
/**
 * 取消关注 /bxg/focus/cancel
 * 讲师id
 */
$(".guanzhu2").click(function(){
	console.log($("#teacherId").val());
	var teacherId = $("#teacherId").val();
	if(teacherId == ""){
		alert("获取讲师信息有误");
		return;
	}
	requestService("/bxg/focus/cancel", {
		lecturerId : teacherId
	}, function(data) {
		if(data.success){
			//alert("取消关注成功");
			//var a = this;
			$(".guanzhu2").hide();
			$(".guanzhu1").show();
		};
	})
});
/**
 * 公共点击事件
 */

/**************************************  直播课程点击事件,因为只有直播有预约  *************************88**************************/

/**
 * courseId 课程id
 * type : 去直播详情页还是点播详情页
 * page : 记录历史用于返回页面使用   page：1 返回列表页  2 返回历史记录页 3 返回搜索结果页  4 返回用户中小心页面
 */

mui(".nav-content").on('click', '.gotodetails', function() {
	window.location.href = "/bxg/page/my_course";
})

function courseDetails(courseId,lineState,page){
	/**
	 * 这个地方只需要判断是否已经预约就行了..
	 *  就直播需要预约啦。其他的不需要啦
	 */
	alert("这个地方换位置了啊");
}
/**
 * 增加观看记录
 * 课程id
 */
function addHistory(courseId,type){
	requestService("/bxg/history/add", {
		course_id : courseId,
		type:type
	},function(data) {
		if (data.success) { //去详情页面
			console.log("增加观看记录成功");
		}else{
			console.log("增加观看记录失败");
		}
	})
}
/**
 * 验证密码
 * 课程id
 */
function courseIsPwd(courseId){
	var falg = true;
	requestService("/bxg/common/courseIsPwd", {
		course_id : courseId
	}, function(data) {
		
		falg =  data.success;
	},false)
	return  falg;
}

/**
 * 验证是否付费
 * 课程id
 */
function courseIsBuy(courseId){
	var falg = true;
	requestService("/bxg/common/courseIsBuy", {
		course_id : courseId
	}, function(data) {
		falg =  data.success;
	},false)
	return  falg;
}

// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	// if (r != null) return unescape(r[2]);
	if (r != null)
		return decodeURI(r[2]);
	return null; // 返回参数值
}

// 钱的转换
function money(pay) {
	return pay / 100;
}
// 逗号分隔
function imgsubstr(imgurl) {
	var strurl = imgurl;
	var attrurl = strurl.split(",");
	return attrurl;
}
// 判断字段空值
function stringnull(zifu) {
	if (zifu == "" || zifu == null || zifu == undefined || zifu == "undefined"
			|| zifu == "null") {
		return false;
	}
	return true;

}
/*
 * 判断字段空值   如果值等于null 或者 undefined 或者 'undefined' 或者 "" 统一返回 ""
 *          如果不等于null时，返回真实的值
 */
function returnstring(zifu) {
	if (zifu == "" || zifu == null || zifu == undefined || zifu == "undefined"
			|| zifu == "null") {
		return "";
	}
	return zifu;
}
// 浏览器端h5支付
function browserPay(userId, orderNo) {

	var payc = {
		userId : userId,
		orderNo : orderNo
	}

	requestService(
			"/bxg/test/h5pay",
			payc,
			function(data) {
				// if(data.success) {
				var params = data.resultObject;
				console.log(params.mweb_url);
				/*
				 * 正常流程用户支付完成后会返回到指定页面，可以在MWEB_URL后凭借redirect_url参数，来指定回调页面。
				 * 如：您希望用户支付完成后跳转至https://www.wechatpay.com.cn，则可以做如下处理
				 * 假设您通过统一下单接口获到的MWEB_URL=
				 * https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx20161110163838f231619da20804912345&package=1037687096
				 * 则拼接后的地址为MWEB_URL= https://wx.tenpay.com/cgi-bin/mmpayweb-bin/
				 * checkmweb?prepay_id=wx20161110163838f231619da20804912345
				 * &package=1037687096&
				 * redirect_url=https%3A%2F%2Fwww.wechatpay.com.cn
				 */
				var redirect_url = encodeURIComponent(getServerHost()+"/bxg/page/wait_money");
				// 查看订单页面
				// window.location.href = params.mweb_url
				// +"&redirect_url="+redirect_url;
				window.location.href = params.mweb_url + "&redirect_url="+getServerHost()+"/bxg/page/wait_money";
			});
}

//  公众号付款
function qupay(userId, orderNo) {
	var payc = {
		userId : userId,
		orderNo : orderNo
	}

	requestService("/bxg/wxpay/h5Prepay", payc, function(data) {
		if (data.success) {
			var resultpay = data.resultObject;
			// var ordernum = resultpay.order_no;
			var timestamp = resultpay.timeStamp;
			var nonceStr = resultpay.nonceStr;
			var package = resultpay.package;
			var signType = resultpay.signType;
			var paySign = resultpay.paySign;
			// 支付成功后的回调函数

			// timestamp = '"' + timestamp + '"';
			// nonceStr = '"' + nonceStr + '"';
			// package = '"' + package + '"';
			// signType = '"' + signType + '"';
			// paySign = '"' + paySign + '"';

			// alert('timestamp=[' + timestamp + ']');
			// alert('nonceStr=[' + nonceStr + ']');
			// alert('package=[' + package + ']');
			// alert('signType=[' + signType + ']');
			// alert('paySign=[' + paySign + ']');

			wx.chooseWXPay({
				timestamp : timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
				nonceStr : nonceStr, // 支付签名随机串，不长于 32 位
				package : package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
				signType : signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
				paySign : paySign, // 支付签名
				success : function(res) {
					location.href = "/bxg/page/wait_money";
				},
				fail : function(res) {
					// alert("no")
					// alert('fail=' + res.errMsg);
				}
			});

			/*
			 * wx.chooseWXPay({ timestamp: timestamp, //
			 * 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
			 * nonceStr: nonceStr, // 支付签名随机串，不长于 32 位 package: package, //
			 * 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***） signType: signType, //
			 * 签名方式，默认为'SHA1'，使用新版支付需传入'MD5' paySign: paySign, // 支付签名 success:
			 * function(res) { // 支付成功后的回调函数 alert(res+'支付成功'); } });
			 */
		}
	});
}

String.prototype.stripHTML = function() {
	var reTag = /<(?:.|\s)*?>/g;
	return this.replace(reTag,"");
}

function dataConverStr(startTime){
	
	var date = new Date(startTime);
	var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;  
	
//	$(".order_center p:eq(0)").html(h+":"+minute+"开播");
//	$(".order_center p:eq(1)").html(y+"."+m+"."+d);
    
	return y+"."+m+"."+d+"  "+h+":"+minute;
}

function timestampConverStr(startTime){
	
	var timestamp2 = Date.parse(new Date(stringTime));
	
	var date = new Date(startTime);
	var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;  
	
//	$(".order_center p:eq(0)").html(h+":"+minute+"开播");
//	$(".order_center p:eq(1)").html(y+"."+m+"."+d);
    
	return y+"."+m+"."+d+"  "+h+":"+minute;
}

/**
 * 公共的用localStorage存储用户信息接口
 * 
 * @param data
 */
function commonLocalStorageSetItem(data){
	var configresult = data.resultObject;
	localStorage.setItem("token",JSON.stringify(configresult));
	localStorage.setItem("userId",configresult.id)
	localStorage.setItem("name",configresult.loginName);
	localStorage.setItem("smallHeadPhoto",configresult.smallHeadPhoto);
	localStorage.setItem("sex",configresult.sex);
	localStorage.setItem("provinceName",configresult.provinceName);
	localStorage.setItem("cityName",configresult.cityName);
	localStorage.setItem("province",configresult.province);
	localStorage.setItem("city",configresult.city);
	localStorage.setItem("email",configresult.email);
	localStorage.setItem("info",configresult.info);
	localStorage.setItem("username",configresult.name);
	localStorage.setItem("ticket",configresult.ticket);
	
	localStorage.setItem("occupation",configresult.occupation);
	localStorage.setItem("occupationOther",configresult.occupationOther);
}

/** * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423      
* (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
* (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
* (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
* (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
*/        
Date.prototype.pattern=function(fmt) {         
	var o = {         
		"M+" : this.getMonth()+1, //月份         
		"d+" : this.getDate(), //日         
		"h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
		"H+" : this.getHours(), //小时         
		"m+" : this.getMinutes(), //分         
		"s+" : this.getSeconds(), //秒         
		"q+" : Math.floor((this.getMonth()+3)/3), //季度         
		"S" : this.getMilliseconds() //毫秒         
	};         
	var week = {         
		"0" : "/u65e5",         
		"1" : "/u4e00",         
		"2" : "/u4e8c",         
		"3" : "/u4e09",         
		"4" : "/u56db",         
		"5" : "/u4e94",         
		"6" : "/u516d"        
	};         
	if(/(y+)/.test(fmt)){         
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
	}         
	if(/(E+)/.test(fmt)){         
	    fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
	}         
	for(var k in o){         
	    if(new RegExp("("+ k +")").test(fmt)){         
	        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
	    }         
	}         
	return fmt;         
}       
 
var date = new Date();      
//window.alert(date.pattern("yyyy-MM-dd hh:mm:ss"));


