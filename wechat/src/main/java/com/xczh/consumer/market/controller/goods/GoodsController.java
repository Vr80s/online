package com.xczh.consumer.market.controller.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.utils.ResponseObject;

import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.vo.GoodsPageParams;

/**
 * Description：医师页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/26 0026 上午 11:59
 **/
@RestController
@RequestMapping(value = "/xczh/goods")
public class GoodsController {

   
	 private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);
	
	
	@Autowired
	private GoodsService goodsService;

	
    /**
     * banner图
     *
     * @return
     */
    @RequestMapping("list")
    public ResponseObject list(GoodsPageParams goodsPageParams,GoodsPageParams.OrderType orderType) {
        
    	Object object =  goodsService.list(goodsPageParams, orderType);

    	LOGGER.info(object.toString());
    	
    	//还需要在这里添加逻辑了，这里添加逻辑的话
    	
    	
        return ResponseObject.newSuccessResponseObject(object);
    }
    
   
}
