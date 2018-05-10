package com.xczhihui.medical.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.domain.Announcement;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.dao.AnnouncementDao;
import com.xczhihui.medical.service.AnnouncementService;

/**
 * @author hejiwei
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public Page<Announcement> list(int page, int size, String hospitalId) {
        return announcementDao.list(page, size, hospitalId);
    }
}
