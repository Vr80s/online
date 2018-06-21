
window.onload = function(){
	requestService("/xczh/doctors/category", null, function (data) {
		// 热门中医
		$('#hot_title').html(template('hot_titles',data.resultObject.HOT));
		// $('#hot').html(template('hot_id', {items: data.resultObject.HOT.doctors}));

		// 名青年中医
		$('#mqnzy_title').html(template('mqnzy_titles',data.resultObject.MQNZY));
		// $('#mqnzy').html(template('mqnzy_id', {items: data.resultObject.MQNZY.doctors}));

		// 名老中医
		$('#mlzy_title').html(template('mlzy_titles',data.resultObject.MLZY));
		// $('#mlzy').html(template('mlzy_id', {items: data.resultObject.MLZY.doctors}));

		//少数民族中医
		$('#ssmzzy_title').html(template('ssmzzy_titles',data.resultObject.SSMZZY));
		// $('#ssmzzy').html(template('ssmzzy_id', {items: data.resultObject.SSMZZY.doctors}));

		//国医大师
		$('#gyds_title').html(template('gyds_titles',data.resultObject.GYDS));
		// $('#gyds').html(template('gyds_id', {items: data.resultObject.GYDS.doctors}));

		//家传中医
		$('#gzy_title').html(template('gzy_titles',data.resultObject.GZY));
		// $('#gzy').html(template('gzy_id', {items: data.resultObject.GZY.doctors}));

		if (data.success == true) {
			// 热门中医
			if (data.resultObject.HOT.doctors == 0 || data.resultObject.HOT.doctors == null) {
	            $(".hot_title").hide();
	        } else {
	            $('#hot').html(template('hot_id', {items: data.resultObject.HOT.doctors}));
	        }

	        // 名青年中医
			if (data.resultObject.MQNZY.doctors == 0 || data.resultObject.MQNZY.doctors == null) {
	            $(".mqnzy").hide();
	        } else {
				$('#mqnzy').html(template('mqnzy_id', {items: data.resultObject.MQNZY.doctors})); 
	        }

	        // 名老中医
			if (data.resultObject.MLZY.doctors == 0 || data.resultObject.MLZY.doctors == null) {
	            $(".mlzy").hide();
	        } else {
				$('#mlzy').html(template('mlzy_id', {items: data.resultObject.MLZY.doctors}));
	        }

	         // 少数民族中医
			if (data.resultObject.SSMZZY.doctors == 0 || data.resultObject.SSMZZY.doctors == null) {
	            $(".ssmzzy").hide();
	        } else {
				$('#ssmzzy').html(template('ssmzzy_id', {items: data.resultObject.SSMZZY.doctors}));
			};

			 // 国医大师
			if (data.resultObject.GYDS.doctors == 0 || data.resultObject.GYDS.doctors == null) {
	            $(".gyds").hide();
	        } else {
				$('#gyds').html(template('gyds_id', {items: data.resultObject.GYDS.doctors}));
			};

			 // 家传中医
			if (data.resultObject.GZY.doctors == 0 || data.resultObject.GZY.doctors == null) {
	            $(".gzy").hide();
	        } else {
				$('#gzy').html(template('gzy_id', {items: data.resultObject.GZY.doctors}));
			};

		};

		// 点击热门中医跳转
		$("#hot_title .physician_title").click(function () {
            var code_num = $(this).attr("code");
            window.location.href = "/xcview/html/physician/physician_list.html?code=" + code_num + "";
        });
        $(".hot_title .physician_cen").click(function () {
            var dataId = $(this).attr("data-id");
            window.location.href = "/xcview/html/physician/index.html?data-id=" + dataId + "";
        });

        // 点击名老中医跳转
		$("#mqnzy_title .physician_title").click(function () {
            var code_num = $(this).attr("code");
            window.location.href = "/xcview/html/physician/physician_list.html?code=" + code_num + "";
        });
        $(".mlzy .physician_cen").click(function () {
            var dataId = $(this).attr("data-id");
            window.location.href = "/xcview/html/physician/index.html?data-id=" + dataId + "";
        });

        // 点击名青年中医跳转
		$("#mlzy_title .physician_title").click(function () {
            var code_num = $(this).attr("code");
            window.location.href = "/xcview/html/physician/physician_list.html?code=" + code_num + "";
        });
        $(".mqnzy .physician_cen").click(function () {
            var dataId = $(this).attr("data-id");
            window.location.href = "/xcview/html/physician/index.html?data-id=" + dataId + "";
        });

        // 点击少数民族中医跳转
		$("#ssmzzy_title .physician_title").click(function () {
            var code_num = $(this).attr("code");
            window.location.href = "/xcview/html/physician/physician_list.html?code=" + code_num + "";
        });
        $(".ssmzzy .physician_cen").click(function () {
            var dataId = $(this).attr("data-id");
            window.location.href = "/xcview/html/physician/index.html?data-id=" + dataId + "";
        });

         // 点击国医大师跳转
		$("#gyds_title .physician_title").click(function () {
            var code_num = $(this).attr("code");
            window.location.href = "/xcview/html/physician/physician_list.html?code=" + code_num + "";
        });
        $(".gyds .physician_cen").click(function () {
            var dataId = $(this).attr("data-id");
            window.location.href = "/xcview/html/physician/index.html?data-id=" + dataId + "";
        });

         // 点击家传中医跳转
		$("#gzy_title .physician_title").click(function () {
            var code_num = $(this).attr("code");
            window.location.href = "/xcview/html/physician/physician_list.html?code=" + code_num + "";
        });
		$(".gzy .physician_cen").click(function () {
            var dataId = $(this).attr("data-id");
            window.location.href = "/xcview/html/physician/index.html?data-id=" + dataId + "";
        });

	});

	
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




};

