package com.xczhihui.bxg.online.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.common.util.enums.CourseForm;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.ChapterLevelVo;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.UserVideoVo;

/**
 * 视频相关功能数据访问层
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:22
 */
@Repository
public class VideoDao extends SimpleHibernateDao {

    @Autowired
    private CourseDao courseDao;
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
        sql.append("select id,create_person createPerson,create_time createTime,content,user_id userId,course_id courseId,");
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
     * 查看当前登录用户是否购买过当前课程
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
        if(c.getType()== CourseForm.VOD.getCode() && (c.getDirectId()==null|| "".equals(c.getDirectId())) && !c.getCollection()){
            throw new RuntimeException("此课程下没有相关视频!");
        };
        String sql="";
        //写用户报名信息表，如果有就不写了
        String apply_id = UUID.randomUUID().toString().replace("-", "");
        sql = "select id from oe_apply where user_id=:userId ";
        List<Map<String, Object>> applies = orderDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
      
        try {
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
        	
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("网络有点忙,请稍等!");
		}

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
         * 如果是付费的点播课，先得查询此课程下正在报名中的班级信息
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

		try {
			if (!StringUtils.hasText(criticizeVo.getUserId()) && 
	    			criticizeVo.getCourseId() == null) {
				throw new RuntimeException("参数错误！");
			}
			String sql = "insert into oe_criticize (id,create_person,content,"
			        + "user_id,course_id,content_level,deductive_level,criticize_lable,"
			        + "overall_level,is_buy) "
			        + "values (:id,:createPerson,:content,:userId,"
			        + ":courseId,:contentLevel,:deductiveLevel,:criticizeLable,"
			        + ":overallLevel,:isBuy)";
			this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(criticizeVo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败！");
		}
	}
	public void saveReply(String content, String userId,String criticizeId,Integer collectionId) {
		
		try {
			
			if (!StringUtils.hasText(content) || !StringUtils.hasText(userId)) {
				throw new RuntimeException("参数错误！");
			}
			/*
			 * 回复此评论的人
			 */
			CriticizeVo cvo = this.findCriticizeById(criticizeId);
			if (cvo==null) {
				throw new RuntimeException("获取此条评论信息有误！");
			}
			/**
			 * 回复其实也是一个评论
			 *   插入一个评论
			 *   插入一个回复
			 */
			CriticizeVo criticizeVo = new CriticizeVo();
			criticizeVo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			criticizeVo.setContent(content);
			criticizeVo.setCreatePerson(userId);
			criticizeVo.setUserId(cvo.getUserId());
			criticizeVo.setCourseId(cvo.getCourseId());
			
			/**
			 * 如果这个是免费的就没有必要的
			 */
			
			boolean isbuy = this.checkUserIsBuyCourse(cvo.getCourseId(),userId,collectionId);
			criticizeVo.setIsBuy(isbuy);
			
			this.saveNewCriticize(criticizeVo);
			/**
			 * 然后在这个评论中增加一个回复
			 */
			String replyId = UUID.randomUUID().toString().replaceAll("-", "");
			String sql = "insert into oe_reply (id,create_person,reply_content,"
			        + "reply_user,criticize_id) "
			        + "values (:id,:createPerson,:content,:reply_user,"
			        + ":criticizeId)";
			 Map<String,Object> params=new HashMap<String,Object>();
			 params.put("id", replyId);

			 params.put("createPerson", userId);     //
			 params.put("content", cvo.getContent());         //内容
			 params.put("reply_user", cvo.getCreatePerson());     //当前被回复的评论id是这个回复创建人
			 params.put("criticizeId", criticizeVo.getId());  //此条评论的人
			 this.getNamedParameterJdbcTemplate().update(sql,params);
			
		}catch (Exception e) {
			e.printStackTrace();
			
			throw new RuntimeException("回复失败！");
		}
	}

