package com.xczhihui.bxg.online.web.service.impl;


import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.LecturerDao;
import com.xczhihui.bxg.online.web.service.LecturerService;
import com.xczhihui.bxg.online.web.vo.LecturVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   LecturerServiceImpl:讲师业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class LecturerServiceImpl extends OnlineBaseServiceImpl implements LecturerService {

    @Autowired
    private LecturerDao lecturerDao;

    /**
     * 根据教师编号，查找教师实例
     * @param courseId 课程的ID编号
     * @return  教师对象
     */
    @Override
    public List<LecturVo> findLecturerById(Integer courseId) {
        Integer pageNumber = 1;
        Integer pageSize = 3;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        Page<LecturVo> page=null;
        if(courseId != null){
             String sql =" select le.`name` from  course_r_lecturer grl join oe_lecturer le where grl.lecturer_id = le.id  and grl.is_delete = 0" +
                         " and  grl.course_id= :courseId  and le.role_type=1  and le.is_delete=0  order by  le.create_time  limit 3 ";
             paramMap.put("courseId",courseId);
             page= dao.findPageBySQL(sql,paramMap, LecturVo.class,pageNumber,pageSize);
        }
        return page.getItems();
    }

    @Override
    public List<LecturVo> listByCourseId(String courseId) {
        return lecturerDao.listByCourseId(courseId);
    }
}
