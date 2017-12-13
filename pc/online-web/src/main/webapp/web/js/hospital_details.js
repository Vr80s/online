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
		//渲染纵向轮播
//		if(data.resultObject.medicalHospitalPictures){
//			
//			console.log(data.resultObject.medicalHospitalPictures.length)
//			for(var i = 0;i < data.resultObject.medicalHospitalPictures.length; i++ ){
//				var $li = "<li><img src="+data.resultObject.medicalHospitalPictures[i].picture+"></li>";
//				if(i<=3){
//					$('#lunbo').append($li);
//				}
//				
//			}
//		}


	//渲染纵向轮播
   	$('#lunbo').html(template('lunboTpl',data.resultObject));
   	//设置右侧图片
   	var num = 0;
   	if(data.resultObject.medicalHospitalPictures.length!=0){
   		$('.big_pic').attr('src',data.resultObject.medicalHospitalPictures[0].picture)
   		
   	setInterval(function(){
   		if(num < 3){
   			num += 1;
   				$('.big_pic').attr('src',data.resultObject.medicalHospitalPictures[num].picture);
   		}else{
   			num = 0;
   			$('.big_pic').attr('src',data.resultObject.medicalHospitalPictures[num].picture);
   		}
   	},2000)
   	}
   	
   	
    });
    
		//纵向轮播
		setTimeout(function(){
//			$('.pgwSlider').pgwSlider();
		},1500)
	
	//右侧图片设置
	
	$('.big_pic').click(function(){
		console.log(222)
	})
	//点击图片改变
	
	
	$('body').on('click','.lunboPic',function(){
		$('.big_pic').attr('src',$(this).attr('src'))
	})
	

})
