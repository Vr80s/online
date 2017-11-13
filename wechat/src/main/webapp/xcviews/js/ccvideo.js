



$(function(){
	var say = '说点什么...';
	
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
function chZJ(videoId){
	/**
	 * 请求代码啦
	 */
//	屏幕分辨率的高：window.screen.height 
//	屏幕分辨率的宽：window.screen.width 
	var playerwidth = window.screen.width;
	var playerheight = window.screen.height*30/100;
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

/**
 * 
 */
/*
 * 加载评论列表
 */
var pageNumber = 1;
var videoId ="";
/**
 * 获取评论列表
 */
var name = localStorage.name;
var userId =  localStorage.userId;
var smallHeadPhoto =  localStorage.smallHeadPhoto;
function  getVideoCriticize(pageSize){
	//视频id :2c9aec355eb943f5015ecb4221f30005
	//用户名：15936216273
	//分页参数：pageSize
	
	//生产
//	var dataParams ={
//		pageSize:pageSize,
//		videoId:videoId,	
//		name:name,
//		pageNumber:20
//	}
	//测试
	var dataParams ={
		pageNumber:pageNumber,
		videoId:"2c9aec355eb943f5015ecb4221f30005",	
		name:"15936216273",
		pageSize:20
	}
	requestService("/bxg/criticize/getVideoCriticize", 
			dataParams, function(data) {
		
		var items = data.resultObject.items;
		if(items.length>0){
			var str = '';
			for (var i = items.length - 1; i >= 0; i--) {
	          	 if(userId == items[i].userId){
	          	    str += "<div class='coze_cen_ri'> "+
	      			 "<img src="+items[i].smallPhoto+" alt='' "+
	      			"	class='coze_cen_ri_img' /> "+
	      			"  <div class='coze_cen_bg_ri'> "+
	      			"	<img src='/xcviews/images/sanjiao2.png' alt='' />"+items[i].content+"  "+
	      			" </div> "+
	      			" <div class='both'></div></div>";
	               }else{
	              	 str += "<div class='coze_cen'>";
	                   str+="<img src='"+items[i].smallPhoto+"' alt='' class='coze_cen_left_img' />";
	                   str+="<div class='coze_cen_bg'>";
	                   str+="<img src='/xcviews/images/sanjiao.png' alt='' />"+items[i].content+"</div>" +
	                   		"<div class='both'></div></div>";
	               }
	        }		
			if (pageNumber == 1){
	         	$("#chatmsg").html(str);
	         	setTimeout(function(){
	     			$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
	             },50);
	        }else{
	         	if(pageNumber == 2){
	         		$("#chatmsg").prepend(str);
	         		setTimeout(function(){
	         			$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
	                 },50);
	         		
	           }else{
	         		$("#chatmsg").prepend(str);
	         		setTimeout(function() {
	         		   $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","-=500");
	         		 },100)   
	         	}
	         }
		}
	})
}
getVideoCriticize(1);

var falg = 1;
$(".chatmsg-box").mCustomScrollbar({
	scrollInertia: 200,
 	theme:"dark",
     axis:"y",
     onTotalScroll:"50px",
     alwaysTriggerOffsets:false,
     onTotalScrollBackOffse:"100px",
     onTotalScrollOffset:"100px",
     callbacks: {
         onTotalScrollBack: function() {
        	/*var curr_page = parseInt($('#chatmsg').data('curr_page'));
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



 $("#sendChat").click(function() {
      //var userInfo  = VHALL_SDK.getUserinfo();
      var text = $("#mywords").html();
      var msg = null;
//      msg = VHALL_SDK.sendChat({
//	      text: text
//	  });
      /*
       * 保存评论
       */
//      保存评论需要的字段：
//      评论内容，创建时间、用户id、章节id、视频id
      
//      var dataParams = {
//          content:text,	
//          chapterId:chapterId,
//          videoId:videoId
//      }
      /*
       * <img src="http://attachment-center.ixincheng.com:38080/
       *          data/picture/online/expression/images/arclist/9.gif" />
       */
      if(text.length>3000){
    	  alert("评论长度太长了");
    	  return;
      }
      var dataParams = {
          content:text,	
          chapterId:"2c9aec345eb431d8015eb6a297790055",
          videoId:"2c9aec355eb943f5015ecb4221f30005"
      }
      requestService("/bxg/criticize/saveCriticize", 
  			dataParams, function(data) {
    	  if(data.success){
    		  var str = "<div class='coze_cen_ri'> "+
	 			 "<img src="+smallHeadPhoto+" alt='' "+
	 			"	class='coze_cen_ri_img' /> "+
	 			"  <div class='coze_cen_bg_ri'> "+
	 			"	<img src='/xcviews/images/sanjiao2.png' alt='' />"+text+"  "+
	 			" </div> "+
	 			" <div class='both'></div></div>";
    		  
	 	      $("#chatmsg").append(str);  
	 	      $("#mywords").val('');
	 	      $(".chatmsg-box").mCustomScrollbar("scrollTo","bottom","0");
    	  }else{
    		  alert(data.errorMessage);
    	  }
      })
 });