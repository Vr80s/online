(function() {
	var goodsDetail = {
		node: {
			closeBtn: $('.close'),
			screenW: $('.screenW'),
			subW: $('.subW'),
			share: $('#share'),
			shareBox: $('.shareBox')
		},
		/*入口*/
		init: function() {
			var self = this;
			self.closeTap();
			self.shareTap();
		},
		/*分享点击弹窗*/
		shareTap: function() {
			var self = this;
			self.node.share.on('tap', function() {
				self.wShow();
				self.node.shareBox.show().siblings().hide();
			});
		},
		/*点击关闭弹窗*/
		closeTap: function() {
			var self = this;
			self.node.closeBtn.on('tap', function() {
				self.wHide();
			});
		},
		/*窗口显示*/
		wShow: function() {
			var self = this;
			self.node.screenW.show();
			self.node.subW.addClass('move').removeClass('back');
		},
		/*窗口隐藏*/
		wHide: function() {
			var self = this;
			self.node.subW.addClass('back').removeClass('move');
			setTimeout(function() {
				self.node.screenW.hide();
			}, 500);
		}
	};
	/*商品js入口*/
	goodsDetail.init();

	/*百度分享js*/
	window._bd_share_config = {
		"common": {
			"bdSnsKey": {},
			"bdText": "",
			"bdMini": "2",
			"bdMiniList": false,
			"bdPic": "",
			"bdStyle": "0",
			"bdSize": "16"
		},
		"share": {},
		"image": {
			"viewList": ["qzone", "tsina", "sqq", "tqq", "weixin"],
			"viewText": "分享到：",
			"viewSize": "16"
		},
		"selectShare": {
			"bdContainerClass": null,
			"bdSelectMiniList": ["qzone", "tsina", "sqq", "tqq", "weixin"]
		}
	};
	with(document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
})();