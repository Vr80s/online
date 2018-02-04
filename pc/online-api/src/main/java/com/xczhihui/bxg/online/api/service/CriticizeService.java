package com.xczhihui.bxg.online.api.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.Criticize;

/**
 * 评论接口：
 * ClassName: CriticizeService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年11月9日<br>
 */
public interface CriticizeService {
    /**
     * 提交评论
     * Description：
     * @param criticizeVo
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public void saveCriticize(CriticizeVo criticizeVo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 *  
	 * Description：得到此视频下的所有评论
	 * @param videoId 视频id
	 * @param name    当前用户登录名，用于判断是否点赞用的
	 * @param pageNumber 
	 * @param pageSize   
	 * @return
	 * @return Page<Criticize>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public Page<CriticizeVo> getVideoCriticize(String teacherId, String courseId,
			Integer pageNumber, Integer pageSize);
	/**
	 * Description：点赞和取消点赞
	 * @param isPraise
	 * @param criticizeId
	 * @param user
	 * @return
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Map<String, Object> updatePraise(Boolean isPraise,String criticizeId, String loginName);
	
	
	
	 /**
     * 提交新的评论
     * Description：
     * @param criticizeVo
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public void saveNewCriticize(CriticizeVo criticizeVo) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * Description：评论回复接口
	 * @param content 回复的内容
	 * @param userId  回复人id
	 * @param teacherId 回复的这个讲师的id
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void saveReply(String content, String userId,String criticizeId);

	/**
	 *  
	 * Description：查看用户评论或者课程评论,传递哪个参数用哪个参数评论
	 * @param teacherId  讲师id
	 * @param courseId   课程id
	 * @param pageNumber 第几页
	 * @param pageSize   每页多少条
	 * @param userId	  当前用户id
	 * @return
	 * @return Page<Criticize>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Page<Criticize> getUserOrCourseCriticize(String teacherId, Integer courseId,
			Integer pageNumber, Integer pageSize,String userId);

    /**
     * Description：判断这个星级用户是否购买过这个课程以及判断是否已经星级评论了一次此课程
     * @param courseId
     * @param userId
     * @return
     * @return Integer  返回参数： 0 未购买     1 购买了，但是没有星级评论过     2 购买了，也星级评论了
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	public Integer findUserFirstStars(Integer courseId, String userId);
	
}
