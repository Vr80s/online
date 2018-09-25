package net.shopxx.merge.service.impl;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.entity.*;
import net.shopxx.merge.service.OrderOperService;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.vo.*;
import net.shopxx.service.*;
import net.shopxx.util.SystemUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * 熊猫中医与shop用户关系
 */
@Service
public class OrderOperServiceImpl implements OrderOperService {

	@Inject
	private SkuService skuService;
	@Inject
	private ReceiverService receiverService;
	@Inject
	private OrderService orderService;
	@Inject
	private PaymentMethodService paymentMethodService;
	@Inject
	private ShippingMethodService shippingMethodService;
	@Inject
	private UsersRelationService usersRelationService;
	@Inject
	private CouponCodeService couponCodeService;
	@Inject
	private AreaService areaService;
	@Inject
	private CartService cartService;
	@Inject
	private OrderShippingService orderShippingService;
	@Inject
	private StoreService storeService;

	@Override
	@Transactional(readOnly = true)
	public void checkSku(Long skuId, Integer quantity) {
		if (quantity == null || quantity < 1) {
			throw new RuntimeException("数量有误");
		}
		Sku sku = skuService.find(skuId);
		if (sku == null) {
			throw new RuntimeException("商品不存在");
		}
		if (Product.Type.GIFT.equals(sku.getType())) {
			throw new RuntimeException("商品为赠品");
		}
		if (!sku.getIsActive()) {
			throw new RuntimeException("商品无效");
		}
		if (!sku.getIsMarketable()) {
			throw new RuntimeException("商品未上架");
		}
		if (quantity > sku.getAvailableStock()) {
			throw new RuntimeException("商品可用库存不足");
		}
		if (sku.getProduct().getStore().hasExpired()) {
			throw new RuntimeException("店铺已关闭");
		}
	}

