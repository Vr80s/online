$(function(){
	var hosID;
//	这是点击课程里面开始
	//点击左侧课程
	$(".courseP").click(function() {
	    $(".curriculum_two").show();
	    $(".curriculum_one").hide();
	});

	//点击课程--新课程
	$("#kecheng_bottom2 .button").click(function() {
	    $(".curriculum_two").hide();
	    $(".curriculum_one").show();
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
	    
//	    $(".courseP").addClass("activeP");
//	    $(".specialP").removeClass("activeP");
//	    $(".liveP").removeClass("activeP");
//	    $(".schedulingP").removeClass("activeP");
//	    $(".resourceP").removeClass("activeP");
//	    $('.courseP ').click()
	    
	    
//	    alert(135214);
	    
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
		
//	这是点击课程里面结束


//这是点击专辑里面开始
	//点击左侧专辑
	$(".specialP").click(function() {
	    $("#zhuanji_bottom2").show();
	    $("#zhuanji_bottom").hide();
	    $("#zhuanjis_bottom").hide();
	});
	
	//点击第一页,新专辑
	$("#zhuanji_bottom2 .returns").click(function() {
	    $("#zhuanji_bottom2").hide();
	    $("#zhuanji_bottom").show();
	});
	
	//点击第二页返回--到第一页
	$("#zhuanji_bottom .returns").click(function() {
	    $("#zhuanji_bottom").hide();
	    $("#zhuanji_bottom2").show();
	});
	
	
	// //点击新专辑添加课程开始
	// //点击添加课程
	// $(".add_course").click(function() {
	//     $(".new_box").show();
	// });
	
	//关闭添加课程弹框
	$(".new_box_main p").click(function() {
	    $(".new_box").hide();
	});
	//点击新专辑添加课程结束
	
	//添加课程开始
	$(".new_box .new_box_main .size").click(function() {
	    $("#zhuanjis_bottom").show();
	    $("#zhuanji_bottom").hide();
	    $(".new_box").hide();
	});
	
	
//	课程添加返回  
	$("#zhuanjis_bottom .returns").click(function() {
	    $("#zhuanji_bottom2").show();
	    $("#zhuanjis_bottom").hide();
	});
	
	//添加课程结束  
	
//这是点击专辑里面结束
	

//这是点击资源开始   
	$(".resourceP").click(function() {
	    $(".resource_two").show();
	    $(".resource_one").hide();
	});
	
	$(".resource_two .zhuanlan_top button").click(function() {
	    $(".resource_one").show();
	    $(".resource_two").hide();
	});
	$(".resource_one .zhuanlan_top button").click(function() {
	    $(".resource_one").hide();
	    $(".resource_two").show();
	});

//这是点击资源结束
	


//点击收益部分  课程收益
$('.select-udss .classResive').click(function(event){
//	$('#gift_Resive').css('display','none');
//	$('#kecheng_Resive').css('display','block');
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
//	$('#kecheng_Resive').css('display','none');
//	$('#gift_Resive').css('display','block');
//	$('#gift_Resive').removeClass('hide');
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
	$(".account_main_alter").hide();
	
//	隐藏个人信息内容
    $(".message_return").hide();
    $(".personal_details").hide();
//  localStorage.AnchorsTbl_accountInf = 'name_news ';	
});

//点击个人信息  
$(".name_personage").click(function() {
localStorage.AccountNumber = 'name_personage'; 
$('.begin_approve').hide();
$('.account_main').hide();
//$(".account_main_alter").addClass('hide');
//$(".personal_details").show();
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
    $(".personal_details").hide();
    $(".message_return").show();
    
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
		//                  $('.hospital_worktime ul li').eq(i).addClass('color keshiColor');
		                }
		            }
		        }
		        
		         //医馆列表的选中效果
			    RequestService("/medical/doctor/getHospital", "get", null, function(data) {
			    	 $('.workHos_select').selectpicker('val',(data.resultObject.id));
			    })
			    
			    //精彩致辞的选中状态
			    $('.speech_resource').selectpicker('val',(data.resultObject.resourceId));
    
		    	}
    			
    		}else if(localStorage.AccountStatus == '2'){
    			//医馆自己的模板渲染
    		}
			$('#u_detailAddress').val(data.resultObject.detailAddress)
    })
    
   
    
    
});



