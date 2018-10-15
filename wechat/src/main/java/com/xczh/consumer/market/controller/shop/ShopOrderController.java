package com.xczh.consumer.market.controller.shop;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;

import net.shopxx.merge.service.OrderOperService;
import net.shopxx.merge.service.ShopCartService;
import net.shopxx.merge.vo.OrderVO;
import net.shopxx.merge.vo.ReceiverVO;

/**
 * 商城接口
 */
@RestController
@RequestMapping("/xczh/shop")
public class ShopOrderController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShopOrderController.class);

    @Autowired
    public OrderOperService orderOperService;
    @Autowired
    public IMedicalDoctorBusinessService iMedicalDoctorBusinessService;
    @Autowired
    private ShopCartService shopCartService;
    @Value("${shop.url}")
    private String shopUrl;

    @RequestMapping("checkSku")
    public ResponseObject checkSku(Long skuId, Integer quantity) {
        orderOperService.checkSku(skuId, quantity);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("checkSkus")
    public ResponseObject checkSkus(@RequestParam List<Long> cartItemIds) {
        if (!shopCartService.checkInventory(cartItemIds)) {
            return ResponseObject.newSuccessResponseObject("商品库存不足");
        }
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("checkout")
    public ResponseObject checkout(@Account String accountId, Long skuId,
                                   Integer quantity, String cartItemIds, Long shippingMethodId) {

        LOGGER.info("accountId:" + accountId + ",skuId:" + skuId + ",quantity:" + quantity + ",cartItemIds:" + cartItemIds);

        Map<String, Object> map = orderOperService.checkout(skuId, quantity, cartItemIds, accountId, shippingMethodId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * @param cartItemIds      购物车子项目id
     * @param skuId            库存id
     * @param quantity         数量
     * @param cartTag          购物车标签
     * @param receiverId       收货地址
     * @param shippingMethodId 配送方式
     * @param code             优惠码
     * @param invoiceTitle     发票抬头
     * @param invoiceTaxNumber 纳税人识别码
     * @param balance          金额
     * @param memo             备忘
     * @param accountId
     * @return
     */
    @RequestMapping("/order/create")
    public ResponseObject createOrder(@Account String accountId, String cartItemIds, Long skuId, Integer quantity, String cartTag, Long receiverId, Long shippingMethodId,
                                      String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo, String memoJson) {

        Map<String, Object> map = orderOperService.create(cartItemIds, skuId, quantity, cartTag, receiverId, shippingMethodId, code, invoiceTitle, invoiceTaxNumber, balance, memoJson, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    @RequestMapping("/order/calculate")
    public ResponseObject calculateOrder(@Account String accountId, String cartItemIds, Long skuId, Integer quantity, Long receiverId, Long shippingMethodId, String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo) {
        Map<String, Object> map = orderOperService.calculate(cartItemIds, skuId, quantity, receiverId, shippingMethodId, code, invoiceTitle, invoiceTaxNumber, balance, memo, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    @RequestMapping("/order/getByOrderSns")
    public ResponseObject getByOrderSns(String orderSns) {
        List<OrderVO> orderVOS = orderOperService.findBySns(orderSns);
        return ResponseObject.newSuccessResponseObject(orderVOS);
    }

    @RequestMapping(value = "/receiver/add")
    public ResponseObject addReceiver(@Account String accountId, @RequestParam String consignee, @RequestParam String address
            , @RequestParam String zipCode, @RequestParam String phone
            , @RequestParam Boolean isDefault, @RequestParam Long areaId) {
        orderOperService.addReceiver(consignee, address, zipCode, phone, isDefault, areaId, accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("/receiver/list")
    public ResponseObject receiverlist(@Account String accountId) {
        List<ReceiverVO> receiverlist = orderOperService.receiverlist(accountId);
        return ResponseObject.newSuccessResponseObject(receiverlist);
    }

    @RequestMapping(value = "/receiver", method = RequestMethod.GET)
    public ResponseObject receiver(@Account String accountId, @RequestParam Long receiverId) {
        ReceiverVO receiver = orderOperService.findReceiverById(receiverId, accountId);
        return ResponseObject.newSuccessResponseObject(receiver);
    }

    @RequestMapping("/receiver/update")
    public ResponseObject updateReceiver(@Account String accountId, @RequestParam String consignee, @RequestParam String address
            , @RequestParam String zipCode, @RequestParam String phone
            , @RequestParam Boolean isDefault, @RequestParam Long areaId, @RequestParam Long receiverId) {
        orderOperService.updateReceiver(receiverId, consignee, address, zipCode, phone, isDefault, areaId, accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("/receiver/setDefaultReceiver")
    public ResponseObject setDefaultReceiver(@Account String accountId, @RequestParam Boolean isDefault, @RequestParam Long receiverId) {
        orderOperService.setDefaultReceiver(receiverId, isDefault, accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("/receiver/delete")
    public ResponseObject deleteReceiver(@Account String accountId, @RequestParam Long receiverId) {
        orderOperService.deleteReceiver(receiverId, accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping("/area")
    public ResponseObject findArea(Long parentId) {
        List<Map<String, Object>> area = orderOperService.findArea(parentId);
        return ResponseObject.newSuccessResponseObject(area);
    }

    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public ResponseObject list(@Account String accountId, @RequestParam(required = false) OrderVO.Type type, @RequestParam(required = false) OrderVO.Status status, @RequestParam(required = false) Boolean isPendingReceive
            , @RequestParam(required = false) Boolean isPendingRefunds, @RequestParam(required = false) Boolean isUseCouponCode, @RequestParam(required = false) Boolean isExchangePoint
            , @RequestParam(required = false) Boolean isAllocatedStock, @RequestParam(required = false) Boolean hasExpired
            , @RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) throws SQLException {
        List<OrderVO> list = orderOperService.findPage(type, status, null, accountId, null, isPendingReceive,
                isPendingRefunds, isUseCouponCode, isExchangePoint, isAllocatedStock, hasExpired, pageNumber, pageSize);
        /*for(int i=0;i<list.size();i++){
            String doctorId = list.get(i).getDoctorId();
            if(doctorId != null){
                MedicalDoctorVO medicalDoctor = iMedicalDoctorBusinessService.findSimpleById(doctorId);
                list.get(i).setDoctorName(medicalDoctor.getName());
                list.get(i).setDoctorHeadPortrait(medicalDoctor.getHeadPortrait());
            }

        }*/
        return ResponseObject.newSuccessResponseObject(list);
    }

    @RequestMapping(value = "/order/detail", method = RequestMethod.GET)
    public ResponseObject detail(@RequestParam String sn) {
        OrderVO order = orderOperService.findBySn(sn);
        /*String doctorId = order.getDoctorId();
        if(doctorId != null){
            MedicalDoctorVO medicalDoctor = iMedicalDoctorBusinessService.findSimpleById(doctorId);
            order.setDoctorName(medicalDoctor.getName());
            order.setDoctorHeadPortrait(medicalDoctor.getHeadPortrait());
        }*/
        return ResponseObject.newSuccessResponseObject(order);
    }

    @RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
    public ResponseObject cancel(@Account String accountId, @RequestParam String sn) {
        orderOperService.cancel(sn, accountId);
        return ResponseObject.newSuccessResponseObject("取消成功");
    }


    @RequestMapping(value = "/order/receive", method = RequestMethod.POST)
    public ResponseObject receive(@Account String accountId, @RequestParam String sn) {
        orderOperService.receive(sn, accountId);
        return ResponseObject.newSuccessResponseObject("确认收货");
    }

    @RequestMapping(value = "/order/payment")
    public ResponseObject payment(@Account String accountId, @RequestParam String orderSnsStr) {
        Map payment = orderOperService.payment(orderSnsStr);
        payment.put("ipandatcmUserId", accountId);
        payment.put("shopUrl", shopUrl);
        return ResponseObject.newSuccessResponseObject(payment);
    }

    @RequestMapping(value = "/order/transitStep", method = RequestMethod.GET)
    public ResponseObject transitStep(@RequestParam Long shippingId) {
        return ResponseObject.newSuccessResponseObject(orderOperService.getTransitSteps(shippingId));
    }

    @RequestMapping(value = "/order/shipping", method = RequestMethod.GET)
    public ResponseObject shipping(@RequestParam Long orderId) {
        return ResponseObject.newSuccessResponseObject(orderOperService.findOrderShippingByOrderId(orderId));
    }

    @RequestMapping(value = "/order/delete", method = RequestMethod.POST)
    public ResponseObject delete(@RequestParam Long orderId) {
        orderOperService.delete(orderId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    @RequestMapping(value = "/isPaySuccess/{paymentTransactionSn}")
    public ResponseObject isPaySuccess(@PathVariable String paymentTransactionSn) {
        return ResponseObject.newSuccessResponseObject(orderOperService.isPaySuccess(paymentTransactionSn));
    }

}
