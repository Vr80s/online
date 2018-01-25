package com.xczhihui.bxg.online.manager.anchor.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.anchor.dao.AnchorDao;
import com.xczhihui.bxg.online.manager.anchor.service.AnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *   CourseServiceImpl:课程业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class CourseAnchorServiceImpl extends OnlineBaseServiceImpl implements AnchorService {

    @Autowired
    private AnchorDao anchorDao;

    @Override
	public Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor, int pageNumber, int pageSize) {
    	Page<CourseAnchor> page = anchorDao.findCourseAnchorPage(courseAnchor, pageNumber, pageSize);
    	return page;
	}

}
