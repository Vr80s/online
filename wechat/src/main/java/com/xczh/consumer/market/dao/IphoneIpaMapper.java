package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;

import org.apache.commons.dbutils.handlers.MapHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * 苹果内购
 * @author liutao
 * @create 2017-10-10 18:02
 **/
@Repository
public class IphoneIpaMapper extends BasicSimpleDao {

    public void save(String json,String actualPrice,String transactionId,String orderNo,String userId,String subject,Integer type) {
        try {
            this.update(JdbcUtil.getCurrentConnection()," insert into iphone_iap(json,actual_price,transaction_id,order_no,create_time,user_id,subject,type) values(?,?,?,?,?,?,?,?) ",json,actualPrice,transactionId,orderNo,new Date(),userId,subject,type);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
	public Integer findIap(String orderNo) throws SQLException {
		String sql = "SELECT count(*) as c from iphone_iap where  order_no = ? ";
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql,
				new MapHandler(),orderNo);
		Integer isCurrntCount = 0;
		if(map!=null && map.size()>0){
			Object type = map.get("c");
			if(type!=null){
				isCurrntCount = Integer.parseInt(type.toString());
			}
		}
		return isCurrntCount;
	}


}