	 /**
     * 获取主播的或者课程的评价数
     * @return
	 * @throws IllegalAccessException 
     */
    public Page<Criticize> getUserOrCourseCriticize(String teacherId,Integer courseId,
    		Integer pageNumber, Integer pageSize,String userId){
        
    	try {
    		
    		Map<String,Object> paramMap = new HashMap<>();
            pageNumber = pageNumber == null ? 1 : pageNumber;
            pageSize = pageSize == null ? 10 : pageSize;
            /**
             * 购买者这里怎么显示了啊。好尴尬了，不能用多个循环吧，不然会卡点呢
             * 	 或者是购买成功	
             * 
             * 一个专辑下存在多个课程，然后课程
             */
            if(courseId !=null || teacherId!=null){
               StringBuffer sql = new StringBuffer("select c from Criticize c  where c.status = 1  and c.onlineUser is not null  ");
    	       
               if(org.apache.commons.lang.StringUtils.isNotBlank(teacherId)){
    	       	  sql.append("  and c.userId =:userId ");
    	       	  paramMap.put("userId", teacherId);
    	       }else if(courseId!=null && courseId!=0){
    	    	  //查找这个课程是不是专辑、如果是专辑就 用in来查找啦
    	    	  List<Integer> list =  getCoursesIdListByCollectionId(courseId);
    	    	  
    	    	  if(list.size()>0){
    	    		  list.add(courseId);
    	    		  String str = "";
    	    		  for (int i = 0; i < list.size(); i++) {
    					Integer array_element = list.get(i);
    					if(i == list.size()-1){
    						str +=array_element;
    					}else{
    						str +=array_element+",";
    					}
    				  }
    	    		  sql.append("  and c.courseId in ("+str+") ");
    	    	  }else{
    	    		  sql.append("  and c.courseId =:courseId ");
    		       	  paramMap.put("courseId",courseId);
    	    	  } 
    	       }
    	        sql.append(" order by c.createTime desc ");

    	        Page<Criticize>  criticizes = this.findPageByHQL(sql.toString(),paramMap,pageNumber,pageSize);
    	        
    	        if(criticizes.getTotalCount()>0){
    	        	String loginName = "";
    	        	if(userId!=null){
                        //这里就查询了一次，所是ok的。这是不是需要查询下。
    	        		OnlineUser u = this.get(userId,OnlineUser.class);
    	        		loginName = u.getLoginName();
    	        	}
    	        	 for (Criticize c : criticizes.getItems()) {
    	        		/**
    	        		 * 判断会否点过赞 
    	        		 */
    	        		String loginNames =  c.getPraiseLoginNames();
    	 	        	Boolean isPraise = false;
    	 	        	if(org.apache.commons.lang.StringUtils.isNotBlank(loginNames)){
    	 	        		for (String loginName1 : loginNames.split(",")) {
    	 						if(loginName.equals(loginName1)){
    	 							isPraise =  true;
    	 						}
    	 					}
    	 	        	}
    	 	        	c.setIsPraise(isPraise);
    	 	        	/**
    	        		 * 星级的平均数
    	        		 */
    	 	        	if(c.getOverallLevel()!=null&& !"".equals(c.getOverallLevel()) && c.getOverallLevel()!=0){
    	 	        		
    	 	        		System.out.println("c.getOverallLevel():"+c.getOverallLevel());
    	 	        		System.out.println("c.getContentLevel():"+c.getContentLevel());
    	 	         		System.out.println("c.getDeductiveLevel():"+c.getDeductiveLevel());
    	 	         		
                            BigDecimal totalAmount = new BigDecimal(c.getOverallLevel() !=null ? c.getOverallLevel() :0 );
                            totalAmount =totalAmount.add(new BigDecimal(c.getContentLevel()  !=null ? c.getContentLevel() :0 ));
                            totalAmount =totalAmount.add(new BigDecimal(c.getDeductiveLevel()  !=null ? c.getDeductiveLevel() :0 ));
                          
                            String startLevel = "0";
                            try {
                                startLevel = divCount(totalAmount.doubleValue(),3d,1);
                                System.out.println("startLevel:"+startLevel);
                                c.setStarLevel(Float.valueOf(startLevel));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
    	 			}
    	        }
                return criticizes;
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("获取评论列表有误!");
		}
        return  null;
    }

	/**
	 * Description：求平均值，并且把小数点的都截取到5，或者大于5的
	 * @param value1
	 * @param value2
	 * @param scale
	 * @return
	 * @throws IllegalAccessException
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
    public static String divCount(double value1,double value2,int scale) throws IllegalAccessException{  
        //如果精确范围小于0，抛出异常信息  
        if(scale<0){           
            throw new IllegalAccessException("精确度不能小于0");  
        }  
        
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));  
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2)); 
        /**
         * 得到平均数，保留以为小数
         */
        BigDecimal b3 =  b1.divide(b2, 1, BigDecimal.ROUND_HALF_UP);
          
        return criticizeStartLevel(b3.doubleValue()).toString();
    }  
	
    
	public static Double criticizeStartLevel(Double startLevel) {

		if (startLevel != null && startLevel != 0) { // 不等于0
			String b = startLevel.toString();
			if (b.length() > 1
					&& !b.substring(b.length() - 1, b.length()).equals("0")) { // 不等于整数
				String[] arr = b.split("\\.");
				Integer tmp = Integer.parseInt(arr[1]);
				if (tmp >= 5) {
					return (double) (Integer.parseInt(arr[0]) + 1);
				} else {
					return Double.valueOf(arr[0] + "." + 5);
				}
			} else {
				return startLevel;
			}
		}
		return startLevel;
	}
    
    
    /**
     * Description：判断这个星级用户是否购买过这个课程以及判断是否已经星级评论了一次此课程
     * @param courseId
     * @return
     * @return Integer  返回参数： 0 未购买     1 购买了，但是没有星级评论过     2 购买了，也星级评论了
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
    public Integer findUserFirstStars(Integer courseId,String createPerson) {
        
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        paramMap.put("createPerson", createPerson);
        
        Integer isViewStars = 0;
        
    	StringBuffer sqlStr = new StringBuffer();
    	sqlStr.append(" SELECT count(*) as count from apply_r_grade_course  argc where argc.is_delete=0 and argc.course_id =:courseId "); 
    	sqlStr.append(" and argc.user_id=:createPerson  ");
    	List<Map<String, Object>> listArgs= this.getNamedParameterJdbcTemplate().queryForList(sqlStr.toString(), paramMap);
    	/*
    	 * 判断是不是等于： 1
    	 * 付费的有没有购买过
    	 * 免费的有没有观看过
    	 */
        if(listArgs.size()>0){
            String  count = listArgs.get(0).get("count").toString();
            int s =Integer.parseInt(count);
            if(s>0){
                isViewStars=1;
            }
        }
        
        /*
         * 判断是不是等于： 2
         *   有没有星星评论
         */
        if(isViewStars==1){
        	StringBuffer sql = new StringBuffer();
	        sql.append("select criticize_lable ");
	        sql.append(" from oe_criticize where course_id=:courseId and create_person=:createPerson ");
	        List<Map<String, Object>> list= this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
        	
	        if(list.size()>0){ //评论过
	            for(int i=0;i<list.size();i++){  
	                if(list.get(i).get("criticize_lable")!=null&&!list.get(i).get("criticize_lable").equals("")){
	                    isViewStars=2;
	                    break;
	                }
	            }
	        }
        }
        
        return isViewStars;
    }
    
    
    /**
     * 
     * Description：通过课程id得到这个专辑的信息
     * @param courseId
     * @return
     * @return List<Integer>
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
    public List<Integer>  getCoursesIdListByCollectionId(Integer courseId) {
    	
        String sql="SELECT \n" +
                "  oc.`id` \n"+ 
                "FROM\n" +
                "  `oe_course` oc \n" +
                "  JOIN `collection_course` cc \n" +
                "    ON oc.id = cc.`course_id` \n" +
                "WHERE cc.`collection_id` = "+courseId+" \n";
        List<Integer> list = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, Integer.class);
        return list;
       
    }
    
    
    /**
     * 
     * Description：判断用户是否已经购买过
     * @param courseId
     * @param userId
     * @return
     * @return Boolean true 购买  false 未购买
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
    public Boolean  checkUserIsBuyCourse(Integer courseId,String userId,Integer collectionId) {
        StringBuffer sql = new StringBuffer();
   	    sql.append(" SELECT count(*) from apply_r_grade_course  argc where argc.is_delete=0 "
 	 		+ "and argc.course_id = ?  ");
       sql.append(" and argc.user_id=?  and argc.order_no is not null limit 1 ");
        
        int count = 0;
        if(collectionId!=null){
        	 Object [] obj  ={collectionId,userId};
             count =  this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(
             		sql.toString(),Integer.class,obj);
        }else{
        	 Object [] obj  ={courseId,userId};
             count =  this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(
             		sql.toString(),Integer.class,obj);
        }
        if(count>0){
        	return true;
        }else{
        	return false;
        }
    }
    
    
    


}
