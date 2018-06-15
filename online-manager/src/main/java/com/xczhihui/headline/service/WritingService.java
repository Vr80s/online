package com.xczhihui.headline.service;

import java.util.List;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalWritings;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.TagVo;
import com.xczhihui.headline.vo.WritingVo;

public interface WritingService {

    Page<MedicalWritings> findWritingsPage(WritingVo searchVo,
                                         int currentPage, int pageSize);

    List<ArticleTypeVo> getArticleTypes();

    List<TagVo> getTags();

    void addWriting(WritingVo writingVo);

    void addArticleTag(ArticleVo articleVo);

    WritingVo findWritingById(String id);

    void updateWriting(WritingVo writingVo);

    void deletes(String[] ids);

    void updateStatus(String id);

    void addPreArticle(ArticleVo articleVo);

    void updateMedicalDoctorWritings(String id, String[] doctorId);

}
