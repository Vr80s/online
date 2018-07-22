
window.onload = function(){
	
	
 	requestService("/xczh/bunch/hotDoctorSearch", null,function (data) {
	    if (data.success == true) {
		    //给头部默认值
	        $('#defaultSearch').html(template('defaultSearchs', {items: data.resultObject.defaultSearch}));
	        // 热门搜索
	        //$('#hotSearch').html(template('hotSearchs', {items: data.resultObject.hotSearch}));
//	        if (data.resultObject.hotSearch == 0 || data.resultObject.hotSearch == null) {
//	            	$(".hot_search").hide();
//	            } else {
//		        	$('#hotSearch').html(template('hotSearchs', {items: data.resultObject.hotSearch}));
//	        }
	    }
	})
	
	requestService("/xczh/doctors/category", null, function (data) {
		if (data.success == true) {
			$('#hot_title').html(template('gzy_id',{items: data.resultObject}));
		};
		
		$(".physician_cen").click(function(){
			 var id = $(this).attr("data-id");
			 window.location.href = "/xcview/html/physician/physicians_page.html?doctor=" + id + "";
		})
		
		// 点击title跳转
		$("#hot_title .physician_title").click(function () {
	        var code_num = $(this).attr("code");
	        window.location.href = "/xcview/html/physician/physician_list.html?type=" + code_num + "";
	    });
		
		
	},false);

	// banner跳转
    requestGetService("/xczh/doctors/banner",null,function(data) {
        if(data.success==true){
            var obj = data.resultObject;
            $(".banner_img").html(template('top_details',{items:obj}));            
            // 轮播滑动js
            var mySwiper = new Swiper('.swiper-container', {
                autoplay: true, //可选选项，自动滑动
                loop: true,
                pagination: {
                    el: '.swiper-pagination'
                }
            });

            $(".swiper-banner-btn").click(function () {
                var data_id = $(this).find("img").attr("data_id");
                //增加banner的点击量
                clickBanner(data_id);
                //页面跳转
                // var data_url = $(this).find("img").attr("data_url");
                var data_target = $(this).find("img").attr('data_target');

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

    // 点击刷新图片
    $(".switchover").click(function () {
        // 隐藏刷新动画转圈效果
/*        if($('.physician_title_img').is('.img')){
            $('.physician_title_img').removeClass('img');
        }else{
            $(".physician_title_img").addClass("img");
            myFunction();
            clickSwitchover();
        }
*/
        myFunction();
        clickSwitchover();

 
    });
    function myFunction() {
        setTimeout(function(){
            $('.physician_title_img').removeClass('img');
        }, 400);
    };

    function clickSwitchover(){
        // 热门医师 换一批
        requestService("/xczh/doctors/hotInBatch",null, function (data) {
            if (data.success==true) {
                $('.exothecium').html(template('exothecium_id', {items: data.resultObject}));
                
                $(".batch_main_bg:eq(0)").addClass("batch_main_bg_one");
                $(".batch_main_bg:eq(1)").addClass("batch_main_bg_two");
                $(".batch_main_bg:eq(2)").addClass("batch_main_bg_three");
                $(".batch_main_bg").css("background-size","2.27rem 3.18rem");
                
                // 详情跳转
                $(".batch_main").click(function(){
                    var id = $(this).attr("data-ids");
                    window.location.href = "/xcview/html/physician/physicians_page.html?doctor=" + id + "";
                });
            }
        });
    }
    clickSwitchover();

    

};

