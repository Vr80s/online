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
	$(".restart").click(function () {
        localStorage.removeItem("healthy.result");
        location.href="./healthy-home.html";
    });
});
// function handelMedicalConstitutionProposals(result){
//     for(var j=0;j<result.medicalConstitutionProposals.length;j++){
//         var recipesArr = [];
//         for(var i=0;i<result.medicalConstitutionRecipes.length;i++){
//             if(result.medicalConstitutionProposals[j].constitutionId == result.medicalConstitutionRecipes[i].constitutionId){
//                 recipesArr.push()
//             }
//         }
//     }
// }
function createProposals(result) {
    // result.medicalConstitutionProposals = handelMedicalConstitutionProposals(result);
    for(var i=0;i<result.medicalConstitutionProposals.length;i++){
        if(result.medicalConstitutionProposals[i]==null)continue;
        var str = '<div class="suggest-wrap suggest'+result.medicalConstitutionProposals[i].constitutionId+'">' +
            '<div class="suggest-title">' +
            '<p>'+result.medicalConstitutionProposals[i].tag+'建议</p>' +
            '</div>' +
            '<div class="live-tip">' +
            '<p class="live-title">' +
            '<img src="/xcview/images/aixin.png" alt="标题图标" />' +
            '<span>生活注意</span>' +
            '</p>' +
            '<p class="live-text-one p1">' +
            '</p>' +
            '</div>' +
            '<div class="live-tip">' +
            '<p class="live-title">' +
            '<img src="/xcview/images/aixin.png" alt="标题图标" />' +
            '<span>饮食宜忌</span>' +
            '</p>' +
            '<p class="live-text-one p2">' +
            '</p>' +
            '</div>' +
            '<div class="live-tip p3">' +
            '<p class="live-title">' +
            '<img src="/xcview/images/aixin.png" alt="标题图标" />' +
            '<span>食疗推荐</span>' +
            '</p>' +
            '</div>' +
            '<div class="live-tip">' +
            '<p class="live-title">' +
            '<img src="/xcview/images/aixin.png" alt="标题图标" />' +
            '<span>保健穴位</span>' +
            '</p>' +
            '<span class="p4"></span>' +
            '</div>' +
            '</div>';
        $(".result3").append(str);
    }
    
    var p1 = "";
    var p2 = "";
    var p4 = "";
    for(var i=0;i<result.medicalConstitutionProposals.length;i++){
        if(result.medicalConstitutionProposals[i]==null)continue;
        p1 = result.medicalConstitutionProposals[i].attention;
        p2 = '<p class="live-text-one">'+result.medicalConstitutionProposals[i].taboo+'</p>';
        p4 = result.medicalConstitutionProposals[i].acupoint;

        var $suggest = $(".suggest"+result.medicalConstitutionProposals[i].constitutionId);
        $suggest.find(".p1").append(p1);
        $suggest.find(".p2").append(p2);
        $suggest.find(".p4").append(p4);
    }

    for(var i=0;i<result.medicalConstitutionRecipes.length;i++){
        if(result.medicalConstitutionRecipes[i]==null)continue;
        var p3 = '<p style="color:#000;">'+result.medicalConstitutionRecipes[i].name+'</p>'+
            '<p class="live-text-one">' +
            '<span class="live-left-tip">原料：</span>'+result.medicalConstitutionRecipes[i].material +
            '</p>' +
            '<p class="live-text-one">' +
            '<span class="live-left-tip">制法：</span>'+result.medicalConstitutionRecipes[i].method +
            '</p>';
        var $suggest = $(".suggest"+result.medicalConstitutionRecipes[i].constitutionId);
        $suggest.find(".p3").append(p3);
    }
}