;
(function($) {
	var $loadDiv;
	$.fn.showloading = function(options) {
		var defaults = {
			img:"../images/ansandqus/loading.gif",
        	text:"加载中"
		}
		var options = $.extend(defaults, options);
		$loadDiv = $("<div class='loadDiv'><img src=" + options.img + "><p>" + options.text + "</p></div>");
		$loadDiv.css({
			"display": "block",
			"margin": "0 auto",
			"width": "100px",
			"height": "30px",
			"text-align": "center",
			"position":"absolute",
			"left":"50%",
			"top":"40%",
			"margin-left":"-50px",
			"margin-top":"-15px"
		});
		$loadDiv.find("img").css({
			"display": "block",
			"margin": "0 auto"
		});
		$loadDiv.find("p").css({
			"display": "block",
			"margin": "0 auto",
			"font-size":"12px",
			"color":"#333"
		});
		this.each(function() {
			$(this).css({"position":"relative"}).append($loadDiv);
		})
	}
	$.fn.closeloading =function clear(){
		this.each(function() {
			$(this).find(".loadDiv").remove();
		})
	}
})(jQuery)