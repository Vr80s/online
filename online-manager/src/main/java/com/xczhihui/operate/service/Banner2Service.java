package com.xczhihui.operate.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.operate.vo.Banner2Vo;

public interface Banner2Service {

    Page<Banner2Vo> findBanner2Page(Banner2Vo banner2Vo,
                                    Integer pageNumber, Integer pageSize);

    /**
     * 新增BANNER
     *
     * @return void
     */
    void addBanner(Banner2Vo banner2Vo);

    /**
     * 修改banner
     *
     * @return void
     */
    void updateBanner(Banner2Vo banner2Vo);

    /**
     * 修改状态
     *
     * @return void
     */
    boolean updateStatus(Banner2Vo banner2Vo);

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
    void updateSortUp(Integer id);

    /**
     * 向上调整顺序
     *
     * @return void
     */
    void updateSortDown(Integer id);

    /**
     * 处理老的banner数据
     */
    void updateOldData();
}
