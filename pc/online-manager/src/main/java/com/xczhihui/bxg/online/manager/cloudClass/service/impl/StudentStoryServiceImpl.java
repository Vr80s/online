package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.StudentStory;
import com.xczhihui.bxg.online.manager.cloudClass.dao.StudentStoryDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.StudentStoryService;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;

@Service
public class StudentStoryServiceImpl extends OnlineBaseServiceImpl implements StudentStoryService {

    @Autowired
    private StudentStoryDao studentStoryDao;

    @Override
    public Page<StudentStory> findStudentStoryPage(StudentStory studentStory,
                                                   int pageNumber, int pageSize) {
        return studentStoryDao.findCloudClassCoursePage(studentStory, pageNumber, pageSize);
    }

    @Override
    public void addStudentStory(StudentStory studentStory) {
        studentStory.setDelete(false);
        studentStory.setCreateTime(new Date());
        studentStory.setCreatePerson(ManagerUserUtil.getUsername());
        studentStoryDao.save(studentStory);
    }

    @Override
    public void deletes(String[] ids) {
        for (String id : ids) {
            String hqlPre = "from StudentStory where  isDelete=0 and id = ?";
            StudentStory studentStory = dao.findByHQLOne(hqlPre, new Object[]{id});
            if (studentStory != null) {
                studentStory.setDelete(true);
                dao.update(studentStory);
            }
        }
    }

    @Override
    public StudentStory findCourseById(String id) {
        return dao.get(id, StudentStory.class);
    }

    @Override
    public void updateStudentStory(StudentStory studentStory) {
        StudentStory studentStoryOld = dao.get(studentStory.getId(), StudentStory.class);

        String p = studentStoryOld.getCreatePerson();
        Date cd = studentStoryOld.getCreateTime();

        BeanUtils.copyProperties(studentStory, studentStoryOld);
        studentStoryOld.setCreatePerson(p);
        studentStoryOld.setCreateTime(cd);
        studentStoryOld.setDelete(false);

        dao.update(studentStoryOld);

    }
}
