package com.xczhihui.medical.anchor.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public interface IAssetService {

    Page<Map> getCoinTransactionPage(Page<Map> page, String id);

    Page<Map> getRmbTransactionPage(Page<Map> page, String id);
}
