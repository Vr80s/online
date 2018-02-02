package com.xczhihui.medical.anchor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public interface IAssetService {

    Page<Map> getCoinTransactionPage(Page<Map> page, String id);

    Page<Map> getRmbTransactionPage(Page<Map> page, String id);
}
