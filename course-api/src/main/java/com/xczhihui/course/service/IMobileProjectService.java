package com.xczhihui.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.MobileProject;

/**
 * @ClassName: IMobileProject
 * @Description: app课程专题
 * @Author: wangyishuai
 * @CreateDate: 2018/1/16 11:39
 **/
public interface IMobileProjectService {
    public Page<MobileProject> selectMobileProjectPage(Page<MobileProject> page, Integer type);
}
