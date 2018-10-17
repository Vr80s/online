$(function () {

    appointmentList(1,'down');  /*定义一个方法*/

})

var productId = getQueryString("productId");
var currentSku = null;
function appointmentList(pageNumber, downOrUp) {
	requestService("/xczh/shop/goods/review",{
		productId:productId,
		pageNumber:pageNumber,
		pageSize:10
	},function (data) {
	    if (data.success == true) {
	    	var obj = data.resultObject;
	    	var recommendsHide = $(".recommends").html();
			if (recommendsHide==null || recommendsHide=="") {
//			    alert(1);
				$("#minirefresh").hide();
			    $(".quie_pic").show();
			};
			
			if(downOrUp=='down'){
	            // 评价列表
	            $(".recommends").html(template('shop_recommend', {items: obj}));
	            miniRefresh.endDownLoading(true);// 结束下拉刷新
	        } else if(obj==null){
	            miniRefresh.endUpLoading(true);// 结束上拉加载
	        } else {
	           	$(".recommends").append(template('shop_recommend', {items: obj}));
	            miniRefresh.endUpLoading(false);
	        }
	        
//	       $(".recommends").html(template('shop_recommend', {items: obj}));
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

//底部--购物车数量
requestGetService("/xczh/shop/cart/quantity",null,function (data) {
    if (data.success == true) {
    	var quantity = data.resultObject;
    	
//  	$(".shopping_quantity").html(quantity);
       
        if(quantity == null || quantity == 0){
       		$(".shopping_quantity").hide();
        }else{
       		$(".shopping_quantity").html(quantity);	
       		$(".shopping_quantity").show();
        };
        
    }
});


//点击购物车效果
requestGetService("/xczh/shop/goods/details",{
    productId:productId
},function (data) {
    if (data.success == true) {
        var obj = data.resultObject;
        if (data.resultObject.specificationItemvs == null) {
			$(".specifications").hide();
			$(".category").hide();
		}

         // 点击加入购物车-封面图
        $(".message").html(template('message', {items: obj}));
        // 选择规格
        
        specificationsResutl =  obj.specificationItemvs;
        $(".specifications").html(template('specifications', {item: obj.specificationItemvs}));
        
        $(".category").html(template('category', {item: obj.specificationItemvs}));
        

        specificationChoose(obj);
    }
});