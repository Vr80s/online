package com.xczhihui.bxg.online.web.service;/**
 * Created by admin on 2016/9/19.
 */

import com.xczhihui.bxg.online.web.vo.AskTagVo;

import java.util.List;

/**
 * 标签业务层接口类
 *
 * @author 康荣彩
 * @create 2016-09-19 16:16
 */
public interface AskTagService {


    /**
     * 根据学科ID号查找对应的标签
     * @param menuId
     * @return
     */
    public List<AskTagVo> getTagsByMenuId(Integer menuId);
}
