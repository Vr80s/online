
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
    /*requestGetService("/xczh/doctors/banner",null,function(data) {
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

        }
    });*/

	requestService("/xczh/doctors/banner", null,
        function (data) {
            if (data.success) {

                //swiper轮播开始
                var result_play = data.resultObject.banner.records;
                var str_play = "";
                for (var int = 0; int < result_play.length; int++) {
                    var wb = result_play[int];
                    str_play += "<div class='swiper-slide swiper-banner swiper-banner-play'>" +
                        "<img src='" + wb.imgPath + "?imageView2/2/w/750'  data_target='" + wb.target + "' data_id='" + wb.id + "' data_url='" + wb.url + "' style='width: 7.5rem;height:3.2rem;' />" +
                        "</div>";
                }
                $("#wrapper-box-play").html(str_play);
                var mySwiper = new Swiper('#swiper-container-play', {
                    pagination: '#swiper-banner-list-play',
                    loop: true,
                    autoplay: 3000,
                    autoplayDisableOnInteraction: false,
                    //pagination : '#swiper-pagination1',
                })
            } else {
                alert("网络异常");
            }
            ;
//				轮播图跳转
            $(".swiper-banner-play").click(function () {
                var data_id = $(this).find("img").attr("data_id");
                clickBanner(data_id);
                //页面跳转
                var data_url = $(this).find("img").attr("data_url");
                var data_target = $(this).find("img").attr('data_target');

                bannerJump(data_target);
            })
            //swiper轮播结束
            if (data.success == true) {
                $(".newests").html(template('newests', {items: data.resultObject.allCourseList}))
                $(".newest_title").click(function () {
                    var lineState = $(this).attr("lineState");
                    window.location.href = "/xcview/html/curriculum_table.html?courseType=3&lineState=" + lineState + "";
                })
            }
    })


	    
};

