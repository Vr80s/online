

var productId = getQueryString("productId");
requestGetService("/xczh/shop/goods/details",{
    productId:productId,
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
        
        // 判断商品介绍为空
        if (obj.caption == null) {
            $(".referral").hide();
        }else{
            $(".referral").show();
        };

        /*var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationType: 'fraction'
        });*/
        




    }
});



