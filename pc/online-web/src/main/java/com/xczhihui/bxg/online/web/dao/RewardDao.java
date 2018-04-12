package com.xczhihui.bxg.online.web.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.RewardStatement;
import com.xczhihui.bxg.online.common.domain.Reward;

/**
 * @author liutao 【jvmtar@gmail.com】
 * @create 2017-09-13 16:47
 **/
@Repository
public class RewardDao extends SimpleHibernateDao {

   public void save(RewardStatement rewardStatement){
            super.save(rewardStatement);
    }

    public List<Reward> findAll(){
    	return super.findByHQL("from Reward where isDelete=0 and status=1 order by sort desc");
    }

    public Reward getReward(Integer id){
    	DetachedCriteria dc = DetachedCriteria.forClass(Reward.class);
		dc.add(Restrictions.eq("id", id));
		Reward r = this.findEntity(dc);
    	return r;
    }

    public String getRewardTotal(String liveId) {
        String sql="SELECT SUM(price) FROM `oe_reward_statement` ors WHERE ors.`live_id`= \""+liveId+"\"";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, String.class);
    }
}