	@Override
	@Transactional
	public Map<String, Object> checkout(Long skuId, Integer quantity, String cartItemIds, String ipandatcmUserId) {
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Cart currentCart = currentUser.getCart();
		Map<String,Object> map = new HashMap<>();
		Cart cart;
		Order.Type orderType;
		if (skuId != null) {
			Sku sku = skuService.find(skuId);
			if (sku == null) {
				throw new RuntimeException("信息有误");
			}
			if (Product.Type.GIFT.equals(sku.getType())) {
				throw new RuntimeException("信息有误");
			}
			if (quantity == null || quantity < 1) {
				throw new RuntimeException("信息有误");
			}

			cart = generateCart(currentUser, sku, quantity, null);

			switch (sku.getType()) {
				case GENERAL:
					orderType = Order.Type.GENERAL;
					break;
				case EXCHANGE:
					orderType = Order.Type.EXCHANGE;
					break;
				default:
					orderType = null;
					break;
			}
		}else if(StringUtils.isNotBlank(cartItemIds)){
			cart = generateCart(currentUser, null, null,cartItemIds);
			orderType = Order.Type.GENERAL;
		} else {
			cart = currentCart;
			orderType = Order.Type.GENERAL;
		}
		if (cart == null || cart.isEmpty()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasNotActive()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasNotMarketable()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasLowStock()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasExpiredProduct()) {
			throw new RuntimeException("信息有误");
		}
		if (orderType == null) {
			throw new RuntimeException("信息有误");
		}

		Receiver defaultReceiver = receiverService.findDefault(currentUser);
		List<Order> orders = orderService.generate(orderType, cart, defaultReceiver, null, null, null, null, null, null);

		BigDecimal price = BigDecimal.ZERO;
		BigDecimal fee = BigDecimal.ZERO;
		BigDecimal freight = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal promotionDiscount = BigDecimal.ZERO;
		BigDecimal couponDiscount = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal amountPayable = BigDecimal.ZERO;
		Long rewardPoint = 0L;
		Long exchangePoint = 0L;
		boolean isDelivery = false;

		for (Order order : orders) {
			price = price.add(order.getPrice());
			fee = fee.add(order.getFee());
			freight = freight.add(order.getFreight());
			tax = tax.add(order.getTax());
			promotionDiscount = promotionDiscount.add(order.getPromotionDiscount());
			couponDiscount = couponDiscount.add(order.getCouponDiscount());
			amount = amount.add(order.getAmount());
			amountPayable = amountPayable.add(order.getAmountPayable());
			rewardPoint = rewardPoint + order.getRewardPoint();
			exchangePoint = exchangePoint + order.getExchangePoint();
			if (order.getIsDelivery()) {
				isDelivery = true;
			}
		}
//		Member member = cart.getMember();
//		System.out.println(member.getUsername());
		/*====数据处理开始====*/
		Set<CartItemVO> cartItems = new HashSet<CartItemVO>();
		for (CartItem cartItem : cart.getCartItems()) {
			CartItemVO cartItemVO = new CartItemVO();
			BeanUtils.copyProperties(cartItem,cartItemVO);
			cartItemVO.setId(cartItem.getId());
			SkuVO skuVO = new SkuVO();
			Sku sku = cartItem.getSku();
			BeanUtils.copyProperties(sku, skuVO);
			cartItemVO.setSku(skuVO);
			cartItems.add(cartItemVO);
		}

		CartVO cartVO = new CartVO();
		BeanUtils.copyProperties(cart,cartVO);

		cartVO.setCartItems(cartItems);
		List<OrderVO> orderVOs = new ArrayList<>();
		for (Order order : orders) {
			OrderVO orderVO = new OrderVO();
			BeanUtils.copyProperties(order,orderVO);
			List<OrderItemVO> orderItemVOList = new ArrayList<>();
			for(OrderItem orderItem : order.getOrderItems()){
				OrderItemVO orderItemVO = new OrderItemVO();
				BeanUtils.copyProperties(orderItem,orderItemVO);
				orderItemVOList.add(orderItemVO);
			}
			orderVO.setOrderItems(orderItemVOList);
			orderVOs.add(orderVO);
		}
		/*====数据处理结束====*/

		map.put("skuId", skuId);
		map.put("quantity", quantity);
		map.put("cart", cartVO);
		map.put("orderType", orderType.toString());
//		map.put("defaultReceiver", defaultReceiver);
		map.put("orders", orderVOs);
		map.put("price", price);
		map.put("fee", fee);
		map.put("freight", freight);
		map.put("tax", tax);
		map.put("promotionDiscount", promotionDiscount);
		map.put("couponDiscount", couponDiscount);
		map.put("amount", amount);
		map.put("amountPayable", amountPayable);
		map.put("rewardPoint", rewardPoint);
		map.put("exchangePoint", exchangePoint);
		map.put("isDelivery", isDelivery);

		List<PaymentMethod> paymentMethods = new ArrayList<>();
		if (cart.contains(Store.Type.GENERAL)) {
			CollectionUtils.select(paymentMethodService.findAll(), new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					PaymentMethod paymentMethod = (PaymentMethod) object;
					return paymentMethod != null && PaymentMethod.Method.ONLINE.equals(paymentMethod.getMethod());
				}
			}, paymentMethods);
		} else {
			paymentMethods = paymentMethodService.findAll();
		}
