
$(function(){

	$('.textarea').keyup(function(){
	    var question = $(".textarea").val();
	    if(question==""||question==undefined){
	        $(".quiz").css("opacity","0.3");
	        $(".quiz").css("background","#00bc12");
	    }else{
	        $(".quiz").css("opacity","1");
	        $(".quiz").css("background","#00bc12");
	    }
	})

	$(".quiz").click(function(){

		var question = $(".textarea").val();
		if (question=="") {
			$(".quiz").css("opacity","0.3");
        	return false;
		}else{
			$(".quiz").css("opacity","1");
			var doctorId = getQueryString("doctor");
		 	requestService("/xczh/question/addDoctorQuestion",{
		 		doctorId:doctorId,
		 		question:question
		 	},function (data) {
			    if (data.success == true) {
				  	// webToast("提交成功","middle",1500);
				  	// var doctorId = getQueryString("doctor"); 
				  	// alert(11111);
				  	// setTimeout(window.location.href = "/xcview/html/physician/physicians_page.html?doctor=" + doctorId + "",1600);
				  	$(".prosperity_popout").show();
			    }else{
			    	webToast(data.errorMessage,"middle",1500);
			    }
			},false);
		};

	});
	var doctorId = getQueryString("doctor");
	$(".prosperity_popout_hide").click(function(){
		history.back();   //可以就是没有刷新数据
		// window.location.reload("/xcview/html/physician/physicians_page.html?doctor="+doctorId);
		// window.location.reload(1);
		// window.history.go(-1)
		// window.history.go(-1);location.reload()
		// window.history.go(-2); 
		//window.location.href="/xcview/html/physician/physicians_page.html?doctor="+doctorId; 
		// self.location=document.referrer;
	});

	/*$(".prosperity_popout_hide").click(function(){
        $(".prosperity_popout").hide();
        
    });*/

});