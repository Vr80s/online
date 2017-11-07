package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.StudentStory;
import com.xczhihui.bxg.online.web.dao.ScoreTypeDao;
import com.xczhihui.bxg.online.web.dao.StudentStoryDao;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.MenuService;
import com.xczhihui.bxg.online.web.service.StudentStoryService;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.bxg.online.web.vo.StudentStoryVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学员故事
 * @author majian
 * @date 2016-8-18 11:29:58
 */
@Service
public class StudentStoryServiceImpl extends OnlineBaseServiceImpl implements StudentStoryService {

    @Autowired
    private StudentStoryDao studentStoryDao;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScoreTypeDao scoreTypeDao;

    @Override
    public List<StudentStoryVo> findListByIndex() throws InvocationTargetException, IllegalAccessException {
        List<StudentStoryVo> studentStoryVos=new ArrayList<StudentStoryVo>();
        List<StudentStory> studentStories=studentStoryDao.findListByIndex();
        if(studentStories!=null&&studentStories.size()>0){
            for(StudentStory studentStory:studentStories){
                StudentStoryVo studentStoryVo=new StudentStoryVo();
                if(studentStory!=null) {
                    BeanUtils.copyProperties(studentStoryVo, studentStory);
                    studentStoryVo.setSalary(String.valueOf(studentStoryVo.getSalary()) + "k");
                    Menu menu=menuService.findById(studentStory.getMenuId());
                    studentStoryVo.setMenu(menu != null ? menu.getName() : "");
                    ScoreType sourceType=scoreTypeDao.findById(studentStory.getCourseTypeId());
                    studentStoryVo.setCourse(sourceType != null ? sourceType.getName() : "");
                    studentStoryVos.add(studentStoryVo);
                }
            }
        }
        return studentStoryVos;
    }

    @Override
    public StudentStoryVo findById(String id) throws InvocationTargetException, IllegalAccessException {
        StudentStory entity=studentStoryDao.findOneEntitiyByProperty(StudentStory.class, "id", id);
        StudentStoryVo studentStoryVo=new StudentStoryVo();
        if(entity!=null) {
            BeanUtils.copyProperties(studentStoryVo, entity);
            studentStoryVo.setSalary(String.valueOf(studentStoryVo.getSalary()) + "k");
            Menu menu=menuService.findById(entity.getMenuId());
            studentStoryVo.setMenu(menu != null ? menu.getName() : "");
            ScoreType sourceType=scoreTypeDao.findById(entity.getCourseTypeId());
            studentStoryVo.setCourse(sourceType != null ? sourceType.getName() : "");
        }
        return studentStoryVo;
    }
}
