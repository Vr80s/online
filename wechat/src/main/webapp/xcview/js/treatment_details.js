//requestService
// $(function(){

	// 点击跳转详情
	function orders(id){
        requestGetService("/doctor/treatment/user/appointment",{id:id},function (data) {
            if (data.success == true) {
               
                location.href ='/xcview/html/physician/my_bookings.html?id='+id;
            }
        });
    };

    function foreshow(id){
        requestGetService("/doctor/treatment/user/appointment",{id:id},function (data) {
            if (data.success == true) {
               
                location.href ='/xcview/html/live_play.html?my_study='+id;
            }
        });
    };

    var id = getQueryString("infoId");
	requestGetService("/doctor/treatment/appointmentInfo",{id:id},function (data) {
        if (data.success == true) {
            // 提交预约信息
            $('.main').html(template('main_id', {items: data.resultObject}));

			//   status ===== 预约信息状态1->待审核 2->预约成功 4->已完成 5->已过期 6->审核不通过  
			//   start  ===== 按钮是否可点击   true
            if (data.resultObject.status == 1) {
            	$(".button").show();
        	}else if(data.resultObject.status == 2){
        		$(".determines").show();
        	}else if(data.resultObject.status == 4){
        		$(".check_btn").show();
        	};/*else if(data.resultObject.status == 5 || data.resultObject.status == 5){
        		$(".check_btn").hide();
        	}*/

        	// 点击查看视频跳转
        	$(".check_btn").click(function(){
        		var courseId = $(".check_btn").attr("data-courseId");
        		location.href ='/xcview/html/live_play.html?my_study='+courseId;
        	
        	});

        	// 点击去下载
			$(".determines").click(function(){
			//安卓路径
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
		        //如果是ios系统，直接跳转至appstore该应用首页，传递参数为该应用在appstroe的id号  
				//window.location.href="itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=123456";  
		            window.location.href="itms-apps://itunes.apple.com/cn/app/id1279187788";
				//https://itunes.apple.com/cn/app/id1279187788
		         }
		         else if(browser.versions.android)  
		         {  
		            window.location.href = androidURL;  
		         }  	
			});


            // 点击拒绝显示弹框
			$(".handler_btn").click(function(){
				alert(1111);
				// tooltip
				$(".tooltip").show();
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
				        	jqtoast(data.errorMessage);
				        }
				});

			});
        }
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



// });