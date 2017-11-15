



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
		pageSize:2
	}
	requestService("/bxg/criticize/getVideoCriticize", 
			dataParams, function(data) {
		
		var items = data.resultObject.items;
		if(items.length>0){
			var str = '';
			for (var i = items.length - 1; i >= 0; i--) {
				  var obj = items[i]; 
				  str += "<div class='discuss_main_one'>"+
					"<div class='discuss'>"+
						"<img src='"+obj.smallPhoto+"' alt='' class='discuss_img' />"+
						"<p>"+obj.userName+"</p>"+
					"</div>"+
					"<div class='discuss_size'>"+obj.content+"</div>";
				  
					//<!-- 回复模块啦 -->
					if(stringnull(obj.response)){
						str += "<div class='discuss_size_reply'>熊猫中医回复："+obj.response+"</div>";
					}
					str +="<div class='discuss_time'>"+
						"<div class='discuss_time_size'>"+obj.createTime.replace(/-/g,".")+"</div>"+
						"<div class='praise'>"+
							"<span id='praise' class='praise0' title="+obj.id+">";
							 if(obj.isPraise){
								 str += "<img src='../images/yizan.png' class='praise-img' />"; 
							 }else{
								 str += "<img src='../images/zan01.png' class='praise-img' />";
							 }
							 str +="</span>"+
							"<span id='praise-txt'>"+obj.praiseSum+"</span>"+
							"<span id='add-num'><em>+1</em></span>"+
						"</div>"+
					    "</div>"+
					  "<div class='both'></div>"+
				    "</div>";
	        }		
			if (pageNumber == 1){
	         	$("#new_chatmsg").html(str);
	         	setTimeout(function(){
	     			$(".discuss_main").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
	             },50);
	        }else{
	        	$("#new_chatmsg").prepend(str);
         		setTimeout(function(){
         			$(".discuss_main").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
                },50);
	        }
		}
	},false)
}
getVideoCriticize(1);



/*"<div class='praise'>"+
"<span id='praise' class='praise0' ><img src='../images/zan01.png' title="+obj.id+" class='praise-img' /></span>"+
"<span id='praise-txt'>0</span>"+
"<span id='add-num'><em>+1</em></span>"+
"</div>"+*/
/**
 * 点赞和取消赞
 */

$(".praise0").click(function(){
	var praise_img = $(this).find("img");
	
	/*alert(123);*/
	
	var criticizeId =  $(this).attr("title");
	
	var praise_txt = $(this).next();
	var text_box = $(this).next().next();
//	var text_box = $("#add-num");
//	var praise_txt = $("#praise-txt");
	var num=parseInt(praise_txt.text());
	var falg = true;
	if(praise_img.attr("src") == ("../images/yizan.png")){  //取消赞
		$(this).html("<img src='../images/zan01.png' id='praise-img' class='animation' />");
		praise_txt.removeClass("hover");
		text_box.show().html("<em class='add-animation'>-1</em>");
		$(".add-animation").removeClass("hover");
		num -=1;
		praise_txt.text(num)
		falg = false;
	}else{												  //点赞
		$(this).html("<img src='../images/yizan.png' id='praise-img' class='animation' />");
		praise_txt.addClass("hover");
		text_box.show().html("<em class='add-animation'>+1</em>");
		$(".add-animation").addClass("hover");
		num +=1;
		praise_txt.text(num);
		falg = true;
	}
	
	//Boolean isPraise, String criticizeId
     var dataParams ={
    		 isPraise: falg,
    		 criticizeId:criticizeId
     }
	 requestService("/bxg/criticize/updatePraise",dataParams, function(data) {
    	  if(data.success){
    		  console.log("点赞成功");
    	  }	  
	 },false)
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



 $("#sendChat").click(function() {
      //var userInfo  = VHALL_SDK.getUserinfo();
      var text = $("#mywords").html();
      $("#mywords").html("")
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
      var str ="";
      requestService("/bxg/criticize/saveCriticize", 
  			dataParams, function(data) {
    	  if(data.success){
    		  
    		  /*str += "<div class='discuss_main_one'>"+
				"<div class='discuss'>"+
					"<img src='"+smallHeadPhoto+"' alt='' class='discuss_img' />"+
					"<p>"+name+"</p>"+
				"</div>"+
				"<div class='discuss_size'>"+text+"</div>";
			  
			  str +="<div class='discuss_time'>"+
					"<div class='discuss_time_size'>"+new Date().pattern("yyyy.MM.dd hh:mm:ss")+"</div>"+
					"<div class='praise'>"+
						"<span id='praise' class='praise0'><img src='../images/zan01.png' id='praise-img' /></span>"+
						"<span id='praise-txt'>0</span>"+
						"<span id='add-num'><em>+1</em></span>"+
					"</div>"+
				    "</div>"+
				  "<div class='both'></div>"+
			    "</div>";*/
	 	      
			 /* $("#new_chatmsg").append(str);  
	 	      $("#mywords").val('');
	 	      $(".discuss_main").mCustomScrollbar("scrollTo","bottom","0");*/
    	  
			  $("#mywords").val('');
    		  getVideoCriticize(1);
	 	      $(".discuss_main").mCustomScrollbar("scrollTo","bottom","0");
    	  }else{
    		  alert(data.errorMessage);
    	  }
      })
      
      var token = localStorage.getItem("token");
	if(!stringnull(token)){
	  location.href = "/xcviews/html/share.html?course_id=" + getQueryString("courseId");
	}
		
	//初始化微信 jssdk
	var ccontrollerAddress = "/bxg/wxjs/h5JSSDK";
	var urlparm = {
		url: window.location.href
	}
	var signObj = "";
	requestService(ccontrollerAddress, urlparm, function(data) {
		if (data.success) {
			signObj = data.resultObject;
		    console.log(JSON.stringify(signObj));
		}	
	},false)	
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: signObj.appId, // 必填，企业号的唯一标识，此处填写企业号corpid
	    timestamp: signObj.timestamp, // 必填，生成签名的时间戳
	    nonceStr:signObj.noncestr, // 必填，生成签名的随机串
	    signature: signObj.sign,// 必填，签名，见附录1
	    jsApiList: [
			'checkJsApi',
			'onMenuShareTimeline',
			'onMenuShareAppMessage',
			'onMenuShareQQ',
			'onMenuShareWeibo',
			'onMenuShareQZone',
			'hideMenuItems',
			'showMenuItems'         
	    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
      
      
 });