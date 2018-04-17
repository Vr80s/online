/**
 * Created by Administrator on 2016/7/29.
 */
var home = false;
var bath = "";
(function(){
    if(document.location.host.indexOf('dev-front.ixincheng.com')!=-1){
        bath = "/apis"+bath;
    }
})();

var RequestService = function(method, type, params, callBack, async) {
	$.ajax({
		url: bath + method,
		type: type,
		data: params,
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
//数组去重
Array.prototype.unique3 = function () {
	var res = [];
	var json = {};
	for (var i = 0; i < this.length; i++) {
		if (!json[this[i]]) {
			res.push(this[i]);
			json[this[i]] = 1;
		}
	}
	return res;
};

//验证身份证号
var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}   
function isCardID(sId){   
    var iSum=0 ;  
    var info="" ;  
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
//根据cookie判断是否在线
function live(){
	
};
//判断是否在线
	function isAlive(){
		$.ajax({
			type: "get",
			url: bath + "/online/user/isAlive",
			async: false,
			success: function(data) {
				if(data.success === true) {	
				} else {
					$('#login').modal('show');
					$('#login').css("display", "block");
					$(".loginGroup .logout").css("display", "block");
					$(".loginGroup .login").css("display", "none");
					alert("没登录！");
					window.location.href=bath+"/index.html"
				}
			}
		});
	};
	function isAlive0(){
		$.ajax({
			type: "get",
			url: bath + "/online/user/isAlive",
			async: false,
			success: function(data) {
				if(data.success === true) {	
				} else {
					$('#login').modal('show');$('#login').css("display", "block");
					$(".loginGroup .logout").css("display", "block");
					$(".loginGroup .login").css("display", "none");
					return false;
				}
			}
		});
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

function showLoding() { //loding页面
	var index = layer.load(2, {
		shade: [0.4, '#fff'], //0.1透明度的白色背景
		scrollbar: false,
		time: 8000
	});
	$("html").removeAttr("style");
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

function initTFormModal(title, txt) {
	$("#bzh_modal_tform .title").val(title);
	$("#bzh_modal_tform .txt").val($.trim(txt));
}

$(function($) {
	$.fn.Tabnavigator = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || method == null) {
			return methods.init.apply(this, arguments);
		};
	}

	var methods = {
		init: function(options) {
			var defaultVar = {};
			var opts = $.extend(defaultVar, options);
			return this.each(function() {
				$(this).click(function(evt) {
					var target = evt.target;
					if($(target).hasClass("btn-item")) {
						$(this).find(".pointer").animate({
							"left": $(target).position().left,
							"width": $(target).width() + 20
						}, 300);
						$(this).parent().find(".view-stack").css("display", "none");
						$(this).trigger("tabChange", {
							data: $(target).data("type")
						});
					}
				});
			})
		},
		otherFunction: function(index) {}
	}
}(jQuery));

$(function($) {
	$.fn.ButtonBar = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || method == null) {
			return methods.init.apply(this, arguments);
		};
	}

	var methods = {
		init: function(options) {
			var defaultVar = {};
			var opts = $.extend(defaultVar, options);
			return this.each(function() {
				$(this).click(function(evt) {
					if(!$(evt.target).hasClass("btn")) {
						return
					};
					$(this).find(".btn").removeClass("btn-success");
					$(evt.target).addClass("btn-success");
					$(this).trigger("btnBarChange", {
						type: $(evt.target).data("type"),
						name: $(evt.target).data("name")
					});
				});
			})
		},

		otherFunction: function(index) {}
	}
}(jQuery));
$(function($) {
	$.fn.SlideNav = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || method == null) {
			return methods.init.apply(this, arguments);
		};
	}

	var methods = {
		init: function(options) {
			var defaultVar = {};
			var opts = $.extend(defaultVar, options);
			return this.each(function() {

			})
		},

		otherFunction: function(index) {}
	}
}(jQuery));

function pageScroll() { //返回顶部
	window.scrollBy(0, -500);
	scrolldelay = setTimeout('pageScroll()', 10);
	if($(window).scrollTop() == 0) {
		$(".h_top").show(300);
		clearTimeout(scrolldelay);
		$(".h_top").hide(400);
	}
}


//图片缩放
$(function() {
    $.fn.extend({
		showlargeimg: function() {
			var obj = $(this).attr("src"); //获取src
			var window_height = $(window).height(); //判断可视区域高度
			var window_width = $(window).width(); //判断可视区域宽度
			$("body").append("<div class='background_img' style='width:100%;height: 100%;position: fixed;top:0px;left: 0px;z-index: 99;background-color: #000;opacity: 0.5'></div>" +
					"<div id='largeimg' style='position: fixed;z-index: 100;'></div>") //添加模态大图
			$("#largeimg").html("<div><img src='" + obj + "'/></div>"); //添加大图
			var thisheight = parseInt($("#largeimg div img").height()); //原图高度number类型
			var thiswidth = parseInt($("#largeimg div img").width()); //原图宽度number类型
			//判断是否超宽
			if(($("#largeimg div img").width()) >= window_width && ($("#largeimg div img").height()) < window_height) { //图片超宽，不超高
				$("#largeimg div img").css("width", (window_width * 0.8) + "px");
			} else if(($("#largeimg div img").width()) >= window_width && ($("#largeimg div img").height()) >= window_height) { //图片超高超宽
				if(($("#largeimg div img").width() - window_width) < ($("#largeimg div img").height() - window_height)) { //图片超高比例大于超宽比例
					var h = thisheight / thiswidth;
					$("#largeimg div img").css("height", (window_height * 0.8) + "px");
					$("#largeimg div img").css("width", $("#largeimg div img").width() * h + "px");
				} else { //推按超高比例小于超宽比例
					$("#largeimg div img").css("width", (window_width * 0.8) + "px");
				}
			} else if(($("#largeimg div img").width()) < window_width && ($("#largeimg div img").height()) >= window_height) { //图片超高不超宽
				var h = thisheight / thiswidth;
				$("#largeimg div img").css("heigth", (window_height * 0.8) + "px");
				$("#largeimg div img").css("width", $("#largeimg div img").width() * h + "px");
			}
			$(document.body).css("overflow", "hidden");
			$("body").css("padding-right", "17px");
			$(".other-page").css("padding-right", "17px");
			var heights = (window_height - $("#largeimg").height()) / 2;
			var widths = (window_width - $("#largeimg").width()) / 2;
			$("#largeimg").css("left", widths + "px"); //模态水平居中
			$("#largeimg").css("top", heights + "px"); //模态垂直居中
			//关闭
			$(".background_img").click(function() {
				$(this).remove();
				$("#largeimg").remove();
				$(document.body).css("overflow", "");
				$("body").css("padding-right", "0px");
				$(".other-page").css("padding-right", "0px");
			});
			$("#largeimg").click(function() {
				$(this).remove();
				$(".background_img").remove();
				$(document.body).css("overflow", "");
				$("body").css("padding-right", "0px");
				$(".other-page").css("padding-right", "0px");
			})
		}
	});
});