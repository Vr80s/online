



function chZJ(videoId){
   return;
}

var course_id = getQueryString("courseId");


//新的token啦：频道id：ch_fc6f51f4    ，   token access:27376e92:07a2aa77f97a36e0
/**
 * 
 */
var obj = {
    roomId:"lss_ad04fdfe",
    appId:"27376e92",
    accountId:"test_jssdk",
    token:"access:27376e92:07a2aa77f97a36e0"
}


  Vhall.ready(function(){
  	 /**
      * 播放器初始化
      */
      VhallPlayer.init({
          roomId:obj.roomId,//房间ID,直播必填，点播不写
          recordId:'',//回放Id，点播必填，直播不写
          type:'live',//播放类型,必填，live 直播, vod 为点播
          videoNode:'video',//推流视频回显节点id，必填
          complete:function(){
              /**
              * 播放
              */
              VhallPlayer.play();
          }
      });
      
         /**
         * 初始化文档对象
         */
        var doc = VhallDocPassive({
          channelId:'ch_fc6f51f4',//频道ID
          docNode:'doc'//文档显示节点div id
        /*  width:'800',//非必填
          height:'450'//非必填 */        
        });
      
      
  });
  
  /**
   * 通用的配置
   */
  Vhall.config({
     appId :obj.appId,//应用 ID ,必填
     accountId :obj.accountId,//第三方用户唯一标识,必填
     token :obj.token//token必填
  });
  
  