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
       console.log(data);
       //医馆标题变化
       $('.hospital_detail_inf > h3').text(data.resultObject.name)
       //渲染医馆简介
       var con = data.resultObject.description;
       $('.hospital_detail_inf>p').text(con);
       //联系方式
       var tel = data.resultObject.tel;
       $('.hospital_detail_inf_bottom_tel').text('联系电话：'+tel)
       //地址渲染
       var sheng = data.resultObject.province;
       var shi =  data.resultObject.city;
       $('.sheng').text(sheng);
       $('.shi').text(shi);
     
    });
	
	
})
