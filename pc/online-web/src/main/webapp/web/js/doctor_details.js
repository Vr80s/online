$(function(){
	$('.path .doctor').addClass('select');
	
	//获取url中参数值的方法
	function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]); 
    }
    return null;
	}
	
	
	//获取医师的id值
	var id = getQueryString('Id');
	 RequestService("/medical/doctor/getDoctorById", "GET", {
	 	id:id,
	 }, function (data) {
       console.log(data);
//     //医师名字
//     $('.header_inf_right>span:first-child').text(data.resultObject.name);
//     //医师职位
//     $('.header_inf_right>span:nth-child(2)').text(data.resultObject.title);
//     //医师头像
//     $('.header_inf_left').attr('background-image','url('+data.resultObject.headPortrait+')');
//     //职位渲染
//     $('.zhiwu').html("<em></em>"+data.resultObject.title+"");
//   
//     //所在城市渲染
//     $('.dizhi').html("<em></em>"+data.resultObject.city+"");
//     //主治症状
//     for(var i = 0; i < data.resultObject.fields.length; i++){
//     	var span = $('<span></span>');
//     	span.text(data.resultObject.fields[i].name);
//     	$('.doctor_inf2 > p').append(span);
//     }
       //医师描述
       $('.doctor_int>p').html(data.resultObject.description)
// 		
// 		
       if(data.resultObject.medicalHospital){
       	
       	  $('.yiguan').html("<em></em>"+data.resultObject.medicalHospital.name+"");
       	  
       //坐诊医馆信息
       //跳转
       $('.to_hospital_detail').attr('href','/web/html/hospital_details.html?'+data.resultObject.medicalHospital.id+'');
       //医馆图片
       $('.hospital_pic>img').attr('src',data.resultObject.medicalHospital.medicalHospitalPictures[0].picture)
       //名称
       $('.hospital_inf>p:first-child').text(data.resultObject.medicalHospital.name);
       //电话
       $('.hospital_inf>p:nth-child(2)>span').text(data.resultObject.medicalHospital.tel);
       //坐诊时间
       var worktime = data.resultObject.workTime;
       $('.hospital_inf>p:nth-child(3)>span').text(worktime);
       //医馆地址
       $('.hospital_inf>p:nth-child(4)>span').text(data.resultObject.medicalHospital.province+data.resultObject.medicalHospital.city)
       }
       //顶部渲染
        $('#doctor_detail_header').html(template('doctor_detail_topTpl',data.resultObject));
        //所在医馆信息渲染
//     $('#doc_hospital').html(template('doc_hospitalTpl',data.resultObject));
    });
    
    
    
//  RequestService("/medical/doctor/getDoctorById", "GET", {
//	 	id:id
//	 }, function (data) {
//	        if(!data.resultObject){
//	        	//没有数据处理
//	           alert("没有数据/搜索失败")
//	        }else{
//	        	//获取到数据渲染
//	        	//创建一个盒子
//	        	console.log(data);
//	           $('#doctor_detail_header').html(template('doctor_detail_topTpl',{doctor:data.resultObject}));
//	        }
//	    });
	
})
