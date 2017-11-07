package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.ArticleService;
import com.xczhihui.bxg.online.web.vo.AppraiseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 文章控制层
 *
 * @author 康荣彩
 * @create 2016-08-30 21:04
 */
@RestController
@RequestMapping(value = "/bxs/article")
public class ArticleController {

    @Autowired
    private ArticleService service;
    /**
     * 获取博学社banner信息
     * @return
     */
    @RequestMapping(value = "/getArticleBanner",method= RequestMethod.GET)
    public ResponseObject  getArticleBanner() {
         return ResponseObject.newSuccessResponseObject(service.getArticleBanner());
    }

    /**
     * 获取文章分类
     * @return
     */
    @RequestMapping(value = "/getArticleType",method= RequestMethod.GET)
    public ResponseObject  getArticleType(){
        return  ResponseObject.newSuccessResponseObject(service.getArticleType());
    }
    /**
     * 按分类获取分页后的文章
     * @param type
     * @return
     */
    @RequestMapping(value = "/getPaperArticle",method= RequestMethod.GET)
    public ResponseObject  getPaperArticle(Integer pageNumber, Integer pageSize,String type,String tagId){
        return ResponseObject.newSuccessResponseObject(service.getPaperArticle(pageNumber, pageSize, type,tagId));
    }

    /**
     * 获取热门文章
     * @return
     */
    @RequestMapping(value = "/getHotArticle",method= RequestMethod.GET)
    public ResponseObject  getHotArticle(){
        return ResponseObject.newSuccessResponseObject(service.getHotArticle());
    }


    /**
     * 热门标签
     * @return
     */
    @RequestMapping(value = "/getHotTags",method= RequestMethod.GET)
    public ResponseObject getHotTags() {
         return  ResponseObject.newSuccessResponseObject(service.getHotTags());
    }


    /**
     * 获取文章信息，更具文章id
     * @return
     */
    @RequestMapping(value = "/getArticleById",method= RequestMethod.GET)
    public ResponseObject  getArticleById(Integer articleId,Integer preId,HttpServletRequest request){
        return  ResponseObject.newSuccessResponseObject(service.updateBrowseSumAndgetArticleById(articleId,preId, request));
    }


    /**
     * 相关推荐
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/getCorrelationTitle",method= RequestMethod.GET)
    public ResponseObject getCorrelationTitle(Integer articleId){
       return ResponseObject.newSuccessResponseObject(service.getCorrelationTitle(articleId));
    }

    /**
     * 保存评论信息
     * @param appraiseVo
     */
    @RequestMapping(value = "/saveAppraise",method= RequestMethod.POST)
    public ResponseObject  saveAppraise(AppraiseVo appraiseVo,HttpServletRequest request){
         service.saveAppraise(appraiseVo, request);
         return ResponseObject.newSuccessResponseObject("评论成功!");
    }


    /**
     * 根据文章id，获取此文章下所有评论
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/getAppraiseByArticleId",method= RequestMethod.GET)
    public ResponseObject getAppraiseByArticleId(Integer articleId,Integer pageNumber,Integer pageSize,HttpServletRequest request){
        return ResponseObject.newSuccessResponseObject(service.getAppraiseByArticleId(articleId,pageNumber,pageSize,request));
    }

    /**
     * 更新文章点赞数
     * @param praiseSum 文章点赞数量
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatePraiseSum",method= RequestMethod.GET)
    public ResponseObject updatePraiseSum(Integer articleId,Integer praiseSum,HttpServletRequest request){
         return   ResponseObject.newSuccessResponseObject(service.updatePraiseSum(articleId,praiseSum,request));
    }

    /**
     * 删除评论
     * @param appraiseId
     * @param request
     */
    @RequestMapping(value = "/deleteAppraiseId",method= RequestMethod.POST)
    public  ResponseObject   deleteAppraiseId(String appraiseId,HttpServletRequest request){
         service.deleteAppraiseId(appraiseId, request);
         return  ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 获取热门课程
     * @return
     */
    @RequestMapping(value = "/getHotCourses",method= RequestMethod.GET)
    public ResponseObject getHotCourses(){
        List<Map<String,Object>> courses = service.getHotCourses();
        for (Map<String, Object> course : courses) {
            course.put("original_cost",new java.text.DecimalFormat("0.00").format(course.get("original_cost")));
            course.put("current_price",new java.text.DecimalFormat("0.00").format(course.get("current_price")));
        }
        return  ResponseObject.newSuccessResponseObject(courses);
    }
}
