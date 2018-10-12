$(function () {

    recommends(1,'down');  /*定义一个方法*/
//    newests(1,'down');
//    hottests(1,'down');
//    ascendings(1,'down');
//    descendings(1,'down');

})

// $(function () {
    // banner图
    requestService("/xczh/shop/goods/banner",null,function (data) {
        if (data.success == true) {   
            $(".top_details").html(template('top_details', {items: data.resultObject}));
            // 轮播--渲染时放到，渲染的js下面
            var mySwiper = new Swiper('.banner',{
                autoplay:1500,
                visibilityFullFit : true,
                loop:true,
                pagination : '.pagination'
            });

            var a = $(".swiper-slide").css("display");
            if (a == "block") {
                $(".banner").show();
                $(".minirefresh-wrap").css("top","5rem");
            } else {
                $(".banner").hide();
                $(".minirefresh-wrap").css("top","2rem");
            }
            
            $(".swiper-slide img").click(function () {
                var data_id = $(this).attr("data_id");
                //增加banner的点击量
                clickBanner(data_id);
                //页面跳转
                // var data_url = $(this).find("img").attr("data_url");
                var data_target = $(this).attr('data_target');

                bannerJump(data_target);
            })

            // 点击banner跳转
            function bannerJump(target) {
                if (!target) {
                    return ;
                } else {
                    // 定义跳转路径--共用
                    if(document.location.host.indexOf('dev.ixincheng.com')!=-1){
                        target = "/apis"+target;
                    }

                    location.href = target;
                }
            }
        }
    });

    //增加点击数banner
    function clickBanner(id) {
        requestService("/xczh/recommend/clickBanner", {
            id: id
        }, function (data) {

        });
    }

function recommends(pageNumber, downOrUp){
	
    // 商品列表--推荐
    var recommend = "RECOMMEND_DESC";
    requestGetService("/xczh/shop/goods/list",{
        pageNumber:pageNumber,
        pageSize:6,
        orderType:recommend
    },function (data) {
        if (data.success == true) {
            var obj = data.resultObject;
            //downOrUp为down时为下拉刷新等于up时为上拉操作
            if(downOrUp=='down'){
                $(".product_list").html(template('product_list', {items: obj}));
//              listClick();
                miniRefresh.endDownLoading(true);// 结束下拉刷新
            } else if(obj.length==0){
                miniRefresh.endUpLoading(true);// 结束上拉加载
            } else {
                $(".product_list").append(template('product_list',{items:obj}));
                miniRefresh.endUpLoading(false);
            }
            
//          $(".product_list li").off("click");
            $(".product_list li").click(function(){
		        var id = $(this).attr("data-id");
		        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
		    })
        }
            
    });
}
    

    $(".default_click").click(function(){
    	
    	page = 1;
        // 商品列表--推荐
        var recommend = 'RECOMMEND_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:1,
            pageSize:10
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                $(".product_list").html(template('product_list', {items: obj}));
                $(".product_list li").click(function(){
			        var id = $(this).attr("data-id");
			        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
			    })
//              listClick();
            }
        });
    });
    

    function newests(pageNumber, downOrUp){
        // 商品列表--最新
        var newest = 'DATE_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:pageNumber,
            pageSize:6,
            orderType:newest
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                //downOrUp为down时为下拉刷新等于up时为上拉操作
                if(downOrUp=='down'){
                    $(".product_list").html(template('product_list', {items: obj}));
//                  listClick();
                    miniRefresh.endDownLoading(true);// 结束下拉刷新
                } else if(obj.length==0){
                    miniRefresh.endUpLoading(true);// 结束上拉加载
                } else {
                    $(".product_list").append(template('product_list',{items:obj}));
                    miniRefresh.endUpLoading(false);
                }
            }
                
        });
    }
    $(".option_newest").click(function(){
    	
    	page = 1;
        // 商品列表--最新
        var newest = 'DATE_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:1,
            pageSize:10,
            orderType:newest
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                $(".product_list").html(template('product_list', {items: obj}));
                $(".product_list li").click(function(){
			        var id = $(this).attr("data-id");
			        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
			    })
//              listClick();
            }
        });
    });
    

    function hottests(pageNumber, downOrUp){
    	
        // 商品列表--最热
        var hottest = 'SALES_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:pageNumber,
            pageSize:6,
            orderType:hottest
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                //downOrUp为down时为下拉刷新等于up时为上拉操作
                if(downOrUp=='down'){
                    $(".product_list").html(template('product_list', {items: obj}));
//                  listClick();
                    miniRefresh.endDownLoading(true);// 结束下拉刷新
                } else if(obj.length==0){
                    miniRefresh.endUpLoading(true);// 结束上拉加载
                } else {
                    $(".product_list").append(template('product_list',{items:obj}));
                    miniRefresh.endUpLoading(false);
                }
            }
                
        });
    }
    $(".option_hottest").click(function(){
    	
    	page = 1;
        // 商品列表--最热
        var hottest = 'SALES_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:1,
            pageSize:10,
            orderType:hottest
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                $(".product_list").html(template('product_list', {items: obj}));
                $(".product_list li").click(function(){
			        var id = $(this).attr("data-id");
			        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
			    })
//              listClick();
            }
        });
    });
    

    function ascendings(pageNumber, downOrUp){
        // 商品列表--价格升序
        var ascendings = 'PRICE_ASC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:pageNumber,
            pageSize:6,
            orderType:ascendings
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                //downOrUp为down时为下拉刷新等于up时为上拉操作
                if(downOrUp=='down'){
                    $(".product_list").html(template('product_list', {items: obj}));
//                  listClick();
                    miniRefresh.endDownLoading(true);// 结束下拉刷新
                } else if(obj.length==0){
                    miniRefresh.endUpLoading(true);// 结束上拉加载
                } else {
                    $(".product_list").append(template('product_list',{items:obj}));
                    miniRefresh.endUpLoading(false);
                }
            }
                
        });
    }
    $(".option_price").click(function(){
    	
    	page = 1;
        // 商品列表--价格升序
        var ascending = 'PRICE_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:1,
            pageSize:10,
            orderType:ascending
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                $(".product_list").html(template('product_list', {items: obj}));
                $(".product_list li").click(function(){
			        var id = $(this).attr("data-id");
			        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
			    })
//              listClick();
            }
        });
    });
    $(".down").click(function(){
    	
    	page = 1;
        // 商品列表--价格升序
        var ascending = 'PRICE_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:1,
            pageSize:10,
            orderType:ascending
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                $(".product_list").html(template('product_list', {items: obj}));
                $(".product_list li").click(function(){
			        var id = $(this).attr("data-id");
			        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
			    })
//              listClick();
            }
        });
    });

    
    function descendings(pageNumber, downOrUp){
    	
        // 商品列表--价格降序
        var descendings = 'PRICE_DESC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:pageNumber,
            pageSize:6,
            orderType:descendings
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                //downOrUp为down时为下拉刷新等于up时为上拉操作
                if(downOrUp=='down'){
                    $(".product_list").html(template('product_list', {items: obj}));
//                  listClick();
                    miniRefresh.endDownLoading(true);// 结束下拉刷新
                } else if(obj.length==0){
                    miniRefresh.endUpLoading(true);// 结束上拉加载
                } else {
                    $(".product_list").append(template('product_list',{items:obj}));
                    miniRefresh.endUpLoading(false);
                }
            }
                
        });
    }
    $(".low_to_high").click(function(){
    	
    	page = 1;
    	
        // 商品列表--价格降序
        var descending = 'PRICE_ASC';
        requestGetService("/xczh/shop/goods/list",{
            pageNumber:1,
            pageSize:10,
            orderType:descending
        },function (data) {
            if (data.success == true) {
                var obj = data.resultObject;
                $(".product_list").html(template('product_list', {items: obj}));
                $(".product_list li").click(function(){
			        var id = $(this).attr("data-id");
			        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
			    })
//              listClick();
            }else{
            	
            }
        });
    });

    //$(".default_click").click();/*默认点击推荐*/

    

    //刷新
    // 初始化页码
    var page = 1;

    // miniRefresh 对象
    var miniRefresh = new MiniRefresh({
        container: '#minirefresh',
        down: {
            //isLock: true,//是否禁用下拉刷新
            callback: function () {
                page = 1;
                
                refurbish(page,'down');
            }
        },
        up: {
            isAuto: false,
            callback: function () {
                page++;
                
                refurbish(page,'up');
            }
        }
    });

