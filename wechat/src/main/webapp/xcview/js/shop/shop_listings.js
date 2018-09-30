$(function () {

    listData(1,'down');  /*定义一个方法*/

})

var keyWord = getQueryString("queryKey");

/**
 * 
 * @param {} pageNumber 当前第几页
 * @param {} downOrUp   
 * @param {} orderType  排序类型
 * @param {} keyWord    关键字
 */
function listData(pageNumber, downOrUp,orderType,keyWord){
    
    requestGetService("/xczh/shop/goods/list",{
        pageNumber:pageNumber,
        pageSize:6,
        orderType:orderType,
        keyWord:keyWord
    },function (data) {
        if (data.success == true) {
            var obj = data.resultObject;
            /*$(".product_list").html(template('product_list',{items: obj}));*/
            
            if(downOrUp=='down'){
	            // 评价列表
	            $(".product_list").html(template('product_list',{items: obj}));
	            miniRefresh.endDownLoading(true);// 结束下拉刷新
	        } else if(obj==null){
	            miniRefresh.endUpLoading(true);// 结束上拉加载
	        } else {
//	           	$(".recommends").append(template('shop_recommend', {items: obj}));
	           	$(".product_list").append(template('product_list',{items: obj}));
	            miniRefresh.endUpLoading(false);
	        }
	        
	        
        }
    });
}
/**
 * 默认查询
 */
listData(0,null,"RECOMMEND_DESC",keyWord);


//点击选项
$(".default_click").click(function(){
    // 商品列表--推荐
    var recommend = 'RECOMMEND_DESC';
    requestGetService("/xczh/shop/goods/list",{
        pageNumber:1,
        pageSize:10
    },function (data) {
        if (data.success == true) {
            var obj = data.resultObject;
            $(".product_list").html(template('product_list', {items: obj}));
            /*$(".product_list li").click(function(){
		        var id = $(this).attr("data-id");
		        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
		    })*/
//              listClick();
        }
    });
});

$(".option_newest").click(function(){
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
            /*$(".product_list li").click(function(){
		        var id = $(this).attr("data-id");
		        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
		    })*/
//              listClick();
        }
    });
});

$(".option_hottest").click(function(){
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
            /*$(".product_list li").click(function(){
		        var id = $(this).attr("data-id");
		        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
		    })*/
//              listClick();
        }
    });
});


$(".option_price").click(function(){
    // 商品列表--价格升序
    var ascending = 'PRICE_ASC';
    requestGetService("/xczh/shop/goods/list",{
        pageNumber:1,
        pageSize:10,
        orderType:ascending
    },function (data) {
        if (data.success == true) {
            var obj = data.resultObject;
            $(".product_list").html(template('product_list', {items: obj}));
            /*$(".product_list li").click(function(){
		        var id = $(this).attr("data-id");
		        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
		    })*/
//              listClick();
        }
    });
});

 $(".low_to_high").click(function(){
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
            /*$(".product_list li").click(function(){
		        var id = $(this).attr("data-id");
		        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
		    })*/
//              listClick();
        }
    });
});

$(".down").click(function(){
    // 商品列表--价格升序
    var ascending = 'PRICE_ASC';
    requestGetService("/xczh/shop/goods/list",{
        pageNumber:1,
        pageSize:10,
        orderType:ascending
    },function (data) {
        if (data.success == true) {
            var obj = data.resultObject;
            $(".product_list").html(template('product_list', {items: obj}));
            /*$(".product_list li").click(function(){
		        var id = $(this).attr("data-id");
		        window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
		    })*/
//              listClick();
        }
    });
});

var urlAttribute=getQueryString('queryKey')
if (urlAttribute=='' || urlAttribute== null) {
	
} else{
//	$('.header_seek_main .span_hide').hide();
//	$('.header_seek_main').append('<span style='margin-left: 0.38rem;margin-top: -0.6rem;'>' + urlAttribute + '</span>');
	/*$('.header .header_default').text(urlAttribute);
	$('.header .tacitly_approve').html(urlAttribute);*/
	$('.header').append('<span>' + urlAttribute + '</span>');
	
}



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
            listData(page,'down');

        }
    },
    up: {
        isAuto: false,
        callback: function () {
            page++;
            listData(page,'up');
        }
    }
});