package com.xczh.consumer.market.controller.article;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.body.article.AppraiseBody;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.vo.MobileArticleVO;
import com.xczhihui.medical.headline.service.IOeBxsAppraiseService;

/**
 * @author hejiwei
 */
@RequestMapping("/xczh/article")
@RestController
public class ArticleController {

    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;
    @Autowired
    private IOeBxsAppraiseService oeBxsAppraiseService;
    @Autowired
    private CacheService cacheService;
    @Value("${returnOpenidUri}")
    private String returnOpenUri;

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ResponseObject get(@RequestParam int id) {
        MobileArticleVO mobileArticleVO = medicalDoctorArticleService.get(id);
        if (mobileArticleVO == null) {
            return ResponseObject.newErrorResponseObject("文章找不到");
        }
        String typeId = mobileArticleVO.getTypeId();
        if (typeId != null) {
            if (typeId.equals(HeadlineType.YA.getCode())) {
                mobileArticleVO.setType("医案");
            } else if (typeId.equals(HeadlineType.ZZ.getCode())) {
                mobileArticleVO.setType("著作");
            } else if (typeId.equals(HeadlineType.DJZL.getCode())) {
                mobileArticleVO.setType("专栏");
            } else if (typeId.equals(HeadlineType.MYBD.getCode())) {
                mobileArticleVO.setType("媒体报道");
            } else {
                mobileArticleVO.setType("文章");
            }
        } else {
            mobileArticleVO.setType("文章");
        }
        mobileArticleVO.setContent(XzStringUtils.formatA(mobileArticleVO.getContent()));
        mobileArticleVO.setContentUrl(returnOpenUri + "/xcview/html/article_fragment.html?id=" + id);
        return ResponseObject.newSuccessResponseObject(mobileArticleVO);
    }

    @RequestMapping(value = "appraise/list", method = RequestMethod.GET)
    public ResponseObject list(@RequestParam(defaultValue = "1") int pageNumber,
                               @RequestParam(defaultValue = "10") int pageSize, @RequestParam int articleId,
                               @Account(optional = true) Optional<String> accountIdOpt) {
        return ResponseObject.newSuccessResponseObject(oeBxsAppraiseService.listByArticleId(articleId, accountIdOpt.orElse(null), pageNumber, pageSize));
    }

    @RequestMapping(value = "appraise", method = RequestMethod.POST)
    public ResponseObject appraise(AppraiseBody appraiseBody, @Account String accountId) {
        oeBxsAppraiseService.saveAppraise(appraiseBody.build(accountId));
        return ResponseObject.newSuccessResponseObject("提交成功");
    }

    @RequestMapping(value = "appraise", method = RequestMethod.DELETE)
    public ResponseObject delete(@RequestParam String id, @Account String accountId) {
        oeBxsAppraiseService.deleteAppraise(id, accountId);
        return ResponseObject.newErrorResponseObject("删除成功");
    }

    @RequestMapping(value = "appraise/praise", method = RequestMethod.POST)
    public ResponseObject appraisePraise(@RequestParam String id, @Account String accountId, @RequestParam boolean praise) {
        boolean result = praise ? oeBxsAppraiseService.praise(accountId, id) : oeBxsAppraiseService.unPraise(accountId, id);
        if (!result) {
            return ResponseObject.newErrorResponseObject(praise ? "已点过赞了~" : "赞已取消过了~");
        } else {
            return ResponseObject.newSuccessResponseObject(ImmutableMap.of("praiseSum", oeBxsAppraiseService.updatePraiseCnt(id, praise)));
        }
    }
}
