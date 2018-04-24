package com.xczhihui.bxg.online.web.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.OrderVo;

/**
 * 订单业务层接口类
 *
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
public interface OrderService {

    /**
     * 获取用户全部订单信息
     * @param request
     * @return 所有订单信息
     */
    public Page<OrderVo> getMyAllOrder(Integer  orderStatus,Integer timeQuantum,Integer pageNumber, Integer pageSize,HttpServletRequest request);

    /**
     * 为购买用户发送消息通知
     */
    public  void  savePurchaseNotice(String basePath,String orderNo);

    /**
     * 返回当前订单支付状态
     */
    public Integer getOrderStatus(String orderNo);

    Integer getOrderStatusById(String orderId);

    /**
     * 获得支付需要的信息
     * @param orderNo
     * @return
     */
    public Map<String, Object> checkPayInfo(String orderNo);

    public OrderVo findOrderByOrderId(String orderId);


    /**
	 * 检查订单是否被支付
	 * @param orderNo
	 * @return
	 */
	public String checkOrder(String orderNo);

    /**
     * 取消订单或删除订单
     * @param type:0删除订单 1:取消订单
     * @param orderNo
     */
    public void  updateOrderStatu(Integer type,String orderNo,OnlineUser user);

    /**
     *查询目前购买的课程能参与的活动,以及每种活动下的购买课程信息
     * @param ids 购买课程的id数组
     * @return
     */
    public List<Map<String,Object>> findActivityOrder(HttpServletRequest request,String ids);

    /**
     * 获取用户购买课程所享受的所有活动，以及每种活动下能参与的课程
     * @param ids
     * @return
     */
    public List<Map<String,Object>>  findActivityCourse(String ids);

    /**
     * 根据课程id查询课程
     * @param idArr  课程id号
     * @return 返回对应的课程对象
     */
    public  List<Map<String,Object>>   findNotActivityOrder(String  idArr,String orderNo,HttpServletRequest request);

	/** 
	 * Description：获取用户消费记录
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object consumptionList(String userId, Integer pageNumber,
			Integer pageSize);

    /**
     * 修改订单号
     * @param orderNo
     * @param orderId
     */
	void updateOrderNo(String orderNo,String orderId);
	/**
	 * 
	 * Description：根据订单号和订单状态查找订单信息
	 *              如果传递status 等于null，就是查询全部  
	 * @param orderId
	 * @param status
	 * @return
	 * @return OrderVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	OrderVo findOrderByOrderNoAndStatus(String orderId, Integer status);
}
