$(function(){
	$.ajax({
		url:basePath+'/site/information/find',
		dataType:'json',
		success:function(data){
			
		}
	});
});
function importOrders (obj){
	$("#siteform").resetForm();
}