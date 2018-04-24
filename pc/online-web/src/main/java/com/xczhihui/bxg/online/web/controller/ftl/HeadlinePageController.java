package com.xczhihui.bxg.online.web.controller.ftl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.ArticleService;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.utils.HtmlUtil;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorWritingService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import com.xczhihui.medical.headline.model.OeBxsArticle;
import com.xczhihui.medical.headline.service.IOeBxsArticleService;

/**
 * Description：头条页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/28 0028 下午 4:50
 **/
@Controller
@RequestMapping(value = "/headline")
public class HeadlinePageController extends AbstractController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IOeBxsArticleService oeBxsArticleService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;
    @Autowired
    private IMedicalDoctorWritingService medicalDoctorWritingService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        List<Map<String, Object>> articleType = articleService.getArticleType();
        return new ModelAndView("redirect:headline/" + articleType.get(0).get("id"));
    }

    @RequestMapping(value = "{type}", method = RequestMethod.GET)
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

        Page<OeBxsArticle> articles = oeBxsArticleService.selectArticlesByPage(new Page(current, size), type);
        view.addObject("articles", articles);
        articles.getRecords().forEach(article ->
                article.setContent(HtmlUtil.getTextFromHtml(article.getContent()))
        );
        Map echoMap = new HashMap();
        echoMap.put("type", type);
        doConditionEcho(view, echoMap);

        return view;
    }

    @RequestMapping(value = "list/{type}", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "page", required = false) Integer current, Integer size, @PathVariable String type) {
        ModelAndView view = new ModelAndView("headline/list");

        current = current == null ? 1 : current;
        size = size == null ? 10 : size;
        List<Map<String, Object>> hotArticle = articleService.getHotArticle();
        view.addObject("hotArticle", hotArticle);
        List<Map<String, Object>> articleType = articleService.getArticleType();
        view.addObject("articleType", articleType);
        type = type == null ? (String) articleType.get(0).get("id") : type;
        String typeText = null;
        for (Map<String, Object> map : articleType) {
            if (map.get("id").equals(type)) {
                typeText = (String) map.get("name");
            }
        }
        Page<OeBxsArticle> articles = oeBxsArticleService.selectArticlesByPage(new Page(current, size), type);
        view.addObject("articles", articles);
        articles.getRecords().forEach(article ->
                article.setContent(HtmlUtil.getTextFromHtml(article.getContent()))
        );
        Map echoMap = new HashMap();
        echoMap.put("type", type);
        doConditionEcho(view, echoMap);
        doTitleKeyWords(view, typeText + "-", typeText + ",");

        return view;
    }

    @RequestMapping(value = "details/{id}", method = RequestMethod.GET)
    public ModelAndView details(HttpServletRequest request, @RequestParam(value = "page", required = false, defaultValue = "1") Integer current,
                                @RequestParam(defaultValue = "10") Integer size, @PathVariable Integer id) {
        ModelAndView view = new ModelAndView("headline/details");
        OeBxsArticle article = oeBxsArticleService.selectArticleById(id);
        view.addObject("article", article);

        OnlineUser onlineUser = getOnlineUserNull(request);
        String userId = onlineUser == null ? "" : onlineUser.getId();

        Page<OeBxsAppraise> appraises = oeBxsArticleService.selectArticleAppraiseById(new Page(current, size), id, userId);
        view.addObject("appraises", appraises);
        String typeId = article.getTypeId();
        if (typeId == null) {
            view.addObject("writing", medicalDoctorWritingService.findByArticleId(id));
//            view.addObject("writings", medicalDoctorWritingService.)
        } else {
            if (HeadlineType.MYBD.getCode().equals(typeId)) {
                List<OeBxsArticleVO> recentlyNewsReports = medicalDoctorBusinessService.getRecentlyNewsReports();
                view.addObject("suggestedArticles", recentlyNewsReports);
            } else {
                List<Map<String, Object>> hotArticles = articleService.getHotArticle();
                view.addObject("suggestedArticles", hotArticles);
            }
        }

        //名医报道
        if (HeadlineType.MYBD.getCode().equals(typeId)) {
            view.addObject("reportDoctors", medicalDoctorArticleService.listReportDoctorByArticleId(id));
        } else if (HeadlineType.DJZL.getCode().equals(typeId)) {
            view.addObject("authors", medicalDoctorArticleService.listHotSpecialColumnAuthorByArticleId(id));
        }

        Map echoMap = new HashMap();
        echoMap.put("id", id);
        doConditionEcho(view, echoMap);

        doTitleKeyWords(view, article.getTitle() + "-", article.getTitle() + ",");
        String content = HtmlUtil.getTextFromHtml(article.getContent());
        content = content.length() < 100 ? content : content.substring(0, 99);
        view.addObject("description", content);

        return view;
    }

}
