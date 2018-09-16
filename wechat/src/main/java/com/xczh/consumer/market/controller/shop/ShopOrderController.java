package com.xczh.consumer.market.controller.shop;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import net.shopxx.merge.service.OrderOperService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 商城接口
 */
@RestController
@RequestMapping("/xczh/shop")
public class ShopOrderController {
	
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShopOrderController.class);
    
    @Autowired
    public OrderOperService orderOperService;

    @RequestMapping("checkSku")
    public ResponseObject checkSku(Long skuId, Integer quantity) {
        orderOperService.checkSku(skuId,quantity);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("checkout")
    public ResponseObject checkout(@Account String accountId, Long skuId, Integer quantity) {
        Map<String, Object> map = orderOperService.checkout(skuId, quantity, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * @param skuId 库存id
     * @param quantity 数量
     * @param cartTag 购物车标签
     * @param receiverId 收货地址
     * @param paymentMethodId 支付方式
     * @param shippingMethodId 配送方式
     * @param code 优惠码
     * @param invoiceTitle 发票抬头
     * @param invoiceTaxNumber 纳税人识别码
     * @param balance 金额
     * @param memo 备忘
     * @param accountId
     * @return
     */
    @RequestMapping("/order/create")
    public ResponseObject createOrder(@Account String accountId,Long skuId, Integer quantity, String cartTag, Long receiverId, Long paymentMethodId, Long shippingMethodId,
                                         String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo){
        Map<String, Object> map = orderOperService.create(skuId, quantity, cartTag, receiverId, paymentMethodId, shippingMethodId, code, invoiceTitle, invoiceTaxNumber, balance, memo, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }



}
