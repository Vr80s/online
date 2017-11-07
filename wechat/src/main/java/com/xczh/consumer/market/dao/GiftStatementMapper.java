package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.GiftStatement;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.RankingUserVo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author
 * @create 2017-08-21 20:35
 **/
@Repository
public class GiftStatementMapper extends BasicSimpleDao {

    public int addGiftStatement(GiftStatement giftStatement) throws SQLException {


        int ct=        this.update(JdbcUtil.getCurrentConnection(),"insert into oe_gift_statement(channel,gift_id,gift_name,create_time,price,count,giver,receiver,live_id,pay_type,client_type,status) values(?,?,?,?,?,?,?,?,?,?,?,?)",giftStatement.getChannel(),giftStatement.getGiftId(),giftStatement.getGiftName(),giftStatement.getCreateTime(),giftStatement.getPrice(),giftStatement.getCount(),giftStatement.getGiver(),giftStatement.getReceiver(),giftStatement.getLiveId(),giftStatement.getPayType(),giftStatement.getClientType(),giftStatement.getStatus());

        return ct;
    }

    /**
     * 榜单列表
     * @param type 0 总榜 1 日榜 2 周榜 3月榜单
     * @return
     */
    public List<RankingUserVo> rankingList(String liveId, int type, int pageNumber, int pageSize) throws SQLException {
        String con="";
        if(type==1){
            con=" and to_days(ogs.create_time) = to_days(now()) ";
        }else if(type==2){
            con=" and YEARWEEK(date_format(ogs.create_time,'%Y-%m-%d')) = YEARWEEK(now())  ";
        }else if(type==3){
            con=" and DATE_FORMAT(ogs.create_time,'%Y%m' ) = DATE_FORMAT(CURDATE() , '%Y%m' ) ";
        }

        String sql="select ou.id userId,ou.`name`,ou.small_head_photo smallHeadPhoto,ogs.create_time,SUM(ogs.count) giftCount from oe_gift_statement ogs INNER JOIN oe_user ou on(ogs.giver=ou.id)  where ogs.live_id=? "+con+" GROUP BY giver ORDER BY  SUM(ogs.count) desc ";
        List<RankingUserVo> lists = this.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(), pageNumber, pageSize,RankingUserVo.class, liveId);
        return lists;

    }
}
;