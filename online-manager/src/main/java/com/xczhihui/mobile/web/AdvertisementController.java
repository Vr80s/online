package com.xczhihui.mobile.web;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.bxg.online.common.domain.Advertisement;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.medical.service.MedicalEnrollmentRegulationsService;
import com.xczhihui.menu.service.CommonMenuService;
import com.xczhihui.mobile.service.MobileAdvertisementService;
import com.xczhihui.mobile.service.MobileSearchService;
import com.xczhihui.mobile.vo.MobileAdvertisementVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mobile/advertisement")
public class AdvertisementController extends AbstractController {
    protected final static String MOBILE_PATH_PREFIX = "/advertisement/";
    @Autowired
    private MobileSearchService service;
    @Autowired
    private MobileAdvertisementService mobileAdvertisementService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AnchorService anchorService;
    @Autowired
    private CommonMenuService commonMenuService;
    @Autowired
    private MedicalEnrollmentRegulationsService medicalEnrollmentRegulationsService;
    @Value("${web.url}")
    private String weburl;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/advertisement/advertisement");
        List<MenuVo> menus = commonMenuService.list();
        List<CourseVo> courses = new ArrayList<>();
        if (!menus.isEmpty()) {
            courses = courseService.listByMenuId(menus.get(0).getId());
        }
        mav.addObject("courses", courses);
        mav.addObject("anchors", anchorService.listDoctor());
        mav.addObject("regulations", medicalEnrollmentRegulationsService.getAllMedicalEntryInformationList());
        mav.addObject("menus", menus);
        return mav;
    }


    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo courses(TableVo tableVo) {

        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        CourseVo searchVo = new CourseVo();
        MobileAdvertisementVo mobileAdvertisementVo = new MobileAdvertisementVo();
        Group courseName = groups.findByName("search_courseName");

        if (courseName != null) {
            searchVo.setCourseName(courseName.getPropertyValue1().toString());
        }

        Group menuId = groups.findByName("search_menu");
        if (menuId != null) {
            searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1()
                    .toString()));
        }

        Group scoreTypeId = groups.findByName("search_scoreType");
        if (scoreTypeId != null) {
            searchVo.setCourseTypeId(scoreTypeId.getPropertyValue1().toString());
        }

        Group isRecommend = groups.findByName("search_isRecommend");

        if (isRecommend != null) {
            searchVo.setIsRecommend(Integer.parseInt(isRecommend
                    .getPropertyValue1().toString()));
        }

        Group searchCourse = groups.findByName("search_Course");
        if (searchCourse != null) {
            searchVo.setIsCourseDetails(Integer.parseInt(searchCourse
                    .getPropertyValue1().toString()));
        }
        Page<MobileAdvertisementVo> page = mobileAdvertisementService.findMobileAdvertisementPage(mobileAdvertisementVo,
                currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }


    /**
     * 添加数据
     *
     * @param mobileAdvertisementVo
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(MobileAdvertisementVo mobileAdvertisementVo){
        ResponseObject responseObj = new ResponseObject();
        mobileAdvertisementVo.setCreatePerson(ManagerUserUtil.getUsername());
        if (mobileAdvertisementVo.getName() == null) {
            throw new IllegalArgumentException("请输入搜索名称");
        }
        Advertisement existsEntity = mobileAdvertisementService.findAdvertisementByName(
                mobileAdvertisementVo.getName());
        if (existsEntity != null && existsEntity.isDelete() != true) {
            throw new IllegalArgumentException("已经存在");
        }
        try {
            mobileAdvertisementService.save(mobileAdvertisementVo);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增搜索关键字成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增搜索关键字失败");
        }
        return responseObj;
    }

    /**
     * 更新数据
     *
     * @param mobileAdvertisementVo
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(MobileAdvertisementVo mobileAdvertisementVo){
        ResponseObject responseObject = new ResponseObject();

        if (mobileAdvertisementVo == null || mobileAdvertisementVo.getName() == null) {
            throw new IllegalArgumentException("请输入必填项");
        }
        Advertisement existsEntity = mobileAdvertisementService.findAdvertisementByName(
                mobileAdvertisementVo.getName());
        if (existsEntity != null && existsEntity.isDelete() != true) {
            throw new IllegalArgumentException("已经存在");
        }
        Advertisement ms = mobileAdvertisementService.findById(mobileAdvertisementVo.getId().toString());
        ms.setName(mobileAdvertisementVo.getName());
        ms.setImgPath(mobileAdvertisementVo.getImgPath());
        ms.setLinkParam(mobileAdvertisementVo.getLinkParam());
        ms.setRouteType(mobileAdvertisementVo.getRouteType());
        ms.setUrl(mobileAdvertisementVo.getUrl());
        mobileAdvertisementService.update(ms);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("修改完成!");
        return responseObject;
    }

    /**
     * 删除数据
     *
     * @param ids
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException,
            IllegalAccessException {
        String msg = "";
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            msg = service.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage(msg);
        return responseObject;
    }

    /**
     * 禁用or启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            mobileAdvertisementService.updateStatus(id);
            responseObj.setSuccess(true);
            responseObj.setResultObject("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

}
