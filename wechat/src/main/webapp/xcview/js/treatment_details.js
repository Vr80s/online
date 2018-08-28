//requestService
$(function(){
    var id = getQueryString("infoId");
   
	requestGetService("/doctor/treatment/appointmentInfo",{id:id},function (data) {
        if (data.success == true) {
            // 提交预约信息
            $('.main').html(template('main_id', {items: data.resultObject}));
        }
    });

	// 点击拒绝显示弹框
	$(".handler_btn").click(function(){
		// tooltip
		$(".tooltip").show();
	});

	// 点击取消
	$(".call_off").click(function(){
		$(".tooltip").hide();
	});

	// 点击确认拒绝
	$(".determine").click(function(){
		requestService("/doctor/treatment/apply",{
	    		infoId:id,
	    		status: false
	    },function (data) {
		        if (data.success == true) {
		        	$(".tooltip").hide();   /*弹框隐藏--提示拒绝弹框*/
		        	// $('.button').html(template('button', {items: data.resultObject}));
		            jqtoast("操作成功");
		            setTimeout(function () {
	                    window.location.href="/xcview/html/treatment.html"; 
	                },1600);
		        }else{
		        	$(".tooltip").hide();   /*弹框隐藏--提示拒绝弹框*/
		        	jqtoast(data.errorMessage);
		        }

		});

	});

    

	// 点击审核通过
	$(".handler_btns").click(function(){
		requestService("/doctor/treatment/apply",{
	    		infoId:id,
	    		status: true
	    },function (data) {
		        if (data.success == true) {
		        	// $('.button').html(template('button', {items: data.resultObject}));
		            jqtoast("操作成功");
		            setTimeout(function () {
	                    window.location.href="/xcview/html/treatment.html"; 
	                },1500);
		        }else{
		        	jqtoast(data.resultObject.errorMessage);
		        }
		});

	});

});