//选择医馆列表选中之后出发的事件
$('#speech_select1').change(function(){
    //医馆ID的获取
     //医馆ID的获取
    hosID = $('#speech_select1').val()
    if(hosID == -1){
        //清空信息
        clearHosList()
        return false;
    }
    //获取对应的医馆信息渲染到页面上
    RequestService("/medical/hospital/getHospitalById", "get", {
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
//					$('#detail_address').text('');
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
    //封面清空
//  $('#hospital .fengmian_pic').html('	<p style="font-size: 90px;height: 100px;font-weight: 300;color: #d8d8d8;text-align: center;">+</p><p style="text-align: center;color: #999;font-size: 14px;">点击上传医馆封面图资图片</p>');
    //电话清空
    $('#doctor_baseInf .appointmentTel').val('');
    //坐诊时间
    $('#u_workTime > li').removeClass('color');

}




//点击选择省份效果
$('#demo1 .choosePro').click(function(){
	$("#demo1 .choosePro option[value = '-1']").text('请选择省')
//	alert(11)
})








$(".message_return .message_title .two").click(function() {
    $(".message_return").hide();
    $(".personal_details").show();
});



//
//
// //判断个人信息为空
// $('.message_return .name_btn').click(function(){
// //	昵称为空
// 	var NicknameNull = $.trim($('.message_return .put0').val());
//
// 	//个人介绍
// 	var ReferralNull = $.trim($('.message_return .textarea').val());
//
// 	//任职医馆
// 	var OfficelNull = $.trim($('.message_return .put1').val());
//
// //	详细地址
// 	var AddressNull = $.trim($('.message_return .name_textarea').val());
//
//
// 	//昵称为空
// 	if(NicknameNull == ''){
// 		$('.message_return .return_warning0').removeClass('hide');
// 		return false;
// 	}else{
// 		$('.message_return .return_warning0').addClass('hide');
// 	}
//
// //	个人时间
// 	if(ReferralNull == ''){
// 		$('.message_return .return_warning3').removeClass('hide');
// 		return false;
// 	}else{
// 		$('.message_return .return_warning3').addClass('hide');
// 	}
//
// //	任职医馆
// 	if(OfficelNull == ''){
// 		$('.message_return .return_warning4').removeClass('hide');
// 		return false;
// 	}else{
// 		$('.message_return .return_warning4').addClass('hide');
// 	}
//
// 	//	城市-详细地址
// 	if(AddressNull == ''){
// 		$('.message_return .return_warning5').removeClass('hide');
// 		return false;
// 	}else{
// 		$('.message_return .return_warning5').addClass('hide');
// 	}
//
//
//
//
//
//
//
//
// })







//账户个人信息城市
// $('#demo1').citys({
//     required:false,
//     nodata:'disabled',
//     onChange:function(data){
//         var text = data['direct']?'(直辖市)':'';
//         $('#places').text('当前选中地区：'+data['province']);
//         // $('#place').text('当前选中地区：'+data['province']+text+' '+data['city']+' '+data['area']);
//     }
// });



//课程新课程  城市
// $('#demo2').citys({
//     required:false,
//     nodata:'disabled',
//     onChange:function(data){
//         var text = data['direct']?'(直辖市)':'';
//         $('#place').text('当前选中地区：'+data['province']);
//         // $('#place').text('当前选中地区：'+data['province']+text+' '+data['city']+' '+data['area']);
//     }
// });
//
	
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
	
	
//	$('#workTime li').click(function(){
//		alert(222)
//	})
//	

	//主播修改信息的时候获取医馆信息渲染医馆列表
	if(localStorage.AccountStatus == 1){
		RequestService("/medical/doctor/apply/listHospital/0", "get", null, function(data) {
    //头像预览
    console.log(data);

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


