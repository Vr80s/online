package net.shopxx.merge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;

import net.sf.json.JSONObject;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.dao.BusinessDao;
import net.shopxx.dao.OrderDao;
import net.shopxx.dao.OrderDeleteDao;
import net.shopxx.dao.OrderItemDeleteDao;
import net.shopxx.dao.StoreDao;
import net.shopxx.entity.Area;
import net.shopxx.entity.Business;
import net.shopxx.entity.Cart;
import net.shopxx.entity.CartItem;
import net.shopxx.entity.CouponCode;
import net.shopxx.entity.Invoice;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderDelete;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.OrderItemDelete;
import net.shopxx.entity.OrderShipping;
import net.shopxx.entity.PaymentMethod;
import net.shopxx.entity.PaymentTransaction;
import net.shopxx.entity.Product;
import net.shopxx.entity.Receiver;
import net.shopxx.entity.ShippingMethod;
import net.shopxx.entity.Sku;
import net.shopxx.entity.Store;
import net.shopxx.merge.enums.OrderType;
import net.shopxx.merge.enums.Status;
import net.shopxx.merge.enums.UsersType;
import net.shopxx.merge.service.OrderOperService;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.vo.AreaVO;
import net.shopxx.merge.vo.CartItemVO;
import net.shopxx.merge.vo.CartVO;
import net.shopxx.merge.vo.OrderItemVO;
import net.shopxx.merge.vo.OrderPageParams;
import net.shopxx.merge.vo.OrderShippingVO;
import net.shopxx.merge.vo.OrderVO;
import net.shopxx.merge.vo.OrdersVO;
import net.shopxx.merge.vo.ProductVO;
import net.shopxx.merge.vo.ReceiverVO;
import net.shopxx.merge.vo.ScoreVO;
import net.shopxx.merge.vo.SkuVO;
import net.shopxx.merge.vo.StoreVO;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.service.AreaService;
import net.shopxx.service.CartService;
import net.shopxx.service.CouponCodeService;
import net.shopxx.service.OrderService;
import net.shopxx.service.OrderShippingService;
import net.shopxx.service.PaymentMethodService;
import net.shopxx.service.PaymentTransactionService;
import net.shopxx.service.PluginService;
import net.shopxx.service.ReceiverService;
import net.shopxx.service.ShippingMethodService;
import net.shopxx.service.SkuService;
import net.shopxx.service.StoreService;
import net.shopxx.util.SystemUtils;
import net.shopxx.util.WebUtils;


/**
 * 熊猫中医与shop用户关系
 */
@Service
public class OrderOperServiceImpl implements OrderOperService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderOperServiceImpl.class);

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
	
	@Inject
	private OrderDao orderDao;
	@Inject
	private PluginService pluginService;
	@Autowired
	private CacheService redisCacheService;
	@Autowired
	private IMedicalDoctorBusinessService medicalDoctorBusinessService;

	@Inject
	private OrderItemDeleteDao orderItemDeleteDao;
	@Inject
	private OrderDeleteDao orderDeleteDao;
	@Inject
	private PaymentTransactionService paymentTransactionService;


	@Override
	@Transactional
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
	public Map<String, Object> checkout(Long skuId, Integer quantity, String cartItemIds, String ipandatcmUserId, Long shippingMethodId) {
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		Cart currentCart = currentUser.getCart();
		Map<String,Object> map = new HashMap<>();
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
				throw new RuntimeException("商品可用库存不足");
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
			throw new RuntimeException("商品信息有误");
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
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		Receiver defaultReceiver = receiverService.findDefault(currentUser);
		List<Order> orders = orderService.generate(orderType, cart, defaultReceiver, null, shippingMethod, null, null, null, null);

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
				SkuVO skuVO = new SkuVO();
				BeanUtils.copyProperties(orderItem.getSku(),skuVO);
				orderItemVO.setSku(skuVO);
				orderItemVOList.add(orderItemVO);
			}
			orderVO.setOrderItems(orderItemVOList);
			StoreVO storeVO = new StoreVO();
			BeanUtils.copyProperties(order.getStore(),storeVO);
			storeVO.setId(order.getStore().getId());
			orderVO.setStore(storeVO);
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

