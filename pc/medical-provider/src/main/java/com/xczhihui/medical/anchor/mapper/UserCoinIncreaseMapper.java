package com.xczhihui.medical.anchor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户熊猫币增长明细mapper
 * @author zhuwenbao
 */
public interface UserCoinIncreaseMapper extends BaseMapper<UserCoinIncrease> {

	List<UserCoinIncreaseVO> listCourseOrder(@Param("userId") String userId, @Param("page") Page<UserCoinIncreaseVO> page
			, @Param("gradeName") String gradeName, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

	List<UserCoinIncreaseVO> listGiftOrder(@Param("userId") String userId, @Param("page") Page<UserCoinIncreaseVO> page
			, @Param("gradeName") String gradeName, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

	/**
	 * 根据课程id获取课程苹果扣除的总数
	 */
	BigDecimal sumIosBrokerageValueByCourseId(String courseId);

	/**
	 * 根据直播id获取课程苹果扣除的总数
	 */
	BigDecimal sumIosBrokerageValueByLiveId(String liveId);

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
	BigDecimal sumGiftTotalPriceByLiveId(String liveId);

	/**
	 * 礼物排行榜:总贡献值排序
	 */
	List<UserCoinIncreaseVO> rankGiftList(@Param("liveId") String liveId, @Param("page") Page<UserCoinIncreaseVO> page);

	/**
	 * 礼物排行榜:获取用户对应的‘获得熊猫币’
	 */
	BigDecimal sumValue(@Param("giver") String giver, @Param("receiver") String receiver);
}