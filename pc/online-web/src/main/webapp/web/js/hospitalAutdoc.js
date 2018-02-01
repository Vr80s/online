$(function(){
	//初始化页面和每页多少条数据
	window.currentPage = 1;
	window.size = 8;
;
	
	//点击左侧医师管理按钮进行对应数据请求
	$('#doc_Administration_tabBtn').click(function(){
//		alert(1)
		
		RequestService("/medical/hospital/getDoctors","post",{
			currentPage:currentPage,
			size:size,
		},function(data){
			console.log(data)
		})
	})
	
	
	//医师预览功能
$('.doc_Administration_bottom2 .preview').click(function(){
	$('#mask').removeClass('hide');
	$('#doc_Administration_bottom3').addClass('hide');
	$('#doc_Administration_bottom4').removeClass('hide');
})


//医师编辑功能
$('#doc_Administration_bottom2 .edit').click(function(){
	$('#mask').removeClass('hide');
	$('#doc_Administration_bottom4').addClass('hide');
	$('#doc_Administration_bottom3').removeClass('hide');
})


//医师编辑关闭按钮
$('#doc_Administration_bottom4 .close_doc_inf').click(function(){
	$('#mask').addClass('hide');
	$('#doc_Administration_bottom4').addClass('hide');
})

//医师预览关闭按钮
$('#doc_Administration_bottom3 .close_doc_inf').click(function(){
	$('#mask').addClass('hide');
	$('#doc_Administration_bottom3').addClass('hide');
})
})
	