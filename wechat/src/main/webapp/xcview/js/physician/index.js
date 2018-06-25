
window.onload = function(){
	
	
 	requestService("/xczh/bunch/hotDoctorSearch", null,function (data) {
	    if (data.success == true) {
		    //给头部默认值
	        $('#defaultSearch').html(template('defaultSearchs', {items: data.resultObject.defaultSearch}));
	        // 热门搜索
	        //$('#hotSearch').html(template('hotSearchs', {items: data.resultObject.hotSearch}));
	        if (data.resultObject.hotSearch == 0 || data.resultObject.hotSearch == null) {
	            	$(".hot_search").hide();
	            } else {
		        	$('#hotSearch').html(template('hotSearchs', {items: data.resultObject.hotSearch}));
	        }
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


	
};

