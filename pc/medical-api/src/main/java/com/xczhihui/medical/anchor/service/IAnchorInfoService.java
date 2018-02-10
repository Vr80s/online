package com.xczhihui.medical.anchor.service;

import com.xczhihui.medical.anchor.vo.CourseAnchorVO;

/**
 * 主播工作台资产业务接口
 * @author zhuwenbao
 */
public interface IAnchorInfoService {

    /**
     * 获取主播信息
     */
    CourseAnchorVO detail(String userId);

    /**
     * 更新主播信息
     */
    void update(CourseAnchorVO target);

}
