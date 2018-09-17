package com.xczh.consumer.market.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;

import net.shopxx.merge.service.ShopCartService;

/**
 * @author hejiwei
 */
@RestController
@RequestMapping("xczh/shop/cart")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject add(@Account String accountId, @RequestParam long skuId, @RequestParam int count) {
        shopCartService.add(accountId, skuId, count);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject get(@Account String accountId) {
        return ResponseObject.newSuccessResponseObject(shopCartService.getCart(accountId));
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseObject modify(@Account String accountId, @RequestParam Long skuId, @RequestParam int quantity) {
        shopCartService.modify(accountId, skuId, quantity);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "sku/delete", method = RequestMethod.POST)
    public ResponseObject remove(@Account String accountId, @RequestParam Long skuId, @RequestParam int quantity) {
        shopCartService.remove(accountId, skuId);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "clear", method = RequestMethod.POST)
    public ResponseObject clear(@Account String accountId) {
        shopCartService.clear(accountId);
        return ResponseObject.newSuccessResponseObject();
    }
}
