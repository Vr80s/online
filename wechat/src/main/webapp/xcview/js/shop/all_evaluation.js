$(function () {

    appointmentList(1,'down');  /*定义一个方法*/

})

var productId = getQueryString("productId");
function appointmentList(pageNumber, downOrUp) {
	requestService("/xczh/shop/goods/review",{
		productId:10152,
		pageNumber:pageNumber,
		pageSize:10
	},function (data) {
	    if (data.success == true) {
	    	var obj = data.resultObject;
			if(downOrUp=='down'){
	            // 评价列表
	            $(".recommends").html(template('shop_recommend', {items: obj}));
	            miniRefresh.endDownLoading(true);// 结束下拉刷新
	        } else if(obj.length==0){
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
       
        if(quantity == null){
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

        // 点击加入购物车-封面图
        $(".message").html(template('message', {items: obj}));
        // 选择规格
        
        $(".specifications").html(template('specifications', {item: obj.specificationItemvs}));
       
        // $(".specificationsss").html(template('specificationsss', {item: obj.specificationItemvs}));
        
        $(".category").html(template('category', {item: obj.specificationItemvs}));
        

        var skus = obj.skuVOs;
        var specificationIds = [];
        var defaultSkus = {};
        //var skuData = [];
        
        for (var i = 0; i < skus.length; i++) {
        	if(skus[i].isDefault){
        		specificationIds = skus[i].specificationValueIds;
        		defaultSkus = skus[i];
        	}
        	if(skus[i].specificationValueIds!=null &&
        		skus[i].specificationValueIds.length>0){
        			
        	   skus[i].specificationIdsStr = skus[i].specificationValueIds.join(',');
        	}
        }
        
          /**
         * 显示库存是否充足
         * 	当前价格 
         */
        if(defaultSkus!=null){
         	$(".information .price").html("￥"+defaultSkus.price);
       	    $(".information .repertory").html("库存"+defaultSkus.stock+"件");
        }
        
		//默认选中
        $(".specification").each(function(index,obj){
        	 var dataId = $(obj).attr("data-id");
        	 for (var i = 0; i < specificationIds.length; i++) {
	        	 if(dataId == specificationIds[i]){
	        	 	$(obj).removeClass("hide");
	        	 }
        	 }
        })
        
        $('.specifications_ul .casing').each(function(index,obj){
        	 var dataId = $(obj).attr("data-id");
        	 for (var i = 0; i < specificationIds.length; i++) {
	        	 if(dataId == specificationIds[i]){
	        	 	$(obj).addClass("public");
	        	 }
        	 }
        })
        
//     	 点击规格li
         $('.specifications_ul .casing').click(function(){
         	// 判断显示已选择
            if ($(".include").hasClass("public")) {
                $(".kind").show();
                $(".specification").show();
                $(".choice").html("已选择");
            };
            $(this).addClass('public');
            $(this).siblings().removeClass('public');
            
            $(".specification").addClass("hide");
            
            var lalala = [];
            $('.specifications_ul .casing[class*="public"]').each(function(index,obj){
            	 var dataId = $(obj).attr("data-id");
            	 $(".specification").each(function(index,objs){
            	 	 var dataIds = $(objs).attr("data-id");
		        	 if(dataId == dataIds){
		        	 	$(objs).removeClass("hide");
						
		        	 	lalala.push(dataIds);
		        	 }
		        })
            })
            
            var currentSku = {};
            for (var i = 0; i < skus.length; i++) {
            	if(skus[i].specificationIdsStr == lalala.join(",")){
            		currentSku = skus[i];
            	}
            }
            
            if(currentSku!=null){
	         	$(".information .price").html("￥"+currentSku.price);
	       	    $(".information .repertory").html("库存"+currentSku.stock+"件");
        	}
        });
        
    }
});