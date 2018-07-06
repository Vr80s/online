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
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
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
        OeBxsArticleVO oeBxsArticleVO = medicalDoctorArticleService.get(id);
        if (oeBxsArticleVO == null) {
            return ResponseObject.newErrorResponseObject("文章找不到");
        }
        if (oeBxsArticleVO.getTypeId() != null && oeBxsArticleVO.getTypeId().equals(HeadlineType.YA.getCode())) {
            oeBxsArticleVO.setType("医案");
        } else {
            oeBxsArticleVO.setType("文章");
        }
        oeBxsArticleVO.setContent(IStringUtil.filterLinkTag(oeBxsArticleVO.getContent()));
        oeBxsArticleVO.setContentUrl(returnOpenUri + "/xcview/html/article_fragment.html?id=" + id);
        return ResponseObject.newSuccessResponseObject(oeBxsArticleVO);
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
