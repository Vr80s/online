$(function(){
//	导航字体颜色
	$(".header_left .path .recommend").css({"color":"#00BC12"})
//	轮播图速度控制
	$('.carousel').carousel({
	  interval: 3000
	})
	$(".hover-select").hover(function(){
		$(".banner-prev").fadeIn(200)
	},function(){
		$(".banner-prev").fadeOut(200)
		
	})
	//1：认证医师 2：认证医馆 3：医师认证中 4：医馆认证中 5：医师认证被拒 6：医馆认证被拒 7：即没有认证医师也没有认证医馆
	
    //登入之后进行判断 右侧医师入驻入口是否有
    RequestService("/medical/common/isDoctorOrHospital", "GET", null, function (data) {
        if (data.success == true) {
            //判断
            if (data.resultObject == 1 || data.resultObject == 2) {
                //医师认证成功
                $('.wrap-anchor').addClass('hide');
            } else {
                $('.wrap-anchor').removeClass('hide');
            }
        } else if (data.success == false && data.errorMessage == "请登录！") {
            $('.wrap-anchor').removeClass('hide');
        }
    });
	
	
	
})
