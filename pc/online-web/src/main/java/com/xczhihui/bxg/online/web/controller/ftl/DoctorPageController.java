package com.xczhihui.bxg.online.web.controller.ftl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.enums.DoctorType;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：医师页面
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/26 0026 上午 11:59
 **/
@Controller
@RequestMapping(value = "/doctors")
public class DoctorPageController extends AbstractController{

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private BannerService  bannerService;
    @Autowired
    private IMedicalDepartmentService medicalDepartmentService;

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("doctor/doctors");

        List<BannerVo> banners = bannerService.list(null, null, 6);
        view.addObject("banners", banners);

        List<MedicalDepartmentVO> hotDepartments = medicalDoctorBusinessService.getHotDepartment();
        view.addObject("hotDepartments", hotDepartments);

        List<OeBxsArticleVO> recentlyNewsReports = medicalDoctorBusinessService.getRecentlyNewsReports();
        view.addObject("recentlyNewsReports", recentlyNewsReports);

        List<MedicalWritingsVO> recentlyWritings = medicalDoctorBusinessService.getRecentlyWritings();
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
    public ModelAndView list(@RequestParam(value="page", required=false) Integer current, Integer size, Integer type, String hospitalId, String name, String field, String departmentId) {
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
        Map echoMap = new HashMap();
        if(StringUtils.isNotBlank(name)){
            title.append(name+"-");
            keywords.append(name+",");

            echoMap.put("name",name);
        }
        if(type!=null){
            String dt = DoctorType.getDoctorTypeText(type);
            title.append(dt+"-");
            keywords.append(dt+",");

            echoMap.put("type",type);
            echoMap.put("typeText",dt);
        }
        if(StringUtils.isNotBlank(departmentId)){
            MedicalDepartment department = medicalDoctorBusinessService.getDepartmentById(departmentId);
            if(department != null){
                String departmentName = department.getName();
                title.append(departmentName+"-");
                keywords.append(departmentName+",");

                echoMap.put("departmentId",departmentId);
                echoMap.put("departmentText",departmentName);
            }
        }

        doTitleKeyWords(view,title.toString(),keywords.toString());
        doConditionEcho(view,echoMap);

        return view;
    }
}
