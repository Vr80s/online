var home = false;
var bath = "";
(function(){
    if(document.location.host.indexOf('dev-front.xczhihui.com')!=-1){
        bath = "/apis"+bath;
    }
})();
var RequestJsonService = function(url, type, params, callBack, async){
    ajaxService(url, type, params, callBack, async,"application/json");
}
var RequestService = function(url, type, params, callBack, async) {
    ajaxService(url, type, params, callBack, async,"application/x-www-form-urlencoded");
};

var ajaxService = function(url, type, params, callBack, async,contentType) {
    $.ajax({
        url: bath + url,
        type: type,
        data: params,
        contentType: contentType,
        async: async === undefined ? true : async,
        cache: false, //清除缓存
        success: function(data) {
            if(callBack) {
                callBack(data);
            }
            $("*[data-txt]").hover(function(e) {
                //var eve = e || window.event;
                var string = $(this).data('txt');
                if($(this).attr("data-maxlengts")) {
                    var mylength = $(this).attr("data-maxlengts");
                } else {
                    var mylength = 10;
                }
                if(string.length >= mylength) {
                    layer.tips(string, $(this), {
                        tips: [1, '#f8f8f8'],
                        area: ['auto', 'auto']
                    });
                }
            }, function() {
                layer.closeAll()
            });
        }
    });
};



//判断字段为非空值
function isNotBlank(str) {
    str = $.trim(str);
    if (str == undefined || str == null || str == "" || str == "undefined" || str == "null") {
        return false;
    }
    return true;
}

//判断字段为空值
function isBlank(str) {
    return !isNotBlank(str);
}


//验证身份证号
var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}   
function isCardID(sId){   
    var iSum=0 ;  
    if(!/^\d{17}(\d|x)$/i.test(sId)) return "身份证号格式错误";
    sId=sId.replace(/x$/i,"a");   
    if(aCity[parseInt(sId.substr(0,2))]==null) return "身份证号格式错误";// "你的身份证地区非法";   
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));   
    var d=new Date(sBirthday.replace(/-/g,"/")) ;  
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证号格式错误";//"身份证上的出生日期非法";   
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;  
    if(iSum%11!=1) return "身份证号格式错误";//"你输入的身份证号非法";   
    return true;
};

//获取地址栏参数
$.getUrlParam = function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if(r != null) return unescape(r[2]);
			return null;
	};


var showHtml = function() { //解决加载页面问题
	$("header").css("display", "block");
	$("footer").css("display", "block");
	$(".view-container").css("display", "block");
};
var hideHtml = function() {
	$("header").css("display", "none");
	$("footer").css("display", "none");
	$(".view-container").css("display", "none");
}

//删除图片
function myquiz_bottom_dv(eve) {
	var class_t = $(eve.target).parent().parent().children("div:eq(0)").attr("class");
	$("." + class_t + "").text((($(eve.target).parent().parent().parent().children().length) - 1) + "/5");
	$(eve.target).parent().parent().remove();
	$("." + class_t + "").css("display", "none");
	$("." + class_t + ":last").css("display", "block");
	if($(eve.target).parent().parent().parent().children().length < 6) {
		$("." + imgz + "").css("display", "none");
	}
}

//统计图片数量
var imgs = 0;
//上传阻止变量
var imgz;


function pageScroll() { //返回顶部
	window.scrollBy(0, -500);
	scrolldelay = setTimeout('pageScroll()', 10);
	if($(window).scrollTop() == 0) {
		$(".h_top").show(300);
		clearTimeout(scrolldelay);
		$(".h_top").hide(400);
	}
}