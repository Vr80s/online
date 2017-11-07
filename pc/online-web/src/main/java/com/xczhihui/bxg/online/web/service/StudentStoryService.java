package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.bxg.online.web.vo.StudentStoryVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 学员故事服务层
 *
 * @author majian
 * @date 2016-8-18 11:22:14
 */
public interface StudentStoryService {

    /**
     * 获取所有学员故事
     *
     * @return 列表数据
     */
    public List<StudentStoryVo> findListByIndex() throws InvocationTargetException, IllegalAccessException;

    /**
     * 根据编号返回实体
     * @param id
     * @return
     */
    public StudentStoryVo findById(String id) throws InvocationTargetException, IllegalAccessException;
}
