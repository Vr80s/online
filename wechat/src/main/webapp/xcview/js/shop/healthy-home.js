$(function(){
	$(".sex-ul li").click(function(){
		$(".sex-ul li img").removeClass("active");
		$(this).find("img").addClass("active");
	})
	document.getElementById('liMONTH').addEventListener('tap',function () {
	   // var minDate=new Date();
		// 	minDate.setFullYear(1900,01,01)
	   var datePicker=new mui.DtPicker({
			type:'date',//若只显示年月日，类型为date，
			// minDate:minDate,
			beginDate: new Date(1900, 00, 01),//设置开始日期
			endDate: new Date()//设置结束日期
		});
		datePicker.show(function(selectItems){
			$("#datMONTH").val(selectItems);
		});
	})
	$(".start").click(function(){
		var birthDay = $("#datMONTH").val();
		var sex = $(".active").attr("data-sex");
		localStorage.setItem("healthy.birthDay",birthDay);
		localStorage.setItem("healthy.sex",sex);
		location.href="./healthy-answer.html"
	});
})
