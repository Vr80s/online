require.config({
	baseUrl: '../js/libs',
	paths: {
		'text': './requirejs-text/text',
		'css': './require-css/css',
		'json': './requirejs-json/json',
		'route': './path.min',
		'template': './template',
		'font-awesome':'./font-awesome-4.7.0/css/font-awesome.min',
		'jquery':'./jquery-1.12.4.min',
		'jquery.fs.boxer': './boxer/jquery.fs.boxer.min',
		'boxerCss': './boxer/jquery.fs.boxer',
		'ckplayer': './ckplayer6.8/ckplayer/ckplayer',
		'audiojs' : './audiojs/audiojs/audio.min',
		'layer': './layer-v2.4/layer/layer',
		'layerCss': './layer-v2.4/layer/skin/layer',
		'laypage': './laypage-v1.3/laypage/laypage',
		'laypageCss': './laypage-v1.3/laypage/skin/laypage',
		'layui':'./layui-v1.0.2/layui/lay/dest/layui.all',
		'layuiCss': './layui-v1.0.2/layui/css/layui',
		'common': '../../../web_yx/js/common/common',
		'config': '../config',
		'tplUrl': '../../../web_yx/views',
		'cssUrl': '../../../web_yx/css',
		'webUrl': '../../../css'
	},
	shim: {
		'route' : {
			exports: 'Path'
		},
		'config': {
			exports: 'base'
		},
		'ckplayer': {
			deps: ['jquery'],
			exports:'CKobject'
		},
		'jquery.fs.boxer': ['jquery','css!boxerCss'],
		'layer': ['jquery','css!layerCss'],
		'laypage': ['jquery','css!laypageCss'],
		'layui': {
			deps: ['jquery','css!layuiCss'],
			exports:'layui'//exports的值为jqueryPlaceholder提供的 对外接口的名称
		},
		'ckplayer': {
			deps: ['jquery'],
			exports:'CKobject'
		},
		'audiojs': {
			deps: ['jquery'],
			exports:'audiojs'
		}
	}

});

//初始化---公用方式;
requirejs([
	'../js/common/route.js'
],function(route){
	//开始路由文件的执行
	route.Route();




});