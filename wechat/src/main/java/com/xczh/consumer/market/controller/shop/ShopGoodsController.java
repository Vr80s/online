package com.xczh.consumer.market.controller.shop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.medical.banner.model.OeBanner;
import com.xczhihui.medical.banner.service.PcBannerService;

import net.shopxx.merge.service.GoodsService;
import net.shopxx.merge.service.ShopReviewService;
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
    
    @Autowired
    public IMobileBannerService mobileBannerService;
    
    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    
    @Autowired
    private PcBannerService bannerService;
    
    @Autowired
    private ShopReviewService shopReviewService;

    @RequestMapping("list")
    public ResponseObject list(GoodsPageParams goodsPageParams,GoodsPageParams.OrderType orderType) {
        return ResponseObject.newSuccessResponseObject(goodsService.list(goodsPageParams, orderType));
    }

    @RequestMapping("details")
    public ResponseObject details(Long productId) {
        return ResponseObject.newSuccessResponseObject(goodsService.findProductById(productId));
    }
    
    @RequestMapping("banner")
    public ResponseObject banner(HttpServletRequest request) {

        int clientType = HeaderInterceptor.getClientType().getCode();
        Page<OeBanner> page =  bannerService.page(new Page<>(1, 3),8,clientType);
        if(HeaderInterceptor.ONLY_THREAD.get()) {
            return ResponseObject.newSuccessResponseObject(null);
        }
        page.getRecords().forEach(bannerVo -> {
            String routeType = bannerVo.getRouteType();
            String linkParam = bannerVo.getLinkParam();
            if (StringUtils.isNotBlank(routeType)) {
                String url = MultiUrlHelper.getUrl(mobileBannerService.getHandleRouteType(routeType, linkParam),
                        APPUtil.getMobileSource(request), MultiUrlHelper.handleParam(returnOpenidUri, linkParam, routeType));
                bannerVo.setTarget(url);
            } else {
                bannerVo.setTarget("");
            }
        });
    	
        return ResponseObject.newSuccessResponseObject(page.getRecords());
    }
    
    @RequestMapping("review")
    public ResponseObject review(Long productId,Integer pageNumber,Integer pageSize) {
    	
        return ResponseObject.newSuccessResponseObject(shopReviewService.list(productId,pageNumber,pageSize));
    }
    
    
}
