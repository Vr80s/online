$(function(){
	$(".sex-ul li").click(function(){
		$(".sex-ul li img").removeClass("active");
		$(this).find("img").addClass("active");
	})
document.getElementById('liMONTH').addEventListener('tap',function () {
    var datePicker=new mui.DtPicker({
	    type:'month',//若只显示年月日，类型为date，
	});
	datePicker.show(function(selectItems){
	    h("#datMONTH").val(selectItems);
    });
})

})
