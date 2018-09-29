

var productId = getQueryString("productId");
requestGetService("/xczh/shop/goods/details",{
    productId:productId
},function (data) {
    if (data.success == true) {
        var obj = data.resultObject;
        // 详情
        $(".list_details").html(template('list_details', {items: obj}));
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
        if (obj.posts !=null) {
            $(".physician_recommend").show();
            $(".recommend_main").html(template('recommend_main', {items: obj.posts}));
        }else{
            $(".physician_recommend").hide();
        };

        // 评价
        if (obj.posts !=null) {
            $(".evaluate").show();
            $(".evaluate_main").html(template('evaluate_main', {items: obj.reviewvs}));
        }else{
            $(".evaluate").hide();
        };
        
        // 商品详情
        if (obj.introduction == null) {
            $(".commodity_details").hide();
        }else{
            $(".commodity_details").show();
            $(".commodity_details_center").html(obj.introduction);
        };

        // 点击加入购物车-封面图
        $(".message").html(template('message', {items: obj}));
        // 选择规格
        
        
        $(".specifications").html(template('specifications', {item: obj.specificationItemvs}));
       
        // $(".specificationsss").html(template('specificationsss', {item: obj.specificationItemvs}));
        
        $(".category").html(template('category', {item: obj.specificationItemvs}));
        

        // 点击规格
        /*var aBtn=$('.specifications_ul .casing');
        for(i=0;i<aBtn.length;i++){
            $(aBtn[i]).click(function(){
                var specification = document.querySelector(".specification");  //规格
                specification.style.display="none";
                // 判断显示已选择
                if ($(".include").hasClass("public")) {
                    $(".kind").show();
                    $(".specification").show();
                    $(".choice").html("已选择");
                };
                for(i=0;i<aBtn.length;i++){
                    $(aBtn[i]).siblings().removeClass('public');
                }
                $(this).addClass('public');
                // var detaId = $(this).attr("data-id");
                alert(detaId);
                var publicHtml = $(this).html();
                $(".specification").html(publicHtml);
            })
        };*/

       

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

function listClick(){
    // 点击进入详情
    $(".recommend").click(function(){
//      var id = $(this).attr("data-id");
//var productId = getQueryString("productId");
        window.location.href = "/xcview/html/shop/recommend.html?productId=" + productId + "";
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
       
        if(quantity == null){
       		$(".shopping_quantity").hide();
        }else{
       		$(".shopping_quantity").html(quantity);	
       		$(".shopping_quantity").show();
        };
        
    }
});

