package com.xczhihui.medical.anchor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;

import java.util.List;

public interface ICourseOrderService {

    /**
     * 获取用户的课程订单列表
     * @param userId 用户id
     */
    Page<UserCoinIncreaseVO> list(String userId, Page<UserCoinIncreaseVO> page);
}
