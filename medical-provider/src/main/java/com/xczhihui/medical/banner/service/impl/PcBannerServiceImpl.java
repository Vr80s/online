package com.xczhihui.medical.banner.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.banner.mapper.BannerMapper;
import com.xczhihui.medical.banner.model.OeBanner;
import com.xczhihui.medical.banner.service.PcBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yangxuan
 *
 */
@Service
public class PcBannerServiceImpl implements PcBannerService {

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 获取科室列表（分页）
     *
     * @param page 翻页对象
     * @return 每页的科室内容
     */
    @Override
    public Page<OeBanner> page(Page page,Integer type, Integer clientType) {

        
        return page.setRecords(bannerMapper.page(page,type,clientType));
    }
}
