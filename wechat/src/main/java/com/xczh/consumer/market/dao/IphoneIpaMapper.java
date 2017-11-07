package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;

/**
 * 苹果内购
 * @author liutao
 * @create 2017-10-10 18:02
 **/
@Repository
public class IphoneIpaMapper extends BasicSimpleDao {

    public void save(String json,String actualPrice,String transactionId,String orderNo,String userId,String subject) {
        try {
            this.update(JdbcUtil.getCurrentConnection()," insert into iphone_iap(json,actual_price,transaction_id,order_no,create_time,user_id,subject) values(?,?,?,?,?,?,?) ",json,actualPrice,transactionId,orderNo,new Date(),userId,subject);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }




}
