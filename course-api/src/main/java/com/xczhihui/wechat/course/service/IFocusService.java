package com.xczhihui.wechat.course.service;

import java.util.List;

import com.xczhihui.wechat.course.vo.FocusVo;

public interface IFocusService {
	/**
	 * 
	 * Description：查找我的粉丝总数我关注的主播总数
	 * @param userId
	 * @return
	 * @return List<Integer>  list.get(0)我的粉丝总数  list.get(1)我关注的主播总数
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<Integer> selectFocusAndFansCount(String userId);
	/**
	 * 
	 * Description：我关注的主播集合
	 * @param userId
	 * @return
	 * @return List<FocusVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<FocusVo> selectFocusList(String userId);
	/**
	 * 
	 * Description：我的粉丝集合
	 * @param userId
	 * @return
	 * @return List<FocusVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<FocusVo> selectFansList(String userId);
	/**
	 * 
	 * Description：是否关注了某个主播
	 * @param userId
	 * @return
	 * @return Integer  0 没有关注  1 关注
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Integer isFoursLecturer(String userId,String lecturerId);
	/**
	 * Description：
	 * @param lecturerId
	 * @param id
	 * @param parseInt
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	void updateFocus(String lockId,String lecturerId, String id, Integer parseInt);
}
