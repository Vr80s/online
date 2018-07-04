package com.xczhihui.medical.service;

import java.util.List;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.vo.DoctorPostsCommentVO;

/**
 * @author hejiwei
 */
public interface DoctorPostsCommentService {

    /**
     * 评价列表
     *
     * @param keyword 搜索关键字
     * @param page    分页参数
     * @param pageSize    分页大小
     * @return
     */
    Page<DoctorPostsCommentVO> list(String keyword, int page, int pageSize);

    /**
     * 批量删除
     *
     * @param ids ids
     */
    void deleteByIds(List<Integer> ids);
}
