




function chZJ(videoId){
   return;
}

var course_id = getQueryString("courseId");


//新的token啦：频道id：ch_fc6f51f4    ，   token access:27376e92:07a2aa77f97a36e0

//access:27376e92:07a2aa77f97a36e0
/**
 * 
 */
var obj = {
    roomId:"lss_ad04fdfe",
    appId:"27376e92",
    accountId:"test_jssdk",
    token:"access:27376e92:07a2aa77f97a36e0",
    channelId:'ch_fc6f51f4'
}

  
 
function elsBind(){

    window.doc = {};
    var readyCallback = function(){
    	
      window.doc = new VhallDocPassive({
        // roomId:$('#ipt-roomId').val(),//房间ID,bi tian
        channelId:obj.channelId, //频道Id
        // docId:$('#ipt-docId').val(),//jpg big
         docNode:'my-doc-area'//文档显示节点div id
        // width:$('#ipt-width').val(),
        // height:$('#ipt-height').val()
      });

        /**
         * 初始化聊天对象
         */
        window.chat = new VhallChat({
           appId :obj.appId,//应用 ID ,必填
           token:obj.token,//token必填
           channelId:'ch_fc6f51f4'//频道Id，必填
        });

        /**
         * 监听聊天消息
         */
        window.chat.on(function(msg){
            //在此收到聊天消息，消息内容为msg
            //todo
            var msgBox = document.getElementById('msg-box');
            var span = document.createElement('span');
            span.innerText = msg.data;
            msgBox.appendChild(span);
        });
    }

    window.Vhall.ready(readyCallback);
    
    
    
    window.Vhall.config({
         appId :obj.appId,//应用 ID ,必填
         accountId :obj.accountId,//第三方用户唯一标识,必填
         token:obj.token//token必填
    });

    
    
    setTimeout(function(){
          VhallLive.init({
               roomId:obj.roomId,
               type:'live',
               videoNode:'myVideo',
               complete:function(){
                  VhallLive.play();
               }
          });
     },1000);
      
      
    $("#sendChat").click(function() {
        $(".coze_bottom").css("bottom", "0rem");  //这是输入框在最底部,添加到其他文件不起作用
        var text = $("#mywords").val();
        if(text!=null){
          window.chat.emit(text);
        }
    });
}
//初始化    
elsBind();


function msgList(){
	
//	channel_id string  是   频道ID
//  type    int 否   查询类型 ，1 聊天列表（ 默认），2 自定义聊天列表
//  pos    int 否   获取条目节点，默认为 0。eg : 10 从第10条开始查询
//  limit   int 否   获取条目数量，默认为 10 条，最大为1000条
//  start_time  date    是   查询开始时间，格式为：2017/01/01
//  end_time    date    否   查询结束时间，默认为当前时间，格式为：2017/01/01
  var params = {
    channel_id:obj.channelId,
    type:1,
    pos:0,
    limit:0,
    start_time:"2017/01/01"
  }
	
  $.ajax({
        url: "//api.yun.vhall.com/api/v1/channel/get-message-list",
        type: "get",
        dataType: "jsonp",
        jsonp: "callback",
        data: params,
        success: function(e) {
           
        	alert("哈哈");
        },
        error: function(e) {
            r.trigger("sendSign", {
                code: 20005,
                msg: "接口请求失败"
            })
        }
  })
}

msgList();

  