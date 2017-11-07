package com.xczhihui.bxg.online.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.BarrierVo;
import com.xczhihui.bxg.online.web.vo.ChapterPointVo;
import com.xczhihui.bxg.online.web.vo.ChapterVo;

/**
 * 视频章节表控制层
 *
 * @author 康荣彩
 * @create 2016-11-02 21:34
 */
@Repository
public class ChapterDao extends SimpleHibernateDao {

    /**
     * 获取第一级菜单(章节信息)
     * @param courseId  课程id
     * @param request 
     * @return 章节信息集合
     */
    public List<ChapterVo>  findChapterInfo(Integer courseId, HttpServletRequest request) {
    	//获取登录用户
        BxgUser loginUser = UserLoginUtil.getLoginUser(request);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        if(loginUser != null ){ //用户登录，查看用户是否购买此课程,报名isApply=true,否则isApply=false
            paramMap.put("userId",loginUser.getId());
            String  uSql="select id from user_r_video  where  user_id=:userId and course_id=:courseId  limit 1";
            List<Map<String, Object>> listVideo= this.getNamedParameterJdbcTemplate().queryForList(uSql, paramMap);
//            courseVo.setIsApply(listVideo.size() > 0 ? true : false);
            if(listVideo.size() > 0){
            	String sql = "select id, name from oe_chapter  where  course_id = ?  and   `level`=2  and is_delete =0   order by sort asc";
            	return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(ChapterVo.class),courseId);
            }
        }
        return null;
    }
    
    /**
     * 获取第一级菜单(章节信息)
     * @param courseId  课程id
     * @param request 
     * @return 章节信息集合
     */
    public List<ChapterVo>  findChapterInfoForApi(Integer courseId, HttpServletRequest request) {
    	//获取登录用户
    	BxgUser loginUser = UserLoginUtil.getLoginUser(request);
    	Map<String,Object> paramMap = new HashMap<>();
    	paramMap.put("courseId", courseId);
    	if(loginUser != null ){ //用户登录，查看用户是否购买此课程,报名isApply=true,否则isApply=false
    		paramMap.put("userId",loginUser.getId());
    		String  uSql="select id from user_r_video  where  user_id=:userId and course_id=:courseId  limit 1";
    		List<Map<String, Object>> listVideo= this.getNamedParameterJdbcTemplate().queryForList(uSql, paramMap);
//            courseVo.setIsApply(listVideo.size() > 0 ? true : false);
    		if(listVideo.size() > 0){
    			String sql = "select id, name from oe_chapter  where  course_id = ?  and   `level`=2  and is_delete =0   order by sort asc";
    			return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(ChapterVo.class),courseId);
    		}
    	}
    	return null;
    }


    /**
     * 获取每一章的节与知识点，并统计知识点的学习状态
     * @param chapterId 章的id
     * @param courseId 课程id
     * @param request
     * @return
     */
    //public List<ChapterPointVo>  findChapterByChapterId2(String chapterId,Integer courseId,HttpServletRequest request) {
    //    OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");
    //    int barrierIndex=0;
    //    int pointIndex=0;
    //    if(u!=null){
    //        Map<String,Object> paramMap = new HashMap<>();
    //        paramMap.put("chapterId",chapterId);
    //        paramMap.put("courseId",courseId);
    //        paramMap.put("userId",u.getId());
    //        //1、先获取当前章下所有小节
    //        String sql = "select id, name from oe_chapter  where  parent_id = ?  and   `level`=3 and is_delete = 0   order by sort  ";
    //        List<ChapterPointVo> chapterVos= this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(ChapterPointVo.class),chapterId);
    //        //2、获取当前章下所有知识点
    //        StringBuffer  zsdsql = new StringBuffer();
    //        zsdsql.append(" select  c.id as pointId,  c.name, c.parent_id as sectionId, c.barrier_id,");
    //        zsdsql.append(" ( select count(v.id)  from oe_video v  where  v.chapter_id=c.id  and  ");
    //        zsdsql.append("   v.course_id=:courseId   and v.is_delete=0  and v.status=1  ) as  videoCount ,");
    //        zsdsql.append(" ( select count(v.id)  from  user_r_video r left join oe_video v  on v.id = r.video_id where  v.chapter_id=c.id and  ");
    //        zsdsql.append(" r.user_id=:userId and r.course_id=:courseId  and r.study_status=1  and v.status=1 and v.is_delete=0 ) as  studyFinish ,");
    //        zsdsql.append(" ( select count(v.id)  from user_r_video r left join  oe_video v on  v.id = r.video_id  where   v.chapter_id=c.id and " );
    //        zsdsql.append(" r.user_id=:userId and r.course_id=:courseId  and r.study_status=0  and v.status=1 and v.is_delete=0 ) as  unStudy , ");
    //        zsdsql.append(" ( select ifnull(v.video_id,'0')   from  oe_video v    where  v.chapter_id=c.id  and   " );
    //        zsdsql.append(" v.course_id=:courseId  and v.status=1 and v.is_delete=0  order by  v.sort desc  limit 1) as ccvideoId,  ");
    //        zsdsql.append(" ( select v.id from   oe_video v   where    v.chapter_id=c.id and v.course_id=:courseId " );
    //        zsdsql.append("  and v.status=1 and v.is_delete=0 order by  v.sort desc  limit 1) as id ");
    //        zsdsql.append(" from  oe_chapter c  join oe_chapter j on c.parent_id=j.id   where  j.parent_id = :chapterId  and c.level=4 and  c.is_delete=0 order by j.sort,c.sort ");
    //        List<ChapterPointVo> zsdchapterVos= this.findEntitiesByJdbc(ChapterPointVo.class, zsdsql.toString(), paramMap);
    //        //3、获取当前章的所有关卡
    //        String gqsql = " select  b.id, b.name, b.kpoint_id,bu.barrier_status ,bu.lock_status  from   oe_chapter " +
    //                       " c  join oe_chapter j on c.parent_id=j.id ,oe_barrier b, oe_barrier_user bu " +
    //                       " where j.parent_id = :chapterId and bu.user_id=:userId and c.level=4 and  c.is_delete=0 " +
    //                       " and b.id = bu.barrier_id and b.kpoint_id=c.id  order by j.sort, c.sort   ";
    //        List<BarrierVo> barriers=  this.findEntitiesByJdbc(BarrierVo.class, gqsql, paramMap);
    //        Map<String, BarrierVo> mapBarriers = new HashMap<String, BarrierVo>();
    //        if(barriers.size()>0){
    //            //将关卡存入map集合中
    //            for (BarrierVo barrierVo:barriers){
    //                mapBarriers.put(barrierVo.getId(),barrierVo);
    //            }
    //            if( chapterVos.size() > 0 && zsdchapterVos.size()>0){
    //                //2、循环小节，为每个小节组装知识点
    //                for (ChapterPointVo chapterVo : chapterVos){
    //                    List<ChapterPointVo> pointVos = new ArrayList<ChapterPointVo>();
    //                    for (ChapterPointVo pointVo : zsdchapterVos) {
    //                        if (pointVo.getSectionId().equals(chapterVo.getId())) {
    //                            //当前知识点权限对应的关卡
    //                            BarrierVo barrier=mapBarriers.get(pointVo.getBarrier_id());
    //                            //没有为知识点分配关卡或此知识点的关卡是开锁状态，那此知识点为开锁状态\
    //                            pointVo.setLock_status(barrier==null ? 1 :barrier.getLock_status());
    //                            pointVos.add(pointVo);
    //                            //此知识点是一个关卡，就在集合中追加一个关卡
    //                            if (barrier !=null && barrier.getKpoint_id().equals(pointVo.getPointId())){
    //                                ChapterPointVo point = new ChapterPointVo();
    //                                point.setId(barrier.getId());
    //                                point.setName(barrier.getName());
    //                                point.setLock_status(barrier.getLock_status());
    //                                point.setBarrier_status(barrier.getBarrier_status());
    //                                point.setType(1);
    //                                pointVos.add(point);
    //                            }
    //                        }
    //                    }
    //
    //
    //                    chapterVo.setChapterSons(pointVos);
    //                }
    //            }
    //        }  else {
    //            if( chapterVos.size() > 0 && zsdchapterVos.size()>0){
    //                //2、循环小节，为每个小节组装知识点
    //                for (ChapterPointVo chapterVo : chapterVos){
    //                    List<ChapterPointVo> pointVos = new ArrayList<ChapterPointVo>();
    //                    for (ChapterPointVo pointVo : zsdchapterVos) {
    //                        if (pointVo.getSectionId().equals(chapterVo.getId())) {
    //                            pointVo.setLock_status(1);
    //                            pointVos.add(pointVo);
    //                        }
    //                    }
    //                    chapterVo.setChapterSons(pointVos);
    //                }
    //            }
    //        }
    //
    //        return  chapterVos;
    //    }
    //    return  null;
    //}

    /**
     * 获取每一章的节与知识点，并统计知识点的学习状态
     * @param chapterId 章的id
     * @param courseId 课程id
     * @param request
     * @return
     */
    public   List<Map<String, Object>>  findChapterByChapterId(String chapterId,Integer courseId,HttpServletRequest request) {
        List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>(); //结果的集合
        OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId",u.getId());
        paramMap.put("chapterId",chapterId);
        paramMap.put("courseId",courseId);
        if(u!=null){
            //1、先获取当前章下所有小节
            String sql = "select id, name from oe_chapter  where  parent_id =?  and   `level`=3 and is_delete = 0   order by sort  ";
            List<Map<String, Object>> sections = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,chapterId);

            //2、先获取当前章下所有知识点
            String sqlzsd = " select z.id as pointId,z.name, z.parent_id as sectionId, z.barrier_id from oe_chapter j,oe_chapter z "+
                            " where j.id=z.parent_id and  j. parent_id =?   and j.is_delete = 0 and z.is_delete=0 and z.status=1   order by j.sort,z.sort  ";
            List<Map<String, Object>> points= this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sqlzsd,chapterId);

            //3、查询当前用户当前章的所有视频
            String vsql = " select v1.id,v2.study_status,v1.chapter_id,v1.video_id from oe_chapter j,oe_chapter z,oe_video v1,user_r_video v2" +
                          " where j.id=z.parent_id and z.id=v1.chapter_id and v1.id=v2.video_id and j. parent_id =:chapterId  and j.is_delete = 0  " +
                          " and  v1.is_delete=0 and v1.status=1  and z.is_delete=0 and z.status=1 and v2.user_id=:userId order by j.sort,z.sort,v1.sort";
            List<Map<String, Object>> videos = this.getNamedParameterJdbcTemplate().queryForList(vsql, paramMap);

            //4、获取当前章的所有关卡
            String gqsql = " select  b.id, b.name, b.kpoint_id,bu.barrier_status ,bu.lock_status  from oe_barrier b, oe_barrier_user bu " +
                           " where  b.id = bu.barrier_id and b.course_id=:courseId  and bu.user_id=:userId and b.status=1  order by b.create_time  ";
            List<BarrierVo> barriers=  this.findEntitiesByJdbc(BarrierVo.class, gqsql, paramMap);
            Map<String, BarrierVo> mapBarriers = new HashMap<String, BarrierVo>();
            if(barriers.size()>0) {
                //将关卡存入map集合中
                for (BarrierVo barrierVo : barriers) {
                    mapBarriers.put(barrierVo.getId(), barrierVo);
                }
            }
            //4、循环组装树结构
            if( sections.size() > 0 && points.size()>0) {
                for ( Map<String, Object> chapter : sections) {
                    List<Map<String, Object>> pointMap = new ArrayList<Map<String, Object>>(); //知识点的集合
                    for ( Map<String, Object> point : points) {
                         if(point.get("sectionId").equals(chapter.get("id"))){
                             int learstatus=0; //状态，0未开始，1学习中，2已完成
                             int total=0;//知识点下视频总数
                             int studyfinish=0;//学完视频总数
                             int notlearn=0;  //未开始学习的视频总数
                             int status_sum=0; //视频状态和
                             boolean flag=true;
                             int type= 1; //1:是知识点 0:关卡
                             //循环视频，为每个知识点统计视频总数，学习状态
                             for (Map<String, Object> video :videos){
                                 if(video.get("chapter_id").equals(point.get("pointId"))){
                                     Integer status= Integer.valueOf(video.get("study_status").toString()); //学习状态
                                      if (flag){
                                          point.put("ccvideoId",video.get("video_id"));
                                          point.put("id",video.get("id"));
                                          flag=false;
                                      }
                                     total=total+1;
                                     if(status==1){
                                         studyfinish++;
                                     }else if(status==0){
                                         notlearn++;
                                     }
                                     int study_status = Integer.valueOf(video.get("study_status").toString());
                                     status_sum += study_status;
                                 }
                             }
                             if (studyfinish ==total) {
                                 learstatus = 2;
                             } else if (notlearn == total ) {
                                 learstatus = 0;
                             } else {
                                 learstatus =1;
                             }
                             point.put("type",0);
                             point.put("videoCount",total);
                             point.put("study_status", learstatus);

                             //未知识点设置关卡以及权限
                             //当前知识点权限对应的关卡
                             BarrierVo barrier=null;
                             point.put("lock_status", 1);
                             if(mapBarriers.size()>0){
                                 if( point.get("barrier_id") ==null || point.get("barrier_id").equals("") ){
                                     pointMap.add(point);
                                 }else {
                                     barrier=mapBarriers.get(point.get("barrier_id"));
                                     //没有为知识点分配关卡或此知识点的关卡是开锁状态，那此知识点为开锁状态
                                     if(barrier != null){
                                         point.put("lock_status", barrier.getLock_status());
                                         pointMap.add(point);
                                         //此知识点是一个关卡，就在集合中追加一个关卡
                                         if (barrier.getKpoint_id().equals(point.get("pointId"))){
                                             Map<String, Object> newPoint=new HashMap<String, Object>();
                                             newPoint.put("id",barrier.getId());
                                             newPoint.put("name",barrier.getName());
                                             newPoint.put("lock_status", barrier.getLock_status());
                                             newPoint.put("barrier_status",barrier.getBarrier_status());
                                             newPoint.put("type", 1);
                                             pointMap.add(newPoint);
                                         }
                                     }else{
                                         pointMap.add(point);
                                     }
                                 }
                             }else{
                                 pointMap.add(point);
                             }

                         }
                    }
                    chapter.put("chapterSons", pointMap);
                    returnlist.add(chapter);
                }
            }
        }
        return  returnlist;
    }


    /**
     * 查找最后播放未完成的视频(点击继续播放调用)
     * @param courseId  课程ID号
     * @return 最后播放未完成的视频
     */
    public ChapterPointVo  findLastPayVideo (Integer courseId,HttpServletRequest request) {
         OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");
         Map<String,Object> paramMap = new HashMap<>();
         paramMap.put("courseId",courseId);
         paramMap.put("userId",u.getId());
         if( u != null){
             //查看此课程用户是开始学习还是继续学习
             String querySql= " SELECT * from user_r_video  where course_id=:courseId and user_id=:userId and last_learn_time is not null";
             List<Map<String,Object>> courses=this.getNamedParameterJdbcTemplate().queryForList(querySql, paramMap);
             String sql="";
             if(courses.size() > 0){
                 sql=" select ifnull(v.video_id,'0') as ccvideoId,c.parent_id as sectionId, v.id, v.chapter_id as pointId ,  v.course_id as courseId, r.study_status,"+
                         " v.sort   from oe_video v  join user_r_video r  on v.id = r.video_id " +
                         " join oe_chapter c  on v.chapter_id = c.id     where   r.user_id=:userId  and r.course_id=:courseId and r.is_delete=0 and r.status=1  order by r.last_learn_time desc  limit 1";
             }else{
                  sql=" select ifnull(v.video_id,'0') as ccvideoId,j.id as sectionId, v.id, v.chapter_id as pointId ,  v.course_id as courseId " +
                      "  from oe_video v,oe_chapter p,oe_chapter j, oe_chapter z  where v.chapter_id=p.id  and p.parent_id = j.id " +
                      "  and j.parent_id = z.id  and z.course_id=:courseId  and z.is_delete=0 and z.`status`=1 and j.is_delete=0 and j.`status`=1 " +
                      "  and p.is_delete=0 and p.`status`=1 and  v.is_delete=0 and v.`status`=1  order by z.sort,j.sort,p.sort,v.sort desc  limit 1";
             }
             List<ChapterPointVo>  videos= this.findEntitiesByJdbc(ChapterPointVo.class,sql, paramMap);
             if(!CollectionUtils.isEmpty(videos) && videos.size() > 0){
                 return   videos.get(0);
             }

         }
          return  null;
    }

	public Object findChapterInfo(Integer courseId, String userId) {
		//获取登录用户
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        paramMap.put("userId",userId);
        String  uSql="select id from user_r_video  where  user_id=:userId and course_id=:courseId  limit 1";
        List<Map<String, Object>> listVideo= this.getNamedParameterJdbcTemplate().queryForList(uSql, paramMap);
        if(listVideo.size() > 0){
        	String sql = "select id, name from oe_chapter  where  course_id = ?  and   `level`=2  and is_delete =0   order by sort asc";
        	return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(ChapterVo.class),courseId);
        }
        return null;
	}


    /**
     * 获取每一章的节与知识点，并统计知识点的学习状态
     * @param chapterId 章的id
     * @param courseId 课程id
     * @param request
     * @return
     */
    public   List<Map<String, Object>>  findChapterByChapterId(String chapterId,Integer courseId,String userId) {
        List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>(); //结果的集合

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId",userId);
        paramMap.put("chapterId",chapterId);
        paramMap.put("courseId",courseId);
            //1、先获取当前章下所有小节
            String sql = "select id, name from oe_chapter  where  parent_id =?  and   `level`=3 and is_delete = 0   order by sort  ";
            List<Map<String, Object>> sections = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,chapterId);

            //2、先获取当前章下所有知识点
            String sqlzsd = " select z.id as pointId,z.name, z.parent_id as sectionId, z.barrier_id from oe_chapter j,oe_chapter z "+
                            " where j.id=z.parent_id and  j. parent_id =?   and j.is_delete = 0 and z.is_delete=0 and z.status=1   order by j.sort,z.sort  ";
            List<Map<String, Object>> points= this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sqlzsd,chapterId);

            //3、查询当前用户当前章的所有视频
            String vsql = " select v1.id,v2.study_status,v1.chapter_id,v1.video_id from oe_chapter j,oe_chapter z,oe_video v1,user_r_video v2" +
                          " where j.id=z.parent_id and z.id=v1.chapter_id and v1.id=v2.video_id and j. parent_id =:chapterId  and j.is_delete = 0  " +
                          " and  v1.is_delete=0 and v1.status=1  and z.is_delete=0 and z.status=1 and v2.user_id=:userId order by j.sort,z.sort,v1.sort";
            List<Map<String, Object>> videos = this.getNamedParameterJdbcTemplate().queryForList(vsql, paramMap);

            //4、获取当前章的所有关卡
            String gqsql = " select  b.id, b.name, b.kpoint_id,bu.barrier_status ,bu.lock_status  from oe_barrier b, oe_barrier_user bu " +
                           " where  b.id = bu.barrier_id and b.course_id=:courseId  and bu.user_id=:userId and b.status=1  order by b.create_time  ";
            List<BarrierVo> barriers=  this.findEntitiesByJdbc(BarrierVo.class, gqsql, paramMap);
            Map<String, BarrierVo> mapBarriers = new HashMap<String, BarrierVo>();
            if(barriers.size()>0) {
                //将关卡存入map集合中
                for (BarrierVo barrierVo : barriers) {
                    mapBarriers.put(barrierVo.getId(), barrierVo);
                }
            }
            //4、循环组装树结构
        if( sections.size() > 0 && points.size()>0) {
            for ( Map<String, Object> chapter : sections) {
                List<Map<String, Object>> pointMap = new ArrayList<Map<String, Object>>(); //知识点的集合
                for ( Map<String, Object> point : points) {
                     if(point.get("sectionId").equals(chapter.get("id"))){
                         int learstatus=0; //状态，0未开始，1学习中，2已完成
                         int total=0;//知识点下视频总数
                         int studyfinish=0;//学完视频总数
                         int notlearn=0;  //未开始学习的视频总数
                         int status_sum=0; //视频状态和
                         boolean flag=true;
                         int type= 1; //1:是知识点 0:关卡
                         //循环视频，为每个知识点统计视频总数，学习状态
                         for (Map<String, Object> video :videos){
                             if(video.get("chapter_id").equals(point.get("pointId"))){
                                 Integer status= Integer.valueOf(video.get("study_status").toString()); //学习状态
                                  if (flag){
                                      point.put("ccvideoId",video.get("video_id"));
                                      point.put("id",video.get("id"));
                                      flag=false;
                                  }
                                 total=total+1;
                                 if(status==1){
                                     studyfinish++;
                                 }else if(status==0){
                                     notlearn++;
                                 }
                                 int study_status = Integer.valueOf(video.get("study_status").toString());
                                 status_sum += study_status;
                             }
                         }
                         if (studyfinish ==total) {
                             learstatus = 2;
                         } else if (notlearn == total ) {
                             learstatus = 0;
                         } else {
                             learstatus =1;
                         }
                         point.put("type",0);
                         point.put("videoCount",total);
                         point.put("study_status", learstatus);

                         //未知识点设置关卡以及权限
                         //当前知识点权限对应的关卡
                         BarrierVo barrier=null;
                         point.put("lock_status", 1);
                         if(mapBarriers.size()>0){
                             if( point.get("barrier_id") ==null || point.get("barrier_id").equals("") ){
                                 pointMap.add(point);
                             }else {
                                 barrier=mapBarriers.get(point.get("barrier_id"));
                                 //没有为知识点分配关卡或此知识点的关卡是开锁状态，那此知识点为开锁状态
                                 if(barrier != null){
                                     point.put("lock_status", barrier.getLock_status());
                                     pointMap.add(point);
                                     //此知识点是一个关卡，就在集合中追加一个关卡
                                     if (barrier.getKpoint_id().equals(point.get("pointId"))){
                                         Map<String, Object> newPoint=new HashMap<String, Object>();
                                         newPoint.put("id",barrier.getId());
                                         newPoint.put("name",barrier.getName());
                                         newPoint.put("lock_status", barrier.getLock_status());
                                         newPoint.put("barrier_status",barrier.getBarrier_status());
                                         newPoint.put("type", 1);
                                         pointMap.add(newPoint);
                                     }
                                 }else{
                                     pointMap.add(point);
                                 }
                             }
                         }else{
                             pointMap.add(point);
                         }

                     }
                }
                chapter.put("chapterSons", pointMap);
                returnlist.add(chapter);
            }
        }
        return  returnlist;
    }
}