function refurbish(page,downUp){
	
	 /*
     * 获取当前排序规则
     */
    
    var defaultClick = $("#mainMenuBar li[class*='default_click']");
    
    if(defaultClick!=null && defaultClick.length>0){
    			
    	if(defaultClick.hasClass("option_recommend")){
    		
    		  recommends(page,downUp);
    	}else if(defaultClick.hasClass("option_newest")){
    		  newests(page,downUp);
    	}else if(defaultClick.hasClass("option_hottest")){
    		  hottests(page,downUp);
    	}
    }else{
		var display1 = $(".low_to_high").css("display");
		if(display1 == "list-item"){
			descendings(page,downUp);
		}
    	var display2 = $(".down").css("display"); 
        if(display2 == "list-item"){
			ascendings(page,downUp);
		}
    } 	
}



/*function listClick(){
    // 点击进入详情
    $(".product_list li").click(function(){
        var id = $(this).attr("data-id");
        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
    })
};*/
// 点击进入详情
    $(".product_list li").click(function(){
        var id = $(this).attr("data-id");
        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
    })
// })


requestService("/xczh/shop/goods/hotSearch", null,
	function(data) {
		if (data.success == true) {
			// 	    	<!--给inpiu默认值-->
			$(".queryKey").html(template('queryKey', {
				items: data.resultObject.defaultSearch
			}))
			if (data.resultObject.defaultSearch != null && data.resultObject.defaultSearch.length > 0) {
				localStorage.setItem("defaultKey", data.resultObject.defaultSearch[0].name);
			}
		}
},false)

/*is_weixn();
function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        alert("微信打开");
		console.log(111);
		$(".minirefresh-wrap").css("bottom",".98rem");
    } else {
//      alert("app打开");
		$(".minirefresh-wrap").css("bottom","0");
		console.log(222);
    }
}*/

//底部--购物车数量
requestGetService("/xczh/shop/cart/quantity",null,function (data) {
    if (data.success == true) {
    	var quantity = data.resultObject;
    	
//  	$(".shopping_quantity").html(quantity);
       
        if(quantity == null || quantity == 0){
       		$(".shopping_carts span").hide();
        }else{
       		$(".shopping_carts span").html(quantity);	
       		$(".shopping_carts span").show();
        };
        /*var shoppimgNumber = $(".shopping_quantity").html();
        if(shoppimgNumber = "0"){
       		$(".shopping_quantity").hide();
        }else{
       		$(".shopping_quantity").html(quantity);	
       		$(".shopping_quantity").show();
        };*/
        
    }
});
