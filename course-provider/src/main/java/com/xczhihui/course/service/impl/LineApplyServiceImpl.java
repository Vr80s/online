package com.xczhihui.course.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.course.exception.LineApplyException;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.LineApplyMapper;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.model.LineApply;
import com.xczhihui.course.service.ILineApplyService;
import com.xczhihui.course.vo.LineCourseApplyStudentVO;

/**
 * @author hejiwei
 */
@Service
public class LineApplyServiceImpl implements ILineApplyService {

    @Autowired
    private LineApplyMapper lineApplyMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Map<String, Object> findLineApplyByUserId(String userId) {
        return lineApplyMapper.newestApplyInfoByUserId(userId);
    }

    @Override
    public boolean submitted(String userId, Integer courseId) {
        return lineApplyMapper.findLineApplyByUserIdAndCourseId(userId, courseId) != null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    @Lock(lockName = "addOrUpdateLineApplyLock", waitTime = 5, effectiveTime = 8)
    public void saveOrUpdate(String lockId, LineApply lineApply) {
        LineApply lineApplyOld = lineApplyMapper.findLineApplyByUserIdAndCourseId(lineApply.getUserId(), lineApply.getCourseId());
        if (lineApplyOld == null) {
            Course course = courseMapper.selectById(lineApply.getCourseId());
            if (course == null) {
                throw new LineApplyException("课程找不到");
            }
            if (course.getType() != Course.COURSE_TYPE_OFFLINE) {
                throw new LineApplyException("课程不是线下课");
            }
            String id = CodeUtil.getRandomUUID();
            lineApply.setId(id);
            lineApply.setAnchorId(course.getUserLecturerId());
            lineApplyMapper.insert(lineApply);
        }
    }

    @Override
    public Page<LineCourseApplyStudentVO> listLineApplyStudent(Page<LineCourseApplyStudentVO> page, Integer courseId, String anchorId) {
        page.setRecords(lineApplyMapper.listLineApplyStudent(page, courseId, anchorId));
        return page;
    }

    @Override
    public boolean updateLearned(String id, boolean learned, String anchorId) {
        LineApply lineApply = lineApplyMapper.selectById(id);
        if (lineApply != null) {
            return lineApplyMapper.updateLearned(id, learned, anchorId) > 0;
        }
        return false;
    }
}
