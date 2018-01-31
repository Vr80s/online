package com.xczhihui.wechat.course.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.wechat.course.model.OnlineUser;
import com.xczhihui.wechat.course.vo.OnlineUserVO;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MyInfoMapper extends BaseMapper<OnlineUser> {

	/**
	 * 
	 * Description：查找主播的学员、课程数
	 * @param userId
	 * @return
	 * @return List<BigDecimal>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<BigDecimal> selectCollegeCourseXmbNumber(String userId);
	
	/**
	 * Description：结算页面记录信息
	 * @param userId
	 * @return
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	List<Map<String, Object>> selectSettlementList(String userId);
	
	/**
	 * 
	 * Description：提现页面记录信息
	 * @param userId
	 * @return
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<Map<String, Object>> selectWithdrawalList(String userId);
	/**
	 * 
	 * Description：更改用户信息
	 * @param user
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	void updateUserSetInfo(OnlineUserVO user);
}