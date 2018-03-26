package com.xczhihui.bxg.online.web.controller.ftl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.enums.DoctorType;
import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.bxg.online.web.vo.BannerVo;
import com.xczhihui.medical.department.vo.MedicalDepartmentVO;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Description：医师页面
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/26 0026 上午 11:59
 **/
@Controller
@RequestMapping(value = "/")
public class PractitionerController {

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private BannerService  bannerService;

    @RequestMapping(value="doctors",method=RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("practitioner");

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
}
