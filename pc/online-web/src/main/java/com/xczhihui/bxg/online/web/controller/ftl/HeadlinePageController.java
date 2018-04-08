package com.xczhihui.bxg.online.web.controller.ftl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.HeadlineType;
import com.xczhihui.bxg.online.web.service.ArticleService;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.utils.HtmlUtil;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import com.xczhihui.medical.headline.model.OeBxsArticle;
import com.xczhihui.medical.headline.service.IOeBxsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：头条页面
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/28 0028 下午 4:50
 **/
@Controller
@RequestMapping(value = "/headline")
public class HeadlinePageController extends AbstractController{

    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IOeBxsArticleService oeBxsArticleService;

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView index() {
        List<Map<String, Object>> articleType = articleService.getArticleType();
        ModelAndView view = new ModelAndView("redirect:headline/"+ articleType.get(0).get("id"));
        return view;
    }

    @RequestMapping(value="{type}",method=RequestMethod.GET)
    public ModelAndView page(@PathVariable String type) {
        ModelAndView view = new ModelAndView("headline/index");

        int current = 1;
        int size = 6;
        List<BannerVo> banners = bannerService.list(null, null, 3);
        view.addObject("banners", banners);
        List<Map<String, Object>> hotArticle = articleService.getHotArticle();
        view.addObject("hotArticle", hotArticle);
        List<OeBxsArticleVO> hotSpecialColumn = medicalDoctorBusinessService.getHotSpecialColumn();
        view.addObject("hotSpecialColumn", hotSpecialColumn);
        List<MedicalDoctorVO> hotSpecialColumnAuthor = medicalDoctorBusinessService.getHotSpecialColumnAuthor();
        view.addObject("hotSpecialColumnAuthor", hotSpecialColumnAuthor);
        List<Map<String, Object>> articleType = articleService.getArticleType();
        view.addObject("articleType", articleType);

        Page<OeBxsArticle> articles = oeBxsArticleService.selectArticlesByPage(new Page(current,size),type);
        view.addObject("articles", articles);
        articles.getRecords().forEach(article ->
                article.setContent(HtmlUtil.getTextFromHtml(article.getContent().replaceAll("\\<.*?\\>", "")))
        );
        Map echoMap = new HashMap();
        echoMap.put("type",type);
        doConditionEcho(view,echoMap);
        return view;
    }

    @RequestMapping(value="list/{type}",method=RequestMethod.GET)
    public ModelAndView list(@RequestParam(value="page", required=false) Integer current, Integer size, @PathVariable String type) {
        ModelAndView view = new ModelAndView("headline/list");

        current = current==null?1:current;
        size = size==null?10:size;
        List<Map<String, Object>> hotArticle = articleService.getHotArticle();
        view.addObject("hotArticle", hotArticle);
        List<Map<String, Object>> articleType = articleService.getArticleType();
        view.addObject("articleType", articleType);
        type = type==null? (String) articleType.get(0).get("id") :type;
        Page<OeBxsArticle> articles = oeBxsArticleService.selectArticlesByPage(new Page(current,size),type);
        view.addObject("articles", articles);
        articles.getRecords().forEach(article ->
                article.setContent(HtmlUtil.getTextFromHtml(article.getContent().replaceAll("\\<.*?\\>", "")))
        );
        Map echoMap = new HashMap();
        echoMap.put("type",type);
        doConditionEcho(view,echoMap);
        return view;
    }

    @RequestMapping(value="details/{id}",method=RequestMethod.GET)
    public ModelAndView details(HttpServletRequest request, @RequestParam(value="page", required=false) Integer current, Integer size, @PathVariable Integer id) {
        ModelAndView view = new ModelAndView("headline/details");
        current = current==null?1:current;
        size = size==null?10:size;
        OeBxsArticle article = oeBxsArticleService.selectArticleById(id);
        String title = "";
        String keywords = "";
        String description = "";
        if(article != null){
            title += article.getTitle();
            keywords += article.getTitle();
            description += HtmlUtil.getTextFromHtml(article.getContent().replaceAll("\\<.*?\\>", "")).substring(0,100);
        }
        view.addObject("article", article);

        OnlineUser onlineUser = getOnlineUserNull(request);
        String userId = onlineUser==null?"":onlineUser.getId();

        Page<OeBxsAppraise> appraises = oeBxsArticleService.selectArticleAppraiseById(new Page(current, size), id, userId);
        view.addObject("appraises", appraises);
        doTitleKeyWordsAndDescription(view,title,keywords,description);

        if(article.getTypeId().equals(HeadlineType.MYBD.getCode())){
            List<OeBxsArticleVO> recentlyNewsReports = medicalDoctorBusinessService.getRecentlyNewsReports();
            view.addObject("suggestedArticles", recentlyNewsReports);
        }else{
            List<Map<String, Object>> hotArticles = articleService.getHotArticle();
            view.addObject("suggestedArticles", hotArticles);
        }

        Map echoMap = new HashMap();
        echoMap.put("id",id);
        doConditionEcho(view,echoMap);
        return view;
    }

}
