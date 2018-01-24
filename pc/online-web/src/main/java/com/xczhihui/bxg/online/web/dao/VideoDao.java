package com.xczhihui.bxg.online.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.ApplyService;
import com.xczhihui.bxg.online.web.vo.ChapterLevelVo;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.UserVideoVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;

/**
 * 视频相关功能数据访问层
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:22
 */
@Repository
public class VideoDao extends SimpleHibernateDao {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserCenterDao userCenter;//DAO
    @Autowired
    private  OrderDao  orderDao;
    

    /**
     * 获取章级别菜单(章节知识点信息)
     * @param courseId  课程id
     * @return 章信息集合
     */
    public List<ChapterLevelVo>  findChapterInfo(Integer courseId) {
        String sql = "select id, name from oe_chapter  where  course_id = ?  and   `level`=2  and is_delete =0  order by sort asc";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(ChapterLevelVo.class),courseId);
    }
    /**
     * 获取节级别菜单(章节知识点信息)
     * @param parentId
     * @return 节信息集合
     */
    public List<ChapterLevelVo>  findSectionInfo(String parentId) {
        String sql = "select id, name from oe_chapter  where  parent_id = ?  and   `level`=3  and is_delete =0 order by sort asc";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(ChapterLevelVo.class),parentId);
    }
    /**
     * 获取知识点级别菜单(章节知识点信息)
     * @param parentId
     * @return 节信息集合
     */
    public List<ChapterLevelVo>  findKnowledgeInfo(String parentId) {
        String sql = "select id, name from oe_chapter  where  parent_id = ?  and   `level`=4  and is_delete =0 order by sort asc";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(ChapterLevelVo.class),parentId);
    }
    /**
     * 根据节Id查询所有知识点
     * @param id
     * @return
     */
    public List<Map<String, Object>> getKnowledgesBySectionId(String id){
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name from oe_chapter where parent_id=:id and status=1 and is_delete=0");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
    }
    /**
     * 没有传入节ID，默认获取第一章第一节知识点
     * @return
     */
    public List<Map<String, Object>> getFirstKnowledges(String courseId){
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name from oe_chapter where parent_id =");
        sql.append(" (select id from oe_chapter where parent_id=(select id from oe_chapter where status=1 and is_delete=0 and LEVEL=2 and course_id =:courseId order by sort limit 1)");
        sql.append(" and status=1 and is_delete=0 order by sort limit 1) and status=1 and is_delete=0");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId",courseId);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
    }
    /**
     * 获取用户购买页知识点下视频列表
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getBuyVideos(String id,String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ov.id,ov.chapter_id chapterId,ov.name as videoName,");
        sql.append(" ov.video_id videoId,ov.video_time videoTime,ov.video_size videoSize,");
        sql.append(" ov.course_id courseId,ov.is_try_learn isLearn ");
        sql.append(" from oe_video ov");
        sql.append(" LEFT JOIN user_r_video urv on ov.id=urv.video_id");
        sql.append(" where ov.STATUS=1 and ov.chapter_id =:id and urv.user_id =:userId");
        sql.append(" ORDER BY ov.is_try_learn DESC,ov.sort DESC");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("userId",userId);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
    }
    /**
     * 获取全部试学的视频
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getTryLearnVideos(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ov.id,ov.chapter_id chapterId,ov.name as videoName,");
        sql.append(" ov.video_id videoId,ov.video_time videoTime,ov.video_size videoSize,");
        sql.append(" ov.course_id courseId,ov.is_try_learn isLearn ");
        sql.append(" from oe_video ov where STATUS=1 and is_delete=0 and ov.chapter_id =:id and is_try_learn=1");
        sql.append(" ORDER BY ov.is_try_learn DESC,ov.sort DESC");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
    }

    /**
     * 获取免费试学页知识点下的视频列表
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getAllLearnVideos(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ov.id,ov.chapter_id chapterId,ov.name as videoName,");
        sql.append(" ov.video_id videoId,ov.video_time videoTime,ov.video_size videoSize,");
        sql.append(" ov.course_id courseId,ov.is_try_learn isLearn ");
        sql.append(" from oe_video ov where STATUS=1 and is_delete=0 and ov.chapter_id =:id ");
        sql.append(" ORDER BY ov.sort DESC");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
    }
    /**
     * 获取所有对该视频的评论
     * @param videoId 视频ID
     * @return
     */
    public Page<CriticizeVo> getVideoCriticize(String videoId,String name, Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 15 : pageSize;
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append(" select oc.id,oc.content,oc.video_id videoId,oc.star_level starLevel,oc.praise_sum praiseSum,oc.create_time createTime,");
        sql.append(" (select name from oe_user where id=oc.user_id) as userName,oc.user_id as userId,");
        sql.append(" IFNULL((FIND_IN_SET(:userName,oc.praise_login_names)>0),0) isPraise,"); //isPraise 判断是否点赞呢
        sql.append(" (select small_head_photo from oe_user where id=oc.user_id) as smallPhoto,");
        sql.append(" oc.praise_login_names as praiseLoginNames,oc.response, oc.response_time response_time");
        sql.append(" from oe_criticize oc where oc.STATUS=1 and oc.is_delete=0 and oc.video_id =:videoId");
        sql.append(" ORDER BY oc.create_time DESC");
        paramMap.put("userName", name);
        paramMap.put("videoId", videoId);
        return this.findPageBySQL(sql.toString(), paramMap,CriticizeVo.class,pageNumber, pageSize);
    }

    /**
     * 提交评论
     */
    public void saveCriticize(CriticizeVo criticizeVo) {
    	if (!StringUtils.hasText(criticizeVo.getUserId()) 
    			|| !StringUtils.hasText(criticizeVo.getVideoId()) || criticizeVo.getCourseId() == null) {
			throw new RuntimeException("参数错误！");
		}
        criticizeVo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        String sql = "insert into oe_criticize (id,create_person,create_time,content,"
                + "user_id,chapter_id,video_id,star_level,course_id) "
                + "values (:id,:createPerson,:createTime,:content,:userId,"
                + ":chapterId,:videoId,:starLevel,:courseId)";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(criticizeVo));
    }

    /**
     * 根据ID查询评论
     */
    public CriticizeVo findCriticizeById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,create_person createPerson,create_time createTime,content,user_id userId,");
        sql.append("chapter_id chapterId,video_id videoId,star_level starLevel,praise_sum praiseSum,praise_login_names praiseLoginNames ");
        sql.append("from oe_criticize where id=:id");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<CriticizeVo> list = this.findEntitiesByJdbc(CriticizeVo.class,sql.toString(),paramMap);
        return list.size()>0? list.get(0) : null;
    }

    /**
     * 点赞、取消点赞
     */
    public void praise(CriticizeVo criticizeVo) {
        String sql = "update oe_criticize set praise_sum =:praiseSum, praise_login_names =:praiseLoginNames where id =:id";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(criticizeVo));
    }
    /**
     * 修改学员视频学习状态,记录最后学习时间
     */
    public void updateStudyStatus(String studyStatus,String videoId,String userId) {
        StringBuffer sql = new StringBuffer();
        Map<String,Object> paramMap = new HashMap<>();
        sql.append(" update user_r_video set study_status =:studyStatus,last_learn_time=now() where video_id =:videoId and user_id =:userId");
        if("2".equalsIgnoreCase(studyStatus)){
            sql.append(" and study_status=0");
        }else{
            sql.append(" and study_status=2");
        }
        paramMap.put("studyStatus", studyStatus);
        paramMap.put("videoId", videoId);
        paramMap.put("userId", userId);
        this.getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
    }
    /**
     * 根据视频id获取学习过的学员
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getLearnedUser(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ov.name videoName,ou.name userName,");
        sql.append(" (select small_head_photo from oe_user where id=urv.user_id) as smallPhoto");
        sql.append(" from user_r_video urv LEFT JOIN oe_video ov on urv.video_id=ov.id");
        sql.append(" LEFT JOIN oe_user ou on urv.user_id=ou.id");
        sql.append(" where urv.STATUS=1 and urv.is_delete=0 and urv.study_status=1 and urv.video_id =:id ORDER BY RAND() limit 0,8");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
    }
    /**
     * 根据课程id获取购买过该课程的学员
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getPurchasedUser(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT oo.pay_time time,ou.name userName,");
        sql.append(" ou.small_head_photo as smallPhoto");
        sql.append(" from oe_order_detail od,oe_order oo");
        sql.append(" JOIN oe_user ou on oo.user_id=ou.id");
//        sql.append(" LEFT JOIN oe_user ou on oo.user_id=ou.id");
        sql.append(" where oo.id=od.order_id and  oo.is_delete=0 and oo.order_status=1 and od.course_id =:id ORDER BY RAND() limit 0,8");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
    }

    /**
     * 查看当前登陆用户是否购买过当前课程
     * @param courseId 课程id
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getUserCourse(Integer courseId,String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT urv.course_id ");
        sql.append(" from user_r_video urv ");
        sql.append(" where urv.is_delete=0 and urv.course_id =:courseId and urv.user_id=:userId  limit 1");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId",courseId);
        paramMap.put("userId",userId);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(),paramMap);
    }



    /**
     * 免费课程 用户报名时，将课程下所有视频插入用户视频中间表中
     * @param courseId 课程id
     * @return
     */
    public  void saveEntryVideo(Integer  courseId,HttpServletRequest  request){
         OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");
         Map<String,Object> paramMap = new HashMap<>();
         paramMap.put("courseId",courseId);
         paramMap.put("userId",u.getId());
         paramMap.put("loginName",u.getLoginName());
         //查看报名的课程是否存在
         CourseApplyVo course= courseDao.getCourseApplyByCourseId(courseId);
         if(course == null){
             throw new RuntimeException("对不起,您要报的课程已下架!");
         }
         //查看用户是否已经报过此课程
         if( this.getUserCourse(courseId,u.getId()).size()>0){
             throw new RuntimeException("同学,您已经报名了!");
         };
        //1、查看当前课程下的所有视频
        String  querySql="select id as video_id  from oe_video where course_id=:courseId and is_delete=0 and status=1";
        List<UserVideoVo>  videos = this.findEntitiesByJdbc(UserVideoVo.class, querySql, paramMap);
		//yuruixin-20170811
        DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
		dc.add(Restrictions.eq("id", courseId));
        Course c = orderDao.findEntity(dc);
//        if((c.getType()==null||c.getType()!=1) && videos.size()<= 0){
        if((c.getType()==null||c.getType()!=1) && (c.getDirectId()==null|| "".equals(c.getDirectId()))){
            throw new RuntimeException("此课程下没有相关视频!");
        };
        String sql="";
        //写用户报名信息表，如果有就不写了
        String apply_id = UUID.randomUUID().toString().replace("-", "");
        sql = "select id from oe_apply where user_id=:userId ";
        List<Map<String, Object>> applies = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        if (applies.size() > 0) {
            apply_id = applies.get(0).get("id").toString();
        } else {

            sql = "insert into oe_apply(id,user_id,create_time,is_delete,create_person) "
                    + " values ('"+apply_id+"',:userId,now(),0,:loginName)";
            orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
        }

        //写用户报名中间表
        String id = UUID.randomUUID().toString().replace("-", "");
        sql = "select (ifnull(max(cast(student_number as signed)),'0'))+1 from apply_r_grade_course where grade_id=-1";
        Integer no = orderDao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
        String sno = no < 10 ? "00"+no : (no < 100 ? "0"+no : no.toString());
        sql = "insert into apply_r_grade_course (id,course_id,grade_id,apply_id,is_payment,create_person,user_id,create_time,cost,student_number)"
                + " values('"+id+"',:courseId,-1,'"+apply_id+"',0,:loginName,:userId,now(),0,'"+sno+"')";
        orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);

        //写用户视频表
