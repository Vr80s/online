package com.xczhihui.course.service;

import java.util.List;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.CriticizeVo;

/**
 * @author hejiwei
 */
public interface CriticizeService {

    /**
     * 评论列表
     *
     * @param keyword    关键字
     * @param pageNumber 页码
     * @param pageSize   页数
     * @return
     */
    Page<CriticizeVo> list(String keyword,
                           Integer pageNumber, Integer pageSize);

    /**
     * 批量删除评论
     *
     * @param ids id列表
     */
    void deletes(List<String> ids);
}
