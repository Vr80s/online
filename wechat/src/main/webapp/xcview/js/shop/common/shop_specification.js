/**
 * 
 */
 
 
var productId = getQueryString("productId");
var currentSku = null;
var specificationsResutl  = [];
var productDetailsObj = {}; 
 
function specificationChoose(obj){

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
    
//     	 点击规格li
     $('.specifications_ul .casing').click(function(){
     	
     	currentSku =null;
     	
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
	         	$(".information .price").html(currentSku.price);
        	}else{
        	 	$(".information .repertory").html("该商品库存不足");
	            $(".shopping_trolley_center .determine").css("background","#aaaaaa");
        	}
	 	}else{
	 		$(".shopping_trolley_center .determine").css("background","#aaaaaa");
	 	}
        
    });
}