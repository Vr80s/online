
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
	var doctorId = getQueryString("doctor");
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
				  	
				  	$(".prosperity_popout").show();
				  	
			    }else{
			    	webToast(data.errorMessage,"middle",1500);
			    }
			},false);
		};

	});
	$(".prosperity_popout_hide").click(function(){
        // $(".prosperity_popout").hide();
        window.history.back();return false;
    });

});