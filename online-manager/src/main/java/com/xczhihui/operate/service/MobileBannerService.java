package com.xczhihui.operate.service;

import java.util.Map;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.operate.vo.MobileBannerVo;

public interface MobileBannerService {

    Page<MobileBannerVo> findMobileBannerPage(
            MobileBannerVo mobileBannerVo, Integer pageNumber, Integer pageSize);

    /**
     * 新增
     *
     * @return void
     */
    void addMobileBanner(MobileBannerVo mobileBannerVo);

    /**
     * 修改banner
     *
     * @return void
     */
    void updateMobileBanner(MobileBannerVo mobileBannerVo);

    /**
     * 修改状态
     *
     * @return void
     */
    boolean updateStatus(MobileBannerVo mobileBannerVo);

    /**
     * 逻辑批量删除
     *
     * @return void
     */
    void deletes(String[] ids);

    /**
     * 向上调整顺序
     *
     * @return void
     */
    void updateSortUp(String id);

    /**
     * 向上调整顺序
     *
     * @return void
     */
    void updateSortDown(String id);

    /**
     * 处理老的banner数据
     */
    void updateOldData();

    /**
     * 获取链接描述
     *
     * @param routeType
     * @param linkParam
     * @return
     */
    Map<String, String> getLinkData(String routeType, String linkParam);
}