//        sql = "insert into user_r_video (id,create_person,sort,video_id,user_id,course_id) "
//                + " select uuid(),'"+u.getLoginName()+"',sort,id,'"+u.getId()+"',course_id "
//                + "from oe_video where course_id=:courseId and is_delete=0 and status=1 ";
//        orderDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    }


    /**
     * 用户购买视频，将课程下所有视频插入用户视频中间表中
     * @param orderNo 订单号
     * @return
     */
   /* public  void savePurchaseVideo(String  orderNo){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("orderNo",orderNo);
        OrderVo  orderVo= orderDao.findOrderByOrderNo(orderNo);
        if (this.getUserCourse(orderVo.getCourse_id(), orderVo.getUser_id()).size() > 0) {
			return;
		}
        
        OnlineUser user =  userCenter.getUser(orderVo.getUser_id());
       *//**
         * 如果是收费的点播课，先得查询此课程下正在报名中的班级信息
         *   1、如果有报名中的班级，那么就将学员添加到此班级下
         *   2、如果没有报名中的班级，将用户填到 0班,  此班级不存在
         *//*
        paramMap.put("courseId",orderVo.getCourse_id());
        String sql="  select id from oe_grade  where is_delete=0  and grade_status = 1 and   course_id =:courseId and "+
                   "    unix_timestamp(NOW()) <=  unix_timestamp(curriculum_time)   order by  curriculum_time  limit 1  ";
        List<Map<String, Object>> listGrade = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        if(listGrade !=null && listGrade.size() > 0){
            applyService.saveApply(user,orderVo.getCourse_id(),Integer.valueOf(listGrade.get(0).get("id").toString()));
        }else{
            //在这里，用户已经支付成功了，分配班级时，发现没有班级了
            //为了不影响用户学习，我们先给用户分配到 0 班，但这个班是不存在的
            applyService.saveApply(user,orderVo.getCourse_id(),0);
        }
        
        user.setApply(true);
        userCenter.updateUser(user);

        //1、查询当前课程下所有视频
        String  querySql="select id as video_id ,course_id as courseId from oe_video where course_id=:courseId and is_delete=0 and status=1 ";
        List<UserVideoVo>  videos = this.findEntitiesByJdbc(UserVideoVo.class, querySql, paramMap);
        //2、循环此课程下的所有视频，将视频信息插入用户视频中间表，归用户所有
        if( !CollectionUtils.isEmpty(videos) && videos.size() > 0){
            for (UserVideoVo  video : videos){
                video.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                video.setUser_id(user.getId());
                video.setCreate_person(user.getLoginName());
                video.setCourse_id(orderVo.getCourse_id());
                String saveSql = " insert into user_r_video (id,create_person,video_id,user_id,course_id) "
                        + " values (:id,:create_person,:video_id,:user_id,:course_id) ";
                this.getNamedParameterJdbcTemplate().update(saveSql, new BeanPropertySqlParameterSource(video));
            }
        }

    }*/
    public  List<Map<String, Object>>  findVideosByCourseId(Integer courseId){
        //1、查询当前课程下所有视频
        String  querySql="select id as video_id  from oe_video where course_id=? and is_delete=0 and status=1";
        List<Map<String, Object>>  check = this.getNamedParameterJdbcTemplate().getJdbcOperations() .queryForList(querySql, courseId);
        return  check;
    }
	public void saveNewCriticize(CriticizeVo criticizeVo) {
		// TODO Auto-generated method stub
    	if (!StringUtils.hasText(criticizeVo.getUserId()) && 
    			criticizeVo.getCourseId() == null) {
			throw new RuntimeException("参数错误！");
		}
		criticizeVo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		String sql = "insert into oe_criticize (id,create_person,content,"
		        + "user_id,course_id,content_level,deductive_level,criticize_lable,"
		        + "overall_level) "
		        + "values (:id,:createPerson,:content,:userId,"
		        + ":courseId,:contentLevel,:deductiveLevel,:criticizeLable,"
		        + ":overallLevel)";
		this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(criticizeVo));
		
		
	}
	public void saveReply(String content, String criticizeId, String userId) {
		// TODO Auto-generated method stub
		if (!StringUtils.hasText(criticizeId) ||
				!StringUtils.hasText(criticizeId) || !StringUtils.hasText(userId)) {
			throw new RuntimeException("参数错误！");
		}
		String replyId = UUID.randomUUID().toString().replaceAll("-", "");
		String sql = "insert into oe_reply (id,create_person,reply_content,"
		        + "reply_user,criticize_id) "
		        + "values (:id,:createPerson,:content,:userId,"
		        + ":criticizeId)";
		 Map<String,Object> params=new HashMap<String,Object>();
		 params.put("id", replyId);
		 params.put("createPerson", userId);
		 params.put("content", content);
		 params.put("userId", userId);
		 params.put("criticizeId", criticizeId);
		 this.getNamedParameterJdbcTemplate().update(sql,params);
	}

	 /**
     * 获取主播的或者课程的评价数
     * @param videoId 视频ID
     * @return
     */
    public Page<Criticize> getUserCriticize(String userId,String courseId, Integer pageNumber, Integer pageSize) {
        Map<String,Object> paramMap = new HashMap<>();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        
        if(courseId !=null || userId!=null){
           StringBuffer sql = new StringBuffer("select c from Criticize c  where c.status = 1 ");
	       if(org.apache.commons.lang.StringUtils.isNotBlank(userId)){
	       	  sql.append("  and c.userId =:userId ");
	       	  paramMap.put("userId", userId);
	       }else{
	       	  sql.append("  and c.courseId =:courseId ");
	       	  paramMap.put("courseId", Integer.parseInt(courseId));
	       }
	       //sql.append(" limit "+pageNumber+","+pageSize);
	       //IFNULL((FIND_IN_SET(:userName,oc.praise_login_names)>0),0) isPraise
	       
	        System.out.println(sql.toString());
	        Page<Criticize>  criticizes = this.findPageByHQL(sql.toString(),paramMap,pageNumber,pageSize);
            
	        if(criticizes.getTotalCount()>0){
	        	OnlineUser u = this.get(userId,OnlineUser.class);
	        	 for (Criticize c : criticizes.getItems()) {
	 	        	String loginNames =  c.getPraiseLoginNames();
	 	        	boolean isPraise = false;
	 	        	if(org.apache.commons.lang.StringUtils.isNotBlank(loginNames)){
	 	        		for (String loginName : loginNames.split(",")) {
	 						if(u.getLoginName().equals(loginName)){
	 							isPraise =  true;
	 						}
	 					}
	 	        	}
	 	        	c.setPraise(isPraise);
	 			}
	        }
	        System.out.println(criticizes.getItems());
            return criticizes;
        }
        return  null;
    }
	
	


}
