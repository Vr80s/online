package com.xczhihui.bxg.online.web.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.RandomUtil;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.utils.MD5Util;
import com.xczhihui.bxg.online.web.utils.PayConfigUtil;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.bxg.online.web.vo.GradeVo;
import com.xczhihui.bxg.online.web.vo.OrderVo;
import com.xczhihui.bxg.online.web.vo.WechatVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;

/**
 * 微信分销业务层对接接口类
 * @author 康荣彩
 * @create 2016-11-21 16:21
 */
@Repository
public class WechatDao  extends SimpleHibernateDao {

    @Autowired
    private UserCenterAPI userCenterAPI;
    @Autowired
    private  OrderDao  orderDao;
    @Autowired
    private  GradeDao gradeDao;

    @Autowired
    private  CourseDao courseDao;
    @Autowired
    private ApplyGradeCourseDao  applyGradeCourseDao;
    @Autowired
    private  ApplyDao applyDao;




    //数据字典
    private Map<String, String> attrs = new HashMap<String, String>();

    /**
     * 微信分销调用此接口
     * 实现：用户注册，下单，购买课程等。。
     * @param wechatVo：参数包含：手机号、课程id、商户订单号、微信订单号、金额
     * @return
     */
    public String saveUserAndBuyCourse(WechatVo wechatVo) {
        //参数加密，生成签名，进行签名对比
        String params = "courseId="+wechatVo.getCourseId()+"&mobile="+wechatVo.getMobile()+"&money="+wechatVo.getMoney() +
                        "&orderNo="+wechatVo.getOrderNo()+"&timestamp="+wechatVo.getTimestamp()+"&transactionId="+wechatVo.getTransactionId();
        params= MD5Util.MD5Encode(params, "utf-8").toUpperCase();
        //生成签名
        String sign = MD5Util.MD5Encode(params+"&KEY="+ PayConfigUtil.KEY, "utf-8").toUpperCase();
        if(wechatVo.getSign().equals(sign)){

                //先判断此订单是否存在，如果存在，就是已经购买成功，否则继续注册以及购买课程流程
                OrderVo orderVo = orderDao.findOrderByOrderNo(wechatVo.getOrderNo());
                if (orderVo != null && orderVo.getOrder_status() == 1) {
                    return "购买成功";
                }

                //初始化字典

                //查看用户中心是否存在此用户
                ItcastUser iu = userCenterAPI.getUser(wechatVo.getMobile());
                //查看本地数据库中是否存在此用户
                OnlineUser o = this.findByHQLOne("from OnlineUser where loginName=?", wechatVo.getMobile());
                /**
                 * 新注册，根据用户中心判断用户是否存在
                 */
                String cdate = DateUtil.formatDate(new Date());
                Map<String, Object> paramMap = new HashMap<>();
                String nikeName = "bxg_" + (int) (Math.random() * 90000 + 10000);
                String userId = UUID.randomUUID().toString().replace("-", "");
                if (iu == null) {
                    String mobile = wechatVo.getMobile();

                    //用户表sql
                    String userSql = String.format("insert into oe_user "
                                    + "(id,login_name,`name`,sex,mobile,create_time,is_delete,small_head_photo,is_apply) "
                                    + "values ('%s','%s','%s',%d,'%s','%s',%d,'%s',%d);",
                            userId, mobile, nikeName, 2, mobile, cdate, 0, "/web/images/defaultHeadImg.jpg", 1);
                    this.getNamedParameterJdbcTemplate().update(userSql, paramMap);

                    //默认密码
                    String password=String.valueOf((int)(Math.random() * 900000 + 100000));

                    //向用户中心注册用户
                    userCenterAPI.regist(wechatVo.getMobile(), password,nikeName, UserSex.UNKNOWN, null,
                            wechatVo.getMobile(), UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
                }

                CourseVo courseVo = courseDao.findCourseOrderById(wechatVo.getCourseId());  //查看要报名的课程是否存在
                //查看此用户是否购买过课程,如果购买了，不添加学员信息
                Apply apply = applyDao.findByUserId(userId);
                Integer gradeId = 0;
                String applyId = "";
                if (apply == null) {
                    List<GradeVo> gradeVos = gradeDao.findGradeByCourseId(wechatVo.getCourseId()); //查询当前课程下报名中的班级
                    if (courseVo != null) {
                        //判断
                        gradeId = gradeVos.size() > 0 ? gradeVos.get(0).getId() : 0;
                        String sno = applyDao.createStudentNumber(wechatVo.getCourseId(), gradeId);
                        //报名信息sql
                        applyId = UUID.randomUUID().toString().replace("-", "");
                        String applySql = String.format("insert into oe_apply "
                                        + "(id,user_id,create_time,is_delete,mobile,student_number) "
                                        + " values ('%s','%s','%s',%d,'%s','%s');",
                                applyId, userId, cdate, 0, wechatVo.getMobile(), sno);
                        this.getNamedParameterJdbcTemplate().update(applySql, paramMap);
                    }
                }

                //查看学员是否在当前班级报过名
                ApplyGradeCourse agc = applyGradeCourseDao.findInfoByID(userId, wechatVo.getCourseId(), gradeId);
                if (agc == null) {
                    //apply_r_grade_course中间表信息sql
                    Double money = Double.valueOf(wechatVo.getMoney());
                    String courseName = courseVo.getCourseName();
                    String orderNo = TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12);
                    String sno = applyDao.createStudentNumber(wechatVo.getCourseId(), gradeId);
                    Integer courseId = wechatVo.getCourseId();
                    String apply_r_grade_course_id = UUID.randomUUID().toString().replace("-", "");
                    String apply_r_grade_courseSql = String.format("insert into apply_r_grade_course "
                                    + "(id,course_id,grade_id,apply_id,is_payment,create_time,is_delete,cost,student_number) "
                                    + "values ('%s',%d,%d,'%s',%d,'%s',%d,%.2f,'%s');",
                            apply_r_grade_course_id, Integer.valueOf(courseId), Integer.valueOf(gradeId), applyId, 2, cdate, 0, money, sno);
                    this.getNamedParameterJdbcTemplate().update(apply_r_grade_courseSql, paramMap);

                    paramMap.put("gradeId", gradeId);
                    //修改当前班级学习人数
                    if(gradeId != 0){
                        String sql = " update oe_grade set student_count=student_count+1 where id=:gradeId ";
                        this.getNamedParameterJdbcTemplate().update(sql,paramMap);
                    }


                    //oe_order订单表信息sql
                    String order_id = UUID.randomUUID().toString().replace("-", "");
                    String orderSql = String.format("insert into oe_order "
                                    + "(id,create_person,order_no,course_id,course_name,actual_pay,purchaser,pay_time,order_status) "
                                    + "values ('%s','%s','%s',%d,'%s',%.2f,'%s',%s,%d); ",
                            order_id, wechatVo.getMobile(), orderNo, courseId, courseName, money, nikeName, "now()", 1);
                    this.getNamedParameterJdbcTemplate().update(orderSql, paramMap);

                    paramMap.put("courseId", wechatVo.getCourseId());
                    //1、查询当前课程下所有视频
//                    String querySql = "select id as video_id ,course_id as courseId from oe_video where course_id=:courseId and is_delete=0 and status=1 ";
//                    List<UserVideoVo> videos = this.findEntitiesByJdbc(UserVideoVo.class, querySql, paramMap);
                    //2、循环此课程下的所有视频，将视频信息插入用户视频中间表，归用户所有
//                    if (!CollectionUtils.isEmpty(videos) && videos.size() > 0) {
//                        for (UserVideoVo video : videos) {
//                            video.setId(UUID.randomUUID().toString().replaceAll("-", ""));
//                            video.setUser_id(userId);
//                            video.setCreate_person(wechatVo.getMobile());
//                            video.setCourse_id(courseId);
//                            String sql = " insert into user_r_video (id,create_person,video_id,user_id,course_id) "
//                                    + " values (:id,:create_person,:video_id,:user_id,:course_id) ";
//                            this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(video));
//                        }
//                    }

                }
        }else{
            return "参数有误!";
        }

        return "购买成功";
    }


  /*  public void initSystemVariate(){
        //查数据字典
        List<SystemVariate> lst = dao.findByHQL("select t1 from SystemVariate t1,SystemVariate t2 "
                + "where t1.parentId=t2.id and t2.name=?","message_provider");
        for (SystemVariate systemVariate : lst) {
            attrs.put(systemVariate.getName(), systemVariate.getValue());
        }
    }*/

}
