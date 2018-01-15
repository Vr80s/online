/*
 * tabslider 1.0
 *
 * lu yubin
 *
 * 50856845@qq.com
 *
 * Copyright 2014
 *
*/
(function($){
app = new function(){};
/**
 * 返回滚动值
 */
app.TransformVal = function(transformVal){
	return {
				'-webkit-transform'	: 'translate('+transformVal+'px,0)',
				'-moz-transform'	: 'translate('+transformVal+'px,0)',
				'-o-transform'		: 'translate('+transformVal+'px,0)',
				'-ms-transform'		: 'translate('+transformVal+'px,0)',
				'transform'			: 'translate('+transformVal+'px,0)'
			};
};
/**
 * 函数节流
 */
app.Throttle = function(fn, delay, mustRunDelay){
	var timer = null;
	var t_start;
	return function(){
		var context = this, args = arguments, t_curr = +new Date();
		clearTimeout(timer);
		if(!t_start){
			t_start = t_curr;
		}
		if(t_curr - t_start >= mustRunDelay){
			fn.apply(context, args);
			t_start = t_curr;
		}
		else {
			timer = setTimeout(function(){
				fn.apply(context, args);
			}, delay);
		}
	};
};

app.TabSlider = function(dom,options){
	var $this=$(dom);
	
	if($this.length == 0) return this;
    if($this.length > 1){
        $this.each(function(){app.TabSlider($(this),options)});
        return this;
    }
	
	if($this.data('bind')){
		return false;	
	}else{
		$this.data('bind',true);
	}
	
    var opts = $.extend({
					auto : false,
					spend : 3000,
					threshold : 100,
					maxWidth : null,
					type : 'tabs' // 'tabs' , 'line' , 'slide'
    			}, options || {});
	
	var tw, timer,
		$hd = $this.find("ul.tabSlider-hd"), 
		$content = $this.find("div.tabSlider-bd"),
		$bd = $this.find("div.tabSlider-bd>div.tabSlider-wrap"),
		moved = false, isScrolling ,
		currentIndex = 0, minWidth , offset ,
		isTouchPad = (/hp-tablet/gi).test(navigator.appVersion),
        hasTouch = 'ontouchstart' in window && !isTouchPad,
        start_ev = hasTouch ? 'touchstart' : 'mousedown',
        move_ev = hasTouch ? 'touchmove' : 'mousemove',
        end_ev = hasTouch ? 'touchend' : 'mouseup',
		c_ev = hasTouch ? 'tap' : 'click',
		//初始化
		_init=function(){
			_setStyle();
			$bd.on(''+start_ev+' '+move_ev+' '+end_ev+'',_eventHandler);
			$hd.children('li').on(c_ev,_eventClick);
			if(opts.auto){_setTimer()}
		},
		//设置样式
		_setStyle = function(){
			if (opts.maxWidth) {$this.css({ maxWidth: opts.maxWidth });}
			if (opts.type!=''){
				$hd.addClass(opts.type);
				$content.addClass(opts.type);	
			}
			minWidth=$this.width();
			$bd.children('div.tabSlider-box').each(function(index){
						$(this).css(app.TransformVal(index*minWidth));
					})
			var index=$bd.children('div.tabSlider-box.curr').index();
			if(index!=0){
				currentIndex=index;
				$bd.css(app.TransformVal('-'+index*minWidth));
			}
			_contentHeight();
		},
		//判定事件
		_eventHandler=function(e) {
			switch (e.type) {
				case move_ev:
					_touchMove(e);
					break;
				case start_ev:
					_touchStart(e);
					break;
				case end_ev:
					_touchEnd();
					break;
			}
		},
		_touchStart=function(e) {
			var point = e.touches ? e.touches[0] : e;
			if ($(e.target).closest($bd).length != 0) {
				offset=({
					pageX:      point.pageX,
					pageY:      point.pageY,
					X    :      0,
					Y    :      0
				});
				if(opts.auto) _clearTimer();
				$content.addClass('tabSlider-transition');
				isScrolling = undefined;
				moved=true;	
			}					 
		},
		_touchMove=function(e) {		
			if(!moved) return;			
			var point = e.touches ? e.touches[0] : e;			
			offset.X = point.pageX - offset.pageX;
			offset.Y = point.pageY - offset.pageY;
			if ( typeof isScrolling == 'undefined') {
				isScrolling = !!( isScrolling || Math.abs(offset.X) < Math.abs(offset.Y) );
			}
			if (moved && !isScrolling ) {
				$bd.css(app.TransformVal(offset.X-currentIndex*minWidth));
				e.preventDefault();
			}
		},
		_touchEnd=function() {
			if (!moved || isScrolling) return;
			var stepLength = offset.X <= -opts.threshold ? Math.ceil(-offset.X / minWidth) : (offset.X > opts.threshold) ? -Math.ceil(offset.X / minWidth) : 0;
			if(stepLength==1){
				if(currentIndex<$bd.children('div.tabSlider-box').length-1){currentIndex++;}	
			}else if (stepLength==-1){
				if(currentIndex>0){currentIndex--;}
			}			
			_switchTo();
			if(opts.auto) _setTimer();
			moved = false;				
			
		},
		// hd 单击事件
		_eventClick=function(){			
			currentIndex=$(this).index();
			_switchTo();			
		},
		// 跳转到指定区域
		_switchTo=function(){
			$bd.removeClass('tabSlider-transition').addClass('tabSlider-animate').css(app.TransformVal("-"+(minWidth*currentIndex)));
			_contentHeight();
			_nav();
		},
		//改变当前HD
		_nav=function(){
			$hd.children('li').eq(currentIndex).addClass('curr').siblings().removeClass('curr');
		},	
		//设置内容区域高度
		_contentHeight = function(){console.info($bd.children('div.tabSlider-box').eq(currentIndex).height())
			var h=$bd.children('div.tabSlider-box').eq(currentIndex).height();
			$content.css('height',h);	
			//setTimeout(function(){app.scrollCurrent.scroller.refresh();},200);
		},
		//自动轮播
		_setTimer = function () {
			if (timer) _clearTimer();
			timer = setInterval(function () {					
				_switchTo(currentIndex >= $hd.children('li').length - 1 ? currentIndex=0 : currentIndex += 1);
			}, opts.spend)
		},
		//清除自动轮播
		_clearTimer = function () {
			clearInterval(timer);
			timer = null;
		}
	_init();
	$(window).resize(function(){app.Throttle(_setStyle(),50,30)});
};	
})(Zepto);