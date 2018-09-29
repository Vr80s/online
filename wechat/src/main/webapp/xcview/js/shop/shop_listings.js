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