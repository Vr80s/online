package com.xczhihui.bxg.online.web.controller.ftl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.utils.HtmlUtil;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.DoctorType;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import com.xczhihui.medical.department.vo.MedicalDepartmentVO;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorSolrService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorWritingService;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorSolrVO;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;

/**
 * Description：医师页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/26 0026 上午 11:59
 **/
@Controller
@RequestMapping(value = "/doctors")
public class DoctorPageController extends AbstractFtlController {

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDoctorSolrService medicalDoctorSolrService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private IMedicalDepartmentService medicalDepartmentService;
    @Autowired
    private IMedicalDoctorWritingService medicalDoctorWritingService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;
    @Autowired
    private ICourseService courseService;
    
    @Autowired
    private IMobileHotSearchService mobileHotSearchService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("doctor/doctors");

        List<BannerVo> banners = bannerService.list(null, null, 6);
        view.addObject("banners", banners);

        
        //医师默认搜索框
        view.addObject("defaultSearch", mobileHotSearchService.HotSearchListByString(SearchType.DOCTOR_DEFAULT_SEARCH.getCode()));
        
        
        List<MedicalDepartmentVO> hotDepartments = medicalDoctorBusinessService.getHotDepartment();
        view.addObject("hotDepartments", hotDepartments);

        List<OeBxsArticleVO> recentlyNewsReports = medicalDoctorBusinessService.getRecentlyNewsReports();
        view.addObject("recentlyNewsReports", recentlyNewsReports);

        List<OeBxsArticleVO> recentlyWritings = medicalDoctorArticleService.listPublicRecommendWritings(1, 5).getRecords();
        view.addObject("recentlyWritings", recentlyWritings);

        Page<MedicalDoctorVO> page = new Page<>();
        page.setCurrent(1);
        page.setSize(4);

