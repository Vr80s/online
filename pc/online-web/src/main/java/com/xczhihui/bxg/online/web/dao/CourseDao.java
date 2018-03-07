package com.xczhihui.bxg.online.web.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.CourseDescriptionVo;
import com.xczhihui.bxg.online.web.vo.CourseLecturVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;

/**
 * 课程底层实现类
 *
 * @author rongcai Kang
 */
@Repository
public class CourseDao extends SimpleHibernateDao {

    @Autowired
    private  VideoDao  videoDao;
    @Autowired
    private  ApplyGradeCourseDao  applyGradeCourseDao;

    //成为分享大使课程的id，购买此课程可成为分享大使
    @Value("${share.course.id:191}")
    private String shareCourseId;


    /**
     * 首页中医课堂列表
     * @param menuId      学科ID号（小类型）
     * @param couseTypeId  课程难度（绑定在小类型）
     * @param pageNumber  当前是第几页，默认1
     * @param pageSize    每页显示多少行，默认20getCourseById
     * @param orderType 排序类型 1综合 2最新 3 人气 4价格
     * @param orderBy 0 升序 1倒序
     * @param multimediaType 授课类型 1视频2音频
     * @param  isFree 是否免费 0付费 1 免费
     * @return Example 分页列表
     */
    public Page<CourseLecturVo> listZyktCourse(Integer menuType, Integer menuId, String couseTypeId,String multimediaType,String isFree,String orderType,String orderBy, Integer pageNumber, Integer pageSize) {
       // menuId = menuId==165 ? 0 : menuId;//165或0就是查全部
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize =12;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //paramMap.put("menuId", menuId);

        StringBuffer  sqlSb=new StringBuffer();
        sqlSb.append(" select cou.id,cou.type,cou.direct_id,cou.grade_name,cou.smallimg_path  as smallImgPath,floor(cou.current_price*10) current_price,cou.start_time startTime,cou.end_time endTime,cou.user_lecturer_id userLecturerId,cou.address,cou.multimedia_type multimediaType,IF(ISNULL(cou.`course_pwd`),0,1) coursePwd,cou.collection,cou.lecturer name,");
        sqlSb.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = cou.id),0) + IFNULL(default_student_count, 0) learnd_count,");
        sqlSb.append(" cou.course_length,cou.is_free, tm.`name` as courseType, cou.description_show from oe_course cou "
                + "left join teach_method tm on cou.courseType = tm.id  left join oe_menu om on om.id = menu_id");
        sqlSb.append("  where  cou.is_delete=0  and  cou.status=1  ");
        sqlSb.append("  and  cou.type=2  ");

        if(menuType!=null){
            if(menuType==1||menuType==2||menuType==3){
                sqlSb.append(" AND cou.type = 2 ");
                paramMap.put("type", menuType);
                sqlSb.append(" AND om.type = :type ");
            }else if(menuType ==4){
                sqlSb.append(" AND cou.type = 3 ");
            }
        }

        if(org.apache.commons.lang.StringUtils.isNotBlank(couseTypeId)){
            paramMap.put("menuId", couseTypeId);
            sqlSb.append(" and cou.menu_id = :menuId ");
        }

        if(org.apache.commons.lang.StringUtils.isNotBlank(multimediaType)){
            sqlSb.append(" and cou.multimedia_type=:multimediaType");
            paramMap.put("multimediaType", multimediaType);
        }

        if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
            sqlSb.append(" and cou.is_free=:isFree");
            paramMap.put("isFree", isFree);
        }

        if(orderType!=null){
        switch (orderType){
            case "1":
                sqlSb.append(" order by   learnd_count desc,cou.create_time desc  ") ;
                break;
            case "2":
                sqlSb.append(" order by  cou.create_time desc ") ;
                break;
            case "3":
                sqlSb.append(" order by  learnd_count desc ") ;
                break;
            case "4":
                sqlSb.append(" order by  current_price "+orderBy) ;
                break;
                default:
                    break;
        }
        }else{
            sqlSb.append(" order by   learnd_count desc,cou.create_time desc  ") ;
        }
        System.out.println(sqlSb.toString());
        Page<CourseLecturVo> page = this.findPageBySQL(sqlSb.toString(), paramMap, CourseLecturVo.class, pageNumber, pageSize);
        return page;
    }



    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    public Page<CourseVo> findUserCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("courseStatus", courseStatus);
        String sql = "SELECT \n" +
                "  oc.id,\n" +
                "  oc.grade_name AS courseName,\n" +
                "  oc.collection,\n" +
                "  oc.smallimg_path AS smallImgPath\n" +
                "FROM\n" +
                "  oe_course oc \n" +
                "  JOIN \n" +
                "  `apply_r_grade_course` argc\n" +
                "  ON oc.`id` = argc.`course_id`\n" +
                "WHERE argc.`user_id` = :userId \n" +
                "  AND oc.is_free = :courseStatus \n" +
                "  AND argc.`is_payment` IN (0,2) AND oc.`type`= 2 \n" +
                "GROUP BY oc.id " ;
        Page<CourseVo> page = this.findPageBySQL(sql, paramMap, CourseVo.class, pageNumber, pageSize);
        return page;
    }
    
    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    public Page<CourseVo> findUserPublicCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("userId", userId);
    	paramMap.put("courseStatus", courseStatus);
    	String sql = " select oc.id,oc.grade_name as courseName,oc.smallimg_path as smallImgPath,oc.`start_time` AS start_time, oc.`start_time` AS startTime, oc.`end_time` AS endTime,oc.direct_id" +
    			" from  oe_course  oc join `apply_r_grade_course` argc on oc.id = argc.`course_id`   where oc.type = 1 and argc.user_id=:userId  and oc.is_free =:courseStatus  group by oc.id ";
    	Page<CourseVo> page = this.findPageBySQL(sql, paramMap, CourseVo.class, pageNumber, pageSize);
    	return page;
    }
    
    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    public Page<CourseVo> findUserRealCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("userId", userId);
    	paramMap.put("courseStatus", courseStatus);
    	String sql = " select oc.id,oc.grade_name as courseName,oc.smallimg_path as smallImgPath,oc.start_time startTime,oc.end_time endTime" +
    			" from  oe_course  oc JOIN `apply_r_grade_course` argc ON oc.`id`=argc.`course_id`  where argc.user_id=:userId  and oc.is_free =:courseStatus AND oc.type=3 group by oc.id ";
    	Page<CourseVo> page = this.findPageBySQL(sql, paramMap, CourseVo.class, pageNumber, pageSize);
    	return page;
    }

    /**
     * 首页所有课程,根据菜单编号，查找对应的课程列表
     * @param menuId      学科ID号
     * @param couseTypeId 课程类别ID号
     * @param pageNumber  当前是第几页，默认1
     * @param pageSize    每页显示多少行，默认20getCourseById
     * @return Example 分页列表
     */
    public Page<CourseLecturVo> getCourseAndLecturerlist(Integer type, Integer menuId, String couseTypeId, Integer pageNumber, Integer pageSize) {
    	menuId = menuId==165 ? 0 : menuId;//165或0就是查全部
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 400 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("menuId", menuId);
        paramMap.put("couseTypeId", couseTypeId);
        StringBuffer  sqlSb=new StringBuffer();
        sqlSb.append(" select cou.id,cou.type,cou.direct_id,cou.is_recommend as isRecommend,cou.grade_name,cou.smallimg_path  as smallImgPath,cou.multimedia_type multimediaType,floor(cou.current_price*10) current_price,cou.start_time startTime,cou.end_time endTime,cou.user_lecturer_id userLecturerId,cou.address,IF(ISNULL(cou.`course_pwd`),0,1) coursePwd,cou.collection,cou.lecturer name,");
        sqlSb.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = cou.id),0)+IFNULL(default_student_count, 0) learnd_count, ");
        sqlSb.append(" cou.course_length,cou.is_free, tm.`name` as courseType, cou.description_show from oe_course cou "
        		+ "left join teach_method tm on cou.courseType = tm.id  left join oe_menu om on om.id = menu_id");
        sqlSb.append("  where  cou.is_delete=0  and  cou.status=1  ");
        switch (menuId) {
        case 0:
        	break;
        case 1:
        	break;
        case 2:
        	break;
        case 3:
        	break;
        default:
        	sqlSb.append(" and  menu_id = :menuId ");
        	break;
        }
        if(type==1||type==2||type==3){
        	paramMap.put("type", type);
        	sqlSb.append(" AND om.type = :type ");
        	sqlSb.append(" AND om.yun_status = 1 ");
        	sqlSb= "0".equals(couseTypeId) ? sqlSb.append("") : sqlSb.append(" and cou.course_type_id = :couseTypeId ");
        	sqlSb.append(" AND cou.type = 2 ");
        }else if(type ==4){
            sqlSb.append(" AND cou.type = 3 ");
        }else{
        	sqlSb= "0".equals(couseTypeId) ? sqlSb.append("") : sqlSb.append(" and cou.course_type_id = :couseTypeId ");
        	sqlSb.append(" AND cou.type = 2 ");
        }
        sqlSb.append(" order by  cou.sort desc ") ;
        Page<CourseLecturVo> page = this.findPageBySQL(sqlSb.toString(), paramMap, CourseLecturVo.class, pageNumber, pageSize);
        return page;
    }


    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @return Example 分页列表
     */
    public CourseVo getCourseById(Integer courseId,String ispreview,HttpServletRequest request) {
        CourseVo courseVo = null;
        //是否是预览，如果是预览这里就关联预览表
        String courseTableName = "1".equals(ispreview) ? "oe_course_preview" : "oe_course";
        String course_type = "1".equals(ispreview) ? "" : "c.course_type,";
        if (courseId != null) {
        	String sql = " select "+course_type+" c.id,c.lecturer_description lecturerDescription,c.direct_id, c.is_recommend,c.type, c.is_free, c.grade_name as courseName ,c.description,floor(c.current_price*10) current_price,ifnull(c.start_time,now()) startTime,ifnull(c.end_time,now()) endTime,c.start_time,c.user_lecturer_id userLecturerId,c.collection,c.course_number courseNumber,c.status,"+
//                         " if(c.is_free=1,IFNULL((SELECT  COUNT(*)  FROM apply_r_grade_course WHERE course_id = c.id),0)+SUM(IFNULL(default_student_count, 0)),"+
//                         " (select  sum(ifnull(student_count,0))+sum(ifnull(default_student_count,0)) from  oe_grade  where course_id=?  and is_delete=0 and status=1)) learnd_count,"+
                         " IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) + IFNULL(pv, 0) learnd_count,"+
                         " c.course_length,c.detailimg_path as detailImgPath, c.bigimg_path as bigImgPath,c.cloud_classroom ,CONCAT(replace(s.`name`,'课',''),t.`name`,\"课\") as scoreName," +
                         " c.course_outline as courseOutline , c.common_problem as commonProblem, c.course_detail  as courseDetail,ifnull(c.qqno,'暂无QQ')as qqno,m.name,c.description_show " +
                         " from "+courseTableName+" c left join oe_menu m on c.menu_id = m.id left join score_type s on c.course_type_id = s.id left join teach_method t ON c.courseType = t.id where c.is_delete=0 and  c.id=?";
            List<CourseVo> courseVoList = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId}, BeanPropertyRowMapper.newInstance(CourseVo.class));
            courseVo = courseVoList.size() > 0 ? courseVoList.get(0) : courseVo;
            if(courseVo != null){
                //成为分享大使课程的id，购买此课程可成为分享大使
                courseVo.setShareCourseId(shareCourseId);
            }

            //获取登录用户
            BxgUser loginUser = UserLoginUtil.getLoginUser(request);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("courseId", courseId);
            //用户登录，查看用户是否购买此课程,报名isApply=true,否则isApply=false
            if(loginUser != null && courseVo != null){
            	/*yuruixin - 20170810*/
//                paramMap.put("userId",loginUser.getId());
//                String  uSql="select id from user_r_video  where  user_id=:userId and course_id=:courseId  limit 1";
//                List<Map<String, Object>> listVideo= this.getNamedParameterJdbcTemplate().queryForList(uSql, paramMap);
//                courseVo.setIsApply(listVideo.size() > 0 ? true : false);
                ApplyGradeCourse  applyGradeCourse = applyGradeCourseDao.findByCourseIdAndUserId(courseId, loginUser.getId());
                if(applyGradeCourse != null) {
                    courseVo.setIsApply("1".equals(applyGradeCourse.getIsPayment()) ? false : true);
                }else{
                    applyGradeCourse = applyGradeCourseDao.findCollectionCourseByCourseIdAndUserId(courseId, loginUser.getId());
                    if(applyGradeCourse != null) {
                        courseVo.setIsApply("1".equals(applyGradeCourse.getIsPayment()) ? false : true);
                    }
                }
            }
        }

        checkAvailable(courseVo);
        return courseVo;
    }


    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param courseId 课程id
     * @return Example 分页列表
     */
    public CourseApplyVo getCourseApplyByCourseId(Integer courseId) {
        if (courseId != null) {
            String sql = " select c.id,  c.grade_name as courseName,c.description, c.bigimg_path as bigImgPath,c.cloud_classroom as cloudClassroom, c.is_free,c.original_cost as originalCost,c.course_pwd coursePwd,"
                        +" c.current_price as currentPrice , t.name as courseType from oe_course c left join teach_method t  on c.courseType = t.id  where c.id= ? and c.is_delete =0 and c.status=1  ";
            List<CourseApplyVo> courseVoList = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId}, BeanPropertyRowMapper.newInstance(CourseApplyVo.class));
            return courseVoList.size() > 0 ? courseVoList.get(0) : null;
        }
        return null;
    }

    /**
     * 根据课程ID号，查找购买的课程是否下架
     * @param
     * @return
     */
    public CourseApplyVo getPurchaseCourseByCourseId(String orderId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("orderNo",orderId);
        if (StringUtils.hasText(orderId)) {
            String sql = " select type,c.id,c.is_free,o.user_id as userId,collection,c.direct_id directId from oe_order o,oe_order_detail od,oe_course c" +
                    " where o.id=od.order_id and od.course_id=c.id and o.id=:orderNo and c.is_delete =0 and c.status=1  ";
            List<CourseApplyVo> courseVoList =   this.findEntitiesByJdbc(CourseApplyVo.class, sql, paramMap);
            return  courseVoList.size() > 0 ? courseVoList.get(0) : null;
        }
        return null;
    }

    /**
     * 查找相关课程信息
     *
     * @param courseId 课程id
     * @return
     */
    public List<CourseVo> getCourseByCourseId(Integer courseId,Integer num) {
        List<CourseVo> courseVoList = null;
        if (courseId != null ) {
            String sql=" select  c.id,c.grade_name as courseName,c.smallimg_path as smallimgPath ,c.original_cost as originalCost ,c.multimedia_type multimediaType,"+
	            		" IFNULL((SELECT  COUNT(*)  FROM  apply_r_grade_course  WHERE course_id = c.id),0)+ IFNULL(default_student_count, 0) learndCount,"+
//                        " if(c.is_free=1,(SELECT count(*) FROM apply_r_grade_course where course_id=c.id),"+
//                        " (select  sum(ifnull(student_count,0))+sum(ifnull(default_student_count,0)) from  oe_grade  where course_id=c.id  and is_delete=0 and status=1)) learndCount,"+
                        " c.current_price as currentPrice ,c.is_free as isFree,c.description_show" +
                        " from oe_course_recommend r ,oe_course c where r.rec_course_id = c.id and r.show_course_id=?  and "+
                        " c.is_delete=0 and r.is_delete=0  and c.`status`=1  order by r.sort   limit "+ num;
            courseVoList = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(CourseVo.class),courseId);
        }
        return courseVoList;
    }

    /**
     * 检测当前用户是否购买过某学科下的课程
     *
     * @return true：购买了  false：未购买
     */
    public boolean checkUserToPay(String userId, Integer  menuId) {
        List<CourseVo> questions = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(
                     "  select co.menu_id from apply_r_grade_course a  join  oe_apply  ap  on a.apply_id = ap.id   join oe_user u on ap.user_id = u.id   " +
                     "  join  oe_course co  on  a.course_id = co.id  where   u.id=?  and co.menu_id=?  and a.is_payment =2  limit 1",
                      BeanPropertyRowMapper.newInstance(CourseVo.class),userId,menuId);
        return   questions.size() >= 1 ?  true :   false;
    }


    /**
     * 博文答详情页面的推荐课程
     *
     * @param menuId      学科ID号
     * @return  推荐课程集合 courseLecturVos
     */
    public List<CourseLecturVo>  getRecommendedCourse(Integer menuId) {
        String sql  = " select cou.id,cou.grade_name,cou.smallimg_path  as smallImgPath,cou.original_cost,cou.current_price, " +
                      " if(cou.is_free=1,(SELECT count(*) FROM apply_r_grade_course where course_id=cou.id),"+
                      "  (select   sum(ifnull(student_count,0))+sum(ifnull(default_student_count,0)) from  oe_grade  where course_id=cou.id  and is_delete=0 and status=1)) learnd_count,"+
                      " cou.is_free,tm.`name` as courseType,cou.course_length as courseLength, cou.description_show from oe_course cou   join  teach_method tm   where  cou.courseType = tm.id  and  " +
                      " cou.menu_id =? and cou.is_free =0  and  cou.is_delete=0  and  cou.status=1  order by  cou.sort limit 3 ";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(CourseLecturVo.class),menuId);
    }

    /**
     * 首页的推荐课程
     *
     * @param num   展示条数
     * @return      推荐课程集合 courseLecturVos
     */
    public List<CourseLecturVo>  getRecommendCourse(Integer num) {
        String sql  = " select cou.id,cou.grade_name,cou.smallimg_path  as smallImgPath,cou.original_cost as originalCost,cou.current_price as currentPrice , " +
                      " if(cou.is_free=1,(SELECT count(*) FROM apply_r_grade_course where course_id=cou.id),"+
                      "  (select   sum(ifnull(student_count,0))+sum(ifnull(default_student_count,0)) from  oe_grade  where course_id=cou.id  and is_delete=0 and status=1)) learnd_count,"+
                      " cou.course_length,cou.is_free,tm.`name` as courseType,cou.description_show,cou.rec_img_path as recImgPath from oe_course cou   join  teach_method tm     " +
                      " where  cou.courseType = tm.id  and cou.is_delete=0  and  cou.status=1 and  is_recommend=1  order by  cou.recommend_sort  limit 4 ";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(CourseLecturVo.class));
    }

    /**
     * 根据课程Id查询课程介绍列表数据
     * @param courseId
     * @return
     */
    public List<CourseDescriptionVo> getCourseDescriptionsByCourseId(Boolean isPreview,String courseId) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramMap = new HashMap<>();
        sql.append(" select ocd.id,ocd.course_id courseId,(select grade_name from oe_course where id = ocd.course_id) courseName,ocd.course_title courseTitle,ocd.course_content courseContent,");
        sql.append(" (select om.`name` from oe_course oc LEFT JOIN oe_menu om on oc.menu_id=om.id where oc.id = ocd.course_id)  menuName,");
        sql.append(" (select max(id) from oe_course_description where id<ocd.id and status=1 and is_delete=0 and course_id =:courseId) lastId,");
        sql.append(" (select min(id) from oe_course_description where id>ocd.id and status=1 and is_delete=0 and course_id =:courseId) nextId,");
        sql.append(" (select tm.`name` from oe_course oc LEFT JOIN teach_method tm on oc.courseType=tm.id where oc.id=ocd.course_id) courseType,");
        sql.append(" ocd.preview,ocd.course_title_preview courseTitlePreview,ocd.course_content_preview courseContentPreview");
        sql.append(" from oe_course_description ocd where ocd.status=1 and ocd.is_delete=0 and ocd.course_id =:courseId");
        if (!isPreview){
            sql.append(" and ocd.preview = 0");
        }
        sql.append(" order by ocd.id");
        sql.append(" limit 9");
        paramMap.put("courseId",courseId);
        return this.findEntitiesByJdbc(CourseDescriptionVo.class, sql.toString(), paramMap);
    }

    /**
     * 根据Id查询课程介绍详情
     * @param id
     * @return
     */
    public CourseDescriptionVo getCourseDescriptionsById(String id,String courseId){
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append(" select ocd.id,ocd.course_id courseId,ocd.course_title courseTitle,ocd.course_content courseContent,");
        sql.append(" (select max(id) from oe_course_description where id<ocd.id and status=1 and is_delete=0 and course_id =:courseId) lastId,");
        sql.append(" (select min(id) from oe_course_description where id>ocd.id and status=1 and is_delete=0 and course_id =:courseId) nextId,");
        sql.append(" ocd.preview,ocd.course_title_preview courseTitlePreview,ocd.course_content_preview courseContentPreview");
        sql.append(" from oe_course_description ocd where ocd.status=1 and ocd.is_delete=0 and ocd.id =:id");
        sql.append(" order by ocd.id");
        paramMap.put("id", id);
        paramMap.put("courseId", courseId);
        List<CourseDescriptionVo> CourseDescriptionList = this.findEntitiesByJdbc(CourseDescriptionVo.class,sql.toString(),paramMap);
        if(CourseDescriptionList!=null && CourseDescriptionList.size()>0){
            return CourseDescriptionList.get(0);
        }
        return null;
    }


    /**
     * 报名后课程详情也接口，根据课程id查找对应课程
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
     public  CourseVo   findEnterCourseDetail(HttpServletRequest request,Integer  courseId){
         OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");
         Map<String,Object> paramMap = new HashMap<>();
         StringBuffer sql = new StringBuffer();
         List<CourseVo> courseVos=null;
         if(u != null){
             paramMap.put("courseId",courseId);
             paramMap.put("userId",u.getId());
             sql.append( "  select c.id,c.grade_name as courseName,c.description,c.course_length as courseLength, c.course_type,c.is_free,");
             sql.append( " IFNULL((SELECT  COUNT(*)  FROM  apply_r_grade_course  WHERE course_id = c.id),0)+ IFNULL(default_student_count, 0) learndCount,");
//             sql.append( " if(c.is_free=1,(SELECT count(*) FROM apply_r_grade_course where course_id=c.id),");
//             sql.append( " (select  sum(ifnull(student_count,0))+sum(ifnull(default_student_count,0)) from  oe_grade  where course_id=:courseId  and is_delete=0 and status=1)) learndCount,");
             sql.append( " (select count(id) from  oe_video   where status=1 and is_delete=0 and course_id=:courseId  ) as count,");
             sql.append( " (select count(id) from  user_r_video  where status=1 and is_delete=0 and user_id=:userId and course_id=:courseId and study_status=1 ) as learndVideo, ");
             sql.append( " (select count(id) from  user_r_video  where status=1 and is_delete=0 and user_id=:userId and course_id=:courseId and study_status=0 ) as unStudy ");
             sql.append( " from oe_course c  where c.id=:courseId");
             courseVos= this.findEntitiesByJdbc(CourseVo.class, sql.toString(), paramMap);
         }
         return  courseVos.size()>0 ? courseVos.get(0) : null;
     }

    /**
     * 根据课程id查询课程
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
    public  CourseVo   findCourseOrderById(Integer  courseId){
         String  sql =" select id ,is_free isFree, course_type,collection,is_sent isSent,direct_id directId, grade_name as courseName ,smallimg_path as smallImgPath,original_cost as originalCost ,start_time,IF(ISNULL(`course_pwd`),0,1) coursePwd," +
                      " current_price as currentPrice, now() as create_time, type, FORMAT(original_cost - current_price,2) as preferentyMoney from oe_course  where id =:courseId";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId",courseId);
        List<CourseVo>  courseVos= this.findEntitiesByJdbc(CourseVo.class, sql.toString(), paramMap) ;
        return courseVos.size() > 0 ? courseVos.get(0) : null;
    }

    /**
     * 查询购物车中提交的订单
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
    //public  List<Map<String,Object> >   findConfirmOrderById(Integer  courseId){
    //    String  sql =" select id ,course_type, grade_name as courseName ,smallimg_path as smallImgPath,original_cost as originalCost ," +
    //            " current_price as currentPrice, now() as create_time,  FORMAT(original_cost - current_price,2) as preferentyMoney from oe_course  where id =:courseId";
    //    Map<String,Object> paramMap = new HashMap<>();
    //    paramMap.put("courseId",courseId);
    //    List<CourseVo>  courseVos= this.findEntitiesByJdbc(CourseVo.class, sql.toString(), paramMap) ;
    //    return courseVos.size() > 0 ? courseVos.get(0) : null;
    //}


    /**
     * 购买课程时，进行检测此订单关联的课程是否下架以及是否购买
     * @param
     */
    public   void   checkCouseInfo(String  orderId){
        //查看报名的课程是否存在
        CourseApplyVo course= this.getPurchaseCourseByCourseId(orderId);
        if(course == null){
            throw new RuntimeException("对不起,您要购买的课程已下架!");
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId",course.getId());
        //1、查询当前课程下是否有视频
//        String  querySql="select id as video_id ,course_id as courseId from oe_video where course_id=:courseId and is_delete=0";
//        List<UserVideoVo>  videos = this.findEntitiesByJdbc(UserVideoVo.class, querySql, paramMap);
        /*20170810---yuruixin*/
        if (course.getType()== CourseForm.VOD.getCode() && (course.getDirectId()==null || "".equals(course.getDirectId().trim())) && !course.getCollection())
        {
            throw new RuntimeException("此课暂时没有视频,请稍后购买!");
        }
        // 查看用户有没有购买此课程
        if( videoDao.getUserCourse(course.getId(), course.getUserId()).size()>0){
            throw new RuntimeException("同学,您已经购买了此课程!");
        };

    }


    /**
     * 获取当前课程下学员评价
     * @param courseId 课程id
     * @return
     */
    public  Page<CriticizeVo>  findStudentCriticize(Integer courseId, Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
         if(courseId !=null){
           /*  String querySql=" select u.small_head_photo smallPhoto,u.`name` userName,c.id,c.content,c.star_level starLevel,c.create_time createTime,c.praise_sum praiseSum," +
                             " c.response,c.response_time response_time from oe_criticize c ,oe_user u where c.user_id=u.id and c.is_delete=0 and c.`status`=1 and   c.course_id=:courseId";
             Map<String,Object> paramMap = new HashMap<>();
             paramMap.put("courseId",courseId);
             Page<CriticizeVo>  criticizes = this.findPageBySQL(querySql,paramMap,CriticizeVo.class,pageNumber,pageSize);
             return  criticizes;*/
        	 String querySql="select c from Criticize c  where c.status = 1 and c.onlineUser.id =:userId ";
             Map<String,Object> paramMap = new HashMap<>();
	       	  paramMap.put("userId", "ef823d8ef62b48d89459ac0871953282");
             //  Page<BannerVo> page = dao.findPageByHQL("from Banner where 1=1 and isDelete=0 and type = :type and status=1 order by sort ", paramMap, pageNumber, pageSize);
             Page<Criticize>  criticizes = this.findPageByHQL(querySql,paramMap,pageNumber,pageSize);
             System.out.println(criticizes.getItems());
         }
         return  null;
    }


    /**
     * 获取好评的数量
     * @param courseId 课程ID
     * @return
     */
    public Integer getGoodCriticizSum(Integer courseId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT count(c.id) ");
        sql.append(" from oe_criticize c  where  c.is_delete=0 and c.`status`=1 and c.star_level >=4  and  c.course_id=:courseId ");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId",courseId);
        return  this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(),paramMap,Integer.class);

    }


    /**
     * 获取课程目录
     * @param courseId
     * @return
     */
    public List<Map<String, Object>> getCourseCatalog(Integer courseId) {

        List<Map<String, Object>> returnmap = new ArrayList<Map<String, Object>>();

        //查询所有章节知识点
        String sql = "select id,name,parent_id,level from oe_chapter "
                + "where is_delete=0 and course_id=? and level>1 order by sort";
        List<Map<String, Object>> chapters = videoDao.getNamedParameterJdbcTemplate()
                .getJdbcOperations().queryForList(sql, courseId);

        //组装树形结构
        for (Map<String, Object> zhangmap : chapters) {
            //循环章
            if (((Integer)zhangmap.get("level")) == 2) {
                List<Map<String, Object>> zhangsons = new ArrayList<Map<String, Object>>();
                //循环取节>>>>>
                for (Map<String, Object> jiemap : chapters) {
                    if (jiemap.get("parent_id").equals(zhangmap.get("id"))) {
                        List<Map<String, Object>> jiesons = new ArrayList<Map<String, Object>>();
                        //循环取知识点>>>>>
                        for (Map<String, Object> zhishidianmap : chapters) {
                            if (zhishidianmap.get("parent_id").equals(jiemap.get("id"))) {
                                jiesons.add(zhishidianmap);
                            }
                        }
                        jiemap.put("chapterSons", jiesons);
                        //<<<<<循环取知识点
                        zhangsons.add(jiemap);
                    }
                }
                zhangmap.put("chapterSons", zhangsons);
                //<<<<<循环取节
                returnmap.add(zhangmap);
            }
        }
        return returnmap;
    }


    /**
     * 获取当前用户买的最新课程信息
     */
    public  List<Map<String, Object>>  findNewestCourse(String orderNo){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNo",orderNo);
      /*  String  sql =" select o.course_id,o.course_name,o.user_id,a.grade_id,ifnull(g.qqno,0) qqno from  apply_r_grade_course a,oe_apply ap," +
                     " oe_order o,oe_grade g where   a.apply_id= ap.id and o.course_id = a.course_id and a.grade_id=g.id " +
                     " and ap.user_id=o.user_id  and  o.order_no=:orderNo";*/
//        String sql=" select o.user_id,a.grade_id,od.course_id, c.grade_name course_name, ifnull(g.qqno,0) qqno from " +
//                   " oe_order o,oe_order_detail od,oe_apply ap,apply_r_grade_course a,oe_course c,oe_grade g " +
//                   " where o.id = od.order_id  and o.user_id=ap.user_id and ap.id = a.apply_id  and od.course_id = c.id and a.grade_id=g.id " +
//                   " and o.order_no=:orderNo and a.course_id=od.course_id ";
        String sql = "SELECT \n" +
                "  o.user_id,\n" +
                "  od.course_id,\n" +
                "  c.grade_name course_name\n" +
                "FROM\n" +
                "  oe_order o,\n" +
                "  oe_order_detail od,\n" +
                "  apply_r_grade_course a,\n" +
                "  oe_course c\n" +
                "WHERE o.id = od.order_id \n" +
                "  AND od.course_id = c.id \n" +
                "  AND o.order_no = :orderNo \n" +
                "  AND a.course_id = od.course_id ";
        List<Map<String, Object>> courses= this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
        return  courses;
    }

	public CourseVo getOpenCourseById(Integer courseId, String ispreview, HttpServletRequest request) {
        CourseVo courseVo = null;
        //是否是预览，如果是预览这里就关联预览表
        String courseTableName = "1".equals(ispreview) ? "oe_course_preview" : "oe_course";
        String course_type = "1".equals(ispreview) ? "" : "c.course_type,";
        if (courseId != null) {
            String sql = " select "+course_type+" c.id, c.is_recommend, c.is_free, c.grade_name as courseName ,c.description,c.current_price,c.original_cost,"+
//            			"IF(c.live_status='1', 2, IF(c.live_status='2', 1, 3)) AS broadcastState, "+
                         " if(c.is_free=1,(SELECT count(*) FROM apply_r_grade_course where course_id=c.id),"+
                         " (select  sum(ifnull(student_count,0))+sum(ifnull(default_student_count,0)) from  oe_grade  where course_id=?  and is_delete=0 and status=1)) learnd_count,c.course_length,"+
                         " c.detailimg_path as detailImgPath, c.bigimg_path as bigImgPath,c.cloud_classroom ,CONCAT(replace(s.`name`,'课',''),t.`name`,\"课\") as scoreName," +
                         " c.course_outline as courseOutline , c.common_problem as commonProblem, c.course_detail  as courseDetail,ifnull(c.qqno,'暂无QQ')as qqno,m.name,c.description_show " +
                         " from "+courseTableName+" c join oe_menu m  join score_type s  join  teach_method t where c.course_type_id = s.id  and c.courseType = t.id  and c.menu_id = m.id and c.is_delete=0 and c.status=1  and  c.id=?";
            List<CourseVo> courseVoList = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId,courseId}, BeanPropertyRowMapper.newInstance(CourseVo.class));
            courseVo = courseVoList.size() > 0 ? courseVoList.get(0) : courseVo;
            if(courseVo != null){
                //成为分享大使课程的id，购买此课程可成为分享大使
                courseVo.setShareCourseId(shareCourseId);
            }

            //获取登录用户
            BxgUser loginUser = UserLoginUtil.getLoginUser(request);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("courseId", courseId);
            if(loginUser != null && courseVo != null){ //用户登录，查看用户是否购买此课程,报名isApply=true,否则isApply=false
                paramMap.put("userId",loginUser.getId());
                String  uSql="select id from user_r_video  where  user_id=:userId and course_id=:courseId  limit 1";
                List<Map<String, Object>> listVideo= this.getNamedParameterJdbcTemplate().queryForList(uSql, paramMap);
                courseVo.setIsApply(listVideo.size() > 0 ? true : false);
            }
            
        }

        return courseVo;
    }

    private void checkAvailable(CourseVo courseVo) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(courseVo.getStartTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);//时
        calendar.set(Calendar.MINUTE, 0);//分
        calendar.set(Calendar.SECOND, 0);//秒
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-2);//日
        Date date = calendar.getTime();
