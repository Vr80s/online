package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.RewardStatement;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @author lituao
 * @create 2017-08-22 10:41
 **/
@Repository
public class RewardStatementMapper  extends BasicSimpleDao {

   public int insert(RewardStatement s) throws SQLException {
        return this.update(JdbcUtil.getCurrentConnection(),"insert into oe_reward_statement(channel,reward_id,create_time,price,giver,receiver,live_id,pay_type,client_type,status,order_no) values(?,?,?,?,?,?,?,?,?,?,?)",s.getChannel(),s.getRewardId(),s.getCreateTime(),s.getPrice(),s.getGiver(),s.getReceiver(),s.getLiveId(),s.getPayType(),s.getClientType(),s.getStatus(),s.getOrderNo());
    }

}
