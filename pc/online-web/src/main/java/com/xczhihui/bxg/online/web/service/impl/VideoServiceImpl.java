package com.xczhihui.bxg.online.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.VideoDao;
import com.xczhihui.bxg.online.web.service.ApplyService;
import com.xczhihui.bxg.online.web.service.VideoService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:18
 */
@Service
public class VideoServiceImpl extends OnlineBaseServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private CourseDao  courseDao;

    @Override
    public List<Map<String, Object>> getVideos(String id,String courseId,OnlineUser user,Boolean isTryLearn) {
        String userId = "";
        if(user!=null){
            userId = user.getId();
        }
        List<Map<String, Object>> knowledges;
        if(id!=null) {
            /** 根据节id查询所有知识点 */
            knowledges = videoDao.getKnowledgesBySectionId(id);
        }else{
            /** 没有传入节ID，默认获取第一章第一节知识点 */
            knowledges = videoDao.getFirstKnowledges(courseId);
        }
        for (Map<String, Object> knowledge : knowledges) {
            String knowledgeId = (String) knowledge.get("id");
            /** 根据知识点Id查询视频列表，拼装到知识点集合 */
            List<Map<String,Object>> videos;
            if(isTryLearn){
                videos = videoDao.getTryLearnVideos(knowledgeId);
            }else{
                videos = videoDao.getBuyVideos(knowledgeId,userId);
            }
            if(videos!=null && videos.size()>0) {
                knowledge.put("videos",videos);
            }
        }
        return knowledges;
    }
    
    /**
     * 视频播放页、试学页面、答题闯关、视频列表 姜海成
     * @param courseId
     * @param userId
     * @param isTryLearn
     * @return
     */
    @Override
    public List<Map<String, Object>> getvideos(Integer courseId, String userId, Boolean isTryLearn) {
    	
    	List<Map<String, Object>> returnmap = new ArrayList<Map<String, Object>>();
    	
    	//查询所有章节知识点
    	String sql = "select id,name,parent_id,level,barrier_id from oe_chapter "
    			+ "where is_delete=0 and course_id=? and level>1 order by sort";
    	List<Map<String, Object>> chapters = videoDao.getNamedParameterJdbcTemplate()
    			.getJdbcOperations().queryForList(sql, courseId);
    	
    	//查询本课程所有视频
    	List<Map<String, Object>> videos = null;
    	List<Map<String, Object>> barriers = null;//关卡
    	if (isTryLearn) {//试学
    		sql = "select t2.id,t2.chapter_id as chapterId,t2.name as videoName,t2.video_id as videoId,t2.video_time as videoTime,"+
    				"t2.video_size as videoSize,t2.course_id as courseId,t2.is_try_learn isLearn "+
    				" from oe_video t2 "+
    				" where t2.course_id = ? and t2.is_delete = 0  and t2.status=1 order by t2.sort";
    		videos = videoDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,courseId);
		} else {//播放页
			sql = "select t2.id,t2.chapter_id as chapterId,t2.name as videoName,t2.video_id as videoId,t2.video_time as videoTime,"+
					"t2.video_size as videoSize,t2.course_id as courseId,t2.is_try_learn isLearn,t1.study_status "+
					" from user_r_video t1, oe_video t2"+
					" where t1.video_id = t2.id and t1.user_id = ? and t1.course_id = ? and t1.is_delete = 0  and t1.status=1 order by t2.sort";
			videos = videoDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,userId,courseId);
			
			//查询本课程此用户的所有关卡
	    	sql = "select t1.*,t2.`name`,t2.kpoint_id from oe_barrier_user t1,oe_barrier t2 where t1.barrier_id=t2.id and t1.user_id=? "
	    			+ " and t1.course_id=? and t2.is_delete=0 and t2.status = 1 ";
	    	barriers = videoDao.getNamedParameterJdbcTemplate()
	    			.getJdbcOperations().queryForList(sql,userId, courseId);
		}
    	
    	//组装树形结构
    	for (Map<String, Object> zhangmap : chapters) {
    		//循环章
    		if (((Integer)zhangmap.get("level")) == 2) {
    			boolean zhangIsTry = false;
    			List<Map<String, Object>> zhangsons = new ArrayList<Map<String, Object>>();
    			//循环取节>>>>>
    			for (Map<String, Object> jiemap : chapters) {
    				if (jiemap.get("parent_id").equals(zhangmap.get("id"))) {
    					boolean jieIsTry = false;
    					List<Map<String, Object>> jiesons = new ArrayList<Map<String, Object>>();
    					//循环取知识点>>>>>
    	    			for (Map<String, Object> zhishidianmap : chapters) {
    	    				if (zhishidianmap.get("parent_id").equals(jiemap.get("id"))) {
    	    					boolean zhishidianIsTry = false;
    	    					
    	    					zhishidianmap.put("hasBarrier", "0");
    	    					Boolean videoHasLoker = false;//该关卡视频上有没有关卡
    	    					String barrierId = null;//barrierId = null表示这个知识点下的视频能看，否则不能看
    	    					
    	    					if (barriers != null && barriers.size() > 0) {
    	    						//取这个知识点上的关卡>>>>>
    	    						for (Map<String, Object> barriermap : barriers) {
    	    							Object bo = barriermap.get("kpoint_id");
    	    							if (bo != null && bo.equals(zhishidianmap.get("id").toString())) {
    	    								zhishidianmap.put("hasBarrier", "1");
	    									zhishidianmap.put("barrierName", barriermap.get("name"));
	    									zhishidianmap.put("barrierStatus", barriermap.get("barrier_status"));
	    									zhishidianmap.put("lockStatus", barriermap.get("lock_status"));
	    									break;
										}
    	    						}
    	    						//<<<<<取知这个识点上的关卡
    	    						
    	    						//判断这个知识点下的视频能不能看 >>>>>
    	    						Object pbo = zhishidianmap.get("barrier_id");
    	    						if (pbo != null && !"".equals(pbo.toString())) {
    	    							videoHasLoker = true;//有关卡
    	    							for (Map<String, Object> barriermap : barriers) {
    	    								if (barriermap.get("barrier_id").equals(pbo.toString())) {
    	    									if ("0".equals(barriermap.get("lock_status").toString())) {
    	    										barrierId = barriermap.get("id").toString();
    												break;
												}
    	    								}
    	    							}
									}
    	    						//<<<<<判断这个知识点下的视频能不能看
    	    					}
    	    					
    	    					//循环取视频>>>>>
    	    					List<Map<String, Object>> zhishidiansons = new ArrayList<Map<String, Object>>();
    	    	    			for (Map<String, Object> videomap : videos) {
    	    	    				if (videomap.get("chapterId").equals(zhishidianmap.get("id"))) {
    	    	    					if ((Boolean)videomap.get("isLearn") && !zhishidianIsTry && !jieIsTry && !zhangIsTry) {
    	    	    						zhishidianIsTry = true;
    	    	    						jieIsTry = true;
    	    	    						zhangIsTry = true;
										}
    	    	    					//该关卡视频上有没有关卡、是否可以看
    	    	    					if (videoHasLoker) {//有关卡
    	    	    						videomap.put("lockStatus", "1");//开锁
    	    	    						if(barrierId != null){
    	    	    							videomap.put("lockStatus", "0");//关锁
    	    	    							videomap.put("barrierId", barrierId);
    	    	    						}
										}
    	    	    					zhishidiansons.add(videomap);
    	    						}
    	    	    			}
    	    	    			//<<<<<循环取视频
    	    	    			zhishidianmap.put("videos", zhishidiansons);
    	    	    			zhishidianmap.put("isLearn", zhishidianIsTry);
    	    	    			jiesons.add(zhishidianmap);
    						}
    	    			}
    	    			jiemap.put("isLearn", jieIsTry);
    	    			jiemap.put("chapterSons", jiesons);
    	    			//<<<<<循环取知识点
    	    			zhangsons.add(jiemap);
					}
    			}
    			zhangmap.put("isLearn", zhangIsTry);
    			zhangmap.put("chapterSons", zhangsons);
    			//<<<<<循环取节
    			returnmap.add(zhangmap);
    		}
    	}
    	return returnmap;
    }

    @Override
    public Page<CriticizeVo> getVideoCriticize(String videoId, String name, Integer pageNumber, Integer pageSize) {
        
    	return videoDao.getVideoCriticize(videoId,name, pageNumber, pageSize);
    }

    @Override
    public void saveCriticize(CriticizeVo criticizeVo) {
        videoDao.saveCriticize(criticizeVo);
    }

    @Override
    public CriticizeVo findCriticizeById(String id) {
        if(!StringUtils.hasText(id)){
            return null;
        }
        return videoDao.findCriticizeById(id);
    }

    @Override
    public Map<String, Object> updatePraise(Boolean isPraise,String id,String  loginName) {
        /** 根据id查出当前评论 */
        CriticizeVo criticizeVo = videoDao.findCriticizeById(id);
        boolean praise = false;
        int sum = 0;
        Map<String,Object> returnMap = new HashMap<>();
        /** 点赞排除排除已经点赞的，记录点赞人 */
        if(criticizeVo!=null) {
            String praiseLoginNames = criticizeVo.getPraiseLoginNames();
            sum = criticizeVo.getPraiseSum();
            if (isPraise) {
                if (!StringUtils.hasText(praiseLoginNames) || !praiseLoginNames.contains(loginName)) {
                    criticizeVo.setPraiseSum(++ sum);
                    praise = true;
                    if(!StringUtils.hasText(praiseLoginNames)) {
                        criticizeVo.setPraiseLoginNames(loginName);
                    }else{
                        criticizeVo.setPraiseLoginNames(praiseLoginNames + "," + loginName);
                    }
                    videoDao.praise(criticizeVo);
                }
            } else {
                if (criticizeVo.getPraiseLoginNames().contains(loginName)) {
                    criticizeVo.setPraiseSum(-- sum);
                    praiseLoginNames = praiseLoginNames.replace(","+loginName, "").replace(loginName, "");
                    criticizeVo.setPraiseLoginNames(praiseLoginNames);
                    videoDao.praise(criticizeVo);
                }
            }
        }
        returnMap.put("praise",praise);
        returnMap.put("praiseSum",sum);
        return returnMap;
    }

    @Override
    public void updateStudyStatus(String studyStatus, String videoId, String userId) {
        videoDao.updateStudyStatus(studyStatus, videoId, userId);
    }

    @Override
    public List<Map<String, Object>> getLearnedUser(String id) {
        return videoDao.getLearnedUser(id);
    }

    @Override
    public List<Map<String, Object>> getPurchasedUser(String id) {
        return videoDao.getPurchasedUser(id);
    }

    /**
     * 免费课程 用户报名时，将课程下所有视频插入用户视频中间表中
     * @param courseId 课程id
     * @return
     */
    @Override
    public  String saveEntryVideo(Integer  courseId, String password, HttpServletRequest request){
           CourseApplyVo courseApplyVo= courseDao.getCourseApplyByCourseId(courseId);
//           if (courseApplyVo !=null && Double.valueOf(courseApplyVo.getOriginalCost())==0 && Double.valueOf(courseApplyVo.getCurrentPrice())==0){
    	   if(courseApplyVo.getCoursePwd()!=null&&!"".equals(courseApplyVo.getCoursePwd().trim())){
    		   if(password==null || !password.equals(courseApplyVo.getCoursePwd())) {
                   return "密码错误";
               }
    	   }
           if (courseApplyVo !=null && Double.valueOf(courseApplyVo.getCurrentPrice())==0){
               videoDao.saveEntryVideo(courseId,request);
               return "报名成功";
           }
           throw new RuntimeException(String.format("课程下架或非法操作"));
    }

    @Override
    public  String   findVideosByCourseId(Integer courseId){
    	/*20170810---yuruixin*/
    	CourseVo course = courseDao.findCourseOrderById(courseId);
        if(course.getType()==null && (course.getDirectId() == null || "".equals(course.getDirectId().trim())) ){
            throw new RuntimeException(String.format("视频正在来的路上，请稍后购买"));
        }
        return "购买";
    }

    public static void main(String[] args) {
    	int sum =1;
    	++sum;
    	System.out.println(sum);
	}
}
