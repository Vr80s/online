package com.xczhihui.course.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.CourseVo;

@Repository("publicCourseDao")
public class PublicCourseDao extends HibernateDao<Course> {

    public Page<CourseVo> findCloudClassCoursePage(CourseVo courseVo,
                                                   int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "select c.id,c.current_price*10 currentPrice,c.`lecturer`,c.`course_length`,"
                        + "c.`start_time` as startTime,c.end_time as endTime,c.smallimg_path smallimgPath,"
                        + " c.default_student_count AS defaultStudentCount, "
                        + " if(c.live_status = 2,if(DATE_SUB(now(),INTERVAL 30 MINUTE)>=c.start_time,6,if(  "
                        + "			    DATE_ADD(now(),INTERVAL 10 MINUTE)>=c.start_time and now() < c.start_time,"
                        + "    4,if(DATE_ADD(now(),INTERVAL 2 HOUR)>=c.start_time and now() < c.start_time,5,c.live_status))),c.live_status) "
                        + "			     AS liveStatus, "
                        + " ABS(timestampdiff(second,current_timestamp,c.start_time)) as recent,"
                        + " c.recommend_sort recommendSort, "
                        + "c.grade_name as courseName ,c.sort_update_time as sortUpdateTime,ou.login_name as lecturerName ,m.name as menuName,c.`course_pwd` coursePwd "
                        + ",c.live_source as liveSource,c.release_time as releaseTime,c.status as status,c.direct_id as directId  \n"
                        + " from oe_course c  LEFT JOIN\n"
                        + "  oe_menu m ON c.menu_id = m.id \n"
                        + "  LEFT JOIN \n"
                        + "  oe_user ou ON c.user_lecturer_id = ou.id  "
                        + "  LEFT JOIN course_anchor ca  "
                        + "  ON c.user_lecturer_id = ca.user_id  "
                        + "where c.is_delete = 0 \n" + "  AND c.type = 1  ");
        if (courseVo.getCourseName() != null) {
            paramMap.put("courseName", "%" + courseVo.getCourseName() + "%");
            sql.append(" and c.grade_name like :courseName ");
        }
        if (courseVo.getLecturerName() != null) {
            paramMap.put("lecturerName", "%" + courseVo.getLecturerName() + "%");
            sql.append(" and ( c.`lecturer` like :lecturerName or ou.login_name like :lecturerName )");
        }
        if (courseVo.getMenuId() != null) {
            paramMap.put("menuId", courseVo.getMenuId());
            sql.append(" and c.menu_id = :menuId ");
        }
        if (courseVo.getStatus() != null) {
            paramMap.put("status", courseVo.getStatus());
            sql.append(" and c.status = :status ");
        }
        if (courseVo.getLiveStatus() != null) {
            paramMap.put("liveStatus", courseVo.getLiveStatus());
            sql.append(" and c.live_status = :liveStatus ");
        }
        sql.append(" order by c.status desc,recommendSort desc,recent ");

        return this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
    }

    public void updateCourseDirectId(Course course) {
        String sql = "update oe_course set direct_id = :direct_id where  id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", course.getId());
        params.put("direct_id", course.getDirectId());
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

}
