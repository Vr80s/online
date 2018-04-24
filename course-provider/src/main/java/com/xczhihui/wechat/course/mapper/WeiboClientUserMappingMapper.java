package com.xczhihui.wechat.course.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.wechat.course.model.WeiboClientUserMapping;

/**
 * <p>
  *  Mapper 接口
 * </p>
 * @author yuxin
 * @since 2017-12-09
 */
public interface WeiboClientUserMappingMapper extends BaseMapper<WeiboClientUserMapping> {
	
	/**
	 * 
	 * Description：微博用户唯一标识获取  
	 * @param uid
	 * @return
	 * @return WeiboClientUserMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	WeiboClientUserMapping selectWeiboClientUserMappingByUid(String uid);
	
	/**
	 * 
	 * Description： 通过熊猫中医用户id获取 
	 * @param userId
	 * @return
	 * @return WeiboClientUserMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	WeiboClientUserMapping selectWeiboClientUserMappingByUserIdAndUid(@Param("userId")String userId,@Param("uid")String uid);
	
	/**
	 * 
	 * Description：更新用户信息到
	 * @param id
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	void updateByIdByMapper(String id);

	void deleteAccount(String userId);

	WeiboClientUserMapping selectWeiboClientUserMappingByUserId(String userId);

}