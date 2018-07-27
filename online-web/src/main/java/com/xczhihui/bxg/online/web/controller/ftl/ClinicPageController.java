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
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.field.model.MedicalField;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAnnouncementService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalRecruitBusinessService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalSolrService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVO;
import com.xczhihui.medical.hospital.vo.MedicalHospitalSolrVO;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

/**
 * Description：医馆页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/28 0028 下午 4:50
 **/
@Controller
@RequestMapping(value = "/clinics")
public class ClinicPageController extends AbstractFtlController {

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalHospitalBusinessService medicalHospitalBusinessServiceImpl;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private IMedicalHospitalRecruitBusinessService medicalHospitalRecruitBusinessService;
    @Autowired
    private IMedicalHospitalAnnouncementService medicalHospitalAnnouncementService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IMedicalHospitalSolrService medicalHospitalSolrServiceImpl;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("clinic/clinics");

        List<BannerVo> banners = bannerService.list(null, null, 7);
        view.addObject("banners", banners);

        List<MedicalHospitalVo> recClinics = medicalHospitalBusinessServiceImpl.selectRecHospital();
        view.addObject("recClinics", recClinics);

        List<MedicalFieldVO> hotFields = medicalHospitalBusinessServiceImpl.getHotField();
        view.addObject("hotFields", hotFields);

        List<MedicalHospitalRecruitVO> recruits = medicalHospitalRecruitBusinessService.selectRecHospitalRecruit();
        view.addObject("recruits", recruits);


        Page<MedicalHospitalVo> clinics = medicalHospitalBusinessServiceImpl.selectHospitalPage(new Page<>(0, 9), null, null);
        view.addObject("clinics", clinics);

        return view;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable String id) {
        ModelAndView view = new ModelAndView("clinic/details");

        MedicalHospitalVo clinic = medicalHospitalBusinessServiceImpl.selectHospitalById(id);
        if (clinic == null) {
            return to404();
        }

        view.addObject("clinic", clinic);

        Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(new Page<>(1, 4), null, id, null, null, null);

        view.addObject("doctors", doctors);

        List<MedicalHospitalRecruitVO> recruits = medicalHospitalRecruitBusinessService.selectHospitalRecruitByHospitalId(id);
        view.addObject("recruits", recruits);

        List<MedicalHospitalVo> recClinics = medicalHospitalBusinessServiceImpl.selectRecHospital();
        view.addObject("recClinics", recClinics);
        String accountId = medicalHospitalBusinessServiceImpl.getAccountIdByHospitalId(id);
        if (accountId != null) {
            view.addObject("courses", courseService.selectLecturerAllCourse(new Page<CourseLecturVo>(1, 6), accountId).getRecords());
            view.addObject("accountId", accountId);
        }
        view.addObject("announcement", medicalHospitalAnnouncementService.findNewestOne(id));

        doTitleKeyWords(view, clinic.getName() + "-", clinic.getName() + ",");

        return view;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "page", required = false) Integer current, Integer size, String name, String field) throws IOException, SolrServerException {
        ModelAndView view = new ModelAndView("clinic/list");
        current = current == null ? 1 : current;
        size = size == null ? 20 : size;

//        Page<MedicalHospitalVo> clinics = medicalHospitalBusinessServiceImpl.selectHospitalPage(new Page<>(current, size), name, field);
        Page<MedicalHospitalSolrVO> clinics = medicalHospitalSolrServiceImpl.selectHospitalListBySolr(new Page<>(current, size), name, field);
        view.addObject("clinics", clinics);

        Page<MedicalFieldVO> fields = medicalHospitalBusinessServiceImpl.getFieldsPage(new Page(1, Integer.MAX_VALUE));
        view.addObject("fields", fields);

        StringBuilder title = new StringBuilder();
        StringBuilder keywords = new StringBuilder();
        Map echoMap = new HashMap();
        if (StringUtils.isNotBlank(name)) {
            title.append(name + "-");
            keywords.append(name + ",");
            echoMap.put("name", name);
        }
        if (StringUtils.isNotBlank(field)) {
            MedicalField medicalField = medicalHospitalBusinessServiceImpl.getFieldById(field);
            if (medicalField != null) {
                String fieldName = medicalField.getName();
                title.append(fieldName + "-");
                keywords.append(fieldName + ",");
                echoMap.put("field", field);
                echoMap.put("fieldText", fieldName);
            }
        }

        doTitleKeyWords(view, title.toString(), keywords.toString());
        doConditionEcho(view, echoMap);

        return view;
    }


    @RequestMapping(value = "{id}/doctors", method = RequestMethod.GET)
    public ModelAndView doctors(@PathVariable String id) {
        ModelAndView view = new ModelAndView("clinic/doctors");

        MedicalHospitalVo clinic = medicalHospitalBusinessServiceImpl.selectHospitalById(id);
        if (clinic == null) {
            return to404();
        }
        view.addObject("clinic", clinic);

        List<MedicalDoctorVO> recDoctors = medicalDoctorBusinessService.selectRecDoctor();
        view.addObject("recDoctors", recDoctors);

        Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(new Page<>(1, 10), null, id, null, null, null);
        view.addObject("doctors", doctors);

        doTitleKeyWords(view, clinic.getName() + "-", clinic.getName() + ",");

        return view;
    }
}
