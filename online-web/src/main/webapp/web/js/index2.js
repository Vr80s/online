/**
 * Created by admin on 2016/7/27.
 */
//测试环境只能点击ID为1
template.helper('href', function(num) {
	if(num != "") {
		return '' + bath + '/web/courseDetail/' + num;
	} else {
		return 'javascript:;';
	}
});



$(function() {
	/*轮播图*/
	RequestService("/banner/getBannerList?type=2", "GET", "", function(data) {
		if(data.resultObject.length === 1) {
			$.each(data.resultObject, function(index, item) {
				$("#left,#right").css("display", "none");
				if(index === 0) {
					var imgPath = item.imgPath;
					$('#slider').append($('<li data-indexId=' + data.resultObject[index].id + ' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
					$("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
				} else {
					var imgPath = item.imgPath;
					$('#slider').append($('<li data-indexId=' + data.resultObject[index].id + ' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
					$("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
				}
			})
		} else if(data.resultObject.length > 1 && data.resultObject.length <= 6) {
			$.each(data.resultObject, function(index, item) {
				if(index === 0) {
					var imgPath = item.imgPath;
					$('#slider').append($('<li data-indexId=' + data.resultObject[index].id + ' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
					$("#aImg" + index).css("background", "url('" + imgPath + "')  no-repeat top center");
					$('#selector').append($('<span class="cur"></span>'));
				} else {
					var imgPath = item.imgPath;
					$('#slider').append($('<li data-indexId=' + data.resultObject[index].id + ' class="cur" style="display:none;" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
					$("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
					$('#selector').append($('<span></span>'));
				}
			});
		} else if(data.resultObject.length > 6) {
			$.each(data.resultObject.slice(0), function(index, item) {
				if(index === 0) {
					var imgPath = item.imgPath;
					$('#slider').append($('<li data-indexId=' + data.resultObject[index].id + ' class="cur" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
					$("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
					$('#selector').append($('<span class="cur"></span>'));
				} else {
					var imgPath = item.imgPath;
					$('#slider').append($('<li data-indexId=' + data.resultObject[index].id + ' class="cur" style="display:none;" data-img="' + imgPath + '"><a id="aImg' + index + '" target="_blank" href="' + item.imgHref + '" ></a></li>'));
					$("#aImg" + index).css("background", "url('" + imgPath + "') no-repeat top center");
					$('#selector').append($('<span></span>'));
				}
			});
		}
		init();
		$("#slider li").on("click", function() {
			var indexId = $(this).attr("data-indexId");
			RequestService("/banner/updateClickCount", "POST", {
				id: indexId
			}, function() {

			})
		})
	});
});

function init() {
	var $sliders = $('#slider li');
	var $selector = $('#selector');
	var $selectors = $('#selector span');
	var $left = $('#left');
	var $right = $('#right');
	var arg = $selector.width() / 2;
	$selector.css("left", "50%");
	$selector.css("marginLeft", -arg);
	//自动切换
	var step = 0;

	function autoChange() {
		if(step === $sliders.length) {
			step = 0;
		};
		$sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
		$selectors.eq(step).addClass('cur').siblings().removeClass('cur');
		step++;
	}

	var timer = window.setInterval(autoChange, 2000);

	//点击圆圈切换
	$selector.on('click', function(e) {
		var $target = $(e.target);
		if($target.get(0).tagName === 'SPAN') {
			window.clearInterval(timer);
			$target.addClass('cur').siblings().removeClass('cur');
			step = $target.index();
			$sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
			timer = window.setInterval(autoChange, 2000);
		}
	});

	//点击左右按钮切换
	$left.on('click', function() {
		window.clearInterval(timer);
		step--;
		if(step < 0) {
			step = $sliders.length - 1;
			$sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
			$selectors.eq(step).addClass('cur').siblings().removeClass('cur');
		} else {
			$sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
			$selectors.eq(step).addClass('cur').siblings().removeClass('cur');
		}
		timer = window.setInterval(autoChange, 2000);
	})
	$right.on('click', function() {
		window.clearInterval(timer);
		step++;
		if(step > $sliders.length - 1) {
			step = 0;
			$sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
			$selectors.eq(step).addClass('cur').siblings().removeClass('cur');
		} else {
			$sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
			$selectors.eq(step).addClass('cur').siblings().removeClass('cur');
		}
		timer = window.setInterval(autoChange, 2000);
	})
}

function rTips(errorMessage) {
	//  $(".rTips").text(errorMessage);
	$(".rTips").css("display", "block");
	$(".rTips-bg").css("display", "block");

	$(".rTips span").click(function() {
		$(".rTips-bg").css("display", "none");
		$(".rTips").css("display", "none");
	})

}

function addSelectedMenu() {
	$(".home").addClass("select");
}