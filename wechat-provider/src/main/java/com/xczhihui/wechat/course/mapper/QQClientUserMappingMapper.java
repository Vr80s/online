package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.wechat.course.model.QQClientUserMapping;

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
	QQClientUserMapping selectQQClientUserMappingByUserId(String userId,String openId);

	void updateByIdByMapper(String id);

}