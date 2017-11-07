package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/8/30.
 */

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 学员报名信息底层类
 *
 * @author 康荣彩
 * @create 2016-08-30 19:21
 */
@Repository
public class ApplyDao extends SimpleHibernateDao {

    @Autowired
    private ApplyGradeCourseDao  applyGradeCourseDao;
    /**
     * 根据当前用户id，查找对应的学员信息
     * @return
     */
    public Apply findByUserId(String userId){
        return this.findByHQLOne("from Apply where isDelete = 0 and  userId=?",userId);
    }


    public   String   createStudentNumber(Integer courseId,Integer gradeId){
        String number="";
        //先查学员表中学号最大的学员信息
        ApplyGradeCourse applyGradeCourse=applyGradeCourseDao.findUser(courseId,gradeId);
        //如果有学员，学号就在该学员的学号累加
        if(applyGradeCourse != null){
            String newNumber=String.valueOf(Integer.valueOf(applyGradeCourse.getStudentNumber())+1) ;
            switch (newNumber.length()){
                case 1:
                    number="00"+newNumber;
                    break;
                case 2:
                    number="0"+newNumber;
                    break;
                default:
                    number=newNumber;
            }
        }else{
            number="001";
        }
        return  number;
    }


}
