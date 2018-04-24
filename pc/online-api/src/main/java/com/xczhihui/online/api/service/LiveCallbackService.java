package com.xczhihui.online.api.service;



/**
 * 接受到微吼 开始直播结束直播的请求后，进行IM通知
 * 直播回调service
 * ClassName: LiveCallbackService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年3月13日<br>
 */
public interface LiveCallbackService {


   /**
    * 目前仅仅支持开播 
    * Description：当开始直播时，广播这个直播开始
    * @param liveId
    * @param type 1 直播 开始  2 直播结束
    * @return void
    * @author name：yangxuan <br>email: 15936216273@163.com
    *
    */
   void liveCallbackImRadio(String liveId,Integer type) ;
}
