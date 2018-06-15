// rem适应函数
		$(function() {
			
			setAdaption(7.5);
			function setAdaption(baseFontsize) {
				baseFontsize = baseFontsize || 7.5;
				var docEl = document.documentElement;
				var refresh = function() {
					var dpr = window.devicePixelRatio,
						deviceWidth = docEl.getBoundingClientRect().width, //获得屏幕宽度
						deviceFontsize = deviceWidth / baseFontsize + "px"; //计算rem基准值
					docEl.setAttribute('data-dpr', dpr);
					docEl.style.fontSize = deviceFontsize; //设置rem基准值
				};

				window.addEventListener('pageshow', function(e) {
					if(!e.pretersised) {
						refresh();
					}
				}, false);
				window.addEventListener('resize', function() {
					refresh();
				}, false);
			}

		})