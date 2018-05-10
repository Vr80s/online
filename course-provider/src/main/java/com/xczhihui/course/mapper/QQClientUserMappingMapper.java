package com.xczhihui.course.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.QQClientUserMapping;

/**
 * <p>
  *  Mapper 接口
 * </p>
 * @author yuxin
 * @since 2017-12-09
 */
public interface QQClientUserMappingMapper extends BaseMapper<QQClientUserMapping> {

	/**
	 * 
	 * Description：qq用户唯一标识获取  
	 * @param openId
	 * @return
	 * @return QQClientUserMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	QQClientUserMapping selectQQClientUserMappingByOpenId(String openId);
	
	/**
	 * 
	 * Description： 通过熊猫中医用户id获取 
	 * @param userId
	 * @return
	 * @return WeiboClientUserMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	QQClientUserMapping selectQQClientUserMappingByUserIdAndUniondId(@Param("userId")String userId,@Param("unionId")String unionId);

	void updateByIdByMapper(String id);

	QQClientUserMapping selectQQClientUserMappingByUnionId(String unionId);
	
	
	Map<String,Object> selectUserBindingInfo(@Param("userId")String userId);

	void deleteAccount(String id);

	QQClientUserMapping selectQQClientUserMappingByUserIdAndOpenId(
			@Param("userId")String userId, @Param("openId")String openId);

	QQClientUserMapping selectQQClientUserMappingByUserId(@Param("userId")String userId);
}