//        System.out.println("Christmas is:"+FORMAT.FORMAT(date));
        String now = format.format(Calendar.getInstance().getTime());
        Date nowd;
        try {
            nowd = format.parse(now);
            int flag = nowd.compareTo(date);
            if (flag > 0) {//当天及当天之后，<0就是在日期之前
//                System.out.println("已过期");
                courseVo.setAvailable(false);
            }else{
//                System.out.println("未过期");
                courseVo.setAvailable(true);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateSentById(Integer id) {
		String sql="update oe_course set is_sent = 1 where  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

	public List<CourseVo> getOpenCourseToSend() {
        Map<String, Object> paramMap = new HashMap<>();
        String sql = "SELECT id ,course_type,is_sent isSent, grade_name AS courseName,start_time AS startTime,end_time AS endTime "
        		+ "FROM oe_course oc WHERE oc.is_sent = FALSE AND oc.is_delete =FALSE AND oc.type = 1 AND oc.status = 1 AND oc.start_time > NOW()";
        return this.findEntitiesByJdbc(CourseVo.class, sql, paramMap);
	}

	public OnlineUser getLecturer(String userLecturerId) {
		DetachedCriteria dc = DetachedCriteria.forClass(OnlineUser.class);
		dc.add(Restrictions.eq("id", userLecturerId));
		OnlineUser user = this.findEntity(dc);
		return user;
	}
	
    /**
     * 根据课程id查找此课程的状态
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
     public  CourseVo   findCourseLiveStatus(Integer  courseId){
         StringBuffer sql = new StringBuffer();
         List<CourseVo> courseVos=null;
         Map<String,Object> paramMap = new HashMap<String, Object>();
         paramMap.put("courseId",courseId);
         sql.append( "  select c.live_status  as liveStatus");
         sql.append( " from oe_course c  where c.id=:courseId");
         courseVos= this.findEntitiesByJdbc(CourseVo.class, sql.toString(), paramMap);
         return  courseVos.size()>0 ? courseVos.get(0) : null;
     }
     
     
     public  Page<Criticize>  findUserCriticize(Integer courseId, Integer pageNumber, Integer pageSize){
         pageNumber = pageNumber == null ? 1 : pageNumber;
         pageSize = pageSize == null ? 10 : pageSize;
          if(courseId !=null){
         	 String querySql="select c from Criticize c  where c.status = 1 and c.onlineUser.id =:userId ";
              Map<String,Object> paramMap = new HashMap<>();
 	       	  paramMap.put("userId", "ef823d8ef62b48d89459ac0871953282");
              //  Page<BannerVo> page = dao.findPageByHQL("from Banner where 1=1 and isDelete=0 and type = :type and status=1 order by sort ", paramMap, pageNumber, pageSize);
              Page<Criticize>  criticizes = this.findPageByHQL(querySql,paramMap,pageNumber,pageSize);
              System.out.println(criticizes.getItems());
              return criticizes;
          }
          return  null;
     }


    public Course getCourse(Integer courseId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
        dc.add(Restrictions.eq("id", courseId));
        Course course = this.findEntity(dc);
        if(course == null){
            return null;
        }
        return course;
    }

    public List<Course> getLiveCourse() {
        DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
        //上架中
        dc.add(Restrictions.eq("status", "1"));
        //直播状态为预告
        dc.add(Restrictions.eq("liveStatus", 2));
        //未删除的课程
        dc.add(Restrictions.eq("isDelete", false));
        List<Course> courses = this.findEntities(dc);
        return courses;
    }

    public List<CourseVo> getCoursesByCollectionId(Integer collectionId) {
        List<CourseVo> courseVoList = null;
        String sql="SELECT \n" +
                "  oc.`id`,\n" +
                "  oc.`course_length` as courseLength,\n" +
                "  oc.`grade_name` as name\n" +
                "FROM\n" +
                "  `oe_course` oc \n" +
                "  JOIN `collection_course` cc \n" +
                "    ON oc.id = cc.`course_id` \n" +
                "WHERE cc.`collection_id` = ? \n" +
                "ORDER BY cc.`collection_course_sort` DESC";
        courseVoList = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(CourseVo.class),collectionId);
        return courseVoList;
    }
}