//		List<PaymentMethod> paymentMethods = new ArrayList<>();
//		if (cart.contains(Store.Type.GENERAL)) {
//			CollectionUtils.select(paymentMethodService.findAll(), new Predicate() {
//				@Override
//				public boolean evaluate(Object object) {
//					PaymentMethod paymentMethod = (PaymentMethod) object;
//					return paymentMethod != null && PaymentMethod.Method.ONLINE.equals(paymentMethod.getMethod());
//				}
//			}, paymentMethods);
//		} else {
//			paymentMethods = paymentMethodService.findAll();
//		}
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
	@Transactional
	public List<OrderVO> findPage(OrderVO.Type type, OrderVO.Status status, ScoreVO store, String ipandatcmUserId, ProductVO product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, int pageNumber,int pageSize) {
		
		//获取当前用户
		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
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
		
		LOGGER.info("orderList size "+orderList.size());
		
		List<OrderVO> list = new ArrayList<>();
		
		for(Order order : orderList){
			OrderVO o = new OrderVO();
			/*String name = order.getStore().getBusiness().getUsername();
			Business business = businessService.findByUsername(name);
			o.setDoctorId(business.getDoctorId());*/
			BeanUtils.copyProperties(order,o);
			o.setId(order.getId());
			o.setStatus(OrderVO.Status.valueOf(order.getStatus().toString()));
			List<OrderItemVO> orderItemVOList = new ArrayList<>();
			for(OrderItem orderItem : order.getOrderItems()){
				OrderItemVO orderItemVO = new OrderItemVO();
				BeanUtils.copyProperties(orderItem,orderItemVO);
				//获取库存
				SkuVO sku = new SkuVO();
				ProductVO productvo = new ProductVO();
				if(orderItem.getSku()!=null) {
					List<String> specification = orderItem.getSku().getSpecifications();
					if(specification!=null && specification.size()>0){
						String citiesCommaSeparated = String.join(";", specification);
						sku.setSpecifications(citiesCommaSeparated);
					}
					
					BeanUtils.copyProperties(orderItem.getSku(),sku);
					productvo.setId(orderItem.getSku().getProduct().getId());
					productvo.setIsmarketable(orderItem.getSku().getProduct().getIsMarketable());
					productvo.setIsOutOfStock(orderItem.getSku().getProduct().getIsOutOfStock());
					productvo.setIsactive(orderItem.getSku().getProduct().getIsActive());
					sku.setId(orderItem.getSku().getId());
					orderItemVO.setSku(sku);
					orderItemVO.getSku().setProduct(productvo);
				}

				orderItemVOList.add(orderItemVO);
			}
			o.setOrderItems(orderItemVOList);
			//医师推荐信息
			String key = RedisCacheKey.STORE_DOCTOR_RELEVANCE +
					RedisCacheKey.REDIS_SPLIT_CHAR + order.getStore().getId();

			String value = redisCacheService.get(key);
			if (value != null) {
				LOGGER.info("value :" + value);
				JSONObject jasonObject = JSONObject.fromObject(value);
				o.setDoctor((Map) jasonObject);
			} else {
				String doctorId = order.getStore().getBusiness().getDoctorId();
				if (doctorId != null) {
					Map<String, Object> map = medicalDoctorBusinessService.getDoctorInfoByDoctorId(doctorId);
					LOGGER.info("map tostring " + (map != null ? map.toString() : null));
					JSONObject jasonObject = JSONObject.fromObject(map);
					redisCacheService.set(key, jasonObject.toString());
					o.setDoctor(jasonObject);
				}
			}
			list.add(o);
		}
		
		return list;
	}

	@Override
	@Transactional
	public OrderVO findBySn(String sn) {
		Order order = orderService.findBySn(sn);
		OrderVO o = new OrderVO();
		if(order != null){
			/*String name = order.getStore().getBusiness().getUsername();
			Business business = businessService.findByUsername(name);
			o.setDoctorId(business.getDoctorId());*/
			BeanUtils.copyProperties(order,o);
			o.setId(order.getId());
			o.setStatus(OrderVO.Status.valueOf(order.getStatus().toString()));
			List<OrderItemVO> orderItemVOList = new ArrayList<>();
			for(OrderItem orderItem : order.getOrderItems()){
				OrderItemVO orderItemVO = new OrderItemVO();
				BeanUtils.copyProperties(orderItem,orderItemVO);
				orderItemVO.setId(orderItem.getId());
				//获取库存
				SkuVO sku = new SkuVO();
				ProductVO product = new ProductVO();
				if(orderItem.getSku() != null){
					List<String> specification = orderItem.getSku().getSpecifications();
					if(specification.size()>0){
						String citiesCommaSeparated = String.join(";", specification);
						sku.setSpecifications(citiesCommaSeparated);
					}
					BeanUtils.copyProperties(orderItem.getSku(),sku);
					product.setId(orderItem.getSku().getProduct().getId());
					product.setIsmarketable(orderItem.getSku().getProduct().getIsMarketable());
					product.setIsactive(orderItem.getSku().getProduct().getIsActive());
					product.setIsOutOfStock(orderItem.getSku().getProduct().getIsOutOfStock());
					sku.setId(orderItem.getSku().getId());
				}
				orderItemVO.setSku(sku);
				orderItemVO.getSku().setProduct(product);
				orderItemVOList.add(orderItemVO);
			}
			o.setOrderItems(orderItemVOList);
			//医师推荐信息
			String key = RedisCacheKey.STORE_DOCTOR_RELEVANCE +
					RedisCacheKey.REDIS_SPLIT_CHAR + order.getStore().getId();

			String value = redisCacheService.get(key);
			if (value != null) {
				LOGGER.info("value :" + value);
				JSONObject jasonObject = JSONObject.fromObject(value);
				o.setDoctor((Map) jasonObject);
			} else {
				String doctorId = order.getStore().getBusiness().getDoctorId();
				if (doctorId != null) {
					Map<String, Object> map = medicalDoctorBusinessService.getDoctorInfoByDoctorId(doctorId);
					LOGGER.info("map tostring " + (map != null ? map.toString() : null));
					JSONObject jasonObject = JSONObject.fromObject(map);
					redisCacheService.set(key, jasonObject.toString());
					o.setDoctor(jasonObject);
				}
			}
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
	@Transactional
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
	@Transactional
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
	public void setDefaultReceiver(Long receiverId, Boolean isDefault, String ipandatcmUserId) {
		Receiver receiver = receiverService.find(receiverId);
		if (receiver == null) {
			throw new RuntimeException("修改信息有误");
		}
		receiver.setIsDefault(isDefault);

		Member currentUser = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
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
	@Transactional
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
			throw new RuntimeException("订单被锁定");
			//throw new RuntimeException("member.order.locked");
		}
		orderService.cancel(order);
	}

	@Override
	@Transactional
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
			//throw new RuntimeException("member.order.locked");
			throw new RuntimeException("订单已被锁定，请稍后再试！");
		}
		orderService.receive(order);
	}

	@Transactional
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
	
	@Override
	public OrdersVO findBySnXc(String sn) {
		Order order = orderService.findBySn(sn);
		OrdersVO o = new OrdersVO();
		if(order != null){
			BeanUtils.copyProperties(order,o);
		}
		return o;
	}
	
	@Autowired
	private IMedicalDoctorAccountService medicalDoctorAccountService;
	
	@Autowired
	private BusinessDao businessDao;
	
	@Autowired
	private StoreDao storeDao;
	
	@Override
	@Transactional
	public Object findPageXc(OrderPageParams orderPageParams, Status status, ScoreVO store, 
			String ipandatcmUserId, ProductVO product,UsersType usersType,OrderType orderType) {
		
		List<Store> stores =null;Member member = null;
		try {
			if(UsersType.BUSINESS.equals(usersType)) {  //商家
				MedicalDoctorAccount medicalDoctorAccount = medicalDoctorAccountService.getByUserId(ipandatcmUserId);
				if(medicalDoctorAccount.getDoctorId()!=null) {
					String doctorId = medicalDoctorAccount.getDoctorId();
					//医师得到商家、商家得到店铺
					List<Business> businesss = businessDao.findBusinessByDoctorId(doctorId);
					stores = storeDao.findStoreByBusinesss(businesss);
				}
			}else{
				member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取商家信息有误");
		}

		if(stores==null && member == null) {
			return null;
		}
		
		Pageable pageable = new Pageable(orderPageParams.getPageNumber(), orderPageParams.getPageSize());
		Page<Order> orderList = orderDao.findPageXc(orderPageParams,
				Order.Type.GENERAL,
				(status !=null ? Order.Status.valueOf(status.toString()) : null),
				stores, member, null, pageable,orderType);
		
		//分页参数赋值
        net.shopxx.merge.page.Pageable pageableVo = new 
        		net.shopxx.merge.page.Pageable(orderPageParams.getPageNumber(), 
        				orderPageParams.getPageSize());

		List<OrdersVO> list = new ArrayList<>();
		for(Order order : orderList.getContent()){
			OrdersVO o = new OrdersVO();
			try {
				org.springframework.beans.BeanUtils.copyProperties(order,o);
				o.setStatus(order.getStatus().ordinal());
				List<OrderItem> orderItems = order.getOrderItems();
				List<OrderItemVO> orderVoItems = new ArrayList<OrderItemVO>();
				for (OrderItem orderItem : orderItems) {
					OrderItemVO orderItemVO = new OrderItemVO();
					org.springframework.beans.BeanUtils.copyProperties(orderItem,orderItemVO);
					orderVoItems.add(orderItemVO);
				}
				o.setOrderVoItems(orderVoItems);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(o);
		}
		return new net.shopxx.merge.page.Page<OrdersVO>(list, orderList.getTotal(), pageableVo);
	}


	
	@Override
	@Transactional
	public Object findOrderDeleterPageXc(OrderPageParams orderPageParams,Status status, ScoreVO store, 
			String ipandatcmUserId, ProductVO product,UsersType usersType,OrderType orderType) {
		
		List<Store> stores =null;Member member = null;
		try {
			if(UsersType.BUSINESS.equals(usersType)) {  //商家
				MedicalDoctorAccount medicalDoctorAccount = medicalDoctorAccountService.getByUserId(ipandatcmUserId);
				if(medicalDoctorAccount.getDoctorId()!=null) {
					String doctorId = medicalDoctorAccount.getDoctorId();
					//医师得到商家、商家得到店铺
					List<Business> businesss = businessDao.findBusinessByDoctorId(doctorId);
					stores = storeDao.findStoreByBusinesss(businesss);
				}
			}else{
				member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取商家信息有误");
		}

		if(stores==null && member == null) {
			return null;
		}
		
		Pageable pageable = new Pageable(orderPageParams.getPageNumber(), orderPageParams.getPageSize());
		
		Page<OrderDelete> orderList = orderDeleteDao.findPageXc(orderPageParams,
				OrderDelete.Type.GENERAL,
				(status !=null ? OrderDelete.Status.valueOf(status.toString()) : null),
				stores, member, null, pageable,orderType);
		
		
		List<OrdersVO> orderVoList =  new ArrayList<OrdersVO>();
		List<OrderDelete> content = orderList.getContent();
		for (OrderDelete orderDelete : content) {
			
			 OrdersVO ov = new OrdersVO();
			 ov.setPrice(orderDelete.getPrice());
			 ov.setConsignee(orderDelete.getConsignee());
			 ov.setPhone(orderDelete.getPhone());
			 ov.setCreatedDate(orderDelete.getCreateOrderDate());
			 ov.setSn(orderDelete.getSn());
			 ov.setStatus(9);
			
			 List<OrderItemVO> findByOrders = orderItemDeleteDao.findByOrders(orderDelete.getOrderId());
			 ov.setOrderVoItems(findByOrders);
			 orderVoList.add(ov);
		}
		
		//分页参数赋值
        net.shopxx.merge.page.Pageable pageableVo = new 
        		net.shopxx.merge.page.Pageable(orderPageParams.getPageNumber(), 
        				orderPageParams.getPageSize());
        
		return new net.shopxx.merge.page.Page<OrdersVO>(orderVoList, orderList.getTotal(), pageableVo);
	}
	
	
	
	@Override
	@Transactional
	public Map payment(String orderSnsStr) {
		String[] orderSns = orderSnsStr.split(",");
		Map map = new HashMap();
		List<PaymentPlugin> paymentPlugins = pluginService.getActivePaymentPlugins(WebUtils.getRequest());
		PaymentPlugin defaultPaymentPlugin = null;
		PaymentMethod orderPaymentMethod = null;
		BigDecimal fee = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		boolean online = false;
		List<Order> orders = new ArrayList<>();
		for (String orderSn : orderSns) {
			Order order = orderService.findBySn(orderSn);
			if (order == null) {
				throw new RuntimeException("单号错误");
			}
			BigDecimal amountPayable = order.getAmountPayable();
			if (order.getAmount().compareTo(order.getAmountPaid()) <= 0 || amountPayable.compareTo(BigDecimal.ZERO) <= 0) {
				throw new RuntimeException("金额有误");
			}
			orderPaymentMethod = order.getPaymentMethod();
			if (orderPaymentMethod == null) {
				throw new RuntimeException("支付出现问题");
			}
			if (PaymentMethod.Method.ONLINE.equals(orderPaymentMethod.getMethod())) {
				if (CollectionUtils.isNotEmpty(paymentPlugins)) {
					defaultPaymentPlugin = paymentPlugins.get(0);
				}
				online = true;
			} else {
				fee = fee.add(order.getFee());
				online = false;
			}
			amount = amount.add(amountPayable);
			orders.add(order);
		}
		if (online && defaultPaymentPlugin != null) {
			fee = defaultPaymentPlugin.calculateFee(amount).add(fee);
			amount = fee.add(amount);
		}
		map.put("fee", fee);
		map.put("amount", amount);
		map.put("orderSns", Arrays.asList(orderSns));
		return map;
	}

	@Override
	@Transactional
	public OrderShippingVO findOrderShippingByOrderId(Long orderId) {
		OrderShippingVO osvo = new OrderShippingVO();
		OrderShipping os = orderShippingService.findByOrderId(orderId);
		if(os != null){
			BeanUtils.copyProperties(os,osvo);
			osvo.setId(os.getId());
		}
		return osvo;
	}


	@Override
	@Transactional
	public void delete(Long orderId) {
		Order o = orderService.find(orderId);
		//备份order
		OrderDelete od = new OrderDelete();
		BeanUtils.copyProperties(o,od);
		String type = null;
		if(o.getType() != null){
			type = o.getType().toString();
		}
		od.setType(OrderDelete.Type.valueOf(type.trim()));
		String status = null;
		if(o.getStatus() != null){
			status = o.getStatus().toString();
		}
		od.setStatus(OrderDelete.Status.valueOf(status.trim()));
		od.setOrderId(o.getId());
		od.setDeleteDate(new Date());
		od.setCreateOrderDate(o.getCreatedDate());
		orderDeleteDao.merge(od);
		//备份orderitem
		for(int i=0;i<o.getOrderItems().size();i++){
			OrderItem oi =o.getOrderItems().get(i);
			OrderItemDelete oid = new OrderItemDelete();
			BeanUtils.copyProperties(oi,oid);
			oid.setOrderItemId(oi.getId());
			String orderItemType = null;
			if(oi.getType() != null){
				orderItemType = oi.getType().toString();
			}
			oid.setType(Product.Type.valueOf(orderItemType.trim()));
			List<String> specifications = oi.getSpecifications();
			List<String> newSpecifications = new ArrayList<>();
			for(int j=0;j<specifications.size();j++){
				newSpecifications.add(specifications.get(i));
			}
			oid.setSpecifications(newSpecifications);
			orderItemDeleteDao.merge(oid);
		}

		orderService.delete(orderId);
	}

	@Override
	@Transactional
	public Map<String, Object> isPaySuccess(String paymentTransactionSn) {
		Map<String, Object> data = new HashMap<>();
		PaymentTransaction paymentTransaction = paymentTransactionService.findBySn(paymentTransactionSn);
		data.put("isPaySuccess", paymentTransaction != null && BooleanUtils.isTrue(paymentTransaction.getIsSuccess()));
		return data;
	}
	
	
	
	
	
	
}