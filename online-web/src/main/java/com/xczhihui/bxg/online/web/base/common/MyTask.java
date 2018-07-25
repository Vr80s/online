package com.xczhihui.bxg.online.web.base.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xczhihui.bxg.online.web.dao.OrderDao;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.medical.anchor.service.ICourseApplyService;

/**
 * Created by admin on 2016/11/11.
 */
@Component
public class MyTask extends SimpleHibernateDao {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ICourseApplyService courseApplyService;

    @Autowired
    private CourseService courseService;

    @Value("${web.url}")
    private String weburl;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 每半个小时执行一次
     * 将数据库超过24小时未支付的订单关闭
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateOrderStatus() {
        //将未支付的订单超过24小时的置为失效
        orderDao.updateStatus();
    }

    /**
     * Description：每隔半小时执行一次
     * 处理用户资源上传后时长更新
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 12:17 2018/2/2 0002
     **/
    /*@Scheduled(cron = "0 0/30 * * * ?")
    public void dealCourseApplyResourceTask() {
        courseApplyService.updateCourseApplyResource();
    }*/


    /**
     * Description：每隔半小时执行一次
     * 将超出预定时间半小时未直播的课程下架
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 12:17 2018/2/2 0002
     **/
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateCourseExceptionTask() {
        courseService.updateCourseException();
    }


}
