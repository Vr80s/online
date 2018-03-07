package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.AlipayPaymentRecordH5;

/**
 * @author
 * @create 2017-08-18 17:12
 **/
public interface AlipayPaymentRecordH5Service {

    void insert(AlipayPaymentRecordH5 alipayPaymentRecordH5);

    /**
     * 
     * Description：根据商户id查询支付宝交易记录
     * @param outTradeNo
     * @return
     * @return AlipayPaymentRecordH5
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	AlipayPaymentRecordH5 queryAlipayPaymentRecordH5ByOutTradeNo(String outTradeNo);
}
