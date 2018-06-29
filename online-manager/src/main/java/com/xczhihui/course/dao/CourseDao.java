package com.xczhihui.course.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.CourseVo;

/**
 * 云课堂课程管理DAO
 *
 * @author yxd
 */
@Repository("courseDao")
public class CourseDao extends HibernateDao<Course> {
    public Page<CourseVo> findCloudClassCoursePage(CourseVo courseVo,
                                                   int pageNumber, int pageSize) {
              Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "SELECT \n"
                        + "  oc.id AS id,\n"
                        + "  oc.grade_name AS courseName,\n"
                        + "  oc.direct_id directId,\n"
                        + "  oc.class_template AS classTemplate,\n"
                        + "  om.name AS xMenuName,\n"
                        + "  st.name AS scoreTypeName,\n"
                        + "  oc.multimedia_type multimediaType,\n"
                        + "  oc.address,\n"
                        + "  IF(ISNULL(oc.`course_pwd`), 0, 1) coursePwd,\n"
                        + "  tm.name AS teachMethodName,\n"
                        + "  oc.course_length AS courseLength,\n"
                        + "  oc.learnd_count AS learndCount,\n"
                        + "  oc.city AS realCitys,\n"
                        + "  oc.create_time AS createTime,\n"
                        + "  oc.status AS STATUS,\n"
                        + "  oc.is_free AS isFree, \n"
                        + "  oc.smallimg_path AS smallingPath, \n"
                        +
                        "  oc.current_price*10 AS currentPrice,\n"
                        + "  oc.description AS description,\n"
                        + "  oc.menu_id AS menuId,\n"
                        + "  oc.course_type_id AS courseTypeId,\n"
                        + "  oc.courseType AS courseType,\n"
                        + "  COUNT(og.id) AS countGradeNum,\n"
                        + " oc.recommend_sort recommendSort,"
                        + "  oc.release_time as releaseTime,\n"
                        + "  oc.start_time as startTime,\n"
                        + "  oc.end_time as endTime,\n"
                        + "  oc.course_type AS serviceType,\n"
                        + "  oc.user_lecturer_id AS userLecturerId,\n"
                        + "  ca.`name` lecturerName,\n"
                        + "  oc.`lecturer`,\n"
                        + "  oc.`collection`,\n"
                        + "  oc.sort_update_time as sortUpdateTime "
                        +
                        "FROM\n"
                        + "  oe_course oc \n"
                        + "  LEFT JOIN oe_menu om \n"
                        + "    ON om.id = oc.menu_id \n"
                        + "  LEFT JOIN score_type st \n"
                        + "    ON st.id = oc.course_type_id \n"
                        + "  LEFT JOIN teach_method tm \n"
                        + "    ON tm.id = oc.courseType \n"
                        + "  LEFT JOIN oe_grade og \n"
                        + "    ON og.course_id = oc.id \n"
                        + "  LEFT JOIN oe_user ou\n"
                        + "    ON ou.id=oc.user_lecturer_id "
                        + "  LEFT JOIN course_anchor ca  "
                        + "  ON oc.user_lecturer_id = ca.user_id where oc.is_delete = 0 ");
        if (courseVo.getCourseName() != null) {
            paramMap.put("courseName", "%" + courseVo.getCourseName() + "%");
            sql.append("and oc.grade_name like :courseName ");
        }
        if (courseVo.getMenuId() != null) {
            paramMap.put("menuId", courseVo.getMenuId());
            sql.append("and oc.menu_id = :menuId ");
        }
        if (courseVo.getCourseTypeId() != null) {
            paramMap.put("courseTypeId", courseVo.getCourseTypeId());
            sql.append("and oc.course_type_id = :courseTypeId ");
        }
        if (courseVo.getCourseType() != null) {
            paramMap.put("courseType", courseVo.getCourseType());
            sql.append("and oc.courseType = :courseType ");
        }
        if (courseVo.getServiceType() != null) {
            paramMap.put("serviceType", courseVo.getServiceType());
            sql.append("and oc.course_type = :serviceType ");
        }
        if (courseVo.getStatus() != null) {
            paramMap.put("status", courseVo.getStatus());
            sql.append("and oc.status = :status ");
        }
        if (courseVo.getType() != null) {
            paramMap.put("type", courseVo.getType());
            sql.append("and om.type = :type ");
        }
        if (courseVo.getRealCitys() != null) {
            paramMap.put("city", courseVo.getRealCitys());
            sql.append("and oc.city = :city ");
        }

