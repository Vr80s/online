package com.xczhihui.bxg.online.api.service;

import com.xczhihui.common.util.enums.Payment;

/**
 * Description：订单支付处理业务类
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 9:06 2018/1/24 0024
 **/
public interface OrderPayService {

    /**
     * 处理支付成功
     * @param orderNo 订单号
     * @param transactionId 微信支付订单号
     */
    public void addPaySuccess(String orderNo, Payment payment, String transactionId);

}
