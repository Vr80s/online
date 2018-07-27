package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczhihui.course.exception.CourseException;

@Service
public class OnlineCourseServiceImpl extends BasicSimpleDao implements OnlineCourseService {

    /**
     * 修改直播课程的浏览数
     * @param courseId 鲜花数
     * @throws SQLException
     */
    @Override
    public void updateBrowseSum(Integer courseId) throws SQLException {
        String sql = " update oe_course  set pv = (pv + 1)  where id = ? and is_delete = 0 and status=1 ";
        super.update(JdbcUtil.getCurrentConnection(), sql, courseId);
    }

    @Override
    public void updateLiveSourceType(Integer courseId) throws SQLException {
        String sql = " update oe_course  set live_source_type = 1  where id = ? ";
        
        super.update(JdbcUtil.getCurrentConnection(), sql, courseId);
    }
    
    @Override
    public void  updateLiveType(Integer courseId,Integer liveType) throws SQLException {
        String sql = " update oe_course  set live_type = ?  where  live_status = 1 and id = ?  ";
        Integer count = super.update(JdbcUtil.getCurrentConnection(), sql, new Object[]{liveType,courseId});
        if(count<=0) {
            throw new  CourseException("更改失败");
        }
    }

}
