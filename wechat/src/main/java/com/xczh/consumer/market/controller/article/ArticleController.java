package com.xczh.consumer.market.controller.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;

/**
 * @author hejiwei
 */
@RequestMapping("/xczh/article")
@RestController
public class ArticleController {

    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;

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
        return ResponseObject.newSuccessResponseObject(oeBxsArticleVO);
    }
}
