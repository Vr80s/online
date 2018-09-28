$(function () {

    appointmentList(1,'down');  /*定义一个方法*/

})



var productId = getQueryString("productId");

function appointmentList(pageNumber, downOrUp) {

	requestService("/xczh/shop/goods/recommends",{
		productId:10152,
		pageNumber:pageNumber,
		pageSize:10
	},function (data) {
	    if (data.success == true) {
	    	var obj = data.resultObject;
			if(downOrUp=='down'){
                // 推荐列表
                $(".recommends").html(template('shop_recommend', {items: obj}));
                miniRefresh.endDownLoading(true);// 结束下拉刷新
            } else if(obj.length==0){
                miniRefresh.endUpLoading(true);// 结束上拉加载
            } else {
	           	$(".recommends").append(template('shop_recommend', {items: obj}));
                miniRefresh.endUpLoading(false);
            }
	    }else{
	        jqtoast(data.errorMessage);
	    }
	});

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
            appointmentList(page,'down');

        }
    },
    up: {
        isAuto: false,
        callback: function () {
            page++;
            appointmentList(page,'up');
        }
    }
});