//		map.put("paymentMethods", paymentMethods);
//		map.put("shippingMethods", shippingMethodService.findAll());
//		String str = JsonUtils.toJson(map);
//		System.out.println(str);
		return map;
	}

	/**
	 *
	 * @param skuId 库存id
	 * @param quantity 数量
	 * @param cartTag 购物车标签
	 * @param receiverId 收货地址
	 * @param shippingMethodId 配送方式
	 * @param code 优惠码
	 * @param invoiceTitle 发票抬头
	 * @param invoiceTaxNumber 纳税人识别码
	 * @param balance 金额
	 * @param memo 备忘
	 * @param ipandatcmUserId
	 * @return
	 */
	@Override
	@Transactional
	public Map<String, Object> create(String cartItemIds,Long skuId, Integer quantity, String cartTag, Long receiverId,  Long shippingMethodId,
									  String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo, String ipandatcmUserId) {
		Long paymentMethodId = 1L;
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Cart currentCart = currentUser.getCart();
		Map<String, Object> data = new HashMap<>();
		Cart cart;
		Order.Type orderType;
		if (skuId != null) {
			Sku sku = skuService.find(skuId);
			if (sku == null) {
				throw new RuntimeException("商品不存在");
			}
			if (Product.Type.GIFT.equals(sku.getType())) {
				throw new RuntimeException("商品为赠品");
			}
			if (quantity == null || quantity < 1) {
				throw new RuntimeException("商品库存不足");
			}

			cart = generateCart(currentUser, sku, quantity, null);

			switch (sku.getType()) {
				case GENERAL:
					orderType = Order.Type.GENERAL;
					break;
				case EXCHANGE:
					orderType = Order.Type.EXCHANGE;
					break;
				default:
					orderType = null;
					break;
			}
		}else if(StringUtils.isNotBlank(cartItemIds)){
			cart = generateCart(currentUser, null, null,cartItemIds);
			cartService.deleteByIds(cartItemIds);
			orderType = Order.Type.GENERAL;
		} else {
			cart = currentCart;
			orderType = Order.Type.GENERAL;
		}
		if (cart == null || cart.isEmpty()) {
			throw new RuntimeException("商品信息有误");
		}
		if (cartTag != null && !StringUtils.equals(cart.getTag(), cartTag)) {
			throw new RuntimeException("购物车发生变化");
		}
		if (cart.hasNotActive()) {
			throw new RuntimeException("存在已失效商品");
		}
		if (cart.hasNotMarketable()) {
			throw new RuntimeException("存在已下架商品");
		}
		if (cart.hasLowStock()) {
			throw new RuntimeException("存在库存不足商品");
		}
		if (cart.hasExpiredProduct()) {
			throw new RuntimeException("存在已过期店铺商品");
		}
		if (orderType == null) {
			throw new RuntimeException("订单类型有误");
		}
		Receiver receiver = cart.getIsDelivery() ? receiverService.find(receiverId) : null;
		if (cart.getIsDelivery() && (receiver == null || !currentUser.equals(receiver.getMember()))) {
			throw new RuntimeException("不支持的物流方式");
		}
		if (balance != null && balance.compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("总价格不能小于0");
		}
		if (balance != null && balance.compareTo(currentUser.getAvailableBalance()) > 0) {
			throw new RuntimeException("可用余额不足");
		}
		if (currentUser.getPoint() < cart.getExchangePoint()) {
			throw new RuntimeException("积分不足");
		}
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		if (cart.contains(Store.Type.GENERAL) && paymentMethod != null && PaymentMethod.Method.OFFLINE.equals(paymentMethod.getMethod())) {
			throw new RuntimeException("存在已下架商品");
		}
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		if (cart.getIsDelivery() && shippingMethod == null) {
			throw new RuntimeException("配送方式不为空");
		}
		CouponCode couponCode = couponCodeService.findByCode(code);
		if (couponCode != null && couponCode.getCoupon() != null && !cart.isValid(couponCode.getCoupon().getStore(), couponCode)) {
			throw new RuntimeException("优惠码已失效");
		}
		Invoice invoice = StringUtils.isNotEmpty(invoiceTitle) ? new Invoice(invoiceTitle, invoiceTaxNumber, null) : null;
		List<Order> orders = orderService.create(orderType, cart, receiver, paymentMethod, shippingMethod, couponCode, invoice, balance, memo);
		List<String> orderSns = new ArrayList<>();
		for (Order order : orders) {
			if (order != null && order.getAmount().compareTo(order.getAmountPaid()) > 0 && order.getAmountPayable().compareTo(BigDecimal.ZERO) > 0) {
				orderSns.add(order.getSn());
			}
		}
		data.put("orderSns", orderSns);
		return data;
	}

	@Override
	public List<OrderVO> findPage(OrderVO.Type type, OrderVO.Status status, ScoreVO store, String ipandatcmUserId, ProductVO product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, int pageNumber,int pageSize) {
		//获取当前用户
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Store ss = storeService.findByBusinessId(10101L);
		Pageable pageable = new Pageable(pageNumber, pageSize);
		String t = null;
		if(type != null){
			t = type.toString();
		}
		String s = null;
		if(status != null){
			s = status.toString();
		}
		List<Order> orderList = orderService.findPage(t == null?null:Order.Type.valueOf(t.trim()), s == null?null:Order.Status.valueOf(s.trim()), null, currentUser, null, isPendingReceive, isPendingRefunds, isUseCouponCode, isExchangePoint, isAllocatedStock, hasExpired, pageable).getContent();
		List<OrderVO> list = new ArrayList<>();
		for(Order order : orderList){
			OrderVO o = new OrderVO();
			BeanUtils.copyProperties(order,o);
			o.setId(order.getId());
			list.add(o);
		}
		return list;
	}

	@Override
	public OrderVO findBySn(String sn) {
		Order order = orderService.findBySn(sn);
		OrderVO o = new OrderVO();
		if(order != null){
			BeanUtils.copyProperties(order,o);
		}
		return o;
	}

	@Override
	public List<OrderVO> findBySns(String sns) {
		String[] snArr = sns.split(",");
		List<OrderVO> orderVOs = new ArrayList<>();
		for (String sn : snArr){
			OrderVO order = this.findBySn(sn);
			if(order != null){
				orderVOs.add(order);
			}
		}
		return orderVOs;
	}

	@Override
	@Transactional
	public Map calculate(String cartItemIds, Long skuId, Integer quantity, Long receiverId, Long shippingMethodId, String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo, String ipandatcmUserId) {
		Long paymentMethodId = 1L;

		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Cart currentCart = currentUser.getCart();

		Map<String, Object> data = new HashMap<>();
		Cart cart;
		Order.Type orderType;
		if (skuId != null) {
			Sku sku = skuService.find(skuId);
			if (sku == null) {
				throw new RuntimeException("信息有误");
			}
			if (Product.Type.GIFT.equals(sku.getType())) {
				throw new RuntimeException("信息有误");
			}
			if (quantity == null || quantity < 1) {
				throw new RuntimeException("信息有误");
			}

			cart = generateCart(currentUser, sku, quantity,null);

			switch (sku.getType()) {
				case GENERAL:
					orderType = Order.Type.GENERAL;
					break;
				case EXCHANGE:
					orderType = Order.Type.EXCHANGE;
					break;
				default:
					orderType = null;
					break;
			}
		}else if(StringUtils.isNotBlank(cartItemIds)){
			cart = generateCart(currentUser, null, null,cartItemIds);
			orderType = Order.Type.GENERAL;
		} else {
			cart = currentCart;
			orderType = Order.Type.GENERAL;
		}
		if (cart == null || cart.isEmpty()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasNotActive()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasNotMarketable()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasLowStock()) {
			throw new RuntimeException("信息有误");
		}
		if (cart.hasExpiredProduct()) {
			throw new RuntimeException("信息有误");
		}
		if (orderType == null) {
			throw new RuntimeException("信息有误");
		}

		Receiver receiver = receiverService.find(receiverId);
		if (receiver != null && !currentUser.equals(receiver.getMember())) {
			throw new RuntimeException("收件人信息有误");
		}
		if (balance != null && balance.compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("支付信息有误");
		}
		if (balance != null && balance.compareTo(currentUser.getAvailableBalance()) > 0) {
			throw new RuntimeException("余额不足");
		}
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		if (cart.contains(Store.Type.GENERAL) && paymentMethod != null && PaymentMethod.Method.OFFLINE.equals(paymentMethod.getMethod())) {
			throw new RuntimeException("信息有误");
		}
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		CouponCode couponCode = couponCodeService.findByCode(code);
		if (couponCode != null && couponCode.getCoupon() != null && !cart.isValid(couponCode.getCoupon().getStore(), couponCode)) {
			throw new RuntimeException("优惠码信息有误");
		}
		Invoice invoice = StringUtils.isNotEmpty(invoiceTitle) ? new Invoice(invoiceTitle, invoiceTaxNumber, null) : null;
		List<Order> orders = orderService.generate(orderType, cart, receiver, paymentMethod, shippingMethod, couponCode, invoice, balance, memo);

		BigDecimal price = BigDecimal.ZERO;
		BigDecimal fee = BigDecimal.ZERO;
		BigDecimal freight = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal promotionDiscount = BigDecimal.ZERO;
		BigDecimal couponDiscount = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal amountPayable = BigDecimal.ZERO;
		Long rewardPoint = 0L;
		Long exchangePoint = 0L;
		boolean isDelivery = false;

		for (Order order : orders) {
			price = price.add(order.getPrice());
			fee = fee.add(order.getFee());
			freight = freight.add(order.getFreight());
			tax = tax.add(order.getTax());
			promotionDiscount = promotionDiscount.add(order.getPromotionDiscount());
			couponDiscount = couponDiscount.add(order.getCouponDiscount());
			amount = amount.add(order.getAmount());
			amountPayable = amountPayable.add(order.getAmountPayable());
			rewardPoint = rewardPoint + order.getRewardPoint();
			exchangePoint = exchangePoint + order.getExchangePoint();
			if (order.getIsDelivery()) {
				isDelivery = true;
			}
		}

		data.put("price", price);
		data.put("fee", fee);
		data.put("freight", freight);
		data.put("tax", tax);
		data.put("promotionDiscount", promotionDiscount);
		data.put("couponDiscount", couponDiscount);
		data.put("amount", amount);
		data.put("amountPayable", amountPayable);
		data.put("rewardPoint", rewardPoint);
		data.put("exchangePoint", exchangePoint);
		data.put("isDelivery", isDelivery);
		return data;
	}

	/**
	 *
	 * @param consignee 收件人
	 * @param address 地址
	 * @param zipCode 邮编
	 * @param phone 手机号
	 * @param isDefault 默认地址
	 * @param areaId 地区id
	 * @param ipandatcmUserId
	 */
	@Override
	@Transactional
	public void addReceiver(String consignee, String address, String zipCode, String phone, Boolean isDefault, Long areaId, String ipandatcmUserId) {
		Receiver receiver = new Receiver();
		receiver.setConsignee(consignee);
		receiver.setAddress(address);
		receiver.setZipCode(zipCode);
		receiver.setPhone(phone);
		receiver.setIsDefault(isDefault);

		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Area area = areaService.find(areaId);
		receiver.setArea(area);
		if (Receiver.MAX_RECEIVER_COUNT != null && currentUser.getReceivers().size() >= Receiver.MAX_RECEIVER_COUNT) {
			throw new RuntimeException("收货地址超过最大数量");
		}
		receiver.setAreaName(null);
		receiver.setMember(currentUser);
		Receiver re = receiverService.save(receiver);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReceiverVO> receiverlist(String ipandatcmUserId) {
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Pageable pageable = new Pageable(0, 100);
		Page<Receiver> page = receiverService.findPage(currentUser, pageable);
		List<Receiver> receiverList = page.getContent();
		List<ReceiverVO> receiverVOList = new ArrayList<>();
		for(Receiver receiver : receiverList){
			ReceiverVO receiverVO = new ReceiverVO();
			BeanUtils.copyProperties(receiver,receiverVO);
			receiverVO.setId(receiver.getId());
			receiverVOList.add(receiverVO);
		}
		return receiverVOList;
	}

	@Override
	@Transactional(readOnly = true)
	public ReceiverVO findReceiverById(Long receiverId, String ipandatcmUserId) {
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Receiver receiver = receiverService.find(receiverId);
		if(receiver == null || !receiver.getMember().getId().equals(currentUser.getId())){
			throw new RuntimeException("查询信息有误");
		}
		ReceiverVO receiverVO = new ReceiverVO();
		BeanUtils.copyProperties(receiver,receiverVO);
		Area area = receiver.getArea();
		AreaVO areaVO = new AreaVO();
		areaVO.setId(area.getId());
		areaVO.setTreePath(area.getTreePath());
		receiverVO.setArea(areaVO);
		receiverVO.setId(receiver.getId());
		return receiverVO;
	}

	/**
	 *
	 * @param consignee 收件人
	 * @param address 地址
	 * @param zipCode 邮编
	 * @param phone 手机号
	 * @param isDefault 默认地址
	 * @param areaId 地区id
	 * @param ipandatcmUserId
	 */
	@Override
	@Transactional
	public void updateReceiver(Long receiverId, String consignee, String address, String zipCode, String phone, Boolean isDefault, Long areaId, String ipandatcmUserId) {
		Receiver receiver = receiverService.find(receiverId);
		if (receiver == null) {
			throw new RuntimeException("修改信息有误");
		}
		receiver.setConsignee(consignee);
		receiver.setAddress(address);
		receiver.setZipCode(zipCode);
		receiver.setPhone(phone);
		receiver.setIsDefault(isDefault);

		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Area area = areaService.find(areaId);
		receiver.setArea(area);
		receiver.setAreaName(null);
		receiver.setMember(currentUser);
		receiverService.update(receiver);
	}

	@Override
	@Transactional
	public void deleteReceiver(Long receiverId, String ipandatcmUserId) {
		Receiver receiver = receiverService.find(receiverId);
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		if (receiver == null || !receiver.getMember().getId().equals(currentUser.getId())) {
			throw new RuntimeException("删除信息有误");
		}
		receiverService.delete(receiver);
	}

	@Override
	public void cancel(String sn, String ipandatcmUserId) {
		Order order = orderService.findBySn(sn);
		if (order == null) {
			throw new RuntimeException("未找到该订单");
		}
		if (order.hasExpired() || (!Order.Status.PENDING_PAYMENT.equals(order.getStatus()) && !Order.Status.PENDING_REVIEW.equals(order.getStatus()))) {
			throw new RuntimeException("未找到该订单");
		}
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		if (!orderService.acquireLock(order, currentUser)) {
			throw new RuntimeException("member.order.locked");
		}
		orderService.cancel(order);
	}

	@Override
	public void receive(String sn, String ipandatcmUserId) {
		Order order = orderService.findBySn(sn);
		if (order == null) {
			throw new RuntimeException("未找到该订单");
		}

		if (order.hasExpired() || !Order.Status.SHIPPED.equals(order.getStatus())) {
			throw new RuntimeException("未找到该订单");
		}
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		if (!orderService.acquireLock(order, currentUser)) {
			throw new RuntimeException("member.order.locked");
		}
		orderService.receive(order);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findArea(Long parentId) {
		List<Map<String, Object>> data = new ArrayList<>();
		Area parent = areaService.find(parentId);
		Collection<Area> areas = parent != null ? parent.getChildren() : areaService.findRoots();
		for (Area area : areas) {
			Map<String, Object> item = new HashMap<>();
			item.put("name", area.getName());
			item.put("value", area.getId());
			data.add(item);
		}
		return data;
	}

	@Override
	public Map<String, Object> getTransitSteps(Long shippingId) {
		Map<String, Object> data = new HashMap<>();
		OrderShipping orderShipping = orderShippingService.find(shippingId);
		if (orderShipping == null) {
			throw new RuntimeException("订单信息有误");
		}
		Setting setting = SystemUtils.getSetting();
		if (StringUtils.isEmpty(setting.getKuaidi100Customer()) || StringUtils.isEmpty(setting.getKuaidi100Key()) || StringUtils.isEmpty(orderShipping.getDeliveryCorpCode()) || StringUtils.isEmpty(orderShipping.getTrackingNo())) {
			throw new RuntimeException("物流信息有误");
		}
		data.put("transitSteps", orderShippingService.getTransitSteps(orderShipping));
		return data;
	}

	/**
	 * 生成购物车
	 *
	 * @param member
	 *            会员
	 * @param sku
	 *            SKU
	 * @param quantity
	 *            数量
	 * @param cartItemIds
	 * @return 购物车
	 */
	public Cart generateCart(Member member, Sku sku, Integer quantity, String cartItemIds) {
		Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");

		Cart cart = new Cart();
		cart.setMember(member);
		Set<CartItem> cartItems = new HashSet<>();
		cart.setCartItems(cartItems);
		if(sku != null){
			Assert.state(!Product.Type.GIFT.equals(sku.getType()), "[Assertion failed] - sku type can't be GIFT");
			Assert.notNull(sku, "[Assertion failed] - sku is required; it must not be null");
			Assert.notNull(quantity, "[Assertion failed] - quantity is required; it must not be null");
			Assert.state(quantity > 0, "[Assertion failed] - quantity must be greater than 0");

			CartItem cartItem = new CartItem();
			cartItem.setSku(sku);
			cartItem.setQuantity(quantity);
			cartItems.add(cartItem);
			cartItem.setCart(cart);
		}else if(StringUtils.isNotBlank(cartItemIds)){
			String[] idsStr = cartItemIds.split(",");

			List<CartItem> cartItemList = cartService.getCartItemListByIds(idsStr);
			for (CartItem cartItem:cartItemList){
				CartItem ci = new CartItem();
				ci.setSku(cartItem.getSku());
				ci.setQuantity(cartItem.getQuantity());
				cartItems.add(ci);
				ci.setCart(cart);
			}
		}
		return cart;
	}

}