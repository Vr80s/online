package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.BarrierVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关卡考试底层实现类
 *
 * @author rongcai Kang
 */
@Repository
public class BarrierDao  extends SimpleHibernateDao {


    /**
     * 获取关卡基本信息
     * @param id 关卡id
     * @param examStatu 考试状态 0:开始考试  1:考试通过  2:考试未通过
     * @return
     */
      public BarrierVo  getBarrierBasicInfo(String id,Integer examStatu,OnlineUser u){
          Map<String,Object> paramMap = new HashMap<>();
          paramMap.put("id",id);
          paramMap.put("userId",u.getId());
          String sum_total="";
          String query="";
          if(examStatu==0){//开始考试，统计考试题数
              sum_total=" , (select sum(bs.question_sum) from oe_barrier_strategy bs where bs.is_delete=0 and bs.barrier_id=b.id) as sum_total";
              query =" and  submit_time is  null  ";
          }else if(examStatu==1) //考试通过
          {
              query=" and result=1 ";
          }else{
              query=" and result=0 and submit_time is not  null ";
          }

          //1、获取当前课程的所有关卡
          String sql = " select b.id, b.`name`, b.total_score,b.pass_score_percent,b.limit_time" +
                         sum_total +
                       " ,br.score,br.result,br.use_time,br.number_pass,br.rank,(UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(br.create_time)) as current_usetime"+
                       " from oe_barrier b,oe_barrier_record br where b.id=br.barrier_id and br.user_id=:userId "+query+" and  b.id=:id and b.is_delete=0  order by br.submit_time desc  limit 1";
          List<BarrierVo> barriers=  this.findEntitiesByJdbc(BarrierVo.class, sql, paramMap);

          return barriers.size() >0 ? barriers.get(0):null;
      }


    /**
     * 获取最新一次闯关关卡基本信息
     * @param id 关卡id
     * @return
     */
    public BarrierVo  getNewBarrierBasicInfo(String id,OnlineUser u){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("userId",u.getId());
        //1、获取当前课程的所有关卡
        String sql = " select b.id, b.`name`, b.total_score,b.pass_score_percent ,b.limit_time" +
                    " ,br.score,if(br.submit_time is null,3,br.result) result,br.use_time,br.submit_time,br.number_pass,br.rank,"+
                    "  (select sum(bs.question_sum) from oe_barrier_strategy bs where bs.is_delete=0 and bs.barrier_id=b.id) as sum_total"+
                    " from oe_barrier b,oe_barrier_record br where b.id=br.barrier_id and br.user_id=:userId  and  b.id=:id and b.is_delete=0  order by br.create_time desc limit 1";
        List<BarrierVo> barriers=  this.findEntitiesByJdbc(BarrierVo.class, sql, paramMap);

        if(barriers.size()<= 0){
             sql =  " select b.id, b.`name`, b.total_score,b.pass_score_percent,b.limit_time,"+
                    "  (select sum(bs.question_sum) from oe_barrier_strategy bs where bs.is_delete=0 and bs.barrier_id=b.id) as sum_total,"+
                    " 2 result from  oe_barrier b where   b.id=:id and b.is_delete=0 ";
           barriers=  this.findEntitiesByJdbc(BarrierVo.class, sql, paramMap);
        }
        return barriers.size() >0 ? barriers.get(0):null;
    }


}
