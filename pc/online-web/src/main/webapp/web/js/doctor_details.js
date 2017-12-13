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
	 	id:id
	 }, function (data) {
       console.log(data);
       //医师头像
       var pic = data.resultObject.headPortrait;
       $('.header_inf_left').attr('background-image','url('+pic+')')
       //职位渲染
       var zhiwu = data.resultObject.title;
       $('.zhiwu').html("<em></em>"+zhiwu+"");
       //所在医院渲染
       var yiguan = data.resultObject.hospitalName;
       $('.yiguan').html("<em></em>"+yiguan+"");
       //所在城市渲染
       var city = data.resultObject.city;
       $('.dizhi').html("<em></em>"+city+"");
       //医师描述
       var dis = data.resultObject.description;
       $('.doctor_int>p').text(dis)
       //坐诊医馆信息
       $('.hospital_inf>p:first-child').text(yiguan);
       //预约电话
       $('.hospital_inf>p:nth-child(2)>span').text(yiguan);
       //坐诊时间
       var worktime = data.resultObject.workTime;
       $('.hospital_inf>p:nth-child(3)>span').text(worktime);
       //渲染医馆简介
//     var con = data.resultObject.description;
//     $('.hospital_detail_inf>p').text(con);
//     //联系方式 
//     var tel = data.resultObject.tel;
//     $('.hospital_detail_inf_bottom_tel').text('联系电话：'+tel)
//     //地址渲染
//     var sheng = data.resultObject.province;
//     var shi =  data.resultObject.city;
//     $('.sheng').text(sheng);
//     $('.shi').text(shi);
      
    });
	
})
