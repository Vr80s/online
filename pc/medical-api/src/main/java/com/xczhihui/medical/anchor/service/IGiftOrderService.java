package com.xczhihui.medical.anchor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;

import java.util.List;

public interface IGiftOrderService {

    /**
     * 获取用户的礼物订单列表
     * @param userId 用户id
     */
    Page<UserCoinIncreaseVO> list(String userId, Page<UserCoinIncreaseVO> page);

    /**
     * 礼物订单排行榜
     * @param userId 用户id
     */
    List<UserCoinIncreaseVO> sort(String userId);

}
