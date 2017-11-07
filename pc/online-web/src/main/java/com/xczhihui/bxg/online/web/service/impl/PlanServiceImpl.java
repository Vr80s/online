package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.PlanService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   PlanService:学习计划业务层接口实现类
 * * @author Rongcai Kang
 */
@Service
public class PlanServiceImpl extends OnlineBaseServiceImpl implements PlanService {


    /**
     * 获取用户此课程学习计划
     * @return
     */
    public List<Map<String,Object>> getUserLearPlan(Integer courseId, HttpServletRequest request){
        //获取当前登录用户
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(u !=null){

            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("userId",u.getId());
            paramMap.put("courseId", courseId);
            //获取用户所在班级
            String gradesql="SELECT  g.grade_id  from oe_apply a,apply_r_grade_course g where a.id=g.apply_id and g.course_id=:courseId and a.user_id=:userId  limit 1";
            List<Map<String, Object>> grades =  this.getDao().getNamedParameterJdbcTemplate().queryForList(gradesql, paramMap);
            /*20170724---yuruixin*/
            Integer gradeId = null;
            if(grades.size()>0)
            	gradeId=Integer.valueOf(grades.get(0).get("grade_id").toString());
            /*20170724---yuruixin*/
            paramMap.put("grade_id",gradeId);
            //获取此班级的学习计划
            String plansql=" SELECT  id,plan_date,week,day,chuanjiang_has,chuanjiang_name,DATE_FORMAT(chuanjiang_start_time,'%Y-%m-%d %H:%i') chuanjiang_start_time,template_id,"+
                           " DATE_FORMAT(chuanjiang_end_time,'%Y-%m-%d %H:%i') chuanjiang_end_time,chuanjiang_mode,chuanjiang_room_id,chuanjiang_room_link,rest_has from oe_plan  where grade_id=:grade_id  order by plan_date";
            List<Map<String, Object>> plans =dao.getNamedParameterJdbcTemplate().queryForList(plansql, paramMap);
            //获取此课程模板设置的所有知识点
            String sql="SELECT  name,plan_template_id from oe_chapter where course_id=:courseId and `level`=4  and plan_template_id is not null ";
            List<Map<String, Object>> points =dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
            if(points.size() >0 && plans.size()>0){
                for (Map<String, Object> plan :plans){
                    List<Map<String, Object>> pointMap =new ArrayList<Map<String, Object>>();
                    for (Map<String, Object> point :points){
                        if(point.get("plan_template_id").toString().equals(plan.get("template_id").toString())){
                            pointMap.add(point);
                        }
                    }
                    plan.put("point",pointMap);
                }
            }
            return  plans;
        }
        return  null;
    }
}
