package com.xczhihui.bxg.online.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;









import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.service.CriticizeService;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.web.dao.VideoDao;
import com.xczhihui.bxg.online.web.service.VideoService;

/**
 * 评论接口实现类
 * ClassName: CriticizeServiceImpl.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年11月9日<br>
 */
@Service
public class CriticizeServiceImpl implements CriticizeService {

	@Autowired
	private VideoDao videoDao;
	@Autowired
	private VideoService videoService;
	
	@Override
	public void saveCriticize(CriticizeVo criticize) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		CriticizeVo cv = new CriticizeVo();
		BeanUtils.copyProperties(cv,criticize);
		/**
		 * 判断此用户是否购买过此课程
		 *   如果这个课程是一个系列的话
		 */
		boolean isBuy =   false;
		if(criticize.getCourseId()!=null){
			if(videoDao.getUserCourse(criticize.getCourseId(), criticize.getUserId()).size()>0){
				isBuy = true;
			}
		}
		criticize.setIsBuy(isBuy);
		videoDao.saveNewCriticize(cv);
	}


	@Override
	public Page<Criticize> getUserOrCourseCriticize(String teacherId, Integer courseId,
			Integer pageNumber, Integer pageSize,String userId) {
		//return videoDao.getVideoCriticize(videoId,name,pageNumber,pageSize);
		
		if(null ==teacherId && null == courseId){
			throw new RuntimeException("课程id和讲师id只能传递一个！");
		}
		if(StringUtils.isNotBlank(teacherId) && courseId!=null){
			throw new RuntimeException("课程id和讲师id只能存在一个！");
		}
		return videoDao.getUserOrCourseCriticize(teacherId,courseId,pageNumber,pageSize,userId);
	}
	
	
	@Override
	public Map<String, Object> updatePraise(Boolean isPraise,
			String criticizeId, String loginName) {
		// TODO Auto-generated method stub
		return videoService.updatePraise(isPraise, criticizeId, loginName);
	}

	@Override
	public void saveNewCriticize(CriticizeVo criticizeVo)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		videoDao.saveNewCriticize(criticizeVo);
	}

	@Override
	public void saveReply(String content, String userId,String criticizeId) {
		// TODO Auto-generated method stub
		
		videoDao.saveReply(content,userId,criticizeId);
	}


	@Override
	public Page<CriticizeVo> getVideoCriticize(String teacherId,
			String courseId, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer findUserFirstStars(Integer courseId, String userId) {
		// TODO Auto-generated method stub
		return videoDao.findUserFirstStars(courseId,userId);
	}
}
