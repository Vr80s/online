/*window.onload = function(){
	
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
	});
	
};*/