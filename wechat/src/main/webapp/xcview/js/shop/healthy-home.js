$(function(){
	if(localStorage.getItem("healthy.result") != null){
		location.href = "healthy-result.html"
	}else{
		$("body").show();
	}
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
	
//	分享显示和隐藏
	$(".header_news img").click(function(){
		$(".weixin_ceng").show();
	});
	$(".weixin_ceng").click(function(){
		$(".weixin_ceng").hide();
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
