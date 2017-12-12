package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.PayRecordVo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * 消费记录
 * @author liutao
 * @create 2017-08-23 16:27
 **/
@Repository
public class PayRecordMapper extends BasicSimpleDao {

    public List<PayRecordVo> findByUserId(String userId,
                                          Integer pageNumber, Integer pageSize) throws SQLException {
        String sql="SELECT r.* FROM "
        		+ "( SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.gmt_create gmtCreate, apr.total_amount totalAmount "
        		+ " FROM alipay_payment_record apr WHERE apr.user_id =? "
        		+ "UNION SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.gmt_create gmtCreate, apr.total_amount totalAmount "
        		+ " FROM alipay_payment_record_h5 apr WHERE apr.user_id =? "
        		+ "UNION SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.time_end gmtCreate,  truncate((apr.total_fee/100),2)  totalAmount "
        		+ " FROM wxcp_pay_flow apr WHERE user_id =?"
        		+ " union select ii.order_no,ii.`subject`,ii.create_time gmtCreate,ii.actual_price totalAmount "
        		+ "from iphone_iap ii where ii.user_id=? ) r ORDER BY r.gmtCreate DESC";
        
        Object[] params = new Object[]{userId,userId,userId,userId};
        List<PayRecordVo> lists = this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,PayRecordVo.class, params);
        return lists;
    }

    public PayRecordVo findByOrderNo(String orderNo) throws SQLException {
        String sql="SELECT r.* FROM ( SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.gmt_create gmtCreate, apr.total_amount totalAmount FROM alipay_payment_record apr WHERE apr.out_trade_no =? UNION SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.gmt_create gmtCreate, apr.total_amount totalAmount FROM alipay_payment_record_h5 apr WHERE apr.out_trade_no =? UNION SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.time_end gmtCreate, TRUNCATE ((apr.total_fee / 100), 2) totalAmount FROM wxcp_pay_flow apr WHERE out_trade_no =? ) r ORDER BY r.gmtCreate DESC";
        Object[] params = new Object[]{orderNo,orderNo,orderNo};
       List<PayRecordVo> lists = this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), 1, 10,PayRecordVo.class, params);

       if(lists.size()>0){
           return lists.get(0);
       }
        return null;
    }



}
