package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 购物车模块数据访问层代码
 * @Author Fudong.Sun【】
 * @Date 2017/2/20 16:09
 */
@Repository
public class ShoppingCartDao extends SimpleHibernateDao{

    /**
     * 查询我的购物车中课程数量
     * @param userId
     * @return
     */
    public Integer findCourseNum(String userId){
        String sql = "select count(1) from oe_shopping_cart where user_id=?";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,userId);
    }
    /**
     * 课程加入购物车
     * @param userId
     * @param courseId
     */
    public void addCart(String userId, Integer courseId) {
        String sql = "insert into oe_shopping_cart(id,user_id,course_id,create_time) values (:id,:user_id,:course_id,now())";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", UUID.randomUUID().toString().replace("-", ""));
        params.addValue("user_id",userId);
        params.addValue("course_id",courseId);
        this.getNamedParameterJdbcTemplate().update(sql,params);
    }

    /**
     * 查询该课程是否在购物车中
     * @param courseId
     * @return true:存在，false：不存在
     */
    public Boolean ifExistsCourse(String userId,Integer courseId){
        String sql = "select count(1) from oe_shopping_cart where course_id=? and user_id=?";
        int num = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,courseId,userId);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 我的购物车课程列表
     * @param userId
     * @return
     */
    public List<Map<String,Object>> lists(String userId){
        String sql = "select p.id,p.course_id ,c.course_type, c.grade_name as courseName ,c.smallimg_path as smallImgPath,c.original_cost as originalCost," +
        		"c.type,direct_id,c.id courseId,"+
                "c.current_price as currentPrice, now() as create_time from oe_shopping_cart p LEFT JOIN oe_course c on p.course_id=c.id " +
                "where p.user_id =:user_id order by p.create_time desc";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("user_id",userId);
        return this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }

    public int delete(String userId,Set<String> ids){
        String sql = "delete from oe_shopping_cart where id in (:ids) and user_id =:user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", ids);
        params.addValue("user_id", userId);
        return this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    /**
     * 课程批量加入购物车
     * @param userId 购买课程用户id
     * @param courseIds 加入购物车课程id数组
     * @param rule_id 活动id号
     */
    public void addCourseToCart(String userId, List<String> courseIds,String rule_id) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        String sql;
        if (rule_id==null){
            params.addValue("course_id", courseIds);
            sql = " replace into oe_shopping_cart(id,user_id,course_id,create_time) "+
                " SELECT replace(uuid(),'-','') id,'"+userId+"' user_id,id course_id ,now() create_time from oe_course  where id in (:course_id) ";
        }else{
            params.addValue("rule_id", rule_id);
            sql = " replace into oe_shopping_cart(course_id,id,user_id,create_time) "+
                " SELECT DISTINCT rd.course_id , replace(uuid(),'-','') id,'"+userId+"' user_id,now() create_time from oe_activity_rule_detail rd where rd.rule_id =:rule_id " +
                " and not exists(select 1 from oe_order oo left join oe_order_detail ood on oo.id=ood.order_id where ood.course_id=rd.course_id and oo.user_id='"+userId+"' and oo.order_status=1)" +
                " and not exists(select 1 from oe_course where id=rd.course_id and current_price=0)";
        }
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    /**
     * 检查是否已经购买此课程
     * @param userId
     * @param courseId
     * @return
     */
    public boolean ifHasBuyCourse(String userId,Integer courseId){
        String sql = "select count(1) from oe_order oo left join oe_order_detail ood on oo.id=ood.order_id where ood.course_id=? and oo.user_id=? and oo.order_status=1";
        int num = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,courseId,userId);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 检查是否是0元课
     * @param courseId
     * @return
     */
    public boolean ifZeroCourse(Integer courseId){
        String sql = "select count(1) from oe_course where id=? and current_price=0";
        int num = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,courseId);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }
}
