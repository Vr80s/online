$(function(){
	//初始化页面和每页多少条数据
	window.current = 1;
	window.size = 8;
	window.hospitalId = '69461bb30de84ebeb3899a404e26e655',
	window.doctorName = 1;
	
	//点击左侧医师管理按钮进行对应数据请求
	$('#doc_Administration_tabBtn').click(function(){
//		alert(1)
		
//		RequestService("/medical/hospital/getDoctors","post",{
//			currentPage:current,
//			size:size,
//			hospitalId:hospitalId
//		},function(data){
//			console.log(data)
//		})
	})
})
	