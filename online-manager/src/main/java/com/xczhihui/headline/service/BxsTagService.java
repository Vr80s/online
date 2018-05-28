package com.xczhihui.headline.service;

/**
 * Created by admin on 2016/8/29.
 */

import java.util.List;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.headline.vo.TagVo;

/**
 * 标签业务层接口类
 *
 * @author 王高伟
 * @create 2016-10-16 16:25:52
 */
public interface BxsTagService {

    /**
     * @return
     */
    List<TagVo> findTagVo(TagVo tagVo);

    Page<TagVo> findPage(TagVo searchVo, int currentPage, int pageSize);

    String addTag(TagVo tagVo);

    String deletes(String[] _ids);

    void updateStatus(String id);

    void updateTag(TagVo tagVo);
}
