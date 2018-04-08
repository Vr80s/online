package com.xczhihui.cloudClass.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.xczhihui.cloudClass.dao.VideoResDao;
import com.xczhihui.cloudClass.vo.TreeNode;
import com.xczhihui.cloudClass.vo.VideoResVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Chapter;
import com.xczhihui.bxg.online.common.domain.UserRVideo;
import com.xczhihui.bxg.online.common.domain.Video;
import com.xczhihui.cloudClass.service.VideoResService;
import com.xczhihui.cloudClass.vo.LibraryVo;

@Service("videoResService")
public class VideoResServiceImpl implements VideoResService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Autowired
     VideoResDao videoResDao;
	 
	@Override
	public Page<VideoResVo> findPage(VideoResVo searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return videoResDao.findCloudVideoResPage(searchVo, currentPage, pageSize);
	}
	@Override
	public List<TreeNode> getChapterTree(String courseId, String courseName) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap =null;
		String sql="select id,name ,parent_id as pId,level ,level as type ,level as contenttype from oe_chapter where is_delete=0 and course_id= "+courseId +" order by sort";
		List<TreeNode> treeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
		
		List<Map<String, Object>> allvideos = videoResDao.getNamedParameterJdbcTemplate().queryForList("select z.id zid,j.id jid,zsd.id zsdid,sp.id spid,sp.is_try_learn from oe_chapter z,oe_chapter j,oe_chapter zsd,oe_video sp "
				+ " where z.id = j.parent_id and j.id = zsd.parent_id and zsd.id = sp.chapter_id and sp.is_delete=0 and z.parent_id = '"+courseId+"' ", new HashMap<String,Object>());
		
		
		int courseVideoSum=0;
		boolean courseVideoflag =false;
		
		
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNode chapter:treeNodes){
				//if(chapter.getLevel()==2){//章
					chapter.setActName(chapter.getName());
					int zSum=0;
					int jSum=0;
					int zsdSum=0;
					boolean flag =false;
					for(Map<String, Object> v : allvideos){
						if(v.get("zid").toString().equals(chapter.getId())){
							zSum+=1;
							if(Boolean.valueOf(v.get("is_try_learn").toString())){
								flag = true;
							}
						}
						if(v.get("jid").toString().equals(chapter.getId())){
							jSum+=1;
							if(Boolean.valueOf(v.get("is_try_learn").toString())){
								flag = true;
							}
						}
						if(v.get("zsdid").toString().equals(chapter.getId())){
							zsdSum+=1;
							if(Boolean.valueOf(v.get("is_try_learn").toString())){
								flag = true;
							}
						}
						
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						courseVideoflag = true;
					}
					if(chapter.getLevel()==2){
						courseVideoSum=courseVideoSum+zSum;
						chapter.setName(chapter.getName()+"  "+zSum);
					}
					if(chapter.getLevel()==3){
						chapter.setName(chapter.getName()+"  "+jSum);
					}
					if(chapter.getLevel()==4){
						chapter.setName(chapter.getName()+"  "+zsdSum);
					}
					
				//}
				
			}
		}
		
