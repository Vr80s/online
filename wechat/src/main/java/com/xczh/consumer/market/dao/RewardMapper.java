package com.xczh.consumer.market.dao;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/8/26 15:13
 */

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.xczh.consumer.market.bean.Reward;
import com.xczh.consumer.market.utils.JdbcUtil;

/**
 * @author liutao
 * @create 2017-08-26 15:13
 **/
@Repository
public class RewardMapper extends BasicSimpleDao {

    public List<Reward> findAll() throws SQLException {
        String sql="select * from oe_reward where is_delete=0 and status=1  order by sort ";
        return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(Reward.class));
    }


    public Reward get(String id){
        String sql="select id,is_delete isDelete,create_time createTime,sort,status,price,is_freedom isFreedom,brokerage from oe_reward where is_delete=0 and status=1 and id= ?";
        try {
        	List<Reward>  list = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(Reward.class),id);
            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
}
