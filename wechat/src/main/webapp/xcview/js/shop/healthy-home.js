$(function(){
	$(".sex-ul li").click(function(){
		$(".sex-ul li img").removeClass("active");
		$(this).find("img").addClass("active");
        check();
	})
	document.getElementById('liMONTH').addEventListener('tap',function () {
	   var datePicker=new mui.DtPicker({
			type:'date',//若只显示年月日，类型为date，
			beginDate: new Date(1900, 00, 01),//设置开始日期
			endDate: new Date()//设置结束日期
		});
		datePicker.show(function(selectItems){
			$("#datMONTH").val(selectItems);
            check();
		});
	})

	$(".start").click(function(){
		if($(".start").hasClass("active")){
            var birthday = $("#datMONTH").val();
            var sex = $(".active").attr("data-sex");
            localStorage.setItem("healthy.birthday",birthday);
            localStorage.setItem("healthy.sex",sex);
            location.href="./healthy-answer.html";
        }
	});
});

function check(){
    $(".start").removeClass("active");
	if(isBlank($("#datMONTH").val())){
     	return false;
	}
	if(isBlank($(".sex.active").attr("data-sex"))){
		return false;
	}
	$(".start").addClass("active");
	return true;
}
