var protocoltype =document.location.protocol;
var documnetHost = document.location.host;

var config = "";
$.ajax({url:"/config.json",async:false,success:function(data){
  config = data;
}});
var full = protocoltype +"//" + config.pc;

//如果是专辑分享的话：如下
//if(collectionId!=null && collectionId!=undefined && collectionId!=""){
//	courseId = collectionId;
//}

var shareCourseId = ""
if(collectionId!=undefined && collectionId!=""){
	shareCourseId = collectionId;
}else{
	shareCourseId = courseId;
}


function stripHTML(str) {
	if(str!=null && str!=undefined){
		var reTag = /<(?:.|\s)*?>/g;
	    return str.replace(reTag, "");
	}
    return str;
}

/**
 * pc端详情页面
 */
var pc_url= full+"/courses/"+shareCourseId+"/info";

/**
 * 获取微信端 域名
 */
var wxurl = "http://" + config.wechat;
/**
 * 获取微信端分享连接地址
 */
//-- shareType 分享类型 1 课程  2 主播    shareId 当类型是1时为课程id，当是2时为用户id
var share_link = "/wx_share.html?shareType=1&shareId="+shareCourseId;
var qrcodeurl = wxurl+share_link;

/**
 * 微信pc分享 显示二维码
 */ 
$("#qrcodeCanvas1").qrcode({
	render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
	text : qrcodeurl,    //扫描了二维码后的内容显示,在这里也可以直接填一个网址，扫描二维码后
	width : "90",               //二维码的宽度
	height : "90",              //二维码的高度
	background : "#ffffff",       //二维码的后景色
	foreground : "#000000",        //二维码的前景色
	src: '/web/images/yrx.png'             //二维码中间的图片
});
$("#qrcodeCanvas2").qrcode({
	render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
	text : qrcodeurl,    //扫描了二维码后的内容显示,在这里也可以直接填一个网址，扫描二维码后
	width : "90",               //二维码的宽度
	height : "90",              //二维码的高度
	background : "#ffffff",       //二维码的后景色
	foreground : "#000000",        //二维码的前景色
	src: '/web/images/yrx.png'             //二维码中间的图片
});


/**
 * 微博分享
 */
$("#weibo_share").click(function(){
	var  p = {
        url: pc_url,/*获取URL，可加上来自分享到QQ标识，方便统计*/
        title : courseName,/*分享标题(可选)*/
        pic : smallImgPath /*分享图片(可选)*/
    };
    var s = [];
    for (var i in p) {
        s.push(i + '=' + encodeURIComponent(p[i] || ''));
    }
    var _src = "http://service.weibo.com/share/share.php?" + s.join('&') ;
    window.open(_src);
})
/**
 * qq分享
 */
$("#qq_share").click(function(){
	
	//详情剔除script标签，减少长度
	description =stripHTML(description);
	if(description!=null && description.length>40){
		description =  description.substring(0,40);
	}
	 var  p = {
        url: pc_url,/*获取URL，可加上来自分享到QQ标识，方便统计*/
        desc: '中医传承', /*分享理由(风格应模拟用户对话),支持多分享语随机展现（使用|分隔）*/
        title : courseName,/*分享标题(可选)*/
        summary : description,/*分享描述(可选)*/
        pics : smallImgPath  /*分享图片(可选)*/
    };
    var s = [];
    for (var i in p) {
        s.push(i + '=' + encodeURIComponent(p[i] || ''));
    }
    var _src = "http://connect.qq.com/widget/shareqq/index.html?" + s.join('&') ;
    window.open(_src);
})





/**
 * 如果在手机上打开此连接，跳转到对应微信端的页面
 */
//浏览器判断
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

/**
 * 判断打开此连接的浏览器类型
 */
if(browser.versions.mobile || browser.versions.ios || browser.versions.android ||
    browser.versions.iPhone || browser.versions.iPad){
        wxurl = "http://"+config.wechat+"/wx_share.html?shareType=1&shareId="+course_id;
    window.location = wxurl;
}
