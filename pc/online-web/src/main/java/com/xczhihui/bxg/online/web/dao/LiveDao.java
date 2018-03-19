package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.bxg.online.web.vo.OpenCourseVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公开直播课底层业务类
 * @author majian
 * @date 2016-8-22 11:33:00
 */
@Repository
public class LiveDao extends SimpleHibernateDao {

	@Autowired
	private CourseDao coursedao;
	
	@Autowired
	private GiftDao giftDao;
	@Autowired
	private RewardDao rewardDao;
	@Value("${ENV_FLAG}")
	private String envFlag;

    /**
     * 首页获取公开直播课
     * @param  num:条数
     * @return
     */
    public List<OpenCourseVo> getOpenCourse(Integer num){
        StringBuffer  sqlbf=new StringBuffer();
//        sqlbf.append(" select z.* from (select c.id , c.is_free as free,c.grade_name as courseName,c.smallimg_path,c.direct_id,IF ( c.is_free = 1, ( SELECT count(*) FROM apply_r_grade_course WHERE course_id = c.id ), ( SELECT sum(ifnull(student_count, 0)) + sum( ifnull(default_student_count, 0)) FROM oe_grade WHERE course_id = c.id AND is_delete = 0 AND STATUS = 1 )) learnd_count,c.external_links,c.direct_seeding,IF(ISNULL(c.`course_pwd`),0,1) coursePwd,");
        sqlbf.append(" select z.* from (select c.id , c.is_free as free,c.grade_name as courseName,c.smallimg_path,c.direct_id,IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) + IFNULL(pv, 0) learnd_count,c.external_links,c.direct_seeding,IF(ISNULL(c.`course_pwd`),0,1) coursePwd,");
        sqlbf.append(" c.start_time as formatStartTime,ifnull(ou.name ,'暂无讲师') as teacherName,if(now()< c.start_time,2,1 ) as broadcastState");
//        sqlbf.append(" from oe_course c left join oe_lecturer l on c.lecturer_id = l.id where c.type=1 and c.is_delete=0 and c.status=1 and  now() <= c.end_time  order by c.sort desc  ) as z   limit 4 ");
        sqlbf.append(" from oe_course c left join oe_user ou on c.user_lecturer_id = ou.id where c.type=1 and c.is_delete=0 and c.status=1 and  now() <= c.end_time  order by c.sort desc  ) as z  ");
        if(num!=null&&num!=0){
            sqlbf.append("limit "+num);
        }
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sqlbf.toString(), BeanPropertyRowMapper.newInstance(OpenCourseVo.class));
    }

    /**
     * 首页获取正在播放的直播
     * 如果没有就返回即将要播放的第一个直播
     * @return
     */
    public OpenCourseVo getIndexLive(){
        StringBuffer  sqlbf=new StringBuffer();
        sqlbf.append("SELECT z.* FROM ( SELECT c.id, c.is_free AS free, c.grade_name AS courseName, c.smallimg_path, c.direct_id, c.external_links, c.direct_seeding,IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) + IFNULL(pv, 0) learnd_count, IF (ISNULL(c.`course_pwd`), 0, 1) coursePwd, c.start_time AS formatStartTime, ifnull(ou. NAME, '暂无讲师') AS teacherName, c.live_status AS broadcastState FROM oe_course c LEFT JOIN oe_user ou ON c.user_lecturer_id = ou.id WHERE c.type = 1 AND c.is_delete = 0 AND c. STATUS = 1 AND now() <= c.end_time AND now() >= c.start_time ORDER BY c.sort DESC ) AS z LIMIT 1");
//        sqlbf.append("SELECT z.* FROM ( SELECT c.id, c.is_free AS free, c.grade_name AS courseName, c.smallimg_path, c.direct_id, c.external_links, c.direct_seeding, IF ( c.is_free = 1, IFNULL((SELECT  COUNT(*)  FROM apply_r_grade_course WHERE course_id = c.id),0)+SUM(IFNULL(default_student_count, 0)), ( SELECT sum(ifnull(student_count, 0)) + sum( ifnull(default_student_count, 0)) FROM oe_grade WHERE course_id = c.id AND is_delete = 0 AND STATUS = 1 )) learnd_count, IF (ISNULL(c.`course_pwd`), 0, 1) coursePwd, c.start_time AS formatStartTime, ifnull(ou. NAME, '暂无讲师') AS teacherName, IF (now() < c.start_time, 2, 1) AS broadcastState FROM oe_course c LEFT JOIN oe_user ou ON c.user_lecturer_id = ou.id WHERE c.type = 1 AND c.is_delete = 0 AND c. STATUS = 1 AND now() <= c.end_time AND now() >= c.start_time ORDER BY c.sort DESC ) AS z LIMIT 1");

        List<OpenCourseVo>  list=  this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sqlbf.toString(), BeanPropertyRowMapper.newInstance(OpenCourseVo.class));
        if(list.size()>0){
            return list.get(0);
        }else{
            return getOpenCourse(1).get(0);
        }
    }



    /**
     * 修改直播课程的浏览数
     *
     * @param courseId 课程的封装对象
     */
    public int updateBrowseSum(Integer courseId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        String sql = " update oe_course  set pv = (pv + 1)  where id = :courseId and is_delete = 0 and status=1 ";
        this.getNamedParameterJdbcTemplate().update(sql, paramMap);
        StringBuffer  sqlbf=new StringBuffer();
        sqlbf.append("SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) + IFNULL(pv, 0) learnd_count FROM oe_course c where c.id="+courseId);

        int count =  this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sqlbf.toString(), Integer.class);
        return count;
    }

    /**
     * 修改鲜花数
     * @param courseId 课程ID号
     */
    public Integer updateFlowersNumber(Integer courseId) {
        //1、先查看当前课程讲师的鲜花数
        Integer num=0; //鲜花数
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        String  sqlNum="select ifnull(flowers_number,0) flowers_number from oe_course  where id=:courseId and is_delete=0 and status=1";
        List<OpenCourseVo> openCourseVos= this.findEntitiesByJdbc(OpenCourseVo.class, sqlNum, paramMap);
        num=openCourseVos.size()>0 ? openCourseVos.get(0).getFlowers_number()+1 : 0+1;  //当前课程最近鲜花数
        paramMap.put("num", num);
        String sql = " update oe_course  set flowers_number = :num  where id =:courseId  ";
        this.getNamedParameterJdbcTemplate().update(sql,paramMap);
        return  num;
    }




    /**
     * 获取直播课程信息，根据课程id查询课程
     * @param courseId 课程id号
     */
    public Map<String, Object> getOpenCourseById(Integer courseId,String planId) {
        String  sql="";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        List<Map<String, Object>> openCourseVos=null;
        Map<String, Object> course=null;
        //查询直播课信息
        sql="select \n" +
                "  c.id,\n" +
                "  c.menu_id,\n" +
                "  c.grade_name as courseName,\n" +
                "  ca.name as teacherName,\n" +
                "  c.description,\n" +
                "  c.smallimg_path as smallImgPath,\n" +
                "  ca.profile_photo as head_img,\n" +
                "  ca.user_id as teacherId,\n" +
                "  IFNULL(\n" +
                "    (SELECT \n" +
                "      COUNT(*) \n" +
                "    FROM\n" +
                "      apply_r_grade_course \n" +
                "    WHERE course_id = c.id),\n" +
                "    0\n" +
                "  ) + IFNULL(default_student_count, 0) + IFNULL(pv, 0) learnd_count,\n" +
                " c.live_status AS broadcastState,\n" +
                "  DATE_FORMAT(c.end_time, '%H:%i') end_time,\n" +
                "  DATE_FORMAT(\n" +
                "    c.start_time,\n" +
                "    '%Y年%m月%d日 %H:%i'\n" +
                "  ) start_time,\n" +
                "  c.direct_id,\n" +
                "  c.type,\n" +
                "  c.flowers_number,\n" +
                "  c.user_lecturer_id userLecturerId FROM\n" +
                "  oe_course c \n" +
                "  LEFT JOIN `course_anchor` ca\n" +
                "    ON c.user_lecturer_id = ca.user_id\n" +
                "WHERE c.id = :courseId";
        List<Map<String, Object>> courses=this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        if(courses.size()>0){
            course=courses.get(0);
        }
        if(course!=null && (int) course.get("type") == CourseForm.LIVE.getCode()){
            //获取主播礼物数目
            int count = giftDao.findByLiveId(courseId);
            String rewardTotal = rewardDao.getRewardTotal(courseId.toString());
            course.put("giftCount", count);
            course.put("rewardTotal", rewardTotal==null?0:rewardTotal);
        }
        return course;
    }


    /**
     * 更新在线人数
     * @param courseId  课程id
     * @param personNumber 当前在线人数
     */
    public void  saveOnUserCount(Integer courseId,Integer personNumber){
        Integer num=0;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        String  sqlNum="select ifnull(highest_number_line,0) highest_number_line  from oe_course  where id=:courseId and is_delete=0 and status=1";
        List<OpenCourseVo> openCourseVos= this.findEntitiesByJdbc(OpenCourseVo.class, sqlNum, paramMap);
        num=openCourseVos.size()>0 ? openCourseVos.get(0).getHighest_number_line() : 0;  //当前课程在线人数
        //当前在线人数和历史在线人数作对比,如果当前在线人数大与历史在线人数，则修改数据库，否则不变
        if(personNumber > num){
            paramMap.put("num", personNumber);
            String sql = " update oe_course  set highest_number_line = :num  where id =:courseId  ";
            this.getNamedParameterJdbcTemplate().update(sql,paramMap);
        }
    }



    /**
     * 获取一周的课程表数据
     * @param currentTime 前端传过来的时间
     */
    public  List<OpenCourseVo>   getCourseTimetable(long currentTime){
        // 定义输出日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //毫秒值转换成日期
        String time=sdf.format(currentTime);
        //参数组装
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("time", time);

        StringBuffer  bf=new StringBuffer();
        bf.append(" select id,start_time as hourTime,date_format(start_time,'%w') as day,grade_name as courseName,direct_id,external_links,direct_seeding ,  ");
        bf.append(" live_status as broadcastState ");
//        bf.append("  if(now() between start_time and end_time ,1,2 ) as broadcastState ");
        bf.append(" from oe_course where type =1 and date(start_time) between subdate(:time,date_format(:time,'%w')-1) and  date_sub(:time,INTERVAL WEEKDAY(:time) - 6 DAY) ");
        bf.append(" order by start_time asc, sort desc ");
        List<OpenCourseVo>  courseVos= this.findEntitiesByJdbc(OpenCourseVo.class,  bf.toString(), paramMap) ;
        return  courseVos;
    }

	public List<OpenCourseVo> getOpenCourse(Integer num, String userId) {
        StringBuffer  sqlbf=new StringBuffer();
        if(userId == null){
        	sqlbf.append("SELECT \n" +
                    "  c.id,\n" +
                    "  c.is_free AS free,\n" +
                    "  c.description,\n" +
                    "  c.grade_name AS courseName,\n" +
                    "  c.smallimg_path,\n" +
                    "  c.direct_id,\n" +
                    "  IFNULL(\n" +
                    "    (SELECT \n" +
                    "      COUNT(*) \n" +
                    "    FROM\n" +
                    "      apply_r_grade_course \n" +
                    "    WHERE course_id = c.id),\n" +
                    "    0\n" +
                    "  ) + IFNULL(default_student_count, 0) learnd_count,\n" +
                    "  c.direct_seeding,\n" +
                    "  c.start_time AS formatStartTime,\n" +
                    "  c.lecturer AS teacherName,\n" +
                    "  c.live_status AS broadcastState \n" +
                    "FROM\n" +
                    "  oe_course c \n" +
                    "  LEFT JOIN oe_user ou \n" +
                    "    ON c.user_lecturer_id = ou.id \n" +
                    "WHERE c.type = 1 \n" +
                    "  AND c.is_delete = 0 \n" +
                    "  AND c.status = 1 \n" +
                    "ORDER BY broadcastState,\n" +
                    "  c.start_time ");
        }else{
        	sqlbf.append("SELECT \n" +
                    "  c.id,\n" +
                    "  c.is_free AS free,\n" +
                    "  c.description,\n" +
                    "  c.grade_name AS courseName,\n" +
                    "  c.smallimg_path,\n" +
                    "  c.direct_id,\n" +
                    "  IFNULL(\n" +
                    "    (SELECT \n" +
                    "      COUNT(*) \n" +
                    "    FROM\n" +
                    "      apply_r_grade_course \n" +
                    "    WHERE course_id = c.id),\n" +
                    "    0\n" +
                    "  ) + IFNULL(default_student_count, 0) learnd_count,\n" +
                    "  c.direct_seeding,\n" +
                    "  c.start_time AS formatStartTime,\n" +
                    "   c.lecturer teacherName,\n" +
                    "  IF(ISNULL(ocs.user_id), 0, 1) isSubscribe,\n" +
                    " c.live_status AS broadcastState \n" +
                    "FROM\n" +
                    "  oe_course c \n" +
                    "  LEFT JOIN oe_user ou \n" +
                    "    ON c.user_lecturer_id = ou.id \n" +
                    "  LEFT JOIN `oe_course_subscribe` ocs \n" +
                    "    ON ocs.user_id = '"+userId+"' \n" +
                    "    AND ocs.course_id = c.id \n" +
                    "WHERE c.type = 1 \n" +
                    "  AND c.is_delete = 0 \n" +
                    "  AND c.status = 1 \n" +
                    "ORDER BY broadcastState,\n" +
                    "  c.start_time ");
        }
    
    
        if(num!=null&&num!=0){
            sqlbf.append("limit "+num);
        }
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sqlbf.toString(), BeanPropertyRowMapper.newInstance(OpenCourseVo.class));
    }

}
