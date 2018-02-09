package com.xczhihui.bxg.online.web.base.common;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.CourseApplyResource;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.OrderDao;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.MessageService;
import com.xczhihui.bxg.online.web.vo.OrderVo;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
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
    private ICourseApplyService courseApplyService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CourseService courseService;
    
    @Value("${online.web.url}")
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
//    	System.out.println("查询所有未产生佣金的已完成订单");
    }

    /**
     * Description：每隔半小时执行一次
     * 处理用户资源上传后时长更新
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 12:17 2018/2/2 0002
     **/
    @Scheduled(cron = "0 0/30 * * * ?")
    public void dealCourseApplyResourceTask(){
        courseApplyService.updateCourseApplyResource();
    }


    /**
     * Description：每隔半小时执行一次
     * 将超出预定时间半小时未直播的课程下架
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 12:17 2018/2/2 0002
     **/
    @Scheduled(cron = "0 0/10 * * * ?")
    public void dealConstrueTask(){
        courseService.updateCourseException();
    }




}
