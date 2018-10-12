package com.xczh.consumer.market.controller.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.medical.banner.model.OeBanner;
import com.xczhihui.medical.banner.service.PcBannerService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;

import net.shopxx.merge.enums.OrderType;
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
    
	@Autowired
	private IMedicalDoctorPostsService medicalDoctorPostsService;
	
    @Autowired
    private IMobileHotSearchService mobileHotSearchService;

    @RequestMapping("list")
    public ResponseObject list(GoodsPageParams goodsPageParams, OrderType orderType) {
        return ResponseObject.newSuccessResponseObject(goodsService.list(goodsPageParams, orderType));
    }

    @RequestMapping("details")
    public ResponseObject details(Long productId, @Account(optional = true) Optional<String> accountIdOpt) {
        goodsService.updateClick(accountIdOpt.orElse(null), productId);
        return ResponseObject.newSuccessResponseObject(goodsService.findProductById(productId));
    }

    @RequestMapping("banner")
    public ResponseObject banner(HttpServletRequest request) {

        int clientType = HeaderInterceptor.getClientType().getCode();
        Page<OeBanner> page = bannerService.page(new Page<>(1, 3), 8, clientType);
        if (HeaderInterceptor.ONLY_THREAD.get()) {
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

    
    /**
     * 热门搜索列表
     */
    @RequestMapping("hotSearch")
    @ResponseBody
    public ResponseObject hotSearchList()
            throws Exception {
    	
        Map<String, Object> mapAll = new HashMap<String, Object>();
        //默认搜索框
        mapAll.put("defaultSearch", mobileHotSearchService.HotSearchList(SearchType.PRODUCT_DEFAULT_SEARCH.getCode()));
        //热门搜索
        mapAll.put("hotSearch", mobileHotSearchService.HotSearchList(SearchType.PRODUCT_HOT_SEARCH.getCode()));
        return ResponseObject.newSuccessResponseObject(mapAll);
    }
    
    
    @RequestMapping("review")
    public ResponseObject review(Long productId,@RequestParam(required = false, value = "pageNumber")Integer pageNumber,
    		@RequestParam(required = false, value = "pageSize")Integer pageSize) {
    	
        return ResponseObject.newSuccessResponseObject(shopReviewService.list(productId,pageNumber,pageSize));
    }
    

    @RequestMapping("recommends")
    public ResponseObject recommends(Long productId,
    		@RequestParam(required = false, value = "pageNumber")Integer pageNumber,
    		@RequestParam(required = false, value = "pageSize")Integer pageSize) {
    	
    	pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
    	
        return ResponseObject.newSuccessResponseObject(medicalDoctorPostsService.
        		getProductPostsByProductIdAndDoctorId(productId,null,pageNumber, pageSize));
    }
    
    @RequestMapping("addReview")
    public ResponseObject addReview(Long orderId,@RequestParam("postdata") String postdata,
    		@Account String accountId,HttpServletRequest request) throws Exception {
    	
		shopReviewService.addReview(orderId,postdata,accountId,request.getRemoteAddr());
		
        return ResponseObject.newSuccessResponseObject(null);
    }
}
