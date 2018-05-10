package com.xczhihui.medical.service;

import com.xczhihui.bxg.online.common.domain.Announcement;
import com.xczhihui.common.util.bean.Page;

/**
 * @author hejiwei
 */
public interface AnnouncementService {

    /**
     * 公告列表
     *
     * @param page       page
     * @param size       size
     * @param hospitalId 医馆id
     * @return
     */
    Page<Announcement> list(int page, int size, String hospitalId);
}
