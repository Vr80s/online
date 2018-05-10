package com.xczhihui.medical.headline.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import org.apache.ibatis.annotations.Param;

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

    List<OeBxsAppraise> selectArticleAppraiseById(@Param("page") Page<OeBxsAppraise> page, @Param("id") Integer id, @Param("userId") String userId);
}