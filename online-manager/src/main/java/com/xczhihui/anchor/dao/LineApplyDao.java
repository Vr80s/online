package com.xczhihui.anchor.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.LineCourseApplyStudentVO;

/**
 * @author hejiwei
 */
@Repository
public class LineApplyDao extends HibernateDao<LineCourseApplyStudentVO> {

    public Page<LineCourseApplyStudentVO> findCourseAnchorPage(String courseName, String anchorName,
                                                               int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<>(2);
        StringBuilder builder = new StringBuilder("SELECT la.real_name as realName, la.sex, la.mobile, la.wechat_no as wechatNo, la.learned," +
                "                 ac.create_time as createTime, la.id, oc.grade_name as courseName, ou.name as anchorName" +
                "                 FROM oe_line_apply la , apply_r_grade_course ac, oe_course oc,  oe_user ou " +
                "                 WHERE 1=1 and la.`anchor_id` = ou.`id` and la.`course_id` = ac.`course_id` and la.`user_id` = ac.`user_id` and ac.`course_id` = oc.id ");
        if (StringUtils.isNotBlank(courseName)) {
            paramMap.put("courseName", "%" + courseName + "%");
            builder.append(" and oc.grade_name like :courseName");
        }
        if (StringUtils.isNotBlank(anchorName)) {
            paramMap.put("anchorName", "%" + anchorName + "%");
            builder.append(" and ou.name like :anchorName");
        }
        builder.append(" ORDER BY la.create_time desc");
        return this.findPageBySQL(builder.toString(),
                paramMap, LineCourseApplyStudentVO.class, pageNumber, pageSize);
    }

    public void updateLearned(String id, boolean learned) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id).addValue("learned", learned);
        String sql = "update oe_line_apply set learned = :learned where id = :id";
        this.getNamedParameterJdbcTemplate().update(sql, mapSqlParameterSource);
    }
}
