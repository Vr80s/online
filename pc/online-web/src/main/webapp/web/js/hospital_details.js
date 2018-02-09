$(function(){
	$('.path .hospital').addClass('select');



	
	//获取url中参数值的方法
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
	}
	
	//获取医馆的id值
	var id = getQueryString('Id');
	console.log(id);
	
	 RequestService("/medical/hospital/getHospitalById", "GET", {
	 	id:id
	 }, function (data) {
       $('title').text('熊猫中医 - '+data.resultObject.name);
	 	
//	 	$('#hospital_detail_pic').html('');
       console.log(data);
       //医馆标题变化
       $('.hospital_detail_inf > h3').text(data.resultObject.name);

       //渲染医馆简介
       $('.hospital_detail_con>p').html(data.resultObject.description);
       //联系方式
       $('.hospital_detail_inf_bottom_tel').text('联系电话：'+data.resultObject.tel)
       //地址渲染
       $('.sheng').text(data.resultObject.province);
       $('.shi').text(data.resultObject.city);
       
       if(data.resultObject.detailedAddress ){
       		$('.detail').text(data.resultObject.detailedAddress)
       		$('.detail').attr('title',data.resultObject.detailedAddress)
       }
       
      
	
	//医馆信息渲染
	$('#hos_inf').html(template('hos_infTpl',data.resultObject));
	
	//渲染纵向轮播
	if(data.resultObject.medicalHospitalPictures.length == 0){
//		$('#hospital_detail_pic').html('<h3>暂无医馆图片</h3>');
		$('#hospital_detail_pic').html("<img src='/web/images/hospitalDefault.png' alt='暂无图片' style='width: 619px;height: 342px;transform: translateY(-50%);top:50%;position: absolute;'>");
	}
   	$('#lunbo').html(template('lunboTpl',data.resultObject));
   	
   	//启动轮播图
   	$('.succesny').olvSlides({
		thumb: true,
		thumbPage: true,
		thumbDirection: "Y",
		effect: 'fade'

	});
// 	//设置右侧图片
// 	var num = 0;
// 	if(data.resultObject.medicalHospitalPictures.length!=0){
// 		$('.big_pic').attr('src',data.resultObject.medicalHospitalPictures[0].picture)
// 		
// 	setInterval(function(){
// 		if(num < 3){
// 			num += 1;
// 				$('.big_pic').attr('src',data.resultObject.medicalHospitalPictures[num].picture);
// 		}else{
// 			num = 0;
// 			$('.big_pic').attr('src',data.resultObject.medicalHospitalPictures[num].picture);
// 		}
// 	},2000)
// 	}
   	
   	
    });
    
		
	//右侧图片设置
	
//	$('.big_pic').click(function(){
//		console.log(222)
//	})
	//点击图片改变
	
	
//	$('body').on('click','.lunboPic',function(){
//		$('.big_pic').attr('src',$(this).attr('src'))
//	})
	
	
	//医馆医师
	    RequestService("/medical/doctor/getDoctors","GET",{
			current:1,
			size:4,
			hospitalId:id
	    },function(data){
	        if(data.resultObject.total == 0){
	        	//没有数据处理
//	           alert("名老中医没有数据")
//			$('.doctor_inf').html('<h3>暂无数据</h3>');
			$('.hospital_doctor').addClass('hide')
	        }else if(data.resultObject.total > 0){
	        	console.log(data)
	        	$('.hospital_doctor').removeClass('hide')
	        	//获取到数据渲染
	        	if(data.resultObject.total > 4){
	        		$('#more_doc').removeClass('hide');
	        	};
	           $('#yiguan_mingjia').html(template('hos_docTpl',{doctor:data.resultObject.records}));
	        }
	    });
	

	
	//医馆招聘信息
	    RequestService("/medical/hospitalRecruit/getHospitalRecruitById","GET",{
			hospitalId:id
	    },function(data){
	    	//更多给名医添加id
	    	console.log(id);
	    	$('#more_doc').attr('href','/web/html/practitionerListing.html?name=&hospitalId='+id+'')
	        if(data.resultObject.length == 0){
				$('.employ').addClass('hide')
	        }else{
	        	console.log(data)
	           $('#employ').html(template('employ_infTpl',{inf:data.resultObject}));
	        }
	    });
	
	

})
