


RequestService("/vhallyun/vhallYunToken","get",{channelId:vhallObj.channelId,roomId:vhallObj.roomId},
    function(data) {
    if (data.success) {
        vhallObj.token=data.resultObject;
    }   
},false); 


var onOff = true, isFilter = true;

$("#filter-msg").on("click", function() {//只看主办方消息   
    if (isFilter) {   //
        $(this).addClass("filter-yes").attr("title", "查看全部消息");
        for (var i = 0; i < $(".chatmsg li").length; i++) {
            var isRole = $(".chatmsg li").eq(i).data("role");
            if (isRole == "no") {
                $(".chatmsg li").eq(i).addClass("hide");
            }
        }
        $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999");
        
        //变成红色
        $("#filter-msg").removeClass("lecturer");
        $("#filter-msg").addClass("lecturer_filter1");
        isFilter = false;
    } else {
    	
        $(this).removeClass("filter-yes").attr("title", "只看主办方消息");
        $(".chatmsg li").removeClass("hide");
        $(".chartlist").mCustomScrollbar("update").mCustomScrollbar("scrollTo", "99999");
        
        $("#filter-msg").removeClass("lecturer_filter1");
        $("#filter-msg").addClass("lecturer");
        isFilter = true;
    }
})


if(liveStatus == 1 || liveStatus == 3){
    //初始化 微吼云播放器 
    elsBind();
    //初始化消息
    msgList(0,10);
}  

var initVideoFalg =  0;

function initVideo(){
	
 	window.doc = {};
    var readyCallback = function(){
      initVideoFalg = 1;  	
      var docNode = $('#my-doc-area').length > 0 ? "my-doc-area" : vhallObj.channelId;
      window.doc = new VhallDocPassive({
        channelId:vhallObj.channelId, //频道Id
        docNode:docNode//文档显示节点div id
        /*width:320,
        height:180*/
      });
      
      var roomId = (liveStatus == 1 ? vhallObj.roomId : "");
      var liveType = (liveStatus == 1 ? "live" : "vod");
      var recordId = (liveStatus == 1 ? "" : vhallObj.recordId);
      //判断是回放呢，还是直播呢
      VhallPlayer.init({
       roomId:roomId,
       recordId:recordId, //回放Id，点播必填，直播不写
       type:liveType,
       videoNode:'myVideo',
       complete:function(){
          VhallPlayer.play();
       }
     });   
    }
    
    window.Vhall.ready(readyCallback);
	window.Vhall.config({
         appId :vhallObj.appId,//应用 ID ,必填
         accountId :vhallObj.accountId,//第三方用户唯一标识,必填
         token:vhallObj.token//token必填
    });
}


/**
 * 获取当前网络状态
 * 	@return {int}  
 * 0:音频/视频尚未初始化,
 * 1:音频/视频是活动的且已选取资源，但并未使用网络,
 * 2:浏览器正在下载数据,
 * 3:未找到音频/视频来源
 */
//var falgNetWorkstate  = 0;
//setInterval(function(){
//	try{
//		var netWorkstate = VhallPlayer.getNetworkState();
//		if(netWorkstate ==3 ){
//			falgNetWorkstate++;
//		}
//    	if(falgNetWorkstate>2){
//    		$(".playback").attr("type",21);
//    		$(".playback div").hide();
//        	$(".media-error").show();
//        	$(".playback").show();
//    	}
//    	if(netWorkstate != 2){
//    		console.error("netWorkstate："+netWorkstate);
//    	}
//	}catch(error){
//	 	console.log(error);
//	 	if(initVideoFalg !=1){
//	 		initVideo();
//	 	}
//	}
//},1000)


