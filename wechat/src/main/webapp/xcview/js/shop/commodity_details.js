

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

        $('.specifications_ul .casing').click(function(){
             // 判断显示已选择
                if ($(".include").hasClass("public")) {
                    $(".kind").show();
                    $(".specification").show();
                    $(".choice").html("已选择");
                };
                $(this).addClass('public');
                $(this).siblings().removeClass('public');
                var publicHtml = $(this).html();
                $(".specification").html(publicHtml);

                /*var publicHtml = $(this).html();
                var detaId = $(this).attr("data-ids");// 一级id
                $(".fl1_"+detaId).html(publicHtml);*/

        });



    }
});

/*$(".specifications_ul .include").click(function(){
        alert(11111);
    });*/

