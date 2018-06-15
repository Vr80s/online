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


$(".home").css("color", "rgb(0, 188, 18)");






function init() {
	var $sliders = $('#slider li');
	var $selector = $('#selector');
	var $selectors = $('#selector span');
	var $left = $('#left');
	var $right = $('#right');
	var arg = $selector.width() / 2;
//	$selector.css("left", "50%");
//	$selector.css("marginLeft", -arg);
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

init();

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