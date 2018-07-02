package com.xczhihui.medical.headline.service.impl;

import static com.xczhihui.common.util.RedisCacheKey.APPRAISE_PRAISE_KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.headline.mapper.OeBxsAppraiseMapper;
import com.xczhihui.medical.headline.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import com.xczhihui.medical.headline.service.IOeBxsAppraiseService;
import com.xczhihui.medical.headline.vo.AppraiseVO;
import com.xczhihui.medical.headline.vo.SimpleUserVO;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
@Service
public class OeBxsAppraiseServiceImpl extends ServiceImpl<OeBxsAppraiseMapper, OeBxsAppraise> implements IOeBxsAppraiseService {

    @Autowired
    private OeBxsArticleMapper oeBxsArticleMapper;
    @Autowired
    private OeBxsAppraiseMapper oeBxsAppraiseMapper;
    @Autowired
    private CacheService cacheService;

    @Override
    public void saveAppraise(OeBxsAppraise oeBxsAppraise) {
        String replyCommentId = oeBxsAppraise.getReplyCommentId();
        Integer articleId = oeBxsAppraise.getArticleId();
        OeBxsArticleVO oeBxsArticleVO = oeBxsArticleMapper.get(articleId);
        if (oeBxsArticleVO == null) {
            throw new MedicalException("文章不存在");
        }
        if (StringUtils.isNotBlank(replyCommentId)) {
            OeBxsAppraise replyTarget = oeBxsAppraiseMapper.selectById(replyCommentId);
            if (replyTarget == null || replyTarget.getIsDelete()) {
                throw new MedicalException("回复的评论已被删除");
            } else {
                oeBxsAppraise.setTargetUserId(replyTarget.getUserId());
            }
        }
        oeBxsAppraise.setId(CodeUtil.getRandomUUID());
        oeBxsAppraiseMapper.insert(oeBxsAppraise);
    }

    @Override
    public void deleteAppraise(String id, String userId) {
        OeBxsAppraise oeBxsAppraise = oeBxsAppraiseMapper.selectById(id);
        if (oeBxsAppraise == null || oeBxsAppraise.getIsDelete()) {
            throw new MedicalException("评论不存在");
        } else if (!oeBxsAppraise.getUserId().equals(userId)) {
            throw new MedicalException("非法请求");
        } else {
            oeBxsAppraise.setIsDelete(true);
            oeBxsAppraiseMapper.updateById(oeBxsAppraise);
        }
    }

    @Override
    public int updatePraiseCnt(String id, boolean praise) {
        if (praise) {
            oeBxsAppraiseMapper.incrAppraisePraiseCnt(id);
        } else {
            oeBxsAppraiseMapper.decrAppraisePraiseCnt(id);
        }
        OeBxsAppraise oeBxsAppraise = oeBxsAppraiseMapper.selectById(id);
        if (oeBxsAppraise != null) {
            return oeBxsAppraise.getPraiseCnt();
        } else {
            return 0;
        }
    }

    @Override
    public List<AppraiseVO> listByArticleId(Integer articleId, String accountId, int pageNumber, int pageSize) {
        Page<AppraiseVO> page = new Page<>(pageNumber, pageSize);
        List<AppraiseVO> oeBxsAppraises = oeBxsAppraiseMapper.listByArticleId(articleId, page);
        List<String> replyIds = new ArrayList<>();
        oeBxsAppraises.forEach(appraiseVO -> {
            String replyCommentId = appraiseVO.getReplyCommentId();
            if (StringUtils.isNotBlank(replyCommentId)) {
                replyIds.add(replyCommentId);
            }
            appraiseVO.setAuthor(new SimpleUserVO(appraiseVO.getUserId(), appraiseVO.getName(), appraiseVO.getSmallHeadPhoto()));
            if (accountId != null) {
                appraiseVO.setSelf(accountId.equals(appraiseVO.getUserId()));
                appraiseVO.setPraised(cacheService.sismenber(APPRAISE_PRAISE_KEY + accountId, appraiseVO.getId()));
            } else {
                appraiseVO.setSelf(false);
                appraiseVO.setPraised(false);
            }
        });

        //处理列表中评论别人的评论的数据
        if (!replyIds.isEmpty()) {
            List<AppraiseVO> replyAppraises = oeBxsAppraiseMapper.listByIds(replyIds);
            if (!replyAppraises.isEmpty()) {
                Map<String, AppraiseVO> appraiseVOMap = replyAppraises.stream().collect(Collectors.toMap(AppraiseVO::getId, appraiseVO -> appraiseVO));
                oeBxsAppraises.forEach(appraiseVO -> {
                    String replyCommentId = appraiseVO.getReplyCommentId();
                    if (StringUtils.isNotBlank(replyCommentId)) {
                        AppraiseVO reply = appraiseVOMap.get(replyCommentId);
                        if (reply != null) {
                            reply.setAuthor(new SimpleUserVO(reply.getUserId(), reply.getName(), reply.getSmallHeadPhoto()));
                        }
                        appraiseVO.setReply(reply);
                        appraiseVO.setSelf(false);
                    }
                });
            }
        }
        return oeBxsAppraises;
    }

    @Override
    public boolean praise(String userId, String appraiseId) {
        Long result = cacheService.sadd(APPRAISE_PRAISE_KEY + userId, appraiseId);
        return result != null && result == 1;
    }

    @Override
    public boolean unPraise(String userId, String appraiseId) {
        Long result = cacheService.srem(APPRAISE_PRAISE_KEY + userId, appraiseId);
        return result != null && result == 1;
    }
}
