package com.xczhihui.bxg.online.manager.operate.service;

import com.xczhihui.bxg.online.manager.operate.vo.MobilePartnerCourseVo;

/**
 * 移动合伙人课程业务接口
 * @Author Fudong.Sun【】
 * @Date 2017/3/9 20:55
 */
public interface MobilePartnerCourseService {
    /**
     * 通过课程id查找合伙人课程信息
     * @return
     */
    public MobilePartnerCourseVo findCourseInfoByCourseId();

    /**
     * 保存和添加合伙人课程信息
     * @param mobilePartnerCourseVo
     */
    public void saveOrUpdate(MobilePartnerCourseVo mobilePartnerCourseVo);
}
