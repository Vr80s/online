$(function () {

    requestService("/xczh/shop/goods/banner",null,function (data) {
        if (data.success == true) {   
            $(".top_details").html(template('top_details', {items: data.resultObject}));
            // 轮播--渲染时放到，渲染的js下面
            var mySwiper = new Swiper('.banner',{
                autoplay:1500,
                visibilityFullFit : true,
                loop:true,
                pagination : '.pagination',
            });

            var a = $(".swiper-slide").css("display");
            if (a == "block") {
                $(".banner").show();
            } else {
                $(".banner").hide();
            }

            $(".swiper-slide img").click(function () {
                var data_id = $(this).attr("data_id");
                //增加banner的点击量
                clickBanner(data_id);
                //页面跳转
                // var data_url = $(this).find("img").attr("data_url");
                var data_target = $(this).attr('data_target');

                bannerJump(data_target);
            })

            // 点击banner跳转
            function bannerJump(target) {
                if (!target) {
                    return ;
                } else {
                    // 定义跳转路径--共用
                    if(document.location.host.indexOf('dev.ixincheng.com')!=-1){
                        target = "/apis"+target;
                    }

                    location.href = target;
                }
            }
        }
    });

    //增加点击数banner
    function clickBanner(id) {
        requestService("/xczh/recommend/clickBanner", {
            id: id
        }, function (data) {

        });
    }


    // 商品列表
    // var DESC = 'RECOMMEND_DESC';
    requestGetService("/xczh/shop/goods/list",{
        pageNumber:1,
        pageSize:10
    },function (data) {
        if (data.success == true) {
            // $(".product_list").html(template('product_list', {items: data.resultObject}));
            $(".product_list").html(template('product_list', {items: data.resultObject}));
            // $(".referrer").html(template('referrer', {items: data.resultObject.doctor}));

            /*if (data.resultObject.productImages[0] == null) {
                alert(111);
            }else{
                alert(222);
            };*/
            
            // $(".referrer span").html(data.resultObject[0].doctor.name);
            // $(".referrer img").src(data.resultObject[0].doctor.avatar);
        }

        // 点击进入详情
        $(".product_list li").click(function(){
            var id = $(this).attr("data-id");
            window.location.href = "/xcview/html/shop/commodity_details.html?productId=" + id + "";
            /*if(doctorType == 1){
                window.location.href = "/xcview/html/physician/physicians_page.html?doctor=" + id + "";
            } else {
                window.location.href = "/xcview/html/live_personal.html?userLecturerId=" + userId + "";
            }*/

        })


    });

    



})

