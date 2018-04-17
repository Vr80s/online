package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.wechat.course.model.Criticize;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface ICriticizeService extends IService<Criticize> {

    Map<String,Object> getCourseCriticizes(Page<Criticize> page, Integer courseId,String userId) throws UnsupportedEncodingException;

    Map<String,Object> getAnchorCriticizes(Page<Criticize> page, String anchorUserId,String userId) throws UnsupportedEncodingException;
}
