package com.xczhihui.bxg.online.web.base.common;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.dao.OrderDao;
import com.xczhihui.bxg.online.web.service.MessageService;
import com.xczhihui.bxg.online.web.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by admin on 2016/11/11.
 */
@Component
public class MyTask extends SimpleHibernateDao{

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MessageService messageService;
    
    @Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;
    /**
     * 每半个小时执行一次
     * 将数据库超过24小时未支付的订单关闭
     *
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void  updateOrderStatus(){
        /*Map<String, Object> paramMap = new HashMap<String, Object>();
        //查询所有未支付订单,订单失效前1小时给用户提醒
        String  sql ="select t2.course_id,t1.order_no,t3.grade_name,t1.user_id from oe_order t1,oe_order_detail t2,oe_course t3 "
        		+ " where t1.id = t2.order_id and t2.course_id = t3.id and t1.order_status=0  "
        		+ " and date_add(t1.create_time, INTERVAL 1 day) BETWEEN date_add(now(),INTERVAL 1 Hour) and date_add(now(),INTERVAL  '1:30'  HOUR_MINUTE) "
        		+ " group by t1.id ";
        List<Map<String,Object>> orders = this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
        for (Map<String,Object> order :orders){
            String msg_id = UUID.randomUUID().toString().replace("-", "");
            String msg_link = weburl+"/web/html/payCourseDetailPage.html?id="+order.get("course_id")+"&courseType=1&free=0";
            String msg_link1 =weburl+"/web/"+order.get("order_no")+"/findOrderByOrderNo";
            String message = "您购买的课程<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">" + order.get("grade_name") + "</a>订单即将失效，"+
                             "<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link1 + "','" + order.get("order_no") + "');\">点击去支付>></a>";
            messageService.addMessage(msg_id, order.get("user_id").toString(), 0, message,0,null);
        }*/

