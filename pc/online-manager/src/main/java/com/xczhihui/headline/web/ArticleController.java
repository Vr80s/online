package com.xczhihui.headline.web;

import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.headline.service.ArticleService;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.TagVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.*;

/**
 * 文章管理
 *
 * @author yxd
 */

@Controller
@RequestMapping("headline/article")
public class ArticleController extends AbstractController {
    protected final static String HEADLINE_PATH_PREFIX = "/headline/";

    @Autowired
    private ArticleService articleService;
    @Autowired
    private AttachmentCenterService attachmentCenterService;

    @Value("${online.web.url}")
    private String weburl;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        List<ArticleTypeVo> articleTypes = articleService.getArticleTypes();
        List<TagVo> tags = articleService.getTags();
        request.setAttribute("articleTypes", articleTypes);
        request.setAttribute("tags", tags);
        return HEADLINE_PATH_PREFIX + "/articleList";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo articles(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        ArticleVo searchVo = new ArticleVo();

        Group title = groups.findByName("search_title");
        if (title != null) {
            searchVo.setTitle(title.getPropertyValue1().toString());
        }
        Group type = groups.findByName("search_type");
        if (type != null) {
            searchVo.setTypeId(type.getPropertyValue1().toString());
        }
        Group status = groups.findByName("statusSearch");
        if (status != null) {
            searchVo.setStatus(Integer.parseInt(status.getPropertyValue1()
                    .toString()));
        }

        Group startTime = groups.findByName("startTime");
        if (startTime != null) {
            searchVo.setStartTime(DateUtil.parseDate(startTime
                    .getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        Group stopTime = groups.findByName("stopTime");
        if (stopTime != null) {
            searchVo.setStopTime(DateUtil.parseDate(stopTime
                    .getPropertyValue1().toString(), "yyyy-MM-dd"));
        }

        Page<ArticleVo> page = articleService.findArticlePage(searchVo,
                currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    @RequestMapping(value = "toAdd")
    public String toAdd(HttpServletRequest request) {
        List<ArticleTypeVo> articleTypes = articleService.getArticleTypes();
        List<TagVo> tags = articleService.getTags();
        request.setAttribute("articleTypes", articleTypes);
        request.setAttribute("tags", tags);
        request.setAttribute("weburl", weburl);
        return HEADLINE_PATH_PREFIX + "/articleAdd";
    }

    /**
     * 添加
     *
     * @param articleVo
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(HttpServletRequest request, ArticleVo articleVo) {
        String content = articleVo.getContent();
        content = ImageUtils.base64ToimageURL(content, attachmentCenterService);
        articleVo.setContent(content);
        articleService.addArticle(articleVo);
        articleService.addArticleTag(articleVo);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 预览
     *
     * @param articleVo
     * @return
     */
    @RequestMapping(value = "addPre", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addPre(HttpServletRequest request, ArticleVo articleVo) {
        String content = articleVo.getContent();
        content = ImageUtils.base64ToimageURL(content, attachmentCenterService);
        articleVo.setContent(content);
        articleVo.setUserId(ManagerUserUtil.getId());
        articleService.addPreArticle(articleVo);
        articleService.addArticleTag(articleVo);
        return ResponseObject.newSuccessResponseObject(articleVo.getId());
    }

    /**
     * 修改
     *
     * @param articleVo
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(HttpServletRequest request, ArticleVo articleVo) {
        String content = articleVo.getContent();
        content = ImageUtils.base64ToimageURL(content, attachmentCenterService);
        articleVo.setContent(content);
        articleService.updateArticle(articleVo);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    @RequestMapping(value = "toEdit")
    public String toEdit(HttpServletRequest request, Integer id, String typeId,
                         String typeName, String tagId, String tagName, String author) {
        ArticleVo article = articleService.findArticleById(id);
        article.setTypeId(typeId);
        article.setTypeName(typeName);
        article.setTagId(tagId);
        article.setTagName(tagName);
        article.setAuthor(author);
        List<ArticleTypeVo> articleTypes = articleService.getArticleTypes();
        List<TagVo> tags = articleService.getTags();
        request.setAttribute("articleTypes", articleTypes);
        request.setAttribute("tags", tags);
        request.setAttribute("article", article);
        request.setAttribute("weburl", weburl);
        return HEADLINE_PATH_PREFIX + "/articleEdit";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) {
        if (ids != null) {
            String[] idsArr = ids.split(",");
            articleService.deletes(idsArr);
        }
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param id id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Integer id) {
        articleService.updateStatus(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * Description：设置文章推荐值
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/4/18 10:00
     **/
    @RequestMapping(value = "updateRecommendSort", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRecommendSort(Integer id, Integer recommendSort, String recommendTime) {
        ResponseObject responseObject = new ResponseObject();
        articleService.updateRecommendSort(id, recommendSort, recommendTime);
        responseObject.setSuccess(true);
        responseObject.setResultObject("修改成功!");
        return responseObject;
    }
}
