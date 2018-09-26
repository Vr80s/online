

var productId = getQueryString("productId");
requestGetService("/xczh/shop/goods/details",{
    productId:productId,
},function (data) {
    if (data.success == true) {
        var obj = data.resultObject;
        $(".list_details").html(template('list_details', {items: obj}));
        // $(".swiper-wrapper").html(template('top_details', {items: obj}));
        // $(".banner").html(template('top_details', {items: obj}));
        $(".banner").html(template('top_details', {items: obj.productImages}));
        
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



