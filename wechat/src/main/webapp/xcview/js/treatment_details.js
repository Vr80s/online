//requestService
$(function(){
    var id = getQueryString("infoId");
   
	requestGetService("/doctor/treatment/appointmentInfo",{id:id},function (data) {
        if (data.success == true) {
            // 提交预约信息
            $('.main').html(template('main_id', {items: data.resultObject}));
        }
    });

    // 点击拒绝
	$(".handler_btn").click(function(){
		requestService("/doctor/treatment/apply",{
	    		infoId:id,
	    		status: false
	    },function (data) {
		        if (data.success == true) {
		        	// $('.button').html(template('button', {items: data.resultObject}));
		            jqtoast("操作成功");
		            setTimeout(function () {
	                    window.location.href="/xcview/html/treatment.html"; 
	                },1600);
		        }/*else{
		        	jqtoast("data.resultObject.errorMessage");
		        }*/

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





