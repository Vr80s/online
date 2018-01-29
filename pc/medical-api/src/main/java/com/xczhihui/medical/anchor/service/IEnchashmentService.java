package com.xczhihui.medical.anchor.service;

import com.xczhihui.bxg.online.common.enums.OrderFrom;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-29
 */
public interface IEnchashmentService {

    void saveEnchashmentApplyInfo(String userId, BigDecimal enchashmentSum, int bankCardId, OrderFrom orderFrom);
}
