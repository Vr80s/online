package com.xczh.consumer.market.controller.shop;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import net.shopxx.merge.service.OrderOperService;
import net.shopxx.merge.vo.OrderVO;
import net.shopxx.merge.vo.ReceiverVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
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
    public ResponseObject checkout(@Account String accountId, Long skuId, Integer quantity,String cartItemIds) {
        Map<String, Object> map = orderOperService.checkout(skuId, quantity, cartItemIds, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * @param cartItemIds 购物车子项目id
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
     * @param accountId
     * @return
     */
    @RequestMapping("/order/create")
    public ResponseObject createOrder(@Account String accountId,String cartItemIds,Long skuId, Integer quantity, String cartTag, Long receiverId, Long shippingMethodId,
                                         String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo ){
        Map<String, Object> map = orderOperService.create(cartItemIds,skuId, quantity, cartTag, receiverId, shippingMethodId, code, invoiceTitle, invoiceTaxNumber, balance, memo, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    @RequestMapping("/order/calculate")
    public ResponseObject calculateOrder(@Account String accountId,String cartItemIds, Long skuId, Integer quantity, Long receiverId, Long shippingMethodId, String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo ){
        Map<String, Object> map = orderOperService.calculate(cartItemIds,skuId, quantity, receiverId, shippingMethodId, code, invoiceTitle, invoiceTaxNumber, balance, memo, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    @RequestMapping("/order/getByOrderSns")
    public ResponseObject getByOrderSns(String orderSns ){
        List<OrderVO> orderVOS = orderOperService.findBySns(orderSns);
        return ResponseObject.newSuccessResponseObject(orderVOS);
    }

    @RequestMapping(value = "/receiver/add")
    public ResponseObject addReceiver(@Account String accountId, @RequestParam String consignee, @RequestParam String address
                                    , @RequestParam String zipCode, @RequestParam String phone
                                    , @RequestParam Boolean isDefault, @RequestParam Long areaId){
        orderOperService.addReceiver(consignee,address,zipCode,phone,isDefault,areaId,accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("/receiver/list")
    public ResponseObject receiverlist(@Account String accountId){
        List<ReceiverVO> receiverlist = orderOperService.receiverlist(accountId);
        return ResponseObject.newSuccessResponseObject(receiverlist);
    }

    @RequestMapping(value = "/receiver",method = RequestMethod.GET)
    public ResponseObject receiver(@Account String accountId,@RequestParam Long receiverId){
        ReceiverVO receiver = orderOperService.findReceiverById(receiverId, accountId);
        return ResponseObject.newSuccessResponseObject(receiver);
    }

    @RequestMapping("/receiver/update")
    public ResponseObject updateReceiver(@Account String accountId, @RequestParam String consignee, @RequestParam String address
            , @RequestParam String zipCode, @RequestParam String phone
            , @RequestParam Boolean isDefault, @RequestParam Long areaId, @RequestParam Long receiverId){
        orderOperService.updateReceiver(receiverId,consignee,address,zipCode,phone,isDefault,areaId,accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("/receiver/delete")
    public ResponseObject deleteReceiver(@Account String accountId, @RequestParam Long receiverId){
        orderOperService.deleteReceiver(receiverId,accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("/area")
    public ResponseObject findArea(Long parentId){
        List<Map<String, Object>> area = orderOperService.findArea(parentId);
        return ResponseObject.newSuccessResponseObject(area);
    }

}