        Page<MedicalDoctorVO> doctors1 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.MQNZY.getCode(), null, null, null, null);
        view.addObject("doctors1", doctors1);

        Page<MedicalDoctorVO> doctors2 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.MLZY.getCode(), null, null, null, null);
        view.addObject("doctors2", doctors2);

        Page<MedicalDoctorVO> doctors3 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.SSMZZY.getCode(), null, null, null, null);
        view.addObject("doctors3", doctors3);

        Page<MedicalDoctorVO> doctors4 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.GYDS.getCode(), null, null, null, null);
        view.addObject("doctors4", doctors4);

        Page<MedicalDoctorVO> doctors5 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.GZY.getCode(), null, null, null, null);
        view.addObject("doctors5", doctors5);

        return view;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable String id) {
        ModelAndView view = new ModelAndView("doctor/details");

        MedicalDoctorVO doctor = medicalDoctorBusinessService.selectDoctorById(id);
        if (doctor == null) {
            return to404();
        }

        doctor.setWorkTime(XzStringUtils.workTimeScreen(doctor.getWorkTime()));

        view.addObject("doctor", doctor);

        Page page = new Page<>();
        page.setCurrent(1);
        page.setSize(2);

        Page newsReports = medicalDoctorBusinessService.getNewsReportsByPage(page, id);
        for (Object oeBxsArticleVO : newsReports.getRecords()) {
            OeBxsArticleVO newsReport = (OeBxsArticleVO) oeBxsArticleVO;
            newsReport.setContent(HtmlUtil.getTextFromHtml(newsReport.getContent()));
        }
        view.addObject("newsReports", newsReports);

        Page specialColumns = medicalDoctorBusinessService.getSpecialColumns(page, id);
        for (Object oeBxsArticleVO : specialColumns.getRecords()) {
            OeBxsArticleVO specialColumn = (OeBxsArticleVO) oeBxsArticleVO;
            specialColumn.setContent(HtmlUtil.getTextFromHtml(specialColumn.getContent()));
        }
        view.addObject("specialColumns", specialColumns);
        view.addObject("writings", medicalDoctorWritingService.listPublic(1, 6, id).getRecords());
        MedicalDoctorAccount medicalDoctorAccount = medicalDoctorBusinessService.getByDoctorId(id);
        if (medicalDoctorAccount != null) {
            view.addObject("courses", courseService.selectLecturerAllCourse(new Page<CourseLecturVo>(1, 6), medicalDoctorAccount.getAccountId()).getRecords());
            view.addObject("accountId", medicalDoctorAccount.getAccountId());
        }
        view.addObject("relationDoctors", medicalDoctorBusinessService.listRandomByType(doctor.getType(), 8));

        doTitleKeyWords(view, doctor.getName() + "-", doctor.getName() + ",");

        return view;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "page", required = false) Integer current, Integer size, String name, DoctorQueryVo dqv) throws IOException, SolrServerException {
        ModelAndView view = new ModelAndView("doctor/list");
        current = current == null ? 1 : current;
        size = size == null ? 10 : size;
        dqv.setQueryKey(name);
//        Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(new Page(current, size), type, hospitalId, name, field, departmentId);

        Page<MedicalDoctorSolrVO> doctors = medicalDoctorSolrService.selectDoctorListBySolr(new Page(current, size), dqv);

        view.addObject("doctors", doctors);

        //医师默认搜索框
        view.addObject("defaultSearch", mobileHotSearchService.HotSearchListByString(3));
        
        
        List<MedicalDoctorVO> recDoctors = medicalDoctorBusinessService.selectRecDoctor();
        view.addObject("recDoctors", recDoctors);

        Page page = new Page(0, Integer.MAX_VALUE);
        Page<MedicalDepartment> departments = medicalDepartmentService.page(page);
        view.addObject("departments", departments);

        List<Map> doctorTypeList = DoctorType.getDoctorTypeList();
        view.addObject("doctorTypeList", doctorTypeList);

        StringBuilder title = new StringBuilder();
        StringBuilder keywords = new StringBuilder();
        Map echoMap = new HashMap();
        if (StringUtils.isNotBlank(dqv.getQueryKey())) {
            title.append(dqv.getQueryKey() + "-");
            keywords.append(dqv.getQueryKey() + ",");

            echoMap.put("name", dqv.getQueryKey());
        }
        if (StringUtils.isNotBlank(dqv.getType())) {
            String dt = DoctorType.getDoctorTypeText(Integer.valueOf(dqv.getType()));
            title.append(dt + "-");
            keywords.append(dt + ",");

            echoMap.put("type", Integer.valueOf(dqv.getType()));
            echoMap.put("typeText", dt);
        }
        if (StringUtils.isNotBlank(dqv.getDepartmentId())) {
            MedicalDepartment department = medicalDoctorBusinessService.getDepartmentById(dqv.getDepartmentId());
            if (department != null) {
                String departmentName = department.getName();
                title.append(departmentName + "-");
                keywords.append(departmentName + ",");

                echoMap.put("departmentId", dqv.getDepartmentId());
                echoMap.put("departmentText", departmentName);
            }
        }

        doTitleKeyWords(view, title.toString(), keywords.toString());
        doConditionEcho(view, echoMap);

        return view;
    }

    @RequestMapping(value = "writing", method = RequestMethod.GET)
    public ModelAndView getPublicWritingByDoctorId(@RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView("doctor/writing");
        Page<OeBxsArticleVO> oeBxsArticleVOPage = medicalDoctorArticleService.listPublicWritings(page, 10);
        oeBxsArticleVOPage.getRecords().forEach(oeBxsArticleVO -> {
            oeBxsArticleVO.setContent(HtmlUtil.getTextFromHtml(oeBxsArticleVO.getContent()));
        });
        modelAndView.addObject("writings", oeBxsArticleVOPage);
        modelAndView.addObject("doctors", medicalDoctorBusinessService.selectRecDoctor());
        return modelAndView;
    }
}
