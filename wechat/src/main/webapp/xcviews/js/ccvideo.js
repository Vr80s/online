



$(function(){
	var say = '聊聊您的想法吧';
	
	if ($("#mywords").html() === "") {
		$("#mywords").html(say);
	}
	$("#mywords").click(function(){
        if($("#mywords").html() == say){
           $("#mywords").html("");
        }
    });
    $("#page_emotion  dd").click(function(){
    	var aa = $("#form_article").html();
        $("#mywords").html($("#mywords").html().replace(say, '') );
        //$("mywords").val(aa);
    });
});

/**
 * 点击初始化视频的方法：
 * @param videoId
 */
function chZJ(videoId,chapterId){
	
	chapterId = chapterId,
	videoId = videoId;
	/**
	 * 请求代码啦
	 */
//	屏幕分辨率的高：window.screen.height 
//	屏幕分辨率的宽：window.screen.width 
	var playerwidth = window.screen.width;
	var playerheight = 8.95*21.8;
	console.log(playerwidth);
	var dataParams = {
		playerwidth:playerwidth,	
		playerheight:playerheight,
		videoId:videoId
	}
	requestService("/bxg/ccvideo/commonCourseStatus", 
			dataParams, function(data) {
		if(data.success){
			var playCodeStr = data.resultObject;
			var playCodeObj = JSON.parse(playCodeStr);
			console.log(playCodeObj.video.playcode);
			$("#ccvideo").html(playCodeObj.video.playcode);
		}else{
//			$(".no_video").show();
//        	$(".li1").click(function(){
//        		$(".opc").show();
//        	});	
			//$("#error_code").text(t.code);
    		$(".history_bg").show();
		}
	},false);
}


var falg = 1;
$(".discuss_main").mCustomScrollbar({
	scrollInertia: 200,
 	theme:"dark",
     axis:"y",
     onTotalScroll:"50px",
     alwaysTriggerOffsets:false,
     onTotalScrollBackOffse:"100px",
     onTotalScrollOffset:"100px",
     callbacks: {
         onTotalScrollBack: function() {
        	/*var curr_page = parseInt($('#new_chatmsg').data('curr_page'));
            if(falg==1){
             	curr_page++;
             	falg++;
            }*/
            //VHALL_SDK.vhall_get_record_history_chat_msg(curr_page + 1);
        	pageNumber++;
        	getVideoCriticize(pageNumber);
         }
     }
});
