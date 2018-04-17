package com.xczhihui.headline.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.headline.service.ArticleService;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.TagVo;

/**
 * Description：专栏 creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>
 * email: yuruixin@ixincheng.com
 * @Date: 2018/4/8 0008 上午 10:47
 **/
@Controller
@RequestMapping("headline/column")
public class ColumnController extends AbstractController {
    protected final static String HEADLINE_PATH_PREFIX = "/headline/";

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        List<ArticleTypeVo> articleTypes = articleService.getArticleTypes();
        List<TagVo> tags = articleService.getTags();
        request.setAttribute("articleTypes", articleTypes);
        request.setAttribute("tags", tags);
        return HEADLINE_PATH_PREFIX + "/columnList";
    }

}