        if (courseVo.getId() > 0) {
            paramMap.put("courseId", courseVo.getId());
            sql.append("and oc.id <> :courseId ");
        }
        if (courseVo.getOnlineCourse() == 0) {
            sql.append(" AND oc.`type` = 2");
        } else {
            sql.append(" AND oc.`type` = 3");
        }
        if (courseVo.getMultimediaType() != null) {
            paramMap.put("multimediaType", courseVo.getMultimediaType());
            sql.append(" and oc.multimedia_type = :multimediaType ");
        }

        if (courseVo.getOnlineCourse() == 0) {
            sql.append(" group by oc.id  order by oc.status desc,recommendSort desc,oc.release_time desc");
        } else {
            sql.append(" group by oc.id  order by oc.status desc,recommendSort desc,oc.start_time desc");
        }

        Page<CourseVo> courseVos = this.findPageBySQL(sql.toString(), paramMap,
                CourseVo.class, pageNumber, pageSize);
        for (CourseVo entityVo : courseVos.getItems()) {
            List<ApplyGradeCourse> temps = this.findEntitiesByProperty(
                    ApplyGradeCourse.class, "courseId", entityVo.getId());
            entityVo.setActCount(temps.size());
        }
        return courseVos;
    }

    public Page<CourseVo> findCloudClassCourseRecPage(CourseVo courseVo,
                                                      int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "SELECT oc.id as id ,oc.grade_name as courseName, oc.class_template as classTemplate, om.name as xMenuName,st.name as scoreTypeName,oc.smallimg_path smallimgPath,oc.lecturer,"
                        + "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,oc.multimedia_type as multimediaType,oc.recommend_sort as recommendSort,"
                        + " if(oc.live_status = 2,if(DATE_SUB(now(),INTERVAL 30 MINUTE)>=oc.start_time,6,if(  "
                        + "			    DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time,"
                        + "    4,if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status))),oc.live_status) "
                        + "			     AS liveStatus, "

                        + "oc.create_time as createTime,oc.collection as collection,oc.start_time as startTime,oc.release_time,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,ou.name  as lecturerName,oc.city as realCitys,"
                        + "oc.current_price*10 as currentPrice,oc.description as description,oc.menu_id as menuId,oc.course_type_id as courseTypeId,oc.sort_update_time as sortUpdateTime,"
                        + "oc.courseType as courseType,count(og.id) as countGradeNum,oc.is_recommend,oc.rec_img_path,oc.course_type as serviceType FROM oe_course oc "
                        + "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st ON st.id = oc.course_type_id "
                        + "LEFT JOIN teach_method tm ON tm.id = oc.courseType left join oe_user as ou on oc.user_lecturer_id = ou.id "
                        + "left join oe_grade og on og.course_id = oc.id where oc.is_delete = 0  ");

        /**
         * 这里不仅仅是课程进行推荐 并且课程的推荐位需要按照：视频、音频进行排序 对线下课进行排序 对直播进行排序：预告、直播中、已经结束的
         * online_course = 1 线下课 type 不等于null 就是 直播 multimedia_type = 1 视频 = 2音频
         */

        Integer offLine = courseVo.getOnlineCourse();
        Integer courseType = courseVo.getType();
        Integer multimediaType = courseVo.getMultimediaType();
        Integer liveStatus = courseVo.getLiveStatus();// 直播状态1.直播中，2预告，3直播结束

        String city = courseVo.getRealCitys();//

        if (offLine != null && !"".equals(offLine) && offLine.equals(1)) {
            sql.append(" and oc.type = 3 ");
            if (city != null && !"".equals(city)) {
                sql.append(" and oc.city ='" + city + "'");
            }
        } else if (courseType != null && !"".equals(courseType)) {
            sql.append(" and oc.type = 1 ");
            if (liveStatus != null && !"".equals(offLine)) {
                sql.append(" and oc.live_status = " + liveStatus);
            }
        } else if (multimediaType != null && !"".equals(multimediaType)
                && !multimediaType.equals(0)) {
            sql.append(" and oc.multimedia_type = " + multimediaType);
        }

        sql.append(" group by oc.id  order by oc.status desc,oc.recommend_sort desc,oc.release_time desc ");
        return this.findPageBySQL(sql.toString(), paramMap, CourseVo.class,
                pageNumber, pageSize);

    }

    /**
     * 查询出所有的授课方式
     *
     * @return
     */
    public List<TeachMethod> getTeachMethod() {
        String sql = "SELECT * FROM teach_method WHERE status=1 AND is_delete=0";
        Map<String, Object> params = new HashMap<String, Object>();
        return this.getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(TeachMethod.class));
    }

    /**
     * 查询出课程
     *
     * @return
     */
    public List<Course> getCourse() {
        String sql = "SELECT * FROM oe_course WHERE is_delete = 0  AND status=1";
        Map<String, Object> params = new HashMap<String, Object>();
        return this.getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Course.class));
    }

    public void deleteById(Integer id) {
        String sql = "UPDATE oe_course SET is_delete = 1 WHERE  id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        this.getNamedParameterJdbcTemplate().update(sql, params);
    }

    public List<ScoreType> getScoreType() {
        // TODO Auto-generated method stub
        String sql = "SELECT * FROM score_type WHERE is_delete = 0  AND status=1 AND id<>0";
        Map<String, Object> params = new HashMap<String, Object>();
        return this.getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(ScoreType.class));
    }

    public List<Grade> getGrade(String gradeIds) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(
                "select * from oe_grade where is_delete = 0  and status=1 ");
        Map<String, Object> params = new HashMap<String, Object>();
        if (gradeIds != null) {
            sql.append(" and course_id in(" + gradeIds + ")");
            // params.put("courseId", gradeIds);
        }
        // List<Grade>
        // vos=this.getNamedParameterJdbcTemplate().query(sql.toString(),params,BeanPropertyRowMapper.newInstance(Grade.class));
        return this.getNamedParameterJdbcTemplate().query(sql.toString(),
                params, BeanPropertyRowMapper.newInstance(Grade.class));
    }

    public List<Course> getOpenCourseToSend() {
        String sql = "SELECT id ,course_type,is_sent isSent, grade_name AS courseName,start_time AS startTime,end_time AS endTime "
                + "FROM oe_course oc WHERE oc.is_sent = FALSE AND oc.is_delete =FALSE AND oc.type = 1 AND oc.status = 1 AND oc.start_time > NOW()";
        Map<String, Object> params = new HashMap<String, Object>();
        return this.getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Course.class));
    }

    public Course getCourseById(Integer courseId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
        dc.add(Restrictions.eq("id", courseId));
        Course course = this.findEntity(dc);
        return course;
    }

    public List<Course> getCourseByCollectionId(Integer id) {
        String sql = "SELECT \n" + "  oc.`grade_name`,\n" + "  oc.`id`,\n"
                + "  oc.`lecturer`,\n"
                + "  oc.`current_price`*10 AS current_price " + "FROM\n"
                + "  `oe_course` oc \n" + "  JOIN `collection_course` cc \n"
                + "    ON oc.id = cc.`course_id` \n"
                + "WHERE cc.`collection_id` = :cid \n"
                + "AND oc.`is_delete` = 0";
        // "AND oc.`is_delete` = 0\n" +
        // "ORDER BY oc.`collection_course_sort` ASC";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cid", id);
        return this.getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Course.class));
    }

    public List<OnlineUser> getUsersByCourseId(Integer courseId, Date lastTime) {
        String sql = "SELECT \n" +
                "  ou.*,\n" +
                "  argc.`course_id` \n" +
                "FROM\n" +
                "  `apply_r_grade_course` argc \n" +
                "  JOIN `oe_user` ou \n" +
                "    ON argc.`user_id` = ou.`id` \n" +
                "WHERE argc.`course_id` = :courseId and (:lastTime is null OR argc.create_time > :lastTime) \n" +
                "GROUP BY ou.id ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("courseId", courseId);
        params.put("lastTime", lastTime);
        return this.getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(OnlineUser.class));
    }

    public List<CourseVo> listSimpleCourse(int offset, int size) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("offset", offset).addValue("size", size);
        String sql = "select id, grade_name as courseName" +
                " from oe_course where status = 1 and is_delete = 0 order by create_time desc limit :offset,:size;";
        return this.getNamedParameterJdbcTemplate().queryForList(sql, mapSqlParameterSource, CourseVo.class);
    }
}
