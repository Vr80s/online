package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.PayRecordVo;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${rate}")
	private int rate;
   
	
    public List<PayRecordVo> findByUserId(String userId,
                                          Integer pageNumber, Integer pageSize) throws SQLException {


    	//alipay_payment_record 这是pc端的打赏、充值、购买记录表
    	//alipay_payment_record_h5 	支付宝h5、安卓app--支付打赏充值记录
    	//wxcp_pay_flow 	所有关于微信支付的记录表
    	//iphone_iap	苹果内购产生的记录信息表
    	
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
     /**
      * 
      * Description：我的钱包接口
      * @param userId
      * @param pageNumber
      * @param pageSize
      * @return
      * @throws SQLException
      * @return List<PayRecordVo>
      * @author name：yangxuan <br>email: 15936216273@163.com
      *
      */
	 public List<PayRecordVo> findUserWallet(String userId,
	            Integer pageNumber, Integer pageSize) throws SQLException {

	    	//alipay_payment_record 这是pc端的打赏、充值、购买记录表
	    	//alipay_payment_record_h5 	支付宝h5、安卓app--支付打赏充值记录
	    	//wxcp_pay_flow 	所有关于微信支付的记录表
	    	//iphone_iap	苹果内购产生的记录信息表
	    	
			String sql="SELECT r.* FROM "
			+ "( SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.gmt_create gmtCreate, apr.total_amount*"+rate+" totalAmount "
			+ " FROM alipay_payment_record apr WHERE apr.user_id =? "
			
			+ "UNION SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.gmt_create gmtCreate, apr.total_amount*"+rate+" totalAmount "
			+ " FROM alipay_payment_record_h5 apr WHERE apr.user_id =? "
			
			// 微信充值、购买、打赏记录表（现在暂无打赏）  -- 单位是人民币
			+ "UNION SELECT apr.out_trade_no outTradeNo, apr. SUBJECT, apr.time_end gmtCreate,truncate((apr.total_fee/100),2)*"+rate+" totalAmount "
    		+ " FROM wxcp_pay_flow apr WHERE user_id =?"
			
			// 所有熊猫币--》购买课程的记录    -- 单位就是熊猫币
			+ " union select ucc.order_no_course as outTradeNo,'购买课程' as `subject`,ucc.create_time gmtCreate,ucc.value totalAmount "
			+ "from user_coin_consumption ucc where ucc.user_id=? "
			
			//用户送礼产生的的记录   这就就全部都是礼物了    -- 单位也是熊猫币
			+ " union select ogs.id as outTradeNo,'赠送礼物' as subject,ogs.create_time as gmtCreate,(ogs.price*ogs.price) as totalAmount "
			+ "from oe_gift_statement ogs where ogs.giver=? "
			
			+ ") r ORDER BY r.gmtCreate DESC";
			
			
			
			
			
			
			Object[] params = new Object[]{userId,userId,userId,userId,userId};
			List<PayRecordVo> lists = this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,PayRecordVo.class, params);
			return lists;
	    }
}
