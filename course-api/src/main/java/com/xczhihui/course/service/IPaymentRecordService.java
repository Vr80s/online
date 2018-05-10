package com.xczhihui.course.service;

import com.xczhihui.course.model.AlipayPaymentRecord;
import com.xczhihui.course.model.WxcpPayFlow;

import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface IPaymentRecordService {

    public AlipayPaymentRecord saveAlipayPaymentRecord(Map<String, String> params);


    WxcpPayFlow saveWxPayPaymentRecord(Map<String, String> params) throws ParseException;
}
