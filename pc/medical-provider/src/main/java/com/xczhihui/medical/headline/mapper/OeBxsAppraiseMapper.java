package com.xczhihui.medical.headline.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.headline.model.OeBxsAppraise;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
public interface OeBxsAppraiseMapper extends BaseMapper<OeBxsAppraise> {

    List<OeBxsAppraise> selectArticleAppraiseById(Page<OeBxsAppraise> page, Integer id, String userId);
}