/*		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNode chapter:treeNodes){
				
				if(chapter.getLevel()==2){//章
					chapter.setActName(chapter.getName());
					List<TreeNode> sections=getSectionByChapter(chapter.getId());//根据章获取获取节
					boolean flag =false;
					for(TreeNode section : sections){
						List<TreeNode> topics = getTopicBySection(section.getId());//根据节获取知识点
						for(TreeNode topic:topics){
//							Map<String, Object> params =null;
//							String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
//							List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
//							if(videos!=null&&videos.size()>0){
//								flag = true;
//							}
							
							for (Map<String, Object> v : allvideos) {
								if (v.get("zsdid").toString().equals(topic.getId()) && Boolean.valueOf(v.get("is_try_learn").toString())) {
									flag = true;
								}
							}
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						courseVideoflag = true;
					}
					chapter.setName(chapter.getName()+"  "+getVideoCountByChapter(chapter.getId()));
					courseVideoSum=courseVideoSum+getVideoCountByChapter(chapter.getId());
				}
				
				
				
				if(chapter.getLevel()==3){//节
					chapter.setActName(chapter.getName());
					List<TreeNode> topics = getTopicBySection(chapter.getId());//根据节获取知识点
					boolean flag =false;
					for(TreeNode topic:topics){
						Map<String, Object> params =null;
						String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
						List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
						if(videos!=null&&videos.size()>0){
							flag = true;
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						};
					chapter.setName(chapter.getName()+"  "+getVideoCountBySection(chapter.getId()));
				}
				
				
				if(chapter.getLevel()==4){//知识点
					chapter.setActName(chapter.getName());
					Map<String, Object> params =null;
					String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+chapter.getId()+"'";
					List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
					if(videos!=null&&videos.size()>0){
						chapter.setName(chapter.getName()+"---(试学)");
					}
					chapter.setName(chapter.getName()+"   "+getVideoCountByTopic(chapter.getId()));
				}
			}
		}*/
		
		
		TreeNode fistTree=new TreeNode();
		
		fistTree.setId(courseId);
		fistTree.setLevel(1);
		fistTree.setType("1");
		fistTree.setContenttype("1");
		fistTree.setName(courseName);
		fistTree.setOpen(true);
		if(courseVideoflag){
			fistTree.setName(fistTree.getName()+"---(试学)");
		}
		fistTree.setName(fistTree.getName()+"  "+courseVideoSum);
		treeNodes.add(fistTree);

		return treeNodes;
	}
	
	//根据知识点获取视频数量
	public int getVideoCountByTopic(String topicId){
		Map<String, Object> paramMap =null;
		String sql = "select * from oe_video where is_delete=0 and chapter_id = '"+topicId+"'";
		List<Video> videos = videoResDao.findEntitiesByJdbc(Video.class, sql, paramMap);
		return videos==null?0:videos.size();
	}
	//根据节获取知识点
	public  List<TreeNode> getTopicBySection(String sectionId){
		Map<String, Object> paramMap =null;
		String sql = "select * from oe_chapter where is_delete=0 and parent_id= '"+sectionId+"'";
		List<TreeNode> TreeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
		return TreeNodes;
	};
	//根据节点获取视频数量
	public int getVideoCountBySection(String sectionId){
		List<TreeNode> topics = getTopicBySection(sectionId);
		if(topics.size()==0){
			return 0;
		}
		StringBuilder sql  = new StringBuilder("select * from oe_video where is_delete=0 ");
		sql.append(" and chapter_id in ( ");
 		for(int i=0;i<topics.size();i++){
	        if(i!=0) {
                sql.append(",");
            }
	        sql.append("'"+topics.get(i).getId()+"'");
        }
        sql.append(" ) ");
		Map<String, Object> paramMap =null;
		List<Video> videos = videoResDao.findEntitiesByJdbc(Video.class, sql.toString(), paramMap);
		return videos==null?0:videos.size();
	}
	//根据章获取节
	public  List<TreeNode> getSectionByChapter(String chapterId){
		Map<String, Object> paramMap =null;
		String sql = "select * from oe_chapter where is_delete=0 and parent_id= '"+chapterId+"'";
		List<TreeNode> TreeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
		return TreeNodes;
	}
	//根据章获取视频数量
	public int getVideoCountByChapter(String chapterId){
		List<TreeNode> sections = getSectionByChapter(chapterId);
		int count=0;
		for(TreeNode section:sections){
			count =getVideoCountBySection(section.getId())+count;
		}
		return count;
	}
	
	@Override
	public void saveChapter(Chapter chapter) {
		videoResDao.save(chapter);
		
	}
	@Override
	public void updateChapter(TreeNode treeNode) {
		Chapter chapter =videoResDao.findOneEntitiyByProperty(Chapter.class, "id", treeNode.getId());
		String oldName = chapter.getName();
		chapter.setName(treeNode.getName());
		videoResDao.update(chapter);
	}
	@Override
	public void deleteChapter(String ids) {
		String [] idArr =ids.split(",");
		for(String id :idArr){
			Chapter chapter =videoResDao.findOneEntitiyByProperty(Chapter.class, "id", id);
			if(null!=chapter){
				/*chapter.setDelete(true);
				videoResDao.update(chapter);*/
				//章节知识点改变成物理删除，原因是影响到了章节知识点的排序
				videoResDao.delete(chapter);
			}
			List<Video> videos= videoResDao.findEntitiesByProperty(Video.class, "chapterId", id);
			if(null!=videos&&videos.size()>0){
				for(Video video :videos){
					video.setDelete(true);
					videoResDao.update(video);
				}
				
			}
		}
		
		
	}
	@Override
	public void addVideo(Video video) {
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT IFNULL(MAX(sort),0) as sort FROM oe_video ";
		List<Video> temp = videoResDao.findEntitiesByJdbc(Video.class, sql, params);
		int sort;
		if(temp.size()>0){
			 sort=temp.get(0).getSort().intValue()+1;
		}else{
			 sort=1;
		}
		video.setSort(sort);
		videoResDao.save(video);
		//新增视频同步到用户视频
		String hql="from UserRVideo where courseId =? group by userId ";
		List<UserRVideo> UserRVideos= videoResDao.findByHQL(hql, video.getCourseId());
		if(UserRVideos!=null&&UserRVideos.size()>0){
			for(UserRVideo userRVideo:UserRVideos){
				UserRVideo entity= new UserRVideo();
				entity.setUserId(userRVideo.getUserId());
				entity.setCreatePerson(userRVideo.getCreatePerson());
				entity.setCreateTime(video.getCreateTime());
				entity.setVideoId(video.getId());
				entity.setSort(video.getSort());
				entity.setApplyId(userRVideo.getApplyId());
				entity.setLastLearnTime(null);
				entity.setCollection(false);
				entity.setCourseId(userRVideo.getCourseId());
				entity.setStatus(0);
				entity.setStudyStatus(0);
				entity.setDelete(false);
				videoResDao.save(entity);
			}
		}
		
	}
	@Override
	public void updateVideo(Video video) {
		Video entity = videoResDao.findOneEntitiyByProperty(Video.class, "id", video.getId());
		entity.setName(video.getName());
		entity.setVideoSize(video.getVideoSize());
		entity.setVideoTime(video.getVideoTime());
		entity.setVideoId(video.getVideoId());
		entity.setIsTryLearn(video.getIsTryLearn());
		entity.setVideoVersion(video.getVideoVersion());
		videoResDao.update(entity);
	}
	@Override
	public void deleteVideoById(String id) {
		Video entity = videoResDao.findOneEntitiyByProperty(Video.class, "id", id);
		entity.setDelete(true);
		videoResDao.update(entity);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isDelete", entity.isDelete());
		paramMap.put("videoId", entity.getId());
		videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set is_delete = :isDelete where video_id = :videoId", paramMap);
		
		
	}
	@Override
	public void deletes(String[] ids) {
		for(String id:ids){
			Video entity = videoResDao.findOneEntitiyByProperty(Video.class, "id", id);
			entity.setDelete(true);
			videoResDao.update(entity);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("isDelete", entity.isDelete());
			paramMap.put("videoId", entity.getId());
			videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set is_delete = :isDelete where video_id = :videoId", paramMap);
        }
		
	}
	private Chapter findChapterById(String id){
		return videoResDao.findOneEntitiyByProperty(Chapter.class, "id", id);
	}
	private void updateChapter(Chapter entity){
		 videoResDao.update(entity);
	}
	@Override
	public String updateNodeSort(LibraryVo libraryVo) {
		//TODO 拖到当前节点上面	libraryVo.getMoveType()
				Chapter target = findChapterById(libraryVo.getTargetId());
				Chapter chapter = findChapterById(libraryVo.getId());//被拖动移动的节点  	 在目标节点上面
				Integer sort = target.getSort();//目标节点
				if (chapter.getSort()>sort) {//移动节点大于目标节点     说明是向下移动  
					/*List<Chapter> list = videoResDao.findChapterByOrderId(target.getId(), target.getSort(), chapter.getSort());
					for (Chapter changeObj:list) {
						changeObj.setSort(changeObj.getSort()+1);
						updateChapter(changeObj);
					}
					if (libraryVo.getMoveType().equals("prev")) {//向前移动  
						chapter.setSort(sort);  //更新被拖动的节点  
					}else{
						chapter.setSort(sort+1);  
						target.setSort(sort);
						updateChapter(target);
					}*/
					chapter.setSort(sort+1);  
					updateChapter(chapter);
					String sql = "update oe_chapter t set t.sort = t.sort + 1 where sort>"+sort+1+" and t.parent_id =:pId and t.id<>:id";
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pId", chapter.getParentId());
					paramMap.put("id", chapter.getId());
					videoResDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
				}else{
					chapter.setSort(sort);  
					updateChapter(chapter);
					String sql = "update oe_chapter t set t.sort = t.sort + 1 where sort>"+sort+" and t.parent_id =:pId and t.id<>:id";
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pId", chapter.getParentId());
					paramMap.put("id", chapter.getId());
					videoResDao.getNamedParameterJdbcTemplate().update(sql, paramMap);
				}
				return "1";
	}
	@Override
	public void updateSortUp(String id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Video where  isDelete=0 and id = ?";
		 Video videoPre= videoResDao.findByHQLOne(hqlPre,new Object[] {id});
         Integer videoPreSort=videoPre.getSort();
         
         String hqlNext="from Video where sort < (select sort from Video where id= ? ) and chapterId = ?  and isDelete=0 order by sort desc";
         Video videoNext= videoResDao.findByHQLOne(hqlNext,new Object[] {id,videoPre.getChapterId()});
         Integer videoNextSort=videoNext.getSort();
         
         videoPre.setSort(videoNextSort);
         videoNext.setSort(videoPreSort);
         
         videoResDao.update(videoPre);
         videoResDao.update(videoNext);
         
        Map<String, Object> paramMapPre = new HashMap<String, Object>();
        paramMapPre.put("sort", videoPre.getSort());
        paramMapPre.put("videoId", videoPre.getId());
 		videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set sort = :sort where video_id = :videoId ", paramMapPre);
 		
 		Map<String, Object> paramMapNext = new HashMap<String, Object>();
 		paramMapNext.put("sort", videoNext.getSort());
 		paramMapNext.put("videoId", videoNext.getId());
 	 	videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set sort = :sort where  video_id = :videoId", paramMapNext);
		
	}
	@Override
	public void updateSortDown(String id) {
         String hqlPre="from Video where  isDelete=0 and id = ?";
		 Video videoPre= videoResDao.findByHQLOne(hqlPre,new Object[] {id});
         Integer videoPreSort=videoPre.getSort();
         
         String hqlNext="from Video where sort > (select sort from Video where id= ? ) and chapterId = ?  and isDelete=0 order by sort asc";
         Video videoNext= videoResDao.findByHQLOne(hqlNext,new Object[] {id,videoPre.getChapterId()});
         Integer videoNextSort=videoNext.getSort();
         
         videoPre.setSort(videoNextSort);
         videoNext.setSort(videoPreSort);
         
         videoResDao.update(videoPre);
         videoResDao.update(videoNext);
         
         Map<String, Object> paramMapPre = new HashMap<String, Object>();
         paramMapPre.put("sort", videoPre.getSort());
         paramMapPre.put("videoId", videoPre.getId());
  		 videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set sort = :sort where video_id = :videoId", paramMapPre);
  		
  		Map<String, Object> paramMapNext = new HashMap<String, Object>();
  		paramMapNext.put("sort", videoNext.getSort());
  		paramMapNext.put("videoId", videoNext.getId());
  	 	videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set sort = :sort where  video_id = :videoId", paramMapNext);
		
	}
	@Override
	public List<Chapter>  findVideoUse(String NodeIds, Integer courseId) {
		Map<String, Object> paramMap =null;
		StringBuilder sql = new StringBuilder("SELECT c.id FROM oe_chapter c,oe_video v,user_r_video uv where c.is_delete=0 and v.is_delete=0 and uv.is_delete=0 and v.chapter_id = c.id and v.id =uv.video_id and c.id in( ");
		String [] ids=NodeIds.split(",");
		 for(int i=0;i<ids.length;i++){
               if(i!=0) {
                   sql.append(",");
               }
               sql.append("'"+ids[i]+"'");
           }
           sql.append(" ) ");
           
		List<Chapter> chapters= videoResDao.findEntitiesByJdbc(Chapter.class, sql.toString(), paramMap);
		return chapters;
	}
	
	@Override
	public void updateStatus(String id) {
		Video video=videoResDao.get(id, Video.class);
        if(video!=null&&video.getStatus()==1){
        	video.setStatus(0);
        }else{
        	video.setStatus(1);
        }
        videoResDao.update(video);
        Map<String, Object> paramMap = new HashMap<String , Object>();
        paramMap.put("status", video.getStatus());
        paramMap.put("videoId", video.getId());
        videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set status = :status where video_id = :videoId", paramMap);
	}
	@Override
	public void updateStatusEnable(String id) {
		Video video=videoResDao.get(id, Video.class);
        video.setStatus(1);
        videoResDao.update(video);
        Map<String, Object> paramMap = new HashMap<String , Object>();
        paramMap.put("status", video.getStatus());
        paramMap.put("videoId", video.getId());
        videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set status = :status where video_id = :videoId", paramMap);
	}
	@Override
	public void updateStatusDisable(String id) {
		Video video=videoResDao.get(id, Video.class);
        video.setStatus(0);
        videoResDao.update(video);
        Map<String, Object> paramMap = new HashMap<String , Object>();
        paramMap.put("status", video.getStatus());
        paramMap.put("videoId", video.getId());
        videoResDao.getNamedParameterJdbcTemplate().update("update user_r_video set status = :status where video_id = :videoId", paramMap);
	}
	
	@Override
	public List<Video> saveCopyTree(String level, String courentTreeId, Integer courseId,String[] kpos) {
		Map<String, Object> paramMap =null;
		String str="";
		for(int i = 0; i < kpos.length; i++){
			str=kpos[i]+"','"+str;
		}
		str=str.substring(0, str.length()-3);
		
		String sql=" SELECT id,create_person,create_time,is_delete,status,sort,name,parent_id,course_id,level from oe_chapter WHERE id in(SELECT parent_id from oe_chapter WHERE id in (SELECT parent_id from oe_chapter c where is_delete = 0 and id in ('"+str+"') group by c.parent_id))"
				+ " UNION"
				+ " SELECT id,create_person,create_time,is_delete,status,sort,name,parent_id,course_id,level from oe_chapter WHERE id in (SELECT parent_id from oe_chapter c where is_delete = 0 and id in ('"+str+"') group by c.parent_id)"
				+ " UNION"
				+ " SELECT id,create_person,create_time,is_delete,status,sort,name,parent_id,course_id,level from oe_chapter c where is_delete = 0 and id in ('"+str+"') ";
		List<Chapter> chapters = videoResDao.findEntitiesByJdbc(Chapter.class, sql, paramMap);//章、节、知识点
		
		String sqlVideo="select * from oe_video where is_delete = 0 and chapter_id in ('"+str+"') order by sort" ;
		List<Video> videos = videoResDao.findEntitiesByJdbc(Video.class, sqlVideo, paramMap);//选中知识点下的所有视频
		List<Video> newVideos = new ArrayList<Video>();
		
		if("1".equals(level)){//拷贝章
			
			List<String> tempsOld=new ArrayList<String>();//旧章、节、知识点的ID
			Map<String, String> oldNew =new HashMap<String, String>();//key为旧章、节、知识点的ID，val为对应新章、节、知识点的ID
			for(int i=0;i<chapters.size();i++){//循环章、节、知识点
				
				chapters.get(i).setCourseId(courseId);
				chapters.get(i).setSort(null);
				chapters.get(i).setCreateTime(new Date());
				chapters.get(i).setBarrierId(null);
				if(2==chapters.get(i).getLevel()){//章的情况
					String oldId=chapters.get(i).getId();//旧章的ID
					chapters.get(i).setParentId(courentTreeId);
					tempsOld.add(oldId);//存储旧章的ID
					videoResDao.save(chapters.get(i));
					oldNew.put(oldId, chapters.get(i).getId());//对应存储key为旧章的ID，val为对应新章的ID
				}else if(3==chapters.get(i).getLevel()){//节的情况
					
					for(int j=0;j<tempsOld.size();j++){
						if(tempsOld.get(j).equals(chapters.get(i).getParentId())){//节父ID是旧章ID的情况下，进行对应替换
							String oldId=chapters.get(i).getId();//旧节的ID
							chapters.get(i).setParentId(oldNew.get(tempsOld.get(j)));
							tempsOld.add(oldId);//存储旧节的ID
							videoResDao.save(chapters.get(i));
							oldNew.put(oldId, chapters.get(i).getId());//对应存储key为旧节的ID，val为对应新节的ID
						}
					}
					
				}else if(4==chapters.get(i).getLevel()){//知识点的情况
					for(int j=0;j<tempsOld.size();j++){
						if(tempsOld.get(j).equals(chapters.get(i).getParentId())){//知识点PID是旧节ID的情况下，进行对应替换
							String oldId=chapters.get(i).getId();//旧知识点的ID
							chapters.get(i).setParentId(oldNew.get(tempsOld.get(j)));
							tempsOld.add(oldId);//存储旧知识点的ID
							videoResDao.save(chapters.get(i));
							oldNew.put(oldId, chapters.get(i).getId());//对应存储key为旧知识点的ID，val为对应新知识点的ID
						}
					}
				}
				
			}
			
			//保存视频到知识点下
			for(int i=0;i<videos.size();i++){//循环视频
				for(int j = 0; j < kpos.length; j++){//循环知识点
					if(kpos[j].equals(videos.get(i).getChapterId())){//对比视频的chapterId是否等于选中知识点的id
						Video temp=new Video();
						BeanUtils.copyProperties(videos.get(i), temp);
						temp.setSort(null);
						temp.setCourseId(courseId);
						temp.setChapterId(oldNew.get(kpos[j]));
						temp.setCreateTime(new Date());
						videoResDao.save(temp);
						newVideos.add(temp);
					}
				}
				
			}
			
		}else if("2".equals(level)){//拷贝节
			
			List<String> tempsOld=new ArrayList<String>();//旧章、节、知识点的ID
			Map<String, String> oldNew =new HashMap<String, String>();//key为旧章、节、知识点的ID，val为对应新章、节、知识点的ID
			for(int i=0;i<chapters.size();i++){//循环章、节、知识点
				
				chapters.get(i).setCourseId(courseId);
				chapters.get(i).setSort(null);
				chapters.get(i).setCreateTime(new Date());
				if(3==chapters.get(i).getLevel()){//节的情况
				
					String oldId=chapters.get(i).getId();//旧节的ID
					chapters.get(i).setParentId(courentTreeId);
					tempsOld.add(oldId);//存储旧节的ID
					videoResDao.save(chapters.get(i));
					oldNew.put(oldId, chapters.get(i).getId());//对应存储key为旧节的ID，val为对应新节的ID
			
				}else if(4==chapters.get(i).getLevel()){//知识点的情况
					for(int j=0;j<tempsOld.size();j++){
						if(tempsOld.get(j).equals(chapters.get(i).getParentId())){//知识点PID是旧节ID的情况下，进行对应替换
							String oldId=chapters.get(i).getId();//旧知识点的ID
							chapters.get(i).setParentId(oldNew.get(tempsOld.get(j)));
							tempsOld.add(oldId);//存储旧知识点的ID
							videoResDao.save(chapters.get(i));
							oldNew.put(oldId, chapters.get(i).getId());//对应存储key为旧知识点的ID，val为对应新知识点的ID
						}
					}
				}
				
			}
			
			//保存视频到知识点下
			for(int i=0;i<videos.size();i++){//循环视频
				for(int j = 0; j < kpos.length; j++){//循环知识点
					if(kpos[j].equals(videos.get(i).getChapterId())){//对比视频的chapterId是否等于选中知识点的id
						Video temp=new Video();
						BeanUtils.copyProperties(videos.get(i), temp);
						temp.setSort(null);
						temp.setCourseId(courseId);
						temp.setChapterId(oldNew.get(kpos[j]));
						temp.setCreateTime(new Date());
						videoResDao.save(temp);
						newVideos.add(temp);
					}
				}
			}
			
		}else if("3".equals(level)){//拷贝知识点
			
			List<String> tempsOld=new ArrayList<String>();//旧章、节、知识点的ID
			Map<String, String> oldNew =new HashMap<String, String>();//key为旧章、节、知识点的ID，val为对应新章、节、知识点的ID
			for(int i=0;i<chapters.size();i++){//循环章、节、知识点
				
				chapters.get(i).setCourseId(courseId);
				chapters.get(i).setSort(null);
				chapters.get(i).setCreateTime(new Date());
				if(4==chapters.get(i).getLevel()){//知识点的情况
						
					String oldId=chapters.get(i).getId();//旧知识点的ID
					chapters.get(i).setParentId(courentTreeId);
					tempsOld.add(oldId);//存储旧知识点的ID
					videoResDao.save(chapters.get(i));
					oldNew.put(oldId, chapters.get(i).getId());//对应存储key为旧知识点的ID，val为对应新知识点的ID
						
					}
				
			}
			
			//保存视频到知识点下
			for(int i=0;i<videos.size();i++){//循环视频
				for(int j = 0; j < kpos.length; j++){//循环知识点
					if(kpos[j].equals(videos.get(i).getChapterId())){//对比视频的chapterId是否等于选中知识点的id
						Video temp=new Video();
						BeanUtils.copyProperties(videos.get(i), temp);
						temp.setSort(null);
						temp.setCourseId(courseId);
						temp.setChapterId(oldNew.get(kpos[j]));
						temp.setCreateTime(new Date());
						videoResDao.save(temp);
						newVideos.add(temp);
					}
				}
				
			}
			
		}
		
		return newVideos;
	}
	
	@Override
    public void addVideoToUerVideo(Integer courseId, Video video){
		Video temp =videoResDao.findOneEntitiyByProperty(Video.class, "id", video.getId());
		//新增视频同步到用户视频
		String hql="from UserRVideo where courseId =? group by userId ";
		List<UserRVideo> UserRVideos= videoResDao.findByHQL(hql, courseId);
		if(UserRVideos!=null&&UserRVideos.size()>0){
			for(UserRVideo userRVideo:UserRVideos){
				UserRVideo entity= new UserRVideo();
				entity.setUserId(userRVideo.getUserId());
				entity.setCreatePerson(userRVideo.getCreatePerson());
				entity.setCreateTime(temp.getCreateTime());
				entity.setVideoId(temp.getId());
				entity.setSort(temp.getSort());
				entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				entity.setLastLearnTime(null);
				entity.setCollection(false);
				entity.setCourseId(userRVideo.getCourseId());
				entity.setStatus(temp.getStatus());
				entity.setStudyStatus(0);
				entity.setDelete(false);
				videoResDao.save(entity);
			}
		}
	}
	
	@Override
	public List<TreeNode> getChapterTreeCriticize(String courseId, String courseName) {
		Map<String, Object> paramMap =null;
		String sql= " select * from (select id,name ,parent_id as pId,level -1 level ,level - 1 as type ,level -1 as contenttype,sort,0 cntNum from oe_chapter where is_delete=0 and course_id= "+courseId +
					" union all " +
					" select ov.id, " +
					" (case when ov.is_try_learn = 1 then " +
					" concat(ov.`name`,'---(试学)') " +
					" else ov.`name` end" +
					" ) name " +
					" ,ov.chapter_id pId, 4 level,4 type,4 contenttype,(sort * -1) sort,(select count(1) from oe_criticize oc where oc.video_id = ov.id and oc.is_delete = 0) cntNum " +
					" from oe_video ov where ov.is_delete = 0 and ov.course_id = "+courseId+
					" and exists(select 1 from oe_chapter oc where oc.id = ov.chapter_id and oc.is_delete = 0)) t order by sort";
		List<TreeNode> treeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
		
		int courseVideoSum=0;
		boolean courseVideoflag =false;
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNode chapter:treeNodes){
				
				if(chapter.getLevel()==1){//章
					chapter.setActName(chapter.getName());
					List<TreeNode> sections=getSectionByChapter(chapter.getId());//根据章获取获取节
					boolean flag =false;
					for(TreeNode section : sections){
						List<TreeNode> topics = getTopicBySection(section.getId());//根据节获取
						for(TreeNode topic:topics){
							Map<String, Object> params =null;
							String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
							List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
							if(videos!=null&&videos.size()>0){
								flag = true;
							}
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						courseVideoflag = true;
					}
					chapter.setName(chapter.getName()+"  "+getCntNumByParent(chapter,treeNodes));//章 ID 
					courseVideoSum=courseVideoSum+getVideoCountByChapter(chapter.getId());
				}
				
				
				
				if(chapter.getLevel()==2){//节
					chapter.setActName(chapter.getName());
					List<TreeNode> topics = getTopicBySection(chapter.getId());//根据节获取知识点
					boolean flag =false;
					for(TreeNode topic:topics){
						Map<String, Object> params =null;
						String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
						List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
						if(videos!=null&&videos.size()>0){
							flag = true;
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						};
					chapter.setName(chapter.getName()+"  "+getCntNumByParent(chapter,treeNodes));//节 ID 
				}
				
				if(chapter.getLevel()==3){//知识点
					chapter.setActName(chapter.getName());
					Map<String, Object> params =null;
					String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+chapter.getId()+"'";
					List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
					if(videos!=null&&videos.size()>0){
						chapter.setName(chapter.getName()+"---(试学)");
					}
					chapter.setName(chapter.getName()+"   "+getCntNumByParent(chapter,treeNodes));//知识点 ID 
				}
				
				if(chapter.getLevel()==4){//视频
					chapter.setActName(chapter.getName());
					chapter.setName(chapter.getName()+"   "+chapter.getCntNum());
				}
			}
		}
		
		
//		TreeNode fistTree=new TreeNode();
//		
//		fistTree.setId(courseId);
//		fistTree.setLevel(1);
//		fistTree.setType("1");
//		fistTree.setContenttype("1");
//		fistTree.setName(courseName);
//		fistTree.setOpen(true);
//		if(courseVideoflag){
//			fistTree.setName(fistTree.getName()+"---(试学)");
//		}
//		fistTree.setName(fistTree.getName()+"  "+courseVideoSum);
//		treeNodes.add(fistTree);

		return treeNodes;
	}
	
	public int getCntNumByParent(TreeNode treeNode,List<TreeNode> treeNodes){
		
		if(treeNode == null){
			return 0;
		}
		int cntNum = 0;

		if(treeNode.getLevel() == 4){//如果是视频就退回
			return treeNode.getCntNum();
		}else{
			for(int i=0;i<treeNodes.size();i++){
				if(treeNode.getId().equals(treeNodes.get(i).getpId())){
					cntNum += getCntNumByParent(treeNodes.get(i),treeNodes);
				}
			}
		}
		
		return cntNum;
	}
	@Override
	public List<TreeNode> getChapterTreeNotes(String courseId, String courseName) {
				Map<String, Object> paramMap =null;
				String sql= " select * from (select id,name ,parent_id as pId,level -1 level ,level - 1 as type ,level -1 as contenttype,sort,0 cntNum from oe_chapter where is_delete=0 and course_id= "+courseId +
							" union all " +
							" select ov.id, " +
							" (case when ov.is_try_learn = 1 then " +
							" concat(ov.`name`,'---(试学)') " +
							" else ov.`name` end" +
							" ) name " +
							" ,ov.chapter_id pId, 4 level,4 type,4 contenttype,(sort * -1) sort,(select count(1) from oe_notes ono where ono.video_id = ov.id and ono.is_delete = 0) cntNum " +
							" from oe_video ov where ov.is_delete = 0 and ov.course_id = "+courseId+
							" and exists(select 1 from oe_chapter oc where oc.id = ov.chapter_id and oc.is_delete = 0)) t order by sort";
				List<TreeNode> treeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
				
				int courseVideoSum=0;
				boolean courseVideoflag =false;
				if(treeNodes!=null&&treeNodes.size()>0){
					for(TreeNode chapter:treeNodes){
						
						if(chapter.getLevel()==1){//章
							chapter.setActName(chapter.getName());
							List<TreeNode> sections=getSectionByChapter(chapter.getId());//根据章获取获取节
							boolean flag =false;
							for(TreeNode section : sections){
								List<TreeNode> topics = getTopicBySection(section.getId());//根据节获取
								for(TreeNode topic:topics){
									Map<String, Object> params =null;
									String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
									List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
									if(videos!=null&&videos.size()>0){
										flag = true;
									}
								}
							}
							if(flag){
								chapter.setName(chapter.getName()+"---(试学)");
								courseVideoflag = true;
							}
							chapter.setName(chapter.getName()+"  "+getCntNumByParent(chapter,treeNodes));//章 ID 
							courseVideoSum=courseVideoSum+getVideoCountByChapter(chapter.getId());
						}
						
						
						
						if(chapter.getLevel()==2){//节
							chapter.setActName(chapter.getName());
							List<TreeNode> topics = getTopicBySection(chapter.getId());//根据节获取知识点
							boolean flag =false;
							for(TreeNode topic:topics){
								Map<String, Object> params =null;
								String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
								List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
								if(videos!=null&&videos.size()>0){
									flag = true;
								}
							}
							if(flag){
								chapter.setName(chapter.getName()+"---(试学)");
								};
							chapter.setName(chapter.getName()+"  "+getCntNumByParent(chapter,treeNodes));//节 ID 
						}
						
						if(chapter.getLevel()==3){//知识点
							chapter.setActName(chapter.getName());
							Map<String, Object> params =null;
							String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+chapter.getId()+"'";
							List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
							if(videos!=null&&videos.size()>0){
								chapter.setName(chapter.getName()+"---(试学)");
							}
							chapter.setName(chapter.getName()+"   "+getCntNumByParent(chapter,treeNodes));//知识点 ID 
						}
						
						if(chapter.getLevel()==4){//视频
							chapter.setActName(chapter.getName());
							chapter.setName(chapter.getName()+"   "+chapter.getCntNum());
						}
					}
				}
				
				
//				TreeNode fistTree=new TreeNode();
//				
//				fistTree.setId(courseId);
//				fistTree.setLevel(1);
//				fistTree.setType("1");
//				fistTree.setContenttype("1");
//				fistTree.setName(courseName);
//				fistTree.setOpen(true);
//				if(courseVideoflag){
//					fistTree.setName(fistTree.getName()+"---(试学)");
//				}
//				fistTree.setName(fistTree.getName()+"  "+courseVideoSum);
//				treeNodes.add(fistTree);

				return treeNodes;
	}
	
	@Override
	public List<TreeNode> getChapterTreeBarrier(String courseId,String courseName) {

		Map<String, Object> paramMap =null;
//		String sql="select id,name ,parent_id as pId,level ,level as type ,level as contenttype from oe_chapter where is_delete=0 and course_id= "+courseId +" order by sort";
		String sql= " select * from ( " +
					" select id,name ,parent_id as pId,level ,level as type ,level as contenttype,sort,true open from oe_chapter where is_delete=0 and course_id= "+ courseId +
					" UNION ALL " +
					" select ob.id,ob.`name` ,och.parent_id as pId,och.level ,och.level as type ,och.level+1 as contenttype,och.sort,true open from oe_chapter och JOIN oe_barrier ob on(och.id = ob.kpoint_id)  " +
					" where ob.is_delete = 0 and och.is_delete = 0 and och.`level` = 4 and ob.course_id = "+courseId +" ) t " +
					" order by t.sort ,t.contenttype asc,t.id ";

		List<TreeNode> treeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
		
		int courseVideoSum=0;
		boolean courseVideoflag =false;
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNode chapter:treeNodes){
				
				if(chapter.getLevel()==2){//章
					chapter.setActName(chapter.getName());
					List<TreeNode> sections=getSectionByChapter(chapter.getId());//根据章获取获取节
					boolean flag =false;
					for(TreeNode section : sections){
						List<TreeNode> topics = getTopicBySection(section.getId());//根据节获取知识点
						for(TreeNode topic:topics){
							Map<String, Object> params =null;
							String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
							List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
							if(videos!=null&&videos.size()>0){
								flag = true;
							}
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						courseVideoflag = true;
					}
//					chapter.setName(chapter.getName());
//					courseVideoSum=courseVideoSum+getVideoCountByChapter(chapter.getId());
				}
				
				
				
				if(chapter.getLevel()==3){//节
					chapter.setActName(chapter.getName());
					List<TreeNode> topics = getTopicBySection(chapter.getId());//根据节获取知识点
					boolean flag =false;
					for(TreeNode topic:topics){
						Map<String, Object> params =null;
						String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
						List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
						if(videos!=null&&videos.size()>0){
							flag = true;
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						};
//					chapter.setName(chapter.getName());
				}
				
				
				if(chapter.getLevel()==4){//知识点
					chapter.setActName(chapter.getName());
					Map<String, Object> params =null;
					String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+chapter.getId()+"'";
					List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
					if(videos!=null&&videos.size()>0){
						chapter.setName(chapter.getName()+"---(试学)");
					}
//					chapter.setName(chapter.getName());
				}
			}
		}
		
		
		TreeNode fistTree=new TreeNode();
		
		fistTree.setId(courseId);
		fistTree.setLevel(1);
		fistTree.setType("1");
		fistTree.setContenttype("1");
		fistTree.setName(courseName);
		fistTree.setOpen(true);
		if(courseVideoflag){
			fistTree.setName(fistTree.getName()+"---(试学)");
		}
		treeNodes.add(fistTree);

		return treeNodes;
	}
	
	@Override
	public List<TreeNode> getChapterTreeBarrierAdd(String courseId,String courseName) {
		Map<String, Object> paramMap =null;
		String sql="select id,name ,parent_id as pId,level ,level as type ,level as contenttype,(case when barrier_Id is not null and barrier_Id != '' and level = 4 then true else false end) chkDisabled from oe_chapter where is_delete=0 and course_id= "+courseId +" order by sort";
		List<TreeNode> treeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
		
		boolean courseVideoflag =false;
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNode chapter:treeNodes){
				
				if(chapter.getLevel()==2){//章
					chapter.setActName(chapter.getName());
					List<TreeNode> sections=getSectionByChapter(chapter.getId());//根据章获取获取节
					boolean flag =false;
					for(TreeNode section : sections){
						List<TreeNode> topics = getTopicBySection(section.getId());//根据节获取知识点
						for(TreeNode topic:topics){
							Map<String, Object> params =null;
							String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
							List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
							if(videos!=null&&videos.size()>0){
								flag = true;
							}
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						courseVideoflag = true;
					}
//					chapter.setName(chapter.getName());
//					courseVideoSum=courseVideoSum+getVideoCountByChapter(chapter.getId());
				}
				
				
				
				if(chapter.getLevel()==3){//节
					chapter.setActName(chapter.getName());
					List<TreeNode> topics = getTopicBySection(chapter.getId());//根据节获取知识点
					boolean flag =false;
					for(TreeNode topic:topics){
						Map<String, Object> params =null;
						String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
						List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
						if(videos!=null&&videos.size()>0){
							flag = true;
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
					};
//					chapter.setName(chapter.getName());
				}
				
				
				if(chapter.getLevel()==4){//知识点
					chapter.setActName(chapter.getName());
					Map<String, Object> params =null;
					String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+chapter.getId()+"'";
					List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
					if(videos!=null&&videos.size()>0){
						chapter.setName(chapter.getName()+"---(试学)");
					}
//					chapter.setName(chapter.getName());
				}
			}
		}
		
		
		TreeNode fistTree=new TreeNode();
		
		fistTree.setId(courseId);
		fistTree.setLevel(1);
		fistTree.setType("1");
		fistTree.setContenttype("1");
		fistTree.setName(courseName);
		fistTree.setOpen(true);
		if(courseVideoflag){
			fistTree.setName(fistTree.getName()+"---(试学)");
		}
		treeNodes.add(fistTree);
		
		return treeNodes;
	}

	@Override
	public List<TreeNode> getChapterTreeExamPaperAdd(String courseId,String courseName) {
		Map<String, Object> paramMap =null;
		String sql="select id,name ,parent_id as pId,level ,level as type ,level as contenttype,(case when barrier_Id is not null and barrier_Id != '' and level = 4 then true else false end) chkDisabled from oe_chapter where is_delete=0 and course_id= "+courseId +" order by sort";
		List<TreeNode> treeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, paramMap);
		
		boolean courseVideoflag =false;
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNode chapter:treeNodes){
				
				if(chapter.getLevel()==2){//章
					chapter.setActName(chapter.getName());
					List<TreeNode> sections=getSectionByChapter(chapter.getId());//根据章获取获取节
					boolean flag =false;
					for(TreeNode section : sections){
						List<TreeNode> topics = getTopicBySection(section.getId());//根据节获取知识点
						for(TreeNode topic:topics){
							Map<String, Object> params =null;
							String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
							List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
							if(videos!=null&&videos.size()>0){
								flag = true;
							}
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
						courseVideoflag = true;
					}
//					chapter.setName(chapter.getName());
//					courseVideoSum=courseVideoSum+getVideoCountByChapter(chapter.getId());
				}
				
				
				
				if(chapter.getLevel()==3){//节
					chapter.setActName(chapter.getName());
					List<TreeNode> topics = getTopicBySection(chapter.getId());//根据节获取知识点
					boolean flag =false;
					for(TreeNode topic:topics){
						Map<String, Object> params =null;
						String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+topic.getId()+"'";
						List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
						if(videos!=null&&videos.size()>0){
							flag = true;
						}
					}
					if(flag){
						chapter.setName(chapter.getName()+"---(试学)");
					};
//					chapter.setName(chapter.getName());
				}
				
				
				if(chapter.getLevel()==4){//知识点
					chapter.setActName(chapter.getName());
					Map<String, Object> params =null;
					String topicSql = "select * from oe_video where is_delete=0 and is_try_learn=1 and chapter_id = '"+chapter.getId()+"'";
					List<Video> videos=videoResDao.findEntitiesByJdbc(Video.class, topicSql, params);
					if(videos!=null&&videos.size()>0){
						chapter.setName(chapter.getName()+"---(试学)");
					}
//					chapter.setName(chapter.getName());
				}
			}
		}
		
		
		TreeNode fistTree=new TreeNode();
		
		fistTree.setId(courseId);
		fistTree.setLevel(1);
		fistTree.setType("1");
		fistTree.setContenttype("1");
		fistTree.setName(courseName);
		fistTree.setOpen(true);
		if(courseVideoflag){
			fistTree.setName(fistTree.getName()+"---(试学)");
		}
		treeNodes.add(fistTree);
		
		return treeNodes;
	}

}
