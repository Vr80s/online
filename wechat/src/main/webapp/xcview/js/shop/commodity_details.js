
$(function(){
	function quantity(){
		requestGetService("/xczh/shop/cart/quantity",null,function (data) {
		    if (data.success == true) {
		    	var quantity = data.resultObject;
		        if(quantity == null || quantity == 0){
		       		$(".shopping_quantity").hide();
		        }else{
		       		$(".shopping_quantity").html(quantity);	
		       		$(".shopping_quantity").show();
		        };
		    }
		});
	};
	setTimeout(function(){
		quantity();
	},100);

});

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
        
        try{
        
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
			$(".ruling_price span").html(v);
			
			
			var j = $(".original_price span").html();
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
			$(".original_price span").html(j);
        }catch(e){
           console.error(e);
        }

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
            $(".evaluate_main").html(template('evaluate_main', {items: obj.reviewvs[0]}));
            $(".evaluate_top .reviewvCount").html(data.resultObject.reviewvCount);
            $(".no_evaluation").hide();
        }else{
            $(".evaluate").show();
//          $(".evaluate_main").html(template('evaluate_main', {items: obj.reviewvs}));
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
        
        specificationChoose(obj);
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

//底部--购物车数量

//点击购物车
$(".shopping_cart").click(function(){
	requestPostService("/xczh/myinfo/myFocus",null,function (data) {
	    if (data.success == true) {
	       	location.href ='/xcview/html/shop/shopping_trolley.html'
	    }
	});
});

    

