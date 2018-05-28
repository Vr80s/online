
(function($) {
	$.fn.slide = function(options) {
		var defaults = {
			boxId: "", //包围ul的盒模型ID
			autoPlay:false,//自动播放,默认false
			time: "500", //轮播间隔
			prevId: "", //上一页按钮
			nextId: "", //下一页按钮
		};
		var options = $.extend(defaults, options);
		this.each(function() {
			var adiv = document.getElementById(options.boxId);
			var oul = adiv.getElementsByTagName("ul")[0];
			var ali = oul.getElementsByTagName("li");
			oul.innerHTML += oul.innerHTML;
			var iwidth = parseInt($("#"+options.boxId).find("li").eq(0).get(0).offsetWidth)+parseInt($("#"+options.boxId).find("li").eq(0).css("margin-right"));
			oul.style.width = iwidth * (ali.length) + "px";
			var n = 0;//轮播计数器
			var m=0;//选中乱
			var b = true;
			var timer;
			//相应的CSS
			$("#" + options.boxId).css({
				"overflow": "hidden",
			});
			oul.style.listStyle = "none";
			$("#" + options.boxId).find("li").css({
				"float": "left",
				"display": "block",
				"height": ($("#" + options.boxId).height()),
				"width": parseInt(($("#" + options.boxId).width()) / options.vic) + "px"
			});
//			$("#" + options.boxId).find("img").css({
//				"width": "100%",
//				"height": "100%"
//			});
			prev();next();
			function prev() {
				var prev = document.getElementById(options.nextId);
				if (options.prevId != "") {
					prev.onclick = function() {
						if (b) {
							b = false;
							clearInterval(timer);
							n++;
							if (n > ali.length / 2) {
								adiv.scrollLeft = 0;
								//						$("#adiv").css("scrollLeft","0");
								n = 1;
							}
							$("#" + options.boxId).stop(true, true).animate({
								"scrollLeft": n * iwidth
							}, 400, function() {
								b = true
							});
							timer = setInterval(function() {
								auto();
							}, options.time);
							return n;
						}
					};
				}
			};
			adiv.onmouseover=function(){
				clearInterval(timer);
			};
			adiv.onmouseout=function(){
				timer = setInterval(function() {
								auto();
							}, options.time);
			};
			function next() {
				var next = document.getElementById(options.prevId);
				if (options.nextId != "") {
					next.onclick = function() {
						if (b) {
							b = false;
							n--;
							clearInterval(timer);
							if (n < 0) {
								adiv.scrollLeft = oul.offsetWidth / 2;
								n = ali.length / 2 - 1;
							}
							$("#" + options.boxId).stop(true, true).animate({
								"scrollLeft": n * iwidth
							}, 400, function() {
								b = true
							});
							timer = setInterval(function() {
								auto();
							}, options.time);
							return n;
						}
					};
				}
			}
			timer = setInterval(function() {
				auto();
			}, options.time);

			function auto() {
				if(options.autoPlay){
					if (b) {
					b = false;
					n++;
					$("#" + options.boxId).stop(true, true).animate({
						"scrollLeft": n * iwidth
					}, 400, function() {
						b = true;
						if (n >=ali.length / 2) {
							adiv.scrollLeft = 0;
							//						$("#adiv").css("scrollLeft","0");
							n = 0;
						}
					});
					return n;
				}else{
					return false;
				}
				}
			}
		})
	}
})(jQuery)