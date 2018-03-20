



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
