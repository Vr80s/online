package com.xczhihui.bxg.online.web.controller.ftl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.utils.HtmlUtil;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.headline.model.OeBxsArticle;
import com.xczhihui.medical.headline.service.IOeBxsArticleService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalRecruitBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

@Controller
@RequestMapping(value = "/index")
public class HomeController extends AbstractFtlController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private IMobileBannerService mobileBannerService;

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    @Autowired
    private IMedicalHospitalBusinessService medicalHospitalBusinessServiceImpl;

    @Autowired
    private IOeBxsArticleService oeBxsArticleService;

    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;

    @Autowired
    private IMedicalHospitalRecruitBusinessService medicalHospitalRecruitBusinessService;


    @Value("${web.url}")
    private String webUrl;


    /**
     * 推荐页面
     *
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws InvocationTargetException, IllegalAccessException {

        ModelAndView view = new ModelAndView("index");

        /**
         * banner图   2.主页banner3.头条banner4.创业banner5.海外banner
         */
        view.addObject("bannerList", bannerService.list(null, null, 2));

        //在线课程 --直播课呗，直播为主、回放其次
        //线下课
        Map<String, Object> map = mobileBannerService.selectPcIndex(PagingFixedType.PC_INDEX.getValue());
        view.addObject("courseTypeList", map);

        /**
         * 名医坐诊   包含坐诊时间的
         *   推荐值最高的取四个
         */
        List<MedicalDoctorVO> listMdv = medicalDoctorBusinessService.selectDoctorRecommendList4Random(0, 0, 4);
        view.addObject("doctorList", listMdv);


        /**
         * 头条推荐
         */
        Page<OeBxsArticle> articles = oeBxsArticleService.selectArticlesByPage(new Page(0, 2), "");
        articles.getRecords().forEach(article ->
                article.setContent(HtmlUtil.getTextFromHtml(article.getContent()))
        );
        view.addObject("articles", articles);

        /**
         * 医馆推荐
         */
        Page<MedicalHospitalVo> hospitalList = medicalHospitalBusinessServiceImpl.selectHospitalPage(new Page<>(0, 6), null, null);
        view.addObject("hospitalList", hospitalList);


        /*********************  右侧的数据   ***************************/


        /**
         * 名医
         */
        view.addObject("doctorCourseList", medicalDoctorBusinessService.selectDoctorCouserByAccountId(0, 3));

        /**
         * 大家专栏
         */
        view.addObject("hotSpecialColumnAuthors", medicalDoctorArticleService.listSpecialAuthorContent(3));

        /**
         * 医馆招聘信息  -- 先屏蔽掉
         */
//	      List<MedicalHospitalRecruitVO> recruits = medicalHospitalRecruitBusinessService.selectRecHospitalRecruit();
//	      view.addObject("recruits", recruits);

        return view;
    }
}
