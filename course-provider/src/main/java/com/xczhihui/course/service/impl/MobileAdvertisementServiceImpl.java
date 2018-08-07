package com.xczhihui.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.mapper.MobileAdvertisementMapper;
import com.xczhihui.course.model.MobileAdvertisement;
import com.xczhihui.course.service.IMobileAdvertisementService;
import com.xczhihui.course.service.IMobileBannerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MobileAdvertisementServiceImpl extends ServiceImpl<MobileAdvertisementMapper, MobileAdvertisement> implements IMobileAdvertisementService {

    @Autowired
    private MobileAdvertisementMapper mobileAdvertisementMapper;
    @Autowired
    private IMobileBannerService mobileBannerService;
    @Value("${mobile.domain}")
    private String returnOpenidUri;


    @Override
    public MobileAdvertisement selectMobileAdvertisement(String source) {
        MobileAdvertisement mobileAdvertisement = mobileAdvertisementMapper.selectMobileAdvertisement();
        if(mobileAdvertisement != null && StringUtils.isNotBlank(mobileAdvertisement.getRouteType())){
            String url = MultiUrlHelper.getUrl(mobileBannerService.getHandleRouteType(mobileAdvertisement.getRouteType(), mobileAdvertisement.getLinkParam()),
                    source, MultiUrlHelper.handleParam(returnOpenidUri, mobileAdvertisement.getLinkParam(), mobileAdvertisement.getRouteType()));
            mobileAdvertisement.setTarget(url);
        }
        return mobileAdvertisement;
    }

    @Override
    public void addClickNum(Integer id) {
        mobileAdvertisementMapper.addClickNum(id);
    }



}
