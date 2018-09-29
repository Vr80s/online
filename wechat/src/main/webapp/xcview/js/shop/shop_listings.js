

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
            $(".product_list").html(template('product_list',
            			{items: obj}));
        }
    });
}
/**
 * 默认查询
 */
listData(0,null,"RECOMMEND_DESC",keyWord);    