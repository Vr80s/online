//package com.xczhihui.bxg.online.web.service.impl;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.xczhihui.bxg.common.util.SLEmojiFilter;
//import com.xczhihui.bxg.common.util.bean.Page;
//import com.xczhihui.bxg.online.api.service.CriticizeService;
//import com.xczhihui.bxg.online.api.vo.CriticizeVo;
//import com.xczhihui.bxg.online.common.domain.Criticize;
//import com.xczhihui.bxg.online.web.dao.VideoDao;
//import com.xczhihui.bxg.online.web.service.VideoService;
//
///**
// * 评论接口实现类
// * ClassName: CriticizeServiceImpl.java <br>
// * Description: <br>
// * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
// * Create Time: 2017年11月9日<br>
// */
//@Service
//public class CriticizeServiceImpl implements CriticizeService {
//
//	@Autowired
//	private VideoDao videoDao;
//	@Autowired
//	private VideoService videoService;
//
//	@Override
//	public void saveCriticize(CriticizeVo criticize) throws IllegalAccessException, InvocationTargetException {
//		// TODO Auto-generated method stub
//		CriticizeVo cv = new CriticizeVo();
//		BeanUtils.copyProperties(cv,criticize);
//		/**
//		 * 判断此用户是否购买过此课程
//		 *   如果这个课程是一个系列的话
//		 */
//		boolean isBuy =   false;
//		if(criticize.getCourseId()!=null){
//			if(videoDao.getUserCourse(criticize.getCourseId(), criticize.getUserId()).size()>0){
//				isBuy = true;
//			}
//		}
//		criticize.setIsBuy(isBuy);
//		videoDao.saveNewCriticize(cv);
//	}
//
//
//	@Override
//	public Map<String,Object> getUserOrCourseCriticize(String teacherId, Integer courseId,
//			Integer pageNumber, Integer pageSize,String userId) throws UnsupportedEncodingException {
//
//
//		if(null ==teacherId && null == courseId){
//			throw new RuntimeException("课程id和讲师id只能传递一个！");
//		}
//		if(StringUtils.isNotBlank(teacherId) && courseId!=null){
//			throw new RuntimeException("课程id和讲师id只能存在一个！");
//		}
//
//		Page<Criticize> pageList  = videoDao.getUserOrCourseCriticize(teacherId,courseId,pageNumber,pageSize,userId);
//
//		List<Criticize> list1  = new ArrayList<Criticize>();
//
//		for (Criticize criticize : pageList.getItems()) {
//			/*
//			 * 评论的内容要转化，回复的内容也需要转化
//			 */
//			if(StringUtils.isNotBlank(criticize.getContent())){
//				criticize.setContent(SLEmojiFilter.emojiRecovery2(criticize.getContent()));
//			}
//			if(criticize.getReply()!=null && criticize.getReply().size()>0){
//				List<com.xczhihui.bxg.online.common.domain.Reply> replyList = criticize.getReply();
//				String replyContent = replyList.get(0).getReplyContent();
//				if(StringUtils.isNotBlank(replyContent)){
//					replyList.get(0).setReplyContent(SLEmojiFilter.emojiRecovery2(replyList.get(0).getReplyContent()));
//				}
//				criticize.setReply(replyList);
//			}
//			list1.add(criticize);
//		}
//		/**
//		 * 这里判断用户发表的评论中是否包含发表心心了，什么的如果包含的话就不返回了
//		 * 		并且判断这个用户有没有购买过这个课程
//		 */
//		Map<String,Object> map = new HashMap<String,Object>();
//		if(userId!=null && courseId!=null){
//			Integer cv = this.findUserFirstStars(courseId,userId);
//			map.put("commentCode", cv);
//		}else{
//			map.put("commentCode", 0);
//		}
//		map.put("items", list1);
//
//
//		return map;
//	}
//
//
//	@Override
//	public Map<String, Object> updatePraise(Boolean isPraise,
//			String criticizeId, String loginName) {
//		// TODO Auto-generated method stub
////		return videoService.updatePraise(isPraise, criticizeId, loginName);
//		return null;
//	}
//
//	@Override
//	public void saveNewCriticize(CriticizeVo criticizeVo)
//			throws IllegalAccessException, InvocationTargetException {
//		videoDao.saveNewCriticize(criticizeVo);
//	}
//
//	@Override
//	public void saveReply(String content, String userId,String criticizeId,Integer collectionId) {
//
//		videoDao.saveReply(content,userId,criticizeId,collectionId);
//	}
//
//	@Override
//	public Integer findUserFirstStars(Integer courseId,String createPerson) {
//		// TODO Auto-generated method stub
//		return videoDao.findUserFirstStars(courseId,createPerson);
//	}
//}