function elsBind(){
    
	//初始化视频
    initVideo();
    
    /**
     * 加载消息
     */
    setTimeout(function(){
    	
        try{
    		var md = document.getElementsByTagName("video")[0];
    		debugger;
    		//开始播放
    		md.play();
		    md.addEventListener("ended",function(){
		    	 $(".playback div").hide();
		         $(".playback").attr("type",20);
	             $(".playback-rebroadcast").show();
	             $(".playback").show();
		    })	
    	}catch(error){
    	   console.log(error);
    	}
    	
      window.Vhall.ready(function(){
        window.chat = new VhallChat({
           channelId:vhallObj.channelId //频道Id
        });
        
        /**
         * 监听聊天消息
         */
        window.chat.on(function(msg){
            //在此收到聊天消息，消息内容为msg
            if (msg){
                var str = chatLoad(msg);
                if(str!=""){
                 $("#chatmsg").append(str);  
                }
            }
            $("#mywords").val('');
        });
        
        /**
         * 监听自定义消息
         */
        window.chat.onCustomMsg(function(msg){
             msg = JSON.parse(msg);
             try{
             	var e="";
             	//20 重播  21 刷新页面
             	$(".playback").attr("type",msg.type);
             	
                if(msg.type ==10 ){//聊天
                    e+=liaotian(msg);
                }else if(msg.type == 11){ //礼物
                    e+=liveGiftList(msg);
                     //在礼物区域显示
                	createGiftList(msg.message);
                }else if(msg.type == 12){ // 开始直播啦
                	// 刷新页面 --》在观看
                	setTimeout(function () {
                		  location.reload();
                	},1000)
                	
                }if(msg.type == 13){ //直播结束了
                	
                    $(".playback div").hide();
                    if(record){
                        $(".generate-replay").show();
                    }else{
                        $(".learning-center").show();
                    }
                	$(".playback").show();
                	
                } else if (msg.type == 14) { // 退出直播间，但是没有结束直播
  					$(".playback div").hide();
                	$(".leave").show();
                	$(".playback").show();
                	
				} else if (record && msg.type == 16) { // 回放生成成功
					
					setTimeout(function () {
        				$(".playback div").hide();
				    	$(".see-the-replay").show();
                		$(".playback").show();
        			},2000)
					
				} else if (!record || msg.type == 17) { // 回放生成失败
					$(".playback div").hide();
					$(".learning-center").show();
                	$(".playback").show();
				}
                if (e != "") {
					$("#chatmsg").append(e);
					$("#mywords").val("");
				}
				
				$(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
				
             }catch(error){
               console.error(error);
             }
        })
        
        window.chat.join(function(msg){
            viewJoinleaveRoomInfo(msg,"join");
        })
        window.chat.leave(function(msg){
            viewJoinleaveRoomInfo(msg,"leave");
        })
     });	
     window.Vhall.config({
          appId :vhallObj.appId,//应用 ID ,必填
          accountId :vhallObj.accountId,//第三方用户唯一标识,必填
          token:vhallObj.token//token必填
     });
    },1000);	
      
    //  
    $(".playback").click(function() {
    	var type = $(this).attr("type");
    	if(!record){
            location.href="/my#menu1-1";
        }else if (type == 16 || type ==20 || type == 21) { // 回放生成成功   重播  获取媒体资源有误
        	location.reload();
		}else if (type == 17) { // 回放生成失败,点击去学习中心吧
			location.href="/my";
	    }
    }) 
    
    /**
     * 发送聊天消息
     */  
    $("#sendChat").click(function() {
    	
        $(".coze_bottom").css("bottom", "0rem");  //这是输入框在最底部,添加到其他文件不起作用
        var text = $("#mywords").val();
        if(text!=null && ""!=text && undefined!=text){
          var content = {
	            type:10,                 //消息类型     1 聊天消息
	            message:{
	                content:text,   //发送的内容
	                role:"normal"           //发送人的角色    主播： host   普通用户： normal
	          } 
	      } 
	      
	      $("#mywords").val("");
         /**
          * 发送消息
          */ 
         RequestService("/vhallyun/vhallYunSendMessage","get",{channel_id:vhallObj.channelId,body:JSON.stringify(content)},
                function(data) {
                if (data.success) {
					console.log("发送消息成功");
                }   
          }); 
        }
    });
}

$(".chatmsg-box").mCustomScrollbar();

/**
 * 获取消息列表
 * @param {} pos   第几页
 * @param {} limit 每页多少条
 */
function msgList(pos,limit){
	
  var num = (pos - 1) * limit;
  num = num < 0 ? 0 : num;	
 	
  var params = {
    channel_id:vhallObj.channelId,
    pos:num,
    limit:limit,
    start_time:"2017/01/01"
  }
  /**
   * 获取列表啦
   */ 
  RequestService("/vhallyun/message","get",params,
        function(data) {
        if (data.success && data.resultObject.code == 200) {
        	 var res = data.resultObject;
        	 var e = "";
             for (var i = res.data.length - 1; i >= 0; i--) {
                var item = res.data[i].data;
                try{
                    item = JSON.parse(JSON.parse(item));
                	if(item.type ==10 ){//聊天
                        e+=liaotian(item);
                    }else if(item.type == 11){ //礼物
                        e+=liveGiftList(item);
                    }
                }catch(error){
                   console.error(error);
                }
             }
             if(e!=""){
             	 $("#chatmsg").html(e);
             	 $(".chatmsg-box").mCustomScrollbar('update').mCustomScrollbar("scrollTo","bottom");
             }
        } 	
  });      	
}
msgList(0,100);



  //聊天消息
function liaotian(obj){
	obj = obj.message;
	
    var role_str = "";
    var role= obj.role;
    var str_hide = "no";
    if(role=='assistant'){
        role_str +="<a class='tips assistant' data-role='yes' href='javascript:;'>助理</a>";
        str_hide = "yes";
        
    } else if(role == 'host'){
        role_str +="<a class='tips host' data-role='yes' href='javascript:;'>主播</a>";
        str_hide = "yes";
    }
    else if(role == 'guest'){
        role_str +="<a class='tips guest' data-role='yes' href='javascript:;'>嘉宾</a>";
        str_hide = "yes";
    }
//      头像注释
    role_str+="<a class='name' href='javascript:;' title=' user_name'>"+obj.username+" </a>";
    
    var className = "";
    if(!isFilter && role =="user" ){
        className = "hide";
    }
    
    //替换表情为url
    var contentEmoji = (obj.content!=null && obj.content!="") ? replaceEmoji(obj.content) :obj.content;
    
    var aaa = "<li uid=' user_id' data-role="+str_hide+" class="+className+">"+
    /*聊天区域*/
        "<div class='msg'>"+
        "<p> " + role_str+"：<span style='color:#fff;'>"+contentEmoji+"</span></p >"+
        "</div>"+
      "</li>";
    return aaa;
}
    
//进入直播间
function viewJoinleaveRoomInfo(msg,joinOrLeave){

	var userName = msg.nick_name;
	var content = "进入直播间";
    if(joinOrLeave == "join"){
        content = "进入直播间";
    }else if(joinOrLeave == "leave"){
        content = "退出直播间";
    }
    var  role_str="<a class='name' href='javascript:;' title=' user_name'>"+userName+" </a>";
    var className = "";
    if(!isFilter){
        className = "hide";
    }
    var aaa = "<li uid=' user_id' data-role='no'  class="+className+" >"+
    /*聊天区域*/
        "<div class='msg'>"+
            "<p> " + role_str+"：<span style='color:#fff;'>"+content+"</span></p >"+
        "</div>"+
      "</li>";
   
    $("#chatmsg").append(aaa);
    
}
    
    
//送礼
function liveGiftList(obj){
	var message = obj.message;
    var  role_str="<a class='name' href='javascript:;' title=' user_name'>"+message.senderInfo.userName+" </a>";
    var className = "";
    if(!isFilter){
        className = "hide";
    }
    var aaa = "<li uid=' user_id' data-role='no'  class="+className+" >"+
    /*聊天区域*/
        "<div class='msg'>"+
            "<p> " + role_str+"：赠送给主播1个<span style='color:#fff;'>"+message.giftInfo.name+"</span></p >"+
        "</div>"+
      "</li>";
    return aaa;
  }


// 请求--》json文件
var emoji = [{"text":"[微笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_1@2x.png"},{"text":"[撇嘴]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_2@2x.png"},{"text":"[色]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_3@2x.png"},{"text":"[发呆]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_4@2x.png"},{"text":"[得意]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_5@2x.png"},{"text":"[流泪]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_6@2x.png"},{"text":"[害羞]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_7@2x.png"},{"text":"[闭嘴]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_8@2x.png"},{"text":"[睡]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_9@2x.png"},{"text":"[哭]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_10@2x.png"},{"text":"[尴尬]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_11@2x.png"},{"text":"[发怒]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_12@2x.png"},{"text":"[调皮]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_13@2x.png"},{"text":"[呲牙]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_14@2x.png"},{"text":"[惊讶]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_15@2x.png"},{"text":"[难过]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_16@2x.png"},{"text":"[酷]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_17@2x.png"},{"text":"[汗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_18@2x.png"},{"text":"[抓狂]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_19@2x.png"},{"text":"[吐]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_20@2x.png"},{"text":"[偷笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_21@2x.png"},{"text":"[愉快]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_22@2x.png"},{"text":"[白眼]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_23@2x.png"},{"text":"[傲慢]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_24@2x.png"},{"text":"[饥饿]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_25@2x.png"},{"text":"[困]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_26@2x.png"},{"text":"[惊恐]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_27@2x.png"},{"text":"[流汗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_28@2x.png"},{"text":"[憨笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_29@2x.png"},{"text":"[悠闲]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_30@2x.png"},{"text":"[奋斗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_31@2x.png"},{"text":"[咒骂]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_32@2x.png"},{"text":"[疑问]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_33@2x.png"},{"text":"[嘘]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_34@2x.png"},{"text":"[晕]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_35@2x.png"},{"text":"[疯了]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_36@2x.png"},{"text":"[衰]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_37@2x.png"},{"text":"[骷髅]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_38@2x.png"},{"text":"[敲打]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_39@2x.png"},{"text":"[再见]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_40@2x.png"},{"text":"[擦汗]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_41@2x.png"},{"text":"[抠鼻]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_42@2x.png"},{"text":"[鼓掌]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_43@2x.png"},{"text":"[糗大了]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_44@2x.png"},{"text":"[坏笑]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_45@2x.png"},{"text":"[左哼哼]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_46@2x.png"},{"text":"[右哼哼]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_47@2x.png"},{"text":"[哈欠]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_48@2x.png"},{"text":"[鄙视]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_49@2x.png"},{"text":"[委屈]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_50@2x.png"},{"text":"[快哭了]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_51@2x.png"},{"text":"[阴险]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_52@2x.png"},{"text":"[亲亲]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_53@2x.png"},{"text":"[吓]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_54@2x.png"},{"text":"[可怜]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_55@2x.png"},{"text":"[菜刀]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_56@2x.png"},{"text":"[西瓜]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_57@2x.png"},{"text":"[啤酒]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_58@2x.png"},{"text":"[篮球]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_59@2x.png"},{"text":"[乒乓]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_60@2x.png"},{"text":"[咖啡]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_61@2x.png"},{"text":"[饭]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_62@2x.png"},{"text":"[猪头]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_63@2x.png"},{"text":"[玫瑰]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_64@2x.png"},{"text":"[凋谢]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_65@2x.png"},{"text":"[嘴唇]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_66@2x.png"},{"text":"[爱心]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_67@2x.png"},{"text":"[心碎]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_68@2x.png"},{"text":"[蛋糕]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_69@2x.png"},{"text":"[闪电]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_70@2x.png"},{"text":"[炸弹]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_71@2x.png"},{"text":"[刀]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_72@2x.png"},{"text":"[足球]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_73@2x.png"},{"text":"[瓢虫]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_74@2x.png"},{"text":"[便便]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_75@2x.png"},{"text":"[月亮]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_76@2x.png"},{"text":"[太阳]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_77@2x.png"},{"text":"[礼物]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_78@2x.png"},{"text":"[拥抱]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_79@2x.png"},{"text":"[强]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_80@2x.png"},{"text":"[弱]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_81@2x.png"},{"text":"[握手]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_82@2x.png"},{"text":"[胜利]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_83@2x.png"},{"text":"[抱拳]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_84@2x.png"},{"text":"[勾引]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_85@2x.png"},{"text":"[拳头]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_86@2x.png"},{"text":"[差劲]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_87@2x.png"},{"text":"[爱你]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_88@2x.png"},{"text":"[NO]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_89@2x.png"},{"text":"[OK]","imgUrl":"https://file.ipandatcm.com/static/img/Expression_90@2x.png"}];

var pattern1 = /\[[\u4e00-\u9fa5]+\]/g;
var pattern2 = /\[[\u4e00-\u9fa5]+\]/;
/**
 * 表情
 */
function replaceEmoji(contents) {
	content = contents.match(pattern1);
	if(content == null){
	  return contents;
	}
	str = contents;
	for (i = 0; i < content.length; i++) {
		var src = "";
		for (j = 0; j < emoji.length; j++) {
			if (emoji[j].text == content[i]) {
				src = emoji[j].imgUrl;
				break;
			}
		}
		if(src!=""){
			var imgBag = "<img src="+src+" style='width:20px;' />";
		    str = str.replace(pattern2, imgBag);
		}
	}
	return str;
}
