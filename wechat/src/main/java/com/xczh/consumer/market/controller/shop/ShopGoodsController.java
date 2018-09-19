package com.xczh.consumer.market.controller.shop;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.utils.ResponseObject;

import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.vo.GoodsPageParams;

/**
 * 商城接口
 */
@RestController
@RequestMapping("/xczh/shop/goods")
public class ShopGoodsController {
	
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShopGoodsController.class);
    
    @Autowired
    public GoodsService goodsService;

    @RequestMapping("list")
    public ResponseObject checkSku(GoodsPageParams goodsPageParams,GoodsPageParams.OrderType orderType) {
        return ResponseObject.newSuccessResponseObject(goodsService.list(goodsPageParams, orderType));
    }

    @RequestMapping("details")
    public ResponseObject checkout(Long productId) {
        return ResponseObject.newSuccessResponseObject(goodsService.findProductById(productId));
    }
}
