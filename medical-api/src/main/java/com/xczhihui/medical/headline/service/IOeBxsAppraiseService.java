package com.xczhihui.medical.headline.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import com.xczhihui.medical.headline.vo.AppraiseVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
public interface IOeBxsAppraiseService extends IService<OeBxsAppraise> {

    /**
     * 保存评价
     *
     * @param oeBxsAppraise
     */
    void saveAppraise(OeBxsAppraise oeBxsAppraise);

    /**
     * 删除评论
     *
     * @param id
     * @param userId
     */
    void deleteAppraise(String id, String userId);

    /**
     * 更新点赞数
     *
     * @param id
     * @param praise
     * @return
     */
    int updatePraiseCnt(String id, boolean praise);

    /**
     * 文章id查询评论列表
     *
     * @param articleId 文章id
     * @param accountId 用户id
     * @param page      当前页
     * @param pageSize  分页数量
     * @return
     */
    List<AppraiseVO> listByArticleId(Integer articleId, String accountId, int page, int pageSize);

    /**
     * 赞评论
     *
     * @param userId
     * @param appraiseId
     * @return
     */
    boolean praise(String userId, String appraiseId);

    /**
     * 取消赞评论
     *
     * @param userId
     * @param appraiseId
     * @return
     */
    boolean unPraise(String userId, String appraiseId);
}
