$(function(){
	(function(){	
		var orderType;
		shopList(1)
		function shopList(pageNumber,orderType){
			var keyword=$.trim($(".input-code").val()),
				minSales=$.trim($(".min-sales").val()),
				maxSales=$.trim($(".max-sales").val()),
				minPrice=$.trim($(".min-price").val()),
				maxPrice=$.trim($(".max-price").val());
			var shopData={};
				shopData.pageNumber=pageNumber,
				shopData.pageSize=10,
				shopData.keyword=keyword,
	//			销量低到高
				shopData.minSales=minSales,
				shopData.maxSales=maxSales,
	//			价格低到高
				shopData.minPrice=minPrice,
				shopData.maxPrice=maxPrice,
	//			自己的商品
				shopData.all=0;
				if(orderType != null || orderType != ""){
					shopData.orderType=orderType;
				}else{
					shopData.orderType="";
				}
			
			RequestService("/doctor/product/list", "get",shopData, function (data) {
		  		if (data.success==true) {
		  			var shopData=data.resultObject.content;
		  			if(shopData.length==0){
		  				$(".shop-null").removeClass("hide");
		  			}else{
		  				$(".shop-null").addClass("hide");
		  				$("#shop-list-ul").html(template("shop-template",{items:shopData}))
		  			}
	//	  			分页
					var aa=Math.ceil(data.resultObject.total/10)
	 				if (aa > 1) { //分页判断,接口总页数
		                    $(".not-data").remove();
		                    $(".shop_pages").removeClass("hide");
		                    $(".shop_pages .searchPage .allPage").text(aa);  //接口总页数
		                    $("#Pagination_shop").pagination(aa, {			//接口总页数
		                        num_edge_entries: 1, //边缘页数
		                        num_display_entries: 4, //主体页数
		                        current_page: pageNumber - 1,  //所传的页数
		                        callback: function (page) {
		                            //翻页功能
		                            shopList(page + 1,orderType);
		                        }
		                    });
		                } else {
		                    $(".shop_pages").addClass("hide");
		                }
		  		} else{
		  			
		  		}
		  })
		}
	
		//		清空筛选条件
		$(".clear-screen").click(function(){
			$(".input-code, .min-sales, .max-sales, .min-price, .max-price").val("");
		})
//		点击筛选条件进行筛选
		$(".screen-commodity").click(function(){
			shopList(1)
		})
//		点击升序,降序进行筛选
		$(".shopping-list-top").on("click","li i",function(){
			var dataType=$(this).attr("data-style");
			$(".shopping-list-top li i").removeClass("active");
			$(this).addClass("active")
			shopList(1,dataType)
		})
	})();
})
