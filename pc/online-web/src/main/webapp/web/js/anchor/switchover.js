$(function(){
	var hosID;
//	这是点击课程里面开始
	//点击左侧课程
//	$(".courseP").click(function() {
//	    $(".curriculum_two").show();
//	    $(".curriculum_one").hide();
//	});

	//点击课程--新课程
	$("#kecheng_bottom2 .button").click(function() {
	    $(".curriculum_two").hide();
	    $(".curriculum_one").show();
	    $(".row_size").show();//这是图片上传时的+和提示显示
	});
	//点击课程  返回
	$(".curriculum_one .button").click(function() {
	    $(".curriculum_one").hide();
	    $(".curriculum_two").show();
	});	
	
	//点击学堂隐藏  
	$(".school").click(function() {
	    $(".account_main_alter").hide();
	    $(".account_main_message").hide();
	    $(".personal_details").hide();
	    $(".message_return").hide();
	    $("#zhuanji").hide();
	    $("#resource").hide();
	    $(".curriculum_one").hide();
	    $(".curriculum_two").show();

	    $("#curriculum").show();
	    $(".wrap_box").show();
	    $(".littleBoxs").slideUp("slow");
	    
	});
	
	$(".littleBox p").click(function() {
	    $(".account_main_alter").hide();
	    $(".account_main_message").hide();
	    $(".personal_details").hide();
	    $(".message_return").hide();
	});


	//点击左侧专辑
//	$(".specialP").click(function() {
//	    $("#zhuanji_bottom2").show();
//	    $("#zhuanji_bottom").hide();
//	    $("#zhuanjis_bottom").hide();
//	});
	
	//点击第一页,新专辑
	$("#zhuanji_bottom2 .returns").click(function() {
	    $("#zhuanji_bottom2").hide();
	    $("#zhuanji_bottom").show();
	    $(".row_size").show();//这是图片上传时的+和提示显示
	});
	
	//点击第二页返回--到第一页
	$("#zhuanji_bottom .returns").click(function() {
	    $("#zhuanji_bottom").hide();
	    $("#zhuanji_bottom2").show();
	});

	//关闭添加课程弹框
	$(".new_box_main p").click(function() {
	    $(".new_box").hide();
	});
	
	//添加课程开始
	$(".new_box .new_box_main .size").click(function() {
	    $("#zhuanjis_bottom").show();
	    $("#zhuanji_bottom").hide();
	    $(".new_box").hide();
	});
	
	//课程添加返回
	$("#zhuanjis_bottom .returns").click(function() {
	    $("#zhuanji_bottom2").show();
	    $("#zhuanjis_bottom").hide();
	});
	
	//这是点击资源开始
	$(".resourceP").click(function() {
	   
	});
	
	$(".resource_two .zhuanlan_top button").click(function() {
	    $(".resource_one").show();
	    $(".resource_two").hide();
	});
	$(".resource_one .zhuanlan_top button").click(function() {
	    $(".resource_one").hide();
	    $(".resource_two").show();
	});

//点击收益部分  课程收益
$('.select-udss .classResive').click(function(event){
	$('.littleBoxss .giftResive').removeClass('activeP')
	$('.littleBoxss .classResive').addClass('activeP')
	$('#gift_Resive').addClass('hide');
	$('#kecheng_Resive').removeClass('hide');
	event.stopPropagation(); // 阻止事件冒泡
	localStorage.AnchorsTbl_myResive = 'classResive';
	getCourseResiveList (1);
	$('#test06').val('');
	$('#test07').val('');
})

//礼物收益点击
$('.select-udss .giftResive').click(function(event){
	$('.littleBoxss .classResive').removeClass('activeP')
	$('.littleBoxss .giftResive').addClass('activeP')

	$('#kecheng_Resive').addClass('hide');
	$('#gift_Resive').removeClass('hide');
	event.stopPropagation(); // 阻止事件冒泡
	localStorage.AnchorsTbl_myResive = 'giftResive';
	getGiftResiveList (1);
	$('#test08').val('');
	$('#test09').val('');
})

//点击帐号列表
$(".account_number").click(function() {
    $("#curriculum").hide();
    $(".begin_approve").show();
    $(".school").show();
    $(".physician_two_two").hide();
    $(".message_return").hide();
    $("#resource").hide();
//  处理学堂关闭
    $(".littleBox").slideUp("slow");
    $(".name_news").addClass("activeP");
	$(".name_personage").removeClass("activeP");
});


//点击认证信息   name_news
$(".name_news").click(function() {
//	隐藏认证信息第二页  
	localStorage.AccountNumber = 'name_news'; 
//	$(".account_main_alter").hide();
	
//	隐藏个人信息内容
//  $(".message_return").addClass('hide');
//  $(".personal_details").addClass('hide');
});

//点击个人信息  
$(".name_personage").click(function() {
	localStorage.AccountNumber = 'name_personage';
//	$('.begin_approve').hide();
//	$('.account_main').hide();
});

//账户重新认证点击开始
$(".reauthentication .right_btn").click(function() {
    $(".begin_approve").hide();
    $(".physician_two_two").removeClass('hide');
    $(".physician_two_two").show();
});

//点击返回按钮
$(".account_main_alter_title .two").click(function() {
	 $(".physician_two_two").hide();
    $(".begin_approve").show();
});

//账户个人信息  
$(".right_modification").click(function() {
	//获取资源列表渲染
    initResource(1,true);
    $(".personal_details").addClass('hide');
    $(".message_return").removeClass('hide');
    
    //获取主播信息回显
    RequestService("/anchor/info", "get", null,function(data){
    	if(data.success == true){
    		$('.account .message_return .anchor_nick_name').val(data.resultObject.name);
    		
    		if(data.resultObject.profilePhoto){
    		$('.account .message_return #profilePhotoImg').html('<img id="imghead" border="0" src='+data.resultObject.profilePhoto+' width="90" height="90" >')
    		}

    		if(data.resultObject.detail == null){
    			UE.getEditor('anchor_details_editor').setContent('');
    		}else{
    			UE.getEditor('anchor_details_editor').setContent(data.resultObject.detail);
    		}
    		
    		if(localStorage.AccountStatus == '1'){
    			 //坐诊时间渲染
		        var workArr = data.resultObject.workTime.split(",");		
		        var j;
		        for(var i =0 ;i < $('#doctor_baseInf .workTime ul li ').length ;i++){
		            for(j = 0;j < workArr.length ;j++ ){
		                if($('#doctor_baseInf .workTime ul li').eq(i).text() == workArr[j]){
		                	if(! $('#doctor_baseInf .workTime ul li').eq(i).hasClass('color')){
		                	 $('#doctor_baseInf .workTime ul li').eq(i).click();
		                	}
		                }
		            }
		        }
		        
		         //医馆列表的选中效果
			    RequestService("/medical/doctor/getHospital", "get", null, function(data) {
			    	 $('.workHos_select').selectpicker('val',(data.resultObject.id));
			    })
			    
			    //精彩致辞的选中状态
			    $('.speech_resource').selectpicker('val',(data.resultObject.resourceId));
    
		    }else if(localStorage.AccountStatus == '2'){
    			//医馆自己的模板渲染
    			//电话
    			if(data.resultObject.tel){
    				$('#u_hospital_tel').val(data.resultObject.tel)
    			}
    			//城市
    			if(!data.resultObject.province){
					$('#demo1 .choosePro option:selected').text('请选择省');
					$('#demo1 .chooseCity option:selected').text('请选择市');
    			}
    			
    		}
			$('#u_detailAddress').val(data.resultObject.detailAddress)
		}
    })
});

//选择医馆列表选中之后出发的事件
$('#speech_select1').change(function(){
    //医馆ID的获取
    hosID = $('#speech_select1').val()
    if(hosID == -1){
        //清空信息
        clearHosList()
        return false;
    }
    //获取对应的医馆信息渲染到页面上
    RequestService("/hospital/getHospitalById", "get", {
        id: hosID,
    }, function(data) {
        console.log(data);
        //省
        if(data.resultObject.province){
            $('#hosPro').val(data.resultObject.province)
        }
        //市
        if(data.resultObject.city){
            $('#hosCity').val(data.resultObject.city)
        }
        //详细地址
        if(data.resultObject.detailedAddress){
            $('#u_detailAddress').val(data.resultObject.detailedAddress)
        }
        //电话号码
        if(data.resultObject.tel){
            $('.appointmentTel').val(data.resultObject.tel);
        }
    })
})


//清空医师入驻医馆信息列表
function clearHosList(){
    //省分
    $('#hosPro').val('');
    //市区
    $('#hosCity').val('')
    //详细地址清空
    $('#u_detailAddress').val('')
    //电话清空
    $('#doctor_baseInf .appointmentTel').val('');
    //坐诊时间
    $('#u_workTime > li').removeClass('color');
}

//点击选择省份效果
$('#demo1 .choosePro').click(function(){
	$("#demo1 .choosePro option[value = '-1']").text('请选择省')
})

$(".message_return .message_title .two").click(function() {
    $(".message_return").addClass("hide");
    $(".personal_details").removeClass('hide');
});

	//进入之后判断账户中主播修改信息的页面结构
	if(localStorage.AccountStatus == 1){
		//医师
		$('#doctor_baseInf').removeClass('hide');
		$('#hospital_baseInf').addClass('hide');
		
		//地址选择部分变化
		$('#demo1 .choosePro').addClass('hide');
		$('#demo1 .chooseCity').addClass('hide');
		
		$('#hosPro').removeClass('hide');
		$('#hosCity').removeClass('hide');
		
		$('#u_detailAddress').attr('readonly',true)
		
	}else if(localStorage.AccountStatus == 2){
		//医馆
		$('#doctor_baseInf').addClass('hide');
		$('#hospital_baseInf').removeClass('hide')
		
		//地址选择部分变化
		$('#hosPro').addClass('hide');
		$('#hosCity').addClass('hide');
		
		$('#demo1 .choosePro').removeClass('hide');
		$('#demo1 .chooseCity').removeClass('hide');

		$('#u_detailAddress').attr('readonly',false)
	}

	//主播修改信息的时候获取医馆信息渲染医馆列表
	if(localStorage.AccountStatus == 1){
		RequestService("/doctor/apply/listHospital/0", "get", null, function(data) {
			//列表渲染
			$('#speech_select1').append('<option value="-1">请选择医馆</option>')
			$('#speech_select1').append(template('hosListTpl', {item:data.resultObject.records}));
			//渲染之后在此调用插件
			$('.workHos_select').selectpicker({
				'selectedText': 'cat',size:10
			});
		});
	}

});


