package com.xczhihui.order.service;

import com.xczhihui.bxg.online.common.domain.UserCoinIncrease;
import com.xczhihui.common.util.bean.Page;

public interface RechargeService {

    public Page<UserCoinIncrease> findUserCoinIncreasePage(
            UserCoinIncrease userCoinIncrease, Integer pageNumber,
            Integer pageSize);

    /**
     * 逻辑批量删除
     *
     * @return void
     */
    public void deletes(String[] ids);

}
