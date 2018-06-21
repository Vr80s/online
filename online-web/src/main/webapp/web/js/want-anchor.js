

//医师页面的医师入驻入口点击跳转效果
	$('.toDocJoin_ys').click(function () {
	    RequestService("/medical/common/isDoctorOrHospital", "GET", null, function (data) {
	        if (data.success == true) {
	            //请求数据成功进行判断
	            if ($('.login').css('display') == 'block' && data.resultObject == 2) {
	                //登录并且入驻了医馆了
	                $('#tip').text('您已完成了医馆认证，不能进行医师认证！');
	                $('#tip').toggle();
	                setTimeout(function () {
	                    $('#tip').toggle();
	                }, 2000)
	            } else if ($('.login').css('display') == 'block' && data.resultObject == 1) {
	                //注册医师成功
	                window.location.href = "/web/html/anchors_resources.html";
	            } else if ($('.login').css('display') == 'block' && data.resultObject == 7) {
	                //登录了并且都没有注册过
	                window.location.href = "/web/html/ResidentDoctor.html";
	            } else if ($('.login').css('display') == 'block' && data.resultObject == 3 || data.resultObject == 5 || data.resultObject == 6) {
	                //登录了 并且注册了没有通过的
	                window.location.href = "/web/html/ResidentDoctor.html";
	            } else if (data.resultObject == 4) {
	                //登录并且入驻了医馆了
	                $('#tip').text('您已提交医馆认证，暂时不能进行医师认证！');
	                $('#tip').toggle();
	                setTimeout(function () {
	                    $('#tip').toggle();
	                }, 2000)
	            }
	        } else if (data.success == false) {
	            window.location.href = "/web/html/practitionerRegister.html";
	        }
	 });
	});    
    
  //医师页面的医师入驻入口点击跳转效果
    $('.toDocJoin_yg').click(function () {
        RequestService("/medical/common/isDoctorOrHospital", "GET", null, function (data) {
        	
        	
        	//登录并且入驻了医师了
            $('#tip').text('您已完成了医师认证，不能进行医馆认证！');
            $('#tip').toggle();
            setTimeout(function () {
                $('#tip').toggle();
            }, 2000)
        	
        	
            if (data.success == true) {
            	
            	
                //请求数据成功进行判断
                if ($('.login').css('display') == 'block' && data.resultObject == 1) {
                    //登录并且入驻了医师了
                    $('#tip').text('您已完成了医师认证，不能进行医馆认证！');
                    $('#tip').toggle();
                    setTimeout(function () {
                        $('#tip').toggle();
                    }, 2000)
                } else if ($('.login').css('display') == 'block' && data.resultObject == 2) {
                    //注册医馆成功
                    window.location.href = "/clinics/my";
                } else if ($('.login').css('display') == 'block' && data.resultObject == 7) {
                    //登录了并且都没有注册过
                    window.location.href = "/clinics/my";
                } else if ($('.login').css('display') == 'block' && data.resultObject == 3 || data.resultObject == 4 || data.resultObject == 5 || data.resultObject == 6) {
                    //登录了 并且注册了没有通过的
                    window.location.href = "/clinics/my";
                } else if (data.resultObject == 3) {
                    //登录并且入驻了医馆了
                    $('#tip').text('您已提交医师认证，暂时不能进行医馆认证！');
                    $('#tip').toggle();
                    setTimeout(function () {
                        $('#tip').toggle();
                    }, 2000)
                }
            } else if (data.success == false) {
                window.location.href = "/web/html/hospitalRegister.html";
            }
        });  
    });  