package com.xczhihui.mobile.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Advertisement;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.mobile.dao.MobileAdvertisementDao;
import com.xczhihui.mobile.service.MobileAdvertisementService;
import com.xczhihui.mobile.vo.MobileAdvertisementVo;
import com.xczhihui.operate.service.MobileBannerService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 题库service实现类
 *
 * @author snow
 */
@Service("mobileAdvertisementService")
public class MobileAdvertisementServiceImpl extends OnlineBaseServiceImpl implements
        MobileAdvertisementService {
    @Autowired
    private MobileAdvertisementDao mobileAdvertisementDao;
    @Autowired
    private MobileBannerService mobileBannerService;

    @Override
    public Page<MobileAdvertisementVo> findMobileAdvertisementPage(
            MobileAdvertisementVo mobileAdvertisementVo, int pageNumber, int pageSize) {
        Page<MobileAdvertisementVo> page = mobileAdvertisementDao.findMobileAdvertisementPage(
                mobileAdvertisementVo, pageNumber, pageSize);
        page.getItems().forEach(mobileAdvertisement -> {
            Map<String, String> linkData = mobileBannerService.getLinkData(mobileAdvertisement.getRouteType(), mobileAdvertisement.getLinkParam());
            mobileAdvertisement.setLinkDesc(linkData.get("linkDesc"));
            mobileAdvertisement.setMenuId(linkData.get("menuId"));
        });
        return page;
    }

    @Override
    public void save(MobileAdvertisementVo mobileAdvertisementVo) {
        Advertisement advertisement = new Advertisement();
        advertisement.setName(mobileAdvertisementVo.getName());
        advertisement.setImgPath(mobileAdvertisementVo.getImgPath());
        advertisement.setCreatePerson(mobileAdvertisementVo.getCreatePerson());
        advertisement.setCreateTime(new Date());
        advertisement.setStatus(0);
        advertisement.setClickSum(0);
        advertisement.setDelete(false);
        advertisement.setUrl(mobileAdvertisementVo.getUrl());
        advertisement.setRouteType(mobileAdvertisementVo.getRouteType());
        advertisement.setLinkParam(mobileAdvertisementVo.getLinkParam());
        mobileAdvertisementDao.save(advertisement);
    }

    @Override
    public Advertisement findAdvertisementByName(String name) {
        DetachedCriteria dc = DetachedCriteria.forClass(Advertisement.class);
        dc.add(Restrictions.eq("name", name));
        return mobileAdvertisementDao.findEntity(dc);
    }

    @Override
    public void update(Advertisement advertisement) {
        mobileAdvertisementDao.update(advertisement);
    }

    @Override
    public Advertisement findById(String parseInt) {
        return mobileAdvertisementDao.findById(parseInt);
    }

    @Override
    public void updateStatus(String id) {
        Advertisement scoreType = mobileAdvertisementDao.findById(id);
        if (scoreType.getStatus() != null && scoreType.getStatus() == 1) {  //禁用
            scoreType.setStatus(0);
        } else {                      //启用
            scoreType.setStatus(1);
        }
        mobileAdvertisementDao.update(scoreType);
    }


}
