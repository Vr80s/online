package com.xczhihui.bxg.online.web.service;

import com.xczhihui.wechat.course.vo.PayMessageVo;

import java.util.Map;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/4/19 0019-下午 2:59<br>
 */
public interface PayService {

    void aliPayBusiness(Map<String, String> params) throws Exception;

    void wxPayBusiness(Map<String, String> params) throws Exception;

    void business(String type, String outTradeNo, String tradeNo, PayMessageVo payMessageVo) throws Exception;
}
