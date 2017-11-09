define(['template','layer','route',
	'css!cssUrl/common.css'
], function (template,layer,route
) {
    function Route() {


	    var Path = route;
	    /**
	     * 路由配置
	     */
	    function clearPanel(){
		    //你可以把一些代码在这里做花式DOM的过渡，如淡入淡出或幻灯片。
		    //$("body").fadeOut(1000);

	    }

	    //首页
	    var homeIndex = function(id) {
		    require( ['../views/home/home.js'], function (m) {
			    m.createPage();
		    });
	    };

	    //复习详情页
	    var reviewDetails = function(id) {
		    require( ['../views/details/review/review-details.js'], function (m) {
			    m.createPage(id);
		    });
	    };

	    //预习详情页
	    var previewDetails = function(id) {
		    require( ['../views/details/preview/preview-details.js'], function (m) {
			    m.createPage(id);
		    });
	    };


	    //404页
	    var error404 = function() {
		    require( ['app/view/404/404.js'], function (m) {
			    m.createPage();
		    });
	    };

	    /**
	     *
	     */
		//路由---首页
	    Path.map("#/home").to(function(){
		    homeIndex();
	    }).enter(clearPanel).exit(function(){
	    });

	    //路由---复习详情页
	    Path.map("#/review/details/:id").to(function(){
		    reviewDetails(this.params['id']);
	    }).enter(clearPanel).exit(function(){
	    });

	    //路由---预习详情页
	    Path.map("#/preview/details/:id").to(function(){
		    previewDetails(this.params['id']);
	    }).enter(clearPanel).exit(function(){
	    });

	    //路由--- 404
	    Path.rescue(function(){
		    error404();
	    });

	    //初始化路由配置
	    Path.root("");
	    Path.listen();

    };


	return {
		Route: Route
	};

});