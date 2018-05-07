

if(liveStatus!=1){ //
	//$('#button').attr('disabled',"true");
	$("#mywords").attr('disabled',"disabled");
	$("#mywords").attr('readonly',"readonly");
}

/**
* 礼物排行榜
 * @returns
 */
function getRankingListByLiveId(){
	//获取个人熊猫币余额
	/**
	 * 课程id，分页参数
	 * @param data
	 * @returns
	 */
	var params = {
		liveId:"579",
		pageSize:10,
		pageNumber:1	
	}
	RequestService("/gift/getRankingListByLiveId", "GET",params, function(data) {
		var balance = data.resultObject;
		if(data.success && data.resultObject!=null && data.resultObject.length>0){
			var small_items = [];
			var items = data.resultObject;
			for (var i = 0; i < items.length; i++) {
				var item = items[i];
				if(i==0){
					$(".billboard-two-bg").html("<img src='"+item.smallHeadPhoto+"' />");
					$(".billboard-two").find(".billboard-name").html(item.name);
					$(".billboard-two").find("span").html(item.giftCount);
				}else if(i==1){
					$(".billboard-one-bg").html("<img src='"+item.smallHeadPhoto+"' />");
					$(".billboard-one").find(".billboard-name").html(item.name);
					$(".billboard-one").find("span").html(item.giftCount);
				}else if(i==2){
					$(".billboard-three-bg").html("<img src='"+item.smallHeadPhoto+"' />");
					$(".billboard-three").find(".billboard-name").html(item.name);
					$(".billboard-three").find("span").html(item.giftCount);
				}else{
					small_items.push(item);
				}
			}
			var gifts ="";
			for (var i = 0; i < small_items.length; i++) {
				var item = small_items[i];
				var bank = i+4;
				var gift = "<div class='toptenreviews-list-center'>"+
					"	<span class='number'>"+bank+"</span>"+
					"	<div class='toptenreviews-list-img'><img src='"+item.smallHeadPhoto+"' /></div>"+
					"	<div class='toptenreviews-list-size'>"+item.name+"</div>"+
					"	<div class='toptenreviews-list-number'>"+
					"		<span>贡献&nbsp;&nbsp;</span>"+
					"		<span>"+item.giftCount+"</span>"+
					"	</div>"+
					"</div>";
				gifts += gift;
			}
			$(".toptenreviews-list").html(gifts);
	     }
	});
}
//排行榜渲染
getRankingListByLiveId();



/**
 * 获取主播信息
 * @returns
 */
function getHostInfo(){
	
	RequestService("/host/getHostInfoById", "GET", {
		lecturerId:lecturerId
	}, function(data) {
		var obj = data.resultObject;
		var lecturerInfo =  obj.lecturerInfo;
		$(".concern-img").attr("src",lecturerInfo.small_head_photo);
		$(".concern-left").find(".concern-name").text(lecturerInfo.name);
		$(".concern-number").find("span").eq(0).html("关注&nbsp;"+obj.focusCount+"");
		$(".concern-number").find("span").eq(1).html("粉丝&nbsp;"+obj.fansCount+"");
		if(obj.isFours==1){//已关注
			$(".concern-right").css("background","#bbb");
			$(".concern-right").html("已关注");
		}else if(obj.isFours==0){//未关注
			$(".concern-right").css("background","#00BC12");
			$(".concern-right").html("加关注");
		}
	});
}
getHostInfo();

/*点击关注已关注开始*/
$(".concern-click").click(function(){
	var lalal = $(".concern-click").text();
	if(lalal == "加关注"){
		RequestService("/focus/updateFocus", "GET", {
			lecturerId:lecturerId,type:1
		}, function(data) {
			console.log("lalala"+data);
		});
		$(".concern-right").css("background","#bbb");
		$(".concern-right").html("已关注");
		getHostInfo();
	} else{
		RequestService("/focus/updateFocus", "GET", {
			lecturerId:lecturerId,type:2
		}, function(data) {
			console.log("lalala"+data);
		});
		$(".concern-right").css("background","#00BC12");
		$(".concern-right").html("加关注");
		getHostInfo();
	}
});
/*点击关注已关注结束*/










