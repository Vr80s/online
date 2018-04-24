package com.xczhihui.course.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xczhihui.course.vo.OnlineUserVO;


public interface IMyInfoService {
	
	/**
	 * Description：根据用户id查找得到   查找主播的学员、课程数  
	 * @param userId
	 * @return
	 * @return List<BigDecimal>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	List<BigDecimal> selectCollegeCourseXmbNumber(String userId);
	
	/**
	 * 结算页面记录信息
	 * Description：
	 * @param id
	 * @return
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<Map<String,Object>> selectSettlementList(Integer pageNumber,Integer pageSize,String id);

	/**
	 * 提现页面记录信息
	 * Description：
	 * @param id
	 * @return
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<Map<String,Object>> selectWithdrawalList(Integer pageNumber,Integer pageSize,String id);

    /**
     * 更改用户信息
     * Description：
     * @param user
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	void updateUserSetInfo(OnlineUserVO user);

    /**
     * 
     * Description：获取用户权限：0 普通用户  1 主播
     * @param id
     * @return
     * @return Integer
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	Integer getUserHostPermissions(String id);
	
	/**
	 * 
	 * Description：获取推荐的主播医师
	 * @return
	 * @return List<Map<String,String>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<Map<String,Object>> hostInfoRec();

	List<Map<String,Object>> findUserWallet(Integer pageNumber,Integer pageSize, String id);
	
	
	/**
	 * 查询主播信息
	 * @param userId
	 * @return
	 */
	Map<String,Object> findHostInfoById(String userId);
	
	
}
