package com.xczhihui.medical.anchor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户熊猫币增长明细mapper
 * @author zhuwenbao
 */
public interface UserCoinIncreaseMapper extends BaseMapper<UserCoinIncrease> {

	List<UserCoinIncreaseVO> listCourseOrder(String userId);

	List<UserCoinIncreaseVO> listGiftOrder(String userId);

	/**
	 * 根据课程id获取课程苹果扣除的总数
	 */
	BigDecimal sumIosBrokerageValue(String courseId);

	/**
	 * 根据课程id获取课程获得总熊猫币
	 */
	BigDecimal sumValueByCourse(String courseId);

	/**
	 * 根据直播课程id获取礼物熊猫币
	 */
	BigDecimal sumValueByGift(String liveId);

	/**
	 * 根据直播id获取直播的礼物总价
	 */
	BigDecimal sumGiftTotalPrice(String liveId);
}