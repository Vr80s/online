package com.xczhihui.bxg.online.web.controller.ftl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;
import com.xczhihui.bxg.online.common.enums.DoctorType;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.utils.HtmlUtil;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import com.xczhihui.medical.department.vo.MedicalDepartmentVO;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalRecruitBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVO;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Description：医馆页面
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/28 0028 下午 4:50
 **/
@Controller
@RequestMapping(value = "/clinics")
public class ClinicPageController extends AbstractController{

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalHospitalBusinessService medicalHospitalBusinessServiceImpl;
    @Autowired
    private BannerService  bannerService;
    @Autowired
    private IMedicalDepartmentService medicalDepartmentService;
    @Autowired
    private IMedicalHospitalRecruitBusinessService medicalHospitalRecruitBusinessService;

    @RequestMapping(method=RequestMethod.GET)
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


        Page<MedicalHospitalVo> clinics = medicalHospitalBusinessServiceImpl.selectHospitalPage(new Page<>(0,9),null,null);
        view.addObject("clinics", clinics);

        return view;
    }

    @RequestMapping(value="{id}",method=RequestMethod.GET)
    public ModelAndView details(@PathVariable String id) {
        ModelAndView view = new ModelAndView("doctor/details");

        MedicalDoctorVO doctor = medicalDoctorBusinessService.selectDoctorById(id);
        view.addObject("doctor", doctor);

        Page page = new Page<>();
        page.setCurrent(1);
        page.setSize(2);

        Page newsReports = medicalDoctorBusinessService.getNewsReportsByPage(page, id);
        for (Object oeBxsArticleVO:newsReports.getRecords()) {
            OeBxsArticleVO newsReport = (OeBxsArticleVO)oeBxsArticleVO;
            newsReport.setContent(HtmlUtil.getTextFromHtml(newsReport.getContent().replaceAll("\\<.*?\\>", "")));
        }
        view.addObject("newsReports", newsReports);

        Page specialColumns = medicalDoctorBusinessService.getSpecialColumns(page, id);
        for (Object oeBxsArticleVO:specialColumns.getRecords()) {
            OeBxsArticleVO specialColumn = (OeBxsArticleVO)oeBxsArticleVO;
            specialColumn.setContent(HtmlUtil.getTextFromHtml(specialColumn.getContent().replaceAll("\\<.*?\\>","")));
        }
        view.addObject("specialColumns", specialColumns);

        List<MedicalWritingsVO> writings = medicalDoctorBusinessService.getWritingsByDoctorId(id);
        view.addObject("writings", writings);

        doTitleKeyWords(view,doctor.getName()+"-",doctor.getName()+",");

        return view;
    }

    @RequestMapping(value="list",method=RequestMethod.GET)
    public ModelAndView list(Integer current,Integer size,Integer type,String hospitalId,String name,String field,String departmentId,String departmentText,String typeText) {
        ModelAndView view = new ModelAndView("doctor/list");
        current = current==null?1:current;
        size = size==null?10:size;

        Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(new Page(current,size), type, hospitalId, name, field, departmentId);
        view.addObject("doctors", doctors);

        List<MedicalDoctorVO> recDoctors = medicalDoctorBusinessService.selectRecDoctor();
        view.addObject("recDoctors", recDoctors);

        Page page = new Page(0,Integer.MAX_VALUE);
        Page<MedicalDepartment> departments = medicalDepartmentService.page(page);
        view.addObject("departments", departments);

        List<Map> doctorTypeList = DoctorType.getDoctorTypeList();
        view.addObject("doctorTypeList", doctorTypeList);

        StringBuilder title = new StringBuilder();
        StringBuilder keywords = new StringBuilder();
        if(name!=null){
            title.append(name+"-");
            keywords.append(name+",");
        }
        if(type!=null){
            String dt = DoctorType.getDoctorTypeText(type);
            title.append(dt+"-");
            keywords.append(dt+",");
        }
        if(departmentId!=null){
            MedicalDepartment department = medicalDoctorBusinessService.getDepartmentById(departmentId);
            if(department != null){
                String departmentName = department.getName();
                title.append(departmentName+"-");
                keywords.append(departmentName+",");
            }
        }

        doTitleKeyWords(view,title.toString(),keywords.toString());

        return view;
    }
}
