

var productId = getQueryString("productId");
var currentSku = null;
var specificationsResutl  = [];
requestGetService("/xczh/shop/goods/details",{
    productId:productId
},function (data) {
    if (data.success == true) {
        var obj = data.resultObject;
        // 详情
        $(".list_details").html(template('list_details', {items: obj}));
        var v = $(".ruling_price span").html();
		if(/^\d+$/.test(v)){
		v = v + ".00";
		}else if(/^(\d+\.)(\d+)$/.test(v)){
			var i = RegExp.$1;
			var t = RegExp.$2;
			if(t.length == 1)
			v = v + "0";
			else if(t.length > 2)
			v = i + t.substring(0,2);
		}
		//alert(v);
		$(".ruling_price span").html(v);

        // $(".swiper-wrapper").html(template('top_details', {items: obj}));
        // $(".banner").html(template('top_details', {items: obj}));
        // 轮播图
        $(".banner").html(template('top_details', {items: obj.productImages}));
        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationType: 'fraction'
        });

        // 判断商品介绍为空
        if (obj.caption == null) {
            $(".referral").hide();
        }else{
            $(".referral").show();
        };

        // 医师推荐
        if (obj.posts.length>0) {
            $(".physician_recommend").show();
            $(".recommend_main").html(template('recommend_main', {items: obj.posts}));
            $(".no_recommendation").hide();
        }else{
            $(".physician_recommend").hide();
//          $(".recommend_main").html(template('recommend_main', {items: obj.posts}));
//          $(".no_recommendation").show();
        };

        // 评价
//      if (obj.posts !=null) {
        if (isNotBlank(obj.reviewvs)) {
            $(".evaluate").show();
            $(".evaluate_main").html(template('evaluate_main', {items: obj.reviewvs}));
            $(".no_evaluation").hide();
        }else{
            $(".evaluate").show();
            $(".evaluate_main").html(template('evaluate_main', {items: obj.reviewvs}));
            $(".no_evaluation").show();
        };
        
        // 商品详情
        if (obj.introduction == null) {
            $(".commodity_details").hide();
        }else{
            $(".commodity_details").show();
            $(".commodity_details_center").html(obj.introduction);
        };
        
//      选择空--无规格
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
         	$(".information .price").html(defaultSkus.price);
         	
         	var j = $(".information .price").html();
			if(/^\d+$/.test(j)){
			j = j + ".00";
			}else if(/^(\d+\.)(\d+)$/.test(j)){
				var i = RegExp.$1;
				var t = RegExp.$2;
				if(t.length == 1)
				j = j + "0";
				else if(t.length > 2)
				j = i + t.substring(0,2);
			}
			//alert(v);
			$(".information .price").html(j);
         	
         	
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

function listClick(){
    // 点击进入详情
    $(".recommend").click(function(){
        window.location.href = "/xcview/html/shop/recommend.html?productId=" + productId + "";
    })
};
function evaluation(){
    // 点击全部评价
    $(".evaluate_top").click(function(){
        window.location.href = "/xcview/html/shop/all_evaluation.html?productId=" + productId + "";
    })
};
/*$(".specifications_ul .include").click(function(){
        alert(11111);
    });*/

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
        /*var shoppimgNumber = $(".shopping_quantity").html();
        if(shoppimgNumber = "0"){
       		$(".shopping_quantity").hide();
        }else{
       		$(".shopping_quantity").html(quantity);	
       		$(".shopping_quantity").show();
        };*/
        
    }
});


