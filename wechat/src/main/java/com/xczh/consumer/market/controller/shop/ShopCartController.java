package com.xczh.consumer.market.controller.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;

import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.service.ShopCartService;

/**
 * @author hejiwei
 */
@RestController
@RequestMapping("xczh/shop/cart")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject add(@Account String accountId, @RequestParam long skuId, @RequestParam int quantity) {
        shopCartService.add(accountId, skuId, quantity);
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

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseObject update(@Account String accountId, @RequestParam Long oldSkuId, @RequestParam Long updatedSkuId, @RequestParam int quantity) {
        if (!oldSkuId.equals(updatedSkuId)) {
            List<Long> oldSkuIds = new ArrayList<>();
            oldSkuIds.add(oldSkuId);
            shopCartService.remove(accountId, oldSkuIds);
        }
        return ResponseObject.newSuccessResponseObject(shopCartService.modify(accountId, updatedSkuId, quantity));
    }

    @RequestMapping(value = "sku/delete", method = RequestMethod.POST)
    public ResponseObject remove(@Account String accountId, @RequestParam List<Long> skuIds) {
        shopCartService.remove(accountId, skuIds);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "clear", method = RequestMethod.POST)
    public ResponseObject clear(@Account String accountId) {
        shopCartService.clear(accountId);
        return ResponseObject.newSuccessResponseObject();
    }
    
    @RequestMapping(value = "quantity",method = RequestMethod.GET)
    public ResponseObject getCartQuantity(@Account String accountId) {
        return ResponseObject.newSuccessResponseObject(shopCartService.getCartQuantity(accountId));
    }

    @RequestMapping(value = "product", method = RequestMethod.GET)
    public ResponseObject getProduct(@RequestParam Long id) {
        return ResponseObject.newSuccessResponseObject(goodsService.getProductById(id));
    }
}
