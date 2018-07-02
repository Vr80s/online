package com.xczhihui.medical.headline.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.headline.model.OeBxsAppraisePraise;

/**
 * @author hejiwei
 */
public interface OeBxsAppraisePraiseMapper extends BaseMapper<OeBxsAppraisePraise> {

    /**
     * 保存点赞数据
     *
     * @param userId     点赞用户id
     * @param appraiseId 评论id
     * @param praiseTime 点赞时间
     * @return
     */
    @Insert({"INSERT INTO oe_appraise_praise (user_id,appraise_id,praise_time) VALUES (#{userId},#{appraiseId},#{praiseTime})" +
            " ON DUPLICATE KEY UPDATE praise_time=#{praiseTime}, deleted = false"})
    int savePraise(@Param("userId") String userId, @Param("appraiseId") String appraiseId, @Param("praiseTime") Date praiseTime);


}
