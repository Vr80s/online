package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.ShareOrderVo;
import com.xczhihui.bxg.online.web.vo.ShareUserVo;
import com.xczhihui.bxg.online.web.vo.SubsidiesVo;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 分销管理模块数据访问层
 * @Author Fudong.Sun【】
 * @Date 2016/12/8 16:09
 */
@Repository
public class ShareManageDao extends SimpleHibernateDao{
    /**
     * 查询用户各等级学费补贴
     * @param userId  补贴对象
     * @return
     */
    public SubsidiesVo findSubsidies(String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append(" IFNULL((select SUM(subsidies) from oe_share_order where target_user_id=:userId and is_delete=0),0) as allFee,");
        sql.append(" IFNULL((select SUM(subsidies) from oe_share_order where target_user_id=:userId and is_delete=0 and `level`=0),0) as levelFee1,");
        sql.append(" IFNULL((select SUM(subsidies) from oe_share_order where target_user_id=:userId and is_delete=0 and `level`=1),0) as levelFee2,");
        sql.append(" IFNULL((select SUM(subsidies) from oe_share_order where target_user_id=:userId and is_delete=0 and `level`=2),0) as levelFee3");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        List<SubsidiesVo>  list = this.findEntitiesByJdbc(SubsidiesVo.class,sql.toString(),paramMap);
        return list.size()>0? list.get(0) : null;
    }

    /**
     * 根据条件查询分享订单
     * @param userId
     * @param searchCase
     * @param level
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<ShareOrderVo> findShareOrders(String userId,Integer searchCase,String searchContent,Integer level,String startTime,String endTime,Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 15 : pageSize;
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append(" select oso.`id`, oso.`buy_user_id`,ou.login_name,oso.`create_time`, oso.`order_no`,oso.`share_order_no`, oso.`target_user_id`,");
        sql.append(" oso.`course_id`, oso.`course_name`, oso.`actual_pay`, oso.`pay_time`, oso.`level`,oso.`subsidies`, oso.`order_status`");
        sql.append(" from oe_share_order oso LEFT JOIN oe_user ou on oso.buy_user_id=ou.id");
        sql.append(" where oso.is_delete=0  and oso.target_user_id= :userId");
        if(searchCase!=null && !StringUtils.isNullOrEmpty(searchContent)) {
            if(searchCase==0){
                sql.append(" and oso.course_name like :searchContent");
            }else if(searchCase==0){
                sql.append(" and ou.login_name like :searchContent");
            }
        }
        if(level!=null){
            sql.append(" and oso.level= :level");
        }
        if(!StringUtils.isNullOrEmpty(startTime) && !StringUtils.isNullOrEmpty(endTime)){
            sql.append(" and oso.pay_time>= :startTime and oso.pay_time<= :endTime");
        }else if(StringUtils.isNullOrEmpty(startTime) && !StringUtils.isNullOrEmpty(endTime)){
            sql.append(" and oso.pay_time<= :endTime");
        }else if(!StringUtils.isNullOrEmpty(startTime) && StringUtils.isNullOrEmpty(endTime)){
            sql.append(" and oso.pay_time>= :startTime");
        }
        sql.append(" order by oso.`pay_time` desc");
        paramMap.put("userId", userId);
        paramMap.put("searchContent", "%" + searchContent + "%");
        paramMap.put("level", level);
        paramMap.put("startTime", startTime + " 00:00:00");
        paramMap.put("endTime", endTime + " 24:00:00");
        return this.findPageBySQL(sql.toString(), paramMap, ShareOrderVo.class, pageNumber, pageSize);
    }


    /**
     * 查询用户各级别用户人数
     * @return
     */
    public Map<String, Object> findUserCount(OnlineUser  user) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append(" (select count(id) from oe_user where parent_id=:userId and is_delete=0 and status=0 ) as oneCount ,");
        sql.append(" (select count(son1.id) from oe_user ou   JOIN oe_user son1 on son1.parent_id=ou.id where ou.parent_id=:userId  and son1.is_delete=0 and son1.status=0 ) as twoCount,");
        sql.append(" (select count(son2.id) from oe_user ou   JOIN oe_user son1 on son1.parent_id=ou.id  JOIN oe_user son2 on son2.parent_id=son1.id where ou.parent_id=:userId  and son2.is_delete=0 and son2.status=0 ) as threeCount");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", user.getId());
        List<Map<String, Object>>  list = this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
        return list.size()>0? list.get(0) : null;
    }


    /**
     * 获取当前登录用户的所有一级分享用户
     * @param searchCase 0:按照昵称搜索  1:按照用户名搜索
     * @param searchContent 搜索内容
     * @param startTime  搜索开始时间
     * @param endTime  结束时间
     * @param pageNumber 当前页面
     * @param pageSize  每页条数
     * @param u  当前登录用户
     * @return
     */
    public Page<ShareUserVo> findOneLevelUser(Integer searchCase,String searchContent,String startTime,String endTime,Integer pageNumber, Integer pageSize,OnlineUser u) throws ParseException {
        DateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 15 : pageSize;
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append(" select u.name,u.login_name, u.create_time, ");
        sql.append(" (SELECT count(c.id) from oe_share_order c  where c.target_user_id=:userId and c.buy_user_id=u.id ) as buyCouseCount ");
        sql.append(" from oe_user u where u.parent_id=:userId  and u.is_delete=0 and u.status=0");
        if(searchCase!=null && searchContent!=null) {
            if(searchCase==0){
                sql.append(" and name like :searchContent");
            }else if(searchCase==1){
                sql.append(" and login_name like :searchContent");
            }
        }

        if(!StringUtils.isNullOrEmpty(startTime) && !StringUtils.isNullOrEmpty(endTime)){
            sql.append(" and create_time between :startTime and  :endTime");
        }else if(StringUtils.isNullOrEmpty(startTime) && !StringUtils.isNullOrEmpty(endTime)){
            sql.append(" and create_time <= :endTime");
        }else if(!StringUtils.isNullOrEmpty(startTime) && StringUtils.isNullOrEmpty(endTime)){
            sql.append(" and create_time>= :startTime");
        }
        sql.append(" order by u.change_time desc ");
        paramMap.put("userId", u.getId());
        paramMap.put("searchContent", "%" + searchContent + "%");
        paramMap.put("startTime",startTime);
        if(!StringUtils.isNullOrEmpty(endTime)){
            Date date = (formatter.parse(endTime));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            paramMap.put("endTime", formatter.format(cal.getTime()));
        }

        return this.findPageBySQL(sql.toString(), paramMap, ShareUserVo.class, pageNumber, pageSize);
    }

}
