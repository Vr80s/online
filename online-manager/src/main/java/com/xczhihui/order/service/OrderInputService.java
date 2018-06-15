package com.xczhihui.order.service;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.order.vo.OrderInputVo;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 线下订单录入
 * 
 * @author Haicheng Jiang
 */
public interface OrderInputService {
	/**
	 * 查询
	 * 
	 * @param orderVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<OrderInputVo> findOrderInputPage(OrderInputVo orderVo,
			Integer pageNumber, Integer pageSize);

	/**
	 * 新增用户
	 * 
	 * @param loginName
	 */
	public void addUser(String loginName) throws InterruptedException;

	/**
	 * 新增订单
	 * 
	 * @param vo
	 * @return 订单号
	 */
	public String addOrder(OrderInputVo vo);

	String saveOrderInput(OnlineUser user, String courseId, Double coursePrice, String createPerson, Integer orderFrom);

	public void checkOrderInput(OrderInputVo vo);

	/**
	 * Description：批量新增订单 creed: Talk is cheap,show me the code
	 * 
	 * @author name：yuxin <br>
	 *         email: yuruixin@ixincheng.com
	 * @Date: 下午 10:00 2018/1/24 0024
	 **/
	void addOrders(List<OrderInputVo> lv);

    void updateValidity(String[] id, String days);
}
