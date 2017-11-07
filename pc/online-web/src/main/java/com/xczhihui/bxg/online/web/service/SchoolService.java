package com.xczhihui.bxg.online.web.service;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.online.web.vo.SchoolVo;

import java.util.List;

/**
 * 学校信息业务层接口类
 *
 * @author 康荣彩
 * @create 2016-08-29 18:31
 */
public interface SchoolService {

    /**
     * 根据市级编号 查找对应的学校信息
     * @param cityId
     * @return
     */
    public List<SchoolVo> getSchoolList(String cityId);
}
