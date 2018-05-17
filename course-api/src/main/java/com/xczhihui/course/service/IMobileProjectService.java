package com.xczhihui.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.MobileProject;
import com.xczhihui.course.vo.MenuVo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IMobileProject
 * @Description: app课程专题
 * @Author: wangyishuai
 * @CreateDate: 2018/1/16 11:39
 **/
public interface IMobileProjectService {
    /** 获取课程专题列表 type：1 推荐 2 分类
     * Description：
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 13:58
     **/
    public Page<MobileProject> selectMobileProjectPage(Page<MobileProject> page, Integer type);
    /**
     * Description：获取课程类型列表
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 14:00
     **/
    List<Map<String, Object>> getCourseType();
    
    /**
     * 查找分类
     * @return
     */
    public List<MenuVo> selectMenuVo();
    
}
