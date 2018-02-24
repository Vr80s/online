package com.xczhihui.medical.anchor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;

public interface ICourseOrderService {

    /**
     * 获取用户的课程订单列表
     * @param userId 用户id
     * @param gradeName 课程名
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    Page<UserCoinIncreaseVO> list(String userId, Page<UserCoinIncreaseVO> page,
                                  String gradeName, String startTime, String endTime);
}
