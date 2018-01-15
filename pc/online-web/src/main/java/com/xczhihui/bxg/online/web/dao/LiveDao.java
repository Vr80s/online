package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
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
        if(planId ==null || "".equals(planId) || "null".equals(planId)){
            sql="select id,menu_id,grade_name courseName,description, ifnull(type,0) type,user_lecturer_id userLecturerId, IF(ISNULL(c.`course_pwd`), 0, 1) coursePwd,"
            		+"c.live_status AS broadcastState, "
//            		+"IF(c.live_status='1', 2, IF(c.live_status='2', 1, 3)) AS broadcastState, "
            		+ "c.is_free AS free,description,c.smallimg_path as smallImgPath from  oe_course c where id=:courseId";
            List<Map<String, Object>> courses=this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
            if(courses.size() > 0 && ("1").equals(courses.get(0).get("type").toString())){//公开直播课
                sql= " select c.id,c.menu_id, c.grade_name as courseName,ifnull(ou.name,'暂无讲师') as teacherName,c.description,c.smallimg_path as smallImgPath,ou.small_head_photo as head_img,ou.id as teacherId,ou.id teacherId,IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) + IFNULL(pv, 0) learnd_count, " +
                		"c.live_status AS broadcastState, "+
//                     " IF(c.live_status='1', 2, IF(c.live_status='2', 1, 3)) AS broadcastState, "+
                     " DATE_FORMAT(c.end_time,'%H:%i') end_time, DATE_FORMAT(c.start_time,'%Y年%m月%d日 %H:%i') start_time, c.direct_id,c.flowers_number  "+
                     " from oe_course c left join oe_user ou on c.user_lecturer_id = ou.id  where c.id=:courseId ";
                openCourseVos =this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
                course=openCourseVos.size() > 0 ? openCourseVos.get(0) :null;
            }else if(courses.size() > 0 && !("1").equals(courses.get(0).get("type").toString())){ //非公开课
                 course=courses.get(0);
//                 sql =  " select le.`name` from  course_r_lecturer grl join oe_lecturer le where grl.lecturer_id = le.id  and grl.is_delete = 0" +
//                		 " and  grl.course_id= :courseId  and le.role_type=1  and le.is_delete=0  order by  le.create_time  limit 2 ";
//                 List<Map<String, Object>> lecturVos=this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
//                 if(!CollectionUtils.isEmpty(lecturVos) && lecturVos.size() == 1){
//                     course.put("teacherName", lecturVos.get(0).get("name"));
//                 }else if(!CollectionUtils.isEmpty(lecturVos) && lecturVos.size() > 1){
//                     course.put("teacherName",lecturVos.get(0).get("name")+"等");
//                 }
             		OnlineUser onlineUser = coursedao.getLecturer((String) course.get("userLecturerId"));
             		course.put("teacherName",onlineUser.getName());
            }
        }else{//查询串讲课信息
            paramMap.put("planId", planId);
            sql=" select  c.id,c.menu_id, p.chuanjiang_name courseName,DATE_FORMAT(p.chuanjiang_start_time,'%Y年%m月%d日 %H:%i')  start_time,DATE_FORMAT( p.chuanjiang_end_time,'%H:%i') end_time,l.`name` teacherName,l.head_img," +
                " p.chuanjiang_room_id direct_id ,ifnull(c.flowers_number,0) flowers_number,g.id gradeId  from oe_plan p,oe_lecturer l,oe_grade g,oe_course c where p.grade_id=g.id and p.chuanjiang_lecturer_id=l.id and g.course_id=c.id  and  p.id=:planId ";
            openCourseVos =this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
            if (openCourseVos.size()>0){
                course= openCourseVos.get(0);
            }

        }

        //获取主播礼物数目
        int count = giftDao.findByUserId((String) course.get("teacherId"));
        String rewardTotal = rewardDao.getRewardTotal(courseId.toString());
        course.put("giftCount", count);
        course.put("rewardTotal", rewardTotal==null?0:rewardTotal);
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
//        sqlbf.append(" select z.* from (select c.id , c.is_free as free,c.grade_name as courseName,c.smallimg_path,c.direct_id,IF ( c.is_free = 1, ( SELECT count(*) FROM apply_r_grade_course WHERE course_id = c.id ), ( SELECT sum(ifnull(student_count, 0)) + sum( ifnull(default_student_count, 0)) FROM oe_grade WHERE course_id = c.id AND is_delete = 0 AND STATUS = 1 )) learnd_count,c.external_links,c.direct_seeding,IF(ISNULL(c.`course_pwd`),0,1) coursePwd,");
//        sqlbf.append(" c.start_time as formatStartTime,ifnull(ou.name ,'暂无讲师') as teacherName,if(now()< c.start_time,2,1 ) as broadcastState");
////        sqlbf.append(" from oe_course c left join oe_lecturer l on c.lecturer_id = l.id where c.type=1 and c.is_delete=0 and c.status=1 and  now() <= c.end_time  order by c.sort desc  ) as z   limit 4 ");
//        sqlbf.append(" from oe_course c left join oe_user ou on c.user_lecturer_id = ou.id where c.type=1 and c.is_delete=0 and c.status=1 and  now() <= c.end_time  order by c.sort desc  ) as z  ");
        if(userId == null){
        	sqlbf.append("SELECT c.id,c.is_free AS free,c.description,c.grade_name AS courseName,c.smallimg_path,c.direct_id,IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) learnd_count,");
        	sqlbf.append("c.external_links,c.direct_seeding,IF(ISNULL(c.`course_pwd`), 0, 1) coursePwd,c.start_time AS formatStartTime,IFNULL(ou.name, '暂无讲师') AS teacherName,");
//        	if("test".equals(envFlag)){
//        		sqlbf.append("IF(NOW() < c.start_time, 2, IF(NOW() > c.end_time, 3, 1)) AS broadcastState  FROM oe_course c  LEFT JOIN oe_user ou  ON c.user_lecturer_id = ou.id  WHERE c.type = 1 ");
//        		sqlbf.append("AND c.is_delete = 0  AND c.status = 1 ORDER BY broadcastState ,c.start_time");
//        	}else{
        		sqlbf.append("c.live_status AS broadcastState  FROM oe_course c  LEFT JOIN oe_user ou  ON c.user_lecturer_id = ou.id  WHERE c.type = 1 ");
        		sqlbf.append("AND c.is_delete = 0  AND c.status = 1 ORDER BY broadcastState ,c.start_time");
//        	}
        }else{
        	sqlbf.append("SELECT c.id,c.is_free AS free,c.description,c.grade_name AS courseName,c.smallimg_path,c.direct_id,IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) learnd_count,");
        	sqlbf.append("c.external_links,c.direct_seeding,IF(ISNULL(c.`course_pwd`), 0, 1) coursePwd,c.start_time AS formatStartTime,IFNULL(ou.name, '暂无讲师') AS teacherName,");
        	sqlbf.append("IF(ISNULL(ocs.user_id),0,1) isSubscribe,");
//        	if("test".equals(envFlag)){
//        		sqlbf.append("IF(NOW() < c.start_time, 2, IF(NOW() > c.end_time, 3, 1)) AS broadcastState  FROM oe_course c  LEFT JOIN oe_user ou  ON c.user_lecturer_id = ou.id LEFT JOIN `oe_course_subscribe` ocs ON ocs.user_id = '"+userId+"' AND ocs.course_id = c.id ");
//        		sqlbf.append("WHERE c.type = 1 AND c.is_delete = 0  AND c.status = 1  ORDER BY broadcastState ,c.start_time");
//        	}else{
        		sqlbf.append("c.live_status AS broadcastState  FROM oe_course c  LEFT JOIN oe_user ou  ON c.user_lecturer_id = ou.id LEFT JOIN `oe_course_subscribe` ocs ON ocs.user_id = '"+userId+"' AND ocs.course_id = c.id ");
            	sqlbf.append("WHERE c.type = 1 AND c.is_delete = 0  AND c.status = 1  ORDER BY broadcastState ,c.start_time");
//        	}
        }
    
    
        if(num!=null&&num!=0){
            sqlbf.append("limit "+num);
        }
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sqlbf.toString(), BeanPropertyRowMapper.newInstance(OpenCourseVo.class));
    }

}
