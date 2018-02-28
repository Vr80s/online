(function($) {
	mui(".navtab").on('tap', 'a', function() {
		document.location.href = this.href;
	})
})(mui);

/**
 * 判断是不是来自微信浏览器
 */
function is_weixin(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

var accessCommon = localStorage.access;
var current = location.href;
var domain = window.location.host;
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
		return "http://www.ixincheng.com"; 
	}
	return server_domain; 
}

/**
 * courseId 课程id
 * 如果页面访问是pc浏览器访问跳转到官网浏览器  falg： false 跳到官网首页   ture 跳到课程页面
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
/**
 *  如果页面来自课程的访问就不跳到官网
 */
if(current.indexOf("/xcviews/html/share.html")==-1
	 && current.indexOf("/xcviews/html/foreshow.html")==-1
		&& current.indexOf("/bxg/xcpage/courseDetails")==-1
		&& current.indexOf("/xcviews/html/particulars.html")==-1){
	
	h5PcConversions(false);
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
				location.href = "/xcview/html/cn_login.html";
			}else if(msg.code == 1003){ //被同一用户顶掉了
				location.href = "/xcview/html/common.html";
			}else if(msg.code == 1005){ //token过期  -->去完善信息页面
				var openId =  msg.resultObject.openId;
				var unionId =  msg.resultObject.unionId;
				
				
				location.href = "/xcview/html/evpi.html?openId="+openId+"&unionId="+unionId;
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
		  location.href = "/bxg/page/index/"+localStorage.openid+"/null";
	  }
  }else{
	  //登录页面
	  location.href = "/bxg/page/login/1";
  }
}
/**
 * 公共点击事件
 */

/**************************************  直播课程点击事件,因为只有直播有预约  *************************88**************************/

// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	// if (r != null) return unescape(r[2]);
	if (r != null)
		return decodeURI(r[2]);
	return null; // 返回参数值
}


//获取url中的参数，并返回一个对象
function getUrlParamsReturnJson() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
        }
    }
    return theRequest;
};

/*
 * 传递一个json返回对象瓶装为ur请求地址，待参数的
 */
function createUrl(obj){
    var length = obj && obj.length,
        idx = 0,
        url = obj.url + '?';
    for (var key in obj) {
        if (key != 'url' && obj[key] !== null) {
            url += (key + '=' + encodeURIComponent(obj[key]) + '&');
        }
    }
    return url.substring(0, url.lastIndexOf('&'));
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

String.prototype.stripHTML = function() {
	var reTag = /<(?:.|\s)*?>/g;
	return this.replace(reTag,"");
}

function stripHTML(str){
	var reTag = /<(?:.|\s)*?>/g;
	return str.replace(reTag,"");
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
    localStorage.setItem("name",configresult.name);

	localStorage.setItem("smallHeadPhoto",configresult.smallHeadPhoto);
	localStorage.setItem("sex",configresult.sex);
	
	localStorage.setItem("province",configresult.province);
	localStorage.setItem("city",configresult.city);
	localStorage.setItem("district",configresult.district);
	
	
	localStorage.setItem("provinceName",configresult.provinceName);
	localStorage.setItem("cityName",configresult.cityName);
	localStorage.setItem("countyName",configresult.countyName);
	
	localStorage.setItem("email",configresult.email);
	localStorage.setItem("info",configresult.info);
    localStorage.setItem("username",configresult.loginName);

	localStorage.setItem("ticket",configresult.ticket);
	
	localStorage.setItem("occupation",configresult.occupation);
	localStorage.setItem("occupationOther",configresult.occupationOther);
	localStorage.setItem("occupationText",configresult.occupationText);
	
	
	
	
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
	         //return tips;
	    }
}

/**
 * cookie
 * 返回 1000 有效   1002 过期，去登录页面  1005 过期且去完善信息
 */
function authenticationCooKie(){
	var falg = 1000;
	var user_cookie = cookie.get("_uc_t_");
	var third_party_cookie = cookie.get("third_party_uc_t_");
	if(!stringnull(user_cookie)){ //未登录
		falg = 1002
//		location.href ="/xcview/html/enter.html";
//		return;
		if(stringnull(third_party_cookie)){   //用户用微信登录的但是没有绑定注册信息
			falg = 1005;
//			location.href ="/xcview/html/evpi.html";
//			return;
		}
	}
	return falg;
}


