package com.xczhihui.wechat.course.service.impl;

import com.xczhihui.bxg.common.util.enums.PayOrderType;
import com.xczhihui.wechat.course.mapper.AlipayPaymentRecordMapper;
import com.xczhihui.wechat.course.mapper.WxcpPayFlowMapper;
import com.xczhihui.wechat.course.model.AlipayPaymentRecord;
import com.xczhihui.wechat.course.model.Order;
import com.xczhihui.wechat.course.model.WxcpPayFlow;
import com.xczhihui.wechat.course.service.IOrderService;
import com.xczhihui.wechat.course.service.IPaymentRecordService;
import com.xczhihui.wechat.course.vo.PayMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Description: 支付记录<br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/4/19 0019-下午 3:23<br>
 */
@Service
public class PaymentRecordServiceImpl implements IPaymentRecordService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String BUY_COURSE_TEXT = "购买课程{0}";
    private static final String BUY_COIN_TEXT = "充值熊猫币:{0}个";

    @Autowired
    private AlipayPaymentRecordMapper alipayPaymentRecordMapper;
    @Autowired
    private WxcpPayFlowMapper wxcpPayFlowMapper;
    @Autowired
    private IOrderService orderService;

    @Value("${rate}")
    private int rate;

    @Override
    public AlipayPaymentRecord saveAlipayPaymentRecord(Map<String, String> params) {
        logger.info("回调："+params.get("out_trade_no"));
        for (Map.Entry<String, String> entry : params.entrySet()) {
            logger.info(entry.getKey() + " = " + entry.getValue());
        }
        AlipayPaymentRecord apr = new AlipayPaymentRecord();
        apr.setOutTradeNo(params.get("out_trade_no"));
        apr = alipayPaymentRecordMapper.selectOne(apr);
        if(apr!=null){
            //若记录存在，返回空
            return null;
        }

        AlipayPaymentRecord alipayPaymentRecord = new AlipayPaymentRecord();
        String userId = null;
        PayMessageVo payMessageVo = PayMessageVo.getPayMessageVo(params.get("passback_params"));

        if(PayOrderType.COURSE_ORDER.getCode().equals(payMessageVo.getType())){
            userId = payMessageVo.getUserId();
            Order order = orderService.getOrderNo4PayByOrderNo(params.get("out_trade_no"));
            alipayPaymentRecord.setSubject(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
        }else if(PayOrderType.COIN_ORDER.getCode().equals(payMessageVo.getType())){
            BigDecimal count = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(rate));
            userId = payMessageVo.getUserId();
            alipayPaymentRecord.setSubject((MessageFormat.format(BUY_COIN_TEXT,count)));
        }

        alipayPaymentRecord.setUserId(userId);
        alipayPaymentRecord.setTradeNo(params.get("trade_no"));
        alipayPaymentRecord.setAppId(params.get("app_id"));
        alipayPaymentRecord.setOutTradeNo(params.get("out_trade_no"));
        alipayPaymentRecord.setOutBizNo(params.get("out_biz_no"));
        alipayPaymentRecord.setBuyerId(params.get("buyer_id"));
        alipayPaymentRecord.setSellerId(params.get("seller_id"));
        alipayPaymentRecord.setTradeStatus(params.get("trade_status"));
        alipayPaymentRecord.setTotalAmount(params.get("total_amount"));
        alipayPaymentRecord.setReceiptAmount(params.get("receipt_amount"));
        alipayPaymentRecord.setInvoiceAmount(params.get("invoice_amount"));
        alipayPaymentRecord.setBuyerPayAmount(params.get("buyer_pay_amount"));
        alipayPaymentRecord.setPointAmount(params.get("point_amount"));
        alipayPaymentRecord.setRefundFee(params.get("refund_fee"));
        alipayPaymentRecord.setBody(params.get("body"));
        alipayPaymentRecord.setGmtCreate(params.get("gmt_create"));
        alipayPaymentRecord.setGmtPayment(params.get("gmt_payment"));
        alipayPaymentRecord.setGmtClose(params.get("gmt_close"));
        alipayPaymentRecord.setFundBillList(params.get("fund_bill_list"));
        alipayPaymentRecord.setVoucherDetailList(params.get("voucher_detail_list"));
        alipayPaymentRecord.setPassbackParams(params.get("passback_params"));
        alipayPaymentRecordMapper.insert(alipayPaymentRecord);
        return alipayPaymentRecord;
    }

    @Override
    public WxcpPayFlow saveWxPayPaymentRecord(Map<String, String> packageParams) throws ParseException {
        logger.info("回调："+packageParams.get("out_trade_no"));
        WxcpPayFlow wpf = new WxcpPayFlow();
        wpf.setTransactionId(packageParams.get("transaction_id"));
        wpf = wxcpPayFlowMapper.selectOne(wpf);
        if(wpf!=null){
            //若记录存在，返回空
            return null;
        }
        String out_trade_no = String.valueOf(packageParams.get("out_trade_no"));
        String transaction_id = String.valueOf(packageParams.get("transaction_id"));
        String flow_id = UUID.randomUUID().toString().replace("-", "");
        String appid = String.valueOf(packageParams.get("appid")); // 应用ID
        String attach = String.valueOf(packageParams.get("attach")); // 商户数据包
        String bank_type = String.valueOf(packageParams.get("bank_type"));// 付款银行
        String fee_type = String.valueOf(packageParams.get("fee_type")); // fee_type
        String is_subscribe = String.valueOf(packageParams.get("is_subscribe"));// 是否关注公众账号
        String mch_id = String.valueOf(packageParams.get("mch_id")); // 商户号
        String nonce_str = String.valueOf(packageParams.get("nonce_str"));// 随机字符串
        String openid = String.valueOf(packageParams.get("openid"));
        String result_code = String.valueOf(packageParams.get("result_code"));// 业务结果
        String return_code = String.valueOf(packageParams.get("return_code"));
        String sign = String.valueOf(packageParams.get("sign"));
        String sub_mch_id = String.valueOf(packageParams.get("sub_mch_id"));
        String time_end = String.valueOf(packageParams.get("time_end")); // 支付完成时间
        String total_fee = String.valueOf(packageParams.get("total_fee")); // 总金额
        String trade_type = String.valueOf(packageParams.get("trade_type"));// 交易类型
        String payment_type = "WeChat";
        WxcpPayFlow wxcpPayFlow = new WxcpPayFlow();

        String userId = null;
        PayMessageVo payMessageVo = PayMessageVo.getPayMessageVo(attach);

        if(PayOrderType.COURSE_ORDER.getCode().equals(payMessageVo.getType())){
            userId = payMessageVo.getUserId();
            Order order = orderService.getOrderNo4PayByOrderNo(out_trade_no.substring(0,20));
            wxcpPayFlow.setSubject(MessageFormat.format(BUY_COURSE_TEXT,order.getCourseNames()));
        }else if(PayOrderType.COIN_ORDER.getCode().equals(payMessageVo.getType())){
            //微信金额单位为分
            BigDecimal count = new BigDecimal(total_fee).divide(new BigDecimal(100)).multiply(new BigDecimal(rate));
            userId = payMessageVo.getUserId();
            wxcpPayFlow.setSubject((MessageFormat.format(BUY_COIN_TEXT,count)));
        }

        wxcpPayFlow.setUserId(userId);
        wxcpPayFlow.setFlowId(flow_id);
        wxcpPayFlow.setAppid(appid);
        wxcpPayFlow.setAttach(attach);
        wxcpPayFlow.setBankType(bank_type);
        wxcpPayFlow.setFeeType(fee_type);
        wxcpPayFlow.setIsSubscribe(is_subscribe);
        wxcpPayFlow.setMchId(mch_id);
        wxcpPayFlow.setNonceStr(nonce_str);
        wxcpPayFlow.setOpenid(openid);
        wxcpPayFlow.setOutTradeNo(out_trade_no);
        wxcpPayFlow.setResultCode(result_code);
        wxcpPayFlow.setReturnCode(return_code);
        wxcpPayFlow.setSign(sign);
        wxcpPayFlow.setSubMchId(sub_mch_id);
        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=format.parse(time_end);
        wxcpPayFlow.setTimeEnd(date);
        wxcpPayFlow.setTotalFee(Integer.valueOf(total_fee));
        wxcpPayFlow.setTradeType(trade_type);
        wxcpPayFlow.setTransactionId(transaction_id);
        wxcpPayFlow.setPaymentType(payment_type);
        wxcpPayFlowMapper.insert(wxcpPayFlow);
        return wxcpPayFlow;
    }
}