        //将未支付的订单超过24小时的置为失效
        orderDao.updateStatus();
    }


    /**
     * 产生佣金订单
     * 每天凌晨3:00计算所有新生成订单的佣金
     * "0 0 3 * * ?"
     */
    @Scheduled(cron = "0 0 3 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void addShareOrder(){
         //查询所有未产生佣金的已完成订单
    	orderDao.addShareOrder();
    	System.out.println("查询所有未产生佣金的已完成订单");
    }

    /**
     * 课程到期通知:
     * 提前7天、3天、1天,在早9点通知
     * 每天早上九点执行
    @Scheduled(cron = "0 0 9 * * ?")
    public void dealDueCourseTask(){
        //查询所有普通课程开始时间
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "select id,create_time,grade_name from oe_course where type is null and is_delete=0";
        List<Map<String,Object>> courses = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
        for (Map<String, Object> course : courses) {
            String courseId = (String) course.get("id");
            String grade_name = (String) course.get("grade_name");
            Date create_time = (Date) course.get("create_time");
            //开始时间
            Calendar curr = Calendar.getInstance();
            curr.setTime(new Date());
            long startTime = curr.getTimeInMillis();
            //一年后的时间
            Calendar curr1 = Calendar.getInstance();
            curr1.setTime(create_time);
            curr1.add(Calendar.YEAR, 1);
            long endTime = curr1.getTimeInMillis();
            long difference = (endTime-startTime)/1000/60/60/24;//天数差值
            if(difference==7){
                params.addValue("course_id",courseId);
                sql = "select oa.id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.course_id=:course_id";
                List<Map<String,Object>> users = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
                String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                String msg_link = "http://onlineweb.ixincheng.com/web/html/CourseDetailZhiBo.html?courseId=" + courseId;
                String message = "您的课程&nbsp;<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">" + grade_name + "</a>&nbsp;还有7天到期，抓紧时间学习啦~";
                for (Map<String, Object> user : users) {
                    String user_id = (String) user.get("user_id");
                    messageService.addMessage(msg_id, user_id, 1, message,0);
                }
            }else if(difference==3){
                params.addValue("course_id",courseId);
                sql = "select oa.id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.course_id=:course_id";
                List<Map<String,Object>> users = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
                String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                String msg_link = "http://onlineweb.ixincheng.com/web/html/CourseDetailZhiBo.html?courseId=" + courseId;
                String message = "您的课程&nbsp;<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">" + grade_name + "</a>&nbsp;还有3天到期，抓紧时间学习啦~";
                for (Map<String, Object> user : users) {
                    String user_id = (String) user.get("user_id");
                    messageService.addMessage(msg_id, user_id, 1, message,0);
                }
            }else if(difference==1){
                params.addValue("course_id",courseId);
                sql = "select oa.id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.course_id=:course_id";
                List<Map<String,Object>> users = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
                String msg_link = "http://onlineweb.ixincheng.com/web/html/CourseDetailZhiBo.html?courseId=" + courseId;
                for (Map<String, Object> user : users) {
                    String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                    String message = "您的课程&nbsp;<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">" + grade_name + "</a>&nbsp;还有1天到期，抓紧时间学习啦~";
                    String user_id = (String) user.get("user_id");
                    messageService.addMessage(msg_id, user_id, 1, message,0);
                }
            }
        }
    }*/



    /**
     * 串讲通知:
     * 串讲课提前半小时提醒，持续至串讲结束，不可关闭通知
     * 每5分钟执行
     * cron = "0 0/5 * * * ?"
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void dealConstrueTask(){
        /** 1，查询所有未开始的串讲课，发送消息通知 */
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "select ol.id,ol.grade_id,ol.chuanjiang_start_time,ol.chuanjiang_mode,ol.chuanjiang_room_id,ol.chuanjiang_room_link,og.course_id,micro_course_ids " +
                "from oe_plan ol LEFT JOIN oe_grade og on ol.grade_id=og.id where chuanjiang_has =1 and ol.chuanjiang_start_time>NOW() and og.is_delete=0";
        List<Map<String,Object>> courses = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
        for (Map<String, Object> course : courses) {
            Integer course_id = Integer.parseInt(course.get("course_id").toString());
            String plan_id = (String) course.get("id");
            Integer grade_id = Integer.parseInt(course.get("grade_id").toString());
            Integer chuanjiang_mode = Integer.parseInt(course.get("chuanjiang_mode").toString());
            String chuanjiang_room_id = (String) course.get("chuanjiang_room_id");
            String chuanjiang_room_link = (String) course.get("chuanjiang_room_link");
            String micro_course_ids = course.get("micro_course_ids")==null? "" : (String) course.get("micro_course_ids");
            Date start_time = (Date) course.get("chuanjiang_start_time");
            //当前时间
            Calendar curr = Calendar.getInstance();
            curr.setTime(new Date());
            long nowTime = curr.getTimeInMillis();
            //串讲课开始的时间
            Calendar curr1 = Calendar.getInstance();
            curr1.setTime(start_time);
            long startTIme = curr1.getTimeInMillis();
            long difference = (startTIme - nowTime) / 1000 / 60;//分钟数差值

            String msg_link;//直播间链接地址
            if(chuanjiang_mode==0){
                msg_link = weburl+"/web/livepage/"+ course_id+"/"+chuanjiang_room_id+"/"+plan_id;//本站直播间链接地址
            }else{
                msg_link = chuanjiang_room_link;//外部直播间链接地址
            }
            String time = new SimpleDateFormat("HH:mm").format(start_time);
            //获取串讲课所在班级下所有的学员，以及有权限的微课学员
            Set<String> usrIds = new HashSet<>();
            params.addValue("grade_id", grade_id);
            sql = "select oa.id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.grade_id=:grade_id";
            List<Map<String, Object>> users = this.getNamedParameterJdbcTemplate().queryForList(sql, params);
            usrIds.addAll(users.stream().map(user -> (String) user.get("user_id")).collect(Collectors.toList()));
            String[] mCourseIds = micro_course_ids.split(",");
            for (String mCourseId : mCourseIds) {
                params.addValue("course_id",mCourseId);
                sql = "select oa.id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.course_id=:course_id";
                List<Map<String,Object>> userMs = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
                usrIds.addAll(userMs.stream().map(userM -> (String) userM.get("user_id")).collect(Collectors.toList()));
            }

            //串讲课开课前半个小时给班级所有学员发送开课通知,并且是没有发送消息公告的的串讲课
            int count = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject("select count(1) from oe_message where plan_id=?",Integer.class,plan_id);//串讲课发送公告数
            if (difference >= 30 && difference < 35 && count == 0) {
                for (String usrId : usrIds) {
                    String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                    String message = "您的串讲课于"+time+"开始~<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">点击进入直播教室>></a>";
                    messageService.addMessage(msg_id, usrId, 1, message,1,plan_id);
                }
            }
        }
        /** 2，查询所有结束的串讲课，将串讲消息下线 */
        sql = "select ol.id,ol.grade_id,ol.chuanjiang_start_time,ol.chuanjiang_end_time from oe_plan ol LEFT JOIN oe_grade og on ol.grade_id=og.id " +
                "where chuanjiang_has =1 and ol.chuanjiang_end_time<NOW() and og.is_delete=0";
        List<Map<String,Object>> endCourses = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
        for (Map<String, Object> course : endCourses) {
            String plan_id = (String) course.get("id");
            //获取串讲课所在班级下所有的学生
            params.addValue("planId", plan_id);
            sql = " update  oe_message set is_online=0  where is_online=1 and plan_id=:planId";
            this.getNamedParameterJdbcTemplate().update(sql, params);
        }
    }

    /**
     * 串讲消息:
     * 串讲课提前两小时发送消息
     * 每5分钟执行
     * cron = "0 0/5 * * * ?"
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void dealCJMessageTask(){
        /** 查询所有串讲课开始前未发送消息的，发送消息 */
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = "select ol.id,ol.grade_id,ol.chuanjiang_start_time,ol.chuanjiang_mode,ol.chuanjiang_room_id,ol.chuanjiang_room_link,ol.chuanjiang_name,og.course_id,og.`name` as gradeName,ol.micro_course_ids,ol.chuanjiang_is_send " +
                "from oe_plan ol LEFT JOIN oe_grade og on ol.grade_id=og.id where chuanjiang_has =1 and ol.chuanjiang_start_time>NOW() and og.is_delete=0";
        List<Map<String,Object>> courses = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
        for (Map<String, Object> course : courses) {
            Integer courseId = Integer.parseInt(course.get("course_id").toString());
            String plan_id = (String) course.get("id");
            String gradeName = course.get("gradeName").toString();
            Integer grade_id = Integer.parseInt(course.get("grade_id").toString());
            Integer chuanjiang_mode = Integer.parseInt(course.get("chuanjiang_mode").toString());
            String chuanjiang_name = (String) course.get("chuanjiang_name");
            String chuanjiang_room_id = (String) course.get("chuanjiang_room_id");
            String chuanjiang_room_link = (String) course.get("chuanjiang_room_link");
            String micro_course_ids = (String) course.get("micro_course_ids");
            boolean chuanjiang_is_send = (boolean) course.get("chuanjiang_is_send");
            Date start_time = (Date) course.get("chuanjiang_start_time");
            //当前时间
            Calendar curr = Calendar.getInstance();
            curr.setTime(new Date());
            long nowTime = curr.getTimeInMillis();
            //串讲课开始的时间
            Calendar curr1 = Calendar.getInstance();
            curr1.setTime(start_time);
            long startTIme = curr1.getTimeInMillis();
            long difference = (startTIme - nowTime) / 1000 / 60;//分钟数差值
            //						当前时间			减去			开课时间										如果在120分钟以内  就发送提醒
            if(!chuanjiang_is_send && (difference <= 120)) {//发送消息提醒  新增 且 在两小时内提醒
                String msg_link;
                String msg_link2 = weburl + "/web/html/CourseDetailZhiBo.html?courseId=" + courseId;

                if (chuanjiang_mode == 0) {//本站
                    msg_link = weburl + "/web/livepage/" + courseId + "/" + chuanjiang_room_id + "/" + plan_id;//直播间链接地址
                } else {//外部
                    msg_link = chuanjiang_room_link;
                }

                //拼出消息
                String time = new SimpleDateFormat("MM月dd日 HH:mm").format(start_time);
                String message = "您的班级<a href=\"javascript:void(0)\" onclick=\"on_click_msg('msg_id','" + msg_link2 + "');\">" + gradeName + "</a>新增一门串讲课，名称为《" + chuanjiang_name + "》," + time + "开课,<a href=\"javascript:void(0)\" onclick=\"on_click_msg('msg_id','" + msg_link + "');\">点击进入直播教室>></a>";
                params = new MapSqlParameterSource();

                //发送给职业课用户
                sql = "insert into oe_message (id,context,user_id,type,create_time,status,is_online) " +
                        " select a.id,replace(:context,'msg_id',a.id),a.user_id,1,now(),1,0 from(select REPLACE(UUID(),'-','') id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.grade_id=:gradeId ) a";

                params.addValue("context", message);
                params.addValue("gradeId", grade_id);

                this.getNamedParameterJdbcTemplate().update(sql, params);

                if(micro_course_ids != null && !"".equals(micro_course_ids))
                {
                    message = "职业课&nbsp;"+gradeName+"&nbsp;新增一门串讲课，名称为《"+chuanjiang_name+"》,"+time+"开课,<a href=\"javascript:void(0)\" onclick=\"on_click_msg('msg_id','" + msg_link + "');\">点击进入直播教室>></a>";
                    //再发送给包含的微课用户
                    sql = "insert into oe_message (id,context,user_id,type,create_time,status,is_online) " +
                            " select max(a.id),replace(:context,'msg_id',max(a.id)),a.user_id,1,now(),1,0 from(select REPLACE(UUID(),'-','') id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.grade_id<>:gradeId and arg.course_id in("+micro_course_ids+")) a"+
                            " where not exists(select 1 from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.grade_id=:gradeId and oa.user_id = a.user_id)" +
                            "group by a.user_id";
                    params.addValue("context", message);
                    this.getNamedParameterJdbcTemplate().update(sql,params);
                }
                //设置为已发送
                sql ="update oe_plan t set " +
                        "	t.chuanjiang_is_send = 1 " +
                        " where t.id = ? ";
                this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{plan_id});
            }
        }
    }

}
