package net.shopxx.merge.service;


import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.enums.Status;
import net.shopxx.merge.enums.UsersType;
import net.shopxx.merge.vo.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderOperService {

    void checkSku(Long skuId, Integer quantity);

    Map<String, Object> checkout(Long skuId, Integer quantity, String cartItemIds, String ipandatcmUserId, Long shippingMethodId);

    Map<String, Object> create(String cartItemIds,Long skuId, Integer quantity, String cartTag, Long receiverId, Long shippingMethodId,
                               String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo, String ipandatcmUserId);

    /**
     * 查找订单分页
     *
     * @param type
     *            类型
     * @param status
     *            状态
     * @param store
     *            店铺
     * @param ipandatcmUserId
     *            会员id
     * @param product
     *            商品
     * @param isPendingReceive
     *            是否等待收款
     * @param isPendingRefunds
     *            是否等待退款
     * @param isUseCouponCode
     *            是否已使用优惠码
     * @param isExchangePoint
     *            是否已兑换积分
     * @param isAllocatedStock
     *            是否已分配库存
     * @param hasExpired
     *            是否已过期
     * @param pageSize
     *            分页信息
     * @return 订单分页
     */
    List<OrderVO> findPage(OrderVO.Type type, OrderVO.Status status, ScoreVO store, String ipandatcmUserId, ProductVO product, Boolean isPendingReceive,
                           Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, int pageNumber, int pageSize);

    /**
     * 根据编号查找订单
     *
     * @param sn
     *            编号(忽略大小写)
     * @return 订单，若不存在则返回null
     */
    OrderVO findBySn(String sn);

    List<OrderVO> findBySns(String sns);

    Map calculate(String cartItemIds, Long skuId, Integer quantity, Long receiverId, Long shippingMethodId, String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo, String ipandatcmUserId);

    void addReceiver(String consignee, String address, String zipCode, String phone, Boolean isDefault, Long areaId, String ipandatcmUserId);

    List<ReceiverVO> receiverlist(String ipandatcmUserId);

    ReceiverVO findReceiverById(Long receiverId, String ipandatcmUserId);

    void updateReceiver(Long receiverId, String consignee, String address, String zipCode, String phone, Boolean isDefault, Long areaId, String ipandatcmUserId);

    void setDefaultReceiver(Long receiverId, Boolean isDefault, String ipandatcmUserId);

    void deleteReceiver(Long receiverId, String ipandatcmUserId);

    List<Map<String, Object>> findArea(Long parentId);

    /**
     * 订单取消
     *
     * @param sn
     *            编号(忽略大小写)
     */
    void cancel(String sn, String ipandatcmUserId);

    /**
     * 订单收货
     *
     * @param sn
     *            编号(忽略大小写)
     */
    void receive(String sn, String ipandatcmUserId);

    /**
     * 获取物流动态
     *
     * @param shippingId
     *            订单发货
     * @return 物流动态
     */
    Map<String, Object> getTransitSteps(Long shippingId);

	/**  
	 * <p>Title: findBySnXc</p>  
	 * <p>Description: </p>  
	 * @param sn
	 * @return  
	 */ 
	OrdersVO findBySnXc(String sn);

	/**  
	 * <p>Title: findPageXc</p>  
	 * <p>Description: </p>  
	 * @param type
	 * @param status
	 * @param store
	 * @param ipandatcmUserId
	 * @param product
	 * @param isPendingReceive
	 * @param isPendingRefunds
	 * @param isUseCouponCode
	 * @param isExchangePoint
	 * @param isAllocatedStock
	 * @param hasExpired
	 * @param pageNumber
	 * @param pageSize
	 * @return  
	 */ 
	Object findPageXc(OrderPageParams orderPageParams,Status status, ScoreVO store, 
			String ipandatcmUserId, ProductVO product,UsersType usersType,OrderType orderType);

    Map payment(String orderSnsStr);

	/**
	 * 根据订单id查找订单发货
	 *
	 * @param orderI
	 *
	 * @return 订单发货，若不存在则返回null
	 */
	OrderShippingVO findOrderShippingByOrderId(Long orderId);

	/**
	 * 订单删除
	 *
	 * @param orderId
	 *            订单id
	 */
	void delete(Long orderId);

    Map<String, Object> isPaySuccess(String paymentTransactionSn);

	/**  
	 * <p>Title: findPageXc1</p>  
	 * <p>Description: </p>  
	 * @param orderPageParams
	 * @param status
	 * @param store
	 * @param ipandatcmUserId
	 * @param product
	 * @param usersType
	 * @param orderType
	 * @return  
	 */ 
	Object findPageXc1(OrderPageParams orderPageParams, Status status, ScoreVO store, String ipandatcmUserId,
			ProductVO product, UsersType usersType, OrderType orderType);
}
