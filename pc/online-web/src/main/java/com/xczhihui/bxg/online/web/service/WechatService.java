package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.web.vo.WechatVo;

/**
 * 微信分销业务层对接接口类
 * @author 康荣彩
 * @create 2016-11-21 16:21
 */
public interface WechatService {


      /**
       * 微信分销调用此接口
       * 实现：用户注册，下单，购买课程等。。
       * @param wechatVo：参数包含：手机号、课程id、商户订单号、微信订单号、金额
       * @return
       */
      public  String  saveUserAndBuyCourse(WechatVo wechatVo);
}
