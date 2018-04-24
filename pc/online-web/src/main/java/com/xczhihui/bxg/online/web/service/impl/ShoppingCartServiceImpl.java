package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.ShoppingCartDao;
import com.xczhihui.bxg.online.web.dao.VideoDao;
import com.xczhihui.bxg.online.web.service.ShoppingCartService;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 购物车模块业务实现类
 * @Author Fudong.Sun【】
 * @Date 2017/2/20 16:12
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

    @Autowired
    private ShoppingCartDao dao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private VideoDao videoDao;

    @Override
    public Integer findCourseNum(String userId) {
        return dao.findCourseNum(userId);
    }

    @Override
    public ResponseObject addCart(BxgUser user, Integer courseId) {
        CourseVo course = courseDao.findCourseOrderById(courseId);
        if(course.getType()== CourseForm.VOD.getCode() && (course.getDirectId()==null||"".equals(course.getDirectId().trim()))&&!course.getCollection()){
            throw new RuntimeException(String.format("视频正在来的路上，请稍后购买"));
        }
        //课程是否已经加入购物车
        boolean existCourse = dao.ifExistsCourse(user.getId(),courseId);
        //课程是否已经购买了
        boolean ifBuy = dao.ifHasBuyCourse(user.getId(),courseId);
        boolean ifZero = dao.ifZeroCourse(courseId);
        if(existCourse){
            return ResponseObject.newSuccessResponseObject("该课程已在购物车中了，无需重复加入");
        }else if(ifBuy){
            return ResponseObject.newSuccessResponseObject("您已经购买过该课程");
        }else if(ifZero) {
            throw new RuntimeException("0元课程无需加入购物车，请直接报名");
        }else{
            dao.addCart(user.getId(), courseId);
            return ResponseObject.newSuccessResponseObject("已成功添加至购物车");
        }
    }

    @Override
    public List<Map<String, Object>> lists(String userId) {
        List<Map<String, Object>> courses = dao.lists(userId);
        for (Map<String, Object> course : courses) {
            Calendar calendar = Calendar.getInstance();
            Date createTime = (Date) course.get("create_time");
            calendar.setTime(createTime);
            calendar.add(Calendar.YEAR, 1);
            Date newTime = calendar.getTime();
            String formTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newTime);
            course.put("courseValidTime","即日起至"+formTime);
            course.put("originalCost",new java.text.DecimalFormat("0.00").format(course.get("originalCost")));
            course.put("currentPrice",new java.text.DecimalFormat("0.00").format(course.get("currentPrice")));
        }
        return courses;
    }

    @Override
    public Integer delete(String userId, Set<String> ids) {
        return dao.delete(userId,ids);
    }

    /**
     * 课程批量加入购物车
     * @param userId 购买课程用户id
     * @param courseIds 加入购物车课程id数组
     * @param rule_id 活动id号
     */
    @Override
    public ResponseObject addCourseToCart(String userId, String[] courseIds, String rule_id) {
        List<String> ids = Arrays.asList(courseIds);
        Iterator<String> iter = ids.iterator();
        while (iter.hasNext()) {
           String id = iter.next();
           if (dao.ifHasBuyCourse(userId,Integer.parseInt(id))){
               iter.remove();
           }
        }
        dao.addCourseToCart(userId,ids,rule_id);
        return ResponseObject.newSuccessResponseObject("操作成功!");
    }
}
