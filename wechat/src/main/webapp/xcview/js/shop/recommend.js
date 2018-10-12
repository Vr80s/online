$(function () {

    appointmentList(1,'down');  /*定义一个方法*/

})

var productId = getQueryString("productId");
var currentSku = null;
function appointmentList(pageNumber, downOrUp) {

	requestService("/xczh/shop/goods/recommends",{
		productId:productId,
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

        // 点击加入购物车-封面图
        $(".message").html(template('message', {items: obj}));
        // 选择规格
        
        specificationsResutl =  obj.specificationItemvs;
        $(".specifications").html(template('specifications', {item: obj.specificationItemvs}));
        
        $(".category").html(template('category', {item: obj.specificationItemvs}));
        

        var skus = obj.skuVOs;
        var specificationIds = [];
        var defaultSkus = {};
        
        for (var i = 0; i < skus.length; i++) {
        	if(skus[i].isDefault){
        		specificationIds = skus[i].specificationValueIds;
        		defaultSkus = skus[i];
        	}
        	if(skus[i].specificationValueIds!=null &&
        		skus[i].specificationValueIds.length>0){
        			
        	   skus[i].specificationIdsStr = skus[i].specificationValueIds.sort().join(',');
        	}
        }
        
          /**
         * 显示库存是否充足
         * 	当前价格 
         */
        if(defaultSkus!=null){
        	
        	if(defaultSkus.isOutOfStock){
        		 $(".information .repertory").html("该商品库存不足");
        		 $(".shopping_trolley_center .determine").css("background","#aaaaaa");
        	}else{
        		 $(".information .repertory").html("库存"+defaultSkus.availableStock+"件");
        		 $(".shopping_trolley_center .determine").css("background","#F97215");
        	}
         	$(".information .price").html("￥"+defaultSkus.price);
       	    currentSku = defaultSkus;
        }
        
		//默认选中
//        $(".specification").each(function(index,obj){
//        	 var dataId = $(obj).attr("data-id");
//        	 for (var i = 0; i < specificationIds.length; i++) {
//	        	 if(dataId == specificationIds[i]){
//	        	 	$(obj).removeClass("hide");
//	        	 }
//        	 }
//        })
        
//        $('.specifications_ul .casing').each(function(index,obj){
//        	 var dataId = $(obj).attr("data-id");
//        	 for (var i = 0; i < specificationIds.length; i++) {
//	        	 if(dataId == specificationIds[i]){
//	        	 	$(obj).addClass("public");
//	        	 }
//        	 }
//        })
        
//     	 点击规格li
         $('.specifications_ul .casing').click(function(){
         	// 判断显示已选择
            if ($(".include").hasClass("public")) {
                $(".kind").show();
                $(".specification").show();
            };
            $(this).addClass('public');
            $(this).siblings().removeClass('public');
            
            $(".specification").addClass("hide");
            $(".specification").removeClass("showfalg");
            
            var lalala = [];
            $('.specifications_ul .casing[class*="public"]').each(function(index,obj){
            	 
            	 var dataId = $(obj).attr("data-id");
            	 $(".specification").each(function(index,objs){
            	 	 var dataIds = $(objs).attr("data-id");
            	 	 var dataName = $(objs).attr("title");
		        	 if(dataId == dataIds){
		        	 	$(objs).addClass("showfalg");
		        	 	lalala.push(dataIds);
		        	 	 $(".specification_name").each(function(index,obj){
		        	 		var title = $(obj).attr("title");
		        	 		if(dataName == title){
		        	 			$(obj).addClass("hide");
		        	 		}
		        	 	})
		        	 }
		        })
            })
            
		    var specificationNames = $(".specification_name[class*='hide']");
    	 	if(specificationNames!=null
    	 		    && specificationNames.length == specificationsResutl.length){
    	 			
    	 		 $(".shopping_trolley_center .determine").css("background","#F97215");   	
    	 		 $(".showfalg").removeClass("hide");
    	 		 $(".choice").html("已选择");
    	 		 
    	 		 
	 		    if(lalala!=null){
	             	lalala.sort();
	            }
	            
	            for (var i = 0; i < skus.length; i++) {
	            	if(skus[i].specificationIdsStr == lalala.join(",")){
	            		currentSku = skus[i];
	            	}
	            }
	            
	            if(currentSku!=null){
	            	if(currentSku.isOutOfStock){
		        		 $(".information .repertory").html("该商品库存不足");
		        		 $(".shopping_trolley_center .determine").css("background","#aaaaaa");
		        	}else{
		        		 $(".information .repertory").html("库存"+currentSku.availableStock+"件");
		        		  $(".shopping_trolley_center .determine").css("background","#F97215");
		        	}
		         	$(".information .price").html("￥"+currentSku.price);
		       	    
	        	}
    	 	}else{
    	 		$(".shopping_trolley_center .determine").css("background","#aaaaaa");
    	 	}
            
        });
        
    }
});