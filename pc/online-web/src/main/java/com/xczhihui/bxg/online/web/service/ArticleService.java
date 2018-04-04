package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.web.vo.AppraiseVo;
import com.xczhihui.bxg.online.web.vo.ArticleVo;
import com.xczhihui.medical.headline.model.OeBxsArticle;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *   ArticleService:文章信息业务层接口
 * * @author Rongcai Kang
 */
public interface ArticleService {

    /**
     * 获取博学社banner信息
     * @return
     */
     public List<Map<String,Object>>   getArticleBanner();

    /**
     * 获取文章分类
     * @return
     */
     public  List<Map<String,Object>>  getArticleType();

    /**
     * 按分类获取分页后的文章
     * @param type
     * @return
     */
    public  Page<ArticleVo>  getPaperArticle(Integer pageNumber, Integer pageSize,String type,String tagId);

    /**
     * 获取热门文章
     * @return
     */
    public List<Map<String,Object>>  getHotArticle();

    /**
     * 获取热门标签
     * @return
     */
    public List<Map<String,Object>>  getHotTags();

    /**
     * 获取文章信息更具文章id
     * @return
     */
    public Map<String,Object>  updateBrowseSumAndgetArticleById(Integer articleId,Integer preId,HttpServletRequest request);

    /**
     * 相关推荐
     * @param articleId
     * @return
     */
    public List<Map<String,Object>> getCorrelationTitle(Integer articleId);


    /**
     * 保存评论信息
     * @param appraiseVo
     */
    public void  saveAppraise(AppraiseVo appraiseVo,HttpServletRequest request);

    /**
     * 根据文章id，获取此文章下所有评论
     * @param articleId
     * @return
     */
    public Page<AppraiseVo>  getAppraiseByArticleId(Integer articleId,Integer pageNumber,Integer pageSize,HttpServletRequest request);


    /**
     * 更新文章点赞数
     * @param praiseSum
     * @param request
     * @return
     */
    public  Map<String,Object>   updatePraiseSum(Integer articleId,Integer praiseSum,HttpServletRequest request);


    /**
     * 删除文章
     * @param appraiseId
     * @param request
     */
    public void deleteAppraiseId(String appraiseId, HttpServletRequest request);


    /**
     * 获取热门课程
     * @return
     */
    public List<Map<String,Object>>  getHotCourses();
}
