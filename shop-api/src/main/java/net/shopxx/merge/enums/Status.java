package net.shopxx.merge.enums;

/**
 * 状态
 */
public enum Status {

	/**
	 * 等待付款
	 */
	PENDING_PAYMENT,

	/**
	 * 等待审核
	 */
	PENDING_REVIEW,

	/**
	 * 等待发货
	 */
	PENDING_SHIPMENT,

	/**
	 * 已发货
	 */
	SHIPPED,

	/**
	 * 已收货
	 */
	RECEIVED,

	/**
	 * 已完成
	 */
	COMPLETED,

	/**
	 * 已失败
	 */
	FAILED,

	/**
	 * 已取消
	 */
	CANCELED,

	/**
	 * 已拒绝
	 */
	DENIED
}