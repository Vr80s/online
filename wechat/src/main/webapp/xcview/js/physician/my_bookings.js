//requestService
$(function(){
    var id = getQueryString("id");
    requestGetService("/doctor/treatment/appointmentInfo",{id:id},function (data) {
        if (data.success == true) {
            // 提交预约信息
            $('.main').html(template('main_id', {items: data.resultObject}));
        	
        	if (data.resultObject.start == true) {
        		$(".handler_btns").show();
        	}else{
        		$(".handler_btn").show();
        	};


        	<!-- 点击取消 -->
			$(".handler_btns").click(function(){
				$(".tooltip").show();
			});

        	<!-- 点击取消 -->
			$(".call_off").click(function(){
				$(".tooltip").hide();
			});

			// 点击去下载
			$(".determine").click(function(){
		//			安卓路经
				var androidURL ="http://sj.qq.com/myapp/detail.htm?apkName=com.bj.healthlive";  
				var browser = {  
		       versions: function() {  
		             var u = navigator.userAgent,  
		                 app = navigator.appVersion;  
		             return {  
		                     android: u.indexOf("Android") > -1 || u.indexOf("Linux") >-1,  
		                     iPhone: u.indexOf("iPhone") > -1 ,  
		                     iPad: u.indexOf("iPad") >-1,  
		                     iPod: u.indexOf("iPod") > -1,  
		                   };  
		             } (),  
		        language: (navigator.browserLanguage || navigator.language).toLowerCase()  
		     }  

		     if (browser.versions.iPhone||browser.versions.iPad||browser.versions.iPod)  
		         { 
		            //如果是ios系統，直接跳转至appstore该应用首頁，传递参数为该应用在appstroe的id号  
		//              window.location.href="itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=123456";  
		            window.location.href="itms-apps://itunes.apple.com/cn/app/id1279187788";
		//          	https://itunes.apple.com/cn/app/id1279187788
		         }
		         else if(browser.versions.android)  
		         {  
		            window.location.href = androidURL;  
		         }  
					
			});


        }
    });
});





