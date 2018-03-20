package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.Gift;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author liutao
 * @create 2017-08-21 20:34
 **/
@Repository
public class GiftMapper extends BasicSimpleDao {

    public Gift get(String id){
        String sql="select id, id giftId,name,smallimg_path smallimgPath,price,is_free isFree,is_continuous isContinuous,continuous_count continuousCount  from oe_gift where id=? ";
        try {
          return   this.query(JdbcUtil.getCurrentConnection(), sql,new BeanHandler<>(Gift.class),new Object[]{id});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Gift> listAll(Integer pageNumber, Integer pageSize) throws SQLException {
        String sql="select id, id giftId,name,smallimg_path smallimgPath,price,is_free isFree,is_continuous isContinuous,continuous_count continuousCount from oe_gift og where og.is_delete=0 and og.status=1 ORDER BY sort desc";
        List<Gift> lists = this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,Gift.class, null);
        return lists;
    }

    //用户收到的礼物数量
    public int findByUserId(String userId) throws SQLException {

        String sql="select SUM(count) from oe_gift_statement where receiver=? ";

       return this.query(JdbcUtil.getCurrentConnection(),sql, new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet resultSet) throws SQLException {
                if(resultSet.next()){
                 return   resultSet.getInt(1);
                }
                return 0;
            }
        },new Object[]{userId});

    }

    //课程收到的礼物数量
    public int findByLiveId(int liveId) throws SQLException {

        String sql="select SUM(count) from oe_gift_statement where live_id=? ";

        return this.query(JdbcUtil.getCurrentConnection(),sql, new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet resultSet) throws SQLException {
                if(resultSet.next()){
                    return   resultSet.getInt(1);
                }
                return 0;
            }
        },new Object[]{liveId});

    }
}
