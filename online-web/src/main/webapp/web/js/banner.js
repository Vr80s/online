    //banner
   
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

	var timer = window.setInterval(autoChange, 3000);

	//点击圆圈切换
	$selector.on('click', function(e) {
		var $target = $(e.target);
		if($target.get(0).tagName === 'SPAN') {
			window.clearInterval(timer);
			$target.addClass('cur').siblings().removeClass('cur');
			step = $target.index();
			$sliders.eq(step).fadeIn(800).siblings().fadeOut(800);
			timer = window.setInterval(autoChange, 3000);
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
		timer = window.setInterval(autoChange, 3000);
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
		timer = window.setInterval(autoChange, 3000);
	})
}

init();