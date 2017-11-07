package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.WechatDao;
import com.xczhihui.bxg.online.web.service.WechatService;
import com.xczhihui.bxg.online.web.vo.WechatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信分销业务层对接接口类
 * @author 康荣彩
 * @create 2016-11-21 16:21
 */
@Service
public class WechatServiceImpl extends OnlineBaseServiceImpl implements WechatService {

    @Autowired
    private WechatDao wechatDao;


    /**
     * 微信分销调用此接口
     * 实现：用户注册，下单，购买课程等。。
     * @param wechatVo：参数包含：手机号、课程id、商户订单号、微信订单号、金额
     * @return
     */
    @Override
    public String saveUserAndBuyCourse(WechatVo wechatVo) {
//        //1、注册此用户
//        String saveUserResult=userService.addWechatRegist(wechatVo);
//        //2、注册用户成功后，给此用户生成购买订单，并且将课程相关视频规划到用户下
//        if ("注册成功".equals(saveUserResult))
//        {
//            //2.1 进行下单
//            orderDao.saveWechatOrder(wechatVo);
//            //2.2 给此用户相关课程的所有视频
//            applyService.saveApply();
//
//        }
//        return null;

        return  wechatDao.saveUserAndBuyCourse(wechatVo);

    }
}
