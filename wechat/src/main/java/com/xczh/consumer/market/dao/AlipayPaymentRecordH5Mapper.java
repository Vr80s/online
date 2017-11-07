package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.AlipayPaymentRecordH5;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @author liutao
 * @create 2017-08-18 16:55
 **/
@Repository
public class AlipayPaymentRecordH5Mapper  extends BasicSimpleDao {



    public int insert(AlipayPaymentRecordH5 record) throws SQLException {

        StringBuilder sql = new StringBuilder();
        sql.append("insert into alipay_payment_record_h5			");
        sql.append("(       notify_time,\n" +
                "       notify_type,\n" +
                "       notify_id ,\n" +
                "       app_id ,\n" +
                "       charset,\n" +
                "       version ,\n" +
                "       sign_type,\n" +
                "       sign ,\n" +
                "       trade_no ,\n" +
                "       out_trade_no,\n" +
                "       out_biz_no ,\n" +
                "       buyer_id ,\n" +
                "       buyer_logon_id ,\n" +
                "       seller_id ,\n" +
                "       seller_email ,\n" +
                "       trade_status ,\n" +
                "       total_amount ,\n" +
                "       receipt_amount ,\n" +
                "       invoice_amount ,\n" +
                "       buyer_pay_amount ,\n" +
                "       point_amount ,\n" +
                "       refund_fee ,\n" +
                "       `subject`,\n" +
                "       body,\n" +
                "       gmt_create ,\n" +
                "       gmt_payment ,\n" +
                "       gmt_refund ,\n" +
                "       gmt_close ,\n" +
                "       fund_bill_list,\n" +
                "       passback_params ,\n" +
                "       user_id ,\n" +
                "       voucher_detail_list	                               	");

        sql.append(")                                   ");
        sql.append("values                              ");
        sql.append("(                                   ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?,                                 ");
        sql.append("  ?                                 ");
        sql.append(")                                   ");


      return   super.update(
                JdbcUtil.getCurrentConnection()	,
                sql.toString()					,
                record.getNotifyTime()				,
                record.getNotifyType()				,
                record.getNotifyId()				,
                record.getAppId()			,
                record.getCharset()			,
                record.getVersion()		,
                record.getSignType()				,
                record.getSign()			,
                record.getTradeNo()				,
                record.getOutTradeNo()		,
                record.getOutBizNo()			,
                record.getBuyerId()			,
                record.getBuyerLogonId()				,
                record.getSellerId()			,
                record.getSellerEmail()			,
                record.getTradeStatus()			,
                record.getTotalAmount()			,
                record.getReceiptAmount()		,
                record.getInvoiceAmount(),
                record.getBuyerPayAmount(),
                record.getPointAmount(),
                record.getRefundFee(),
                record.getSubject(),
                record.getBody(),
                record.getGmtCreate(),
                record.getGmtPayment(),
                record.getGmtRefund(),
                record.getGmtClose(),
                record.getFundBillList(),
                record.getPassbackParams(),
                record.getUserId(),
                record.getVoucherDetailList()

        );
    }
}
