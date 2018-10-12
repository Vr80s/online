/*定义一个方法*/
/*$(function () {

    refurbish(1,'down');  

})*/
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
            
            if(downOrUp=='down' && obj!=null && obj.length<=0){

				//TODO  这里搞个默认图片            	
            	$(".product_list").html("额,没有商品哎~");
            	
	        }else if(downOrUp=='down' && obj!=null && obj.length>0){
	            $(".product_list").html(template('product_list',{items: obj}));
	        	miniRefresh.endDownLoading(true);// 结束下拉刷新
	        } else if(downOrUp=='up' && obj!=null && obj.length<=0){
	            miniRefresh.endUpLoading(true);// 结束上拉加载
	        } else if(downOrUp=='up' && obj!=null && obj.length>0){
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
listData(1,"down","RECOMMEND_DESC",keyWord);


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
            //listData(page,'down');

		    refurbish(page,'down');          
        }
    },
    up: {
        isAuto: false,
        callback: function () {
            page++;
            //listData(page,'up');
            
            refurbish(page,'up');
        }
    }
});


function refurbish(page,downUp){
	
	 /*
     * 获取当前排序规则
     */
    var orderType = "RECOMMEND_DESC";
    var defaultClick = 
    	$("#mainMenuBar li[class*='default_click']");
    
    if(defaultClick!=null && defaultClick.length>0){
    	if(defaultClick.hasClass("option_recommend")){
    		orderType =  "RECOMMEND_DESC";
    	}else if(defaultClick.hasClass("option_newest")){
    	    orderType =  "DATE_DESC";
    	}else if(defaultClick.hasClass("option_hottest")){
    	    orderType =  "SALES_DESC";
    	}
    	
    }else{
		var display1 = $(".low_to_high").css("display");
		if(display1 == "list-item"){
			orderType = "PRICE_ASC";
		}
    	var display2 = $(".down").css("display"); 
        if(display2 == "list-item"){
        	orderType = "PRICE_DESC";
		}
    } 	
    listData(page,downUp,orderType,keyWord);
}



