package com.xczhihui.bxg.online.manager.headline.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalWritings;
import com.xczhihui.bxg.online.manager.headline.vo.ArticleTypeVo;
import com.xczhihui.bxg.online.manager.headline.vo.ArticleVo;
import com.xczhihui.bxg.online.manager.headline.vo.TagVo;
import com.xczhihui.bxg.online.manager.headline.vo.WritingVo;

public interface WritingService {

	public Page<MedicalWritings> findCoursePage(WritingVo searchVo, int currentPage, int pageSize);

	public List<ArticleTypeVo> getArticleTypes();

	public List<TagVo> getTags();

	public void addWriting(WritingVo writingVo);

	public void addArticleTag(ArticleVo articleVo);

	public WritingVo findWritingById(String id);

	public void updateWriting(WritingVo writingVo);

	public void deletes(String[] ids);

	public void updateStatus(String id);

	public void addPreArticle(ArticleVo articleVo);

	public void updateRecommend(Integer id);

	public void updateMedicalDoctorWritings(String id, String[] doctorId);


}
