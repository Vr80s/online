package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineCourse;
import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.PayRecordVo;

import java.sql.SQLException;
import java.util.List;

public interface OnlineOrderService {
	/**
	 * 取消订单或删除订单
	 * @param type:0删除订单 1:取消订单 2:未支付->已支付
	 * @param orderNo 订单号 （不是id）
	 */
	public void updateOnlineOrderStatus(Integer type, String orderNo) throws SQLException;
	/**
	 * 获取订单列表
	 * @param status
	 * @param pageNumber
	 * @return
	 * @throws SQLException
	 */
	List<OnlineOrder> getOnlineOrderList(Integer status, String userId, Integer pageNumber, Integer pageSize) throws SQLException;

	/**
	 * 学习中心列表
	 * @param pageNumber
	 * @return
	 * @throws SQLException
	 */
	List<OnlineCourse> listLearningCenter(Integer type, String userId, Integer pageNumber, Integer pageSize) throws SQLException;
	/**
	 * 根据订单号查询订单信息
	 * @param orderNo
	 * @return
	 * @throws SQLException
	 */
	OnlineOrder getOnlineOrderByOrderNo(String orderNo)throws SQLException;
	/**
	 * 分销，更新用户的上级
	 * @param id
	 * @param shareCode
	 * @throws Exception
	 */
	void updateUserParentId(String id, String shareCode)throws Exception;

	/**
	 * 消费记录
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	List<PayRecordVo> listPayRecord(String userId,
                                    Integer pageNumber, Integer pageSize);

	/**
	 * 消费记录详情
	 * @param orderNo
	 * @return
	 */
	PayRecordVo listPayRecordItem(String orderNo);

	/**
	 * 获取订单的多个商品名
	 * @param orderNo
	 * @return
	 */
	String getCourseNames(String orderNo);

	/**
	 * 修改订单号
	 * @param oldOrderNo
	 * @return 新的订单号
	 */
	String updateOrderNo(String oldOrderNo) throws SQLException;

	public OnlineOrder getOrderByOrderId(String orderId) throws SQLException;
	/**
	 *
	 * Description：重新购买课程时，需要重新生成订单，判断是否需要重新生成订单
	 * @param courseIds
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public ResponseObject orderIsExitCourseIsBuy(String courseIds, String userId)
			throws SQLException;
	/**
	 * 
	 * Description：新的版本的通过订单id获取多个课程
	 * @param orderNo
	 * @return
	 * @throws SQLException
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	ResponseObject getNewOrderAndCourseInfoByOrderId(String orderId)
			throws SQLException;
	/**
	 * 
	 * Description：新的版本的通过订单号获取多个课程
	 * @param orderNo
	 * @return
	 * @throws SQLException
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	ResponseObject getNewOrderAndCourseInfoByOrderNo(String orderNo)
			throws SQLException;
}
