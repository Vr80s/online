$(function () {
    var resultStr = localStorage.getItem("healthy.result");
    if(resultStr == null){
        location.href = "./healthy-home.html"
    }else{
        var result = JSON.parse(resultStr);
        if(result.constitutionScoreList.length == 0){
            $(".result1").html("恭喜您，近期身体状态良好，请继续保持");
            $(".result1").show();
        }else if(result.constitutionScoreList.length > 0){
            createProposals(result);
            $(".result3").show();
            if(result.constitutionScoreList[0].score <= 5){
                $(".result1").html("您的身体基本健康");
                $(".result1").show();
                $(".result2").html("但是有<span>"+result.constitution+"</span>的倾向");
                $(".result2").show();
            }else{
                $(".result2").html("您有<span>"+result.constitution+"</span>的倾向");
                $(".result2").show();
            }
        }
    }
 //	分享显示和隐藏
	$(".header_news img").click(function(){
		$(".weixin_ceng").show();
	});
	$(".weixin_ceng").click(function(){
		$(".weixin_ceng").hide();
	});
});

function createProposals(result) {
    var p1 = "";
    var p2 = "";
    var p3 = "";
    for(var i=0;i<result.medicalConstitutionProposals.length;i++){
        if(result.medicalConstitutionProposals[i]==null)continue;
        p1 += '<p class="live-text-one">'+result.medicalConstitutionProposals[i].attention+'</p>';
        p2 += '<p class="live-text-one">'+result.medicalConstitutionProposals[i].taboo+'</p>';
        p3 += '<div class="live-tip"><p class="live-title"><img src="/xcview/images/aixin.png" alt="标题图标" /><span>'+result.medicalConstitutionProposals[i].acupoint+'</span></p></div>'
    }
    $(".p1").append(p1);
    $(".p2").append(p2);
    $(".p3").append(p3);

    var p4 = "";
    for(var i=0;i<result.medicalConstitutionRecipes.length;i++){
        p4 += '<div class="live-tip">' +
            '<p class="live-title">' +
            '<img src="/xcview/images/aixin.png" alt="标题图标" />' +
            '<span>'+result.medicalConstitutionRecipes[i].name+'</span>' +
            '</p>' +
            '<p class="live-text-one">' +
            '<span class="live-left-tip">原料：</span>'+result.medicalConstitutionRecipes[i].material +
            '</p>' +
            '<p class="live-text-one">' +
            '<span class="live-left-tip">制法：</span>'+result.medicalConstitutionRecipes[i].method +
            '</p>' +
            '</div>';
    }
    $(".p4").append(p4);
}