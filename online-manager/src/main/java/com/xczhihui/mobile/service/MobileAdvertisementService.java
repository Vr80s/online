package com.xczhihui.mobile.service;

import com.xczhihui.bxg.online.common.domain.Advertisement;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.mobile.vo.MobileAdvertisementVo;

public interface MobileAdvertisementService {

    Page<MobileAdvertisementVo> findMobileAdvertisementPage(
            MobileAdvertisementVo mobileAdvertisementVo, int currentPage, int pageSize);

    void save(MobileAdvertisementVo mobileAdvertisementVo);

    Advertisement findAdvertisementByName(String name);

    void update(Advertisement advertisement);

    Advertisement findById(String parseInt);

    void updateStatus(String id);
}
